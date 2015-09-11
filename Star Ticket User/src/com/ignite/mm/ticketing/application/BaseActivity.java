package com.ignite.mm.ticketing.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.ignite.mm.ticketing.starticket.BusBookingListActivity;
import com.ignite.mm.ticketing.starticket.BusReveiwActivity;
import com.ignite.mm.ticketing.starticket.R;
import com.ignite.mm.ticketing.starticket.ThreeDaySalesActivity;
import com.ignite.mm.ticketing.starticket.UserLogin;
import com.ignite.mm.ticketing.starticket.UserProfileActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * {@link #BaseActivity} is base class(parent activity) that can be called from other activities. 
 * Usable methods 
 * (1) {@link #closeAllActivities()} 
 * (2) {@link #getToday()} 
 * (3) {@link #getTodayTime()}
 * (4) {@link #callHotLine(String)}  
 * (5) {@link #changeDate(String)}
 * (6) {@link #changeDateString(String)}
 * (7) {@link #showAlert(String)}
 * (8) {@link #getFloatingMenu()}
 * (8) {@link #alertDialog(String, String, String, OnClickListener, OnClickListener)}     
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class BaseActivity extends ActionBarActivity{

	public LoginUser AppLoginUser;
	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.ignite.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	
	/**
	 *  To filter activities to register
	 * @return IntentFilter
	 */
	private static IntentFilter createIntentFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}
	
	/**
	 * Register activities that extends this base class.
	 * If so, it includes to close, when we call {@link #closeAllActivities()}.
	 */
	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}
	
	/**
	 * UnRegister activities that extends this base class.
	 */
	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
	}
	
	/**
	 * Finish (or) close the activities that register 
	 *
	 */
	public class BaseActivityReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)){
				finish();
			}
		}
	} 
	
	/**
	 * Close all activities that extend this base class
	 */
	protected void closeAllActivities(){
		sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
	}
	
	/**
	 * {@link #onCreate} works when an activity starts to load.
	 * 1) {@link #registerBaseActivityReceiver}
	 * 2) create {@link LoginUser} object only one time in base class 
	 * to get User's Information
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		registerBaseActivityReceiver();
		AppLoginUser = new LoginUser(this);
	}
    
	/**
	 * {@link #onResume} works when the activity is come back again
	 * 1) create {@link LoginUser} object again
	 * to get User's Information
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		AppLoginUser = new LoginUser(this);
		Log.i("", "Username(Resume): "+AppLoginUser.getId());
		
		/*if(AppLoginUser.isExpires()){
			closeAllActivities();
		}*/
		super.onResume();
	}
	
	/**
	 * Get Today Date eg. 2015-09-10
	 * @return return today date (String)
	 */
	protected String getToday(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		Log.i("","Hello Today: "+formattedDate);
		return formattedDate;
	}
	
	/**
	 * Get Current Time eg. 03:00 PM
	 * @return return current time (String)
	 */
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
	
	/**
	 * Create Menu upper right corner of all activities 
	 * to get Star Ticket's CallCenter Phones and About
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	/**
	 * When menu items are clicked (upper right corner of all activities), 
	 * call star ticket's Call Center Phones and check About Info
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
	      
	    switch(item.getItemId()) {
	        case R.id.menu_hotLine:
	        	callHotLine("0931166772");
	        	return true;
	        case R.id.menu_hotline2:
	        	callHotLine(item.getTitle().toString());
	        	return true;
	        case R.id.menu_hotline3:
	        	callHotLine(item.getTitle().toString());
	        	return true;
   	   	}
		return false;
	 }
	
	/**
	 *  Call Star Ticke't HotLines when {@code phoneNo} is clicked.
	 * @param phoneNo Phone No (String) to make call
	 */
	protected void callHotLine(String phoneNo) {
		// TODO Auto-generated method stub
		  String hotPh = "tel:" + phoneNo.trim() ;
		  Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse(hotPh));
	      try{
	         startActivity(in);
	      }
	      catch (android.content.ActivityNotFoundException ex){
	         Toast.makeText(getApplicationContext(),"Not founded",Toast.LENGTH_SHORT).show();
	      }
	}
	
	/**
	 * Change Date Format from yyyy-MM-dd to dd-MM-yyyy
	 * @param date Date(String) 2015-09-10
	 * @return Date(String) 10-09-2015
	 */
	protected static String changeDate(String date){
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
	
	/**
	 * Change Date Format from yyyy-MM-dd to dd-MMMM-yyyy
	 * @param date Date(String) 2015-09-10
	 * @return Date(String) 10-Sept-2015
	 */
	protected static String changeDateString(String date){
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
	
	/**
	 * Show Alert Dialog 
	 * @param message Message to show in Alert Dialog
	 */
	protected void showAlert(String message) {
		// TODO Auto-generated method stub
		
/*			final MaterialDialog mMaterialDialog = new MaterialDialog(this))
		   // .setTitle("MaterialDialog")
		   // .setMessage("Hello world!")
		    .setPositiveButton("OK", new View.OnClickListener() {
		        public void onClick(View v) {
		            mMaterialDialog.dismiss();
		        }
		    })
		    .setNegativeButton("CANCEL", new View.OnClickListener() {
		        public void onClick(View v) {
		            mMaterialDialog.dismiss();
		        }
		    });
	
		mMaterialDialog.show();
	
		// You can change the message anytime. before show
		//mMaterialDialog.setTitle(message);
		mMaterialDialog.show();
		// You can change the message anytime. after show
		mMaterialDialog.setMessage(message);*/
	
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		//alert.setIcon(R.drawable.attention_icon);
		//alert.setTitle("Warning");
		alert.setMessage(message);
		
		alert.show();
	}
	
	/**
	 *  Get floating Menu (Me,Booking,Order)
	 */
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
      //  int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);
            
        ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        
       /* TextView fabIconStar = new TextView(this);
        
        fabIconStar.setText("Menu");
        fabIconStar.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.f);
        fabIconStar.setTextColor(Color.GRAY);
        fabIconStar.setGravity(Gravity.CENTER_HORIZONTAL);
        fabIconStar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_launcher, 0, 0);
        fabIconStar.setCompoundDrawablePadding(0);*/

        //Menu 
        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        starParams.setMargins(redActionButtonMargin,
                              redActionButtonMargin,
                              redActionButtonMargin,
                              redActionButtonMargin);
        fabIconStar.setLayoutParams(starParams);

        //Menu
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
        //Menu
        SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
        lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        //Sub Menu Content Margin
        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin);
        lCSubBuilder.setLayoutParams(blueContentParams);
        
        // Set Sub Menu Button Size
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

       /* ImageView lcIcon1 = new ImageView(this);
        ImageView lcIcon2 = new ImageView(this);
        ImageView lcIcon3 = new ImageView(this);
        ImageView lcIcon4 = new ImageView(this);
        ImageView lcIcon5 = new ImageView(this);*/
        
        TextView lcIcon1 = new TextView(this);
        TextView lcIcon2 = new TextView(this);
        TextView lcIcon3 = new TextView(this);
        TextView lcIcon4 = new TextView(this);
        
        lcIcon1.setText("bus review");
        lcIcon1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9.f);
        lcIcon1.setTextColor(Color.WHITE);
        lcIcon1.setGravity(Gravity.CENTER_HORIZONTAL);
        lcIcon1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bus, 0, 0);
        
        lcIcon2.setText("Order");
        lcIcon2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9.f);
        lcIcon2.setTextColor(Color.WHITE);
        lcIcon2.setGravity(Gravity.CENTER_HORIZONTAL);
        lcIcon2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_my_order, 0, 0);
        
        lcIcon3.setText("Booking");
        lcIcon3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9.f);
        lcIcon3.setTextColor(Color.WHITE);
        lcIcon3.setGravity(Gravity.CENTER_HORIZONTAL);
        lcIcon3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_my_booking, 0, 0);
        
        lcIcon4.setText("Me");
        lcIcon4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9.f);
        lcIcon4.setTextColor(Color.WHITE);
        lcIcon4.setGravity(Gravity.CENTER_HORIZONTAL);
        lcIcon4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.me, 0, 0);

       /* lcIcon1.setImageDrawable(getResources().getDrawable(R.drawable.bus));
        lcIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_order));
        lcIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_booking));
        lcIcon4.setImageDrawable(getResources().getDrawable(R.drawable.me));
*/
        SubActionButton ic_bus = lCSubBuilder.setContentView(lcIcon1, blueContentParams).build();
        SubActionButton ic_order = lCSubBuilder.setContentView(lcIcon2, blueContentParams).build();
        SubActionButton ic_booking =  lCSubBuilder.setContentView(lcIcon3, blueContentParams).build();
        SubActionButton ic_me = lCSubBuilder.setContentView(lcIcon4, blueContentParams).build();
        
        // Build another menu with custom options
        final FloatingActionMenu leftCenterMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(ic_order)
                .addSubActionView(ic_booking)
                .addSubActionView(ic_me)
                .attachTo(leftCenterButton)
                .build();  
        
        /*.setRadius(redActionMenuRadius)
        .setStartAngle(70)
        .setEndAngle(-70)
        .attachTo(leftCenterButton)
        .build();*/
        
        ic_bus.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), BusReveiwActivity.class));
			}
		});
        
        ic_order.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (AppLoginUser.getId() != null && !AppLoginUser.getId().equals("0")) {
					startActivity(new Intent(getApplicationContext(), ThreeDaySalesActivity.class));
				}else {
					startActivity(new Intent(getApplicationContext(), UserLogin.class));
				}
			}
		});
        
        ic_booking.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (AppLoginUser.getId() != null && !AppLoginUser.getId().equals("0")) {
					startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
				}else {
					startActivity(new Intent(getApplicationContext(), UserLogin.class));
				}
				
			}
		});
        
        ic_me.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (AppLoginUser.getId() != null && !AppLoginUser.getId().equals("0")) {
					startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
				}else {
					startActivity(new Intent(getApplicationContext(), UserLogin.class));
				}
				
			}
		});
        
    }
	
	/**
	 * Show alert dialog with yes and no (buttons)
	 * @param MSG message to show in dialog
	 * @param ok custom text to show in Yes button
	 * @param cancel custom text to show in No button
	 * @param YES Yes button if you agree
	 * @param NO No button if you disagree
	 */
	protected void alertDialog(String MSG, String ok, String cancel, OnClickListener YES, OnClickListener NO){
		AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
		alertDialog.setMessage(MSG);
		
		alertDialog.setPositiveButton(ok, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
	
		if(YES != null){
			alertDialog.setPositiveButton(ok, YES);
		}else{
			alertDialog.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });
		}
		
		if(NO != null){
			alertDialog.setNegativeButton(cancel, NO);
		}
		
		alertDialog.setCancelable(false);
		alertDialog.show();
		
	}
}
