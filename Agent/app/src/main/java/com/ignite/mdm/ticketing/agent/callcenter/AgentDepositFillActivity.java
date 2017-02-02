package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentPayment;
import com.ignite.mdm.ticketing.sqlite.database.model.GetAgentBalanceRequest;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AgentDepositFillActivity extends BaseSherlockActivity implements View.OnClickListener{
	private SharedPreferences sharedPreferences;

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
		sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);


	String permit_access_token = 	sharedPreferences.getString("operator_token","");
		String permit_ip = 	sharedPreferences.getString("permit_ip","http://www.starticket.com");

		String json = new GetAgentBalanceRequest(permit_access_token,
				sharedPreferences.getString("code_no", null)).toJson();

		String param = MCrypt.getInstance().encrypt(json);

		mLinearLayoutCbOddPayment = (LinearLayout)findViewById(R.id.linear_layout_pay_with_cb_pay);
		mLinearLayoutCbOddPayment.setOnClickListener(this);
		getAgentAmountBalance(permit_ip, param);

	
	}

	ProgressDialog dialog;

	private void getAgentAmountBalance(String base_url, String param) {

		Log.e("TAG", "~~ call getAgentAmountBalance ~~");
		dialog = ProgressDialog.show(AgentDepositFillActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		NetworkEngine.setIP(base_url);
		NetworkEngine.getInstance().getAgentBalance(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (dialog != null) {
					dialog.dismiss();
				}

				Toast.makeText(AgentDepositFillActivity.this, "Can't connect to server!", Toast.LENGTH_SHORT).show();
				Log.e("TAG", " ~ refotft File " + arg0.getLocalizedMessage());
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				AgentPayment payment =
						DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AgentPayment>() {
						}.getType());

				dialog.dismiss();

				int balance = payment.getTotal_balance();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putInt("balance", balance).apply();

				int comission = payment.getTotal_commission();
				String paidAmount = payment.getTotal_paidamount();
				int soldamout = payment.getTotal_soldamount();
				((TextView)findViewById(R.id.balance)).setText("Your Amount Balance is  "+balance);
				if (balance <= 0) {
					showAlert("Your balance is low! \n Please refill");
				}


			}
		});
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
