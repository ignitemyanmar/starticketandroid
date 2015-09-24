package com.ignite.mm.ticketing.starticket;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.ignite.mm.ticketing.sqlite.database.model.PaymentRequest;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class StarTicketWebActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
    //setContentView(R.layout.activity_2c2p_payment);
	
 	/*dialog = new ZProgressHUD(Payment2C2PActivity.this);
	dialog.setMessage("Pls Wait...");*/
	
	/*try {
		//dialog.show();
	} catch (NullPointerException e) {
		// TODO: handle exception
	}*/
	
	/*webView = (WebView)findViewById(R.id.web_2c2p);
	webView.setWebViewClient(new MyWebViewClient());
	//webView.setWebChromeClient(chromeClient);
	webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
	webView.clearCache(false);
	webView.clearHistory();
	webView.getSettings().setJavaScriptEnabled(true);
	webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
	
	webView.loadUrl("http://starticketmyanmar.com/api/payment/2c2p/"
 			+MCrypt.getInstance().encrypt(paymentRequest)+"?access_token="
 			+AppLoginUser.getAccessToken());*/
	}

}
