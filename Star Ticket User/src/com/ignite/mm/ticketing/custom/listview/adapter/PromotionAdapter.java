package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.model.Promotion;
import com.ignite.mm.ticketing.user.R;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PromotionAdapter extends BaseAdapter {
	
	private List<Promotion> listOperator;
	private TextView txtTitle;
	private LayoutInflater mInflater;
	private Activity aty;
	
	public PromotionAdapter(Activity aty,List<Promotion> arg0) {
		super();
		// TODO Auto-generated constructor stub
		this.mInflater= LayoutInflater.from(aty);
		this.aty = aty;
		this.listOperator = arg0;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listOperator.size();
	}

	public Promotion getItem(int index) {
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
			
			convertView = mInflater.inflate(R.layout.list_item_promotion, null);
			
			holder = new ViewHolder();
			
			holder.txt_operator_promo = (TextView) convertView.findViewById(R.id.txt_operator_promo);
			holder.txt_buy_amount = (TextView)convertView.findViewById(R.id.txt_buy_amount);
			holder.txt_promo_amount = (TextView)convertView.findViewById(R.id.txt_promo_amount);
			holder.txt_currency = (TextView)convertView.findViewById(R.id.txt_currency);
			holder.txt_promo_type = (TextView)convertView.findViewById(R.id.txt_promo_type);
			
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.txt_operator_promo.setText(getItem(position).getOperatorName());
		//holder.txt_buy_amount.setText("ယခုလတြင္ "+getItem(position).get);
		holder.txt_promo_amount.setText(getItem(position).getOperatorName());
		holder.txt_currency.setText(getItem(position).getOperatorName());
		holder.txt_promo_type.setText(getItem(position).getOperatorName());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_operator_promo, txt_buy_amount, txt_promo_amount, txt_currency, txt_promo_type;
	}
}

