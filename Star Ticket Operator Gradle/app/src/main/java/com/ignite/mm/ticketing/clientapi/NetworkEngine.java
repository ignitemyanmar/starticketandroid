package com.ignite.mm.ticketing.clientapi;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class NetworkEngine {
	static INetworkEngine instance;
	//public static String ip = "elite.starticketmyanmar.com";
	//public static String ip = "lumbini.starticketmyanmar.com";
	public static String ip = "mdm.starticketmyanmar.com";
	//public static String ip = "192.168.1.101";
	//public static String ip = "192.168.1.175";
	
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
	
	public static void setIP(String address){
		ip = address;
		instance = null;
	}

	public static String getIp() {
		return ip;
	}
}
