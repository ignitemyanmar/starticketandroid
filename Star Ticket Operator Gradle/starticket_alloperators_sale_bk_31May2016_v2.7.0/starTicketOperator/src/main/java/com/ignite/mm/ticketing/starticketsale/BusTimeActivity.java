package com.ignite.mm.ticketing.starticketsale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActionBarActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TimeAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllTimeBundleListObject;
import com.ignite.mm.ticketing.sqlite.database.model.City;
import com.ignite.mm.ticketing.sqlite.database.model.OAuth2Error;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;
import com.ignite.mm.ticketing.sqlite.database.model.Time;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BusTimeActivity extends BaseActionBarActivity {
	
	private SKConnectionDetector connectionDetector;
	private Spinner from , to , time , operator;
	private Button date;
	public static String selectedFromId = ""; 
	public static String selectedToId = ""; 
	private String selectedTime = "";
	private String selectedOperatorId = "";
	private String selectedAgentId = "";
	public static String selectedDate = "";	
	private  List<Time> time_list;
	private Operators operatorList;
	protected List<City> cities;
	protected List<Operator> operators;
	private ListView lst_morning_time;
	private TextView txt_operator;
	private LinearLayout layout_operator;
	private ListView lst_evening_time;
	private List<Time> time_morning_list;
	private List<Time> time_evening_list;
	private String selectedFrom;
	private String selectedTo;
	private Integer NotifyBooking;
	protected String selectedClasses;
	private ZProgressHUD dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.busdest_list);
		
		connectionDetector = new SKConnectionDetector(this);
			
		from = (Spinner)findViewById(R.id.spn_from);
		
		to = (Spinner)findViewById(R.id.spn_to);
		
		time = (Spinner)findViewById(R.id.spn_time);
		
		txt_operator = (TextView)findViewById(R.id.txt_operator);
		layout_operator = (LinearLayout)findViewById(R.id.layout_operator);
		operator =  (Spinner)findViewById(R.id.spn_operator);
		lst_morning_time = (ListView) findViewById(R.id.lst_time_morning);
		lst_morning_time.setOnItemClickListener(morningTimeClickListener);
		
		lst_evening_time = (ListView) findViewById(R.id.lst_time_evening);
		lst_evening_time.setOnItemClickListener(eveningTimeClickListener);
			
		
		Bundle bundle = getIntent().getExtras();
		selectedFromId = bundle.getString("from_id");
		selectedToId = bundle.getString("to_id");
		selectedFrom = bundle.getString("from");
		selectedTo = bundle.getString("to");
		selectedDate = bundle.getString("date");
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            toolbar.setTitle(selectedFrom+" - "+selectedTo);
            toolbar.setSubtitle(changeDate(selectedDate));
            this.setSupportActionBar(toolbar);
        }
        
        dialog = new ZProgressHUD(this);
		
		if(connectionDetector.isConnectingToInternet()){
			dialog.show();
			SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
			String user_id = pref.getString("user_id", null);
			String user_type = pref.getString("user_type", null);
			if(user_type.equals("operator")){
				txt_operator.setVisibility(View.GONE);
				layout_operator.setVisibility(View.GONE);
				selectedOperatorId = user_id;
				getTime();
			}else{
				getOperator();
				operator.setOnItemSelectedListener(operatorClickListener);
			}
		}else{
			connectionDetector.showErrorMessage();
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(selectedTime.length() != 0){
			finish();
		}
	}
	
	private AllTimeBundleListObject bundleAllTimes;
	
	private void getTime() {
		String param = MCrypt.getInstance().encrypt(SecureParam.getTimesParam(AppLoginUser.getAccessToken(), AppLoginUser.getUserID(), selectedFromId, selectedToId, selectedDate));
		NetworkEngine.getInstance().getAllTime(param, new Callback<Response>() {

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				time_morning_list = new ArrayList<Time>();
				time_evening_list = new ArrayList<Time>();
				
				List<Time> times = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Time>>() {}.getType());
				
				for(Time time: times){
					if(time.getTime().toLowerCase().contains("am")){
						time_morning_list.add(time);
					}else{
						time_evening_list.add(time);
					}
				}
				
				lst_morning_time.setAdapter(new TimeAdapter(BusTimeActivity.this, time_morning_list));
				setListViewHeightBasedOnChildren(lst_morning_time);
				lst_evening_time.setAdapter(new TimeAdapter(BusTimeActivity.this, time_evening_list));
				setListViewHeightBasedOnChildren(lst_evening_time);
				
				//Put All Times into bundle object to send to BusSelectSeatActivity
				bundleAllTimes = new AllTimeBundleListObject();
				for (int i = 0; i < time_morning_list.size(); i++) {
					bundleAllTimes.getAllTimes().add(time_morning_list.get(i));
				}
				for (int i = 0; i < time_evening_list.size(); i++) {
					bundleAllTimes.getAllTimes().add(time_evening_list.get(i));
				}
				
				if(time_morning_list.size() == 0 && time_evening_list.size() == 0){
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(BusTimeActivity.this);
					alertDialog.setMessage("There is no trip yet.");
					alertDialog.setCancelable(false);
					alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
					alertDialog.show();
				}
				
				if (dialog != null) {
					dialog.dismissWithSuccess();
				}
				
			}
			
			public void failure(RetrofitError arg0) {
				
				if (dialog != null) {
					dialog.dismissWithFailure();
				}
				
			}
		});
	}
	
	
	private OnItemClickListener morningTimeClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.i("", "morning arg2: "+arg2);
			
			selectedTime = time_morning_list.get(arg2).getTime();
			selectedClasses = time_morning_list.get(arg2).getClass_id();
			Bundle bundle = new Bundle();
	        bundle.putString("agent_id", selectedAgentId);
			bundle.putString("operator_id", selectedOperatorId);
			bundle.putString("from_city_id", selectedFromId);
			bundle.putString("from_city", selectedFrom);
			bundle.putString("to_city_id", selectedToId);
			bundle.putString("to_city", selectedTo);
			bundle.putString("class_id", selectedClasses);
			bundle.putString("time", selectedTime);
			bundle.putString("date", selectedDate);
			bundle.putString("trip_id", time_morning_list.get(arg2).getTripid());
			bundle.putString("all_times", new Gson().toJson(bundleAllTimes));
			startActivity(new Intent(getApplicationContext(), BusSeatViewPagerActivity.class).putExtras(bundle));
			
		}
	};
	
	private OnItemClickListener eveningTimeClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.i("", "evening arg2: "+arg2);
			
			selectedTime = time_evening_list.get(arg2).getTime();
			selectedClasses = time_evening_list.get(arg2).getClass_id();
			Bundle bundle = new Bundle();
	        bundle.putString("agent_id", selectedAgentId);
			bundle.putString("operator_id", selectedOperatorId);
			bundle.putString("from_city_id", selectedFromId);
			bundle.putString("from_city", selectedFrom);
			bundle.putString("to_city_id", selectedToId);
			bundle.putString("to_city", selectedTo);
			bundle.putString("class_id", selectedClasses);
			bundle.putString("time", selectedTime);
			bundle.putString("date", selectedDate);
			bundle.putString("trip_id", time_evening_list.get(arg2).getTripid());
			bundle.putString("all_times", new Gson().toJson(bundleAllTimes));
			startActivity(new Intent(getApplicationContext(), BusSeatViewPagerActivity.class).putExtras(bundle));
			
		}
	};
		
	private OnItemSelectedListener operatorClickListener = new OnItemSelectedListener() {
	
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedOperatorId = operators.get(arg2).getId();
			dialog.show();
			getTime();
			
		}
	
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getOperator() {
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		NetworkEngine.getInstance().getAllOperator(accessToken, new Callback<Response>() {
	
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				operatorList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Operator>() {}.getType());
				operators = new ArrayList<Operator>();
				operators.addAll(operatorList.getOperators());
				operator.setAdapter(new OperatorListAdapter(BusTimeActivity.this, operators));
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				OAuth2Error error = (OAuth2Error) arg0.getBodyAs(OAuth2Error.class);
				Log.i("","Hello Error Response Code : "+arg0.getResponse().getStatus());
				Log.i("","Hello Error : "+error.getError());
				Log.i("","Hello Error Desc : "+error.getError_description());
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
		}else {
			SharedPreferences notify = getSharedPreferences("NotifyBooking",
					Context.MODE_PRIVATE);
			NotifyBooking = notify.getInt("count", 0);
			if (NotifyBooking > 0) {
				menu.getItem(0).setTitle(NotifyBooking.toString());
			}
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
	        case R.id.action_booking_noti:
	        	SharedPreferences sharedPreferences = getSharedPreferences("order", MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", getToday());
				editor.commit();
				startActivity(new Intent(getApplicationContext(),BusBookingListActivity.class));
				return true;
	        	
		   	}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void setListViewHeightBasedOnChildren(ListView listView) {
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
}
