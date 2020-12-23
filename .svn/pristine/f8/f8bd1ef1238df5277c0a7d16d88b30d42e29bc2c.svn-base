package com.menadinteractive.histo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.InterventionMaterielAdapter;
import com.menadinteractive.segafredo.carto.CartoMapActivity;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.tasks.task_sendWSData;

public class InterventionActivity extends BaseActivity implements OnClickListener{

	ArrayList<Bundle> cli;
	String commentaire = null;	
	CheckBox checkAutre;//
	boolean bcheckAutre=false;
	Spinner sTypeDemande;
	Spinner sJourFermeture;
	String m_stvaleurDebut;
	String m_stvaleurFin;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_TAKE_PHOTO = 2;
	String currentFilePath;
	Bitmap newImage = null;
	
	
	com.menadinteractive.segafredo.communs.myListView lvMateriel;
	EditText etFilter, etAutre;
	ImageButton ibFind;
	Button butAutres;
	String m_soccode="1";
	String heureDarrivee;
	String m_numcde="";
	String m_codecli = "";
	String m_typ_inter="";
	private ProgressDialog m_ProgressDialog = null;
	structClient structcli=new structClient();
	//TODO récupérer le bon taux à partir de params
	float m_dTVA = 0;

	ArrayList<Materiel> materiels;
	InterventionMaterielAdapter adapter;

	Context mContext;
	TextView tvAutre;

	Handler handler;

	ArrayList<Bundle> idListIntervention= null;// 
	
	ArrayList<Bundle> idJourFermeture= null;// 
	
