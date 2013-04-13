package com.geely.geely;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class Search extends Activity implements OnClickListener {

	private static final String DB_NAME = "geelyParts";
	private static final String COLUMN_NAME = "tMark";
	private SQLiteDatabase database;
	String query;
	
	Cursor c2;
	
	
	TextView tvTitle;
	String title, queryStr;
	SimpleCursorAdapter scAdapter;
	String sqlQuery, sqlQuery2, gpData;
	
	int id_category, id;
	EditText srhField;
	Button srhAdd;
	ListView LvData;
	Intent incomInt;
	// коллекция для групп
	ArrayList<Map<String, String>> groupData;

	// коллекция для элементов одной группы
	ArrayList<Map<String, String>> childDataItem;

	// общая коллекция для коллекций элементов
	ArrayList<ArrayList<Map<String, String>>> childData;
	// в итоге получится childData = ArrayList<childDataItem>

	// список аттрибутов группы или элемента
	Map<String, String> m;
	
	List<Integer> idOfGroups = new ArrayList<Integer>();

	ExpandableListView elvMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchview);

		System.out.println("Ready for search");

		srhField = (EditText) findViewById(R.id.srhField);
		srhAdd = (Button) findViewById(R.id.srhAdd);
		srhAdd.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		System.out.println("Click on srhBtn");
		// TODO Auto-generated method stub
		query = srhField.getText().toString();
		System.out.println("B" + query);
		query = query.replaceAll(" ", ""); 
		System.out.println("A" + query);
		
		if(queryStr != ""){
			showResult();
		}else{
			System.out.println("EMPTY FIELD");
		}
		
		
	
	}

	@SuppressLint("DefaultLocale")
	private void showResult() {
		// TODO Auto-generated method stub
		query.toLowerCase().substring(1);
		System.out.println("srh= " + query );
		
		
		
		// открываем БД
		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this,
				DB_NAME);
		elvMain = (ExpandableListView) findViewById(R.id.elvMain);
		database = dbOpenHelper.openDataBase();
		
		/*
		 * Находим список категорий согласно поиску
		 */
		
		
		// заполняем коллекцию групп из массива с названиями групп
		groupData = new ArrayList<Map<String, String>>();
		System.out.println("queryStr " + queryStr);
		sqlQuery = "select _id, title, id_category from subcategory where title LIKE '%" +query+ "%'"; 
		
		Log.d("SQL-", sqlQuery);
		
		c2 = database.rawQuery(sqlQuery, null);
		c2.moveToFirst();
		
		
		
		if (!c2.isAfterLast()) {
			do {
				//id = c2.getInt(0);
				gpData = c2.getString(1);
				id_category = c2.getInt(2);
				
				// кидаем id subcategory в коллекцию
				idOfGroups.add(c2.getInt(0));
				

				
				
				String title = gpData.replaceAll(" +", " ");
				
				Log.d("LOG-", "id=" + id);
				Log.d("LOG-", "title=" + title);
				Log.d("LOG-", "id_category=" + id_category);
				
				m = new HashMap<String, String>();
				m.put("groupName", title);
				groupData.add(m);
			} while (c2.moveToNext());
		}
		 System.out.println("countidOfCat" + idOfGroups.size());
		 // список аттрибутов групп для чтения
		String groupFrom[] = new String[] { "groupName" };
		// список ID view-элементов, в которые будет помещены аттрибуты групп
		int groupTo[] = new int[] { R.id.tvText };

		
		
		
		
		// создаем коллекцию для коллекций элементов
		childData = new ArrayList<ArrayList<Map<String, String>>>();

		for (int i = 0; i < idOfGroups.size(); i++) {
			Log.d("==", "=======================================");
			// создаем коллекцию элементов для групп
			childDataItem = new ArrayList<Map<String, String>>();
			// добавляем к группе коллекции
			Log.d("LOG-", "id_category2=" + id_category);
			// Находим  имя таблицы из которой нужно взять данные
			sqlQuery = "SELECT table_name FROM category WHERE _id = "	+ idOfGroups.get(i);
			
			Log.d("SQL-table_name ", sqlQuery);
			c2 = database.rawQuery(sqlQuery, null);
			c2.moveToFirst();
			String table_name = c2.getString(0);
			// Cursor c2 = database.rawQuery(sqlQuery , null);
			// String table_name = c2.getString(0);
			 System.out.println("tableName" + table_name);
			sqlQuery = "select " + COLUMN_NAME + " from " + table_name
					+ " where id_subcategory =" + idOfGroups.get(i);

			Log.d("SQL-", sqlQuery);
			c2 = database.rawQuery(sqlQuery, null);
			c2.moveToFirst();
			System.out.println("count" + c2.getCount());
			if (!c2.isAfterLast()) {
				do {
					String data = c2.getString(0);
					String tMark = data.replaceAll(" +", " ");
					Log.d(COLUMN_NAME, tMark);
					//Log.d("data", data);
					// заполняем список аттрибутов для каждого элемента
					m = new HashMap<String, String>();
					m.put("tMark", tMark); // название
					// добавляем в коллекцию коллекций
					childDataItem.add(m);
				} while (c2.moveToNext());
			}
			childData.add(childDataItem);
			Log.d("==", "=======================================");
		}

		// список аттрибутов элементов для чтения
		String childFrom[] = new String[] { "tMark" };
		// список ID view-элементов, в которые будет помещены аттрибуты
		// элементов
		int childTo[] = new int[] { R.id.textView1 };

		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this, groupData, R.layout.group, groupFrom, groupTo,
				childData, R.layout.items, childFrom, childTo);

		//View headerView = getLayoutInflater().inflate(R.layout.header, null);
		//((TextView) headerView.findViewById(R.id.headerText)).setText(title);

		//elvMain.addHeaderView(headerView);
		elvMain.setAdapter(adapter);
		//query = null;
		c2.close();
	}

}