package com.ignite.mm.ticketing.user;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ignite.mm.ticketing.application.BaseActivity;

public class WriteReviewActivity extends BaseActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private RatingBar rateBar_operator;
	private TextView txt_rate_message;
	private EditText edt_customer_comment;
	private Button btn_submit_review;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setText("Review ေရးမည္");
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
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);	
		
		setContentView(R.layout.activity_write_review);
		
		txt_rate_message = (TextView)findViewById(R.id.txt_rate_message);
		edt_customer_comment = (EditText)findViewById(R.id.edt_customer_comment);
		
		rateBar_operator = (RatingBar)findViewById(R.id.rateBar_operator);
		rateBar_operator.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (rating == 0.5 || rating == 1.0) {
					//txt_rate_message.setText("Avoid");
					txt_rate_message.setText("Service မဆုိးပါဘူး ");
				}
				if (rating == 1.5 || rating == 2.0) {
					//txt_rate_message.setText("Needs Improve");
					txt_rate_message.setText("Service လုိေသးတယ္  ");
				}
				if (rating == 2.5 || rating == 3.0) {
					//txt_rate_message.setText("Good");
					txt_rate_message.setText("Service ေကာင္းတယ္");
				}
				if (rating == 3.5 || rating == 4.0) {
					//txt_rate_message.setText("Super");
					txt_rate_message.setText("Service အရမ္းေကာင္းတယ္");
				}
				if (rating == 4.5 || rating == 5.0) {
					//txt_rate_message.setText("Unforgettable");
					txt_rate_message.setText("Service အေကာင္းဆံုးပါပဲ ");
				}
			}
		});
		
		btn_submit_review = (Button)findViewById(R.id.btn_submit_review);
		
		//addListenerOnRatingBar();
		
		btn_submit_review.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edt_customer_comment.getText().toString().length() < 20) {
					Toast.makeText(WriteReviewActivity.this, "ေက်းဇူးျပဳ၍ အနည္းဆံုး စာလံဳးေရ (၂၀) ေရးပါ",
										Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(WriteReviewActivity.this, String.valueOf(rateBar_operator.getRating()+"/5")+" "
							+txt_rate_message.getText().toString()+" "
							+edt_customer_comment.getText().toString(),
										Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	  public void addListenerOnRatingBar() {
		  
			//if rating value is changed,
			//display the current rating value in the result (textview) automatically
			rateBar_operator.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
		 
					txt_rate_message.setText(String.valueOf(rating));
		 
				}
			});
		  }
}
