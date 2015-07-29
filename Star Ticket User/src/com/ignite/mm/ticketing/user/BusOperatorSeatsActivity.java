package com.ignite.mm.ticketing.user;

import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorSeatsAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.SearchOperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.Trip;
import com.ignite.mm.ticketing.user.R;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class BusOperatorSeatsActivity extends BaseActivity{
	private String selectedFromCity = "";
	private String selectedToCity = "";
	private String selectedTripDate = "";
	private String selectedTripTime = "";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private ListView lv_operator_seats;
	private ZProgressHUD dialog;
	private List<OperatorSeat> OperatorSeats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_show_operator_seats);
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			selectedFromCity = bundle.getString("from_city");
			selectedToCity = bundle.getString("to_city");
			selectedTripDate = bundle.getString("trip_date");
			selectedTripTime = bundle.getString("trip_time");
		}
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            
    		if (selectedTripTime != null) {
    			if (!selectedTripDate.equals("") && selectedTripDate != null) {
    				if (!selectedTripTime.equals("") && selectedTripTime != null) {
    					toolbar.setTitle(selectedFromCity+" - "+selectedToCity);
    					toolbar.setSubtitle(changeDate(selectedTripDate)+" ["+selectedTripTime+"]");
    				}else if (selectedTripTime.equals("") || selectedTripTime == null)
    				{
    					toolbar.setTitle(selectedFromCity+" - "+selectedToCity);
    					toolbar.setSubtitle(changeDate(selectedTripDate)+" [All Time]");
    				}
    			}else {
    				toolbar.setTitle("00/00/0000 [ 00:00:00 ]");
    			}
    		}
    		
            this.setSupportActionBar(toolbar);
        }
		
		lv_operator_seats = (ListView)findViewById(R.id.lv_operator_seats);
		lv_operator_seats.setDividerHeight(0);
		lv_operator_seats.setOnItemClickListener(clickListener);
		
		SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			
			getOperatorSeats();
		}else{
			skDetector.showErrorDialog();
		}
	}
	
	@Override
	public Intent getParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getParentActivityIntent();
	}

	private void getOperatorSeats() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(BusOperatorSeatsActivity.this);
		dialog.show();
		
		Log.i("", "Search Operator: "+selectedFromCity+", "
						+selectedToCity+", "
						+selectedTripDate+", "
						+selectedTripTime+", access: "+AppLoginUser.getAccessToken());
		
		NetworkEngine.setIP("test.starticketmyanmar.com");
		NetworkEngine.getInstance().postSearch(selectedFromCity, selectedToCity, selectedTripDate
				, selectedTripTime, "", new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					OperatorSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorSeat>>(){}.getType());
					
					if (OperatorSeats != null && OperatorSeats.size() > 0) {
						
						lv_operator_seats.setAdapter(new OperatorSeatsAdapter(BusOperatorSeatsActivity.this, OperatorSeats));
						setListViewHeightBasedOnChildren(lv_operator_seats);
					}else {
						alertDialog("သင္ ရွာေဖြ ေသာ ခရီးစဥ္ သည္ လက္ မွတ္ မ်ား ကုန္ သြား ပါသျဖင့္ အျခားေန႔ ေရြးၿပီး ရွာ ႏုိင္ ပါသည္။"
								, "Back", "", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										finish();
									}
								}, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										//dialog.dismiss();
									}
								});
					}
				}
				
				dialog.dismissWithSuccess();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismissWithFailure();
			}
		});
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putString("operator_id", OperatorSeats.get(position).getOperatorId());
			bundle.putString("operator_name", OperatorSeats.get(position).getOperator());
			bundle.putString("tripId", OperatorSeats.get(position).getId());
			bundle.putString("from_city", OperatorSeats.get(position).getFromName());
			bundle.putString("to_city", OperatorSeats.get(position).getToName());
			bundle.putString("trip_date", selectedTripDate);
			bundle.putString("trip_time", OperatorSeats.get(position).getTime());
			
			startActivity(new Intent(BusOperatorSeatsActivity.this, BusSelectSeatActivity.class).putExtras(bundle));
		}
	};
	
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}	
}
