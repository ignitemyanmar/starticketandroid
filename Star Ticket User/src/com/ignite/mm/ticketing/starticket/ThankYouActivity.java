package com.ignite.mm.ticketing.starticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.ignite.mm.ticketing.application.BaseActivity;

/**
 * {@link #ThankYouActivity} is the class to show Thank You page after order (or) booking success
 * <p>
 * (1) {@link #onBackPressed()}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class ThankYouActivity extends BaseActivity{
	private TextView txt_thankYou;
	private TextView txt_email_send;
	private TextView btn_continue_buy;
	private String payment_type;
	private TextView txt_hotline1;
	private TextView txt_hotline2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanks);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			payment_type = bundle.getString("payment_type");
		}
		
		Log.i("", "Payment Type (thank you): "+payment_type);
		
		txt_thankYou = (TextView)findViewById(R.id.txt_thankyou);
		txt_email_send = (TextView)findViewById(R.id.txt_email_send);
		btn_continue_buy = (TextView)findViewById(R.id.btn_continue_buy);
		txt_hotline1 = (TextView)findViewById(R.id.txt_hotline1);
		txt_hotline2 = (TextView)findViewById(R.id.txt_hotline2);
		
		txt_hotline1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callHotLine(txt_hotline1.getText().toString());
			}
		});
		
		txt_hotline2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callHotLine(txt_hotline2.getText().toString());
			}
		});
		
		if (payment_type.equals("Cash on Shop")) {
			txt_thankYou.setText("Thank you! Your booking is complete! Please pay at nearest convenience stores (CityExpress, G&G, ABC) within 2 Hours, if not your booking will be canceled!!");
		}else if (payment_type.equals("Pay with MPU") && payment_type.equals("Pay with VISA/MASTER")) {
			txt_thankYou.setText("Thank you! Your order is complete!");
		}else if (payment_type.equals("Cash on Delivery")) {
			txt_thankYou.setText("Thank you! Your order is complete! We'll deliver after calling you "+AppLoginUser.getPhone());
		}
		
		btn_continue_buy.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				closeAllActivities();
				startActivity(new Intent(ThankYouActivity.this, SaleTicketActivity.class));
			}
		});
		//txt_email_send.setVisibility(View.VISIBLE);
	}
	
	/**
	 * If back arrow button clicked, close All Activities and go back {@link SaleTicketActivity}
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		closeAllActivities();
		startActivity(new Intent(ThankYouActivity.this, SaleTicketActivity.class));
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
		v3Tracker.set(Fields.SCREEN_NAME, "Thank You Screen, "
				+payment_type+", "
				+AppLoginUser.getUserName());
		
		// This screenview hit will include the screen name.
		v3Tracker.send(MapBuilder.createAppView().build());
	}
}
