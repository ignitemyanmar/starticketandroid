package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.TourCompany;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class TourCompanyLvAdapter extends BaseAdapter {

	private Activity activity;
	private List<TourCompany> tourCompanyList;
	private LayoutInflater mInflater;

	public TourCompanyLvAdapter(Activity cxt, List<TourCompany> tourCompanyList){
		activity = cxt;
		mInflater = LayoutInflater.from(cxt); //change
		this.tourCompanyList = tourCompanyList;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return tourCompanyList.size();
	}

	public TourCompany getItem(int position) {
		// TODO Auto-generated method stub
		return tourCompanyList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null; //change initialize null
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.listitem_tour_company, null);
			
			holder.txt_tour_name = (TextView)convertView.findViewById(R.id.txt_tour_name);
			holder.txt_trip_name = (TextView)convertView.findViewById(R.id.txt_trip_name);
			holder.txt_days = (TextView)convertView.findViewById(R.id.txt_days);
			holder.txt_seaters = (TextView)convertView.findViewById(R.id.txt_seaters);
			holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
			holder.txt_hotel_bus_food = (TextView)convertView.findViewById(R.id.txt_hotel_bus_food);
			holder.txt_dept_date = (TextView)convertView.findViewById(R.id.txt_depart_date);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_tour_name.setText(getItem(position).getCompanyName());
		holder.txt_trip_name.setText(getItem(position).getFromCity()+"-"+getItem(position).getToCity());
		holder.txt_days.setText("("+getItem(position).getNight()+") ညအိပ္  / ("+getItem(position).getDay()+") ရက္");
		holder.txt_seaters.setText(getItem(position).getSeater()+" �?ံု ကား");
		holder.txt_price.setText(getItem(position).getPrice());
		holder.txt_hotel_bus_food.setText("bus"+"+"+getItem(position).getHotel()+"+"+getItem(position).getFood());
		
/*			if (getItem(position).getHotel().equals("1") && getItem(position).getFood().equals("1")) {
			holder.txt_hotel_bus_food.setText("bus"+"+"
					+"hotel"+"+"
					+"food");
		}else if (getItem(position).getHotel().equals("1")) {
			holder.txt_hotel_bus_food.setText("bus"+"+"+"hotel");
		}else if (getItem(position).getFood().equals("1")) {
			holder.txt_hotel_bus_food.setText("bus"+"+"+"food");
		}else {
			holder.txt_hotel_bus_food.setText("bus ticket");
		}*/
		
		holder.txt_dept_date.setText(getItem(position).getDepartDate());
		
		return convertView;
	}

	public static class ViewHolder {
		TextView txt_tour_name, txt_trip_name, txt_days, txt_seaters, txt_price, txt_hotel_bus_food, txt_dept_date;
	}

}
