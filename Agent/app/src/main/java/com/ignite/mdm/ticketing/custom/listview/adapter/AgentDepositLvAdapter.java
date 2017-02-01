package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.text.DecimalFormat;
import java.util.List;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.PaidHistory;
import com.ignite.mdm.ticketing.sqlite.database.model.Payment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class AgentDepositLvAdapter extends BaseAdapter{

	private Activity aty;
	private List<Payment> paidHistory;
	private LayoutInflater mInflater;

	public AgentDepositLvAdapter(Activity aty, List<Payment> paidHistory) {
		super();
		this.aty = aty;
		this.paidHistory = paidHistory;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return paidHistory.size();
	}

	public Payment getItem(int position) {
		// TODO Auto-generated method stub
		return paidHistory.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.listitem_agent_deposit, null);
			
			holder.txt_paydate = (TextView)convertView.findViewById(R.id.txt_pay_date);
			holder.txt_deposit = (TextView)convertView.findViewById(R.id.txt_deposit);
			holder.txt_remark = (TextView)convertView.findViewById(R.id.txt_remark);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_paydate.setText(getItem(position).getPayDate());
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		Double depositAmount = 0.0;
		
//		if (getItem(position).get() != null) {
//			if (!getItem(position).getDeposit().equals("")) {
//				depositAmount = Double.valueOf(getItem(position).getDeposit());
//			}else {
//				depositAmount = 0.00;
//			}
//		}else {
//			depositAmount = 0.00;
//		}
		
		holder.txt_deposit.setText(getItem(position).getAmount());
		holder.txt_remark.setText(getItem(position).getPayRemark());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView txt_paydate, txt_deposit, txt_remark;
	}
}
