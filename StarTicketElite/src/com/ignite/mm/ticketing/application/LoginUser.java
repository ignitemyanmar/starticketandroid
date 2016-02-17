package com.ignite.mm.ticketing.application;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LoginUser {

	private String AccessToken;
	private String TokenType;
	private long Expires;
	private long ExpiresIn;
	private String RefreshToken;
	private String UserID;
	private String UserGroupID;
	private String LoginUserID;
	private String UserName;
	private String UserType;
	private String TodayString;
	private long TodayLong;
	private Context ctx;

	public LoginUser(Context ctx){
		this.ctx = ctx;
		TodayString = getTodayString();
		TodayLong = changeFrom(TodayString);
		
		SharedPreferences pref = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
		AccessToken = pref.getString("access_token", null);
		TokenType = pref.getString("token_type", null);
		Expires = pref.getLong("expires", 0);
		ExpiresIn = pref.getLong("expires_in", 0);
		RefreshToken = pref.getString("refresh_token", null);
		UserID = pref.getString("user_id", null);
		UserGroupID = pref.getString("usergroup_id", null);
		LoginUserID = pref.getString("login_user_id", null);
		UserName = pref.getString("user_name", null);
		UserType = pref.getString("user_type", null);
        
		Log.d("","Today ---> "+" Long is "+TodayLong +" and "+ convertTime(TodayLong));
		Log.d("","Expires ---> "+" Long is "+Expires +" and "+ convertServerTime(Expires));
	}
	
	public boolean isExpires(){
		boolean is = false;
		Date today = getTodayDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Expires * 1000);
        Date expireDate = cal.getTime();
		if(expireDate.compareTo(today) < 0){
			is = true;
			SharedPreferences ref = ctx.getSharedPreferences("User",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = ref.edit();
			editor.clear();
			editor.commit();
		}
		return is;
	}
	
	public void login(){
		SharedPreferences sharedPreferences = ctx.getSharedPreferences("User",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.clear();
		editor.commit();
		
		editor.putString("access_token", getAccessToken());
		editor.putString("token_type", getTokenType());
		editor.putLong("expires", getExpires());
		editor.putLong("expires_in", getExpiresIn());
		editor.putString("refresh_token", getRefreshToken());
		editor.putString("user_id", getUserID());
		editor.putString("usergroup_id", getUserGroupID());
		editor.putString("login_user_id", getLoginUserID());
		editor.putString("user_name", getUserName());
		editor.putString("user_type", getUserType());
		
		editor.commit();
	}
	
	public void logout(){
		SharedPreferences ref = ctx.getSharedPreferences("User",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = ref.edit();
		editor.clear();
		editor.commit();
	}
		
	private String getTodayString(){
		Calendar c = Calendar.getInstance(Locale.getDefault());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
	
	private Date getTodayDate(){
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.getTime();
	}
	
	private long changeFrom(String str){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			return df.parse(str).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
    }
	
	public String convertServerTime(long time){
    	
    	Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
        Date date = cal.getTime();
    	
        Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        return format.format(date);
    }
    
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}

	public String getTokenType() {
		return TokenType;
	}

	public void setTokenType(String tokenType) {
		TokenType = tokenType;
	}

	public long getExpires() {
		return Expires;
	}

	public void setExpires(long expires) {
		Expires = expires;
	}

	public long getExpiresIn() {
		return ExpiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		ExpiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}
	
	public String getUserGroupID() {
		return UserGroupID;
	}

	public void setUserGroupID(String userGroupID) {
		UserGroupID = userGroupID;
	}
	
	public String getLoginUserID() {
		return LoginUserID;
	}

	public void setLoginUserID(String loginUserID) {
		LoginUserID = loginUserID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public void setTodayString(String todayString) {
		TodayString = todayString;
	}

	public long getTodayLong() {
		return TodayLong;
	}

	public void setTodayLong(long todayLong) {
		TodayLong = todayLong;
	}
    
    
}
