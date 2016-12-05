package com.bigkoo.svprogresshud.view;

import com.bigkoo.svprogresshud.R;

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
 * Created by Sai on 15/8/15. 默认的SVProgress效果
 */
public class SVProgressDefaultView extends LinearLayout
{
	// private int resBigLoading = R.drawable.ic_svstatus_loading;
	private int resInfo = R.drawable.ic_svstatus_info;
	private int resSuccess = R.drawable.ic_svstatus_success;
	private int resError = R.drawable.ic_svstatus_error;
	private ImageView ivBigLoading, ivSmallLoading;
	private SVCircleProgressBar circleProgressBar;
	private TextView tvMsg;

	private MaterialProgressDrawable mBigProgress;
	private int[] colors =
	{ 0xFF7F7F7F };
	private final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
	private MaterialProgressDrawable mSmallProgress;

	public SVProgressDefaultView(Context context)
	{
		super(context);
		initViews();
		init();
	}

	private void initViews()
	{
		LayoutInflater.from(getContext())
				.inflate(R.layout.view_svprogressdefault, this, true);
		ivBigLoading = (ImageView) findViewById(R.id.ivBigLoading);
		ivSmallLoading = (ImageView) findViewById(R.id.ivSmallLoading);
		circleProgressBar = (SVCircleProgressBar) findViewById(R.id.circleProgressBar);
		tvMsg = (TextView) findViewById(R.id.tvMsg);
	}

	private void init()
	{
		// v4下md风格的ProgressDrawable
		mBigProgress = new MaterialProgressDrawable(getContext(), ivBigLoading);
		mBigProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
		// 圈圈颜色,可以是多种颜色
		mBigProgress.setColorSchemeColors(colors);
		// 设置圈圈的各种大小
		mBigProgress.updateSizes(MaterialProgressDrawable.LARGE);
		mBigProgress.setAlpha(255);
		mBigProgress.setStartEndTrim(0f, 0.8f);
		mBigProgress.setArrowScale(1f); // 0~1之间
		mBigProgress.setProgressRotation(1);
		mBigProgress.showArrow(false);

		mSmallProgress = new MaterialProgressDrawable(getContext(), ivSmallLoading);
		mSmallProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
		// 圈圈颜色,可以是多种颜色
		mSmallProgress.setColorSchemeColors(colors);
		// 设置圈圈的各种大小
		mSmallProgress.updateSizes(MaterialProgressDrawable.LARGE);
		mSmallProgress.setAlpha(255);
		mSmallProgress.setStartEndTrim(0f, 0.8f);
		mSmallProgress.setArrowScale(1f); // 0~1之间
		mSmallProgress.setProgressRotation(1);
		mSmallProgress.showArrow(false);
	}

	public void show()
	{
		clearAnimations();
		ivBigLoading.setVisibility(View.VISIBLE);
		ivBigLoading.setImageDrawable(mBigProgress);
		ivSmallLoading.setVisibility(View.GONE);
		circleProgressBar.setVisibility(View.GONE);
		tvMsg.setVisibility(View.GONE);
		// 开启旋转动画
		// ivBigLoading.startAnimation(mRotateAnimation);
		mBigProgress.stop();
		mBigProgress.start();
	}

	public void showWithStatus(String string)
	{
		if( string == null )
		{
			show();
			return;
		}
		showBaseStatus(string);
		// 开启旋转动画
		mSmallProgress.stop();
		mSmallProgress.start();
	}

	public void showInfoWithStatus(String string)
	{
		showBaseStatus(resInfo, string);
	}

	public void showSuccessWithStatus(String string)
	{
		showBaseStatus(resSuccess, string);
	}

	public void showErrorWithStatus(String string)
	{
		showBaseStatus(resError, string);
	}

	public void showWithProgress(String string)
	{
		showProgress(string);
	}

	public SVCircleProgressBar getCircleProgressBar()
	{
		return circleProgressBar;
	}

	public void setText(String string)
	{
		tvMsg.setText(string);
	}

	public void showProgress(String string)
	{
		clearAnimations();
		tvMsg.setText(string);
		ivBigLoading.setVisibility(View.GONE);
		ivSmallLoading.setVisibility(View.GONE);
		circleProgressBar.setVisibility(View.VISIBLE);
		tvMsg.setVisibility(View.VISIBLE);
	}

	public void showBaseStatus(String string)
	{
		clearAnimations();
		ivSmallLoading.setImageDrawable(mSmallProgress);
		tvMsg.setText(string);
		ivBigLoading.setVisibility(View.GONE);
		circleProgressBar.setVisibility(View.GONE);
		ivSmallLoading.setVisibility(View.VISIBLE);
		tvMsg.setVisibility(View.VISIBLE);
	}

	public void showBaseStatus(int res, String string)
	{
		clearAnimations();
		ivSmallLoading.setImageResource(res);
		tvMsg.setText(string);
		ivBigLoading.setVisibility(View.GONE);
		circleProgressBar.setVisibility(View.GONE);
		ivSmallLoading.setVisibility(View.VISIBLE);
		tvMsg.setVisibility(View.VISIBLE);
	}

	public void dismiss()
	{
		clearAnimations();
	}

	private void clearAnimations()
	{
		ivBigLoading.clearAnimation();
		ivSmallLoading.clearAnimation();
	}

}
