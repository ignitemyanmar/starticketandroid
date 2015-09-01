package com.ignite.mm.ticketing.starticket;

import info.hoang8f.widget.FButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.EditSeatDialog;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusClassAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusSeatAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.GroupUserListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.RemarkListAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.BundleListObjSeats;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
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

@SuppressLint("SimpleDateFormat") public class BusSelectSeatActivity extends BaseActivity{
	
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
	private String FromCity;
	private String ToCity;
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
		
		//Get Data from past clicks
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
				
		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}
		
		SelectedSeat 	= "";
		btn_booking		= (Button) findViewById(R.id.btn_booking);
		btn_now_booking = (Button) findViewById(R.id.btn_now_booking);
		
		Log.i("", "");
	}
	
	@Override
	public Intent getParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getParentActivityIntent();
	}
	
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
	
	private void setupBooking(){
		bookingDialog = new BookingDialog(BusSelectSeatActivity.this, null);
		bookingDialog.setCallbackListener(new BookingDialog.Callback() {

			public void onCancel() {
				// TODO Auto-generated method stub
				
			}

			public void onSave(String agentId, String custName,
					String custPhone, int remarkType, String remark) {
				// TODO Auto-generated method stub
				isBooking = 1;
				AgentID = "0";
				CustName = custName;
				CustPhone = custPhone;
				RemarkType = remarkType;
				Remark = remark;
			//	postSale();
			}
		});	
		bookingDialog.show();
	}
	
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
	 * Get Seat Plan's Price Information
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
			
			Map<Integer, List<Seat_list>> map = new HashMap<Integer, List<Seat_list>>();
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
			}
			
			//Set Seat List in Grid
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());
			
			Log.i("", "Seat List (mseat): "+BusSeats.get(0).getSeat_plan().get(0).getSeat_list());
			
			seatAdapter = new BusSeatAdapter(this, BusSeats.get(0).getSeat_plan().get(0).getSeat_list(), AppLoginUser.getRole());
			seatAdapter.setCallbacks(callbacks);
			mSeat.setAdapter(seatAdapter);	
			
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));
			
			lvClass = (ListView)findViewById(R.id.lvBusClass);
			lvClass.setAdapter(new BusClassAdapter(this, BusSeats.get(0).getSeat_plan()));
			lvClass.setOnItemClickListener(itemClickListener);
			
			dialog.dismissWithSuccess();
			
		}else{
			
			dialog.dismissWithFailure("No bus yet");
		}
	}
	
	private String getRemarkType(int remarkType){
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွတ္ ခ်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္းၾကိဳ");
		remarkTypes.add("ေတာင္းရန္");
		remarkTypes.add("ခံု ေရႊ႕ ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖတ္");
		remarkTypes.add("ေတာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");
		return remarkTypes.get(remarkType).toString();
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			txt_operator.setText("ကားဂိတ္ :   "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္ အမ်ိဳးအစား :   "+ BusSeats.get(0).getSeat_plan().get(position).getClasses());
			txt_price.setText("ေစ်း ႏႈန္း :  "+ BusSeats.get(0).getSeat_plan().get(position).getPrice()+" Ks");
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(position).getColumn());
			//mSeat.setAdapter(new BusSeatAdapter(BusSelectSeatActivity.this, BusSeats.get(0).getSeat_plan().get(position).getSeat_list()));	
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(position).getColumn()));
		}
	};
	
	protected EditSeatDialog editSeatDialog;
	private BusSeatAdapter.Callbacks callbacks = new BusSeatAdapter.Callbacks() {
		
		public void onClickEdit(final Seat_list list) {
			// TODO Auto-generated method stub
			editSeatDialog = new EditSeatDialog(BusSelectSeatActivity.this);
			editSeatDialog.setName(list.getCustomerInfo().getName());
			editSeatDialog.setPhone(list.getCustomerInfo().getPhone());
			editSeatDialog.setNRC(list.getCustomerInfo().getNrcNo());
			editSeatDialog.setTicketNo(list.getCustomerInfo().getTicketNo());
			editSeatDialog.setCallbackListener(new EditSeatDialog.Callback() {
				
				private ProgressDialog dialog1;

				public void onEdit() {
					dialog = new ZProgressHUD(BusSelectSeatActivity.this);
					dialog.show();
					// TODO Auto-generated method stub
			        String param = MCrypt.getInstance().encrypt(SecureParam.editSeatInfoParam(
			        		permit_access_token, 
			        		BusSeats.get(0).getSeat_plan().get(0).getId().toString(), 
			        		Date, 
			        		list.getSeat_no(), 
			        		editSeatDialog.getName(), 
			        		editSeatDialog.getPhone(), 
			        		editSeatDialog.getNRC(), 
			        		editSeatDialog.getTicketNo()));
			        NetworkEngine.setIP(permit_ip);
					NetworkEngine.getInstance().editSeatInfo(param,
							new Callback<Response>() {

								public void failure(RetrofitError arg0) {
									// TODO Auto-generated method stub
								}

								public void success(Response arg0,
										Response arg1) {
									// TODO Auto-generated method stub
									NetworkEngine.setIP("starticketmyanmar.com");
									NetworkEngine.getInstance().editSeatInfo(
											AppLoginUser.getAccessToken(),
											BusSeats.get(0).getSeat_plan().get(0).getId().toString(), 
											Date,
											editSeatDialog.getName(), 
											editSeatDialog.getPhone(), 
											editSeatDialog.getNRC(), 
											editSeatDialog.getTicketNo(), 
											list.getSeat_no(), new Callback<Response>() {

												public void failure(
														RetrofitError arg0) {
													// TODO Auto-generated method stub
													
												}

												public void success(
														Response arg0,
														Response arg1) {
													// TODO Auto-generated method stub
													onResume();
													dialog.dismissWithSuccess();
													editSeatDialog.dismiss();
													SKToastMessage.showMessage(BusSelectSeatActivity.this, "Successfully Updated.", SKToastMessage.SUCCESS);
												}
											});
									
								}
							});
				}
				
				public void onCancel() {
					
					// TODO Auto-generated method stub
					alertDialog("Are you sure, you want to delete?", "Yes", "No", new DialogInterface.OnClickListener() {
						
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									dialog1 = ProgressDialog.show(BusSelectSeatActivity.this, "", " Please wait...", true);
							        dialog1.setCancelable(true);
							        String param = MCrypt.getInstance().encrypt(SecureParam.deleteTicketParam(
							        		permit_access_token, 
							        		BusSeats.get(0).getSeat_plan().get(0).getId().toString(), 
							        		Date, 
							        		list.getSeat_no(), 
							        		AppLoginUser.getId()));
							        NetworkEngine.setIP(permit_ip);
									NetworkEngine.getInstance().deleteTicket(param,
											new Callback<Response>() {

												public void success(
														Response arg0,
														Response arg1) {
													// TODO Auto-generated
													// method stub
													NetworkEngine.setIP("starticketmyanmar.com");
													NetworkEngine.getInstance().deleteSeat(
															AppLoginUser.getAccessToken(), 
															BusSeats.get(0).getSeat_plan().get(0).getId().toString(), 
															Date, 
															list.getSeat_no(), 
															new Callback<Response>() {

																public void failure(
																		RetrofitError arg0) {
																	// TODO Auto-generated method stub
																	
																}

																public void success(
																		Response arg0,
																		Response arg1) {
																	// TODO Auto-generated method stub
																	onResume();
																	dialog1.dismiss();
																	SKToastMessage
																			.showMessage(
																					BusSelectSeatActivity.this,
																					"Successfully Deleted.",
																					SKToastMessage.SUCCESS);
																	editSeatDialog.dismiss();
																}
															});
													
													
												}

												public void failure(
														RetrofitError arg0) {
													// TODO Auto-generated
													// method stub
													dialog1.dismiss();
												}
											});
								}
					}, new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					
				}
			});
			editSeatDialog.show();
		}
	};
	
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
						setupBooking();
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
						
						 BundleListObjSeats seatsListObj = new BundleListObjSeats();
						 String FromCity = "";
						 String ToCity = "";
						 
						 List<SelectSeat> seats = new ArrayList<SelectSeat>();
						 
						 String[] selectedSeat = SelectedSeat.split(",");
					        
							for (int i = 0; i < selectedSeat.length; i++) {
								seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0)
										.getId(), BusSeats.get(0).getSeat_plan().get(0)
										.getSeat_list().get(Integer.valueOf(selectedSeat[i]))
										.getSeat_no().toString()));
							}
							
							FromCity = BusSeats.get(0).getSeat_plan().get(0).getFrom().toString();
							ToCity = BusSeats.get(0).getSeat_plan().get(0).getTo().toString();
							
					        Log.i("","Hello From City: "+FromCity+" , To City : "+ToCity+" and Select Seat -> "+seats.toString());
					        
					        seatsListObj.setSeatsList(seats);
					        
					        if (from_intent.equals("SaleTicket")) {
					        	//One Way (or) After Go Seat Choose, ...
					        	//If already log in , ..
								if (AppLoginUser.getId() != null && !AppLoginUser.getId().equals("0")) {
									
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
									
									//bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
				    				
				    				nextScreen.putExtras(bundle);
				    				startActivity(nextScreen);
				    				
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
			        				
			        				bundle.putString("Selected_seats", "");
			        				//Get Seat Count
			        				String[] seatArray = SelectedSeat.split(",");
			        				bundle.putString("SeatCount", seatArray.length+"");
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
								
								Log.i("", "permit ip(bus selected): "+goTripInfo_obj.getPermit_ip());
			    				
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
	
	public void showDialog(String msg){
		LayoutInflater factory = LayoutInflater.from(this);
	    final View msgDialogView = factory.inflate(R.layout.custom_msg_dialog, null);
	    final AlertDialog msgDialog = new AlertDialog.Builder(this).create();
	    msgDialog.setView(msgDialogView);
	    LinearLayout msgContainer = (LinearLayout)msgDialogView.findViewById(R.id.dialog_container);
	    try {
			JSONObject data = new JSONObject(msg);
			JSONArray arr = data.getJSONArray("Seat");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				TextView txtMsg = new TextView(this);
				txtMsg.setText(obj.getString("seatNo")+" is "+obj.getString("seatStatus"));
				msgContainer.addView(txtMsg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    msgDialogView.findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	            //your business logic 
	            msgDialog.dismiss();
	        }
	    });
	    msgDialog.show();
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
			rows = (int) (x + 0);
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
}

