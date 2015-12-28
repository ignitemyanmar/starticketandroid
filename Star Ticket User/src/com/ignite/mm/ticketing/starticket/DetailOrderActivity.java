package com.ignite.mm.ticketing.starticket;

import java.text.NumberFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;

/**
 * {@link #OrderDetailActivity} is the class to show Order Confirm Detail Info
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class DetailOrderActivity extends BaseActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private String orderString;
	private ThreeDaySale orderDetailList;
	private TextView txt_order_no;
	private TextView txt_buy_date;
	private TextView txt_trip;
	private TextView txt_trip_date_time;
	private TextView txt_operator;
	private TextView txt_bus_class;
	private TextView txt_seats;
	private TextView txt_price;
	//private TextView txt_seat_qty;
	//private TextView txt_amount;
	private TextView txt_discount;
	private TextView txt_amount_needToPay;
	private TextView txt_ticket_nos;
	private TextView txt_trip_date_time2;
	private TextView txt_trip_date_time3;
	private TextView txt_transaction_id;
	private TextView txt_payment_type;
	private TextView txt_status;
	private LinearLayout layout_delivery;
	private TextView txt_delivery_charges;
	private int delivery_charges = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_myticket);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			orderString = extras.getString("order_detail");
		}
		
		 orderDetailList = new Gson().fromJson(orderString, ThreeDaySale.class);
		 
		 txt_buy_date = (TextView)  findViewById(R.id.txt_buy_date);
		 txt_trip = (TextView)  findViewById(R.id.txt_trip);
		 txt_trip_date_time = (TextView)  findViewById(R.id.txt_trip_date_time);
		 txt_trip_date_time2 = (TextView)  findViewById(R.id.txt_trip_date_time2);
		 txt_trip_date_time3 = (TextView)  findViewById(R.id.txt_trip_date_time3);
		 txt_operator = (TextView)  findViewById(R.id.txt_operator);
		 txt_bus_class = (TextView)  findViewById(R.id.txt_bus_class);
		 txt_seats = (TextView)  findViewById(R.id.txt_seats);
		 txt_ticket_nos = (TextView) findViewById(R.id.txt_ticket_nos);
		 txt_price = (TextView)  findViewById(R.id.txt_price);
		 //txt_seat_qty = (TextView)  findViewById(R.id.txt_seat_qty);
		 //txt_amount = (TextView)  findViewById(R.id.txt_amount);
		 txt_discount = (TextView)  findViewById(R.id.txt_discount);
		 txt_order_no = (TextView)  findViewById(R.id.txt_order_no);
		 txt_amount_needToPay = (TextView)  findViewById(R.id.txt_amount_needToPay);
		 txt_transaction_id = (TextView)  findViewById(R.id.txt_transaction_id);
		 txt_payment_type = (TextView)  findViewById(R.id.txt_payment_type);
		 txt_status = (TextView)  findViewById(R.id.txt_status);
		 layout_delivery = (LinearLayout)findViewById(R.id.layout_delivery);
		 txt_delivery_charges = (TextView)findViewById(R.id.txt_delivery_charges);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Order လက္ မွတ္   ");
            this.setSupportActionBar(toolbar);
        }
        
        txt_order_no.setText(orderDetailList.getOrderId()+"");
		txt_buy_date.setText(orderDetailList.getDate());
		txt_trip.setText(orderDetailList.getTrip());
		txt_trip_date_time.setText(changeDate(orderDetailList.getDepartureDate()));
		txt_trip_date_time2.setText("");
		txt_trip_date_time3.setText(orderDetailList.getTime());
		txt_operator.setText(orderDetailList.getOperator());
		txt_bus_class.setText(orderDetailList.getClass_());
		txt_seats.setText(orderDetailList.getSeatNo());
		txt_ticket_nos.setText(orderDetailList.getTicketNo());
		
		if (orderDetailList.getPaymentType() != null) {
			if (orderDetailList.getPaymentType().equals("Cash on Delivery")) {
				if (orderDetailList.getDelivery_charges() > 0) {
					layout_delivery.setVisibility(View.VISIBLE);
					txt_delivery_charges.setText("Ks "+orderDetailList.getDelivery_charges());
					delivery_charges = orderDetailList.getDelivery_charges();
				}else {
					layout_delivery.setVisibility(View.VISIBLE);
					delivery_charges = 1000;
				}
			}
		}
		
		//Show Delivery Status (pending or complete)
		if (orderDetailList.getDelivery().equals("1")) {
			txt_status.setText("Progressing...");
			txt_status.setTextColor(getResources().getColor(R.color.yellow));
		}else {
			txt_status.setText("Paid");
			txt_status.setTextColor(getResources().getColor(R.color.green));
		}
		
		//Show payment type
		if (orderDetailList.getPaymentType() != null) {
			if (orderDetailList.getPaymentType().equals("Pay with VISA/MASTER")) {
				txt_payment_type.setText("Pay with VISA/MASTER");
			}else {
				txt_payment_type.setText(orderDetailList.getPaymentType());
			}
		}
		
		if (orderDetailList.getTransactionId().equals("0")) 
		{
			txt_transaction_id.setText("#");
		}else {
			txt_transaction_id.setText(orderDetailList.getTransactionId());
		}
		
		//Change (0,000,000) format
			NumberFormat nf = NumberFormat.getInstance();
			
			double price_Int = 0.0;
			double amount_Int = 0.0;
			double discount_Int = 0.0;
			double exchange_rate = 0.0;
			double amount_USD = 0.0;
			//double ticket_qty = 0.0;
			
			//Get Exchange Rate
			if (orderDetailList.getExchangeRate() > 0) {
				exchange_rate = Double.valueOf(orderDetailList.getExchangeRate());
			}
			
			if (orderDetailList.getPrice() > 0) {
				price_Int = Double.valueOf(orderDetailList.getPrice());
			}
			
			if (orderDetailList.getTotalAmount() > 0) {
				amount_Int = Double.valueOf(orderDetailList.getTotalAmount());
			}
			
			if (orderDetailList.getTotalUSD() > 0) {
				amount_USD = Double.valueOf(orderDetailList.getTotalUSD());
			}
			
			//Show Discount
			if (orderDetailList.getDiscountAmount() > 0) {
				if (orderDetailList.getPaymentType() != null) {
					if (orderDetailList.getPaymentType().equals("Pay with VISA/MASTER")) {
						if (exchange_rate > 0) {
							discount_Int = Double.valueOf(orderDetailList.getDiscountAmount()) / exchange_rate;
						}
					}else {
						discount_Int = Double.valueOf(orderDetailList.getDiscountAmount());
					}
				}
			}
			
			String price  = nf.format(price_Int);
			String amount  = nf.format(amount_Int);
		
		Double totalUSD = 0.0;
		 
		//Show Total amount
		if (orderDetailList.getPaymentType() != null) {
			if (orderDetailList.getPaymentType().equals("Pay with VISA/MASTER")) {
				Log.i("", "detail payment(visa/m): "+orderDetailList.getPaymentType());
				if (orderDetailList.getRoundTrip().equals("0")) {
					//one way
					//add +4USD for booking fee
					totalUSD = amount_USD + 1;
					//txt_amount.setText("US$ "+String.format("%.2f", totalUSD)+"");
				}else if (orderDetailList.getRoundTrip().equals("1")) {
					//round trip 
					//add +2USD for booking fee
					totalUSD = amount_USD + 0.5;
					//txt_amount.setText("US$ "+String.format("%.2f", totalUSD)+"");
				}
				
				txt_price.setText(orderDetailList.getTicketQty()+"x "+orderDetailList.getTotalUSD() / 2);
				txt_discount.setText("US$ "+String.format("%.2f", discount_Int));
				txt_amount_needToPay.setText("US$ "+String.format("%.2f", (totalUSD - discount_Int)));
			}else {
				Log.i("", "detail payment: "+orderDetailList.getPaymentType());
				
				txt_price.setText(orderDetailList.getTicketQty()+"x "+orderDetailList.getPrice());
				txt_discount.setText(nf.format(discount_Int)+" Ks");
				double total = (amount_Int - discount_Int) + delivery_charges;
				txt_amount_needToPay.setText(nf.format(total)+" Ks");
			}
		}
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
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
		v3Tracker.set(Fields.SCREEN_NAME, "e-Voucher Screen, "
				+AppLoginUser.getUserName());
		
		// This screenview hit will include the screen name.
		v3Tracker.send(MapBuilder.createAppView().build());
	}
}

