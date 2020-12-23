package com.menadinteractive.printmodels;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;

public class printPreviewActivity extends BaseActivity {

	ImageView ivPrint;
	LinearLayout llPrintView;
	TextView tvPreview;
	
	TextView textViewsignature;
	TextView textViewsignature1;
	
	String data, numdoc;
	File file;
	boolean isTech;
	boolean isResp;
	boolean israpport;
	Bitmap mBitmap;
	boolean isauto;
	public printPreviewActivity() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printpreview);

		Bundle bundle = this.getIntent().getExtras();
		data=getBundleValue(bundle, "data");
		isTech = bundle.getBoolean("isTech");
		isResp = bundle.getBoolean("isResp");
		israpport = bundle.getBoolean("israpport");
		if(bundle.getString("numdocpp") != null){
			numdoc = bundle.getString("numdocpp");
		}
		isauto= bundle.getBoolean("isauto");

		
		mBitmap = null;
		initGUI();
		initListeners();
		ViewTreeObserver vto = llPrintView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
			@Override 
			public void onGlobalLayout() { 
				//on enregistre le pdf
				String pdfName = "";
				if(numdoc != null){
					pdfName = numdoc;
				}
				
				file = Fonctions.createPdfDocumentFromContent(llPrintView, pdfName,israpport);
			} 
		});
		/*if(isauto==false)
		{
			String pdfName = "";
			if(numdoc != null){
				pdfName = numdoc;
			}
			file = Fonctions.createPdfDocumentFromContent(llPrintView, pdfName);
			Log.d("TAG", "isauto");
			Intent resultIntent = new Intent();
			Bundle bundleAuto = new Bundle();
			bundleAuto.putString("zplData", data);
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}*/
	}

	void initGUI() {

		Typeface face=Typeface.createFromAsset(getAssets(), Global.FONT_PRINT);


		llPrintView=(LinearLayout) findViewById(R.id.llPrintView);
		ivPrint=(ImageView)findViewById(R.id.image_header);
		tvPreview=(TextView)findViewById(R.id.textViewPreview);
		textViewsignature=(TextView)findViewById(R.id.textViewsignature);
		textViewsignature1=(TextView)findViewById(R.id.textViewsignature1);
		if(isTech==false && isResp==false )
		{
			textViewsignature.setVisibility(View.GONE);
			textViewsignature1.setVisibility(View.GONE);
			
		}
		else
		{
			
			if(israpport==true)
			{
				textViewsignature.setText("");
				textViewsignature1.setText("Signature");
			}
				
		}
		
		tvPreview.setTypeface(face);

		tvPreview.setText(data);
		
		ImageView imageView =(ImageView) findViewById(R.id.ivSignaturetech);
		
		if(isTech){
			imageView.setVisibility(View.VISIBLE);
			//on récupère la signature et on l'ajoute à l'impression
			File signature = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_tech_"+numdoc+".jpg");
			if(signature.exists()){
				
				//Bitmap r = BitmapFactory.decodeFile(file.getAbsolutePath());
				//			Drawable r = (Drawable)BitmapDrawable.createFromPath(file.getAbsolutePath());
				//			iv_signaturetech.setBackgroundDrawable(r);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				mBitmap = BitmapFactory.decodeFile(signature.getAbsolutePath(), options);
				imageView.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, 120, 120, false));
				mBitmap = null;
			}
			//on récupère la signature et on l'ajoute à l'impression
			File signatureRapport = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_rapport_"+numdoc+".jpg");
			if(signatureRapport.exists()){
				//Bitmap r = BitmapFactory.decodeFile(file.getAbsolutePath());
				//			Drawable r = (Drawable)BitmapDrawable.createFromPath(file.getAbsolutePath());
				//			iv_signaturetech.setBackgroundDrawable(r);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				mBitmap = BitmapFactory.decodeFile(signatureRapport.getAbsolutePath(), options);
				imageView.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, 120, 120, false));
				mBitmap = null;
			}
		}
		
		if(isResp){
			imageView.setVisibility(View.VISIBLE);
				//on récupère la signature et on l'ajoute à l'impression
					File signature = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_resp_"+numdoc+".jpg");
					if(signature.exists()){

						//Bitmap r = BitmapFactory.decodeFile(file.getAbsolutePath());
				//			Drawable r = (Drawable)BitmapDrawable.createFromPath(file.getAbsolutePath());
				//			iv_signaturetech.setBackgroundDrawable(r);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				mBitmap = BitmapFactory.decodeFile(signature.getAbsolutePath(), options);
				imageView.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, 120, 120, false));
				mBitmap = null;
			}
			//on récupère la signature et on l'ajoute à l'impression
			/*File signatureRapport = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_rapport_"+numdoc+".jpg");
			if(signatureRapport.exists()){
				//Bitmap r = BitmapFactory.decodeFile(file.getAbsolutePath());
				//			Drawable r = (Drawable)BitmapDrawable.createFromPath(file.getAbsolutePath());
				//			iv_signaturetech.setBackgroundDrawable(r);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				mBitmap = BitmapFactory.decodeFile(signatureRapport.getAbsolutePath(), options);
				imageView.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, 120, 120, false));
				mBitmap = null;
				
			}*/
		}
		
		
	}

	void initListeners() {
		ivPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("TAG", "printPreviewActivity->ivPrint.setOnClickListener");
				
				Intent resultIntent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("zplData", data);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
				//returnOK();
			}
		});
	}

}
