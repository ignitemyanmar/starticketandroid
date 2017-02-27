package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import com.ignite.mdm.ticketing.sqlite.database.model.CbOddRequest;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.MCrypt;

import static com.ignite.mdm.ticketing.Config.ip2;

public class CbOddPaymentActivity extends BaseSherlockActivity {

  private final String BASE_URL = ip2 + "cbodd-payment/";

  private WebView mWebViewCbOddPayment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cb_odd_payment);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle("CB Payment");
    }

    mWebViewCbOddPayment = (WebView) findViewById(R.id.web_view_cb_odd_payment);
    mWebViewCbOddPayment.setWebViewClient(new CbOddPaymentWebViewClient());
    mWebViewCbOddPayment.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    mWebViewCbOddPayment.clearCache(false);
    mWebViewCbOddPayment.clearHistory();
    mWebViewCbOddPayment.getSettings().setJavaScriptEnabled(true);
    mWebViewCbOddPayment.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

    SharedPreferences sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
    String agentGroupId = sharedPreferences.getString("agent_group_id", null);

    CbOddRequest request = new CbOddRequest();
    request.setAgentgroup_id(agentGroupId);

    String encrypt_request = MCrypt.getInstance().encrypt(new Gson().toJson(request));

    Log.e("TAG", "odd : " + BASE_URL + encrypt_request);

    mWebViewCbOddPayment.loadUrl(BASE_URL + encrypt_request);
  }

  private class CbOddPaymentWebViewClient extends WebViewClient {

    private ProgressDialog dialog;

    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      // TODO Auto-generated method stub
      return super.shouldOverrideUrlLoading(view, url);
    }

    @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
      // TODO Auto-generated method stub
      super.onPageStarted(view, url, favicon);

      dialog =
          ProgressDialog.show(CbOddPaymentActivity.this, "", getString(R.string.please_wait), true);
      ProgressBar progress = (ProgressBar) dialog.findViewById(android.R.id.progress);
      progress.getIndeterminateDrawable()
          .setColorFilter(getResources().getColor(R.color.primary),
              android.graphics.PorterDuff.Mode.SRC_ATOP);
      dialog.setCancelable(false);
      dialog.setCanceledOnTouchOutside(false);
    }

    @Override public void onPageFinished(WebView view, String url) {
      // TODO Auto-generated method stub
      super.onPageFinished(view, url);

      if (dialog != null) {
        dialog.dismiss();
      }
    }
  }
}
