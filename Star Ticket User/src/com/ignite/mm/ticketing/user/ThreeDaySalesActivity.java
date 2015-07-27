package com.ignite.mm.ticketing.user;

import java.text.NumberFormat;
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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.ThreeDaySalesLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.smk.sklistview.SKListView;
import com.smk.sklistview.SKListView.Callbacks;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class ThreeDaySalesActivity extends BaseActivity{

	private String intents = "";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private SKListView lv_threeday_sales;
	private ZProgressHUD dialog;
	private List<ThreeDaySale> lst_threeday_sale;
	private SKConnectionDetector skDetector;
	private TextView txt_total_tickets;
	private TextView txt_total_amount;
	private TextView txt_total_points;
	private int offset = 1;
	private int limit = 12;
	protected boolean isLoading = false;
	private ThreeDaySalesLvAdapter threeDaySalesLvAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_threeday_sales);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Order စာရင္း ");
            this.setSupportActionBar(toolbar);
        }
		
		lv_threeday_sales = (SKListView)findViewById(R.id.lst_threeday_sales);	
		lv_threeday_sales.setOnItemClickListener(clickListener);
		
		txt_total_tickets = (TextView)findViewById(R.id.txt_total_tickets);
		txt_total_amount = (TextView)findViewById(R.id.txt_total_amount);
		txt_total_points = (TextView)findViewById(R.id.txt_total_points);
		
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			
			offset = 1;
			
			dialog = new ZProgressHUD(ThreeDaySalesActivity.this);
			dialog.show();
			
			getThreeDaySales();
		}else{
			skDetector.showErrorMessage();
		}
		
		lv_threeday_sales.setCallbacks(callbacks);
		lst_threeday_sale = new ArrayList<ThreeDaySale>();
		threeDaySalesLvAdapter = new ThreeDaySalesLvAdapter(this, lst_threeday_sale);
		lv_threeday_sales.setAdapter(threeDaySalesLvAdapter);
		lv_threeday_sales.setDividerHeight(0);
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}

	/**
	 *  Get Sales Reports for 3 days
	 */
	private void getThreeDaySales() {
		// TODO Auto-generated method stub
		isLoading = true;
		
		Log.i("", "Access Token: "+AppLoginUser.getAccessToken()+"Code No: "+AppLoginUser.getCodeNo());
		
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().getThreeDaySales(AppLoginUser.getAccessToken(), AppLoginUser.getCodeNo(), String.valueOf(offset)
				, String.valueOf(limit), new Callback<List<ThreeDaySale>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					Log.e("", "Error URL: "+arg0.getUrl());
					SKToastMessage.showMessage(ThreeDaySalesActivity.this, "Server တစ္ခုခု ခ်ိဳ႕ ယြင္း ေနပါသည္", SKToastMessage.ERROR);
					//showAlert("Server တစ္ခုခု ခ်ိဳ႕ ယြင္း ေနပါသည္");
				}
				
				dialog.dismissWithFailure();
			}

			public void success(List<ThreeDaySale> arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "Success Three Day sales");
				
				if (arg0 != null && arg0.size() > 0) {
					
					Log.i("", "not null "+arg0.toString());
					//lst_threeday_sale = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<ThreeDaySale>>(){}.getType());
					
					lst_threeday_sale.addAll(arg0);
						
						Log.i("", "Three Day Sale List: "+lst_threeday_sale.toString());
						
						threeDaySalesLvAdapter.notifyDataSetChanged();
						
						totalValues();
						
						if(arg0.size() == limit){
							
							Log.i("", "enter here : loading false! "+arg0.size());
							
							offset++;
							lv_threeday_sales.setNextPage(true);
							isLoading = false;
						}else{
							Log.i("", "enter here : loading true! "+arg0.size());
							
							lv_threeday_sales.setNextPage(false);
						}
						
				}else {
					SKToastMessage.showMessage(ThreeDaySalesActivity.this, "No list", SKToastMessage.INFO);
					lv_threeday_sales.setNextPage(false);
				}
				
				dialog.dismissWithSuccess();
			}
		});		
	}
	
	private Callbacks callbacks = new Callbacks() {
		
		public void onScrollChanged(int scrollY) {
			// TODO Auto-generated method stub
			//.....
		}

		public void onNextPageRequest() {
			// TODO Auto-generated method stub
			if(skDetector.isConnectingToInternet()){
				if(!isLoading){
					//Toast.makeText(ThreeDaySalesActivity.this, "Next Request", Toast.LENGTH_LONG).show();
					getThreeDaySales();
				}
			}
		}

		public void onScrollState(int scrollSate) {
			// TODO Auto-generated method stub
			//.....
		}
	};
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (position < lst_threeday_sale.size()) {
				startActivity(new Intent(ThreeDaySalesActivity.this, OrderDetailActivity.class).putExtra("order_detail"
						, new Gson().toJson(lst_threeday_sale.get(position))));
			}
		}
	};
	
	private void totalValues() {
		// TODO Auto-generated method stub
		Integer total_ticket = 0;
		Integer total_amount = 0;
		
		if (lst_threeday_sale != null && lst_threeday_sale.size() > 0) {
			for (int i = 0; i < lst_threeday_sale.size(); i++) {
				total_ticket += lst_threeday_sale.get(i).getTicketQty(); 
				total_amount += Integer.valueOf(lst_threeday_sale.get(i).getTotalAmount());
				//total_point += lst_threeday_sale.get(i).ge;
			}
		}
		
		//Change (0,000,000) format
		NumberFormat nf = NumberFormat.getInstance();
		String amount = nf.format(total_amount);
				
		txt_total_tickets.setText(total_ticket+"");
		txt_total_amount.setText(amount+"");
	}
}
