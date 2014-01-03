package com.droidfactory.ladoo;


import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.droidfactory.ladoo.adapter.EventsAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventsFragment extends ListFragment {

	private EventsAdapter mAdapter;
	List<JSONObject> rows;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rows = new LinkedList<JSONObject>();
		for(int i=1;i<=10;i++){
			JSONObject dummyObj = new JSONObject();
			try {
				dummyObj.put("title", "Title " + i);
				dummyObj.put("sub_title", "Sub Title " + i);
				dummyObj.put("image", i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rows.add(dummyObj);
		}
	}

	/**
	 * This will return list view
	 * */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = super.onCreateView(inflater, container, savedInstanceState);
		ListView lv = (ListView) layout.findViewById(android.R.id.list);
		ViewGroup parent = (ViewGroup) lv.getParent();
		int lvIndex = parent.indexOfChild(lv);
		parent.removeViewAt(lvIndex);
		RelativeLayout mLinearLayout = (RelativeLayout) inflater.inflate(R.layout.activity_events, container, false);
		parent.addView(mLinearLayout, lvIndex, lv.getLayoutParams());
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
//		mAdapter = new EventsAdapter(this, R.layout.events_list, rows);
//		setListAdapter(mAdapter);
	}

}
