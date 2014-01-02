package com.droidfactory.ladoo;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.droidfactory.ladoo.adapter.NewParentAdapter;

public class NewParent extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_view_pager);

		final int[] TAB_ID = { 0, 1, 2, 3, 4 };
		final String[] TAB_TITLE = { "Tab1", "Tab2", "Tab3", "Tab4", "Tab5" };
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		NewParentAdapter mNewTripPagerAdapter = new NewParentAdapter(getSupportFragmentManager(), TAB_ID, TAB_TITLE);
		final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mNewTripPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between pages, select the
				// corresponding tab.
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				// show the given tab
				mViewPager.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// hide the given tab
			}

			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// probably ignore this event
			}
		};

		// Add 3 tabs, specifying the tab's text and TabListener
		for (int i = 0; i < TAB_TITLE.length; i++) {
			actionBar.addTab(actionBar.newTab().setText(TAB_TITLE[i]).setTabListener(tabListener));
		}

	}

}
