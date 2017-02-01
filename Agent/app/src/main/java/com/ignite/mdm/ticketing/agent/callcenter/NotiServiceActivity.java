package com.ignite.mdm.ticketing.agent.callcenter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.sqlite.database.model.NotiCounts;
import com.ignite.mm.ticketing.application.LoginUser;
import com.smk.skconnectiondetector.SKConnectionDetector;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class NotiServiceActivity extends Service{

    final static String ACTION = "NotifyServiceAction";
  //  final static String STOP_SERVICE_BROADCAST_KEY="StopServiceBroadcastKey";
   // final static int RQS_STOP_SERVICE = 1;
    
	MyPhoneReceiver notifyServiceReceiver;

	@Override
	    public void onCreate() {
			notifyServiceReceiver = new MyPhoneReceiver();
	        super.onCreate();
	    }
	 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
	    // START YOUR TASKS
		 IntentFilter intentFilter = new IntentFilter();
	     intentFilter.addAction(ACTION);
	     registerReceiver(notifyServiceReceiver, intentFilter);
	     
	  // Send Notification
	  // define sound URI, the sound to be played when there's a notification
			Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			
			// build notification
			// the addAction re-use the same intent to keep the example short
			NotificationCompat.Builder notiBuilder  = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle("Star Ticket")
			        .setContentText("New Delivery! Pls send ticket to home ...")
			        .setAutoCancel(true)
			        .setTicker("New Delivery! Pls send ticket to home ...")
			        .setSound(soundUri);
			
			// Creates an explicit intent for an Activity in your app
			Intent myIntent = new Intent(this, DeliveryActivity.class);
		      
			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(DeliveryActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(myIntent);
			
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
			notiBuilder.setContentIntent(resultPendingIntent);
			
			//Notification Show 
			NotificationManager notificationManager = 
			  (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			
				// mId allows you to update the notification later on.
			notificationManager.notify(0, notiBuilder.build());
			
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
	    // STOP YOUR TASKS
		this.unregisterReceiver(notifyServiceReceiver);
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class MyPhoneReceiver extends BroadcastReceiver {
		private SKConnectionDetector skDetector;
		private Integer noti_booking_count = 0;
		private Integer noti_delivery_count = 0;
		private Integer noti_agentorder_count = 0;
		
	  @Override
		  public void onReceive(Context context, Intent intent) {
	      	if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
				  skDetector = SKConnectionDetector.getInstance(context);
					//Get Notification Counts
					if(skDetector.isConnectingToInternet()){
						//Get Booking Noti + Delivery Noti + Seat Booking Noti (Counts)
						//getNotiCounts(context);
					}else{
						//Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
				  }
	          }
		  }
	  
		private void getNotiCounts(final Context context) {
			// TODO Auto-generated method stub
			//dialog = ProgressDialog.show(HomeNewActivity.this, "", "Loading Noti...", true);
			//dialog.setCancelable(true);
			
			NetworkEngine.setIP("starticketmyanmar.com");
			NetworkEngine.getInstance().getNotiCounts(new Callback<NotiCounts>() {
				
				public void success(NotiCounts arg0, Response arg1) {
					// TODO Auto-generated method stub
					if (arg0 != null) {
						
						//Log.i("", "Noti obj: "+arg0.toString());
						noti_booking_count = arg0.getBookingnoti_count();
						noti_delivery_count = arg0.getDeliverynoti_count();
						noti_agentorder_count = arg0.getAgentordernoti_count();
						
						/*//if (context != null) {
							//if (Integer.valueOf(userRole) <= 3) {
								//If Agents
								//Show Noti Booking
								if (noti_booking_count > 0) {
									
									
								}
							}else {*/
								//If CallCenter
								if (noti_delivery_count > 0) {
									Intent serviceIntent = new Intent("com.ignite.mm.ticketing.starticketnew.NotiServiceActivity");
							        context.startService(serviceIntent);	
								}
								
							//}
						//}
					}else {
						//Log.i("", "Noti obj is null");
					}
					
/*					if (dialog != null) {
						dialog.dismiss();
					}*/
				}
				
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					if (arg0.getResponse() != null) {
						//Log.i("", "Error: "+arg0.getResponse().getStatus());
					}
					
/*					if (dialog != null) {
						dialog.dismiss();
					}*/
				}
			});
		}
	} 

}
