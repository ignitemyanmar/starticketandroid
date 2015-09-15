package com.ignite.mm.ticketing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActionBarActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.CloseSeatDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.EditSeatDialog;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusClassAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusSeatAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.EditBusSeatAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.GroupUserListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.RemarkListAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import com.ignite.mm.ticketing.sqlite.database.model.AgentList;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mm.ticketing.sqlite.database.model.ReturnComfrim;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;
import com.ignite.mm.ticketing.sqlite.database.model.SeatPlan;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeat;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #EditBusSelectSeatActivity} is the class to do Own Seat Open and Close
 * <p>
 * Private methods:
 * (1) {@link #getSeatPlan()}
 * (2) {@link #postCloseSeat()}
 * (3) {@link #postOpenSeat()}
 * <p>
 * ** Star Ticket Operator App is used to sell bus tickets via online. 
 * @version 2.0 
 * @author Su Wai Phyo (Ignite Software Solutions)
 * <p>
 * Last Modified : 14/Sept/2015
 * <p>
 * Last ModifiedBy : Saw Maine K
 */
public class EditBusSelectSeatActivity extends BaseActionBarActivity{
	
	public static List<BusSeat> Bus_Seat;
	private GridView mSeat;
	public static String SelectedSeat;
	protected ArrayList<Seat> Seat;
	protected ZProgressHUD dialog;
	private SKConnectionDetector connectionDetector;
	protected ReturnComfrim returnComfirm;
	private String AgentID = "0";
	private String CustName = "";
	private String CustPhone = "";
	private int RemarkType = 0;
	private String Remark = "";
	private String OperatorID;
	private String FromCity;
	private String ToCity;
	private String Time;
	private String Date;
	private TextView txt_operator;
	private TextView txt_classes;
	private TextView txt_price;
	private TextView txt_dept_date;
	private TextView txt_dept_time;
	private Button btn_booking;
	private String From;
	private String To;
	private Button btn_now_booking;
	private Button btn_check_out;
	private String Classes;
	protected BookingDialog bookingDialog;
	private List<Seat_list> remarkSeats;
	private ListView lst_remark;
	private String BusClasses;
	private LinearLayout layout_remark;
	private EditBusSeatAdapter seatAdapter;
	protected List<Agent> agentList;
	private String TripId;
	private Button btn_closeseat;
	private Button btn_openseat;
	private Bundle intentParams;
	private FrameLayout loading;
	public static List<BusSeat> BusSeats;
	public static List<OperatorGroupUser> groupUser = new ArrayList<OperatorGroupUser>();
	public static String CheckOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_bus_seat_list);

		mSeat = (GridView) findViewById(R.id.grid_seat);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		loading = (FrameLayout) findViewById(R.id.loading);
		connectionDetector = new SKConnectionDetector(this);
		
		intentParams = getIntent().getExtras();	
		AgentID = intentParams.getString("agent_id");
		OperatorID = intentParams.getString("operator_id");
		FromCity = intentParams.getString("from_city_id");
		ToCity = intentParams.getString("to_city_id");
		From = intentParams.getString("from_city");
		To = intentParams.getString("to_city");
		Classes = intentParams.getString("class_id");
		Time = intentParams.getString("time");
		Date = intentParams.getString("date");
		TripId = intentParams.getString("trip_id");
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle(From+" - "+To);
            toolbar.setSubtitle(changeDate(Date) +" "+Time);
            toolbar.setVisibility(View.VISIBLE);
            this.setSupportActionBar(toolbar);
        }
		
		SelectedSeat 	= "";
		btn_booking		= (Button) findViewById(R.id.btn_booking);
		btn_booking.setOnClickListener(clickListener);
		btn_now_booking = (Button) findViewById(R.id.btn_now_booking);
		btn_now_booking.setOnClickListener(clickListener);
		btn_check_out = (Button) findViewById(R.id.btn_check_out);
		btn_check_out.setOnClickListener(clickListener);
		txt_operator = (CustomTextView) findViewById(R.id.txt_operator);
		txt_classes = (CustomTextView) findViewById(R.id.txt_classes);
		txt_price = (CustomTextView) findViewById(R.id.txt_price);
		txt_dept_date = (CustomTextView) findViewById(R.id.txt_departure_date);
		txt_dept_date.setText("ထြက္ခြာမည့္ ေန႕ရက္ : "+ Date);
		txt_dept_time = (CustomTextView) findViewById(R.id.txt_departure_time);
		txt_dept_time.setText("ထြက္ခြာမည့္ အခ်ိန္ : "+ Time);
		btn_closeseat = (Button) findViewById(R.id.btn_close_seat);
		btn_closeseat.setOnClickListener(clickListener);
		btn_openseat = (Button) findViewById(R.id.btn_open_seat);
		btn_openseat.setOnClickListener(clickListener);
		
		Date today = null;
    	Date formatedDate = null;
		try {
			formatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(Date);
			today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int compare = today.compareTo(formatedDate);
		if(compare > 0){
			btn_check_out.setVisibility(View.GONE);
			btn_now_booking.setVisibility(View.GONE);
			btn_booking.setVisibility(View.GONE);
			btn_openseat.setVisibility(View.GONE);
			btn_closeseat.setVisibility(View.GONE);
		}
		btn_check_out.setVisibility(View.GONE);
		btn_now_booking.setVisibility(View.GONE);
		btn_booking.setVisibility(View.GONE);
		
		agentList = BusSeatViewPagerActivity.agentList;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(SelectedSeat.length() != 0){
			SelectedSeat = "";
		}
			
		if(connectionDetector.isConnectingToInternet())
		{ 	
			loading.setVisibility(View.VISIBLE);
			getSeatPlan();
		}else{
			connectionDetector.showErrorMessage();
		}
	}
	
	/**
	 *  Get Seat Plan for selected seats
	 */
	private void getSeatPlan() {
		String param = MCrypt.getInstance().encrypt(SecureParam.getSeatPlanParam(AppLoginUser.getAccessToken(), OperatorID, TripId, FromCity, ToCity, Classes, Date, Time));
		NetworkEngine.getInstance().getItems(param, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				// Try to get response body
				SelectedSeat = "";
				BusSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<BusSeat>>() {}.getType());
				getData();
				loading.setVisibility(View.GONE);
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	/**
	 *  Give Own Seats to agents
	 */
	private void postCloseSeat(){
		if(SelectedSeat.length() > 0){
			CloseSeatDialog closeSeatDialog = new CloseSeatDialog(this, agentList);
			closeSeatDialog.setCallbackListener(new CloseSeatDialog.Callback() {
				
				public void onSave(String agentId, String remark) {
					// TODO Auto-generated method stub
					dialog = new ZProgressHUD(EditBusSelectSeatActivity.this);
					dialog.show();
					List<SelectSeat> seats = new ArrayList<SelectSeat>();
					String[] selectedSeat = SelectedSeat.split(",");
					for (int i = 0; i < selectedSeat.length; i++) {
						seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0).getId(),BusSeats.get(0).getSeat_plan().get(0).getSeat_list().get(Integer.valueOf(selectedSeat[i])).getSeat_no().toString()));
					}
					NetworkEngine.getInstance().postCloseSeat(
							AppLoginUser.getAccessToken(), Date, 
							BusSeats.get(0).getSeat_plan().get(0).getId(), 
							remark,
							seats.toString(),
							agentId,
							new Callback<Response>() {

						public void failure(RetrofitError arg0) {
							// TODO Auto-generated method stub
							dialog.dismissWithFailure();
							
						}

						public void success(Response arg0, Response arg1) {
							// TODO Auto-generated method stub
							dialog.dismissWithSuccess();
							onResume();
							
						}
					});
				}
				
				public void onCancel() {
					// TODO Auto-generated method stub
					
				}
			});
			
		}else{
			SKToastMessage.showMessage(EditBusSelectSeatActivity.this, "Please choose the seat.", SKToastMessage.ERROR);
		}
		
	}
	
	/**
	 * Close Own seats
	 */
	private void postOpenSeat(){
		if(SelectedSeat.length() > 0){
			dialog = new ZProgressHUD(this);
			dialog.show();
			List<SelectSeat> seats = new ArrayList<SelectSeat>();
			String[] selectedSeat = SelectedSeat.split(",");
			for (int i = 0; i < selectedSeat.length; i++) {
				seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0).getId(),BusSeats.get(0).getSeat_plan().get(0).getSeat_list().get(Integer.valueOf(selectedSeat[i])).getSeat_no().toString()));
			}
			Log.i("","Hello Seat: "+ seats.toString());
			NetworkEngine.getInstance().postOpenSeat(AppLoginUser.getAccessToken(), Date, BusSeats.get(0).getSeat_plan().get(0).getId(), seats.toString(), new Callback<Response>() {

				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					dialog.dismissWithFailure();
					
				}

				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub
					dialog.dismissWithSuccess();
					onResume();
					
				}
			});
		}	
		
	}
	
		
	private void getData() {
		if(BusSeats.size() > 0){
			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္အမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(0).getClasses());
			txt_price.setText("ေစ်းႏႈန္း :"+ BusSeats.get(0).getSeat_plan().get(0).getPrice()+" Ks");
			BusClasses = BusSeats.get(0).getSeat_plan().get(0).getClasses();
			
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());
			seatAdapter = new EditBusSeatAdapter(this, BusSeats.get(0).getSeat_plan().get(0).getSeat_list());
			mSeat.setAdapter(seatAdapter);	
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));

		}else{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setMessage("There is no bus yet.");
			alertDialog.setCancelable(false);
			alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			alertDialog.show();
		}
	}
	
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {

			if(v == btn_closeseat){
				postCloseSeat();
			}
			
			if(v == btn_openseat){
				postOpenSeat();
			}
		}
	};
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bus_edit_selectseat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
	        case R.id.action_delete:
	        	alertDialog("Are you sure, you want to delete?", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						final ZProgressHUD zp_dialog = ZProgressHUD.getInstance(EditBusSelectSeatActivity.this);
						zp_dialog.show();
						List<SelectSeat> seats = new ArrayList<SelectSeat>();
						String[] selectedSeat = SelectedSeat.split(",");
						for (int i = 0; i < selectedSeat.length; i++) {
							seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0).getId(),BusSeats.get(0).getSeat_plan().get(0).getSeat_list().get(Integer.valueOf(selectedSeat[i])).getSeat_no().toString()));
						}
						String seatlist = MCrypt.getInstance().encrypt(seats.toString());
				        String param = MCrypt.getInstance().encrypt(SecureParam.deleteTicketParam(AppLoginUser.getAccessToken(), BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date, seatlist, AppLoginUser.getLoginUserID()));
						NetworkEngine.getInstance().deleteTicket(param,
								new Callback<Response>() {

									public void success(Response arg0,Response arg1) {
										// TODO Auto-generated
										// method stub
										zp_dialog.dismiss();
										onResume();
										SKToastMessage
												.showMessage(
														EditBusSelectSeatActivity.this,
														"Successfully Deleted.",
														SKToastMessage.SUCCESS);
										
									}

									public void failure(RetrofitError arg0) {
										zp_dialog.dismiss();
									}
								});
						}
				}, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				return true;
	        	
		   	}
		return super.onOptionsItemSelected(item);
	}

	public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int items = listAdapter.getCount();
		int rows = 0;

		View listItem = listAdapter.getView(0, null, gridView);
		listItem.measure(0, 0);
		totalHeight = listItem.getMeasuredHeight();

		float x = 1;
		if (items > columns) {
			x = items / columns;
			rows = (int) (x + 1);
			totalHeight *= rows;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);

	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		RelativeLayout focuslayout = (RelativeLayout) findViewById(R.id.layout_seat_plan);
		focuslayout.requestFocus();
		super.onStart();
	}
}

