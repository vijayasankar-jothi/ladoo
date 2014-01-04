package com.droidfactory.ladoo.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.droidfactory.ladoo.DetailView;
import com.droidfactory.ladoo.MainActivity;
import com.droidfactory.ladoo.MainActivity.FragmentCommunicator;
import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.SwipeDismissListViewTouchListener;
import com.droidfactory.ladoo.adapter.EventsAdapter;
import com.droidfactory.ladoo.adapter.HighAdapter;
import com.droidfactory.ladoo.adapter.MainAdapter;
import com.droidfactory.ladoo.adapter.NewParentAdapter;
import com.droidfactory.ladoo.database.Model;
import com.droidfactory.ladoo.listener.ModelListener;
import com.droidfactory.ladoo.object.ParentObject;
import com.droidfactory.ladoo.task.ChildListLoader;
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
		ListView lv = (ListView) layout.findViewById(android.R.id.list);

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

//		boolean showPager = false;
//		TAB_ID.clear();
//		TAB_TITLE.clear();
//
//		if (parent_id == 0) {
//			showPager = true;
//			TAB_ID.add("101");
//			TAB_ID.add("102");
//			TAB_ID.add("103");
//
//			TAB_TITLE.add("Movie");
//			TAB_TITLE.add("Celebrity");
//			TAB_TITLE.add("Game");
//		} else if (parent_id == 1) {
//			showPager = true;
//			TAB_ID.add("201");
//			TAB_ID.add("202");
//			TAB_ID.add("203");
//
//			TAB_TITLE.add("Movie Premieres");
//			TAB_TITLE.add("Oscars");
//			TAB_TITLE.add("Cannes");
//		} else if (parent_id == 2) {
//			showPager = true;
//			TAB_ID.add("301");
//			TAB_ID.add("302");
//			TAB_ID.add("303");
//
//			TAB_TITLE.add("Celebrity");
//			TAB_TITLE.add("Paparazzi");
//			TAB_TITLE.add("Stills");
//		} else if (parent_id == 7) {
//			showPager = true;
//			TAB_ID.add("401");
//			TAB_ID.add("402");
//
//			TAB_TITLE.add("Movie Reviews");
//			TAB_TITLE.add("Games Reviews");
//		}
//
//		if (showPager) {
//			final ActionBar actionBar = this.getActivity().getActionBar();
//			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//			NewParentAdapter mNewTripPagerAdapter = new NewParentAdapter(this.getActivity().getSupportFragmentManager(), TAB_ID, TAB_TITLE);
//			final ViewPager mViewPager = (ViewPager) ((Activity) this.getActivity()).findViewById(R.id.pager);
//			mViewPager.setAdapter(mNewTripPagerAdapter);
//
//			mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//				@Override
//				public void onPageSelected(int position) {
//					// When swiping between pages, select the
//					// corresponding tab.
//					MainFragment.this.getActivity().getActionBar().setSelectedNavigationItem(position);
//				}
//			});
//
//			// Create a tab listener that is called when the user changes tabs.
//			ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//
//				@Override
//				public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
//					// TODO Auto-generated method stub
//					int position = tab.getPosition();
//					mViewPager.setCurrentItem(position);
//
//				}
//
//				@Override
//				public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
//					// TODO Auto-generated method stub
//
//				}
//			};
//
//			// Add 3 tabs, specifying the tab's text and TabListener
//			for (int i = 0; i < TAB_TITLE.size(); i++) {
//				actionBar.addTab(actionBar.newTab().setText((String) TAB_TITLE.get(i)).setTabListener(tabListener));
//			}
//		}

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
		MainAdapter mainAdapter = ((MainAdapter) MainFragment.this.getListAdapter());
		SparseBooleanArray checked = lv.getCheckedItemPositions();
		switch (item.getItemId()) {
			default:
				break;
		}
	}

	private void initAdapters() {
		adapter = new EventsAdapter(this, R.layout.events_list, rows);
		SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);
		// SwingRightInAnimationAdapter swingRightInAnimationAdapter = new
		// SwingRightInAnimationAdapter(adapter);
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(swingRightInAnimationAdapter);
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
		getActivity().startActivity(intent);
		((Activity) mContext).overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
	}

	public void onDeleted() {
		loadChildrens();
	}

}