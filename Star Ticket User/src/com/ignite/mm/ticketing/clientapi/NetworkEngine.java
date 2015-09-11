package com.ignite.mm.ticketing.clientapi;

import retrofit.RestAdapter;

/**
 * {@link #NetworkEngine} is the class to connect API's domain and call {@link INetworkEngine} interface for route
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class NetworkEngine {
	public static INetworkEngine instance;
	public static String ip = "starticketmyanmar.com";
	//128.199.255.246
	public static void setIP(String address){
		ip = address;
		instance = null;
	}
	public static INetworkEngine getInstance() {
		if (instance==null) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://"+ ip +"/").setLogLevel(RestAdapter.LogLevel.FULL).setErrorHandler(new MyErrorHandler()).build();
			instance = adapter.create(INetworkEngine.class);
		}
		return instance;
	}
}
