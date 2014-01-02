package com.droidfactory.ladoo.fragment;

import java.sql.Timestamp;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.droidfactory.ladoo.MainActivity;
import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.database.Model;

public  class NewParentFragment extends Fragment {
	public static final String ARG_POSITION = "position";
	public static final String ARG_TRANSPORT_TYPE = "transport_type";
	private Calendar fromDate;
	private Calendar toDate;
	private String trip_name;
	private int type_of_transport;
	
	public View rootView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Default items
		type_of_transport = getArguments().getInt(ARG_TRANSPORT_TYPE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		rootView = inflater.inflate(R.layout.new_parent, container, false);

		fromDate = Calendar.getInstance();
		toDate = Calendar.getInstance();
		
		final DatePickerDialog.OnDateSetListener fromDatePickListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Button dateButton = (Button) rootView.findViewById(R.id.button1);
				dateButton.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
				fromDate.set(year, monthOfYear, dayOfMonth, fromDate.get(Calendar.HOUR_OF_DAY), fromDate.get(Calendar.MINUTE),
						fromDate.get(Calendar.SECOND));
			}
		};

		final DatePickerDialog.OnDateSetListener toDatePickListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Button dateButton = (Button) rootView.findViewById(R.id.button2);
				dateButton.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
				toDate.set(year, monthOfYear, dayOfMonth, toDate.get(Calendar.HOUR_OF_DAY), toDate.get(Calendar.MINUTE),
						toDate.get(Calendar.SECOND));
			}
		};
		
		rootView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new DatePickerDialog(getActivity(), fromDatePickListener, fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		rootView.findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new DatePickerDialog(getActivity(), toDatePickListener, toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH), toDate.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		
		rootView.findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String name = ((EditText) rootView.findViewById(R.id.editText1)).getText().toString();
				Model model = new Model(NewParentFragment.this.getActivity());
				model.addParent(name,"new year","other things",0, new Timestamp(fromDate.getTimeInMillis()), new Timestamp(toDate.getTimeInMillis()));
				
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
			}
		});
		
		return rootView;
	}

}
