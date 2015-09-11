package com.ignite.mm.ticketing.starticket;

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

/**
 * {@link #BusReveiwActivity} is not included in v1.0
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
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
            toolbar.setTitle(R.string.str_bus_info+"");
            this.setSupportActionBar(toolbar);
        }
		
		layout_bus_review = (LinearLayout)findViewById(R.id.layout_bus_review);
		lv_bus_profile = (ListView)findViewById(R.id.lv_bus_profile);
		
		list = new ArrayList<String>();
		list.add(R.string.str_mandalarmin+"");
		list.add("Elite");
		list.add(R.string.str_shwemandalar+"");
		list.add(R.string.str_moekaungkin+"");
		list.add("Asia Express ");
		list.add(R.string.str_arkartha+"");
		list.add(R.string.str_toeyadanar+"");
		
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
