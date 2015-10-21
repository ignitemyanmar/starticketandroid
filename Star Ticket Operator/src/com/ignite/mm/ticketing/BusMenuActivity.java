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

public class BusMenuActivity extends BaseActionBarActivity {
	private LinearLayout btn_sale_ticket;
	private LinearLayout btn_order;
	private LinearLayout btn_old_sale;
	private LinearLayout btn_all_booking;
	private LinearLayout btn_search_code;
	private TextView txt_sale;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_busticketing_menu);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            this.setSupportActionBar(toolbar);
        }
		
        txt_sale = (TextView)findViewById(R.id.txt_sale);
		btn_sale_ticket = (LinearLayout) findViewById(R.id.btn_sale_ticket);
		btn_order = (LinearLayout) findViewById(R.id.btn_credit_list);
		btn_old_sale = (LinearLayout) findViewById(R.id.btn_cancel_order);
		btn_all_booking = (LinearLayout) findViewById(R.id.all_booking);
		btn_search_code = (LinearLayout) findViewById(R.id.btn_search_code);
		
		//If Sale Checker, show only sale & sale check
		if (AppLoginUser.getUserRole().equals("7")) {
			txt_sale.setText(getResources().getString(R.string.str_sale_salecheck));
			btn_old_sale.setVisibility(View.GONE);
			btn_order.setVisibility(View.GONE);
			btn_search_code.setVisibility(View.GONE);
		}else if (NetworkEngine.getIp().equals("lumbini.starticketmyanmar.com")) {
			
			txt_sale.setText(getResources().getString(R.string.str_khonekyi_khonepaing));
			btn_old_sale.setVisibility(View.GONE);
			btn_order.setVisibility(View.GONE);
			btn_search_code.setVisibility(View.GONE);
			
		}else {
			btn_old_sale.setVisibility(View.VISIBLE);
			btn_order.setVisibility(View.VISIBLE);
			btn_search_code.setVisibility(View.VISIBLE);
			
		}
		
		btn_sale_ticket.setOnClickListener(clickListener);
		btn_order.setOnClickListener(clickListener);
		btn_old_sale.setOnClickListener(clickListener);
		btn_all_booking.setOnClickListener(clickListener);
		btn_search_code.setOnClickListener(clickListener);
		
		SKConnectionDetector detector = SKConnectionDetector.getInstance(this);
		if(detector.isConnectingToInternet()){
			getNotiBooking();
		}
		
	}
	
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
								editor.putString("fromButton", "old_sale");
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
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bus_confirm, menu);
		if (NetworkEngine.getIp().equals("lumbini.starticketmyanmar.com")) {
			MenuItem item = menu.findItem(R.id.action_booking_noti);
			item.setVisible(false);
			this.invalidateOptionsMenu();
		}
		this.menu = menu;

		return true;
	}

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
