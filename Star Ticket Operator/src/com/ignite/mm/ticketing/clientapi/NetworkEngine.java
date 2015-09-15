package com.ignite.mm.ticketing.clientapi;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * {@link #NetworkEngine} is the class to connect API's domain and call {@link INetworkEngine} interface for route
 * <p>
 * ** Star Ticket Operator App is used to sell bus tickets via online. 
 * @version 2.0 
 * @author Su Wai Phyo (Ignite Software Solutions)
 * <p>
 * Last Modified : 14/Sept/2015
 * <p>
 * Last ModifiedBy : Saw Maine K
 */
public class NetworkEngine {
	static INetworkEngine instance;
	public static RequestInterceptor requestInterceptor = new RequestInterceptor() {
	  public void intercept(RequestFacade request) {
	    request.addHeader("Accept-Encoding", "gzip, deflate, sdch");
	  }
	};

	public static INetworkEngine getInstance() {
		if (instance==null) {
			RestAdapter adapter = new RestAdapter.Builder()
					.setEndpoint("http://"+ip)
					//.setRequestInterceptor(requestInterceptor)
					.setLogLevel(RestAdapter.LogLevel.FULL)
					.setErrorHandler(new MyErrorHandler()).build();
			instance = adapter.create(INetworkEngine.class);
		}
		return instance;
	}
	public static String ip = "192.168.1.101";
	public static void setIP(String address){
		ip = address;
		instance = null;
	}
}
