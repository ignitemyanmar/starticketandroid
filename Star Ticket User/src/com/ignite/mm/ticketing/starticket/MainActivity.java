package com.ignite.mm.ticketing.starticket;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.starticket.R;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

/**
 * {@link #MainActivity} is Main class(first activity).
 * (1) Splash Screen Show for 1000 milliseconds (1 second)
 * (2) Start another activity {@link #SaleTicketActivity} that show Trip Search By Date
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class MainActivity extends BaseActivity {
	private Context ctx = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Thread splashTread = new Thread() {

			@Override
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					//close the main activity
					finish();
					
					//Start another activity #SaleTicketActivity that show Trip Search
					startActivity(new Intent(ctx, SaleTicketActivity.class));
				}
			}
		};

		//Splash Screen Show for 1000 milliseconds before #SaleTicketActivity
		splashTread.start();
	} 
}
