
package com.ignite.mdm.ticketing.clientapi;

import retrofit.RestAdapter;

import static com.ignite.mdm.ticketing.Config.ip;
import static com.ignite.mdm.ticketing.Config.ip2;

public class NetworkEngine {
	
	public static INetworkEngine instance;
	public static INetworkEngine instance2;


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
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ip2)
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
