package com.geely.geely;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends Activity implements OnClickListener, OnLongClickListener {

	private static final String DB_NAME = "geelyParts";
	private static final String COLUMN_NAME = "tMark";
	private SQLiteDatabase database;
	String query;
	Cursor c;

	TextView tvTitle;
	String title, queryStr, queryStrUp;
	SimpleCursorAdapter scAdapter;
	String sqlQuery, sqlQuery2, gpData;

	int id_category, id;
	EditText srhField;
	Button srhAdd;
	ListView LvData;
	Intent incomInt;
	TextView tmarkText;
	
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

		System.out.println("Ready for search");

		srhField = (EditText) findViewById(R.id.srhField);
		srhAdd = (Button) findViewById(R.id.srhAdd);
		srhAdd.setOnClickListener(this);
		
		
		

	}

	@Override
	public void onClick(View v) {
		// �������� �����
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

		System.out.println("Click on srhBtn");
		// TODO Auto-generated method stub
		query = srhField.getText().toString();
		query = query.replaceAll(" ", "");
		System.out.println("QUERY " + query);
		if (query != "") {
			showResult();
		} else {
			System.out.println("EMPTY FIELD");
		}

	}

	@SuppressLint("DefaultLocale")
	private void showResult() {
		List<Integer> idOfGroups = new ArrayList<Integer>();
		List<Integer> idOfCats = new ArrayList<Integer>();
		// Cursor c;
		// TODO Auto-generated method stub

		// queryStr = query.toLowerCase().substring(1);
		queryStr = query.toLowerCase();
		String queryFc = query.substring(0, 1).toUpperCase();
		queryStrUp = queryFc.concat(query.substring(1));
		// ��������� ��
		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this,
				DB_NAME);
		elvMain = (ExpandableListView) findViewById(R.id.elvMain);
		database = dbOpenHelper.openDataBase();

		/*
		 * ������� ������ ��������� �������� ������
		 */
		// ��������� ��������� ����� �� ������� � ���������� �����
		groupData = new ArrayList<Map<String, String>>();
		System.out.println("queryStr " + queryStr);
		System.out.println("queryStrUp " + queryStrUp);
		sqlQuery = "SELECT _id, title, id_category FROM subcategory WHERE (title LIKE '%"
				+ queryStr + "%' OR title LIKE '%" + queryStrUp + "%')";

		Log.d("SQL-", sqlQuery);

		c = database.rawQuery(sqlQuery, null);
		c.moveToFirst();

		if (!c.isAfterLast()) {
			do {
				id = c.getInt(0);
				gpData = c.getString(1);
				id_category = c.getInt(2);

				// ������ id subcategory � ���������
				idOfGroups.add(c.getInt(0));

				// ������ id category � ���������
				idOfCats.add(c.getInt(2));

				String title = gpData.replaceAll(" +", " ");

				Log.d("LOG-", "id=" + id);
				Log.d("LOG-", "title=" + title);
				Log.d("LOG-", "id_category=" + id_category);

				m = new HashMap<String, String>();
				m.put("groupName", title);
				groupData.add(m);
			} while (c.moveToNext());
		}

		// ���������� ������� � ��������� ������������
		for (int ss : idOfGroups) {
			System.out.println("ss = " + ss);
		}

		System.out.println("countidOfCat= " + idOfGroups.size());

		if (idOfGroups.size() == 0) {

			Toast.makeText(this, "��� �����������", Toast.LENGTH_LONG).show();

		}

		// ������ ���������� ����� ��� ������
		String groupFrom[] = new String[] { "groupName" };
		// ������ ID view-���������, � ������� ����� �������� ��������� �����
		int groupTo[] = new int[] { R.id.tvText };

		// ������� ��������� ��� ��������� ���������
		childData = new ArrayList<ArrayList<Map<String, String>>>();

		for (int i = 0; i < idOfGroups.size(); i++) {
			Log.d("==", "=======================================");
			System.out.println("idOfGroups.get(i) = " + idOfGroups.get(i));
			// ������� ��������� ��������� ��� �����
			childDataItem = new ArrayList<Map<String, String>>();
			// ��������� � ������ ���������
			// Log.d("LOG-", "id_category2=" + id_category);
			// ������� ��� ������� �� ������� ����� ����� ������

			sqlQuery = "SELECT table_name FROM category WHERE _id = (SELECT id_category FROM subcategory WHERE _id = "
					+ idOfGroups.get(i) + " )";

			Log.d("SQL-table_name ", sqlQuery);

			Cursor c3 = database.rawQuery(sqlQuery, null);
			c3.moveToFirst();
			String table_name = c3.getString(0);

			System.out.println("tableName" + table_name);

			sqlQuery = "select " + COLUMN_NAME + " from " + table_name
					+ " where id_subcategory =" + idOfGroups.get(i);

			Log.d("SQL-", sqlQuery);
			c3 = database.rawQuery(sqlQuery, null);
			c3.moveToFirst();
			System.out.println("count" + c3.getCount());
			if (!c3.isAfterLast()) {
				do {
					String data = c3.getString(0);
					String tMark = data.replaceAll(" +", " ");
					Log.d(COLUMN_NAME, tMark);
					// ��������� ������ ���������� ��� ������� ��������
					m = new HashMap<String, String>();
					m.put("tMark", tMark); // ��������
					// ��������� � ��������� ���������
					childDataItem.add(m);
				} while (c3.moveToNext());
			}

			childData.add(childDataItem);
			Log.d("==", "=======================================");
		}

		// ������ ���������� ��������� ��� ������
		String childFrom[] = new String[] { "tMark" };
		// ������ ID view-���������, � ������� ����� �������� ���������
		// ���������
		int childTo[] = new int[] { R.id.textView1 };

		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this, groupData, R.layout.group, groupFrom, groupTo, childData,
				R.layout.items, childFrom, childTo);

		elvMain.setAdapter(adapter);

		c.close();
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		//System.out.println("test " + tmarkText.getText());
		
		
		return false;
	}
}