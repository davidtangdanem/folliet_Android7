package com.menadinteractive.segafredo.encaissement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;










import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SignatureActivity  extends Activity{

	ExternalStorage externalStorage;
	/** Menu ID for the command to clear the window. */
	private static final int CLEAR_ID = 0;
	private static final int CLEAR_SECOND = 1;
	

	String mFileName, mFolder, zplData;

	/** The view responsible for drawing the window. */
	SignatureView mView;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		externalStorage = new ExternalStorage(this, false);
		Bundle bundle = this.getIntent().getExtras();
		mFileName= bundle.getString("filename");
		mFolder = bundle.getString("folderSignature", "");
		zplData = bundle.getString("zplData", "");
		// Create and attach the view that is responsible for painting.
		mView = new SignatureView(this, null);
		setContentView(mView);
		mView.requestFocus();
		Toast.makeText(this, getString(R.string.signer_ici), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mView.isFilled()){
				saveSignature();
				setResult(RESULT_OK);
			}
			else
				setResult(RESULT_CANCELED);
			
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("zplData", zplData);
			resultIntent.putExtras(bundle);
			setResult(Activity.RESULT_OK, resultIntent);
			finish(); 

			return super.onKeyDown(keyCode, event);
		}
		else
			return super.onKeyDown(keyCode, event);

	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, CLEAR_ID, 0, "Valider");
		menu.add(0, CLEAR_SECOND, 1, "Clear");

		return super.onCreateOptionsMenu(menu);
	}



	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case CLEAR_ID:

			if(mView.isFilled()){
				saveSignature();
				setResult(RESULT_OK);
			}
			else
				setResult(RESULT_CANCELED);
			
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("zplData", zplData);
			resultIntent.putExtras(bundle);
			setResult(Activity.RESULT_OK, resultIntent);
			finish(); 
			
			return true;
			
		case CLEAR_SECOND:

			mView.clear();
			mView.setFilled(false);
			return true;


		default:
			return super.onOptionsItemSelected(item);
		}
	}

	void saveSignature()
	{
		mView.setDrawingCacheEnabled(true);
		Bitmap b = mView.getDrawingCache();

		File sdCard = Environment.getExternalStorageDirectory();
		File file = null;
		if(mFolder != null && !mFolder.equals("")){
			file = new File(mFolder + mFileName+".jpg");
		}else{
			file = new File(externalStorage.getSignaturesFolder()+File.separator+ mFileName+".jpg");
		}
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			Boolean res= b.compress(CompressFormat.JPEG, 20, fos);

		} catch (FileNotFoundException e1) {
			Toast.makeText(this,e1.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			e1.printStackTrace();
		}
	}
}
