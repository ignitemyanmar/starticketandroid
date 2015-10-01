package com.ignite.mm.ticketing.starticket;

import info.hoang8f.widget.FButton;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
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
import com.ignite.mm.ticketing.sqlite.database.model.Currency;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.ignite.mm.ticketing.sqlite.database.model.Loyalty;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #PaymentActivity} is the class for Loyalty Program ( for discount )
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * (2) {@link #getCurrency()}
 * (3) {@link #getLoytalty()}
 * (4) {@link #clickListener}
 * (5) {@link #confirmOrder(String, String, String, String, String, String, String, String, String, String, String, String)}
 * (6) {@link #postOnlineSaleConfirm(String, String, String, String, String, String, String, String)}
 * (7) {@link #onBackPressed()}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class PaymentActivity extends BaseActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private Bundle bundle;
	private Spinner spn_expire_month;
	private Spinner spn_expire_year;
	private EditText edt_cardholder_name;
	private EditText edt_card_no;
	private EditText edt_security_code;
	private TextView txt_use_points;
	private FButton btn_payment;
	private TextView txt_one_point_amount;
	private TextView txt_total_amount;
	private EditText edt_points;
	private EditText edt_gift_money;
	private EditText edt_promo_code;
	private EditText edt_pin_code;
	private ZProgressHUD dialog;
	private String agentgroup_id;
	private String operator_id;
	private TextView txt_total_points;
	private TextView txt_total_gift_money;
	protected Integer totalPoints = 0;
	protected double totalGiftMoney = 0;
	private String selectedSeats;
	private String busOccurence;
	private String permit_access_token;
	private String Permit_agent_id;
	private String permit_ip;
	private String BuyerName;
	private String BuyerPhone;
	private String BuyerNRC;
	private String FromCity;
	private String ToCity;
	private String Operator_Name;
	private String from_to;
	private String time;
	private String classes;
	private String date;
	private String ConfirmDate;
	private String ConfirmTime;
	private String sale_order_no;
	private String ExtraCityName;
	private String ExtraCityID;
	
	private String points_toUse = "0";
	private String giftMoney_toUse = "0";
	private String promoCode;
	private String pinNo;
	protected SKConnectionDetector skDetector;
	private TextView txt_current_points;
	private TextView txt_old_points;
	private TextView txt_current_gift_money;
	private TextView txt_old_gift_money;
	private String from_payment;
	private double total_amount = 0.0;
	private double go_total_amount = 0.0;
	private String price = "0";
	private String seat_count;
	private String ExtraCityPrice = "0";
	private LinearLayout layout_giftMoney;
	private String ticketNos;
	private TextView txt_total_need_to_pay;
	protected double currencyRate;
	private TextView txt_USD;
	private LinearLayout layout_total_need_to_pay;
	private LinearLayout layout_total_with_USD;
	private LinearLayout layout_total_info;
	private TextView txt_from_to;
	private TextView txt_dept_date;
	private TextView txt_dept_time;
	private TextView txt_seats;
	private TextView txt_bus_class;
	private TextView txt_price;
	private TextView txt_passenger_name;
	private TextView txt_passenger_phone;
	private TextView txt_payment_type;
	private String from_intent;
	private String return_date;
	private int trip_type;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	private TextView txt_trip_info;
	private LinearLayout layout_return_title;
	private LinearLayout layout_return_trip_info;
	private TextView txt_trip_info_return;
	private TextView txt_to_from_return;
	private TextView txt_return_date;
	private TextView txt_return_time;
	private TextView txt_return_seatNo;
	private TextView txt_return_bus_class;
	private TextView txt_return_price;
	private String go_seat_count;
	
	final static int REQ_CODE = 1;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Log.i("", "ExtraCityPrice : "+ExtraCityPrice);
		
		skDetector = new SKConnectionDetector(PaymentActivity.this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		
		bundle = getIntent().getExtras();	
		
		if (bundle != null) {
			
			from_payment = bundle.getString("from_payment");
			price = bundle.getString("price");
			seat_count = bundle.getString("seat_count");
			agentgroup_id = bundle.getString("agentgroup_id");
			operator_id = bundle.getString("operator_id");
			
			sale_order_no = bundle.getString("sale_order_no");
			selectedSeats = bundle.getString("Selected_seats");
			ticketNos = bundle.getString("ticket_nos");
			busOccurence = bundle.getString("busOccurence");
			permit_access_token = bundle.getString("permit_access_token");
			Permit_agent_id = bundle.getString("Permit_agent_id");
			permit_ip = bundle.getString("permit_ip");
			BuyerName = bundle.getString("BuyerName");
			BuyerPhone = bundle.getString("BuyerPhone");
			BuyerNRC = bundle.getString("BuyerNRC");
			FromCity = bundle.getString("FromCity");
			ToCity = bundle.getString("ToCity");
			Operator_Name = bundle.getString("Operator_Name");
			from_to = bundle.getString("from_to");
			time = bundle.getString("time");
			classes = bundle.getString("classes");
			date = bundle.getString("date");
			ConfirmDate = bundle.getString("ConfirmDate");
			ConfirmTime = bundle.getString("ConfirmTime");
			
			ExtraCityID = bundle.getString("ExtraCityID");
			ExtraCityName = bundle.getString("ExtraCityName");
			
			if (!bundle.getString("ExtraCityPrice").equals("0") && bundle.getString("ExtraCityPrice") != null) {
				if (!bundle.getString("ExtraCityPrice").equals("")) {
					ExtraCityPrice  = bundle.getString("ExtraCityPrice");
				}
			}
			
			return_date = bundle.getString("ReturnDate");
			from_intent = bundle.getString("from_intent");
			trip_type = bundle.getInt("trip_type");
			goTripInfo_str = bundle.getString("GoTripInfo");
			goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
			
			//Log.i("", "Go Trip Info(payment act:): "+goTripInfo_obj.toString());
			
			/*if (goTripInfo_obj != null) {
				if (goTripInfo_obj.getExtraCityPrice() != null) {
					if (!goTripInfo_obj.getExtraCityPrice().equals("0")) {
						ExtraCityPrice  = goTripInfo_obj.getExtraCityPrice();
					}
				}
			}*/
		}
		
		setContentView(R.layout.activity_payment);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Loyalty Program");
            this.setSupportActionBar(toolbar);
        }
        
		//Trip info
        txt_trip_info = (TextView)findViewById(R.id.txt_trip_info);
		txt_from_to = (TextView)findViewById(R.id.txt_from_to);
		txt_dept_date = (TextView)findViewById(R.id.txt_dept_date);
		txt_dept_time = (TextView)findViewById(R.id.txt_dept_time);
		txt_seats = (TextView)findViewById(R.id.txt_seats);
		txt_bus_class = (TextView)findViewById(R.id.txt_bus_class);
		txt_price = (TextView)findViewById(R.id.txt_price);
		
		//Return Trip
		layout_return_title = (LinearLayout)findViewById(R.id.layout_return_title);
		layout_return_trip_info = (LinearLayout)findViewById(R.id.layout_return_trip_info);
		txt_trip_info_return = (TextView)findViewById(R.id.txt_trip_info_return);
		txt_to_from_return = (TextView)findViewById(R.id.txt_to_from_return);
		txt_return_date = (TextView)findViewById(R.id.txt_return_date);
		txt_return_time = (TextView)findViewById(R.id.txt_return_time);
		txt_return_seatNo = (TextView)findViewById(R.id.txt_return_seatNo);
		txt_return_bus_class = (TextView)findViewById(R.id.txt_return_bus_class);
		txt_return_price = (TextView)findViewById(R.id.txt_return_price);
		
		txt_total_amount = (TextView)findViewById(R.id.txt_total_amount);
		
		txt_passenger_name = (TextView)findViewById(R.id.txt_passenger_name);
		txt_passenger_phone = (TextView)findViewById(R.id.txt_passenger_phone);
		txt_payment_type = (TextView)findViewById(R.id.txt_payment_type);
		
		layout_total_info = (LinearLayout)findViewById(R.id.layout_total_info);
        layout_giftMoney = (LinearLayout)findViewById(R.id.layout_giftMoney);
		txt_one_point_amount = (TextView)findViewById(R.id.txt_one_point_amount);
		edt_points = (EditText)findViewById(R.id.edt_points);
		edt_gift_money = (EditText)findViewById(R.id.edt_gift_money);
		edt_promo_code = (EditText)findViewById(R.id.edt_promo_code);
		edt_pin_code = (EditText)findViewById(R.id.edt_pin_code);
		btn_payment = (FButton)findViewById(R.id.btn_payment);
		btn_payment.setButtonColor(getResources().getColor(R.color.yellow));
		btn_payment.setShadowEnabled(true);
		btn_payment.setShadowHeight(3);
		btn_payment.setCornerRadius(7);
		
		txt_total_points = (TextView)findViewById(R.id.txt_points);
		txt_total_gift_money = (TextView)findViewById(R.id.txt_gift_money);
		
		//Visa+Master+MPU
		layout_total_with_USD = (LinearLayout)findViewById(R.id.layout_total_with_USD);
		layout_total_need_to_pay = (LinearLayout)findViewById(R.id.layout_total_need_to_pay);
		txt_USD = (TextView)findViewById(R.id.txt_USD);
		txt_total_need_to_pay = (TextView)findViewById(R.id.txt_total_need_to_pay);
		
		//Visa + Master
		edt_cardholder_name = (EditText)findViewById(R.id.edt_cardholder_name);	
		edt_card_no = (EditText)findViewById(R.id.edt_card_no);
		edt_security_code = (EditText)findViewById(R.id.edt_security_code);
		txt_use_points = (TextView)findViewById(R.id.txt_use_points);
		
		txt_current_points = (TextView)findViewById(R.id.txt_current_points);
		txt_old_points = (TextView)findViewById(R.id.txt_old_points);
		
		txt_current_gift_money = (TextView)findViewById(R.id.txt_current_gift_money);
		txt_old_gift_money = (TextView)findViewById(R.id.txt_old_gift_money);
		
		int maxLength = 3;    
		edt_security_code.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
		
		spn_expire_month = (Spinner)findViewById(R.id.spn_expire_month);
		spn_expire_year = (Spinner)findViewById(R.id.spn_expire_year);
		
        //Trip Info Title
        if (trip_type == 1) 
        	txt_trip_info.setText("Trip Info (one way)");
        
        //Trip Info (One Way)
		if (trip_type == 1) {
			layout_return_title.setVisibility(View.GONE);
			layout_return_trip_info.setVisibility(View.GONE);
			
			txt_from_to.setText(from_to+" ["+Operator_Name+"]");
			txt_dept_date.setText(changeDate(date));
			txt_dept_time.setText(time);
			txt_seats.setText(selectedSeats);
			txt_bus_class.setText(classes);
			
			if (Integer.valueOf(ExtraCityPrice) > 0) {
				txt_price.setText(ExtraCityPrice+" Ks");
			}else {
				txt_price.setText(price+" Ks");
			}
			
		}else if (trip_type == 2) {
			//Trip Info (Round Trip)
			layout_return_title.setVisibility(View.VISIBLE);
			layout_return_trip_info.setVisibility(View.VISIBLE);
			
			//Show Go Trip Info 
			txt_from_to.setText(goTripInfo_obj.getFrom_to()+" ["+goTripInfo_obj.getOperator_Name()+"]");
			txt_dept_date.setText(changeDate(goTripInfo_obj.getDate()));
			txt_dept_time .setText(goTripInfo_obj.getTime());
			txt_seats.setText(goTripInfo_obj.getSelected_seats());
			txt_bus_class.setText(goTripInfo_obj.getClasses());
			
			//Already assign ExtraCityPrice from goTripInfo_obj on create !!!!!!!!!
			if (Integer.valueOf(ExtraCityPrice) > 0) {
				txt_price.setText(ExtraCityPrice+" Ks");
			}else {
				txt_price.setText(goTripInfo_obj.getPrice()+" Ks");
			}
			
			//Show Return Trip Info
			txt_to_from_return.setText(from_to+" ["+Operator_Name+"]");
			txt_return_date.setText(changeDate(return_date));
			txt_return_time.setText(time);
			txt_return_seatNo.setText(selectedSeats);
			txt_return_bus_class.setText(classes);
			txt_return_price.setText(price+" Ks");
			
		}
		
		//Get Seat Count
		if (goTripInfo_obj != null) {
			if (goTripInfo_obj.getSelected_seats() != null && !goTripInfo_obj.getSelected_seats().equals("")) {
				String[] seat_string = goTripInfo_obj.getSelected_seats().split(",");
				go_seat_count = String.valueOf(seat_string.length);
			}
		}
		
		
		//Customer Information
		txt_passenger_name.setText(BuyerName);
		txt_passenger_phone.setText(BuyerPhone);
		txt_payment_type.setText(from_payment);
		
		ArrayList<String> spinnerMonth = new ArrayList<String>();
		spinnerMonth.add("1");
		spinnerMonth.add("2");
		spinnerMonth.add("3");
		spinnerMonth.add("4");
		spinnerMonth.add("5");
		spinnerMonth.add("6");
		spinnerMonth.add("7");
		spinnerMonth.add("8");
		spinnerMonth.add("9");
		spinnerMonth.add("10");
		spinnerMonth.add("11");
		spinnerMonth.add("12");
		
		@SuppressWarnings("unchecked")
		ArrayAdapter spinnerMonthAdapter = new ArrayAdapter(PaymentActivity.this
				, android.R.layout.simple_spinner_dropdown_item
				, spinnerMonth);
		spn_expire_month.setAdapter(spinnerMonthAdapter);
		
		ArrayList<String> spinnerYear = new ArrayList<String>();
		
		//Current Year
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		spinnerYear.add(currentYear+"");
		spinnerYear.add((currentYear+1)+"");
		spinnerYear.add((currentYear+2)+"");
		spinnerYear.add((currentYear+3)+"");
		spinnerYear.add((currentYear+4)+"");
		spinnerYear.add((currentYear+5)+"");
		spinnerYear.add((currentYear+6)+"");
		spinnerYear.add((currentYear+7)+"");
		spinnerYear.add((currentYear+8)+"");
		spinnerYear.add((currentYear+9)+"");
		spinnerYear.add((currentYear+10)+"");
		
		@SuppressWarnings("unchecked")
		ArrayAdapter spinnerYearAdapter = new ArrayAdapter(PaymentActivity.this
				, android.R.layout.simple_spinner_dropdown_item
				, spinnerYear);
		spn_expire_year.setAdapter(spinnerYearAdapter);
		
		txt_use_points.setOnClickListener(clickListener);
		btn_payment.setOnClickListener(clickListener);
		
		 
		Integer priceInt = 0;
		Integer seat_countInt = 0;
		
		Log.i("", "price: "+price+", seat count: "+seat_count);
		
		//One Way and Return
		if (price != null && seat_count != null) {
			
			priceInt = Integer.valueOf(price);
			seat_countInt = Integer.valueOf(seat_count);
			
			//If Extra City Choose, .. 
			if (!ExtraCityPrice.equals("0") && ExtraCityPrice != null) {
				if (!ExtraCityPrice.equals("")) {
					total_amount = Integer.valueOf(ExtraCityPrice)  * seat_countInt;
				}
			}else {
				total_amount = priceInt * seat_countInt;
			}
			
		}else {
			if (!ExtraCityPrice.equals("0") && ExtraCityPrice != null) {
				if (!ExtraCityPrice.equals("")) {
					total_amount = Integer.valueOf(ExtraCityPrice)  * seat_countInt;
				}
			}else {
				total_amount = priceInt * seat_countInt;
			}
		}
		
		//If Round Trip
		Integer go_priceInt = 0;
		Integer go_seat_countInt = 0;
		
		//If Round Trip, .. 
		if (trip_type == 2) {
			
			Log.i("", "price: "+goTripInfo_obj.getPrice()+", seat count: "+go_seat_count);
			
			if (goTripInfo_obj.getPrice() != null && go_seat_count != null) {
				go_priceInt = Integer.valueOf(goTripInfo_obj.getPrice());
				go_seat_countInt = Integer.valueOf(go_seat_count);
				
				/*if (!goTripInfo_obj.getExtraCityPrice().equals("0") && goTripInfo_obj.getExtraCityPrice() != null) {
					if (!goTripInfo_obj.getExtraCityPrice().equals("")) {
						go_total_amount = Integer.valueOf(goTripInfo_obj.getExtraCityPrice())  * go_seat_countInt;
					}
				}else {*/
					go_total_amount = go_priceInt * go_seat_countInt;
					Log.i("", "go total amount(ok): "+go_total_amount);
				//}
				
			}else {
				/*if (!goTripInfo_obj.getExtraCityPrice().equals("0") && goTripInfo_obj.getExtraCityPrice() != null) {
					if (!goTripInfo_obj.getExtraCityPrice().equals("")) {
						go_total_amount = Integer.valueOf(goTripInfo_obj.getExtraCityPrice())  * go_seat_countInt;
					}
				}else {*/
					go_total_amount = go_priceInt * go_seat_countInt;
				//}
				
			}
			
			Log.i("", "go total amount: "+go_total_amount
					+"return total: "+total_amount);
			
			//return total + go total (Round Trip Total)
			total_amount = total_amount + go_total_amount;
		}
		
		Log.i("", "total amount gyi: "+total_amount);
		
		//Get Usable Points & Gift Money
		getCurrency();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
	     if(requestCode == REQ_CODE){

				if (resultCode == RESULT_OK){

					Log.i("", "Payment Success !!!!!!!!!!!!!!!!!!!!!!!");
					

				}else if(resultCode == RESULT_CANCELED){
					
					Log.i("", "Payment Cancel ................... :(");
					Toast.makeText(PaymentActivity.this, "You cancel Payment!", Toast.LENGTH_LONG).show();
				}
			}
	}
	
	/**
	 * If back arrow button clicked, call {@link #deleteSeats()}
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	/**
	 * Get current (USD) Exchange Rate from Server
	 */
	private void getCurrency() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(PaymentActivity.this);
		dialog.show();
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getCurrencyRate(new Callback<Double>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Currency Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismissWithFailure();
			}

			public void success(Double arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 > 0) {
					currencyRate = arg0;
					
					Log.i("", "Rate: "+currencyRate);
					
					getLoytalty();
					
				}else {
					Log.i("", "Currency Rate is '0' !!!!!!!");
				}
			}
		});
	}
	
	/**
	 * Get Loyalty Program's Gift Money
	 */
	private void getLoytalty() {
		// TODO Auto-generated method stub
		
		Log.i("", "Loyalty User Id: "+AppLoginUser.getId()+", Loyalty Ph: "+AppLoginUser.getPhone()
				+", total amount: "+String.valueOf(total_amount)
				+", Payment method: "+1
				+", Agent group id: "+agentgroup_id
				+", Operator id: "+operator_id
				+", currency rate: "+currencyRate);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getLoyaltyByUser(AppLoginUser.getId(), new Callback<Loyalty>() {
			
			public void success(Loyalty arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				if (arg0 != null) {
					
					Log.i("", "Loyalty: "+arg0.toString());
					
					
					if (arg0.getPoints() >= 0) {
						totalPoints = arg0.getPoints();
					}
					
					txt_total_points.setText(totalPoints+"");
					
					if (arg0.getGiftMoney() > 0) {
						totalGiftMoney = arg0.getGiftMoney();
					}
					
					String str_totalWithUSD = "";
					String str_totalGiftMoneyUSD = "";
					String str_finalTotalUSD = "";
					double totalWithUSD = 0;
					double totalGiftMoneyUSD = 0;
					double finalTotalUSD = 0;
					
					//Calculate USD ($)
					totalWithUSD = (total_amount / currencyRate) + 4;
					totalGiftMoneyUSD = totalGiftMoney / currencyRate;
					finalTotalUSD = totalWithUSD - totalGiftMoneyUSD;
					
					str_totalWithUSD = String.format("%.2f", totalWithUSD);
					str_totalGiftMoneyUSD = String.format("%.2f", totalGiftMoneyUSD);
					str_finalTotalUSD = String.format("%.2f", finalTotalUSD);
					
					Log.i("", "totalwith USD: "+totalWithUSD
							+", totalGift UDD: "+totalGiftMoneyUSD
							+", finalTotalUSD: "+finalTotalUSD
							+", str1: "+str_totalWithUSD
							+", str2: "+str_totalGiftMoneyUSD
							+", str3: "+str_finalTotalUSD);
					
					if (from_payment.equals("Pay with VISA/MASTER")) {
						layout_total_with_USD.setVisibility(View.VISIBLE);
						layout_total_info.setVisibility(View.GONE);
						txt_USD.setText(str_totalWithUSD+"");
					}else {
						layout_total_with_USD.setVisibility(View.GONE);
						layout_total_info.setVisibility(View.VISIBLE);
						txt_total_amount.setText(total_amount+"");
					}
					
					//If Gift Money got !!
					if (arg0.getGiftMoney() > 0) {
						
						layout_giftMoney.setVisibility(View.VISIBLE);
						
						if (from_payment.equals("Pay with VISA/MASTER")) {
							txt_total_gift_money.setText(str_totalGiftMoneyUSD+" USD");
							
							if (Double.valueOf(str_finalTotalUSD) >= 0) {
								txt_total_need_to_pay.setText(str_finalTotalUSD+" USD");
							}else {
								txt_total_need_to_pay.setText(0.0+" USD");
							}
							
						}else {
							txt_total_gift_money.setText(totalGiftMoney+" Ks");
							Double netAmount = total_amount - totalGiftMoney;
							
							if (netAmount >= 0) {
								txt_total_need_to_pay.setText(netAmount+" Ks");
							}else {
								txt_total_need_to_pay.setText(0.0+" Ks");
							}
							
						}
					}else {			//If Gift Money Not get...
						layout_giftMoney.setVisibility(View.GONE);
						
						if (from_payment.equals("Pay with VISA/MASTER")) {
							txt_total_need_to_pay.setText(str_totalWithUSD+" USD");
						}else {
							txt_total_need_to_pay.setText(total_amount+" Ks");
						}
					}
				}
				
				dialog.dismissWithSuccess();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "loyalty error: "+arg0.getResponse().getStatus()+", status: "+arg0.getResponse().getReason());
				}
				Log.i("", "Error");
				
				dialog.dismissWithFailure();
			}
		});
	}

	/**
	 * {@code btn_payment} clicked: If payment type is Visa/Master, 
	 * go next activity {@link Payment2C2PActivity}. 
	 * <p>
	 * If payment type is Cash on Delivery, call {@link #confirmOrder(String, String, String, String, String, String, String, String, String, String, String, String)}
	 */
	private OnClickListener clickListener = new OnClickListener() {

		private Integer points_to_use = 0;
		private Integer giftMoney_to_use = 0;

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == txt_use_points) {
				//startActivity(new Intent(PaymentActivity.this, LoyaltyProgramActivity.class));
			}
			if (v == btn_payment) {
				
				if (checkField()) {
					
					points_toUse = edt_points.getText().toString();
					giftMoney_toUse = edt_gift_money.getText().toString();
					promoCode = edt_promo_code.getText().toString();
					pinNo = edt_pin_code.getText().toString();
					
					if(skDetector.isConnectingToInternet()){
						
						if (from_payment.equals("Pay with MPU") || from_payment.equals("Pay with VISA/MASTER")) {
							
								Bundle bundle = new Bundle();
								bundle.putString("from_payment", from_payment);
								bundle.putString("order_no", sale_order_no);
								bundle.putString("order_amount", String.valueOf(total_amount - totalGiftMoney));
								bundle.putString("points_toUse", points_toUse);
								bundle.putString("giftMoney_toUse", giftMoney_toUse);
								bundle.putString("total_giftMoney", String.valueOf(totalGiftMoney));
								bundle.putString("price", price);
								bundle.putString("seat_count", seat_count);
								bundle.putString("agentgroup_id", agentgroup_id);
								bundle.putString("operator_id", operator_id);
								bundle.putString("selectedSeats", selectedSeats);
								bundle.putString("busOccurence", busOccurence);
								bundle.putString("permit_access_token", permit_access_token);
								bundle.putString("Permit_agent_id", Permit_agent_id);
								bundle.putString("permit_ip", permit_ip);
								bundle.putString("BuyerName", BuyerName);
								
								bundle.putString("BuyerPhone", BuyerPhone);
								bundle.putString("BuyerNRC", BuyerNRC);
								bundle.putString("FromCity", FromCity);
								bundle.putString("ToCity", ToCity);
								bundle.putString("Operator_Name", Operator_Name);
								bundle.putString("from_to", from_to);
								bundle.putString("time", time);
								
								bundle.putString("classes", classes);
								bundle.putString("date", date);
								bundle.putString("ConfirmDate", ConfirmDate);
								bundle.putString("ConfirmTime", ConfirmTime);
								bundle.putString("ExtraCityID", ExtraCityID);
								bundle.putString("ExtraCityName", ExtraCityName);
								
								bundle.putString("ticketNos", ticketNos);
								
								bundle.putString("ReturnDate", return_date);
								bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
								bundle.putInt("trip_type", trip_type);
								bundle.putString("from_intent", from_intent);
								
								Intent intent = new Intent(PaymentActivity.this, Payment2C2PActivity.class).putExtras(bundle);
								startActivityForResult(intent, REQ_CODE);
								
						}else if (from_payment.equals("Cash on Delivery")){
							
							dialog = new ZProgressHUD(PaymentActivity.this);
							dialog.setMessage("Pls wait...");
							dialog.show();
							dialog.setCancelable(false);
							
							//If One Way
							if (trip_type == 1) {
								confirmOrder(from_payment, selectedSeats, ticketNos
										, busOccurence, BuyerName, BuyerNRC, permit_access_token
										, sale_order_no, Permit_agent_id, ExtraCityID, ConfirmDate, "");
							}else if (trip_type == 2) {
								//If Round Trip
								//Confirm for Go Trip
								confirmOrder(from_payment, goTripInfo_obj.getSelected_seats(), goTripInfo_obj.getTicket_nos()
										, goTripInfo_obj.getBusOccurence(), goTripInfo_obj.getBuyerName()
										, goTripInfo_obj.getBuyerNRC(), goTripInfo_obj.getPermit_access_token()
										, goTripInfo_obj.getSale_order_no(), goTripInfo_obj.getPermit_agent_id()
										, goTripInfo_obj.getExtraCityID(), goTripInfo_obj.getConfirmDate(), "");
							}
						}
					}else {
						skDetector.showErrorMessage();
					}
				}
			}
		}
	};
	
	private String[] selectedSeat;
	private String[] ticketNoArray;
	protected ZProgressHUD progress;
	private String points;
	private String gift_money;
	private String use_points;
	private String use_gift_money;
	
	/**
	 * Confirm and save order into Operator Database and online DB
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
	private void confirmOrder(final String paymentType, String selectedSeats, final String ticketNos
			, String busOccurence, String buyerName, String buyerNRC, String permitAccessToken
			, String saleOrderNo, String permitAgentId, String ExtraCityId, String confirmDate
			, String from_go_trip_success) {
		
		Log.i("", "Ticket list: "+ticketNos);
		Log.i("", "buyer nrc: "+buyerNRC);
		
		final String from_goTrip_success = from_go_trip_success;
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
		
		if (selectedSeats != null) {
			selectedSeat = selectedSeats.split(",");
		}
		
		if (ticketNos != null && !ticketNos.equals("")) {
			ticketNoArray = ticketNos.split(",");
		}
		
		Log.i("", "Selected Seats(Payment) : "+selectedSeats);
		
		for (int j = 0; j < selectedSeat.length; j++) {
			seats.add(new ConfirmSeat(busOccurence, selectedSeat[j].toString(),
					buyerName, buyerNRC, ticketNoArray[j].toString(), false,
					"blah", 0));
		}
		
		Log.i("", "Ticket Arrays: "+seats.toString());
		
		Log.i("", "Param (Confirm) to encrypt: "
				+"access: "+permit_access_token+
				", SaleOrderNo: "+sale_order_no+
				", AgentID: "+Permit_agent_id+
				", Agent Name: "+Operator_Name+
				", Customer: "+AppLoginUser.getUserName()+
				", Phone: "+AppLoginUser.getPhone()+
				", Nrc: "+BuyerNRC+
				", Extra city id: "+ExtraCityID+
				", Seats: "+seats.toString()+
				", Order date: "+ConfirmDate+
				", Device id: "+DeviceUtil.getInstance(this).getID()+
				", isbooking: "+"0");
		
		//Do Encrypt of Params				
		String param = MCrypt.getInstance()
				.encrypt(
				SecureParam.postSaleConfirmParam(permitAccessToken
				, saleOrderNo, "0"
				, permitAgentId, ""
				, AppLoginUser.getUserName()
				, AppLoginUser.getPhone(), buyerNRC
				, "0", ""
				, ExtraCityId,  MCrypt.getInstance()
				.encrypt(seats.toString())
				, "1"
				, "local"
				, confirmDate, DeviceUtil.getInstance(this).getID(), "0", String.valueOf(AppLoginUser.getId())));
				
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
						
						if(!jsonObj.getBoolean("status") && jsonObj.getString("device_id").equals(DeviceUtil.getInstance(PaymentActivity.this).getID())){
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(PaymentActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
						}else{
							//Save into Online Database
							if (trip_type == 1) {
								//If One Way
								postOnlineSaleConfirm(paymentType, from_goTrip_success, sale_order_no
													, operator_id, ExtraCityName, agentgroup_id
													, ticketNos, String.valueOf(totalGiftMoney));
								
							}else if (trip_type == 2) {
								//If Round Trip
		        				if (!from_goTrip_success.equals("from_go_trip_success")) {
		        					
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
								}
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
		
		//bundle.getString("permit_ip")
		HttpConnection lt = new HttpConnection(handler, "POST",
				"http://"+bundle.getString("permit_ip")+"/sale/comfirm", params);
		lt.execute();
		
		Log.i("", "Permit IP: "+bundle.getString("permit_ip"));
	}
	
	/**
	 *  Store orders into Online Sale Database
	 * @param ticketNos2 Star Ticket No(s)
	 * @param agentgroup_id2 Agent Group Id
	 * @param extraCityName2  Extra City Name
	 * @param operator_id2 Operator Id
	 * @param sale_order_no2 Order No
	 */
	private void postOnlineSaleConfirm(final String paymentType, final String from_goTrip_success, String sale_order_no2
			, String operator_id2, String extraCityName2, String agentgroup_id2, String ticketNos2, String totalGiftMoney) {
		// TODO Auto-generated method stub
		Log.i("", "sale_order_no: "+sale_order_no2+", operator_id: "
				+operator_id2+", user_code_no: "+AppLoginUser.getCodeNo()
				+", access_token: "+AppLoginUser.getAccessToken()
				+", extra_name: "+extraCityName2
				+", payment_type: "+paymentType
				+", loyalty_phone: "+AppLoginUser.getPhone()
				+", loyalty_name: "+AppLoginUser.getUserName()
				+", loyalty_address: "+AppLoginUser.getAddress()
				+", use_gift_money: "+totalGiftMoney
				+", starticket_no: "+ticketNos2
				+",	agentgroup_id: "+agentgroup_id2);
		
		Integer tripTypeOnline = 0;
		
		if (trip_type == 1) {
			//If one way 
			tripTypeOnline = 0;
		}else if (trip_type == 2) {
			//If round trip
			tripTypeOnline = 1;
		}
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postOnlineSaleDB(
				sale_order_no2, 
				operator_id2, 
				AppLoginUser.getCodeNo(), 
				AppLoginUser.getAccessToken(), 
				extraCityName2, 
				AppLoginUser.getPhone(), 
				AppLoginUser.getUserName(), 
				AppLoginUser.getAddress(), 
				"", "0", 
				totalGiftMoney, "", "", 
				agentgroup_id2, "", paymentType, ticketNos2, tripTypeOnline.toString(), new Callback<Response>() {
			
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
							
							if (paymentType.equals("Pay with Online") && paymentType.equals("Pay with MPU") && paymentType.equals("Pay with VISA/MASTER")) {
								
								Bundle bundle = new Bundle();
		        				bundle.putString("payment_type", "Pay with Online");
		        				bundle.putString("payment_type", "Pay with MPU");
		        				bundle.putString("payment_type", "Pay with VISA/MASTER");
		        				startActivity(new Intent(PaymentActivity.this, ThankYouActivity.class).putExtras(bundle));
		        				
		        				dialog.dismissWithSuccess();
		        				
							}else if (paymentType.equals("Cash on Delivery")) {
								
								Bundle bundle = new Bundle();
		        				bundle.putString("payment_type", "Cash on Delivery");
								
								if (trip_type == 1) {
									//If one way
			        				startActivity(new Intent(PaymentActivity.this, ThankYouActivity.class).putExtras(bundle));
			        				dialog.dismissWithSuccess();
								}else if (trip_type == 2) {
									//If round trip
			        				if (!from_goTrip_success.equals("from_go_trip_success")) {
			        					
			        					Log.i("", "go trip success: "+from_goTrip_success);
			        					
			        					//If return trip not success yet, .......
			        					//Confirm for Return Trip
			        					confirmOrder(from_payment, selectedSeats, ticketNos
												, busOccurence, BuyerName, BuyerNRC, permit_access_token
												, sale_order_no, Permit_agent_id, ExtraCityID, ConfirmDate, "from_go_trip_success");
			        					
									}else {
										
										Log.i("", "return success: "+from_goTrip_success);
										
										//If return trip success, Go to thank you page
										startActivity(new Intent(PaymentActivity.this, ThankYouActivity.class).putExtras(bundle));
										dialog.dismissWithSuccess();
									}
								}
							}
						}
					}
				});
	}
	
	protected boolean checkField() {
		// TODO Auto-generated method stub
		use_points = "0";
		use_gift_money = "0";
		
		if (edt_points.getText().toString().length() > 0){
			use_points = edt_points.getText().toString();
		}
		
		if (edt_gift_money.getText().toString().length() > 0){
			use_gift_money = edt_gift_money.getText().toString();
		}
		
		if (Integer.valueOf(use_points) > totalPoints) {
			edt_points.setError("Check your total points!");
			return false;
		}
		if (Integer.valueOf(use_gift_money) > totalGiftMoney) {
			edt_gift_money.setError("Check your total Gift Money!");
			return false;
		}
		return true;
	}
	
	/**
	 * close the activity 
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//For Google Analytics
		Tracker v3Tracker = GoogleAnalytics.getInstance(this).getTracker("UA-67985681-1");

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		v3Tracker.set(Fields.SCREEN_NAME, "Loyalty Program Screen, "+AppLoginUser.getUserName());
		
		v3Tracker.send(MapBuilder.createAppView().build());
		
		// And so will this event hit.
		v3Tracker.send(MapBuilder
				  .createEvent("UX", "touch", "menuButton", null)
				  .build()
				);
	}
}
