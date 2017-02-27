package com.ignite.mdm.ticketing.agent.callcenter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.Config;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.AgentDepositLvAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentDeposit;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentPayment;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentPaymentsRequest;
import com.ignite.mdm.ticketing.sqlite.database.model.GetAgentBalanceRequest;
import com.ignite.mdm.ticketing.sqlite.database.model.PaidHistory;
import com.ignite.mdm.ticketing.sqlite.database.model.PaymentResponse;
import com.ignite.mdm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AgentDepositActivity extends BaseSherlockActivity{

	private Button btn_search;
	private TextView txt_total_deposit;
	private TextView txt_total_sale;
	private TextView txt_total_extra;
	private TextView txt_total_credit;
	private ListView lv_deposit_history;
	private TextView txt_search_fromdate;
	private TextView txt_search_todate;
	private TextView txt_search_total_deposit;
	private TextView txt_search_total_sale;
	private TextView txt_fromdate;
	private TextView txt_todate;
	private Configuration config;
	//private RelativeLayout layout_no_internet;
	private SKConnectionDetector connection;
	private ProgressDialog dialog;
	private AgentDepositLvAdapter agentDepositAdapter;
	private List<PaidHistory> paidHistoryList;
	private AgentDeposit agentDepositObj;
	//private TextView txt_deposit_default;
	private TextView txt_total_percentage;
	
	
	private Permission permission;
	
	private String permit_ip ;
	private String permit_access_token ;
	private String permit_operator_id;
	private String permit_operator_group_id;
	private String permit_agent_id;
	private String permit_operator_phone ;


	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent_deposit);
		
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		if (toolbar != null) {
			toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
			this.setSupportActionBar(toolbar);
		}
		
		config = getResources().getConfiguration();
		
		paidHistoryList = new ArrayList<PaidHistory>();
		
		//txt_deposit_default = (TextView)findViewById(R.id.txt_deposit_default);
		txt_fromdate = (TextView)findViewById(R.id.txt_fromdate);
		txt_todate = (TextView)findViewById(R.id.txt_todate);
		btn_search = (Button)findViewById(R.id.btn_search);
		
		txt_total_deposit = (TextView)findViewById(R.id.txt_total_deposit);
		txt_total_sale = (TextView)findViewById(R.id.txt_total_sale);
		txt_total_extra = (TextView)findViewById(R.id.txt_total_extra);
		txt_total_credit = (TextView)findViewById(R.id.txt_total_credit);
		
		lv_deposit_history = (ListView)findViewById(R.id.lv_deposit_history);
		
		txt_search_fromdate = (TextView)findViewById(R.id.txt_search_fromdate);
		txt_search_todate = (TextView)findViewById(R.id.txt_search_todate);
		
		txt_search_total_deposit = (TextView)findViewById(R.id.txt_search_totaldeposit);
		txt_search_total_sale = (TextView)findViewById(R.id.txt_search_totalsale);
		
		txt_total_percentage = (TextView)findViewById(R.id.txt_total_percentage);
		
		//layout_no_internet = (RelativeLayout)findViewById(R.id.layout_no_internet);
		//layout_no_internet.setOnClickListener(clickListener);
		
		connection = SKConnectionDetector.getInstance(getBaseContext());
		
		btn_search.setOnClickListener(clickListener);
		txt_fromdate.setOnClickListener(clickListener);
		txt_todate.setOnClickListener(clickListener);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String todayDate = sdf.format(cal.getTime());
		
		//Subtract 3 months from current date
		cal.add(Calendar.MONTH, -3);
		String lastThreeMonthsDate = sdf.format(cal.getTime());
		
		//Starts #1 of last three months
		String[] splitDate = lastThreeMonthsDate.split("-");
		
		txt_fromdate.setText(splitDate[0]+"-"+splitDate[1]+"-01");
		txt_todate.setText(todayDate);
		
		txt_search_fromdate.setText(getResources().getString(R.string.strmm_fromdate)+" ("+txt_fromdate.getText().toString()+")  ");
		txt_search_todate.setText(getResources().getString(R.string.strmm_todate)+" ("+txt_todate.getText().toString()+") ");
		
		if (connection.isConnectingToInternet()) {
			//getAgentDeposit();
			getPermission();
		}else {
			Toast.makeText(AgentDepositActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
			//layout_no_internet.setVisibility(View.VISIBLE);
		}
		
		
		
	}

	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_search) {
				if (connection.isConnectingToInternet()) {
					//getAgentDeposit();
					txt_search_fromdate.setText(getResources().getString(R.string.strmm_fromdate)+" ("+txt_fromdate.getText().toString()+")  ");
					txt_search_todate.setText(getResources().getString(R.string.strmm_todate)+" ("+txt_todate.getText().toString()+") ");
					
					getAgentPayments(txt_fromdate.getText().toString(),txt_todate.getText().toString());
				}else {
					Toast.makeText(AgentDepositActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
			if (v == txt_fromdate) {
		        final NewCalendarDialog calendarDialog = new NewCalendarDialog(AgentDepositActivity.this);
		        
		        calendarDialog.txt_calendar_title.setText(R.string.strmm_fromdate_title);
		        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
		      //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
		        calendarDialog.calendar.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
		        calendarDialog.calendar.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
		        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.accent));
		        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
		        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
		        calendarDialog.calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
		        calendarDialog.calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
		        
		        if (config.smallestScreenWidthDp >= 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp < 600){
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
		        }
		        
				calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						
						txt_fromdate.setText(chooseDate);
						calendarDialog.dismiss();
					}
				});
				
				calendarDialog.show();
			}
			if (v == txt_todate) {
		        final NewCalendarDialog calendarDialog = new NewCalendarDialog(AgentDepositActivity.this);
		        
		        calendarDialog.txt_calendar_title.setText(R.string.strmm_todate_title);
		        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
		      //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
		        calendarDialog.calendar.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
		        calendarDialog.calendar.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
		        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.accent));
		        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
		        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
		        calendarDialog.calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
		        calendarDialog.calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
		        
		        if (config.smallestScreenWidthDp >= 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
		        }else if (config.smallestScreenWidthDp < 600){
		        	calendarDialog.calendar.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
		        }
		        
				calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
					
					private Date today;
					public void choose(String chooseDate) {
						// TODO Auto-generated method stub
						
						txt_todate.setText(chooseDate);
						calendarDialog.dismiss();
					}
				});
				
				calendarDialog.show();
			}
		}
	};
	
	
	
	private void getPermission() {
		// TODO Auto-generated method stub
		//1. Get Permission
		dialog = ProgressDialog.show(AgentDepositActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		
		Log.e("TAG","~~ call permission ~~");
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getPermission(AppLoginUser.getAccessToken(),
				Config.CLIENT_OPERATOR_ID, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
				
				}
				Log.i("", "Fail permission: "+arg0.getCause().toString());
				
				Toast.makeText(AgentDepositActivity.this, "No Permission "+arg0.getCause(), Toast.LENGTH_SHORT).show();
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
			
				if (arg0 != null) {
					permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>(){}.getType());
					
					if (permission != null) {
						
						 permit_ip = permission.getIp();
						 permit_access_token = permission.getAccess_token();
						 permit_operator_id = permission.getOperator_id();
						 permit_operator_group_id = permission.getOperatorgroup_id();
						 permit_agent_id = permission.getOnlinesaleagent_id();
						 permit_operator_phone = permission.getOperator_phone();
						 
						String json = new GetAgentBalanceRequest(permit_access_token, AppLoginUser.getCodeNo()).toJson();
						
						String param = MCrypt.getInstance().encrypt(json);
						
						if (connection.isConnectingToInternet()) {
							getAgentAmountBalance(permit_ip, param);
						}else {
							Toast.makeText(AgentDepositActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
	}
	
	
	private void getAgentAmountBalance(String base_url,String param){
		
		Log.e("TAG","~~ call getAgentAmountBalance ~~");
		
		NetworkEngine.setIP(base_url);
		NetworkEngine.getInstance().getAgentBalance(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				Log.e("TAG"," ~ refotft File "+arg0.getLocalizedMessage());
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				AgentPayment payment = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AgentPayment>(){}.getType());
				
				dialog.dismiss();
				
				int balance = payment.getTotal_balance();
				int comission = payment.getTotal_commission();
				String paidAmount = payment.getTotal_paidamount();
				int soldamout = payment.getTotal_soldamount();
				
				txt_total_deposit.setText(paidAmount);
				txt_total_extra.setText(Integer.toString(balance));
				txt_total_sale.setText(Integer.toString(soldamout));
				txt_total_percentage.setText(Integer.toString(comission));
				
			}	
		});
	}
	
	private void getAgentPayments(String startDate,String endDate){
		
		dialog = ProgressDialog.show(AgentDepositActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		
		String json = new AgentPaymentsRequest(permit_access_token,AppLoginUser.getCodeNo(), startDate, endDate).toJson();
		String param = MCrypt.getInstance().encrypt(json);
		
		NetworkEngine.setIP(permit_ip);
		NetworkEngine.getInstance().getAgentPayments(param, new Callback<PaymentResponse>() {

			public void success(PaymentResponse paymentResponse, Response response) {
				// TODO Auto-generated method stub
				if(response.getStatus() == 200){
					dialog.dismiss();
					//Log.e("TAG","balace : "+paymentResponse.getTotalBalance()+" payment list size : "+paymentResponse.getPaymentList().size());
					agentDepositAdapter = new AgentDepositLvAdapter(AgentDepositActivity.this, paymentResponse.getPaymentList());
					lv_deposit_history.setAdapter(agentDepositAdapter);
					txt_search_total_deposit.setText(paymentResponse.getTotalPaidAmount());
					txt_search_total_sale.setText(paymentResponse.getTotalSoldAmount());
				
					
				}
			}
			
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Log.e("TAG", "Retorfit error : "+error.getMessage());
				Toast.makeText(getBaseContext(), "Can't connect to server!", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	
	
	
	private void getSearchTotal() {
		// TODO Auto-generated method stub
		Log.i("", "Search Total: "+paidHistoryList.toString());
		
		if (paidHistoryList != null) {
			if (paidHistoryList.size() > 0) {
				Double total = 0.0;
				
				for (PaidHistory ph : paidHistoryList) {
					if (ph.getDeposit() != null) {
						if (!ph.getDeposit().equals("")) {
							Double deposit = Double.valueOf(ph.getDeposit());
							total += deposit;
						}
					}
				}
				
				txt_search_total_deposit.setText(DecimalFormatter(total)+"");
				getAgentSoldAmount();
			}else {
				txt_search_total_deposit.setText("0.00");
				getAgentSoldAmount();
			}
		}else {
			txt_search_total_deposit.setText("0.00");
			getAgentSoldAmount();
		}
	}
	
	private void getAgentSoldAmount() {
		// TODO Auto-generated method stub
		if (agentDepositObj.getSearchSoldAmount() != null) {
			if (!agentDepositObj.getSearchSoldAmount().equals("")) {
				Double searchTotalAmount = Double.valueOf(agentDepositObj.getSearchSoldAmount());
				txt_search_total_sale.setText(DecimalFormatter(searchTotalAmount));
			}else {
				txt_search_total_sale.setText("0.00");
			}
		}else {
			txt_search_total_sale.setText("0.00");
		}
	}
	
	
	
//	private void getAgentDeposit() {
//		// TODO Auto-generated method stub
//		dialog = ProgressDialog.show(AgentDepositActivity.this, "", "Please wait ...", true);
//		dialog.setCancelable(true);
//		
//		Log.i("", "Agent Deposit Objs: "+AppLoginUser.getAccessToken()+", "+AppLoginUser.getId()+", "
//										+txt_fromdate.getText().toString()+", "+txt_todate.getText().toString());
//		
//		
//		NetworkEngine.getInstance().getAgentDeposit(AppLoginUser.getAccessToken(), AppLoginUser.getId()
//				, txt_fromdate.getText().toString(), txt_todate.getText().toString(), new Callback<AgentDeposit>() {
//			
//			public void success(AgentDeposit arg0, Response arg1) {
//				// TODO Auto-generated method stub
//				if (arg0 != null) {
//					Log.i("", "Agent Deposit: "+arg0.toString());
//					
//					agentDepositObj = null;
//					agentDepositObj = arg0;
//					
//					//Show All Totals 
//					Double total_deposit = 0.0;
//					Double total_sale = 0.0;
//					Double total_extra = 0.0;
//					Double total_credit = 0.0;
//					Double total_percentage = 0.0;
//					Double total_percentage_amt = 0.0;
//					//Double default_deposit = 0.00;
//					
//					if (agentDepositObj.getTotalPaid() != null) {
//						total_deposit = Double.valueOf(agentDepositObj.getTotalPaid());
//						//default_deposit = total_deposit - 100000;
//						
//						if (agentDepositObj.getTotalSoldAmount() != null) {
//							total_sale = Double.valueOf(agentDepositObj.getTotalSoldAmount());
//							
//							//Commision
//							total_percentage = agentDepositObj.getPercentage();
//							total_percentage_amt = total_sale * total_percentage / 100;
//							
//							total_deposit = Double.valueOf(agentDepositObj.getTotalPaid()) + total_percentage_amt;
//							total_extra = total_deposit - total_sale;
//							total_credit = total_sale - total_deposit;
//							
//						}
//						
//						//Show Total Payment, not include (default deposit) 100,000 Ks 
///*						if (default_deposit >= 0) {
//							//txt_deposit_default.setText(getResources().getString(R.string.strmm_default_deposit)+":  100,000 Ks");
//							
//						}else {
//							txt_deposit_default.setText(getResources().getString(R.string.strmm_default_deposit)+" 0.00 Ks");
//						}*/
//						
//						txt_total_deposit.setText(DecimalFormatter(Double.valueOf(agentDepositObj.getTotalPaid())));
//						txt_total_sale.setText(DecimalFormatter(total_sale));
//						
//						NumberFormat nf = NumberFormat.getInstance();
//						txt_total_percentage.setText(DecimalFormatter(total_percentage_amt)+" ("+nf.format(total_percentage)+"%)");
//					}
//					
//					//Extra Amount Show
//					if (total_extra >= 0) {
//						txt_total_extra.setText(DecimalFormatter(total_extra));
//						txt_total_credit.setText(0.00+"");
//						txt_total_extra.setBackgroundColor(getResources().getColor(R.color.green));
//						txt_total_credit.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//					}
//					
//					//Credit Amount Show
//					if (total_credit >= 0) {
//						txt_total_extra.setText(0.00+"");
//						txt_total_credit.setText(DecimalFormatter(total_credit));
//						txt_total_credit.setBackgroundColor(getResources().getColor(R.color.accent_dark));
//						txt_total_extra.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//					}
//					
//					//Zero Balance Show
//					if (total_extra == 0.0 && total_credit == 0.0) {
//						txt_total_credit.setText("0.00");
//						txt_total_extra.setText("0.00");
//						txt_total_credit.setBackgroundColor(getResources().getColor(R.color.accent_dark));
//						txt_total_extra.setBackgroundColor(getResources().getColor(R.color.accent_dark));
//					}
//					
//					if (agentDepositObj.getPaidHistory() != null) {
//						if (agentDepositObj.getPaidHistory().size() > 0) {
//							
//							paidHistoryList = agentDepositObj.getPaidHistory();
//							
//							agentDepositAdapter = new AgentDepositLvAdapter(AgentDepositActivity.this, paidHistoryList);
//							lv_deposit_history.setAdapter(agentDepositAdapter);
//							getSearchTotal();
//							
//						}else {
//							lv_deposit_history.setAdapter(null);
//							paidHistoryList.clear();
//							getSearchTotal();
//							//clearTotal();
//							Toast.makeText(getBaseContext(), "No Payment History", Toast.LENGTH_SHORT).show();
//						}
//					}else {
//						lv_deposit_history.setAdapter(null);
//						paidHistoryList.clear();
//						getSearchTotal();
//						//clearTotal();
//						Toast.makeText(getBaseContext(), "No Payment History", Toast.LENGTH_SHORT).show();
//					}
//				}else {
//					lv_deposit_history.setAdapter(null);
//					txt_search_total_sale.setText("0.00");
//					txt_search_total_deposit.setText("0.00");
//					//clearTotal();
//					Toast.makeText(getBaseContext(), "No Payment History", Toast.LENGTH_SHORT).show();
//				}
//				
//				if (dialog != null) {
//					dialog.dismiss();
//				}
//			}
//			
//			public void failure(RetrofitError arg0) {
//				// TODO Auto-generated method stub
//				if (arg0.getResponse() != null) {
//					Toast.makeText(getBaseContext(), "Can't connect to server!", Toast.LENGTH_SHORT).show();
//				}
//				
//				if (dialog != null) {
//					dialog.dismiss();
//				}
//			}
//		});
//	}
	
	private String DecimalFormatter(Double doubleValue) {
		// TODO Auto-generated method stub
		
		//Decimal Format
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(doubleValue);
	}
	
	private void clearTotal() {
		// TODO Auto-generated method stub
		txt_total_deposit.setText("0.00");
		txt_total_credit.setText("0.00");
		txt_total_extra.setText("0.00");
		txt_total_sale.setText("0.00");
		txt_total_extra.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		txt_total_credit.setBackgroundColor(getResources().getColor(android.R.color.transparent));
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
