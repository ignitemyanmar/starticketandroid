package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.ignite.mdm.ticketing.custom.listview.adapter.GalleryPhotoAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.TripDetailItineraryLvAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.TourCompany;
import com.ignite.mdm.ticketing.sqlite.database.model.TourDetail;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import java.util.ArrayList;
import java.util.List;

public class TourDetailActivity extends BaseSherlockActivity{
	private Toolbar toolbar;
	private TextView txt_cost;
	private LinearLayout layout_cost;
	private LinearLayout layout_day_itinerary;
	private TextView txt_trip_detail;
	private TextView txt_photos;
	private ListView lv_day_itinerary;
	private LinearLayout layout_photos;
	private Spinner spn_qty;
	private TextView txt_price;
	private TextView txt_total;
	private List<String> qtyList;
	private String selectedQty;
	@SuppressWarnings("deprecation")
	private Gallery img_gallery;
	private ImageView imgv_photo;
	private Button btn_search;
	private String str_tourCo;
	private TourCompany selectedTourCo;
	private TextView txt_tour_company;
	private TextView txt_trip;
	private TextView txt_seaters;
	private TextView txt_dept_date;
	private TextView txt_days;
	private TextView txt_inclusions;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_detail);
		
		//Bundle bundle = getIntent().getExtras();
		
/*		if (bundle != null) {
			str_tourCo = bundle.getString("tourcompanyactivity.tourcompany");
		}
		
		selectedTourCo = new Gson().fromJson(str_tourCo, TourCompany.class);*/
		
		selectedTourCo = (TourCompany) getIntent().getSerializableExtra("tourCompany");
		
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		
		if (toolbar != null) {
			toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
			setSupportActionBar(toolbar);
		}
		
		txt_tour_company = (TextView)findViewById(R.id.txt_tour_co);
		txt_trip = (TextView)findViewById(R.id.txt_trip);
		txt_seaters = (TextView)findViewById(R.id.txt_seaters);
		txt_dept_date = (TextView)findViewById(R.id.txt_dept_date);
		txt_days = (TextView)findViewById(R.id.txt_days);
		txt_inclusions = (TextView)findViewById(R.id.txt_inclusion);
		
		txt_cost = (TextView)findViewById(R.id.txt_cost);
		txt_trip_detail = (TextView)findViewById(R.id.txt_trip_detail);
		txt_photos = (TextView)findViewById(R.id.txt_photos);
		lv_day_itinerary = (ListView)findViewById(R.id.lv_day_itinerary);
		
		layout_cost = (LinearLayout)findViewById(R.id.layout_cost);
		layout_day_itinerary = (LinearLayout)findViewById(R.id.layout_day_itinerary);
		layout_photos = (LinearLayout)findViewById(R.id.layout_photos);
		
		spn_qty = (Spinner)findViewById(R.id.spn_qty);
		txt_price = (TextView)findViewById(R.id.txt_price);
		txt_total = (TextView)findViewById(R.id.txt_total);
		
		imgv_photo = (ImageView)findViewById(R.id.imgv_photo);
		img_gallery = (Gallery)findViewById(R.id.img_gallery);
		
		//Set Trip Info 
		if (selectedTourCo != null) {
			txt_tour_company.setText(selectedTourCo.getCompanyName());
			txt_trip.setText(selectedTourCo.getFromCity()+" - "+selectedTourCo.getToCity());
			txt_seaters.setText(selectedTourCo.getSeater()+" �?ံု ကား");
			txt_dept_date.setText("Dept: Date - "+selectedTourCo.getDepartDate());
			txt_days.setText("("+selectedTourCo.getNight()+") ညအိပ္  / ("+selectedTourCo.getDay()+") ရက္");
			txt_inclusions.setText("bus"+"+"+selectedTourCo.getHotel()+"+"+selectedTourCo.getFood());
			txt_price.setText(selectedTourCo.getPrice());
		}
		
		btn_search = (Button)findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getBaseContext(), CustomerInfoActivity.class));
			}
		});
		
		final GalleryPhotoAdapter photoAdapter = new GalleryPhotoAdapter(this);
		img_gallery.setAdapter(photoAdapter);
		img_gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				imgv_photo.setImageResource(photoAdapter.mImageIds[position]);
			}
		});
		
		qtyList = new ArrayList<String>();
		qtyList.add("1");
		qtyList.add("2");
		qtyList.add("3");
		qtyList.add("4");
		qtyList.add("5");
		qtyList.add("6");
		qtyList.add("7");
		qtyList.add("8");
		qtyList.add("9");
		qtyList.add("10");
		qtyList.add("+10");
		
		ArrayAdapter<String> qtyAdapter = new ArrayAdapter<String>(TourDetailActivity.this, android.R.layout.simple_dropdown_item_1line
				, qtyList);
		spn_qty.setAdapter(qtyAdapter);
		spn_qty.setOnItemSelectedListener(onItemSelectedListener);
		
		List<TourDetail> tripDetailList = new ArrayList<TourDetail>();
		tripDetailList.add(new TourDetail("Day 1 Yangon - Chaungtha", "After breakfast, proceed by private aircon coach/car to Chaung Tha. Arrive in the afternoon, lunch at Hotel.Enjoy refreshment swimming, sunbathing on the white sandy beach, carefree loitering along the coast and feel the difference away from the rustling and bustling of hectic city life. Overnight in Chaung Tha.", ""));
		tripDetailList.add(new TourDetail("Day 2 Chaung Tha", "Whole day free at leisure. Overnight in Chaung Tha.", ""));
		tripDetailList.add(new TourDetail("Day 3 Chaung Tha", "Free at leisure. Overnight in Chaung Tha.", ""));
		tripDetailList.add(new TourDetail("Day 4 Chaung Tha - Yangon", "Breakfast. Free at leisure. Drive back to Yangon after lunch. Upon arrival at Yangon, transfer to Hotel (or) airport.", ""));
				
		lv_day_itinerary.setAdapter(new TripDetailItineraryLvAdapter(TourDetailActivity.this, tripDetailList));
		
		txt_cost.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_cost.setSelected(true);
				txt_trip_detail.setSelected(false);
				txt_photos.setSelected(false);
				layout_cost.setVisibility(View.VISIBLE);
				layout_day_itinerary.setVisibility(View.GONE);
				layout_photos.setVisibility(View.GONE);
			}
		});
		
		txt_trip_detail.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_cost.setSelected(false);
				txt_trip_detail.setSelected(true);
				txt_photos.setSelected(false);
				layout_cost.setVisibility(View.GONE);
				layout_day_itinerary.setVisibility(View.VISIBLE);
				layout_photos.setVisibility(View.GONE);
			}
		});
		
		txt_photos.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_cost.setSelected(false);
				txt_trip_detail.setSelected(false);
				txt_photos.setSelected(true);
				layout_cost.setVisibility(View.GONE);
				layout_day_itinerary.setVisibility(View.GONE);
				layout_photos.setVisibility(View.VISIBLE);
			}
		});
		
	}
	
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			selectedQty = qtyList.get(position);
			
			if (!selectedQty.equals("+10")) {
				txt_total.setText(""+Integer.valueOf(selectedQty) * Integer.valueOf(txt_price.getText().toString())+".00");
			}else {
				txt_total.setText(" +"+10 * Integer.valueOf(txt_price.getText().toString())+".00");
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		txt_cost.setSelected(true);
		txt_trip_detail.setSelected(false);
		txt_photos.setSelected(false);
		layout_cost.setVisibility(View.VISIBLE);
		layout_day_itinerary.setVisibility(View.GONE);
		layout_photos.setVisibility(View.GONE);
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
