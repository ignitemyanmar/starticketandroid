package com.ignite.mdm.ticketing.agent.callcenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.agent.util.PrefManager;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentDeposit;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentPayment;
import com.ignite.mdm.ticketing.sqlite.database.model.GetAgentBalanceRequest;
import com.ignite.mdm.ticketing.sqlite.database.model.NotiCounts;
import com.ignite.mdm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.text.DecimalFormat;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@SuppressLint("CutPasteId") public class HomeNewActivity extends AppCompatActivity {

  //private FrameLayout btn_sale_tickets;
  //private TextView btn_book_confirm;
  //private ActionBar actionBar;
  //private TextView actionBarTitle;
  //private TextView actionBarTitle2;
  //private ImageButton actionBarBack;
  //private TextView btn_three_day_sales;
  //private Button btn_book_confirm_user;
  private String login_name;
  private String userRole;
  //private TextView txt_book_by_user;
  //private TextView btn_khoneat;
  //private TextView btn_delivery_list;
  //private TextView txt_delivery;
  //private TextView txt_khoneat;
  //private TextView btn_agent_seats_booking;
  //private CardView textViewFillDeposit;
  //private FrameLayout btn_tour_package;
  //private FrameLayout btn_car_rental;
  //private TextView txt_agent_deposit;
  //private LinearLayout layout_user_profile;
  //private TextView txt_net_deposit;
  private TextView txt_user_name;
  private LoginUser appLoginUser;

  private SKConnectionDetector skDetector;
  private ProgressDialog dialog;
  private NotiCounts notiCounts;
  //private LinearLayout layout_btn_agent_seats_booking;
  //private LinearLayout layout_btn_delivery_list;
  //private FrameLayout noti_booking_frame;
  //private FrameLayout noti_delivery_frame;
  //private FrameLayout noti_tour_booking_frame;
  //private TextView txt_noti_booking_count;
  //private TextView txt_noti_delivery_count;
  private LinearLayout btnSaleTickets;
  private LinearLayout btnThreeDaySales;
  private LinearLayout txtAgentDeposit;
  private LinearLayout textViewFillDeposit;

  //private TextView txt_noti_tour_booking_count;
  private Integer noti_booking_count = 0;
  private Integer noti_delivery_count = 0;
  private Integer noti_agentorder_count = 0;
  //private LinearLayout layout_btn_book_confirm;

  private SharedPreferences sharedPreferences;

  private Permission permission;

  private String permit_ip;
  private String permit_access_token;
  private String permit_operator_id;
  private String permit_operator_group_id;
  private String permit_agent_id;
  private String permit_operator_phone;
  private LinearLayout invoice;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);

    sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
  }

  @Override protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
    // Toast.makeText(getBaseContext(),
    // "Default Lang: "+Locale.getDefault().toString(),
    // Toast.LENGTH_LONG).show();

    skDetector = SKConnectionDetector.getInstance(this);

    setContentView(R.layout.activity_home_new_layout);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      login_name = bundle.getString("login_name");
      userRole = bundle.getString("userRole");
    }

    // Title
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
      toolbar.setTitle("Menu");
      this.setSupportActionBar(toolbar);
    }
    btnSaleTickets = (LinearLayout) findViewById(R.id.btn_sale_tickets);
    btnThreeDaySales = (LinearLayout) findViewById(R.id.btn_three_day_sales);
    txtAgentDeposit = (LinearLayout) findViewById(R.id.txt_agent_deposit);
    textViewFillDeposit = (LinearLayout) findViewById(R.id.text_view_fill_deposit);
    invoice = (LinearLayout) findViewById(R.id.invoices);
    invoice.setOnClickListener(clickListener);

    btnSaleTickets.setOnClickListener(clickListener);
    btnThreeDaySales.setOnClickListener(clickListener);

    txtAgentDeposit.setOnClickListener(clickListener);
    textViewFillDeposit.setOnClickListener(clickListener);
    //textViewFillDeposit = (CardView) findViewById(R.id.text_view_fill_deposit);
    ////txt_net_deposit = (TextView) findViewById(R.id.txt_net_deposit);
    //txt_user_name = (TextView) findViewById(R.id.txt_user_name);
    //layout_user_profile = (LinearLayout) findViewById(R.id.layout_user_profile);
    //txt_agent_deposit = (TextView) findViewById(R.id.txt_agent_deposit);
    //btn_agent_seats_booking = (TextView) findViewById(R.id.btn_agent_seats_booking);
    ////layout_btn_agent_seats_booking = (LinearLayout) findViewById(R.id.layout_btn_agent_seats_booking);
    //btn_khoneat = (TextView) findViewById(R.id.btn_khoneat);
    //btn_three_day_sales = (TextView) findViewById(R.id.btn_three_day_sales);
    //btn_book_confirm = (TextView) findViewById(R.id.btn_book_confirm);

    //layout_btn_book_confirm = (LinearLayout) findViewById(R.id.layout_btn_book_confirm);
    //layout_btn_book_confirm.setVisibility(View.GONE);

    //btn_sale_tickets = (FrameLayout) findViewById(R.id.btn_sale_tickets);
    //
    //btn_tour_package = (FrameLayout) findViewById(R.id.btn_tour_package);
    //
    //btn_car_rental = (FrameLayout) findViewById(R.id.btn_car_rental);

    appLoginUser = new LoginUser(getBaseContext());
    // btn_book_confirm_user =
    // (Button)findViewById(R.id.btn_book_confirm_user);
    // txt_book_by_user = (TextView)findViewById(R.id.txt_book_by_user);
    //btn_delivery_list = (TextView) findViewById(R.id.btn_delivery_list);
    //layout_btn_delivery_list = (LinearLayout) findViewById(R.id.layout_btn_delivery_list);
    // btn_sale_by_tripDate = (Button)findViewById(R.id.btn_khoneat);
    // txt_delivery = (TextView)findViewById(R.id.txt_delivery);

    //noti_booking_frame = (FrameLayout) findViewById(R.id.noti_booking);
    //noti_delivery_frame = (FrameLayout) findViewById(R.id.noti_delivery);
    //noti_tour_booking_frame = (FrameLayout) findViewById(R.id.noti_tour_booking);

    //txt_noti_booking_count = (TextView) findViewById(R.id.noti_booking_count);
    //txt_noti_delivery_count = (TextView) findViewById(R.id.noti_delivery_count);
    //txt_noti_tour_booking_count = (TextView) findViewById(R.id.noti_tour_booking_count);

    //txt_net_deposit_title = (TextView) findViewById(R.id.txt_net_deposit_title);

    //btn_delivery_list.setOnClickListener(clickListener);
    //layout_btn_book_confirm.setOnClickListener(clickListener);
    //layout_btn_agent_seats_booking.setOnClickListener(clickListener);
    //layout_btn_delivery_list.setOnClickListener(clickListener);
    //textViewFillDeposit.setOnClickListener(clickListener);

    Log.i("", "role : " + userRole + ", user name: " + login_name);

    if (userRole != null) {
      // If Agents (Online)
      if (Integer.valueOf(userRole) <= 3) {

        // txt_book_by_user.setVisibility(View.GONE);
        // btn_book_confirm_user.setVisibility(View.GONE);

        // txt_delivery.setVisibility(View.GONE);
        // btn_delivery_list.setVisibility(View.GONE);

        // For Agent
        //btn_khoneat.setVisibility(View.GONE);
        //layout_btn_agent_seats_booking.setVisibility(View.GONE);
        //layout_btn_delivery_list.setVisibility(View.GONE);
        //layout_user_profile.setVisibility(View.VISIBLE);

        // Show Agent's Net Deposit Amount
        if (skDetector.isConnectingToInternet()) {
          // getAgentDeposit();
          getPermission();
        } else {
          Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT)
              .show();
        }

        //txt_user_name.setText(appLoginUser.getUserName());
      } else {
        // If Call Center
        //txt_agent_deposit.setVisibility(View.GONE);
        // Get Notification Counts
        if (skDetector.isConnectingToInternet()) {
          // Get Booking Noti + Delivery Noti + Seat Booking Noti
          // (Counts)
          getNotiCounts();
        } else {
          Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT)
              .show();
        }
      }
    }
    //btn_book_confirm.setOnClickListener(clickListener);
    //layout_btn_book_confirm.setOnClickListener(clickListener);
    //layout_btn_delivery_list.setOnClickListener(clickListener);
    //layout_btn_agent_seats_booking.setOnClickListener(clickListener);
    //btn_agent_seats_booking.setOnClickListener(clickListener);
    //btn_three_day_sales.setOnClickListener(clickListener);
    //btn_sale_tickets.setOnClickListener(clickListener);
    ////btn_tour_package.setOnClickListener(clickListener);
    ////btn_car_rental.setOnClickListener(clickListener);
    ////btn_khoneat.setOnClickListener(clickListener);
    //txt_agent_deposit.setOnClickListener(clickListener);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_logout, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // TODO Auto-generated method stub
    switch (item.getItemId()) {
      case R.id.logout:
        // Get Notification Coun
        PrefManager.clearPreferences(HomeNewActivity.this);

        startActivity(new Intent(HomeNewActivity.this, UserLoginActivity.class));
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void getPermission() {
    // TODO Auto-generated method stub
    // 1. Get Permission
    dialog = ProgressDialog.show(HomeNewActivity.this, "", "Please wait ...", true);
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);

    Log.e("TAG", "~~ call permission ~~");
    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .getPermission(sharedPreferences.getString("access_token", null), "3",
            new Callback<Response>() {

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  Log.i("", "Fail permission: " + arg0.getResponse().getStatus());
                }

                if (dialog != null) {
                  dialog.dismiss();
                }

                Toast.makeText(HomeNewActivity.this, "Can't connect to server!", Toast.LENGTH_SHORT)
                    .show();
              }

              public void success(Response arg0, Response arg1) {
                // TODO Auto-generated method stub

                if (arg0 != null) {
                  permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>() {
                  }.getType());

                  if (permission != null) {
                    permit_ip = permission.getIp();
                    permit_access_token = permission.getAccess_token();
                    permit_operator_id = permission.getOperator_id();
                    permit_operator_group_id = permission.getOperatorgroup_id();
                    permit_agent_id = permission.getOnlinesaleagent_id();
                    permit_operator_phone = permission.getOperator_phone();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("operator_token", permit_access_token);
                    editor.apply();
                    Log.e("TAG", "~ data ~ > ip: " + permit_ip + ", : " + permit_access_token);
                    String json = new GetAgentBalanceRequest(permit_access_token,
                        sharedPreferences.getString("code_no", null)).toJson();

                    String param = MCrypt.getInstance().encrypt(json);

                    if (skDetector.isConnectingToInternet()) {
                      // getAgentAmountBalance(permit_ip, param);
                      getAgentAmountBalance(permit_ip, param);
                    } else {
                      Toast.makeText(HomeNewActivity.this, "No Internet Connection",
                          Toast.LENGTH_SHORT).show();
                    }
                  }
                }
              }
            });
  }

  private void getAgentAmountBalance(String base_url, String param) {

    Log.e("TAG", "~~ call getAgentAmountBalance ~~");

    NetworkEngine.setIP(base_url);
    NetworkEngine.getInstance().getAgentBalance(param, new Callback<Response>() {

      public void failure(RetrofitError arg0) {
        // TODO Auto-generated method stub
        if (dialog != null) {
          dialog.dismiss();
        }

        Toast.makeText(HomeNewActivity.this, "Can't connect to server!", Toast.LENGTH_SHORT).show();
        Log.e("TAG", " ~ refotft File " + arg0.getLocalizedMessage());
      }

      public void success(Response arg0, Response arg1) {
        // TODO Auto-generated method stub
        AgentPayment payment =
            DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AgentPayment>() {
            }.getType());

        dialog.dismiss();

        int balance = payment.getTotal_balance();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("balance", balance).apply();

        int comission = payment.getTotal_commission();
        String paidAmount = payment.getTotal_paidamount();
        int soldamout = payment.getTotal_soldamount();

        if (balance <= 0) {
          showAlert("Your balance is low! \n Please refill");
        }
        //txt_net_deposit.setText("Ks " + balance);
        //txt_net_deposit.setBackgroundColor(getResources().getColor(R.color.green));

        // txt_total_deposit.setText(paidAmount);
        // txt_total_extra.setText(Integer.toString(balance));
        // txt_total_sale.setText(Integer.toString(soldamout));
        // txt_total_percentage.setText(Integer.toString(comission));

      }
    });
  }

  private String DecimalFormatter(Double doubleValue) {
    // TODO Auto-generated method stub

    // Decimal Format
    DecimalFormat formatter = new DecimalFormat("#,###");
    return formatter.format(doubleValue);
  }

  private OnClickListener clickListener = new OnClickListener() {

    public void onClick(View v) {
      // TODO Auto-generated method stub

      if (v == textViewFillDeposit) {
        startActivity(new Intent(getApplicationContext(), AgentDepositFillActivity.class));
      }

      //TODO This should be changed
      if (v == txtAgentDeposit) {
        startActivity(new Intent(getBaseContext(), OutstandingActivity.class));
      }

      //TODO This should be changed
      if (v == btnSaleTickets) {
        Bundle bundle = new Bundle();
        bundle.putString("from_intent", "sale");
        startActivity(
            new Intent(getApplicationContext(), SaleTicketActivity.class).putExtras(bundle));
      }

      if (v == invoice) {
        startActivity(new Intent(getApplicationContext(), InvoiceActivity.class));
      }
      //TODO This Should be changed
      if (v == btnThreeDaySales) {
        startActivity(new Intent(getApplicationContext(), ThreeDaySalesActivity.class));
      }
    }
  };

  protected AgentDeposit agentDepositObj;

  //private TextView txt_net_deposit_title;
  public void showAlert(String message) {
    // TODO Auto-generated method stub
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    //alert.setIcon(R.drawable.attention_icon);
    //alert.setTitle("Warning");
    alert.setMessage(message);
    alert.show();
  }

  /**
   * Get Agent's Deposit Amount
   */

  private void getAgentDeposit() {
    // TODO Auto-generated method stub
    dialog = ProgressDialog.show(HomeNewActivity.this, "", "Please wait ...", true);
    dialog.setCancelable(true);

    Log.i("", "token: " + appLoginUser.getAccessToken() + ", user id: " + appLoginUser.getId());

    NetworkEngine.getInstance()
        .getAgentDeposit(appLoginUser.getAccessToken(), appLoginUser.getId(), "", "",
            new Callback<AgentDeposit>() {

              public void success(AgentDeposit arg0, Response arg1) {
                // TODO Auto-generated method stub
                if (arg0 != null) {
                  //Log.i("", "Agent Deposit: " + arg0.toString());
                  //
                  //agentDepositObj = null;
                  //agentDepositObj = arg0;
                  //
                  //Double totalPaid = 0.0;
                  //Double totalSale = 0.0;
                  //Double extraAmount = 0.0;
                  //Double creditAmount = 0.0;
                  //
                  //if (agentDepositObj.getTotalPaid() != null) {
                  //  if (!agentDepositObj.getTotalPaid().equals("")) {
                  //    totalPaid = Double.valueOf(agentDepositObj.getTotalPaid());
                  //  }
                  //}
                  //
                  //if (agentDepositObj.getTotalSoldAmount() != null) {
                  //  if (!agentDepositObj.getTotalSoldAmount().equals("")) {
                  //    totalSale = Double.valueOf(agentDepositObj.getTotalSoldAmount());
                  //  }
                  //}
                  //
                  //if (agentDepositObj.getPercentage() != null) {
                  //  totalPaid = Double.valueOf(agentDepositObj.getTotalPaid()) + (totalSale
                  //      * agentDepositObj.getPercentage() / 100);
                  //}

                  //extraAmount = totalPaid - totalSale;
                  //creditAmount = totalSale - totalPaid;

                  //NumberFormat nf = NumberFormat.getInstance();
                  // Show Extra Amount
                  //if (extraAmount > 0) {
                  //  txt_net_deposit_title.setText(
                  //      getResources().getString(R.string.strmm_deposit_net));
                  //  txt_net_deposit.setText("Ks " + nf.format(extraAmount));
                  //  txt_net_deposit.setBackgroundColor(getResources().getColor(R.color.green));
                  //}

                  // Show Credit Amount
                  //if (creditAmount > 0) {
                  //  txt_net_deposit_title.setText(
                  //      getResources().getString(R.string.strmm_credit_title));
                  //  txt_net_deposit.setText("Ks " + nf.format(creditAmount));
                  //  txt_net_deposit.setBackgroundColor(
                  //      getResources().getColor(R.color.accent_dark));
                  //}
                  //
                  //if (extraAmount == 0 || creditAmount == 0) {
                  //  txt_net_deposit_title.setText(
                  //      getResources().getString(R.string.strmm_deposit_net));
                  //  txt_net_deposit.setText("Ks 0.00");
                  //  txt_net_deposit.setBackgroundColor(
                  //      getResources().getColor(R.color.accent_dark));
                  //}
                }

                if (dialog != null) {
                  dialog.dismiss();
                }
              }

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  Toast.makeText(getBaseContext(), "Can't connect to server!", Toast.LENGTH_SHORT)
                      .show();
                  Log.i("", "Error: " + arg0.getCause());
                }

                if (dialog != null) {
                  dialog.dismiss();
                }
              }
            });
  }

  /**
   * Get Notification Counts for Booking, Agent Seat Booking, Delivery
   */
  private void getNotiCounts() {
    // TODO Auto-generated method stub
    dialog = ProgressDialog.show(HomeNewActivity.this, "", "Loading Noti...", true);
    dialog.setCancelable(true);

    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance().getNotiCounts(new Callback<NotiCounts>() {

      public void success(NotiCounts arg0, Response arg1) {
        // TODO Auto-generated method stub
        if (arg0 != null) {

          Log.i("", "Noti obj: " + arg0.toString());
          noti_booking_count = arg0.getBookingnoti_count();
          noti_delivery_count = arg0.getDeliverynoti_count();
          noti_agentorder_count = arg0.getAgentordernoti_count();

          if (dialog != null) {
            dialog.dismiss();
          }
        }
      }

      public void failure(RetrofitError arg0) {
        // TODO Auto-generated method stub
        if (arg0.getResponse() != null) {
          Log.i("", "Error: " + arg0.getResponse().getStatus());
        }

        if (dialog != null) {
          dialog.dismiss();
        }
      }
    });
  }

  private void restartActivity() {
    Intent intent = getIntent();
    finish();
    startActivity(intent);
  }

  /**
   * If back arrow button clicked, close this activity.
   */
  @Override public Intent getSupportParentActivityIntent() {
    // TODO Auto-generated method stub
    finish();
    return super.getSupportParentActivityIntent();
  }
}
