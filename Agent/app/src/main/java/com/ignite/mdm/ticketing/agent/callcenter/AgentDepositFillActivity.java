package com.ignite.mdm.ticketing.agent.callcenter;

import com.ignite.mm.ticketing.application.BaseSherlockActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AgentDepositFillActivity extends BaseSherlockActivity implements View.OnClickListener{
	
	private LinearLayout mLinearLayoutCbOddPayment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent_deposit_fill);
		
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		if(toolbar != null){
			this.setSupportActionBar(toolbar);
			getSupportActionBar().setTitle("Agent Deposit");
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		mLinearLayoutCbOddPayment = (LinearLayout)findViewById(R.id.linear_layout_pay_with_cb_pay);
		mLinearLayoutCbOddPayment.setOnClickListener(this);
	
	}
	
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linear_layout_pay_with_cb_pay:
			
			Intent oddPayment = new Intent(this,CbOddPaymentActivity.class);
			startActivity(oddPayment);
			
			break;

		default:
			throw new UnsupportedOperationException("Invalid View Id : "+v.getId());
		}
	}
	

  @Override
public Intent getSupportParentActivityIntent() {
	// TODO Auto-generated method stub
	  finish();
	return super.getSupportParentActivityIntent();
}
}
