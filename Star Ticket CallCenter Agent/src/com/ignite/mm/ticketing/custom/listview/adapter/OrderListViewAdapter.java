package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.sqlite.database.model.Booking;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Booking> listItem;
	private Activity aty;
	
	public OrderListViewAdapter(Activity aty, List<Booking> bookingListByUser){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = bookingListByUser;

	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public Booking getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
			Configuration config = aty.getResources().getConfiguration();
			if (config.smallestScreenWidthDp >= 600) {
				convertView = mInflater.inflate(R.layout.list_item_credit_tablet, null);
			}else {
				convertView = mInflater.inflate(R.layout.list_item_credit_phone, null);
			}
        	
        	holder.txt_trip = (TextView) convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_dept_info = (TextView) convertView.findViewById(R.id.txt_date_time_class_price);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seat_no);
        	holder.txt_order_date = (TextView) convertView.findViewById(R.id.txt_order_date);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	holder.txt_order_no = (TextView) convertView.findViewById(R.id.txt_order_no);
        	holder.txt_customer = (TextView)convertView.findViewById(R.id.txt_customer_name);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_order_no.setText("Order No :  "+getItem(position).getId());
		holder.txt_customer.setText(getItem(position).getCustomerName()+" ["
				+getItem(position).getCustomerPhone()+"]");
		holder.txt_trip.setText(getItem(position).getTrip()+" ["+getItem(position).getOperator()+"]");
		holder.txt_dept_info.setText("["+getItem(position).getDepartureDate()+" - "
									+getItem(position).getTime()+"] "+getItem(position).getClass_()+" ["
									+getItem(position).getPrice()+" Ks]");
		holder.txt_seats.setText(getItem(position).getSeatNo());
		holder.txt_order_date.setText(getItem(position).getDate());
		holder.txt_amount.setText(getItem(position).getTotalAmount()+" Ks");
		
		return convertView;
	}
	static class ViewHolder {
		TextView txt_trip, txt_dept_info, txt_seats, txt_order_date, txt_amount, txt_order_no, txt_customer;
	}
}
