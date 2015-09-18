package com.ignite.mm.ticketing.agent.callcenter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusOperatorGridAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.SalebytripdatesLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.Salebytripdate;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class SaleByTripDateActivity extends BaseSherlockActivity{

	private String intents = "";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private ProgressDialog dialog;
	private List<Salebytripdate> lst_sale_by_tripDate;
	private SKConnectionDetector skDetector;
	private ListView lv_sale_byTripDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sale_by_trip_date);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
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
		actionBarTitle.setText("Trip Date အလုိက္  အေရာင္းစာရင္း");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		lv_sale_byTripDate = (ListView)findViewById(R.id.lst_sale_byTripDate);	
		
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			getSaleByTripDate();
		}else{
			skDetector.showErrorMessage();
		}
	}
	
	private void getSaleByTripDate() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(SaleByTripDateActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		Log.i("", "Access Token: "+AppLoginUser.getAccessToken()+"Code No: "+AppLoginUser.getCodeNo());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getSaleByTripDate(AppLoginUser.getAccessToken(), AppLoginUser.getCodeNo(), 
				new Callback<List<Salebytripdate>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					Log.e("", "Error URL: "+arg0.getUrl());
					showAlert("Something's Wrong in Server!");
				}
				
				dialog.dismiss();
			}

			public void success(List<Salebytripdate> arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				if (arg0 != null) {
					
					Log.i("", "Sale list by Trip Date: "+arg0.toString());
					//lst_threeday_sale = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Salebytripdate>>(){}.getType());
					
					lst_sale_by_tripDate = arg0;
					
					if (lst_sale_by_tripDate != null && lst_sale_by_tripDate.size() > 0) {
						
						for (int i = 0; i < lst_sale_by_tripDate.size(); i++) {
							Salebytripdate sale = (Salebytripdate)lst_sale_by_tripDate.get(i);
							sale.setDepartureDate(changeDate(lst_sale_by_tripDate.get(i).getDepartureDate()));
						}
						
						lv_sale_byTripDate.setAdapter(new SalebytripdatesLvAdapter(SaleByTripDateActivity.this, lst_sale_by_tripDate));
						lv_sale_byTripDate.setDividerHeight(0);
					}else {
						showAlert("No Sale!");
					}
				}else {
					showAlert("No Sale!");
				}
				
				dialog.dismiss();
			}
		});		
	}
}

