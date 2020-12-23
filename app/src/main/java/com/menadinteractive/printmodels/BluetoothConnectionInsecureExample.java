package com.menadinteractive.printmodels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import android.R.string;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;
 
 
public class BluetoothConnectionInsecureExample {
	 
  	public final static int ERRORMSG_OK=0;
    public final static int ERRORMSG_PAUSED=1;
    public final static int ERRORMSG_DOOROPEN=2;
    public final static int ERRORMSG_PAPER=3;
    public final static int ERRORMSG_CANNOTPRINT=4;
    public final static int ERRORMSG_CONNECTION=5;
    public final static int ERRORMSG_SYNTAXERR=6;
	    
	Handler h;
	private OutputStream outputStream;
	private InputStream inStream;
	BluetoothSocket socket;
	
	public BluetoothConnectionInsecureExample(Handler handle)
	{
		h=handle;
	}
    public void sendZplOverBluetooth(  final String theBtMacAddress ,final String texttoimpr) {
        new Thread(new Runnable() {
            public void run() {
                try {
                	Log.d("TAG", "sendZplOverBluetooth prepare");
                    // Initialize
                    Looper.prepare();                     
                    init();
                    
                    write(texttoimpr);        
                    
                    // Make sure the data got to the printer before closing the connection
                    Thread.sleep(1000);
                    
                    
                    outputStream.close();
                    inStream.close();
                    
                   Looper.myLooper().quit();
				   Log.d("TAG", "sendZplOverBluetooth quit");
            } catch (Exception e) {
                // Handle communications error here.
                e.printStackTrace();
            }
          
            h.sendEmptyMessage(ERRORMSG_OK);
        }
    }).start();
    }
    private void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0){
                	for (BluetoothDevice device : bondedDevices) {
                    
	                    ParcelUuid[] uuids = device.getUuids();
	                    if (device.getName().contains("MIP480"))
	                    {
		                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
		                    try {
								socket.connect();
								outputStream = socket.getOutputStream();
								inStream = socket.getInputStream();
								break;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    break;
	                    }
                	}
                }
                
                Log.e("error", "No appropriate paired devices.");
            }else{
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }
    private void init2() throws IOException {
        try {
			BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
			if (blueAdapter != null) {
			    if (blueAdapter.isEnabled()) {
			        Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

			        if(bondedDevices.size() > 0){
			        	 for (BluetoothDevice device : bondedDevices) {
			        		 	ParcelUuid[] uuids = device.getUuids();
					              socket = device.createRfcommSocketToServiceRecord(uuids[1].getUuid());
					              outputStream = socket.getOutputStream();
						            inStream = socket.getInputStream();
						            
						            try {
						            	Log.e("TAG","primary connect");
						            	if (socket.isConnected()==false)
						            		socket.connect();
									} catch (Exception e) {
										  Log.e("TAG","trying fallback...");

							              socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
							              socket.connect();

							              Log.e("TAG","Connected");
									}
					      
					            
					           
			                  break;
			                }
			             
			           
			        }

			        Log.e("error", "No appropriate paired devices.");
			    }else{
			        Log.e("error", "Bluetooth is disabled.");
			    }
			}
		} catch (Exception e) {
			 Log.e("TAG",e.getLocalizedMessage());
		}
    }
    
    public void write(String s) throws IOException {
    	outputStream.write(0x1B);
    	outputStream.write(0x40);
    	outputStream.write(0x1B);
    	outputStream.write(0x32);
    	byte[] bytes = s.getBytes();
    	
    	for(int i = 0;i < bytes.length;i++){
    		outputStream.write(bytes[i]);
    	}
        
        outputStream.write(0x0C);
    }
    
   
    public byte[] convertExtendedAscii(String input)
   	{
   	        int length = input.length();
   	        byte[] retVal = new byte[length];
   	       
   	        for(int i=0; i<length; i++)
   	        {
   	                  char c = input.charAt(i);
   	                 
   	                  if (c < 127)
   	                  {
   	                          retVal[i] = (byte)c;
   	                  }
   	                  else
   	                  {
   	                          retVal[i] = (byte)(c - 256);
   	                  }
   	        }
   	       
   	        return retVal;
   	}
    
    public static String getErrMsg(int i)
    {
    	return "";
    	
    }
}