package com.ignite.mm.ticketing.starticket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.thuongnh.zprogresshud.ZProgressHUD;

@SuppressLint("SetJavaScriptEnabled") public class DetailWebViewActivity extends BaseActivity {
	private WebView webView;
	private String Url;
	private ZProgressHUD dialog;
	private String City;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_detail);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Url = extras.getString("url");
			City = extras.getString("city");
		}
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle(City);
           
            this.setSupportActionBar(toolbar);
        }
        
		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new MyWebViewClient());
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		webView.clearCache(true);
		webView.clearHistory();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.loadUrl("file:///android_asset/web/"+Url);
	}
	
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			dialog = ZProgressHUD.getInstance(DetailWebViewActivity.this);
			dialog.setMessage("Pls wait...");
			dialog.setCancelable(true);
			dialog.show();
			super.onPageStarted(view, url, favicon);
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			dialog.dismiss();
			super.onPageFinished(view, url);
		}
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
