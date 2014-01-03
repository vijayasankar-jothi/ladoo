package com.droidfactory.ladoo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Schema extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ladoo.db";
	private static final int DATABASE_VERSION = 2;

	public static final String TABLE_SYS_PROP = "SystemProperties";
	public static final String TABLE_PARENTS = "Parent";
	public static final String TABLE_CHILDREN = "Child";

	private static String TABLE_CREATE_SYS_PROP;
	private static String TABLE_CREATE_PARENTS;
	private static String TABLE_CREATE_CHILDREN;

	public static final String COLUMN_KEYNAME = "KEYNAME";
	public static final String COLUMN_VALUENAME = "VALUENAME";

	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_PARENT_ID = "PARENT_ID";
	public static final String COLUMN_PARENT_OTHER = "PARENT_OTHER";
	public static final String COLUMN_PARENT_NAME = "PARENT_NAME";
	public static final String COLUMN_PARENT_DESC = "PARENT_DESC";
	public static final String COLUMN_PARENT_AMOUNT = "PARENT_AMOUNT";
	public static final String COLUMN_PARENT_TYPE = "PARENT_TYPE";
	public static final String COLUMN_PARENT_VALUE = "PARENT_VALUE";

	public static final String COLUMN_CHILD_ID = "CHILD_ID";
	public static final String COLUMN_CHILD_NAME = "CHILD_NAME";
	public static final String COLUMN_CHILD_DESC = "CHILD_DESC";
	public static final String COLUMN_TIME = "TIMEVALUE";
	public static final String COLUMN_FROM_TIME = "FROM_TIME";
	public static final String COLUMN_TO_TIME = "TO_TIME";
	public static final String COLUMN_STATUS = "STATUS";
	public static final String COLUMN_CHILD_TYPE = "CHILD_TYPE";
	public static final String COLUMN_TYPE = "TYPE";

	public static final String KEY_CURRENCY = "currencytype";
	public static final String KEY_MAINPAGE = "mainpage";
	public static final String KEY_MY_LOCATION = "mylocation";
	public static final String VALUE_CURRENCY = "$";
	public static final String VALUE_MAINPAGE = "users";
	public static final String VALUE_MY_LOCATION = "";

	public Schema(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		StringBuilder table_create_query_builder = new StringBuilder();
		table_create_query_builder.append("create table ").append(TABLE_SYS_PROP).append("(");
		table_create_query_builder.append(COLUMN_KEYNAME).append(" text NOT NULL,");
		table_create_query_builder.append(COLUMN_VALUENAME).append(" text NOT NULL");
		table_create_query_builder.append(");");
		TABLE_CREATE_SYS_PROP = table_create_query_builder.toString();

		table_create_query_builder = new StringBuilder();
		table_create_query_builder.append("create table ").append(TABLE_PARENTS).append("(");
		table_create_query_builder.append(COLUMN_PARENT_ID).append(" INTEGER primary key autoincrement,");
		table_create_query_builder.append(COLUMN_PARENT_TYPE).append(" INTEGER DEFAULT 0,");
		table_create_query_builder.append(COLUMN_PARENT_NAME).append(" text not null,");
		table_create_query_builder.append(COLUMN_PARENT_DESC).append(" text  null,");
		table_create_query_builder.append(COLUMN_PARENT_OTHER).append(" text  null,");
		table_create_query_builder.append(COLUMN_PARENT_VALUE).append(" INTEGER DEFAULT 0,");
		table_create_query_builder.append(COLUMN_FROM_TIME).append(" datetime DEFAULT (datetime(current_timestamp)),");
		table_create_query_builder.append(COLUMN_TO_TIME).append(" datetime DEFAULT (datetime(current_timestamp))");
		table_create_query_builder.append(");");
		TABLE_CREATE_PARENTS = table_create_query_builder.toString();

		table_create_query_builder = new StringBuilder();
		table_create_query_builder.append("create table ").append(TABLE_CHILDREN).append("(");
		table_create_query_builder.append(COLUMN_CHILD_ID).append(" INTEGER primary key autoincrement,");
		table_create_query_builder.append(COLUMN_ID).append(" LONG DEFAULT 0,");
		table_create_query_builder.append(COLUMN_CHILD_NAME).append(" text not null,");
		table_create_query_builder.append(COLUMN_CHILD_DESC).append(" text DEFAULT 'no description',");
		table_create_query_builder.append(COLUMN_TIME).append(" datetime DEFAULT (datetime(current_timestamp)),");
		table_create_query_builder.append(COLUMN_STATUS).append(" INTEGER DEFAULT -1,");
		table_create_query_builder.append(COLUMN_CHILD_TYPE).append(" INTEGER DEFAULT -1,");
		table_create_query_builder.append(COLUMN_TYPE).append(" INTEGER DEFAULT -1,");
		table_create_query_builder.append(" FOREIGN KEY(").append(COLUMN_ID).append(")");
		table_create_query_builder.append(" REFERENCES ").append(TABLE_PARENTS).append("(").append(COLUMN_PARENT_ID).append(")");
		table_create_query_builder.append(" ON DELETE CASCADE");
		table_create_query_builder.append(");");
		TABLE_CREATE_CHILDREN = table_create_query_builder.toString();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_SYS_PROP);
		db.execSQL(TABLE_CREATE_PARENTS);
		db.execSQL(TABLE_CREATE_CHILDREN);
		LoadSystemProperties(db);
	}

	private void LoadSystemProperties(SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_KEYNAME, KEY_CURRENCY);
		values.put(COLUMN_VALUENAME, VALUE_CURRENCY);
		db.insert(TABLE_SYS_PROP, null, values);
		values.put(COLUMN_KEYNAME, KEY_MAINPAGE);
		values.put(COLUMN_VALUENAME, VALUE_MAINPAGE);
		db.insert(TABLE_SYS_PROP, null, values);
		values.put(COLUMN_KEYNAME, KEY_MY_LOCATION);
		values.put(COLUMN_VALUENAME, VALUE_MY_LOCATION);
		db.insert(TABLE_SYS_PROP, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYS_PROP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILDREN);
		onCreate(db);
	}

}
