package com.ignite.mm.ticketing;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import com.ignite.mm.ticketing.sqlite.database.model.AgentList;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mm.ticketing.sqlite.database.model.TripInfo;
import com.ignite.mm.ticketing.sqlite.database.model.Time;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActionBarActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusSeatFragmentPagerAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllTimeBundleListObject;

/**
 * {@link #BusSeatViewPagerActivity} is the class to show Bus Seats(both can book + can sell)
 * <p>
 * Private methods:
 * (1) call Fragment {@link BusSeatFragmentPagerAdapter}
 * <p>
 * ** Star Ticket Operator App is used to sell bus tickets via online. 
 * @version 2.0 
 * @author Su Wai Phyo (Ignite Software Solutions)
 * <p>
 * Last Modified : 14/Sept/2015
 * <p>
 * Last ModifiedBy : Saw Maine K
 */
public class BusSeatViewPagerActivity extends BaseActionBarActivity{

	private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private BusSeatFragmentPagerAdapter adapter;
	private int NotifyBooking;
	private String AgentID;
	public static String OperatorID;
	public static String FromCity;
	public static String ToCity;
	public static String From;
	public static String To;
	public static String Classes;
	public static String Time;
	public static String Date;
	private String TripId;
	private String All_Times_String;
	private List<Time> AllTimeList;
	private String App_Login_User_String;
	private LoginUser App_Login_User;
	
	private Drawable oldBackground = null;
	private int currentColor;
	private int click_position;
	public static List<OperatorGroupUser> groupUser;
	public static List<Agent> agentList;
	public static Menu menu;
	public static MenuItem menuItem;
	public static TripInfo tripInfo;
	public static LoginUser app_login_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Show View of Bus Seats by Time
		setContentView(R.layout.activity_seat_view_pager);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			AgentID = bundle.getString("agent_id");
			OperatorID = bundle.getString("operator_id");
			FromCity = bundle.getString("from_city_id");
			ToCity = bundle.getString("to_city_id");
			From = bundle.getString("from_city");
			To = bundle.getString("to_city");
			Classes = bundle.getString("class_id");
			Time = bundle.getString("time");
			Date = bundle.getString("date");
			TripId = bundle.getString("trip_id");
			All_Times_String = bundle.getString("all_times");
			App_Login_User_String = bundle.getString("app_login_user");
		}
		
		AllTimeBundleListObject bundleAllTimes = new Gson().fromJson(All_Times_String, AllTimeBundleListObject.class);
		AllTimeList = bundleAllTimes.getAllTimes();
		
		if (AllTimeList != null && AllTimeList.size() > 0) {
			
			Log.i("", "All Time List: "+AllTimeList.toString());
			
			for (int i = 0; i < AllTimeList.size(); i++) {
				if (TripId.equals(AllTimeList.get(i).getTripid())) {
					click_position = i;
				}
			}
		}
		
		//Page Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle(From+" - "+To);
            toolbar.setSubtitle(changeDate(Date));
            this.setSupportActionBar(toolbar);
        }
        
        //View Pager for Seats Plan
		pager = (ViewPager)findViewById(R.id.pager);
		
		//Tabs of Each Time
		tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
		tabs.setTextColorResource(R.color.white);
		tabs.setIndicatorColorResource(R.color.accent_dark);
		tabs.setIndicatorHeight(5);
		
		//No use
		getAgent();
		
		//No use
		getOperatorGroupUser();
		
		app_login_user = AppLoginUser;
		
		//Add Time List into Pager Adapter
		if (AllTimeList != null && AllTimeList.size() > 0) {
			adapter = new BusSeatFragmentPagerAdapter(getSupportFragmentManager(), AllTimeList);
		}
        
        pager.setAdapter(adapter);
        pager.setCurrentItem(click_position);
        tabs.setViewPager(pager);
        
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        
        pager.setPageMargin(pageMargin);
        
        //changeColor(Color.GREEN);

        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(BusSeatViewPagerActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void getAgent(){
		String param = MCrypt.getInstance().encrypt(SecureParam.getAllAgentParam(AppLoginUser.getAccessToken(), AppLoginUser.getUserID()));
		NetworkEngine.getInstance().getAllAgent(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				AgentList agents = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AgentList>() {}.getType());
				agentList = agents.getAgents();
			}
		});
	}
    
    private void getOperatorGroupUser(){
		String param = MCrypt.getInstance().encrypt(SecureParam.getOperatorGroupUserParam(AppLoginUser.getAccessToken(), AppLoginUser.getUserID()));
		NetworkEngine.getInstance().getOperatorGroupUser(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				groupUser = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorGroupUser>>() {}.getType());;
			}
		});
	}
    
    
    private void changeColor(int newColor) {
        tabs.setBackgroundColor(newColor);
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
            getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = ld;
        currentColor = newColor;
    }
    
    public void onColorClicked(View v) {
        int color = Color.parseColor(v.getTag().toString());
        changeColor(color);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
    }
    
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
