package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;
import com.ignite.mm.ticketing.sqlite.database.model.MenuIcon;
import com.ignite.mm.ticketing.starticket.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopTenAttractionLvAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<MenuIcon> lstMenu;

	public TopTenAttractionLvAdapter(Activity aty, List<MenuIcon> lstMenu) {
		mInflater = LayoutInflater.from(aty);
		this.lstMenu = lstMenu;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return lstMenu.size();
	}

	public MenuIcon getItem(int position) {
		// TODO Auto-generated method stub
		return lstMenu.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (getCount() > 0) {
			ViewHolder holder;
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.list_item_topten, null);
				holder = new ViewHolder();
				holder.icon = (ImageView)convertView.findViewById(R.id.list_icon);
				holder.title = (TextView)convertView.findViewById(R.id.list_title);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
				
			}
			
			
			//Random rand = new Random();
			//holder.bar.setBackgroundColor(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			holder.icon.setImageResource(lstMenu.get(position).getIcon());
			holder.title.setText(lstMenu.get(position).getTitle());
		}
		return convertView;
	}
	
	static class ViewHolder{
		ImageView icon;
		TextView title;
	}

}