	ArrayList<String> codes = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intervention);
		TableSouches souche=new TableSouches(m_appState.m_db,this);
		idListIntervention= new ArrayList<Bundle>();
		idJourFermeture= new ArrayList<Bundle>();
		
		Bundle bundle = this.getIntent().getExtras();
		m_codecli = bundle.getString("codeclient");
		m_typ_inter = bundle.getString("typ_inter");
		
		

		mContext = this;

		if (m_codecli.equals(""))
			m_numcde="";
		else
		{
			if (m_numcde.equals(""))
				m_numcde =  souche.get(TableSouches.TYPEDOC_INTERVENTION, Preferences.getValue(this, Espresso.LOGIN, "0"));
			TableClient cli=new TableClient(m_appState.m_db);
			cli.getClient(m_codecli, structcli,  new StringBuilder());

		}

		if (m_numcde.equals("") && m_codecli.equals("")==false) 
		{
			finish();
			return;

		}
		heureDarrivee=Fonctions.getYYYYMMDDhhmmss();

		initGUI();
		initListeners();
		setListeners();
		fillIntervention("");
		fillJourFermeture(Global.dbClient.getClientJourFer(m_codecli));
		m_stvaleurDebut=Global.dbClient.getClientJourFer(m_codecli);
		
		

		PopulateList();

		// bloquage Remise

		checkAutre = (CheckBox) findViewById(R.id.checkAutre);

		checkAutre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkAutre.isChecked()) {
					checkAutre.setFocusable(true);
					bcheckAutre=true;
					//etAutre.setVisibility(View.VISIBLE);
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

		sTypeDemande = (Spinner) findViewById(R.id.sTypeDemande);
		sJourFermeture = (Spinner) findViewById(R.id.sJourFermeture);
		
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
			
			launchAutres(m_numcde, m_codecli,Global.Autres,Global.Autres,m_typ_inter);
			
			
			
			
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
				
				
				launchSymptomes(stnumserie,stcodearticle,stlibarticle,m_codecli,code_symp,"","0",m_numcde,"0");
				
				
				
				
				/*

				if(materiel.getChecked()){
					materiel.setChecked(false);
					view.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
					if(codes.contains(materiel.getNumeroSerie())){
						codes.remove(materiel.getNumeroSerie());
					}
				}else{
					materiel.setChecked(true);
					view.setBackgroundColor(mContext.getResources().getColor(R.color.greenPanier));
					if(!codes.contains(materiel.getNumeroSerie())){
						codes.add(materiel.getNumeroSerie());
					}
				}*/
				
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		addMenu(menu, R.string.commande_valider,
				android.R.drawable.ic_menu_add);

		addMenu(menu, R.string.prospect_comment,
				android.R.drawable.ic_menu_info_details);
		
		addMenu(menu, R.string.Photo,
				android.R.drawable.ic_menu_camera);
		
		addMenu(menu, R.string.gallerie,
				android.R.drawable.picture_frame);
		
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
			if(Controle()==true)
			{
				MessageYesNo(getString(R.string.Intervention_save));
			
			}

			return true;
		case R.string.prospect_comment:
			structEntCde sent=new structEntCde();
			if (ent.load(sent, m_numcde, new StringBuffer(), false))
				commentaire=sent.COMMENTCDE;

			LaunchComment( commentaire,m_codecli,80,"C" );


			return true;
			
		case R.string.Photo:
			dispatchTakePictureIntent(m_numcde, getDemandeIntervention());
			
			return true;
		
		case R.string.gallerie:
			Bundle bundle = new Bundle();
			bundle.putString("type", getDemandeIntervention());
			bundle.putString("numcde", m_numcde);
			Intent i1 = new Intent(InterventionActivity.this, GalleryIntervention.class);
			i1.putExtras(bundle);
			startActivityForResult(i1, 150);
		
			return true;
		case R.string.commande_annuler:
			StringBuffer err = new StringBuffer();
			Global.dbKDLinCde.deleteNumcde(m_numcde, err);
			returnCancel();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	boolean saveCde(boolean okToSendToServer) {
		try {

			dbKD83EntCde.structEntCde ent = new dbKD83EntCde.structEntCde();

			ent.SOCCODE = getSocCode();
			if (okToSendToServer){
				ent.SEND = "1"; // par défaut 1 pour envoyé
				//incremente le numro de doc
				TableSouches souche=new TableSouches(m_appState.m_db,InterventionActivity.this);
				souche.incNum( Preferences.getValue(InterventionActivity.this, Espresso.LOGIN, "0"),TableSouches.TYPEDOC_INTERVENTION);
			}
			else
				ent.SEND="0";
			ent.CODEREP = getLogin();

			ent.TYPEDOC=TableSouches.TYPEDOC_INTERVENTION;
			ent.CODECLI = m_codecli;
			ent.REFCDE = getJourFermeture();		
			ent.EMAILCLI = "";
			ent.ADRLIV = getDemandeIntervention();
			ent.REMISE1 = 0; // Fonctions.GetStringToFloatDanem(this.getTextViewText(this,
			ent.ESCOMPTE = "";
			ent.DATELIVR=ent.DATECDE = Fonctions.getYYYYMMDD();
			if(commentaire != null){
				ent.COMMENTCDE = commentaire;
			}else{
				ent.COMMENTCDE = Global.dbKDEntCde.getCommentFromNumCde(m_numcde);
			}
			ent.MNTHT = Fonctions.GetStringToFloatDanem(getTextViewText(this,
					R.id.textViewMntHTVal));

			//TODO calcul correct du montant de la tva
			ent.MNTTVA1 = Fonctions.GetStringToFloatDanem(this.getTextViewText(
					this, R.id.textViewMntTVAVal));

			ent.MNTTC = Fonctions.GetStringToFloatDanem(this.getTextViewText(
					this, R.id.textViewMntTTCVal));

			ent.R_REGL = structcli.MODEREGLEMENT;
			ent.R_COND = "";
			ent.R_NBJOURS = "";
			ent.R_CODEREGL = "";

			String regledecalcalule=Global.dbParam.getComment(Global.dbParam.PARAM_MODEREGLEMENT,ent.R_REGL);
			if (regledecalcalule.equals("")==false)
			{

				ent.ECHEANCE =Global.dbParam.calcDateEcheance(Fonctions.getYYYYMMDD(),regledecalcalule);
			}
			else
			{
				ent.ECHEANCE =  Fonctions.getYYYYMMDD();
			}


			ent.PORT = "";
			ent.DEPOT =  Preferences.getValue(this, Espresso.DEPOT, "1");
			ent.ETAT = "1";
			if (Global.dbKDEntCde.isPrintOk(m_numcde) ==false)
				ent.ISPRINT = "0";
			else
				ent.ISPRINT="1";
			StringBuffer sbVer = new StringBuffer();
			ent.VERSION = Fonctions.getInToStringDanem(Fonctions.getVersion(
					this, sbVer));// mv363

			ent.DATEHEUREDEBUT = heureDarrivee;//Fonctions.getYYYYMMDDhhmmss();
			ent.DATEHEUREFIN = Fonctions.getYYYYMMDDhhmmss();

			if(Global.dbClient.getTypeClientValue(m_codecli).equals(Global.dbClient.CLI_TYPE_PROSPECT))
			{
				StringBuffer stBuf =new StringBuffer();
				Global.dbClient.saveUpdateTypeclient(Global.dbClient.CLI_TYPE_CLIENT,Fonctions.getYYYYMMDD(),m_codecli, stBuf);
			}
			if (!Global.dbKDEntCde.save(ent, m_numcde, new StringBuffer())) {

				return false;
			}

			//tof: enregistrement client vu
			structClient strClient = new structClient ();
			strClient.CODE = m_codecli ;
			CartoMapActivity.updateVisite("", strClient, true ,this);			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
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
		if (requestCode==LAUNCH_COMMENTAIRE)
		{
			if (resultCode==RESULT_OK)
			{
				commentaire=data.getExtras().getString("newvalue");
				Global.dbKDEntCde.saveComment(m_numcde, commentaire);
			}
		}
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
				
				//checkAutre.setChecked(true);
				//butAutres.setVisibility(View.VISIBLE);
				
			}
		}
		


		if (requestCode == LAUNCH_SAISIEQTE) {
			if (m_codecli.equals("")==false && resultCode == Activity.RESULT_OK ) {

				Bundle b = data.getExtras();
				Global.CODCLI_TEMP = m_codecli;
				
				saveLin(getLogin(),getSocCode(), m_codecli,
						m_numcde, getBundleValue(b, "codearticle"), getBundleValue(b, "libarticle") , getBundleValue(b, "numserie"),
						getBundleValue(b, "codesymp"),getBundleValue(b, "libsymp"),"","","","","",TableSouches.TYPEDOC_INTERVENTION,true);
				
					
				if(!codes.contains(getBundleValue(b, "numserie"))){
					codes.add(getBundleValue(b, "numserie"));
				}
				
			}
			if (m_codecli.equals("")==false && resultCode == Activity.RESULT_CANCELED) {

				Bundle bd = data.getExtras();
				Global.CODCLI_TEMP = m_codecli;
				StringBuffer err = new StringBuffer();
				Global.dbKDLinCde.deleteArticleIntervention(m_numcde, getBundleValue(bd, "numserie"),  err);
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

					TableSouches souche=new TableSouches(m_appState.m_db,InterventionActivity.this);
					//if (souche.get(TableSouches.TYPEDOC_RETOURMACHINE, getLogin()).equals(""))
					//{
					//	promptText(getString(R.string.retour_mat_riel), getString(R.string.vous_n_tes_pas_autoris_faire_des_retours_mat_riel), false);
					//	return;
					//}

					/*dbKD451RetourMachineclient ret=new dbKD451RetourMachineclient(m_appState.m_db);
					if (ret.getPieceNotSend(m_codecli)!=null)
					{
						promptText(getString(R.string.nouveau_retour_impossible), getString(R.string.vous_avez_d_j_un_retour_envoyer_avant_d_en_faire_un_nouveau), false);
						return;
					}*/

					//launchMenuCommentaireRetour(m_codecli,"",numserie,codemateriel,designation);

					break;
				}
			}

		};
		return h;
	}
	void fillIntervention(String selVal) {
		try {

			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_INTERVENTION,
					this.idListIntervention,false) == true) {

				int pos = 0;
				String[] items = new String[idListIntervention.size()];
				for (int i = 0; i < idListIntervention.size(); i++) {

					items[i] = idListIntervention.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListIntervention.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.sTypeDemande);


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	void fillJourFermeture(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_JOURFERMETURE,
					this.idJourFermeture, true) == true) {

				int pos = 0;
				String[] items = new String[idJourFermeture.size()];
				for (int i = 0; i < idJourFermeture.size(); i++) {

					items[i] = idJourFermeture.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idJourFermeture.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.sJourFermeture);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	String getDemandeIntervention()
	{
		String getDemandeIntervention="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.sTypeDemande);
		if (pos > -1)
			try {
				getDemandeIntervention = idListIntervention.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return getDemandeIntervention;
	}
	String getJourFermeture() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.sJourFermeture);
		if (pos > -1)
			try {
				pays = idJourFermeture.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
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
		
		/*if(getJourFermeture().equals(""))
		{
			bres=false;
			promptText("Demande Intervention", "Veuillez renseigner le jour de fermeture", false);	
		}*/
		if(Fonctions.GetStringDanem(commentaire).equals(""))
		{
			bres=false;
			promptText("Demande Intervention", "Le commentaire est obligatoire", false);
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
	public void MessageYesNo(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {	
				m_stvaleurFin=getJourFermeture();
				StringBuffer buff = new StringBuffer ();
				if(!Fonctions.GetStringDanem(m_stvaleurDebut).equals(m_stvaleurFin))
				{
					Global.dbClient.saveModifJourFermeture(m_codecli,"M",m_stvaleurFin,Fonctions.getYYYYMMDD(), buff);
				}
				
				saveCde(true);
				setResult(RESULT_OK);
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
