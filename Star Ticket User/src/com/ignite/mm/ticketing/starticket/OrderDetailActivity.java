package com.ignite.mm.ticketing.starticket;

import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

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
public class OrderDetailActivity extends BaseActivity{

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
	private TextView txt_seat_qty;
	private TextView txt_amount;
	private TextView txt_discount;
	private TextView txt_amount_needToPay;
	private TextView txt_ticket_nos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_item_threeday_sales);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			orderString = extras.getString("order_detail");
		}
		
		orderDetailList = new Gson().fromJson(orderString, ThreeDaySale.class);
		
		 txt_buy_date = (TextView)  findViewById(R.id.txt_buy_date);
		 txt_trip = (TextView)  findViewById(R.id.txt_trip);
		 txt_trip_date_time = (TextView)  findViewById(R.id.txt_trip_date_time);
		 txt_operator = (TextView)  findViewById(R.id.txt_operator);
		 txt_bus_class = (TextView)  findViewById(R.id.txt_bus_class);
		 txt_seats = (TextView)  findViewById(R.id.txt_seats);
		 txt_ticket_nos = (TextView) findViewById(R.id.txt_ticket_nos);
		 txt_price = (TextView)  findViewById(R.id.txt_price);
		 txt_seat_qty = (TextView)  findViewById(R.id.txt_seat_qty);
		 txt_amount = (TextView)  findViewById(R.id.txt_amount);
		 txt_discount = (TextView)  findViewById(R.id.txt_discount);
		 txt_order_no = (TextView)  findViewById(R.id.txt_order_no);
		 txt_amount_needToPay = (TextView)  findViewById(R.id.txt_amount_needToPay);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Order လက္ မွတ္   ");
            this.setSupportActionBar(toolbar);
        }
        
        txt_order_no.setText("Order နံပါတ္ : "+orderDetailList.getOrderId());
		txt_buy_date.setText(orderDetailList.getDate());
		txt_trip.setText(orderDetailList.getTrip());
		txt_trip_date_time.setText(changeDate(orderDetailList.getDepartureDate())+" - "+orderDetailList.getTime());
		txt_operator.setText(orderDetailList.getOperator());
		txt_bus_class.setText(orderDetailList.getClass_());
		txt_seats.setText(orderDetailList.getSeatNo());
		txt_ticket_nos.setText(orderDetailList.getTicketNo());
		
		
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
					if (orderDetailList.getPaymentType().toLowerCase().equals("pay with master/visa")) {
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
			
		txt_seat_qty.setText(orderDetailList.getTicketQty()+"");
		txt_price.setText(price+" Ks");
		
		Double totalUSD = 0.0;
		 
		//Show Total amount
		if (orderDetailList.getPaymentType() != null) {
			if (orderDetailList.getPaymentType().toLowerCase().equals("pay with master/visa")) {
				if (orderDetailList.getRoundTrip().equals("0")) {
					//one way
					//add +4USD for booking fee
					totalUSD = amount_USD + 4;
					txt_amount.setText("US$ "+String.format("%.2f", totalUSD)+"");
				}else if (orderDetailList.getRoundTrip().equals("1")) {
					//round trip 
					//add +2USD for booking fee
					totalUSD = amount_USD + 2;
					txt_amount.setText("US$ "+String.format("%.2f", totalUSD)+"");
				}
				
				txt_discount.setText("US$ "+String.format("%.2f", discount_Int));
				txt_amount_needToPay.setText("US$ "+String.format("%.2f", (totalUSD - discount_Int)));
			}else {
				txt_amount.setText(amount+" Ks");
				txt_discount.setText(nf.format(discount_Int)+" Ks");
				txt_amount_needToPay.setText(nf.format(amount_Int - discount_Int)+" Ks");
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
}
