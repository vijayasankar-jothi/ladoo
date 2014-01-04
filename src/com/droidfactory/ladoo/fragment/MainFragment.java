package com.droidfactory.ladoo.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.droidfactory.ladoo.DetailView;
import com.droidfactory.ladoo.MainActivity;
import com.droidfactory.ladoo.MainActivity.FragmentCommunicator;
import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.SwipeDismissListViewTouchListener;
import com.droidfactory.ladoo.adapter.EventsAdapter;
import com.droidfactory.ladoo.adapter.HighAdapter;
import com.droidfactory.ladoo.adapter.MainAdapter;
import com.droidfactory.ladoo.database.Model;
import com.droidfactory.ladoo.listener.ModelListener;
import com.droidfactory.ladoo.object.ParentObject;
import com.droidfactory.ladoo.task.ChildListLoader;
import com.haarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;
import com.viewpagerindicator.LinePageIndicator;

public class MainFragment extends ListFragment implements FragmentCommunicator, LoaderManager.LoaderCallbacks<ParentObject> {

	private ListView lv;
	public int parent_id = -1;
	public String parent_desc = null;
	private Context mContext;
	private EditText new_child_edit;
	private View new_child_save;
	private LinkedList<JSONObject> rows;
	String[] titles = { "", "3 Days to Kill", "Kaley Cuoco", "Welcome to Yesterday", "Academy Awards", "Best Film Trailers of All Times", "Do Movie Critics Play a Significant..", "Lady Gaga" };
	String[] sub_titles = { "", "A dying Secret Service agent decides to retire..", "looks sporty after leaving a gym in Sherman Oaks", "A teenaged underdog gets accepted to MIT, only to..",
			"It is always a sense of pride, power and fame when..", "In this list one of the best trailers of all times..", "The most commonly asked question yet very important..",
			"Way to London Heathrow Airport (10 Images)" };

	ArrayList TAB_ID = new ArrayList();
	ArrayList TAB_TITLE = new ArrayList();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = super.onCreateView(inflater, container, savedInstanceState);
		lv = (ListView) layout.findViewById(android.R.id.list);

