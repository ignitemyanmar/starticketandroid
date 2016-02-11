package com.ignite.mm.ticketing.agent.callcenter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ignite.barcode.GenerateBarcode;
import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BluetoothDeviceDialog;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.DeviceAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.PDFBusAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import com.ignite.mm.ticketing.sqlite.database.model.Device;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.smk.skalertmessage.SKToastMessage;
import com.zkc.helper.printer.BlueToothService;
import com.zkc.helper.printer.PrintService;
import com.zkc.helper.printer.PrinterClass;
import com.zkc.helper.printer.PrinterClassFactory;

@SuppressLint({ "SdCardPath", "ShowToast" })
public class PDFBusActivity extends BaseSherlockActivity {
	private final static String PDF_FILE_PATH = Environment.getExternalStorageDirectory()+"/IgniteEasyTicket/";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;

	private  ArrayList<AllBusObject> allBusObject;
	private TextView actionBarTitle2;
	private String OperatorName = "";
	private String BusTrip =  "";
	private String TripTime = "";
	
	private String BusClass = "";
	private String SeatPrice = "0";
	public static String Bar_Code_No = "0";
	public static String TripDate = "";
	public static String SelectedSeat = "";
	private static int bitmapWidth = 150;
	private static int bitmapHeight = 50;
	private String ConfirmDate;
	private String ConfirmTime;
	private static ListView lv_bus_booking_sheet;
	private Bitmap bmTicketView;
	
	//Print Ticket Slip - Variables
	public Handler mhandler = null;
	public static PrinterClass printerClass = null;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter = null;
	public static ArrayAdapter<String> mNewDevicesArrayAdapter = null;
	public static List<Device> deviceList;
	private DeviceAdapter adapter;
	
	private String BuyerName;
	
	//Check Device connected
	public static boolean checkState = true;
	private Thread tv_update;
	TextView textView_state;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	Handler handler = null;
	
	private ProgressDialog dialog;
	private Bitmap bitmapVoucher;
	
	private LinearLayout img_print;
	private String OrderDateTime;
	private String Phone;
	private String TicketNo;
	private String SeatCount;
	
	private String deviceAddr;
	private String BuyerNRC;
	private String from_intent = "";
	
	int imgWidth=48;
	private String extra_city = "";
	private String ticket_price = "0";
	private String total_amount = "0";
	private String Passengers = "";
	private String operatorPhone;
	private String random_tickets;
	private String[] random_array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bus_ticket_item);

