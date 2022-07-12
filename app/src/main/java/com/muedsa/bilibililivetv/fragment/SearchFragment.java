package com.muedsa.bilibililivetv.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.SearchSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.DetailsActivity;
import com.muedsa.bilibililivetv.model.LiveRoom;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.model.LiveUser;
import com.muedsa.bilibililivetv.model.LiveUserConvert;
import com.muedsa.bilibililivetv.presenter.LiveRoomPresenter;
import com.muedsa.bilibililivetv.presenter.LiveUserPresenter;
import com.muedsa.bilibililivetv.task.Message;
import com.muedsa.bilibililivetv.task.RequestBilibiliSearchTask;
import com.muedsa.bilibililivetv.task.TaskRunner;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SearchFragment extends SearchSupportFragment implements SearchSupportFragment.SearchResultProvider {
    private static final String TAG = SearchFragment.class.getSimpleName();

    private static final int HEAD_TITLE_SEARCH_RESULT_LIVE_USER = 1;
    private static final int HEAD_TITLE_SEARCH_RESULT_LIVE_ROOM = 2;

    private ArrayObjectAdapter mRowsAdapter;

    private final Handler mHandler = new Handler();
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private TaskRunner taskRunner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareBackgroundManager();
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setSearchResultProvider(this);
        taskRunner = TaskRunner.getInstance();
    }

    private void prepareBackgroundManager() {
        FragmentActivity activity = requireActivity();
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        if(mBackgroundManager.getDrawable() != null) {
            mDefaultBackground = mBackgroundManager.getDrawable();
            setBlurBackground(mDefaultBackground);
        }
    }

    private void setBlurBackground(Drawable drawable) {
        GlideApp.with(requireActivity())
                .load(drawable)
                .transform(new BlurTransformation(25, 3))
                .error(mDefaultBackground)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable drawable,
                                                @Nullable Transition<? super Drawable> transition) {
                        mBackgroundManager.setDrawable(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        GlideApp.with(requireActivity())
                .load(uri)
                .transform(new FitCenter(), new BlurTransformation(25, 3))
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
        mBackgroundTimer.schedule(new SearchFragment.UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> updateBackground(mBackgroundUri));
        }
    }


    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        taskRunner.executeAsync(new RequestBilibiliSearchTask(query), result -> {
            if(result.what == Message.MessageType.SUCCESS){
                SearchResult searchResult = (SearchResult) result.obj;
                mRowsAdapter.clear();
                loadLiveUserSearchData(searchResult.getLiveUser());
                loadLiveRoomSearchData(searchResult.getLiveRoom());
            }
        });
        return true;
    }

    private void loadLiveUserSearchData(List<com.muedsa.bilibililiveapiclient.model.search.LiveUser> liveUserList) {
        if(liveUserList == null || liveUserList.isEmpty()){
            return;
        }
        LiveUserPresenter liveUserPresenter = new LiveUserPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(liveUserPresenter);
        for (com.muedsa.bilibililiveapiclient.model.search.LiveUser searchLiveUser : liveUserList) {
            LiveUser liveUser = LiveUserConvert.convert(searchLiveUser);
            listRowAdapter.add(liveUser);
        }
        HeaderItem liveUserHeader = new HeaderItem(HEAD_TITLE_SEARCH_RESULT_LIVE_USER,
                getResources().getString(R.string.head_title_search_result_live_user));
        mRowsAdapter.add(new ListRow(liveUserHeader, listRowAdapter));
    }

    private void loadLiveRoomSearchData(List<com.muedsa.bilibililiveapiclient.model.search.LiveRoom> liveRoomList) {
        if(liveRoomList == null || liveRoomList.isEmpty()){
            return;
        }
        LiveRoomPresenter liveRoomPresenter = new LiveRoomPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(liveRoomPresenter);
        for (com.muedsa.bilibililiveapiclient.model.search.LiveRoom searchLiveRoom : liveRoomList) {
            LiveRoom liveRoom = LiveRoomConvert.buildWithRoomId(0);
            LiveRoomConvert.updateRoomInfo(liveRoom, searchLiveRoom);
            listRowAdapter.add(liveRoom);
        }
        HeaderItem liveUserHeader = new HeaderItem(HEAD_TITLE_SEARCH_RESULT_LIVE_ROOM,
                getResources().getString(R.string.head_title_search_result_live_room));
        mRowsAdapter.add(new ListRow(liveUserHeader, listRowAdapter));
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            LiveRoom liveRoom = null;
            FragmentActivity activity = requireActivity();
            if(item instanceof LiveUser){
                LiveUser liveUser = (LiveUser) item;
                liveRoom = LiveRoomConvert.buildWithRoomId(liveUser.getRoomId());
            }else if(item instanceof LiveRoom){
                liveRoom = (LiveRoom) item;
            }
            if(liveRoom != null){
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.LIVE_ROOM, liveRoom);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                DetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                activity.startActivity(intent, bundle);
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
            if (item instanceof LiveUser) {
                mBackgroundUri = ((LiveUser) item).getUface();
                startBackgroundTimer();
            }else if(item instanceof LiveRoom){
                mBackgroundUri = ((LiveRoom) item).getSystemCoverImageUrl();
                startBackgroundTimer();
            }
        }
    }
}