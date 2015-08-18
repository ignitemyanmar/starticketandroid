package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.starticket.R;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusProfileAdapter extends BaseAdapter {
	
	private List<String> listOperator;
	private TextView txtTitle;
	private LayoutInflater mInflater;
	private Activity aty;
	
	public BusProfileAdapter(Activity aty,List<String> listOperator) {
		super();
		// TODO Auto-generated constructor stub
		this.mInflater= LayoutInflater.from(aty);
		this.aty = aty;
		this.listOperator = listOperator;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listOperator.size();
	}

	public String getItem(int index) {
		// TODO Auto-generated method stub
		return listOperator.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_bus_profile, null);
			
			holder = new ViewHolder();
			
			holder.txt_operator = (TextView) convertView.findViewById(R.id.txt_operator);
			
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.txt_operator.setText(getItem(position));
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_operator;
	}
}
