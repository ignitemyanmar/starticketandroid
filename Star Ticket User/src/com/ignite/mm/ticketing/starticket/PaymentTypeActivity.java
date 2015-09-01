package com.ignite.mm.ticketing.starticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import info.hoang8f.widget.FButton;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

@SuppressLint("SimpleDateFormat") public class PaymentTypeActivity extends BaseActivity{

	
	private RadioButton radio_payWithMPU;
	private RadioButton radio_payWithVisaMaster;
	private RadioButton radio_cashOnShop;
	private RadioButton radio_cashOnDelivery;
	private SKConnectionDetector skDetector;
	private FButton btn_confirm;
	private RadioGroup radioGroup;
	private TextView txt_booking_fee;
	private String fromPayment;
	private ZProgressHUD dialog;
	private Integer isBooking = 0;
	private ZProgressHUD progress;
	private Calendar cal;
	private Date deptDateTime;
	private String from_intent;
	private String deptTime;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	private int trip_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_payment_type);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			from_intent = bundle.getString("from_intent");
			goTripInfo_str = bundle.getString("GoTripInfo");
			goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
			trip_type = bundle.getInt("trip_type");
		}
		
		Log.i("", "Permit IP at Busconfirmact: "+BusConfirmActivity.permit_ip);
		
		 Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		 toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		 toolbar.setTitle("Payment Type");
	     this.setSupportActionBar(toolbar);
	     
	     skDetector = new SKConnectionDetector(this);
         
	     txt_booking_fee = (TextView)findViewById(R.id.txt_booking_fee);
			
        radio_payWithMPU = (RadioButton)findViewById(R.id.radio_payWithMPU);
 		radio_payWithVisaMaster = (RadioButton)findViewById(R.id.radio_payWithVisaMaster);
 		radio_cashOnShop = (RadioButton)findViewById(R.id.radio_cashOnShop);
 		radio_cashOnDelivery = (RadioButton)findViewById(R.id.radio_cashOnDelivery);
 				
	    radio_payWithVisaMaster.setOnClickListener(new OnClickListener() {
				
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.VISIBLE);
			}
		});
	    
	    radio_payWithMPU.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.GONE);
			}
		});
	    
	    radio_cashOnDelivery.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.GONE);
			}
		});
	    
	    radio_cashOnShop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_booking_fee.setVisibility(View.GONE);
			}
		});
	     
		//Get only 06:00 AM format
		String timeformat = null;
		try {
			if (BusConfirmActivity.time.length() == 8) {
				timeformat = BusConfirmActivity.time;
			}else if (BusConfirmActivity.time.length() < 8) {
				timeformat = "0"+BusConfirmActivity.time;
			}else if (BusConfirmActivity.time.length() > 8) {
				timeformat = BusConfirmActivity.time.substring(0, 8);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			Log.i("", "Time Out Of Bound Exception: "+e);
		}
		
		SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm aa");
		Date timeTochange = null;
		try {
			if (timeformat != null) {
				timeTochange = serverFormat.parse(timeformat);
				Log.i("", "Server Time Format: "+serverFormat.format(timeTochange));
			}
			
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			Log.i("", "Server Time Exception: "+e2);
			e2.printStackTrace();
		}
		
 		//Show/Hide of Cash on Delivery & Cash on Shop
		if (from_intent.equals("SaleTicket")) {
			deptTime = BusConfirmActivity.date+" "+serverFormat.format(timeTochange);
		}else if (from_intent.equals("BusConfirm")){
			deptTime = BusConfirmActivity.return_date+" "+serverFormat.format(timeTochange);
		}
		
		
		//Today+24hr
		SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		deptDateTime = null;
		try {
			deptDateTime = formatter.parse(deptTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("", "Dept date time: "+deptDateTime+", today+24hr: "+nowFormat.format(cal.getTime()));
 				
 		//Show(or)Hide
		//Not Allow Click for Cash On Delivery, Cash On Shop (during 1 day before Departure Date)
		//Because we want users make booking + purchasing (1 day in advance) 
		//except Online Payment
		if (cal.getTime().compareTo(deptDateTime) >= 0) {
			radio_cashOnDelivery.setEnabled(false);
			radio_cashOnShop.setEnabled(false);
		}else {
			radio_cashOnDelivery.setEnabled(true);
			radio_cashOnShop.setEnabled(true);
		}
 		
 		btn_confirm = (FButton)findViewById(R.id.btn_confirm);
 		btn_confirm.setButtonColor(getResources().getColor(R.color.yellow));
		btn_confirm.setShadowEnabled(true);
		btn_confirm.setShadowHeight(3);
		btn_confirm.setCornerRadius(7);
		
 		btn_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (skDetector.isConnectingToInternet()) {
					if (radio_payWithMPU.isChecked()) {	
						fromPayment = "Pay with MPU";
						postSale(fromPayment);
					}else if (radio_payWithVisaMaster.isChecked()) {	
						fromPayment = "Pay with VISA/MASTER";
						postSale(fromPayment);
					}else if (radio_cashOnShop.isChecked()) {	//Booking (Pay @Store)
						isBooking = 1;
						fromPayment = "Cash on Shop";
						postSale(fromPayment);
					}else {				//Booking (Pay On Delivery)
						fromPayment = "Cash on Delivery";
						postSale(fromPayment);
					}
				}else {
					SKToastMessage.showMessage(PaymentTypeActivity.this, "Please check your internet connection!", SKToastMessage.ERROR);
				}
			}
		});
	}
	
	/**
	 * Save Booking (or) Sale into Database
	 * @param fromPayment Payment Type
	 */
	public void postSale(final String fromPayment)
	{
		dialog = new ZProgressHUD(PaymentTypeActivity.this);
		dialog.show();

		//Buy Ticket
		if(isBooking == 0){
			
			Log.i("", "Seat List(to payment): "+BusConfirmActivity.selectedSeatNos);
			
			Intent nextScreen = new Intent(PaymentTypeActivity.this, PaymentActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("from_payment", fromPayment);
			bundle.putString("sale_order_no", BusConfirmActivity.sale_order_no);
			bundle.putString("price", BusConfirmActivity.Price);
			bundle.putString("seat_count", BusConfirmActivity.seat_count+"");
			bundle.putString("agentgroup_id", AppLoginUser.getAgentGroupId());
			bundle.putString("operator_id", BusConfirmActivity.permit_operator_id);
			bundle.putString("Selected_seats", BusConfirmActivity.selectedSeatNos);
			bundle.putString("ticket_nos", BusConfirmActivity.TicketLists);
			bundle.putString("busOccurence", BusConfirmActivity.BusOccurence);
			bundle.putString("permit_access_token", BusConfirmActivity.permit_access_token);
			bundle.putString("Permit_agent_id", BusConfirmActivity.Permit_agent_id);
			bundle.putString("permit_ip", BusConfirmActivity.permit_ip);
			bundle.putString("BuyerName", BusConfirmActivity.CustName);
			bundle.putString("BuyerPhone", BusConfirmActivity.CustPhone);
			bundle.putString("BuyerNRC", "");
			bundle.putString("FromCity", BusConfirmActivity.FromCity);
			bundle.putString("ToCity", BusConfirmActivity.ToCity);
			bundle.putString("Operator_Name", BusConfirmActivity.Operator_Name);
			bundle.putString("from_to", BusConfirmActivity.from_to);
			bundle.putString("time", BusConfirmActivity.time);
			bundle.putString("classes", BusConfirmActivity.classes);
			bundle.putString("date", BusConfirmActivity.date);
			bundle.putString("ConfirmDate", BusConfirmActivity.ConfirmDate);
			bundle.putString("ConfirmTime", BusConfirmActivity.ConfirmTime);
			bundle.putString("ExtraCityID", BusConfirmActivity.ExtraCityID);
			bundle.putString("ExtraCityName", BusConfirmActivity.ExtraCityName);
			bundle.putString("ExtraCityPrice", BusConfirmActivity.ExtraCityPrice);
			bundle.putString("ReturnDate", BusConfirmActivity.return_date);
			bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
			bundle.putInt("trip_type", trip_type);
			bundle.putString("from_intent", from_intent);
			
			nextScreen.putExtras(bundle);
			startActivity(nextScreen);
			dialog.dismiss();
		}else{ 
			//Booking Finished!
			Log.i("", "Seat List(to booking): "+BusConfirmActivity.selectedSeatNos);
			
			isBooking = 0;
			
			//If One Way
			if (trip_type == 1) {
				postOnlineSale(BusConfirmActivity.sale_order_no, fromPayment, BusConfirmActivity.TicketLists);
			}else if (trip_type == 2) {
				//If Round Trip
				//Book for Go Trip
				postOnlineSale(goTripInfo_obj.getSale_order_no(), fromPayment, goTripInfo_obj.getTicket_nos());
				
				//Book for Return Trip
				postOnlineSale(BusConfirmActivity.sale_order_no, fromPayment, BusConfirmActivity.TicketLists);
			}
		}
	}

	/**
	 *  Store only Booking into Online Sale Database (starticketmyanmar.com)
	 */
	private void postOnlineSale(String orderNo, String paymentType, String ticketList) {
		// TODO Auto-generated method stub
		
		Log.i("", "Ticket list: "+ticketList+
				", OrderNo: "+orderNo+
				", paymenttype: "+paymentType+
				", permit operator id: "+BusConfirmActivity.permit_operator_id+
				", user code: "+AppLoginUser.getCodeNo()+
				", user token: "+AppLoginUser.getAccessToken()+
				", extra city: "+BusConfirmActivity.ExtraCityName+
				", user phone: "+AppLoginUser.getPhone()+
				", user name: "+AppLoginUser.getUserName());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postOnlineSaleDB(
				orderNo
				, BusConfirmActivity.permit_operator_id
				, AppLoginUser.getCodeNo()
				, AppLoginUser.getAccessToken(), BusConfirmActivity.ExtraCityName, AppLoginUser.getPhone()
				, AppLoginUser.getUserName(), AppLoginUser.getAddress(), ""
				, "0", "0", "", "", "", "1"
				, paymentType, "", new Callback<Response>() {
			
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Error: "+arg0.getResponse().getStatus());
						}
						dialog.dismissWithFailure();
					}

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							
		    				try {
		    					
		    					Log.i("", "Booking status: "+arg1.getStatus()+", reson: "+arg1.getReason());
		    					
		    					Bundle bundle = new Bundle();
		        				bundle.putString("payment_type", "Cash on Shop");
		    					
		    					startActivity(new Intent(PaymentTypeActivity.this, ThankYouActivity.class).putExtras(bundle));
		    					
		    					dialog.dismissWithSuccess();
		    					
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		if (trip_type == 1) {
			//If one way 
			deleteSeats("", BusConfirmActivity.permit_ip, BusConfirmActivity.permit_access_token, BusConfirmActivity.sale_order_no);
		}else if (trip_type == 2) {
			//If Round Trip
			//delete Go selected seats first.... 
			deleteSeats("", goTripInfo_obj.getPermit_ip(), goTripInfo_obj.getPermit_access_token(), goTripInfo_obj.getSale_order_no());
		} 
		
		return super.getSupportParentActivityIntent();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (trip_type == 1) {
			//If one way 
			deleteSeats("", BusConfirmActivity.permit_ip, BusConfirmActivity.permit_access_token, BusConfirmActivity.sale_order_no);
		}else if (trip_type == 2) {
			//If Round Trip
			//delete Go selected seats first.... 
			deleteSeats("", goTripInfo_obj.getPermit_ip(), goTripInfo_obj.getPermit_access_token(), goTripInfo_obj.getSale_order_no());
		} 
		
	}
	
	private void deleteSeats(final String from_go_delete_success, final String permit_ip, final String permit_access_token, final String sale_order_no) {
		// TODO Auto-generated method stub
		AlertDialogWrapper.Builder alertDialog = new AlertDialogWrapper.Builder(this);
		
		if (!from_go_delete_success.equals("from_go_delete_success")) {
			//Delete Return Selected Seats
			
			alertDialog.setMessage("Are you sure You want to cancel (Departure) selected Seats?");
		}else {
			alertDialog.setMessage("Are you sure You want to cancel (Return) selected Seats?");
		}

		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (SKConnectionDetector.getInstance(
								PaymentTypeActivity.this)
								.isConnectingToInternet()) {
							
							String param = MCrypt.getInstance().encrypt(SecureParam.deleteSaleOrderParam(permit_access_token));
							
							Log.i("", "Permit IP: "+permit_ip+", Param to delete: "+param+", SaleOrderNo to delete: "+MCrypt.getInstance().encrypt(sale_order_no));
							
							progress = new ZProgressHUD(PaymentTypeActivity.this);
							progress.show();
							
							NetworkEngine.setIP(permit_ip);
							NetworkEngine.getInstance().deleteSaleOrder(
									param, MCrypt.getInstance().encrypt(sale_order_no),
									new Callback<Response>() {

										public void success(
												Response arg0,
												Response arg1) {
											
											if (!from_go_delete_success.equals("from_go_delete_success")) {
												//Delete Return Selected Seats
												
												deleteSeats("from_go_delete_success", BusConfirmActivity.permit_ip, BusConfirmActivity.permit_access_token
														, BusConfirmActivity.sale_order_no);
											}else {
												closeAllActivities();
						    					startActivity(new Intent(PaymentTypeActivity.this, SaleTicketActivity.class));
						    					progress.dismiss();
											}
										}

										public void failure(
												RetrofitError arg0) {
											// TODO Auto-generated method
											progress.dismiss();
											Log.i("", "Can't delete!");
										}
									});
						}else {
							SKConnectionDetector.getInstance(PaymentTypeActivity.this).showErrorMessage();
						}
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (dialog != null) {
							dialog.cancel();
						}
						progress.dismiss();
						return;
					}
				});

		alertDialog.show();
	}
}
