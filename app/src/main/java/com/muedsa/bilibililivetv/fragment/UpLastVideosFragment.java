package com.muedsa.bilibililivetv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpacePageInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpaceSearchResult;
import com.muedsa.bilibililiveapiclient.model.space.SpaceUpList;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.UpLastVideosActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.model.bilibili.UpLastVideosViewModel;
import com.muedsa.bilibililivetv.model.factory.BilibiliRequestViewModelFactory;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.util.CrashlyticsUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.bilibililivetv.widget.BackgroundManagerDelegate;
import com.muedsa.bilibililivetv.widget.PageFlowObjectAdapter;

import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class UpLastVideosFragment extends VerticalGridSupportFragment {
    private static final String TAG = UpLastVideosFragment.class.getSimpleName();

    private static final int NUM_OF_COLS = 6;

    private PageFlowObjectAdapter mAdapter;

    private BackgroundManagerDelegate backgroundManagerDelegate;

    private long mid;
    private String uname;

    private static final int PAGE_SIZE = 50;

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
            backgroundManagerDelegate = new BackgroundManagerDelegate(activity,
                    r -> r.transform(new FitCenter(), new BlurTransformation(25, 3)));
            setOnItemViewSelectedListener(new ItemViewSelectedListener());
            setOnItemViewClickedListener(new ItemViewClickedListener());
            setTitle(uname);
            setupRowAdapter();
            setupViewModel();
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
                mAdapter.setLoading(true);
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
                mAdapter.setLoading(false);
            } else if (m.getStatus() == RMessage.Status.ERROR) {
                mAdapter.setLoading(false);
                Log.e(TAG, "bilibiliVideoDetail error:", m.getError());
                FragmentActivity activity = requireActivity();
                ToastUtil.error(activity, activity.getString(R.string.toast_msg_up_last_videos_failure), m.getError());
                CrashlyticsUtil.log(m.getError());
            }
        });

        if (mAdapter.hasNextPage()) {
            upLastVideosViewModel.loadVideos(mAdapter.currentPageNum() + 1, PAGE_SIZE, mid);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        backgroundManagerDelegate.dispose();
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            String newBackgroundUri = null;
            if (item instanceof SearchVideoInfo) {
                newBackgroundUri = ((SearchVideoInfo) item).getPic();
                int index = mAdapter.indexOf(item);
                if (mAdapter.hasNextPage() && !mAdapter.isLoading() && shouldNextPage(index)) {
                    upLastVideosViewModel.loadVideos(mAdapter.currentPageNum() + 1, PAGE_SIZE, mid);
                }
            }

            if (!Strings.isNullOrEmpty(newBackgroundUri)) {
                backgroundManagerDelegate.startBackgroundUpdate(newBackgroundUri);
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
