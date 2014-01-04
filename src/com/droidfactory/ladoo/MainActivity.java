package com.droidfactory.ladoo;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);

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
		mMainLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mMainLayout.setScrimColor(this.getResources()
				.getColor(R.color.scrim_bg));
		setDrawerActionBarToggle();
	}

	private void setDrawerActionBarToggle() {
		final ActionBar mActionBar = MainActivity.this.getActionBar();

		mDrawerToggle = new ActionBarDrawerToggle(this, mMainLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
				mActionBar.setTitle(mainFragment.parent_desc);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();

				mActionBar.setTitle("Categories");
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
		DrawerAdapter drawer_adapter = new DrawerAdapter(this, parentObjArray);
		mDrawerList.setAdapter(drawer_adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerList.setItemChecked(positionOfSelectedParent, true);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 8: {
				Intent shareIntent;
				try {
					getPackageManager()
							.getPackageInfo("com.facebook.katana", 0);
					shareIntent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("fb://profile/325124554230666"));
				} catch (Exception e) {
					shareIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("https://www.facebook.com/overallsitecom"));
				}
				startActivity(shareIntent);
				break;
			}
			case 9: {
				Intent shareIntent;
				shareIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://twitter.com/overallsite_"));
				startActivity(shareIntent);
				break;
			}
			case 3: {
				Intent galleryIntent;
				galleryIntent = new Intent(getBaseContext(),
						GalleryActivity.class);
				startActivity(galleryIntent);
				break;

			} case 2: {
				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				MainActivity.this.startActivity(intent);
				break;

			}
			case 5: {
				Intent trailersIntent = new Intent(getBaseContext(),TrailersView.class);
				startActivity(trailersIntent);
				break;
				
			}
			default: {
				mainFragment = new MainFragment();
				mainFragment.parent_id = parentObjArray.get(position).parent_id;
				mainFragment.parent_desc = parentObjArray.get(position).parent_desc;
				replaceAFragment(R.id.fragment_container, mainFragment,
						"PARENT_TAG" + mainFragment.parent_id);

			}

			}
			mDrawerList.setItemChecked(position, true);
			setTitle(mainFragment.parent_desc);
			closeDrawer();
		}
	}

	public void addAFragment(int frag_container, Fragment aFragment, String tag) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(frag_container, aFragment, tag);
		transaction.commit();
		this.getSupportFragmentManager().executePendingTransactions();
	}

	public void replaceAFragment(int frag_container, Fragment aFragment,
			String tag) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
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
