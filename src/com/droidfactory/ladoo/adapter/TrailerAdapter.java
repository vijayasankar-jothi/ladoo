package com.droidfactory.ladoo.adapter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.TrailersView;
import com.droidfactory.ladoo.fragment.MainFragment;
import com.droidfactory.ladoo.util.AndroidUtils;


public class TrailerAdapter extends ArrayAdapter<String> {

	private LayoutInflater inflater;

	private final List<String> jsonRows;
	private final Context mContext;


	public TrailerAdapter(TrailersView listFrag, int textViewResourceId, List<String> mListRows) {
		super(listFrag, textViewResourceId, mListRows);
		this.jsonRows = mListRows;
		this.mContext = listFrag;
		inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = setHolder(convertView, parent);
		}
		populateRowView(position, convertView);
		return convertView;
	}

	private static class Holder {
		public WebView webview;
		public TextView textView;
	}

	private View setHolder(View rowView, ViewGroup parent) {
		Holder holder = new Holder();
		rowView = inflater.inflate(R.layout.trailer_row, parent, false);
		holder.webview = (WebView) rowView.findViewById(R.id.webView);
		holder.textView = (TextView) rowView.findViewById(R.id.text);
		// Set tag
		rowView.setTag(holder);
		return rowView;
	}

	private void populateRowView(int position, View rowView) {
		try {
			loadRowElements(jsonRows.get(position), (Holder) rowView.getTag());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void loadRowElements(String jsonRow, Holder holder) throws JSONException {
		WebSettings settings = holder.webview.getSettings(); 
		settings.setDefaultTextEncodingName("utf-8");
		settings.setJavaScriptEnabled(true);
		settings.setLoadsImagesAutomatically(true);
		holder.webview.getSettings().setPluginState(PluginState.ON);
		holder.webview.setBackgroundColor(0x00000000);
		holder.webview.loadData("<iframe width='560' height='315' src='//www.youtube.com/embed/Gbdv1GzxAto' frameborder='0'></iframe>", "text/html", "utf-8");
//		holder.webview.setWebChromeClient(new WebChromeClient());
		holder.textView.setText("HERE HRER HERE");
	}




	public List<String> getAdapterList() {
		return this.jsonRows;
	}	

}