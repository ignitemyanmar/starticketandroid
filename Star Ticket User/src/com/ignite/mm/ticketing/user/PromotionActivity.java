package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.PromotionAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Promotion;

public class PromotionActivity extends BaseActivity{
	private ListView lv_promotion;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private RelativeLayout layout_noPromotion;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
/*		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setText("ပ႐ုိမိုရွင္း");
		//actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/
		
		setContentView(R.layout.activity_promotion);
		
		//Get Floating Menu from Base Activity
		getFloatingMenu();	
		
		dialog = ProgressDialog.show(PromotionActivity.this, "", "Please wait ...", true);
		
		layout_noPromotion = (RelativeLayout)findViewById(R.id.layout_noPromotion);
		lv_promotion = (ListView)findViewById(R.id.lv_promotion);
		lv_promotion.setDividerHeight(0);
		
		List<String> listOperatorPromo = new ArrayList<String>();
		listOperatorPromo.add("မႏၱလာမင္း");
		listOperatorPromo.add("Elite");
		listOperatorPromo.add("ေရႊမႏၱလာ");
		/*listOperatorPromo.add("မုိးေကာင္းကင္");
		listOperatorPromo.add("Asia Express ");
		listOperatorPromo.add("အာကာသ");
		listOperatorPromo.add("တုိးရတနာ");*/
		
		NetworkEngine.getInstance().GetPromotion(new Callback<List<Promotion>>() {
			
			public void success(List<Promotion> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.size() > 0) {
					lv_promotion.setAdapter(new PromotionAdapter(PromotionActivity.this, arg0));
				}else {
					layout_noPromotion.setVisibility(View.VISIBLE);
					lv_promotion.setAdapter(null);
				}
				
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Promo Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}
		});
	}	
}
