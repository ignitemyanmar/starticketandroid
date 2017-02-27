package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.google.zxing.oned.rss.FinderPattern;
import com.ignite.mdm.ticketing.agent.callcenter.TourCompanyActivity;
import com.ignite.mdm.ticketing.agent.callcenter.TourConfirmActivity;
import com.ignite.mdm.ticketing.agent.callcenter.TourDetailActivity;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.Booking;
import com.ignite.mdm.ticketing.sqlite.database.model.TourBooking;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TourBookingLvAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<TourBooking> tourBookingList;
	private Activity aty;
	private String userRole;
	
	public TourBookingLvAdapter(Activity aty, List<TourBooking> tourBookingList, String userRole){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.tourBookingList = tourBookingList;
		this.userRole = userRole;

	}
	public int getCount() {
		// TODO Auto-generated method stub
		return tourBookingList.size();
	}

	public TourBooking getItem(int position) {
		return tourBookingList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.list_item_tourbooking, null);
        	
        	holder.txt_trip = (TextView) convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_dept_info = (TextView) convertView.findViewById(R.id.txt_date_time_class_price);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seat_no);
        	holder.txt_order_date = (TextView) convertView.findViewById(R.id.txt_order_date);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	holder.txt_order_no = (TextView) convertView.findViewById(R.id.txt_order_no);
        	holder.txt_customer = (TextView)convertView.findViewById(R.id.txt_customer_name);
        	holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
        	holder.btn_confirm = (Button)convertView.findViewById(R.id.btn_confirm);
        			
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		
		if (userRole != null) {
			Integer role = Integer.valueOf(userRole);
			if (role <= 3) {
				holder.btn_delete.setVisibility(View.GONE);
			}else {
				holder.btn_delete.setVisibility(View.VISIBLE);
			}
		}
		
		holder.txt_order_no.setText(aty.getResources().getString(R.string.strmm_booking_no)+" : "+getItem(position).getOrderId());
		holder.txt_customer.setText(getItem(position).getCustomerName()+" ["
				+getItem(position).getCustomerPhone()+"]");
		holder.txt_trip.setText(getItem(position).getFromCity()+" => "+getItem(position).getToCity()+" ["+getItem(position).getCompanyName()+"]");
		holder.txt_dept_info.setText(getItem(position).getDepartDate()+"  ("+getItem(position).getNight()+") ညအိပ္ ("
									+getItem(position).getDay()+") ရက္ "+getItem(position).getSeater()+"�?ံု ကား ["
									+formatter.format(getItem(position).getPrice())+" Ks]");
		holder.txt_seats.setText(getItem(position).getPassengerQty()+"");
		holder.txt_order_date.setText(getItem(position).getBookingDate());
		
		Double total = getItem(position).getPassengerQty() * getItem(position).getPrice();
		holder.txt_amount.setText(formatter.format(total)+" Ks");
		
		holder.btn_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aty.startActivity(new Intent(aty, TourConfirmActivity.class)
							.putExtra("tourbooking", tourBookingList.get(position)));
			}
		});
		
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Delete Booking 
			}
		});
		
		return convertView;
	}
	static class ViewHolder {
		TextView txt_trip, txt_dept_info, txt_seats, txt_order_date, txt_amount, txt_order_no, txt_customer;
		Button btn_delete, btn_confirm;
	}
}
