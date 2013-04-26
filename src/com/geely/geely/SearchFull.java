package com.geely.geely;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SearchFull extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchview);

		System.out.println("Ready for full search");
	}

}
