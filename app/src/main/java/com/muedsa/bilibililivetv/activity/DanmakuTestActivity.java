package com.muedsa.bilibililivetv.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.DanmakuTestFragment;
public class DanmakuTestActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmaku_test);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.danmaku_test_fragment, new DanmakuTestFragment())
                    .commitNow();
        }
    }
}
