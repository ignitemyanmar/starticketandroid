package com.ignite.mm.ticketing.starticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ignite.mm.ticketing.application.BaseActivity;

public class AboutActivity extends BaseActivity{
private TextView txt_web_link;
private TextView txt_facebook_link;

@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Show View to search for FromCity Names, ToCity Names, Trip Times, Trip Date by (one way) or (round trip) 
		setContentView(R.layout.activity_about);
		
		//Title Toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("About Us");
            this.setSupportActionBar(toolbar);
        }
        
        txt_web_link = (TextView)findViewById(R.id.txt_web_link);
        txt_facebook_link = (TextView)findViewById(R.id.txt_facebook_link);
        
        txt_web_link.setOnClickListener(clickListener);
        txt_facebook_link.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == txt_web_link) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://starticket.com.mm/")));
			}
			if (v == txt_facebook_link) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/starticketmm/")));
			}
		}
	};

	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
