package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.Time;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Time> listItem;
	private Activity aty;
	public TimeAdapter(Activity aty, List<Time> _list){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public Time getItem(int position) {
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
        	holder.Time = (TextView)convertView.findViewById(R.id.txt_time);
        	holder.Classes = (TextView)convertView.findViewById(R.id.txt_class);
        	holder.TotalSeat = (TextView)convertView.findViewById(R.id.txt_total_seat);
        	holder.txt_notrip = (TextView)convertView.findViewById(R.id.txt_notrip);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
			
		//holder.txt_notrip.setText(getItem(position).getNo_trip());
		
		holder.Classes.setText(getItem(position).getBus_class());

			if(getItem(position).getTime().toLowerCase().contains("am")){
				
				convertView.setBackgroundResource(R.drawable.bg_gold_light2);
			}else{
				convertView.setBackgroundResource(R.drawable.bg_gold_light);
			}

		
		holder.Time.setText(getItem(position).getTime());
		
		Log.i("", "Get Time: "+getItem(position).getTime());
		
		holder.TotalSeat.setText(getItem(position).getTotal_sold_seat().toString() +"/"+ getItem(position).getTotal_seat().toString());
		
		int left_total_seat = getItem(position).getTotal_seat() - getItem(position).getTotal_sold_seat();
		
		if(left_total_seat > 0 && left_total_seat <= 5){
			//holder.Time.setBackgroundResource(R.drawable.ovel_time_orange);
			//holder.Line.setBackgroundColor(aty.getResources().getColor(R.color.orange));
		}else if(left_total_seat == 0){
			//holder.Time.setBackgroundResource(R.drawable.ovel_time_red);
			//holder.Line.setBackgroundColor(aty.getResources().getColor(R.color.red));
		}else{
			//holder.Time.setBackgroundResource(R.drawable.ovel_time_green);
			//holder.Line.setBackgroundColor(aty.getResources().getColor(R.color.forest_green));
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView Time;
		TextView Classes;
		TextView TotalSeat;
		TextView txt_notrip;
	}
}
