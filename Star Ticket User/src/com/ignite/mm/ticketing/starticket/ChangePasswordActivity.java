package com.ignite.mm.ticketing.starticket;

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
import android.widget.GridView;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.ForgotPassword;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #ChangePasswordActivity} is the class to change Password 
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * (1) {@link #getResetPassword()}
 * (1) {@link #checkFields()}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class ChangePasswordActivity extends BaseActivity{
	private TextView txt_login_email;
	private FButton btn_continue;
	private FButton btn_cancel;
	private String email;
	private EditText txt_code;
	private EditText txt_password;
	private EditText txt_confirm_password;
	protected String new_password;
	protected SKConnectionDetector connectionDetector;
	private ZProgressHUD dialog;
	protected String code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Reset Password");
            this.setSupportActionBar(toolbar);
        }
        
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
        	email = bundle.getString("email");
		}
        
        connectionDetector = SKConnectionDetector.getInstance(this);
        
		txt_login_email = (TextView)findViewById(R.id.txt_email);
		txt_login_email.setText("**We've already sent the Code to your mail: ("+email+"). Enter it below"); 
		
		txt_code = (EditText)findViewById(R.id.txt_code);
		txt_password  = (EditText)findViewById(R.id.txt_password);
		txt_confirm_password = (EditText)findViewById(R.id.txt_confirm_password);
		
		btn_continue = (FButton)findViewById(R.id.btn_continue);
		btn_continue.setButtonColor(getResources().getColor(R.color.golden_brown_light));
		btn_continue.setShadowEnabled(true);
		btn_continue.setShadowHeight(3);
		btn_continue.setCornerRadius(7);
		
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
		
		btn_continue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkFields()) {
					code = txt_code.getText().toString();
					new_password = txt_password.getText().toString();
					
					if (connectionDetector.isConnectingToInternet()) {
						getResetPassword();
					}else {
						connectionDetector.showErrorMessage();
					}
				}
				startActivity(new Intent(ChangePasswordActivity.this, UserProfileActivity.class));
			}
		});
	}
	
	/**
	 *  Get code from Gmail and input (the code, new password and email) to change new password
	 */
	private void getResetPassword() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(ChangePasswordActivity.this);
		dialog.setMessage("Pls wait ...");
		dialog.show();
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().GetResetPassword(email, code, new_password, new Callback<ForgotPassword>() {
			
			public void success(ForgotPassword arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					if (arg0.getStatus() == 400) {
						SKToastMessage.showMessage(ChangePasswordActivity.this, "Your account has already been reset password", SKToastMessage.ERROR);
					}

					if (arg0.getStatus() == 200) {
						
						SKToastMessage.showMessage(ChangePasswordActivity.this, "Change Password Success", SKToastMessage.SUCCESS);
						closeAllActivities();
						startActivity(new Intent(ChangePasswordActivity.this, SaleTicketActivity.class));
					}
				}
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				dialog.dismissWithFailure();
			}
		});
	}

	/**
	 * @return If code, password, confirm password is null, return false. 
	 * If password is less than 6 char, return false
	 */
	private boolean checkFields() {
		if (txt_code.getText().toString().length() == 0) {
			txt_code.setError("Enter the code we've sent to your email");
			return false;
		}
		if (txt_password.getText().toString().length() == 0) {
			txt_password.setError("Enter New Password");
			return false;
		}
		if (txt_password.getText().toString().length() < 6) {
			txt_password.setError("Enter minimum six characters!");
			return false;
		}
		if (txt_confirm_password.getText().toString().length() == 0) {
			txt_confirm_password.setError("Enter New Password again");
			return false;
		}
		if (!txt_confirm_password.getText().toString().equals(txt_password.getText().toString())) {
			txt_confirm_password.setError("Two passwords are not same!");
			return false;
		}

		return true;

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

