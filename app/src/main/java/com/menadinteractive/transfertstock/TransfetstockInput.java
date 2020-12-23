package com.menadinteractive.transfertstock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Global;

public class TransfetstockInput extends BaseActivity {
	String m_codeart;
	String m_lblart;
	String m_prix;
	String m_reappro;
	String m_dechargement;
	
	String m_qteTheo;
	String m_numcde;
	String m_photo;
	String m_webfiche ;
	
	TextView tvTitre;
	EditText etReappro;
	EditText etDechargement;
	
	EditText etStockTheo;
	ImageButton bOk;
	ImageButton bCancel;
	ImageView imgGrande;
	ImageView imgPetite;
	WebView wvPhoto;
	WebView wvPhotoThumb;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfert_inputdlg3);   

		Bundle bundle = this.getIntent().getExtras();
		m_codeart =getBundleValue(bundle, "codeart") ; 
		m_lblart =getBundleValue(bundle, "lblart") ;
		m_prix =getBundleValue(bundle, "prix") ;
		m_reappro =getBundleValue(bundle, "reappro") ;
		m_dechargement =getBundleValue(bundle, "dechargement") ;
		
		m_qteTheo =getBundleValue(bundle, "qtetheo") ;
		m_numcde =getBundleValue(bundle, "numcde") ;
		m_photo=getBundleValue(bundle, "photoname") ;
		m_webfiche=getBundleValue(bundle, "webfiche") ;
		
		//Mis � jour du stock
		m_qteTheo=Global.dbKDLinTransfert.StockTheorique(TransfetstockInput.this,m_codeart,m_reappro,m_dechargement);
				
		initGui();
		initListeners();

		DispImage(imgGrande);
		DispImage(imgPetite);
		
		//Chargement webwiew
		String addr = "http://sd3.danem.fr/negoscloud/docs_customers/ferrero/docs_server/documents/prod/"+m_codeart+"/"+m_photo+".jpg" ;
		
		//webView.loadUrl("http://www.alpha-geek.fr/public/Google/blue_dot_circle.png");
		String data = "<html><head><title>"
		+ "png"
		+ "</title><meta name=\"viewport\"\"content=\"initial-scale=1.0; maximum-scale=1.0; user-scalable=0 \" /></head>";
		data = data + "<body><center><img src=\""
		+ addr + "\" /></center></body></html>";
	//	wvPhoto.loadData(data, "text/html", null);
		wvPhoto.getSettings().setLoadWithOverviewMode(true);
		wvPhoto.getSettings().setUseWideViewPort(true);
		wvPhoto.getSettings().setBuiltInZoomControls(true);
		wvPhoto.getSettings().setAppCacheEnabled(true);
		wvPhoto.getSettings().setCacheMode(	android.webkit.WebSettings.LOAD_NORMAL);
		wvPhoto.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
		wvPhoto.setVisibility(View.VISIBLE);
		wvPhoto.loadUrl(addr);
		
	 
		
		
		addr = "http://sd3.danem.fr/negoscloud/docs_customers/ferrero/docs_server/documents/prod/"+m_codeart+"/thumb/"+m_photo+".jpg" ;
		
		//webView.loadUrl("http://www.alpha-geek.fr/public/Google/blue_dot_circle.png");
		data = "<html><head><title>"
		+ "png"
		+ "</title><meta name=\"viewport\"\"content=\"initial-scale=1.0; maximum-scale=1.0; user-scalable=0 \" /></head>";
		data = data + "<body><center><img height=\"100\" src=\""
		+ addr + "\" /></center></body></html>";
		wvPhotoThumb.loadData(data, "text/html", null);
		wvPhotoThumb.getSettings().setAppCacheEnabled(true);
		wvPhotoThumb.getSettings().setCacheMode(	android.webkit.WebSettings.LOAD_NORMAL);
		//Si saisie dans reappro alors on vide dans d�chargement
		
		//Saisie dans la zone Prix
		etReappro.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						//if(hasFocus==false)
						{
							etDechargement.setText("");
						}

					}
				});
		etDechargement.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				//if(hasFocus==false)
				{
					etReappro.setText("");
				}

			}
		});
		/*etReappro.addTextChangedListener(new TextWatcher() {

							@Override
							public void afterTextChanged(Editable s) {
								//etDechargement.setText("");
								
							}

							@Override
							public void beforeTextChanged(CharSequence s, int start, int count,
									int after) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onTextChanged(CharSequence s, int start, int before,
									int count) {
								
							
							}
						 
						 });*/
		//Si saisie dans d�chargement alors on vide dans reappro
	etDechargement.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable s) {
							//etReappro.setText("");
						}

						@Override
						public void beforeTextChanged(CharSequence s, int start, int count,
								int after) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence s, int start, int before,
								int count) {
						
							
						
						}
					 
					 });
			
		
	}
	
	void initGui()
	{
		tvTitre=(TextView)findViewById(R.id.textViewTitre);
		tvTitre.setText(m_codeart+"-"+m_lblart);
		
		etStockTheo=(EditText)findViewById(R.id.editStockTheo);
		
		etReappro=(EditText)findViewById(R.id.editTextReappro);
		etDechargement=(EditText)findViewById(R.id.editTextDecharg);
		
		etStockTheo.setText(m_prix);
		etStockTheo.setEnabled(false);
		
		etReappro.setText(m_reappro);
		etDechargement.setText(m_dechargement);
		
		etStockTheo.requestFocus();

		etStockTheo.setText(m_qteTheo);
		bOk=(ImageButton)findViewById(R.id.imageButtonOk);
		bCancel=(ImageButton)findViewById(R.id.imageButtonCancel);
		
		imgGrande=(ImageView)findViewById(R.id.imageViewGrande);
		imgPetite=(ImageView)findViewById(R.id.imageViewPetite);
		wvPhoto = (WebView)findViewById(R.id.webViewPhoto);
		wvPhoto.setClickable(false);
		wvPhoto.setLongClickable(false);
		wvPhoto.setFocusable(false);
		wvPhoto.setFocusableInTouchMode(false);
		wvPhoto.setScrollContainer(false);
		
		wvPhotoThumb = (WebView)findViewById(R.id.webViewPhotoThumb);
		wvPhotoThumb.setClickable(false);
		wvPhotoThumb.setLongClickable(false);
		wvPhotoThumb.setFocusable(false);
		wvPhotoThumb.setFocusableInTouchMode(false);
		wvPhotoThumb.setScrollContainer(false);
		

	}
	
	String getPrix()
	{
		return etStockTheo.getText().toString();
	}
	String getReappro()
	{
		return etReappro.getText().toString();
	}
	String getDechargement()
	{
		return etDechargement.getText().toString();
	}
	String getQteTheo()
	{
		return etStockTheo.getText().toString();
	}
	void initListeners()
	{
		bOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent result = new Intent();
				Bundle b = new Bundle();
			    b.putString("reappro", getReappro());
			    b.putString("dechargement", getDechargement());
			    b.putString("qtetheo", getQteTheo());
			    b.putString("codeart", m_codeart);
			    b.putString("lblart", m_lblart);
			    
			    result.putExtras(b);
			    setResult(Activity.RESULT_OK, result);
			    
				finish();
			}
		});
		
		bCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		imgPetite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewFullSizeImage();
				
			}
		});
	
		wvPhoto.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            	
	    
                return true;
            }

            public void onLoadResource(WebView view, String url) {
            	//Toast.makeText(MainActivity.this, "load resource "  , Toast.LENGTH_LONG).show();
                
            }

            
            public void onPageFinished(WebView view, String url) {
   
            }
            public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
 
            	      
            }
            
            
        });
	}
	
	void DispImage(ImageView img)
	{
		
		String fullPath = ExternalStorage.getFolderPath(ExternalStorage.ROOT_FOLDER);
		File jpgFile = new File(fullPath + m_photo+".jpg");
				
		Bitmap thumbnail = null;
		// if (jpgFile.exists()) //on enleve le test car ca
		// fait trop ralentir la liste
		// on laisse la gestion de l'erreur au catch
		{
			// Log.d("TAG", "file exist " + photoname);
			try {
				thumbnail = BitmapFactory.decodeStream(
						new FileInputStream(jpgFile), null,
						null);

				// else
				// thumbnail = defaultThumbnailBitmap;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("TAG",
						"error decoding" + e.toString());
			}
		}

		img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		img.setImageBitmap(thumbnail);

	}
	
	private void viewFullSizeImage(){
		
		String fullPath = ExternalStorage.getFolderPath(ExternalStorage.ROOT_FOLDER);

			//		File jpgFile = new File(fullPath + CommandeState.currentProduct.PHOTONAME+ ".jpg");
			File jpgFile = new File(fullPath + m_photo+".jpg");

			if(jpgFile.exists()){
				Intent intent = new Intent();  
				intent.setAction(android.content.Intent.ACTION_VIEW);  

				//intent.setData(Uri.fromFile(file));
				intent.setDataAndType(Uri.fromFile(jpgFile), "image/*");  
				startActivity(intent);  
			}
	
	}
	

}
