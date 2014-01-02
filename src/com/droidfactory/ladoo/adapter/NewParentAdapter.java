package com.droidfactory.ladoo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.droidfactory.ladoo.fragment.NewParentFragment;

public class NewParentAdapter extends FragmentStatePagerAdapter {
	
	private final int total_items;
	private final String[] tab_titles;
	private final int[] tab_ids;

	public NewParentAdapter(FragmentManager fm,int[] tab_ids,String[] tab_titles) {
		super(fm);
		this.total_items=tab_ids.length;
		this.tab_ids=tab_ids;
		this.tab_titles=tab_titles;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new NewParentFragment();
		Bundle args = new Bundle();
		args.putInt(NewParentFragment.ARG_POSITION, i);
		args.putInt(NewParentFragment.ARG_TRANSPORT_TYPE, tab_ids[i]);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return total_items;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tab_titles[position];
	}

}
