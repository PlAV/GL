package com.geely.geely;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class ExpandList extends Activity  {

	private static final String DB_NAME = "geelyParts";
	private static final String COLUMN_NAME = "tMark";

	Cursor c;
	private final SQLiteDatabase database;
	TextView tvTitle;
	String title;
	SimpleCursorAdapter scAdapter;
	String sqlQuery, sqlQuery2;
	int id_category;
	ListView LvData;
	TextView tmarkText;

	// коллекция для групп
	ArrayList<Map<String, String>> groupData;

	// коллекция для элементов одной группы
	ArrayList<Map<String, String>> childDataItem;

	// общая коллекция для коллекций элементов
	ArrayList<ArrayList<Map<String, String>>> childData;
	// в итоге получится childData = ArrayList<childDataItem>

	// список аттрибутов группы или элемента
	Map<String, String> m;

	public ExpandList(Context context, String tbName, int ID_CAT,
			ExpandableListView elvMain, View headerView) {
		
//		tmarkText =  (TextView) findViewById(R.id.textView1);
//		System.out.println("test " );
		//tmarkText.setOnClickListener(this);
		 
		
		
		
		// открываем БД
		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(context,
				DB_NAME);

		database = dbOpenHelper.openDataBase();

		// заполняем коллекцию групп из массива с названиями групп
		groupData = new ArrayList<Map<String, String>>();

		// получаем курсор
		sqlQuery = "select _id, title from subcategory where id_category = "
				+ ID_CAT;

		Log.d("SQL-", sqlQuery);
		c = database.rawQuery(sqlQuery, null);

		c.moveToFirst();
		List<Integer> idOfGroups = new ArrayList<Integer>();

		if (!c.isAfterLast()) {
			do {
				int id = c.getInt(0);

				// кидаем id в коллекцию
				idOfGroups.add(id);
				// System.out.println("countidOf" + idOfGroups.size());

				String gpData = c.getString(1);
				String title = gpData.replaceAll(" +", " ");
				Log.d("LOG-", "title=" + title);
				Log.d("LOG-", "id=" + id);
				// Log.d("LOG-", "id_category=" + id_category);
				m = new HashMap<String, String>();
				m.put("groupName", title);
				groupData.add(m);
			} while (c.moveToNext());
		}
		// for (int id : idOfGroups) {
		// System.out.println("countidOf" + id);
		// }

		// список аттрибутов групп для чтения
		String groupFrom[] = new String[] { "groupName" };

		// список ID view-элементов, в которые будет помещены аттрибуты групп
		int groupTo[] = new int[] { R.id.tvText };

		// создаем коллекцию для коллекций элементов
		childData = new ArrayList<ArrayList<Map<String, String>>>();

		for (int i = 0; i < idOfGroups.size(); i++) {

			// создаем коллекцию элементов для групп
			childDataItem = new ArrayList<Map<String, String>>();
			// добавляем к группе коллекции

			sqlQuery = "select " + COLUMN_NAME + " from " + tbName
					+ " where id_subcategory =" + idOfGroups.get(i);

			Log.d("SQL-", sqlQuery);
			c = database.rawQuery(sqlQuery, null);
			c.moveToFirst();
			System.out.println("count" + c.getCount());
			if (!c.isAfterLast()) {
				do {
					String data = c.getString(0);
					String tMark = data.replaceAll(" +", " ");
					Log.d(COLUMN_NAME, tMark);
					Log.d("data", data);
					// заполняем список аттрибутов для каждого элемента
					m = new HashMap<String, String>();
					m.put("tMark", tMark); // название
					// добавляем в коллекцию коллекций
					childDataItem.add(m);
				} while (c.moveToNext());
			}
			childData.add(childDataItem);
			Log.d("==", "=======================================");
		}

		// список аттрибутов элементов для чтения
		String childFrom[] = new String[] { "tMark" };
		// список ID view-элементов, в которые будет помещены аттрибуты
		// элементов
		int childTo[] = new int[] { R.id.textView1 };

		Log.d("TEST", "TEST");
		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				context, groupData, R.layout.group, groupFrom, groupTo,
				childData, R.layout.items, childFrom, childTo);

		elvMain.addHeaderView(headerView);
		elvMain.setAdapter(adapter);

		c.close();
		//dbOpenHelper.close();
	}

	public void OnCreateContextMenu(){
		System.out.println("test22 ");
	}

	

}