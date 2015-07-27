package com.ignite.mm.ticketing.user;

import info.hoang8f.widget.FButton;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.Loyalty;
import com.smk.skalertmessage.SKToastMessage;
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

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		bundle = getIntent().getExtras();	
		
		if (bundle != null) {
			price = bundle.getString("price");
			seat_count = bundle.getString("seat_count");
			agentgroup_id = bundle.getString("agentgroup_id");
			operator_id = bundle.getString("operator_id");
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
		
		private String points_toUse;
		private String giftMoney_toUse;
		private String promoCode;
		private String pinNo;

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == txt_use_points) {
				startActivity(new Intent(PaymentActivity.this, LoyaltyProgramActivity.class));
			}
			if (v == btn_payment) {
				
				if (checkField()) {
					
					points_toUse = edt_points.getText().toString();
					giftMoney_toUse = edt_gift_money.getText().toString();
					promoCode = edt_promo_code.getText().toString();
					pinNo = edt_pin_code.getText().toString();
					
					startActivity(new Intent(PaymentActivity.this, Payment2C2PActivity.class));
				}
				
				
			}
		}
	};

	protected boolean checkField() {
		// TODO Auto-generated method stub
		if (edt_points.getText().toString().length() == 0) {
			edt_points.setError("Enter Points Quantity to use");
			return false;
		}
		if (Integer.valueOf(edt_points.getText().toString()) > totalPoints) {
			edt_points.setError("Check your total points!");
			return false;
		}
		if (edt_gift_money.getText().toString().length() == 0) {
			edt_gift_money.setError("Enter Gift Money Amount to use");
			return false;
		}
		if (Integer.valueOf(edt_gift_money.getText().toString()) > totalGiftMoney) {
			edt_gift_money.setError("Check your total Gift Money!");
			return false;
		}
		if (edt_promo_code.getText().toString().length() == 0) {
			edt_promo_code.setError("Enter Promotion Code");
			return false;
		}
		if (edt_pin_code.getText().toString().length() == 0) {
			edt_pin_code.setError("Enter Pin No");
			return false;
		}
		return true;
	}
	
	
}
