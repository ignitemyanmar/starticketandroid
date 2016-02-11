package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.callcenter.PDFBusActivity;
import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.sqlite.database.model.KhoneAtList;
import com.ignite.mm.ticketing.sqlite.database.model.Salebytripdate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SalebytripdatesLvAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<KhoneAtList> listItem;
	private Activity aty;
	
	public SalebytripdatesLvAdapter(Activity aty, List<KhoneAtList> _list){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public KhoneAtList getItem(int position) {
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
			
        	convertView = mInflater.inflate(R.layout.list_item_salebytripdate, null);
        	        	
        	holder.txt_seller_name = (TextView)convertView.findViewById(R.id.txt_seller_name);
        	holder.txt_sale_date = (TextView) convertView.findViewById(R.id.txt_sale_date);
        	holder.txt_customer_name = (TextView) convertView.findViewById(R.id.txt_customer_name);
        	holder.txt_trip_operator = (TextView) convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_trip_date_time_class = (TextView) convertView.findViewById(R.id.txt_trip_date_time_class);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seats);
        	holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	//holder.btn_print = (Button) convertView.findViewById(R.id.btn_print);
        	
        	holder.txt_operator_name = (TextView)convertView.findViewById(R.id.txt_operator_name);
    		holder.txt_ticket_no  = (TextView)convertView.findViewById(R.id.txt_ticket_no);
    		holder.txt_khoneat_time = (TextView)convertView.findViewById(R.id.txt_khoneat_time);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_operator_name.setText(getItem(position).getTrip().getOperator_name());
		holder.txt_khoneat_time.setText(aty.getResources().getString(R.string.str_khoneat_time)+" :  "+Html.fromHtml("<font color = red>"+getItem(position).getKnoneupTime()+"</font>"));
		holder.txt_ticket_no.setText(getItem(position).getTicket_nos());		
		holder.txt_seller_name.setText(getItem(position).getSeller().getName());
		holder.txt_sale_date.setText(getItem(position).getDate());
		holder.txt_customer_name.setText(getItem(position).getCustomerName()+" ["+getItem(position).getCustomerPhone()+"]");
		holder.txt_trip_operator.setText(getItem(position).getTrip_name());
		holder.txt_trip_date_time_class.setText(getItem(position).getDepartureDate()+"  "
				+getItem(position).getTime()+"  "+getItem(position).get_class()+" ["
				+Html.fromHtml("<font color = red>"+getItem(position).getSaleitems().get(0).getPrice()+"</font> Ks]"));
		holder.txt_seats.setText(getItem(position).getSeat_nos());
		//holder.txt_price.setText("Price :  "+getItem(position).getPrice()+" Ks");
		holder.txt_amount.setText(getItem(position).getTotalAmount()+" Ks");
		
		/*holder.btn_print.setOnClickListener(new OnClickListener() {
			
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
		TextView txt_sale_date, txt_customer_name, txt_trip_operator, txt_trip_date_time_class, txt_seats, txt_price, txt_amount;
		TextView txt_seller_name;
		TextView txt_operator_name, txt_ticket_no, txt_khoneat_time;
		//Button btn_print;
	}
}

