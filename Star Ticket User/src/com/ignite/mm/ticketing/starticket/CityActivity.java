package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.CityLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.MenuIcon;
import com.ignite.mm.ticketing.sqlite.database.model.ThingsToDo;

public class CityActivity extends BaseActivity{
	private ListView lv_cities;
	private ArrayList<ThingsToDo> lstThingstodo;
	private String obj_string;
	private MenuIcon obj_menu;
	private String city_title;
	private boolean isClick;
	private int city_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cities);
		  
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			obj_string = bundle.getString("obj_city");
			obj_menu = new Gson().fromJson(obj_string, MenuIcon.class);
		}
		
		if (obj_menu != null) {
			city_title = obj_menu.getTitle();
			city_icon = obj_menu.getIcon();
		}
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Things to do at "+city_title);
           
            this.setSupportActionBar(toolbar);
        }
        
        lv_cities = (ListView)findViewById(R.id.lv_cities);
        lv_cities.setDividerHeight(0);
		
        lstThingstodo = new ArrayList<ThingsToDo>();
        lstThingstodo.add(new ThingsToDo(city_icon, "Shwedagon Pagoda", "One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas. One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas."));
        lstThingstodo.add(new ThingsToDo(city_icon, "People's Park", "This recently renovated park is notable for its splendid views of the west side of Shwedagon Paya. There are lots of pleasant features such as flower gardens and ponds; fountains, including one made up of concentric rings of white elephants; and tree-top observation platforms linked by fun swinging bridges. More quirky aspects include a decommissioned Myanmar Airways Fokker you can climb inside, a fighter jet and an old steam train. At the park's northwest corner is the Natural World Amusement Park with things like bumper cars and a log flume rollercoaster (K1000 a ride). Facing onto Damazedi Rd is the kid's amusement park Happy Zone."));
        lstThingstodo.add(new ThingsToDo(city_icon, "Shwedagon Pagoda", "One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas. One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas."));
        lstThingstodo.add(new ThingsToDo(city_icon, "People's Park", "This recently renovated park is notable for its splendid views of the west side of Shwedagon Paya. There are lots of pleasant features such as flower gardens and ponds; fountains, including one made up of concentric rings of white elephants; and tree-top observation platforms linked by fun swinging bridges. More quirky aspects include a decommissioned Myanmar Airways Fokker you can climb inside, a fighter jet and an old steam train. At the park's northwest corner is the Natural World Amusement Park with things like bumper cars and a log flume rollercoaster (K1000 a ride). Facing onto Damazedi Rd is the kid's amusement park Happy Zone."));
        lstThingstodo.add(new ThingsToDo(city_icon, "Shwedagon Pagoda", "One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas. One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas."));
        lstThingstodo.add(new ThingsToDo(city_icon, "People's Park", "This recently renovated park is notable for its splendid views of the west side of Shwedagon Paya. There are lots of pleasant features such as flower gardens and ponds; fountains, including one made up of concentric rings of white elephants; and tree-top observation platforms linked by fun swinging bridges. More quirky aspects include a decommissioned Myanmar Airways Fokker you can climb inside, a fighter jet and an old steam train. At the park's northwest corner is the Natural World Amusement Park with things like bumper cars and a log flume rollercoaster (K1000 a ride). Facing onto Damazedi Rd is the kid's amusement park Happy Zone."));
        lstThingstodo.add(new ThingsToDo(city_icon, "Shwedagon Pagoda", "One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas. One of Buddhism's most sacred sites, the 325ft zedi here is adorned with 27 metric tons of gold leaf, along with thousands of diamonds and other gems, and is believed to enshrine eight hairs of the Gautama Buddha as well as relics of three former buddhas."));
        lstThingstodo.add(new ThingsToDo(city_icon, "People's Park", "This recently renovated park is notable for its splendid views of the west side of Shwedagon Paya. There are lots of pleasant features such as flower gardens and ponds; fountains, including one made up of concentric rings of white elephants; and tree-top observation platforms linked by fun swinging bridges. More quirky aspects include a decommissioned Myanmar Airways Fokker you can climb inside, a fighter jet and an old steam train. At the park's northwest corner is the Natural World Amusement Park with things like bumper cars and a log flume rollercoaster (K1000 a ride). Facing onto Damazedi Rd is the kid's amusement park Happy Zone."));
        
		lv_cities.setAdapter(new CityLvAdapter(CityActivity.this, lstThingstodo));
		lv_cities.setOnItemClickListener(clickListener);
		
		isClick = false;
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			TextView txt_todo = (TextView) view.findViewById(R.id.txt_todo);
				if (!isClick) {
					isClick = true;
					txt_todo.setEllipsize(null);
					txt_todo.setMaxLines(Integer.MAX_VALUE);
					txt_todo.setText(lstThingstodo.get(position).getTodo_body());
				}else {
					isClick = false;
					txt_todo.setEllipsize(TruncateAt.END);
					txt_todo.setMaxLines(3);
					txt_todo.setText(lstThingstodo.get(position).getTodo_body());
				}
			}
		};
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}

