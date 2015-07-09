package com.ignite.mm.ticketing.user;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.user.R;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class HomeActivity extends SherlockActivity{

	private Button btn_sale_tickets;
	private Button btn_book_confirm;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private Button btn_three_day_sales;
	private Button btn_book_confirm_user;
	private String login_name;
	private String userRole;
	
	private TextView txt_book_by_user;
	private Button btn_myPoints;
	private Button btn_myProfile;
	private Button btn_busProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			login_name = bundle.getString("login_name");
			userRole = bundle.getString("userRole");
		}
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		//actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBarTitle.setText("မာတိကာ");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		

		//Check Screen Size
		Configuration config = getResources().getConfiguration();
       
		setContentView(R.layout.activity_home_phone);
		
		//For Tablet
		if (config.smallestScreenWidthDp >= 600) {
			//setContentView(R.layout.activity_home);
		}else {
			//setContentView(R.layout.activity_home_phone);
		}
		
		btn_sale_tickets = (Button)findViewById(R.id.btn_sale_tickets);
		btn_book_confirm = (Button)findViewById(R.id.btn_book_confirm);
		btn_book_confirm_user = (Button)findViewById(R.id.btn_book_confirm_user);
		//txt_book_by_user = (TextView)findViewById(R.id.txt_book_by_user);
		
		Log.i("", "role : "+userRole+", user name: "+login_name);
		
		if (userRole != null) {
			if (Integer.valueOf(userRole) <= 3) {
				//txt_book_by_user.setVisibility(View.GONE);
				//btn_book_confirm_user.setVisibility(View.GONE);
			}
		}
		//btn_book_confirm_user.setText("Reservation Confirm ("+login_name+")");
		btn_three_day_sales = (Button)findViewById(R.id.btn_three_day_sales);
		btn_myPoints = (Button) findViewById(R.id.btn_myPoints);
		btn_myProfile = (Button) findViewById(R.id.btn_myProfile);
		btn_busProfile = (Button)findViewById(R.id.btn_busProfile);
		
		btn_book_confirm.setOnClickListener(clickListener);
		btn_book_confirm_user.setOnClickListener(clickListener);
		btn_sale_tickets.setOnClickListener(clickListener);
		btn_three_day_sales.setOnClickListener(clickListener);
		btn_myPoints.setOnClickListener(clickListener);
		btn_myProfile.setOnClickListener(clickListener);
		btn_busProfile.setOnClickListener(clickListener);
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_sale_tickets) {
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "sale");
				startActivity(new Intent(getApplicationContext(), SaleTicketActivity.class).putExtras(bundle));
			}
			if (v == btn_book_confirm) {
				/*Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservation");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));*/
			}
			if (v == btn_book_confirm_user) {
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservationUser");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
			}
			if (v == btn_three_day_sales) {
				startActivity(new Intent(getApplicationContext(), ThreeDaySalesActivity.class));
			}
			if (v == btn_myPoints) {
				//startActivity(new Intent(getApplicationContext(), User.class));
			}
			if (v == btn_myProfile) {
				startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
			}
			if (v == btn_busProfile) {
				//do some
			}
		}
	};
}
