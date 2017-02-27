package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.BookingSearch;
import com.ignite.mdm.ticketing.sqlite.database.model.Saleitem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CustomerTicketListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<BookingSearch> listItem;
	private Callback mCallbacks;
	public CustomerTicketListViewAdapter(Activity aty, List<BookingSearch> _list){
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public BookingSearch getItem(int position) {

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
        	convertView = mInflater.inflate(R.layout.list_item_customer_ticket, null);
        	holder.chk_remove = (CheckBox)convertView.findViewById(R.id.chk_remove);
        	holder.txt_name = (TextView) convertView.findViewById(R.id.txt_customer_name);
        	holder.txt_nrc = (TextView) convertView.findViewById(R.id.txt_nrc);
        	holder.txt_seat_no = (TextView) convertView.findViewById(R.id.txt_seat_no);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_name.setText(getItem(position).getCustomerName().toString());
		holder.txt_nrc.setText(getItem(position).getCustomerNrc().toString().length() == 0? "-":getItem(position).getCustomerNrc().toString());

		holder.txt_seat_no.setText("Seat No: "+getItem(position).getSeatNo().toString());
		
		holder.chk_remove.setTag(position);
		holder.chk_remove.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				int pos = (Integer) buttonView.getTag();
				if(mCallbacks != null){
					mCallbacks.onChildItemCheckedChange(pos, isChecked);
				}
			}
		});
		return convertView;
	}
	
	public void setCallbacks(Callback listener) {
        mCallbacks = listener;
    }
	
	public interface Callback{
		public void onChildItemCheckedChange(int position,boolean isChecked);
	}
	static class ViewHolder {
		CheckBox chk_remove;
		TextView txt_name;
		TextView txt_nrc;
		TextView txt_seat_no;
	}
}