package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.SpeakInBurmeseLvAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TopTenAttractionLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.MenuIcon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class SpeakInBurmeseActivity extends BaseActivity {
	private ListView lv_speak_burmese;
	private ArrayList<MenuIcon> lstMenu;
	private ImageView imgLearnMyanmar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speak_in_burmese);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Speak in Burmese");
           
            this.setSupportActionBar(toolbar);
        }
        
        lv_speak_burmese = (ListView)findViewById(R.id.lv_speak_burmese);
        //lv_speak_burmese.setDividerHeight(0);
		
		lstMenu = new ArrayList<MenuIcon>();
		lstMenu.add(new MenuIcon(R.drawable.basic,"Basic"));
		lstMenu.add(new MenuIcon(R.drawable.greeting,"Greeting"));
		lstMenu.add(new MenuIcon(R.drawable.eat_drink,"Eat & Drink"));
		lstMenu.add(new MenuIcon(R.drawable.shopping_speak,"Shopping"));
		//lstMenu.add(new MenuIcon(R.drawable.airport,"Airport"));
		lstMenu.add(new MenuIcon(R.drawable.hotel_speak,"Hotel"));
		
		lv_speak_burmese.setAdapter(new SpeakInBurmeseLvAdapter(SpeakInBurmeseActivity.this, lstMenu));
		lv_speak_burmese.setOnItemClickListener(clickListener);
		
		imgLearnMyanmar = (ImageView)findViewById(R.id.img_learn_myanmar);
		imgLearnMyanmar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/app/learn-myanmar/id618066169?mt=8"));
				startActivity(browserIntent);				
			}
		});
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putInt("id", position);
			bundle.putString("name",lstMenu.get(position).getTitle());
			startActivity(new Intent(SpeakInBurmeseActivity.this, SpeakingActivity.class).putExtras(bundle));
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

