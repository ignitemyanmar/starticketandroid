package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.SpeakingListAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Speaking;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SpeakingActivity extends BaseActivity {
	private ListView lvSpeaking;
	private Map<Integer, ArrayList<Speaking>> mapSpecking;
	private ArrayList<Speaking> lstSpeakings;
	private ArrayList<Speaking> lstSpeakingBasic;
	private ArrayList<Speaking> lstSpeakingEatDrink;
	private ArrayList<Speaking> lstSpeakingGreeting;
	private ArrayList<Speaking> lstSpeakingShopping;
	private ArrayList<Speaking> lstSpeakingAirport;
	private ArrayList<Speaking> lstSpeakingHotel;
	private int id;
	private String name;
	protected MediaPlayer mMedia;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speaking);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			id = extras.getInt("id");
			name = extras.getString("name");
		}
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Speaking of " + name);
           
            this.setSupportActionBar(toolbar);
        }
        
		lstSpeakingBasic = new ArrayList<Speaking>();
		lstSpeakingBasic.add(new Speaking("Do you speak English?","in:-gə-lei'-lo-pyɔ-ta'-la:",R.raw.f_cat_a_013));
		lstSpeakingBasic.add(new Speaking("Yes","ho'-ke̥",R.raw.f_cat_b_001));
		lstSpeakingBasic.add(new Speaking("Thank you","kyei:-zu:-tin-ba-de",R.raw.f_cat_b_006));
		lstSpeakingBasic.add(new Speaking("When?","be-ə-chein-le:",R.raw.f_cat_b_029));
		lstSpeakingBasic.add(new Speaking("How many/How much?","be-lau'-le:",R.raw.f_cat_b_032));
		lstSpeakingBasic.add(new Speaking("Excuse me","tə-hsei'-lau'",R.raw.f_cat_b_046));
		lstSpeakingBasic.add(new Speaking("Do you understand?","na:-le-dhə-la:",R.raw.f_cat_b_066));
		lstSpeakingBasic.add(new Speaking("It is good","kaun:-de",R.raw.f_cat_b_078));
		lstSpeakingBasic.add(new Speaking("Where is the (e.g., hotel)?"," ... be-hma-le:",R.raw.f_cat_d_021));
		lstSpeakingBasic.add(new Speaking("Where is the toilet?","ein-tha-be-hma-le:",R.raw.f_cat_d_022));
		lstSpeakingBasic.add(new Speaking("Where is the nearest train station?","ə-ni:-zon:-bu-da-yon-gḁ-be-na:-hma-le:",R.raw.f_cat_e_009));
		lstSpeakingBasic.add(new Speaking("Can I use your phone?","hpon:-thon:-lo̥-yḁ-mə-la:",R.raw.f_cat_e_027));
		lstSpeakingBasic.add(new Speaking("How much is the fare?","ka:-gḁ-be-lau'-jḁ-le:",R.raw.f_cat_e_029));
		lstSpeakingBasic.add(new Speaking("Take me to Shwedagon","shwei-də-gon-hpə-ya:-go-maun:-ba",R.raw.f_cat_e_034));
		lstSpeakingBasic.add(new Speaking("Where is the nearest police station?","ə-ni:-zon:-ye:-sə-hkan:-be-hma-le:",R.raw.f_cat_j_004));
		lstSpeakingBasic.add(new Speaking("Where is the hospital?","hsei:-yon-be-hma-le:",R.raw.f_cat_j_009));
		lstSpeakingBasic.add(new Speaking("I lost my bag","ei'-pyau'-thwa:-de",R.raw.f_cat_j_023));
		
		lstSpeakingGreeting = new ArrayList<Speaking>();
		lstSpeakingGreeting.add(new Speaking("Hello","min-gə-la-ba",R.raw.f_cat_a_001));
		lstSpeakingGreeting.add(new Speaking("How are you","nei-kaun:-la:",R.raw.f_cat_a_002));
		lstSpeakingGreeting.add(new Speaking("I'm fine","kaun:-ba-te",R.raw.f_cat_a_003));
		
		lstSpeakingEatDrink = new ArrayList<Speaking>();
		lstSpeakingEatDrink.add(new Speaking("Vegetarian","the'-tha'-lu'",R.raw.f_cat_f_022));
		lstSpeakingEatDrink.add(new Speaking("I like Burmese food","bə-ma-ə-sa:-ə-sa-go-jai'-de",R.raw.f_cat_f_033));
		lstSpeakingEatDrink.add(new Speaking("Can I have a menu?","mi-nyu:-yḁ-nain-mə-la:",R.raw.f_cat_f_039));
		lstSpeakingEatDrink.add(new Speaking("I'm allergic to …","... ne̥-mə-te̥-bu:",R.raw.f_cat_f_043));
		lstSpeakingEatDrink.add(new Speaking("Delicious","ə-yḁ-tha-shi̥-de", R.raw.f_cat_f_052));
		
		lstSpeakingShopping = new ArrayList<Speaking>();
		lstSpeakingShopping.add(new Speaking("Can you make it cheaper?","zei:-shɔ̥-pei:-ba-on:",R.raw.f_cat_l_004));
		lstSpeakingShopping.add(new Speaking("It's too expensive","ə-yan:-zei:-ji:-de",R.raw.f_cat_l_009));
		lstSpeakingShopping.add(new Speaking("I am looking for ... to buy","… we-bo̥-sha-nei-da",R.raw.f_cat_l_029));
		lstSpeakingShopping.add(new Speaking("How much does it cost?","da-be-lau'-jḁ-le:",R.raw.f_cat_l_102));
		lstSpeakingShopping.add(new Speaking("I like it","jai'-te",R.raw.f_cat_l_103));
	
		lstSpeakingAirport = new ArrayList<Speaking>();
		
		lstSpeakingHotel = new ArrayList<Speaking>();
		lstSpeakingHotel.add(new Speaking("Where is the nearest hotel?","ə-ni:-zon:-ho-te-be-hma-le:",R.raw.f_cat_h_006));
		lstSpeakingHotel.add(new Speaking("I want to check-in","sə-yin:-thwin:-�?in-de",R.raw.f_cat_h_028));
		
		mapSpecking = new HashMap<Integer, ArrayList<Speaking>>();
		mapSpecking.put(0, lstSpeakingBasic);
		mapSpecking.put(1, lstSpeakingGreeting);
		mapSpecking.put(2, lstSpeakingEatDrink);
		mapSpecking.put(3, lstSpeakingShopping);
		mapSpecking.put(4, lstSpeakingAirport);
		mapSpecking.put(5, lstSpeakingHotel);
			
		
		lvSpeaking = (ListView)findViewById(R.id.list_speaking);
		lstSpeakings = mapSpecking.get(id);
		if(lstSpeakings.size() > 0){
			lvSpeaking.setAdapter(new SpeakingListAdapter(this, lstSpeakings));
			lvSpeaking.setOnItemClickListener(itemClickListener);
		}	
		
	}
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			mMedia = MediaPlayer.create(SpeakingActivity.this, lstSpeakings.get(position).getSound());
			mMedia.start();
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
