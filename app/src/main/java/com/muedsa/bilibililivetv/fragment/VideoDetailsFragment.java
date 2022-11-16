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
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.ErrorCode;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.video.PlayInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoPage;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.MainActivity;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.activity.VideoPlaybackActivity;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.model.VideoInfoConvert;
import com.muedsa.bilibililivetv.model.VideoPlayInfo;
import com.muedsa.bilibililivetv.presenter.DetailsDescriptionPresenter;
import com.muedsa.bilibililivetv.presenter.VideoCardPresenter;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.util.DpUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VideoDetailsFragment extends DetailsSupportFragment {
    private static final String TAG = VideoDetailsFragment.class.getSimpleName();


    private static final int DETAIL_THUMB_WIDTH = 216;
    private static final int DETAIL_THUMB_HEIGHT = 136;

    private String bv;
    private int page;

    private String url;
    private VideoInfo videoInfo;

    private List<VideoPlayInfo> videoPlayInfoList;

    private DetailsOverviewRow detailsOverviewRow;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private DetailsSupportFragmentBackgroundController mDetailsBackground;

    private ListCompositeDisposable listCompositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);
        mDetailsBackground = new DetailsSupportFragmentBackgroundController(this);
        FragmentActivity activity = requireActivity();
        bv = activity.getIntent().getStringExtra(VideoDetailsActivity.VIDEO_BV);
        page = activity.getIntent().getIntExtra(VideoDetailsActivity.VIDEO_PAGE, 1);
        if (!Strings.isNullOrEmpty(bv) && bv.startsWith(BilibiliLiveApi.VIDEO_BV_PREFIX)) {
            listCompositeDisposable = new ListCompositeDisposable();
            mPresenterSelector = new ClassPresenterSelector();
            mAdapter = new ArrayObjectAdapter(mPresenterSelector);
            setupDetailsOverviewRowPresenter();
            initializeBackground();
            setAdapter(mAdapter);
            initVideoDetail();
            setOnItemViewClickedListener(new ItemViewClickedListener());
        } else {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initVideoDetail() {
        RxRequestFactory.bilibiliVideoDetail(bv, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoDetail -> {
                    FragmentActivity activity = requireActivity();
                    if (Objects.nonNull(videoDetail.getVideoInfo())
                            && Objects.nonNull(videoDetail.getVideoInfo().getVideoData())) {
                        //视频信息 封面图
                        videoInfo = videoDetail.getVideoInfo();
                        url = videoDetail.getUrl();
                        setupDetailsOverviewRow();
                        setupVideoPagesRow();
                        updateCardImage();
                        updateBackground();
                        //播放按钮
                        BilibiliResponse<PlayInfo> playInfoResponse = videoDetail.getPlayInfoResponse();
                        if (Objects.nonNull(playInfoResponse)) {
                            if (Objects.nonNull(playInfoResponse.getCode()) &&
                                    ErrorCode.SUCCESS == playInfoResponse.getCode()) {
                                videoPlayInfoList = VideoInfoConvert.buildVideoPlayInfoList(videoInfo, playInfoResponse.getData(), url);
                                updateDetailsOverviewActions();
                            } else {
                                ToastUtil.showLongToast(activity, Objects.nonNull(playInfoResponse.getMessage())?
                                        playInfoResponse.getMessage() : activity.getString(R.string.toast_msg_jump_video_detail_error));
                            }
                        } else {
                            ToastUtil.showLongToast(activity, activity.getString(R.string.toast_msg_jump_video_detail_error));
                        }
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    } else {
                        ToastUtil.showLongToast(activity, activity.getString(R.string.toast_msg_jump_video_detail_error));
                    }

                }, throwable -> {
                    Log.e(TAG, "bilibiliVideoDetail error:", throwable);
                    FragmentActivity activity = requireActivity();
                    ToastUtil.showLongToast(activity, activity.getString(R.string.toast_msg_jump_video_detail_error) + ":" +throwable.getMessage());
                }, listCompositeDisposable);

    }

    private void setupDetailsOverviewRow() {
        if (Objects.nonNull(videoInfo)) {
            detailsOverviewRow = new DetailsOverviewRow(videoInfo);
            detailsOverviewRow.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.no_cover));
            mAdapter.add(detailsOverviewRow);
        }
    }

    private void setupVideoPagesRow() {
        if (Objects.nonNull(videoInfo)
                && Objects.nonNull(videoInfo.getVideoData())
                && Objects.nonNull(videoInfo.getVideoData().getPages())
                && videoInfo.getVideoData().getPages().size() > 1) {
            VideoCardPresenter presenter = new VideoCardPresenter();
            ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(presenter);
            arrayObjectAdapter.addAll(0, videoInfo.getVideoData().getPages());
            HeaderItem headerItem = new HeaderItem("视频选集");
            ListRow listRow = new ListRow(headerItem, arrayObjectAdapter);
            mAdapter.add(listRow);
            mPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        }
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
                getActivity(), VideoDetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(sharedElementHelper);
        detailsPresenter.setParticipatingEntranceTransition(true);

        detailsPresenter.setOnActionClickedListener(action -> {
            FragmentActivity activity = requireActivity();
            if (Objects.nonNull(videoPlayInfoList) && action.getId() < videoPlayInfoList.size()) {
                int index = (int) action.getId();
                Intent intent = new Intent(activity, VideoPlaybackActivity.class);
                intent.putExtra(VideoDetailsActivity.PLAY_INFO, videoPlayInfoList.get(index));
                startActivity(intent);
            } else {
                ToastUtil.showLongToast(activity, activity.getString(R.string.toast_msg_video_play_failure));
            }
        });
        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
    }

    private void updateDetailsOverviewActions() {
        if (Objects.nonNull(videoPlayInfoList)) {
            ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();
            for (int i = 0; i < videoPlayInfoList.size(); i++) {
                VideoPlayInfo videoPlayInfo = videoPlayInfoList.get(i);
                Action action = new Action(i, videoPlayInfo.getQualityDescription(), videoPlayInfo.getCodecs());
                actionAdapter.add(action);
            }
            detailsOverviewRow.setActionsAdapter(actionAdapter);
        }
    }

    private void updateCardImage() {
        FragmentActivity activity = requireActivity();
        if (Objects.isNull(videoInfo) || Objects.isNull(videoInfo.getVideoData()) || Strings.isNullOrEmpty(videoInfo.getVideoData().getPic()))
            return;
        int width = DpUtil.convertDpToPixel(activity, DETAIL_THUMB_WIDTH);
        int height = DpUtil.convertDpToPixel(activity, DETAIL_THUMB_HEIGHT);
        GlideApp.with(activity)
                .load(videoInfo.getVideoData().getPic())
                .centerCrop()
                .error(R.drawable.no_cover)
                .into(new CustomTarget<Drawable>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable drawable,
                                                @Nullable Transition<? super Drawable> transition) {
                        detailsOverviewRow.setImageDrawable(drawable);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });
    }

    private void updateBackground() {
        if (Objects.isNull(videoInfo) || Objects.isNull(videoInfo.getVideoData()) || Objects.isNull(videoInfo.getVideoData().getPages()))
            return;
        videoInfo.getVideoData().getPages().stream()
                .filter(p -> p.getPage() == page)
                .findFirst()
                .ifPresent(videoPage -> {
                    GlideApp.with(requireActivity())
                            .asBitmap()
                            .centerCrop()
                            .error(R.drawable.default_background)
                            .load(videoPage.getFirstFrame())
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap bitmap,
                                                            @Nullable Transition<? super Bitmap> transition) {
                                    mDetailsBackground.setCoverBitmap(bitmap);
                                    mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {}
                            });
                });
    }

    private void initializeBackground() {
        mDetailsBackground.enableParallax();
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof VideoPage) {
                VideoPage videoPage = (VideoPage) item;
                FragmentActivity activity = requireActivity();
                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO_BV, bv);
                intent.putExtra(VideoDetailsActivity.VIDEO_PAGE, videoPage.getPage());
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        VideoDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                activity.startActivity(intent, bundle);
            }
        }
    }
}
