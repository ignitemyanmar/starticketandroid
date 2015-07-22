
package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.BookingFilterDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OrderListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Booking;
import com.ignite.mm.ticketing.sqlite.database.model.BookingSearch;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;
import com.ignite.mm.ticketing.sqlite.database.model.From;
import com.ignite.mm.ticketing.sqlite.database.model.TimesbyOperator;
import com.ignite.mm.ticketing.sqlite.database.model.To;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusBookingListActivity extends BaseActivity {
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
	//protected List<Booking> bookingList;
	protected List<Booking> bookingListByUser;
	private String tripId = "";
	private String intents;
	private LinearLayout layout_booking_cancel;
	private View view_cover;

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
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Booking စာရင္း  ");
            this.setSupportActionBar(toolbar);
        }
		
		auto_txt_codeno = (EditText)findViewById(R.id.auto_txt_codeno);
		btn_search_codeno = (Button)findViewById(R.id.btn_search_codeno);
		btn_search_codeno.setOnClickListener(clickListener);
		
		layout_booking_cancel = (LinearLayout)findViewById(R.id.layout_booking_cancel);
		view_cover = (View)findViewById(R.id.view_cover);
		
		//btn_search = (Button) findViewById(R.id.btn_search);
		//btn_search.setOnClickListener(clickListener);
		
		SharedPreferences pref = getSharedPreferences("order", Activity.MODE_PRIVATE);
		String orderDate = pref.getString("order_date", "");
		BookCode = pref.getString("book_code", "");
		
		Log.i("", "Book Code to search: "+BookCode);		
		
		lv_booking_list = (ListView) findViewById(R.id.lv_booking_list);
		credit_list = new ArrayList<CreditOrder>();

		connectionDetector = SKConnectionDetector.getInstance(this);
		if(connectionDetector.isConnectingToInternet()){
			getBookingListByUser();
		}else{
			connectionDetector.showErrorMessage();
		}
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	/**
	 *  Get Booking List (filter) By User (or) By User+Order id
	 */
	private void getBookingListByUser() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingListByUser(AppLoginUser.getAccessToken(), ""
				, tripId, AppLoginUser.getId(), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail : "+arg0.getResponse().getStatus());
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					bookingListByUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						for (int i = 0; i < bookingListByUser.size(); i++) {
							String changeDate = changeDateString(bookingListByUser.get(i).getDate());
							Booking book = (Booking)bookingListByUser.get(i);
							book.setDate(changeDate);
						}
						
						layout_booking_cancel.setVisibility(View.VISIBLE);
						//view_cover.setVisibility(View.VISIBLE);
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
					}else {
						//showAlert("Booking မွာထားျခင္း မရွိေသးပါ");
						SKToastMessage.showMessage(BusBookingListActivity.this, "Booking မွာထားျခင္း မရွိေသးပါ", SKToastMessage.INFO);
						layout_booking_cancel.setVisibility(View.GONE);
						//view_cover.setVisibility(View.GONE);
						lv_booking_list.setAdapter(null);
					}
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
	}

	private void getBookingList() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        NetworkEngine.setIP("test.starticketmyanmar.com");
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
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						for (int i = 0; i < bookingListByUser.size(); i++) {
							String changeDate = changeDateString(bookingListByUser.get(i).getDate());
							Booking bs = (Booking)bookingListByUser.get(i);
							bs.setDate(changeDate);
						}
						
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
						
					}else {
						//showAlert("No Booking!");
						lv_booking_list.setAdapter(null);
					}
				}
				
				dialog.dismiss();
			}
		});
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
						connectionDetector.showErrorDialog();
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

        NetworkEngine.setIP("test.starticketmyanmar.com");
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
        
        NetworkEngine.setIP("test.starticketmyanmar.com");
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
					bookingListByUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						String changeDate = changeDateString(bookingListByUser.get(0).getDate());
						String deptDate = changeDateString(bookingListByUser.get(0).getDepartureDate());
						Booking bs = (Booking)bookingListByUser.get(0);
						bs.setDate(changeDate);
						bs.setDepartureDate(deptDate);
						
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
					}else {
						showAlert("၄င္း ကုတ္နံပါတ္ မရွိပါ။ တျခား ကုတ္ နံပါတ္ (သုိ႔ ) ဖုန္းနံပါတ္ ျဖင့္ ရွာၾကည့္ပါ။");
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
		
		if (alert != null) {
			alert.show();
		}
	}
	
}
