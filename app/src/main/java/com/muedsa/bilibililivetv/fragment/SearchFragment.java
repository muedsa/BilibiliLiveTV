package com.muedsa.bilibililivetv.fragment;

import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muedsa.bilibililiveapiclient.model.search.SearchLiveRoom;
import com.muedsa.bilibililiveapiclient.model.search.SearchLiveUser;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.LiveRoomDetailsActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.model.LiveUser;
import com.muedsa.bilibililivetv.model.LiveUserConvert;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.model.bilibili.SearchViewModel;
import com.muedsa.bilibililivetv.model.factory.BilibiliRequestViewModelFactory;
import com.muedsa.bilibililivetv.presenter.LiveRoomCardPresenter;
import com.muedsa.bilibililivetv.presenter.LiveUserCardPresenter;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.ToastUtil;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SearchFragment extends SearchSupportFragment implements SearchSupportFragment.SearchResultProvider {
    private static final String TAG = SearchFragment.class.getSimpleName();

    private static final int HEAD_TITLE_SEARCH_RESULT_VIDEO = 1;
    private static final int HEAD_TITLE_SEARCH_RESULT_LIVE_USER = 2;
    private static final int HEAD_TITLE_SEARCH_RESULT_LIVE_ROOM = 3;

    private ArrayObjectAdapter mRowsAdapter;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private Drawable mDefaultBackground;
    private int defaultWidth;
    private int defaultHeight;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private SearchViewModel searchViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareBackgroundManager();
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setSearchResultProvider(this);


        searchViewModel = new ViewModelProvider(SearchFragment.this,
                BilibiliRequestViewModelFactory.getInstance())
                .get(SearchViewModel.class);
        searchViewModel.getResult().observe(this, m -> {
            if(RMessage.Status.LOADING.equals(m.getStatus())) {
                mRowsAdapter.clear();
            } else if(RMessage.Status.SUCCESS.equals(m.getStatus())) {
                SearchResult searchResult = m.getData();
                if(searchResult != null){
                    loadVideoSearchData(searchResult.getVideo());
                    loadLiveUserSearchData(searchResult.getLiveUser());
                    loadLiveRoomSearchData(searchResult.getLiveRoom());
                }
            } else if(RMessage.Status.ERROR.equals(m.getStatus())) {
                Log.e(TAG, "bilibiliSearchLive error", m.getError());
                ToastUtil.error(requireActivity(), "search error", m.getError());
            }
        });
    }

    private void prepareBackgroundManager() {
        mDefaultBackground = ContextCompat.getDrawable(requireContext(), R.drawable.default_background);
        FragmentActivity activity = requireActivity();
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        WindowManager windowManager = activity.getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
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

    private void updateBackground(String uri) {
        int width = defaultWidth;
        int height = defaultHeight;
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
        searchViewModel.search(query);
        return true;
    }

    private void loadVideoSearchData(List<SearchVideoInfo> searchVideoInfoList) {
        if(Objects.isNull(searchVideoInfoList) || searchVideoInfoList.isEmpty()){
            return;
        }
        VideoCardPresenter videoCardPresenter = new VideoCardPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(videoCardPresenter);
        for (SearchVideoInfo searchVideoInfo : searchVideoInfoList) {
            listRowAdapter.add(searchVideoInfo);
        }
        HeaderItem liveUserHeader = new HeaderItem(HEAD_TITLE_SEARCH_RESULT_VIDEO,
                getResources().getString(R.string.head_title_search_result_video));
        mRowsAdapter.add(new ListRow(liveUserHeader, listRowAdapter));
    }

    private void loadLiveUserSearchData(List<SearchLiveUser> searchLiveUserList) {
        if(searchLiveUserList == null || searchLiveUserList.isEmpty()){
            return;
        }
        LiveUserCardPresenter liveUserCardPresenter = new LiveUserCardPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(liveUserCardPresenter);
        for (SearchLiveUser searchLiveUser : searchLiveUserList) {
            LiveUser liveUser = LiveUserConvert.convert(searchLiveUser);
            listRowAdapter.add(liveUser);
        }
        HeaderItem liveUserHeader = new HeaderItem(HEAD_TITLE_SEARCH_RESULT_LIVE_USER,
                getResources().getString(R.string.head_title_search_result_live_user));
        mRowsAdapter.add(new ListRow(liveUserHeader, listRowAdapter));
    }

    private void loadLiveRoomSearchData(List<SearchLiveRoom> searchLiveRoomList) {
        if(searchLiveRoomList == null || searchLiveRoomList.isEmpty()){
            return;
        }
        LiveRoomCardPresenter liveRoomCardPresenter = new LiveRoomCardPresenter(null);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(liveRoomCardPresenter);
        for (SearchLiveRoom searchLiveRoom : searchLiveRoomList) {
            listRowAdapter.add(searchLiveRoom);
        }
        HeaderItem liveUserHeader = new HeaderItem(HEAD_TITLE_SEARCH_RESULT_LIVE_ROOM,
                getResources().getString(R.string.head_title_search_result_live_room));
        mRowsAdapter.add(new ListRow(liveUserHeader, listRowAdapter));
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            ImageView sharedElement = ((ImageCardView) itemViewHolder.view).getMainImageView();
            if(item instanceof LiveUser){
                LiveUser liveUser = (LiveUser) item;
                LiveRoom liveRoom = LiveRoomConvert.buildWithRoomId(liveUser.getRoomId());
                jumpLiveRoomDetailsActivity(liveRoom, sharedElement);
            }else if(item instanceof SearchLiveRoom){
                SearchLiveRoom searchLiveRoom = (SearchLiveRoom) item;
                LiveRoom liveRoom = LiveRoomConvert.buildWithRoomId(searchLiveRoom.getRoomId());
                jumpLiveRoomDetailsActivity(liveRoom, sharedElement);
            }else if(item instanceof SearchVideoInfo){
                SearchVideoInfo searchVideoInfo = (SearchVideoInfo) item;
                jumpVideoDetailsActivity(searchVideoInfo.getBvId(), sharedElement);
            }
        }
    }

    private void jumpLiveRoomDetailsActivity(LiveRoom liveRoom, View sharedElement) {
        if(Objects.nonNull(liveRoom)){
            FragmentActivity activity = requireActivity();
            Intent intent = new Intent(activity, LiveRoomDetailsActivity.class);
            intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, liveRoom);
            Bundle bundle = null;
            if(Objects.nonNull(sharedElement)){
                bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement,
                                LiveRoomDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
            }
            activity.startActivity(intent, bundle);
        }
    }

    private void jumpVideoDetailsActivity(String bv, View sharedElement) {
        FragmentActivity activity = requireActivity();
        Intent intent = new Intent(activity, VideoDetailsActivity.class);
        intent.putExtra(VideoDetailsActivity.VIDEO_BV, bv);
        intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
        Bundle bundle = null;
        if(Objects.nonNull(sharedElement)){
            bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement,
                    VideoDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
        }
        requireActivity().startActivity(intent, bundle);
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
            }else if(item instanceof SearchLiveRoom){
                mBackgroundUri = LiveRoomConvert.getImageUrl(((SearchLiveRoom) item));
                startBackgroundTimer();
            }else if(item instanceof SearchVideoInfo){
                mBackgroundUri = ((SearchVideoInfo) item).getPic();
                startBackgroundTimer();
            }
        }
    }
}