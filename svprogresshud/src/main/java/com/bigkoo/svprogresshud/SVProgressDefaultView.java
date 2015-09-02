package com.bigkoo.svprogresshud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sai on 15/8/15.
 * 默认的SVProgress效果
 */
public class SVProgressDefaultView extends LinearLayout {
    private int resBigLoading = R.drawable.ic_svstatus_loading;
    private int resInfo = R.drawable.ic_svstatus_info;
    private int resSuccess = R.drawable.ic_svstatus_success;
    private int resError = R.drawable.ic_svstatus_error;
    private ImageView ivBigLoading, ivSmallLoading;
    private SVCircleProgressBar circleProgressBar;
    private TextView tvMsg;

    private RotateAnimation mRotateAnimation;

    public SVProgressDefaultView(Context context) {
        super(context);
        initViews();
        init();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_svprogressdefault, this, true);
        ivBigLoading = (ImageView) findViewById(R.id.ivBigLoading);
        ivSmallLoading = (ImageView) findViewById(R.id.ivSmallLoading);
        circleProgressBar = (SVCircleProgressBar) findViewById(R.id.circleProgressBar);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
    }

    private void init() {
        mRotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000L);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    public void show() {
        clearAnimations();
        ivBigLoading.setImageResource(resBigLoading);
        ivBigLoading.setVisibility(View.VISIBLE);
        ivSmallLoading.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.GONE);
        tvMsg.setVisibility(View.GONE);
        //开启旋转动画
        ivBigLoading.startAnimation(mRotateAnimation);
    }

    public void showWithStatus(String string) {
        if (string == null) {
            show();
            return;
        }
        showBaseStatus(resBigLoading, string);
        //开启旋转动画
        ivSmallLoading.startAnimation(mRotateAnimation);
    }

    public void showInfoWithStatus(String string) {
        showBaseStatus(resInfo, string);
    }

    public void showSuccessWithStatus(String string) {
        showBaseStatus(resSuccess, string);
    }

    public void showErrorWithStatus(String string) {
        showBaseStatus(resError, string);
    }
    public void showWithProgress(String string) {
        showProgress(string);
    }

    public SVCircleProgressBar getCircleProgressBar() {
        return circleProgressBar;
    }

    public void setText(String string){
        tvMsg.setText(string);
    }

    public void showProgress(String string) {
        clearAnimations();
        tvMsg.setText(string);
        ivBigLoading.setVisibility(View.GONE);
        ivSmallLoading.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.VISIBLE);
    }

    public void showBaseStatus(int res, String string) {
        clearAnimations();
        ivSmallLoading.setImageResource(res);
        tvMsg.setText(string);
        ivBigLoading.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.GONE);
        ivSmallLoading.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        clearAnimations();
    }

    private void clearAnimations() {
        ivBigLoading.clearAnimation();
        ivSmallLoading.clearAnimation();
    }

}
