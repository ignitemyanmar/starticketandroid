package com.ignite.mm.ticketing.user;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mm.ticketing.sqlite.database.model.Loyalty;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

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
	protected Integer totalGiftMoney = 0;
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
	private Integer total_amount = 0;
	private String price;
	private String seat_count;
	
	final static int REQ_CODE = 1;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
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
		}
		
		setContentView(R.layout.activity_payment);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Loyalty Program");
            /*toolbar.setTitle(bundle.getString("from_to")+" ["+bundle.getString("Operator_Name")+"] "
            					+bundle.getString("date")+" ["+bundle.getString("time")+"] "
            					+bundle.getString("classes"));*/
            this.setSupportActionBar(toolbar);
        }
		
		txt_one_point_amount = (TextView)findViewById(R.id.txt_one_point_amount);
		txt_total_amount = (TextView)findViewById(R.id.txt_total_amount);
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
		
		if (price != null && seat_count != null) {
			priceInt = Integer.valueOf(price);
			seat_countInt = Integer.valueOf(seat_count);
			total_amount = priceInt * seat_countInt;
		}else {
			total_amount = priceInt * seat_countInt;
		}
		
		//Get Usable Points & Gift Money
		postLoytalty();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
	     if(requestCode == REQ_CODE){

				if (resultCode == RESULT_OK){

					Log.i("", "Payment Success !!!!!!!!!!!!!!!!!!!!!!!");
					
					//confirmOrder(from_payment);

				}else if(resultCode == RESULT_CANCELED){

					//confirmOrder(from_payment);
					
					Log.i("", "Payment Cancel ................... :(");
					Toast.makeText(PaymentActivity.this, "You cancel Payment!", Toast.LENGTH_LONG).show();
				}
			}
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		deleteSeats();
		return super.getSupportParentActivityIntent();
	}
	
	private void postLoytalty() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(PaymentActivity.this);
		dialog.show();
		
		txt_total_amount.setText(total_amount.toString());
		
		Log.i("", "Loyalty Ph: "+AppLoginUser.getPhone()
				+", total amount: "+String.valueOf(total_amount)
				+", Payment method: "+1
				+", Agent group id: "+agentgroup_id
				+", Operator id: "+operator_id
				);
		
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().postLoyalty(AppLoginUser.getPhone(), String.valueOf(total_amount)
							, "1", agentgroup_id
							, operator_id, new Callback<Loyalty>() {
			
			public void success(Loyalty arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				if (arg0 != null) {
					
					Integer current_points = 0;
					Integer current_giftMoneys = 0;
					
					if (arg0.getCurrent_points() == null) {
						totalPoints = arg0.getPoints() + current_points;
					}else {
						current_points = Integer.valueOf(arg0.getCurrent_points());
						totalPoints = arg0.getPoints() + current_points;
					}
					
					if (arg0.getCurrent_gift_money() == null) {
						totalGiftMoney = arg0.getGiftMoney() + current_giftMoneys;
					}else {
						current_giftMoneys = Integer.valueOf(arg0.getCurrent_gift_money());
						totalGiftMoney = arg0.getGiftMoney() + current_giftMoneys;
					}
					
					Log.i("", "Loyalty Obj: "+arg0.toString());
					
					txt_current_points.setText(current_points+"");
					txt_current_gift_money.setText(current_giftMoneys+" Ks");
					txt_old_points.setText(arg0.getPoints()+"");
					txt_old_gift_money.setText(arg0.getGiftMoney()+" Ks");
					
					txt_total_points.setText(totalPoints+"");
					txt_total_gift_money.setText(totalGiftMoney+" Ks");
				}
				
				dialog.dismissWithSuccess();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				Log.i("", "Error");
				
				dialog.dismissWithFailure();
			}
		});
	}

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
						
						if (from_payment.equals("Pay with Online")) {
							
							if (points_toUse != null && !points_toUse.equals("")) {
								points_to_use = Integer.valueOf(points_toUse);
							}
							
							if (giftMoney_toUse != null && !giftMoney_toUse.equals("")) {
								giftMoney_to_use  = Integer.valueOf(giftMoney_toUse);
							}
							
							//Integer total_need_to_pay = total_amount - ((points_to_use * 10) + giftMoney_to_use);
							Integer total_need_to_pay = total_amount - totalGiftMoney;
							
							Bundle bundle = new Bundle();
							bundle.putString("order_no", sale_order_no);
							bundle.putString("order_amount", total_need_to_pay.toString());
							bundle.putString("points_toUse", points_toUse);
							bundle.putString("giftMoney_toUse", giftMoney_toUse);
							bundle.putString("total_giftMoney", totalGiftMoney.toString());
							
							bundle.putString("from_payment", from_payment);
							bundle.putString("price", price);
							bundle.putString("seat_count", seat_count);
							bundle.putString("agentgroup_id", agentgroup_id);
							bundle.putString("operator_id", operator_id);
							bundle.putString("sale_order_no", sale_order_no);
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
							
							Intent intent = new Intent(PaymentActivity.this, Payment2C2PActivity.class).putExtras(bundle);
							startActivityForResult(intent, REQ_CODE);
							
						}else if (from_payment.equals("Cash on Delivery")){
							
							dialog = new ZProgressHUD(PaymentActivity.this);
							dialog.show();
							
							confirmOrder(from_payment);
						}
					}else {
						skDetector.showErrorDialog();
					}
				}
			}
		}
	};
	
	private String[] selectedSeat;
	protected ZProgressHUD progress;
	private String points;
	private String gift_money;
	private String use_points;
	private String use_gift_money;
	
	private void confirmOrder(final String paymentType) {
		
		Log.i("", "buyer nrc: "+BuyerNRC);
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
		
		if (selectedSeats != null) {
			selectedSeat = selectedSeats.split(",");
		}
		
		Log.i("", "Selected Seats(Payment) : "+selectedSeats);
		
		for (int j = 0; j < selectedSeat.length; j++) {
			seats.add(new ConfirmSeat(busOccurence, selectedSeat[j].toString(),
					BuyerName, BuyerNRC, "0", false,
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
				SecureParam.postSaleConfirmParam(permit_access_token
				, sale_order_no, "0"
				, Permit_agent_id, ""
				, AppLoginUser.getUserName()
				, AppLoginUser.getPhone(), BuyerNRC
				, "0", ""
				, ExtraCityID,  MCrypt.getInstance()
				.encrypt(seats.toString())
				, "1"
				, "local"
				, ConfirmDate, DeviceUtil.getInstance(this).getID(), "0", String.valueOf(AppLoginUser.getId())));
				
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
							SKToastMessage.showMessage(PaymentActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားေသာေၾကာင့္ သင္ မွာ ေသာ လက္ မွတ္ မ်ား မရ ႏုိင္ေတာ့ပါ။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
							dialog.dismissWithFailure();
						}else{
							//Store Sale on City Mart DB
							postOnlineSaleConfirm(paymentType);
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
	 *  Store sales into Online Sale Database (test.starticketmyanmar.com)
	 */
	protected void postOnlineSaleConfirm(final String paymentType) {
		// TODO Auto-generated method stub
		Log.i("", "SaleOrderNo: "+sale_order_no+", Op-Id: "+operator_id+", User code no: "+AppLoginUser.getCodeNo()
				+", Token: "+AppLoginUser.getAccessToken()+", paymentType: "+paymentType);
		
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().postOnlineSaleDB(sale_order_no, operator_id, AppLoginUser.getCodeNo()
				, AppLoginUser.getAccessToken(), ExtraCityName, AppLoginUser.getPhone()
				, AppLoginUser.getUserName(), AppLoginUser.getAddress(), ""
				, points_toUse, totalGiftMoney.toString(), "", "", agentgroup_id, ""
				, paymentType, new Callback<Response>() {
			
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Error: "+arg0.getResponse().getStatus());
						}
						
						dialog.dismissWithFailure("Sorry, Can't Confirm!");
					}

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							
							dialog.dismissWithSuccess();
							
							if (paymentType.equals("Pay with Online")) {
								SKToastMessage.showMessage(PaymentActivity.this, AppLoginUser.getPhone()+"လက္ မွတ္ ျဖတ္ ၿပီး ပါ ၿပီ။", SKToastMessage.SUCCESS);
							}else if (paymentType.equals("Cash on Delivery")) {
								SKToastMessage.showMessage(PaymentActivity.this, AppLoginUser.getPhone()+" သုိ႔ ဖုန္းဆက္ၿပီး လာပုိ႔ေပးပါမည္", SKToastMessage.SUCCESS);
							}
							
							closeAllActivities();
							startActivity(new Intent(PaymentActivity.this, SaleTicketActivity.class));
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		deleteSeats();
	}
	
	private void deleteSeats() {
		// TODO Auto-generated method stub
		AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
		alertDialog.setMessage("You want to cancel Seats?");

		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (SKConnectionDetector.getInstance(
								PaymentActivity.this)
								.isConnectingToInternet()) {
							
							String param = MCrypt.getInstance().encrypt(SecureParam.deleteSaleOrderParam(permit_access_token));
							
							Log.i("", "Permit IP: "+permit_ip+", Param to delete: "+param+", SaleOrderNo to delete: "+MCrypt.getInstance().encrypt(sale_order_no));
							
							progress = new ZProgressHUD(PaymentActivity.this);
							progress.show();
							
							NetworkEngine.setIP(permit_ip);
							NetworkEngine.getInstance().deleteSaleOrder(
									param, MCrypt.getInstance().encrypt(sale_order_no),
									new Callback<Response>() {

										public void success(
												Response arg0,
												Response arg1) {
											
											closeAllActivities();
					    					startActivity(new Intent(PaymentActivity.this, SaleTicketActivity.class));
					    					
					    					progress.dismiss();
										}

										public void failure(
												RetrofitError arg0) {
											// TODO Auto-generated method
											progress.dismiss();
											Log.i("", "Can't delete!");
										}
									});
						}else {
							SKConnectionDetector.getInstance(PaymentActivity.this).showErrorDialog();
						}
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						return;
					}
				});

		alertDialog.show();
	}
}
