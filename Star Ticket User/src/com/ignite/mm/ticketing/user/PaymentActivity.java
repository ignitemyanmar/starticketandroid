package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.actionbarsherlock.app.ActionBar;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;

public class PaymentActivity extends BaseSherlockActivity{

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
	private Button btn_confirm;
	private TextView txt_use_points;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setText("Payment Info");
		//actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		//actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);	
		
		bundle = getIntent().getExtras();	
		
		/*actionBarTitle.setText(bundle.getString("from_to")+" ["+bundle.getString("Operator_Name")+"]");
		Log.i("", "Date: "+bundle.getString("date"));
		actionBarTitle2.setText(bundle.getString("date")+" ["+bundle.getString("time")+"] "+bundle.getString("classes"));*/
		
		setContentView(R.layout.activity_payment);
		
		edt_cardholder_name = (EditText)findViewById(R.id.edt_cardholder_name);	
		
		edt_card_no = (EditText)findViewById(R.id.edt_card_no);
		edt_security_code = (EditText)findViewById(R.id.edt_security_code);
		
		txt_use_points = (TextView)findViewById(R.id.txt_use_points);
		
		btn_confirm = (Button)findViewById(R.id.btn_confirm);
		
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
		btn_confirm.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == txt_use_points) {
				startActivity(new Intent(PaymentActivity.this, LoyaltyProgramActivity.class));
			}
			if (v == btn_confirm) {
				
				startActivity(new Intent(PaymentActivity.this, Payment2C2PActivity.class));
				
			}
		}
	};
}
