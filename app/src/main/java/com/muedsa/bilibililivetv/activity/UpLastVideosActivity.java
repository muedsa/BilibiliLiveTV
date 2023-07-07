package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.UpLastVideoFragment;

public class UpLastVideosActivity extends FragmentActivity {

    public static final String MID = "mid";
    public static final String UNAME = "uname";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_last_videos);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.up_last_videos_fragment, new UpLastVideoFragment())
                    .commitNow();
        }
    }
}
