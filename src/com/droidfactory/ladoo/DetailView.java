package com.droidfactory.ladoo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailView extends Activity {
	
	String name ="";
	Long time =0l;
	String current_loc ="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		name = getIntent().getStringExtra("name");
		time = getIntent().getLongExtra("time",0);
		
		ViewHolder vh = new ViewHolder();
		setViewHolder(vh);
		populateView(vh);
	}
	
	private void populateView(ViewHolder vh) {
		vh.txt1.setText(name);
		vh.txt2.setText(time+"");
		vh.txt3.setText(current_loc);
	}
	
	private void setViewHolder(ViewHolder vh) {
		vh.txt1 = (TextView) findViewById(R.id.textView1);
		vh.txt2 = (TextView) findViewById(R.id.textView2);
		vh.txt3 = (TextView) findViewById(R.id.textView3);
	}
	class ViewHolder {
		TextView txt1;
		TextView txt2;
		TextView txt3;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slideinright, R.anim.slideoutright);
	}

}
