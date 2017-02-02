package com.ignite.mm.ticketing.gatethein;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.AES;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.EnterIPDialog;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureKey;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
import com.smk.activate.Activation;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class UserLogin extends ActionBarActivity {

	private EditText txtEmail;
	private EditText txtPassword;
	private Context ctx = this;
	private Button[] buttons = new Button[3];
	private ZProgressHUD dialog;
	// private String UserEmail, UserPassword;
	private SKConnectionDetector connectionDetector;
	private String title;

	public static boolean isSkip = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
/*		if (getResources().getString(R.string.str_shwemanthu).equals("lumbini.starticketmyanmar.com")) {
			title = "Lumbini";
		}else if (getResources().getString(R.string.str_shwemanthu).equals("shwemanthu.starticketmyanmar.com")) {
			title = "Shwe Man Thu";
		}else if (getResources().getString(R.string.str_shwemanthu).equals("mdm.starticketmyanmar.com")) {
			title = "Mandalar Min";
		}else {
			title = "User's Login";
		}*/
		
		title = "User's Login";
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle(title);
            this.setSupportActionBar(toolbar);
        }

		txtEmail = (EditText) this.findViewById(R.id.txt_login_email);
		txtPassword = (EditText) this.findViewById(R.id.txt_login_password);

		buttons[0] = (Button) findViewById(R.id.cmd_login);
		buttons[1] = (Button) findViewById(R.id.btn_enter_ip);
		buttons[2] = (Button) findViewById(R.id.cmd_to_register);

		for (int i = 0; i < buttons.length; i++) {

			buttons[i].setOnClickListener(clickListener);

		}
		connectionDetector = SKConnectionDetector.getInstance(this);
		connectionDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if (!connectionDetector.isConnectingToInternet())
			connectionDetector.showErrorMessage();
	}

	private long getSysLongDate() {
		long long_date = 0;
		Calendar c = Calendar.getInstance(Locale.getDefault());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedDate = df.format(c.getTime());
		try {
			Log.i("", "Hello Date String: " + formattedDate);
			long_date = df.parse(formattedDate).getTime() / 1000;
			Log.i("", "Hello Long Date: " + long_date);
			Log.i("", "Hello - Long Date: " + long_date / 3600);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return long_date;
	}
	
	private void getLogin(){
		
		if (connectionDetector.isConnectingToInternet()) {
			if (checkFields()) {
				dialog = new ZProgressHUD(UserLogin.this);
				dialog.show();
				String userEmail = txtEmail.getText().toString();
				/*if (txtEmail.getText().toString().contains("@")) {
					userEmail = txtEmail.getText().toString();
				} else {
					userEmail = txtEmail.getText().toString()
							+ "@gmail.com";
				}*/
				
				String param = MCrypt.getInstance().encrypt(SecureParam.getAccessTokenParam(SecureKey.getGrant(), SecureKey.getId(), SecureKey.getKey(), MCrypt.getInstance().encrypt(userEmail), MCrypt.getInstance().encrypt(txtPassword.getText().toString()), SecureKey.getScope(), SecureKey.getState()));
				Log.i("", "Hello Param: " + param);
				NetworkEngine.getInstance().getAccessToken(param, new Callback<Response>() {

					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						Log.i("", "fail ....."+arg0.getCause());
						if (arg0.getResponse() != null) {
							if (arg0.getResponse().getStatus() == 400) {
								SKToastMessage
										.showMessage(
												UserLogin.this,
												"Check Email or Password",
												SKToastMessage.ERROR);
							}
						}
						
						SKToastMessage
						.showMessage(
								UserLogin.this,
								"Check Email or Password",
								SKToastMessage.ERROR);
						
						if (dialog != null) {
							dialog.dismissWithFailure();
						}
					}

					public void success(Response arg0, Response arg1) {
						Log.i("","Hello Header: "+ arg1.getHeaders().toString());
						// TODO Auto-generated method stub
						if (dialog != null) {
							dialog.dismissWithSuccess();
						}
						
						//Log.i("", " ....."+arg0.toString());
						
						AccessToken _token = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AccessToken>() {}.getType());
						LoginUser user = new LoginUser(UserLogin.this);
						user.setAccessToken(_token.getAccess_token());
						user.setTokenType(_token.getToken_type());
						user.setExpires(_token.getExpires());
						user.setExpiresIn(_token.getExpires_in());
						user.setRefreshToken(_token.getRefresh_token());
						user.setUserID(_token.getUser().getId());
						user.setUserGroupID(_token.getUser().getOperatorgroup_id());
						user.setLoginUserID(_token.getUser().getUser_id());
						user.setUserName(_token.getUser().getName());
						user.setUserType(_token.getUser().getType());
						user.setUserRole(_token.getUser().getRole());
						user.login();

						SharedPreferences sharedPreferences = getSharedPreferences("old_sale",MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.clear();
						editor.commit();
						editor.putString("working_date", "");
						editor.commit();
						Intent intent = new Intent(getApplicationContext(),	BusMenuActivity.class);
						startActivity(intent);
					}
				});
			}
		} else {
			connectionDetector.showErrorMessage();
		}
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// for Login button
			if (v == buttons[0]) {
				getLogin();
			}
			// for skip button
			if (v == buttons[1]) {
				EnterIPDialog dialog = new EnterIPDialog(UserLogin.this);
				dialog.setCallbackListener(new EnterIPDialog.Callback() {
					
					public void onSetIP(String ip) {
						// TODO Auto-generated method stub
						NetworkEngine.setIP(ip);
					}
					
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
			}

		}
	};
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}

	public boolean checkFields() {
		if (txtEmail.getText().toString().length() == 0) {
			txtEmail.setError("Enter The User Email");
			return false;
		}
		if (txtPassword.getText().toString().length() == 0) {
			txtPassword.setError("Enter The User Password");
			return false;
		}

		return true;

	}

}