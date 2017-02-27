package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.ignite.mdm.ticketing.custom.listview.adapter.TourCompanyLvAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.TourCompany;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import java.util.ArrayList;
import java.util.List;

public class TourCompanyActivity extends BaseSherlockActivity{
	private ListView lv_tour_company;
	private List<TourCompany> tour_company_list;
	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_company);
		
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		
		if (toolbar != null) {
			toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
			setSupportActionBar(toolbar);
		}
		
		lv_tour_company = (ListView)findViewById(R.id.lv_tour_company);
		
		tour_company_list = new ArrayList<TourCompany>();
		tour_company_list.add(new TourCompany("Myawaddy Travels & Tour", "ရန္ကုန္", "မ�?�?�ေလး", "2", "3", "29"
				, "30000", "Test Test Test", "12 Mar, 16", "hotel", "food"));
		tour_company_list.add(new TourCompany("OK Travels & Tour", "ရန္ကုန္", "အင္းေလး - ေ�?ာင္ႀကီး", "3", "4", "45"
				, "60000", "Test Test Test", "15 Mar, 16", "hotel", "food"));
		tour_company_list.add(new TourCompany("ABC Travels & Tour", "ရန္ကုန္", "ပုဂံ-ေညာင္ဦး", "2", "3", "29"
				, "70000", "Test Test Test", "12 Mar, 16", "hotel", "food"));
		tour_company_list.add(new TourCompany("DEF Travels & Tour", "ရန္ကုန္", "အင္းေလး - ေ�?ာင္ႀကီး", "3", "4", "45"
				, "80000", "Test Test Test", "15 Mar, 16", "hotel", "food"));
		tour_company_list.add(new TourCompany("GHI Travels & Tour", "ရန္ကုန္", "ပုဂံ-ေညာင္ဦး", "2", "3", "29"
				, "40000", "Test Test Test", "12 Mar, 16", "hotel", "food"));
		tour_company_list.add(new TourCompany("JKL Travels & Tour", "ရန္ကုန္", "အင္းေလး - ေ�?ာင္ႀကီး", "3", "4", "45"
				, "90000", "Test Test Test", "15 Mar, 16", "hotel", "food"));
		
		lv_tour_company.setAdapter(new TourCompanyLvAdapter(TourCompanyActivity.this, tour_company_list));
		lv_tour_company.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Bundle bundle = new Bundle();
				//bundle.putString("tourcompanyactivity.tourcompany", new Gson().toJson(tour_company_list.get(position)));
				
				startActivity(new Intent(TourCompanyActivity.this, TourDetailActivity.class)
				.putExtra("tourCompany", tour_company_list.get(position)));
			}
		});
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
