package com.droidfactory.ladoo.adapter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidfactory.ladoo.EventsFragment;
import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.fragment.MainFragment;


public class EventsAdapter extends ArrayAdapter<JSONObject> {

	private LayoutInflater inflater;

	private final List<JSONObject> jsonRows;
	private final Context mContext;


	public EventsAdapter(MainFragment listFrag, int textViewResourceId, List<JSONObject> mListRows) {
		super(listFrag.getActivity(), textViewResourceId, mListRows);
		this.jsonRows = mListRows;
		this.mContext = listFrag.getActivity();
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
		public TextView title;
		public TextView sub_title;
		public ImageView article_image;
	}

	private View setHolder(View rowView, ViewGroup parent) {
		Holder holder = new Holder();
		rowView = inflater.inflate(R.layout.events_list, parent, false);
		holder.title = (TextView) rowView.findViewById(R.id.title);
		holder.sub_title = (TextView) rowView.findViewById(R.id.sub_title);
		holder.article_image = (ImageView) rowView.findViewById(R.id.image);

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

	private void loadRowElements(JSONObject jsonRow, Holder holder) throws JSONException {
		holder.title.setText(jsonRow.getString("title"));
		holder.sub_title.setText(jsonRow.getString("sub_title"));
		holder.article_image.setImageResource(jsonRow.getInt("image_id"));
	}




	public List<JSONObject> getAdapterList() {
		return this.jsonRows;
	}	

}