package com.ignite.mm.ticketing.agent;

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
import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
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
<<<<<<< HEAD
	private String userRole;
	private TextView txt_book_by_user;
=======
>>>>>>> 1109c570512a580f3b31c10ada00f7c782daab49

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			login_name = bundle.getString("login_name");
<<<<<<< HEAD
			userRole = bundle.getString("userRole");
=======
>>>>>>> 1109c570512a580f3b31c10ada00f7c782daab49
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
		actionBarTitle.setText("Menu");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		

		//Check Screen Size
		Configuration config = getResources().getConfiguration();
       
		//For Tablet
		if (config.smallestScreenWidthDp >= 600) {
			setContentView(R.layout.activity_home);
		}else {
			setContentView(R.layout.activity_home_phone);
		}
		
		btn_sale_tickets = (Button)findViewById(R.id.btn_sale_tickets);
		btn_book_confirm = (Button)findViewById(R.id.btn_book_confirm);
		btn_book_confirm_user = (Button)findViewById(R.id.btn_book_confirm_user);
<<<<<<< HEAD
		txt_book_by_user = (TextView)findViewById(R.id.txt_book_by_user);
		
		Log.i("", "role : "+userRole+", user name: "+login_name);
		
		if (userRole != null) {
			if (Integer.valueOf(userRole) <= 3) {
				txt_book_by_user.setVisibility(View.GONE);
				btn_book_confirm_user.setVisibility(View.GONE);
			}
		}
=======
>>>>>>> 1109c570512a580f3b31c10ada00f7c782daab49
		//btn_book_confirm_user.setText("Reservation Confirm ("+login_name+")");
		btn_three_day_sales = (Button)findViewById(R.id.btn_three_day_sales);
		
		btn_sale_tickets.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "sale");
				startActivity(new Intent(getApplicationContext(), SaleTicketActivity.class).putExtras(bundle));
			}
		});
		
		btn_book_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservation");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
			}
		});
		
		btn_book_confirm_user.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservationUser");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
			}
		});
		
		btn_three_day_sales.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ThreeDaySalesActivity.class));
			}
		});
	}
}
