package com.ignite.mm.ticketing.user;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import info.hoang8f.widget.FButton;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.ForgotPassword;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class ForgetPasswordActivity extends BaseActivity{
	private EditText txt_login_email;
	private FButton btn_search_email;
	private FButton btn_cancel;
	private String user_email = "";
	private SKConnectionDetector connectionDetector;
	private ZProgressHUD dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Send Code");
            this.setSupportActionBar(toolbar);
        }
		
        connectionDetector = SKConnectionDetector.getInstance(this);
        
		txt_login_email = (EditText)findViewById(R.id.txt_login_email);
		btn_search_email = (FButton)findViewById(R.id.txt_search_email);
		btn_search_email.setButtonColor(getResources().getColor(R.color.golden_brown_light));
		btn_search_email.setShadowEnabled(true);
		btn_search_email.setShadowHeight(3);
		btn_search_email.setCornerRadius(7);
		
		btn_cancel   = (FButton)findViewById(R.id.btn_cancel);
		btn_cancel.setButtonColor(getResources().getColor(R.color.gray));
		btn_cancel.setShadowEnabled(true);
		btn_cancel.setShadowHeight(3);
		btn_cancel.setCornerRadius(7);
		
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_search_email.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (checkFields()) {
					user_email = txt_login_email.getText().toString();
					
					if (connectionDetector.isConnectingToInternet()) {
						getForgotPassword();
					}else {
						connectionDetector.showErrorMessage();
					}
				}
			}
		});
	}
	
	private void getForgotPassword() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(ForgetPasswordActivity.this);
		dialog.setMessage("Pls wait ...");
		dialog.show();
		
		Log.i("", "User Email: "+user_email);
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().GetForgotPassword(user_email, new Callback<ForgotPassword>() {
			
			public void success(ForgotPassword arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				if (arg0 != null) {
					if (arg0.getStatus() == 200) {
						
						Bundle bundle = new Bundle();
						bundle.putString("email", user_email);
						startActivity(new Intent(ForgetPasswordActivity.this, ChangePasswordActivity.class).putExtras(bundle));
					}
				}
				dialog.dismissWithSuccess();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				dialog.dismissWithFailure();
			}
		});
	}

	public boolean checkFields() {
		if (txt_login_email.getText().toString().length() == 0) {
			txt_login_email.setError("Enter Your Email");
			return false;
		}
		if (!txt_login_email.getText().toString().contains("@")) {
			txt_login_email.setError("Your email should contain '@'");
			return false;
		}

		return true;

	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
		
	}
}
