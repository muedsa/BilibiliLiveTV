package com.muedsa.bilibililivetv.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.leanback.app.ErrorSupportFragment;

import com.muedsa.bilibililivetv.R;

/*
 * This class demonstrates how to extend ErrorSupportFragment.
 */
public class ErrorFragment extends ErrorSupportFragment {
    private static final String TAG = ErrorFragment.class.getSimpleName();
    private static final boolean TRANSLUCENT = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.app_name));
    }

    public void setErrorContent() {
        setImageDrawable(ContextCompat.getDrawable(requireContext(), androidx.leanback.R.drawable.lb_ic_sad_cloud));
        setMessage(getResources().getString(R.string.error_fragment_message));
        setDefaultBackground(TRANSLUCENT);

        setButtonText(getResources().getString(R.string.dismiss_error));
        setButtonClickListener(
                arg0 -> requireActivity().getSupportFragmentManager().beginTransaction().remove(ErrorFragment.this).commit());
    }
}