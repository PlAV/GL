package com.geely.geely;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class Search extends Activity {

	private static final String DB_NAME = "geelyParts";
	private static final String COLUMN_NAME = "tMark";

	Cursor c;
	private SQLiteDatabase database;
	TextView tvTitle;
	String title, queryStr;
	SimpleCursorAdapter scAdapter;
	String sqlQuery, sqlQuery2, query;
	int id_category;

	ListView LvData;
	Intent incomInt;
	// ��������� ��� �����
	ArrayList<Map<String, String>> groupData;

	// ��������� ��� ��������� ����� ������
	ArrayList<Map<String, String>> childDataItem;

	// ����� ��������� ��� ��������� ���������
	ArrayList<ArrayList<Map<String, String>>> childData;
	// � ����� ��������� childData = ArrayList<childDataItem>

	// ������ ���������� ������ ��� ��������
	Map<String, String> m;

	ExpandableListView elvMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchview);

		System.out.println("!!!!!!!");
		/*incomInt = getIntent();
		
		

		Intent incomInt = getIntent();
		query = incomInt.getStringExtra("query");
		title = incomInt.getStringExtra("title");
		// ��������� ��
		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this,
				DB_NAME);
		elvMain = (ExpandableListView) findViewById(R.id.elvMain);
		database = dbOpenHelper.openDataBase();

		// ��������� ��������� ����� �� ������� � ���������� �����
		groupData = new ArrayList<Map<String, String>>();

		queryStr = query.replaceAll(" ", "").toLowerCase().substring(1);

		sqlQuery = "select _id, title, id_category from subcategory where title LIKE '%"
				+ queryStr + "%' ";
		Log.d("SQL-", sqlQuery);

		Log.d("SQL-", sqlQuery);
		c = database.rawQuery(sqlQuery, null);

		c.moveToFirst();
		List<Integer> idOfGroups = new ArrayList<Integer>();

		if (!c.isAfterLast()) {
			do {
				int id = c.getInt(0);

				// ������ id � ���������
				idOfGroups.add(id);
				// System.out.println("countidOf" + idOfGroups.size());

				String gpData = c.getString(1);
				id_category = c.getInt(2);
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

		// ������ ���������� ����� ��� ������
		String groupFrom[] = new String[] { "groupName" };

		// ������ ID view-���������, � ������� ����� �������� ��������� �����
		int groupTo[] = new int[] { R.id.tvText };

		// ������� ��������� ��� ��������� ���������
		childData = new ArrayList<ArrayList<Map<String, String>>>();

		for (int i = 0; i < idOfGroups.size(); i++) {

			// ������� ��������� ��������� ��� �����
			childDataItem = new ArrayList<Map<String, String>>();
			// ��������� � ������ ���������

			System.out.println("tableNameBefore");
			sqlQuery = "SELECT table_name FROM category WHERE _id = "
					+ id_category;
			Log.d("SQL-table_name ", sqlQuery);
			c = database.rawQuery(sqlQuery, null);
			c.moveToFirst();
			String table_name = c.getString(0);
			// Cursor c2 = database.rawQuery(sqlQuery , null);
			// String table_name = c2.getString(0);
			// System.out.println("tableName" + table_name);
			sqlQuery = "select " + COLUMN_NAME + " from " + table_name
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
					// ��������� ������ ���������� ��� ������� ��������
					m = new HashMap<String, String>();
					m.put("tMark", tMark); // ��������
					// ��������� � ��������� ���������
					childDataItem.add(m);
				} while (c.moveToNext());
			}
			childData.add(childDataItem);
			Log.d("==", "=======================================");
		}

		// ������ ���������� ��������� ��� ������
		String childFrom[] = new String[] { "tMark" };
		// ������ ID view-���������, � ������� ����� �������� ���������
		// ���������
		int childTo[] = new int[] { R.id.textView1 };

		Log.d("TEST", "TEST");
		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this, groupData, R.layout.groupsrh, groupFrom, groupTo,
				childData, R.layout.itemssrh, childFrom, childTo);

		View headerView = getLayoutInflater().inflate(R.layout.header, null);
		((TextView) headerView.findViewById(R.id.headerText)).setText(title);

		elvMain.addHeaderView(headerView);
		elvMain.setAdapter(adapter);
		query = null;
		c.close();
*/
	}

}