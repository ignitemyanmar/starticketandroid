package com.ignite.mm.ticketing.starticket;

import info.hoang8f.widget.FButton;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

/**
 * {@link #WriteReviewActivity} not include in v1.0
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class WriteReviewActivity extends BaseActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private RatingBar rateBar_operator;
	private TextView txt_rate_message;
	private EditText edt_customer_comment;
	private FButton btn_submit_review;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_write_review);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Review ေရးမည္");
            this.setSupportActionBar(toolbar);
        }
		
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
					txt_rate_message.setText("Service လုိေသးတယ္   ");
				}
				if (rating == 2.5 || rating == 3.0) {
					//txt_rate_message.setText("Good");
					txt_rate_message.setText("Service ေကာင္းတယ္");
				}
				if (rating == 3.5 || rating == 4.0) {
					//txt_rate_message.setText("Super");
					txt_rate_message.setText("Service အရမ္းေကာင္း တယ္");
				}
				if (rating == 4.5 || rating == 5.0) {
					//txt_rate_message.setText("Unforgettable");
					txt_rate_message.setText("Service အေကာင္းဆံုးပါပဲ ");
				}
			}
		});
		
		btn_submit_review = (FButton)findViewById(R.id.btn_submit_review);
		btn_submit_review.setButtonColor(getResources().getColor(R.color.yellow));
		btn_submit_review.setShadowEnabled(true);
		btn_submit_review.setShadowHeight(3);
		btn_submit_review.setCornerRadius(7);
		
		//addListenerOnRatingBar();
		
		btn_submit_review.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edt_customer_comment.getText().toString().length() < 20) {
					Toast.makeText(WriteReviewActivity.this, "ေက်းဇူးျပဳ၍ အနည္းဆံုး စာလံုးေရ (20) ေရးပါ ",
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
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
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
