package com.geely.geely;

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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFull extends Activity implements OnClickListener {
	RadioGroup rg;
	Button srhAdd;
	RadioButton rbCk, rbMk, rbFc;
	private String checkedBtn = "ck";
	public TextView checkedId;
	private static final String DB_NAME = "geelyParts";
	private SQLiteDatabase database;
	Cursor c;
	TextView tvTitle, tmarkText;
	String title, queryStr, number, sqlQuery, sqlQuery2, gpData, query;
	SimpleCursorAdapter scAdapter;
	int id_category, id;
	EditText fullSrhField;
	ListView LvData;
	Intent incomInt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchfullview);

		// System.out.println("Ready for full search");

		rg = (RadioGroup) findViewById(R.id.radioGroupMarks);

		srhAdd = (Button) findViewById(R.id.srhAdd);
		srhAdd.setOnClickListener(this);
		fullSrhField = (EditText) findViewById(R.id.fullSrhField);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (rg.getCheckedRadioButtonId()) {
		case R.id.ck:
			checkedBtn = "CK";
			break;
		case R.id.mk:
			checkedBtn = "MK";
			break;
		case R.id.fc:
			checkedBtn = "FC";
			break;
		}
		// скрываем клаву
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

		// System.out.println("Click on fullSrhBtn");

		query = fullSrhField.getText().toString();
		// System.out.println("query");
		query = query.replaceAll(" ", "");
		// System.out.println("QUERY " + query);
		if (query != "") {
			showResult();
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("DefaultLocale")
	private void showResult() {

		// TODO Auto-generated method stub

		queryStr = query.toUpperCase();
		// // // открываем БД
		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this,
				DB_NAME);
		database = dbOpenHelper.openDataBase();

		// * Ќаходим список категорий согласно поиску
		// */
		// // заполняем коллекцию групп из массива с названиями групп
		System.out.println("queryStr " + queryStr);

		sqlQuery = "SELECT _id, number, title  FROM geely_full WHERE (title LIKE '%"
				+ queryStr + "%' AND car_mark LIKE '%" + checkedBtn + "%')";
		//
		Log.d("SQL-", sqlQuery);
		//
		c = database.rawQuery(sqlQuery, null);
		// формируем столбцы сопоставления
		String[] from = new String[] { "title", "number" };
		int[] to = new int[] { R.id.title, R.id.itemNumber };
		System.out.println("CPUNT= " + c.getCount());
		if (c.getCount() == 0) {
			Toast.makeText(this, R.string.search_no_records, Toast.LENGTH_LONG)
					.show();
			return;
		}

		// создааем адаптер и настраиваем список
		scAdapter = new SimpleCursorAdapter(this, R.layout.srhresult, c, from,
				to);
		ListView srhResult = (ListView) findViewById(R.id.srhResult);
		srhResult.setAdapter(scAdapter);

	}
}
