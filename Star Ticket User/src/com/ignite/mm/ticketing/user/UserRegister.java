package com.ignite.mm.ticketing.user;

import info.hoang8f.widget.FButton;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.user.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegister extends BaseActivity implements OnClickListener {
	
	private ZProgressHUD dialog;
	private ImageButton UserPhoto;
	private MaterialEditText txtFirstname;
	private MaterialEditText txtLastname;
	private MaterialEditText txtNRC;
	private MaterialEditText txtEmail;
	private MaterialEditText txtPassword;
	private MaterialEditText txtCPassword;
	private MaterialEditText txtPhone;
	private MaterialEditText txtAddress , txtBankAccNo;
	private Intent intent;
	private String photoname;
	private String Splitphotoname = "default.png";
	private String status;
	int serverResponseCode = 0;
	private ActionBar actionBar;
	//private static String http = "http://10.0.2.2/index.php/mobile";
	private static String http = "http://www.mmjunction.com/index.php/mobile/";
    private static String user_register = http +"user";
    
    private static final int SELECT_PICTURE = 1;
    String serverResponseMessage;
    Uri selectedImageUri;
	private String FirstName;
	private String LastName;
	private String Password;
	private String CPassword;
	private String NRC;
	private String Phone;
	private String Address;
	private String UserName;
	private String UserEmail;
	private String UserPassword , BankAccNo;
	private String selectedImagePath;
	String uploadFilePath;
	String uploadFileName;
	private String separatedfileName;
	String[] seperated; 
	String userid;
	private String upLoadServerUri = "http://www.mmjunction.com/mobile/user/upload" ;
	private SKConnectionDetector connectionDetector;
	private View actionBarBack;
	private TextView actionBarTitle;
	
	private String TicketTypeID, BusDesID, BusDesName, Date, AgentID, AgentName, DepID, DepTime, SelectedSeatIndex;
	private TextView actionBarTitle2;
	private SKConnectionDetector checkInternet;
	private MaterialEditText txtUserName;
	
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_register);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Register");
            this.setSupportActionBar(toolbar);
        }
        
        checkInternet = SKConnectionDetector.getInstance(this);
                
        /*SharedPreferences pref = this.getApplicationContext().getSharedPreferences("User", Activity.MODE_PRIVATE);
		userid = pref.getString("userid",null);*/
		
        UserPhoto = (ImageButton) this.findViewById(R.id.user_photo);
        txtUserName = (MaterialEditText) this.findViewById(R.id.txt_user_name);
        txtEmail = (MaterialEditText) this.findViewById(R.id.txtUserEmail);
        txtPassword = (MaterialEditText) this.findViewById(R.id.txtUserPassword);
        txtCPassword = (MaterialEditText) this.findViewById(R.id.txtUserConfirmPassword);
        //txtBankAccNo = (MaterialEditText) this.findViewById(R.id.txtBankAccNo);
        txtPhone = (MaterialEditText)this.findViewById(R.id.txtPhone);
        txtAddress = (MaterialEditText)this.findViewById(R.id.txtAddress);
        UserPhoto.setTag("nopic");
        FButton btnRegister =(FButton)findViewById(R.id.btnRegister);
        btnRegister.setButtonColor(getResources().getColor(R.color.yellow));
        btnRegister.setShadowEnabled(true);
        btnRegister.setShadowHeight(3);
        btnRegister.setCornerRadius(7);
        
        btnRegister.setOnClickListener(this);
       
        UserPhoto.setOnClickListener(new OnClickListener() {
              public void onClick(View arg0) {
            	  
            	  intent = new Intent();
                  intent.setType("image/*");
                  intent.setAction(Intent.ACTION_GET_CONTENT);
                  startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

              }
        });
   
    }    
    
    
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
        	
            if (requestCode == SELECT_PICTURE) {
            	
                selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                UserPhoto.setImageURI(selectedImageUri);
               
                uploadFilePath = selectedImagePath.toString();
                seperated = uploadFilePath.split("/");
           	 	Integer s = seperated.length;
           	 	UserPhoto.setTag("Image");
           	 	separatedfileName = seperated[s-1].toString().trim();
           	 	
            }
        }
    }
    
    
    //This Method is to get the Image Path from Gallery
	public String getPath(Uri uri) {
		
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
        
    }
	
	//Register Click Listener
    public void onClick(View v) {
    	
    	if (checkInternet.isConnectingToInternet()) {
    		
	    	if(checkField()){
	    		
	    		dialog = new ZProgressHUD(UserRegister.this);
	    		dialog.show();
		        
	    		UserName = txtUserName.getText().toString();
	    		UserEmail = txtEmail.getText().toString();
	    		UserPassword = txtPassword.getText().toString();
	    		Phone = txtPhone.getText().toString();
	    		Address = txtAddress.getText().toString();
	    		
	    		ResgisterUser();
	    		
	    		/*if(UserPhoto.getTag() != "nopic")
	    		{   			
	    			new Thread(new Runnable() {
	    				
	    	            public void run() {
	    	            	
	    	                 runOnUiThread(new Runnable() {
	    	                        public void run() {
	    	                        	
	    	                        	try {
	    									
	    	                        		 
	    								} catch (Exception e) {
	    									// TODO: handle exception
	    								}
	    	                        }
	    	                    });
	    	                 Looper.prepare();
	    	                 
	    	                 //Upload Image File
	    	                 uploadFile(uploadFilePath);
	    	                 ResgisterUser();
	    	                 
	    	                 Looper.loop();
	    	            }
	    	        
	    	          }).start();
	    		}
	    		else
	    		{
	    			 ResgisterUser();
	    		}*/
	    	}//End Check Fields
		}else {
			checkInternet.showErrorDialog();
		}
	}
    
    private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}

		}
	};


    //(Register) Submit Button Click
	private void ResgisterUser() {
		
		if(UserLogin.isSkip){
			
			UserLogin.isSkip = false;
			SharedPreferences sharedPreferences = this.getSharedPreferences("User",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				
				editor.clear();
				editor.commit();
				editor.putString("userid", "1");
				editor.putString("useremail", "hits@gmail.com");
				editor.putString("username", "Hits");

				editor.commit();
			//Intent intent = new Intent(getApplicationContext(),	Bus_Info_Activity.class);
			finish();
			startActivity(intent);
		}else{
			
			Log.i("", "Enter here.................... (Register)");
			
			
			NetworkEngine.getInstance().postRegister(UserName, UserEmail, UserPassword
					, Phone, Address, new Callback<LoginUser>() {
				
				public void success(LoginUser arg0, Response arg1) {
					// TODO Auto-generated method stub
					if(arg1.getStatus() == 200){
						
						if (arg0 != null) {
							Log.i("", "Success .............................. "+arg0.toString());
							
							SharedPreferences sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedPreferences.edit();
							
							editor.clear();
							editor.commit();
							
							editor.putString("message", arg0.getMessage());
							editor.putString("user_id", String.valueOf(arg0.getUser().getId()));
							editor.putString("user_name", arg0.getUser().getName());
							editor.putString("email", arg0.getUser().getEmail());
							editor.putString("code_no", arg0.getUser().getCodeNo());
							editor.putString("role", String.valueOf(arg0.getUser().getRole()));
							editor.putString("agent_group_id", String.valueOf(arg0.getUser().getAgentgroupId()));
							editor.putString("group_branch", String.valueOf(arg0.getUser().getGroupBranch()));
							editor.putString("create_at", arg0.getUser().getCreatedAt());
							editor.putString("update_at", arg0.getUser().getUpdatedAt());
							editor.putString("phone", arg0.getUser().getPhone());
							editor.putString("address", arg0.getUser().getAddress());
							editor.putString("agentGroupName", arg0.getUser().getAgentgroupName());
							editor.commit();
							
							finish();
							
							SKToastMessage.showMessage(UserRegister.this, "သင္၏ Account ကုိ 'မွတ္ ပံု တင္' ၿပီးပါၿပီ", SKToastMessage.SUCCESS);
						}
					}
					
					if(arg1.getStatus() == 400){
						//SKToastMessage.showMessage(UserRegister.this, "Your email is already exist!", SKToastMessage.ERROR);
						SKToastMessage.showMessage(UserRegister.this, "သင္၏ Email (သုိ႔) ဖုန္းနံပါတ္ သည္ ရွိၿပီးသားျဖစ္ေနပါသည္! တျခား Email (သုိ႔) ဖုန္းနံပါတ္ ျဖင့္ 'မွတ္ ပံု တင္' ျပဳလုပ္ ပါ!", SKToastMessage.ERROR);
					}
					
					dialog.dismissWithSuccess();
				}
				
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					Log.i("", "Enter here - fail");
					if (arg0.getResponse() != null) {
						if(arg0.getResponse().getStatus() == 400){
							SKToastMessage.showMessage(UserRegister.this, "သင္၏ Email (သုိ႔) ဖုန္းနံပါတ္ သည္ ရွိၿပီးသားျဖစ္ေနပါသည္! တျခား Email (သုိ႔) ဖုန္းနံပါတ္ ျဖင့္ 'မွတ္ ပံု တင္' ျပဳလုပ္ ပါ!", SKToastMessage.ERROR);
						}else if(arg0.getResponse().getStatus() == 500){
							SKToastMessage.showMessage(UserRegister.this, "Something is wrong in server!", SKToastMessage.ERROR);
						}
					}
					dialog.dismissWithFailure();
				}
			});

		}
	}
	
    private boolean checkField(){
    	
    	if(txtUserName.getText().toString().length() == 0){
    		txtUserName.setError("Enter User Name");
    		return false;
    	}
    	if(txtEmail.getText().toString().length() == 0){
			txtEmail.setError("Enter Email");
			return false;
		}
    	if(txtPassword.getText().toString().length() == 0){
			txtPassword.setError("Enter Password");
			return false;
		}
		if (txtPassword.getText().toString().length() < 6) {
			txtPassword.setError("Enter minimun six charactors!");
			return false;
		}
    	if(txtCPassword.getText().toString().length() == 0){
			txtCPassword.setError("Enter Password again");
			return false;
		}
    	if(!txtPassword.getText().toString().equals(txtCPassword.getText().toString())){
			txtCPassword.setError("Passwords do not Match!");
			return false;
		}
		if(txtPhone.getText().toString().length() == 0){
			txtPhone.setError("Enter Phone No");
			return false;
		}
		if(txtPhone.getText().toString().length() == 0){
			txtPhone.setError("Enter Phone No");
			return false;
		}
		if(txtPhone.getText().toString().length() < 6)
    	{
			txtPhone.setError("Enter at least '6' numbers");
			return false;
		}
    	if (!txtPhone.getText().toString().startsWith("09") && !txtPhone.getText().toString().startsWith("01")) {
    		txtPhone.setError("Enter only start with '09 (or) 01'");
    		return false;
		}
    	
    	return true;
    }
	
    @SuppressWarnings("finally")
	public String uploadFile(String sourceFileUri) {
  	  	String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
        
        if (!sourceFile.isFile()) {
      	  
	           dialog.dismiss(); 
	           
	           Log.e("uploadFile", "Source File not exist :"
	        		               +uploadFilePath + "" + uploadFileName);
	           
	           runOnUiThread(new Runnable() {
	               public void run() {
	            	//   messageText.setText("Source File not exist :"
	            			//   +uploadFilePath + "" + uploadFileName);
	               }
	           }); 
	           
	           return "0";
         
        }
        else
        {
	           try { 
	        	   
	            	 // open a URL connection to the Servlet
	               FileInputStream fileInputStream = new FileInputStream(sourceFile);
	               URL url = new URL(upLoadServerUri);
	               
	               // Open a HTTP  connection to  the URL
	               conn = (HttpURLConnection) url.openConnection(); 
	               conn.setDoInput(true); // Allow Inputs
	               conn.setDoOutput(true); // Allow Outputs
	               conn.setUseCaches(false); // Don't use a Cached Copy
	               conn.setRequestMethod("POST");
	               conn.setRequestProperty("Connection", "Keep-Alive");
	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	               conn.setRequestProperty("Picture", fileName); 
	               
	               dos = new DataOutputStream(conn.getOutputStream());
	     
	               dos.writeBytes(twoHyphens + boundary + lineEnd); 
	               dos.writeBytes("Content-Disposition: form-data; name=\"Picture\";filename=\""+ fileName + "\"" + lineEnd);
	               
	               dos.writeBytes(lineEnd);
	     
	               // create a buffer of  maximum size
	               bytesAvailable = fileInputStream.available(); 
	     
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               buffer = new byte[bufferSize];
	     
	               // read file and write it into form...
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                 
	               while (bytesRead > 0) {
	            	   
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
	                 
	                }
	     
	               // send multipart form data necesssary after file data...
	               dos.writeBytes(lineEnd);
	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	     
	               // Responses from the server (code and message)
	               serverResponseCode = conn.getResponseCode();
	               serverResponseMessage= conn.getResponseMessage();
	                
	               Log.i("uploadFile", "HTTP Response is : " 
	            		   + serverResponseMessage + ": " + serverResponseCode);
	               
	               
	               Log.e("Server Response Message", serverResponseMessage);
	               
	               if(serverResponseCode == 200){
	            	   
	            	   BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	                   StringBuilder sb = new StringBuilder();
	                   String line;
	                   while ((line = br.readLine()) != null) {
	                       sb.append(line+"\n");
	                   }
	                   br.close();
	                   
	                   Log.e("String",sb.toString());
	                   String serverResponemsg = sb.toString();
	                   
	                   JSONObject data=new JSONObject(serverResponemsg);
	                   status =  data.getString("status");
	                   photoname =   data.getString("photoname");
	                   
	                   
	                   seperated = photoname.split("/");
	                   Integer s = seperated.length;
	                   if(seperated.length > 0){
	                	   Splitphotoname = seperated[s-1].toString().trim();
	                   }else{
	                	   Splitphotoname = "default.png";
	                   }
	             
	                   return sb.toString();
	               }    
	              
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	                
	          } catch (MalformedURLException ex) {
	        	  
	              dialog.dismiss();  
	              ex.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	Toast.makeText(UserRegister.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                  }
	              });
	              
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	          } catch (Exception e) {
	        	  
	              dialog.dismiss();  
	              e.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	//  messageText.setText("Got Exception : see logcat ");
	                      Toast.makeText(UserRegister.this, "Got Exception : see logcat ", 
	                    		  Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : " 
	            		                           + e.getMessage(), e);  
	          }
	           finally
	           {
	        	  
	 	          dialog.dismiss();    
	 	          
	 	          return serverResponseMessage;
	           }
	           
	          
	          
         } 
        // End else block 
       } 
   
    
}



					