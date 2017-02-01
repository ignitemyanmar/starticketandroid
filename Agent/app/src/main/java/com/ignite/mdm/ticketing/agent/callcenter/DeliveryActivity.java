package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.DeliveryListViewAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.Delivery;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DeliveryCompleteDialog;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
	private DeliveryListViewAdapter deliveryListViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_delivery);
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            this.setSupportActionBar(toolbar);
        }
        
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
		actionBarTitle.setText(getResources().getString(R.string.str_delivery_list));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/
		
		lv_delivery = (ListView)findViewById(R.id.lv_delivery);
		
		skDetector = SKConnectionDetector.getInstance(this);
		
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
						
						deliveryListViewAdapter = new DeliveryListViewAdapter(DeliveryActivity.this, lst_delivery, AppLoginUser.getAccessToken());
						deliveryListViewAdapter.setmCallbackDelivery(callBackDelivery);
						lv_delivery.setAdapter(deliveryListViewAdapter);
						lv_delivery.setDividerHeight(0);
					}else {
						lv_delivery.setAdapter(null);
						showAlert("No Delivery List!");
					}
				}else {
					lv_delivery.setAdapter(null);
					showAlert("No Delivery List!");
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
	}
	
	private DeliveryListViewAdapter.CallbackDelivery callBackDelivery = new DeliveryListViewAdapter.CallbackDelivery() {
		
		public void onDeliveryClick(Integer position, String id) {
			// TODO Auto-generated method stub
			setupCompleteDialog(id);
		}
		
		public void OnDeleteClick(Integer position, String id) {
			// TODO Auto-generated method stub
			deleteDelivery(id);
		}
	};
	private DeliveryCompleteDialog deliveryCompleteDialog;
	
	private void setupCompleteDialog(final String orderId){
		
		Log.i("", "Order id(tag): "+orderId);
		
		deliveryCompleteDialog = new DeliveryCompleteDialog(this);
		deliveryCompleteDialog.setCallbackListener(new DeliveryCompleteDialog.Callback() {

			public void onCancel() {
				// TODO Auto-generated method stub
				
			}

			public void onComplete(String password) {
				// TODO Auto-generated method stub
				postCompleteDelivery(password, orderId);
			}
		});	
		
		deliveryCompleteDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		deliveryCompleteDialog.show();
	}
	
	private void deleteDelivery(final String orderId) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryActivity.this);
		alertDialog.setMessage("Are you sure to delete Delivery?");

		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (SKConnectionDetector.getInstance(
								DeliveryActivity.this)
								.isConnectingToInternet()) {
							
							Log.i("", "to delete: "+AppLoginUser.getAccessToken()+" "
										+orderId+" "+AppLoginUser.getId()
										+" "+AppLoginUser.getUserID());
						 	
							NetworkEngine.getInstance().deleteDelivery(AppLoginUser.getAccessToken(), orderId, AppLoginUser.getId(), new Callback<Response>() {

								public void failure(RetrofitError arg0) {
									// TODO Auto-generated method stub
									
								}

								public void success(Response arg0, Response arg1) {
									// TODO Auto-generated method stub

									Toast.makeText(DeliveryActivity.this, "Deleted", Toast.LENGTH_LONG).show();
									
									//Reload Delivery List
									if(skDetector.isConnectingToInternet()){
										getDelivery();
									}else{
										Toast.makeText(DeliveryActivity.this, "Not Available Network!", Toast.LENGTH_LONG).show();
									}
								}
							});
						}
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						return;
					}
				});

		alertDialog.show();
	}
	/**
	 *  Delivery Complete
	 */
	private void postCompleteDelivery(String password, String orderId) {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(DeliveryActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postCompleteDelivery(AppLoginUser.getAccessToken(), password, orderId, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "Complete body: "+arg0.getBody());
				if (dialog != null) {
					dialog.dismiss();
					SKToastMessage.showMessage(DeliveryActivity.this, "Success Delivery", SKToastMessage.SUCCESS);
					
					//Reload Delivery List
					if(skDetector.isConnectingToInternet()){
						getDelivery();
					}else{
						Toast.makeText(DeliveryActivity.this, "Not Available Network!", Toast.LENGTH_LONG).show();
					}
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Complete Fail: "+arg0.getResponse().getReason());
				}
				
				if (dialog != null) {
					SKToastMessage.showMessage(DeliveryActivity.this, "Check your password again!", SKToastMessage.ERROR);
					dialog.dismiss();
				}
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
