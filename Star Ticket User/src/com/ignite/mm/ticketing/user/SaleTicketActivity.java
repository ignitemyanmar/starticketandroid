package com.ignite.mm.ticketing.user;

import info.hoang8f.widget.FButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.CalendarDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.FromCitiesAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.ToCitiesAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TripTimeAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Times;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;
import com.thuongnh.zprogresshud.ZProgressHUD;

@SuppressLint("SimpleDateFormat") public class SaleTicketActivity extends BaseActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private Spinner spn_from_trip;
	private Spinner spn_to_trip;
	private Spinner spn_trip_date;
	private Spinner spn_trip_time;
	private FButton btn_search;
	private ArrayList<String> fromCities;
	
	private FromCitiesAdapter fromCitiesAdapter;
	private String selectedFromCity;
	private ZProgressHUD dialog;
	private ArrayList<String> toCities;
	private ToCitiesAdapter toCitiesAdapter;
	private String selectedToCity;
	private String selectedTripDate;
	private String currentDate;
	private String tomorrowDate;
	private Button btn_trip_date;
	private List<Times> tripTimes;
	private TripTimeAdapter tripTimeAdapter;
	private SKConnectionDetector skDetector;
	private TextView txt_me;
	private TextView txt_my_booking;
	private String login_name;
	private String userRole;
	private TextView txt_promotion;
	private TextView txt_review;
	private ImageView img_promotion_ads;
	private LinearLayout layout_trip_time;
	private View view_trip_time;
	
	private CalendarPickerView calendar;
	private AlertDialog theDialog;
	private CalendarPickerView dialogView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			login_name = bundle.getString("login_name");
			userRole = bundle.getString("userRole");
		}
		
		setContentView(R.layout.activity_sale_ticket);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("ခရီးစဥ္၊ ရက္၊ အခ်ိန္ ေရြးပါ ~");
            this.setSupportActionBar(toolbar);
        }
		
		//Get Floating Menu from Base Activity
		getFloatingMenu();
		
		spn_from_trip = (Spinner)findViewById(R.id.spn_from_trip);
		spn_to_trip = (Spinner)findViewById(R.id.spn_to_trip);
		btn_trip_date = (Button)findViewById(R.id.btn_trip_date);
		spn_trip_time = (Spinner)findViewById(R.id.spn_trip_time);
		btn_search = (FButton)findViewById(R.id.btn_search);
		btn_search.setButtonColor(getResources().getColor(R.color.yellow));
		btn_search.setShadowEnabled(true);
		btn_search.setShadowHeight(3);
		btn_search.setCornerRadius(7);
		
		img_promotion_ads = (ImageView)findViewById(R.id.img_promotion_ads);
		
		layout_trip_time = (LinearLayout)findViewById(R.id.layout_trip_time);
		view_trip_time = (View)findViewById(R.id.view_trip_time);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		currentDate = sdf.format(cal.getTime());
		
		//Add one day to current date time
		//cal.add(Calendar.DATE, 1);
		tomorrowDate = sdf.format(cal.getTime());
		btn_trip_date.setText(tomorrowDate);
		
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			
			dialog = new ZProgressHUD(SaleTicketActivity.this);
			dialog.show();
			
			getFromCities();
			getToCities();
		}else{
			skDetector.showErrorDialog();
		}
		
		spn_from_trip.setOnItemSelectedListener(fromCityClickListener);
		spn_to_trip.setOnItemSelectedListener(toCityClickListener);
		spn_trip_time.setOnItemSelectedListener(tripTimeClickListener);
		
		btn_search.setOnClickListener(clickListener);
		img_promotion_ads.setOnClickListener(clickListener);
	    btn_trip_date.setOnClickListener(clickListener);	    
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}

	private void getFromCities() {
		// TODO Auto-generated method stub
		
		fromCities = new ArrayList<String>();
		fromCities.add("Choose - From City");
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().getFromCities(AppLoginUser.getAccessToken(), new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub

				if (arg0 != null) {
					List<String> fromCityList = new ArrayList<String>();
					fromCityList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<String>>(){}.getType());
					
					fromCities.addAll(fromCityList);
					
					if (fromCities != null && fromCities.size() > 0) {
						fromCitiesAdapter = new FromCitiesAdapter(SaleTicketActivity.this, fromCities);
						spn_from_trip.setAdapter(fromCitiesAdapter);						
					}
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
			}
		});
	}

	private void getToCities() {
		// TODO Auto-generated method stub

		toCities = new ArrayList<String>();
		toCities.add("Choose - To City");
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().getToCities(AppLoginUser.getAccessToken(), new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub

				if (arg0 != null) {
					List<String> toCityList = new ArrayList<String>();
					toCityList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<String>>(){}.getType());
					
					toCities.addAll(toCityList);
					
					if (toCities != null && toCities.size() > 0) {
						toCitiesAdapter = new ToCitiesAdapter(SaleTicketActivity.this, toCities);
						spn_to_trip.setAdapter(toCitiesAdapter);		
					}
				}
				
				if (dialog != null) {
					dialog.dismissWithSuccess();
				}
				
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				if (dialog != null) {
					dialog.dismissWithFailure();
				}
			}
		});
	}
	
	private void getTripTime() {
		// TODO Auto-generated method stub

			dialog = new ZProgressHUD(SaleTicketActivity.this);
			dialog.show();
			
			tripTimes = new ArrayList<Times>();
			
			NetworkEngine.setIP("test.starticketmyanmar.com");
			NetworkEngine.getInstance().getTimesByTrip(AppLoginUser.getAccessToken(), selectedFromCity, selectedToCity, new Callback<Response>() {
				
				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub

					if (arg0 != null) {
						List<Times> Times = new ArrayList<Times>();
						Times = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Times>>(){}.getType());
						
						if (Times != null && Times.size() > 0) {
							tripTimes.add(new Times("Choose Time (All)", "", ""));
							tripTimes.addAll(Times);
						}
						
						if (tripTimes != null && tripTimes.size() > 0) {
							tripTimeAdapter = new TripTimeAdapter(SaleTicketActivity.this, tripTimes);
							spn_trip_time.setAdapter(tripTimeAdapter);						
						}
						
						layout_trip_time.setVisibility(View.VISIBLE);
						view_trip_time.setVisibility(View.VISIBLE);
					}
					
					dialog.dismissWithSuccess();					
				}
				
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					if (arg0.getResponse() != null) {
						Log.i("", "Error: "+arg0.getResponse().getStatus());
					}
					
					if (dialog != null) {
						dialog.dismissWithFailure();
					}
				}
			});
	}
	
	//----------------------------------------- Click Listener ---------------------------------------------------------------------

	private OnItemSelectedListener fromCityClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
				if (position > 0) {
					selectedFromCity = fromCities.get(position);
				}else {
					selectedFromCity = "";
				}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}; 
	
	private OnItemSelectedListener toCityClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
				if (position > 0) {
					selectedToCity = toCities.get(position);
					if (selectedFromCity.equals("Choose - From City") && selectedToCity.equals("Choose - To City")) {
						SKToastMessage.showMessage(SaleTicketActivity.this, "ခရီးစဥ္  ေရြးပါ", SKToastMessage.WARNING);
					}else {
						if(skDetector.isConnectingToInternet()){
							getTripTime();
						}else{
							skDetector.showErrorDialog();
						}
					}
				}else {
					selectedToCity = "";
				}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	protected String selectedTripTime; 
	
	private OnItemSelectedListener tripTimeClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
				if (position > 0) {
					selectedTripTime = tripTimes.get(position).getTime();
				}else {
					selectedTripTime = "";
				}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_trip_date) {
		        final CalendarDialog calendarDialog = new CalendarDialog(SaleTicketActivity.this);
				calendarDialog.setOnCallbacksListener(new CalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						Date fDate = null;
						
						try {
							Log.i("","Hello Choose Date : "+ chooseDate);
							fDate = new SimpleDateFormat("yyyy-MM-dd").parse(chooseDate);
							today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
							
							Log.i("", "FormatedDate : "+fDate+", Today: "+today);
							
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (fDate != null && today != null) {
							int compare = fDate.compareTo(today);
							
							Log.i("","Hello Compare : "+ compare);
							if(compare >= 0){
								selectedTripDate = DateFormat.format("yyyy-MM-dd",fDate).toString();
								btn_trip_date.setText(selectedTripDate);
								
								calendarDialog.dismiss();
							}else {
								SKToastMessage.showMessage(SaleTicketActivity.this, "Please choose today or grater than today!", SKToastMessage.ERROR);
							}
						}
					}
				});
				
				calendarDialog.show();
			}
			if (v == btn_search) {
				
				if (checkFields()) {
					Bundle bundle = new Bundle();
					bundle.putString("from_city", selectedFromCity);
					bundle.putString("to_city", selectedToCity);
					bundle.putString("trip_date", btn_trip_date.getText().toString());
					bundle.putString("trip_time", selectedTripTime);
					
					startActivity(new Intent(SaleTicketActivity.this, BusOperatorSeatsActivity.class).putExtras(bundle));
				}
			}
