
package com.ignite.mm.ticketing.starticket;

import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OrderListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Booking;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;
import com.ignite.mm.ticketing.starticket.R;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #BusBookingListActivity} is the class to show Booking List 
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * (2) {@link #getBookingListByUser()}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class BusBookingListActivity extends BaseActivity {
	private ListView lv_booking_list;
	private List<CreditOrder> credit_list;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private Button btn_search;
	private String BookCode = "";
	private EditText auto_txt_codeno;
	private Button btn_search_codeno;
	private TextView action_bar_title2;
	private SKConnectionDetector connectionDetector;
	private String operatorId;
	private String book_code;
	//protected List<Booking> bookingList;
	protected List<Booking> bookingListByUser;
	private String tripId = "";
	private String intents;
	private LinearLayout layout_booking_cancel;
	private View view_cover;
	private TextView txt_expire_time;
	private RelativeLayout layout_noBooking;
	protected ZProgressHUD dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			tripId  =  bundle.getString("trip_id");
			intents  =  bundle.getString("from_intent");
		}
		
		setContentView(R.layout.activity_bus_booking_list);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Booking စာရင္း  ");
            this.setSupportActionBar(toolbar);
        }
        
        layout_noBooking = (RelativeLayout)findViewById(R.id.layout_noBooking);
        
		auto_txt_codeno = (EditText)findViewById(R.id.auto_txt_codeno);
		btn_search_codeno = (Button)findViewById(R.id.btn_search_codeno);
		
		layout_booking_cancel = (LinearLayout)findViewById(R.id.layout_booking_cancel);
		view_cover = (View)findViewById(R.id.view_cover);
		
		Log.i("", "Book Code to search: "+BookCode);		
		
		lv_booking_list = (ListView) findViewById(R.id.lv_booking_list);

		connectionDetector = SKConnectionDetector.getInstance(this);
		if(connectionDetector.isConnectingToInternet()){
			getBookingListByUser();
		}else{
			Toast.makeText(BusBookingListActivity.this, "No Network Connection", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 *  close the activity
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	/**
	 *  Get Booking List (filter) By User
	 */
	private void getBookingListByUser() {
		// TODO Auto-generated method stub
		dialog = new ZProgressHUD(BusBookingListActivity.this);
		dialog.show();
        
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getBookingListByUser(AppLoginUser.getAccessToken(), ""
				, tripId, AppLoginUser.getId(), new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail : "+arg0.getResponse().getStatus());
				}
				
				if (dialog != null) {
					dialog.dismissWithFailure();
				}
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					bookingListByUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Booking>>(){}.getType());
					
					if (bookingListByUser != null && bookingListByUser.size() > 0) {
						
						Log.i("", "Booking list by user: "+bookingListByUser.toString());
						
						for (int i = 0; i < bookingListByUser.size(); i++) {
							String changeDate = changeDateString(bookingListByUser.get(i).getDepartureDate());
							Booking book = (Booking)bookingListByUser.get(i);
							book.setDepartureDate(changeDate);
						}
						
						layout_booking_cancel.setVisibility(View.VISIBLE);
						//view_cover.setVisibility(View.VISIBLE);
						lv_booking_list.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, bookingListByUser));
						lv_booking_list.setDividerHeight(0);
					}else {
						layout_noBooking.setVisibility(View.VISIBLE);
						lv_booking_list.setAdapter(null);
					}
				}else {
					layout_noBooking.setVisibility(View.VISIBLE);
					lv_booking_list.setAdapter(null);
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//For Google Analytics
		EasyTracker.getInstance(this).activityStart(this);
		
		//For Google Analytics
		Tracker v3Tracker = GoogleAnalytics.getInstance(this).getTracker("UA-67985681-1");

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		v3Tracker.set(Fields.SCREEN_NAME, "My Booking Screen, "
				+AppLoginUser.getUserName());
		
		// This screenview hit will include the screen name.
		v3Tracker.send(MapBuilder.createAppView().build());
	}
}
