package com.ignite.mm.ticketing.starticket;

import java.util.ArrayList;
import java.util.List;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.TopTenAttractionLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.MenuIcon;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TopTenAttractionsActivity extends BaseActivity {
	private List<MenuIcon> lstMenu;
	private ListView lv_top_ten;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topten_attractions);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Top Ten Attractions");
           
            this.setSupportActionBar(toolbar);
        }
        
        lv_top_ten = (ListView)findViewById(R.id.lv_top_ten);
        lv_top_ten.setDividerHeight(0);
		
		lstMenu = new ArrayList<MenuIcon>();
		lstMenu.add(new MenuIcon(R.drawable.shwedagon_05, "Shwedagon Pagoda"));
		lstMenu.add(new MenuIcon(R.drawable.bagan_02, "Bagan"));
		
		lstMenu.add(new MenuIcon(R.drawable.inle_01, "Inle Lake"));
		lstMenu.add(new MenuIcon(R.drawable.mandalay_02, "Mandalay"));
		lstMenu.add(new MenuIcon(R.drawable.putao_01, "Putao"));
		lstMenu.add(new MenuIcon(R.drawable.marauk_u_01, "Marauk U"));
		lstMenu.add(new MenuIcon(R.drawable.ngapali_01, "Ngapali Beach"));
		lstMenu.add(new MenuIcon(R.drawable.ngwe_saung_01, "Ngwe Saung Beach"));
		lstMenu.add(new MenuIcon(R.drawable.golden_rock_01, "Golden Rock (Kyaikhtiyo Pagoda)"));
		lstMenu.add(new MenuIcon(R.drawable.kawthaung_02, "Kaw Thaung"));
		
		lv_top_ten.setAdapter(new TopTenAttractionLvAdapter(TopTenAttractionsActivity.this, lstMenu));
		lv_top_ten.setOnItemClickListener(clickListener);
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putString("city", lstMenu.get(position).getTitle());
			if(position == 0){
				bundle.putString("url", "topten_shwedagonpagoda.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 1){
				bundle.putString("url", "topten_bagan.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 2){
				bundle.putString("url", "topten_inle.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 3){
				bundle.putString("url", "topten_mandalay.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 4){
				bundle.putString("url", "topten_putao.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			
			if(position == 5){
				bundle.putString("url", "topten_marauku.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 6){
				bundle.putString("url", "topten_ngapali_beach.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 7){
				bundle.putString("url", "topten_ngwe_saung_beach.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 8){
				bundle.putString("url", "topten_golden_rock.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
			}
			if(position == 9){
				bundle.putString("url", "topten_kawthaung.html");
				startActivity(new Intent(TopTenAttractionsActivity.this,DetailWebViewActivity.class).putExtras(bundle));
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
