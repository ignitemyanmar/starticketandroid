package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.NumberFormat;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;
import com.ignite.mm.ticketing.user.PDFBusActivity;
import com.ignite.mm.ticketing.user.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ThreeDaySalesLvAdapter extends BaseAdapter{

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
			
        	/*holder.txt_sale_date = (TextView) convertView.findViewById(R.id.txt_sale_date);
        	holder.txt_customer_name = (TextView) convertView.findViewById(R.id.txt_customer_name);
        	holder.txt_trip_operator = (TextView) convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_trip_date_time_class = (TextView) convertView.findViewById(R.id.txt_trip_date_time_class);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seats);
        	holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	holder.btn_print = (Button) convertView.findViewById(R.id.btn_print);*/
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_order_no.setText(getItem(position).getOrderId());		
		holder.txt_order_date.setText(getItem(position).getDate());
		
		//Change (0,000,000) format
		NumberFormat nf = NumberFormat.getInstance();
		String amount = nf.format(Integer.valueOf(getItem(position).getTotalAmount()));
		
		holder.txt_order_amount.setText(amount+"");
		
		if (getItem(position).getDiscount_amount() != null && !getItem(position).getDiscount_amount().equals("")) {
			String discount = nf.format(Integer.valueOf(getItem(position).getDiscount_amount()));
			holder.txt_discount.setText(discount);
		}else {
			holder.txt_discount.setText("0");
		}
		
/*		holder.txt_sale_date.setText("၀ယ္သည့္ ေန႔    :  "+getItem(position).getDate());
		holder.txt_customer_name.setText(getItem(position).getCustomerName()+" ["+getItem(position).getCustomerPhone()+"]");
		holder.txt_trip_operator.setText(getItem(position).getTrip()+" ["+getItem(position).getOperator()+"]");
		holder.txt_trip_date_time_class.setText("["+getItem(position).getDepartureDate()+" - "
				+getItem(position).getTime()+"] "+getItem(position).getClass_()+" ["+getItem(position).getPrice()+" Ks]");
		holder.txt_seats.setText(getItem(position).getSeatNo());
		//holder.txt_price.setText("Price :  "+getItem(position).getPrice()+" Ks");
		holder.txt_amount.setText(getItem(position).getTotalAmount()+" Ks");*/
		
/*		holder.btn_print.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent showVoucher = new Intent(aty.getApplication(), PDFBusActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "from_threeday_sales");
				bundle.putString("Operator_Name", getItem(position).getOperator());
				bundle.putString("from_to", getItem(position).getTrip());
				bundle.putString("date", getItem(position).getDepartureDate());
				bundle.putString("time", getItem(position).getTime());
				bundle.putString("classes", getItem(position).getClass_());
				bundle.putString("selected_seat", getItem(position).getSeatNo());
				bundle.putString("ticket_price", getItem(position).getPrice());
				bundle.putString("sale_order_no", getItem(position).getOrderId());
				bundle.putString("ConfirmDate", getItem(position).getDate());
				bundle.putString("ConfirmTime", "");
				bundle.putString("BuyerName", getItem(position).getCustomerName());
				bundle.putString("BuyerPhone", getItem(position).getCustomerPhone());
				bundle.putString("BuyerNRC", getItem(position).getCustomerNrc());
				bundle.putString("TicketNo", getItem(position).getTicketNo());
				bundle.putString("SeatCount", String.valueOf(getItem(position).getTicketQty()));
				bundle.putString("total_amount", getItem(position).getTotalAmount());
				bundle.putString("Passengers", getItem(position).getPassengers());
				
				showVoucher.putExtras(bundle);
				aty.startActivity(showVoucher);
			}
		});*/
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_order_no, txt_order_date, txt_order_amount, txt_discount;
	}
}
