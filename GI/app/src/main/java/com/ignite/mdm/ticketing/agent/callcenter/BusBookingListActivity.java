
package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.OrderListViewAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.TourBookingLvAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.Booking;
import com.ignite.mdm.ticketing.sqlite.database.model.CreditOrder;
import com.ignite.mdm.ticketing.sqlite.database.model.From;
import com.ignite.mdm.ticketing.sqlite.database.model.TimesbyOperator;
import com.ignite.mdm.ticketing.sqlite.database.model.To;
import com.ignite.mdm.ticketing.sqlite.database.model.TourBooking;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BookingFilterDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BusBookingListActivity extends BaseSherlockActivity {
	private ListView lv_booking_list;
	private List<CreditOrder> credit_list;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	
	private Button btn_search;
	private String BookCode = "";
	private EditText auto_txt_codeno;
	private Button btn_search_codeno;
	private TextView action_bar_title2;
	private SKConnectionDetector connectionDetector;
	private String operatorId;
	private String book_code;
	protected List<Booking> bookingList;
	protected List<Booking> bookingListByUser;
	private String tripId = "";
	private String intents;
	private Spinner spn_book_type;
	private List<String> menu_list;
	private Integer selectedBookType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			tripId  =  bundle.getString("trip_id");
			intents  =  bundle.getString("from_intent");
		}
		
		setContentView(R.layout.activity_bus_booking_list);
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
    		if (intents != null) {
    			if (intents.equals("reservation")) {
    				toolbar.setTitle(R.string.strmm_booking);
    			}else if (intents.equals("reservationUser")) {
    				//toolbar.setTitle("Booking စာရင္း  ("+AppLoginUser.getUserName()+")");
    			}else if (intents.equals("BusSelectSeat")) {     
    				//toolbar.setTitle("Booking စာရင္း  ("+AppLoginUser.getUserName()+")");
    			}
    		}
            this.setSupportActionBar(toolbar);
        }
		
        spn_book_type = (Spinner)findViewById(R.id.spn_book_type);
		auto_txt_codeno = (EditText)findViewById(R.id.auto_txt_codeno);
		btn_search_codeno = (Button)findViewById(R.id.btn_search_codeno);
		btn_search_codeno.setOnClickListener(clickListener);
		
		//btn_search = (Button) findViewById(R.id.btn_search);
		//btn_search.setOnClickListener(clickListener);
		
		SharedPreferences pref = getSharedPreferences("order", Activity.MODE_PRIVATE);
		String orderDate = pref.getString("order_date", "");
		BookCode = pref.getString("book_code", "");
		
		Log.i("", "Book Code to search: "+BookCode);		
		
		lv_booking_list = (ListView) findViewById(R.id.lv_booking_list);
		credit_list = new ArrayList<CreditOrder>();
		lv_booking_list.setOnItemClickListener(itemClickListener);
		lv_booking_list.setClickable(true);
		
		menu_list = new ArrayList<String>();
		menu_list.add(getString(R.string.strmm_bus_ticket));
		//menu_list.add(getString(R.string.strmm_tourpackage));
		
		ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(getBaseContext()
						, android.R.layout.simple_dropdown_item_1line, menu_list);
		
		spn_book_type.setAdapter(menuAdapter);
		spn_book_type.setOnItemSelectedListener(onItemSelectedListener);
		//getTimeData();
		//getCity();
	}
	
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			selectedBookType = position;
			
			if (position == 0) {
				if(connectionDetector.isConnectingToInternet()){
					getBookingList();
				}else{
					Toast.makeText(BusBookingListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}else if (position == 1) {
				
				getTourBookingList();
				//getTourBookingList();
				
				/*if(connectionDetector.isConnectingToInternet()){
					getBookingList();
				}else{
					Toast.makeText(BusBookingListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}*/
			}else {
				//Do nothing
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		connectionDetector = SKConnectionDetector.getInstance(this);
		if(connectionDetector.isConnectingToInternet()){
			if (intents != null) {
				if (intents.equals("reservation")) {
					//getBookingList();
				}else if (intents.equals("reservationUser")) {
					//getBookingListByUser();
				}else if (intents.equals("BusSelectSeat")) {
					//getBookingListByUser();
				}
			}else {
				finish();
			}
		}else{
			Toast.makeText(BusBookingListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 *  Get Booking List (filter) By User (or) By User+Order id
	 */
	private void getBookingListByUser() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingListByUser(AppLoginUser.getAccessToken(), ""
				, tripId, AppLoginUser.getId(), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail : "+arg0.getResponse().getStatus());
				}
				dialog.dismiss();
			}


			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					Log.i("", "booking server : "+arg0.toString());
					
					bookingListByUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					Log.i("", "booking list : "+bookingListByUser.toString());
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						for (int i = 0; i < bookingListByUser.size(); i++) {
							String changeDate = changeDateString(bookingListByUser.get(i).getDate());
							Booking book = (Booking)bookingListByUser.get(i);
							book.setDate(changeDate);
						}
						
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
					}else {
						showAlert("No Booking!");
						lv_booking_list.setAdapter(null);
					}
				}
				
				dialog.dismiss();
			}
		});
	}

	private void getBookingList() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingListAll(AppLoginUser.getAccessToken(), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail : "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					bookingListByUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					Log.i("", "Booking List All: "+bookingListByUser.toString());
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						for (int i = 0; i < bookingListByUser.size(); i++) {
							String changeDate = changeDateString(bookingListByUser.get(i).getDate());
							Booking bs = (Booking)bookingListByUser.get(i);
							bs.setDate(changeDate);
						}
						
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
					}else {
						showAlert("No Booking!");
						lv_booking_list.setAdapter(null);
					}
				}
				
				dialog.dismiss();
			}
		});
	}
	
	private void getTourBookingList() {
		// TODO Auto-generated method stub
		
		List<TourBooking> tourBookingList = new ArrayList<TourBooking>();
		tourBookingList.add(new TourBooking("Myawaddy Travels & Tour", "ရန္ကုန္", "မ�?�?�ေလး", "2", "3", "29"
				, 30000.0, "Test Test Test", "12-3-2016", "hotel", "food", 5, "00101200001234", "Su Su", "09000000", "", "01 Mar, 2016"));
		tourBookingList.add(new TourBooking("OK Travels & Tour", "ရန္ကုန္", "အင္းေလး - ေ�?ာင္ႀကီး", "3", "4", "45"
				, 60000.0, "Test Test Test", "15-3-2016", "hotel", "food", 2, "00111300001235", "Mya Mya", "09111111", "", "01 Mar, 2016"));
		tourBookingList.add(new TourBooking("ABC Travels & Tour", "ရန္ကုန္", "ပုဂံ-ေညာင္ဦး", "2", "3", "29"
				, 70000.0, "Test Test Test", "12-3-2016", "hotel", "food", 3, "00121300001236", "Hla Hla", "09222222", "", "01 Mar, 2016"));
		
		lv_booking_list.setAdapter(new TourBookingLvAdapter(BusBookingListActivity.this, tourBookingList, AppLoginUser.getRole()));
		lv_booking_list.setDividerHeight(0);
		
		
/*		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingListAll(AppLoginUser.getAccessToken(), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail : "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					bookingListByUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					Log.i("", "Booking List All: "+bookingListByUser.toString());
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						for (int i = 0; i < bookingListByUser.size(); i++) {
							String changeDate = changeDateString(bookingListByUser.get(i).getDate());
							Booking bs = (Booking)bookingListByUser.get(i);
							bs.setDate(changeDate);
						}
						
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
					}else {
						showAlert("No Booking!");
						lv_booking_list.setAdapter(null);
					}
				}
				
				dialog.dismiss();
			}
		});*/
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			if(v == btn_search_codeno){
				if (auto_txt_codeno.getText().toString().length() == 0) {
					SKToastMessage.showMessage(BusBookingListActivity.this, "Enter Booking Code No (or) Phone No.", SKToastMessage.WARNING);
				}else {
					
					book_code = auto_txt_codeno.getText().toString(); 
					
					if(connectionDetector.isConnectingToInternet()){
						getBookingListByCodeNoPhone();
					}else{
						connectionDetector.showErrorMessage();
					}
				}
			}
		}
	};
	
	private void ShowFilterDialog() {
		// TODO Auto-generated method stub
		
		BookingFilterDialog filterDialog = new BookingFilterDialog(BusBookingListActivity.this);
		filterDialog.setFromCity(fromCities);
		filterDialog.setToCity(toCities);
		filterDialog.setTime(Times);
		filterDialog.setCallbackListener(new BookingFilterDialog.Callback() {
			
			public void onSave(String date, String from_id, String to_id, String time) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", date);
				editor.putString("from", from_id);
				editor.putString("to", to_id);
				editor.putString("time", time);
				editor.commit();
				//getBookingList();
			}
			
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		filterDialog.show();
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), BusBookingConfirmDeleteActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("booking_order", new Gson().toJson(bookingListByUser.get(arg2)));
			
			intent.putExtras(bundle);
			startActivity(intent);
			
		}
	};
	private ProgressDialog dialog;
	protected List<From> fromCities;
	protected List<To> toCities;
	protected List<TimesbyOperator> Times;
	private void getCity() {
/*		NetworkEngine.getInstance().getCitybyOperator(AppLoginUser.getAccessToken(), AppLoginUser.getUserID(), new Callback<Cities>() {
		
			public void success(Cities arg0, Response arg1) {
				// TODO Auto-generated method stub
				fromCities = arg0.getFrom();
				toCities = arg0.getTo();
			}

			public void failure(RetrofitError arg0) {
				
			}
			
		});*/
	}
	
	private void getTimeData() {
/*		NetworkEngine.getInstance().getTimebyOperator(AppLoginUser.getAccessToken(), AppLoginUser.getUserID() , new Callback<List<TimesbyOperator>>() {

			public void success(List<TimesbyOperator> arg0, Response arg1) {
				// TODO Auto-generated method stub
				Times = arg0;
			}
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}

	
	/*private void getBookingListByCodeNo(){
		Log.i("", "Enter here............. get booking list!!!");
		
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);

        NetworkEngine.setIP("starticketmyanmar.com");
        NetworkEngine.getInstance().getPermission(AppLoginUser.getAccessToken(), operatorId, new Callback<Response>() {
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
					Log.i("", "Trip Operator ID: "+operatorId);
				}
				
				dialog.dismiss();
			}
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>(){}.getType());
					
					if (permission != null) {
						Log.i("", "Trip Operator ID: "+operatorId);
						Log.i("", "Permission: "+permission.toString());
						
						permit_ip = permission.getIp();
						permit_access_token = permission.getAccess_token();
						permit_operator_id = permission.getOperator_id();
						permit_operator_group_id = permission.getOperatorgroup_id();
						permit_agent_id = permission.getOnlinesaleagent_id();	
						
						String book_code = auto_txt_codeno.getText().toString(); 
				        
				        Log.i("", "Booking Code (User Input) : "+book_code);
						
				        NetworkEngine.setIP(permit_ip);
						
						Log.i("", "Permit IP: "+permit_ip);
						Log.i("", "Network engine instance: "+NetworkEngine.instance);
						
				        String param = MCrypt.getInstance().encrypt(SecureParam.getBookingOrderParam(permit_access_token, permit_operator_id
				        		, "", "", "", "", book_code));
				        
				        Log.i("", "Param(Reservation): "+param);
						NetworkEngine.getInstance().getBookingOrder(param, new Callback<Response>() {

							public void failure(RetrofitError arg0) {
								// TODO Auto-generated method stub
								Log.i("","Failure : "+ arg0.getCause());
								dialog.dismiss();
								showAlert();
								lv_booking_list.setAdapter(null);
							}

							public void success(Response arg0, Response arg1) {
								// TODO Auto-generated method stub
								if (arg0 != null) {
									
									credit_list = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<CreditOrder>>(){}.getType());
									
									if (credit_list != null && credit_list.size() > 0) {
										
										Log.i("","Hello Credit List: "+ credit_list.toString());
										
										List<Saleitem> seats = new ArrayList<Saleitem>();
										
										seats = credit_list.get(0).getSaleitems();
										String bus_seats = "";
										for (int i = 0; i < seats.size(); i++) {
											if (i == seats.size()-1) {
												bus_seats += seats.get(i).getSeatNo();
											}else {
												bus_seats += seats.get(i).getSeatNo()+",";
											}
										}
										
										String changeDate = changeDateString(credit_list.get(0).getDate());
										CreditOrder co = (CreditOrder)credit_list.get(0);
										co.setDate(changeDate);
										
										lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, credit_list, bus_seats));
									}else {
										showAlert();
										lv_booking_list.setAdapter(null);
									}
									
									dialog.dismiss();
								}
							}
						});
					}
				}
			}
		});
	}*/
	
	private void getBookingListByCodeNoPhone() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingByCodeNoPhone(AppLoginUser.getAccessToken(), book_code, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
					Log.i("", "Book code: "+book_code);
				}
				
				dialog.dismiss();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					bookingList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					if (bookingList != null && bookingList.size() > 0) {
						
						String changeDate = changeDateString(bookingList.get(0).getDate());
						String deptDate = changeDateString(bookingList.get(0).getDepartureDate());
						Booking bs = (Booking)bookingList.get(0);
						bs.setDate(changeDate);
						bs.setDepartureDate(deptDate);
						
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingList));
						lv_booking_list.setDividerHeight(0);
					}else {
						showAlert("Not Found This Reservation Code!");
						lv_booking_list.setAdapter(null);
					}
				}
				
				dialog.dismiss();
			}
		});
	}
	
	
	public void showAlert(String msg) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(BusBookingListActivity.this);
		//alert.setIcon(R.drawable.attention_icon);
		alert.setMessage(msg);
		alert.show();
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
	
}
