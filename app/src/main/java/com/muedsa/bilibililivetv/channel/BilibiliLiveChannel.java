package com.muedsa.bilibililivetv.channel;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.tvprovider.media.tv.Channel;
import androidx.tvprovider.media.tv.ChannelLogoUtils;
import androidx.tvprovider.media.tv.PreviewProgram;
import androidx.tvprovider.media.tv.TvContractCompat;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.AppVersionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressLint("RestrictedApi")
public class BilibiliLiveChannel {
    private static final String TAG = BilibiliLiveChannel.class.getSimpleName();

    private static final int MAX_PROGRAMS_NUM = 8;

    public static void sync(@NonNull Context context, @NonNull LiveRoom liveRoom) {
        try {
            long channelId = getChannelId(context);
            List<PreviewProgram> programs = getPrograms(context, channelId);
            Optional<PreviewProgram> programOptional = programs.stream().filter(p -> liveRoom.getId() == p.getInternalProviderFlag1()).findFirst();
            if (programOptional.isPresent()) {
                context.getContentResolver().delete(TvContractCompat.buildPreviewProgramUri(programOptional.get().getId()), null, null);
            } else {
                if (programs.size() >= MAX_PROGRAMS_NUM) {
                    context.getContentResolver().delete(TvContractCompat.buildPreviewProgramUri(programs.get(0).getId()), null, null);
                }
            }
            createProgramAndPublish(context, channelId, liveRoom);
        } catch (Exception e) {
            Log.e(TAG, "channel sync: ", e);
        }
    }

    public static List<PreviewProgram> getPrograms(@NonNull Context context, long channelId) {
        List<PreviewProgram> programs = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                TvContractCompat.buildPreviewProgramsUriForChannel(channelId),
                PreviewProgram.PROJECTION, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PreviewProgram program = PreviewProgram.fromCursor(cursor);
                if (channelId == program.getChannelId()) {
                    programs.add(program);
                }
            } while (cursor.moveToNext());
        }
        return programs;
    }

    public static void createProgramAndPublish(Context context, long channelId, LiveRoom liveRoom) {
        PreviewProgram.Builder builder = builderProgram(context, channelId, liveRoom);
        context.getContentResolver().insert(TvContractCompat.PreviewPrograms.CONTENT_URI,
                builder.build().toContentValues());
    }

    private static PreviewProgram.Builder builderProgram(Context context, long channelId, LiveRoom liveRoom) {
        return new PreviewProgram.Builder().setChannelId(channelId)
                .setType(TvContractCompat.PreviewPrograms.TYPE_CLIP)
                .setTitle(liveRoom.getUname())
                .setDescription(liveRoom.getTitle())
                .setThumbnailUri(Uri.parse(liveRoom.getSystemCoverImageUrl()))
                .setPosterArtUri(Uri.parse(liveRoom.getCoverImageUrl()))
                .setInternalProviderFlag1(liveRoom.getId())
                .setIntentUri(buildPreviewProgramUri(context.getResources(), liveRoom.getId()));
    }

    private static Uri buildPreviewProgramUri(Resources resources, long id) {
        return new Uri.Builder()
                .scheme("https")
                .authority(resources.getString(R.string.host_name))
                .appendPath(resources.getString(R.string.tv_uir_path_program))
                .appendPath(String.valueOf(id)).build();
    }

    public static long getChannelId(@NonNull Context context) {
        long channelId = 0;
        String channelName = context.getString(R.string.tv_channel_name);
        Cursor cursor = context.getContentResolver().query(
                TvContractCompat.Channels.CONTENT_URI,
                Channel.PROJECTION, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Channel channel = Channel.fromCursor(cursor);
                if (channelName.equals(channel.getInternalProviderId())) {
                    channelId = channel.getId();
                    if (AppVersionUtil.getVersionCode(context) > channel.getInternalProviderFlag1()) {
                        updateChannel(context, channelId);
                    }
                    break;
                } else {
                    context.getContentResolver().delete(TvContractCompat.buildChannelUri(channel.getId()), null, null);
                }
            } while (cursor.moveToNext());
        } else {
            channelId = createChannelAndPublish(context);
        }
        return channelId;
    }

    private static long createChannelAndPublish(@NonNull Context context) {
        Uri channelUri = context.getContentResolver().insert(
                TvContractCompat.Channels.CONTENT_URI, buildChannel(context).build().toContentValues());
        long channelId = ContentUris.parseId(channelUri);
        updateLogo(context, channelId);
        TvContractCompat.requestChannelBrowsable(context, channelId);
        return channelId;
    }

    private static void updateChannel(@NonNull Context context, long channelId) {
        context.getContentResolver().update(TvContractCompat.buildChannelUri(channelId),
                buildChannel(context).build().toContentValues(), null, null);
        updateLogo(context, channelId);
    }

    private static void updateLogo(@NonNull Context context, long channelId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_channel);
        ChannelLogoUtils.storeChannelLogo(context, channelId, bitmap);
    }

    private static Channel.Builder buildChannel(@NonNull Context context) {
        String channelName = context.getString(R.string.tv_channel_name);
        Channel.Builder builder = new Channel.Builder();
        builder.setType(TvContractCompat.Channels.TYPE_PREVIEW)
                .setDisplayName(channelName)
                .setInternalProviderId(channelName)
                .setInternalProviderFlag1(AppVersionUtil.getVersionCode(context))
                .setAppLinkText(context.getString(R.string.app_name))
                .setAppLinkColor(Color.WHITE);
        return builder;
    }


//    public static Uri getResourceUri(@NonNull Resources resources, int id){
//        return new Uri.Builder()
//                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//                .authority(resources.getResourcePackageName(id))
//                .appendPath(resources.getResourceTypeName(id))
//                .appendPath(resources.getResourceEntryName(id))
//                .build();
//    }

    public static void clear(Context context) {
        long channelId = getChannelId(context);
        context.getContentResolver().delete(TvContractCompat.buildPreviewProgramsUriForChannel(channelId), null, null);
        context.getContentResolver().delete(TvContractCompat.buildChannelUri(channelId), null, null);
    }
}
