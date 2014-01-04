package com.droidfactory.ladoo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class ImageViewActivity extends Activity {

	ViewPager viewPager;
	Context mContext;

	private int imageArray[] = { R.drawable.img_three, R.drawable.img_one,
			R.drawable.img_two, R.drawable.img_four, R.drawable.image_five,
			R.drawable.img_six, R.drawable.img_seven };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flipper);
		mContext = getBaseContext();
		HorizontalScrollView hView = new HorizontalScrollView(mContext);
		LinearLayout lLayout = new LinearLayout(mContext);
		for (int i = 0; i < imageArray.length; i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(imageArray[i]);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setPadding(10, 200, 10, 0);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					500, 600);
			imageView.setLayoutParams(layoutParams);
			lLayout.addView(imageView);
			getActionBar().setTitle("Gallery");
		}
		hView.addView(lLayout);
		LinearLayout base_layout = (LinearLayout) findViewById(R.id.base_layout);
		base_layout.addView(hView);
	}
}
