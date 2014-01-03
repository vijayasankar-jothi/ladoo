package com.droidfactory.ladoo;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.droidfactory.ladoo.adapter.DrawerAdapter;
import com.droidfactory.ladoo.database.Model;
import com.droidfactory.ladoo.fragment.MainFragment;
import com.droidfactory.ladoo.object.ParentObject;

public class MainActivity extends FragmentActivity {

	private ListView mDrawerList;
	private DrawerLayout mMainLayout;
	private RelativeLayout mDrawerMainLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ArrayList<ParentObject> parentObjArray;
	public FragmentCommunicator fragmentCommunicator;
	private MainFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enableDrawer();

		if (savedInstanceState == null) {
			initFirstTimeLoader();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// fragmentCommunicator not allowed to call when mPager is active
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		Model model = new Model(this);
		switch (item.getItemId()) {
			case R.id.movie:
				Intent intent = new Intent(this, NewParent.class);
				startActivity(intent);
				break;
			case R.id.celebrity:
				model.deleteAParent(this, 1);
				break;
			case R.id.game:
				model.deleteAParent(this, 1);
				break;
			case R.id.movie_premiere:
				model.deleteAParent(this, 1);
				break;
			case R.id.oscars:
				model.deleteAParent(this, 1);
				break;
			case R.id.cannes:
				model.deleteAParent(this, 1);
				break;
			case R.id.sandiego:
				model.deleteAParent(this, 1);
				break;
			case R.id.clebrity_images:
				model.deleteAParent(this, 1);
				break;
			case R.id.paparazzi:
				model.deleteAParent(this, 1);
				break;
			case R.id.tv_serial:
				model.deleteAParent(this, 1);
				break;
			case R.id.movie_review:
				model.deleteAParent(this, 1);
				break;
			case R.id.game_review:
				model.deleteAParent(this, 1);
				break;

		}
		return true;
	}

	private void enableDrawer() {
		mMainLayout = (DrawerLayout) findViewById(R.id.main_layout_with_drawer);
		mDrawerMainLayout = (RelativeLayout) findViewById(R.id.main_drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_list);
		mMainLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mMainLayout.setScrimColor(this.getResources().getColor(R.color.item));
		setDrawerActionBarToggle();
	}

	private void setDrawerActionBarToggle() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mMainLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mMainLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void initFirstTimeLoader() {
		Model model = new Model(this);
		parentObjArray = model.getCategoriesObjectArray();
		int size_of_parent = parentObjArray.size();
		int positionOfSelectedParent = 0;
		if (size_of_parent == 0) {
			System.out.println("No parents to load");
			if (mainFragment != null) {
				mainFragment.setListShownNoAnimation(true);
				findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
			}
			return;
		}
		drawDefaultListOfElements(positionOfSelectedParent);
		mainFragment = new MainFragment();
		mainFragment.parent_id = parentObjArray.get(positionOfSelectedParent).parent_id;
		mainFragment.parent_desc = parentObjArray.get(positionOfSelectedParent).parent_desc;
		addAFragment(R.id.fragment_container, mainFragment, "PAERNT_TAG");
		setTitle(mainFragment.parent_desc);
	}

	private void drawDefaultListOfElements(int positionOfSelectedParent) {
		
		mDrawerList.setAdapter(new DrawerAdapter(this, parentObjArray));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerList.setItemChecked(positionOfSelectedParent, true);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mainFragment = new MainFragment();
			mainFragment.parent_id = parentObjArray.get(position).parent_id;
			mainFragment.parent_desc = parentObjArray.get(position).parent_desc;
			replaceAFragment(R.id.fragment_container, mainFragment, "PARENT_TAG" + mainFragment.parent_id);

			mDrawerList.setItemChecked(position, true);
			setTitle(mainFragment.parent_desc);
			closeDrawer();
		}
	}

	public void addAFragment(int frag_container, Fragment aFragment, String tag) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(frag_container, aFragment, tag);
		transaction.commit();
		this.getSupportFragmentManager().executePendingTransactions();
	}

	public void replaceAFragment(int frag_container, Fragment aFragment, String tag) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		// transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
		transaction.replace(frag_container, aFragment, tag);
		// transaction.addToBackStack(null); //Don't enable this unless you need
		transaction.commit();
	}

	public interface FragmentCommunicator {
		public void passDataToFragment(boolean isDrawerOpen);
	}

	public void closeDrawer() {
		mMainLayout.closeDrawer(mDrawerMainLayout);
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
