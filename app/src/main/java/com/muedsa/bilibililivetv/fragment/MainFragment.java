package com.muedsa.bilibililivetv.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.DanmakuTestActivity;
import com.muedsa.bilibililivetv.activity.LiveRoomDetailsActivity;
import com.muedsa.bilibililivetv.activity.LoginActivity;
import com.muedsa.bilibililivetv.activity.SearchActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.channel.BilibiliLiveChannel;
import com.muedsa.bilibililivetv.model.LiveRoomViewModel;
import com.muedsa.bilibililivetv.presenter.GithubReleasePresenter;
import com.muedsa.bilibililivetv.presenter.LiveRoomPresenter;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.github.model.GithubReleaseTagInfo;

import java.util.ArrayList;
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
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int MAX_NUM_COLS = 8;
    private static final int HEAD_TITLE_HISTORY = 1;
    private static final int HEAD_TITLE_BILIBILI_HISTORY = 2;
    private static final int HEAD_TITLE_OTHER = 3;
    private static final int HEAD_TITLE_LATEST_VERSION = 4;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Drawable mDefaultBackground;
    private int defaultWidth;
    private int defaultHeight;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private LiveRoomViewModel liveRoomViewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private LiveRoomPresenter.CardLongClickListener liveRoomCardLongClickListener;


    private List<ListRow> historyListRows;
    private ListRow bilibiliVideoHistoryListRow;
    private ListRow otherListRow;
    private ListRow versionListRow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreated");
        super.onCreate(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

        setupEventListeners();

        initRows();
    }


    private void initRows(){
        liveRoomViewModel = new ViewModelProvider(MainFragment.this,
                new LiveRoomViewModel.Factory(((App) requireActivity().getApplication()).getDatabase().getLiveRoomDaoWrapper()))
                .get(LiveRoomViewModel.class);
        disposable.add(liveRoomViewModel.getLiveRooms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(liveRooms -> {
                    Log.d(TAG, "load liveRooms size: " + liveRooms.size());
                    loadHistoryRows(liveRooms);
                    loadRows();
                }));
        runBilibiliHistoryRequest();
        runLatestVersionTask();
        loadRows();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    private void loadRows() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        //历史记录
        if (null != historyListRows) {
            Log.d(TAG, "loadRows: historyListRows size: " + historyListRows.size());
            rowsAdapter.addAll(0, historyListRows);
        }

        //Bilibili历史记录
        if (bilibiliVideoHistoryListRow != null) {
            rowsAdapter.add(bilibiliVideoHistoryListRow);
        }

        //其他
        if (otherListRow == null) {
            Resources resources = getResources();
            HeaderItem gridHeader = new HeaderItem(HEAD_TITLE_OTHER,
                    resources.getString(R.string.head_title_other));
            GridItemPresenter mGridPresenter = new GridItemPresenter();
            ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
            gridRowAdapter.add(resources.getString(R.string.clear_history));
            gridRowAdapter.add(resources.getString(R.string.clear_channel));
            gridRowAdapter.add(resources.getString(R.string.bilibili_scan_qr_code_login));
            if(BuildConfig.DEBUG){
                gridRowAdapter.add(resources.getString(R.string.danmaku_test));
                gridRowAdapter.add(resources.getString(R.string.video_test));
            }
            otherListRow = new ListRow(gridHeader, gridRowAdapter);
        }
        rowsAdapter.add(otherListRow);

        //版本更新
        if(versionListRow != null){
            rowsAdapter.add(versionListRow);
        }

        setAdapter(rowsAdapter);
    }

    private void loadHistoryRows(List<LiveRoom> list) {
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
        LiveRoomPresenter liveRoomPresenter = new LiveRoomPresenter(liveRoomCardLongClickListener);

        HeaderItem historyRecordHeader = new HeaderItem(HEAD_TITLE_HISTORY,
                getResources().getString(R.string.head_title_history));
        int row = list.size() / MAX_NUM_COLS;
        int mod = list.size() % MAX_NUM_COLS;
        if(mod > 0){
            row++;
        }
        historyListRows = new ArrayList<>(row);
        for(int rowIndex = 0; rowIndex < row; rowIndex++){
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(liveRoomPresenter);
            int max_num_cols = MAX_NUM_COLS;
            if(mod > 0 && rowIndex == row - 1){
                max_num_cols = mod;
            }
            for (int colIndex = 0; colIndex < max_num_cols; colIndex++){
                int listIndex = rowIndex * MAX_NUM_COLS + colIndex;
                listRowAdapter.add(list.get(listIndex));
            }
            if (rowIndex == 0) {
                historyListRows.add(new ListRow(historyRecordHeader, listRowAdapter));
            } else {
                historyListRows.add(new ListRow(listRowAdapter));
            }
        }
    }

    private void loadBilibiliHistoryRows(List<HistoryRecord> list) {
        VideoCardPresenter videoCardPresenter = new VideoCardPresenter();
        HeaderItem headerItem = new HeaderItem(HEAD_TITLE_BILIBILI_HISTORY,
                getResources().getString(R.string.head_title_video_history));
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(videoCardPresenter);
        listRowAdapter.addAll(0, list);
        bilibiliVideoHistoryListRow = new ListRow(headerItem, listRowAdapter);
    }

    private void runLatestVersionTask() {
        RxRequestFactory.githubLatestRelease()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubReleaseTagInfo -> {
                    HeaderItem headerItem = new HeaderItem(HEAD_TITLE_LATEST_VERSION,
                            getResources().getString(R.string.head_title_latest_version));
                    GithubReleasePresenter mGithubReleasePresenter = new GithubReleasePresenter();
                    ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(mGithubReleasePresenter);
                    rowAdapter.add(githubReleaseTagInfo);
                    versionListRow = new ListRow(headerItem, rowAdapter);
                    loadRows();
                }, throwable -> {
                    ToastUtil.showLongToast(getActivity(), throwable.getMessage());
                    Log.e(TAG, "githubLatestRelease error", throwable);
                }, disposable);
    }

    private static final String VIDEO_BUSINESS = "archive";

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
                            loadBilibiliHistoryRows(historyRecordList);
                            loadRows();
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

            if (item instanceof LiveRoom) {
                FragmentActivity activity = requireActivity();
                LiveRoom liveRoom = (LiveRoom) item;
                Log.d(TAG, "roomId: " + liveRoom.getId());
                Intent intent = new Intent(getActivity(), LiveRoomDetailsActivity.class);
                intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, liveRoom);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                LiveRoomDetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                activity.startActivity(intent, bundle);
            } else if (item instanceof HistoryRecord) {
                HistoryRecord historyRecord = (HistoryRecord) item;
                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, historyRecord.getHistory().getBvid());
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
                requireActivity().startActivity(intent);
            } else if (item instanceof GithubReleaseTagInfo) {
                jumpToUrl(getString(R.string.latest_version_download_url));
            } else if (item instanceof String) {
                String desc = (String) item;
                if (desc.contains(getString(R.string.clear_history))) {
                    new AlertDialog.Builder(getContext())
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
                    new AlertDialog.Builder(getContext())
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
                    Intent intent = new Intent(getActivity(), DanmakuTestActivity.class);
                    startActivity(intent);
                } else if(desc.contains(getString(R.string.bilibili_scan_qr_code_login))) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else if(desc.contains(getString(R.string.video_test))) {
//                    Intent intent = new Intent(getActivity(), VideoTestActivity.class);
//                    startActivity(intent);
                    Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                    intent.putExtra(VideoDetailsActivity.VIDEO_BV, "BV1gN4y1K7dx");
                    requireActivity().startActivity(intent);
                } else {
                    ToastUtil.showLongToast(getActivity(), desc);
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
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
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