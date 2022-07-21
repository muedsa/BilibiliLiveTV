package com.muedsa.bilibililivetv.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muedsa.bilibililivetv.App;
import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.DanmakuTestActivity;
import com.muedsa.bilibililivetv.activity.DetailsActivity;
import com.muedsa.bilibililivetv.activity.SearchActivity;
import com.muedsa.bilibililivetv.channel.BilibiliLiveChannel;
import com.muedsa.bilibililivetv.model.LiveRoomViewModel;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.presenter.LiveRoomPresenter;
import com.muedsa.bilibililivetv.task.TaskRunner;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainFragment extends BrowseSupportFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int MAX_NUM_COLS = 8;
    private static final int HEAD_TITLE_HISTORY = 1;
    private static final int HEAD_TITLE_OTHER = 2;

    private final Handler mHandler = new Handler();
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private LiveRoomViewModel liveRoomViewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private TaskRunner taskRunner;
    private LiveRoomPresenter.CardLongClickListener liveRoomCardLongClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreated");
        super.onCreate(savedInstanceState);
        taskRunner = TaskRunner.getInstance();

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
                    Log.i(TAG, "load liveRooms size: " + liveRooms.size());
                    loadRows(liveRooms);
                }));
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

    private void loadRows(List<LiveRoom> list) {
        FragmentActivity activity = requireActivity();

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
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
            if(rowIndex == 0){
                rowsAdapter.add(new ListRow(historyRecordHeader, listRowAdapter));
            }else{
                rowsAdapter.add(new ListRow(listRowAdapter));
            }
        }

        HeaderItem gridHeader = new HeaderItem(HEAD_TITLE_OTHER,
                getResources().getString(R.string.head_title_other));
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.clear_history));
        gridRowAdapter.add(getResources().getString(R.string.clear_channel));

        if(BuildConfig.DEBUG){
            gridRowAdapter.add(getResources().getString(R.string.danmaku_test));
        }

        rowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(rowsAdapter);
    }

    private void prepareBackgroundManager() {
        FragmentActivity activity = requireActivity();
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());

        mDefaultBackground = ContextCompat.getDrawable(requireContext(), R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        setBadgeDrawable(requireActivity().getResources().getDrawable(R.drawable.bilibili_logo, null));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        Context context = requireContext();
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
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
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
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.LIVE_ROOM, liveRoom);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                activity.startActivity(intent, bundle);
            } else if (item instanceof String) {
                String desc = (String) item;
                if(desc.contains(getString(R.string.clear_history))){
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
                            .setPositiveButton(getString(R.string.alert_yes), (dialog, which) -> taskRunner.executeAsync(()
                                    -> BilibiliLiveChannel.clear(getContext())))
                            .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> {})
                            .create()
                            .show();

                } else if(desc.contains(getString(R.string.danmaku_test))) {
                    Intent intent = new Intent(getActivity(), DanmakuTestActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), desc, Toast.LENGTH_SHORT).show();
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

}