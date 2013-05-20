package com.geely.geely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class HomeWindow extends Activity implements OnClickListener {

	 TextView analogMK, originAll;
	 Intent analogMkInt, originAllInt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homewindow);

		analogMK = (TextView) findViewById(R.id.analogMK);
		originAll = (TextView) findViewById(R.id.originAll);
		analogMkInt = new Intent(this, MenuWin.class);
		originAllInt = new Intent(this, SearchFull.class);
		analogMK.setOnClickListener(this);
		originAll.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.analogMK:
			startActivity(analogMkInt);
			break;
		case R.id.originAll:
			startActivity(originAllInt);
			break;
		}
	}
}
