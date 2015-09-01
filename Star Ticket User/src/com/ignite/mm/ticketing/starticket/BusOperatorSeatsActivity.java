package com.ignite.mm.ticketing.starticket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorSeatsAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.SearchOperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.Trip;
import com.ignite.mm.ticketing.starticket.R;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class BusOperatorSeatsActivity extends BaseActivity{
	private String selectedFromCity = "";
	private String selectedToCity = "";
	private String selectedTripDate = "";
	private String selectedTripTime = "";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private ListView lv_operator_seats;
	private ZProgressHUD dialog;
	private List<OperatorSeat> OperatorSeats;
	private RelativeLayout Rlayout_noInfo;
	private int selectedTripType;
	private String selectedReturnDate;
	private LinearLayout layout_round_trip_info;
	private TextView txt_round_trip_info;
	private String from_intent;
	private String from_to;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	private TextView txt_change_date;
	private SKConnectionDetector skDetector;
	private LinearLayout layout_round_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_show_operator_seats);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			selectedFromCity = bundle.getString("from_city");
			selectedToCity = bundle.getString("to_city");
			selectedTripDate = bundle.getString("trip_date");
			selectedTripType = bundle.getInt("trip_type");
			
			if (selectedTripType == 1) {
				if (bundle.getString("trip_time") != null) {
					selectedTripTime = bundle.getString("trip_time");
				}else {
					selectedTripTime = "";
				}
			}
			
			//From Bus Confirm (For Return)
			selectedReturnDate = bundle.getString("return_date");
			from_intent = bundle.getString("from_intent");
			
			if (from_intent.equals("BusConfirm")) {
				selectedFromCity = bundle.getString("FromName");
				selectedToCity = bundle.getString("ToName");
				goTripInfo_str = bundle.getString("GoTripInfo");
				goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
			}
		}
		
		layout_round_info = (LinearLayout)findViewById(R.id.layout_round_info);
		txt_round_trip_info = (TextView)findViewById(R.id.txt_round_trip_info);
		txt_change_date = (TextView)findViewById(R.id.txt_change_date);
		
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitleTextAppearance(BusOperatorSeatsActivity.this, R.style.CustomToolbarTextAppearance);
            toolbar.setSubtitleTextAppearance(BusOperatorSeatsActivity.this, R.style.CustomToolbarSubTextAppearance);
            
            //Set Title
            if (from_intent.equals("SaleTicket")) {
            	toolbar.setTitle(selectedFromCity+" - "+selectedToCity);
			}else if (from_intent.equals("BusConfirm")) {
				//Set Return title + sub-title
				toolbar.setTitle(selectedToCity+" - "+selectedFromCity);
				if (!selectedReturnDate.equals("") && selectedReturnDate != null) {
					toolbar.setSubtitle(changeDate(selectedReturnDate)+" [All Time]");
				}
				txt_round_trip_info.setText(R.string.str_choose_return_trip);
				txt_change_date.setText("Change 'Return' Date");
			}
            
            Log.i("", "Trip Time"+selectedTripTime+", Trip Type: "+selectedTripType);
            
            //Set Sub-Title, Show One way (or) Round trip
            if (from_intent.equals("SaleTicket")) {
            	if (!selectedTripDate.equals("") && selectedTripDate != null) {
    				if (selectedTripTime != null && !selectedTripTime.equals("")) {
    					//One Way (include time)
    						toolbar.setSubtitle(changeDate(selectedTripDate)+" ["+selectedTripTime+"]");
    						txt_round_trip_info.setText("One Way");
    						txt_change_date.setText("Change Trip Date");
    				}else
    				{
    					Log.i("", "(enter)Trip Time"+selectedTripTime+", Trip Type: "+selectedTripType);
    					
    					//If one way (all time)
    					if (selectedTripType == 1) {
    						toolbar.setSubtitle(changeDate(selectedTripDate)+" [All Time]");
    						txt_round_trip_info.setText("One Way");
    						txt_change_date.setText("Change Trip Date");
    					}else if (selectedTripType == 2){
    						//If Round Trip 
    						toolbar.setSubtitle(changeDate(selectedTripDate)+" [All Time]");
    						txt_round_trip_info.setText(R.string.str_choose_go_trip);
    						txt_change_date.setText("Change 'Go' Date");
    					}
    				}
    			}else {
    				toolbar.setTitle("00/00/0000 [ 00:00:00 ]");
    			}	
			}
    		
            this.setSupportActionBar(toolbar);
        }
        
		lv_operator_seats = (ListView)findViewById(R.id.lv_operator_seats);
		lv_operator_seats.setDividerHeight(0);
		lv_operator_seats.setOnItemClickListener(clickListener);
		
		Rlayout_noInfo = (RelativeLayout)findViewById(R.id.Rlayout_noInfo);
		
		skDetector = SKConnectionDetector.getInstance(this);
		if(skDetector.isConnectingToInternet()){
			
			 if (from_intent.equals("SaleTicket")) {
				//Go Trip
					 String fromCity = selectedFromCity;
					 String toCity = selectedToCity;
					 String tripDate = selectedTripDate;
					 String tripTime = selectedTripTime;
					 
					 getOperatorSeats(fromCity, toCity, tripDate, tripTime);
					 
				}else if (from_intent.equals("BusConfirm")) {
				//Return Trip 
					 String fromCity = selectedToCity;
					 String toCity = selectedFromCity;
					 String tripDate = selectedReturnDate;
					 String tripTime = "";
					 
					 getOperatorSeats(fromCity, toCity, tripDate, tripTime);
					 
				}
			 
			
		}else{
			Toast.makeText(BusOperatorSeatsActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
		}
		
		layout_round_info.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("static-access")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final NewCalendarDialog calendarDialog = new NewCalendarDialog(BusOperatorSeatsActivity.this);
		        
		        calendarDialog.txt_calendar_title.setText(R.string.str_calendar_title);
		        calendarDialog.calendar.setShowOtherDates(true);
		        calendarDialog.calendar.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
		        calendarDialog.calendar.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
		        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.golden_brown));
		        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
		        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
		        calendarDialog.calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
		        calendarDialog.calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
		        calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
		        
		        Calendar calendar = Calendar.getInstance();
		        calendarDialog.calendar.setMinimumDate(calendar.getTime());
		        
				calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						calendarDialog.dismiss();
						
						if(skDetector.isConnectingToInternet()){
   						 if (from_intent.equals("SaleTicket")) {
   							 
   							selectedTripDate = chooseDate;
   	    					toolbar.setSubtitle(changeDate(selectedTripDate)+" [All Time]");
   	    					
   							//Go Trip
   								 String fromCity = selectedFromCity;
   								 String toCity = selectedToCity;
   								 
   								 getOperatorSeats(fromCity, toCity, selectedTripDate, "");
   								 
   							}else if (from_intent.equals("BusConfirm")) {
   								
   								selectedReturnDate = chooseDate;
   	   	    					toolbar.setSubtitle(changeDate(selectedReturnDate)+" [All Time]");
   	   	    					
   							//Return Trip 
   								 String fromCity = selectedToCity;
   								 String toCity = selectedFromCity;
   								 
   								 getOperatorSeats(fromCity, toCity, selectedReturnDate, "");
   							}
   					}else{
   						Toast.makeText(BusOperatorSeatsActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
   					}
					}
				});
				
				calendarDialog.show();
			}
		});
	}
	
	@Override
	public Intent getParentActivityIntent() {
		// TODO Auto-generated method stub
		if (from_intent.equals("BusConfirm")) {
			//disable back pressed
		}else {
			finish();
			
		}
		return super.getParentActivityIntent();
	}

	/**
	 *  Get Operator, Time, Class, Price, Seat available
	 * @param fromCity From City Name
	 * @param toCity To City Name
	 * @param tripDate Dept Date
	 * @param tripTime Dept Time
	 */
	private void getOperatorSeats(String fromCity, String toCity, String tripDate, String tripTime) {
		// TODO Auto-generated method stub
		lv_operator_seats.setAdapter(null);
		
		dialog = new ZProgressHUD(BusOperatorSeatsActivity.this);
		dialog.show();
		
		Log.i("", "Search Operator: "+fromCity+", "
						+toCity+", "
						+tripDate+", "
						+tripTime+", access: "+AppLoginUser.getAccessToken());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postSearch(fromCity, toCity, tripDate
				, tripTime, "", new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					OperatorSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorSeat>>(){}.getType());
					
					Log.i("", "Operator Info: "+OperatorSeats.toString());
					
					if (OperatorSeats != null && OperatorSeats.size() > 0) {
						
						Rlayout_noInfo.setVisibility(View.GONE);
						lv_operator_seats.setAdapter(new OperatorSeatsAdapter(BusOperatorSeatsActivity.this, OperatorSeats));
						setListViewHeightBasedOnChildren(lv_operator_seats);
					}else {
						Rlayout_noInfo.setVisibility(View.VISIBLE);
					}
				}
				
				dialog.dismissWithSuccess();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismissWithFailure();
			}
		});
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			if (from_intent.equals("BusConfirm")) {
				//Check go date is grater than return date
				if (checkGoDate()) {
					Bundle bundle = new Bundle();
					bundle.putString("operator_id", OperatorSeats.get(position).getOperatorId());
					bundle.putString("operator_name", OperatorSeats.get(position).getOperator());
					bundle.putString("tripId", OperatorSeats.get(position).getId());
					bundle.putString("from_city", OperatorSeats.get(position).getFromName());
					bundle.putString("to_city", OperatorSeats.get(position).getToName());
					bundle.putString("trip_date", selectedTripDate);
					bundle.putString("trip_time", OperatorSeats.get(position).getTime());
					bundle.putInt("trip_type", selectedTripType);
					bundle.putString("return_date", selectedReturnDate);
					bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
					bundle.putString("from_intent", from_intent);
					
					startActivity(new Intent(BusOperatorSeatsActivity.this, BusSelectSeatActivity.class).putExtras(bundle));
				}
			}else if (from_intent.equals("SaleTicket")) {
				Bundle bundle = new Bundle();
				bundle.putString("operator_id", OperatorSeats.get(position).getOperatorId());
				bundle.putString("operator_name", OperatorSeats.get(position).getOperator());
				bundle.putString("tripId", OperatorSeats.get(position).getId());
				bundle.putString("from_city", OperatorSeats.get(position).getFromName());
				bundle.putString("to_city", OperatorSeats.get(position).getToName());
				bundle.putString("trip_date", selectedTripDate);
				bundle.putString("trip_time", OperatorSeats.get(position).getTime());
				bundle.putInt("trip_type", selectedTripType);
				bundle.putString("return_date", selectedReturnDate);
				bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
				bundle.putString("from_intent", from_intent);
				
				startActivity(new Intent(BusOperatorSeatsActivity.this, BusSelectSeatActivity.class).putExtras(bundle));
			}
		}
	};
	
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}	
	
	private boolean checkGoDate() {
		// TODO Auto-generated method stub
			Date goDate = null;
			Date returnDate = null;
			try {
				returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(selectedReturnDate);
				goDate = new SimpleDateFormat("yyyy-MM-dd").parse(goTripInfo_obj.getDate());
				
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (returnDate != null && goDate != null) {
				int compare = returnDate.compareTo(goDate);
				
				
				Log.i("","Hello Compare : "+ compare);
				if(compare < 0){
					AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
					
					if (goTripInfo_obj.getDate() != null && !goTripInfo_obj.getDate().equals("")) {
						alertDialog.setMessage("Departure Date can't be grater than Return Date! Your Chosen Departure Date is : "+changeDateString(goTripInfo_obj.getDate()));
					}
					
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
							return;
						}
					});
					
					alertDialog.show();
					
					return false;
				}
			}
			
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (from_intent.equals("BusConfirm")) {
			//disable back pressed !!!!!!!!!!!!!!!!!!!!!!!!
		}else {
			finish();
		}
	}
}
