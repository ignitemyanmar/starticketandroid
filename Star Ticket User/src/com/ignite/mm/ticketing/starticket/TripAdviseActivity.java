package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.TripAdviseLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.MenuIcon;

public class TripAdviseActivity extends BaseActivity{
	private ListView lv_trip_advise;
	private List<MenuIcon> lstMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_advise);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Trip Advise");
           
            this.setSupportActionBar(toolbar);
        }
        
		lv_trip_advise = (ListView)findViewById(R.id.lv_trip_advise);
		lv_trip_advise.setDividerHeight(0);
		
		lstMenu = new ArrayList<MenuIcon>();
		lstMenu.add(new MenuIcon(R.drawable.mm_map,"Top Ten Attractions"));
		lstMenu.add(new MenuIcon(R.drawable.speak,"Speak in Burmese"));
		lstMenu.add(new MenuIcon(R.drawable.emergincy,"Emergency Numbers"));
		
		lstMenu.add(new MenuIcon(R.drawable.yangon,"Yangon"));
		lstMenu.add(new MenuIcon(R.drawable.mandalay,"Mandalay"));
		lstMenu.add(new MenuIcon(R.drawable.naypyitaw,"Nay Pyi Taw"));
		lstMenu.add(new MenuIcon(R.drawable.inlay,"Inle"));
		lstMenu.add(new MenuIcon(R.drawable.bagan,"Bagan"));
		
		lv_trip_advise.setAdapter(new TripAdviseLvAdapter(TripAdviseActivity.this, lstMenu));
		lv_trip_advise.setOnItemClickListener(clickListener);
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putString("obj_city", new Gson().toJson(lstMenu.get(position)));
			Log.i("", "obj city: "+lstMenu.get(position).toString());
			if (position == 0) {
				startActivity(new Intent(TripAdviseActivity.this, TopTenAttractionsActivity.class).putExtras(bundle));
			}else if (position == 1){
				startActivity(new Intent(TripAdviseActivity.this, SpeakInBurmeseActivity.class).putExtras(bundle));
			}else if (position == 2){
				startActivity(new Intent(TripAdviseActivity.this, EmergencyActivity.class).putExtras(bundle));
			}else {
				startActivity(new Intent(TripAdviseActivity.this, CityActivity.class).putExtras(bundle));
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
