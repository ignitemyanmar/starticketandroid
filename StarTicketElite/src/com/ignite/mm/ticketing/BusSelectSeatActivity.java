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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.actionbarsherlock.app.ActionBar;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.EditSeatDialog;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.BusClassAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusSeatAdapter;
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

public class BusSelectSeatActivity extends BaseSherlockActivity{
	
	public static List<BusSeat> Bus_Seat;
	private ListView lvClass;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private GridView mSeat;
	public static String SelectedSeat;
	protected ArrayList<Seat> Seat;
	protected ProgressDialog dialog;
	private ConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
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
	private ListView lst_group_user;
	private Button btn_booking;
	private String From;
	private String To;
	private Button btn_now_booking;
	private Integer isBooking = 0;
	private Integer NotifyBooking;
	private TextView actionBarNoti;
	private Button btn_check_out;
	private String Classes;
	protected BookingDialog bookingDialog;
	private List<Seat_list> remarkSeats;
	private ListView lst_remark;
	private String BusClasses;
	private LinearLayout layout_remark;
	private BusSeatAdapter seatAdapter;
	protected List<Agent> agentList;
	private String TripId;
	public static List<BusSeat> BusSeats;
	public static List<OperatorGroupUser> groupUser = new ArrayList<OperatorGroupUser>();
	public static String CheckOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_seat_list);

		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		
		actionBarBack.setOnClickListener(clickListener);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mSeat = (GridView) findViewById(R.id.grid_seat);
		lst_group_user = (ListView) findViewById(R.id.lst_group_user);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		connectionDetector = new ConnectionDetector(this);
		
		Bundle bundle = getIntent().getExtras();	
		AgentID = bundle.getString("agent_id");
		OperatorID = bundle.getString("operator_id");
		FromCity = bundle.getString("from_city_id");
		ToCity = bundle.getString("to_city_id");
		From = bundle.getString("from_city");
		To = bundle.getString("to_city");
		Classes = bundle.getString("class_id");
		Time = bundle.getString("time");
		Date = bundle.getString("date");
		TripId = bundle.getString("trip_id");
		
		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.VISIBLE);
			actionBarNoti.setText(NotifyBooking.toString());
		}
		
		actionBarTitle.setText(From+" - "+To+"["+Time+"] - "+changeDate(Date));
		
		SelectedSeat 	= "";
		btn_booking		= (Button) findViewById(R.id.btn_booking);
		btn_booking.setOnClickListener(clickListener);
		btn_now_booking = (Button) findViewById(R.id.btn_now_booking);
		btn_now_booking.setOnClickListener(clickListener);
		btn_check_out = (Button) findViewById(R.id.btn_check_out);
		btn_check_out.setOnClickListener(clickListener);
		mLoadingView = (LinearLayout) findViewById(R.id.ly_loading);
		mNoConnection = (LinearLayout) findViewById(R.id.no_internet);
		txt_operator = (CustomTextView) findViewById(R.id.txt_operator);
		txt_classes = (CustomTextView) findViewById(R.id.txt_classes);
		txt_price = (CustomTextView) findViewById(R.id.txt_price);
		txt_dept_date = (CustomTextView) findViewById(R.id.txt_departure_date);
		txt_dept_date.setText("ထြက္ခြာမည့္ ေန႕ရက္ : "+ Date);
		txt_dept_time = (CustomTextView) findViewById(R.id.txt_departure_time);
		txt_dept_time.setText("ထြက္ခြာမည့္ အခ်ိန္ : "+ Time);
		
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
		}
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(SelectedSeat.length() != 0)
			finish();
		if(connectionDetector.isConnectingToInternet())
		{ 	mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			getOperatorGroupUser();
			getSeatPlan();
			getAgent();
		}else{
			mNoConnection.setVisibility(View.VISIBLE);
			mNoConnection.startAnimation(topInAnimaiton());
		}
	}
	
	private void getOperatorGroupUser(){
		String param = MCrypt.getInstance().encrypt(SecureParam.getOperatorGroupUserParam(AppLoginUser.getAccessToken(), AppLoginUser.getUserID()));
		NetworkEngine.getInstance().getOperatorGroupUser(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				groupUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorGroupUser>>() {}.getType());;
				Log.i("","Hello Group User: "+ groupUser.size());
				lst_group_user.setAdapter(new GroupUserListAdapter(BusSelectSeatActivity.this, groupUser));
				setListViewHeightBasedOnChildren(lst_group_user);
			}
		});
	}
	
	private Animation topInAnimaiton() {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.top_in);
		anim.reset();
		return anim;

	}

	private Animation topOutAnimaiton() {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.top_out);
		anim.reset();
		return anim;

	}
	
	private void getAgent(){
		String param = MCrypt.getInstance().encrypt(SecureParam.getAllAgentParam(AppLoginUser.getAccessToken(), AppLoginUser.getUserID()));
		NetworkEngine.getInstance().getAllAgent(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				AgentList agents = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AgentList>() {}.getType());
				agentList = agents.getAgents();
				bookingDialog = new BookingDialog(BusSelectSeatActivity.this, agentList);
				bookingDialog.setCallbackListener(new BookingDialog.Callback() {

					public void onCancel() {
						// TODO Auto-generated method stub
						
					}

					public void onSave(String agentId, String custName,
							String custPhone, int remarkType, String remark) {
						// TODO Auto-generated method stub
						isBooking = 1;
						AgentID = agentId;
						CustName = custName;
						CustPhone = custPhone;
						RemarkType = remarkType;
						Remark = remark;
						if(!AgentID.equals("0")){
							postSale();
						}	
					}
				});				
			}
		});
	}
	
	private void getSeatPlan() {
		String param = MCrypt.getInstance().encrypt(SecureParam.getSeatPlanParam(AppLoginUser.getAccessToken(), OperatorID, TripId, FromCity, ToCity, Classes, Date, Time));
		NetworkEngine.getInstance().getItems(param, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				// Try to get response body
				SelectedSeat = "";
				BusSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<BusSeat>>() {}.getType());
				getData();
				mLoadingView.setVisibility(View.GONE);
				mLoadingView.startAnimation(topOutAnimaiton());
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				Log.i("","Hello Header Error: "+ arg0.getCause());
				Log.i("","Hello Header Error: "+ arg0.getResponse().getBody());
				Log.i("","Hello Header Error: "+ arg0.getResponse().getHeaders().toString());
			}
		});
	}
	
	public void postSale() {
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
		dialog.setCancelable(true);
		List<SelectSeat> seats = new ArrayList<SelectSeat>();
		String[] selectedSeat = SelectedSeat.split(",");
		for (int i = 0; i < selectedSeat.length; i++) {
			seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0)
					.getId(), BusSeats.get(0).getSeat_plan().get(0)
					.getSeat_list().get(Integer.valueOf(selectedSeat[i]))
					.getSeat_no().toString()));
		}

		String FromCity = BusSeats.get(0).getSeat_plan().get(0).getFrom()
				.toString();
		String ToCity = BusSeats.get(0).getSeat_plan().get(0).getTo().toString();

		if (AppLoginUser.getUserType().equals("agent")) {
			AgentID = AppLoginUser.getUserID();
		} else if (AgentID.length() == 0) {
			AgentID = "0";
		}
		String param = MCrypt.getInstance().encrypt(
				SecureParam.postSaleParam(
						AppLoginUser.getAccessToken(),
						OperatorID, 
						AgentID, 
						CustName, 
						CustPhone, 
						String.valueOf(RemarkType), 
						Remark, 
						AppLoginUser.getUserGroupID(), 
						MCrypt.getInstance().encrypt(seats.toString()),
						BusSeats.get(0).getSeat_plan().get(0).getId().toString(),
						Date, FromCity, ToCity,
						AppLoginUser.getLoginUserID(),
						DeviceUtil.getInstance(this).getID(), 
						isBooking.toString(),
						"false", isBooking.toString().equals("1") ? AppLoginUser.getLoginUserID().toString() : "0"));
		Log.i("","Hello param : "+ param);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("param", param));
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				String jsonData = msg.getData().getString("data");
				Log.i("ans:", "Server Response: " + jsonData);
				try {
					JSONObject jsonObject = new JSONObject(jsonData);
					if (jsonObject.getString("status").equals("1")) {
						if (jsonObject.getBoolean("can_buy")
								&& jsonObject.getString("device_id").equals(
										DeviceUtil.getInstance(
												BusSelectSeatActivity.this)
												.getID())) {
							if (isBooking == 0) {
								Intent nextScreen = new Intent(
										BusSelectSeatActivity.this,
										BusConfirmActivity.class);
								JSONArray jsonArray = jsonObject
										.getJSONArray("tickets");
								String SeatLists = "";
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject obj = jsonArray.getJSONObject(i);
									SeatLists += obj.getString("seat_no") + ",";
								}
								Bundle bundle = new Bundle();
								bundle.putString("from_intent", "checkout");
								bundle.putString("from_to", From + "-" + To);
								bundle.putString("time", Time);
								bundle.putString("classes", BusClasses);
								bundle.putString("date", Date);
								bundle.putString("selected_seat", SeatLists);
								bundle.putString("sale_order_no",
										jsonObject.getString("sale_order_no"));
								bundle.putString("bus_occurence",
										BusSeats.get(0).getSeat_plan().get(0)
												.getId().toString());
								nextScreen.putExtras(bundle);
								startActivity(nextScreen);
							} else {
								SKToastMessage.showMessage(
										BusSelectSeatActivity.this,
										"Booking မွာျပီးျပီး ျဖစ္ပါသည္။",
										SKToastMessage.SUCCESS);
								isBooking = 0;
								getSeatPlan();
							}

							dialog.dismiss();
						} else {
							dialog.dismiss();
							SKToastMessage
									.showMessage(
											BusSelectSeatActivity.this,
											"သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန့္ပိုင္ အတြင္း တစ္ ျခားသူ ယူ သြားေသာေၾကာင့္ သင္မွာေသာလက္မွတ္မ်ား မရႏိုင္ေတာ့ပါ။ ေက်းဇူးျပဳၿပီး တစ္ျခားလက္ မွတ္ မ်ား ျပန္ေရႊးေပးပါ။။",
											SKToastMessage.ERROR);
							getSeatPlan();
						}
					} else {
						isBooking = 0;
						dialog.dismiss();
						SKToastMessage.showMessage(BusSelectSeatActivity.this,
								jsonObject.getString("message"),
								SKToastMessage.ERROR);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		HttpConnection lt = new HttpConnection(handler, "POST",
				"http://"+NetworkEngine.ip+"/sale", params);
		lt.execute();

	}
	
		
	private void getData() {
		if(BusSeats.size() > 0){
			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္အမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(0).getClasses());
			txt_price.setText("ေစ်းႏႈန္း :"+ BusSeats.get(0).getSeat_plan().get(0).getPrice()+" Ks");
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
				//Check for discount;
				if(remarkSeat.getDiscount() > 0){
					Integer key  = 8;
				    if(map.containsKey(key)){
				        map.get(key).add(remarkSeat);
				    }else{
				        List<Seat_list> list = new ArrayList<Seat_list>();
				        list.add(remarkSeat);
				        map.put(key, list);
				    }
				}
				//Check for free ticket;
				if(remarkSeat.getFree_ticket() > 0){
					Integer key  = 9;
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
				lst_remark.setAdapter(new RemarkListAdapter(this, entry.getValue(), entry.getKey()));
				Log.i("","Hello = "+ entry.getValue());
				layout_remark.addView(lst_remark);
				setListViewHeightBasedOnChildren(lst_remark);
			}
			
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());
			seatAdapter = new BusSeatAdapter(this, BusSeats.get(0).getSeat_plan().get(0).getSeat_list());
			seatAdapter.setCallbacks(callbacks);
			mSeat.setAdapter(seatAdapter);	
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));
			
			lvClass = (ListView)findViewById(R.id.lvBusClass);
			lvClass.setAdapter(new BusClassAdapter(this, BusSeats.get(0).getSeat_plan()));
			lvClass.setOnItemClickListener(itemClickListener);
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
					dialog = ProgressDialog.show(BusSelectSeatActivity.this, "", " Please wait...", true);
			        dialog.setCancelable(true);
					// TODO Auto-generated method stub
			        String param = MCrypt.getInstance().encrypt(SecureParam.editSeatInfoParam(AppLoginUser.getAccessToken(), BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date, list.getSeat_no(), editSeatDialog.getName(), editSeatDialog.getPhone(), editSeatDialog.getNRC(), editSeatDialog.getTicketNo()));
					NetworkEngine.getInstance().editSeatInfo(param,
							new Callback<Response>() {

								public void failure(RetrofitError arg0) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}

								public void success(Response arg0,
										Response arg1) {
									// TODO Auto-generated method stub
									onResume();
									dialog.dismiss();
									editSeatDialog.dismiss();
									SKToastMessage.showMessage(BusSelectSeatActivity.this, "Successfully Updated.", SKToastMessage.SUCCESS);
								}
							});
				}
				
				public void onCancel() {
					
					// TODO Auto-generated method stub
					alertDialog("Are you sure, you want to delete?", new DialogInterface.OnClickListener() {
						
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									dialog1 = ProgressDialog.show(BusSelectSeatActivity.this, "", " Please wait...", true);
							        dialog1.setCancelable(true);
							        String param = MCrypt.getInstance().encrypt(SecureParam.deleteTicketParam(AppLoginUser.getAccessToken(), BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date, list.getSeat_no(), AppLoginUser.getLoginUserID()));
									NetworkEngine.getInstance().deleteTicket(param,
											new Callback<Response>() {

												public void success(
														Response arg0,
														Response arg1) {
													// TODO Auto-generated
													// method stub
													onResume();
													dialog1.dismiss();
													SKToastMessage
															.showMessage(
																	BusSelectSeatActivity.this,
																	"Successfully Deleted.",
																	SKToastMessage.SUCCESS);
													editSeatDialog.dismiss();
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
	private String getRemarkType(int remarkType){
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွတ္ခ်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္းၾကိဳ");
		remarkTypes.add("ေတာင္းရန္");
		remarkTypes.add("ခုံေရြ႕ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖတ္");
		remarkTypes.add("ေတာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");
		remarkTypes.add("Discounted");
		remarkTypes.add("Free Ticket");
		return remarkTypes.get(remarkType).toString();
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
			txt_classes.setText("ယာဥ္အမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(position).getClasses());
			txt_price.setText("ေစ်းႏႈန္း :"+ BusSeats.get(0).getSeat_plan().get(position).getPrice()+" Ks");
			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(position).getColumn());
			mSeat.setAdapter(new BusSeatAdapter(BusSelectSeatActivity.this, BusSeats.get(0).getSeat_plan().get(position).getSeat_list()));	
			setGridViewHeightBasedOnChildren(mSeat , Integer.valueOf(BusSeats.get(0).getSeat_plan().get(position).getColumn()));
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
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}
			
			if(v == btn_booking){
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", Date);
				editor.putString("from", FromCity);
				editor.putString("to", ToCity);
				editor.putString("time", Time);
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}
			
			if(v == btn_now_booking){
				if(SelectedSeat.length() != 0){
					if(connectionDetector.isConnectingToInternet()){
						bookingDialog.show();
					}else{
						connectionDetector.showErrorDialog();
					}
				}else{
					SKToastMessage.showMessage(BusSelectSeatActivity.this, "Please choose the seat.", SKToastMessage.ERROR);
				}
				
			}
			
			if(v == btn_check_out){
				if(SelectedSeat.length() != 0){
					if(connectionDetector.isConnectingToInternet()){
						postSale();
					}else{
						connectionDetector.showErrorDialog();
					}
				}else{
					SKToastMessage.showMessage(BusSelectSeatActivity.this, "Please choose the seat.", SKToastMessage.ERROR);
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

