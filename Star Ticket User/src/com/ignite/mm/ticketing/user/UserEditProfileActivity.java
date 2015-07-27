package com.ignite.mm.ticketing.user;

import info.hoang8f.widget.FButton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class UserEditProfileActivity extends BaseActivity {

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private FButton btn_cancel;
	private EditText editTxt_name;
	private EditText editTxt_nrc;
	private EditText editTxt_email;
	private EditText editTxt_current_password;
	private EditText editTxt_new_password;
	private EditText editTxt_mobile;
	private EditText editTxt_homePh;
	private EditText editTxt_address;
	private FButton btn_done;
	private SKConnectionDetector skDetector;
	private ZProgressHUD dialog;
	protected String Name = "";
	protected String CurrentPassword = "";
	protected String NewPassword = "";
	protected String Phone = "";
	protected String Address = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user_profile_edit);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("ကုိယ္ပုိင္ အခ်က္အလက္ ျပင္ရန္");
            this.setSupportActionBar(toolbar);
        }
		
		editTxt_name = (EditText)findViewById(R.id.editTxt_name);
		editTxt_nrc = (EditText)findViewById(R.id.editTxt_nrc);
		editTxt_current_password = (EditText)findViewById(R.id.editTxt_current_password);
		editTxt_new_password  = (EditText)findViewById(R.id.editTxt_new_password);
		editTxt_mobile  = (EditText)findViewById(R.id.editTxt_mobile);
		editTxt_address = (EditText)findViewById(R.id.editTxt_address);
		
		editTxt_name.setText(AppLoginUser.getUserName());
		//editTxt_nrc.setText(AppLoginUser.getNRC());
		editTxt_mobile.setText(AppLoginUser.getPhone());
		editTxt_address.setText(AppLoginUser.getAddress());
		
		btn_cancel = (FButton)findViewById(R.id.btn_cancel);
		btn_cancel.setButtonColor(getResources().getColor(R.color.gray));
		btn_cancel.setShadowEnabled(true);
		btn_cancel.setShadowHeight(3);
		btn_cancel.setCornerRadius(7);
		
		btn_done = (FButton)findViewById(R.id.btn_done);
		btn_done.setButtonColor(getResources().getColor(R.color.yellow));
		btn_done.setShadowEnabled(true);
		btn_done.setShadowHeight(3);
		btn_done.setCornerRadius(7);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_done.setOnClickListener(clickListener);
		
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
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
			if (v == btn_cancel) {
				finish();
			}
			if (v == btn_done) {
				
				if (checkfields()) {
					Name = editTxt_name.getText().toString();
					//NRC = editTxt_nrc.getText().toString();
					CurrentPassword = editTxt_current_password.getText().toString();
					NewPassword = editTxt_new_password.getText().toString();
					Phone = editTxt_mobile.getText().toString();
					Address = editTxt_address.getText().toString();
					
					if(skDetector.isConnectingToInternet()){
						postEditProfile();
					}else{
						skDetector.showErrorDialog();
					}
				}
			}
		}
	};
	

	private void postEditProfile() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(UserEditProfileActivity.this);
		dialog.show();
		
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().postEditProfile(Name
				, AppLoginUser.getEmail(), NewPassword, Phone, Address, AppLoginUser.getAccessToken()
				, CurrentPassword, AppLoginUser.getId(), new Callback<LoginUser>() {
					
					public void success(LoginUser arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg0 != null) {
							if(arg1.getStatus() == 200){
								
								SharedPreferences sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPreferences.edit();
								
								editor.clear();
								editor.commit();
								
								editor.putString("message", arg0.getMessage());
								editor.putString("user_id", String.valueOf(arg0.getUser().getId()));
								editor.putString("user_name", arg0.getUser().getName());
								editor.putString("email", arg0.getUser().getEmail());
								editor.putString("code_no", arg0.getUser().getCodeNo());
								editor.putString("role", String.valueOf(arg0.getUser().getRole()));
								editor.putString("agent_group_id", String.valueOf(arg0.getUser().getAgentgroupId()));
								editor.putString("group_branch", String.valueOf(arg0.getUser().getGroupBranch()));
								editor.putString("create_at", arg0.getUser().getCreatedAt());
								editor.putString("update_at", arg0.getUser().getUpdatedAt());
								editor.putString("phone", arg0.getUser().getPhone());
								editor.putString("address", arg0.getUser().getAddress());
								editor.putString("agentGroupName", arg0.getUser().getAgentgroupName());
								editor.commit();
								
								SKToastMessage.showMessage(UserEditProfileActivity.this, "Updated", SKToastMessage.SUCCESS);
								finish();
							}
							
							/*if(arg1.getStatus() == 400){
								SKToastMessage.showMessage(UserEditProfileActivity.this, "သင္ ႐ုိက္ ထည့္ ေသာ Password အ ေဟာင္း မွားေနပါသည္", SKToastMessage.ERROR);
							}*/
						}
						
						dialog.dismissWithSuccess();
					}
					
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							
							Log.i("", "Update Info Error: "+arg0.getResponse().getStatus()+", "
									+arg0.getResponse().getStatus());
							
							if(arg0.getResponse().getStatus() == 400){
								SKToastMessage.showMessage(UserEditProfileActivity.this, "Check your password or phone no.", SKToastMessage.ERROR);
							}else if(arg0.getResponse().getStatus() == 500){
								SKToastMessage.showMessage(UserEditProfileActivity.this, "Server တစ္ခုခု ခ်ိဳ႕ ယြင္း ေနပါသည္", SKToastMessage.ERROR);
							}else {
								SKToastMessage.showMessage(UserEditProfileActivity.this, "Check your password or phone no.", SKToastMessage.ERROR);
							}
						}
						
						dialog.dismissWithFailure();
					}
				});
	}


	private boolean checkfields() {
		// TODO Auto-generated method stub
		if (editTxt_current_password.getText().toString().length() == 0) {
			editTxt_current_password.setError("Enter old password");
			editTxt_current_password.setFocusable(true);
			return false;
		}
		if (editTxt_name.getText().toString().length() == 0) {
			editTxt_name.setError("Enter Your Name");
			editTxt_name.setFocusable(true);
			return false;
		}
		if(editTxt_mobile.getText().toString().length() == 0){
			editTxt_mobile.setError("Enter Phone No");
			editTxt_mobile.setFocusable(true);
			
			return false;
		}
		if(editTxt_mobile.getText().toString().length() < 6)
    	{
			editTxt_mobile.setError("Enter at least '6' numbers");
			editTxt_mobile.setFocusable(true);
			return false;
		}
    	if (!editTxt_mobile.getText().toString().startsWith("09") && !editTxt_mobile.getText().toString().startsWith("01")) {
    		editTxt_mobile.setError("Enter only start with '09 (or) 01'");
    		editTxt_mobile.setFocusable(true);
    		return false;
		}
		return true;
	}
}
