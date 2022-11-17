package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.SettingsFragment;

public class SettingsActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings_fragment, new SettingsFragment())
                    .commitNow();
        }
    }
}
