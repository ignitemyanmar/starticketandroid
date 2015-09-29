package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.BusBookingListActivity;
import com.ignite.mm.ticketing.BusConfirmActivity;
import com.ignite.mm.ticketing.BusSeatViewPagerActivity;
import com.ignite.mm.ticketing.EditBusSelectSeatActivity;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.EditSeatDialog;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;
import com.ignite.mm.ticketing.sqlite.database.model.ReturnComfrim;
import com.ignite.mm.ticketing.sqlite.database.model.Seat;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeat;
import com.ignite.mm.ticketing.sqlite.database.model.Time;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

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

public class BusSeatFragmentPagerAdapter extends FragmentPagerAdapter{

	private static List<Time> allTimeList = new ArrayList<Time>();
	private int mCount = 0;
	
    public BusSeatFragmentPagerAdapter(FragmentManager fm, List<Time> timelist) {
		super(fm);
		// TODO Auto-generated constructor stub
	        if(timelist != null && timelist.size() > 0){
	        	mCount = timelist.size();
	        	allTimeList = timelist;
	        }
	}

    @Override
    public CharSequence getPageTitle(int position) {
        return allTimeList.get(position).getTime()+"("+allTimeList.get(position).getBus_class()+") "
        		+allTimeList.get(position).getTotal_sold_seat()+"/"+allTimeList.get(position).getTotal_seat();
    }

    @Override
    public int getCount() {
        return mCount;
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
    		btn_closeseat = (Button) rootView.findViewById(R.id.btn_close_seat);
    		btn_closeseat.setOnClickListener(clickListener);
    		btn_openseat = (Button) rootView.findViewById(R.id.btn_open_seat);
    		btn_openseat.setOnClickListener(clickListener);
    		
    		//Log.i("","Hello Usre Type: "+ BusSeatViewPagerActivity.app_login_user.getUserType());
    		
    		if(!BusSeatViewPagerActivity.app_login_user.getUserRole().equals("8")){
    			btn_openseat.setVisibility(View.GONE);
    			btn_closeseat.setVisibility(View.GONE);
    		}
    		
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
    		if(compare > 0){
    			btn_check_out.setVisibility(View.GONE);
    			btn_now_booking.setVisibility(View.GONE);
    			btn_booking.setVisibility(View.GONE);
    		}
    		btn_openseat.setVisibility(View.GONE);
			btn_closeseat.setVisibility(View.GONE);
    	}
    	
