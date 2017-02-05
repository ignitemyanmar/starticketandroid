package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityChangePasswordBinding;
import com.ignite.mdm.ticketing.agent.util.PrefManager;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.sqlite.database.model.ChangePasswordStatus;
import com.smk.skconnectiondetector.SKConnectionDetector;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChangePasswordActivity extends AppCompatActivity {
  ActivityChangePasswordBinding binding;
  SKConnectionDetector skConnectionDetector;
  SharedPreferences pref;
  public static final int DONE = 1;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    skConnectionDetector = new SKConnectionDetector(this);
    pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
    binding.confirm.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });
    binding.change.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (skConnectionDetector.isConnectingToInternet()) {
          if (binding.oldPassword.getText()
              .toString()
              .equals(binding.confirm.getText().toString())) {
            final ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this);
            progressDialog.show();
            NetworkEngine.getInstance()
                .changePassword(pref.getString("access_token", null),
                    pref.getString("user_id", null), binding.oldPassword.getText().toString(),
                    binding.newPassword.getText().toString(), new Callback<ChangePasswordStatus>() {
                      @Override public void success(ChangePasswordStatus changePasswordStatus,
                          Response response) {
                        progressDialog.dismiss();
                        if (changePasswordStatus.getStatus().equals("200")) {
                          SharedPreferences.Editor editor = pref.edit();
                          editor.clear();
                          editor.commit();
                          PrefManager.clearPreferences(ChangePasswordActivity.this);
                          Toast.makeText(ChangePasswordActivity.this, "Success", Toast.LENGTH_SHORT)
                              .show();
                          setResult(DONE);
                          finish();
                        } else {
                          Toast.makeText(ChangePasswordActivity.this, "Error Occured",
                              Toast.LENGTH_SHORT).show();
                        }
                      }

                      @Override public void failure(RetrofitError retrofitError) {
                        retrofitError.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(ChangePasswordActivity.this,
                            "Your Current Password is Wrong", Toast.LENGTH_SHORT).show();
                      }
                    });
          } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT)
                .show();
          }
        } else {
          Toast.makeText(getApplicationContext(), "Your Passwords Do not Match", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
  }
}

