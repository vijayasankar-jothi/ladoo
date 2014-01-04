package com.droidfactory.ladoo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.object.ParentObject;

public class DrawerAdapter extends ArrayAdapter<ParentObject> { 
	
	int headerIcons[] = {R.drawable.ic_file_icon,R.drawable.ic_belllow,R.drawable.ic_camerahigh,R.drawable.ic_gallery,R.drawable.ic_crown,R.drawable.ic_old_camcorder,R.drawable.ic_cherrieshigh,R.drawable.ic_ic_celebs,R.drawable.ic_fb_small,R.drawable.ic_tweet_small};

	public DrawerAdapter(Context mContext, ArrayList<ParentObject> mTripsArray) {
		super(mContext, R.layout.drawer_row, mTripsArray);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = setViewIdToHolder(convertView, parent);
		}
		populateView(position, convertView);
		return convertView;
	}
	
	private View setViewIdToHolder(View rowView, ViewGroup parent) {
		rowView = ((Activity)this.getContext()).getLayoutInflater().inflate(R.layout.drawer_row, parent, false);
		TaskViewHolder holder = new TaskViewHolder();
		holder.parent_name = (TextView) rowView.findViewById(R.id.parent_name);
		rowView.setTag(holder);
		return rowView;
	}
	
	private void populateView(int position, View rowView) {
		TaskViewHolder holder = (TaskViewHolder) rowView.getTag();
		holder.parent_name.setText(this.getItem(position).parent_name);
		holder.parent_name.setCompoundDrawablesWithIntrinsicBounds(0,headerIcons[position], 0, 0);
	}
	
	class TaskViewHolder {
		TextView parent_name;
	}
}

