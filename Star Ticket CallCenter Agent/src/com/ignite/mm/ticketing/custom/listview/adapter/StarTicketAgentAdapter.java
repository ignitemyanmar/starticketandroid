package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.sqlite.database.model.StarTicketAgents;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StarTicketAgentAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<StarTicketAgents> agents;
	private Activity aty;
	private TextView txt_extacity_price;
	
	public StarTicketAgentAdapter(Activity aty,
			List<StarTicketAgents> arg0) {
		// TODO Auto-generated constructor stub
		this.aty = aty;
		this.agents = arg0;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return agents.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return agents.get(position);
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
	        txtTitle.setText(agents.get(position).getName());
	        txt_extacity_price.setText("");
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
        txtTitle.setText(agents.get(position).getName());
        txt_extacity_price.setText("");
		return convertView;
	}

}
