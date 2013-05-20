package com.geely.geely;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


	public class MainActivity extends Activity {
		// Set the display time, in milliseconds (or extract it out as a configurable parameter)
	    private final int SPLASH_DISPLAY_LENGTH = 5000;
	    
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   		requestWindowFeature(Window.FEATURE_NO_TITLE);
	   		setContentView(R.layout.startlogo);
	   		
	   		
	   		//TextView textVersion = (TextView)findViewById(R.string.version);
	        ImageView imgV = (ImageView)findViewById(R.id.imgV);
	        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
	        
	       
	        imgV.setAnimation(anim);
	    }
	    
	    @Override
	    protected void onResume()
	    {
	        super.onResume();
	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        // Obtain the sharedPreference, default to true if not available
	        boolean isSplashEnabled = sp.getBoolean("isSplashEnabled", true);
	 
	        if (isSplashEnabled) {
	            new Handler().postDelayed(new Runnable() {
	                public void run() {
	                    //Finish the splash activity so it can't be returned to.
	                	MainActivity.this.finish();
	                    // Create an Intent that will start the main activity.
	                    Intent mainIntent = new Intent(MainActivity.this, HomeWindow.class);
	                   
	                    MainActivity.this.startActivity(mainIntent);
	                }
	            }, SPLASH_DISPLAY_LENGTH);
	        } else {
	            // if the splash is not enabled, then finish the activity immediately and go to main.
	            finish();
	            Intent mainIntent = new Intent(MainActivity.this, MenuWin.class);
	            MainActivity.this.startActivity(mainIntent);
	        }
	    }
	    
	  
	   
	}
