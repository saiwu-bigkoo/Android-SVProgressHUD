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

import com.bigkoo.svprogresshud.listener.OnDismissListener;
import com.bigkoo.svprogresshud.view.SVCircleProgressBar;
import com.bigkoo.svprogresshud.view.SVProgressDefaultView;

import java.lang.ref.WeakReference;

/**
 * Created by Sai on 15/8/15.
 */
public class SVProgressHUD {
    private WeakReference<Context> contextWeak;
    private static final long DISMISSDELAYED = 1000;
    private SVProgressHUDMaskType mSVProgressHUDMaskType;
    private boolean isShowing;
    private boolean isDismissing;

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
    private OnDismissListener onDismissListener;

    
    public SVProgressHUD(Context context){
        this.contextWeak = new WeakReference<>(context);
        gravity = Gravity.CENTER;
        initViews();
        initDefaultView();
        initAnimation();
    }

    protected void initViews() {
        Context context = contextWeak.get();
        if(context == null) return;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_svprogresshud, null, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }
    protected void initDefaultView(){
        Context context = contextWeak.get();
        if(context == null) return;

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
        isShowing = true;
        decorView.addView(rootView);
        if(mSharedView.getParent()!=null)((ViewGroup)mSharedView.getParent()).removeView(mSharedView);
        rootView.addView(mSharedView);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    private void svShow() {

        mHandler.removeCallbacksAndMessages(null);
        onAttached();

        mSharedView.startAnimation(inAnim);

    }

    public void show() {
        if(isShowing())return;
        setMaskType(SVProgressHUDMaskType.Black);
        mSharedView.show();
        svShow();
    }

    public void showWithMaskType(SVProgressHUDMaskType maskType) {
        if(isShowing())return;
        //判断maskType
        setMaskType(maskType);
        mSharedView.show();
        svShow();
    }

    public void showWithStatus(String string) {
        if(isShowing())return;
        setMaskType(SVProgressHUDMaskType.Black);
        mSharedView.showWithStatus(string);
        svShow();
    }

    public void showWithStatus(String string, SVProgressHUDMaskType maskType) {
        if(isShowing())return;
        setMaskType(maskType);
        mSharedView.showWithStatus(string);
        svShow();
    }

    public void showInfoWithStatus(String string) {
        if(isShowing())return;
        setMaskType(SVProgressHUDMaskType.Black);
        mSharedView.showInfoWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showInfoWithStatus(String string, SVProgressHUDMaskType maskType) {
        if(isShowing())return;
        setMaskType(maskType);
        mSharedView.showInfoWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showSuccessWithStatus(String string) {
        if(isShowing())return;
        setMaskType(SVProgressHUDMaskType.Black);
        mSharedView.showSuccessWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showSuccessWithStatus(String string, SVProgressHUDMaskType maskType) {
        if(isShowing())return;
        setMaskType(maskType);
        mSharedView.showSuccessWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showErrorWithStatus(String string) {
        if(isShowing())return;
        setMaskType(SVProgressHUDMaskType.Black);
        mSharedView.showErrorWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showErrorWithStatus(String string, SVProgressHUDMaskType maskType) {
        if(isShowing())return;
        setMaskType(maskType);
        mSharedView.showErrorWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showWithProgress(String string, SVProgressHUDMaskType maskType) {
        if(isShowing())return;
        setMaskType(maskType);
        mSharedView.showWithProgress(string);
        svShow();
    }

    public SVCircleProgressBar getProgressBar(){
        return mSharedView.getCircleProgressBar();
    }
    public void setText(String string){
        mSharedView.setText(string);
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
                configMaskType(R.drawable.bg_overlay_gradient, true, false);
                break;
            case GradientCancel:
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
        return rootView.getParent() != null || isShowing;
    }

    public void dismiss() {
        if(isDismissing)return;
        isDismissing = true;
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        mSharedView.dismiss();
        mSharedView.startAnimation(outAnim);
    }

    public void dismissImmediately() {
        mSharedView.dismiss();
        rootView.removeView(mSharedView);
        decorView.removeView(rootView);
        isShowing = false;
        isDismissing = false;
        if(onDismissListener != null){
            onDismissListener.onDismiss(this);
        }

    }

    public Animation getInAnimation() {
        Context context = contextWeak.get();
        if(context == null) return null;

        int res = SVProgressHUDAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        Context context = contextWeak.get();
        if(context == null) return null;

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

    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
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

    public void setOnDismissListener(OnDismissListener listener){
        this.onDismissListener = listener;
    }

    public OnDismissListener getOnDismissListener(){
        return onDismissListener;
    }

}
