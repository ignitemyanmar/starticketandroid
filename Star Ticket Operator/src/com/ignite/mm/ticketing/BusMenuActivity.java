package com.ignite.mm.ticketing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActionBarActivity;
import com.ignite.mm.ticketing.application.BookingCodeDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

/**
 * {@link #BusMenuActivity} is the class to show Menu of the app
 * <p>
 * Private methods:
 * (1) {@link #getNotiBooking}
 * (2) {@link #getSupportParentActivityIntent}
 * (3) {@link #onCreateOptionsMenu}
 * (4) {@link #onOptionsItemSelected(MenuItem)}
 * <p>
 * ** Star Ticket Operator App is used to sell bus tickets via online. 
 * @version 2.0 
 * @author Su Wai Phyo (Ignite Software Solutions)
 * <p>
 * Last Modified : 14/Sept/2015
 * <p>
 * Last ModifiedBy : Saw Maine K
 */
public class BusMenuActivity extends BaseActionBarActivity {
	private LinearLayout btn_sale_ticket;
	private LinearLayout btn_order;
	private LinearLayout btn_old_sale;
	private LinearLayout btn_all_booking;
	private LinearLayout btn_search_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Show Menu 
		//1) Sale 
		//2) Enter sale into online after sold by manually
		//3) Booking List by Date
		//4) Booking List by booking code
		setContentView(R.layout.activity_busticketing_menu);
		
		//Page Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            this.setSupportActionBar(toolbar);
        }
		
		btn_sale_ticket = (LinearLayout) findViewById(R.id.btn_sale_ticket);
		btn_order = (LinearLayout) findViewById(R.id.btn_credit_list);
		btn_old_sale = (LinearLayout) findViewById(R.id.btn_cancel_order);
		btn_all_booking = (LinearLayout) findViewById(R.id.all_booking);
		btn_search_code = (LinearLayout) findViewById(R.id.btn_search_code);
		
		btn_sale_ticket.setOnClickListener(clickListener);
		btn_order.setOnClickListener(clickListener);
		btn_old_sale.setOnClickListener(clickListener);
		btn_all_booking.setOnClickListener(clickListener);
		btn_search_code.setOnClickListener(clickListener);
		
		SKConnectionDetector detector = SKConnectionDetector.getInstance(this);
		if(detector.isConnectingToInternet()){
			getNotiBooking();
		}else {
			Toast.makeText(BusMenuActivity.this, "Network unavailable!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * (1) {@code btn_sale_ticket} clicked: go next activity {@link BusTripsCityActivity} for sale tickets
	 * <p>
	 * (2) {@code btn_old_sale} clicked: show Calendar Dialog for manual sale
	 * <p>
	 * (3) {@code btn_order} clicked: show Calendar Dialog for booking list
	 * <p>
	 * (4) {@code btn_search_code} clicked: show Dialog to enter booking code for booking list
	 */
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_sale_ticket){
				SharedPreferences sharedPreferences = getSharedPreferences("old_sale",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("working_date", "");
				editor.commit();
				Intent intent = new Intent(getApplicationContext(),	BusTripsCityActivity.class);
				startActivity(intent);
			}
			
			if(v == btn_order){
				final SKCalender skCalender = new SKCalender(BusMenuActivity.this);
				
				  skCalender.setCallbacks(new Callbacks() {

						public void onChooseDate(String chooseDate) {
				        	// TODO Auto-generated method stub
				        	Date formatedDate = null;
							try {
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
								
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
								String selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
					        	SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPreferences.edit();
								editor.clear();
								editor.commit();
								editor.putString("order_date", selectedDate);
								editor.commit();
					        	skCalender.dismiss();
					        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));	
				        	
				        }
				  });

				skCalender.show();
			}
			
			if(v == btn_old_sale){
				final SKCalender skCalender = new SKCalender(BusMenuActivity.this);
				
				  skCalender.setCallbacks(new Callbacks() {
						public void onChooseDate(String chooseDate) {
				        	// TODO Auto-generated method stub
							Date today = null;
				        	Date formatedDate = null;
							try {
								Log.i("","Hello Choose Date : "+ chooseDate);
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
								today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							int compare = today.compareTo(formatedDate);
							Log.i("","Hello Compare : "+ compare);
							if(compare > 0){
							
					        	String selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
					        	
					        	SharedPreferences sharedPreferences = getSharedPreferences("old_sale",MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPreferences.edit();
								editor.clear();
								editor.commit();
								editor.putString("working_date", selectedDate);
								editor.commit();
					        	skCalender.dismiss();
					        	startActivity(new Intent(getApplicationContext(), BusTripsCityActivity.class));
							}else{
								SKToastMessage.showMessage(BusMenuActivity.this, "Must be less than today!.", SKToastMessage.ERROR);
							}
				        }
				  });

				skCalender.show();
			}
			
			if(v == btn_all_booking){
	        	SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", "");
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}
			
			if(v == btn_search_code){
				BookingCodeDialog bookingCodeDialog = new BookingCodeDialog(BusMenuActivity.this);
				bookingCodeDialog.setCallbackListener(new BookingCodeDialog.Callback() {
					
					public void onSearch(String code) {
						// TODO Auto-generated method stub
						if(code.length() > 0){
							SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedPreferences.edit();
							editor.clear();
							editor.commit();
							editor.putString("book_code", code);
							editor.commit();
				        	startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
						}else{
							SKToastMessage.showMessage(BusMenuActivity.this, "Please Enter Booking Code", SKToastMessage.ERROR);
						}
					}
					
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
				bookingCodeDialog.show();
			}
			
		}
	};
	
	private Menu menu;
	
	/**
	 *  Get Booking Notification, show number of booking in Menu of activities
	 */
	private void getNotiBooking(){
		String param = MCrypt.getInstance().encrypt(SecureParam.getNotiBookingParam(AppLoginUser.getAccessToken(), getToday()));
		NetworkEngine.getInstance().getNotiBooking(param , new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences = getSharedPreferences("NotifyBooking",Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				
				editor.clear();
				editor.commit();
				Integer bookingCount = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Integer>() {}.getType());
				editor.putInt("count", bookingCount);
				editor.commit();
				
				if(bookingCount > 0){
					menu.getItem(0).setTitle(bookingCount.toString());
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	/**
	 * Show Menu for Booking Notification
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bus_confirm, menu);
		this.menu = menu;

		return true;
	}

	/**
	 * Booking Notification clickListener, show booking list
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_booking_noti) {
			SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.clear();
			editor.commit();
			editor.putString("order_date", getToday());
			editor.commit();
        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
