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
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

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

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.LiveRoomDetailsActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.model.LiveUser;
import com.muedsa.bilibililivetv.model.LiveUserConvert;
import com.muedsa.bilibililivetv.presenter.LiveRoomPresenter;
import com.muedsa.bilibililivetv.presenter.LiveUserPresenter;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.ToastUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class SearchFragment extends SearchSupportFragment implements SearchSupportFragment.SearchResultProvider {
    private static final String TAG = SearchFragment.class.getSimpleName();

    private static final int HEAD_TITLE_SEARCH_RESULT_LIVE_USER = 1;
    private static final int HEAD_TITLE_SEARCH_RESULT_LIVE_ROOM = 2;

    private ArrayObjectAdapter mRowsAdapter;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private Drawable mDefaultBackground;
    private int defaultWidth;
    private int defaultHeight;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;
    private ListCompositeDisposable listCompositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareBackgroundManager();
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setSearchResultProvider(this);
        listCompositeDisposable = new ListCompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listCompositeDisposable.dispose();
    }

    private void prepareBackgroundManager() {
        mDefaultBackground = ContextCompat.getDrawable(requireContext(), R.drawable.default_background);
        FragmentActivity activity = requireActivity();
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
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
        listCompositeDisposable.clear();
        boolean flag = true;
        if (query.startsWith(BilibiliLiveApi.VIDEO_BV_PREFIX)) {
            flag = false;
            Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
            intent.putExtra(VideoDetailsActivity.VIDEO_BV, query);
            intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
            requireActivity().startActivity(intent);
        } else {
            RxRequestFactory.bilibiliSearchLive(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchResult -> {
                                mRowsAdapter.clear();
                                loadLiveUserSearchData(searchResult.getLiveUser());
                                loadLiveRoomSearchData(searchResult.getLiveRoom());
                            }, throwable -> {
                                ToastUtil.showLongToast(requireActivity(), throwable.getMessage());
                                Log.e(TAG, "bilibiliSearchLive error", throwable);
                            },
                            listCompositeDisposable);
        }
        return flag;
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
        LiveRoomPresenter liveRoomPresenter = new LiveRoomPresenter(null);
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
            if(liveRoom != null) {
                Intent intent = new Intent(getActivity(), LiveRoomDetailsActivity.class);
                intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, liveRoom);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                LiveRoomDetailsActivity.SHARED_ELEMENT_NAME)
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