package com.muedsa.bilibililivetv.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.model.LiveRoom;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.model.LiveRoomHistoryHolder;
import com.muedsa.bilibililivetv.model.LiveRoomHistoryHolder;
import com.muedsa.bilibililivetv.task.RequestDanmuInfoTask;
import com.muedsa.bilibililivetv.task.RequestLiveRoomInfoTask;
import com.muedsa.bilibililivetv.task.RequestPlayUrlTask;
import com.muedsa.bilibililivetv.task.TaskRunner;

/*
 * LeanbackDetailsFragment extends DetailsFragment, a Wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its meta plus related videos.
 */
public class VideoDetailsFragment extends DetailsSupportFragment {
    private static final String TAG = "VideoDetailsFragment";

    private static final int ACTION_NOTHING = -1;
    private static final int ACTION_WATCH_TRAILER = 1;

    private static final int DETAIL_THUMB_WIDTH = 216;
    private static final int DETAIL_THUMB_HEIGHT = 136;

    private LiveRoom mSelectedLiveRoom;

    private Action playAction;
    private Action liveStatusAction;
    private Action onlineNumAction;
    private DetailsOverviewRow detailsOverviewRow;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private DetailsSupportFragmentBackgroundController mDetailsBackground;

    private TaskRunner taskRunner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);

        mDetailsBackground = new DetailsSupportFragmentBackgroundController(this);

        mSelectedLiveRoom = (LiveRoom) requireActivity().getIntent()
                .getSerializableExtra(DetailsActivity.LIVE_ROOM);

        if (mSelectedLiveRoom != null && mSelectedLiveRoom.getId() > 0) {

            taskRunner = TaskRunner.getInstance();

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
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }


    private void initializeLiveRoomInfo(){
        taskRunner.executeAsync(new RequestLiveRoomInfoTask(mSelectedLiveRoom.getId()), msg -> {
            switch (msg.what){
                case SUCCESS:
                    LiveRoomConvert.updateRoomInfo(mSelectedLiveRoom, (LargeInfo)msg.obj);
                    LiveRoomHistoryHolder.addHistory(mSelectedLiveRoom, getContext());
                    updateCardImage();
                    updateBackground();
                    liveStatusAction.setLabel2(mSelectedLiveRoom.getLiveStatusDesc(getResources()));
                    onlineNumAction.setLabel2(String.valueOf(mSelectedLiveRoom.getOnlineNum()));
                    break;
                case FAIL:
                default:
                    String errorMsg =  getResources().getString(R.string.live_room_info_failure) + (msg.obj instanceof String ? ":" + msg.obj : "");
                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                    break;
            }
        });

        taskRunner.executeAsync(new RequestDanmuInfoTask(mSelectedLiveRoom.getId()), msg -> {
            switch (msg.what){
                case SUCCESS:
                    mSelectedLiveRoom.setDanmuWsToken((String)msg.obj);
                    break;
                case FAIL:
                default:
                    String errorMsg = getResources().getString(R.string.live_danmu_ws_token_failure) + (msg.obj instanceof String ? ":" + msg.obj : "");
                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void initializePlayUrl(){
        playAction.setLabel2(getResources().getString(R.string.watch_trailer_loading));
        taskRunner.executeAsync(new RequestPlayUrlTask(mSelectedLiveRoom.getId()), msg -> {
            switch (msg.what){
                case SUCCESS:
                    mSelectedLiveRoom.setPlayUrlArr(((String[]) msg.obj));
                    playAction.setLabel2(getResources().getString(R.string.watch_trailer_play));
                    break;
                case FAIL:
                default:
                    Toast.makeText(getActivity(), getResources().getString(R.string.live_play_failure), Toast.LENGTH_LONG)
                            .show();
            }
        });
    }

    private void initializeBackground() {
        mDetailsBackground.enableParallax();
        updateBackground();
    }
    private void updateBackground(){
        Glide.with(requireActivity())
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
        updateCardImage();

        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();
        playAction = new Action(ACTION_WATCH_TRAILER, getResources().getString(R.string.watch_trailer_title), getResources().getString(R.string.watch_trailer_loading));
        actionAdapter.add(playAction);
        liveStatusAction = new Action(ACTION_NOTHING, getResources().getString(R.string.room_live_status), mSelectedLiveRoom.getLiveStatusDesc(getResources()));
        actionAdapter.add(liveStatusAction);
        onlineNumAction = new Action(ACTION_NOTHING, getResources().getString(R.string.room_online_num), String.valueOf(mSelectedLiveRoom.getOnlineNum()));
        actionAdapter.add(onlineNumAction);
        detailsOverviewRow.setActionsAdapter(actionAdapter);

        mAdapter.add(detailsOverviewRow);
    }

    private void updateCardImage(){
        FragmentActivity activity = getActivity();
        if(activity == null) return;
        int width = convertDpToPixel(activity.getApplicationContext(), DETAIL_THUMB_WIDTH);
        int height = convertDpToPixel(activity.getApplicationContext(), DETAIL_THUMB_HEIGHT);
        Glide.with(activity)
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
                getActivity(), DetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(sharedElementHelper);
        detailsPresenter.setParticipatingEntranceTransition(true);

        detailsPresenter.setOnActionClickedListener(action -> {
            if (action.getId() == ACTION_WATCH_TRAILER) {
                if(mSelectedLiveRoom.getPlayUrlArr() == null || mSelectedLiveRoom.getPlayUrlArr().length == 0){
                    Toast.makeText(getActivity(), getResources().getString(R.string.live_play_failure), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), PlaybackActivity.class);
                    intent.putExtra(DetailsActivity.LIVE_ROOM, mSelectedLiveRoom);
                    startActivity(intent);
                }
            }
        });
        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
    }

    private int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
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
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.liveRoom), mSelectedLiveRoom);

                Bundle bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(),
                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                                DetailsActivity.SHARED_ELEMENT_NAME)
                                .toBundle();
                getActivity().startActivity(intent, bundle);
            }
        }
    }
}