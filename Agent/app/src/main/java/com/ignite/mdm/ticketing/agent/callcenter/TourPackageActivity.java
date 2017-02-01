package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TourPackageActivity extends BaseSherlockActivity{
	private Button btn_search;
	private Spinner spn_from_trip;
	private Spinner spn_to_trip;
	private TextView txt_trip_date;
	private Spinner spn_trip_time;
	private Spinner spn_price;
	private Configuration config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_package);
		
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            this.setSupportActionBar(toolbar);
        }
		
        spn_from_trip = (Spinner)findViewById(R.id.spn_from_trip);
        spn_to_trip = (Spinner)findViewById(R.id.spn_to_trip);
        txt_trip_date = (TextView)findViewById(R.id.btn_trip_date);
        spn_trip_time = (Spinner)findViewById(R.id.spn_trip_time);
        spn_price = (Spinner)findViewById(R.id.spn_price);
        
        config = getResources().getConfiguration();
        
        //From Trip 
        List<String> fromList = new ArrayList<String>();
        fromList.add("Yangon");
        
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(getBaseContext()
        		, android.R.layout.simple_dropdown_item_1line, fromList);
        spn_from_trip.setAdapter(fromAdapter);
        
        //To Trip 
        List<String> toList = new ArrayList<String>();
        toList.add("Chaung Thar");
        toList.add("Bagan Nyaung Oo");
        toList.add("Taung Gyi-InnLay");
        toList.add("Pyin Oo Lwin");
        toList.add("Mandalay");
        
        ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(getBaseContext()
        		, android.R.layout.simple_dropdown_item_1line, toList);
        spn_to_trip.setAdapter(toAdapter);
        
        //Days
        List<String> days = new ArrayList<String>();
        days.add("Any Day");
        days.add("2 days");
        days.add("3 days");
        days.add("4 days");
        days.add("5 days");
        days.add("6 days");
        days.add("7 days");
        
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getBaseContext()
        		, android.R.layout.simple_dropdown_item_1line, days);
        spn_trip_time.setAdapter(daysAdapter);
        
        //Price
        List<String> prices = new ArrayList<String>();
        prices.add("Any Price");
        prices.add("40,000 Ks");
        prices.add("50,000 Ks");
        prices.add("60,000 Ks");
        prices.add("70,000 Ks");
        prices.add("80,000 Ks");
        prices.add("100,000 Ks");
        
        ArrayAdapter<String> pricesAdapter = new ArrayAdapter<String>(getBaseContext()
        		, android.R.layout.simple_dropdown_item_1line, prices);
        spn_price.setAdapter(pricesAdapter);
        
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(TourPackageActivity.this, TourCompanyActivity.class));
			}
		});
		
        txt_trip_date.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        final NewCalendarDialog calendarDialog = new NewCalendarDialog(TourPackageActivity.this);
		        
		        calendarDialog.txt_calendar_title.setText(R.string.str_calendar_title);
		        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
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
		        
		        Calendar calendar = Calendar.getInstance();
		        
		        //Allow only 15 days to buy in advance for users 
		        //If not log in yet,
		        Log.i("", "Log in id: "+AppLoginUser.getId());
		        if (AppLoginUser.getId() == null || AppLoginUser.getId().equals("")) {
		        	//Add 14 days to current date time
			        calendar.add(Calendar.DATE, 14);
			        calendarDialog.calendar.state().edit().setMaximumDate(calendar.getTime());
				}else {
					if (AppLoginUser.getRole() != null && !AppLoginUser.getRole().equals("")) {
			        	 if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
			        		    //If Agents
					        	//Add 14 days to current date time

			        		    calendarDialog.calendar.state().edit().setMinimumDate(calendar.getTime());
						        calendar.add(Calendar.DATE, 14);
						        calendarDialog.calendar.state().edit().setMaximumDate(calendar.getTime());
							}
					}
				}
		        
				calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						
						txt_trip_date.setText(chooseDate);
						calendarDialog.dismiss();
					}
				});
				
				calendarDialog.show();
			}
		});
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
