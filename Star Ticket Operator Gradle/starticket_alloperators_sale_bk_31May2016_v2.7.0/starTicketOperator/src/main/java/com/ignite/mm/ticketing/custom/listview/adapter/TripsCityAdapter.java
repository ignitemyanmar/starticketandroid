package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.model.TripsCollection;
import com.ignite.mm.ticketing.starticketsale.R;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TripsCityAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<TripsCollection> listItem;
	private Activity aty;
	private String changeColorCity;
	
	public TripsCityAdapter(Activity aty, List<TripsCollection> _list, String changeColorCity){
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
		this.aty = aty;
		this.changeColorCity = changeColorCity;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public TripsCollection getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
        	convertView = mInflater.inflate(R.layout.list_item_trips_city, null);
        	holder.TripCity = (TextView)convertView.findViewById(R.id.txt_trips_city);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//If Bagan is started, change color \
			if (changeColorCity != null) {
				if (changeColorCity.equals(listItem.get(position).getFrom())) {
					Log.i("", "change color city(not null): "+changeColorCity);
					holder.TripCity.setTextColor(aty.getResources().getColor(R.color.white));
					holder.TripCity.setBackgroundDrawable(aty.getResources().getDrawable(R.drawable.bg_trip_item_blue));
				}else {
					holder.TripCity.setBackgroundDrawable(aty.getResources().getDrawable(R.drawable.bg_trip_item));
				}
			}else {
				if (listItem.get(position).getFrom().startsWith("Yangon")) {
					holder.TripCity.setTextColor(aty.getResources().getColor(R.color.white));
					holder.TripCity.setBackgroundDrawable(aty.getResources().getDrawable(R.drawable.bg_trip_item_blue));
				}else {
					holder.TripCity.setBackgroundDrawable(aty.getResources().getDrawable(R.drawable.bg_trip_item));
				}
			}
		
		holder.TripCity.setText(getItem(position).getFrom()+" - "+getItem(position).getTo());
		return convertView;
	}
	static class ViewHolder {
		TextView TripCity;
	}
}
