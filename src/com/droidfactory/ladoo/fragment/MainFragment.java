package com.droidfactory.ladoo.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.droidfactory.ladoo.DetailView;
import com.droidfactory.ladoo.MainActivity;
import com.droidfactory.ladoo.MainActivity.FragmentCommunicator;
import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.adapter.MainAdapter;
import com.droidfactory.ladoo.database.Model;
import com.droidfactory.ladoo.listener.ModelListener;
import com.droidfactory.ladoo.object.ParentObject;
import com.droidfactory.ladoo.task.ChildListLoader;

public class MainFragment extends ListFragment implements FragmentCommunicator, LoaderManager.LoaderCallbacks<ParentObject> {

	private ListView lv;
	public long parent_id = -1;
	public String parent_desc = null;
	private Context mContext;
	private EditText new_child_edit;
	private View new_child_save;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = super.onCreateView(inflater, container, savedInstanceState);
		ListView lv = (ListView) layout.findViewById(android.R.id.list);
		ViewGroup parent = (ViewGroup) lv.getParent();
		int lvIndex = parent.indexOfChild(lv);
		parent.removeViewAt(lvIndex);
		RelativeLayout mLinearLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_main, container, false);
		parent.addView(mLinearLayout, lvIndex, lv.getLayoutParams());
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mContext = this.getActivity();

		setHasOptionsMenu(true);
		((MainActivity) getActivity()).fragmentCommunicator = this;
		super.onActivityCreated(savedInstanceState);
		// Load Views
		initListView(this.getView());
		// Load data
		initAdapters();
		loadChildrens();
	}

	private void initListView(final View rootView) {
		lv = (ListView) rootView.findViewById(android.R.id.list);
		enableModal(lv);
		lv.setItemsCanFocus(true);
		lv.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.new_child, null));
		saveButtonView(this.getView());
		setListShown(false);
	}
	
	private void saveButtonView(final View rootView) {
		new_child_edit = ((EditText) rootView.findViewById(R.id.new_task_edit));
		new_child_save = rootView.findViewById(R.id.new_task_save);

		new_child_edit.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new_child_save.setVisibility(View.VISIBLE);
				return false;
			}
		});

		new_child_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String desc = new_child_edit.getText().toString();
				saveNewChild(desc, v);
			}
		});
	}

	private void saveNewChild(String desc, View save_button_view) {
		if (!desc.isEmpty()) {
			Model model = new Model(mContext);
			Calendar cal_instance = Calendar.getInstance();
			cal_instance.add(Calendar.MINUTE, 5);
			model.addChildrens(desc, parent_id, cal_instance.getTimeInMillis(), 0, 0, 0);
		}
		
		new_child_edit.setText("");
		new_child_save.setVisibility(View.GONE);
		loadChildrens();
	}

	private void enableModal(ListView lv) {
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lv.setMultiChoiceModeListener(new ModelListener(this, getActivity(), lv));
	}

	public void performActions(ListView lv, MenuItem item) {
		MainAdapter mainAdapter = ((MainAdapter) MainFragment.this.getListAdapter());
		SparseBooleanArray checked = lv.getCheckedItemPositions();
		switch (item.getItemId()) {
			default:
				break;
		}
	}

	private void initAdapters() {
		MainAdapter adapter = new MainAdapter(getActivity(), new ArrayList<Long>());
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void loadChildrens() {
		Bundle bundle = new Bundle();
		bundle.putLong("parent_id", parent_id);
		getLoaderManager().restartLoader(10000, bundle, this).forceLoad();
	}

	@Override
	public void passDataToFragment(boolean isDrawerOpen) {
		// TODO Auto-generated method stub
	}

	@Override
	public Loader<ParentObject> onCreateLoader(int arg0, Bundle arg1) {
		return new ChildListLoader(this.getActivity(), arg1);
	}

	@Override
	public void onLoadFinished(Loader<ParentObject> arg0, ParentObject result_trip_obj) {
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
		((MainAdapter) this.getListAdapter()).loadNewData(result_trip_obj);
	}

	@Override
	public void onLoaderReset(Loader<ParentObject> arg0) {
		// TODO Auto-generated method stub
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onListItemClick(ListView l, View v, int position, long id_what) {
		MainAdapter mainAdapter = ((MainAdapter) MainFragment.this.getListAdapter());
		Long id = mainAdapter.getID(--position);
		Intent intent = new Intent(getActivity(), DetailView.class);
		intent.putExtra("id", id);
		intent.putExtra("time", mainAdapter.getTime(id));
		intent.putExtra("name", mainAdapter.getName(id));
		intent.putExtra("status", mainAdapter.getStatus(id)); 

		getActivity().startActivity(intent);
		((Activity) mContext).overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
	}

	public void onDeleted() {
		loadChildrens();
	}

}