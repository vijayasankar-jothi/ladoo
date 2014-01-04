package com.droidfactory.ladoo;

import java.util.LinkedList;
import java.util.List;

import com.droidfactory.ladoo.adapter.TrailerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;

public class TrailersView extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailers_view);
		List<String> urls = new LinkedList<String>();
		urls.add("<iframe width='560' height='315' src='//www.youtube.com/embed/Gbdv1GzxAto' frameborder='0' allowfullscreen></iframe>");
		TrailerAdapter adapter = new TrailerAdapter(this,R.layout.trailer_row,urls);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trailers_view, menu);
		return true;
	}
	
	

}
