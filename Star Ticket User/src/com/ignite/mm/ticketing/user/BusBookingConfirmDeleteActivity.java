package com.ignite.mm.ticketing.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.Booking;
import com.ignite.mm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;

import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusBookingConfirmDeleteActivity extends BaseActivity {
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private Button btn_pay;
	private Button btn_cancel_order;
	private Button btn_back;
	private String creditOrderString;
	private Booking creditOrder;
	private TextView action_bar_title2;
	private String todayDate;
	private String todayTime;
	private Permission permission;
	protected String permit_ip;
	protected String permit_access_token;
	protected String permit_operator_id;
	protected String permit_operator_group_id;
	protected String permit_agent_id;
	private SKConnectionDetector connectionDetector;
	private ProgressDialog dialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
/*		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("Star Ticket");
		action_bar_title2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		action_bar_title2.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/
		
		setContentView(R.layout.activity_pay_delete);
		
		//Get Floating Menu from Base Activity
		getFloatingMenu();
		
		btn_pay = (Button) findViewById(R.id.btn_pay);
		btn_cancel_order = (Button) findViewById(R.id.btn_cancel);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		//if Agent
		if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
			//btn_cancel_order.setVisibility(View.INVISIBLE);
		}
		
		btn_pay.setOnClickListener(clickListener);
		btn_cancel_order.setOnClickListener(clickListener);
		btn_back.setOnClickListener(clickListener);
		
		//Get TodayDate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		todayDate = sdf.format(new Date());
		
		
		Log.i("", "Today date: "+todayDate);
		
		//Get Current Time
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		todayTime = sdf2.format(cal.getTime()).toString();
	}

	private void getPermission() {
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("test.starticketmyanmar.com");
        NetworkEngine.getInstance().getPermission(AppLoginUser.getAccessToken(), creditOrder.getAppOperatorId(), new Callback<Response>() {


        	
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "token: "+AppLoginUser.getAccessToken()+", operator id: "+creditOrder.getAppOperatorId());

				
				if (arg0 != null) {
					permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>(){}.getType());
					if (permission != null) {
						Log.i("", "Permission: "+permission.toString());
						
						permit_ip = permission.getIp();
						permit_access_token = permission.getAccess_token();
						permit_operator_group_id = permission.getOperatorgroup_id();
						permit_agent_id = permission.getOnlinesaleagent_id();	
						permit_operator_id = permission.getOperator_id();
					}else {
						Log.i("", "Permission null");
					}
				}
				
				dialog.dismiss();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(creditOrderString != null){
			finish();
		}else{
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				
			    creditOrderString = extras.getString("booking_order");
			}
			
			creditOrder = new Gson().fromJson(creditOrderString, Booking.class);
			
			connectionDetector = SKConnectionDetector.getInstance(BusBookingConfirmDeleteActivity.this);
			if(connectionDetector.isConnectingToInternet()){
				getPermission();
			}else{
				connectionDetector.showErrorDialog();
			}
		}
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			
			if(v == btn_pay){				
				Log.i("", "Permit token: "+permit_access_token);
				
				Intent nextScreen = new Intent(BusBookingConfirmDeleteActivity.this, BusConfirmActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "booking");
				bundle.putString("Operator_Name", creditOrder.getOperator());
				bundle.putString("from_to", creditOrder.getTrip());
				bundle.putString("time", creditOrder.getTime());
				bundle.putString("classes",creditOrder.getClass_());
				bundle.putString("date", creditOrder.getDepartureDate());
				bundle.putString("agent_id", creditOrder.getAgentId());
				bundle.putString("name", creditOrder.getCustomerName());
				bundle.putString("phone", creditOrder.getCustomerPhone());
				bundle.putString("nrc", creditOrder.getCustomerNrc());
				bundle.putString("Price", creditOrder.getPrice());
				bundle.putString("selected_seat",  creditOrder.getSeatNo());
				bundle.putString("sale_order_no", creditOrder.getId());
				bundle.putString("order_date", creditOrder.getDate());
				bundle.putString("bus_occurence", creditOrder.getTripId());
				bundle.putString("ConfirmDate", todayDate);
				bundle.putString("ConfirmTime", todayTime);
				bundle.putString("permit_access_token", permit_access_token);
				bundle.putString("permit_ip", permit_ip);
				bundle.putString("permit_operator_group_id", permit_operator_group_id);
				bundle.putString("permit_agent_id", permit_agent_id);
				bundle.putString("permit_operator_id", permit_operator_id);
				bundle.putString("SeatCount", String.valueOf(creditOrder.getTicketQty()));
				nextScreen.putExtras(bundle);
				startActivity(nextScreen);
			}
			
			if(v == btn_cancel_order){
				/*Intent intent = new Intent(getApplicationContext(), BusBookingDetailActivity.class);
				intent.putExtra("credit_order", new Gson().toJson(creditOrder));
				startActivity(intent);*/
				deleteBooking();
			}
			
			if(v == btn_back){
				finish();
			}
		}
	};

	protected void deleteBooking() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingDeleteByOrderID(AppLoginUser.getAccessToken(), creditOrder.getId(), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "token: "+AppLoginUser.getAccessToken()+", order id: "+creditOrder.getId());
				if (arg0 != null) {
					Log.i("", "Success delete: "+arg0.getStatus());
					SKToastMessage.showMessage(BusBookingConfirmDeleteActivity.this, creditOrder.getId()+" is successfully deleted !", SKToastMessage.SUCCESS);
					finish();
				}
				
				dialog.dismiss();
			}
		});
	}
	
	
	private void payCredit(){
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
/*		NetworkEngine.getInstance().confirmBooking(accessToken, creditOrder.getId().toString(), new Callback<JSONObject>() {
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				SKToastMessage.showMessage(BusBookingConfirmDeleteActivity.this, "Can't confirmed.", SKToastMessage.ERROR);
			}

			public void success(JSONObject arg0, Response arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				SKToastMessage.showMessage(BusBookingConfirmDeleteActivity.this, "Successfully confirm.", SKToastMessage.SUCCESS);
				finish();
			}
		});*/
	}
}
