package com.ignite.mm.ticketing.starticket;

import info.hoang8f.widget.FButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusClassAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusSeatAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.BundleListObjSeats;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
import com.ignite.mm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.sqlite.database.model.ReturnComfrim;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeat;
import com.ignite.mm.ticketing.starticket.R;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #BusSelectSeatActivity} is the class to choose the seats 
 * <p>
 * Private methods
 * (1) {@link #getParentActivityIntent()}
 * (2) {@link #getPermission()}
 * (3) {@link #getSeatPlan(String)}
 * (4) {@link #getData()}
 * (5) {@link #clickListener}
 * (6) {@link #setGridViewHeightBasedOnChildren(GridView, int)}
 * (7) {@link #postSale(String)}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class BusSelectSeatActivity extends BaseActivity{
	
	public static List<BusSeat> Bus_Seat;
	private ListView lvClass;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private GridView mSeat;
	public static String SelectedSeat;
	protected ArrayList<Seat> Seat;
	protected ZProgressHUD dialog;
	private SKConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
	protected ReturnComfrim returnComfirm;
	private String AgentID = "0";
	private String CustName = "";
	private String CustPhone = "";
	private int RemarkType = 0;
	private String Remark = "";
	private String OperatorID = "0";
	private String Time;
	private String Date;
	private TextView txt_operator;
	private TextView txt_classes;
	private TextView txt_price;
	private TextView txt_dept_date;
	private TextView txt_dept_time;
	private ListView lst_group_user;
	private Button btn_booking;
	private String From;
	private String To;
	private Button btn_now_booking;
	private Integer isBooking = 0;
	private Integer NotifyBooking;
	private TextView actionBarNoti;
	private FButton btn_check_out;
	private String Classes;
	protected BookingDialog bookingDialog;
	private List<Seat_list> remarkSeats;
	private ListView lst_remark;
	private String BusClasses;
	private LinearLayout layout_remark;
	private TextView actionBarTitle2;
	private String currentDate;
	private String todayDate;
	private String todayTime;
	private String permit_access_token = "";
	private String permit_ip;
	private String operator_name = "";
	private Date tripDate;
	private Date tDate;
	private Date tripTime;
	private Date tTime;
	private String permit_operator_group_id;
	private String permit_agent_id;
	private String client_operator_id;
	private String tripId = "0";
	public static List<BusSeat> BusSeats;
	public static List<OperatorGroupUser> groupUser = new ArrayList<OperatorGroupUser>();
	public static String CheckOut;
	private Permission permission;
	private String permit_operator_id = "0";
	private BusSeatAdapter seatAdapter;
	private int trip_type;
	private TextView txt_round_trip_info;
	private String return_date;
	private String from_intent;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_seat_list);
		connectionDetector = new SKConnectionDetector(this);
		
		//Get Data from BusOperatorSeatsActivity
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			//permit_access_token = bundle.getString("permit_access_token");
			AgentID = bundle.getString("agent_id");
			OperatorID = bundle.getString("operator_id");
			FromCity = bundle.getString("from_city_id");
			ToCity = bundle.getString("to_city_id");
			From = bundle.getString("from_city");
			To = bundle.getString("to_city");
			Classes = bundle.getString("class_id");
			Time = bundle.getString("trip_time");
			Date = bundle.getString("trip_date");
			operator_name = bundle.getString("operator_name");
			tripId = bundle.getString("tripId");
			trip_type = bundle.getInt("trip_type");
			return_date = bundle.getString("return_date");
			goTripInfo_str = bundle.getString("GoTripInfo");
			goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
			from_intent = bundle.getString("from_intent");
		}
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitleTextAppearance(BusSelectSeatActivity.this, R.style.CustomToolbarTextAppearance);
            toolbar.setSubtitleTextAppearance(BusSelectSeatActivity.this, R.style.CustomToolbarSubTextAppearance);
            
            toolbar.setTitle(From+" - "+To);
            if (from_intent.equals("SaleTicket")) {
            	if (Date != null) {
    				if (!Date.equals("")) {
    					toolbar.setSubtitle(changeDate(Date)+" ["+Time+"]");
    				}
    			}
			}else if (from_intent.equals("BusConfirm")) {
				if (return_date != null) {
    				if (!return_date.equals("")) {
    					toolbar.setSubtitle(changeDate(return_date)+" ["+Time+"]");
    				}
    			}
			}
            
            this.setSupportActionBar(toolbar);
        }
        
        txt_round_trip_info = (TextView)findViewById(R.id.txt_round_trip_info);
        
        if (from_intent.equals("SaleTicket")) {
        	 if (trip_type == 1) {
             	//If One Way
             	txt_round_trip_info.setText("One Way");
     		}else {
     			txt_round_trip_info.setText(R.string.str_choose_go_seat);
     		}
		}else if (from_intent.equals("BusConfirm")) {
			txt_round_trip_info.setText(R.string.str_choose_return_seat);
		}
        
		mSeat = (GridView) findViewById(R.id.grid_seat);
		lst_group_user = (ListView) findViewById(R.id.lst_group_user);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		
		btn_check_out = (FButton) findViewById(R.id.btn_check_out);
		btn_check_out.setButtonColor(getResources().getColor(R.color.yellow));
		btn_check_out.setShadowEnabled(true);
		btn_check_out.setShadowHeight(3);
		btn_check_out.setCornerRadius(7);
		
		mNoConnection = (LinearLayout) findViewById(R.id.no_internet);
		txt_operator = (CustomTextView) findViewById(R.id.txt_operator);
		txt_classes = (CustomTextView) findViewById(R.id.txt_classes);
		txt_price = (CustomTextView) findViewById(R.id.txt_price);
		txt_dept_date = (CustomTextView) findViewById(R.id.txt_departure_date);
		txt_dept_time = (CustomTextView) findViewById(R.id.txt_departure_time);
		
		if (from_intent.equals("SaleTicket")) {
			txt_dept_date.setText("ထြက္ ခြာ မည့္ ေန႔ရက္  : "+ Date);
			txt_dept_time.setText("ထြက္ ခြာ မည့္ အခ်ိန္  : "+ Time);
		}else if (from_intent.equals("BusConfirm")) {
			txt_dept_date.setText("ျပန္လာမည့္ ေန႔ရက္ : "+ return_date);
			txt_dept_time.setText("ျပန္လာမည့္ အခ်ိန္  : "+ Time);
		}
		
		btn_check_out.setOnClickListener(clickListener);
		
		//Get TodayDate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		todayDate = sdf.format(new Date());
		
		//Get Current Time
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat writeFormat = new SimpleDateFormat("hh:mm aa");
		Calendar cal = Calendar.getInstance();
		Date tTime = null;
		try {
			tTime = sdf2.parse(sdf2.format(cal.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		todayTime = writeFormat.format(tTime);
		
		//Get only 06:00 AM format
		String time = null;
		try {
			if (Time.length() == 8) {
				time = Time;
			}else if (Time.length() < 8) {
				time = "0"+Time;
			}else if (Time.length() > 8) {
				time = Time.substring(0, 8);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			Log.i("", "Time Out Of Bound Exception: "+e);
		}
		
		SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm aa");
		Date timeTochange = null;
		try {
			if (time != null) {
				timeTochange = serverFormat.parse(time);
				Log.i("", "Server Time Format: "+serverFormat.format(timeTochange));
			}
			
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			Log.i("", "Server Time Exception: "+e2);
			e2.printStackTrace();
		}				
				
/*		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}*/
		
		SelectedSeat 	= "";
		btn_booking		= (Button) findViewById(R.id.btn_booking);
		btn_now_booking = (Button) findViewById(R.id.btn_now_booking);
		
		Log.i("", "");
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getParentActivityIntent();
	}
	
	/**
	 *  If this activity is come back, get Permission from Operator, and get Seat Plan.
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(SelectedSeat.length() != 0)
			finish();
		if(connectionDetector.isConnectingToInternet())
		{ 	
			dialog = new ZProgressHUD(BusSelectSeatActivity.this);
			dialog.show();
			
			//Get permission & Get seat plan 
			getPermission();
		}else {
			connectionDetector.showErrorMessage();
		}
	}
	
	/**
	 *  Get Permission from selected Operator to get Seat Plan
	 */
	private void getPermission() {
		// TODO Auto-generated method stub
		//1. Get Permission
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getPermission(AppLoginUser.getAccessToken(), OperatorID, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
					Log.i("", "Trip Operator ID: "+OperatorID);
				}
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>(){}.getType());
					
					if (permission != null) {
						permit_ip = permission.getIp();
						permit_access_token = permission.getAccess_token();
						permit_operator_id = permission.getOperator_id();
						permit_operator_group_id = permission.getOperatorgroup_id();
						permit_agent_id = permission.getOnlinesaleagent_id();
						
						if (from_intent.equals("SaleTicket")) {
							getSeatPlan(Date);
						}else if (from_intent.equals("BusConfirm")) {
							getSeatPlan(return_date);
						}
					}
				}
			}
		});
	}
	
	/**
	 *  Get Seat Plan from permit operator
	 * @param date Date (departure date) or (return date)
	 */
	private void getSeatPlan(String date) {
		
		String param = MCrypt.getInstance().encrypt(SecureParam.getSeatPlanParam(permit_access_token, permit_operator_id, tripId, "", "", "", date, ""));
		
		Log.i("", "Permit token: "+permit_access_token
				+", Operator Id: "+permit_operator_id
				+", Trip Id: "+tripId+", Date: "+date);
		
		Log.i("", "Param to get Seats: "+param);
		
		//NetworkEngine.setIP("128.199.81.168");
		NetworkEngine.setIP(permit_ip);
		NetworkEngine.getInstance().getItems(param, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				// Try to get response body
				if (arg0 != null) {
					SelectedSeat = "";
					
					Log.i("","Success Seat Plan: ");
					
					BusSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<BusSeat>>() {}.getType());
					
					if (BusSeats != null && BusSeats.size() > 0) {
						getData();
					}else {
						dialog.dismissWithFailure();
					}
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				dialog.dismissWithFailure();
				Log.i("","Hello Seat Error: "+ arg0.getCause());
				//Log.i("","Hello Seat Error: "+ arg0.getResponse().getBody());
				//Log.i("","Hello Seat Error: "+ arg0.getResponse().getHeaders().toString());
			}
		});
	}
		
	/**
	 * Get Seat Plan's seats and price information
	 */
	private void getData() {
		
		if(BusSeats.size() > 0){
			
			Log.i("", "Bus Seats: "+BusSeats.toString());
			//Log.i("", "Seats Plan: "+BusSeats.get(0).getSeat_plan());
			
			txt_operator.setText("ကားဂိတ္ : " +
					" "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္ အမ်ိဳးအစား :   "+ BusSeats.get(0).getSeat_plan().get(0).getClasses());
			txt_price.setText("ေစ်း ႏႈန္း :  "+ BusSeats.get(0).getSeat_plan().get(0).getPrice()+" Ks");
			
			BusClasses = BusSeats.get(0).getSeat_plan().get(0).getClasses();
			remarkSeats = new ArrayList<Seat_list>();
			
/*			Map<Integer, List<Seat_list>> map = new HashMap<Integer, List<Seat_list>>();
			for (Seat_list remarkSeat : BusSeats.get(0).getSeat_plan().get(0).getSeat_list()) {
				if(remarkSeat.getRemark_type() != 0){
					Integer key  = remarkSeat.getRemark_type();
				    if(map.containsKey(key)){
				        map.get(key).add(remarkSeat);
				    }else{
				        List<Seat_list> list = new ArrayList<Seat_list>();
				        list.add(remarkSeat);
				        map.put(key, list);
				    }
				}
			}
			
			layout_remark.removeAllViewsInLayout();
			
			for (Map.Entry<Integer, List<Seat_list>> entry : map.entrySet())
			{
			    ListView lst_remark = new ListView(this);
			    View viewRemarkType = View.inflate(this, R.layout.remark_header, null);
			    TextView txtRemartType = (TextView) viewRemarkType.findViewById(R.id.txt_remark_type);
			    txtRemartType.setText(getRemarkType(entry.getKey()));
			    lst_remark.addHeaderView(viewRemarkType);
				lst_remark.setAdapter(new RemarkListAdapter(this, entry.getValue()));
				Log.i("","Hello = "+ entry.getValue());
				layout_remark.addView(lst_remark);
				setListViewHeightBasedOnChildren(lst_remark);
			}*/
			
			//Set Seat List in Grid
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());
			
			Log.i("", "Seat List (mseat): "+BusSeats.get(0).getSeat_plan().get(0).getSeat_list());
			
			seatAdapter = new BusSeatAdapter(this, BusSeats.get(0).getSeat_plan().get(0).getSeat_list(), AppLoginUser.getRole());
			//seatAdapter.setCallbacks(callbacks);
			mSeat.setAdapter(seatAdapter);	
			
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));
			
			lvClass = (ListView)findViewById(R.id.lvBusClass);
			lvClass.setAdapter(new BusClassAdapter(this, BusSeats.get(0).getSeat_plan()));
			
			dialog.dismissWithSuccess();
			
		}else{
			dialog.dismissWithFailure("No bus yet");
		}
	}
	
	private BundleListObjSeats seatsListObj;
	private String selectedSeatNos = "";
	private Integer seat_count = 0;
	private String FromCity = "";
	private String ToCity = "";
	
	/**
	 * {@code btn_check_out} clicked: if user not login yet, go activity {@link UserLogin}. 
	 * If user already log in, go activity {@link BusConfirmActivity}
	 */
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
			
			if(v == actionBarNoti){
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", getToday());
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
			}
			
			if(v == btn_booking){
					if (connectionDetector.isConnectingToInternet()) {
						SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.clear();
						editor.commit();
						editor.putString("order_date", Date);
						editor.putString("from", FromCity);
						editor.putString("to", ToCity);
						editor.putString("time", Time);				
						editor.commit();
						
						Bundle bundle = new Bundle();
						bundle.putString("operator_id", OperatorID);	
						bundle.putString("trip_id", tripId);
						bundle.putString("from_intent", "BusSelectSeat");
			        	startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));

					}else{
						connectionDetector.showErrorMessage();
					}
			}
			
			if(v == btn_now_booking){
				if(SelectedSeat.length() != 0){
					if(connectionDetector.isConnectingToInternet()){
						//setupBooking();
					}else{
						connectionDetector.showErrorMessage();
					}
				}else{
					SKToastMessage.showMessage(BusSelectSeatActivity.this, "ခံု ေရြးပါ", SKToastMessage.ERROR);
				}
			}
			
			if(v == btn_check_out){
				if(SelectedSeat.length() != 0){									
					if(connectionDetector.isConnectingToInternet()){
						//Check Log in already or not?
						Log.i("", "User's Id(check out): "+AppLoginUser.getId());
						
						 seatsListObj = new BundleListObjSeats();
						 
						 
						 List<SelectSeat> seats = new ArrayList<SelectSeat>();
						 
						 String[] selectedSeat = SelectedSeat.split(",");
					        
							for (int i = 0; i < selectedSeat.length; i++) {
								//busoccurance id + seat no
								seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0)
										.getId(), BusSeats.get(0).getSeat_plan().get(0)
										.getSeat_list().get(Integer.valueOf(selectedSeat[i]))
										.getSeat_no().toString()));
							}
							
							FromCity = BusSeats.get(0).getSeat_plan().get(0).getFrom().toString();
							ToCity = BusSeats.get(0).getSeat_plan().get(0).getTo().toString();
							
					        Log.i("","Hello From City: "+FromCity+" , To City : "+ToCity+" and Select Seat -> "+seats.toString());
					        
					        seatsListObj.setSeatsList(seats);
					        
					        for (int j = 0; j < seatsListObj.getSeatsList().size(); j++) {
					        	if (j == seatsListObj.getSeatsList().size() - 1) {
					        		selectedSeatNos += seatsListObj.getSeatsList().get(j).getSeat_no();
								}else {
									selectedSeatNos += seatsListObj.getSeatsList().get(j).getSeat_no()+",";
								}
							}
					        
					        if (from_intent.equals("SaleTicket")) {
					        	//One Way (or) After Go Seat Choose, ...
					        	//If already log in , ..
								if (AppLoginUser.getId() != null && !AppLoginUser.getId().equals("0")) {
									if (trip_type == 1) {
										//if one way
										Intent nextScreen = new Intent(BusSelectSeatActivity.this, BusConfirmActivity.class);
				        				
					    				Bundle bundle = new Bundle();
					    				bundle.putString("from_intent", from_intent);
					    				bundle.putString("FromCity", FromCity);
					    				bundle.putString("ToCity", ToCity);
					    				bundle.putString("Operator_Name", BusSeats.get(0).getOperator());			    				
					    				bundle.putString("from_to", From+" => "+To);
					    				bundle.putString("FromName", From);
					    				bundle.putString("ToName", To);
					    				bundle.putString("time", Time);
					    				bundle.putString("classes", BusClasses);
					    				bundle.putString("date", Date);
					    				bundle.putString("bus_occurence", BusSeats.get(0).getSeat_plan().get(0).getId().toString());
					    				bundle.putString("Price", BusSeats.get(0).getSeat_plan().get(0).getPrice()+"");
				        				bundle.putString("ConfirmDate", todayDate);
				        				bundle.putString("ConfirmTime", todayTime);
				        				bundle.putString("CustomerName", AppLoginUser.getUserName());
				        				
				        				//Get Seat Count
				        				if (selectedSeatNos != null && !selectedSeatNos.equals("")) {
				        					String[] seat_string = selectedSeatNos.split(",");
				        					seat_count = seat_string.length;
				        				}
				        				
				        				bundle.putString("SeatCount", seat_count.toString());
				        				
				        				bundle.putString("seat_List", new Gson().toJson(seatsListObj));
				        				bundle.putString("Selected_seats", selectedSeatNos);
					    				bundle.putString("permit_ip", permit_ip);
					    				bundle.putString("permit_access_token", permit_access_token);
					    				bundle.putString("permit_operator_group_id", permit_operator_group_id);
										bundle.putString("permit_agent_id", permit_agent_id);
										bundle.putString("permit_operator_id", permit_operator_id);
										
										bundle.putInt("trip_type", trip_type);
										bundle.putString("return_date", return_date);
					    				
					    				nextScreen.putExtras(bundle);
					    				startActivity(nextScreen);
									}else if (trip_type == 2) {
										//if round trip
										postSale(Date);
									}
									
				    				dialog.dismissWithSuccess();
								}else {  
									//If Log in not yet ?  
									Bundle bundle = new Bundle();
				    				bundle.putString("from_intent", from_intent);
				    				bundle.putString("FromCity", FromCity);
				    				bundle.putString("ToCity", ToCity);
				    				bundle.putString("Operator_Name", BusSeats.get(0).getOperator());			    				
				    				bundle.putString("from_to", From+" => "+To);
				    				bundle.putString("FromName", From);
				    				bundle.putString("ToName", To);
				    				bundle.putString("time", Time);
				    				bundle.putString("classes", BusClasses);
				    				bundle.putString("date", Date);
				    				bundle.putString("bus_occurence", BusSeats.get(0).getSeat_plan().get(0).getId().toString());
				    				bundle.putString("Price", BusSeats.get(0).getSeat_plan().get(0).getPrice()+"");
			        				bundle.putString("ConfirmDate", todayDate);
			        				bundle.putString("ConfirmTime", todayTime);
			        				bundle.putString("CustomerName", AppLoginUser.getUserName());
			        				
			        				bundle.putString("Selected_seats", selectedSeatNos);
			        				//Get Seat Count
			        				//Get Seat Count
			        				if (selectedSeatNos != null && !selectedSeatNos.equals("")) {
			        					String[] seat_string = selectedSeatNos.split(",");
			        					seat_count = seat_string.length;
			        				}
			        				
			        				bundle.putString("SeatCount", seat_count.toString());
			        				bundle.putString("seat_List", new Gson().toJson(seatsListObj));
				    				bundle.putString("permit_ip", permit_ip);
				    				bundle.putString("permit_access_token", permit_access_token);
				    				bundle.putString("permit_operator_group_id", permit_operator_group_id);
									bundle.putString("permit_agent_id", permit_agent_id);
									bundle.putString("permit_operator_id", permit_operator_id);
									
									bundle.putInt("trip_type", trip_type);
									bundle.putString("return_date", return_date);
									
									dialog.dismissWithSuccess();
									startActivity(new Intent(BusSelectSeatActivity.this, UserLogin.class).putExtras(bundle));
								}
							}else if (from_intent.equals("BusConfirm")) {
								//After Return Seat Choose, ...
								Intent nextScreen = new Intent(BusSelectSeatActivity.this, BusConfirmActivity.class);
		        				
			    				Bundle bundle = new Bundle();
			    				bundle.putString("from_intent", from_intent);
			    				bundle.putString("FromCity", FromCity);
			    				bundle.putString("ToCity", ToCity);
			    				bundle.putString("Operator_Name", BusSeats.get(0).getOperator());			    				
			    				bundle.putString("from_to", From+" => "+To);
			    				bundle.putString("FromName", From);
			    				bundle.putString("ToName", To);
			    				bundle.putString("time", Time);
			    				bundle.putString("classes", BusClasses);
			    				bundle.putString("date", Date);
			    				bundle.putString("bus_occurence", BusSeats.get(0).getSeat_plan().get(0).getId().toString());
			    				bundle.putString("Price", BusSeats.get(0).getSeat_plan().get(0).getPrice()+"");
		        				bundle.putString("ConfirmDate", todayDate);
		        				bundle.putString("ConfirmTime", todayTime);
		        				bundle.putString("CustomerName", AppLoginUser.getUserName());
		        				
		        				//Get Seat Count
		        				String[] seatArray = SelectedSeat.split(",");
		        				bundle.putString("SeatCount", seatArray.length+"");
		        				
		        				bundle.putString("seat_List", new Gson().toJson(seatsListObj));
		        				bundle.putString("Selected_seats", "");
			    				bundle.putString("permit_ip", permit_ip);
			    				bundle.putString("permit_access_token", permit_access_token);
			    				bundle.putString("permit_operator_group_id", permit_operator_group_id);
								bundle.putString("permit_agent_id", permit_agent_id);
								bundle.putString("permit_operator_id", permit_operator_id);
								
								bundle.putInt("trip_type", trip_type);
								bundle.putString("return_date", return_date);
								
								bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
								
								Log.i("", "gotrip(busselectseat): "+goTripInfo_obj.toString());
			    				
			    				nextScreen.putExtras(bundle);
			    				startActivity(nextScreen);
			    				
			    				dialog.dismissWithSuccess();
							}
					      
					}else{
						connectionDetector.showErrorMessage();
					}
				}else{
					SKToastMessage.showMessage(BusSelectSeatActivity.this, "ခံု ေရြးပါ", SKToastMessage.ERROR);
				}
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
		dialog = new ZProgressHUD(BusSelectSeatActivity.this);
		dialog.show();

		Log.i("", "param to encrypt: "
				+"permit_access_token: "+permit_access_token
				+", permit_operator_id: "+permit_operator_id
				+", permit_agent_id: "+permit_agent_id
				+", CustName: "+CustName
				+", CustPhone: "+CustPhone
				+", permit_operator_group_id: "+permit_operator_group_id
				+", seat list: "+seatsListObj.getSeatsList().toString()
				+", occuranceid: "+BusSeats.get(0).getSeat_plan().get(0).getId().toString()
				+", trip date: "+date
				+", FromCity: "+FromCity
				+", ToCity: "+ToCity
				+", user id: "+String.valueOf(AppLoginUser
						.getId())
				+", device id: "+DeviceUtil.getInstance(this).getID());
		
		//Do Encrypt of Params
		String param = MCrypt.getInstance().encrypt(SecureParam.postSaleParam(permit_access_token
					, permit_operator_id, permit_agent_id, CustName, CustPhone, "0"
					, "", permit_operator_group_id, MCrypt.getInstance()
					.encrypt(seatsListObj.getSeatsList().toString()), BusSeats.get(0).getSeat_plan().get(0).getId().toString()
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
									.equals(DeviceUtil.getInstance(BusSelectSeatActivity.this).getID())){
			        			
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
									if (trip_type == 2){	
										//If Round Trip
										//For Return Trip, Choose (Operator, Time, Class) again for return trip
											Bundle bundle = new Bundle();
											bundle.putString("from_intent", "BusConfirm");
											bundle.putInt("trip_type", trip_type);
											bundle.putString("return_date", return_date);
											bundle.putString("FromName", From);
											bundle.putString("ToName", To);
											bundle.putString("GoTripInfo", new Gson().toJson(new GoTripInfo(sale_order_no
													, BusSeats.get(0).getSeat_plan().get(0).getPrice()+""
													, String.valueOf(seat_count)
													, AppLoginUser.getAgentGroupId(), permit_operator_id, selectedSeatNos, TicketLists
													, BusSeats.get(0).getSeat_plan().get(0).getId().toString()
													, permit_access_token, permit_agent_id, permit_ip, AppLoginUser.getUserName()
													, AppLoginUser.getPhone()
													, "", FromCity, ToCity, operator_name, From+" => "+To, Time, BusClasses
													, date, todayDate
													, todayTime, "", "", "", return_date, ""
													, TicketLists, permit_operator_id)));
											
											//Not Allow to choose for Go Trip again
											closeAllActivities();
											
											startActivity(new Intent(BusSelectSeatActivity.this, BusOperatorSeatsActivity.class).putExtras(bundle));
									}
			        			}else{ 
			        				isBooking = 0;
			        			}
			        		}else{
			        			isBooking = 0;
			        			dialog.dismissWithFailure();
			        			SKToastMessage.showMessage(BusSelectSeatActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
			        			
			        			if (from_intent.equals("SaleTicket")) {
									//If one way
			        				closeAllActivities();
			        				startActivity(new Intent(BusSelectSeatActivity.this, SaleTicketActivity.class));
								}else {
									finish();
								}
			        		}
						}else{
							Log.i("", "Khone Kar unfinished(status '0') ...........");
							isBooking = 0;
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(BusSelectSeatActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
							
							if (from_intent.equals("SaleTicket")) {
								//If one way
		        				closeAllActivities();
		        				startActivity(new Intent(BusSelectSeatActivity.this, SaleTicketActivity.class));
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
	 * Balance Grid View's Height
	 * @param gridView GridView
	 * @param columns number of column
	 */
	private void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
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
			rows = (int) (x + 0);
			totalHeight *= rows;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);
	}
}

