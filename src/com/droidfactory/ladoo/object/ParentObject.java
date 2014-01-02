package com.droidfactory.ladoo.object;

import java.util.ArrayList;

public class ParentObject {

	public int parent_id;
	public String parent_name;
	public String parent_desc;

	public ArrayList<Long> child_id_array = new ArrayList<Long>();
	public ArrayList<String> child_name_array = new ArrayList<String>();
	public ArrayList<String> child_desc_array = new ArrayList<String>();
	public ArrayList<Long> child_time_array = new ArrayList<Long>();
	public ArrayList<Integer> child_status_array = new ArrayList<Integer>();
	public ArrayList<Integer> child_type_array = new ArrayList<Integer>();
}
