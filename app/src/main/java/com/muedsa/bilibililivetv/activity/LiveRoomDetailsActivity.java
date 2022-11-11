package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.LiveRoomDetailsFragment;

public class LiveRoomDetailsActivity extends FragmentActivity {
    public static final String SHARED_ELEMENT_NAME = LiveRoomDetailsActivity.class.getName() + "_SE";
    public static final String LIVE_ROOM = "liveRoom";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment, new LiveRoomDetailsFragment())
                    .commitNow();
        }
    }

}