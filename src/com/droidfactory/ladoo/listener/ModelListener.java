package com.droidfactory.ladoo.listener;


import android.support.v4.app.FragmentActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.droidfactory.ladoo.R;
import com.droidfactory.ladoo.fragment.MainFragment;

public class ModelListener implements AbsListView.MultiChoiceModeListener {
	FragmentActivity mActivity;
	ActionMode activeMode;
	ListView lv;
	MainFragment fragInst;

	public ModelListener(MainFragment fragInst, FragmentActivity mActivity, ListView lv) {
		this.mActivity = mActivity;
		this.lv = lv;
		this.fragInst = fragInst;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mActivity.getMenuInflater();
		inflater.inflate(R.menu.context_events, menu);

		activeMode = mode;
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// menu.removeItem(R.id.spam);
		return true;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		fragInst.performActions(lv, item);
		mode.finish();
		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		activeMode = null;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
		updateSubtitle(mode);
	}

	private void updateSubtitle(ActionMode mode) {
		mode.setTitle(lv.getCheckedItemCount() + " selected");
	}
}

