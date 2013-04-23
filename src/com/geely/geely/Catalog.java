package com.geely.geely;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class Catalog extends Activity implements OnChildClickListener {

	final String LOG_TAG = "myLogs";
	ExpandableListView elvMain;
	String tbName, title;
	Intent srhIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.catalog);

		Intent incomInt = getIntent();

		tbName = incomInt.getStringExtra("group");
		if (tbName == null)
			tbName = "";
		title = incomInt.getStringExtra("title");
		int num = incomInt.getIntExtra("num", 0);

		// Search Intent
		if (Intent.ACTION_SEARCH.equals(incomInt.getAction())) {
			srhIntent = new Intent(this, Search.class);
			System.out.println("@@@@@@");
			// ¡ÂÂÏ ÒÚÓÍÛ Á‡ÔÓÒ‡ ËÁ ˝ÍÒÚ˚
			String query = incomInt.getStringExtra(SearchManager.QUERY);
			srhIntent.putExtra("query", query);
			srhIntent.putExtra("title", "Результат поиска");
			startActivity(srhIntent);
		}

		Log.d("name", tbName);

		// Intent incomInt = getIntent();

		View headerView = getLayoutInflater().inflate(R.layout.header, null);
		((TextView) headerView.findViewById(R.id.headerText)).setText(title);

		elvMain = (ExpandableListView) findViewById(R.id.elvMain);
		new ExpandList(this, tbName, num, elvMain, headerView);
		
		elvMain.setOnChildClickListener(this);
		//TextView tmarkText =  (TextView) findViewById(R.id.textView1);
		//System.out.println("test " + tmarkText.getText() );
		
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View v, int arg2,
			int childPosition, long arg4) {
		System.out.println("test " + v.getContentDescription());		
		// TODO Auto-generated method stub
		return false;
	}

}