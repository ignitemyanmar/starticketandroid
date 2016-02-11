package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ignite.mm.ticketing.agent.callcenter.BusSelectSeatActivity;
import com.ignite.mm.ticketing.agent.callcenter.DeliveryActivity;
import com.ignite.mm.ticketing.agent.callcenter.PDFBusActivity;
import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.DeliveryCompleteDialog;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.Delivery;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;
import com.smk.skalertmessage.SKToastMessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DeliveryListViewAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Delivery> listItem;
	private Activity aty;
	private DeliveryCompleteDialog deliveryCompleteDialog;
	private String accessToken;
	protected ProgressDialog dialog;
	
	public DeliveryListViewAdapter(Activity aty, List<Delivery> deliveryList, String accessToken){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = deliveryList;
		this.accessToken = accessToken;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public Delivery getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
        	convertView = mInflater.inflate(R.layout.list_item_delivery, null);
        	
        	holder.txt_departure_date_time = (TextView)convertView.findViewById(R.id.txt_departure_date_time);
        	holder.txt_customer_name = (TextView)convertView.findViewById(R.id.txt_customer_name);
        	holder.txt_trip = (TextView)convertView.findViewById(R.id.txt_trip);
        	holder.txt_operator_class = (TextView)convertView.findViewById(R.id.txt_operator_class);
        	holder.txt_seats = (TextView)convertView.findViewById(R.id.txt_seats);
        	holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
        	holder.txt_amount = (TextView)convertView.findViewById(R.id.txt_amount);
        	holder.txt_transaction_no = (TextView)convertView.findViewById(R.id.txt_transaction_no);
        	holder.txt_order_no = (TextView)convertView.findViewById(R.id.txt_order_no);
        	holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
        	holder.btn_complete = (Button)convertView.findViewById(R.id.btn_complete);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_departure_date_time.setText(getItem(position).getDepartureDate()+" ("+getItem(position).getTime()+")");
		holder.txt_customer_name.setText(getItem(position).getCustomerName()+" ("+getItem(position).getCustomerPhone()+")");
		holder.txt_trip.setText(getItem(position).getTrip());
		holder.txt_operator_class.setText(getItem(position).getOperator()+" ("+getItem(position).get_class()+")");
		holder.txt_seats.setText(getItem(position).getTotalSeat()+"("+getItem(position).getSeatNos()+")");
		holder.txt_price.setText(getItem(position).getPrice()+" Ks");
		holder.txt_amount.setText(getItem(position).getTotalAmount()+" Ks");
		holder.txt_transaction_no.setText(getItem(position).getTransactionId());
		holder.txt_order_no.setText(getItem(position).getId());
		
		holder.btn_complete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				setupCompleteDialog(getItem(position).getId());
			}
		});
		
		return convertView;
	}
	
	private void setupCompleteDialog(final String orderId){
		
		Log.i("", "Order id(tag): "+orderId);
		
		deliveryCompleteDialog = new DeliveryCompleteDialog(aty);
		deliveryCompleteDialog.setCallbackListener(new DeliveryCompleteDialog.Callback() {

			public void onCancel() {
				// TODO Auto-generated method stub
				
			}

			public void onComplete(String password) {
				// TODO Auto-generated method stub
				postCompleteDelivery(password, orderId);
			}
		});	
		
		deliveryCompleteDialog.show();
	}
	
	/**
	 *  Delivery Complete
	 */
	private void postCompleteDelivery(String password, String orderId) {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(aty, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postCompleteDelivery(accessToken, password, orderId, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "Complete body: "+arg0.getBody());
				if (dialog != null) {
					dialog.dismiss();
					SKToastMessage.showMessage(aty, "Success Deliver", SKToastMessage.SUCCESS);
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Complete Fail: "+arg0.getResponse().getReason());
				}
				
				if (dialog != null) {
					SKToastMessage.showMessage(aty, "Check your password again!", SKToastMessage.ERROR);
					dialog.dismiss();
				}
			}
		});
	}
	
	static class ViewHolder {
		TextView txt_departure_date_time, txt_customer_name, txt_trip, txt_operator_class, txt_seats, txt_price, txt_amount, txt_transaction_no, txt_order_no;
		Button btn_delete, btn_complete;
	}
	
}