		ViewGroup parent = (ViewGroup) lv.getParent();
		int lvIndex = parent.indexOfChild(lv);
		parent.removeViewAt(lvIndex);
		RelativeLayout mLinearLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_main, container, false);
		parent.addView(mLinearLayout, lvIndex, lv.getLayoutParams());
		rows = new LinkedList<JSONObject>();
		ArrayList<Integer> images = new ArrayList<Integer>();
		images.add(R.drawable.img_five);
		images.add(R.drawable.img_one);
		images.add(R.drawable.img_two);
		images.add(R.drawable.img_three);
		images.add(R.drawable.img_four);
		images.add(R.drawable.img_five);
		images.add(R.drawable.img_six);
		images.add(R.drawable.img_seven);
		for (int i = 1; i <= 7; i++) {
			JSONObject dummyObj = new JSONObject();
			try {
				dummyObj.put("title", titles[i]);
				dummyObj.put("sub_title", sub_titles[i]);
				dummyObj.put("image_id", images.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rows.add(dummyObj);
		}

		return layout;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = ((Activity) mContext).getMenuInflater();
		inflater.inflate(R.menu.context_events, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		long long_val = info.id + 1;// plus one for gingerbread
		int int_val = (int) long_val;
		lv.setItemChecked(int_val, true);
		List<JSONObject> readList = new LinkedList<JSONObject>(); 
		SparseBooleanArray checked = getListView().getCheckedItemPositions();
		for (int i = 0; i < checked.size(); i++) {
			if (checked.valueAt(i)) {
				int position = checked.keyAt(i);
				JSONObject getdata = rows.get(position);
				rows.remove(position);
				readList.add(getdata);
			}
		}
		return true;
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
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(lv, new SwipeDismissListViewTouchListener.DismissCallbacks() {
			@Override
			public boolean canDismiss(int position) {
				return true;
			}

			@Override
			public void onDismiss(ListView listView, int[] reverseSortedPositions) {
				for (int position : reverseSortedPositions) {
					rows.remove(position - 1);
				}
				adapter.notifyDataSetChanged();
			}
		});
		lv.setOnTouchListener(touchListener);
		lv.setOnScrollListener(touchListener.makeScrollListener());
	}

	private void initListView(final View rootView) {
		lv = (ListView) rootView.findViewById(android.R.id.list);
		enableModal(lv);
		lv.setItemsCanFocus(true);
		lv.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.new_child, null));
		viewPagerView(this.getView());
		setListShown(false);
	}

	private int imageArray[] = { R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four };
	private EventsAdapter adapter;
	private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	private AnimateDismissAdapter mAnimateDismissAdapter;

	private void viewPagerView(final View rootView) {
		ViewPager mViewPager;
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		HighAdapter pAdapter = new HighAdapter(mContext, imageArray);
		mViewPager.setAdapter(pAdapter);
		LinePageIndicator circleIndicator = (LinePageIndicator) rootView.findViewById(R.id.indicator);
		circleIndicator.setViewPager(mViewPager);
		mViewPager.setCurrentItem(0);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		menu.setGroupVisible(R.id.group_news_updates, false);
		menu.setGroupVisible(R.id.group_events, false);
		menu.setGroupVisible(R.id.group_photos, false);
		menu.setGroupVisible(R.id.group_reviews, false);

		if (parent_id == 0) { // News updates
			menu.setGroupVisible(R.id.group_news_updates, true);
		} else if (parent_id == 1) { // Events
			menu.setGroupVisible(R.id.group_events, true);
		} else if (parent_id == 2) { // Photos
			menu.setGroupVisible(R.id.group_photos, true);
		} else if (parent_id == 6) { // Reviews
			menu.setGroupVisible(R.id.group_reviews, true);
		}

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
		final List<JSONObject> readList = new LinkedList<JSONObject>(); 

		SparseBooleanArray checked = getListView().getCheckedItemPositions();
		List<Integer> selected = new LinkedList<Integer>();
		for (int i = 0; i < checked.size(); i++) {
			if (checked.valueAt(i)) {
				int position = checked.keyAt(i);
				JSONObject getdata = adapter.getItem(position-1);
				selected.add(position - 1);
				readList.add(getdata);
			}
		}
		for(int j=0;j< readList.size();j++){
		final int k =j;
		final Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        adapter.getView(selected.get(k), null, lv).startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
 
            public void run() {
            	adapter.remove(readList.get(k));
            }
        }, 1000);
		}
	}

	private void initAdapters() {
		adapter = new EventsAdapter(this, R.layout.events_list, rows);
		SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);
		// SwingRightInAnimationAdapter swingRightInAnimationAdapter = new
		// SwingRightInAnimationAdapter(adapter);
		swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(swingRightInAnimationAdapter);
		//
		// swingBottomInAnimationAdapter.setAbsListView(getListView());
		// Assign the ListView to the AnimationAdapter and vice versa
		swingBottomInAnimationAdapter.setAbsListView(lv);
		setListAdapter(swingBottomInAnimationAdapter);
		

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
		Intent intent = new Intent(getActivity(), DetailView.class);
		intent.putExtra("position", position);
		
		if (isJellyBeanCombCompatible()) {
			Bundle scaleBundle = ActivityOptions.makeScaleUpAnimation(v, 30, 30, v.getWidth(), v.getHeight()).toBundle();
			getActivity().startActivity(intent, scaleBundle);
		} else {
			getActivity().startActivity(intent);
			((Activity) mContext).overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
		}
		
		
		((Activity) mContext).overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
	}

	public void onDeleted() {
		loadChildrens();
	}
	
	public static boolean isJellyBeanCombCompatible() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

}