
package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.ignite.mdm.ticketing.sqlite.database.model.TourBooking;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.smk.skalertmessage.SKToastMessage;
import java.text.NumberFormat;

public class TourConfirmActivity extends BaseSherlockActivity{
	private TextView txt_price;
	private TextView txt_minus;
	private TextView txt_total;
	private TextView txt_plus;
	private Button btn_confirm;
	private TextView txt_qty;
	private Integer passengerQty = 1;
	private TourBooking selectedBooking;
	private Double totalPrice;
	private NumberFormat priceFormatter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_confirm);
		
		selectedBooking = (TourBooking) getIntent().getSerializableExtra("tourbooking");
		
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		
		if (toolbar != null) {
			toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
			this.setSupportActionBar(toolbar);
		}
		
		txt_price = (TextView)findViewById(R.id.txt_price);
		txt_minus = (TextView)findViewById(R.id.txt_minus);
		txt_qty = (TextView)findViewById(R.id.txt_qty);
		txt_plus = (TextView)findViewById(R.id.txt_plus);
		txt_total = (TextView)findViewById(R.id.txt_total);
		btn_confirm = (Button)findViewById(R.id.btn_confirm);
		
		//Price Format
		priceFormatter = NumberFormat.getInstance();
		
		if (selectedBooking != null) {
			txt_price.setText(priceFormatter.format(selectedBooking.getPrice()));
		}
		
		txt_minus.setOnClickListener(clickListener);
		txt_plus.setOnClickListener(clickListener);
		btn_confirm.setOnClickListener(clickListener);
		
		txt_qty.setText(passengerQty+"");
		totalPrice();
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == txt_minus) {
				if (passengerQty > 1) {
					passengerQty--;
					txt_qty.setText(passengerQty+"");
					totalPrice();
				}
			}
			if (v == txt_plus) {
				passengerQty++;
				txt_qty.setText(passengerQty+"");
				totalPrice();
			}
			if (v == btn_confirm) {
				closeAllActivities();
				SKToastMessage.showMessage(getBaseContext(), getResources().getString(R.string.strmm_book_confirm_success), SKToastMessage.SUCCESS);
			}
		}
	};
	
	private void totalPrice() {
		// TODO Auto-generated method stub
		totalPrice = passengerQty * selectedBooking.getPrice();
		txt_total.setText("= "+priceFormatter.format(totalPrice)+" Ks");
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
