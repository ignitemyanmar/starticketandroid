package com.ignite.mm.ticketing.gatethein;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureKey;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.AccessToken;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
	private Context ctx = this;
	private SKConnectionDetector connectionDetector;
	private ZProgressHUD dialog;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// RunAnimations_for_FadeOut();
		Thread splashTread = new Thread() {

			@Override
			public void run() {
				try {
					// int waited = 0;

					// while(_active && (waited < _splashTime)) {

					sleep(1000);

					// if(_active) {

					// waited += 500;
					// }
					// }
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();

					startActivity(new Intent(ctx, UserLogin.class));

				}
			}
		};

		splashTread.start();
		
		connectionDetector = SKConnectionDetector.getInstance(this);
		//getLogin();

	}
	
	private void getLogin(){
		if (connectionDetector.isConnectingToInternet()) {
			dialog = new ZProgressHUD(MainActivity.this);
			dialog.show();
			String userEmail = "lumbini@gmail.com";
			String userPassword = "@lumbini";
			String param = MCrypt.getInstance().encrypt(SecureParam.getAccessTokenParam(SecureKey.getGrant(), SecureKey.getId(), SecureKey.getKey(), MCrypt.getInstance().encrypt(userEmail), MCrypt.getInstance().encrypt(userPassword), SecureKey.getScope(), SecureKey.getState()));
			NetworkEngine.getInstance().getAccessToken(param, new Callback<Response>() {

				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					dialog.dismissWithFailure();
					if (arg0.getResponse() != null) {
						if (arg0.getResponse().getStatus() == 400) {
							SKToastMessage
									.showMessage(
											MainActivity.this,
											"သင္�?? Login Email �?ွင့္ Password ဟာ မွား ေနပါသည္",
											SKToastMessage.ERROR);
						}
					}
				}

				public void success(Response arg0, Response arg1) {
					Log.i("","Hello Header: "+ arg1.getHeaders().toString());
					// TODO Auto-generated method stub
					dialog.dismissWithSuccess();
					AccessToken _token = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AccessToken>() {}.getType());
					LoginUser user = new LoginUser(MainActivity.this);
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
					finish();
					Intent intent = new Intent(getApplicationContext(),	BusTripsCityActivity.class);
					startActivity(intent);
				}
			});
			
		} else {
			connectionDetector.showErrorMessage();
		}
	}

	
}
