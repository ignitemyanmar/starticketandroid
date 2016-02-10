package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;
import com.ignite.mm.ticketing.sqlite.database.model.MenuIcon;
import com.ignite.mm.ticketing.sqlite.database.model.ThingsToDo;
import com.ignite.mm.ticketing.starticket.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CityLvAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<ThingsToDo> lstMenu;

	public CityLvAdapter(Activity aty, ArrayList<ThingsToDo> lstMenu) {
		mInflater = LayoutInflater.from(aty);
		this.lstMenu = lstMenu;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return lstMenu.size();
	}

	public ThingsToDo getItem(int position) {
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
				convertView = mInflater.inflate(R.layout.list_item_city, null);
				holder = new ViewHolder();
				holder.img_todo_image = (ImageView)convertView.findViewById(R.id.img_todo_image);
				holder.txt_todo_title = (TextView)convertView.findViewById(R.id.txt_todo_title);
				holder.txt_todo = (TextView)convertView.findViewById(R.id.txt_todo);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			//Random rand = new Random();
			//holder.bar.setBackgroundColor(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			holder.img_todo_image.setImageResource(lstMenu.get(position).getPhoto());
			holder.txt_todo_title.setText(lstMenu.get(position).getTodo_title());
			holder.txt_todo.setText(lstMenu.get(position).getTodo_body());
		}
		return convertView;
	}
	
	static class ViewHolder{
		ImageView img_todo_image;
		TextView txt_todo_title, txt_todo;
	}

}
