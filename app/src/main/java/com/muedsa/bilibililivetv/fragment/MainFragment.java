package com.muedsa.bilibililivetv.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
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

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.BilibiliApiContainer;
import com.muedsa.bilibililiveapiclient.model.FlowItems;
import com.muedsa.bilibililiveapiclient.model.dynamic.DynamicItem;
import com.muedsa.bilibililiveapiclient.model.dynamic.svr.VideoDynamicCard;
import com.muedsa.bilibililiveapiclient.model.history.HistoryRecord;
import com.muedsa.bilibililiveapiclient.model.live.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililivetv.App;
import com.muedsa.bilibililivetv.EnvConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.DanmakuTestActivity;
import com.muedsa.bilibililivetv.activity.LiveRoomDetailsActivity;
import com.muedsa.bilibililivetv.activity.LoginActivity;
import com.muedsa.bilibililivetv.activity.SearchActivity;
import com.muedsa.bilibililivetv.activity.SettingsActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.channel.BilibiliLiveChannel;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.model.LiveRoomViewModel;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.model.bilibili.DynamicFeedViewModel;
import com.muedsa.bilibililivetv.model.factory.BilibiliRequestViewModelFactory;
import com.muedsa.bilibililivetv.presenter.GithubReleasePresenter;
import com.muedsa.bilibililivetv.presenter.LiveRoomCardPresenter;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.DpUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.bilibililivetv.widget.BackgroundManagerDelegate;
import com.muedsa.bilibililivetv.widget.OffsetPageFlowObjectAdapter;
import com.muedsa.github.model.GithubReleaseTagInfo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    private static final int HEAD_TITLE_POPULAR = 1;
    private static final int HEAD_TITLE_FOLLOWED_LIVING_ROOMS = 2;
    private static final int HEAD_TITLE_HISTORY = 3;

    private static final int HEAD_TITLE_VIDEO_DYNAMIC = 4;
    private static final int HEAD_TITLE_BILIBILI_HISTORY = 5;
    private static final int HEAD_TITLE_OTHER = 6;
    private static final int HEAD_TITLE_LATEST_VERSION = 7;

    private static final String VIDEO_BUSINESS = "archive";

    private BackgroundManagerDelegate backgroundManagerDelegate;

    private LiveRoomViewModel liveRoomViewModel;
    private DynamicFeedViewModel dynamicFeedViewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private LiveRoomCardPresenter.CardLongClickListener liveRoomCardLongClickListener;

    private ArrayObjectAdapter videoPopularRowAdapter;
    private ArrayObjectAdapter followedLivingRoomsRowAdapter;
    private ArrayObjectAdapter liveHistoryRowAdapter;
    private OffsetPageFlowObjectAdapter<String> bilibiliVideoDynamicRowAdapter;
    private ArrayObjectAdapter bilibiliVideoHistoryRowAdapter;
    private ArrayObjectAdapter versionRowAdapter;

    private DiffCallback<VideoData> bilibiliVideoPopularDiffCallback;
    private DiffCallback<LiveRoomInfo> bilibiliFollowedLivingRoomsDiffCallback;
    private DiffCallback<LiveRoom> liveRoomDiffCallback;
    private DiffCallback<HistoryRecord> bilibiliHistoryDiffCallback;
    private DiffCallback<GithubReleaseTagInfo> versionDiffCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreated");
        super.onCreate(savedInstanceState);
        backgroundManagerDelegate = new BackgroundManagerDelegate(requireActivity());
        setupUIElements();
        setupEventListeners();
        setupRows();
        registerRows();
        reloadRows();
    }

    private void setupRows() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        // 本地直播间访问历史
        FragmentActivity activity = requireActivity();
        if (liveRoomCardLongClickListener == null) {
            liveRoomCardLongClickListener = liveRoom -> new AlertDialog.Builder(activity)
                    .setTitle(activity.getResources().getString(R.string.remove_history_alert))
                    .setPositiveButton(activity.getResources().getString(R.string.alert_yes), (dialog, which) ->
                            liveRoomViewModel.delete(liveRoom)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe())
                    .setNegativeButton(activity.getResources().getString(R.string.alert_no), (dialog, which) -> {
                    })
                    .create()
                    .show();
        }
        LiveRoomCardPresenter liveRoomCardPresenter = new LiveRoomCardPresenter(liveRoomCardLongClickListener);
        VideoCardPresenter videoCardPresenter = new VideoCardPresenter();

        // 推荐视频
        HeaderItem popularHeader = new HeaderItem(HEAD_TITLE_POPULAR,
                getResources().getString(R.string.head_title_popular));
        videoPopularRowAdapter = new ArrayObjectAdapter(videoCardPresenter);
        ListRow popularListRow = new ListRow(popularHeader, videoPopularRowAdapter);
        rowsAdapter.add(popularListRow);

        // 正在直播 (正在直播的关注列表)
        HeaderItem followedLivingRoomsHeader = new HeaderItem(HEAD_TITLE_FOLLOWED_LIVING_ROOMS,
                getResources().getString(R.string.head_title_followed_living_rooms));
        followedLivingRoomsRowAdapter = new ArrayObjectAdapter(liveRoomCardPresenter);
        rowsAdapter.add(new ListRow(followedLivingRoomsHeader, followedLivingRoomsRowAdapter));

        // 直播历史记录
        HeaderItem historyRecordHeader = new HeaderItem(HEAD_TITLE_HISTORY,
                getResources().getString(R.string.head_title_history));
        liveHistoryRowAdapter = new ArrayObjectAdapter(liveRoomCardPresenter);
        ListRow liveHistoryListRow = new ListRow(historyRecordHeader, liveHistoryRowAdapter);
        rowsAdapter.add(liveHistoryListRow);

        // 动态-投稿视频
        HeaderItem videoDynamicHeader = new HeaderItem(HEAD_TITLE_VIDEO_DYNAMIC,
                getResources().getString(R.string.head_title_video_dynamic));
        bilibiliVideoDynamicRowAdapter = new OffsetPageFlowObjectAdapter<>(videoCardPresenter, "");
        rowsAdapter.add(new ListRow(videoDynamicHeader, bilibiliVideoDynamicRowAdapter));

        // bilibili播放历史
        HeaderItem bilibiliHistoryHeaderItem = new HeaderItem(HEAD_TITLE_BILIBILI_HISTORY,
                getResources().getString(R.string.head_title_video_history));
        bilibiliVideoHistoryRowAdapter = new ArrayObjectAdapter(videoCardPresenter);
        rowsAdapter.add(new ListRow(bilibiliHistoryHeaderItem,
                bilibiliVideoHistoryRowAdapter));

        // 其他
        HeaderItem otherHeader = new HeaderItem(HEAD_TITLE_OTHER,
                activity.getString(R.string.head_title_other));
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(activity.getString(R.string.bilibili_refresh));
        gridRowAdapter.add(activity.getString(R.string.bilibili_scan_qr_code_login));
        gridRowAdapter.add(activity.getString(R.string.setting));
        gridRowAdapter.add(activity.getString(R.string.clear_history));
        gridRowAdapter.add(activity.getString(R.string.clear_channel));
        if (EnvConfig.DEBUG) {
            gridRowAdapter.add(activity.getString(R.string.danmaku_test));
            gridRowAdapter.add(activity.getString(R.string.video_test));
        }
        rowsAdapter.add(new ListRow(otherHeader, gridRowAdapter));

        // 版本
        HeaderItem versionHeaderItem = new HeaderItem(HEAD_TITLE_LATEST_VERSION,
                getResources().getString(R.string.head_title_latest_version));
        GithubReleasePresenter mGithubReleasePresenter = new GithubReleasePresenter();
        versionRowAdapter = new ArrayObjectAdapter(mGithubReleasePresenter);
        rowsAdapter.add(new ListRow(versionHeaderItem, versionRowAdapter));

        setAdapter(rowsAdapter);
    }

    private void registerRows() {
        liveRoomViewModel = new ViewModelProvider(MainFragment.this,
                new LiveRoomViewModel.Factory(((App) requireActivity().getApplication()).getDatabase().getLiveRoomDaoWrapper()))
                .get(LiveRoomViewModel.class);

        liveRoomViewModel.getLiveRooms().observe(MainFragment.this,
                this::updateLiveHistoryRows);

        ViewModelProvider bilibiliViewModelProvider = new ViewModelProvider(MainFragment.this, BilibiliRequestViewModelFactory.getInstance());
        dynamicFeedViewModel = bilibiliViewModelProvider.get(DynamicFeedViewModel.class);
        dynamicFeedViewModel.setType("video");

        dynamicFeedViewModel.getResult().observe(MainFragment.this, m -> {
            if (RMessage.Status.LOADING.equals(m.getStatus())) {
                bilibiliVideoDynamicRowAdapter.setLoading(true);
            } else if (RMessage.Status.SUCCESS.equals(m.getStatus())) {
                if (m.getData() != null) {
                    appendVideoDynamicRows(m.getData());
                }
                bilibiliVideoDynamicRowAdapter.setLoading(false);
            } else if (RMessage.Status.ERROR.equals(m.getStatus())) {
                bilibiliVideoDynamicRowAdapter.setLoading(false);
                Log.e(TAG, "dynamicFeedViewModel error", m.getError());
                ToastUtil.error(requireActivity(), "dynamic feed error", m.getError());
            }
        });
    }

    private void reloadRows() {
        liveRoomViewModel.fetchLiveRooms();
        runBilibiliVideoPopularRequest();
        runBilibiliFollowedLivingRoomsRequest();
        bilibiliVideoDynamicRowAdapter.clear();
        dynamicFeedViewModel.fetchDynamicFeedAll(bilibiliVideoDynamicRowAdapter.getOffset(),
                bilibiliVideoDynamicRowAdapter.currentPageNum() + 1);
        runBilibiliHistoryRequest();
        runLatestVersionTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bilibiliVideoDynamicRowAdapter.size() == 0
                && bilibiliVideoHistoryRowAdapter.size() == 0) {
            reloadRows();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != backgroundManagerDelegate) {
            backgroundManagerDelegate.dispose();
        }
        disposable.dispose();
    }

    private void updateBilibiliVideoPopularRows(List<VideoData> list) {
        if (Objects.isNull(bilibiliVideoPopularDiffCallback)) {
            bilibiliVideoPopularDiffCallback = new DiffCallback<VideoData>() {
                @Override
                public boolean areItemsTheSame(@NonNull VideoData oldItem, @NonNull VideoData newItem) {
                    return oldItem.getBvid().equals(newItem.getBvid());
                }

                @Override
                public boolean areContentsTheSame(@NonNull VideoData oldItem, @NonNull VideoData newItem) {
                    return oldItem.getBvid().equals(newItem.getBvid());
                }
            };
        }
        videoPopularRowAdapter.setItems(list, bilibiliVideoPopularDiffCallback);
    }

    private void updateBilibiliFollowedLiveRoomsRows(List<LiveRoomInfo> list) {
        if (Objects.isNull(bilibiliFollowedLivingRoomsDiffCallback)) {
            bilibiliFollowedLivingRoomsDiffCallback = new DiffCallback<LiveRoomInfo>() {
                @Override
                public boolean areItemsTheSame(@NonNull LiveRoomInfo oldItem, @NonNull LiveRoomInfo newItem) {
                    return oldItem.getRoomId().equals(newItem.getRoomId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull LiveRoomInfo oldItem, @NonNull LiveRoomInfo newItem) {
                    return oldItem.getRoomId().equals(newItem.getRoomId());
                }
            };
        }
        followedLivingRoomsRowAdapter.setItems(list, bilibiliFollowedLivingRoomsDiffCallback);
    }

    private void updateLiveHistoryRows(List<LiveRoom> list) {
        if (Objects.isNull(liveRoomDiffCallback)) {
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

    private void appendVideoDynamicRows(FlowItems<DynamicItem> flowItems) {
        List<DynamicItem> list = flowItems.getItems().stream()
                .filter(i -> BilibiliApiContainer.DYNAMIC_TYPE_AV.equals(i.getType()))
                .collect(Collectors.toList());
        bilibiliVideoDynamicRowAdapter.append(list, flowItems.getOffset(), !flowItems.isHasMore());
    }

    private void updateBilibiliHistoryRows(List<HistoryRecord> list) {
        if (Objects.isNull(bilibiliHistoryDiffCallback)) {
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
        if (Objects.isNull(versionDiffCallback)) {
            versionDiffCallback = new DiffCallback<GithubReleaseTagInfo>() {
                @Override
                public boolean areItemsTheSame(@NonNull GithubReleaseTagInfo oldItem, @NonNull GithubReleaseTagInfo newItem) {
                    return oldItem.getUrl().equals(newItem.getUrl());
                }

                @Override
                public boolean areContentsTheSame(@NonNull GithubReleaseTagInfo oldItem, @NonNull GithubReleaseTagInfo newItem) {
                    return oldItem.getUrl().equals(newItem.getUrl());
                }
            };
        }
        versionRowAdapter.setItems(Collections.singletonList(githubReleaseTagInfo), versionDiffCallback);
    }

    private void runBilibiliFollowedLivingRoomsRequest() {
        RxRequestFactory.bilibiliFollowedLivingRooms(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (Objects.nonNull(list)) {
                        if (!list.isEmpty()) {
                            updateBilibiliFollowedLiveRoomsRows(list);
                        }
                    }
                }, throwable -> {
                    Log.w(TAG, "bilibiliFollowedLivingRooms error", throwable);
                }, disposable);
    }

    private void runBilibiliVideoPopularRequest() {
        RxRequestFactory.bilibiliVideoPopular(1, 50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (Objects.nonNull(list)) {
                        if (!list.isEmpty()) {
                            updateBilibiliVideoPopularRows(list);
                        }
                    }
                }, throwable -> {
                    Log.e(TAG, "bilibiliVideoPopular error", throwable);
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

    private void runLatestVersionTask() {
        RxRequestFactory.githubLatestRelease()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateVersionRows, throwable -> {
                    ToastUtil.showLongToast(getActivity(), throwable.getMessage());
                    Log.e(TAG, "githubLatestRelease error", throwable);
                }, disposable);
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

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            FragmentActivity activity = requireActivity();
            if (item instanceof VideoData) {
                VideoData videoData = (VideoData) item;
                Intent intent = new Intent(activity, VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, videoData.getBvid());
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                VideoDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                startActivity(intent, bundle);
            } else if (item instanceof VideoDynamicCard) {
                VideoDynamicCard videoDynamicCard = (VideoDynamicCard) item;
                Intent intent = new Intent(activity, VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, videoDynamicCard.getBvid());
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                VideoDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                startActivity(intent, bundle);
            } else if (item instanceof DynamicItem) {
                DynamicItem dynamicItem = (DynamicItem) item;
                Intent intent = new Intent(activity, VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, dynamicItem.getModules()
                        .getModuleDynamic()
                        .getMajor()
                        .getArchive()
                        .getBvid());
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                VideoDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                startActivity(intent, bundle);
            } else if (item instanceof LiveRoomInfo) {
                LiveRoomInfo liveRoomInfo = (LiveRoomInfo) item;
                Log.d(TAG, "roomId: " + liveRoomInfo.getRoomId());
                Intent intent = new Intent(getActivity(), LiveRoomDetailsActivity.class);
                intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, LiveRoomConvert.buildWithRoomId(liveRoomInfo.getRoomId()));
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                LiveRoomDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                startActivity(intent, bundle);
            } else if (item instanceof LiveRoom) {
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
                if (desc.contains(getString(R.string.bilibili_refresh))) {
                    reloadRows();
                } else if (desc.contains(getString(R.string.bilibili_scan_qr_code_login))) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }
                if (desc.contains(getString(R.string.setting))) {
                    Intent intent = new Intent(activity, SettingsActivity.class);
                    startActivity(intent);
                } else if (desc.contains(getString(R.string.clear_history))) {
                    new AlertDialog.Builder(activity)
                            .setTitle(getString(R.string.clear_history_alert))
                            .setPositiveButton(getString(R.string.alert_yes), (dialog, which) -> liveRoomViewModel
                                    .clear()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe())
                            .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> {
                            })
                            .create()
                            .show();
                } else if (desc.contains(getString(R.string.clear_channel))) {
                    new AlertDialog.Builder(activity)
                            .setTitle(getString(R.string.clear_channel_alert))
                            .setPositiveButton(getString(R.string.alert_yes), (dialog, which) ->
                                    Completable.create(emitter -> {
                                        BilibiliLiveChannel.clear(getContext());
                                        emitter.onComplete();
                                    }).subscribeOn(Schedulers.io()).subscribe())
                            .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> {
                            })
                            .create()
                            .show();
                } else if (desc.contains(getString(R.string.danmaku_test))) {
                    Intent intent = new Intent(activity, DanmakuTestActivity.class);
                    startActivity(intent);
                } else if (desc.contains(getString(R.string.video_test))) {
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
            String newBackgroundUri = null;
            if (item instanceof VideoData) {
                newBackgroundUri = ((VideoData) item).getPic();
            } else if (item instanceof VideoDynamicCard) {
                newBackgroundUri = ((VideoDynamicCard) item).getFirstFrame();
            } else if (item instanceof LiveRoom) {
                newBackgroundUri = ((LiveRoom) item).getBackgroundImageUrl();
            } else if (item instanceof LiveRoomInfo) {
                newBackgroundUri = ((LiveRoomInfo) item).getSystemCover();
            } else if (item instanceof HistoryRecord) {
                newBackgroundUri = ((HistoryRecord) item).getCover();
            } else if (item instanceof DynamicItem) {
                newBackgroundUri = ((DynamicItem) item).getModules().getModuleDynamic().getMajor().getArchive().getCover();
            }

            if (row.getHeaderItem().getId() == HEAD_TITLE_VIDEO_DYNAMIC) {
                int index = bilibiliVideoDynamicRowAdapter.indexOf(item);
                int size = bilibiliVideoDynamicRowAdapter.size();
                if (bilibiliVideoDynamicRowAdapter.hasNextPage()
                        && !bilibiliVideoDynamicRowAdapter.isLoading()
                        && index + 4 >= size) {
                    dynamicFeedViewModel.fetchDynamicFeedAll(bilibiliVideoDynamicRowAdapter.getOffset(),
                            bilibiliVideoDynamicRowAdapter.currentPageNum() + 1);
                }
            }

            if (!Strings.isNullOrEmpty(newBackgroundUri)) {
                backgroundManagerDelegate.startBackgroundUpdate(newBackgroundUri);
            }
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

    private void jumpToUrl(String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "jumpToUrl: " + url, e);
            ToastUtil.showLongToast(getActivity(), String.format(getString(R.string.toast_msg_jump_url_error), url));
        }
    }
}