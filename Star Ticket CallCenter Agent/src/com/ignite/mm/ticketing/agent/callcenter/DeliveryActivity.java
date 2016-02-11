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
import android.widget.Toast;

import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.DeliveryListViewAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.ThreeDaySalesLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Delivery;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class DeliveryActivity extends BaseSherlockActivity{

	private ListView lv_delivery;
	private List<Delivery> deliveryList;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private SKConnectionDetector skDetector;
	private ProgressDialog dialog;
	private List<Delivery> lst_delivery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_delivery);
		
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
		actionBarTitle.setText(getResources().getString(R.string.str_delivery_list));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		lv_delivery = (ListView)findViewById(R.id.lv_delivery);
		
		skDetector = SKConnectionDetector.getInstance(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(skDetector.isConnectingToInternet()){
			getDelivery();
		}else{
			Toast.makeText(DeliveryActivity.this, "Not Available Network!", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Get Delivery List to send to customers. 
	 * Ascending by Departure Dates
	 */
	private void getDelivery() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(DeliveryActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		Log.i("", "Access Token: "+AppLoginUser.getAccessToken());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getDeliveryList(AppLoginUser.getAccessToken(), new Callback<List<Delivery>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					//Log.e("", "Error URL: "+arg0.getUrl());
					showAlert("Something's Wrong in Server!");
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}

			public void success(List<Delivery> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					Log.i("", "delivery list "+arg0.toString());
					
					lst_delivery = arg0;
					
					if (lst_delivery != null && lst_delivery.size() > 0) {
						
						/*for (int i = 0; i < lst_delivery.size(); i++) {
							ThreeDaySale sale = (ThreeDaySale)lst_delivery.get(i);
							sale.setDepartureDate(changeDate(lst_threeday_sale.get(i).getDepartureDate()));
						}*/
						
						lv_delivery.setAdapter(new DeliveryListViewAdapter(DeliveryActivity.this, lst_delivery, AppLoginUser.getAccessToken()));
						lv_delivery.setDividerHeight(0);
					}else {
						showAlert("No Delivery List!");
					}
				}else {
					showAlert("No Delivery List!");
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
	}
}
