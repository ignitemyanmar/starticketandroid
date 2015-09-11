package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.BusReviewDetailAdapter;

/**
 * {@link #BusReviewDetailActivity} is not included in v1.0
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class BusReviewDetailActivity extends BaseActivity{

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
		
		setContentView(R.layout.activity_bus_review_detail);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Reviews");
            /*toolbar.setTitle(bundle.getString("from_to")+" ["+bundle.getString("Operator_Name")+"] "
            					+bundle.getString("date")+" ["+bundle.getString("time")+"] "
            					+bundle.getString("classes"));*/
            this.setSupportActionBar(toolbar);
        }
		
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
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
