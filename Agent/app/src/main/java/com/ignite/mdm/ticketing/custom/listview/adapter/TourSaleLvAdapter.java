package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.ignite.mdm.ticketing.agent.callcenter.TourConfirmActivity;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.TourBooking;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TourSaleLvAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<TourBooking> tourSaleList;
	private Activity aty;
	
	public TourSaleLvAdapter(Activity aty, List<TourBooking> tourSaleList){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.tourSaleList = tourSaleList;

	}
	public int getCount() {
		// TODO Auto-generated method stub
		return tourSaleList.size();
	}

	public TourBooking getItem(int position) {
		return tourSaleList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.list_item_tour_sale, null);
        	
        	holder.txt_booking_user = (TextView)convertView.findViewById(R.id.txt_booking_user);
        	holder.txt_seller_name = (TextView)convertView.findViewById(R.id.txt_seller_name);
        	holder.txt_sale_date = (TextView) convertView.findViewById(R.id.txt_sale_date);
        	holder.txt_customer_name = (TextView) convertView.findViewById(R.id.txt_customer_name);
        	holder.txt_trip_operator = (TextView) convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_trip_date_time_class = (TextView) convertView.findViewById(R.id.txt_trip_date_time_class);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seats);
        	holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	holder.btn_print = (Button) convertView.findViewById(R.id.btn_print);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		
		holder.txt_booking_user.setText("test");
		holder.txt_seller_name.setText("test");
		holder.txt_sale_date.setText(aty.getResources().getString(R.string.strmm_sale_date)+" : "+getItem(position).getBookingDate());
		holder.txt_customer_name.setText(getItem(position).getCustomerName()+" ["+getItem(position).getCustomerPhone()+"]");
		holder.txt_trip_operator.setText(getItem(position).getFromCity()+" => "+getItem(position).getToCity()+" ["+getItem(position).getCompanyName()+"]");
		holder.txt_trip_date_time_class.setText(getItem(position).getDepartDate()+"  ("+getItem(position).getNight()+") ညအိပ္ ("
				+getItem(position).getDay()+") ရက္ "+getItem(position).getSeater()+"�?ံု ကား ["
				+formatter.format(getItem(position).getPrice())+" Ks]");
		
		holder.txt_seats.setText(getItem(position).getPassengerQty()+"");
		
		Double total = getItem(position).getPassengerQty() * getItem(position).getPrice();
		holder.txt_amount.setText(formatter.format(total)+" Ks");
		
		return convertView;
	}
	static class ViewHolder {
		TextView txt_sale_date, txt_customer_name, txt_trip_operator, txt_trip_date_time_class, txt_seats, txt_price, txt_amount;
		TextView txt_seller_name, txt_booking_user;
		Button btn_print;
	}
}
