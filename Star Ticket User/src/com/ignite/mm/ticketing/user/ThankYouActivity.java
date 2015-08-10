package com.ignite.mm.ticketing.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;

public class ThankYouActivity extends BaseActivity{
	private TextView txt_thankYou;
	private TextView txt_email_send;
	private TextView btn_continue_buy;
	private String payment_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanks);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			payment_type = bundle.getString("payment_type");
		}
		
		txt_thankYou = (TextView)findViewById(R.id.txt_thankyou);
		txt_email_send = (TextView)findViewById(R.id.txt_email_send);
		btn_continue_buy = (TextView)findViewById(R.id.btn_continue_buy);
		
		if (payment_type.equals("Cash on Shop")) {
			txt_thankYou.setText("Booking မွာၿပီးပါၿပီ  ။ (၂) နာရီ အတြင္း  နီးစပ္ရာ Convenience Store တြင္ ေငြေပးေခ်ပါ။ သုိ႔မဟုတ္ပါက သင္၏ booking ပ်က္သြားပါလိမ့္မည္။");
		}else if (payment_type.equals("Pay with Online")) {
			txt_thankYou.setText("လက္ မွတ္ ျဖတ္ ၿပီး ပါ ၿပီ။  ");
		}else if (payment_type.equals("Cash on Delivery")) {
			txt_thankYou.setText("လက္ မွတ္ ျဖတ္ ၿပီး ပါ ၿပီ။  ");
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		closeAllActivities();
		startActivity(new Intent(ThankYouActivity.this, SaleTicketActivity.class));
	}
}
