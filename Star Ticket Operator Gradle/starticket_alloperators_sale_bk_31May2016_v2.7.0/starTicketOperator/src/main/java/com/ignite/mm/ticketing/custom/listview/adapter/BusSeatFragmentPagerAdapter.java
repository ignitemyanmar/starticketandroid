package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.starticketsale.BusMenuActivity;
import com.ignite.mm.ticketing.starticketsale.R;
import com.ignite.mm.ticketing.starticketsale.BusBookingListActivity;
import com.ignite.mm.ticketing.starticketsale.BusConfirmActivity;
import com.ignite.mm.ticketing.starticketsale.BusSeatViewPagerActivity;
import com.ignite.mm.ticketing.starticketsale.BusTimeActivity;
import com.ignite.mm.ticketing.starticketsale.EditBusSelectSeatActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.EditSeatDialog;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.AllTimeBundleListObject;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
import com.ignite.mm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mm.ticketing.sqlite.database.model.ReturnComfrim;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeat;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeatBooking;
import com.ignite.mm.ticketing.sqlite.database.model.Time;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak") public class BusSeatFragmentPagerAdapter extends FragmentPagerAdapter{

	private static List<Time> allTimeList = new ArrayList<Time>();
	private int mCount = 0;
	public static String from_sale;
	private static String userId;
	private static String accessToken;
	public static LinearLayout layout_sale_booking;
	public static String userRole;
	
    public BusSeatFragmentPagerAdapter(FragmentManager fm, List<Time> timelist, String userRole, String userId, String accessToken) {
		super(fm);
		// TODO Auto-generated constructor stub
	        if(timelist != null && timelist.size() > 0){
	        	mCount = timelist.size();
	        	allTimeList = timelist;
	        	this.userRole = userRole;
	        	this.userId = userId;
	        	this.accessToken = accessToken;
	        }
	}

    public CharSequence getPageTitle(int position) {
    	
        return allTimeList.get(position).getTime()+"("+allTimeList.get(position).getBus_class()+") "
        		+allTimeList.get(position).getTotal_sold_seat()+"/"+allTimeList.get(position).getTotal_seat();
    }
    

    @Override
    public int getCount() {
        return mCount;
    }
    
    @Override
    public int getItemPosition(Object object) {
    	// TODO Auto-generated method stub
    	return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return BusSelectSeatFragment.newInstance(position);
    }
    
    public static class BusSelectSeatFragment extends Fragment{
    	
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
    	private TextView txt_operator;
    	private TextView txt_classes;
    	private TextView txt_price;
    	private TextView txt_dept_date;
    	private TextView txt_dept_time;
    	private ListView lst_group_user;
    	private Button btn_booking;
    	private Button btn_now_booking;
    	private Integer isBooking = 0;
    	private Integer NotifyBooking;
    	private ButtonRectangle btn_check_out;
    	protected BookingDialog bookingDialog;
    	private String BusClasses;
    	private LinearLayout layout_remark;
    	private BusSeatAdapter seatAdapter;
    	private Button btn_closeseat;
    	private Button btn_openseat;
    	public List<BusSeat> BusSeats;
    	public static String CheckOut;
    	private int mPosition;
    	private View rootView;
		private FrameLayout loading;
		private String from_old_sale;
		private String sale_order_no;
		private List<SelectSeat> seats;
    	
    	public static BusSelectSeatFragment newInstance(int position) {
    		BusSelectSeatFragment frag = new BusSelectSeatFragment();
    		Bundle bundle = new Bundle();
    		bundle.putInt("_position", position);
    		frag.setArguments(bundle);
    		return frag;
    	}
    	
    	@Override
    	public void onCreate(Bundle savedInstanceState) {
    		
    		super.onCreate(savedInstanceState);
    		mPosition = getArguments() != null ? getArguments().getInt("_position") : 1;
    		setHasOptionsMenu(true);
    	}
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		
    		rootView = inflater.inflate(R.layout.bus_seat_list, container, false); 
    		
    		Log.i("", "mPosition onCreatView: "+mPosition);
    		
            ViewCompat.setElevation(rootView,50);
    		return rootView;
    	}
    	
    	@Override
    	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		
    		super.onActivityCreated(savedInstanceState);
    		loading = (FrameLayout) rootView.findViewById(R.id.loading);
    		mSeat = (GridView) rootView.findViewById(R.id.grid_seat);
    		lst_group_user = (ListView) rootView.findViewById(R.id.lst_group_user);
    		layout_remark = (LinearLayout) rootView.findViewById(R.id.layout_remark);
    		
    		connectionDetector = new SKConnectionDetector(getActivity());
    		
    		SelectedSeat 	= "";
    		btn_booking		= (Button) rootView.findViewById(R.id.btn_booking);
    		btn_booking.setOnClickListener(clickListener);
    		btn_now_booking = (Button) rootView.findViewById(R.id.btn_now_booking);
    		btn_now_booking.setOnClickListener(clickListener);
    		btn_check_out = (ButtonRectangle) rootView.findViewById(R.id.btn_check_out);
    		btn_check_out.setOnClickListener(clickListener);
    		txt_operator = (CustomTextView) rootView.findViewById(R.id.txt_operator);
    		txt_classes = (CustomTextView) rootView.findViewById(R.id.txt_classes);
    		txt_price = (CustomTextView) rootView.findViewById(R.id.txt_price);
    		txt_dept_date = (CustomTextView) rootView.findViewById(R.id.txt_departure_date);
    		txt_dept_time = (CustomTextView) rootView.findViewById(R.id.txt_departure_time);
    		layout_sale_booking = (LinearLayout)rootView.findViewById(R.id.layout_sale_booking);
    		
    		//btn_closeseat = (Button) rootView.findViewById(R.id.btn_close_seat);
    		//btn_closeseat.setOnClickListener(clickListener);
    		//btn_openseat = (Button) rootView.findViewById(R.id.btn_open_seat);
    		//btn_openseat.setOnClickListener(clickListener);
    		
    		//Log.i("","Hello Usre Type: "+ BusSeatViewPagerActivity.app_login_user.getUserType());
    		
