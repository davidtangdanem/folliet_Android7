package com.menadinteractive.histo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.danem.lib.IntentIntegrator;
import com.danem.lib.IntentResult;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.OptionTarifDetailAdapter;
import com.menadinteractive.segafredo.adapter.SymptomesAdapter;
import com.menadinteractive.segafredo.carto.MenuPopup;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD730FacturesDues;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.tarif.OptionTarifActivity;
import com.menadinteractive.segafredo.tasks.task_sendWSData;
import com.menadinteractive.histo.Symptomes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OptionSymptomes extends BaseActivity{
	
	ListView lvOptionTarifDetail;
	EditText etFilter;
	ImageButton ibFind;
	ArrayList<Symptomes> symptome = null;
	
	SymptomesAdapter adapter;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_TAKE_PHOTO = 2;
	String currentFilePath;
	Bitmap newImage = null;
	
	
	
	String numserie = null;
	String stcodeclient=null;
	String stMachine="";
	String stequipement="";
	String stcodearticle="";
	String stlibarticle="";
	String stcodesymp="";
	String stbAutres="0";
	String stbundlemachine="0";
	String stnumcde="";
	String m_auto="0";
	
	
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptomes);
		
		//on récupère codeTarif à partir de l'intent
		Bundle bundle = null;
		if(getIntent() != null){
			bundle = getIntent().getExtras();
			if(bundle.getString("numserie") != null){
				numserie = bundle.getString("numserie");
			}
			stcodeclient = bundle.getString("stcodeclient");
			stcodearticle = bundle.getString("codearticle");
			stlibarticle = bundle.getString("libelarticle");
			stcodesymp = bundle.getString("codesymp");
			stbAutres = bundle.getString("bautres");
			stbundlemachine = bundle.getString("bundlemachine");
			stnumcde = bundle.getString("stnumcde");
			m_auto= bundle.getString("auto");
			
			
		}
		
		if(stbAutres.equals("1"))
		{
			stMachine=stbundlemachine;
			stequipement="";
		}
		else
		{
			// Récuperer MACHINE et EQUIPEMENT
			
			stMachine= Global.dbMaterielClient.GetMachine(numserie,stcodeclient);
			stequipement= Global.dbMaterielClient.GetEquipement(numserie,stcodeclient);
				
		}
		
		//Il n'y a plus qu'à remplir la liste
		
		initGUI();
		initListeners();
		PopulateList("");
		if(Fonctions.GetStringDanem(m_auto).equals("1"))
		{
			 Intent result = new Intent();
			 Bundle b = new Bundle();
			 b.putString("numserie", numserie);
			 b.putString("codeclient", stcodeclient);
			 b.putString("codearticle", stcodearticle );
			 b.putString("libarticle", stlibarticle);
			 b.putString("codesymp", "");
			 b.putString("libsymp", "" );
		
		
			 result.putExtras(b);
			 setResult(Activity.RESULT_OK, result);

			 finish();
		}

	}
	
	/**
	 * Initialisation des éléments graphiques
	 */
	private void initGUI() {
		lvOptionTarifDetail = (ListView) findViewById(R.id.lvOptionTarifDetail);
		etFilter = (EditText) findViewById(R.id.etFilter);
		ibFind = (ImageButton) findViewById(R.id.ibFind);
	}

	/**
	 * Initialisation des listeners
	 */
	private void initListeners() {
		
		ibFind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PopulateList( etFilter.getText().toString().trim());
			}
		});
		
		
		lvOptionTarifDetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Symptomes Symptomes = symptome.get(position);
				
				
				
				 Intent result = new Intent();
				 Bundle b = new Bundle();
				 b.putString("numserie", numserie);
				 b.putString("codeclient", stcodeclient);
				 b.putString("codearticle", stcodearticle );
				 b.putString("libarticle", stlibarticle);
				 b.putString("codesymp", Symptomes.cod_symp);
				 b.putString("libsymp", Symptomes.lib_symp );


				 result.putExtras(b);
				 setResult(Activity.RESULT_OK, result);

				 finish();
				
				
			}
		});
	}
	
	private void PopulateList(String filter) {
		 
		Log.d("TAG","populate symptome");
		 
		hideKeyb(etFilter);
		
		symptome = Symptomes.getAllSymptomes(stMachine,stequipement, filter,stbAutres) ;
		
				//.getAllTarifDetail(stMachine,stequipement, filter);
		updateChecked();
		if(symptome != null && symptome.size() > 0){
			adapter = new SymptomesAdapter(this, 0, symptome);
			lvOptionTarifDetail.setAdapter(adapter);
		}
		
	
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		
		/*addMenu(menu, R.string.Photo,
				android.R.drawable.ic_menu_camera);
		
		addMenu(menu, R.string.gallerie,
				android.R.drawable.picture_frame);
		*/
		
		addMenu(menu, R.string.supprimer,
				android.R.drawable.ic_menu_close_clear_cancel);
		

		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{

		if (keyCode == KeyEvent.KEYCODE_BACK ||keyCode == KeyEvent.KEYCODE_HOME) {
			//Cancel();

			return false;
		}

		else
			return super.onKeyDown(keyCode, event);
	}
	
	@Override

	public boolean onOptionsItemSelected(MenuItem item) {

		
		switch (item.getItemId()) {

		
		case R.string.supprimer:
			Intent result = new Intent();
			 Bundle b = new Bundle();
			 b.putString("numserie", numserie);
			 b.putString("codeclient", stcodeclient);
			 b.putString("codearticle", stcodearticle );
			 b.putString("libarticle", stlibarticle);
			 b.putString("codesymp", "");
			 b.putString("libsymp", "" );


			 result.putExtras(b);
			 setResult(Activity.RESULT_CANCELED, result);

			 finish();
		

			return true;
			
		case R.string.Photo:
			dispatchTakePictureIntent(stnumcde, numserie);
		
		case R.string.gallerie:
			Bundle bundle = new Bundle();
			bundle.putString("type", numserie);
			bundle.putString("numcde", stnumcde);
			Intent i1 = new Intent(OptionSymptomes.this, GalleryIntervention.class);
			i1.putExtras(bundle);
			startActivityForResult(i1, 150);
		
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
		//return super.onOptionsItemSelected(item);
	}
	private void updateChecked() {
	
			for(Symptomes materiel : symptome){
				
					if(materiel.cod_symp.equals(stcodesymp)){
						materiel.setChecked(true);
						
					}
			
		}	
	}
	private void dispatchTakePictureIntent(String numcde, String typeMatricule) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile(numcde, typeMatricule);
			} catch (IOException ex) {
				// Error occurred while creating the File
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				currentFilePath = Uri.fromFile(photoFile).getPath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}
	/**
	 * Permet de créer le fichier pour enregistrer la photo
	 * @param type
	 * @return
	 * @throws IOException
	 */
	private File createImageFile(String numcde, String type) throws IOException {
		// Create an image file name
		//ExternalStorage externalStorage = new ExternalStorage(mBase);
		ExternalStorage externalStorage = new ExternalStorage(this, false);
		

		String imageFileName = numcde+"_"+type;

		int nbPhotos = countPhoto(numcde, type);

		//on ne peut pas dépasser deux photos prises par type
		if(nbPhotos >= 0 && nbPhotos < 2){
			ArrayList<String> names = getPhotoName(numcde,type);
			if(names.size() == 1) {
				if(names.get(0).contains("_1")) imageFileName += "_2";
				else if(names.get(0).contains("_2")) imageFileName += "_1";
			}else imageFileName += "_1";
		}
		//File file = new File(externalStorage.getSignaturesFolder()+File.separator+ "inventaireduo_"+mCodeInv+".jpg");
		
		File storageDir = new File(externalStorage.getPhotosFolder());
		File image = new File(storageDir+File.separator+imageFileName+getString(R.string.jpg_extension));
		return image;
	}

	/**
	 * Permet de compter le nombre de photo par type et par numéro de commande
	 * @param numcde
	 * @param type
	 * @return count
	 */
	private int countPhoto(String numcde, String type){
	//	ExternalStorage externalStorage = new ExternalStorage(mBase);
		ExternalStorage externalStorage = new ExternalStorage(this, false);
		
		int count = 0;
		File photos = new File(
				externalStorage.getPhotosFolder());
		File[] files_sig = photos.listFiles();

		if (files_sig == null) return 0;

		for (File f : files_sig) {
			if (f.isDirectory()) continue;
			String[] tab = f.getName().split("_");
			String name = "";
			if(tab.length > 0) {
				name = tab[0]+"_"+tab[1];
				if(name.equals(numcde+"_"+type)) count++;
			}
		}

		return count;
	}

	private ArrayList<String> getPhotoName(String numcde, String type){
		ArrayList<String> names = new ArrayList<String>();
		//ExternalStorage externalStorage = new ExternalStorage(mBase);
		ExternalStorage externalStorage = new ExternalStorage(this, false);
		
		File photos = new File(
				externalStorage.getPhotosFolder());
		File[] files_sig = photos.listFiles();

		if (files_sig == null) return names;

		for (File f : files_sig) {
			if (f.isDirectory()) continue;

			String[] tab = f.getName().split("_");
			String name = "";
			if(tab.length > 0) {
				name = tab[0]+"_"+tab[1];
				if(name.equals(numcde+"_"+type)) names.add(f.getName());
			}
		}

		return names;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.i("tag", "onActivityResult : "+requestCode);

		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
			if(currentFilePath != null && !currentFilePath.equals("")){
				Uri imageUri = Uri.fromFile(new File(currentFilePath));
				Fonctions.adjustBitmapPictureFromUri(imageUri, 1920, 1440, newImage);
			}

			Toast.makeText(this, R.string.photo_save, Toast.LENGTH_LONG).show();
		//	fillCountPhoto();
		}

		if (requestCode == REQUEST_TAKE_PHOTO && resultCode != RESULT_OK){
			Toast.makeText(this, R.string.photo_not_save, Toast.LENGTH_LONG).show();
			//fillCountPhoto();
		}

		
	}

}
