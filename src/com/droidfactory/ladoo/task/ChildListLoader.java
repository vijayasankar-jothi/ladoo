package com.droidfactory.ladoo.task;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.droidfactory.ladoo.database.Model;
import com.droidfactory.ladoo.object.ParentObject;

public class ChildListLoader extends AsyncTaskLoader<ParentObject> {

	private final Bundle bundle;
	private final Context mContext;

	public ChildListLoader(Context mContext, Bundle bundle) {
		super(mContext);
		this.mContext = mContext;
		this.bundle = bundle;
	}

	@Override
	public ParentObject loadInBackground() {
		long parent_id = bundle.getLong("parent_id");
		Model dataretriver = new Model(mContext);
		return dataretriver.getTasks(parent_id);
	}

	@Override
	public void deliverResult(ParentObject data) {
		if (data == null) {
			System.out.println("Null Data Received");
			return;
		}
		super.deliverResult(data);
	}

}
