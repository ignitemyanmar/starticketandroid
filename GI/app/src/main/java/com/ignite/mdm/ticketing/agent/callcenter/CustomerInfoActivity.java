package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.smk.skalertmessage.SKToastMessage;

public class CustomerInfoActivity extends BaseSherlockActivity{
	private Button btn_book;
	private Toolbar toolbar;
	private TextView txt_wifi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customerinfo);
		
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		
		if (toolbar != null) {
			toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
			setSupportActionBar(toolbar);
		}
		
		btn_book = (Button)findViewById(R.id.btn_book);
		txt_wifi = (TextView)findViewById(R.id.txt_wifi);
		
		btn_book.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SKToastMessage.showMessage(getBaseContext(), getResources().getString(R.string.strmm_booking_success), SKToastMessage.SUCCESS);
				closeAllActivities();
				//startActivity(new Intent(getBaseContext(), SaleTicketActivity.class));
			}
		});
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
