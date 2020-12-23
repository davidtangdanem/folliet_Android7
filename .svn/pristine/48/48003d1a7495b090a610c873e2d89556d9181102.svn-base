package com.menadinteractive.segafredo.tasks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class task_getFileFromURL extends AsyncTask<Void, Void, Integer>{
	
	String m_codecli, url, extension, uri;
	
	OnDowload mListener;
	
	public task_getFileFromURL(String codecli, String _url, String _extension, OnDowload _listener){
		m_codecli = codecli;
		this.url = _url;
		this.extension = _extension;
		mListener = _listener;
	}

	@Override
	protected Integer doInBackground(Void... params) {
		 getFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), m_codecli+"."+this.extension, 
	        		url);  
		 uri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/" + m_codecli+"."+this.extension;
		return 1;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		if(mListener != null){
			mListener.onDownloadFinished(uri);
		}
	}
	
	boolean getFile(String folder, String fileName, String docUrl) {

		try {

			URL url = new URL(docUrl+fileName);

			String path = folder +"/" + fileName;
			File file = new File(path);

			long startTime = System.currentTimeMillis();

			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();

			BufferedInputStream bis = new BufferedInputStream(is);

			ByteArrayBuffer baf = new ByteArrayBuffer(50);

			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			Log.d("ImageManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public interface OnDowload{
		public void onDownloadFinished(String uri);
	}

}
