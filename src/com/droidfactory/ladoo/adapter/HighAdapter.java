package com.droidfactory.ladoo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HighAdapter extends PagerAdapter {

	Context mContext;
	int imageArray[];
	int ImageView = 102;
	int choice;

	public HighAdapter(Context act, int[] imgArra) {
		imageArray = imgArra;
		mContext = act;
	}

	public HighAdapter(Context act, int[] imgArra, int ch) {
		imageArray = imgArra;
		mContext = act;
		choice = ch;
	}

	public int getCount() {
		return imageArray.length;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(imageArray[position]);
//		if (choice == ImageView) {
//			imageView.setScaleType(ScaleType.FIT_XY);
//			imageView.setPadding(0, 150, 0, 0);
//			imageView.setLayoutParams(new LayoutParams(400, 600));
//			HorizontalScrollView hView = new HorizontalScrollView(mContext);
//			container.setVisibility(View.GONE);
//			hView.addView(imageView);
//			((ViewGroup) container.getParent()).addView(hView, 0);
//		} else {
			imageView.setScaleType(ScaleType.FIT_XY);
			((ViewPager) container).addView(imageView, 0);
		//
		// }
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup ssContainer, int ssPosition,
			Object ssObject) {
		((ViewPager) ssContainer).removeView((ImageView) ssObject);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

}
