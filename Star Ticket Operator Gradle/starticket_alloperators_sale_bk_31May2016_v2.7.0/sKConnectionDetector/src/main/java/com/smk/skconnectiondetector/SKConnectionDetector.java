package com.smk.skconnectiondetector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
 
public class SKConnectionDetector {
     
    private static SKConnectionDetector instance;
	private Context _context;
	public static final int HORIZONTAL_TOASH = 1;
	public static final int VERTICAL_TOASH = 2;
	private int MessageStyle;
     
    public SKConnectionDetector(Context context){
        this._context = context;
    }
    
    public static SKConnectionDetector getInstance(Context context) {
    	if(instance == null){
    		instance = new SKConnectionDetector(context);
		}
		return instance;
	}
 
    public boolean isConnectingToInternet(){
    	boolean status = false;
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                    	  status = true;
                      }
 
          }
          return status;
    }
    public void showErrorDialog(){
    	showAlertDialog(this._context);
    }
    
    public void showErrorMessage(){
		Toast toast = new Toast(_context);
		View mView = null;
		switch (getMessageStyle()) {
		case HORIZONTAL_TOASH:
			mView = View.inflate(_context, R.layout.horizontal_toast, null);
			break;
		case VERTICAL_TOASH:
			mView = View.inflate(_context, R.layout.vertical_toast, null);
			break;
		default:
			mView = View.inflate(_context, R.layout.horizontal_toast, null);
			break;
		}

		toast.setView(mView);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
		        0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
    }
    
	public int getMessageStyle() {
		return MessageStyle;
	}

	public void setMessageStyle(int messageStyle) {
		MessageStyle = messageStyle;
	}

	public void showAlertDialog(Context context) {
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
      	 
        // Setting Dialog Title
        alertDialog.setTitle("No Internet Connection.");
 
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	_context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    	
    }
}
