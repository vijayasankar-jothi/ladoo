package com.droidfactory.ladoo;

import com.droidfactory.ladoo.adapter.HighAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class GalleryActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		Bundle extras = getIntent().getExtras();
	}

	private int imageArray[] = { R.drawable.img_one, R.drawable.img_two,
			R.drawable.img_three, R.drawable.img_four };

	private void initGallery() {
		ViewPager mViewPager;
		mViewPager = (ViewPager) findViewById(R.id.pager);
		HighAdapter pAdapter = new HighAdapter(getBaseContext(), imageArray);
	}
}
