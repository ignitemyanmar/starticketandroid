package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.gatethein.R;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CreditOrder> listItem;
	private Activity aty;
	
	public OrderListViewAdapter(Activity aty, List<CreditOrder> _list){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public CreditOrder getItem(int position) {
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
        	convertView = mInflater.inflate(R.layout.list_item_credit, null);
        	holder.buyer = (TextView) convertView.findViewById(R.id.txt_buyer);
        	holder.phone = (TextView) convertView.findViewById(R.id.txt_phone);
        	holder.name = (TextView) convertView.findViewById(R.id.txt_agent_name);
        	holder.trip = (TextView) convertView.findViewById(R.id.txt_trip);
        	holder.date = (TextView) convertView.findViewById(R.id.txt_date);
        	holder.total_ticket = (TextView) convertView.findViewById(R.id.txt_total_ticket);
        	holder.amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	holder.txt_booking_no= (TextView)convertView.findViewById(R.id.txt_booking_no);
        	holder.txt_booking_user= (TextView)convertView.findViewById(R.id.txt_booking_user);
        	holder.txt_date_time= (TextView)convertView.findViewById(R.id.txt_date_time);
        	holder.txt_seat_no= (TextView)convertView.findViewById(R.id.txt_seat_no);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.buyer.setText(getItem(position).getCustomer());
		holder.phone.setText(getItem(position).getPhone());
		holder.name.setText(getItem(position).getAgent());
		holder.trip.setText(getItem(position).getTrip().toString()+" ["+getItem(position).getOperator()+"]");
		holder.txt_date_time.setText(getItem(position).getDate()+" "+getItem(position).getTime()
				+" ["+getItem(position).getClasses()+"] "+getItem(position).getPrice()+" Ks");
		holder.date.setText(getItem(position).getOrderdate());
		holder.total_ticket.setText("Total Ticket : "+getItem(position).getTotalTicket().toString());
		holder.amount.setText("Amount: "+getItem(position).getAmount().toString());
		holder.txt_booking_no.setText(getItem(position).getId());
		holder.txt_booking_user.setText(getItem(position).getBooking_user());
		
		String SeatLists = "";
		for(int i=0; i<getItem(position).getSaleitems().size(); i++){
			if (i == getItem(position).getSaleitems().size() - 1) {
				SeatLists += getItem(position).getSaleitems().get(i).getSeatNo();
			}else {
				SeatLists += getItem(position).getSaleitems().get(i).getSeatNo()+",";
			}
			
		}
		
		holder.txt_seat_no.setText(SeatLists);
		
		
		return convertView;
	}
	static class ViewHolder {
		TextView buyer;
		TextView phone;
		TextView name;
		TextView trip;
		TextView date;
		TextView total_ticket;
		TextView amount, txt_booking_no, txt_booking_user, txt_date_time, txt_seat_no;
	}
}
