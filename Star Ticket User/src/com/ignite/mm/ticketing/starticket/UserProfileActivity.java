package com.ignite.mm.ticketing.starticket;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import jxl.format.VerticalAlignment;
import info.hoang8f.widget.FButton;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout.Alignment;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.Loyalty;
import com.thuongnh.zprogresshud.ZProgressHUD;

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
			if (v == btn_log_out) {
				AppLoginUser.logout();
				finish();
				Toast.makeText(UserProfileActivity.this, "Log Out Success", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private FButton btn_log_out;
	private TextView txt_usable_gift_money;
	private LinearLayout layout_profile_button;
	private ZProgressHUD dialog;
	private TextView txt_userName_login;
	
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_user_profile);
		
		layout_profile_button = (LinearLayout)findViewById(R.id.layout_profile_button);
	//	txt_user_name = (TextView)findViewById(R.id.txt_user_name);
		txt_nrc = (TextView)findViewById(R.id.txt_nrc);
		
		txt_userName_login = (TextView)findViewById(R.id.txt_userName_login);
		txt_email = (TextView)findViewById(R.id.txt_email);
		txt_phone = (TextView)findViewById(R.id.txt_phone);
		txt_address = (TextView)findViewById(R.id.txt_address);
		
		txt_history_points = (TextView)findViewById(R.id.txt_history_points);
		txt_used_points = (TextView)findViewById(R.id.txt_used_points);
		txt_net_points = (TextView)findViewById(R.id.txt_usable_points);
		txt_usable_gift_money  = (TextView)findViewById(R.id.txt_usable_gift_money);
		
		//Check Screen Size
		Configuration config = getResources().getConfiguration();
       
		//For Tablet
		if (config.smallestScreenWidthDp >= 600) {
			layout_profile_button.setOrientation(LinearLayout.HORIZONTAL);
		}else { 
			layout_profile_button.setOrientation(LinearLayout.VERTICAL);
		}
		
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String user_id = pref.getString("user_id", "0");
		
		Log.i("", "user name log in: "+AppLoginUser.getUserNameLogin());
		
		if (Integer.valueOf(user_id) > 0) {
			
			txt_userName_login.setText(AppLoginUser.getUserNameLogin());
			txt_email.setText(AppLoginUser.getEmail());
			txt_phone.setText(AppLoginUser.getPhone());
			txt_address.setText(AppLoginUser.getAddress());
			
			postLoytalty();
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
		
		btn_log_out = (FButton)findViewById(R.id.btn_log_out);
		btn_log_out.setButtonColor(getResources().getColor(R.color.golden_brown));
		btn_log_out.setShadowEnabled(true);
		btn_log_out.setShadowHeight(3);
		btn_log_out.setCornerRadius(7);
		
		btn_edit_info.setOnClickListener(clickListener);
		btn_log_out.setOnClickListener(clickListener);
	}
	
	private void postLoytalty() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(UserProfileActivity.this);
		dialog.show();
		
		Log.i("", "Loyalty Ph: "+AppLoginUser.getPhone()
				+", Payment method: "+1
				);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getLoyaltyByUser(AppLoginUser.getId(), new Callback<Loyalty>() {
			
			private int totalPoints = 0;
			private int totalGiftMoney = 0;

			public void success(Loyalty arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				if (arg0 != null) {
					
					/*Integer current_points = 0;
					
					if (arg0.getCurrent_points() == null) {
						totalPoints = arg0.getPoints();
					}else {
						current_points = Integer.valueOf(arg0.getCurrent_points());
						totalPoints = arg0.getPoints() + current_points;
					}*/
					
					txt_net_points.setText(arg0.getPoints()+"");
					//txt_usable_gift_money.setText(arg0.getGiftMoney()+" Ks");
					
					dialog.dismissWithSuccess();
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				Log.i("", "Error");
				
				dialog.dismissWithFailure();
			}
		});
	}
}
