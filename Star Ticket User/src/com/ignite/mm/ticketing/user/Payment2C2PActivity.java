package com.ignite.mm.ticketing.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mm.ticketing.sqlite.database.model.PaymentRequest;
import com.smk.skalertmessage.SKToastMessage;
import com.thuongnh.zprogresshud.ZProgressHUD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") public class Payment2C2PActivity extends BaseActivity{

	private String order_no;
	private String order_amount;
	private ZProgressHUD dialog;
	private WebView webView;
	
	private final static int FILECHOOSER_RESULTCODE=1;  
	private ValueCallback<Uri> mUploadMessage;
	private String from_payment;
	private String price;
	private String seat_count;
	private String agentgroup_id;
	private String operator_id;
	private String sale_order_no;
	private String selectedSeats;
	private String busOccurence;
	private String permit_access_token;
	private String Permit_agent_id;
	private String permit_ip;
	private String BuyerPhone;
	private String BuyerName;
	private String BuyerNRC;
	private String FromCity;
	private String ToCity;
	private String Operator_Name;
	private String from_to;
	private String time;
	private String classes;
	private String date;
	private String ConfirmDate;
	private String ConfirmTime;
	private String ExtraCityID;
	private String ExtraCityName;
	private String[] selectedSeat;
	private String points_toUse = "0";
	private String giftMoney_toUse = "0";
	private String total_giftMoney = "0";
	

