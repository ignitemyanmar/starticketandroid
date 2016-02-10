package com.ignite.mm.ticketing.starticket;

import info.hoang8f.widget.FButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.FromCitiesAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.ToCitiesAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TripTimeAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.BundleListOperator;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.Times;
import com.ignite.mm.ticketing.starticket.R;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.squareup.timessquare.CalendarPickerView;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #SaleTicketActivity} is the class to search Trips by Date (one way) or (round trip).
 * <p>
 * Private methods
 * (1) {@link #getFloatingMenu()}
 * (2) {@link #getFromCities()}     
 * (3) {@link #getToCities(String, String)}
 * (4) {@link #getTripTime()}   
 * (5) {@link #fromCityClickListener}
 * (6) {@link #toCityClickListener}
 * (7) {@link #tripTimeClickListener}  
 * (8) {@link #clickListener}
 * (9) {@link #getOperatorSeats(String, String, String, String, String)}, if return status is 206, no available for return trip.
 * (10) {@link #checkFields()}
 * <p>
 * Public method
 * (1) {@link #onBackPressed()}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class SaleTicketActivity extends BaseActivity{

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
	private RadioButton radio_one_way;
	private RadioButton radio_round_trip;
	private int trip_type;
	private Button btn_return_date;
	private LinearLayout layout_return_date;
	private View view_return_date;
	private CheckBox cb_foreigner_price;
	private LinearLayout layout_foreigner_price;
	private int foreigner_price;
	private boolean isClick = false;
	private Configuration config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			login_name = bundle.getString("login_name");
			userRole = bundle.getString("userRole");
		}
		
		//Show View to search for FromCity Names, ToCity Names, Trip Times, Trip Date by (one way) or (round trip) 
		setContentView(R.layout.activity_sale_ticket);
		
		//Title Toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.drawable.ic_launcher);
            toolbar.setLogoDescription("Star Ticket - Myanmar Online Bus Ticket");
            toolbar.setTitle("Star Ticket");
            this.setSupportActionBar(toolbar);
        }
		
		//Get Floating Menu from Base Activity
		getFloatingMenu();
		
		layout_foreigner_price = (LinearLayout)findViewById(R.id.layout_foreigner_price);
		cb_foreigner_price = (CheckBox)findViewById(R.id.cb_foreigner_price);
		radio_one_way = (RadioButton)findViewById(R.id.radio_one_way);
		radio_round_trip = (RadioButton)findViewById(R.id.radio_round_trip);
		layout_return_date = (LinearLayout)findViewById(R.id.layout_return_date);
		view_return_date = (View)findViewById(R.id.view_return_date);
		spn_from_trip = (Spinner)findViewById(R.id.spn_from_trip);
		spn_to_trip = (Spinner)findViewById(R.id.spn_to_trip);
		btn_trip_date = (Button)findViewById(R.id.btn_trip_date);
		btn_return_date = (Button)findViewById(R.id.btn_return_date);
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
		cal.add(Calendar.DATE, 1);
		tomorrowDate = sdf.format(cal.getTime());
		
		btn_trip_date.setText(currentDate);
		btn_return_date.setText(tomorrowDate);
		
		skDetector = SKConnectionDetector.getInstance(SaleTicketActivity.this);
		
		//If network connection available, get From Cities
		if(skDetector.isConnectingToInternet()){
			getFromCities();
		}else{
			Toast.makeText(SaleTicketActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
		}
		
		spn_from_trip.setOnItemSelectedListener(fromCityClickListener);
		spn_to_trip.setOnItemSelectedListener(toCityClickListener);
		spn_trip_time.setOnItemSelectedListener(tripTimeClickListener);
		
		btn_search.setOnClickListener(clickListener);
		img_promotion_ads.setOnClickListener(clickListener);
	    btn_trip_date.setOnClickListener(clickListener);
	    btn_return_date.setOnClickListener(clickListener);	 
	    radio_one_way.setOnClickListener(clickListener);
	    radio_round_trip.setOnClickListener(clickListener);
	    
	  //Check Screen Size
        config = getResources().getConfiguration();
	}
	
	/**
	 *  Get FromCity names
	 */
	private void getFromCities() {
		// TODO Auto-generated method stub
		
		dialog = new ZProgressHUD(SaleTicketActivity.this);
		dialog.show();
		
		fromCities = new ArrayList<String>();
		
		//fromCities.add("Choose - From City");
		
			NetworkEngine.setIP("starticketmyanmar.com");
			NetworkEngine.getInstance().getFromCities("", "1", new Callback<Response>() {
				
				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub

					if (arg0 != null) {
						List<String> fromCityList = new ArrayList<String>();
						fromCityList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<String>>(){}.getType());
						
						if (fromCityList != null && fromCityList.size() > 0) {
							fromCities.addAll(fromCityList);
						}
						
						if (fromCities != null && fromCities.size() > 0) {
							fromCitiesAdapter = new FromCitiesAdapter(SaleTicketActivity.this, fromCities);
							spn_from_trip.setAdapter(fromCitiesAdapter);	
							
							for (int i = 0; i < fromCities.size(); i++) {
								if (fromCities.get(i).contains("Yangon")) {
									spn_from_trip.setSelection(i);	
								}
							}
						}
					}
					
					Log.i("", "From City (to): "+spn_from_trip.getSelectedItem().toString());
					
					dialog.dismiss();
					
					/*if(skDetector.isConnectingToInternet()){
						getToCities(spn_from_trip.getSelectedItem().toString());
					}else{
						Toast.makeText(SaleTicketActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
					}*/
				}
				
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					if (arg0.getResponse() != null) {
						Log.i("", "Error: "+arg0.getResponse().getStatus());
					}
					
					dialog.dismissWithFailure("Time Out");
				}
			});
	}

	/**
	 *  Get ToCity names. 
	 *  If one way, show all cities that have own seats. 
	 *  If round trip, 
	 *  show only cities that available for both (departure + return).
	 * @param fromCity Selected From City Name
	 * @param round_trip if round trip 1 , if one way 0
	 */
	private void getToCities(String fromCity, String round_trip) {
		// TODO Auto-generated method stub

		dialog = new ZProgressHUD(SaleTicketActivity.this);
		dialog.show();
		
		toCities = new ArrayList<String>();
		toCities.add("Choose - Arrival City");
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getToCities("", fromCity, round_trip, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub

				if (arg0 != null) {
					
					List<String> toCityList = new ArrayList<String>();
					toCityList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<String>>(){}.getType());
					
					if (toCityList != null && toCityList.size() > 0) {
						toCities.addAll(toCityList);
					}
					
					if (toCities != null && toCities.size() > 0) {
						toCitiesAdapter = new ToCitiesAdapter(SaleTicketActivity.this, toCities);
						spn_to_trip.setAdapter(toCitiesAdapter);		
					}
				}
				
				Log.i("", "enter to city");
				
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismissWithFailure("Time Out");
			}
		});
	}
	
	/**
	 *  Get available Trip Times by selectedFromCity and selectedToCity
	 *  for only one way
	 */
	private void getTripTime() {
		// TODO Auto-generated method stub

			dialog = new ZProgressHUD(SaleTicketActivity.this);
			dialog.show();
			
			tripTimes = new ArrayList<Times>();
			
			NetworkEngine.setIP("starticketmyanmar.com");
			NetworkEngine.getInstance().getTimesByTrip("", selectedFromCity, selectedToCity, new Callback<Response>() {
				
				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub

					if (arg0 != null) {
						List<Times> Times = new ArrayList<Times>();
						Times = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Times>>(){}.getType());
						
						if (Times != null && Times.size() > 0) {
							tripTimes.add(new Times("All Time", "", ""));
							tripTimes.addAll(Times);
						}
						
						if (tripTimes != null && tripTimes.size() > 0) {
							tripTimeAdapter = new TripTimeAdapter(SaleTicketActivity.this, tripTimes);
							spn_trip_time.setAdapter(tripTimeAdapter);						
						}
					}
					
					dialog.dismiss();				
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

	/**
	 * if fromCity is clicked, show toCities for one way (or) round trip
	 */
	private OnItemSelectedListener fromCityClickListener = new OnItemSelectedListener() {
		
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			selectedFromCity = fromCities.get(position);
			
			if(skDetector.isConnectingToInternet()){
				if (radio_one_way.isChecked()) {
					//if one way
					getToCities(selectedFromCity, "0");
				}else {
					//if round trip
					getToCities(selectedFromCity, "1");
				}
				
			}else{
				Toast.makeText(SaleTicketActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}; 
	
	/**
	 * ToCity click, if one way, show trip Time. If round trip, hide trip time
	 */
	private OnItemSelectedListener toCityClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub

				if (position > 0) {
					selectedToCity = toCities.get(position);
					
					if (radio_one_way.isChecked()) {
						layout_trip_time.setVisibility(View.VISIBLE);
						view_trip_time.setVisibility(View.VISIBLE);
						layout_foreigner_price.setVisibility(View.VISIBLE);
						
						if(skDetector.isConnectingToInternet()){
							getTripTime();
						}else{
							Toast.makeText(SaleTicketActivity.this, "No Internet connection!", Toast.LENGTH_SHORT).show();
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
	
	/**
	 * Trip Time click, get selected time
	 */
	 
	private OnItemSelectedListener tripTimeClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			if (tripTimes != null && tripTimes.size() > 0) {
				if (position > 0) {
					selectedTripTime = tripTimes.get(position).getTime();
				}else {
					selectedTripTime = "";
				}
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	/**
	 * (1) If {@code radio_one_way} checked, hide return date, show all of own seat cities
	 * (2) If {@code radio_round_trip} checked, show returnDate and hide tripTime, show only (depart+return) cities
	 * (3) If {@code btn_trip_date} clicked, show Calendar
	 * (4) If {@code btn_return_date} clicked, show Calendar for Return
	 * (5) If {@code btn_search} clicked, Go another activity {@link BusOperatorSeatsActivity} 
	 * (6) If {@code img_promotion_ads} clicked, Go another activity {@link PromotionActivity}
	 */
	private OnClickListener clickListener = new OnClickListener() {
		
		@SuppressWarnings("static-access")
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == radio_one_way) {
				layout_return_date.setVisibility(View.GONE);
				view_return_date.setVisibility(View.GONE);
				
				//one way (show all of own seat cities)
				if(skDetector.isConnectingToInternet()){
					getToCities(selectedFromCity, "0");
				}else{
					Toast.makeText(SaleTicketActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
				}
			}
			if (v == radio_round_trip) {
				layout_return_date.setVisibility(View.VISIBLE);
				view_return_date.setVisibility(View.VISIBLE);
				layout_trip_time.setVisibility(View.GONE);
				view_trip_time.setVisibility(View.GONE);
				
				//round trip (show only (depart+return) cities)
				if(skDetector.isConnectingToInternet()){
					getToCities(selectedFromCity, "1");
				}else{
					Toast.makeText(SaleTicketActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
				}
			}
			if (v == btn_trip_date) {
				
		        final NewCalendarDialog calendarDialog = new NewCalendarDialog(SaleTicketActivity.this);
		        
		        calendarDialog.txt_calendar_title.setText(R.string.str_calendar_title);
		        calendarDialog.calendar.setShowOtherDates(true);
		      //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
		        calendarDialog.calendar.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
		        calendarDialog.calendar.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
		        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.golden_brown));
		        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
		        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
		        calendarDialog.calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
		        calendarDialog.calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
		        
		        if (config.smallestScreenWidthDp >= 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp < 600){
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
		        }
		        
		        //calendarDialog.calendar.setFirstDayOfWeek(Calendar.THURSDAY);
		        
		        Calendar calendar = Calendar.getInstance();
		        //calendarDialog.calendar.setSelectedDate(calendar.getTime());
		        calendarDialog.calendar.setMinimumDate(calendar.getTime());
		        
		      //Allow only 15 days to buy in advance for users 
		        //If not log in yet
		        Log.i("", "Log in id: "+AppLoginUser.getId());
		        
		        if (AppLoginUser.getId() == null || AppLoginUser.getId().equals("")) {
		        	//Add 14 days to current date time
			        calendar.add(Calendar.DATE, 14);
			        calendarDialog.calendar.setMaximumDate(calendar.getTime());
				}else {
					if (AppLoginUser.getRole() != null && !AppLoginUser.getRole().equals("")) {
			        	 if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
					        	//Add 14 days to current date time
						        calendar.add(Calendar.DATE, 14);
						        calendarDialog.calendar.setMaximumDate(calendar.getTime());
							}
					}
				}
		        
				calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						
						btn_trip_date.setText(chooseDate);
						calendarDialog.dismiss();
						
					}
				});
				
				calendarDialog.show();
			}
			if (v == btn_return_date) {
				
		        final NewCalendarDialog calendarDialog = new NewCalendarDialog(SaleTicketActivity.this);
		        
		        calendarDialog.txt_calendar_title.setText(R.string.str_calendar_return_title);
		        calendarDialog.calendar.setShowOtherDates(true);
		      //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
		        calendarDialog.calendar.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
		        calendarDialog.calendar.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
		        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.golden_brown));
		        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
		        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
		        calendarDialog.calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
		        calendarDialog.calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

		        if (config.smallestScreenWidthDp >= 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp < 600){
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
		        }
		        //calendarDialog.calendar.setFirstDayOfWeek(Calendar.THURSDAY);
		        
		        Calendar calendar = Calendar.getInstance();
		        //calendarDialog.calendar.setSelectedDate(calendar.getTime());
		        calendarDialog.calendar.setMinimumDate(calendar.getTime());
		        
			      //Allow only 15 days to buy in advance for users 
		        //If not log in yet
		        if (AppLoginUser.getId() == null || AppLoginUser.getId().equals("")) {
		        	//Add 14 days to current date time
			        calendar.add(Calendar.DATE, 14);
			        calendarDialog.calendar.setMaximumDate(calendar.getTime());
				}else {
					if (AppLoginUser.getRole() != null && !AppLoginUser.getRole().equals("")) {
			        	 if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
					        	//Add 14 days to current date time
						        calendar.add(Calendar.DATE, 14);
						        calendarDialog.calendar.setMaximumDate(calendar.getTime());
							}
					}
				}
		        
				calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						btn_return_date.setText(chooseDate);
						calendarDialog.dismiss();
						
					}
				});
				
				calendarDialog.show();
			}
			if (v == btn_search) {
				
				if (checkFields()) {

				    //One Way (or) Round Trip check
					if (radio_one_way.isChecked()) {
						trip_type = 0;
					}else {
						trip_type = 1;
						
					}
					
					//Foreigner Price or Local Price
					if (cb_foreigner_price.isChecked()) {
						foreigner_price = 1;
					}else {
						foreigner_price = 0;
					}
					
					/*Bundle bundle = new Bundle();
					bundle.putString("from_city", selectedFromCity);
					bundle.putString("to_city", selectedToCity);
					bundle.putString("trip_date", btn_trip_date.getText().toString());
					bundle.putString("trip_time", selectedTripTime);
					bundle.putString("return_date", btn_return_date.getText().toString());
					bundle.putString("from_intent", "SaleTicket");
					bundle.putInt("trip_type", trip_type);*/
					
					SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("foreign_price", Activity.MODE_PRIVATE);
				    SharedPreferences.Editor editor = sharedPreferences.edit();
				     
				    editor.clear();
				    editor.commit();
				     
				    editor.putInt("foreign_price", foreigner_price);
				    editor.commit();
				     
					
					if (trip_type == 0) {
						//if one way
						getOperatorSeats(selectedFromCity, selectedToCity, btn_trip_date.getText().toString(), selectedTripTime, "0");
						
					}else if (trip_type == 1) {
						//if round trip 
						//check if return trip is available or not 
						getOperatorSeats(selectedFromCity, selectedToCity, btn_trip_date.getText().toString(), selectedTripTime, "1");
					}
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
				//displayPromoNoti();
				//displayNotificationTwo();
				startActivity(new Intent(SaleTicketActivity.this, PromotionActivity.class));
			}
		}
	};
	protected List<OperatorSeat> OperatorSeats;
	
	/**
	 *  Check if return trip is available or not 
	 * @param fromCity From City Name
	 * @param toCity To City Name
	 * @param tripDate Trip Date
	 * @param tripTime Trip Time (Optional)
	 * @param round_trip_status 1 (for round trip, to check if return trip is available or not), 0 (for one way)
	 */
	private void getOperatorSeats(final String fromCity, final String toCity, final String tripDate, final String tripTime, String round_trip_status){
		
		dialog = new ZProgressHUD(SaleTicketActivity.this);
		dialog.show();
		
		Log.i("", "Search Operator: "+fromCity+", "
						+toCity+", "
						+tripDate+", "
						+tripTime+", access: "+AppLoginUser.getAccessToken());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postSearch(fromCity, toCity, tripDate, tripTime, "", round_trip_status, String.valueOf(foreigner_price), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismissWithFailure();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					OperatorSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorSeat>>(){}.getType());
					
					if (arg0.getStatus() == 206) {
						Toast.makeText(SaleTicketActivity.this, R.string.str_no_return_trip, Toast.LENGTH_SHORT).show();
					}else {
						
						if (OperatorSeats != null && OperatorSeats.size() > 0) {
							BundleListOperator bundleListOperator = new BundleListOperator();
							bundleListOperator.setOperatorSeat(OperatorSeats);	
							
							Bundle bundle = new Bundle();
							bundle.putString("from_city", fromCity);
							bundle.putString("to_city", toCity);
							bundle.putString("trip_date", tripDate);
							bundle.putString("trip_time", tripTime);
							bundle.putString("return_date", btn_return_date.getText().toString());
							bundle.putString("from_intent", "SaleTicket");
							bundle.putInt("trip_type", trip_type);
							bundle.putString("operator_list", new Gson().toJson(bundleListOperator));
							
							startActivity(new Intent(SaleTicketActivity.this, BusOperatorSeatsActivity.class).putExtras(bundle));
						}else {
							//Toast.makeText(SaleTicketActivity.this, "Sold out of Online seats!", Toast.LENGTH_SHORT).show();
							alertDialog("Sold out of Online seats! Please call STAR TICKET (or) Change date/time"
									, "Call", "Cancel", new DialogInterface.OnClickListener() {
										
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											callHotLine("0931166772");
										}
									}, new DialogInterface.OnClickListener() {
										
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									});
						}
					}
				}
				
				dialog.dismiss();
			}
		});
	}
	  
	/**
	 *  (1) If {@code spn_from_trip} is null, return false
	 *  (2) If {@code spn_to_trip} is null, return false 
	 *  (3) If {@code radio_round_trip} is checked, if return date is less than departure date, return false
	 * @return If all above is ok, return true
	 */
	private boolean checkFields() {
		
		// TODO Auto-generated method stub
		if (spn_from_trip.getSelectedItem() != null) {
			if (spn_from_trip.getSelectedItem().toString().equals("Choose - From City")) {

				SKToastMessage.showMessage(SaleTicketActivity.this, "Please choose Departure City", SKToastMessage.WARNING);
				return false;
			}
		}else {
			SKToastMessage.showMessage(SaleTicketActivity.this, "Please choose Arrival City", SKToastMessage.WARNING);
			return false;
		}
		
		if (spn_to_trip.getSelectedItem() != null) {
			if (spn_to_trip.getSelectedItem().toString().equals("Choose - Arrival City")) {
				SKToastMessage.showMessage(SaleTicketActivity.this, "Please choose Arrival City", SKToastMessage.WARNING);
				return false;
			}	
		}else {
			SKToastMessage.showMessage(SaleTicketActivity.this, "Please choose Arrival City", SKToastMessage.WARNING);
			return false;
		}
		
		if (radio_round_trip.isChecked()) {
			Date goDate = null;
			Date returnDate = null;
			try {
				returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(btn_return_date.getText().toString());
				goDate = new SimpleDateFormat("yyyy-MM-dd").parse(btn_trip_date.getText().toString());
				
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (returnDate != null && goDate != null) {
				int compare = returnDate.compareTo(goDate);
				
				Log.i("","Hello Compare : "+ compare);
				if(compare < 0){
					Toast.makeText(SaleTicketActivity.this, R.string.str_choose_departure_date, Toast.LENGTH_SHORT).show();
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * If press back button of phone system, 
	 * show alert asking "Are you sure you want to exit the app?", 
	 * if yes click, exist the app, if no click, dismiss the dialog
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		alertDialog("Are you sure you want to exit the app?"
		, "Yes", "No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		}, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cb_foreigner_price.setChecked(false);
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
		v3Tracker.set(Fields.SCREEN_NAME, "Trip Search Screen, "+AppLoginUser.getUserName());
		
		// This screenview hit will include the screen name.
		v3Tracker.send(MapBuilder.createAppView().build());
	}
}
