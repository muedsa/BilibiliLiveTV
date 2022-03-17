package com.muedsa.bilibililivetv.ui;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

/**
 * Loads {@link PlaybackVideoFragment}.
 */
public class PlaybackActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new PlaybackVideoFragment())
                    .commit();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}