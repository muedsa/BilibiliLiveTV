package com.muedsa.bilibililivetv.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.DetailsSupportFragment;
import androidx.leanback.app.DetailsSupportFragmentBackgroundController;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.DetailsOverviewRow;
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.live.Durl;
import com.muedsa.bilibililivetv.App;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.LiveRoomDetailsActivity;
import com.muedsa.bilibililivetv.activity.LiveStreamPlaybackActivity;
import com.muedsa.bilibililivetv.activity.MainActivity;
import com.muedsa.bilibililivetv.activity.UpLastVideosActivity;
import com.muedsa.bilibililivetv.channel.BilibiliLiveChannel;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.model.LiveRoomViewModel;
import com.muedsa.bilibililivetv.presenter.DetailsDescriptionPresenter;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.DpUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LiveRoomDetailsFragment extends DetailsSupportFragment {
    private static final String TAG = LiveRoomDetailsFragment.class.getSimpleName();

    private static final int ACTION_NOTHING = -1;
    private static final int ACTION_WATCH_TRAILER = 1;
    private static final int ACTION_LAST_VIDEOS = 2;

    private static final int DETAIL_THUMB_WIDTH = 216;
    private static final int DETAIL_THUMB_HEIGHT = 136;

    private LiveRoom mSelectedLiveRoom;

    private Action playAction;
    private Action liveStatusAction;
    private Action onlineNumAction;
    private Action lastVideosAction;

    private DetailsOverviewRow detailsOverviewRow;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private DetailsSupportFragmentBackgroundController mDetailsBackground;

    private ListCompositeDisposable listCompositeDisposable;

    private LiveRoomViewModel liveRoomViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);

        mDetailsBackground = new DetailsSupportFragmentBackgroundController(this);
        FragmentActivity activity = requireActivity();

        mSelectedLiveRoom = (LiveRoom) activity.getIntent()
                .getSerializableExtra(LiveRoomDetailsActivity.LIVE_ROOM);

        if (mSelectedLiveRoom != null && mSelectedLiveRoom.getId() > 0) {
            listCompositeDisposable = new ListCompositeDisposable();
            liveRoomViewModel = new ViewModelProvider(LiveRoomDetailsFragment.this,
                    new LiveRoomViewModel.Factory(((App) activity.getApplication()).getDatabase().getLiveRoomDaoWrapper()))
                    .get(LiveRoomViewModel.class);

            mPresenterSelector = new ClassPresenterSelector();
            mAdapter = new ArrayObjectAdapter(mPresenterSelector);
            setupDetailsOverviewRow();
            setupDetailsOverviewRowPresenter();
            setAdapter(mAdapter);
            initializeBackground();
            setOnItemViewClickedListener(new ItemViewClickedListener());

            mSelectedLiveRoom.setLiveStatus(0);
            mSelectedLiveRoom.setOnlineNum(0);
            mSelectedLiveRoom.setPlayUrlArr(new String[0]);
            initializePlayUrl();
            initializeLiveRoomInfo();
        } else {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initializeLiveRoomInfo() {
        RxRequestFactory.bilibiliDanmuRoomInfo(mSelectedLiveRoom.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(largeRoomInfo -> {
                            LiveRoomConvert.updateRoomInfo(mSelectedLiveRoom, largeRoomInfo);
                            liveRoomViewModel
                                    .sync(mSelectedLiveRoom)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();
                            BilibiliLiveChannel.sync(requireActivity(), mSelectedLiveRoom);
                            updateCardImage();
                            updateBackground();
                            liveStatusAction.setLabel2(mSelectedLiveRoom.getLiveStatusDesc(getResources()));
                            onlineNumAction.setLabel2(String.valueOf(mSelectedLiveRoom.getOnlineNum()));
                        },
                        throwable -> {
                            FragmentActivity activity = requireActivity();
                            String errorMsg = String.format(activity.getString(R.string.live_room_info_failure),
                                    throwable.getMessage());
                            ToastUtil.showLongToast(activity, errorMsg);
                            Log.e(TAG, errorMsg, throwable);
                        },
                        listCompositeDisposable);

        RxRequestFactory.bilibiliDanmuToken(mSelectedLiveRoom.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> mSelectedLiveRoom.setDanmuWsToken(token),
                        throwable -> {
                            FragmentActivity activity = requireActivity();
                            String errorMsg = String.format(activity.getString(R.string.live_danmu_ws_token_failure),
                                    throwable.getMessage());
                            ToastUtil.showLongToast(activity, errorMsg);
                            Log.e(TAG, errorMsg, throwable);
                        },
                        listCompositeDisposable);
    }

    private void initializePlayUrl() {
        playAction.setLabel2(getResources().getString(R.string.watch_trailer_loading));
        RxRequestFactory.bilibiliPlayUrlMessage(mSelectedLiveRoom.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playUrlData -> {
                            mSelectedLiveRoom.setPlayUrlArr(playUrlData.getDurl().stream().map(Durl::getUrl).toArray(String[]::new));
                            playAction.setLabel2(getResources().getString(R.string.watch_trailer_play));
                        },
                        throwable -> {
                            FragmentActivity activity = requireActivity();
                            String errorMsg = String.format(activity.getString(R.string.live_play_failure),
                                    throwable.getMessage());
                            ToastUtil.showLongToast(activity, errorMsg);
                            Log.e(TAG, errorMsg, throwable);
                        },
                        listCompositeDisposable);
    }

    private void initializeBackground() {
        mDetailsBackground.enableParallax();
    }

    private void updateBackground() {
        if (Strings.isNullOrEmpty(mSelectedLiveRoom.getSystemCoverImageUrl())) return;
        Log.d(TAG, "updateBackground Glide, url:" + mSelectedLiveRoom.getSystemCoverImageUrl());
        GlideApp.with(requireActivity())
                .asBitmap()
                .centerCrop()
                .error(R.drawable.default_background)
                .load(mSelectedLiveRoom.getSystemCoverImageUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap,
                                                @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "details overview background image url ready: " + bitmap);
                        mDetailsBackground.setCoverBitmap(bitmap);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void setupDetailsOverviewRow() {
        Log.d(TAG, "setupDetailsOverviewRow, roomId:" + mSelectedLiveRoom.getId());
        detailsOverviewRow = new DetailsOverviewRow(mSelectedLiveRoom);
        detailsOverviewRow.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.no_cover));

        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();
        playAction = new Action(ACTION_WATCH_TRAILER, getResources().getString(R.string.watch_trailer_title), getResources().getString(R.string.watch_trailer_loading));
        actionAdapter.add(playAction);
        liveStatusAction = new Action(ACTION_NOTHING, getResources().getString(R.string.room_live_status), mSelectedLiveRoom.getLiveStatusDesc(getResources()));
        actionAdapter.add(liveStatusAction);
        onlineNumAction = new Action(ACTION_NOTHING, getResources().getString(R.string.room_online_num), String.valueOf(mSelectedLiveRoom.getOnlineNum()));
        actionAdapter.add(onlineNumAction);
        detailsOverviewRow.setActionsAdapter(actionAdapter);
        lastVideosAction = new Action(ACTION_LAST_VIDEOS, getResources().getString(R.string.action_up_last_videos));
        actionAdapter.add(lastVideosAction);
        detailsOverviewRow.setActionsAdapter(actionAdapter);

        mAdapter.add(detailsOverviewRow);
    }

    private void updateCardImage() {
        FragmentActivity activity = getActivity();
        if (activity == null || Strings.isNullOrEmpty(mSelectedLiveRoom.getCoverImageUrl())) return;
        int width = DpUtil.convertDpToPixel(activity.getApplicationContext(), DETAIL_THUMB_WIDTH);
        int height = DpUtil.convertDpToPixel(activity.getApplicationContext(), DETAIL_THUMB_HEIGHT);
        Log.d(TAG, "updateCardImage Glide, url: " + mSelectedLiveRoom.getCoverImageUrl());
        GlideApp.with(activity)
                .load(mSelectedLiveRoom.getCoverImageUrl())
                .centerCrop()
                .error(R.drawable.no_cover)
                .into(new CustomTarget<Drawable>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable drawable,
                                                @Nullable Transition<? super Drawable> transition) {
                        Log.d(TAG, "details overview card image url ready: " + drawable);
                        detailsOverviewRow.setImageDrawable(drawable);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void setupDetailsOverviewRowPresenter() {
        // Set detail background.
        FullWidthDetailsOverviewRowPresenter detailsPresenter =
                new FullWidthDetailsOverviewRowPresenter(new DetailsDescriptionPresenter());
        detailsPresenter.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.selected_background));

        // Hook up transition element.
        FullWidthDetailsOverviewSharedElementHelper sharedElementHelper =
                new FullWidthDetailsOverviewSharedElementHelper();
        sharedElementHelper.setSharedElementEnterTransition(
                getActivity(), LiveRoomDetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(sharedElementHelper);
        detailsPresenter.setParticipatingEntranceTransition(true);

        detailsPresenter.setOnActionClickedListener(action -> {
            FragmentActivity activity = requireActivity();
            if (action.getId() == ACTION_WATCH_TRAILER) {
                if (mSelectedLiveRoom.getPlayUrlArr() == null || mSelectedLiveRoom.getPlayUrlArr().length == 0) {
                    ToastUtil.showLongToast(activity, activity.getString(R.string.live_play_failure));
                } else {
                    Intent intent = new Intent(activity, LiveStreamPlaybackActivity.class);
                    intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, mSelectedLiveRoom);
                    startActivity(intent);
                }
            } else if (action.getId() == ACTION_LAST_VIDEOS) {
                if (mSelectedLiveRoom.getUid() == null) {
                    ToastUtil.showLongToast(activity, activity.getString(R.string.toast_msg_up_last_videos_failure));
                } else {
                    Intent intent = new Intent(activity, UpLastVideosActivity.class);
                    intent.putExtra(UpLastVideosActivity.MID, mSelectedLiveRoom.getUid());
                    intent.putExtra(UpLastVideosActivity.UNAME, mSelectedLiveRoom.getUname());
                    startActivity(intent);
                }
            }
        });
        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {

            if (item instanceof LiveRoom && getActivity() != null) {
                Log.d(TAG, "onItemClicked, roomId:" + ((LiveRoom) item).getId());
                Intent intent = new Intent(getActivity(), LiveRoomDetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.liveRoom), mSelectedLiveRoom);

                Bundle bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        getActivity(),
                                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                        LiveRoomDetailsActivity.SHARED_ELEMENT_NAME)
                                .toBundle();
                getActivity().startActivity(intent, bundle);
            }
        }
    }
}