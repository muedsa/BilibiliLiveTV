package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.SearchFragment;

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