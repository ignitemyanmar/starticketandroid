package com.ignite.mm.ticketing.user;

import info.hoang8f.widget.FButton;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;

public class UserProfileActivity extends BaseActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private Button btn_order_history;
	private Button btn_my_booking;
	private FButton btn_edit_info;
	private TextView txt_user_name;
	private TextView txt_nrc;
	private TextView txt_email;
	private TextView txt_phone;
	private TextView txt_address;
	private TextView txt_history_points;
	private TextView txt_used_points;
	private TextView txt_net_points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_edit_info) {
				startActivity(new Intent(UserProfileActivity.this, UserEditProfileActivity.class));
			}
			if (v == btn_order_history) {
				startActivity(new Intent(UserProfileActivity.this, ThreeDaySalesActivity.class));
			}
			if (v == btn_my_booking) {
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservationUser");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
			}
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_user_profile);
		
	//	txt_user_name = (TextView)findViewById(R.id.txt_user_name);
		txt_nrc = (TextView)findViewById(R.id.txt_nrc);
		txt_email = (TextView)findViewById(R.id.txt_email);
		txt_phone = (TextView)findViewById(R.id.txt_phone);
		txt_address = (TextView)findViewById(R.id.txt_address);
		
		txt_history_points = (TextView)findViewById(R.id.txt_history_points);
		txt_used_points = (TextView)findViewById(R.id.txt_used_points);
		txt_net_points = (TextView)findViewById(R.id.txt_net_points);
		
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String user_id = pref.getString("user_id", "0");
		
		if (Integer.valueOf(user_id) > 0) {
			//txt_user_name.setText(pref.getString("user_name", ""));
			//txt_nrc.setText(AppLoginUser.getUser().get);
			txt_email.setText(pref.getString("email", ""));
			txt_phone.setText(pref.getString("phone", ""));
			txt_address.setText(pref.getString("address", ""));
		}else {
			finish();
		}
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle(pref.getString("user_name", ""));
            this.setSupportActionBar(toolbar);
        }
		
		btn_edit_info = (FButton)findViewById(R.id.btn_edit_info);
		btn_edit_info.setButtonColor(getResources().getColor(R.color.yellow));
		btn_edit_info.setShadowEnabled(true);
		btn_edit_info.setShadowHeight(3);
		btn_edit_info.setCornerRadius(7);
		
		btn_order_history = (Button)findViewById(R.id.btn_order_history);
		btn_my_booking = (Button)findViewById(R.id.btn_my_booking);
		
		btn_edit_info.setOnClickListener(clickListener);
		btn_order_history.setOnClickListener(clickListener);
		btn_my_booking.setOnClickListener(clickListener);
	}
}
