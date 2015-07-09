package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.BusReviewDetailAdapter;

public class BusReviewDetailActivity extends BaseSherlockActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private ListView lv_review_comments;
	private Button btn_write_review;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setText("Reviews");
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
		
		setContentView(R.layout.activity_bus_review_detail);
		
		//rateBar_operator = (RatingBar)findViewById(R.id.rateBar_operator);
		btn_write_review = (Button)findViewById(R.id.btn_write_review);
		
		List<String> listComments = new ArrayList<String>();
		listComments.add("Mya Mya");
		listComments.add("Hla Hla");
		listComments.add("Maung Maung");
		listComments.add("Khin Khin");
		listComments.add("Soe Soe");
		listComments.add("Myo Myo");
		listComments.add("Aye Aye");
		
		lv_review_comments = (ListView)findViewById(R.id.lv_review_comments);
		lv_review_comments.setAdapter(new BusReviewDetailAdapter(BusReviewDetailActivity.this, listComments));
		lv_review_comments.setDividerHeight(0);
		
		btn_write_review.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(BusReviewDetailActivity.this, WriteReviewActivity.class));
			}
		});
	}
}
