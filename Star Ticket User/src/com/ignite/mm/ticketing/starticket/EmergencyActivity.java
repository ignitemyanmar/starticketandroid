package com.ignite.mm.ticketing.starticket;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.thuongnh.zprogresshud.ZProgressHUD;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled") public class EmergencyActivity extends BaseActivity {
	private WebView webView;
	public ZProgressHUD dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_detail);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Emergency Numbers");
           
            this.setSupportActionBar(toolbar);
        }
        
		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new MyWebViewClient());
		webView.clearCache(true);
		webView.clearHistory();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.loadUrl("file:///android_asset/web/EmergencyNumbers.html");
	}
	
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") > -1) {
				EmergencyActivity.this.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
	            return true;
	        } else {
	            return true;
	        }
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			dialog = ZProgressHUD.getInstance(EmergencyActivity.this);
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

