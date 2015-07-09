package com.ignite.mm.ticketing.user;

import java.text.NumberFormat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;

public class OrderDetailActivity extends BaseSherlockActivity{

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
	private TextView txt_point;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_item_threeday_sales);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
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
		actionBarTitle.setText("Order စာရင္း  အေသးစိတ္");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			orderString = extras.getString("order_detail");
		}
		
		orderDetailList = new Gson().fromJson(orderString, ThreeDaySale.class);
		
		 txt_order_no = (TextView)  findViewById(R.id.txt_order_no);
		 txt_buy_date = (TextView)  findViewById(R.id.txt_buy_date);
		 txt_trip = (TextView)  findViewById(R.id.txt_trip);
		 txt_trip_date_time = (TextView)  findViewById(R.id.txt_trip_date_time);
		 txt_operator = (TextView)  findViewById(R.id.txt_operator);
		 txt_bus_class = (TextView)  findViewById(R.id.txt_bus_class);
		 txt_seats = (TextView)  findViewById(R.id.txt_seats);
		 txt_price = (TextView)  findViewById(R.id.txt_price);
		 txt_seat_qty = (TextView)  findViewById(R.id.txt_seat_qty);
		 txt_amount = (TextView)  findViewById(R.id.txt_amount);
		 txt_point = (TextView)  findViewById(R.id.txt_point);
		 
		txt_order_no.setText("Order နံပါတ္  : "+orderDetailList.getOrderId());
		txt_buy_date.setText(orderDetailList.getDate());
		txt_trip.setText(orderDetailList.getTrip());
		txt_trip_date_time.setText(changeDate(orderDetailList.getDepartureDate())+" - "+orderDetailList.getTime());
		txt_operator.setText(orderDetailList.getOperator());
		txt_bus_class.setText(orderDetailList.getClass_());
		txt_seats.setText(orderDetailList.getSeatNo());
		//txt_point.setText(text);
		
		//Change (0,000,000) format
			NumberFormat nf = NumberFormat.getInstance();
			String price = nf.format(Integer.valueOf(orderDetailList.getPrice()));
			String amount = nf.format(Integer.valueOf(orderDetailList.getTotalAmount()));
				
		txt_price.setText(price+" Ks");
		txt_seat_qty.setText(orderDetailList.getTicketQty()+"");
		txt_amount.setText(amount+" Ks");
		
	}
}