/*		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarTitle.setText("e-Ticket");
		img_print = (LinearLayout)actionBar.getCustomView().findViewById(R.id.img_print_layout);
		img_print.setVisibility(View.VISIBLE);
		img_print.setOnClickListener(clickListener);
		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/

		//deviceList = new ArrayList<Device>();
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        	toolbar.setTitle("e-Ticket");
            this.setSupportActionBar(toolbar);
        }
        
		//Check Bluetooth Connection
		checkBluetoothConnect();
		
		if (printerClass != null) {
			Log.i("", "Check State (2nd) On Create: "+printerClass.getState());
		}
		
		Bundle bundle = getIntent().getExtras();
			
		if (bundle != null) {
			OperatorName = bundle.getString("Operator_Name");
			BusTrip = bundle.getString("from_to");
			TripDate = bundle.getString("date");
			TripTime = bundle.getString("time");
			BusClass = bundle.getString("classes");
			SelectedSeat = bundle.getString("selected_seat");
			SeatPrice = bundle.getString("Price");
			Bar_Code_No = bundle.getString("sale_order_no");
			ConfirmDate = bundle.getString("ConfirmDate");
			ConfirmTime = bundle.getString("ConfirmTime");
			OrderDateTime = bundle.getString("order_date");
			BuyerName = bundle.getString("BuyerName");
			Phone = bundle.getString("BuyerPhone");
			BuyerNRC = bundle.getString("BuyerNRC");
			TicketNo = bundle.getString("TicketNo");
			SeatCount = bundle.getString("SeatCount");
			extra_city = bundle.getString("extra_city");
			ticket_price = bundle.getString("ticket_price");
			total_amount  = bundle.getString("total_amount");
			Passengers  = bundle.getString("Passengers");
			operatorPhone  = bundle.getString("operatorPhone");
			from_intent = bundle.getString("from_intent");
			random_tickets = bundle.getString("random_tickets");
		}
		
		lv_bus_booking_sheet = (ListView)findViewById(R.id.lv_bus_booking_sheet);
		getData();
		
		/*//to link another App
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtras(bundle);
		sendIntent.setType("text/plain");
		
		// Verify that the intent will resolve to an activity
		if (sendIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(sendIntent);
		}*/
	}
	
	
	/**
	 *  Set Bus Ticket Data into adapter
	 */
	private void getData() {
		
		if (ConfirmTime.equals("") || ConfirmTime == null) {
			ConfirmTime = getTodayTime();
		}
		
		Log.i("", "ticket no: "+TicketNo);
		
		allBusObject = new ArrayList<AllBusObject>();
		//Integer amount = Integer.valueOf(SeatCount) * Integer.valueOf(SeatPrice);
		allBusObject.add(new AllBusObject(BusTrip, TripDate, "", OperatorName, TripTime, ""
				, SelectedSeat, ticket_price, "", AppLoginUser.getUserName(), BusClass, ConfirmDate, ConfirmTime
				, Bar_Code_No, getBarcode(), BuyerName, Phone, TicketNo, SeatCount, 0
				, Integer.valueOf(total_amount), BuyerNRC, extra_city, Passengers
				, AppLoginUser.getAgentGroupName(), operatorPhone, random_tickets));
		
		Log.i("", "All Bus Object: "+allBusObject.toString());

		lv_bus_booking_sheet.setAdapter(new PDFBusAdapter(PDFBusActivity.this, allBusObject));
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				if (from_intent != null && from_intent.equals("from_threeday_sales")) {
					finish();
				}else if (from_intent != null && from_intent.equals("booking")) {

					closeAllActivities();
					startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
				}else{
					closeAllActivities();
					startActivity(new Intent(getApplicationContext(), SaleTicketActivity.class));
				}
			}
			if (v == img_print) {
/*				if (printerClass != null) {
					Log.i("", "Check State (2nd) On Print Click: "+printerClass.getState());
					
					//Save Ticket
					if (saveTicket()) {
						
						Log.i("", "Saved!");
						
						Toast.makeText(
								this,
								"Your ticket is saved to " + PDF_FILE_PATH
										+ "BookingSheet", Toast.LENGTH_SHORT).show();
					} else {
						Log.i("", "Fail Saved!");
						Toast.makeText(this, "Can't save your ticket!",
								Toast.LENGTH_LONG).show();
					}
					
					//If Bluetooth Support not have ... 
			        if (!BlueToothService.HasDevice()) {
			        	
			            SKToastMessage.showMessage(PDFBusActivity.this, "The device does not have Bluetooth support!", SKToastMessage.LENGTH_LONG);
			            
			        }else {
			        	//Check Bluetooth Open or not 
			        	if (!printerClass.IsOpen()) {
			        		Log.i("", "Bluetooth Not Open yet!");
							printerClass.open(PDFBusActivity.this);
							checkState = false;
							connectBluetoothService();
						} else if (printerClass.getState() == PrinterClass.LOSE_CONNECT
								|| printerClass.getState() == PrinterClass.FAILED_CONNECT) {
							checkState = false;
							connectBluetoothService();
						} else {
							Log.i("", "Bluetooh Opened!!!!!");
							printSlip();
						}
			        	
					}
				}*/
			}
		}
	};

	/**	 
	 * @param pathName file path to save
	 * @return true if file name is already exist
	 */
	public Boolean IfExistPDF(String pathName) {
		boolean ret = true;

		File file = new File(PDF_FILE_PATH + pathName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				Log.e("TravellerLog :: ", "Problem creating folder");
				ret = false;
			}
		}
		return ret;
	}

	public void changePDF() {
		IfExistPDF("busticket.pdf");
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(PDF_FILE_PATH	+ "busticket.pdf"));
			document.open();

			for (int i = 0; i < Integer.valueOf(Bus_Info_Activity.no_of_ticket); i++) {
				Image image1 = Image.getInstance(PDF_FILE_PATH + "busticket" + (i + 1) + ".png");
				image1.scalePercent(20);
				document.add(image1);
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		goToMail("busticket.png");
	}

	private void goToMail(String pathName) {

		File file = new File(PDF_FILE_PATH + pathName);
		Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Bus Ticketing ");
		intent.putExtra(Intent.EXTRA_TEXT, "");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(intent);
	}
	
	public boolean saveTicket() {
		boolean printed = false;
		bmTicketView = getWholeListViewItemsToBitmap();
		
		//getResizedBitmap(bmTicketView, imgWidth * 8, bmTicketView.getHeight()), PDF_FILE_PATH, "BookingSheet.png")
		
		if (storeImage(resizeImage(bmTicketView, imgWidth * 8, bmTicketView.getHeight()), PDF_FILE_PATH, "BookingSheet.png")) {
			printed = true;
		}
		
		return printed;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		/*menu.add(0, 1, 0, null).setIcon(R.drawable.print_icon)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/
		getMenuInflater().inflate(R.menu.activity_e_ticket, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_print:
			if (printerClass != null) {
				Log.i("", "Check State (2nd) On Print Click: "+printerClass.getState());
				
				//Save Ticket
				if (saveTicket()) {
					
					Log.i("", "Saved!");
					
					/*Toast.makeText(
							this,
							"Your ticket is saved to " + PDF_FILE_PATH
									+ "BookingSheet", Toast.LENGTH_SHORT).show();*/
				} else {
					Log.i("", "Fail Saved!");
					/*Toast.makeText(this, "Can't save your ticket!",
							Toast.LENGTH_LONG).show();*/
				}
				
				//If Bluetooth Support not have ... 
		        if (!BlueToothService.HasDevice()) {
		        	
		            SKToastMessage.showMessage(PDFBusActivity.this, "The device does not have Bluetooth support!", SKToastMessage.LENGTH_LONG);
		            
		        }else {
		        	//Check Bluetooth Open or not 
		        	if (!printerClass.IsOpen()) {
		        		Log.i("", "Bluetooth Not Open yet!");
						printerClass.open(PDFBusActivity.this);
						checkState = false;
						connectBluetoothService();
					} else if (printerClass.getState() == PrinterClass.LOSE_CONNECT
							|| printerClass.getState() == PrinterClass.FAILED_CONNECT) {
						checkState = false;
						connectBluetoothService();
					} else {
						Log.i("", "Bluetooh Opened!!!!!");
						printSlip();
					}
		        	
				}
			}
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}	
	
	BluetoothDeviceDialog deviceDialog;
	
	/**
	 *  Print the Slip !
	 */
	
	protected ProgressDialog progressDialog;
	private Bitmap ticketBitmap;
	
	private void printSlip() {
		// TODO Auto-generated method stub
		
		Log.i("", "Check State (2nd) On Print Method: "+printerClass.getState());

		//Print Ticket
		//If not connected , show device list dialog
		//If connected, print directly
		final Bitmap bitmapVoucher = getWholeListViewItemsToBitmap();
		if (bitmapVoucher != null) {
			
			Log.i("", "Ticket Bitmap is not null!");
			
			if (printerClass != null) {
				
				Log.i("", "Printer class is not null!");
				
		        	Log.i("", "Check State (2nd) On Print Method ==> If bluetooth support: "+printerClass.getState());
		        	
		        	//If not connected with printer, Show Device Dialog
	        		if (printerClass.getState() != PrinterClass.STATE_CONNECTED){
	        			
						Log.i("", "Not connect yet with device !!! ");
						
						//showDeviceNames();
						BluetoothDeviceDialog deviceDialog = new BluetoothDeviceDialog(PDFBusActivity.this);
						
						if (printerClass != null) {
							if (printerClass.getState() == PrinterClass.STATE_CONNECTED) {
								Toast.makeText(PDFBusActivity.this, "connected", Toast.LENGTH_SHORT).show();	
								checkState = true;
							}else if (printerClass.getState() == PrinterClass.STATE_CONNECTING) {
								Toast.makeText(PDFBusActivity.this, "connecting...", Toast.LENGTH_SHORT).show();	
							}else if(printerClass.getState() == PrinterClass.STATE_SCAN_STOP)
							{
								Toast.makeText(PDFBusActivity.this, "stop scanning...", Toast.LENGTH_SHORT).show();	
								
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
							}else if(printerClass.getState() == PrinterClass.STATE_SCANING)
							{
								Log.i("", "Scanning...!");
								
								Toast.makeText(PDFBusActivity.this, "scanning....", Toast.LENGTH_LONG).show();	
								
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
								
							}else {
								//int ss=PrintActivity.pl.getState();
								
								Toast.makeText(PDFBusActivity.this, "Not connect yet!", Toast.LENGTH_SHORT).show();	
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
							}
						}
						
						//Print Setting
						mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
								R.drawable.device_name);
						
						//Paired DeviceList Show
						//if (deviceList != null && deviceList.size() > 0) {
							/*Log.i("", "Paired Device List !! ");
							
							adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
							deviceDialog.getListView().setAdapter(adapter);	*/
							
							//New DeviceList & Scan							
							mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
									R.drawable.device_name);
							
							deviceList = new ArrayList<Device>();
							
							if(deviceList!=null)
							{
								deviceList.clear();
							}
							
							mNewDevicesArrayAdapter.clear();
							printerClass.scan();
							
							//Get Device List after scanning
							deviceList = printerClass.getDeviceList();
						
						if (deviceList != null && deviceList.size() > 0) {
							Log.i("", "New Device's list: "+deviceList.toString());
							
							adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
							deviceDialog.getListView().setAdapter(adapter);	
						}
							
						deviceDialog.setCallbackListener(new BluetoothDeviceDialog.Callback() {
							
							public void onDeviceChoose(int position) {
								//Connect to Selected Device's Address
								Log.i("", "Enter Here, device choose !!!!!!!!!!!!!!");
								
								deviceAddr = deviceList.get(position).getDeviceAddress();
								
								try {
									//Connect with the selected device
									printerClass.connect(deviceAddr);
									checkState = true;
									
								} catch (IndexOutOfBoundsException e) {
									// TODO: handle exception
									Log.i("", "IndexOutOfBoundsException : "+e);
									//checkState = false;
									//showAlert("Can't Print! Index Out Of Bound...");
								} catch (Exception e) {
									// TODO: handle exception
									Log.i("", "Exception : "+e);
									e.printStackTrace();
									//checkState = false;
									//showAlert("Can't Print! Something is wrong...");
								}
								
								progressDialog = ProgressDialog.show(PDFBusActivity.this, "", "Connecting , pls wait  ...", true);
								progressDialog.setCancelable(true);
								
							}
						});
						
						deviceDialog.show();
						
					}else if(printerClass.getState() == PrinterClass.STATE_CONNECTED){
						
						Log.i("", "Connect with Device !!!!!!!!!");
						
						try {
							checkState = true;
							//getResizedBitmap(bitmapVoucher, imgWidth * 8, bitmapVoucher.getHeight())
							printerClass.printImage(resizeImage(bitmapVoucher, imgWidth * 8, bitmapVoucher.getHeight()));
							Toast.makeText(PDFBusActivity.this, "Connected & Printing ...", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(PDFBusActivity.this, "Fail to print!", Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
					}//End if - check connected 
			}else {
				Log.i("", "Printer class is null!");
			}
		}
	}	

	public Bitmap getResizedBitmap(Bitmap bm, int reqWidth, int reqHeight) {
		
		int bWidth = bm.getWidth();
        int bHeight = bm.getHeight();
        
        Log.i("", "Bitmap Width + height: "+bWidth+" , "+bHeight);
        
        int nWidth = reqWidth;
        int nHeight = reqHeight;
        
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix m = new Matrix();
		m.setScale((float) nWidth / bWidth, (float) nHeight / bHeight);
		m.setRectToRect(new RectF(0, 0, bWidth, bHeight), new RectF(0, 0, nWidth, nHeight), Matrix.ScaleToFit.CENTER);
		
		Bitmap resizedBitMap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
		return resizedBitMap;		
	}
	
	/**
	 * resize the bitmap
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	private static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		
		Log.i("", "Bitmap size before resize: "+width+" x "+height);

		if(width>w)
		{
			float scaleWidth = ((float) w) / width;
			float scaleHeight = ((float) h) / height+24;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleWidth);
			Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
					height, matrix, true);
			return resizedBitmap;
		}else{
			Bitmap resizedBitmap = Bitmap.createBitmap(w, height+24, Config.RGB_565);
			Canvas canvas = new Canvas(resizedBitmap);
			Paint paint = new Paint();
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(bitmap, (w-width)/2, 0, paint);
			return resizedBitmap;
		}
	}
	
	public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());

        return output;
    }
	
	//Generate Bar Code Image (Bitmap)
	public static Bitmap getBarcode() {
		
		// barcode data
		String barcode_data = Bar_Code_No;
		
		// barcode image
		Bitmap bitmap = null;

		try {
			GenerateBarcode code = new GenerateBarcode();
			bitmap = code.encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128,
					bitmapWidth, bitmapHeight);
			
			//img_barcode.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return bitmap;
	}

	static List<Bitmap> bmps;
	
	/**
	 * Change ListView to Bitmap file & Save 
	 * @return Bitmap
	 */
	public static Bitmap getWholeListViewItemsToBitmap() {

	    ListView listview    = lv_bus_booking_sheet;
	    ListAdapter adapter  = listview.getAdapter();
	    int itemscount       = adapter.getCount();
	    int allitemsheight   = 0;
	    bmps = new ArrayList<Bitmap>();

	    Log.i("", "Items Count: "+itemscount);
	    
	    //Change Bitmap from listItems (Listview)
	    for (int i = 0; i < itemscount; i++) {

	        View childView = adapter.getView(i, null, listview);
	        childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), MeasureSpec.EXACTLY), 
	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

	        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
	        childView.setDrawingCacheEnabled(true);
	        childView.buildDrawingCache();
	        bmps.add(childView.getDrawingCache());
	        allitemsheight += childView.getMeasuredHeight();
	    }
	    
	    Log.i("", "Bit Map Size after adding List Items: "+bmps.size()+"List view width: "+lv_bus_booking_sheet.getWidth());
	    
	    Bitmap bigbitmap    = Bitmap.createBitmap(lv_bus_booking_sheet.getWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
	    Canvas bigcanvas    = new Canvas(bigbitmap);

	    bigcanvas.drawColor(Color.WHITE);
	    
	    Paint paint = new Paint();
	    int iHeight = 0;

	    for (int i = 0; i < bmps.size(); i++) {
	    	
	    	Log.i("", "Bitmap Size: "+bmps.size());
	    	
	        Bitmap bmp = bmps.get(i);
	        
	        //To cover  java.lang.RuntimeException Bitmap recycle 
	        //android.graphics.Canvas.throwIfCannotDraw  (Error)
	        //if (bmp != null && !bmp.isRecycled()) {
	        	
	        if (bmp != null) {
	        	bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
		        iHeight+=bmp.getHeight();
			}
		        
	      //bmp.recycle();
        	//bmp = null;    
	        	
//	        }else {
//				Log.i("", "Bitmap is already recycled!");
//			}
	    }
	    
	    return bigbitmap;
	}
	
	public static void getBitmapFromView(View view) {
		//view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
        bmps.add(view.getDrawingCache());
	}
	
	public static boolean storeImage(Bitmap imageData, String path, String filename) {
		// get path to external storage (SD card)
		String photoPath = path;
		File sdIconStorageDir = new File(photoPath);

		// create storage directories, if they don't exist
		sdIconStorageDir.mkdirs();

		try {
			String filePath = sdIconStorageDir.toString() + "/" + filename;
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);

			// choose another format if PNG doesn't suit you
			imageData.compress(CompressFormat.PNG, 100, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		} catch (IOException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		}

		return true;
	}
	
	//Check Bluetooth Connect on Create
	public void checkBluetoothConnect() {
		// TODO Auto-generated method stub
		
		mhandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MESSAGE_READ:
					 byte[] readBuf = (byte[]) msg.obj;
					 Log.i("", "readBuf:"+readBuf[0]);
					 if(readBuf[0]==0x13)
					 {
						 PrintService.isFUll=true;
					 }
					 else if(readBuf[0]==0x11)
					 {
						 PrintService.isFUll=false;
					 }
					 else{
		                // construct a string from the valid bytes in the buffer
		                String readMessage = new String(readBuf, 0, msg.arg1);
		                /*Toast.makeText(getApplicationContext(),readMessage,
	                               Toast.LENGTH_SHORT).show();*/
					 }
					break;
				case MESSAGE_STATE_CHANGE:// è“�ç‰™è¿žæŽ¥çŠ¶
					switch (msg.arg1) {
					case PrinterClass.STATE_CONNECTED:// å·²ç»�è¿žæŽ¥
						break;
					case PrinterClass.STATE_CONNECTING:// æ­£åœ¨è¿žæŽ¥
						break;
					case PrinterClass.STATE_LISTEN:
					case PrinterClass.STATE_NONE:
						break;
					case PrinterClass.SUCCESS_CONNECT:
						Log.i("", "Success Connected & Printing..... ");
						final Bitmap ticketBitmap = getWholeListViewItemsToBitmap();
						if (ticketBitmap != null) {
							if (printerClass.getState() == PrinterClass.STATE_CONNECTED) {
								checkState = true;
								//getResizedBitmap(ticketBitmap, imgWidth * 8, ticketBitmap.getHeight())
								printerClass.printImage(resizeImage(ticketBitmap, imgWidth * 8, ticketBitmap.getHeight()));
								progressDialog.dismiss();
								Toast.makeText(PDFBusActivity.this, "Connected & printing ... !", Toast.LENGTH_SHORT).show();
							}
						}
						break;
					case PrinterClass.FAILED_CONNECT:
						SKToastMessage.showMessage(PDFBusActivity.this, "Fail Connect with Printer, Check your Device Power Turn on!", SKToastMessage.ERROR);
						progressDialog.dismiss();
						break;
					case PrinterClass.LOSE_CONNECT:
						SKToastMessage.showMessage(PDFBusActivity.this, "Connection Lost ... ", SKToastMessage.ERROR);
						progressDialog.dismiss();
						Log.i("", "LOSE_CONNECT");
					}
					break;
				case MESSAGE_WRITE:
					Log.i("", "Enter here after print connect!!!!!!");
					break;
				}
				super.handleMessage(msg);
			}
		};
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					break;
				case 1:// æ‰«æ��å®Œæ¯•
					Device d=(Device)msg.obj;
					if(d!=null)
					{
						if(deviceList == null)
						{
							deviceList = new ArrayList<Device>();
						}
						
						if(!checkData(deviceList, d))
						{
							//deviceList.add(d);
							Log.i("", "Device list: "+PDFBusActivity.deviceList.toString());
							
						}
					}else {
						Log.i("", "Message Device Addr: is null!");
					}
					break;
				case 2:// å�œæ­¢æ‰«æ��
					break;
				}
			}
		};
		
		if(checkState)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (PDFBusActivity.printerClass != null) {								
				
				if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_CONNECTED) {
					Toast.makeText(PDFBusActivity.this, "connected device", Toast.LENGTH_LONG).show();	
					//(Command Line _ if already connected with device before, not to put Printer Class's state == 0, to fix state == 3 (connected already))
					//connectBluetoothService(); 
				}else if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_CONNECTING) {
					
					Toast.makeText(PDFBusActivity.this, "connecting device ...", Toast.LENGTH_LONG).show();
					//connectBluetoothService();  //Edited by Su Wai on 24 Mar, 2015 
					
				}else if(PDFBusActivity.printerClass.getState() == PrinterClass.LOSE_CONNECT
						|| PDFBusActivity.printerClass.getState() == PrinterClass.FAILED_CONNECT){
					
					checkState = false;
					
					Log.i("", "Lose or Fail connect, checkState =  "+checkState);
					Toast.makeText(PDFBusActivity.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
					
					connectBluetoothService();   //Edited by Su Wai on 24 Mar, 2015
					
				}else{
					Toast.makeText(PDFBusActivity.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
					connectBluetoothService();   //Edited by Su Wai on 24 Mar, 2015
				}
			}else {
				connectBluetoothService();
			}//End If printerClass is Null
		}
	}
	
	/**
	 *  Connect Bluetooth & put Printer Class's State
	 */
	private void connectBluetoothService() {
		// TODO Auto-generated method stub
		//Connect Bluetooth Service
		try {
			printerClass = PrinterClassFactory.create(0, this, mhandler, handler);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(PDFBusActivity.this, "Fail Bluetooth Service!", Toast.LENGTH_SHORT);
		}
	}

    private boolean checkData(List<Device> list,Device d)
    {
    	for (Device device : list) {
			if(device.getDeviceAddress().equals(d.getDeviceAddress()))
			{
				Log.i("", "Equal!");
				return true;
			}
		}
    	
    	Log.i("", "Not Equal!");
    	return false;
    }	
    
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	checkState = true;
    	
    	Log.i("", "On Restart: "+checkState);
    	
    	super.onRestart();
    }
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (from_intent != null && from_intent.equals("from_threeday_sales")) {
			finish();
		}else if (from_intent != null && from_intent.equals("booking")) {
			closeAllActivities();
			startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
		}else{
			closeAllActivities();
			startActivity(new Intent(getApplicationContext(), SaleTicketActivity.class));
		}
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
