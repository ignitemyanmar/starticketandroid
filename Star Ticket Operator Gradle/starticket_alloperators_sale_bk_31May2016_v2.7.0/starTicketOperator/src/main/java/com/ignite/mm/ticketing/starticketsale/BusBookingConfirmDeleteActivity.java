package com.ignite.mm.ticketing.starticketsale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.starticketsale.R;
import com.ignite.mm.ticketing.application.BaseActionBarActivity;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;

public class BusBookingConfirmDeleteActivity extends BaseActionBarActivity {
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private Button btn_pay;
	private Button btn_cancel_order;
	private Button btn_back;
	private String creditOrderString;
	private CreditOrder creditOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_pay_delete);
		
		btn_pay = (Button) findViewById(R.id.btn_pay);
		btn_cancel_order = (Button) findViewById(R.id.btn_cancel);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		btn_pay.setOnClickListener(clickListener);
		btn_cancel_order.setOnClickListener(clickListener);
		btn_back.setOnClickListener(clickListener);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(creditOrderString != null){
			finish();
		}else{
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
			   creditOrderString = extras.getString("credit_order");
			}
			creditOrder = new Gson().fromJson(creditOrderString, CreditOrder.class);
		}
	}
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			
			if(v == btn_pay){
				Intent nextScreen = new Intent(BusBookingConfirmDeleteActivity.this,BusConfirmActivity.class);
				String SeatLists = "";
				for(int i=0; i<creditOrder.getSaleitems().size(); i++){
					SeatLists += creditOrder.getSaleitems().get(i).getSeatNo()+",";
				}
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "booking");
				bundle.putString("from_to", creditOrder.getTrip());
				bundle.putString("time", creditOrder.getTime());
				bundle.putString("classes",creditOrder.getClasses());
				bundle.putString("date", creditOrder.getDate());
				bundle.putString("agent_id", creditOrder.getAgentId().toString());
				bundle.putString("name", creditOrder.getCustomer());
				bundle.putString("phone", creditOrder.getPhone());
				bundle.putString("selected_seat",  SeatLists);
				bundle.putString("sale_order_no", creditOrder.getId().toString());
				bundle.putString("bus_occurence", creditOrder.getSaleitems().get(0).getTripId().toString());
				nextScreen.putExtras(bundle);
				startActivity(nextScreen);
			}
			
			if(v == btn_cancel_order){
				Intent intent = new Intent(getApplicationContext(), BusBookingDetailActivity.class);
				intent.putExtra("credit_order", new Gson().toJson(creditOrder));
				startActivity(intent);
			}
			
			if(v == btn_back){
				finish();
			}
		}
	};
}
