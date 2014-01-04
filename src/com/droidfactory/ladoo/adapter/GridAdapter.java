package com.droidfactory.ladoo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {

	Context mContext;
	int imageIds[];

	public GridAdapter(Context act, int[] imgArra) {
		imageIds = imgArra;
		mContext = act;
	}

	public int getCount() {
		return imageIds.length;
	}

	public Object getItem(int position) {
		return imageIds[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		ImageView iview;
		if (view == null) {
			iview = new ImageView(mContext);
			iview.setLayoutParams(new GridView.LayoutParams(150, 200));
			iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iview.setPadding(5, 5, 5, 5);
		} else {
			iview = (ImageView) view;
		}
		iview.setImageResource(imageIds[position]);
		return iview;
	}
}