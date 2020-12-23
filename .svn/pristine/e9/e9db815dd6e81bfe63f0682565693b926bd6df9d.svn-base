package com.menadinteractive.histo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.InterventionMaterielAdapter;
import com.menadinteractive.segafredo.carto.CartoMapActivity;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.plugins.Espresso;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InterventionActivityRA extends BaseActivity implements OnClickListener {
	
	ArrayList<Bundle> cli;
	CheckBox checkAutre;//
	boolean bcheckAutre=false;
	String currentFilePath;
	
	
	com.menadinteractive.segafredo.communs.myListView lvMateriel;
	EditText etFilter, etAutre;
	ImageButton ibFind;
	Button butAutres;
	String m_soccode="1";
	String m_codecli = "";
	String m_typ_inter="";
	private ProgressDialog m_ProgressDialog = null;
	structClient structcli=new structClient();
	
	ArrayList<Materiel> materiels;
	InterventionMaterielAdapter adapter;

	Context mContext;
	TextView tvAutre;

	Handler handler;

	ArrayList<Bundle> idListIntervention= null;// 
	
	ArrayList<Bundle> idJourFermeture= null;// 
	
	ArrayList<String> codes = null;
	String m_numcde="";
	
	
	String m_codearticle = "";
	String m_libarticle = "";
	String m_numserie = "";
	String m_codesymp = "";
	String m_libsymp ="";
	
	String m_qui="";
	String m_marque ="";
	String m_typemachine ="";
	String m_marquemachine ="";
	String m_seriemachine ="";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interventionra);
		TableSouches souche=new TableSouches(m_appState.m_db,this);
		idListIntervention= new ArrayList<Bundle>();
		idJourFermeture= new ArrayList<Bundle>();
		
		Bundle bundle = this.getIntent().getExtras();
		m_codecli = bundle.getString("codeclient");
	//	m_typ_inter = bundle.getString("typ_inter");
		
		

		mContext = this;

		
		TableClient cli=new TableClient(m_appState.m_db);
		cli.getClient(m_codecli, structcli,  new StringBuilder());

	
		initGUI();
		initListeners();
		setListeners();
	
		PopulateList();

		// bloquage Remise

		checkAutre = (CheckBox) findViewById(R.id.checkAutre);

		checkAutre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkAutre.isChecked()) {
					checkAutre.setFocusable(true);
					bcheckAutre=true;
					butAutres.setVisibility(View.VISIBLE);






				} else {
					checkAutre.setFocusable(false);
					etAutre.setText("");
					bcheckAutre=false;
					etAutre.setVisibility(View.GONE);
					butAutres.setVisibility(View.GONE);
					StringBuffer err=new StringBuffer();
					Global.dbKDLinCde.deleteArticleIntervention(m_numcde, Global.Autres, err);


					
				}

			}
		});
		
		codes = new ArrayList<String>();

	}

	/**
	 * Initialisation des éléments graphiques
	 */
	private void initGUI() {
		lvMateriel = (com.menadinteractive.segafredo.communs.myListView) findViewById(R.id.lvMateriel);
		etFilter = (EditText) findViewById(R.id.etFilter);
		etAutre = (EditText) findViewById(R.id.etAutre);
		etAutre.setVisibility(View.GONE);
		ibFind = (ImageButton) findViewById(R.id.ibFind);
		butAutres= (Button) findViewById(R.id.butAutres);
		butAutres.setVisibility(View.VISIBLE);
		handler =getHandler();
		tvAutre= (TextView) findViewById(R.id.tvAutre);
		CheckBox checkAutre = (CheckBox) findViewById(R.id.checkAutre);
		checkAutre.setVisibility(View.GONE);
		

	}
	void setListeners()
	{


		ibFind.setOnClickListener(this);
		butAutres.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==ibFind)
		{
			PopulateList() ;
			//PopulateList( etFilter.getText().toString().trim());

		}
		if (v == butAutres) {
			
			launchAutresRA(m_numcde, m_codecli,Global.Autres,Global.Autres,m_typ_inter,m_codesymp,m_libsymp,m_qui,m_marque,m_typemachine,m_marquemachine,m_seriemachine);
			
		}
	}
	private void PopulateList() {

		//		cli=Global.dbMaterielClient.getMaterielClientFilters(m_codecli,  etFilter.getText().toString().trim());
		//		ArrayList<assoc> assocs =new ArrayList<assoc>();

		materiels = Materiel.getAllMateriel(m_codecli, etFilter.getText().toString().trim());
		
		updateChecked();
		adapter = new InterventionMaterielAdapter(mContext, 0, materiels);
		lvMateriel.setAdapter(adapter);


		//		assocs.add(new  assoc(0,R.id.tvLibart,TableMaterielClient.FIELD_LIBART));
		//		assocs.add(new  assoc(0,R.id.tvTypeCon, TableMaterielClient.FIELD_TYPECONTRAT));		
		//		assocs.add(new  assoc(0,R.id.tvNumserie, TableMaterielClient.FIELD_NUMSERIEFAB));	
		//		assocs.add(new  assoc(0,R.id.tvDateInst, TableMaterielClient.FIELD_DATEINST));	
		//		assocs.add(new  assoc(0,R.id.tvDateFin, TableMaterielClient.FIELD_DATEFIN));	
		//		assocs.add(new  assoc(1,R.id.iv2,null));

		//		lvMateriel.attachValues(R.layout.item_list_materielclient, cli,assocs,handler);

	}

	private void updateChecked() {
		if(codes != null && codes.size() > 0){
			for(Materiel materiel : materiels){
				for(String code : codes){
					if(materiel.getNumeroSerie().equals(code)){
						materiel.setChecked(true);
					}
				}
			}
		}	
	}

	/**
	 * Initialisation des listeners de l'activity
	 */
	private void initListeners() {
		lvMateriel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Materiel materiel = materiels.get(position);
				String stnumserie=materiel.getNumeroSerie();
				String stcodearticle=materiel.getNumeroSerie();
				String stlibarticle=materiel.getNumeroSerie();
				String code_symp = Global.dbKDLinCde.GetLoadcode_symp( m_numcde ,stnumserie);
				
				
				launchSymptomes(stnumserie,stcodearticle,stlibarticle,m_codecli,code_symp,"","0",m_numcde,"1");
				
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		addMenu(menu, R.string.commande_valider,
				android.R.drawable.ic_menu_add);

		addMenu(menu, R.string.commande_annuler,
				android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		dbKD83EntCde ent=new dbKD83EntCde(m_appState.m_db);
		boolean valide = ent.isFactureValide(m_numcde);

		StringBuffer buff = new StringBuffer();
		ArrayList<String> ValueMessage=new ArrayList();
		switch (item.getItemId()) {

		case R.string.commande_valider:
			 Intent result = new Intent();
			 Bundle b = new Bundle();
			 b.putString("numserie", m_numserie);
			 b.putString("codeclient", m_codecli);
			 b.putString("codearticle", m_codearticle );
			 b.putString("libarticle", m_libarticle);
			 b.putString("codesymp", m_codesymp);
			 b.putString("libsymp", m_libsymp);
			 
			 b.putString("qui", m_qui);
			 b.putString("marque", m_marque);
			 b.putString("typemachine", m_typemachine);
			 b.putString("marquemachine", m_marquemachine);
			 b.putString("seriemachine", m_seriemachine);
			 
			

			 result.putExtras(b);
			 setResult(Activity.RESULT_OK, result);
			/*if(Controle()==true)
			{
				MessageYesNo(getString(R.string.Intervention_save));
			
			}*/
			 finish();
 
			return true;
		
			
		case R.string.commande_annuler:
			StringBuffer err = new StringBuffer();
			//Global.dbKDLinCde.deleteNumcde(m_numcde, err);
			returnCancel();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static void saveLin(String rep,String m_soccode,String m_codecli,
			String m_numcde,String codeart,String lblart ,String numserie,String code_symp,String lib_symp,
			String saisienumserie,String Qui,String Marque,String typemachine,String jourferm,String typedoc,boolean bcoche){
		dbKD84LinCde.structLinCde lin = new dbKD84LinCde.structLinCde();


		lin.TAXE1 = 0.0f;
		lin.TAXE2 = 0.0f;
		lin.TAXE3 = 0.0f;

		lin.SOCCODE=m_soccode;
		lin.CLICODE=m_codecli;
		lin.CDECODE = m_numcde;

		lin.DESIGNATION = lblart;
		lin.PROCODE = numserie;
		lin.PRIXMODIF = Fonctions.convertToFloat( "");
		if(bcoche==true)
		{
			lin.QTECDE = Fonctions.convertToFloat("1");

		}
		else
			lin.QTECDE = Fonctions.convertToFloat("0");

		lin.QTEGR = Fonctions.convertToFloat("");
		lin.REMISEMODIF=Fonctions.convertToFloat("");
		lin.DATE=Fonctions.getYYYYMMDDhhmmss();
		lin.MNTUNITHT = Fonctions.GetStringToFloatDanem("");
		lin.TYPEPIECE=typedoc;
		lin.COMMENT1=numserie;
		lin.COMMENT2=codeart;
		lin.COMMENT3=code_symp;//Autres: code symptomes 
		lin.LOT=lib_symp;// Autres : lib symp
		
		lin.UV=typemachine;	//Autres: type machine
		lin.CODETVA=saisienumserie;	//Autres : N° de série
		lin.TYPECHECKBOX=Qui;	//Autres ; Qui
		lin.FIELD_LIGNECDE_LINCHOIX1=Marque;	//Autres : Marque
		lin.TYPECDEVALEUR=jourferm;	//Autres : Jour ferm
		
		
		
		lin.REPCODE =rep;
		lin.TAUX =       Fonctions.GetStringToFloatDanem("");
		lin.PRIXINITIAL =  "";;
		lin.MNTUNITNETHT = Fonctions.GetStringToFloatDanem("");
		lin.MNTTOTALHT = Fonctions.GetStringToFloatDanem("");
		lin.MNTTVA=Fonctions.GetStringToFloatDanem("");

		lin.MNTTOTALTTC= Fonctions.GetStringToFloatDanem("") ;
		if (lin.QTECDE == 0 ) {
			Global.dbKDLinCde.delete(m_numcde, lin.PROCODE, "0",
					new StringBuffer(), "");
		} else {
			Global.dbKDLinCde.save(lin, m_numcde, lin.PROCODE,
					"0", new StringBuffer()); 
		}

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode==LAUNCH_AUTRE)
		{
			
			if (resultCode==RESULT_CANCELED)
			{
				//checkAutre.setChecked(false);
				//butAutres.setVisibility(View.GONE);
				tvAutre.setBackgroundColor(color.transparent);
				
				
			}
			if (resultCode==RESULT_OK)
			{
				tvAutre.setBackgroundColor(mContext.getResources().getColor(R.color.greenPanier));
				
				Bundle b = data.getExtras();
				Global.CODCLI_TEMP = m_codecli;
				
				
				m_codearticle = getBundleValue(b, "codearticle");
				m_libarticle = getBundleValue(b, "libarticle");
				m_numserie = getBundleValue(b, "numserie");
				m_codesymp = getBundleValue(b, "codesymp");
				m_libsymp = getBundleValue(b, "libsymp");
				
				m_qui = getBundleValue(b, "qui");
				m_marque = getBundleValue(b, "marque");
				m_typemachine = getBundleValue(b, "typemachine");
				m_marquemachine = getBundleValue(b, "marquemachine");
				m_seriemachine = getBundleValue(b, "seriemachine");
				if(codes != null && codes.size() > 0)
					codes.remove(0);
				PopulateList();
				
				
				
			}
		}
		


		if (requestCode == LAUNCH_SAISIEQTE) {
			if (m_codecli.equals("")==false && resultCode == Activity.RESULT_OK ) {

				Bundle b = data.getExtras();
				Global.CODCLI_TEMP = m_codecli;
				
				//saveLin(getLogin(),getSocCode(), m_codecli,
				//		m_numcde, getBundleValue(b, "codearticle"), getBundleValue(b, "libarticle") , getBundleValue(b, "numserie"),
				//		getBundleValue(b, "codesymp"),getBundleValue(b, "libsymp"),"","","","","",TableSouches.TYPEDOC_INTERVENTION,true);
				
				m_codearticle = getBundleValue(b, "codearticle");
				m_libarticle = getBundleValue(b, "libarticle");
				m_numserie = getBundleValue(b, "numserie");
				m_codesymp = getBundleValue(b, "codesymp");
				m_libsymp = getBundleValue(b, "libsymp");
				
				m_qui="";
				m_marque="";
				m_typemachine="";
				m_marquemachine="";
				m_seriemachine="";
				
					
				if(!codes.contains(getBundleValue(b, "numserie"))){
					codes.add(getBundleValue(b, "numserie"));
				}
				tvAutre.setBackgroundColor(color.transparent);
				
				
			}
			if (m_codecli.equals("")==false && resultCode == Activity.RESULT_CANCELED) {

				Bundle bd = data.getExtras();
				Global.CODCLI_TEMP = m_codecli;
				//StringBuffer err = new StringBuffer();
				//Global.dbKDLinCde.deleteArticleIntervention(m_numcde, getBundleValue(bd, "numserie"),  err);
				if(codes.contains(getBundleValue(bd, "numserie"))){
					codes.remove(getBundleValue(bd, "numserie"));
				}
				
			}
			PopulateList();
			
			
		}



	}
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
				case R.id.lvMateriel:
				case R.id.iv2:
					int  id=bGet.getInt("position");

					String numserie=cli.get(id).getString(TableMaterielClient.FIELD_NUMSERIEFAB);
					String codemateriel=cli.get(id).getString(TableMaterielClient.FIELD_CODEART);
					String designation=cli.get(id).getString(TableMaterielClient.FIELD_LIBART);

					TableSouches souche=new TableSouches(m_appState.m_db,InterventionActivityRA.this);
					

					break;
				}
			}

		};
		return h;
	}
	
	
	boolean Controle()
	{
		boolean bres=true;

		if(Global.dbKDLinCde.Count_Numcde(m_numcde, false)>0)
		{
			
		}
		else
		{
			
			if(bres==true)
			{
				if(Global.dbKDLinCde.Count_Numcde(m_numcde, false)==0)
				{
					bres=false;
					promptText("Demande Intervention", "Sélection ou création d\'une machine obligatoire.", false);
				}
			}
			
		}
		
	

		return bres ; 
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

	public void MessageYesNo(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {	
				StringBuffer buff = new StringBuffer ();
				
				finish();
			}
		})
		.setNegativeButton(getString(android.R.string.no),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	


}
