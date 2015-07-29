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
	private String price;
	private String seat_count;
	private String agentgroup_id;
	private String operator_id;
	private TextView txt_total_points;
	private TextView txt_total_gift_money;
	protected Integer totalPoints;
	protected Integer totalGiftMoney;
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

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		skDetector = new SKConnectionDetector(PaymentActivity.this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		
		bundle = getIntent().getExtras();	
		
		if (bundle != null) {
			price = bundle.getString("price");
			seat_count = bundle.getString("seat_count");
			agentgroup_id = bundle.getString("agentgroup_id");
			operator_id = bundle.getString("operator_id");
			
			sale_order_no = bundle.getString("sale_order_no");
			selectedSeats = bundle.getString("selectedSeats");
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
		
		//Get Usable Points & Gift Money
		postLoytalty();
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	private void postLoytalty() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(PaymentActivity.this);
		dialog.show();
		
		Integer total_amount = Integer.valueOf(price) * Integer.valueOf(seat_count);
		txt_total_amount.setText("Ks "+total_amount.toString());
		
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
					
					totalPoints = arg0.getPoints();
					totalGiftMoney = arg0.getGiftMoney();
					
					Log.i("", "Loyalty Obj: "+arg0.toString());
					//txt_one_point_amount.setText("မွတ္ခ်က္-    1 point = "+10 Ks");
					txt_one_point_amount.setText(arg0.getMessage());
					txt_total_points.setText(arg0.getPoints()+" points");
					txt_total_gift_money.setText("Ks "+arg0.getGiftMoney());
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
						
						dialog = new ZProgressHUD(PaymentActivity.this);
						dialog.show();
						
						confirmOrder();
					}else {
						skDetector.showErrorDialog();
					}
				}
			}
		}
	};
	
	private String[] selectedSeat;
	
	private void confirmOrder() {
		
		Log.i("", "buyer nrc: "+BuyerNRC);
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
		
		selectedSeat = selectedSeats.split(",");
		
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
							postOnlineSaleConfirm();
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
	protected void postOnlineSaleConfirm() {
		// TODO Auto-generated method stub
		Log.i("", "SaleOrderNo: "+sale_order_no+", Op-Id: "+operator_id+", User code no: "+AppLoginUser.getCodeNo()
				+", Token: "+AppLoginUser.getAccessToken());
		
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().postOnlineSaleDB(sale_order_no, operator_id, AppLoginUser.getCodeNo()
				, AppLoginUser.getAccessToken(), ExtraCityName, AppLoginUser.getPhone()
				, AppLoginUser.getUserName(), AppLoginUser.getAddress(), ""
				, points_toUse, giftMoney_toUse, "", "", agentgroup_id, "", new Callback<Response>() {
			
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
							
							SKToastMessage.showMessage(PaymentActivity.this, "လက္ မွတ္ ျဖတ္ ျပီးပါၿပီ !", SKToastMessage.SUCCESS);
							
							closeAllActivities();
							startActivity(new Intent(PaymentActivity.this, SaleTicketActivity.class));
						}
					}
				});
	}

	protected boolean checkField() {
		// TODO Auto-generated method stub
		String points = "0";
		String gift_money = "0";
		
		if (edt_points.getText().toString().length() > 0){
			points = edt_points.getText().toString();
		}
		
		if (edt_gift_money.getText().toString().length() > 0){
			gift_money = edt_gift_money.getText().toString();
		}
		
		if (Integer.valueOf(points) > totalPoints) {
			edt_points.setError("Check your total points!");
			return false;
		}
		if (Integer.valueOf(gift_money) > totalGiftMoney) {
			edt_gift_money.setError("Check your total Gift Money!");
			return false;
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		
		// TODO Auto-generated method stub
			AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
			alertDialog.setMessage("Are you sure to exit?");

			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if (SKConnectionDetector.getInstance(
									PaymentActivity.this)
									.isConnectingToInternet()) {
								String param = MCrypt.getInstance().encrypt(SecureParam.deleteSaleOrderParam(permit_access_token));
								
								Log.i("", "Param to delete: "+param+", SaleOrderNo to delete: "+MCrypt.getInstance().encrypt(sale_order_no));
								
								NetworkEngine.getInstance().deleteSaleOrder(
										param, MCrypt.getInstance().encrypt(sale_order_no),
										new Callback<Response>() {

											public void success(
													Response arg0,
													Response arg1) {
												
											}

											public void failure(
													RetrofitError arg0) {
												// TODO Auto-generated method
												// stub
											}
										});
							}
							finish();
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
