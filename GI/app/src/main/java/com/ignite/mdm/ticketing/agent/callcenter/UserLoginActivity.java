package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.Config;
import com.ignite.mdm.ticketing.agent.util.PrefManager;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mdm.ticketing.sqlite.database.model.GetAgentBalanceRequest;
import com.ignite.mdm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureKey;
import com.ignite.mm.ticketing.application.SecureParam;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserLoginActivity extends BaseSherlockActivity {

  private EditText txtEmail;
  private EditText txtPassword;
  private Permission permission;

  private String permit_ip;
  private String permit_access_token;
  private String permit_operator_id;
  private String permit_operator_group_id;
  private String permit_agent_id;
  private String permit_operator_phone;
  private Context ctx = this;
  private Button btn_login;
  private ProgressDialog dialog;
  private ImageButton actionBarBack;
  private SKConnectionDetector connectionDetector;
  private TextView txt_register;
  public static boolean isSkip = false;
  SharedPreferences sharedPreferences;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_phone);
    sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
    btn_login = (Button) findViewById(R.id.btn_login);
    //btn_login.setTypeface(typeface);
    btn_login.setText("၀င္မည္");
  }

  /**
   * If back arrow button clicked, close this activity.
   */
  @Override public Intent getSupportParentActivityIntent() {
    finish();
    return super.getSupportParentActivityIntent();
  }

  private OnClickListener clickListenerLogin = new OnClickListener() {

    public void onClick(View v) {
      if (v == actionBarBack) {
        finish();
      }
      //for Login button
      if (v == btn_login) {
        login();
      }

      // for User Register
      //if (v == txt_register) {
      //	startActivity(new Intent(getBaseContext(), RegisterActivity.class));
      //}
    }
  };
  private String userEmail;

  public void login() {

    if (connectionDetector.isConnectingToInternet()) {

      if (checkFields()) {

        dialog = ProgressDialog.show(ctx, "", getString(R.string.please_wait), true);
        ProgressBar progress = (ProgressBar) dialog.findViewById(android.R.id.progress);
        progress.getIndeterminateDrawable()
            .setColorFilter(getResources().getColor(R.color.gray),
                android.graphics.PorterDuff.Mode.SRC_ATOP);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        userEmail = txtEmail.getText().toString();

        Log.i("", "Enter here..... log in" + userEmail);
        //Check Email & Password on Server
        NetworkEngine.setIP("starticketmyanmar.com");
        NetworkEngine.getInstance()
            .getAccessToken("password", Config.CLIENT_ID, Config.PASSWORD, userEmail,
                txtPassword.getText().toString(), "", "", new Callback<AccessToken>() {

                  public void success(AccessToken access_token, Response response) {
                    dialog.dismiss();
                    if (response.getStatus() == 200) {
                      if (access_token != null) {
                        LoginUser user = new LoginUser(UserLoginActivity.this);
                        PrefManager.putUserName(UserLoginActivity.this,
                            txtEmail.getText().toString());
                        PrefManager.putPassword(UserLoginActivity.this,
                            txtPassword.getText().toString());

                        user.setId(access_token.getId());
                        user.setName(access_token.getName());
                        user.setEmail(access_token.getEmail());
                        user.setCodeNo(access_token.getCodeNo());
                        user.setPhone(access_token.getPhone());
                        user.setAddress(access_token.getAddress());
                        user.setAgentGroupName(access_token.getAgentgroup_name());
                        user.setRole(String.valueOf(access_token.getRole()));
                        user.setAgentGroupId(String.valueOf(access_token.getAgentgroupId()));
                        user.setGroupBranch(String.valueOf(access_token.getGroupBranch()));
                        user.setAccessToken(access_token.getAccessToken());
                        user.setCreateAt(access_token.getCreatedAt());
                        user.setUpdateAt(access_token.getUpdatedAt());
                        user.setTotal_paid(access_token.getTotal_paid());
                        user.setTotal_sold_amount(access_token.getTotal_sold_amount());
                        user.setPercentage(access_token.getPercentage());
                        user.login();
                        LoginUser appLoginUser = new LoginUser(getBaseContext());
                        getPermission();
                      }
                    } else {

                    }
                  }

                  public void failure(RetrofitError arg0) {
                    // TODO Auto-generated method stub
                    Log.i("", "Enter here... log in fail: " + arg0.getCause());

                    dialog.dismiss();

                    if (arg0.getResponse() != null) {
                      Log.i("", "Log in Fail resp: " + arg0.getResponse().getStatus());
                      if (arg0.getResponse().getStatus() == 401) {
                        SKToastMessage.showMessage(UserLoginActivity.this,
                            "Check Email and Password", SKToastMessage.ERROR);
                      }

                      if (arg0.getResponse().getStatus() == 403) {
                        SKToastMessage.showMessage(UserLoginActivity.this,
                            "Check Email and Password", SKToastMessage.ERROR);
                      }
                    } else {
                      SKToastMessage.showMessage(UserLoginActivity.this,
                          "Can't connect to server right now!", SKToastMessage.ERROR);
                    }
                  }
                });
      }
    } else {

      connectionDetector.showErrorMessage();
      SharedPreferences sharedPreferences = ctx.getSharedPreferences("User", MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();

      editor.clear();
      editor.commit();
      editor.putString("access_token", null);
      editor.putString("token_type", null);
      editor.putLong("expires", 0);
      editor.putLong("expires_in", 0);
      editor.putString("refresh_token", null);
      editor.putString("user_id", "1");
      editor.putString("user_name", "Elite");
      editor.putString("user_type", "operator");

      editor.commit();
      //Intent intent = new Intent(getApplicationContext(),	BusTripsCityActivity.class);
      //finish();
      //startActivity(intent);
    }
  }

  public boolean checkFields() {
    if (txtEmail.getText().toString().length() == 0) {
      txtEmail.setError("Enter Your UserName/Email/Phone");
      return false;
    }
    if (txtPassword.getText().toString().length() == 0) {
      txtPassword.setError("Enter Your Password");
      return false;
    }

    return true;
  }

  @Override protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
    //Check Screen Size
    Configuration config = getResources().getConfiguration();

    //txtEmail.setTypeface(typeface);
    //txtPassword.setTypeface(typeface);
    //btn_login.setTypeface(typeface);

    String AES = SecureKey.getAESKey();
    String Key = SecureKey.getKey();
    Log.e("AES KEY ",
        "@AES Key : " + AES + " , Key : " + Key + ",param > " + SecureParam.deleteAllOrderParam(
            AES));

    //Title
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      toolbar.setTitle(getString(R.string.app_name));
      this.setSupportActionBar(toolbar);
    }

    connectionDetector = SKConnectionDetector.getInstance(this);

    btn_login.setOnClickListener(clickListenerLogin);
    //txt_register = (TextView)findViewById(R.id.txt_register);
    //txt_register.setOnClickListener(clickListenerLogin);
    txtEmail = (EditText) this.findViewById(R.id.txt_login_email);
    txtPassword = (EditText) this.findViewById(R.id.txt_login_password);
    if (PrefManager.getUserName(this) != null) {
      txtEmail.setText(PrefManager.getUserName(this));
      txtPassword.setText(PrefManager.getPassword(this));
      login();
    }

    //connectionDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
    if (!connectionDetector.isConnectingToInternet()) {
      SKToastMessage.showMessage(getBaseContext(), getString(R.string.no_conneciton),
          SKToastMessage.ERROR);
    }
  }

  private void getPermission() {
    // TODO Auto-generated method stub
    // 1. Get Permission
    dialog = ProgressDialog.show(UserLoginActivity.this, "", "Please wait ...", true);
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);

    Log.e("TAG", "~~ call permission ~~");
    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .getPermission(sharedPreferences.getString("access_token", null), Config.CLIENT_OPERATOR_ID,
            new Callback<Response>() {

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  Log.i("", "Fail permission: " + arg0.getResponse().getStatus());
                }

                if (dialog != null) {
                  dialog.dismiss();
                }

                Toast.makeText(UserLoginActivity.this, "Can't connect to server!",
                    Toast.LENGTH_SHORT).show();
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
                    editor.putString("permit_ip", permit_ip);
                    editor.apply();

                    Log.e("TAG", "~ data ~ > ip: " + permit_ip + ", : " + permit_access_token);
                    String json = new GetAgentBalanceRequest(permit_access_token,
                        sharedPreferences.getString("code_no", null)).toJson();

                    String param = MCrypt.getInstance().encrypt(json);

                    if (isSkip) {
                      isSkip = false;
                      finish();
                    } else {
                      LoginUser user = new LoginUser(UserLoginActivity.this);
                      Bundle bundle = new Bundle();
                      bundle.putString("login_name", user.getUserName());
                      bundle.putString("userRole", user.getRole());
                      Intent intent =
                          new Intent(getApplicationContext(), HomeNewActivity.class).putExtras(
                              bundle);
                      startActivity(intent);
                      finish();
                    }

                    //if (skDetector.isConnectingToInternet()) {
                    //  // getAgentAmountBalance(permit_ip, param);
                    //  //getAgentAmountBalance(permit_ip, param);
                    //} else {
                    //  Toast.makeText(HomeNewActivity.this, "No Internet Connection",
                    //      Toast.LENGTH_SHORT).show();
                    //}
                  }
                }
              }
            });
  }
}
