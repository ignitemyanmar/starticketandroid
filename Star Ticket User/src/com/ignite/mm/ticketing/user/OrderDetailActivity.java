package com.ignite.mm.ticketing.user;

import java.text.NumberFormat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;

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
		 txt_price = (TextView)  findViewById(R.id.txt_price);
		 txt_seat_qty = (TextView)  findViewById(R.id.txt_seat_qty);
		 txt_amount = (TextView)  findViewById(R.id.txt_amount);
		 txt_discount = (TextView)  findViewById(R.id.txt_discount);
		 txt_order_no = (TextView)  findViewById(R.id.txt_order_no);
		 txt_amount_needToPay = (TextView)  findViewById(R.id.txt_amount_needToPay);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Order လက္ မွတ္ ");
            this.setSupportActionBar(toolbar);
        }
        
        txt_order_no.setText("Order နံပါတ္  : "+orderDetailList.getOrderId());
		txt_buy_date.setText(orderDetailList.getDate());
		txt_trip.setText(orderDetailList.getTrip());
		txt_trip_date_time.setText(changeDate(orderDetailList.getDepartureDate())+" - "+orderDetailList.getTime());
		txt_operator.setText(orderDetailList.getOperator());
		txt_bus_class.setText(orderDetailList.getClass_());
		txt_seats.setText(orderDetailList.getSeatNo());
		
		
		//Change (0,000,000) format
			NumberFormat nf = NumberFormat.getInstance();
			
			Integer price_Int = 0;
			Integer amount_Int = 0;
			Integer discount_Int = 0;
			
			if (orderDetailList.getPrice() != null && !orderDetailList.getPrice().equals("")) {
				price_Int = Integer.valueOf(orderDetailList.getPrice());
			}
			
			if (orderDetailList.getTotalAmount() != null && !orderDetailList.getTotalAmount().equals("")) {
				amount_Int = Integer.valueOf(orderDetailList.getTotalAmount());
			}
			
			if (orderDetailList.getDiscount_amount() != null && !orderDetailList.getDiscount_amount().equals("")) {
				discount_Int = Integer.valueOf(orderDetailList.getDiscount_amount());
			}
			
			String price  = nf.format(price_Int);
			String amount  = nf.format(amount_Int);
			String discount = nf.format(discount_Int);
			
			Integer total = amount_Int - discount_Int;
			String total_needToPay = nf.format(total);
			
		txt_seat_qty.setText(orderDetailList.getTicketQty()+"");
		txt_price.setText(price+"");
		txt_amount.setText(amount+"");
		txt_discount.setText(discount+"");
		txt_amount_needToPay.setText(total_needToPay+"");
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
