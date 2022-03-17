package com.muedsa.bilibililivetv.ui;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.muedsa.bilibililivetv.R;

public class SearchActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.search_fragment, new SearchFragment())
                .commit();
    }
}