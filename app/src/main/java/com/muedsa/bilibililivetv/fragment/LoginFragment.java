package com.muedsa.bilibililivetv.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.muedsa.bilibililiveapiclient.ErrorCode;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.request.HttpRequestException;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.httpjsonclient.Container;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();

    private ImageView imageView;
    private int imageSize;

    private TextView textView;

    private ListCompositeDisposable listCompositeDisposable;

    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        imageView = root.findViewById(R.id.login_fragment_image_view);
        textView = root.findViewById(R.id.login_fragment_text_view);
        FragmentActivity activity = requireActivity();
        WindowManager windowManager = activity.getWindowManager();
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Rect bounds = windowMetrics.getBounds();
            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            height = bounds.height() - insets.top - insets.bottom;

        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
        }
        imageSize = height / 2;
        int mTop = (height - imageSize) / 2;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if(layoutParams instanceof LinearLayout.LayoutParams){
            ((LinearLayout.LayoutParams)layoutParams).topMargin = mTop;
        }
        listCompositeDisposable = new ListCompositeDisposable();
        checkLogin();
        return root;
    }

    private void checkLogin() {
        Log.d(TAG, "checkLogin");
        listCompositeDisposable.clear();
        RxRequestFactory.bilibiliNav()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userNav -> {
                    Log.d(TAG, "checkLogin: result");
                    if(userNav.isLogin()){
                        prepareLogin();
                    }else{
                        textView.setText(userNav.getUname());
                        GlideApp.with(requireActivity())
                                .load(userNav.getFace())
                                .centerCrop()
                                .into(new CustomTarget<Drawable>(imageSize, imageSize) {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable drawable,
                                                                @Nullable Transition<? super Drawable> transition) {
                                        imageView.setImageDrawable(drawable);
                                    }
                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                                });
                    }
                }, throwable -> {
                    //todo handler throwable
                    Log.d(TAG, "bilibiliNav: ", throwable);
                    if(throwable instanceof HttpRequestException){
                        if(((HttpRequestException) throwable).getCode() == ErrorCode.NOT_LOGIN){
                            prepareLogin();
                        }
                    }
                }, listCompositeDisposable);
    }

    private void prepareLogin(){
        Log.d(TAG, "prepareLogin");
        listCompositeDisposable.clear();
        RxRequestFactory.bilibiliLoginUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginUrl -> {
                    updateQRCode(loginUrl.getUrl());
                    textView.setText(requireActivity().getString(R.string.bilibili_scan_qr_code_login));
                    startCheckScan(loginUrl.getOauthKey());
                }, throwable -> {
                    //todo handler throwable
                    Log.d(TAG, "bilibiliLoginUrl: ", throwable);
                }, listCompositeDisposable);
    }

    private void updateQRCode(String data) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, imageSize, imageSize);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x * width + y] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        imageView.setImageBitmap(bitmap);
    }

    private void startCheckScan(String oauthKey) {
        Log.d(TAG, "startCheckScan");
        releaseTimer();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "bilibiliLoginInfo: oauthKey" + oauthKey);
                RxRequestFactory.bilibiliLoginInfo(oauthKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loginInfo -> {
                            if(loginInfo.getStatus()) {
                                Log.d(TAG, "login success: " + loginInfo.getData().getUrl());
                                releaseTimer();
                                listCompositeDisposable.clear();
                                BilibiliLiveApi.client().putCookie(Container.COOKIE_KEY_SESSDATA, getSessData(loginInfo.getData().getUrl()));
                                checkLogin();
                            }
                        }, throwable -> {
                            //todo handler throwable
                            Log.d(TAG, "startCheckScan: ", throwable);
                        }, listCompositeDisposable);
            }
        }, 1500, 1500);
    }

    private static final Pattern regex = Pattern.compile("\\S*SESSDATA=(\\S*)&bili_jct\\S*");
    private static String getSessData(String loginUrl){
        String sessData = "";
        Matcher matcher = regex.matcher(loginUrl);
        if(matcher.matches()){
            sessData = matcher.group(1);
        }
        Log.d(TAG, "SESSDATA:" + sessData);
        return sessData;
    }

    private void releaseTimer() {
        if(Objects.nonNull(timer)) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listCompositeDisposable.dispose();
        releaseTimer();
    }
}