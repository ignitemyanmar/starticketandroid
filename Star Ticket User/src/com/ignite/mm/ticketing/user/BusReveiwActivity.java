package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.BusProfileAdapter;

public class BusReveiwActivity extends BaseActivity{
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
		
		setContentView(R.layout.activity_bus_review);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("ဘတ္ (စ္) ကား Info");
            this.setSupportActionBar(toolbar);
        }
		
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
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
