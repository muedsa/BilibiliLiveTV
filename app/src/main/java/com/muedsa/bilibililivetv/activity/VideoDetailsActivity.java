package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.VideoDetailsFragment;

public class VideoDetailsActivity extends FragmentActivity {
    public static final String SHARED_ELEMENT_NAME = VideoDetailsActivity.class.getName() + "_SE";
    public static final String VIDEO_BV = "videoBV";
    public static final String VIDEO_PAGE = "videoPage";
    public static final String PLAY_INFO = "playInfo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment, new VideoDetailsFragment())
                    .commitNow();
        }
    }
}
