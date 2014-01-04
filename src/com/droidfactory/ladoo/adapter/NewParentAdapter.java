package com.droidfactory.ladoo.adapter;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.droidfactory.ladoo.fragment.NewParentFragment;

public class NewParentAdapter extends FragmentStatePagerAdapter {
	
	private final int total_items;
	private final ArrayList tab_titles;
	private final ArrayList tab_ids;

	public NewParentAdapter(FragmentManager fm,ArrayList tab_ids,ArrayList tab_titles) {
		super(fm);
		this.total_items=tab_ids.size();
		this.tab_ids=tab_ids;
		this.tab_titles=tab_titles;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new NewParentFragment();
		Bundle args = new Bundle();
		args.putInt(NewParentFragment.ARG_POSITION, i);
		args.putString(NewParentFragment.ARG_TRANSPORT_TYPE, (String)tab_ids.get(i));
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return total_items;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return (String)tab_titles.get(position);
	}

}
