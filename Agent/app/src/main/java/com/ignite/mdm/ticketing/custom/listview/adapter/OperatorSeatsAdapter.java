package com.ignite.mdm.ticketing.custom.listview.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.OperatorSeat;
import java.text.NumberFormat;
import java.util.List;

public class OperatorSeatsAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<OperatorSeat> listItem;
	private Activity aty;
	public OperatorSeatsAdapter(Activity aty, List<OperatorSeat> operatorSeats){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = operatorSeats;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public OperatorSeat getItem(int position) {
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
			
        	convertView = mInflater.inflate(R.layout.list_item_time, null);
        	
        	holder.trip_time = (TextView)convertView.findViewById(R.id.txt_time);
        	holder.classes = (TextView)convertView.findViewById(R.id.txt_class);
        	holder.total_seat = (TextView)convertView.findViewById(R.id.txt_total_seat);
        	holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
        	//holder.txt_operator_name = (TextView)convertView.findViewById(R.id.txt_operator_name);
        	holder.txt_fromto_city = (TextView)convertView.findViewById(R.id.txt_fromto_city);
        	holder.txt_extra_city = (TextView)convertView.findViewById(R.id.txt_extra_city);
        	holder.extra_view = (View)convertView.findViewById(R.id.extra_view);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//holder.txt_operator_name.setText(getItem(position).getOperator());
		holder.classes.setText(getItem(position).getClass_());
		Log.d("DATA",getItem(position).getTime());

		if(getItem(position).getTime().toLowerCase().contains("am")){
			String s =getItem(position).getTime().replaceAll("AM",
					"မနက်");
			holder.trip_time.setText(s);
			//holder.trip_time.setBackgroundColor(aty.getResources().getColor(R.color.golden_brown_light));
			holder.trip_time.setTextColor(aty.getResources().getColor(R.color.primary_dark));
			//convertView.setBackgroundResource(R.drawable.bg_gold_light2);
		}else{

			String s = getItem(position).getTime().replaceAll("PM",
					"ညနေ ");
			holder.trip_time.setText(s);
			//ညနေ
			//holder.trip_time.setBackgroundColor(aty.getResources().getColor(R.color.dark_blue));
			holder.trip_time.setTextColor(aty.getResources().getColor(R.color.dark_blue));
			//convertView.setBackgroundResource(R.drawable.bg_gold_light);
		}



		holder.total_seat.setText("Seats : "+(getItem(position).getPermitseatTotal() - getItem(position).getPermitseatSoldtotal()));
		
		NumberFormat nf = NumberFormat.getInstance();
		
		if (getItem(position).getPrice() != null) {
			//Change (0,000,000) format
			
			String price = nf.format(Integer.valueOf(getItem(position).getPrice()));
			holder.txt_price.setText(price+" Ks");
		}else {
			holder.txt_price.setText(0.0+" Ks");
		}
		
		if (getItem(position).getExtraCity() != null) {
			if (getItem(position).getExtraCity().length() > 0) {
				holder.txt_fromto_city.setVisibility(View.VISIBLE);
				holder.txt_extra_city.setVisibility(View.VISIBLE);
				holder.extra_view.setVisibility(View.VISIBLE);
				holder.txt_fromto_city.setText(getItem(position).getFromName()+"=>"+getItem(position).getToName());
				holder.txt_extra_city.setText(aty.getResources().getString(R.string.strmm_boarding_place)+""+getItem(position).getExtraCity());
			}else{
				holder.txt_fromto_city.setVisibility(View.GONE);
				holder.txt_extra_city.setVisibility(View.GONE);
				holder.extra_view.setVisibility(View.GONE);
			}
		}else {
			holder.txt_fromto_city.setVisibility(View.GONE);
			holder.txt_extra_city.setVisibility(View.GONE);
			holder.extra_view.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView trip_time, classes, sold_seat, total_seat, txt_price, txt_fromto_city, txt_extra_city;
		View extra_view;
	}
}