    	@Override
    	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	    // TODO Add your menu entries here
    		inflater.inflate(R.menu.activity_bus_selectseat, menu);
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
    				editor.putString("time", BusSeatViewPagerActivity.Time);
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
    			/*dialog = new ZProgressHUD(getActivity());
    			dialog.setCancelable(true);
    			dialog.show();*/
    			loading.setVisibility(View.VISIBLE);
    			getSeatPlan();
    		}else{
    			connectionDetector.showErrorMessage();
    		}
    	}
    	
    	private void getSeatPlan() {
    		
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
    	
    	public void postSale() {
    		dialog = new ZProgressHUD(getActivity());
    		dialog.show();
    		
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

    		if (BusSeatViewPagerActivity.app_login_user.getUserType().equals("agent")) {
    			AgentID = BusSeatViewPagerActivity.app_login_user.getUserID();
    		} else if (AgentID.length() == 0) {
    			AgentID = "0";
    		}
    		Log.i("","Hello "+"op:"+ BusSeatViewPagerActivity.OperatorID+",ag"+ AgentID+",cname:"+ CustName+",cphone:"+ CustPhone+",rmkt:"+ RemarkType+",rmk:"+ Remark);
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
    					if (jsonObject.getString("status").equals("1")) {
    						if (jsonObject.getBoolean("can_buy")
    								&& jsonObject.getString("device_id").equals(
    										DeviceUtil.getInstance(
    												getActivity())
    												.getID())) {
    							//Buy
    							if (isBooking == 0) {
    								Intent nextScreen = new Intent(
    										getActivity(),
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
    								//Booking
    								SKToastMessage.showMessage(
    										getActivity(),
    										"Booking Success",
    										SKToastMessage.SUCCESS);
    								isBooking = 0;
    								getSeatPlan();
    							}

    							dialog.dismissWithSuccess();
    						} else {
    							dialog.dismissWithFailure();
    							SKToastMessage
								.showMessage(
										getActivity(),
										getResources().getString(R.string.str_cannot_buy_msg),
										SKToastMessage.ERROR);
    							getSeatPlan();
    						}
    					} else {
    						isBooking = 0;
    						dialog.dismissWithFailure();
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
    	
    	private void getData() {
    		
    		
    		if(BusSeats.size() > 0){
    			txt_dept_date.setText("ထြက္ခြာမည့္ ရက္ : "+ changeDate(BusSeatViewPagerActivity.Date));
    			txt_dept_time.setText("ထြက္ခြာမည့္ အခ်ိန္ : "+ allTimeList.get(mPosition).getTime());
    			txt_operator.setText("ကားဂိတ္ : "+ BusSeats.get(0).getOperator());
    			txt_classes.setText("ကားအမ်ိဳးအစား : "+ BusSeats.get(0).getSeat_plan().get(0).getClasses());
    			txt_price.setText("ေစ်းႏႈန္း  : "+ BusSeats.get(0).getSeat_plan().get(0).getPrice()+" Ks");
    			BusClasses = BusSeats.get(0).getSeat_plan().get(0).getClasses();
    			
    			Map<Integer, List<Seat_list>> map = new HashMap<Integer, List<Seat_list>>();
    			for (Seat_list remarkSeat : BusSeats.get(0).getSeat_plan().get(0).getSeat_list()) {
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
    			seatAdapter = new BusSeatAdapter(getActivity(), BusSeats.get(0).getSeat_plan().get(0).getSeat_list());
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
    			editSeatDialog = new EditSeatDialog(getActivity());
    			editSeatDialog.setName(list.getCustomerInfo().getName());
    			editSeatDialog.setPhone(list.getCustomerInfo().getPhone());
    			editSeatDialog.setNRC(list.getCustomerInfo().getNrcNo());
    			editSeatDialog.setTicketNo(list.getCustomerInfo().getTicketNo());
    			editSeatDialog.setCallbackListener(new EditSeatDialog.Callback() {
    				
    				private ProgressDialog dialog1;

    				public void onEdit() {
    					dialog = new ZProgressHUD(getActivity());
    					// TODO Auto-generated method stub
    			        String param = MCrypt.getInstance().encrypt(SecureParam.editSeatInfoParam(BusSeatViewPagerActivity.app_login_user.getAccessToken(), BusSeats.get(0).getSeat_plan().get(0).getId().toString()
    			        		, BusSeatViewPagerActivity.Date, list.getSeat_no(), editSeatDialog.getName(), editSeatDialog.getPhone(), editSeatDialog.getNRC(), editSeatDialog.getTicketNo()));
    					NetworkEngine.getInstance().editSeatInfo(param,
    							new Callback<Response>() {

    								public void failure(RetrofitError arg0) {
    									// TODO Auto-generated method stub
    									dialog.dismissWithFailure();
    								}

    								public void success(Response arg0,
    										Response arg1) {
    									// TODO Auto-generated method stub
    									onResume();
    									dialog.dismissWithSuccess();
    									editSeatDialog.dismiss();
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
    									dialog.dismiss();
    									dialog1 = ProgressDialog.show(getActivity(), "", " Please wait...", true);
    							        dialog1.setCancelable(true);
    							        String seat = MCrypt.getInstance().encrypt(list.getSeat_no());
    							        String param = MCrypt.getInstance().encrypt(SecureParam.deleteTicketParam(BusSeatViewPagerActivity.app_login_user.getAccessToken(), BusSeats.get(0).getSeat_plan().get(0).getId().toString()
    							        		, BusSeatViewPagerActivity.Date, seat, BusSeatViewPagerActivity.app_login_user.getLoginUserID()));
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
    																	getActivity(),
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
    						bookingDialog = new BookingDialog(getActivity(), BusSeatViewPagerActivity.agentList);
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
    						postSale();
    					}else{
    						connectionDetector.showErrorMessage();
    					}
    				}else{
    					SKToastMessage.showMessage(getActivity(), "Please choose the seat.", SKToastMessage.ERROR);
    				}
    			}	
    		}
    	};

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
    	                dialog.dismiss();
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
