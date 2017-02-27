package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.util.List;

import com.google.zxing.oned.rss.FinderPattern;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.TourDetail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TripDetailItineraryLvAdapter extends BaseAdapter {

	private Activity aty;
	private List<TourDetail> tourDetailList;
	private LayoutInflater inflater;
	
	public TripDetailItineraryLvAdapter(Activity aty,
			List<TourDetail> tourDetailList) {
		super();
		this.aty = aty;
		this.tourDetailList = tourDetailList;
		inflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return tourDetailList.size();
	}

	public TourDetail getItem(int position) {
		// TODO Auto-generated method stub
		return tourDetailList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.lititem_trip_detail_itinerary, null);
			
			holder.txt_title = (TextView)convertView.findViewById(R.id.txt_title);
			holder.txt_description = (TextView)convertView.findViewById(R.id.txt_body);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_title.setText(getItem(position).getTitle());
		holder.txt_description.setText(getItem(position).getDescription());
		
		return convertView;
	}

	static class ViewHolder{
		TextView txt_title, txt_description;
	}

}
