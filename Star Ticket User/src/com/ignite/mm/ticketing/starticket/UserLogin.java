package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mm.ticketing.sqlite.database.model.BundleListObjSeats;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.ignite.mm.ticketing.starticket.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #UserLogin} is the class to log in for buying tickets
 * <p>
 * Private methods
 * (1) {@link #clickListener}
 * (2) Forgot password (button) clicked, go next activity {@link ForgetPasswordActivity}
 * (3) {@link #checkFields()}
 * (4) {@link #getSupportParentActivityIntent()}
 * (5) {@link #postSale(String)}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class UserLogin extends BaseActivity {

	private EditText txtEmail;
	private EditText txtPassword;
	private Context ctx = this;
	private FButton[] buttons = new FButton[3];
	private ZProgressHUD dialog;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private SKConnectionDetector connectionDetector;
	private TextView actionBarTitle2;
	private TextView txt_forget_password;
	private String FromCity;
	private String ToCity;
	private String Operator_Name;
	private String from_to;
	private String time;
	private String classes;
	private String date;
	private String Price;
	private String ConfirmDate;
	private String ConfirmTime;
	private BundleListObjSeats seat_List;
	private String BusOccurence;
	private String Selected_seats;
	private String permit_operator_id;
	private String permit_operator_group_id;
	private String Permit_agent_id;
	private String permit_access_token;
	private String permit_ip;
	private String from_intent = "";
	private String SeatCount;
	private int trip_type;
	private String return_date;
	private String FromName;
	private String ToName;
	public static boolean isSkip = false;
	private Integer isBooking = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Show view for User Log in (Email/UserName/Phone), Password, forgot password and Register
		setContentView(R.layout.activity_login_phone);
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Log In");
            this.setSupportActionBar(toolbar);
        }
        
        Bundle bundle = getIntent().getExtras();
        
		if (bundle != null) {
			
			from_intent = bundle.getString("from_intent");
			FromCity = bundle.getString("FromCity");
			ToCity = bundle.getString("ToCity");
			Operator_Name = bundle.getString("Operator_Name");
			from_to = bundle.getString("from_to");
			FromName = bundle.getString("FromName");
			ToName = bundle.getString("ToName");
			time = bundle.getString("time");
			classes = bundle.getString("classes");
			date = bundle.getString("date");
			Price  = bundle.getString("Price");
			ConfirmDate  = bundle.getString("ConfirmDate");
			ConfirmTime  = bundle.getString("ConfirmTime");
			seat_List  = new Gson().fromJson(bundle.getString("seat_List"), BundleListObjSeats.class);
			BusOccurence = bundle.getString("bus_occurence");
			
			Selected_seats = bundle.getString("Selected_seats");
			permit_operator_id = bundle.getString("permit_operator_id");
			permit_operator_group_id = bundle.getString("permit_operator_group_id");
			Permit_agent_id = bundle.getString("permit_agent_id");
			permit_access_token = bundle.getString("permit_access_token");
			permit_ip = bundle.getString("permit_ip");
			
			SeatCount = bundle.getString("SeatCount");
			
			trip_type = bundle.getInt("trip_type");
			return_date = bundle.getString("return_date");
			
			Log.i("", "from intent: "+from_intent+", from city: "+FromCity+", tocity: "+ToCity);
		}else {
			Log.i("", "Bundle is null........ !!");
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
			Toast.makeText(UserLogin.this, "No Network connection", Toast.LENGTH_SHORT).show();
		
	}

	/**
	 * (1) Log in button clicked: if username/email/phone is valid, 
	 * get accesstoken + user's info and save in {@link BaseActivity} to call user's info anytime. 
	 * <p>
	 * And logIn activity is from {@link UserLogin}, {@link BusBookingListActivity}, {@link ThreeDaySalesActivity}, 
	 * just close the activity after log in success. 
	 * <p>
	 * But logIn activity is from {@link BusSelectSeatActivity}, go next activity {@link BusSelectSeatActivity} after log in success. 
	 * <p>
	 * (2) Register button clicked: go next activity {@link UserRegister}.
	 */
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
										user.setUserName_login(arg0.getusername());
										user.login();
									}
								}
								
								Log.i("", "from intent (login): "+from_intent);
								
								if (from_intent.equals("SaleTicket")) {
									
									Log.i("", "From Select Seat !!!!!!!!!!!!!");
									
									if (trip_type == 0) {
										//if one way
										Intent nextScreen = new Intent(UserLogin.this, BusConfirmActivity.class);
				        				
					    				Bundle bundle = new Bundle();
					    				bundle.putString("from_intent", "SaleTicket");
					    				bundle.putString("FromCity", FromCity);
					    				bundle.putString("ToCity", ToCity);
					    				bundle.putString("Operator_Name", Operator_Name);			    				
					    				bundle.putString("from_to", from_to);
					    				bundle.putString("time", time);
					    				bundle.putString("classes", classes);
					    				bundle.putString("date", date);
					    				bundle.putString("bus_occurence", BusOccurence);
					    				
					    				bundle.putString("Price", Price);
				        				bundle.putString("ConfirmDate", ConfirmDate);
				        				bundle.putString("ConfirmTime", ConfirmTime);
				        				bundle.putString("CustomerName", AppLoginUser.getUserName());
				        				
				        				bundle.putString("Selected_seats", Selected_seats);
				        				
				        				//Get Seat Count
				        				String[] seatArray = Selected_seats.split(",");
				        				bundle.putString("SeatCount", seatArray.length+"");
				        				
				        				bundle.putString("seat_List", new Gson().toJson(seat_List));
					    				bundle.putString("permit_ip", permit_ip);
					    				bundle.putString("permit_access_token", permit_access_token);
					    				bundle.putString("permit_operator_group_id", permit_operator_group_id);
										bundle.putString("permit_agent_id", Permit_agent_id);
										bundle.putString("permit_operator_id", permit_operator_id);
										
										bundle.putInt("trip_type", trip_type);
										bundle.putString("return_date", return_date);
										
										bundle.putString("FromName", FromName);
					    				bundle.putString("ToName", ToName);
					    				
					    				nextScreen.putExtras(bundle);
					    				startActivity(nextScreen);
					    				
					    				finish();
					    				
									}else if (trip_type == 1) {
										postSale(date);
									}
								}else {
									Log.i("", "From Me (or) My booking (or) My order .......... !!!!!!!!!!!!!");
									finish();
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
					
					Toast.makeText(UserLogin.this, "No Network connection", Toast.LENGTH_SHORT).show();
					
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
	
	public static String sale_order_no;
	public static String SeatLists = "";
	public static String TicketLists = "";
	
	/**
	 * Save Selected Seats in Operators Database. 
	 * If one way, go next activity {@link PaymentTypeActivity}. 
	 * If round trip, if return trip not choose yet, go next activity {@link BusOperatorSeatsActivity}, 
	 * if return trip choose finish, go next activity {@link PaymentTypeActivity}.
	 * @param date Date (departure date) or (return date)
	 */
	private void postSale(final String date)
	{
		dialog = new ZProgressHUD(UserLogin.this);
		dialog.show();

		Log.i("", "param to encrypt: "
				+"permit_access_token: "+permit_access_token
				+", permit_operator_id: "+permit_operator_id
				+", permit_agent_id: "+Permit_agent_id
				+", permit_operator_group_id: "+permit_operator_group_id
				+", seat list: "+seat_List.getSeatsList().toString()
				+", occuranceid: "+BusOccurence
				+", trip date: "+date
				+", FromCity: "+FromCity
				+", ToCity: "+ToCity
				+", user id: "+String.valueOf(AppLoginUser
						.getId())
				+", device id: "+DeviceUtil.getInstance(this).getID());
		
		//Do Encrypt of Params
		String param = MCrypt.getInstance().encrypt(SecureParam.postSaleParam(permit_access_token
					, permit_operator_id, Permit_agent_id, "", "", "0"
					, "", permit_operator_group_id, MCrypt.getInstance()
					.encrypt(seat_List.getSeatsList().toString()), BusOccurence
					, date, FromCity, ToCity, String.valueOf(AppLoginUser
					.getId()), DeviceUtil.getInstance(this).getID(), "1",
					String.valueOf(AppLoginUser.getId()),"true",""));
		
		
		Log.i("", "param(busselectseat): "+param);
		
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", param));       
        
		final Handler handler = new Handler() {

			private JSONObject jsonObject;

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				
				Log.i("ans:","Server Response: "+jsonData);
				
				try {
					
					if (jsonData != null) {
						jsonObject = new JSONObject(jsonData);
					}
					
					if (jsonObject != null) {
						if(jsonObject.getString("status").equals("1")){
							
							if(jsonObject.getBoolean("can_buy") && jsonObject.getString("device_id")
									.equals(DeviceUtil.getInstance(UserLogin.this).getID())){
			        			
								sale_order_no = jsonObject.getString("sale_order_no");
								
								Log.i("", "Bus confirm(orderno): "+sale_order_no);
								
								//Get Seats No. including (,)
		        				JSONArray jsonArray = jsonObject.getJSONArray("tickets");	        					        			
		        				
		        				/*for(int i=0; i<jsonArray.length(); i++){
		        					JSONObject obj = jsonArray.getJSONObject(i);
		        					if (i == jsonArray.length() - 1) {
		        						SeatLists += obj.getString("seat_no");
									}else {
										SeatLists += obj.getString("seat_no")+",";
									}
		        				}
		        				
		        				Log.i("", "Seat List(bus confirm): "+SeatLists);*/
		        				
		        				if (TicketLists != null) {
		        					TicketLists = "";
								}
		        				
		        				for(int i=0; i<jsonArray.length(); i++){
		        					JSONObject obj = jsonArray.getJSONObject(i);
		        					if (obj.has("ticket_no")) {
										if (i == jsonArray.length() - 1) {
			        						TicketLists += obj.getString("ticket_no");
										}else {
											TicketLists += obj.getString("ticket_no")+",";
										}
		        						
									}else {
										if (i == jsonArray.length() - 1) {
			        						TicketLists += "-";
										}else {
											TicketLists += "-,";
										}
									}
		        				}
		        				
		        				Log.i("", "Ticket No(bus confirm): "+TicketLists);
		        				
								if(isBooking == 0){
									if (trip_type == 1){	
										//If Round Trip
										//For Return Trip, Choose (Operator, Time, Class) again for return trip
											Bundle bundle = new Bundle();
											bundle.putString("from_intent", "BusConfirm");
											bundle.putInt("trip_type", trip_type);
											bundle.putString("return_date", return_date);
											bundle.putString("FromName", FromName);
											bundle.putString("ToName", ToName);
											bundle.putString("GoTripInfo", new Gson().toJson(new GoTripInfo(sale_order_no
													, Price+""
													, SeatCount
													, AppLoginUser.getAgentGroupId(), permit_operator_id, Selected_seats, TicketLists
													, BusOccurence
													, permit_access_token, Permit_agent_id, permit_ip, "", ""
													, "", FromCity, ToCity, Operator_Name, from_to, time, classes
													, date, ConfirmDate
													, ConfirmTime, "", "", "", return_date, ""
													, TicketLists, permit_operator_id)));
											
											//Not Allow to choose for Go Trip again
											closeAllActivities();
											dialog.dismiss();
											startActivity(new Intent(UserLogin.this, BusOperatorSeatsActivity.class).putExtras(bundle));
									}
			        			}else{ 
			        				isBooking = 0;
			        				dialog.dismiss();
			        			}
			        		}else{
			        			isBooking = 0;
			        			dialog.dismissWithFailure();
			        			SKToastMessage.showMessage(UserLogin.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
			        			
			        			if (from_intent.equals("SaleTicket")) {
									//If one way
			        				closeAllActivities();
			        				startActivity(new Intent(UserLogin.this, SaleTicketActivity.class));
								}else {
									finish();
								}
			        		}
						}else{
							Log.i("", "Khone Kar unfinished(status '0') ...........");
							isBooking = 0;
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(UserLogin.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
							
							if (from_intent.equals("SaleTicket")) {
								//If one way
		        				closeAllActivities();
		        				startActivity(new Intent(UserLogin.this, SaleTicketActivity.class));
							}else {
								finish();
							}
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		HttpConnection lt = new HttpConnection(handler,"POST", "http://"+ permit_ip +"/sale", params);
		lt.execute();
		
		Log.i("", "Post Sale: "+"http://"+ permit_ip +"/sale"+" , Params: "+params.toString());
	}
	
	/**
	 * @return If email and password is null, return false. If not, return true.
	 */
	private boolean checkFields() {
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
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//For Google Analytics
		EasyTracker.getInstance(this).activityStart(this);
		
		//For Google Analytics
		Tracker v3Tracker = GoogleAnalytics.getInstance(this).getTracker("UA-67985681-1");

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		v3Tracker.set(Fields.SCREEN_NAME, "Login Screen, "
				+txtEmail.getText().toString()+", "
				+AppLoginUser.getUserName());
		
		// This screenview hit will include the screen name.
		v3Tracker.send(MapBuilder.createAppView().build());
	}
}
