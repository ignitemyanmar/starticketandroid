package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.NumberFormat;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;
import com.ignite.mm.ticketing.starticket.PDFBusActivity;
import com.ignite.mm.ticketing.starticket.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

@SuppressLint({ "ResourceAsColor", "DefaultLocale" }) public class ThreeDaySalesLvAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<ThreeDaySale> listItem;
	private Activity aty;
	
	public ThreeDaySalesLvAdapter(Activity aty, List<ThreeDaySale> _list){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public ThreeDaySale getItem(int position) {
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
			
        	//convertView = mInflater.inflate(R.layout.list_item_threeday_sales, null);
			convertView = mInflater.inflate(R.layout.list_item_order, null);
        	
			holder.txt_order_no = (TextView) convertView.findViewById(R.id.txt_order_no);
			holder.txt_order_date = (TextView)convertView.findViewById(R.id.txt_order_date);
			holder.txt_order_amount = (TextView)convertView.findViewById(R.id.txt_order_amount);
			holder.txt_discount = (TextView)convertView.findViewById(R.id.txt_discount);
			holder.txt_payment_type = (TextView)convertView.findViewById(R.id.txt_payment_type);
			holder.txt_status = (TextView)convertView.findViewById(R.id.txt_status);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_order_no.setText(getItem(position).getOrderId());	
		
		if (getItem(position).getTransactionId().equals("0")) 
		{
			holder.txt_order_date.setText("#");
		}else {
			holder.txt_order_date.setText(getItem(position).getTransactionId());
		}
			
		
		//Change (0,000,000) format
		NumberFormat nf = NumberFormat.getInstance();
		
		//If visa/master payment, ....
		//Show Total Amount
		if (getItem(position).getPaymentType() != null) {
			if (getItem(position).getPaymentType().equals("pay with master/visa")) {
				if (getItem(position).getRoundTrip().equals("0")) {
					//one way
					//add +4USD for booking fee
					holder.txt_order_amount.setText("$ "+String.format("%.2f", (getItem(position).getTotalUSD() + 4)));
				}else if (getItem(position).getRoundTrip().equals("1")) {
					//round trip 
					//add +2USD for booking fee
					holder.txt_order_amount.setText("$ "+String.format("%.2f", (getItem(position).getTotalUSD() + 2)));
				}
		}else {
				String amount = nf.format(Integer.valueOf(getItem(position).getTotalAmount()));
				holder.txt_order_amount.setText(amount+"");
		}
		}

		
		double discountUSD = 0.0;
		
		//Show Discount amount
		if (Double.valueOf(getItem(position).getExchangeRate()) > 0) {
			discountUSD = Double.valueOf(getItem(position).getDiscountAmount()) / Double.valueOf(getItem(position).getExchangeRate());
		}
		
		//Show Discount amount
		if (getItem(position).getDiscountAmount() > 0) {
			if (getItem(position).getPaymentType() != null) {
				if (getItem(position).getPaymentType().equals("pay with master/visa")) {
					holder.txt_discount.setText("$ "+String.format("%.2f", discountUSD));
				}else {
					String discount = nf.format(Integer.valueOf(getItem(position).getDiscountAmount()));
					holder.txt_discount.setText(discount);
				}
			}
		}else {
			holder.txt_discount.setText("0");
		}
		
		//Show payment type
		if (getItem(position).getPaymentType() != null) {
			if (getItem(position).getPaymentType().toLowerCase().equals("pay with master/visa")) {
				holder.txt_payment_type.setText("Pay with VISA/MASTER");
			}else {
				holder.txt_payment_type.setText(getItem(position).getPaymentType());
			}
		}
		
		//Show Delivery Status (pending or complete)
		if (getItem(position).getDelivery().equals("1")) {
			holder.txt_status.setText("Pending");
			holder.txt_status.setTextColor(aty.getResources().getColor(R.color.yellow));
		}else {
			holder.txt_status.setText("Complete");
			holder.txt_status.setTextColor(aty.getResources().getColor(R.color.green));
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_order_no, txt_order_date, txt_order_amount, txt_discount, txt_payment_type, txt_status;
	}
}
