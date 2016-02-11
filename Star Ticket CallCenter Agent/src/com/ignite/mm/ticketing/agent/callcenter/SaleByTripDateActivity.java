package com.ignite.mm.ticketing.agent.callcenter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusOperatorGridAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.SalebytripdatesLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.KhoneAtList;
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
	private List<KhoneAtList> lst_sale_by_tripDate;
	private SKConnectionDetector skDetector;
	private ListView lv_sale_byTripDate;
	private Spinner spn_operator;
	private Button btn_search;
	protected List<Operator> list_operator;
	protected String selectedOperatorId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sale_by_trip_date);
		
/*		actionBar = getSupportActionBar();
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
		actionBarTitle.setText("ခံုအပ္ရမည့္ စာရင္း");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("ခံုအပ္ရမည့္ စာရင္း");
            this.setSupportActionBar(toolbar);
        }
        
		lv_sale_byTripDate = (ListView)findViewById(R.id.lst_sale_byTripDate);	
		spn_operator = (Spinner)findViewById(R.id.spn_operator);
		spn_operator.setOnItemSelectedListener(onItemSelectedListener);
		
		btn_search = (Button)findViewById(R.id.btn_search);
		
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		
		btn_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(skDetector.isConnectingToInternet()){
					getKhoneatFilter();
				}else{
					skDetector.showErrorMessage();
				}
			}
		});
		
		if(skDetector.isConnectingToInternet()){
			getSaleByTripDate();
		}else{
			skDetector.showErrorMessage();
		}
	}
	
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			selectedOperatorId = list_operator.get(position).getCli_operator_id();
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	protected SalebytripdatesLvAdapter khoneAtAdapter;
	
	private void getSaleByTripDate() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(SaleByTripDateActivity.this, "", "Please Wait...", true);
		dialog.setCancelable(true);
		
		Log.i("", "Access Token: "+AppLoginUser.getAccessToken()+"Code No: "+AppLoginUser.getCodeNo());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getKhoneAtList("",
				new Callback<List<KhoneAtList>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					Log.e("", "Error URL: "+arg0.getUrl()+", cause: "+arg0.getCause());
					showAlert("Something's Wrong in Server!");
				}
				
				dialog.dismiss();
			}

			public void success(List<KhoneAtList> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					Log.i("", "Sale list by Trip Date: "+arg0.toString());
					//lst_threeday_sale = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Salebytripdate>>(){}.getType());
					
					lst_sale_by_tripDate = arg0;
					
					if (lst_sale_by_tripDate != null && lst_sale_by_tripDate.size() > 0) {
						
						for (int i = 0; i < lst_sale_by_tripDate.size(); i++) {
							KhoneAtList sale = (KhoneAtList)lst_sale_by_tripDate.get(i);
							sale.setDate(changeDate(lst_sale_by_tripDate.get(i).getDate()));
							sale.setDepartureDate(changeDate(lst_sale_by_tripDate.get(i).getDepartureDate()));
						}
					
						khoneAtAdapter = new SalebytripdatesLvAdapter(SaleByTripDateActivity.this, lst_sale_by_tripDate);
						lv_sale_byTripDate.setAdapter(khoneAtAdapter);
						lv_sale_byTripDate.setDividerHeight(0);
						
						getOperators();
					}else {
						lv_sale_byTripDate.setAdapter(null);
						showAlert("No List!");
					}
				}else {
					lv_sale_byTripDate.setAdapter(null);
					showAlert("No List!");
				}
				
				//dialog.dismiss();
			}
		});		
	}
	
	private void getKhoneatFilter() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(SaleByTripDateActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		Log.i("", "Access Token: "+AppLoginUser.getAccessToken()+"Code No: "+AppLoginUser.getCodeNo());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getKhoneAtList(selectedOperatorId,
				new Callback<List<KhoneAtList>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					Log.e("", "Error URL: "+arg0.getUrl()+", cause: "+arg0.getCause());
					showAlert("Something's Wrong in Server!");
				}
				
				dialog.dismiss();
			}

			public void success(List<KhoneAtList> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					Log.i("", "Sale list by Trip Date: "+arg0.toString());
					//lst_threeday_sale = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Salebytripdate>>(){}.getType());
					
					lst_sale_by_tripDate = arg0;
					
					if (lst_sale_by_tripDate != null && lst_sale_by_tripDate.size() > 0) {
						
						for (int i = 0; i < lst_sale_by_tripDate.size(); i++) {
							KhoneAtList sale = (KhoneAtList)lst_sale_by_tripDate.get(i);
							sale.setDate(changeDate(lst_sale_by_tripDate.get(i).getDate()));
							sale.setDepartureDate(changeDate(lst_sale_by_tripDate.get(i).getDepartureDate()));
						}
					
						khoneAtAdapter = new SalebytripdatesLvAdapter(SaleByTripDateActivity.this, lst_sale_by_tripDate);
						lv_sale_byTripDate.setAdapter(khoneAtAdapter);
						lv_sale_byTripDate.setDividerHeight(0);
						
					}else {
						lv_sale_byTripDate.setAdapter(null);
						showAlert("No List!");
					}
				}else {
					lv_sale_byTripDate.setAdapter(null);
					showAlert("No List!");
				}
				
				dialog.dismiss();
			}
		});		
	}
	
	private void getOperators() {
		// TODO Auto-generated method stub
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getAllOperator(AppLoginUser.getAccessToken(), new Callback<List<Operator>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getCause());
				}
				
				dialog.dismiss();
			}

			public void success(List<Operator> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					list_operator = arg0;
					
					if (list_operator != null && list_operator.size() > 0) {
					
						  OperatorAdapter operatorAdapter = new OperatorAdapter(SaleByTripDateActivity.this, list_operator);
						  spn_operator.setAdapter(operatorAdapter);
						  
					}else {
						Toast.makeText(SaleByTripDateActivity.this, "No Operator!", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(SaleByTripDateActivity.this, "No Operator!", Toast.LENGTH_SHORT).show();
				}
				
				dialog.dismiss();
			}
		});
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}

