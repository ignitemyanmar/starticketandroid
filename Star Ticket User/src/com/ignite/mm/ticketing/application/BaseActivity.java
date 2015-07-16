package com.ignite.mm.ticketing.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ignite.mm.ticketing.sqlite.database.model.PermissionGlobal;
import com.ignite.mm.ticketing.user.BusBookingListActivity;
import com.ignite.mm.ticketing.user.BusReveiwActivity;
import com.ignite.mm.ticketing.user.R;
import com.ignite.mm.ticketing.user.ThreeDaySalesActivity;
import com.ignite.mm.ticketing.user.UserProfileActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class BaseActivity extends Activity{

	public LoginUser AppLoginUser;
	public PermissionGlobal AppPermission;
	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.ignite.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	
	private static IntentFilter createIntentFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}
	
	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}
	
	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
	}
	
	public class BaseActivityReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)){
				finish();
			}
		}
	} 
	
	protected void closeAllActivities(){
		//sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		registerBaseActivityReceiver();
		AppLoginUser = new LoginUser(this);
		AppPermission = new PermissionGlobal(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
/*		if(AppLoginUser.isExpires()){
			closeAllActivities();
		}*/
		super.onResume();
	}
	
	protected String getToday(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		Log.i("","Hello Today: "+formattedDate);
		return formattedDate;
	}
	
	protected String getTodayTime(){
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat writeFormat = new SimpleDateFormat("hh:mm aa");
		Calendar cal = Calendar.getInstance();
		Date tTime = null;
		try {
			tTime = sdf2.parse(sdf2.format(cal.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String todayTime = writeFormat.format(tTime);
		Log.i("", "today time: "+todayTime);
		return todayTime;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menu_logout) {
			AppLoginUser.logout();
        	closeAllActivities();
        	return true;
		}else {
			return false;  
		}
		
	    /*switch(item.getItemId()) {
	        case R.id.menu_logout:
	        	AppLoginUser.logout();
	        	closeAllActivities();
	        	return true;
   	   	}
		return false; */ 
		
	 }
	
	public static String changeDate(String date){
		Log.i("", "to change date: "+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("", "start date: "+StartDate);
		return DateFormat.format("dd-MM-yyyy", StartDate).toString();
	}
	
	public static String changeDateString(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DateFormat.format("dd-MMMM-yyyy", StartDate).toString();
	}
	
	public void showAlert(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		//alert.setIcon(R.drawable.attention_icon);
		//alert.setTitle("Warning");
		alert.setMessage(message);
		alert.show();
	}
	
	protected void alertDialog(String MSG, OnClickListener YES, OnClickListener NO){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(MSG);
		if(YES != null){
			alertDialog.setButton(Dialog.BUTTON_POSITIVE, "YES", YES);
		}else{
			alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
		}
		if(NO != null){
			alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "NO", NO);
		}
		alertDialog.show();
	}
	
	protected void getFloatingMenu() {
		// TODO Auto-generated method stub
		 // Set up the white button on the lower right corner
        // more or less with default parameter
       /* final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                                                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                                                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                                                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                                                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                                                .attachTo(rightLowerButton)
                                                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });*/

        // Set up the large red button on the center right side
        // With custom button and content sizes and margins
        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
        int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);
            
        ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));

        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        starParams.setMargins(redActionButtonMargin,
                              redActionButtonMargin,
                              redActionButtonMargin,
                              redActionButtonMargin);
        fabIconStar.setLayoutParams(starParams);

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);
        fabIconStarParams.setMargins(redActionButtonContentMargin,
                                    redActionButtonContentMargin,
                                    redActionButtonContentMargin,
                                    redActionButtonContentMargin);

        final FloatingActionButton leftCenterButton = new FloatingActionButton.Builder(this)
                                                .setContentView(fabIconStar, fabIconStarParams)
                                                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                                                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                                                .setLayoutParams(starParams)
                                                .build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
        lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin);
        lCSubBuilder.setLayoutParams(blueContentParams);
        // Set custom layout params
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

        ImageView lcIcon1 = new ImageView(this);
        ImageView lcIcon2 = new ImageView(this);
        ImageView lcIcon3 = new ImageView(this);
        ImageView lcIcon4 = new ImageView(this);
        ImageView lcIcon5 = new ImageView(this);

        lcIcon1.setImageDrawable(getResources().getDrawable(R.drawable.bus));
        lcIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_order));
        lcIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_booking));
        lcIcon4.setImageDrawable(getResources().getDrawable(R.drawable.me));

        SubActionButton ic_bus = lCSubBuilder.setContentView(lcIcon1, blueContentParams).build();
        SubActionButton ic_order = lCSubBuilder.setContentView(lcIcon2, blueContentParams).build();
        SubActionButton ic_booking =  lCSubBuilder.setContentView(lcIcon3, blueContentParams).build();
        SubActionButton ic_me = lCSubBuilder.setContentView(lcIcon4, blueContentParams).build();
        
        // Build another menu with custom options
        final FloatingActionMenu leftCenterMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(ic_bus)
                .addSubActionView(ic_order)
                .addSubActionView(ic_booking)
                .addSubActionView(ic_me)
                .attachTo(leftCenterButton)
                .build();                
        
        ic_bus.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), BusReveiwActivity.class));
			}
		});
        
        ic_order.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ThreeDaySalesActivity.class));
			}
		});
        
        ic_booking.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
			}
		});
        
        ic_me.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
			}
		});
        
    }
}