/*	 @Override  
	 protected void onActivityResult(int requestCode, int resultCode,  
	                                    Intent intent) {  
	  if(requestCode==FILECHOOSER_RESULTCODE)  
	  {  
	   if (null == mUploadMessage) return;  
	            Uri result = intent == null || resultCode != RESULT_OK ? null  
	                    : intent.getData();  
	            mUploadMessage.onReceiveValue(result);  
	            mUploadMessage = null;  
	  }
	}  */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_2c2p_payment);
			
		 Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		 toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		 toolbar.setTitle("Online Payment");
         this.setSupportActionBar(toolbar);
		 
		 Bundle bundle = getIntent().getExtras();
			
		 if (bundle != null) {
			 
			from_payment = bundle.getString("from_payment");
			price = bundle.getString("price");
			seat_count = bundle.getString("seat_count");
			agentgroup_id = bundle.getString("agentgroup_id");
			operator_id = bundle.getString("operator_id");
			sale_order_no = bundle.getString("sale_order_no");
			selectedSeats = bundle.getString("selectedSeats");
			busOccurence = bundle.getString("busOccurence");
			permit_access_token = bundle.getString("permit_access_token");
			Permit_agent_id = bundle.getString("Permit_agent_id");
			permit_ip = bundle.getString("permit_ip");
			BuyerName = bundle.getString("BuyerName");
			BuyerPhone = bundle.getString("BuyerPhone");
			BuyerNRC = bundle.getString("BuyerNRC");
			FromCity = bundle.getString("FromCity");
			ToCity = bundle.getString("ToCity");
			Operator_Name = bundle.getString("Operator_Name");
			from_to = bundle.getString("from_to");
			time = bundle.getString("time");
			classes = bundle.getString("classes");
			date = bundle.getString("date");
			ConfirmDate = bundle.getString("ConfirmDate");
			ConfirmTime = bundle.getString("ConfirmTime");
			ExtraCityID = bundle.getString("ExtraCityID");
			ExtraCityName = bundle.getString("ExtraCityName");
				
			order_no = bundle.getString("order_no");
			order_amount = bundle.getString("order_amount");
			points_toUse = bundle.getString("points_toUse");
			giftMoney_toUse = bundle.getString("giftMoney_toUse");
			total_giftMoney = bundle.getString("total_giftMoney");
			
			dialog = new ZProgressHUD(Payment2C2PActivity.this);
			dialog.setMessage("Plz Wait...");
			
			try {
				dialog.show();
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			
			webView = (WebView)findViewById(R.id.web_2c2p);
			webView.setWebViewClient(new MyWebViewClient());
			//webView.setWebChromeClient(chromeClient);
			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			webView.clearCache(false);
			webView.clearHistory();
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
			
			String paymentRequest = new Gson().toJson(new PaymentRequest(order_no, order_amount));
			 
			 Log.i("", "order no: "+order_no
					 	+", order amount: "+order_amount
					 	+", paymentRequest: "+paymentRequest
					 	+", encrypt: "+MCrypt.getInstance().encrypt(paymentRequest)
					 	+", access: "+AppLoginUser.getAccessToken());
			 
			 webView.loadUrl("http://starticketmyanmar.com/api/payment/2c2p/"
					 			+MCrypt.getInstance().encrypt(paymentRequest)+"?access_token="
					 			+AppLoginUser.getAccessToken());
			 
		}
	}
	 
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			String[] urlArray = url.split("/");
			if (urlArray[urlArray.length - 2].equals("payment_success")) {
				
				/*Intent i = new Intent();
				setResult(RESULT_OK, i);*/
				//confirmOrder(from_payment);
				
				return true;
	        }else if(urlArray[urlArray.length - 1].equals("payment_cancel")){
	        	
	        	/*Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();*/
	        	
	        	confirmOrder(from_payment);
	        	return true;
	        } else {
	        	return super.shouldOverrideUrlLoading(view, url);
	        }
			
			//return super.shouldOverrideUrlLoading(view, url);
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
	
	private void confirmOrder(final String paymentType) {
		
			dialog.setMessage("please wait ...");
			dialog.show();
			
			Log.i("", "buyer nrc: "+BuyerNRC);
			
			List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
			
			if (selectedSeats != null) {
				selectedSeat = selectedSeats.split(",");
			}
			
			Log.i("", "Selected Seats(Payment) : "+selectedSeats);
			
			for (int j = 0; j < selectedSeat.length; j++) {
				seats.add(new ConfirmSeat(busOccurence, selectedSeat[j].toString(),
						BuyerName, BuyerNRC, "0", false,
						"blah", 0));
			}
			
			Log.i("", "Ticket Arrays: "+seats.toString());
			
			Log.i("", "Param (Confirm) to encrypt: "
					+"access: "+permit_access_token+
					", SaleOrderNo: "+sale_order_no+
					", AgentID: "+Permit_agent_id+
					", Agent Name: "+Operator_Name+
					", Customer: "+AppLoginUser.getUserName()+
					", Phone: "+AppLoginUser.getPhone()+
					", Nrc: "+BuyerNRC+
					", Extra city id: "+ExtraCityID+
					", Seats: "+seats.toString()+
					", Order date: "+ConfirmDate+
					", Device id: "+DeviceUtil.getInstance(this).getID()+
					", isbooking: "+"0");
			
			//Do Encrypt of Params				
			String param = MCrypt.getInstance()
					.encrypt(
					SecureParam.postSaleConfirmParam(permit_access_token
					, sale_order_no, "0"
					, Permit_agent_id, ""
					, AppLoginUser.getUserName()
					, AppLoginUser.getPhone(), BuyerNRC
					, "0", ""
					, ExtraCityID,  MCrypt.getInstance()
					.encrypt(seats.toString())
					, "1"
					, "local"
					, ConfirmDate, DeviceUtil.getInstance(this).getID(), "0", String.valueOf(AppLoginUser.getId())));
					
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("param", param));
			
			Log.i("","Hello param (for confirm) : "+ param);
			
			final Handler handler = new Handler() {
	
				//private String total_amount;
	
				public void handleMessage(Message msg) {
	
					String jsonData = msg.getData().getString("data");
					
					if (jsonData != null) {
						try {
							Log.i("","Hello Response Confirm Data:"+ jsonData);
							
							JSONObject jsonObj = new JSONObject(jsonData);
							//JSONObject orderObj = jsonObj.getJSONObject("order");*/
							//total_amount = orderObj.getString("total_amount");
							
							if(!jsonObj.getBoolean("status") && jsonObj.getString("device_id").equals(DeviceUtil.getInstance(Payment2C2PActivity.this).getID())){
								SKToastMessage.showMessage(Payment2C2PActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားေသာေၾကာင့္ သင္ မွာ ေသာ လက္ မွတ္ မ်ား မရ ႏုိင္ေတာ့ပါ။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
								dialog.dismissWithFailure();
							}else{
								//Store Sale on City Mart DB
								postOnlineSaleConfirm(paymentType);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						Log.i("", "Response confirm is null!");
						dialog.dismissWithFailure();
					}
				}
			};
			
			//bundle.getString("permit_ip")
			HttpConnection lt = new HttpConnection(handler, "POST",
					"http://"+permit_ip+"/sale/comfirm", params);
			lt.execute();
			
			Log.i("", "Permit IP: "+permit_ip);
		}
	

/**
 *  Store sales into Online Sale Database (starticketmyanmar.com)
 */
protected void postOnlineSaleConfirm(final String paymentType) {
	// TODO Auto-generated method stub
	Log.i("", "SaleOrderNo: "+sale_order_no+", Op-Id: "+operator_id+", User code no: "+AppLoginUser.getCodeNo()
			+", Token: "+AppLoginUser.getAccessToken()+", paymentType: "+paymentType+", totalGiftMoney: "+total_giftMoney);
	
	NetworkEngine.setIP("starticketmyanmar.com");
	NetworkEngine.getInstance().postOnlineSaleDB(sale_order_no, operator_id, AppLoginUser.getCodeNo()
			, AppLoginUser.getAccessToken(), ExtraCityName, AppLoginUser.getPhone()
			, AppLoginUser.getUserName(), AppLoginUser.getAddress(), ""
			, "0", total_giftMoney, "", "", agentgroup_id, ""
			, paymentType, new Callback<Response>() {
		
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					if (arg0.getResponse() != null) {
						Log.i("", "Error: "+arg0.getResponse().getStatus());
					}
					
					dialog.dismissWithFailure("Sorry, Can't Confirm!");
				}

				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub
					if (arg1 != null) {
						
						dialog.dismissWithSuccess();
						
						SKToastMessage.showMessage(Payment2C2PActivity.this, "လက္ မွတ္ ျဖတ္ ၿပီး ပါ ၿပီ။", SKToastMessage.SUCCESS);
						
						closeAllActivities();
						startActivity(new Intent(Payment2C2PActivity.this, SaleTicketActivity.class));
					}
				}
			});
}
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
