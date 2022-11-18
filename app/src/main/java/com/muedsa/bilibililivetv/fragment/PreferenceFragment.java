package com.muedsa.bilibililivetv.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat;

import com.muedsa.bilibililivetv.R;

public class PreferenceFragment extends LeanbackPreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}