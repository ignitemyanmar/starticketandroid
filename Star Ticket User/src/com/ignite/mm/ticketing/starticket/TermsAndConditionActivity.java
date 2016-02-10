package com.ignite.mm.ticketing.starticket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.thuongnh.zprogresshud.ZProgressHUD;

@SuppressLint("SetJavaScriptEnabled") public class TermsAndConditionActivity extends BaseActivity{
	private WebView webview_terms;
	private ZProgressHUD dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_terms_condition);
		
		Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setTitle("Terms and Conditions");
        this.setSupportActionBar(toolbar);
		
	 	dialog = new ZProgressHUD(TermsAndConditionActivity.this);
		dialog.setMessage("Pls Wait...");
		
		try {
			dialog.show();
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
		
		webview_terms = (WebView)findViewById(R.id.webview_terms);
		webview_terms.setWebViewClient(new MyWebViewClient());
		webview_terms.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		webview_terms.clearCache(false);
		webview_terms.clearHistory();
		webview_terms.getSettings().setJavaScriptEnabled(true);
		webview_terms.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview_terms.loadUrl("http://starticket.com.mm/terms-conditions");
	}
	
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			try {
				dialog.show();
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if(dialog != null)
				dialog.dismiss();
		}
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
