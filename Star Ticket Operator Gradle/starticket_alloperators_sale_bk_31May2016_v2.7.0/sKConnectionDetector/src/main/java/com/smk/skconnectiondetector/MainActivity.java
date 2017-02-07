package com.smk.skconnectiondetector;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
        skDetector.setMessageStyle(SKConnectionDetector.HORIZONTAL_TOASH);
        if(skDetector.isConnectingToInternet()){
        	
        }else{
        	skDetector.showErrorMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
