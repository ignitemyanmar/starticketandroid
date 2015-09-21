
package com.ignite.mm.ticketing.starticket;

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
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
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

/**
 * {@link #Payment2C2PActivity} is the class to get Online Payment Webview 
 * to set User Card Info
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * (2) {@link #confirmOrder(String, String, String, String, String, String, String, String, String, String, String, String)}
 * (3) {@link #postOnlineSaleConfirm(String, String, String, String, String, String, String, String)}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class Payment2C2PActivity extends BaseActivity{

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
	private String ticketNos;
	private String return_date;
	private String from_intent;
	private int trip_type;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	

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
			
			ticketNos = bundle.getString("ticketNos");
			
			return_date = bundle.getString("ReturnDate");
			from_intent = bundle.getString("from_intent");
			trip_type = bundle.getInt("trip_type");
			goTripInfo_str = bundle.getString("GoTripInfo");
			goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
		}
		 
		 	dialog = new ZProgressHUD(Payment2C2PActivity.this);
			dialog.setMessage("Pls Wait...");
			
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
			
			String orderNo = "";
			if (trip_type == 1) {
				//if one way
				orderNo = order_no;
			}else if(trip_type == 2){
				// Round Trip
				if (from_payment.equals("Pay with MPU")) {
					orderNo = goTripInfo_obj.getSale_order_no()+""+order_no;
				}else if (from_payment.equals("Pay with VISA/MASTER")) {
					orderNo = goTripInfo_obj.getSale_order_no()+","+order_no;
				}
			}
			
			String paymentRequest = new Gson().toJson(new PaymentRequest(orderNo, order_amount));
			 
			 Log.i("", "order no: "+orderNo
					 	+", order amount: "+order_amount
					 	+", paymentRequest: "+paymentRequest
					 	+", encrypt: "+MCrypt.getInstance().encrypt(paymentRequest)
					 	+", access: "+AppLoginUser.getAccessToken()
					 	+", fromPayment: "+from_payment);
			 
			 if (from_payment.equals("Pay with MPU")) {
				 
				 Log.i("", "MPU URL: "+"http://starticketmyanmar.com/api/payment/2c2p/"
				 			+MCrypt.getInstance().encrypt(paymentRequest)+"?access_token="
				 			+AppLoginUser.getAccessToken());
				 
				 Log.i("", "MPU enter......");
				 
				 webView.loadUrl("http://starticketmyanmar.com/api/payment/2c2p/"
				 			+MCrypt.getInstance().encrypt(paymentRequest)+"?access_token="
				 			+AppLoginUser.getAccessToken());
				 
			 }else if (from_payment.equals("Pay with VISA/MASTER")) {
				 
				 Log.i("", "MIGS enter......");
				 
				 Log.i("", "MIGS URL: "+"http://starticketmyanmar.com/api/payment/migs/"
				 
				 			+MCrypt.getInstance().encrypt(paymentRequest)+"?access_token="
				 			+AppLoginUser.getAccessToken());
				 
				 webView.loadUrl("http://starticketmyanmar.com/api/payment/migs/"
				 			+MCrypt.getInstance().encrypt(paymentRequest)+"?access_token="
				 			+AppLoginUser.getAccessToken());
			 }
	}
	 
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			String[] urlArray = url.split("/");
			
			Log.i("", "URL Array: "+urlArray[urlArray.length - 1]);
			
			//If Payment Success......, show thank you page
			if (urlArray[urlArray.length - 1].equals("payment_success")) {
				
				Log.i("", "Payment Success!!!!!!!!!!!!");
				
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				
				//If One Way
				if (trip_type == 1) {
					confirmOrder(from_payment, selectedSeats, ticketNos
							, busOccurence, BuyerName, BuyerNRC, permit_access_token
							, order_no, Permit_agent_id, ExtraCityID, ConfirmDate, "");
				}else if (trip_type == 2) {
					//If Round Trip
					//Confirm for Go Trip
					confirmOrder(from_payment, goTripInfo_obj.getSelected_seats(), goTripInfo_obj.getTicket_nos()
							, goTripInfo_obj.getBusOccurence(), goTripInfo_obj.getBuyerName()
							, goTripInfo_obj.getBuyerNRC(), goTripInfo_obj.getPermit_access_token()
							, goTripInfo_obj.getSale_order_no(), goTripInfo_obj.getPermit_agent_id()
							, goTripInfo_obj.getExtraCityID(), goTripInfo_obj.getConfirmDate(), "");
				}
				
				return true;
	        }else if(urlArray[urlArray.length - 1].equals("payment_cancel")){
	        	
	        	Log.i("", "Payment Cancel!!!!!!!!!!!!");
	        	
	        	Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();
				
	        	return true;
	        } else {
	        	return super.shouldOverrideUrlLoading(view, url);
	        }
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
	
	private String[] ticketNoArray;
	
	/**
	 * Confirm and save order into Operator Database and online DB, after online payment success
	 * @param paymentType Payment Type
	 * @param selectedSeats Selected Seats
	 * @param ticketNos Ticket Nos
	 * @param busOccurence BusOccurance
	 * @param buyerName Buyer Name
	 * @param permitAccessToken Permit AccessToken
	 * @param saleOrderNo Order No
	 * @param permitAgentId Permit Agent ID
	 * @param ExtraCityId Extra City Id
	 * @param confirmDate Confirm Date
	 * @param from_go_trip_success status
	 */
	private void confirmOrder(final String paymentType, String selectedSeats, final String ticketNos
			, String busOccurence, String buyerName, String buyerNRC, String permitAccessToken
			, String saleOrderNo, String permitAgentId, String ExtraCityId, String confirmDate
			, String from_go_trip_success) {
		
			dialog.setMessage("please wait ...");
			dialog.show();
			
			Log.i("", "Ticket list: "+ticketNos);
			Log.i("", "buyer nrc: "+buyerNRC);
			
			final String from_goTrip_success = from_go_trip_success;
			
			List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
			
			if (selectedSeats != null) {
				selectedSeat = selectedSeats.split(",");
			}
			
			if (ticketNos != null && !ticketNos.equals("")) {
				ticketNoArray = ticketNos.split(",");
			}
			
			Log.i("", "Selected Seats(Payment) : "+selectedSeats);
			
			for (int j = 0; j < selectedSeat.length; j++) {
				seats.add(new ConfirmSeat(busOccurence, selectedSeat[j].toString(),
						buyerName, buyerNRC, ticketNoArray[j].toString(), false,
						"blah", 0));
			}
			
			Log.i("", "Ticket Arrays: "+seats.toString());
			
			Log.i("", "Param (Confirm) to encrypt: "
					+"access: "+permit_access_token+
					", SaleOrderNo: "+order_no+
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
					SecureParam.postSaleConfirmParam(permitAccessToken
					, saleOrderNo, "0"
					, permitAgentId, ""
					, AppLoginUser.getUserName()
					, AppLoginUser.getPhone(), buyerNRC
					, "0", ""
					, ExtraCityId,  MCrypt.getInstance()
					.encrypt(seats.toString())
					, "1"
					, "local"
					, confirmDate, DeviceUtil.getInstance(this).getID(), "0", String.valueOf(AppLoginUser.getId())));
					
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
								dialog.dismissWithFailure();
								SKToastMessage.showMessage(Payment2C2PActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
							}else{
								//If One Way
								String paymentTypeChange = "";
								
								if (paymentType.equals("Pay with VISA/MASTER")) {
									paymentTypeChange = "pay with master/visa";
								}else {
									paymentTypeChange = paymentType;
								}
								
								if (trip_type == 1) {
									//If one way
									postOnlineSaleConfirm(paymentTypeChange, from_goTrip_success, order_no
														, operator_id, ExtraCityName, agentgroup_id
														, ticketNos, total_giftMoney);
									
								}else if (trip_type == 2) {
									//If Round Trip
			        				if (!from_goTrip_success.equals("from_go_trip_success")) {
			        					
			        					Log.i("", "Payment Type(go): "+paymentTypeChange);
			        					//Online Confirm for Go Trip
										postOnlineSaleConfirm(paymentTypeChange, from_goTrip_success, goTripInfo_obj.getSale_order_no()
												, goTripInfo_obj.getOperator_id(), goTripInfo_obj.getExtraCityName()
												, goTripInfo_obj.getAgentgroup_id()
												, goTripInfo_obj.getTicket_nos(), total_giftMoney);
										
									}else {
										Log.i("", "Payment Type(return): "+paymentTypeChange);
										//Online Confirm for Return Trip
			        					postOnlineSaleConfirm(paymentTypeChange, from_goTrip_success, order_no
												, operator_id, ExtraCityName, agentgroup_id
												, ticketNos, "0");
									}
								}
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
	 *  Store orders into Online Sale Database after saving in operator DB. 
	 *  If success save, show {@link ThankYouActivity}
	 * @param paymentType Payment Type
	 * @param from_goTrip_success from_goTrip_success
	 * @param ticketNos2 Star Ticket No(s)
	 * @param agentgroup_id2 Agent Group Id
	 * @param extraCityName2  Extra City Name
	 * @param operator_id2 Operator Id
	 * @param sale_order_no2 Order No
	 * @param totalGiftMoney Total GiftMoney
	 */
	private void postOnlineSaleConfirm(final String paymentType, final String from_goTrip_success, String sale_order_no2
		, String operator_id2, String extraCityName2, String agentgroup_id2, String ticketNos2, String totalGiftMoney) {
	// TODO Auto-generated method stub
	
	Log.i("", "sale_order_no: "+sale_order_no2+", operator_id: "
			+operator_id2+", user_code_no: "+AppLoginUser.getCodeNo()
			+", access_token: "+AppLoginUser.getAccessToken()
			+", extra_name: "+extraCityName2
			+", payment_type: "+paymentType
			+", loyalty_phone: "+AppLoginUser.getPhone()
			+", loyalty_name: "+AppLoginUser.getUserName()
			+", loyalty_address: "+AppLoginUser.getAddress()
			+", use_gift_money: "+totalGiftMoney
			+", starticket_no: "+ticketNos2
			+",	agentgroup_id: "+agentgroup_id2);
	
	Integer tripTypeOnline = 0;
	
	if (trip_type == 1) {
		//If one way 
		tripTypeOnline = 0;
	}else if (trip_type == 2) {
		//If round trip
		tripTypeOnline = 1;
	}
	
	NetworkEngine.setIP("starticketmyanmar.com");
	NetworkEngine.getInstance().postOnlineSaleDB(sale_order_no2, operator_id2, AppLoginUser.getCodeNo()
			, AppLoginUser.getAccessToken(), extraCityName2, AppLoginUser.getPhone()
			, AppLoginUser.getUserName(), AppLoginUser.getAddress(), ""
			, "0", totalGiftMoney, "", "", agentgroup_id2, ""
			, paymentType, ticketNos2, String.valueOf(tripTypeOnline), new Callback<Response>() {
		
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
						
						Bundle bundle = new Bundle();
        				bundle.putString("payment_type", paymentType);
						
						if (trip_type == 1) {
							//If one way
							startActivity(new Intent(Payment2C2PActivity.this, ThankYouActivity.class).putExtras(bundle));
							dialog.dismissWithSuccess();
						}else if (trip_type == 2) {
							//If round trip
	        				if (!from_goTrip_success.equals("from_go_trip_success")) {
	        					//If return trip not success yet, .......
	        					//Confirm in Operator Server for Return Trip
	        					confirmOrder(from_payment, selectedSeats, ticketNos
	        							, busOccurence, BuyerName, BuyerNRC, permit_access_token
	        							, order_no, Permit_agent_id, ExtraCityID, ConfirmDate, "from_go_trip_success");
							}else {
								//If return trip success, Go to thank you page
								startActivity(new Intent(Payment2C2PActivity.this, ThankYouActivity.class).putExtras(bundle));
								dialog.dismissWithSuccess();
							}
						}
						//startActivity(new Intent(Payment2C2PActivity.this, SaleTicketActivity.class));
					}
				}
			});
}
	
	/**
	 * If back arrow button clicked, close the activity
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		
		//Test Confirm 
		//If One Way
		if (trip_type == 1) {
			confirmOrder(from_payment, selectedSeats, ticketNos
					, busOccurence, BuyerName, BuyerNRC, permit_access_token
					, order_no, Permit_agent_id, ExtraCityID, ConfirmDate, "");
		}else if (trip_type == 2) {
			//If Round Trip
			//Confirm for Go Trip
			confirmOrder(from_payment, goTripInfo_obj.getSelected_seats(), goTripInfo_obj.getTicket_nos()
					, goTripInfo_obj.getBusOccurence(), goTripInfo_obj.getBuyerName()
					, goTripInfo_obj.getBuyerNRC(), goTripInfo_obj.getPermit_access_token()
					, goTripInfo_obj.getSale_order_no(), goTripInfo_obj.getPermit_agent_id()
					, goTripInfo_obj.getExtraCityID(), goTripInfo_obj.getConfirmDate(), "");
		}
		
		finish();
		return super.getSupportParentActivityIntent();
	}
}