/*    		if(!BusSeatViewPagerActivity.app_login_user.getUserRole().equals("8")){
    			btn_openseat.setVisibility(View.GONE);
    			btn_closeseat.setVisibility(View.GONE);
    		}*/
    		
    		Date today = null;
        	Date formatedDate = null;
        	
    		try {
    			formatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(BusSeatViewPagerActivity.Date);
    			today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		int compare = today.compareTo(formatedDate);
    		
    		//If not from old_sale button 
    		SharedPreferences old_sale_btn = getActivity().getSharedPreferences("old_sale",
    				Context.MODE_PRIVATE);
    		from_old_sale = old_sale_btn.getString("fromButton", "");
    		from_sale = old_sale_btn.getString("from_intenet", "");
    		
    		//If Lumbini Server (or) if sale check, Not allow to click CheckOut button + Booking Button
    		if (userRole.equals("7")) {
    			btn_check_out.setEnabled(false);
    			btn_now_booking.setEnabled(false);
			}else {
	    		if (!from_old_sale.equals("old_sale")) {
	        		//If less than today
	        		if(compare > 0){
	        			//layout_sale_booking.setVisibility(View.GONE);
	        			btn_check_out.setEnabled(false);
	        			btn_now_booking.setEnabled(false);
	        			
	        		}else {
	        			btn_check_out.setEnabled(true);
	        			btn_now_booking.setEnabled(true);
					}
	        		btn_booking.setVisibility(View.GONE);
	    		}
			}

    		//btn_openseat.setVisibility(View.GONE);
			//btn_closeseat.setVisibility(View.GONE);
    	}
    	
    	@Override
    	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	    // TODO Add your menu entries here
    		inflater.inflate(R.menu.activity_bus_selectseat, menu);
    		
    		//If Lumbini Server (or) Sale Check , Hide booking list button
    		if (userRole.equals("7")) {
    			MenuItem item = menu.findItem(R.id.action_booking);
    			item.setVisible(false);
    			this.hasOptionsMenu();
			}
    	    super.onCreateOptionsMenu(menu, inflater);
    	}
    	    	
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) {
    		
    		switch(item.getItemId()) {
    			case R.id.action_booking:
    				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("order", Context.MODE_PRIVATE);
    				SharedPreferences.Editor editor = sharedPreferences.edit();
    				editor.clear();
    				editor.commit();
    				editor.putString("order_date", BusSeatViewPagerActivity.Date);
    				editor.putString("from", BusSeatViewPagerActivity.FromCity);
    				editor.putString("to", BusSeatViewPagerActivity.ToCity);
    				editor.putString("time", allTimeList.get(mPosition).getTime());
    				editor.commit();
    	        	startActivity(new Intent(getActivity(),	BusBookingListActivity.class));
    	        case R.id.action_refresh:
    	        	onResume();
    	        	return true;
    	        case R.id.action_edit:
    	        	Bundle bundle = new Bundle();
    		        bundle.putString("agent_id", "");
    				bundle.putString("operator_id", BusSeatViewPagerActivity.OperatorID);
    				bundle.putString("from_city_id", BusSeatViewPagerActivity.FromCity);
    				bundle.putString("from_city", BusSeatViewPagerActivity.From);
    				bundle.putString("to_city_id", BusSeatViewPagerActivity.ToCity);
    				bundle.putString("to_city", BusSeatViewPagerActivity.To);
    				bundle.putString("class_id", BusSeatViewPagerActivity.Classes);
    				bundle.putString("time",  allTimeList.get(mPosition).getTime());
    				bundle.putString("date", BusSeatViewPagerActivity.Date);
    				bundle.putString("trip_id", allTimeList.get(mPosition).getTripid());
    	        	startActivity(new Intent(getActivity(), EditBusSelectSeatActivity.class).putExtras(bundle));
    	        	return true;
    	        	    	        	
    		   	}
    		return super.onOptionsItemSelected(item);
    	}
    	
    	
    	public static String changeDate(String date){
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date StartDate = null;
    		try {
    			StartDate = df.parse(date);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return DateFormat.format("dd/MM/yyyy",StartDate).toString();
    	}
    	
    	@Override
		public void onResume() {
    		// TODO Auto-generated method stub
    		super.onResume();
    		if(SelectedSeat.length() != 0){
    			SelectedSeat = "";
    		}
    			
    		if(connectionDetector.isConnectingToInternet())
    		{ 	
    			getTime();
    			getSeatPlan();
    		}else{
    			connectionDetector.showErrorMessage();
    		}
    	}
    	
    	private void getSeatPlan() {
    		
    		loading.setVisibility(View.VISIBLE);
    		
    		Log.i("", "mPosition from getSeatPlan: "+mPosition);
    		
    		String param = MCrypt.getInstance().encrypt(SecureParam.getSeatPlanParam(BusSeatViewPagerActivity.app_login_user.getAccessToken()
    				, BusSeatViewPagerActivity.OperatorID, allTimeList.get(mPosition).getTripid(), BusSeatViewPagerActivity.FromCity
    				, BusSeatViewPagerActivity.ToCity, BusSeatViewPagerActivity.Classes
    				, BusSeatViewPagerActivity.Date, allTimeList.get(mPosition).getTime()));
    		
    		NetworkEngine.getInstance().getItems(param, new Callback<Response>() {
    			
    			public void success(Response arg0, Response arg1) {
    				// TODO Auto-generated method stub
    				// Try to get response body
    				
    				SelectedSeat = "";
    				if (arg0 != null) {
    					BusSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<BusSeat>>() {}.getType());
					}
    				
    				getData();
    				loading.setVisibility(View.GONE);
    				/*if(dialog != null){
    					dialog.dismissWithSuccess();
    				}*/
    			}
    			
    			public void failure(RetrofitError arg0) {
    				// TODO Auto-generated method stub
    				/*if(dialog != null){
    					dialog.dismissWithFailure();
    				}*/
    				SKToastMessage.showMessage(getActivity(),"Error: "+arg0.getCause(), SKToastMessage.ERROR);
    			}
    		});
    	}
    	
    	private AllTimeBundleListObject bundleAllTimes;
		protected ArrayList<Time> time_morning_list;
		protected ArrayList<Time> time_evening_list;
    	
    	private void getTime() {
    		String param = MCrypt.getInstance().encrypt(SecureParam.getTimesParam(accessToken, userId, BusTimeActivity.selectedFromId
    				, BusTimeActivity.selectedToId, BusTimeActivity.selectedDate));
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
    				
    				//Put All Times into bundle object to send to BusSelectSeatActivity
    				bundleAllTimes = new AllTimeBundleListObject();
    				for (int i = 0; i < time_morning_list.size(); i++) {
    					bundleAllTimes.getAllTimes().add(time_morning_list.get(i));
    				}
    				for (int i = 0; i < time_evening_list.size(); i++) {
    					bundleAllTimes.getAllTimes().add(time_evening_list.get(i));
    				}
    				
    				allTimeList.addAll(bundleAllTimes.getAllTimes());
    				//getPageTit
    			}
    			
    			public void failure(RetrofitError arg0) {
    				
    				//do something
    				
    			}
    		});
    	}
    	
    	public void postSale() {
    		
    		if (getActivity() != null) {
    			dialog = new ZProgressHUD(getActivity());
    			dialog.setMessage("pls wait...");
        		dialog.show();
			}
    		
    		seats = new ArrayList<SelectSeat>();
    		
    		String[] selectedSeat = SelectedSeat.split(",");
    		
    		for (int i = 0; i < selectedSeat.length; i++) {
    			
    			Integer selectSeat = 0;
    			
    			if (selectedSeat[i] != null) {
					if (!selectedSeat[i].equals("")) {
						selectSeat = Integer.valueOf(selectedSeat[i]);
					}
				}
    			
    			//Add All trip id, seat no.
    			seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0)
    					.getId(), BusSeats.get(0).getSeat_plan().get(0)
    					.getSeat_list().get(selectSeat)
    					.getSeat_no().toString()));
    			
    		}

    		String FromCity = BusSeats.get(0).getSeat_plan().get(0).getFrom()
    				.toString();
    		String ToCity = BusSeats.get(0).getSeat_plan().get(0).getTo().toString();

    		if (BusSeatViewPagerActivity.app_login_user.getUserType().equals("agent")) {
    			AgentID = BusSeatViewPagerActivity.app_login_user.getUserID();
    		} else if (AgentID.length() == 0) {
    			AgentID = "0";
    		}
    		
    		Log.i("","Hello "+"op:"+ BusSeatViewPagerActivity.OperatorID+",ag"+ BusSeatViewPagerActivity.app_login_user.getUserID()+",cname:"+ CustName+",cphone:"+ CustPhone+",rmkt:"+ RemarkType+",rmk:"+ Remark);
    		
    		String param = MCrypt.getInstance().encrypt(
    				SecureParam.postSaleParam(
    						BusSeatViewPagerActivity.app_login_user.getAccessToken(),
    						BusSeatViewPagerActivity.OperatorID, 
    						AgentID, 
    						CustName, 
    						CustPhone, 
    						String.valueOf(RemarkType), 
    						Remark, 
    						BusSeatViewPagerActivity.app_login_user.getUserGroupID(), 
    						MCrypt.getInstance().encrypt(seats.toString()),
    						BusSeats.get(0).getSeat_plan().get(0).getId().toString(),
    						BusSeatViewPagerActivity.Date, FromCity, ToCity,
    						BusSeatViewPagerActivity.app_login_user.getLoginUserID(),
    						DeviceUtil.getInstance(getActivity()).getID(), 
    						isBooking.toString(),
    						"false", isBooking.toString().equals("1") ? BusSeatViewPagerActivity.app_login_user.getLoginUserID().toString() : "0"));
    		
    		Log.i("","Hello param : "+ param);
    		
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("param", param));
    		final Handler handler = new Handler() {

    			public void handleMessage(Message msg) {

    				String jsonData = msg.getData().getString("data");
    				Log.i("ans:", "Server Response: " + jsonData);
    				try {
    					JSONObject jsonObject = new JSONObject(jsonData);
    					
    					//status 1 is seat exist, 0 is hidden (no seat)
    					//can buy status is available seat
    					if (jsonObject.getString("status").equals("1")) {
    						if (jsonObject.getBoolean("can_buy")
    								&& jsonObject.getString("device_id").equals(
    										DeviceUtil.getInstance(
    												getActivity())
    												.getID())) {
    							//Buy
    							if (isBooking == 0) {
    								Intent nextScreen = new Intent(
    										getActivity(), BusConfirmActivity.class);
    								JSONArray jsonArray = jsonObject
    										.getJSONArray("tickets");
    								String SeatLists = "";
    								for (int i = 0; i < jsonArray.length(); i++) {
    									JSONObject obj = jsonArray.getJSONObject(i);
    									SeatLists += obj.getString("seat_no") + ",";
    								}
    								Bundle bundle = new Bundle();
    								bundle.putString("from_intent", "checkout");
    								bundle.putString("from_to", BusSeatViewPagerActivity.From + "-" + BusSeatViewPagerActivity.To);
    								bundle.putString("time", allTimeList.get(mPosition).getTime());
    								bundle.putString("classes", BusClasses);
    								bundle.putString("date", BusSeatViewPagerActivity.Date);
    								bundle.putString("selected_seat", SeatLists);
    								bundle.putString("sale_order_no",
    										jsonObject.getString("sale_order_no"));
    								bundle.putString("bus_occurence",
    										BusSeats.get(0).getSeat_plan().get(0)
    												.getId().toString());
    								nextScreen.putExtras(bundle);
    								startActivity(nextScreen);
    							} else {
    								//If booking == 1, Make Booking
    								/*SKToastMessage.showMessage(
    										getActivity(),
    										"Booking Success",
    										SKToastMessage.SUCCESS);
    								isBooking = 0;
    								getSeatPlan();*/
    								
    								sale_order_no = jsonObject.getString("sale_order_no");
    								
    								if (dialog != null) {
        								dialog.dismiss();
    								}
    								
    	    						bookingDialog = new BookingDialog(getActivity(), BusSeatViewPagerActivity.agentList, select_seats);
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
    	    									//Give tripId, orderId to update booking
    	    									confirmBooking();
    	    								}else {
												Toast.makeText(getActivity(), "Choose Agent Name", Toast.LENGTH_SHORT).show();
											}
    	    							}
    	    						});
    							}

    							if (dialog != null) {
    								dialog.dismiss();
								}
    							
    						} else {
    							//Can't buy cuz of another person gets your seats.
    							isBooking = 0;
    							
    							if (dialog != null) {
    								dialog.dismissWithFailure();
								}
    							
    							SKToastMessage
								.showMessage(
										getActivity(),
										getResources().getString(R.string.str_cannot_buy_msg),
										SKToastMessage.ERROR);
    							getSeatPlan();
    						}
    					} else {
    						//Can't buy cuz of status is not 1
    						isBooking = 0;
    						
    						if (dialog != null) {
    							dialog.dismissWithFailure();
							}
    						
    						SKToastMessage.showMessage(getActivity(),
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
    	
    	/**
    	 * Give Customer Info + Agent Names into Booking
    	 */
    	private void confirmBooking() {
    		dialog = new ZProgressHUD(getActivity());
    		dialog.setMessage("pls wait...");
    		dialog.show();
    		
    		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
    		String[] selectedSeat = SelectedSeat.split(",");
    		
    		for (int i = 0; i < selectedSeat.length; i++) {
    			
    			Integer selectSeat = 0;
    			
    			if (selectedSeat[i] != null) {
					if (!selectedSeat[i].equals("")) {
						selectSeat = Integer.valueOf(selectedSeat[i]);
					}
				}
    			
    			seats.add(new ConfirmSeat(BusSeats.get(0).getSeat_plan().get(0)
    					.getId(), BusSeats.get(0).getSeat_plan().get(0)
    					.getSeat_list().get(selectSeat)
    					.getSeat_no().toString(),
    					CustName, "", "", false,
    					"", ""));
    		}
    		
    		SharedPreferences pref_old_sale = getActivity()
    				.getSharedPreferences("old_sale", Activity.MODE_PRIVATE);
    		String working_date = pref_old_sale.getString("working_date", null);

    		SharedPreferences pref = getActivity()
    				.getSharedPreferences("User", Activity.MODE_PRIVATE);
    		
    		String accessToken = pref.getString("access_token", null);
    		String user_id = pref.getString("user_id", null);
    		String user_type = pref.getString("user_type", null);
    		
    		/*if (user_type.equals("agent")) {
    			AgentID = user_id;
    		}*/
			
    		String param = MCrypt.getInstance()
    				.encrypt(
    						SecureParam.postSaleConfirmParam(BusSeatViewPagerActivity.app_login_user.getAccessToken()
    								, sale_order_no,
    								"",
    								AgentID, "",
    								CustName, CustPhone, "",
    								String.valueOf(RemarkType), Remark, "", MCrypt.getInstance().encrypt(seats.toString()),
    								"1",
    								"local",
    								working_date,
    								DeviceUtil.getInstance(getActivity()).getID(),
    								isBooking.toString()
    								, BusSeatViewPagerActivity.app_login_user.getLoginUserID()
    								, ""));

    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("param", param));

    		Log.i("", "Hello Params (confirm booking):" + param);
    		
    		final Handler handler = new Handler() {

    			public void handleMessage(Message msg) {

    				String jsonData = msg.getData().getString("data");
    				
    				try {
    					Log.i("", "Hello Response :" + jsonData);
    					JSONObject jsonObj = new JSONObject(jsonData);
    					
    					//If can not book, not same device id
    					if (!jsonObj.getBoolean("status")
    							&& jsonObj.getString("device_id").equals(
    									DeviceUtil.getInstance(getActivity()).getID())) {
    						SKToastMessage
    						.showMessage(getActivity(),
    								getResources().getString(R.string.str_cannot_buy_msg),
    								SKToastMessage.ERROR);
    						
    						if (dialog != null) {
    							dialog.dismissWithFailure();
    						}
    						
    					} else {
    						//If can book
							SKToastMessage.showMessage(getActivity(),
							"Booking Success",
							SKToastMessage.SUCCESS);
							
							isBooking = 0;
							getSeatPlan();
    						
							if (dialog != null) {
    							dialog.dismissWithSuccess();
    						}
    					}
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		};
    		HttpConnection lt = new HttpConnection(handler, "POST",
    				"http://"+NetworkEngine.ip+"/sale/comfirm", params);
    		lt.execute();
    	}
    	
    	private void getData() {
    		
    		
    		if(BusSeats.size() > 0){
    			txt_dept_date.setText("ထြက္ ခြာ မည့္ ေန႔ရက္ : "+ changeDate(BusSeatViewPagerActivity.Date));
    			txt_dept_time.setText("ထြက္ ခြာ မည့္ အခ်ိန္ : "+ allTimeList.get(mPosition).getTime());
    			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
    			txt_classes.setText("ယာဥ္ အမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(0).getClasses());
    			txt_price.setText("ေစ်း ႏႈန္း : "+ BusSeats.get(0).getSeat_plan().get(0).getPrice()+" Ks");
    			BusClasses = BusSeats.get(0).getSeat_plan().get(0).getClasses();
    			
    			Map<Integer, List<Seat_list>> map = new HashMap<Integer, List<Seat_list>>();
    			for (Seat_list remarkSeat : BusSeats.get(0).getSeat_plan().get(0).getSeat_list()) {
    				
    				Log.e("#---------->","Remark Type > "+remarkSeat.getRemark_type());
    				
    				if(remarkSeat.getRemark_type() != null && remarkSeat.getRemark_type() != 0){
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
    				if (getActivity() != null) {
    					ListView lst_remark = new ListView(getActivity());
        			    View viewRemarkType = View.inflate(getActivity(), R.layout.remark_header, null);
        			    TextView txtRemartType = (TextView) viewRemarkType.findViewById(R.id.txt_remark_type);
        			    Log.e("#----------------> ","entry.getKey > "+entry.getKey());
        			    txtRemartType.setText(getRemarkType(entry.getKey()));
        			    lst_remark.addHeaderView(viewRemarkType);
        				lst_remark.setAdapter(new RemarkListAdapter(getActivity(), entry.getValue(), entry.getKey()));
        				Log.i("","Hello = "+ entry.getValue());
        				layout_remark.addView(lst_remark);
        				setListViewHeightBasedOnChildren(lst_remark);
					}else {
						Log.i("","get activity is null!!!!!!!!!!");
					}
    			}
    			
    			mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());
    			seatAdapter = new BusSeatAdapter(getActivity(), BusSeats.get(0).getSeat_plan().get(0).getSeat_list(), userRole);
    			seatAdapter.setCallbacks(callbacks);
    			mSeat.setAdapter(seatAdapter);	
    			setGridViewHeightBasedOnChildren(mSeat, Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));
    			
    		}else{
    			AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
    			alertDialog.setMessage("There is no bus yet.");
    			alertDialog.setCancelable(false);
    			alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    				
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub
    					getActivity().finish();
    				}
    			});
    			alertDialog.show();
    		}
    	}
    	protected EditSeatDialog editSeatDialog;
    	
    	private BusSeatAdapter.Callbacks callbacks = new BusSeatAdapter.Callbacks() {
    		
    		public void onClickEdit(final Seat_list list) {
    			// TODO Auto-generated method stub
    			editSeatDialog = new EditSeatDialog(getActivity(), userRole, BusSeatViewPagerActivity.agentList
    												, BusSeatViewPagerActivity.app_login_user.getLoginUserID(), list);
    			editSeatDialog.setName(list.getCustomerInfo().getName());
    			editSeatDialog.setPhone(list.getCustomerInfo().getPhone());
    			editSeatDialog.setNRC(list.getCustomerInfo().getNrcNo());
    			editSeatDialog.setTicketNo(list.getCustomerInfo().getTicketNo());
    			editSeatDialog.setDiscount(String.valueOf(list.getDiscount()));
    			editSeatDialog.setAgent(String.valueOf(list.getCustomerInfo().getAgentName()));
    			editSeatDialog.setAgentId(String.valueOf(list.getCustomerInfo().getAgentId()));
    			editSeatDialog.setRemarkType(list.getRemark_type());
    			editSeatDialog.setRemark(list.getRemark());
    			editSeatDialog.setSeatIdRemark(list.getCustomerInfo().getId());
    			editSeatDialog.setFreeTicket(list.getFree_ticket());
    			editSeatDialog.setFreeTicketRemark(list.getFree_ticket_remark());
    			editSeatDialog.setNationality(list.getNationality());
    			editSeatDialog.setCallbackListener(new EditSeatDialog.Callback() {
    				
    				private ProgressDialog dialog1;

    				public void onEdit() {
    					dialog = new ZProgressHUD(getActivity());
    					// TODO Auto-generated method stub
    					String remark;
    					String freeTicket;
    					
    					if (editSeatDialog.getRemarkType() == 0) {
							remark = "";
						}else {
							remark = editSeatDialog.getRemark(); 
						}
    					
    					if (editSeatDialog.getFreeTicket() == 0) {
							freeTicket = "";
						}else{
							freeTicket = editSeatDialog.getFreeTicket().toString();
						}
						

    			        String param = MCrypt.getInstance().encrypt(SecureParam.editSeatInfoParam(BusSeatViewPagerActivity.app_login_user.getAccessToken(), BusSeats.get(0).getSeat_plan().get(0).getId().toString()
    			        		, BusSeatViewPagerActivity.Date, list.getSeat_no(), editSeatDialog.getName()
    			        		, editSeatDialog.getPhone(), editSeatDialog.getNRC(), editSeatDialog.getTicketNo()
    			        		, editSeatDialog.getAgentId(), editSeatDialog.getDiscount()
    			        		, editSeatDialog.getRemarkType().toString()
    			        		, remark, freeTicket
    			        		, editSeatDialog.getFreeTicketRemark(), editSeatDialog.getNationality()));
    			        
    			        Log.i("", "Param update seat: "+param);
    			        
    					NetworkEngine.getInstance().editSeatInfo(param,
    							new Callback<Response>() {

    								public void failure(RetrofitError arg0) {
    									// TODO Auto-generated method stub
    									if (dialog != null) {
    										dialog.dismissWithFailure();
										}
    									
    								}

    								public void success(Response arg0,
    										Response arg1) {
    									// TODO Auto-generated method stub
    									onResume();
    									
    									if (dialog != null) {
    										dialog.dismissWithSuccess();
										}
    									
    									if (editSeatDialog != null) {
    										editSeatDialog.dismiss();
										}
    									
    									SKToastMessage.showMessage(getActivity(), "Successfully Updated.", SKToastMessage.SUCCESS);
    								}
    							});
    				}
    				
    				public void onCancel() {
    					
    					// TODO Auto-generated method stub
    					alertDialog("Are you sure, you want to delete?", new DialogInterface.OnClickListener() {
    						
    								public void onClick(DialogInterface dialog,
    										int which) {
    									// TODO Auto-generated method stub
    									
    									if (dialog != null) {
    										dialog.dismiss();
										}
    									
    			    					String remark;
    			    					
    			    					if (editSeatDialog.getRemarkType() == 0) {
    										remark = "";
    									}else {
    										remark = editSeatDialog.getRemark(); 
    									}
    									
    									dialog1 = ProgressDialog.show(getActivity(), "", " Please wait...", true);
    							        dialog1.setCancelable(true);
    							        String seat = MCrypt.getInstance().encrypt(list.getSeat_no());
    							        String param = MCrypt.getInstance().encrypt(SecureParam.deleteTicketParam(BusSeatViewPagerActivity.app_login_user.getAccessToken()
    							        		, BusSeats.get(0).getSeat_plan().get(0).getId().toString()
    							        		, BusSeatViewPagerActivity.Date, seat
    							        		, BusSeatViewPagerActivity.app_login_user.getLoginUserID()
    							        		, editSeatDialog.getRemarkType().toString()
    			    			        		, remark));
    							        
    							        Log.i("", "Param delete: "+param);
    							        
    									NetworkEngine.getInstance().deleteTicket(param,
    											new Callback<Response>() {

    												public void success(
    														Response arg0,
    														Response arg1) {
    													// TODO Auto-generated
    													// method stub
    													onResume();
    													
    													if (dialog1 != null) {
    														dialog1.dismiss();
														}
    													
    													SKToastMessage
    															.showMessage(
    																	getActivity(),
    																	"Successfully Deleted.",
    																	SKToastMessage.SUCCESS);
    													
    													if (editSeatDialog != null) {
    														editSeatDialog.dismiss();
														}
    													
    												}

    												public void failure(
    														RetrofitError arg0) {
    													// TODO Auto-generated
    													// method stub
    													if (dialog1 != null) {
    														dialog1.dismiss();
														}
    													
    												}
    											});
    								}
    					}, new DialogInterface.OnClickListener() {
    						
    						public void onClick(DialogInterface dialog, int which) {
    							// TODO Auto-generated method stub
    							if (dialog != null) {
    								dialog.dismiss();
								}
    							
    						}
    					});
    					
    				}
    			});
    			editSeatDialog.show();
    		}
    	};
    	
		protected List<SelectSeatBooking> select_seats;
    	
    	private String getRemarkType(int remarkType){
    		List<String> remarkTypes = new ArrayList<String>();
    		remarkTypes.add(getResources().getString(R.string.str_choose_remark));
    		remarkTypes.add(getResources().getString(R.string.str_lan_kyo));
    		remarkTypes.add(getResources().getString(R.string.str_taung_yan));
    		remarkTypes.add(getResources().getString(R.string.str_change_seat));
    		remarkTypes.add(getResources().getString(R.string.str_change_date));
    		remarkTypes.add(getResources().getString(R.string.str_see_pyat));
    		remarkTypes.add(getResources().getString(R.string.str_taung_yaung));
    		remarkTypes.add(getResources().getString(R.string.str_sat_go));
    		remarkTypes.add("Discounted");
    		remarkTypes.add("Free Ticket");
      		return remarkTypes.get(remarkType).toString();
    	}
    	
    	private OnClickListener clickListener = new OnClickListener() {

    		public void onClick(View v) {
    			
    			if(v == btn_booking){
    				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("order", Context.MODE_PRIVATE);
    				SharedPreferences.Editor editor = sharedPreferences.edit();
    				editor.clear();
    				editor.commit();
    				editor.putString("order_date", BusSeatViewPagerActivity.Date);
    				editor.putString("from", BusSeatViewPagerActivity.FromCity);
    				editor.putString("to", BusSeatViewPagerActivity.ToCity);
    				editor.putString("time", BusSeatViewPagerActivity.Time);
    				editor.commit();
    	        	startActivity(new Intent(getActivity(),	BusBookingListActivity.class));
    			}
    			
    			if(v == btn_now_booking){
    				if(SelectedSeat.length() != 0){
    					if(connectionDetector.isConnectingToInternet()){
    						
    						select_seats = new ArrayList<SelectSeatBooking>();
    			    		
    			    		String[] selectedSeat = SelectedSeat.split(",");
    			    		
    			    		for (int i = 0; i < selectedSeat.length; i++) {
    			    			
    			    			Integer selectSeat = 0;
    			    			
    			    			if (selectedSeat[i] != null) {
    								if (!selectedSeat[i].equals("")) {
    									selectSeat = Integer.valueOf(selectedSeat[i]);
    								}
    							}
    			    			
    			    			select_seats.add(new SelectSeatBooking(allTimeList.get(mPosition).getTime(), BusSeats.get(0)
    			    					.getSeat_plan()
    			    					.get(0)
    			    					.getSeat_list().get(selectSeat)
    			    					.getSeat_no().toString()));
    			    			
    			    		}
    			    		
    			    		//Seat Reserve to Operator Server after clicking Booking button
    			    		isBooking = 1;
							AgentID = "";
							CustName = "";
							CustPhone = "";
							RemarkType = 0;
							Remark = "";
							postSale();
    					}else{
    						connectionDetector.showErrorMessage();
    					}
    				}else{
    					SKToastMessage.showMessage(getActivity(), "Please choose the seat.", SKToastMessage.ERROR);
    				}
    			}
    			
    			if(v == btn_check_out){
    				if(SelectedSeat.length() > 0){
    					if(connectionDetector.isConnectingToInternet()){
    						
    						Log.i("", "Current Date Time: "+getCurrentDateTime()+"date: "+BusSeatViewPagerActivity.Date+" "+allTimeList.get(mPosition).getTime().substring(0, 8)+",");
    						
    						    String dateInString = allTimeList.get(mPosition).getTime().substring(0, 8);
    						    
    						    Date convertDate = null;
    						    Date convertTime = null;
    						    Date convertDateTime = null;
    						    Date currentDateTime = null;
    						    
    						    try {
    						    	SimpleDateFormat displayDate = new SimpleDateFormat("yyyy-mm-dd");
    						        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a", Locale.US);
    						        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
    						        SimpleDateFormat concatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    					    		
    						        convertDate = displayDate.parse(BusSeatViewPagerActivity.Date);
    						        convertTime = displayFormat.parse(dateInString);   
    						        convertDateTime = concatDate.parse(BusSeatViewPagerActivity.Date+" "+parseFormat.format(convertTime));
    						        currentDateTime = concatDate.parse(getCurrentDateTime());
    						        
    						        Log.i("", "dept: "+concatDate.format(convertDateTime)+", current: "+concatDate.format(currentDateTime));
    						        
    						    } catch (final ParseException e) {
    						        e.printStackTrace();
    						    }
    						    
    						    int compare = currentDateTime.compareTo(convertDateTime);
    						    
    						    if (from_sale.equals("ticket_sale")) {
    						    	//If Current datetime is less than Trip Datetime
    						    	SimpleDateFormat concatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    						    	 if (compare < 0) {
    						    		 
    	    						    	Log.i("", "current: "+concatDate.format(currentDateTime)+", dept: "+concatDate.format(convertDateTime));
    	    						    	postSale();
    									}else {
    										//If Current datetime is same with tripdatetime and grater than tripdatetime
    										Log.i("", "current(OverTime): "+concatDate.format(currentDateTime)+", dept: "+concatDate.format(convertDateTime));
    										SKToastMessage.showMessage(getActivity(), getActivity().getString(R.string.str_dept_time_over), SKToastMessage.ERROR);
    									}
								}else if (from_sale.equals("old_sale")) {
									postSale();
								}else{
									postSale();
									//Do nothing
								}
    					}else{
    						connectionDetector.showErrorMessage();
    					}
    				}else{
    					SKToastMessage.showMessage(getActivity(), "Please choose the seat.", SKToastMessage.ERROR);
    				}
    			}	
    		}
    	};
    	
    	protected String getCurrentDateTime(){
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Calendar c = Calendar.getInstance();
    		String formattedDate = df.format(c.getTime());
    		return formattedDate;
    	}

    	public boolean onCreateOptionsMenu(Menu menu) {
    		// Inflate the menu; this adds items to the action bar if it is present.
    		getActivity().getMenuInflater().inflate(R.menu.activity_bus_selectseat, menu);
    		SharedPreferences notify = getActivity().getSharedPreferences("NotifyBooking",
    				Context.MODE_PRIVATE);
    		NotifyBooking = notify.getInt("count", 0);
    		if (NotifyBooking > 0) {
    			menu.getItem(0).setTitle(NotifyBooking.toString());
    		}

    		return true;
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
    		
    		if (listItem != null) {
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
			}else {
				Log.i("", "return view is null!!!!!");
			}

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
    	
    	protected void alertDialog(String MSG, DialogInterface.OnClickListener YES, DialogInterface.OnClickListener NO){
    		AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(getActivity());
    		alertDialog.setMessage(MSG);
    		
    		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    			
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				
    			}
    		});
    	
    		if(YES != null){
    			alertDialog.setPositiveButton("YES", YES);
    		}else{
    			alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int which) {
    	            	
    	            	if (dialog != null) {
    	            		dialog.dismiss();
						}
    	                
    	            }
    	        });
    		}
    		if(NO != null){
    			alertDialog.setNegativeButton("NO", NO);
    		}
    		alertDialog.show();
    	}
    	
    	@Override
		public void onStart() {
    		// TODO Auto-generated method stub
    		RelativeLayout focuslayout = (RelativeLayout) rootView.findViewById(R.id.layout_seat_plan);
    		focuslayout.requestFocus();
    		super.onStart();
    	}
    	
    	protected String getToday(){
    		Calendar c = Calendar.getInstance();
    		System.out.println("Current time => " + c.getTime());

    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		String formattedDate = df.format(c.getTime());
    		Log.i("","Hello Today: "+formattedDate);
    		return formattedDate;
    	}
    }
}
