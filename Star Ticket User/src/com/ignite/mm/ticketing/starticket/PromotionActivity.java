package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.PromotionAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Promotion;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class PromotionActivity extends BaseActivity{
	private ListView lv_promotion;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private RelativeLayout layout_noPromotion;
	private ZProgressHUD dialog;
	private SKConnectionDetector connectionDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_promotion);
		
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Promotion");
            this.setSupportActionBar(toolbar);
        }
		
		layout_noPromotion = (RelativeLayout)findViewById(R.id.layout_noPromotion);
		lv_promotion = (ListView)findViewById(R.id.lv_promotion);
		lv_promotion.setDividerHeight(0);
		
		List<String> listOperatorPromo = new ArrayList<String>();
		listOperatorPromo.add(R.string.str_mandalarmin+"");
		listOperatorPromo.add("Elite");
		listOperatorPromo.add(R.string.str_shwemandalar+"");
		/*listOperatorPromo.add("မုိးေကာင္းကင္");
		listOperatorPromo.add("Asia Express ");
		listOperatorPromo.add("အာကာသ");
		listOperatorPromo.add("�?ုိးရ�?နာ");*/
		
		connectionDetector = SKConnectionDetector.getInstance(this);
		if(connectionDetector.isConnectingToInternet()){
			getPromo();
		}else{
			Toast.makeText(PromotionActivity.this, "No Internet connection!", Toast.LENGTH_SHORT).show();
		}
		
	}	
	
	private void getPromo() {
		// TODO Auto-generated method stub
		
		dialog = new ZProgressHUD(PromotionActivity.this);
		dialog.show();
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().GetPromotion(new Callback<List<Promotion>>() {
			
			public void success(List<Promotion> arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "promo data: "+arg0.toString());
				
				if (arg0 != null && arg0.size() > 0) {
					lv_promotion.setAdapter(new PromotionAdapter(PromotionActivity.this, arg0));
				}else {
					layout_noPromotion.setVisibility(View.VISIBLE);
					lv_promotion.setAdapter(null);
				}
				
				dialog.dismissWithSuccess();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Promo Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismissWithFailure();
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
