
package com.ignite.mdm.ticketing.clientapi;

import retrofit.RestAdapter;

public class NetworkEngine {
	
	public static INetworkEngine instance;
	public static INetworkEngine instance2;
	public static String ip = "starticketmyanmar.com";
	public static String ip2 = "mdm.starticketmyanmar.com";
	//128.199.255.246

	public static INetworkEngine getInstance() {
		if (instance==null) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://"+ ip +"/")
					.setLogLevel(RestAdapter.LogLevel.FULL)
					.setErrorHandler(new MyErrorHandler()).build();
			instance = adapter.create(INetworkEngine.class);
		}
		return instance;
	}

	public static INetworkEngine getInstance2() {
		if (instance2==null) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://"+ ip2 +"/")
					.setLogLevel(RestAdapter.LogLevel.FULL)
					.setErrorHandler(new MyErrorHandler()).build();
			instance2 = adapter.create(INetworkEngine.class);
		}
		return instance2;
	}

	public static void setIP(String address){
		ip = address;
		instance = null;
	}

	public static String getIp() {
		return ip;
	}
}
