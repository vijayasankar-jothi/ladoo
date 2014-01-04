package com.droidfactory.ladoo.database;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.droidfactory.ladoo.object.ParentObject;

public class Model {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	// Forming DataBase Basics
	private SQLiteDatabase db;
	private Schema schema;

	public Model(Context context) {
		schema = new Schema(context);
	}

	public SQLiteDatabase getSQLiteDb() {
		return schema.getWritableDatabase();
	}

	public void open() throws SQLException {
		db = getSQLiteDb();
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys = ON;");
		}
	}

	public void close() {
		schema.close();
	}

	// CREATE
	public long addParent(String name, String desc, String other, int type, Timestamp fromTime, Timestamp toTime) {
		open();
		ContentValues values = new ContentValues();
		values.put(Schema.COLUMN_PARENT_NAME, name);
		values.put(Schema.COLUMN_PARENT_DESC, desc);
		values.put(Schema.COLUMN_PARENT_OTHER, other);
		values.put(Schema.COLUMN_PARENT_TYPE, type);
		values.put(Schema.COLUMN_FROM_TIME, dateFormat.format(fromTime));
		values.put(Schema.COLUMN_TO_TIME, dateFormat.format(toTime));
		Long insertId = db.insert(Schema.TABLE_PARENTS, null, values);
		close();
		return insertId;
	}

	public long addChildrens(String name, long parent_id, long time, int status, int type, int children_type) {
		open();
		Long insertId;
		ContentValues values = new ContentValues();
		values.put(Schema.COLUMN_CHILD_NAME, name);
		values.put(Schema.COLUMN_CHILD_DESC, "");
		values.put(Schema.COLUMN_ID, parent_id);
		values.put(Schema.COLUMN_STATUS, status);
		values.put(Schema.COLUMN_TYPE, type);
		values.put(Schema.COLUMN_CHILD_TYPE, children_type);
		values.put(Schema.COLUMN_TIME, dateFormat.format(new Timestamp(time)));

		try {
			db.beginTransaction();
			insertId = db.insert(Schema.TABLE_CHILDREN, null, values);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		close();
		return insertId;
	}

	// UPDATE
	public void updateTime(Timestamp currenttime, long childID) {
		open();
		ContentValues args = new ContentValues();
		args.put(Schema.COLUMN_TIME, dateFormat.format(currenttime));
		String whereClause = Schema.COLUMN_CHILD_ID + " = " + childID;
		db.update(Schema.TABLE_CHILDREN, args, whereClause, null);
		close();
	}

	public long updateStatusAndTime(Timestamp currenttime, int status, long childID) {
		long respondId;
		open();
		ContentValues args = new ContentValues();
		args.put(Schema.COLUMN_STATUS, status);
		args.put(Schema.COLUMN_TIME, dateFormat.format(currenttime));
		String whereClause = Schema.COLUMN_CHILD_ID + " = " + childID;
		respondId = db.update(Schema.TABLE_CHILDREN, args, whereClause, null);
		close();
		return respondId;
	}

	public long updateBulkStatus(int status, ArrayList<Long> childIdList) {
		long respondId;
		open();
		String childListStr = "";
		for (long child_id : childIdList) {
			childListStr = childListStr + child_id + ",";
		}
		childListStr = childListStr.substring(0, childListStr.lastIndexOf(","));

		ContentValues args = new ContentValues();
		args.put(Schema.COLUMN_STATUS, status);
		String whereClause = Schema.COLUMN_CHILD_ID + " IN (" + childListStr + ")";
		respondId = db.update(Schema.TABLE_CHILDREN, args, whereClause, null);
		close();
		return respondId;
	}

	// DELETE
	public long deleteAnItem(String table, String condition) {
		open();
		long delete_id = db.delete(table, condition, null);
		close();
		return delete_id;
	}

	public long deleteAParent(Context mContext, long parent_id) {
		open();
		long delete_id = db.delete(Schema.TABLE_PARENTS, Schema.COLUMN_PARENT_ID + "=" + parent_id, null);
		close();
		return delete_id;
	}

	public void deleteAllParents() {
		open();
		db.delete(Schema.TABLE_PARENTS, null, null);
		close();
	}

	// SHOW
	public ArrayList<ParentObject> getParents() {
		open();
		Cursor cursor = db.rawQuery("SELECT " + Schema.COLUMN_PARENT_ID + "," + Schema.COLUMN_PARENT_NAME + "," + Schema.COLUMN_PARENT_DESC + " FROM " + Schema.TABLE_PARENTS, null);
		return getParentObjectArray(cursor);
	}

	public ParentObject getTasks(long parent_id) {
		open();
		ParentObject tripObj = new ParentObject();
		Cursor cursor = db.rawQuery("SELECT " + Schema.COLUMN_CHILD_ID + "," + Schema.COLUMN_CHILD_NAME + "," + Schema.COLUMN_CHILD_DESC + "," + Schema.COLUMN_TIME + "," + Schema.COLUMN_STATUS + ","
				+ Schema.COLUMN_TYPE + " FROM " + Schema.TABLE_CHILDREN + " where " + Schema.COLUMN_ID + "=" + parent_id + " order by " + Schema.COLUMN_TIME + " desc", null);
		return getChildObjectArray(tripObj, cursor);
	}

	// SCOPE
	public int getChildrensCount(int status) {
		open();
		Cursor cursor = db.rawQuery("SELECT count(*) FROM " + Schema.TABLE_CHILDREN + " where " + Schema.COLUMN_STATUS + "=" + status, null);
		return getCount(cursor);
	}

	private ArrayList<ParentObject> getParentObjectArray(Cursor cursor) {
		ArrayList<ParentObject> parentsArray = new ArrayList<ParentObject>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ParentObject pObj = new ParentObject();
			pObj.parent_id = cursor.getInt(0);
			pObj.parent_name = cursor.getString(1);
			pObj.parent_desc = cursor.getString(2);
			parentsArray.add(pObj);
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return parentsArray;
	}
	
	public ArrayList<ParentObject> getCategoriesObjectArray() {
		int i = 0;
		ArrayList<ParentObject> parentsArray = new ArrayList<ParentObject>();
		String[] categories_in_drawer = {"News Updates","Events","One-minute","Galleries","Top Celebs","Trailers","Reviews","Natural Beauties","Like Us", "Follow Us"};
		String[] categories_in_title = {"News Updates","Events","One-minute","Galleries","Top Celebs","Trailers","Reviews","Natural Beauties", "Like Us on Facebook","Follow Us on twitter"};
		for (String cat_name : categories_in_drawer) {
			String title = categories_in_title[i];
			ParentObject pObj = new ParentObject();
			pObj.parent_id = i; //TODO :: This is for demo
			pObj.parent_name = cat_name;
			pObj.parent_desc = title; //TODO :: This is for demo
			parentsArray.add(pObj);
			i++;
		}
		return parentsArray;
	}

	private ParentObject getChildObjectArray(ParentObject pObj, Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			pObj.child_id_array.add(cursor.getLong(0));
			pObj.child_name_array.add(cursor.getString(1));
			pObj.child_desc_array.add(cursor.getString(2));
			pObj.child_status_array.add(cursor.getInt(4));
			pObj.child_type_array.add(cursor.getInt(5));
			try {
				pObj.child_time_array.add(dateFormat.parse(cursor.getString(3)).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return pObj;
	}

	private double[] getCoordinatesArray(Cursor cursor) {
		cursor.moveToFirst();
		double[] coordinates = new double[2];
		double latitude;
		double longitude;
		while (!cursor.isAfterLast()) {
			latitude = cursor.getDouble(0);
			longitude = cursor.getDouble(1);
			coordinates[0] = latitude;
			coordinates[1] = longitude;
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return coordinates;
	}

	private int getCount(Cursor cursor) {
		cursor.moveToFirst();
		int count = 0;
		while (!cursor.isAfterLast()) {
			count++;
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return count;
	}

	private Long[] getSingleRowValue(Cursor cursor) {
		Long[] values = new Long[2];
		cursor.moveToFirst();
		try {
			while (!cursor.isAfterLast()) {
				values[0] = Long.valueOf(cursor.getInt(0));
				values[1] = Long.valueOf(cursor.getInt(1));
				values[2] = dateFormat.parse(cursor.getString(2)).getTime();
				cursor.moveToNext();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cursor.close();
		close();
		return values;
	}

}
