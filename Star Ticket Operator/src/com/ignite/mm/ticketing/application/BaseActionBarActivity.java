package com.ignite.mm.ticketing.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.AlertDialogWrapper.Builder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * {@link #UserLogin} is base class(parent activity) that can be called from other activities. 
 * <p>
 * Usable methods : 
 * (1) {@link #closeAllActivities()} 
 * (2) {@link #getToday()} 
 * (5) {@link #changeDate(String)}
 * (8) {@link #alertDialog(String, OnClickListener, OnClickListener)}     
 * <p>
 * ** Star Ticket Operator App is used to sell bus tickets via online. 
 * @version 2.0 
 * @author Su Wai Phyo (Ignite Software Solutions)
 * <p>
 * Last Modified : 14/Sept/2015
 * <p>
 * Last ModifiedBy : Saw Maine K
 */
public class BaseActionBarActivity extends ActionBarActivity {
	
	public static LoginUser AppLoginUser;
	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.ignite.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	
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
	
	/**
	 * Close all activities that extend this base class
	 */
	protected void closeAllActivities(){
		sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		registerBaseActivityReceiver();
		AppLoginUser = new LoginUser(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(AppLoginUser.isExpires()){
			closeAllActivities();
		}
		super.onResume();
	}
	
	/**
	 * Get Today Date eg. 2015-09-10
	 * @return return today date (String)
	 */
	protected String getToday(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		Log.i("","Hello Today: "+formattedDate);
		return formattedDate;
	}
	
	/**
	 * Change Date Format from yyyy-MM-dd to dd-MM-yyyy
	 * @param date Date 2015-09-10
	 * @return Date 10-09-2015
	 */
	protected static String changeDate(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DateFormat.format("dd/MM/yyyy",StartDate).toString();
	}
	
	/**
	 * Show alert dialog with yes and no (buttons)
	 * @param MSG message to show in dialog
	 * @param YES Yes button's clickListener if you agree
	 * @param NO No button's clickListener if you disagree
	 */
	protected void alertDialog(String MSG, OnClickListener YES, OnClickListener NO){
		AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
		alertDialog.setMessage(MSG);
		
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
	
		if(YES != null){
			alertDialog.setPositiveButton("YES", YES);
		}else{
			alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });
		}
		if(NO != null){
			alertDialog.setNegativeButton("NO", NO);
		}
		alertDialog.show();
	}
	
}
