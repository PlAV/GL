package com.geely.geely;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class HomeWindow extends Activity implements OnClickListener {

	TextView analogMK, originAll;
	Intent analogMkInt, originAllInt;
	public final int DIALOG_EXIT = 1;

	public static Boolean sentFdb = false;
	public static int countLaunch = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homewindow);

		analogMK = (TextView) findViewById(R.id.analogMk);
		originAll = (TextView) findViewById(R.id.originAll);
		analogMkInt = new Intent(this, MenuWin.class);
		originAllInt = new Intent(this, SearchFull.class);
		analogMK.setOnClickListener(this);
		originAll.setOnClickListener(this);
		if (sentFdb == true) {
			if (++countLaunch % 10 == 0) {
				sentFdb = false;
			}
		}

		System.out.println("countLaunch= " + countLaunch + "sentFdb= "
				+ sentFdb);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.analogMk:
			startActivity(analogMkInt);
			break;
		case R.id.originAll:
			startActivity(originAllInt);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (!sentFdb) {
			showDialog(DIALOG_EXIT);
		} else {
			finish();
		}

		return;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_EXIT) {

			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			// заголовок
			adb.setTitle(R.string.fdb_title);
			// сообщение
			adb.setMessage(R.string.fdb_text);
			// иконка
			adb.setIcon(android.R.drawable.ic_dialog_info);
			adb.setPositiveButton(R.string.fdb_yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							// отмечаем согласие на отзыв
							sentFdb = true;
							countLaunch = 0;
							Intent intentOnExit = new Intent(Intent.ACTION_VIEW);
							intentOnExit.setData(Uri
									.parse("market://details?id=com.geely.geely")); // market://details?id=com.geely.geely
							startActivity(intentOnExit);
							finish();
						}

					});
			adb.setNegativeButton(R.string.fdb_no,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							finish();
						}

					});

			return adb.create();
		}
		return super.onCreateDialog(id);
	}

}
