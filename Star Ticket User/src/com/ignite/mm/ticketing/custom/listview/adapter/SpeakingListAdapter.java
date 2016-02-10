package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;
import com.ignite.mm.ticketing.sqlite.database.model.Speaking;
import com.ignite.mm.ticketing.starticket.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpeakingListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Speaking> lstSpeaking;
	public SpeakingListAdapter(Activity aty, ArrayList<Speaking> lstSpeaking){
		mInflater = LayoutInflater.from(aty);
		this.lstSpeaking = lstSpeaking;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this.lstSpeaking.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(lstSpeaking.size() > 0){
			ViewHolder holder;
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.list_item_speaking, null);
				holder = new ViewHolder();
				holder.title = (TextView)convertView.findViewById(R.id.txt_speaking_title);
				holder.subtitle = (TextView)convertView.findViewById(R.id.txt_speaking_subtitle);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(lstSpeaking.get(position).getTitle());
			holder.subtitle.setText(lstSpeaking.get(position).getSubtitle());
		}
		return convertView;
	}
	static class ViewHolder {
		TextView title;
		TextView subtitle;
	}

}
