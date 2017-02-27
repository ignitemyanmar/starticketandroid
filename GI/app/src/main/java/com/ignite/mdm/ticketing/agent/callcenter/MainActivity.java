package com.ignite.mdm.ticketing.agent.callcenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;

@SuppressLint("ShowToast") public class MainActivity extends BaseSherlockActivity {
  private Context ctx = this;
  //private ActionBar actionBar;

  //Check Device connected
  public static boolean checkState = true;
  private Thread tv_update;
  TextView textView_state;
  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_WRITE = 3;
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_TOAST = 5;
  Handler mhandler = null;
  Handler handler = null;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);

    //startActivity(new Intent(ctx, UserLogin.class));
    startActivity(new Intent(ctx, UserLoginActivity.class));
    finish();
  }
}
