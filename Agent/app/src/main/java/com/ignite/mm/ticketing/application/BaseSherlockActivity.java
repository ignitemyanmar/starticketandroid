package com.ignite.mm.ticketing.application;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import com.ignite.mdm.ticketing.sqlite.database.model.PermissionGlobal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseSherlockActivity extends AppCompatActivity {
	
	public LoginUser AppLoginUser;
	public PermissionGlobal AppPermission;
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.ignite.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	private Locale myLocale;
	
	private static IntentFilter createIntentFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}
	
	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}
	
	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
	}
	
	public class BaseActivityReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)){
				finish();
			}
		}
	} 
	
	protected void closeAllActivities(){
		sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
	}
	
/*	protected void changeLang(String lang) {
		// TODO Auto-generated method stub
    	if (lang.equalsIgnoreCase(""))
    		return;
    	
    	myLocale = new Locale(lang);
    	saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
	}
	
    public void saveLocale(String lang)
    {
    	String langPref = "Language";
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	
    	//editor.clear();
    	//editor.commit();

		editor.putString(langPref, lang);
		editor.commit();
    }*/
	public Typeface typeface;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		registerBaseActivityReceiver();
		AppLoginUser = new LoginUser(this);
		typeface = Typeface.createFromAsset(getAssets(),"fonts/zawgyi.ttf");
		//AppPermission = new PermissionGlobal(this);
	}
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
/*		if(AppLoginUser.isExpires()){
			closeAllActivities();
		}*/
		super.onResume();
	}
	
	protected String getToday(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		Log.i("","Hello Today: "+formattedDate);
		return formattedDate;
	}
	
	protected String getTodayTime(){
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat writeFormat = new SimpleDateFormat("hh:mm aa");
		Calendar cal = Calendar.getInstance();
		Date tTime = null;
		try {
			tTime = sdf2.parse(sdf2.format(cal.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String todayTime = writeFormat.format(tTime);
		Log.i("", "today time: "+todayTime);
		return todayTime;
	}
	
/*	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	        case R.id.setting:
	        	startActivity(get)
	        	return true;
   	   	}
		return false;
	 }*/
	
	public static String changeDate(String date){
		Log.i("", "to change date: "+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("", "start date: "+StartDate);
		return DateFormat.format("dd-MM-yyyy", StartDate).toString();
	}
	
	public static String changeDateString(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DateFormat.format("dd-MMMM-yyyy", StartDate).toString();
	}
	
	public void showAlert(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		//alert.setIcon(R.drawable.attention_icon);
		//alert.setTitle("Warning");
		alert.setMessage(message);
		alert.show();
	}
	
	protected void alertDialog(String MSG, OnClickListener YES, OnClickListener NO){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(MSG);
		if(YES != null){
			alertDialog.setButton(Dialog.BUTTON_POSITIVE, "YES", YES);
		}else{
			alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
		}
		if(NO != null){
			alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "NO", NO);
		}
		alertDialog.show();
	}
}
