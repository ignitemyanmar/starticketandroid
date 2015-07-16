
package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.CustomerTicketListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.BookingSearch;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;
import com.ignite.mm.ticketing.sqlite.database.model.Saleitem;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusBookingDetailActivity extends BaseActivity {
	private ListView lst_customer_ticket;
	private List<BookingSearch> ticket_list;

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private CheckBox chk_remove_all;
	private CustomerTicketListViewAdapter customerTicketAdapter;
	private boolean childChecked = false;
	private String creditOrderString;
	private BookingSearch creditOrder;
	private TextView txt_customer_name;
	private TextView txt_phone;
	private Button btn_delete;
	private TextView action_bar_title2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("�?ံု နံပါ�?္ မ်ား ဖ်က္ျ�?င္း");
		action_bar_title2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		action_bar_title2.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_busticketing_customer_ticket);
		lst_customer_ticket = (ListView) findViewById(R.id.lst_customer_ticket);
		txt_customer_name = (TextView) findViewById(R.id.txt_customer_name);
		txt_phone = (TextView) findViewById(R.id.txt_phone);
		btn_delete = (Button) findViewById(R.id.btn_cancel);
		btn_delete.setOnClickListener(clickListener);
		
		chk_remove_all = (CheckBox) findViewById(R.id.chk_remove_all);
		chk_remove_all.setOnClickListener(clickListener);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   creditOrderString = extras.getString("credit_order");
		}
		creditOrder = new Gson().fromJson(creditOrderString, BookingSearch.class);
		
		ticket_list = new ArrayList<BookingSearch>();
		ticket_list.add(creditOrder);
		
		txt_customer_name.setText(creditOrder.getCustomerName());
		txt_phone.setText("Phone: "+creditOrder.getCustomerPhone());

		
		customerTicketAdapter = new CustomerTicketListViewAdapter(this, ticket_list);
		customerTicketAdapter.setCallbacks(childCheckedCallback);
		lst_customer_ticket.setAdapter(customerTicketAdapter);
		setListViewHeightBasedOnChildren(lst_customer_ticket);	
	}
	
	private CustomerTicketListViewAdapter.Callback childCheckedCallback = new CustomerTicketListViewAdapter.Callback() {
		
		public void onChildItemCheckedChange(int position, boolean isChecked) {
			// TODO Auto-generated method stub
			childChecked = true;
			if(chk_remove_all.isChecked()){
				if(!isChecked){
					chk_remove_all.setChecked(false);
				}
			}else{
				if(isChecked){
					for(int i=0; i<ticket_list.size();i++){
						View childView = lst_customer_ticket.getChildAt(i);
						CheckBox chk_remove = (CheckBox) childView.findViewById(R.id.chk_remove);
						if(chk_remove.isChecked()){
							chk_remove_all.setChecked(true);
						}else{
							chk_remove_all.setChecked(false);
							break;
						}
					}
				}
			}
		}
	};
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			
			if(v == chk_remove_all){
				for(int i=0;i<ticket_list.size();i++){
					View childView = lst_customer_ticket.getChildAt(i);
					CheckBox chk_remove = (CheckBox) childView.findViewById(R.id.chk_remove);
					if(((CheckBox)v).isChecked()){
						chk_remove.setChecked(true);
					}else{
						chk_remove.setChecked(false);
					}
				}
			}
			
			if(v == btn_delete){
				SKConnectionDetector skDetector = SKConnectionDetector.getInstance(BusBookingDetailActivity.this);
				if(skDetector.isConnectingToInternet()){
					deleteOrder();
				}else{
					skDetector.showErrorMessage();
				}
			}
		}
	};
	private ProgressDialog dialog;
	
	private void deleteOrder(){
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		
		if(chk_remove_all.isChecked()){
			String param = MCrypt.getInstance().encrypt(SecureParam.deleteAllOrderParam(accessToken));
			NetworkEngine.getInstance().deleteAllOrder(param, creditOrder.getId().toString(), new Callback<Response>() {

				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Can't Delete Record.", SKToastMessage.ERROR);
				}

				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub
					if (arg0 != null) {
						dialog.dismiss();
						SKToastMessage.showMessage(BusBookingDetailActivity.this, "Successfully deleted.", SKToastMessage.SUCCESS);
						finish();
					}
				}
			});
		}else{
			String saleItem = "";
			
			for(int i=0;i<ticket_list.size();i++){
				View childView = lst_customer_ticket.getChildAt(i);
				CheckBox chk_remove = (CheckBox) childView.findViewById(R.id.chk_remove);
				if(chk_remove.isChecked()){
					saleItem += ticket_list.get(i).getId()+",";
				}
			}
			
			Log.i("","Hello Delete Str = "+ saleItem);
			
			String param = MCrypt.getInstance().encrypt(SecureParam.deleteOrderItemParam(accessToken, saleItem));
			NetworkEngine.getInstance().deleteOrderItem(param, new Callback<Response>() {

				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					Log.e("","Hello: "+ arg0.getResponse().getStatus()+" & "+arg0.getResponse().getReason());
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Can't Delete Record.", SKToastMessage.ERROR);
				}

				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Successfully deleted.", SKToastMessage.SUCCESS);
					finish();
				}
			});
		}
	}
	
	
	private static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null && listView.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
}
