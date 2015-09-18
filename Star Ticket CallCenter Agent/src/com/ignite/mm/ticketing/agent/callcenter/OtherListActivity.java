package com.ignite.mm.ticketing.agent.callcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;

public class OtherListActivity extends BaseSherlockActivity {
	
	/*private  ArrayList<Other> other;
	private ListView lstMenu;*/
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("OTHER");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bundle = new Bundle();
		bundle.putInt("Selected",1);
		Intent nextScreen = new Intent(OtherListActivity.this, MainFragment.class).putExtras(bundle);
		startActivity(nextScreen);
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}

		}
	};
}