package com.droidfactory.ladoo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.droidfactory.ladoo.adapter.GridAdapter;

public class GalleryActivity extends Activity {

	private int imageArray[] = { R.drawable.img_one, R.drawable.img_two,
			R.drawable.img_three, R.drawable.img_four, R.drawable.image_five,
			R.drawable.img_six, R.drawable.img_seven };

	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		GridView gridView = (GridView) findViewById(R.id.galleryGrid);
		GridAdapter gridadapter = new GridAdapter(this, imageArray);
		gridView.setAdapter(gridadapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent showIntent = new Intent(GalleryActivity.this,
						ImageViewActivity.class);
				startActivity(showIntent);
			}
		});
		getActionBar().setTitle("Gallery");
	}
}