/*			if (v == txt_promotion) {
				startActivity(new Intent(SaleTicketActivity.this, PromotionActivity.class));
			}
			if (v == txt_review) {
				startActivity(new Intent(SaleTicketActivity.this, BusReveiwActivity.class));
			}
			if (v == txt_me) {
				startActivity(new Intent(SaleTicketActivity.this, UserProfileActivity.class));
			}
			if (v == txt_my_booking) {
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservationUser");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
			}*/
			if (v == img_promotion_ads) {
				startActivity(new Intent(SaleTicketActivity.this, PromotionActivity.class));
			}
		}
	};
	  
		public boolean checkFields() {
			
			// TODO Auto-generated method stub
			if (spn_from_trip.getSelectedItem().toString().equals("Choose - From City")) {
	
				SKToastMessage.showMessage(SaleTicketActivity.this, "ခရီးစဥ္ ( မွ ) ကုိ ေရြးပါ", SKToastMessage.WARNING);
				return false;
			}
			if (spn_to_trip.getSelectedItem().toString().equals("Choose - To City")) {
				SKToastMessage.showMessage(SaleTicketActivity.this, "ခရီးစဥ္ ( သုိ႔ ) ကုိ ေရြးပါ", SKToastMessage.WARNING);
				return false;
			}		
			
			return true;
		}
}
