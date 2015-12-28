package com.ignite.mm.ticketing.starticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import info.hoang8f.widget.FButton;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.TotalCaptureResult;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #PaymentTypeActivity} is the class 
 * to choose payment type(Pay with MPU, Pay with Visa/Master, 
 * Cash on Shop(City Express,ABC,G&G,Sein Gay Har - parami), Cash on Delivery
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * (2) {@link #onBackPressed()}
 * (3) {@link #deleteSeats()}
 * (4) {@link #deleteSelectedSeats(String, String, String, String)}
 * (5) {@link #postSale(String)}
 * (6) {@link #postOnlineSale(String, String, String)}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class PaymentTypeActivity extends BaseActivity{

	private RadioButton radio_payWithMPU;
	private RadioButton radio_payWithVisaMaster;
	private RadioButton radio_cashOnShop;
	private RadioButton radio_cashOnDelivery;
	private SKConnectionDetector skDetector;
	private FButton btn_confirm;
	private RadioGroup radioGroup;
	private TextView txt_booking_fee;
	private String fromPayment;
	private ZProgressHUD dialog;
	private Integer isBooking = 0;
	private ZProgressHUD progress;
	private Calendar cal;
	private Date deptDateTime;
	private String from_intent;
	private String deptTime;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	private int trip_type;
	private TextView txt_warning;
	private TextView txt_shop_name;
	private TextView txt_shop_name2;
	private TextView txt_delivery_fee;
	private int foreign_type;
	private String foreign_type_str;
	private EditText edt_delivery_address;
	private String[] selectedSeat;
	private String[] ticketNoArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_payment_type);
		
		//Get Foreign Price
		SharedPreferences pref = getSharedPreferences("foreign_price", Activity.MODE_PRIVATE);
		foreign_type = pref.getInt("foreign_price", 0);
				
		if (foreign_type == 1) {
			foreign_type_str = "foreign";
		}else if (foreign_type == 0) {
			foreign_type_str = "local";
		}
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			from_intent = bundle.getString("from_intent");
			goTripInfo_str = bundle.getString("GoTripInfo");
			goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
			trip_type = bundle.getInt("trip_type");
		}
		
		Log.i("", "Permit IP at Busconfirmact: "+BusConfirmActivity.permit_ip);
		Log.i("", "Ticket nos(paymenttype) :"+BusConfirmActivity.TicketLists); 
		//Log.i("", "Go Trip Info(payment type): "+goTripInfo_obj.toString());
		
		 Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		 toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		 toolbar.setTitle("Payment Type");
	     this.setSupportActionBar(toolbar);
	     
	     skDetector = new SKConnectionDetector(this);
         
	     txt_booking_fee = (TextView)findViewById(R.id.txt_booking_fee);
	     txt_shop_name = (TextView)findViewById(R.id.txt_shop_name);
	     txt_shop_name2 = (TextView)findViewById(R.id.txt_shop_name2);
	     txt_delivery_fee = (TextView)findViewById(R.id.txt_delivery_fee);
	     txt_warning = (TextView)findViewById(R.id.txt_warning);
	     edt_delivery_address = (EditText)findViewById(R.id.edt_delivery_address);
			
        radio_payWithMPU = (RadioButton)findViewById(R.id.radio_payWithMPU);
 		radio_payWithVisaMaster = (RadioButton)findViewById(R.id.radio_payWithVisaMaster);
 		radio_cashOnShop = (RadioButton)findViewById(R.id.radio_cashOnShop);
 		radio_cashOnDelivery = (RadioButton)findViewById(R.id.radio_cashOnDelivery);
 				
	    radio_payWithVisaMaster.setOnClickListener(new OnClickListener() {
				
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.VISIBLE);
				txt_shop_name.setVisibility(View.GONE);
				txt_shop_name2.setVisibility(View.GONE);
				txt_delivery_fee.setVisibility(View.GONE);
				edt_delivery_address.setVisibility(View.GONE);
			}
		});
	    
	    radio_payWithMPU.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.GONE);
				txt_shop_name.setVisibility(View.GONE);
				txt_shop_name2.setVisibility(View.GONE);
				txt_delivery_fee.setVisibility(View.GONE);
				edt_delivery_address.setVisibility(View.GONE);
			}
		});
	    
	    radio_cashOnDelivery.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_delivery_fee.setVisibility(View.VISIBLE);
				edt_delivery_address.setVisibility(View.VISIBLE);
				edt_delivery_address.requestFocus();
				txt_booking_fee.setVisibility(View.GONE);
				txt_shop_name.setVisibility(View.GONE);
				txt_shop_name2.setVisibility(View.GONE);
			}
		});
	    
	    radio_cashOnShop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.GONE);
				txt_shop_name.setVisibility(View.VISIBLE);
				txt_shop_name2.setVisibility(View.VISIBLE);
				txt_delivery_fee.setVisibility(View.GONE);
				edt_delivery_address.setVisibility(View.GONE);
			}
		});
	     
		//Get only 06:00 AM format
		String timeformat = null;
		try {
			if (BusConfirmActivity.time.length() == 8) {
				timeformat = BusConfirmActivity.time;
			}else if (BusConfirmActivity.time.length() < 8) {
				timeformat = "0"+BusConfirmActivity.time;
			}else if (BusConfirmActivity.time.length() > 8) {
				timeformat = BusConfirmActivity.time.substring(0, 8);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			Log.i("", "Time Out Of Bound Exception: "+e);
		}
		
		SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm aa");
		Date timeTochange = null;
		try {
			if (timeformat != null) {
				timeTochange = serverFormat.parse(timeformat);
				Log.i("", "Server Time Format: "+serverFormat.format(timeTochange));
			}
			
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			Log.i("", "Server Time Exception: "+e2);
			e2.printStackTrace();
		}
		
 		//Show/Hide of Cash on Delivery & Cash on Shop
		if (from_intent.equals("SaleTicket")) {
			deptTime = BusConfirmActivity.date+" "+serverFormat.format(timeTochange);
		}else if (from_intent.equals("BusConfirm")){
			deptTime = BusConfirmActivity.return_date+" "+serverFormat.format(timeTochange);
		}
		
		
		//Today+24hr
		SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		deptDateTime = null;
		try {
			deptDateTime = formatter.parse(deptTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("", "Dept date time: "+deptDateTime+", today+24hr: "+nowFormat.format(cal.getTime()));
 				
 		//Show(or)Hide
		//Disable Click for Cash On Delivery, Cash On Shop (during 1 day before Departure Date)
		//Because we want users make booking + purchasing (1 day in advance) 
		//except Online Payment
		if (cal.getTime().compareTo(deptDateTime) >= 0) {
			radio_cashOnDelivery.setEnabled(false);
			radio_cashOnShop.setEnabled(false);
			
			txt_warning.setVisibility(View.VISIBLE);
		}else {
			radio_cashOnDelivery.setEnabled(true);
			radio_cashOnShop.setEnabled(true);
		}
 		
 		btn_confirm = (FButton)findViewById(R.id.btn_confirm);
 		btn_confirm.setButtonColor(getResources().getColor(R.color.yellow));
		btn_confirm.setShadowEnabled(true);
		btn_confirm.setShadowHeight(3);
		btn_confirm.setCornerRadius(7);
		
 		btn_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (skDetector.isConnectingToInternet()) {
					if (radio_payWithMPU.isChecked()) {	
						fromPayment = "Pay with MPU";
						postSale(fromPayment);
					}else if (radio_payWithVisaMaster.isChecked()) {	
						fromPayment = "Pay with VISA/MASTER";
						postSale(fromPayment);
					}else if (radio_cashOnShop.isChecked()) {	//Booking (Pay @Store)
						isBooking = 1;
						fromPayment = "Cash on Shop";
						postSale(fromPayment);
					}else {				//Booking (Cash On Delivery)
						fromPayment = "Cash on Delivery";
						if (edt_delivery_address.getText().length() == 0) {
							edt_delivery_address.setError("Enter address in Myanmar for delivery");
						}else if (edt_delivery_address.getText().length() < 20) {
							edt_delivery_address.setError("Enter Full Address");
						}else{
							postSale(fromPayment);
						}
					}
				}else {
					SKToastMessage.showMessage(PaymentTypeActivity.this, "Please check your internet connection!", SKToastMessage.ERROR);
				}
			}
		});
	}
	
	/**
	 * If booking is 0 , call {@link #postOnlineSale(String, String, String)}.
	 * <p> 
	 * If booking is 1, go next activity {@link PaymentActivity} (Loyalty Program)
	 * @param fromPayment Payment Type
	 */
	private void postSale(final String fromPayment)
	{
		dialog = new ZProgressHUD(PaymentTypeActivity.this);
		dialog.setMessage("Pls wait...");
		dialog.show();

		//If buy ticket
		if(isBooking == 0){
			
			Log.i("", "Seat List(to payment): "+BusConfirmActivity.selectedSeatNos);
			
			Intent nextScreen = new Intent(PaymentTypeActivity.this, PaymentActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("from_payment", fromPayment);
			bundle.putString("sale_order_no", BusConfirmActivity.sale_order_no);
			bundle.putString("price", BusConfirmActivity.Price);
			bundle.putString("seat_count", BusConfirmActivity.seat_count+"");
			bundle.putString("agentgroup_id", AppLoginUser.getAgentGroupId());
			bundle.putString("operator_id", BusConfirmActivity.permit_operator_id);
			bundle.putString("Selected_seats", BusConfirmActivity.selectedSeatNos);
			bundle.putString("ticket_nos", BusConfirmActivity.TicketLists);
			bundle.putString("busOccurence", BusConfirmActivity.BusOccurence);
			bundle.putString("permit_access_token", BusConfirmActivity.permit_access_token);
			bundle.putString("Permit_agent_id", BusConfirmActivity.Permit_agent_id);
			bundle.putString("permit_ip", BusConfirmActivity.permit_ip);
			bundle.putString("BuyerName", BusConfirmActivity.CustName);
			bundle.putString("BuyerPhone", BusConfirmActivity.CustPhone);
			bundle.putString("BuyerNRC", "");
			bundle.putString("FromCity", BusConfirmActivity.FromCity);
			bundle.putString("ToCity", BusConfirmActivity.ToCity);
			bundle.putString("Operator_Name", BusConfirmActivity.Operator_Name);
			bundle.putString("from_to", BusConfirmActivity.from_to);
			bundle.putString("time", BusConfirmActivity.time);
			bundle.putString("classes", BusConfirmActivity.classes);
			bundle.putString("date", BusConfirmActivity.date);
			bundle.putString("ConfirmDate", BusConfirmActivity.ConfirmDate);
			bundle.putString("ConfirmTime", BusConfirmActivity.ConfirmTime);
			bundle.putString("ExtraCityID", BusConfirmActivity.ExtraCityID);
			bundle.putString("ExtraCityName", BusConfirmActivity.ExtraCityName);
			bundle.putString("ExtraCityPrice", BusConfirmActivity.ExtraCityPrice);
			bundle.putString("ReturnDate", BusConfirmActivity.return_date);
			bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
			bundle.putInt("trip_type", trip_type);
			bundle.putString("delivery_address", edt_delivery_address.getText().toString());
			bundle.putString("from_intent", from_intent);
			
			nextScreen.putExtras(bundle);
			startActivity(nextScreen);
			dialog.dismiss();
		}else{ 
			//If booking .....
			Log.i("", "Seat List(to booking): "+BusConfirmActivity.selectedSeatNos);
			
			isBooking = 0;
			
			//If One Way
			if (trip_type == 0) {
				confirmBooking(fromPayment, BusConfirmActivity.selectedSeatNos, BusConfirmActivity.TicketLists
						, BusConfirmActivity.BusOccurence, BusConfirmActivity.CustName
						, "", BusConfirmActivity.permit_access_token
						, BusConfirmActivity.sale_order_no, BusConfirmActivity.Permit_agent_id
						, BusConfirmActivity.ExtraCityID, BusConfirmActivity.ConfirmDate);
				
				//postOnlineSale(BusConfirmActivity.sale_order_no, fromPayment, BusConfirmActivity.TicketLists);
			}else if (trip_type == 1) {
				//If Round Trip
				//Book for Departure Trip
				Log.i("", "Round (book) operator success");
				confirmBooking(fromPayment, goTripInfo_obj.getSelected_seats(), goTripInfo_obj.getTicket_nos()
						, goTripInfo_obj.getBusOccurence(), BusConfirmActivity.CustName
						, goTripInfo_obj.getBuyerNRC(), goTripInfo_obj.getPermit_access_token()
						, goTripInfo_obj.getSale_order_no(), goTripInfo_obj.getPermit_agent_id()
						, goTripInfo_obj.getExtraCityID(), goTripInfo_obj.getConfirmDate());
				
				//postOnlineSale(goTripInfo_obj.getSale_order_no(), fromPayment, goTripInfo_obj.getTicket_nos());
				
				//Book for Return Trip
				//confirmBooking(BusConfirmActivity.sale_order_no, fromPayment, BusConfirmActivity.TicketLists);
				//postOnlineSale(BusConfirmActivity.sale_order_no, fromPayment, BusConfirmActivity.TicketLists);
			}
		}
	}
	
	/**
	 * Confirm Booking into Operator Database
	 * @param paymentType Payment Type
	 * @param selectedSeats Selected Seats
	 * @param ticketNos Ticket Nos
	 * @param busOccurence BusOccurance
	 * @param buyerName Buyer Name
	 * @param buyerNRC Buyer NRC
	 * @param saleOrderNo Order No
	 * @param permitAgentId Permit Agent ID
	 * @param ExtraCityId Extra City Id
	 * @param confirmDate Confirm Date
	 * @param from_go_trip_success status
	 */
	
	@SuppressWarnings("deprecation")
	private void confirmBooking(String paymentType, String selectedSeats, String ticketList
			, String busOccurence, String buyerName, String buyerNRC, String permitAccessToken
			, String orderNo, String permitAgentId, String ExtraCityId, String confirmDate) {
		
		if (goTripInfo_obj != null) {
			Log.i("", "Old order no: "+goTripInfo_obj.toString());	
		}
		
		Log.i("", "Ticket list: "+ticketList+
				", OrderNo: "+orderNo+
				", paymenttype: "+paymentType+
				", permit operator id: "+BusConfirmActivity.permit_operator_id+
				", user code: "+AppLoginUser.getCodeNo()+
				", user token: "+AppLoginUser.getAccessToken()+
				", extra city: "+BusConfirmActivity.ExtraCityName+
				", user phone: "+AppLoginUser.getPhone()+
				", user name: "+AppLoginUser.getUserName()+
				", foreign: "+foreign_type_str);
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();

		if (selectedSeats != null) {
			selectedSeat = selectedSeats.split(",");
		}
		
		if (ticketList != null && !ticketList.equals("")) {
			ticketNoArray = ticketList.split(",");
		}
		
		Log.i("", "Selected Seats(Payment) : "+selectedSeats);
		
		for (int j = 0; j < selectedSeat.length; j++) {
			seats.add(new ConfirmSeat(busOccurence, selectedSeat[j].toString(),
					buyerName, buyerNRC, ticketNoArray[j].toString(), false,
					"blah", 0));
		}
		
		Log.i("", "Ticket Arrays: "+seats.toString());
		
		//Do Encrypt of Params				
		String param = MCrypt.getInstance()
				.encrypt(
				SecureParam.postSaleConfirmParam(permitAccessToken
				, orderNo, "0"
				, permitAgentId, ""
				, BusConfirmActivity.CustName
				, BusConfirmActivity.CustPhone, ""
				, "0", ""
				, ExtraCityId,  MCrypt.getInstance()
				.encrypt(seats.toString())
				, "1"
				, foreign_type_str
				, confirmDate, DeviceUtil.getInstance(this).getID(), "1"
				, String.valueOf(AppLoginUser.getId()), paymentType
				, String.valueOf(trip_type), edt_delivery_address.getText().toString(), ""));
				
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("param", param));
		
		Log.i("","Hello param (for confirm) : "+ param);
		
		final Handler handler = new Handler() {

			//private String total_amount;

			public void handleMessage(Message msg) {

				String jsonData = msg.getData().getString("data");
				
				if (jsonData != null) {
					try {
						Log.i("","Hello Response Confirm Data:"+ jsonData);
						
						JSONObject jsonObj = new JSONObject(jsonData);
						//JSONObject orderObj = jsonObj.getJSONObject("order");*/
						//total_amount = orderObj.getString("total_amount");
						
						if(!jsonObj.getBoolean("status") && jsonObj.getString("device_id").equals(DeviceUtil.getInstance(PaymentTypeActivity.this).getID())){
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(PaymentTypeActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
						}else{
							if (trip_type == 0) {
								//If One Way
								Bundle bundle = new Bundle();
		        				bundle.putString("payment_type", "Cash on Shop");
		    					
		    					startActivity(new Intent(PaymentTypeActivity.this, ThankYouActivity.class).putExtras(bundle));
		    					
		    					dialog.dismissWithSuccess();
		    					
								/*postOnlineSaleConfirm(paymentType, from_goTrip_success, sale_order_no
													, operator_id, ExtraCityName, agentgroup_id
													, ticketNos, String.valueOf(totalGiftMoney));*/
								
							}else if (trip_type == 1) {
								//If Round Trip
								Log.i("", "Round (book) operator success");
								//Book for Return Trip
								confirmBookingReturn(BusConfirmActivity.sale_order_no, fromPayment, BusConfirmActivity.TicketLists);
								
		        				/*if (!from_goTrip_success.equals("from_go_trip_success")) {
		        					
		        					//Online Confirm for Go Trip
									postOnlineSaleConfirm(paymentType, from_goTrip_success, goTripInfo_obj.getSale_order_no()
											, goTripInfo_obj.getOperator_id(), goTripInfo_obj.getExtraCityName()
											, goTripInfo_obj.getAgentgroup_id()
											, goTripInfo_obj.getTicket_nos()
											, String.valueOf(totalGiftMoney));
									
								}else {
									//Online Confirm for Return Trip
		        					postOnlineSaleConfirm(paymentType, from_goTrip_success, sale_order_no
											, operator_id, ExtraCityName, agentgroup_id
											, ticketNos, "0");
								}*/
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Log.i("", "Response confirm is null!");
					dialog.dismissWithFailure();
				}
			}
		};
		
		if (trip_type == 0) {
			//If one way
			HttpConnection lt = new HttpConnection(handler, "POST",
					"http://"+BusConfirmActivity.permit_ip+"/sale/comfirm", params);
			lt.execute();
			
			Log.i("", "Permit IP: "+BusConfirmActivity.permit_ip);
		}else if (trip_type == 1) {
			//If Round (Departure)
			HttpConnection lt = new HttpConnection(handler, "POST",
					"http://"+goTripInfo_obj.getPermit_ip()+"/sale/comfirm", params);
			lt.execute();
			
			Log.i("", "Permit IP: "+goTripInfo_obj.getPermit_ip());
		}
	}
	
	/**
	 * Confirm Booking Return Trip
	 */
	private void confirmBookingReturn(String orderNo, String paymentType, String ticketList) {
		
		Log.i("", "Ticket list(return): "+ticketList+
				", OrderNo: "+orderNo+
				", paymenttype: "+paymentType+
				", permit operator id: "+BusConfirmActivity.permit_operator_id+
				", user code: "+AppLoginUser.getCodeNo()+
				", user token: "+AppLoginUser.getAccessToken()+
				", extra city: "+BusConfirmActivity.ExtraCityName+
				", user phone: "+AppLoginUser.getPhone()+
				", user name: "+AppLoginUser.getUserName()+
				", foreign: "+foreign_type_str);
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
		
		if (BusConfirmActivity.selectedSeatNos != null) {
			selectedSeat = BusConfirmActivity.selectedSeatNos.split(",");
		}
		
		if (BusConfirmActivity.TicketLists != null && !BusConfirmActivity.TicketLists.equals("")) {
			ticketNoArray = BusConfirmActivity.TicketLists.split(",");
		}
		
		Log.i("", "Selected Seats(Payment) : "+BusConfirmActivity.selectedSeatNos);
		
		for (int j = 0; j < selectedSeat.length; j++) {
			seats.add(new ConfirmSeat(BusConfirmActivity.BusOccurence, selectedSeat[j].toString(),
					BusConfirmActivity.CustName, "", ticketNoArray[j].toString(), false,
					"blah", 0));
		}
		
		//Do Encrypt of Params				
		String param = MCrypt.getInstance()
				.encrypt(
				SecureParam.postSaleConfirmParam(BusConfirmActivity.permit_access_token
				, orderNo, "0"
				, BusConfirmActivity.Permit_agent_id, ""
				, BusConfirmActivity.CustName
				, BusConfirmActivity.CustPhone, ""
				, "0", ""
				, BusConfirmActivity.ExtraCityID,  MCrypt.getInstance()
				.encrypt(seats.toString())
				, "1"
				, foreign_type_str
				, BusConfirmActivity.ConfirmDate, DeviceUtil.getInstance(this).getID(), "1"
				, String.valueOf(AppLoginUser.getId()), paymentType
				, String.valueOf(trip_type), edt_delivery_address.getText().toString(), ""));
				
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("param", param));
		
		Log.i("","Hello param (for confirm) : "+ param);
		
		final Handler handler = new Handler() {

			//private String total_amount;

			public void handleMessage(Message msg) {

				String jsonData = msg.getData().getString("data");
				
				if (jsonData != null) {
					try {
						Log.i("","Hello Response Confirm Data:(return)"+ jsonData);
						
						JSONObject jsonObj = new JSONObject(jsonData);
						//JSONObject orderObj = jsonObj.getJSONObject("order");*/
						//total_amount = orderObj.getString("total_amount");
						
						if(!jsonObj.getBoolean("status") && jsonObj.getString("device_id").equals(DeviceUtil.getInstance(PaymentTypeActivity.this).getID())){
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(PaymentTypeActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
						}else{
							
							Bundle bundle = new Bundle();
	        				bundle.putString("payment_type", "Cash on Shop");
	    					
	    					startActivity(new Intent(PaymentTypeActivity.this, ThankYouActivity.class).putExtras(bundle));
	    					
	    					dialog.dismissWithSuccess();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Log.i("", "Response confirm is null!");
					dialog.dismissWithFailure();
				}
			}
		};
		
		//If Return IP
		HttpConnection lt = new HttpConnection(handler, "POST",
				"http://"+BusConfirmActivity.permit_ip+"/sale/comfirm", params);
		lt.execute();
		
		Log.i("", "Permit IP: "+BusConfirmActivity.permit_ip);
	}

	/**
	 * Store only Booking into Online Database.  
	 * Note: booking live time is only 2 hour. 
	 * @param orderNo Order No
	 * @param paymentType Payment Type
	 * @param ticketList Ticket No(s)
	 */
	private void postOnlineSale(String orderNo, String paymentType, String ticketList) {
		// TODO Auto-generated method stub
		
		Log.i("", "Ticket list: "+ticketList+
				", OrderNo: "+orderNo+
				", paymenttype: "+paymentType+
				", permit operator id: "+BusConfirmActivity.permit_operator_id+
				", user code: "+AppLoginUser.getCodeNo()+
				", user token: "+AppLoginUser.getAccessToken()+
				", extra city: "+BusConfirmActivity.ExtraCityName+
				", user phone: "+AppLoginUser.getPhone()+
				", user name: "+AppLoginUser.getUserName()+
				", foreign: "+foreign_type_str);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postOnlineSaleDB(
				orderNo
				, BusConfirmActivity.permit_operator_id
				, AppLoginUser.getCodeNo()
				, AppLoginUser.getAccessToken(), BusConfirmActivity.ExtraCityName, BusConfirmActivity.CustPhone
				, BusConfirmActivity.CustName, AppLoginUser.getAddress(), ""
				, "0", "0", "", "", "", "1"
				, paymentType, "", "", foreign_type_str, new Callback<Response>() {
			
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Error: "+arg0.getResponse().getStatus());
						}
						dialog.dismissWithFailure();
					}

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							
		    				try {
		    					
		    					Log.i("", "Booking status: "+arg1.getStatus()+", reson: "+arg1.getReason());
		    					
		    					Bundle bundle = new Bundle();
		        				bundle.putString("payment_type", "Cash on Shop");
		    					
		    					startActivity(new Intent(PaymentTypeActivity.this, ThankYouActivity.class).putExtras(bundle));
		    					
		    					dialog.dismissWithSuccess();
		    					
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}

	/**
	 * If back arrow button clicked, call {@link #deleteSeats()}
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		deleteSeats();		
		return super.getSupportParentActivityIntent();
	}
	
	/**
	 * If back arrow button clicked, call {@link #deleteSeats()}
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		deleteSeats();		
	}
	
	/**
	 * Show Dialog to delete selected seats. 
	 * If Yes click, work {@link #deleteSelectedSeats(String, String, String, String)}
	 */
	private void deleteSeats() {
		// TODO Auto-generated method stub
		AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
		
		alertDialog.setMessage("Are you sure You want to cancel Selected Seats?");

		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (SKConnectionDetector.getInstance(
								PaymentTypeActivity.this)
								.isConnectingToInternet()) {
							
							if (trip_type == 0) {
								//If one way 
								deleteSelectedSeats("", BusConfirmActivity.permit_ip, BusConfirmActivity.permit_access_token, BusConfirmActivity.sale_order_no);
							}else if (trip_type == 1) {
								//If Round Trip
								//delete Go selected seats first.... 
								deleteSelectedSeats("", goTripInfo_obj.getPermit_ip(), goTripInfo_obj.getPermit_access_token(), goTripInfo_obj.getSale_order_no());
							}
						}else {
							SKConnectionDetector.getInstance(PaymentTypeActivity.this).showErrorMessage();
						}
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (dialog != null) {
							dialog.cancel();
						}
						//progress.dismiss();
						return;
					}
				});

		alertDialog.show();
	}
	
	/**
	 * Delete Selected Seats from Operator Database
	 * @param from_go_delete_success from_go_delete_success(status string)
	 * @param permit_ip Permit IP
	 * @param permit_access_token Permit Access Token
	 * @param sale_order_no Order No
	 */
	private void deleteSelectedSeats(final String from_go_delete_success, final String permit_ip, final String permit_access_token, final String sale_order_no) {
		// TODO Auto-generated method stub
		
		String param = MCrypt.getInstance().encrypt(SecureParam.deleteSaleOrderParam(permit_access_token));
		
		Log.i("", "Permit IP: "+permit_ip+", Param to delete: "+param+", SaleOrderNo to delete: "+MCrypt.getInstance().encrypt(sale_order_no));
		
		progress = new ZProgressHUD(PaymentTypeActivity.this);
		progress.show();
		
		NetworkEngine.setIP(permit_ip);
		NetworkEngine.getInstance().deleteSaleOrder(
				param, MCrypt.getInstance().encrypt(sale_order_no),
				new Callback<Response>() {

					public void success(
							Response arg0,
							Response arg1) {
						
						if (!from_go_delete_success.equals("from_go_delete_success")) {
							//Delete Return Selected Seats
							
							deleteSelectedSeats("from_go_delete_success", BusConfirmActivity.permit_ip, BusConfirmActivity.permit_access_token
									, BusConfirmActivity.sale_order_no);
						}else {
							closeAllActivities();
	    					startActivity(new Intent(PaymentTypeActivity.this, SaleTicketActivity.class));
	    					progress.dismiss();
						}
					}

					public void failure(
							RetrofitError arg0) {
						// TODO Auto-generated method
						progress.dismiss();
						Log.i("", "Can't delete!");
					}
				});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//For Google Analytics
		EasyTracker.getInstance(this).activityStart(this);
		
		//For Google Analytics
		Tracker v3Tracker = GoogleAnalytics.getInstance(this).getTracker("UA-67985681-1");

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		v3Tracker.set(Fields.SCREEN_NAME, "Payment Type Choose Screen, "
				+fromPayment+", "
				+deptTime+", "
				+"TripType: "
				+trip_type+", "
				+AppLoginUser.getUserName());
		
		// This screenview hit will include the screen name.
		v3Tracker.send(MapBuilder.createAppView().build());
	}
}
