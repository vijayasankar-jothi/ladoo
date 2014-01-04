package com.droidfactory.ladoo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.support.v4.view.ViewPager;

public class HighAdapter extends PagerAdapter {

	Context mContext;
	int imageArray[];

	public HighAdapter(Context act, int[] imgArra) {
		imageArray = imgArra;
		mContext = act;
	}

	public int getCount() {
		return imageArray.length;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(mContext);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imageView.setImageResource(imageArray[position]);
		imageView.setScaleType(ScaleType.FIT_XY);
		((ViewPager) container).addView(imageView, 0);
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
