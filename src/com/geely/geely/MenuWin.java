package com.geely.geely;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MenuWin extends Activity implements OnClickListener {
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
	String group;
	int num;
	Intent intent, srhIntent;
	Button srhBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		intent = new Intent(this, Catalog.class);
		srhIntent = new Intent(this, Search.class);

		TableLayout tableMenu = (TableLayout) findViewById(R.id.tableMenu);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
		tableMenu.setAnimation(anim);

		tv1 = (TextView) findViewById(R.id.filters);
		tv2 = (TextView) findViewById(R.id.pendant);
		tv3 = (TextView) findViewById(R.id.steer);
		tv4 = (TextView) findViewById(R.id.brake);
		tv5 = (TextView) findViewById(R.id.engine);
		tv6 = (TextView) findViewById(R.id.transmission);
		tv7 = (TextView) findViewById(R.id.clutch);
		tv8 = (TextView) findViewById(R.id.electric);
		tv9 = (TextView) findViewById(R.id.salon);
		tv10 = (TextView) findViewById(R.id.trim);
		srhBtn = (Button) findViewById(R.id.srhBtn);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		tv9.setOnClickListener(this);
		tv10.setOnClickListener(this);
		srhBtn.setOnClickListener(this);

		ImageView imgView = (ImageView) findViewById(R.id.imageView1);
		imgView.setAlpha(70);

		
		
		
		

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add(R.string.menuAbout);
		// menu.add(R.string.chooseModel);

		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		showDialog(1);
		return super.onOptionsItemSelected(item);
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle(R.string.aboutTitle);
		adb.setMessage(R.string.aboutText);
		adb.setIcon(android.R.drawable.ic_dialog_info);
		adb.setPositiveButton(R.string.yes, null);
		return adb.create();
	}

	// public void onBackPressed() {
	// גûחûגאול הטאכמד
	// showDialog(1);

	// Intent intent3 = new Intent(Intent.ACTION_VIEW);
	// intent3.setData(Uri.parse("www.ya.ru"));
	// startActivity(intent3);
	// }

	// ArrayList <HashMap<String,String>> groups = new
	// ArrayList<HashMap<String,String>>();
	// String [] title = {
	// "filters",
	// "pendant",
	// "steer",
	// "brake",
	// "engine",
	// "transmission",
	// "clutch",
	// "electric",
	// "salon",
	// "trim"
	// };

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// title of clicked category
		String title = (String) ((TextView) v).getText();

		switch (v.getId()) {
		case R.id.filters:
			group = "filters";
			num = 1;
			break;
		case R.id.pendant:
			group = "pendant";
			num = 2;
			break;
		case R.id.steer:
			group = "steer";
			num = 3;
			break;
		case R.id.brake:
			group = "brake";
			num = 4;
			break;
		case R.id.engine:
			group = "engine";
			num = 5;
			break;
		case R.id.transmission:
			group = "transmission";
			num = 6;
			break;
		case R.id.clutch:
			group = "clutch";
			num = 7;
			break;
		case R.id.electric:
			group = "electric";
			num = 8;
			break;
		case R.id.salon:
			group = "salon";
			num = 9;
			break;
		case R.id.trim:
			group = "trim";
			num = 10;
			break;
		case R.id.srhBtn:
			startActivity(srhIntent);
			return;
			
		}

		intent.putExtra("group", group);
		intent.putExtra("num", num);
		intent.putExtra("title", title);
		startActivity(intent);

	}

}
