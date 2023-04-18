package com.muedsa.bilibililivetv.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.DiffCallback;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muedsa.bilibililiveapiclient.model.history.HistoryRecord;
import com.muedsa.bilibililivetv.App;
import com.muedsa.bilibililivetv.EnvConfig;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.DanmakuTestActivity;
import com.muedsa.bilibililivetv.activity.LiveRoomDetailsActivity;
import com.muedsa.bilibililivetv.activity.LoginActivity;
import com.muedsa.bilibililivetv.activity.SearchActivity;
import com.muedsa.bilibililivetv.activity.SettingsActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.channel.BilibiliLiveChannel;
import com.muedsa.bilibililivetv.model.LiveRoomViewModel;
import com.muedsa.bilibililivetv.presenter.GithubReleasePresenter;
import com.muedsa.bilibililivetv.presenter.LiveRoomCardPresenter;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.DpUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.github.model.GithubReleaseTagInfo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainFragment extends BrowseSupportFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH_DP = 100;
    private static final int GRID_ITEM_HEIGHT_DP = 100;
    private static final int HEAD_TITLE_HISTORY = 1;
    private static final int HEAD_TITLE_BILIBILI_HISTORY = 2;
    private static final int HEAD_TITLE_OTHER = 3;
    private static final int HEAD_TITLE_LATEST_VERSION = 4;

    private static final String VIDEO_BUSINESS = "archive";

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Drawable mDefaultBackground;
    private int defaultWidth;
    private int defaultHeight;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private LiveRoomViewModel liveRoomViewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private LiveRoomCardPresenter.CardLongClickListener liveRoomCardLongClickListener;

    private ArrayObjectAdapter liveHistoryRowAdapter;
    private ArrayObjectAdapter bilibiliVideoHistoryRowAdapter;
    private ArrayObjectAdapter versionRowAdapter;

    private DiffCallback<LiveRoom> liveRoomDiffCallback;
    private DiffCallback<HistoryRecord> bilibiliHistoryDiffCallback;
    private DiffCallback<GithubReleaseTagInfo> versionDiffCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreated");
        super.onCreate(savedInstanceState);
        prepareBackgroundManager();
        setupUIElements();
        setupEventListeners();
        setupRows();
    }

    private void setupRows(){
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        // 本地直播间访问历史
        FragmentActivity activity = requireActivity();
        if(liveRoomCardLongClickListener == null) {
            liveRoomCardLongClickListener = liveRoom -> new AlertDialog.Builder(activity)
                    .setTitle(activity.getResources().getString(R.string.remove_history_alert))
                    .setPositiveButton(activity.getResources().getString(R.string.alert_yes), (dialog, which) ->
                            liveRoomViewModel.delete(liveRoom)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe())
                    .setNegativeButton(activity.getResources().getString(R.string.alert_no), (dialog, which) -> {})
                    .create()
                    .show();
        }
        LiveRoomCardPresenter liveRoomCardPresenter = new LiveRoomCardPresenter(liveRoomCardLongClickListener);
        liveHistoryRowAdapter = new ArrayObjectAdapter(liveRoomCardPresenter);
        HeaderItem historyRecordHeader = new HeaderItem(HEAD_TITLE_HISTORY,
                getResources().getString(R.string.head_title_history));
        ListRow liveHistoryListRow = new ListRow(historyRecordHeader, liveHistoryRowAdapter);
        rowsAdapter.add(0, liveHistoryListRow);

        // bilibili播放历史
        VideoCardPresenter videoCardPresenter = new VideoCardPresenter();
        HeaderItem bilibiliHistoryHeaderItem = new HeaderItem(HEAD_TITLE_BILIBILI_HISTORY,
                getResources().getString(R.string.head_title_video_history));
        bilibiliVideoHistoryRowAdapter = new ArrayObjectAdapter(videoCardPresenter);
        rowsAdapter.add(1, new ListRow(bilibiliHistoryHeaderItem, bilibiliVideoHistoryRowAdapter));

        // 其他
        HeaderItem otherHeader = new HeaderItem(HEAD_TITLE_OTHER,
                activity.getString(R.string.head_title_other));
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(activity.getString(R.string.bilibili_history_refresh));
        gridRowAdapter.add(activity.getString(R.string.bilibili_scan_qr_code_login));
        gridRowAdapter.add(activity.getString(R.string.setting));
        gridRowAdapter.add(activity.getString(R.string.clear_history));
        gridRowAdapter.add(activity.getString(R.string.clear_channel));
        if(EnvConfig.DEBUG){
            gridRowAdapter.add(activity.getString(R.string.danmaku_test));
            gridRowAdapter.add(activity.getString(R.string.video_test));
        }
        rowsAdapter.add(2, new ListRow(otherHeader, gridRowAdapter));

        // 版本
        HeaderItem versionHeaderItem = new HeaderItem(HEAD_TITLE_LATEST_VERSION,
                getResources().getString(R.string.head_title_latest_version));
        GithubReleasePresenter mGithubReleasePresenter = new GithubReleasePresenter();
        versionRowAdapter = new ArrayObjectAdapter(mGithubReleasePresenter);
        rowsAdapter.add(3, new ListRow(versionHeaderItem, versionRowAdapter));

        setAdapter(rowsAdapter);
    }

    private void loadRows(){
        liveRoomViewModel = new ViewModelProvider(MainFragment.this,
                new LiveRoomViewModel.Factory(((App) requireActivity().getApplication()).getDatabase().getLiveRoomDaoWrapper()))
                .get(LiveRoomViewModel.class);
        disposable.add(liveRoomViewModel.getLiveRooms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(liveRooms -> {
                    Log.d(TAG, "load liveRooms size: " + liveRooms.size());
                    updateLiveHistoryRows(liveRooms);
                }));
        runBilibiliHistoryRequest();
        runLatestVersionTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRows();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer);
            mBackgroundTimer.cancel();
        }
        disposable.dispose();
    }

    private void updateLiveHistoryRows(List<LiveRoom> list) {
        if(Objects.isNull(liveRoomDiffCallback)){
            liveRoomDiffCallback = new DiffCallback<LiveRoom>() {
                @Override
                public boolean areItemsTheSame(@NonNull LiveRoom oldItem, @NonNull LiveRoom newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull LiveRoom oldItem, @NonNull LiveRoom newItem) {
                    return oldItem.getId() == newItem.getId()
                            && oldItem.getTitle().equals(newItem.getTitle())
                            && oldItem.getBackgroundImageUrl().equals(newItem.getBackgroundImageUrl());
                }
            };
        }
        liveHistoryRowAdapter.setItems(list, liveRoomDiffCallback);
    }

    private void updateBilibiliHistoryRows(List<HistoryRecord> list) {
        if(Objects.isNull(bilibiliHistoryDiffCallback)){
            bilibiliHistoryDiffCallback = new DiffCallback<HistoryRecord>() {
                @Override
                public boolean areItemsTheSame(@NonNull HistoryRecord oldItem, @NonNull HistoryRecord newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle())
                            && oldItem.getAuthorName().equals(newItem.getAuthorName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull HistoryRecord oldItem, @NonNull HistoryRecord newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle())
                            && oldItem.getAuthorName().equals(newItem.getAuthorName());
                }
            };
        }
        bilibiliVideoHistoryRowAdapter.setItems(list, bilibiliHistoryDiffCallback);
    }

    private void updateVersionRows(GithubReleaseTagInfo githubReleaseTagInfo) {
        if(Objects.isNull(versionDiffCallback)){
            versionDiffCallback = new DiffCallback<GithubReleaseTagInfo>() {
                @Override
                public boolean areItemsTheSame(@NonNull GithubReleaseTagInfo oldItem, @NonNull GithubReleaseTagInfo newItem) {
                    return oldItem.getUrl().equals(newItem.getUrl());
                }

                @Override
                public boolean areContentsTheSame(@NonNull GithubReleaseTagInfo oldItem, @NonNull GithubReleaseTagInfo newItem) {
                    return  oldItem.getUrl().equals(newItem.getUrl());
                }
            };
        }
        versionRowAdapter.setItems(Collections.singletonList(githubReleaseTagInfo), versionDiffCallback);
    }

    private void runLatestVersionTask() {
        RxRequestFactory.githubLatestRelease()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateVersionRows, throwable -> {
                    ToastUtil.showLongToast(getActivity(), throwable.getMessage());
                    Log.e(TAG, "githubLatestRelease error", throwable);
                }, disposable);
    }

    private void runBilibiliHistoryRequest() {
        RxRequestFactory.bilibiliHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(historyTable -> {
                    if (Objects.nonNull(historyTable.getList())) {
                        List<HistoryRecord> historyRecordList = historyTable.getList().stream()
                                .filter(h -> VIDEO_BUSINESS.equals(h.getHistory().getBusiness()))
                                .collect(Collectors.toList());
                        if (!historyRecordList.isEmpty()) {
                            updateBilibiliHistoryRows(historyRecordList);
                        }
                    }
                }, throwable -> {
                    Log.e(TAG, "bilibiliHistory error", throwable);
                }, disposable);
    }

    private void prepareBackgroundManager() {
        FragmentActivity activity = requireActivity();
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());

        mDefaultBackground = ContextCompat.getDrawable(requireContext(), R.drawable.default_background);
        WindowManager windowManager = activity.getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Rect bounds = windowMetrics.getBounds();
            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            defaultWidth = bounds.width() - insets.left - insets.right;
            defaultHeight = bounds.height()- insets.top - insets.bottom;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            defaultWidth = displayMetrics.widthPixels;
            defaultHeight = displayMetrics.heightPixels;
        }
    }

    private void setupUIElements() {
        Context context = requireContext();
        setBadgeDrawable(ContextCompat.getDrawable(context, R.drawable.bilibili_logo));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(ContextCompat.getColor(context, R.color.fastlane_background));
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(view -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private void updateBackground(String uri) {
        int width = defaultWidth;
        int height = defaultHeight;
        GlideApp.with(requireActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new CustomTarget<Drawable>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable drawable,
                                                @Nullable Transition<? super Drawable> transition) {
                        mBackgroundManager.setDrawable(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            FragmentActivity activity = requireActivity();
            if (item instanceof LiveRoom) {
                LiveRoom liveRoom = (LiveRoom) item;
                Log.d(TAG, "roomId: " + liveRoom.getId());
                Intent intent = new Intent(getActivity(), LiveRoomDetailsActivity.class);
                intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, liveRoom);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                LiveRoomDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                startActivity(intent, bundle);
            } else if (item instanceof HistoryRecord) {
                HistoryRecord historyRecord = (HistoryRecord) item;
                Intent intent = new Intent(activity, VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, historyRecord.getHistory().getBvid());
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                VideoDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                startActivity(intent, bundle);
            } else if (item instanceof GithubReleaseTagInfo) {
                jumpToUrl(getString(R.string.latest_version_download_url));
            } else if (item instanceof String) {
                String desc = (String) item;
                if(desc.contains(getString(R.string.bilibili_history_refresh))) {
                    runBilibiliHistoryRequest();
                }else if(desc.contains(getString(R.string.bilibili_scan_qr_code_login))) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }if(desc.contains(getString(R.string.setting))) {
                    Intent intent = new Intent(activity, SettingsActivity.class);
                    startActivity(intent);
                }else if(desc.contains(getString(R.string.clear_history))) {
                    new AlertDialog.Builder(activity)
                            .setTitle(getString(R.string.clear_history_alert))
                            .setPositiveButton(getString(R.string.alert_yes), (dialog, which) -> liveRoomViewModel
                                    .clear()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe())
                            .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> {})
                            .create()
                            .show();
                }else if(desc.contains(getString(R.string.clear_channel))){
                    new AlertDialog.Builder(activity)
                            .setTitle(getString(R.string.clear_channel_alert))
                            .setPositiveButton(getString(R.string.alert_yes), (dialog, which) ->
                                    Completable.create(emitter -> {
                                        BilibiliLiveChannel.clear(getContext());
                                        emitter.onComplete();
                                    }).subscribeOn(Schedulers.io()).subscribe())
                            .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> {})
                            .create()
                            .show();
                } else if(desc.contains(getString(R.string.danmaku_test))) {
                    Intent intent = new Intent(activity, DanmakuTestActivity.class);
                    startActivity(intent);
                } else if(desc.contains(getString(R.string.video_test))) {
//                    Intent intent = new Intent(getActivity(), VideoTestActivity.class);
//                    startActivity(intent);
                    Intent intent = new Intent(activity, VideoDetailsActivity.class);
                    intent.putExtra(VideoDetailsActivity.VIDEO_BV, "BV11e411N7dy");
                    startActivity(intent);
                } else {
                    ToastUtil.showLongToast(activity, desc);
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            if (item instanceof LiveRoom) {
                mBackgroundUri = ((LiveRoom) item).getBackgroundImageUrl();
                startBackgroundTimer();
            }else if(item instanceof HistoryRecord){
                mBackgroundUri = ((HistoryRecord) item).getCover();
                startBackgroundTimer();
            }
        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(() -> updateBackground(mBackgroundUri));
        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            int width = DpUtil.convertDpToPixel(parent.getContext(), GRID_ITEM_WIDTH_DP);
            int height = DpUtil.convertDpToPixel(parent.getContext(), GRID_ITEM_HEIGHT_DP);
            view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

    private void jumpToUrl(String url){
        try{
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        catch (Exception e){
            Log.e(TAG, "jumpToUrl: " + url, e);
            ToastUtil.showLongToast(getActivity(), String.format(getString(R.string.toast_msg_jump_url_error), url));
        }
    }
}