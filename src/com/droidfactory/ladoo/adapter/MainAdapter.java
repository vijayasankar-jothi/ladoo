package com.droidfactory.ladoo.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.object.ParentObject;
import com.droidfactory.ladoo.util.AndroidUtils;

public final class MainAdapter extends ArrayAdapter<Long> {

	ParentObject parent_obj;
	private boolean edit_mode = false;
	private ArrayList<String> child_name_array = new ArrayList<String>();
	private ArrayList<Long> child_time_array = new ArrayList<Long>();
	private ArrayList<Integer> child_status_array = new ArrayList<Integer>();
	private ArrayList<String> child_desc_array = new ArrayList<String>();
	private ArrayList<Integer> child_type_array = new ArrayList<Integer>();

	private HashMap<Long, Integer> mIdMap = new HashMap<Long, Integer>();

	public MainAdapter(Context mContext, ArrayList<Long> mTasksArray) {
		super(mContext, R.layout.main_row, mTasksArray);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null || edit_mode) {
			convertView = setViewIdToHolder(convertView, parent);
		}
		populateView(position, convertView);
		return convertView;
	}

	private View setViewIdToHolder(View rowView, ViewGroup parent) {
		rowView = ((Activity) this.getContext()).getLayoutInflater().inflate(R.layout.main_row, parent, false);
		TaskViewHolder holder = new TaskViewHolder();
		holder.task_name = (TextView) rowView.findViewById(R.id.task_name);
		holder.task_time = (TextView) rowView.findViewById(R.id.task_time);
		holder.main_relative_layout = (RelativeLayout) rowView.findViewById(R.id.task_main);

		setTypeFace(holder);
		rowView.setTag(holder);

		return rowView;
	}

	private void populateView(final int position, final View rowView) {
		final TaskViewHolder holder = (TaskViewHolder) rowView.getTag();
		holder.task_name.setText(child_name_array.get(position));
		final long task_time_in_millis = child_time_array.get(position);
		showNormalMode(holder, position, task_time_in_millis);

		mIdMap.put(this.getItem(position), position);
	}

	@Override
	public long getItemId(int position) {
		Long item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	class TaskViewHolder {
		TextView task_name;
		TextView task_time;
		RelativeLayout main_relative_layout;
	}

	private void setTypeFace(TaskViewHolder holder) {
		holder.task_name.setTypeface(AndroidUtils.get(getContext(), "Roboto-Condensed"));
		holder.task_time.setTypeface(AndroidUtils.get(getContext(), "Roboto-Light"));
	}

	private void showNormalMode(final TaskViewHolder holder, final int position, long task_time_in_millis) {
		holder.task_time.setText(getFormattedTime(task_time_in_millis));
	}

	public void loadNewData(ParentObject pObj) {
		this.parent_obj = pObj;
		this.clear();
		this.addAll(parent_obj.child_id_array);
		this.child_name_array = parent_obj.child_name_array;
		this.child_time_array = parent_obj.child_time_array;
		this.child_status_array = parent_obj.child_status_array;
		this.child_type_array = parent_obj.child_type_array;
		this.child_desc_array = parent_obj.child_desc_array;
	}

	public long getID(int position) {
		return this.getItem(position);
	}

	public String getName(long id) {
		return child_name_array.get(this.getPosition(id));
	}

	public long getTime(long id) {
		return child_time_array.get(this.getPosition(id));
	}

	public int getStatus(long id) {
		return child_status_array.get(this.getPosition(id));
	}

	public String getDesc(long id) {
		return child_desc_array.get(this.getPosition(id));
	}

	public void removeAnTask(int position) {
		Long item = getItem(position);
		int new_position = ((Long) getItemId(position)).intValue();
		this.remove(item);
		this.child_name_array.remove(new_position);
		this.child_time_array.remove(new_position);
		this.child_status_array.remove(new_position);
		this.child_type_array.remove(new_position);
		this.child_desc_array.remove(new_position);

	}
	
	public static String getFormattedTime(long future_time) {
		return android.text.format.DateFormat.format("MMM dd , kk:mm", new Date(future_time)).toString();
	}

}
