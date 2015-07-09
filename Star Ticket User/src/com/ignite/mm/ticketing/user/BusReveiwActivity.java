package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.BusProfileAdapter;

public class BusReveiwActivity extends BaseSherlockActivity{
	private ListView lv_bus_profile;
	private List<String> list;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private LinearLayout layout_bus_review;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setText("ဘတ္ (စ္) ကား Info");
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
		
		setContentView(R.layout.activity_bus_review);
		
		layout_bus_review = (LinearLayout)findViewById(R.id.layout_bus_review);
		lv_bus_profile = (ListView)findViewById(R.id.lv_bus_profile);
		
		list = new ArrayList<String>();
		list.add("မႏၱလာမင္း");
		list.add("Elite");
		list.add("ေရႊမႏၱလာ");
		list.add("မုိးေကာင္းကင္");
		list.add("Asia Express ");
		list.add("အာကာသ");
		list.add("တုိးရတနာ");
		
		lv_bus_profile.setAdapter(new BusProfileAdapter(BusReveiwActivity.this, list));
		lv_bus_profile.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(BusReveiwActivity.this, BusReviewDetailActivity.class));
			}
		});
	}
	
}
