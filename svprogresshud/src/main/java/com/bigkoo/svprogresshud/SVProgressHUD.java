package com.bigkoo.svprogresshud;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

/**
 * Created by Sai on 15/8/15.
 */
public class SVProgressHUD {
    private Context context;
    private static SVProgressHUD mSVProgressHUD;
    private static final long DISMISSDELAYED = 1000;
    private SVProgressHUDMaskType mSVProgressHUDMaskType;

    public enum SVProgressHUDMaskType {
        None,  // 允许遮罩下面控件点击
        Clear,     // 不允许遮罩下面控件点击
        Black,     // 不允许遮罩下面控件点击，背景黑色半透明
        Gradient,   // 不允许遮罩下面控件点击，背景渐变半透明
        ClearCancel,     // 不允许遮罩下面控件点击，点击遮罩消失
        BlackCancel,     // 不允许遮罩下面控件点击，背景黑色半透明，点击遮罩消失
        GradientCancel   // 不允许遮罩下面控件点击，背景渐变半透明，点击遮罩消失
        ;
    }

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;// mSharedView 的 根View
    private SVProgressDefaultView mSharedView;

    private Animation outAnim;
    private Animation inAnim;
    private int gravity = Gravity.CENTER;

    private static final SVProgressHUD getInstance(Context context) {
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD();
            mSVProgressHUD.context = context;
            mSVProgressHUD.gravity = Gravity.CENTER;
            mSVProgressHUD.initViews();
            mSVProgressHUD.initDefaultView();
            mSVProgressHUD.initAnimation();
        }
        if (context != null && context != mSVProgressHUD.context ){
            mSVProgressHUD.context = context;
            mSVProgressHUD.initViews();
        }

        return mSVProgressHUD;
    }

    protected void initViews() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_svprogresshud, null, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }
    protected void initDefaultView(){
        mSharedView = new SVProgressDefaultView(context);
        params.gravity = gravity;
        mSharedView.setLayoutParams(params);
    }

    protected void initAnimation() {
        if(inAnim == null)
            inAnim = getInAnimation();
        if(outAnim == null)
            outAnim = getOutAnimation();
    }

    /**
     * show的时候调用
     */
    private void onAttached() {
        decorView.addView(rootView);
        if(mSharedView.getParent()!=null)((ViewGroup)mSharedView.getParent()).removeView(mSharedView);
        rootView.addView(mSharedView);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    private void svShow() {

        mHandler.removeCallbacksAndMessages(null);
        if (!isShowing()) {
            onAttached();
        }

        mSharedView.startAnimation(inAnim);

    }

    public static void show(Context context) {
        getInstance(context).setMaskType(SVProgressHUDMaskType.Black);
        getInstance(context).mSharedView.show();
        getInstance(context).svShow();
    }

    public static void showWithMaskType(Context context,SVProgressHUDMaskType maskType) {
        //判断maskType
        getInstance(context).setMaskType(maskType);
        getInstance(context).mSharedView.show();
        getInstance(context).svShow();
    }

    public static void showWithStatus(Context context,String string) {
        getInstance(context).setMaskType(SVProgressHUDMaskType.Black);
        getInstance(context).mSharedView.showWithStatus(string);
        getInstance(context).svShow();
    }

    public static void showWithStatus(Context context,String string, SVProgressHUDMaskType maskType) {
        getInstance(context).setMaskType(maskType);
        getInstance(context).mSharedView.showWithStatus(string);
        getInstance(context).svShow();
    }

    public static void showInfoWithStatus(Context context,String string) {
        getInstance(context).setMaskType(SVProgressHUDMaskType.Black);
        getInstance(context).mSharedView.showInfoWithStatus(string);
        getInstance(context).svShow();
        getInstance(context).scheduleDismiss();
    }

    public static void showInfoWithStatus(Context context,String string, SVProgressHUDMaskType maskType) {
        getInstance(context).setMaskType(maskType);
        getInstance(context).mSharedView.showInfoWithStatus(string);
        getInstance(context).svShow();
        getInstance(context).scheduleDismiss();
    }

    public static void showSuccessWithStatus(Context context,String string) {
        getInstance(context).setMaskType(SVProgressHUDMaskType.Black);
        getInstance(context).mSharedView.showSuccessWithStatus(string);
        getInstance(context).svShow();
        getInstance(context).scheduleDismiss();
    }

    public static void showSuccessWithStatus(Context context,String string, SVProgressHUDMaskType maskType) {
        getInstance(context).setMaskType(maskType);
        getInstance(context).mSharedView.showSuccessWithStatus(string);
        getInstance(context).svShow();
        getInstance(context).scheduleDismiss();
    }

    public static void showErrorWithStatus(Context context,String string) {
        getInstance(context).setMaskType(SVProgressHUDMaskType.Black);
        getInstance(context).mSharedView.showErrorWithStatus(string);
        getInstance(context).svShow();
        getInstance(context).scheduleDismiss();
    }

    public static void showErrorWithStatus(Context context,String string, SVProgressHUDMaskType maskType) {
        getInstance(context).setMaskType(maskType);
        getInstance(context).mSharedView.showErrorWithStatus(string);
        getInstance(context).svShow();
        getInstance(context).scheduleDismiss();
    }

    public static void showWithProgress(Context context,String string, SVProgressHUDMaskType maskType) {
        getInstance(context).setMaskType(maskType);
        getInstance(context).mSharedView.showWithProgress(string);
        getInstance(context).svShow();
    }

    public static SVCircleProgressBar getProgressBar(Context context){
        return getInstance(context).mSharedView.getCircleProgressBar();
    }
    public static void setText(Context context,String string){
        getInstance(context).mSharedView.setText(string);
    }

    private void setMaskType(SVProgressHUDMaskType maskType) {
        mSVProgressHUDMaskType = maskType;
        switch (mSVProgressHUDMaskType) {
            case None:
                configMaskType(android.R.color.transparent, false, false);
                break;
            case Clear:
                configMaskType(android.R.color.transparent, true, false);
                break;
            case ClearCancel:
                configMaskType(android.R.color.transparent, true, true);
                break;
            case Black:
                configMaskType(R.color.bgColor_overlay, true, false);
                break;
            case BlackCancel:
                configMaskType(R.color.bgColor_overlay, true, true);
                break;
            case Gradient:
                //TODO 设置半透明渐变背景
                configMaskType(R.drawable.bg_overlay_gradient, true, false);
                break;
            case GradientCancel:
                //TODO 设置半透明渐变背景
                configMaskType(R.drawable.bg_overlay_gradient, true, true);
                break;
            default:
                break;
        }
    }

    private void configMaskType(int bg, boolean clickable, boolean cancelable) {
        rootView.setBackgroundResource(bg);
        rootView.setClickable(clickable);
        setCancelable(cancelable);
    }

    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null;
    }

    public static boolean isShowing(Context context) {
        return getInstance(context).rootView.getParent() != null;
    }

    public static void dismiss(Context context) {
        getInstance(context).dismiss();
    }

    public void dismiss() {
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        mSharedView.startAnimation(outAnim);
    }

    public void dismissImmediately() {
        mSharedView.dismiss();
        rootView.removeView(mSharedView);
        decorView.removeView(rootView);
        context = null;
    }

    public Animation getInAnimation() {
        int res = SVProgressHUDAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        int res = SVProgressHUDAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    private void setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.sv_outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        } else {
            view.setOnTouchListener(null);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };

    private void scheduleDismiss() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0, DISMISSDELAYED);
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
                setCancelable(false);
            }
            return false;
        }
    };

    Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
