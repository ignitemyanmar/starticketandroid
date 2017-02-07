package com.ignite.mm.ticketing.starticketsale;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActionBarActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.CityAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TripsCityAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.TripsCollection;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

@SuppressLint("NewApi") public class BusTripsCityActivity extends BaseActionBarActivity{
	private GridView grd_trips_city;
	private ZProgressHUD dialog;
	private int NofColumn;
	private String selectedDate;
	private Configuration config;
	private String today;
	private Spinner spn_from_city;
	private String selectedFromCity;
	private ButtonRectangle btn_change_color;
	private List<TripsCollection> tripsCollections;
	private Menu menu;
	private  ArrayList<String> fromCityList;
	private ArrayList<String> newList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_trips_city);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            toolbar.setTitle("Choose Trip");
            this.setSupportActionBar(toolbar);
        }
		
		NofColumn = 2;		
		grd_trips_city = (GridView) findViewById(R.id.grd_trips_city);
		grd_trips_city.setNumColumns(NofColumn);
		spn_from_city = (Spinner)findViewById(R.id.spn_from_city);
		btn_change_color = (ButtonRectangle)findViewById(R.id.btn_change_color);
		
		grd_trips_city.setOnItemClickListener(itemClickListener);
		btn_change_color.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Put From City + To City 
				SharedPreferences sp = getSharedPreferences("changeColorCity", Activity.MODE_PRIVATE);
				selectedFromCity = sp.getString("change_color", null);
				
				grd_trips_city.setAdapter(null);
				grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, tripsCollections, selectedFromCity));
				
				Toast.makeText(BusTripsCityActivity.this, "color changed", Toast.LENGTH_LONG).show();
			}
		});
		
		SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			getTripsCity();
			getNotiBooking();
		}else{
			skDetector.showErrorMessage();
			//fadeData();
		}
		
		spn_from_city.setOnItemSelectedListener(onItemSelectedListener);
		
		//Check Screen Size
        config = getResources().getConfiguration();
	}
	
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			selectedFromCity = newList.get(position);
			
			SharedPreferences sp = getSharedPreferences("changeColorCity", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			
			editor.clear();
			editor.commit();
			
			editor.putString("change_color", selectedFromCity);
			
			editor.commit();
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void fadeData(){
		 tripsCollections = new ArrayList<TripsCollection>();
		 tripsCollections.add(new TripsCollection("1", "Yangon", "2", "Mandalay"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "3", "Nay Pyi Taw"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "4", "Pyin Oo Lwin"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "5", "Inle"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "6", "Bagan"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "7", "Bago"));
	//	 grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, tripsCollections));
	}

	
	
	private void getTripsCity(){
		dialog = new ZProgressHUD(this);
        dialog.show();
		String param = MCrypt.getInstance().encrypt(SecureParam.getTripsParam(AppLoginUser.getAccessToken(), AppLoginUser.getUserID()));
		NetworkEngine.getInstance().getTrips(param, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					tripsCollections = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<TripsCollection>>() {}.getType());
					
					fromCityList = new ArrayList<String>();
				//	fromCityList.add("အေရာင္ေျပာင္းခ်င္သည့္ ျမိဳ႕ကုိ ေရြးပါ");
					
					for (int i = 0; i < tripsCollections.size(); i++) {
						fromCityList.add(tripsCollections.get(i).getFrom());
					}
					
					newList = new ArrayList<String>(new HashSet<String>(fromCityList));
					
					spn_from_city.setAdapter(new CityAdapter(BusTripsCityActivity.this, newList));
					
					SharedPreferences sp = getSharedPreferences("changeColorCity", Activity.MODE_PRIVATE);
					selectedFromCity = sp.getString("change_color", null);
					
					if (selectedFromCity != null) {
						for (int i = 0; i < newList.size(); i++) {
							if (newList.get(i).equals(selectedFromCity)) {
								spn_from_city.setSelection(i);	
								break;
							}
						}
					}else {
						for (int i = 0; i < newList.size(); i++) {
							if (newList.get(i).contains("Yangon")) {
								spn_from_city.setSelection(i);	
								break;
							}
						}
					}
					
					grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, tripsCollections, selectedFromCity));
				}else {
					Toast.makeText(BusTripsCityActivity.this, "No City", Toast.LENGTH_SHORT).show();
				}

				if (dialog != null) {
					dialog.dismissWithSuccess();
				}
				
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (dialog != null) {
					dialog.dismissWithFailure();
				}
				
			}
		});
	}
	
	private void getNotiBooking(){
		String param = MCrypt.getInstance().encrypt(SecureParam.getNotiBookingParam(AppLoginUser.getAccessToken(), getToday()));
		NetworkEngine.getInstance().getNotiBooking(param , new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences = getSharedPreferences("NotifyBooking",Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				
				editor.clear();
				editor.commit();
				Integer bookingCount = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Integer>() {}.getType());
				editor.putInt("count", bookingCount);
				editor.commit();
				
				if(bookingCount > 0){
					menu.getItem(0).setTitle(bookingCount.toString());
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bus_confirm, menu);
		if (NetworkEngine.getIp().equals(getResources().getString(R.string.str_operator_khonepine))) {
			MenuItem item = menu.findItem(R.id.action_booking_noti);
			//item.setVisible(false);
			this.invalidateOptionsMenu();
		}
		this.menu = menu;

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_booking_noti) {
			SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.clear();
			editor.commit();
			editor.putString("order_date", getToday());
			editor.commit();
        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			
			today = getToday();
			
	        final NewCalendarDialog calendarDialog = new NewCalendarDialog(BusTripsCityActivity.this, getTodayDate());
	        
	        calendarDialog.txt_calendar_title.setText(R.string.str_calendar_title);
	        calendarDialog.calendar.setShowOtherDates(true);
	      //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
	        //calendarDialog.calendar.setCurrentDate(getTodayDate());
	        calendarDialog.calendar.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
	        calendarDialog.calendar.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
	        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.golden_brown));
	        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
	        calendarDialog.calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
	        calendarDialog.calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

	        if (config.smallestScreenWidthDp >= 700) {
	        	//Tablet 10 inches
	        	calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
	        	calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
	        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics()));
	        }else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
	        	//Tablet 7 inches
	        	calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
	        	calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
	        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics()));
	        }else if (config.smallestScreenWidthDp < 600){
	        	//Phone
	        	calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearancePhone);
	        	calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearancePhone);
	        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
	        }
	        
	        //calendarDialog.calendar.setCurrentDate(todayDate);
			calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
				
				private Date today;
				public void choose(String chooseDate) {
					// TODO Auto-generated method stub
		        	
					Bundle bundle = new Bundle();
					bundle.putString("from_id", tripsCollections.get(arg2).getFrom_id());
					bundle.putString("to_id", tripsCollections.get(arg2).getTo_id());
					bundle.putString("from", tripsCollections.get(arg2).getFrom());
					bundle.putString("to", tripsCollections.get(arg2).getTo());
					bundle.putString("date", chooseDate);
					startActivity(new Intent(getApplicationContext(), BusTimeActivity.class).putExtras(bundle));
					
					calendarDialog.dismiss();
					
				}
			});
			
			calendarDialog.show();
			
/*			final SKCalender skCalender = new SKCalender(BusTripsCityActivity.this);
			
			  skCalender.setCallbacks(new Callbacks() {

					public void onChooseDate(String chooseDate) {
			        	// TODO Auto-generated method stub
			        	Date formatedDate = null;
						try {
							formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
			        	skCalender.dismiss();
			        	
			        	Bundle bundle = new Bundle();
						bundle.putString("from_id", tripsCollections.get(arg2).getFrom_id());
						bundle.putString("to_id", tripsCollections.get(arg2).getTo_id());
						bundle.putString("from", tripsCollections.get(arg2).getFrom());
						bundle.putString("to", tripsCollections.get(arg2).getTo());
						bundle.putString("date", selectedDate);
						startActivity(new Intent(getApplicationContext(), BusTimeActivity.class).putExtras(bundle));			        	
			        	
			        }
			  });

			skCalender.show();*/
		}
	};
}
