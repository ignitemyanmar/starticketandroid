package com.ignite.mm.ticketing.user;

import info.hoang8f.widget.FButton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class UserLogin extends BaseActivity {

	private EditText txtEmail;
	private EditText txtPassword;
	
	private Context ctx = this;
	private FButton[] buttons = new FButton[3];
	private ZProgressHUD dialog;
//	private String UserEmail, UserPassword;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	
	private SKConnectionDetector connectionDetector;
	private TextView actionBarTitle2;
	private TextView txt_forget_password;
	public static boolean isSkip = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login_phone);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Log In");
            this.setSupportActionBar(toolbar);
        }
        
		//Check Screen Size
/*		Configuration config = getResources().getConfiguration();
       
		//For Tablet
		if (config.smallestScreenWidthDp >= 600) {
			setContentView(R.layout.activity_login);
		}else {
			setContentView(R.layout.activity_login_phone);
		}*/
		
		txtEmail = (EditText) this.findViewById(R.id.txt_login_email);
		txtPassword = (EditText) this.findViewById(R.id.txt_login_password);
		
		txt_forget_password = (TextView)this.findViewById(R.id.txt_forget_password);
		txt_forget_password.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(UserLogin.this, ForgetPasswordActivity.class));
			}
		});

		buttons[0] = (FButton) findViewById(R.id.btn_login);
		
		//FButton fBtn = new FButton(getApplicationContext());
		buttons[0].setButtonColor(getResources().getColor(R.color.golden_brown_light));
		buttons[0].setShadowEnabled(true);
		buttons[0].setShadowHeight(3);
		buttons[0].setCornerRadius(7);
		
		buttons[1] = (FButton) findViewById(R.id.btn_skip_login);
		
		buttons[2] = (FButton) findViewById(R.id.btn_register);
		buttons[2].setButtonColor(getResources().getColor(R.color.yellow));
		buttons[2].setShadowEnabled(true);
		buttons[2].setShadowHeight(3);
		buttons[2].setCornerRadius(7);

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setOnClickListener(clickListener);
		}
		
		connectionDetector = SKConnectionDetector.getInstance(this);
		//connectionDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(!connectionDetector.isConnectingToInternet())
			connectionDetector.showErrorMessage();
		
	}

	private OnClickListener clickListener = new OnClickListener() {

		private String userEmail;

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
			//for Login button
			if (v == buttons[0]) {

				if(connectionDetector.isConnectingToInternet()){
					
					if(checkFields()){
						
						dialog = new ZProgressHUD(UserLogin.this);
		    			dialog.show();
		    			
		    			/*if(txtEmail.getText().toString().contains("@")){		    				
		    				userEmail = txtEmail.getText().toString();
		    			}else{
		    				userEmail = txtEmail.getText().toString()+"@gmail.com";
		    			}*/
		    			
		    			Log.i("", "Enter here..... log in");
		    			//Check Email & Password on Server
		    			
		    			NetworkEngine.setIP("starticketmyanmar.com");
						NetworkEngine.getInstance().getAccessToken("password", "clientID22222", "scrt123321098765432", txtEmail.getText().toString(), txtPassword.getText().toString(), "", "", new Callback<AccessToken>() {
							
							private String userName;

							public void success(AccessToken arg0, Response arg1) {
								// TODO Auto-generated method stub
								dialog.dismissWithSuccess();
								
								Log.i("", "Log in Success: "+arg0.toString());
		   						
								if (arg1.getStatus() == 200) {
									if (arg0 != null) {
										LoginUser user = new LoginUser(UserLogin.this);
										user.setId(arg0.getId());
										user.setName(arg0.getName());
										user.setEmail(arg0.getEmail());
										user.setCodeNo(arg0.getCodeNo());
										user.setPhone(arg0.getPhone());
										user.setAddress(arg0.getAddress());
										user.setAgentGroupName(arg0.getAgentgroup_name());
										user.setRole(String.valueOf(arg0.getRole()));
										user.setAgentGroupId(String.valueOf(arg0.getAgentgroupId()));
										user.setGroupBranch(String.valueOf(arg0.getGroupBranch()));
										user.setAccessToken(arg0.getAccessToken());
										user.setCreateAt(arg0.getCreatedAt());
										user.setUpdateAt(arg0.getUpdatedAt());
										user.setPoints(arg0.getPoints());
										user.setGift_moneys(arg0.getGift_moneys());
										user.login();
									}
									
									
									//startActivity(new Intent(UserLogin.this, BusOperatorActivity.class));
								}

								if(isSkip){
									isSkip = false;
									//Intent intent = new Intent(getApplicationContext(),	Bus_Info_Activity.class);
									finish();
									//startActivity(intent);
								}else{
									Log.i("", "Enter here.... home act:");
									/*LoginUser user = new LoginUser(UserLogin.this);
									Bundle bundle = new Bundle();
									bundle.putString("login_name", user.getUserName());
									bundle.putString("userRole", user.getRole());
									
									Log.i("", "name : "+user.getUserName()+", role: "+user.getRole());*/
									
									finish();
									
									/*Intent intent = new Intent(getApplicationContext(),	SaleTicketActivity.class).putExtras(bundle);
									//Intent intent = new Intent(getApplicationContext(),	BusOperatorActivity.class);
			   						startActivity(intent);*/
			   						//finish();
								}
							}
							
							public void failure(RetrofitError arg0) {
								// TODO Auto-generated method stub
								Log.i("", "Enter here... log in fail: "+arg0.getCause());
								
								dialog.dismissWithFailure();
								
								if(arg0.getResponse() != null){
									Log.i("", "Log in Fail resp: "+arg0.getResponse().getStatus());
									if(arg0.getResponse().getStatus() == 400){
										SKToastMessage.showMessage(UserLogin.this, "Check your Email, Phone (or) Password", SKToastMessage.ERROR);
									}else if(arg0.getResponse().getStatus() == 401){
										SKToastMessage.showMessage(UserLogin.this, "Check your Email, Phone (or) Password", SKToastMessage.ERROR);
									}else if(arg0.getResponse().getStatus() == 403){
										SKToastMessage.showMessage(UserLogin.this, "Check your Email, Phone (or) Password", SKToastMessage.ERROR);
									}else{
										SKToastMessage.showMessage(UserLogin.this, "Check your Email, Phone (or) Password", SKToastMessage.ERROR);
									}
								}
							}
						});
					}
				}else{
					
					connectionDetector.showErrorMessage();
					SharedPreferences sharedPreferences = ctx.getSharedPreferences("User",MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					
					editor.clear();
					editor.commit();
					editor.putString("access_token", null);
					editor.putString("token_type", null);
					editor.putLong("expires", 0);
					editor.putLong("expires_in", 0);
					editor.putString("refresh_token", null);
					editor.putString("user_id", "1");
					editor.putString("user_name", "Elite");
					editor.putString("user_type", "operator");
					editor.commit();
					//Intent intent = new Intent(getApplicationContext(),	BusTripsCityActivity.class);
					//finish();
					//startActivity(intent);
				}
			}//End Log in button
			
/*			//for skip button
			if(v == buttons[1])
			{
				SharedPreferences sharedPreferences = ctx.getSharedPreferences("User",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				
				editor.clear();
				editor.commit();
				
				isSkip = true;
				
				editor.putString("useremail", null);
				editor.commit();
			}*/
			
			// for User Register
			if (v == buttons[2]) {
				startActivity(new Intent(UserLogin.this, UserRegister.class));
			}

		}
	};
	
	public boolean checkFields() {
		if (txtEmail.getText().toString().length() == 0) {
			txtEmail.setError("Enter Your Email");
			return false;
		}
		if (txtPassword.getText().toString().length() == 0) {
			txtPassword.setError("Enter Your Password");
			return false;
		}

		return true;

	}
	

    protected void yesNoAlert() {
    	
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        
        alert.setMessage("Are you sure you want to exit the app?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
       
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
        alert.show();
    }
    
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
