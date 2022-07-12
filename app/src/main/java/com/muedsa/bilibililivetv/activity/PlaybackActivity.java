package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.PlaybackVideoFragment;

/**
 * Loads {@link PlaybackVideoFragment}.
 */
public class PlaybackActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_playback, new PlaybackVideoFragment())
                    .commit();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}