package com.muedsa.bilibililivetv.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.LoginFragment;

public class LoginActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_fragment, new LoginFragment())
                    .commitNow();
        }
    }
}