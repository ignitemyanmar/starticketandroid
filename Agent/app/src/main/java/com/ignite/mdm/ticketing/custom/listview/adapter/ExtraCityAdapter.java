package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.ExtraCity;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExtraCityAdapter extends BaseAdapter {

	private TextView txtTitle;
	private TextView txt_extacity_price;
	private List<ExtraCity> timeby_agent;
	private Activity aty;
	
	public ExtraCityAdapter(Activity aty, List<ExtraCity> times_by_agent) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		this.timeby_agent = times_by_agent;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return timeby_agent.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return timeby_agent.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (convertView == null) {
	        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.spiner_item_list, null);
	        }
	        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
	        txt_extacity_price = (TextView)convertView.findViewById(R.id.txt_extacity_price);
	        
	        txtTitle.setText(timeby_agent.get(position).getCity_name());
	        
	        if (timeby_agent.get(position).getLocal_price() != null) {
	        	if (!timeby_agent.get(position).getLocal_price().equals("0")) {
	        		txt_extacity_price.setText("KS "+timeby_agent.get(position).getLocal_price());
				}
			}
	        
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)
                    aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_sub_item_list, null);
        }
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);       
        txt_extacity_price = (TextView)convertView.findViewById(R.id.txt_extacity_price);
        
        txtTitle.setText(timeby_agent.get(position).getCity_name());
        txt_extacity_price.setText("KS "+timeby_agent.get(position).getLocal_price());
        
		return convertView;
	}

}
