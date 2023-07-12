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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpacePageInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpaceSearchResult;
import com.muedsa.bilibililiveapiclient.model.space.SpaceUpList;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.UpLastVideosActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.model.bilibili.UpLastVideosViewModel;
import com.muedsa.bilibililivetv.model.factory.BilibiliRequestViewModelFactory;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.bilibililivetv.widget.PageFlowObjectAdapter;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class UpLastVideosFragment extends VerticalGridSupportFragment {
    private static final String TAG = UpLastVideosFragment.class.getSimpleName();

    private static final int NUM_OF_COLS = 6;

    private PageFlowObjectAdapter mAdapter;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private Drawable mDefaultBackground;
    private int defaultWidth;
    private int defaultHeight;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private long mid;
    private String uname;

    private int pageNum = 1;

    private static final int PAGE_SIZE = 50;

    private boolean loading = false;

    private UpLastVideosViewModel upLastVideosViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity activity = requireActivity();
        Intent intent = activity.getIntent();
        mid = intent.getLongExtra(UpLastVideosActivity.MID, 0);
        uname = intent.getStringExtra(UpLastVideosActivity.UNAME);
        if (Strings.isNullOrEmpty(uname)) {
            uname = activity.getString(R.string.action_up_last_videos);
        }

        if (mid > 0) {
            prepareBackgroundManager();
            setOnItemViewSelectedListener(new ItemViewSelectedListener());
            setOnItemViewClickedListener(new ItemViewClickedListener());
            setTitle(uname);
            setupRowAdapter();
            setupViewModel();
        }
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
            defaultHeight = bounds.height() - insets.top - insets.bottom;
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
        mBackgroundTimer.schedule(new UpLastVideosFragment.UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> updateBackground(mBackgroundUri));
        }
    }

    private void setupRowAdapter() {
        VerticalGridPresenter verticalGridPresenter = new VerticalGridPresenter();
        verticalGridPresenter.setNumberOfColumns(NUM_OF_COLS);
        setGridPresenter(verticalGridPresenter);

        VideoCardPresenter videoCardPresenter = new VideoCardPresenter();
        mAdapter = new PageFlowObjectAdapter(videoCardPresenter);
        setAdapter(mAdapter);
    }

    private void setupViewModel() {
        upLastVideosViewModel = new ViewModelProvider(UpLastVideosFragment.this,
                BilibiliRequestViewModelFactory.getInstance())
                .get(UpLastVideosViewModel.class);

        upLastVideosViewModel.getResult().observe(this, m -> {
            if (m.getStatus() == RMessage.Status.LOADING) {
                loading = true;
            } else if (m.getStatus() == RMessage.Status.SUCCESS) {
                SpaceSearchResult result = m.getData();
                if (result != null) {
                    SpacePageInfo page = result.getPage();
                    SpaceUpList spaceUpList = result.getList();
                    if (spaceUpList != null && spaceUpList.getVlist() != null) {
                        mAdapter.append(spaceUpList.getVlist(), page.getPn(),
                                spaceUpList.getVlist().size() < PAGE_SIZE);
                    }
                }
                loading = false;
            } else if (m.getStatus() == RMessage.Status.ERROR) {
                loading = false;
                Log.e(TAG, "bilibiliVideoDetail error:", m.getError());
                FragmentActivity activity = requireActivity();
                ToastUtil.error(activity, activity.getString(R.string.toast_msg_up_last_videos_failure), m.getError());
            }
        });

        if(mAdapter.hasNextPage()){
            upLastVideosViewModel.loadVideos(mAdapter.currentPageNum() + 1, PAGE_SIZE, mid);
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            if (item instanceof SearchVideoInfo) {
                mBackgroundUri = ((SearchVideoInfo) item).getPic();
                startBackgroundTimer();
                int index = mAdapter.indexOf(item);
                if (mAdapter.hasNextPage() && !loading && shouldNextPage(index)) {
                    upLastVideosViewModel.loadVideos(mAdapter.currentPageNum() + 1, PAGE_SIZE, mid);
                }
            }
        }
    }

    private boolean shouldNextPage(int index) {
        int size = mAdapter.size();
        return index >= size - (size % NUM_OF_COLS) - NUM_OF_COLS;
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            ImageView sharedElement = ((ImageCardView) itemViewHolder.view).getMainImageView();
            if (item instanceof SearchVideoInfo) {
                SearchVideoInfo searchVideoInfo = (SearchVideoInfo) item;
                FragmentActivity activity = requireActivity();
                Intent intent = new Intent(activity, VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, searchVideoInfo.getBvId());
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
                Bundle bundle = null;
                if (Objects.nonNull(sharedElement)) {
                    bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement,
                            VideoDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                }
                requireActivity().startActivity(intent, bundle);
            }
        }
    }
}
