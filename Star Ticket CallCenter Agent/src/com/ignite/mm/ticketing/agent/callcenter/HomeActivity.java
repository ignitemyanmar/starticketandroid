package com.ignite.mm.ticketing.agent.callcenter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class HomeActivity extends ActionBarActivity{

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
	private Button btn_sale_by_tripDate;
	private Button btn_delivery_list;
	private TextView txt_delivery;
	private TextView txt_khoneat;
	private Button btn_agent_seats_booking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			login_name = bundle.getString("login_name");
			userRole = bundle.getString("userRole");
		}
		
/*		actionBar = getSupportActionBar();
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
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/
		
		setContentView(R.layout.activity_home);
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Menu");
            this.setSupportActionBar(toolbar);
        }

		
		//Check Screen Size
		//Configuration config = getResources().getConfiguration();
       
		
		
		/*//For Tablet
		if (config.smallestScreenWidthDp >= 600) {
			setContentView(R.layout.activity_home);
		}else {
			//setContentView(R.layout.activity_home_phone);
		}*/
		
        btn_agent_seats_booking = (Button)findViewById(R.id.btn_agent_seats_booking);
		btn_sale_tickets = (Button)findViewById(R.id.btn_sale_tickets);
		btn_book_confirm = (Button)findViewById(R.id.btn_book_confirm);
		btn_book_confirm_user = (Button)findViewById(R.id.btn_book_confirm_user);
		txt_book_by_user = (TextView)findViewById(R.id.txt_book_by_user);
		btn_delivery_list = (Button)findViewById(R.id.btn_delivery_list);
		btn_sale_by_tripDate = (Button)findViewById(R.id.btn_khoneat);
		txt_delivery = (TextView)findViewById(R.id.txt_delivery);
		txt_khoneat = (TextView)findViewById(R.id.txt_khoneat);
		
		Log.i("", "role : "+userRole+", user name: "+login_name);
		
		if (userRole != null) {
			//If Agents
			if (Integer.valueOf(userRole) <= 3) {
				//For Agent
				txt_book_by_user.setVisibility(View.GONE);
				btn_book_confirm_user.setVisibility(View.GONE);
				
				txt_delivery.setVisibility(View.GONE);
				btn_delivery_list.setVisibility(View.GONE);
				
				txt_khoneat.setVisibility(View.GONE);
				btn_sale_by_tripDate.setVisibility(View.GONE);
				
				btn_agent_seats_booking.setVisibility(View.GONE);
			}
		}
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
		
		btn_sale_by_tripDate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), SaleByTripDateActivity.class));
			}
		});
		
		btn_delivery_list.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), DeliveryActivity.class));
			}
		});
		
		btn_agent_seats_booking.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AgentSeatsBookingActivity.class));
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
}
