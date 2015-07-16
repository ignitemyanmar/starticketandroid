package com.ignite.mm.ticketing.user;

import com.ignite.mm.ticketing.application.BaseActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") public class Payment2C2PActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);

		 WebView webview = new WebView(Payment2C2PActivity.this);
		 /*String summary = "<html><body>You scored <b>192</b> points.</body></html>";
		 webview.loadData(summary, "text/html", null);*/
		 
		 webview.getSettings().setJavaScriptEnabled(true);
		 webview.getSettings().setSupportZoom(true);
		 webview.getSettings().setBuiltInZoomControls(true);

		 final Activity activity = Payment2C2PActivity.this;
		 webview.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%
		     activity.setProgress(progress * 1000);
		   }
		 });
		 
		 webview.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		   }
		 });

		 getWindow().requestFeature(Window.FEATURE_PROGRESS);
		 
		 //webview.loadUrl("http://developer.android.com/");
		 webview.loadUrl("https://demo2.2c2p.com/2C2PFrontEnd/RedirectV3/payment");
		 
			// Let's display the progress in the activity title bar, like the
		 // browser app does.
		 
		 
		 setContentView(webview);
	}
}
