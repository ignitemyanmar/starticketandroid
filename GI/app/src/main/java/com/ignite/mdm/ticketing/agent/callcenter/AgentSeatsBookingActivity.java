package com.ignite.mdm.ticketing.agent.callcenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.JsonObject;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.AgentSeatsBookingLvAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentSeatsBooking;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;


@SuppressLint("InlinedApi") public class AgentSeatsBookingActivity extends BaseSherlockActivity{

	private ListView lv_agent_seats_booking;
	private SKConnectionDetector skDetector;
	private ProgressDialog dialog;
	protected List<AgentSeatsBooking> lst_agent_seats_booking;
	protected AgentSeatsBookingLvAdapter agent_seats_adapter;
	
    private NotificationManager myNotificationManager;
    private int notificationIdOne = 111;
    private int notificationIdTwo = 112;
    private int numMessagesOne = 0;
    private int numMessagesTwo = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent_seats_booking);
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            toolbar.setTitle(R.string.strmm_bookseat);
            this.setSupportActionBar(toolbar);
        }
        
        lst_agent_seats_booking = new ArrayList<AgentSeatsBooking>();
		lv_agent_seats_booking = (ListView)findViewById(R.id.lv_agent_seats_booking);
		lv_agent_seats_booking.setDividerHeight(0);
		
		skDetector = SKConnectionDetector.getInstance(this);
		if(skDetector.isConnectingToInternet()){
			getAgentSeatsBooking();
		}else{
			skDetector.showErrorMessage();
		}
	}
	
	private void getAgentSeatsBooking() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(AgentSeatsBookingActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getAgentSeatsBooking(new Callback<List<AgentSeatsBooking>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					Log.e("", "Error URL: "+arg0.getUrl());
					showAlert("Something's Wrong in Server!");
				}
				
				dialog.dismiss();
			}

			public void success(List<AgentSeatsBooking> arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "Success Three Day sales");
				
				if (arg0 != null) {
					
					lst_agent_seats_booking.clear();
					lst_agent_seats_booking.addAll(arg0);
					
					if (lst_agent_seats_booking != null && lst_agent_seats_booking.size() > 0) {
						
						Log.i("", "agent seat order: "+lst_agent_seats_booking.toString());
						agent_seats_adapter = new AgentSeatsBookingLvAdapter(AgentSeatsBookingActivity.this, lst_agent_seats_booking);
						agent_seats_adapter.setmCallback(callback);
						lv_agent_seats_booking.setAdapter(agent_seats_adapter);
						
					}else {
						lv_agent_seats_booking.setAdapter(null);
						showAlert("No List!");
					}
				}else {
					lv_agent_seats_booking.setAdapter(null);
					showAlert("No List!");
				}
				
				dialog.dismiss();
			}
		});		
	}
	
	private AgentSeatsBookingLvAdapter.Callback callback = new AgentSeatsBookingLvAdapter.Callback() {
		
		public void onDeleteClick(Integer pos, String id) {
			// TODO Auto-generated method stub
			if(skDetector.isConnectingToInternet()){
				dialog = ProgressDialog.show(AgentSeatsBookingActivity.this, "", "Please wait ...", true);
				dialog.setCancelable(true);
				
				NetworkEngine.setIP("starticketmyanmar.com");
				NetworkEngine.getInstance().changeStatus("2", id, new Callback<JsonObject>() {

					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "fail");
						}
						
						dialog.dismiss();
					}

					public void success(JsonObject arg0, Response arg1) {
						// TODO Auto-generated method stub
						SKToastMessage.showMessage(AgentSeatsBookingActivity.this, "Deleted", SKToastMessage.SUCCESS);
						dialog.dismiss();
						
						getAgentSeatsBooking();
					}
				});
			}else{
				skDetector.showErrorMessage();
			}
		}
		
		public void onCompleteClick(Integer pos, String id) {
			// TODO Auto-generated method stub
			if(skDetector.isConnectingToInternet()){
				dialog = ProgressDialog.show(AgentSeatsBookingActivity.this, "", "Please wait ...", true);
				dialog.setCancelable(true);
				
				NetworkEngine.setIP("starticketmyanmar.com");
				NetworkEngine.getInstance().changeStatus("1", id, new Callback<JsonObject>() {

					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "fail");
						}
						
						dialog.dismiss();
					}

					public void success(JsonObject arg0, Response arg1) {
						// TODO Auto-generated method stub
						SKToastMessage.showMessage(AgentSeatsBookingActivity.this, "Confirmed", SKToastMessage.SUCCESS);
						dialog.dismiss();
						
						getAgentSeatsBooking();
					}
				});
			}else{
				skDetector.showErrorMessage();
			}
		}
	};
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null && listView.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
   protected void displayPromoNoti() {

	      // Invoking the default notification service
	      NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);	
	 
	      mBuilder.setContentTitle("Star Ticket Agent");
	      mBuilder.setContentText("New Booking from Agents");
	      mBuilder.setTicker("Star Ticket Agent: New Booking");
	      mBuilder.setSmallIcon(R.drawable.ic_launcher);

	      // Increase notification number every time a new notification arrives 
	      
	      mBuilder.setNumber(++numMessagesOne);
	      
	      // Creates an explicit intent for an Activity in your app 
	      Intent resultIntent = new Intent(this, AgentSeatsBookingActivity.class);
	      resultIntent.putExtra("notificationId", notificationIdOne);

	      //This ensures that navigating backward from the Activity leads out of the app to Home page
	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

	      // Adds the back stack for the Intent
	      stackBuilder.addParentStack(AgentSeatsBookingActivity.class);

	      // Adds the Intent that starts the Activity to the top of the stack
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_ONE_SHOT //can only be used once
	         );
	      // start the activity when the user clicks the notification text
	      mBuilder.setContentIntent(resultPendingIntent);

	      myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      // pass the Notification object to the system 
	      myNotificationManager.notify(notificationIdOne, mBuilder.build());
	   }

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
