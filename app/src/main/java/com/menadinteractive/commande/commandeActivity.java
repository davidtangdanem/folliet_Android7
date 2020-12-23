package com.menadinteractive.commande;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.histo.HistoDocumentsActivity;
import com.menadinteractive.printmodels.BluetoothConnectionInsecureExample;
import com.menadinteractive.printmodels.Z420ModelFacture;
import com.menadinteractive.segafredo.carto.CartoMapActivity;
import com.menadinteractive.segafredo.carto.MenuPopup;
import com.menadinteractive.segafredo.client.ficheclient;
import com.menadinteractive.segafredo.client.statclient;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.contactcli.ContactCliActivity;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD730FacturesDues;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.dbParam;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteHabitudes.structHabitude;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.db.dbSiteProduit;
import com.menadinteractive.segafredo.db.dbSiteProduit.structArt;
import com.menadinteractive.segafredo.encaissement.ReglementActivity;
import com.menadinteractive.segafredo.encaissement.SignatureActivity;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.rapportactivite.rapportActivity;
import com.menadinteractive.segafredo.rapportactivite.rapportactivite;
import com.menadinteractive.segafredo.tasks.task_sendWSData;

public class commandeActivity extends BaseActivity 
implements OnItemSelectedListener, OnClickListener
{
	String commentaire = null;
	String refcdeclient = null;
	String ss="";
	static final int DATE_PICKER_LIV = 4444;




	private listAdapter m_adapter;
	private ProgressDialog m_ProgressDialog = null;
	structClient structcli=new structClient();
	//	boolean m_isPrinted = false;// est ce que l'on a "essayé" d'imprimer la cde
	ListView myListView;

	boolean m_problemPrinter=false;//si on declare un probleme d'imprimante on laisse passer
	int top = 0;
	int index = 0;

	EditText etFilter;
	EditText etEmailclient;
	TextView tvTypeDoc;
	double m_dTotalHT=0;
	double m_Cli_Ca_moy_fac=0;
	double m_cde_depassement=500;

	boolean bInit=true;

	boolean isValider = false, isComeFromReglement = false, isFirstClickValider = true;
	boolean isfacturable=false;

	int cptArtNonFacture = 0;

	ImageButton ibFilter,ibPanier,ibHabit;
	String heureDarrivee;
	String m_numcde;
	String m_codecli;
	String m_soccode;
	String m_typedoc;

	static String m_clientTVA="";
	String m_clientEmail="";
	String m_EmailAvant="";
	String m_EmailApres="";
	String m_numrapport="";
	
	int __year;
	int __month;
	int __day;
     
	
	//TODO récupérer le bon taux à partir de params
	float m_dTVA = 0;

	Handler hPrintResult;

	ArrayList<Bundle> idFam = null;// les id qui servent a retrouver les pays
	ArrayList<Bundle> idLivraison = null;// les id qui servent a retrouver les pays
	String m_stFam = "" ;	//Filtre Fam (voir spinner)

	Context context;

	structlistLogin rep = null;

	ArrayList<ArrayList<structLinCde>> listPrintFactures = null;

	static int cptImpression = 0;
	boolean m_btech=false;
	boolean m_bresp=false;
	
	boolean m_bVendeur=false;
	String m_contactnom="";
	
	Button bDate;
	Button bRobot;//Modif tof, pour saisie auto d'article
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commande); 

		context = this;

		isComeFromReglement=false;
		isfacturable=false;
		m_bVendeur=false;

		TableSouches souche=new TableSouches(m_appState.m_db,this);
		idFam = new ArrayList<Bundle>();

		Bundle bundle = this.getIntent().getExtras();
		m_codecli = getBundleValue(bundle,"codecli");
		m_soccode = getBundleValue(bundle,"soccode");
		m_typedoc = getBundleValue(bundle,"typedoc");
		m_numcde = getBundleValue(bundle,"numcde");
		m_numrapport = getBundleValue(bundle,"numrapport");

		Log.e("numrapport","numrapport"+m_numrapport);

        //DEV 60.1, valeur par defaut à true pour les client mauvais payeur
        Global.bNonRespect = false ;				//raz global
        Global.dMntGet = Global.dMntToGet = 0 ;		//raz global
        if(Global.dbClient.isMauvaisPayeur(m_codecli))
            Global.bNonRespect = true ;



				StringBuffer stBuf =new StringBuffer();
		m_contactnom=Global.dbKDClientVu._loadNomContact(m_numrapport,   stBuf);
		
		if(bundle != null){
			isComeFromReglement = bundle.getBoolean("isIgnoreReglement", false);
            isfacturable = bundle.getBoolean("isfacturable", false);
		}

		rep = getRep();
		structlistLogin representant = null;
		dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
		representant=new structlistLogin();
		
		login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), representant, new StringBuilder());
		if( Fonctions.GetStringDanem(representant.TYPE).equals(Global.Technicien) )
		{
			m_btech=true;
		}
		if(Fonctions.GetStringDanem(representant.TYPE).equals(Global.Vendeur) )
        {
            m_bVendeur=true;
        }
		if(Fonctions.GetStringDanem(representant.TYPE).equals(Global.Responsable) )
		{
			m_bresp=true;
		}
		m_cde_depassement= Fonctions.GetStringToDoubleDanem(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_DEPASSEMENT, "1"));
		
		Global.CODCLI_TEMP = m_codecli;
		if (m_codecli.equals(""))
			m_numcde="";
		else
		{
			if (m_numcde.equals(""))
				m_numcde =  souche.get(m_typedoc, Preferences.getValue(this, Espresso.LOGIN, "0"));
			TableClient cli=new TableClient(m_appState.m_db);
			cli.getClient(m_codecli, structcli,  new StringBuilder());
			m_Cli_Ca_moy_fac=Fonctions.GetStringToDoubleDanem(structcli.CA_MOY_FAC);
			m_clientTVA=Fonctions.GetStringDanem(structcli.TVA);
			m_EmailAvant=m_clientEmail=Fonctions.GetStringDanem(structcli.EMAIL).trim();
		}

		if (m_numcde.equals("") && m_codecli.equals("")==false) 
		{
			finish();
			return;
		}
		heureDarrivee=Fonctions.getYYYYMMDDhhmmss();
		initGUI();
		initListeners();
		if(m_btech==false && m_bresp==false)
		{
			etEmailclient.setVisibility(View.GONE);
			etEmailclient.setText(Fonctions.GetStringDanem(m_clientEmail));
			if( m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			{
				etEmailclient.setVisibility(View.VISIBLE);
				etEmailclient.setText(Fonctions.GetStringDanem(m_clientEmail));
			}
		}
		else
		{
			//if(m_bresp==true && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			{
				etEmailclient.setVisibility(View.VISIBLE);
				etEmailclient.setText(Fonctions.GetStringDanem(m_clientEmail));
			}
			else
			{
				etEmailclient.setVisibility(View.GONE);
				if(!Fonctions.GetStringDanem(m_clientEmail).equals(""))
					etEmailclient.setText(Fonctions.GetStringDanem(m_clientEmail));
			}
		}

		fillFamille("");

		if (m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE)) {

			LinearLayout linear = (LinearLayout) findViewById(R.id.linearSpinnerLiv);
			linear.setVisibility(View.VISIBLE);

			fillLivraison("");
		}

		// bug N°BC vide dans les rapports si app s'arrête avant d'avoir validé
		if(m_numrapport.equals("") ){
			//On checke si le N° de commande n'existe pas pour récupérer le N° de rapport enregistré
			m_numrapport = Global.dbKDClientVu.getNumRapportFromBC(m_numcde);
			Log.e("numRapport","getNumRapportFromBC"+m_numrapport);
		}

		CalcAll();
		
	}

	@Override
	public void onResume(){
		super.onResume();
		if(isComeFromReglement){
			DisableAllButtons();
		}

		//		int n = Global.dbKDLinCde.Count_Numcde(m_numcde, false);
		//		Fonctions._showAlert("Numéro de souche courant : "+m_numcde+" / Nb lignes : "+n, context);
	}

	void initGUI() {

		myListView = (ListView) findViewById(R.id.lv_4);
		myListView.setTextFilterEnabled(true);
		etFilter=(EditText)findViewById(R.id.etFilter);
		etEmailclient=(EditText)findViewById(R.id.etEmailclient);
		tvTypeDoc=(TextView)findViewById(R.id.textViewTypeDoc);
		ibFilter=(ImageButton)findViewById(R.id.ibFind);
		ibPanier=(ImageButton)findViewById(R.id.buttonPanier);
		ibHabit=(ImageButton)findViewById(R.id.buttonHabit);

		TextView textViewMntTVATitre=(TextView)findViewById(R.id.textViewMntTVATitre);
		TextView textViewMntTVAVal=(TextView)findViewById(R.id.textViewMntTVAVal);
		TextView textViewMntTTCTitre=(TextView)findViewById(R.id.textViewMntTTCTitre);
		TextView textViewMntTTCVal=(TextView)findViewById(R.id.textViewMntTTCVal);
		if(m_clientTVA.equals(TableClient.TVA_FISCAL_NEUF) || m_clientTVA.equals(TableClient.TVA_FISCAL_SIX))
		{
			textViewMntTVATitre.setVisibility(View.GONE);
			textViewMntTVAVal.setVisibility(View.GONE);
			textViewMntTTCTitre.setVisibility(View.GONE);
			textViewMntTTCVal.setVisibility(View.GONE);
		}
		else
		{
			textViewMntTVATitre.setVisibility(View.VISIBLE);
			textViewMntTVAVal.setVisibility(View.VISIBLE);
			textViewMntTTCTitre.setVisibility(View.VISIBLE);
			textViewMntTTCVal.setVisibility(View.VISIBLE);
		}
		bDate = (Button) findViewById(R.id.buttonDateLivr1);
		bDate.setOnClickListener(this);
		bRobot= (Button) findViewById(R.id.buttonRobot);
		bRobot.setOnClickListener(this);
		//if(m_bresp==true && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
		if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
		{
			bDate.setVisibility(View.VISIBLE);
			//bDate.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()));
			bDate.setText("00/00/0000");
		}
		else
		{
			bDate.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()));
			bDate.setVisibility(View.GONE);
		}

			
		//MV 27/03/2015, semble ameliorer le scroll
				myListView.setCacheColorHint(Color.TRANSPARENT); // not sure if this is required for you.
				myListView.setFastScrollEnabled(true);
			    myListView.setScrollingCacheEnabled(false);
		//
				tvTypeDoc.setText(m_typedoc);
		//		//	m_dTVA = (float) Fonctions.GetStringToDoubleDanem(Global.dbParam
		//		//			.getLblAllSoc(Global.dbParam.PARAM_TVA, "V20"));

		
		if(isfacturable==false)
		{
			if(m_btech==true)
			{
				PopulateList("",true,false);	
			}
			else
				PopulateList("",false,true);
		}
		else
			PopulateList("",false,false);
		
	}

	void initListeners() {
		hPrintResult=getHandler();

		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				index = myListView.getFirstVisiblePosition();
				View vw = myListView.getChildAt(0);
				top = (vw == null) ? 0 : vw.getTop();

				String codeart = arg1.getTag().toString();
				launchQtePrix(codeart);
				return;
			}
		});

		Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);
		spinner.setOnItemSelectedListener(this);

		ibFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String filter=etFilter.getText().toString();
				PopulateList(filter,false,false);

			}
		});
		ibHabit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isfacturable==false)
					PopulateList("",false,true);
				else
					PopulateList("",false,false);
					

				if(isFirstClickValider){
					//if(cptArtNonFacture > 0){
					isValider = true;
					m_adapter.notifyDataSetChanged();
					//}
					isFirstClickValider = false;
				}
			}
		});
		ibPanier.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopulateList("",true,false);
				CalcAll();
			}
		});

		bDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//R�cup�rer le string sur le bouton
				//String interneStDateCde= bDate.getText().toString();
				// String styear=Fonctions.Right(interneStDateCde, 4);
				// String stmonth=Fonctions.Mid(interneStDateCde, 3,2);
				// String stday=Fonctions.Left(interneStDateCde, 2);

				String date = Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD());
				String styear=Fonctions.Right(date, 4);
				String stmonth=Fonctions.Mid(date, 3,2);
				String stday=Fonctions.Left(date, 2);

				__year=Fonctions.GetStringToIntDanem(styear);
				__month=Fonctions.GetStringToIntDanem(stmonth);
				Log.e("_month",""+__month);
				__month=__month-1;

				__day=Fonctions.GetStringToIntDanem(stday);

				// On button click show datepicker dialog
				showDialog(DATE_PICKER_LIV);

			}
		});

		//Bouton a masquer en prod, saisie plusieurs article automatiquement
		bRobot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Initialisation du tableau 6516494694984948
				iIdxArtRobot = 0 ;
				arrListCodeArt = new ArrayList<String> () ;
				arrListCodeArt.add("0470") ;
				arrListCodeArt.add("0471") ;
				arrListCodeArt.add("0472") ;
				arrListCodeArt.add("0475") ;
				arrListCodeArt.add("0476") ;
				arrListCodeArt.add("0477") ;
				arrListCodeArt.add("0478") ;
				arrListCodeArt.add("0479") ;
				arrListCodeArt.add("0480") ;
				arrListCodeArt.add("0481") ;
				arrListCodeArt.add("0482") ;
				arrListCodeArt.add("0484") ;
				arrListCodeArt.add("0485") ;
				arrListCodeArt.add("0486") ;
				arrListCodeArt.add("0488") ;
				arrListCodeArt.add("0489") ;
				arrListCodeArt.add("0494") ;
				arrListCodeArt.add("0495") ;
				arrListCodeArt.add("0496") ;
				arrListCodeArt.add("0497") ;
				arrListCodeArt.add("0498") ;
				arrListCodeArt.add("0501") ;
				arrListCodeArt.add("0502") ;
				arrListCodeArt.add("0503") ;
				arrListCodeArt.add("0507") ;
				arrListCodeArt.add("0508") ;
				arrListCodeArt.add("0509") ;
				arrListCodeArt.add("0510") ;
				arrListCodeArt.add("0511") ;
				arrListCodeArt.add("0512") ;
				arrListCodeArt.add("0513") ;
				arrListCodeArt.add("0514") ;
				arrListCodeArt.add("0515") ;
				arrListCodeArt.add("0516") ;
				arrListCodeArt.add("0517") ;
				arrListCodeArt.add("0518") ;
				arrListCodeArt.add("0519") ;
				arrListCodeArt.add("0520") ;
				arrListCodeArt.add("0521") ;
				arrListCodeArt.add("0522") ;
				arrListCodeArt.add("0523") ;
				arrListCodeArt.add("0524") ;
				arrListCodeArt.add("0525") ;
				arrListCodeArt.add("0526") ;
				arrListCodeArt.add("0527") ;
				arrListCodeArt.add("0528") ;
				arrListCodeArt.add("0529") ;
				arrListCodeArt.add("0531") ;
				arrListCodeArt.add("0532") ;
				arrListCodeArt.add("0533") ;
				arrListCodeArt.add("0534") ;
				arrListCodeArt.add("0535") ;
				arrListCodeArt.add("0536") ;
				arrListCodeArt.add("0537") ;
				arrListCodeArt.add("0538") ;
				arrListCodeArt.add("0539") ;
				arrListCodeArt.add("0540") ;
				arrListCodeArt.add("0541") ;
				arrListCodeArt.add("0542") ;
				arrListCodeArt.add("0543") ;
				arrListCodeArt.add("0544") ;
				arrListCodeArt.add("0545") ;
				arrListCodeArt.add("0546") ;
				arrListCodeArt.add("0547") ;
				arrListCodeArt.add("0548") ;
				arrListCodeArt.add("0549") ;
				arrListCodeArt.add("0550") ;
				arrListCodeArt.add("0551") ;
				arrListCodeArt.add("0552") ;
				arrListCodeArt.add("0553") ;
				arrListCodeArt.add("0554") ;
				arrListCodeArt.add("0555") ;
				arrListCodeArt.add("0556") ;
				arrListCodeArt.add("0558") ;
				arrListCodeArt.add("0559") ;
				arrListCodeArt.add("0560") ;
				arrListCodeArt.add("0561") ;
				arrListCodeArt.add("0562") ;
				arrListCodeArt.add("0563") ;
				arrListCodeArt.add("0564") ;
				arrListCodeArt.add("0565") ;
				arrListCodeArt.add("0566") ;
				arrListCodeArt.add("0567") ;
				arrListCodeArt.add("0569") ;
				arrListCodeArt.add("0570") ;
				arrListCodeArt.add("0571") ;
				arrListCodeArt.add("0572") ;
				arrListCodeArt.add("0573") ;
				arrListCodeArt.add("0574") ;
				arrListCodeArt.add("0575") ;
				arrListCodeArt.add("0576") ;
				arrListCodeArt.add("0580") ;
				arrListCodeArt.add("0582") ;
				arrListCodeArt.add("0589") ;
				arrListCodeArt.add("0591") ;
				arrListCodeArt.add("0592") ;
				arrListCodeArt.add("0593") ;
				arrListCodeArt.add("0594") ;
				arrListCodeArt.add("0595") ;
				arrListCodeArt.add("0596") ;
				arrListCodeArt.add("0597") ;
				arrListCodeArt.add("0598") ;
				arrListCodeArt.add("0601") ;
				arrListCodeArt.add("0602") ;
				arrListCodeArt.add("0603") ;
				arrListCodeArt.add("0604") ;
				arrListCodeArt.add("0605") ;
				arrListCodeArt.add("0606") ;
				arrListCodeArt.add("0609") ;
				arrListCodeArt.add("0611") ;
				arrListCodeArt.add("0612") ;
				arrListCodeArt.add("0613") ;
				arrListCodeArt.add("0614") ;
				arrListCodeArt.add("0615") ;
				arrListCodeArt.add("0616") ;
				arrListCodeArt.add("0618") ;
				arrListCodeArt.add("0619") ;
				arrListCodeArt.add("0620") ;
				arrListCodeArt.add("0621") ;
				arrListCodeArt.add("0622") ;
				/*arrListCodeArt.add("0624") ;
				arrListCodeArt.add("0625") ;
				arrListCodeArt.add("0626") ;
				arrListCodeArt.add("0627") ;
				arrListCodeArt.add("0628") ;
				arrListCodeArt.add("0629") ;
				arrListCodeArt.add("0630") ;
				arrListCodeArt.add("0631") ;
				arrListCodeArt.add("0632") ;
				arrListCodeArt.add("0633") ;
				arrListCodeArt.add("0634") ;
				arrListCodeArt.add("0635") ;
				arrListCodeArt.add("0636") ;
				arrListCodeArt.add("0637") ;
				arrListCodeArt.add("0638") ;
				arrListCodeArt.add("0639") ;
				arrListCodeArt.add("0640") ;
				arrListCodeArt.add("0641") ;
				arrListCodeArt.add("0642") ;
				arrListCodeArt.add("0643") ;
				arrListCodeArt.add("0644") ;
				arrListCodeArt.add("0647") ;
				arrListCodeArt.add("0648") ;
				arrListCodeArt.add("0649") ;
				arrListCodeArt.add("0651") ;
				arrListCodeArt.add("0652") ;
				arrListCodeArt.add("0653") ;
				arrListCodeArt.add("0654") ;
				arrListCodeArt.add("0655") ;
				arrListCodeArt.add("0656") ;
				arrListCodeArt.add("0657") ;
				arrListCodeArt.add("0658") ;
				arrListCodeArt.add("0659") ;
				arrListCodeArt.add("0661") ;
				arrListCodeArt.add("0663") ;
				arrListCodeArt.add("0664") ;
				arrListCodeArt.add("0665") ;
				arrListCodeArt.add("0667") ;
				arrListCodeArt.add("0668") ;
				arrListCodeArt.add("0669") ;
				arrListCodeArt.add("0670") ;
				arrListCodeArt.add("0671") ;
				arrListCodeArt.add("0672") ;
				arrListCodeArt.add("0674") ;
				arrListCodeArt.add("0675") ;
				arrListCodeArt.add("0676") ;
				arrListCodeArt.add("0677") ;
				arrListCodeArt.add("0678") ;
				arrListCodeArt.add("0679") ;
				arrListCodeArt.add("0681") ;
				arrListCodeArt.add("0683") ;
				arrListCodeArt.add("0685") ;
				arrListCodeArt.add("0686") ;
				arrListCodeArt.add("0687") ;
				arrListCodeArt.add("0688") ;
				arrListCodeArt.add("0689") ;
				arrListCodeArt.add("0693") ;
				arrListCodeArt.add("0696") ;
				arrListCodeArt.add("0697") ;
				arrListCodeArt.add("0700") ;
				arrListCodeArt.add("0701") ;
				arrListCodeArt.add("0702") ;
				arrListCodeArt.add("0703") ;
				arrListCodeArt.add("0704") ;
				arrListCodeArt.add("0705") ;
				arrListCodeArt.add("0707") ;
				arrListCodeArt.add("0708") ;
				arrListCodeArt.add("0709") ;
				arrListCodeArt.add("0710") ;
				arrListCodeArt.add("0712") ;
				arrListCodeArt.add("0713") ;*/


				launchQtePrixRobot();
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	       
	    case DATE_PICKER_LIV:
	         
	        // open datepicker dialog. 
	        // set date pickreaer for current date
	        // add pickerListener listner to date picker
	    	
	        return new DatePickerDialog(this, pickerListener, __year, __month,__day);
	    }
	    return null;
      }
	 private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
		 
	        // when dialog box is closed, below method will be called.
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	             
	        	__year  = selectedYear;
	            __month = selectedMonth;
	            __day   = selectedDay;
	          
	           	
	            	//bDate.setText(Fonctions.getYYYYMMDD_YYYY_MM_DD(__year,__month,__day) );
	           		bDate.setText(Fonctions.getYYYYMMDD_YYYY_MM_DD(__year,__month,__day) );
		          
	           }
	        };
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				//if (m_ProgressDialog!=null) m_ProgressDialog.dismiss();
				super.handleMessage(msg);
				Bundle bGet = msg.getData();

				++cptImpression;
				//Message yes no est-ce que l'impression a bien été faite
				if(cptImpression == listPrintFactures.size()){
					if (m_ProgressDialog!=null) m_ProgressDialog.dismiss();
					Global.dbKDEntCde.setPrintOK(m_numcde, m_codecli);
					if(m_btech==true || m_bresp==true)
					{
						if(m_numcde != null && !m_numcde.equals("")){
							m_EmailApres=etEmailclient.getText().toString().trim();
							if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
							{
								StringBuffer stBuf=new StringBuffer();
								if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
									Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
								
								//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
								
							}
							//else
								//Fonctions.sendPDFToMailApp(context, m_numcde, structcli.EMAIL);
							
						}	
					}
					else
					{
						MessageYesNo(getString(R.string.document_imprime), R.string.document_imprime, getString(R.string.impression));
					}
					
					cptImpression=0;
				}

				//				if (msg.what!=BluetoothConnectionInsecureExample.ERRORMSG_OK)
				//				{
				//
				//					MessageYesNo(BluetoothConnectionInsecureExample.getErrMsg(msg.what)+"\n\nAvez vous un problème bloquant qui vous empèche d'imprimer ?", 433, getString(R.string.probl_me_d_impression));
				//				}
				//				else
				//				{
				//					Global.dbKDEntCde.setPrintOK(m_numcde, m_codecli);
				//					//MessageYesNo("L'Impression s'est elle bien d�roul�e ?", 543, "Impression du document");
				//				}

			}
		};
		return h;
	}
	private void PopulateList(String filter,boolean isPanier,boolean isHabit) {

		ArrayList<dbSiteProduit.structArt> colNames = new ArrayList<dbSiteProduit.structArt>();

		this.m_adapter = new listAdapter(this, R.layout.item_commande2, colNames);

		myListView.setAdapter(this.m_adapter);

		ArrayList<dbSiteProduit.structArt> arts =null;
		//si recherhe filtre
		if (filter.equals("")==false)
		{
			if(isfacturable==true)
			{
				arts=Global.dbProduit.getProduitsWithHisto(
						m_soccode,m_codecli, m_stFam, filter,Fonctions.GetStringDanemEspace(structcli.CODETRF),m_typedoc ,m_btech);
			}
			else
			{
				arts=Global.dbProduit.getProduitsWithHisto(
						m_soccode,m_codecli, "", filter,"",m_typedoc ,m_btech);
			}
		}
		//si panier
		else if (isPanier /*m_stFam.equals(getString(R.string.commande_panier))*/==true)			
			arts=Global.dbProduit.getProduitsCde(
					m_soccode, this.m_numcde);
		//si tout
		else
			if (isHabit /*m_stFam.equals(getString(R.string.commande_tout))*/== true)			
				//	arts=Global.dbProduit.getProduits( m_soccode, "", "" );
				arts=Global.dbProduit.getProduitsWithHisto(
						m_soccode,m_codecli, "", "","",m_typedoc,m_btech);

			else//si famille
				//arts=Global.dbProduit.getProduits( 	m_soccode, m_stFam, "" );
				arts=Global.dbProduit.getProduitsWithHisto(
						m_soccode,m_codecli, m_stFam, "",Fonctions.GetStringDanemEspace(structcli.CODETRF),m_typedoc,m_btech );

		for (int v = 0; v < arts.size(); v++){
			if(arts.get(v) != null) colNames.add(arts.get(v));
		}

		//Trier la liste en ordre décroissant sur la date de dernière vente
		Collections.sort(colNames, dbSiteProduit.Comparators.DATE);

		//on compte le nombre de date différente, on construit autant de dégradé vert qu'il y a de dates
		//ensuite on attribut un dégradé pour chaque date
		int cpt = 0;
		ArrayList<String> dates = new ArrayList<String>();
		for(structArt art : colNames){
			if(!dates.contains(art.MAXDATE)){
				dates.add(art.MAXDATE);
				cpt++;
			}
		}

		//on récupère un tableau de dégradé
		//Couleur de départ 152 251 152
		//Couleur d'arrivée 34 139 34

		//rgb(255,255,204)
		//rgb(0,153,0)
		if(cpt > 0){
			ArrayList<Integer> colors = Fonctions.getGradientColorsList(255,255,204,0,153,0, cpt);

			//on attribut les couleurs aux arts
			int nb = 0;
			String datePrec = "";
			for(structArt art : colNames){
				if(nb == 0 && colors.size() > 0){
					art.BACKGROUND_COLOR = colors.get(nb);
					datePrec = art.MAXDATE;
					nb++;
				}else{
					if(art.MAXDATE != null && !art.MAXDATE.equals(datePrec) && colors.size() > nb){
						datePrec = art.MAXDATE;
						art.BACKGROUND_COLOR = colors.get(nb);
						nb++;
					}else{
						if(nb > 0) art.BACKGROUND_COLOR = colors.get(nb-1);
					}
				}
			}
		}
	}

	private class listAdapter extends ArrayAdapter<dbSiteProduit.structArt> {

		private ArrayList<dbSiteProduit.structArt> items;

		Context mContext;

		public listAdapter(Context context, int textViewResourceId,
				ArrayList<dbSiteProduit.structArt> items) {
			super(context, textViewResourceId, items);
			this.items = items;
			mContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_commande2, null);
			}

			dbSiteProduit.structArt o = items.get(position);

			if (o != null) {

				//RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl_root);
				LinearLayout rl = (LinearLayout) v.findViewById(R.id.rl_root);
				LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll_saisie);
				LinearLayout ll_taxes = (LinearLayout) v.findViewById(R.id.ll_taxes);
				LinearLayout ll_remises = (LinearLayout) v.findViewById(R.id.ll_remises);
				/*LinearLayout ll_taxes_1 = (LinearLayout) v.findViewById(R.id.ll_taxes_1);
				LinearLayout ll_taxes_2 = (LinearLayout) v.findViewById(R.id.ll_taxes_2);
				LinearLayout ll_taxes_3 = (LinearLayout) v.findViewById(R.id.ll_taxes_3);*/

				TextView tvCode = (TextView) v.findViewById(R.id.textCode);
				//				if(!o.PCB.equals("")) tvCode.setText(o.CODART+" - PCB= "+o.PCB);
				//				else tvCode.setText(o.CODART+" - PCB= 1");
				tvCode.setText(o.CODART);

				TextView tvDerDate = (TextView) v.findViewById(R.id.dateder);
				if( o.MAXDATE!=null &&   !o.MAXDATE.equals("")) tvDerDate.setText( "Der.: "+o.MAXDATE );
				else tvDerDate.setText("" );

				TextView tvLbl = (TextView) v.findViewById(R.id.textLbl); 
				tvLbl.setText(o.NOMART1);

				LinearLayout llPMF = (LinearLayout) v
						.findViewById(R.id.llPMF);

				TextView tvPrix = (TextView) v
						.findViewById(R.id.textViewPrixVal);
				TextView tvQte = (TextView) v.findViewById(R.id.textViewQteVal);
				TextView tvTotalHT = (TextView) v
						.findViewById(R.id.textViewTotHTVal);
				TextView tvPMF = (TextView) v.findViewById(R.id.textViewPMFVal);
				TextView tvTxTva = (TextView) v.findViewById(R.id.textViewTxTvaVal);

				TextView tv_taxe1 = (TextView) v.findViewById(R.id.textViewTaxe1);
				TextView tv_taxe2 = (TextView) v.findViewById(R.id.textViewTaxe2);
				TextView tv_taxe3 = (TextView) v.findViewById(R.id.textViewTaxe3);

				TextView tv_remise1 = (TextView) v.findViewById(R.id.textViewRemise1);
				TextView tv_remise2 = (TextView) v.findViewById(R.id.textViewRemise2);
				TextView tv_remise3 = (TextView) v.findViewById(R.id.textViewRemise3);

				ImageView ivAlerte = (ImageView) v.findViewById(R.id.ivAlerte);


				//rl.setBackgroundColor(Color.TRANSPARENT);
				rl.setBackgroundColor(o.BACKGROUND_COLOR);

				rl.setTag(o.CODART);
				dbKD84LinCde.structLinCde lin = new dbKD84LinCde.structLinCde();
				if (m_numcde.equals("")==false &&  Global.dbKDLinCde.load(lin, m_numcde, o.CODART, "0",
						new StringBuffer())) {
					//rl.setBackgroundColor(Color.GREEN);
					ll.setVisibility(View.VISIBLE);
					//tvPrix.setText(Fonctions.GetDoubleToStringFormatDanem(lin.PRIXMODIF,"0.00"));
					tvPrix.setText(Fonctions.GetDoubleToStringFormatDanem(lin.MNTUNITHT,"0.000"));

					String qte=String.valueOf((int)lin.QTECDE);					
					if (lin.QTEGR>0)
					{
						qte+="+"+String.valueOf((int)lin.QTEGR)+" off";
						//	llPMF.setVisibility(View.VISIBLE);
					}
					tvQte.setText(qte);

					//Choix de la pastille de couleur en fonction de la quantité saisi et moyenne
					double qteElement = 0.0;
					double qteMoy = 0.0;

					structHabitude habitude = Global.dbSiteHabitudes.getHabitudeFromCodeArtAndCodeCli(o.CODART, m_codecli);
					try{
						qteElement = Double.parseDouble(qte.replace(",", "."));
						if(habitude != null && habitude.FIELD_QTE_MOY_FAC != null 
								&& !habitude.FIELD_QTE_MOY_FAC.equals("")){
							qteMoy = Double.parseDouble(habitude.FIELD_QTE_MOY_FAC.replace(",", "."));
						}
					}catch(NumberFormatException ex){

					}

					ivAlerte.setVisibility(View.VISIBLE);
					if(qteElement >= qteMoy){
						ivAlerte.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dot_green));
					}else{
						ivAlerte.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dot_orange));
					}

					//tvTotalHT.setText(Global.dbProduit.CalculLine(lin.QTECDE,Fonctions.convertToInt(lin.UV), lin.PRIXMODIF, 0));
					//	tvTotalHT.setText(Global.dbProduit.CalculLine(lin.QTECDE, lin.PRIXMODIF, 0));
					String stt=Fonctions.GetDoubleToStringFormatDanem(lin.MNTTOTALHT,"0.00");
					String st222t=Fonctions.GetDoubleToStringFormatDanem(lin.MNTTOTALHT,"0.0000");

					tvTotalHT.setText(Fonctions.GetDoubleToStringFormatDanem(lin.MNTTOTALHT,"0.00"));

					tvTxTva.setText(Fonctions.GetDoubleToStringFormatDanem(lin.TAUX,"0.000")+"%");

					// PRIX MOYEN FACTUR�
					float pmf = CalcPrixMoyenAFacturer(lin.QTECDE,
							Fonctions.convertToInt(lin.UV), lin.QTEGR,
							Fonctions.convertToFloat(tvTotalHT.getText()
									.toString()));
					tvPMF.setText(String.valueOf(pmf));

					//TODO PRIX MOYEN FACTURE sans pcb
					if (lin.REMISEMODIF>0)
					{
						tv_remise1.setText("Remise: "+lin.REMISEMODIF+"%");
						tv_remise1.setVisibility(View.VISIBLE);
						ll_remises.setVisibility(View.VISIBLE);
					}
					else
					{
						tv_remise1.setVisibility(View.INVISIBLE);
						ll_remises.setVisibility(View.GONE);
					}
				} else{
					//ll.setVisibility(View.GONE);
					ll.setVisibility(View.INVISIBLE);
					ll_taxes.setVisibility(View.GONE);
					ll_remises.setVisibility(View.GONE);
					if(isValider) {
						ivAlerte.setVisibility(View.VISIBLE);
						ivAlerte.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dot_red));
					}else{
						ivAlerte.setVisibility(View.INVISIBLE);
					}
					cptArtNonFacture++;
				}
			}
			return v;
		}

	}

	float CalcPrixMoyenAFacturer(float qte, int pcb, float qtegr, float mntLinHt) {
		float pmf = 0;
		try {
			pmf = (float) Fonctions.round(mntLinHt / (qte * pcb + qtegr * pcb),
					3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pmf;
	}

	void launchQtePrix(String codeart) {
		dbSiteProduit.structArt art = new dbSiteProduit.structArt();
		if (Global.dbProduit.getProduit(codeart, art, new StringBuilder())
				&& Global.dbKDEntCde.isPrintOk(m_numcde) == false) {

			Bundle b = new Bundle();
			b.putString("codeart", codeart);
			b.putString("lblart", art.NOMART1);
			//	b.putString("prix", art.PV_CONS);
			b.putString("qte", Fonctions.GetStringDanem(art.PCB));
			//	b.putString("grat", "");
			b.putString("numcde", m_numcde);
			b.putString("photoname", art.PHOTONAME);

			b.putString("codcli", m_codecli);
			b.putString("grpcli", structcli.GROUPECLIENT);
			b.putString("codtrf", Fonctions.GetStringDanem(structcli.CODETRF));
			b.putString("typedoc",m_typedoc);

			Log.e("Bundle",""+b);

			//tof: gestion TVA en fonction de l'article
			m_dTVA =  (float) Fonctions.GetStringToDoubleDanem(Global.dbParam
					.getLblAllSoc(Global.dbParam.PARAM_TVA, art.CODETVA));

			/*if(!Global.CODCLI_TEMP.equals("")){
				String taxe = Global.dbTarif.getTaxeOfTarif(Global.CODCLI_TEMP, art.CODART);
				m_dTVA = Float.parseFloat(taxe);
			}*/

			Intent intent = new Intent(commandeActivity.this,
					commandeInput.class); intent.putExtras(b);
					startActivityForResult(intent, LAUNCH_SAISIEQTE);


		}
	}

	//Variables utilisées pour le robot
	int iIdxArtRobot = 0 ;
	List<String> arrListCodeArt = null ;
	void launchQtePrixRobot() {

		if ( iIdxArtRobot >= arrListCodeArt.size() )
		return ;	//tout le tableau a été parcouru

		String codeart = arrListCodeArt.get(iIdxArtRobot) ;
		iIdxArtRobot ++ ;
		String Qte = "10" ;
		dbSiteProduit.structArt art = new dbSiteProduit.structArt();
		if (Global.dbProduit.getProduit(codeart, art, new StringBuilder())
				&& Global.dbKDEntCde.isPrintOk(m_numcde) == false) {

			Bundle b = new Bundle();
			b.putString("codeart", codeart);
			b.putString("lblart", art.NOMART1);
			//	b.putString("prix", art.PV_CONS);
			b.putString("qte", Fonctions.GetStringDanem(art.PCB));
			//	b.putString("grat", "");
			b.putString("numcde", m_numcde);
			b.putString("photoname", art.PHOTONAME);

			b.putString("codcli", m_codecli);
			b.putString("grpcli", structcli.GROUPECLIENT);
			b.putString("codtrf", Fonctions.GetStringDanem(structcli.CODETRF));
			b.putString("typedoc",m_typedoc);
			b.putString("qterobot",Qte);

			Log.e("Bundle",""+b);

			//tof: gestion TVA en fonction de l'article
			m_dTVA =  (float) Fonctions.GetStringToDoubleDanem(Global.dbParam
					.getLblAllSoc(Global.dbParam.PARAM_TVA, art.CODETVA));

			/*if(!Global.CODCLI_TEMP.equals("")){
				String taxe = Global.dbTarif.getTaxeOfTarif(Global.CODCLI_TEMP, art.CODART);
				m_dTVA = Float.parseFloat(taxe);
			}*/

			Intent intent = new Intent(commandeActivity.this,
					commandeInput.class); intent.putExtras(b);
			startActivityForResult(intent, LAUNCH_SAISIEQTEROBOT);


		}
	}
	private final String ROBOT_SCAN="ROBOT_SCAN";
	Handler hRobot = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			long progress=msg.getData().getLong(ROBOT_SCAN);

			//simulation de l'appuie d'une touche pour lancer le scan
			//if ( Global.GL_bRobotMode == true )
			{
				launchQtePrixRobot();
			}

		}
	};


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode==LAUNCH_DANEMBR && resultCode==Activity.RESULT_OK)
		{
			String  cb=getBundleValue(data.getExtras(),"cb");
			String[] lines = cb.split("/idstation/");
			if (lines.length > 0 )
			{
				for (int i=0 ; i< lines.length ; i++)
				{
					String[] fld = lines[i].split("/idstation/");
					//if(Fonctions.GetStringDanem(fld[i]).equals("idstation"))
					if ( i==1)
					{
						try {
							String lbl=Fonctions.GetStringDanem(fld[0]);
							cb=lbl;
						} catch (Exception e) {
							e.printStackTrace();
							cb="";
						}
					}

				}
			}
			if(!Fonctions.GetStringDanem(cb).equals(""))
			{
				String stcodeart=Global.dbProduit.getCodearticle_EAN(cb);

				launchQtePrix(stcodeart);
			}
			else
				Fonctions.FurtiveMessageBox(this,"Article non trouvé.");



		}
		else
		{
			if(requestCode == 10000){
				String zplData = "";
				if(data != null){
					Bundle bundle = data.getExtras();
					zplData = bundle.getString("zplData");
				}
				Log.i("tag", "zplData "+zplData);
				launchPrintPreview(zplData, m_numcde, m_btech,m_bresp,false,false);
			}
			if ( requestCode==LAUNCH_PRINTPREVIEW)
			{
				if (resultCode == Activity.RESULT_OK )
				{
					if(m_btech==true)
						Fonctions.FurtiveMessageBox(this, "Votre BL a bien été validé et envoyé");
					if(m_bresp==true)
						Fonctions.FurtiveMessageBox(this, "Votre Commande a bien été validée et envoyé");


					m_ProgressDialog=ProgressDialog.show(this, getString(R.string.communication_avec_l_imprimante), getString(R.string.patientez_));

					final String mac=getPrinterMacAddress();
					final BluetoothConnectionInsecureExample example = new BluetoothConnectionInsecureExample(hPrintResult);
					final Z420ModelFacture z=new Z420ModelFacture(this, m_typedoc, rep.LOGIN);

					//on récupère la liste découpée normal + gratuit
					//ArrayList<ArrayList<structLinCde>> list = z.getListeProduit(m_numcde, false);

					Thread run = new Thread(new Runnable() {

						@Override
						public void run() {
							for(int i = 0;i<listPrintFactures.size();i++){
								//on passe dans la méthode la liste à imprimer puis numéro de la page puis nombre de pages
								String   zplData=z.getFacture(m_numcde,m_codecli,false,m_typedoc, listPrintFactures.get(i), i+1, listPrintFactures.size(),m_contactnom);

								example.sendZplOverBluetooth(mac,zplData);

								try {
									Thread.sleep(15000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

							}

							m_ProgressDialog.dismiss();
						}
					});
					run.start();


				/*for(int i = 0;i<listPrintFactures.size();i++){
					final int index = i;
					Log.i("tag", "Index test : "+index);
					if(index == 0){w
						//on passe dans la méthode la liste à imprimer puis numéro de la page puis nombre de pages
						String   zplData=z.getFacture(m_numcde,m_codecli,false,m_typedoc, listPrintFactures.get(i), i+1, listPrintFactures.size());

						example.sendZplOverBluetooth(mac,zplData);
					}else{
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								//on passe dans la méthode la liste à imprimer puis numéro de la page puis nombre de pages
								String   zplData=z.getFacture(m_numcde,m_codecli,false,m_typedoc, listPrintFactures.get(index), index+1, listPrintFactures.size());
								Log.i("tag", "Index impression : "+index);
								example.sendZplOverBluetooth(mac,zplData);
							}
						}, 10000);
					}
				}*/

				/*for(int i = 0;i<listPrintFactures.size();i++){
					//on passe dans la méthode la liste à imprimer puis numéro de la page puis nombre de pages
					String   zplData=z.getFacture(m_numcde,m_codecli,false,m_typedoc, listPrintFactures.get(i), i+1, listPrintFactures.size());

					example.sendZplOverBluetooth(mac,zplData);

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/

				}
			}

			if (requestCode==LAUNCH_COMMENTAIRE)
			{
				if (resultCode==RESULT_OK)
				{
					commentaire=data.getExtras().getString("newvalue");
					Global.dbKDEntCde.saveComment(m_numcde, commentaire);
				}
			}
			if (requestCode==LAUNCH_REFCDECLIENT)
			{
				if (resultCode==RESULT_OK)
				{
					refcdeclient=data.getExtras().getString("newvalue");
					Global.dbKDEntCde.saverefcdeclientComment(m_numcde, refcdeclient);
				}
			}

			if (requestCode==LAUNCH_HISTO)
			{
				PopulateList("",true,false);
				CalcAll();
			}
			//test
			if (requestCode == ACTIVITY_PRINT) {

				switch (resultCode) {
					case Activity.RESULT_CANCELED:
						Log.d("TAG", "onActivityResult, resultCode = CANCELED");
						break;
					case Activity.RESULT_FIRST_USER:
						Log.d("TAG", "onActivityResult, resultCode = FIRST_USER");
						break;
					case Activity.RESULT_OK:
						Log.d("TAG", "onActivityResult, resultCode = OK");
						break;
				}
			}

			if (requestCode == LAUNCH_SAISIEQTE || requestCode == LAUNCH_SAISIEQTEROBOT) {
				if (m_codecli.equals("")==false && resultCode == Activity.RESULT_OK ) {

					Bundle b = data.getExtras();
					Global.CODCLI_TEMP = m_codecli;

					saveLin(getLogin(), m_soccode, m_codecli, m_numcde, getBundleValue(b, "codeart"), getBundleValue(b, "lblart"),
							getBundleValue(b, "prix") , getBundleValue(b, "qte"),
							getBundleValue(b, "grat"), getBundleValue(b, "remise"), getBundleValue(b, "prixintit"), m_dTVA,m_typedoc,
							getBundleValue(b, "prixTotal"), getBundleValue(b, "prixUnitaire"));

					//Bug N° BC vide si app s'arrête quand on arrive d'un rapport
					// on enregistre le N° BC dans le rapport
					if (!m_numrapport.equals("")){
						Global.dbKDClientVu.save4_rapport(m_numrapport,m_numcde);
					}

					if(isfacturable==false)
					{
						if(m_btech==true)
						{
							PopulateList("",true,false);
						}
						else
							PopulateList("",false,true);
					}
					else
						PopulateList("",false,false);
					CalcAll();
					myListView.setSelectionFromTop(index, top);

					//on remet à 0 le compteur des produits non facturés
					cptArtNonFacture = 0;
				}
				if ( requestCode == LAUNCH_SAISIEQTEROBOT )
				{
					//Relance auto
					Bundle messageBundle=new Bundle();
					Message myMessage;
					myMessage=hRobot.obtainMessage();

					messageBundle.putLong(ROBOT_SCAN, 1);
					myMessage.setData(messageBundle);

					hRobot.sendMessageDelayed(myMessage, GlobalGL_lRobotInterval);
				}
			}

			if (requestCode==LAUNCH_REGLEMENT){

				//on récupère code cde + code cli
				//			if(data != null){
				//				Bundle bundle = data.getExtras();
				//				if(bundle != null){
				//					m_numcde = bundle.getString(Espresso.NUM_FACTURE);
				//					m_codecli = bundle.getString(Espresso.CODE_CLIENT);
				//				}
				//			}

				//on bloque tous les éléments de la page (document non modifiable)
				isComeFromReglement = true;
				PopulateList("",true,false);
				CalcAll();
			}
		}


	}

	/**
	 * Désactive tous les boutons de la page
	 */
	private void DisableAllButtons() {
		//on désactive la liste
		myListView.setOnItemClickListener(null);

		//on bloque valider

		//on bloque annuler

	}

	public static void saveLin(String rep,String m_soccode,String m_codecli,
			String m_numcde,String codeart,String lblart ,
			String pubrut, String qte,String grat,String remise,String prixinit,float tva,String typedoc, 
			String prixTotal, String prixUnitaire){
		dbKD84LinCde.structLinCde lin = new dbKD84LinCde.structLinCde();

		lin.TAXE1 = 0.0f;
		lin.TAXE2 = 0.0f;
		lin.TAXE3 = 0.0f;

		lin.SOCCODE=m_soccode;
		lin.CLICODE=m_codecli;
		lin.CDECODE = m_numcde;

		lin.DESIGNATION = lblart;
		lin.PROCODE = codeart;
		lin.PRIXMODIF = Fonctions.convertToFloat( pubrut);
		lin.QTECDE = Fonctions.convertToFloat(qte);
		lin.QTEGR = Fonctions.convertToFloat(grat);
		lin.REMISEMODIF=Fonctions.convertToFloat(remise);
		lin.DATE=Fonctions.getYYYYMMDDhhmmss();
		//lin.MNTUNITHT = lin.PRIXMODIF;
		lin.MNTUNITHT = Fonctions.GetStringToFloatDanem(prixUnitaire);
		lin.TYPEPIECE=typedoc;
		lin.REPCODE =rep;
		lin.TAUX =      tva;
		lin.PRIXINITIAL =  prixinit;;

		//calcul du prix net
		float fRemCalc =  lin.PRIXMODIF-(float)Fonctions.round((lin.PRIXMODIF* (lin.REMISEMODIF) / 100 ),3);	
		//lin.MNTUNITNETHT = fRemCalc;
		lin.MNTUNITNETHT = Fonctions.GetStringToFloatDanem(prixUnitaire);

		dbSiteProduit.structArt art = new dbSiteProduit.structArt();
		if (Global.dbProduit.getProduit(lin.PROCODE, art,
				new StringBuilder())) {
			lin.UV = art.UNVENTE.trim();
			lin.CODABAR = Fonctions.GetStringDanem(art.EAN).trim();
			//TODO calcul du montant du produit
			if(m_clientTVA.equals(TableClient.TVA_FISCAL_NEUF) || m_clientTVA.equals(TableClient.TVA_FISCAL_SIX))
			{
				lin.MNTTOTALHT = Fonctions.GetStringToFloatDanem(prixTotal);
				lin.CODETVA=art.CODETVA;
				lin.MNTTVA=(float) Fonctions.round( ((lin.MNTTOTALHT*tva)/100),2);

				lin.MNTTOTALTTC= Fonctions.GetStringToFloatDanem(prixTotal) ;
			}
			else
			{
				lin.MNTTOTALHT = Fonctions.GetStringToFloatDanem(prixTotal);
				lin.CODETVA=art.CODETVA;
				lin.MNTTVA=(float) Fonctions.round( ((lin.MNTTOTALHT*tva)/100),2);

				lin.MNTTOTALTTC= lin.MNTTOTALHT+lin.MNTTVA ;
			}
			if (lin.QTECDE == 0 && lin.QTEGR==0) {
				Global.dbKDLinCde.delete(m_numcde, lin.PROCODE, "0",
						new StringBuffer(), "");
			} else {
				if ( Global.dbKDLinCde.save(lin, m_numcde, lin.PROCODE,
						"0", new StringBuffer())) {

				} else {

				}
			}

		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			/*
			 * if( checkAll()==true) { if (saveCde()==true) finish(); }
			 */
			if (m_numcde.equals(""))
				finish();
			return false;
		}

		else
			return super.onKeyDown(keyCode, event);

	}

	boolean checkAll() {
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (m_numcde.equals("")==false)
		{
			//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) && m_bresp==true)
			if( m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA)|| m_typedoc.equals(TableSouches.TYPEDOC_RELEVELINEAIRE) )
			{
				
			}
			else
			addMenu(menu, R.string.commande_print, R.drawable.print_icon);
			
			if(m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
			{
				addMenu(menu, R.string.commande_pause, android.R.drawable.ic_input_get);
			}
			addMenu(menu, R.string.commande_valider, android.R.drawable.ic_menu_add);

			addMenu(menu, R.string.commande_annuler,
					android.R.drawable.ic_menu_close_clear_cancel);

			//addMenu(menu, R.string.commande_fichecli,
			//R.drawable.action_select_client);
			//		addMenu(menu, R.string.commande_quest,
			//				android.R.drawable.ic_menu_help);
			if(isfacturable==false)
			addMenu(menu, R.string.commande_Historique,R.drawable.action_select_client);
			/*	if(m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
			{

				addMenu(menu, R.string.commande_transmettre, android.R.drawable.ic_dialog_dialer);

            }*/

			addMenu(menu, R.string.comment,
					android.R.drawable.ic_menu_info_details);
			if(m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
			{

			}
			else
			{
				addMenu(menu, R.string.refcdeclient,
						android.R.drawable.ic_menu_info_details);
			}
			//			addMenu(menu, R.string.commande_Cadencier,
			//				R.drawable.action_select_client);
			//	addMenu(menu, R.string.commande_reglement,
			//			android.R.drawable.ic_menu_help);
		}
		else
		{
			addMenu(menu, R.string.commande_quitter,
					android.R.drawable.ic_menu_close_clear_cancel);
		}
		addMenu(menu, R.string.Scanner,-1);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		dbKD83EntCde ent=new dbKD83EntCde(m_appState.m_db);
		boolean valide = ent.isFactureValide(m_numcde);

		switch (item.getItemId()) {

		case R.string.commande_print:

			//Si document non validé alors alert il faut valider le document
			if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			{
				launchPrinting(false);
			}
			else
			{
				if(valide){
					//on peut imprimer ou on envoie vers reglement
					launchPrinting(false);
				}else{
					//alert il faut valider le document
					AlertMessage(getString(R.string.facture_validation_document));
				}
			}

			return true;

			//			if(cptArtNonFacture > 0 && (!Global.dbKDEntCde.isPrintOk(m_numcde) && m_problemPrinter==false)){
			//				isValider = true;
			//				m_adapter.notifyDataSetChanged();
			//				MessageYesNo(getString(R.string.message_yesno_imprimer_facture),
			//						R.string.message_yesno_imprimer_facture,"Validation");
			//				return true;
			//			}else{
			//
			//				if (Global.dbKDLinCde.Count_Numcde(m_numcde, false)==0)
			//				{
			//					AlertMessage(getString(R.string.commande_noLines));
			//					return true;
			//				}
			//				saveCde(false);
			//				MessageYesNo(RecapDocument(),
			//						R.string.commande_print,"Résumé");
			//				// saveCde();
			//				// launchPrinting(item.getItemId());
			//				return true;
			//			}
		case R.string.commande_transmettre:
			if(  (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE)==false ||  isComeFromReglement) && valide && Global.dbKDEntCde.isPrintOk(m_numcde)){
				saveCde(true,false);
				task_sendWSData wsCde = new task_sendWSData(m_appState,null, null);
				wsCde.setDB(m_appState.m_db);
				wsCde.execute();
				isComeFromReglement = false;
				finish();
				return true;
			}
			else{
				//alert il faut valider le document
				AlertMessage(getString(R.string.facture_validation_document_transmettre));
			}
			return true;

		case R.string.commande_valider:

			//String date1 = bDate.getText().toString();

			//Log.e("date1",""+bDate.getText().toString());
			String interneStDateCde = bDate.getText().toString();
			String styear=Fonctions.Right(interneStDateCde, 4);
			String stmonth=Fonctions.Mid(interneStDateCde, 3,2);
			String stday=Fonctions.Left(interneStDateCde, 2);

			String date2 = Fonctions.getDD_MM_YYYY();
			String styear2=Fonctions.Right(date2, 4);
			String stmonth2=Fonctions.Mid(date2, 3,2);
			String stday2=Fonctions.Left(date2, 2);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //Pour déclarer les valeurs dans les nouveaux objets Date, employez le même format de date que pour créer des dates

			Date date1 = null;
			try {
				date1 = sdf.parse(styear+"-"+stmonth+"-"+stday);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Date dateCurrent = null;
			try {
				dateCurrent = sdf.parse(styear2+"-"+stmonth2+"-"+stday2);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Log.e("date1",""+date1);
			Log.e("date1 compare",""+date1.compareTo(dateCurrent));


			/*if (m_typedoc.equals(TableSouches.TYPEDOC_BL) || m_typedoc.equals(TableSouches.TYPEDOC_FACTURE)) {
				if(m_dTotalHT==0)
				{
					String message ="Pièce de vente à 0 euro. Vente impossible.";
					AlertMessage(message);
					return false;
				}

			}*/

			if (m_bresp || m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) ){
				if (date1.compareTo(dateCurrent ) == -1) {
					// traitement du cas date1 < date2 ou date1 = date2
					String message ="La date de livraison choisie est inférieure à la date du jour";
					AlertMessage(message);
					return false;
				}
			}

			if ( m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			{
				Spinner mySpinner = (Spinner) findViewById(R.id.spinnerLivraison);
			String text = mySpinner.getSelectedItem().toString();

			if (text.equals("---"))  {
				String stmessageLivraison = "Vous devez sélectionner un mode de livraison !";
				AlertMessage(stmessageLivraison);
				return false;
			}
		}

			if (m_typedoc.equals(TableSouches.TYPEDOC_BL) || m_typedoc.equals(TableSouches.TYPEDOC_FACTURE)) {

				TableClient client = new TableClient(getDB());
				if (client.getClientIsEtsPublic(m_codecli)) //si etablissement public on verifie si on a une ref cde
				{
					Log.e("isPublic","isPublic");
					structEntCde sentref = new structEntCde();
					if (ent.load(sentref, m_numcde, new StringBuffer(), false)) {
						Log.e("RefCDE",""+sentref.REFCDE);
						if (sentref.REFCDE.equals("")) {
							String stmessageEtsPublic = "Vous devez saisir une réf.Cde client pour cet établissement";
							AlertMessage(stmessageEtsPublic);
							return false;
						}
					}
				}

			}


			if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA))
			{
				saveCde(true,true);
				task_sendWSData wsCde = new task_sendWSData(m_appState,null, null);
				wsCde.setDB(m_appState.m_db);
				wsCde.execute();
				isComeFromReglement = false;
				finish();
				return true;
			}

			if (m_typedoc.equals(TableSouches.TYPEDOC_RELEVELINEAIRE))
			{
				saveCde(true,true);
				task_sendWSData wsCde = new task_sendWSData(m_appState,null, null);
				wsCde.setDB(m_appState.m_db);
				wsCde.execute();
				isComeFromReglement = false;
				finish();
				return true;
			}
			//On affiche dans un premier temps les pastilles rouges
			//Dans un deuxieme temps on affiche le message des pastilles rouges
			//si oui on envoie vers reglement, la facture est valider et devient non modifiable

			//si l'on ne vient pas de reglement
			//alors on envoie vers reglement
			//			if(!isComeFromReglement){
			//				MessageYesNo(getString(R.string.goto_reglement), R.string.goto_reglement, getString(R.string.commande_reglement));
			//				return true;
			//			}

			//Si l'on revient de reglement et que la facture est validée alors on sort
			//on lance la task également

			if(((m_typedoc.equals(TableSouches.TYPEDOC_FACTURE)==false ||  isComeFromReglement) && valide && Global.dbKDEntCde.isPrintOk(m_numcde))|| 
					(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) && valide==true ) ){
				
				//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
				if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE)  &&  m_bresp==true)
				{
					String stmessageMail ="" ;
					if(Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim().equals(""))
					{
						stmessageMail ="Veuillez renseigner un Email";
						AlertMessage(stmessageMail);
					}
					else
					{

						m_EmailApres=etEmailclient.getText().toString().trim();
						if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
						{
							StringBuffer stBuf=new StringBuffer();
							if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
								Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
							//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
						}
						
						saveCde(true,false);
						task_sendWSData wsCde = new task_sendWSData(m_appState,null, null);
						wsCde.setDB(m_appState.m_db);
						wsCde.execute();
						isComeFromReglement = false;

						if(m_bVendeur==true)
						{
							AppelRapportVendeur_Ref(false);
						}
						
						finish();
						return true;
					}
							
							
				}
				else
				{
					saveCde(true,false);
					task_sendWSData wsCde = new task_sendWSData(m_appState,null, null);
					wsCde.setDB(m_appState.m_db);
					wsCde.execute();
					isComeFromReglement = false;

					if(m_bVendeur==true)
					{
						AppelRapportVendeur_Ref(false);
					}
					
					finish();
					return true;
				}
				
			}

			//Si la facture est deja validé alors alert deja validé
			if(!valide){

				if(m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
				{
					{

						ibHabit.performClick();
						if (Global.dbKDLinCde.Count_Numcde(m_numcde, false)==0)
						{
							AlertMessage(getString(R.string.commande_noLines));
							return true;
						}
						
						if(cptArtNonFacture > 0){
							isValider = true;
							m_adapter.notifyDataSetChanged();
							if(m_dTotalHT<m_Cli_Ca_moy_fac && !m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE) && m_btech==false && m_bresp==false)
							{
								String stmessage="";

								stmessage="Attention le montant HT de votre facture ("+Fonctions.GetDoubleToStringFormatDanem(m_dTotalHT, "0.00")+" euros) est inférieur au panier chez ce client ("+Fonctions.GetStringDanem(structcli.CA_MOY_FAC)+" euros). Souhaitez vous terminer votre facture ? Non/Oui ";
								MessageYesNo(stmessage,
										R.string.message_yesno_valider_facture,"Validation");
								//Souhaitez vous terminer votre facture bien que certains produits n\'aient pas été vendus ?
							}
							else
							{
								
								saveCde(false,true);


								dbKD83EntCde ent1=new dbKD83EntCde(m_appState.m_db);
								//On change l'état de la facture pour le passer à 1 (facture validée)
								ent1.setValidationState(m_numcde, m_codecli, "1");

								//on transfert la facture dans les factures dues
								if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE) || m_typedoc.equals(TableSouches.TYPEDOC_AVOIR)){	
									structEntCde structent=new structEntCde();
									ent1.load(structent, m_numcde, new StringBuffer(),false);
									dbKD730FacturesDues dues=new dbKD730FacturesDues(m_appState.m_db);
									dues.insert(structent);
								}

								DecrementeStock();
								// on envoi la facture tt de suite
								//					task_sendWSData wsCde = new task_sendWSData(
								//							m_appState,null);
								//					wsCde.execute();

								setResult(Activity.RESULT_OK, null);

								//On réinitialise les valeurs des éléments non facturés
								//					isValider = false;
								//					cptArtNonFacture = 0;

								if(!isComeFromReglement){
									//MessageYesNo(getString(R.string.goto_reglement), R.string.goto_reglement, getString(R.string.commande_reglement));
									if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE))
										launchReglement();
								}
							}

						}else{
							//MessageYesNo(getString(R.string.message_yesno_valider_facture),
							//		R.string.message_yesno_valider_facture,"Validation");
							if(m_dTotalHT<m_Cli_Ca_moy_fac && !m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE) && m_btech==false && m_bresp==false)
							{
								String stmessage="";

								stmessage="Attention le montant HT de votre facture ("+Fonctions.GetDoubleToStringFormatDanem(m_dTotalHT, "0.00")+" euros) est inférieur au panier chez ce client ("+Fonctions.GetStringDanem(structcli.CA_MOY_FAC)+" euros). Souhaitez vous terminer votre facture ? Non/Oui ";
								MessageYesNo(stmessage,
										R.string.message_yesno_valider_facture,"Validation");
								//Souhaitez vous terminer votre facture bien que certains produits n\'aient pas été vendus ?
							}
							else
							{
								saveCde(false,true);

								dbKD83EntCde ent1=new dbKD83EntCde(m_appState.m_db);
								//On change l'état de la facture pour le passer à 1 (facture validée)
								ent1.setValidationState(m_numcde, m_codecli, "1");

								//on transfert la facture dans les factures dues
								if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE) || m_typedoc.equals(TableSouches.TYPEDOC_AVOIR)){	
									structEntCde structent=new structEntCde();
									ent1.load(structent, m_numcde, new StringBuffer(),false);
									dbKD730FacturesDues dues=new dbKD730FacturesDues(m_appState.m_db);
									dues.insert(structent);
								}

								DecrementeStock();
								// on envoi la facture tt de suite
								//					task_sendWSData wsCde = new task_sendWSData(
								//							m_appState,null);
								//					wsCde.execute();

								setResult(Activity.RESULT_OK, null);

								//On réinitialise les valeurs des éléments non facturés
								//					isValider = false;
								//					cptArtNonFacture = 0;

								if(!isComeFromReglement){
									//MessageYesNo(getString(R.string.goto_reglement), R.string.goto_reglement, getString(R.string.commande_reglement));
									if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE))
										launchReglement();
								}
							}

						}
					}

				}
				else
				{
					if(m_btech==true || m_bresp==true)
					{
						
						isValider = true;
						m_adapter.notifyDataSetChanged();
						isFirstClickValider = false;
						
					}
					if(isFirstClickValider){
						ibHabit.performClick();
						if(m_bVendeur==true)
						{
							AppelRapportVendeur_Ref(true);
						}
					}else{
						if (Global.dbKDLinCde.Count_Numcde(m_numcde, false)==0)
						{
							AlertMessage(getString(R.string.commande_noLines));
							return true;
						}
						
						if(cptArtNonFacture > 0){
							isValider = true;
							m_adapter.notifyDataSetChanged();
							if(m_dTotalHT<m_Cli_Ca_moy_fac && !m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE) && m_btech==false && m_bresp==false)
							{
								String stmessage="";

								stmessage="Attention le montant HT de votre facture ("+Fonctions.GetDoubleToStringFormatDanem(m_dTotalHT, "0.00")+" euros) est inférieur au panier chez ce client ("+Fonctions.GetStringDanem(structcli.CA_MOY_FAC)+" euros). Souhaitez vous terminer votre facture ? Non/Oui ";
								MessageYesNo(stmessage,
										R.string.message_yesno_valider_facture,"Validation");
								//Souhaitez vous terminer votre facture bien que certains produits n\'aient pas été vendus ?

							}
							else
							{
								//if(m_dTotalHT<m_cde_depassement && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
								/*if(m_dTotalHT<m_cde_depassement && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )

								{
									String stmessage="";

									stmessage="Attention le montant HT de votre commande ("+Fonctions.GetDoubleToStringFormatDanem(m_dTotalHT, "0.00")+" euros) est inférieur au panier chez ce client ("+Fonctions.GetDoubleToStringFormatDanem(m_cde_depassement,"0.00")+" euros). La commande ne peut pas être validée. ";
									stmessage="Vous n\'avez pas atteint le montant minimum requis de "+Fonctions.GetDoubleToStringFormatDanem(m_cde_depassement, "0.00")+" euros HT pour cette opération. Continuez votre commande ou annulez";
										//AlertMessage(stmessage);
									MessageYesNo3(stmessage,
												R.string.message_yesno_valider_bc,"Validation");
										
								}
								else
								{*/
									//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
									if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )
									{
										String stmessageMail ="" ;
										if(Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim().equals(""))
										{
											stmessageMail ="Veuillez renseigner un Email";
											AlertMessage(stmessageMail);
										}
										else
										{
											m_EmailApres=etEmailclient.getText().toString().trim();
											if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
											{
												StringBuffer stBuf=new StringBuffer();
												if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
													Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
												//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
											}
											stmessageMail ="Une copie de cette commande sera envoyée au client par Mail à l\'adresse suivante : "+Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim();
											AlertMessage(stmessageMail);
										}
												
									}


									saveCde(false,true);

									dbKD83EntCde ent1=new dbKD83EntCde(m_appState.m_db);
									//On change l'état de la facture pour le passer à 1 (facture validée)
									ent1.setValidationState(m_numcde, m_codecli, "1");

									//on transfert la facture dans les factures dues
									if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE) || m_typedoc.equals(TableSouches.TYPEDOC_AVOIR)){	
										structEntCde structent=new structEntCde();
										ent1.load(structent, m_numcde, new StringBuffer(),false);
										dbKD730FacturesDues dues=new dbKD730FacturesDues(m_appState.m_db);
										dues.insert(structent);
									}

									DecrementeStock();
									// on envoi la facture tt de suite
									//					task_sendWSData wsCde = new task_sendWSData(
									//							m_appState,null);
									//					wsCde.execute();

									setResult(Activity.RESULT_OK, null);

									//On réinitialise les valeurs des éléments non facturés
									//					isValider = false;
									//					cptArtNonFacture = 0;

									if(!isComeFromReglement){
										//MessageYesNo(getString(R.string.goto_reglement), R.string.goto_reglement, getString(R.string.commande_reglement));
										if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE))
											launchReglement();
									}
								//}
							
							}


						}else{
							//MessageYesNo(getString(R.string.message_yesno_valider_facture),
							//		R.string.message_yesno_valider_facture,"Validation");
							if(m_dTotalHT<m_Cli_Ca_moy_fac && !m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE) && m_btech==false && m_bresp==false)
							{
								String stmessage="";

								stmessage="Attention le montant HT de votre facture ("+Fonctions.GetDoubleToStringFormatDanem(m_dTotalHT, "0.00")+" euros) est inférieur au panier chez ce client ("+Fonctions.GetStringDanem(structcli.CA_MOY_FAC)+" euros). Souhaitez vous terminer votre facture ? Non/Oui ";
								MessageYesNo(stmessage,
										R.string.message_yesno_valider_facture,"Validation");
								//Souhaitez vous terminer votre facture bien que certains produits n\'aient pas été vendus ?

							}
							else
							{
								//if(m_dTotalHT<m_cde_depassement && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
								/*if(m_dTotalHT<m_cde_depassement && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )
								{
									String stmessage="";

									stmessage="Attention le montant HT de votre commande ("+Fonctions.GetDoubleToStringFormatDanem(m_dTotalHT, "0.00")+" euros) est inférieur au panier chez ce client ("+Fonctions.GetDoubleToStringFormatDanem(m_cde_depassement,"0.00")+" euros). La commande ne peut pas être validée. ";
                                    stmessage="Vous n\'avez pas atteint le montant minimum requis de "+Fonctions.GetDoubleToStringFormatDanem(m_cde_depassement, "0.00")+" euros HT pour cette opération. Continuez votre commande ou annulez";
									//AlertMessage(stmessage);
                                    MessageYesNo3(stmessage,
											R.string.message_yesno_valider_bc,"Validation");

								}
								else
								{*/
									//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
									if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )
									{
										String stmessageMail ="" ;
										if(Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim().equals(""))
										{
											stmessageMail ="Veuillez renseigner un Email";
											AlertMessage(stmessageMail);
										}
										else
										{
											m_EmailApres=etEmailclient.getText().toString().trim();
											if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
											{
												StringBuffer stBuf=new StringBuffer();
												if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
													Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
												//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
											}
											stmessageMail ="Une copie de cette commande sera envoyée au client par Mail à l\'adresse suivante : "+Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim();
											AlertMessage(stmessageMail);
										}
												
												
									}
									
									saveCde(false,true);

									dbKD83EntCde ent1=new dbKD83EntCde(m_appState.m_db);
									//On change l'état de la facture pour le passer à 1 (facture validée)
									ent1.setValidationState(m_numcde, m_codecli, "1");

									//on transfert la facture dans les factures dues
									if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE) || m_typedoc.equals(TableSouches.TYPEDOC_AVOIR)){	
										structEntCde structent=new structEntCde();
										ent1.load(structent, m_numcde, new StringBuffer(),false);
										dbKD730FacturesDues dues=new dbKD730FacturesDues(m_appState.m_db);
										dues.insert(structent);
									}

									DecrementeStock();
									// on envoi la facture tt de suite
									//					task_sendWSData wsCde = new task_sendWSData(
									//							m_appState,null);
									//					wsCde.execute();

									setResult(Activity.RESULT_OK, null);

									//On réinitialise les valeurs des éléments non facturés
									//					isValider = false;
									//					cptArtNonFacture = 0;

									if(!isComeFromReglement){
										//MessageYesNo(getString(R.string.goto_reglement), R.string.goto_reglement, getString(R.string.commande_reglement));
										if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE))
											launchReglement();
									}
									
								//}
								
								
							}

						}
					}

				}


			}else if(isComeFromReglement){
				//AlertMessage(getString(R.string.commande_deja_valide));	
				//MessageYesNo(getString(R.string.commande_deja_valide)+" "+getString(R.string.document_imprime), R.string.document_imprime, getString(R.string.impression));
				//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
				if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )
				{
					String stmessageMail ="" ;
					if(Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim().equals(""))
					{
						stmessageMail ="Veuillez renseigner un Email";
						AlertMessage(stmessageMail);
					}
					else
						AlertMessage(getString(R.string.document_valide));
							
							
				}
				else
			 	MessageYesNo(getString(R.string.document_valide), R.string.document_imprime, getString(R.string.impression));
			}else{
				//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
				if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )
				{
					String stmessageMail ="" ;
					if(Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim().equals(""))
					{
						stmessageMail ="Veuillez renseigner un Email";
						AlertMessage(stmessageMail);
					}
					else
						AlertMessage(getString(R.string.document_valide));
							
							
				}
				else
				AlertMessage(getString(R.string.document_valide));
			}

			//			saveCde(false);
			//			if (Global.dbKDEntCde.isPrintOk(m_numcde)  == false && m_problemPrinter==false )
			//			{
			//				AlertMessage(getString(R.string.commande_notPrinted));
			//				break;
			//			}
			//			MessageYesNo(getString(R.string.commande_valider_msg),
			//					R.string.commande_valider,"Validation");

			return true;

		case R.string.commande_pause:
			//Si la facture est deja validé alors alert deja validé
			if(!valide){


				if (Global.dbKDLinCde.Count_Numcde(m_numcde, false)==0)
				{
					AlertMessage(getString(R.string.commande_noLines));
					return true;
				}
				if(cptArtNonFacture > 0){
					isValider = true;
					m_adapter.notifyDataSetChanged();

					saveCde(false,false);
                	setResult(Activity.RESULT_OK, null);

				}else{

					saveCde(false,false);

					//dbKD83EntCde ent1=new dbKD83EntCde(m_appState.m_db);
					//ent1.setValidationState(m_numcde, m_codecli, "1");
					setResult(Activity.RESULT_OK, null);
				}
				finish();


			}
			else
				promptText("Commande réassort",
						"La commande a déjà été validé, vous ne pouvez pas la mettre en PAUSE.", 
						false);
				
				
			
			return true;	
		case R.string.commande_annuler:
			if(!valide){
				if (Global.dbKDEntCde.isPrintOk(m_numcde)  == false)
					MessageYesNo(getString(R.string.commande_annuler_msg),
							R.string.commande_annuler,"Annulation");
				else
					AlertMessage(getString(R.string.commande_impossibledelete));
			}else{
				AlertMessage(getString(R.string.commande_non_annuler));
			}

			return true;
		case R.string.commande_quitter:
			//finish();
			Quit();
			return true;
		case R.string.commande_fichecli:
			launchFiche(this,m_codecli,LAUNCH_FICHECLI);
			return true;
		case R.string.commande_Historique:
			launchHisto(this, m_codecli, m_soccode,m_numcde,m_typedoc);
			return true;
		case R.string.refcdeclient:
			structEntCde sentref=new structEntCde();
			if (ent.load(sentref, m_numcde, new StringBuffer(), false))
				refcdeclient=sentref.REFCDE;

			if (!m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
			{
				LaunchRefcdeclient( refcdeclient,m_codecli,20,"R" );
			}

			return true;

		case R.string.Scanner:

			if (launchDanemBR(commandeActivity.this))
			{
				Intent myIntent = new Intent();
				myIntent.setClassName("com.danem.br", "com.danem.br.BarcodeCaptureActivity");
				startActivityForResult(myIntent, LAUNCH_DANEMBR);
			}

				return true;


		case R.string.comment:
			structEntCde sent=new structEntCde();
			if (ent.load(sent, m_numcde, new StringBuffer(), false))
				commentaire=sent.COMMENTCDE;

			if (m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
			{
				LaunchComment( commentaire,m_codecli,40 ,"C");
			}
			else
				LaunchComment( commentaire,m_codecli,250 ,"C");

			return true;

		case R.string.commande_quest:
			MenuPopup.launchQuest(this,m_codecli,"","");
			return true;
		case R.string.commande_reglement:
			//MenuPopup.launchQuest(this,m_codecli,"","");
			//TODO reglement
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		//return super.onOptionsItemSelected(item);
	}

	void launchReglement(){
		Intent intent = new Intent(context,	ReglementActivity.class);
		Bundle b=new Bundle();
		b.putString(Espresso.CODE_CLIENT, m_codecli);
		b.putString(Espresso.NUM_FACTURE, m_numcde);
		intent.putExtras(b);
		startActivityForResult(intent,LAUNCH_REGLEMENT);
	}

	public void launchHisto(Context c,String codecli,String soccode,String numcdeforduplication,String typedocforduplication)
	{
		Intent intent = new Intent(c,		HistoDocumentsActivity.class);
		Bundle b=new Bundle();
		b.putString(Espresso.CODE_CLIENT,codecli);
		b.putString(Espresso.CODE_SOCIETE,soccode);
		b.putString("m_numDocForDuplication",numcdeforduplication);
		b.putString("m_typeDocForDuplication",typedocforduplication);
		intent.putExtras(b);
		startActivityForResult (intent,LAUNCH_HISTO);
	}
	void Quit()
	{
		finish();
		//		MenuPopup.launchQuest(this,m_codecli,"","");
		//		finish();
		/*		
		Intent intent = new Intent(this, ReglementActivity.class);
		Bundle b = new Bundle();
		b.putString(Espresso.CODE_CLIENT, m_codecli);
		b.putString(Espresso.CODE_SOCIETE, m_soccode);
		intent.putExtras(b);
		startActivity(intent);
		 */
	}

	//on recapitule le document pour l'utilisateur
	String RecapDocument()
	{
		String recap="";
		recap="Code client: "+structcli.CODE;
		recap+="\n\nRaison sociale: "+structcli.NOM;
		recap+="\n"+"Enseigne: "+structcli.ENSEIGNE;

		dbKD83EntCde ent=new dbKD83EntCde(m_appState.m_db);
		structEntCde structent=new structEntCde();
		ent.load(structent, m_numcde, new StringBuffer(),false);

		recap+="\n\nDate du document: "+Fonctions.YYYYMMDD_to_dd_mm_yyyy(structent.DATECDE);
		recap+="\n"+"Date d'échéance: "+Fonctions.YYYYMMDD_to_dd_mm_yyyy(structent.ECHEANCE);
		recap+="\n\n"+"Numéro de document: "+m_numcde;
		recap+="\n\n"+"Total HT: "+Fonctions.GetDoubleToStringFormatDanem(structent.MNTHT,"0.00");
		recap+="\nTotal TVA: "+Fonctions.GetDoubleToStringFormatDanem(structent.MNTTVA1+structent.MNTTVA2,"0.00");
		recap+="\nTotal TTC: "+Fonctions.GetDoubleToStringFormatDanem(structent.MNTTC,"0.00");
		recap+="\n\n\n\n\n"+getString(R.string.commande_print_msg);
		//Type de document
		//nombre de lignes
		//cumul des quantit�
		//montant TTC

		return recap;
	}



	static public void launchFiche(Activity c,String codecli,int id) {
		Intent i = new Intent(c, ficheclient.class);
		Bundle b = new Bundle();
		b.putString("numProspect",  codecli);
		i.putExtras(b);

		c.startActivityForResult(i, id);
	}
	static public void launchStatclient(Activity c,String codecli,int id) {
		Intent i = new Intent(c, statclient.class);
		Bundle b = new Bundle();
		b.putString("numProspect",  codecli);
		i.putExtras(b);

		c.startActivityForResult(i, id);
	}
	static public void launchContact(Activity c,String codecli,int id,boolean brapport) {
		Intent i = new Intent(c, ContactCliActivity.class);

		Bundle b = new Bundle();
		b.putString("codeclient",  codecli);
		b.putBoolean("brapport", brapport);
		i.putExtras(b);

		c.startActivityForResult(i, id);
	}

	boolean saveCde(boolean okToSendToServer,boolean bdeuxiemeclique) {
		try {
			int cntLine=Global.dbKDLinCde.Count_Numcde(m_numcde, false);
			
			if (cntLine==0)
			{
				return false;
			}


			if(bdeuxiemeclique==true)
			{
				//incremente le numro de doc

				//TableSouches souche=new TableSouches(m_appState.m_db,commandeActivity.this);
				TableSouches souche=new TableSouches(Global.dbParam.getDB(),commandeActivity.this);

				souche.incNum( Preferences.getValue(commandeActivity.this, Espresso.LOGIN, "0"),m_typedoc);
			}


			dbKD83EntCde.structEntCde ent = new dbKD83EntCde.structEntCde();

			ent.SOCCODE = getSocCode();
			if (okToSendToServer){
				ent.SEND = "1"; // par défaut 1 pour envoyé
				
				//incremente le numro de doc - a retiré
				
				//TableSouches souche=new TableSouches(m_appState.m_db,commandeActivity.this);
				//souche.incNum( Preferences.getValue(commandeActivity.this, Espresso.LOGIN, "0"),m_typedoc);
				//controle si 
			}
			else
				ent.SEND="0";
			ent.CODEREP = getLogin();

			ent.TYPEDOC=m_typedoc;

			ent.CODECLI = m_codecli;
			if(refcdeclient != null){
				ent.REFCDE = refcdeclient;
			}else{
				ent.REFCDE = Global.dbKDEntCde.getRefcdeclientCommentFromNumCde(m_numcde);
			}
			ent.EMAILCLI = "";
			ent.ADRLIV = "";
			ent.REMISE1 = 0; // Fonctions.GetStringToFloatDanem(this.getTextViewText(this,
			// R.id.TexteRemise));
			ent.ESCOMPTE = "";
			ent.DATELIVR=ent.DATECDE = Fonctions.getYYYYMMDD();
			//if(m_bresp==true && m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE))
			{
				String stDate=Fonctions.dd_mm_yyyy_to_YYYYMMDD(bDate.getText().toString());
				ent.DATELIVRSOUHAITE= stDate;
			}
			else
			ent.DATELIVRSOUHAITE= "";
					
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
			ent.WARNING = "" ;
			//DEV 60.1, enregistrement de warning pour les factures des mauvais payeurs
			if(  Fonctions.GetStringDanem(m_typedoc).equals(TableSouches.TYPEDOC_FACTURE)==true )
				if(Global.dbClient.isMauvaisPayeur(m_codecli))
					if ( Global.bNonRespect == true )
						ent.WARNING = "Non Respect" ;

			ent.DATEHEUREDEBUT = heureDarrivee;//Fonctions.getYYYYMMDDhhmmss();
			ent.DATEHEUREFIN = Fonctions.getYYYYMMDDhhmmss();

			ent.MODE_LIVRAISON = "";

			if (m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE)) {
				Spinner mySpinner = (Spinner) findViewById(R.id.spinnerLivraison);
				String text = mySpinner.getSelectedItem().toString();
				dbParam param = new dbParam(m_appState.m_db);
				String coderep = param.getLblAllSocReverse(dbParam.PARAM_TRANSPORT, text);
				ent.MODE_LIVRAISON = coderep;
			}

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
			
			//Log.e("m_numrapport","numrapport"+m_numrapport);
			//Log.e("m_numcde","m_numcde"+m_numcde);
			//Mise à jour rapport
			Global.dbKDClientVu.save4_rapport(m_numrapport,m_numcde);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * calcul le mnt de la cde
	 */
	void CalcAll() {
		try {

			double dTotalHT = 0;
			double dMntTVA = 0;
			double dTotalTTC = 0;

			dbKD84LinCde.structLinCde lin = new dbKD84LinCde.structLinCde();
			StringBuffer stBuf = new StringBuffer();
			dTotalHT = Global.dbKDLinCde.CalculTotalHT(  m_numcde);
			m_dTotalHT=dTotalHT;

			//On récupère les taux de tva
			dbKD84LinCde lin1=new dbKD84LinCde(Global.dbParam.getDB());

			List<structLinCde> lines = lin1.loadTotauxTVA(m_numcde, false);

			float montantht1 = 0;
			float taux1 = 0;
			float montant1 = 0;

			float montantht2 = 0;
			float taux2 = 0;
			float montant2 = 0;
			float montantttc=0;

			if(lines.size() > 0){
				montantht1 = lines.get(0).MNTTOTALHT;
				taux1 = lines.get(0).TAUX;
				montant1=(float)Fonctions.round(((montantht1*taux1)/100),2);
			}

			if(lines.size() > 1){
				montantht2 = lines.get(1).MNTTOTALHT;
				taux2 = lines.get(1).TAUX;
				montant2=(float)Fonctions.round(((montantht2*taux2)/100),2);

			}

			//dMntTVA = Global.dbProduit.Calculpourcent(dTotalHT, m_dTVA);
			//dMntTVA = Global.dbKDLinCde.CalculTotalTVA(m_numcde);
			dMntTVA=montant1+montant2;
			dTotalHT=montantht1+montantht2;
			dTotalTTC=montantttc=(montantht1+montant1)+(montantht2+montant2);
			if(m_clientTVA.equals(TableClient.TVA_FISCAL_NEUF) || m_clientTVA.equals(TableClient.TVA_FISCAL_SIX) )
			{
				dTotalTTC=dTotalHT;
				dMntTVA=0;
			}
			Global.dMntToGet = dTotalTTC ;		//DEV 60.1


			//dTotalTTC = dTotalHT + dMntTVA;

			this.setTextViewText(this, R.id.textViewMntHTVal,
					Fonctions.GetDoubleToStringFormatDanem(dTotalHT, "0.00"));
			this.setTextViewText(this, R.id.textViewMntTVAVal,
					Fonctions.GetDoubleToStringFormatDanem(dMntTVA, "0.00"));
			this.setTextViewText(this, R.id.textViewMntTTCVal,
					Fonctions.GetDoubleToStringFormatDanem(dTotalTTC, "0.00"));

			saveCde(false,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void launchPrinting(boolean duplicata) {


		//	m_ProgressDialog=ProgressDialog.show(this, getString(R.string.communication_avec_l_imprimante), getString(R.string.patientez_));
		Z420ModelFacture z=new Z420ModelFacture(this, m_typedoc, rep.LOGIN);
		String   zplData = "";

		try{
			//on récupère la liste découpée normal + gratuit
			listPrintFactures = z.getListeProduit(m_numcde, duplicata);

			for(int i = 0;i<listPrintFactures.size();i++){
				//on passe dans la méthode la liste à imprimer puis numéro de la page puis nombre de pages
				zplData+=z.getFacture(m_numcde,m_codecli,duplicata,m_typedoc, listPrintFactures.get(i), i+1, listPrintFactures.size(),m_contactnom);
			}
		}catch(Exception ex){
			zplData = getString(R.string.erreur_getfacture);
		}

		if(m_bresp==true)
		{
			if(checkIfSignatureRespExists(m_numcde)){
				launchPrintPreview(zplData, m_numcde, m_btech,m_bresp,false,false);
			}
			else {
				Bundle bundle = new Bundle();
				bundle.putString("filename", "sign_resp_" + m_numcde);
				bundle.putString("zplData", zplData);
				bundle.putString("folderSignature", ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER));

				Intent i = new Intent(commandeActivity.this, SignatureActivity.class);
				i.putExtras(bundle);
				startActivityForResult(i, 10000);
			}
		}
		else
		{
			if(!m_btech || checkIfSignatureExists(m_numcde)){
				launchPrintPreview(zplData, m_numcde, m_btech,m_bresp,false,false);
			}else{
					if(m_btech==true)
					{
						Bundle bundle = new Bundle();
						bundle.putString("filename", "sign_tech_"+m_numcde);
						bundle.putString("zplData", zplData);
						bundle.putString("folderSignature", ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER));
						
						Intent i = new Intent(commandeActivity.this, SignatureActivity.class);
						i.putExtras(bundle);
						startActivityForResult(i,10000);
					}
			}
		}
		
		
		
		/*	 
		String mac=getPrinterMacAddress();
 		   BluetoothConnectionInsecureExample example = new BluetoothConnectionInsecureExample(hPrintResult);

	        example.sendZplOverBluetooth(mac,zplData);
		 */	

	}
	
	private boolean checkIfSignatureExists(String numCommande){
		File file = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_tech_"+numCommande+".jpg");
		return file.exists();
	}
	private boolean checkIfSignatureRespExists(String numCommande){
		File file = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_tech_"+numCommande+".jpg");
		return file.exists();
	}

	void deleteCde() {
		if (Global.dbKDEntCde
				.deleteCde(m_numcde, m_codecli, new StringBuffer()) == false) {
			Fonctions.FurtiveMessageBox(this, "err");
		}

		if(!m_numrapport.equals("")){ // on met à jour le KD101 seulement si on vient d'un rapport
			Global.dbKDClientVu.updateNumBC(m_numrapport,m_numcde,new StringBuffer());
		}
	}
	
	public void MessageYesNo3(String message, int type,String title) {
		final int m_type = type;

		ImageView image = new ImageView(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(type == R.string.message_yesno_valider_bc ){
			image.setImageResource(R.drawable.dot_red);
			builder.setView(image);
		}
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton(getString(R.string.Continuerr),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				switch (m_type) {
				
				case R.string.message_yesno_valider_bc:



					
					//if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) &&  m_bresp==true)
					if(m_typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) )
					{
						String stmessageMail ="" ;
						if(Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim().equals(""))
						{
							stmessageMail ="Veuillez renseigner un Email";
							 AlertMessage(stmessageMail);
						}
						else
						{
							m_EmailApres=etEmailclient.getText().toString().trim();
							if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
							{
								StringBuffer stBuf=new StringBuffer();
								if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
									Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
								//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
							}
							stmessageMail ="Une copie de cette commande sera envoyée au client par Mail à l\'adresse suivante : "+Fonctions.GetStringDanem(etEmailclient.getText().toString()).trim();
							 AlertMessage(stmessageMail);
						}
								
								
					}
					
					
					break;
				
				case R.string.commande_annuler:
					if(isfacturable==true)
					{
						int cntLine=Global.dbKDLinCde.Count_Numcde(m_numcde, false);
						
						if (cntLine==0)
						{
							//AlertMessage(getString(R.string.commande_facturable_saisie));
							
						}
						deleteCde();
						setResult(Activity.RESULT_CANCELED, null);

						Quit();
					}
					else
					{
						deleteCde();
						setResult(Activity.RESULT_CANCELED, null);

						Quit();
						//finish();
					}
					
					
					break;

				case R.string.document_imprime:
					Global.dbKDEntCde.setPrintOK(m_numcde, m_codecli);

					cptImpression = 0;
					break;
				case 434:
					Global.dbKDEntCde.setPrintOK(m_numcde, m_codecli);
					if(m_btech==true || m_bresp==true)
					{
						if(m_numcde != null && !m_numcde.equals("")){
							//Save Email si differenr
							m_EmailApres=etEmailclient.getText().toString().trim();
							if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
							{
								StringBuffer stBuf=new StringBuffer();
								if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
									Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
								//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
							}
							//else
								//Fonctions.sendPDFToMailApp(context, m_numcde, structcli.EMAIL);
							 	
					   		
						}	
					}
					
					
					cptImpression = 0;
					break;
				}

			}
		})
		.setNegativeButton(getString(R.string.Annulerr),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				isFirstClickValider = false;

				switch (m_type) {
				case 434:
					launchPrinting(false);

					break;
				case R.string.document_imprime:
					MessageYesNo(getString(R.string.problem_print), 434, getString(R.string.probl_me_d_impression));
					break;
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	

	public void MessageYesNo(String message, int type,String title) {
		final int m_type = type;

		ImageView image = new ImageView(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(type == R.string.message_yesno_valider_facture || type == R.string.message_yesno_imprimer_facture || type == R.string.message_yesno_valider_bc ){
			image.setImageResource(R.drawable.dot_red);
			builder.setView(image);
		}
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton(getString(R.string.Yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				switch (m_type) {
				case 433://probleme d'imprimante
					m_problemPrinter=true;

					promptText(getString(R.string.probl_me_d_impression),
							getString(R.string.vous_pouvez_valider_le_document_sans_imprimer), 
							false);
					break;
				case R.string.commande_print:
					saveCde(false,false);
					task_sendWSData wsCde = new task_sendWSData(m_appState,null, null);
					wsCde.execute();
					launchPrinting(false);


					/*Intent intent = getSendToPrinterIntent(
		                    commandeActivity.this, new String[]{},
		                    0);*/

					// notify the activity on return (will need to ask the user for
					// approvel)
					//startActivityForResult(intent, ACTIVITY_PRINT);
					break;
				case R.string.commande_valider:
				case R.string.commande_transmettre:

					
					break;
				case R.string.message_yesno_valider_facture:
					DisableAllButtons();
					
					saveCde(false,true);
					//					if (Global.dbKDEntCde.isPrintOk(m_numcde)  == false && m_problemPrinter==false )
					//					{
					//						AlertMessage(getString(R.string.commande_notPrinted));
					//						break;
					//					}

					//ici gestion du reglement
					//si l'on ne vient pas de reglement
					//alors on envoie vers reglement
					//					MessageYesNo(getString(R.string.commande_valider_msg),
					//							R.string.commande_valider,"Validation");

					//if (saveCde(true)==false) return;

					dbKD83EntCde ent=new dbKD83EntCde(m_appState.m_db);
					//On change l'état de la facture pour le passer à 1 (facture validée)
					ent.setValidationState(m_numcde, m_codecli, "1");

					//on transfert la facture dans les factures dues
					if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE) || m_typedoc.equals(TableSouches.TYPEDOC_AVOIR)){	
						structEntCde structent=new structEntCde();
						ent.load(structent, m_numcde, new StringBuffer(),false);
						dbKD730FacturesDues dues=new dbKD730FacturesDues(m_appState.m_db);
						dues.insert(structent);
					}

					DecrementeStock();
					// on envoi la facture tt de suite
					//					task_sendWSData wsCde = new task_sendWSData(
					//							m_appState,null);
					//					wsCde.execute();

					setResult(Activity.RESULT_OK, null);

					//On réinitialise les valeurs des éléments non facturés
					//					isValider = false;
					//					cptArtNonFacture = 0;

					//Quit();

					if(!isComeFromReglement){
						//MessageYesNo(getString(R.string.goto_reglement), R.string.goto_reglement, getString(R.string.commande_reglement));
						if (m_typedoc.equals(TableSouches.TYPEDOC_FACTURE))
							launchReglement();

						break;
					}

					break;
				
				case R.string.message_yesno_imprimer_facture:
					if (Global.dbKDLinCde.Count_Numcde(m_numcde, false)==0)
					{
						AlertMessage(getString(R.string.commande_noLines));
					}
					saveCde(false,false);

					//ici gestion du reglement
					//si l'on ne vient pas de reglement
					//alors on envoie vers reglement
					if(!isComeFromReglement){
						launchReglement();
						break;
					}

					MessageYesNo(RecapDocument(),
							R.string.commande_print,"Résumé");
					// saveCde();
					// launchPrinting(item.getItemId());
					break;
				case R.string.commande_annuler:
					if(isfacturable==true)
					{
						int cntLine=Global.dbKDLinCde.Count_Numcde(m_numcde, false);
						
						if (cntLine==0)
						{
							//AlertMessage(getString(R.string.commande_facturable_saisie));
							
						}
						deleteCde();
						setResult(Activity.RESULT_CANCELED, null);

						Quit();
					}
					else
					{
						deleteCde();
						setResult(Activity.RESULT_CANCELED, null);

						Quit();
						//finish();
					}
					
					
					break;

				case R.string.document_imprime:
					Global.dbKDEntCde.setPrintOK(m_numcde, m_codecli);

					cptImpression = 0;
					break;
				case 434:
					Global.dbKDEntCde.setPrintOK(m_numcde, m_codecli);
					if(m_btech==true || m_bresp==true)
					{
						if(m_numcde != null && !m_numcde.equals("")){
							//Save Email si differenr
							m_EmailApres=etEmailclient.getText().toString().trim();
							if(!Fonctions.GetStringDanem(m_EmailApres).equals(""))
							{
								StringBuffer stBuf=new StringBuffer();
								if(!Fonctions.GetStringDanem(m_EmailApres).equals(Fonctions.GetStringDanem(m_EmailAvant)))
									Global.dbClient.saveModifEmail(m_codecli,Fonctions.GetStringDanem(m_EmailApres), Fonctions.getYYYYMMDD(), stBuf);
								//Fonctions.sendPDFToMailApp(context, m_numcde, m_EmailApres);
							}
							//else
								//Fonctions.sendPDFToMailApp(context, m_numcde, structcli.EMAIL);
							 	
					   		
						}	
					}
					
					
					cptImpression = 0;
					break;
				}

			}
		})
		.setNegativeButton(getString(R.string.No),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				isFirstClickValider = false;

				switch (m_type) {
				case 434:
					launchPrinting(false);

					break;
				case R.string.document_imprime:
					MessageYesNo(getString(R.string.problem_print), 434, getString(R.string.probl_me_d_impression));
					break;
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	

	void DecrementeStock()
	{
		Global.dbKDLinInv.decrementeStock(m_numcde,this);
	}
	public void AlertMessage(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}



	public static class DatePickerFragment extends DialogFragment implements
	DatePickerDialog.OnDateSetListener {

		Handler DateLivr;
		public DatePickerFragment(Handler b)
		{
			DateLivr=b;
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH)+1;
			int day = c.get(Calendar.DAY_OF_MONTH);

			long time = c.getTimeInMillis();

			DatePickerDialog dlg=new DatePickerDialog(getActivity(), this, year, month, day);
			//dlg.getDatePicker().setMinDate(time);

			return dlg;
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			Message msg=new Message();
			msg.what=2;
			Bundle b=new Bundle();
			b.putString("date",Fonctions.ymd_to_YYYYMMDD(year, month+1, day));
			msg.setData(b);
			DateLivr.sendMessage(msg);
		}
	}


	void fillFamille(String selVal) {
		try {
				
			if(isfacturable==false)
			{
				if(m_btech==true && m_typedoc.equals(TableSouches.TYPEDOC_COMMANDE))
				{
					if (Global.dbParam.getFamActivesFacturable(idFam)  == true) {

						Bundle bundle = new Bundle();

						//on inser la famille 'panier'

					/*bundle = new Bundle();
					bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.commande_tout));
					bundle.putString(Global.dbParam.FLD_PARAM_LBL, "---");
					bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.commande_tout));
					bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.commande_tout));
					idFam.add(0,bundle);*/

						int pos = 0;
						String[] items = new String[idFam.size()];
						for (int i = 0; i < idFam.size(); i++) {

							items[i] = idFam.get(i).getString(
									Global.dbParam.FLD_PARAM_LBL);

							String codeRec = idFam.get(i).getString(
									Global.dbParam.FLD_PARAM_CODEREC);

							if (selVal.equals(codeRec)) {
								pos = i;
							}

						}

						Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
								android.R.layout.simple_spinner_item, items);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinner.setAdapter(adapter);

						spinner.setSelection(pos);

					}

				}
				else
				{
					if (Global.dbParam.getFamActives(idFam)  == true) {

						Bundle bundle = new Bundle();

						//on inser la famille 'panier'

						bundle = new Bundle();
						bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.commande_tout));
						bundle.putString(Global.dbParam.FLD_PARAM_LBL, "---");
						bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.commande_tout));
						bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.commande_tout));
						idFam.add(0,bundle);

						int pos = 0;
						String[] items = new String[idFam.size()];
						for (int i = 0; i < idFam.size(); i++) {

							items[i] = idFam.get(i).getString(
									Global.dbParam.FLD_PARAM_LBL);

							String codeRec = idFam.get(i).getString(
									Global.dbParam.FLD_PARAM_CODEREC);

							if (selVal.equals(codeRec)) {
								pos = i;
							}

						}


						Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
								android.R.layout.simple_spinner_item, items);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinner.setAdapter(adapter);

						spinner.setSelection(pos);

					}

				}


				
			}
			else
			{
				if (Global.dbParam.getFamActivesFacturable(idFam)  == true) {

					Bundle bundle = new Bundle();

					//on inser la famille 'panier'

					/*bundle = new Bundle();
					bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.commande_tout));
					bundle.putString(Global.dbParam.FLD_PARAM_LBL, "---");
					bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.commande_tout));
					bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.commande_tout));
					idFam.add(0,bundle);*/

					int pos = 0;
					String[] items = new String[idFam.size()];
					for (int i = 0; i < idFam.size(); i++) {

						items[i] = idFam.get(i).getString(
								Global.dbParam.FLD_PARAM_LBL);

						String codeRec = idFam.get(i).getString(
								Global.dbParam.FLD_PARAM_CODEREC);

						if (selVal.equals(codeRec)) {
							pos = i;
						}

					}

					Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
							android.R.layout.simple_spinner_item, items);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner.setAdapter(adapter);

					spinner.setSelection(pos);

				}

			}

		} catch (Exception ex) {

		}

	}


	void fillLivraison(String selVal)
	{

		dbParam param = new dbParam(m_appState.m_db);
		ArrayList<Bundle> data = param.getAllRecLblFromTable2("TRANSPORT");

		Bundle bundle = new Bundle();

		//on inser la famille 'panier'

		//bundle = new Bundle();
		//bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.commande_tout));
		//bundle.putString(Global.dbParam.FLD_PARAM_LBL, "---");
		//bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.commande_tout));
		//bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.commande_tout));
		//idFam.add(0,bundle);

		int pos = 0;
		String[] items = new String[data.size() + 1];
		for (int i = 0; i < data.size(); i++) {

			Log.e("data.get","data.get"+data.get(i).getString(dbParam.FLD_PARAM_LBL));

			items[i] = data.get(i).getString(dbParam.FLD_PARAM_LBL);

			//String codeRec = idLivraison.get(i).getString(
		    //Global.dbParam.FLD_PARAM_CODEREC);

			/*if (selVal.equals(codeRec)) {
				pos = i;
			}*/

		}

		items[data.size()] = "---";

		if ( items != null )
		{
			Spinner spinner = (Spinner) findViewById(R.id.spinnerLivraison);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setSelection(data.size());
		}

	}


	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		m_stFam = idFam.get(pos).getString(
				Global.dbParam.FLD_PARAM_CODEREC);

		if (m_stFam.equals(getString(R.string.commande_tout)))
			return;
		//m_stFam = "" ;		//6546876546: DEBUGAGE
		PopulateList("",false,false) ;
	}
	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	//test impression
	private static int ACTIVITY_PRINT = 127;

	public static Intent getSendToPrinterIntent(Context ctx, String[] fileFullPaths, int indexToPrint){
		Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);

		// This type means we can send either JPEG, or PNG
		sendIntent.setType("image/*");

		ArrayList<Uri> uris = new ArrayList<Uri>();

		//File fileIn = new File(fileFullPaths[indexToPrint]);
		File fileIn = new File("/sdcard/Pictures/Screenshots/Screenshot_2013-04-24-21-08-04.png");
		Uri u = Uri.fromFile(fileIn);
		uris.add(u);

		sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

		return sendIntent;
	}

	/*public void printPDF(final PrintModel printModel, final File f){
		Uri printFileUri = Uri.fromFile(f);
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setPackage("com.dynamixsoftware.printershare");
		i.setDataAndType(printFileUri,"text/plain");
		startActivity(i);
	}*/
	public void printPDF(Uri uri){
		//Uri printFileUri = Uri.parse(path);
		String packageName = this.getPrinterSharePackageName();

		if(!packageName.equals("")){
			Intent i = new Intent(Intent.ACTION_VIEW);	
			i.setPackage(packageName);
			i.setDataAndType(uri,"text/plain");
			commandeActivity.this.startActivity(i);
		}
	}

	//on récupère le package printershare
	public String getPrinterSharePackageName(){
		ArrayList<String> list = this.getInstalledApps(false);
		for (String name : list) {
			if(name.contains("com.dynamixsoftware.printershare")) return name;
		}
		return "";
	}

	//on récupère la liste des packages
	public ArrayList<String> getInstalledApps(boolean getSysPackages) {     

		List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
		ArrayList<String> listePackageName = new ArrayList<String>();

		for(int i=0;i<packs.size();i++) {
			PackageInfo p = packs.get(i);

			if ((!getSysPackages) && (p.versionName == null)) {
				continue ;
			}
			//newInfo.pname = p.packageName;
			listePackageName.add(p.packageName);
		}

		return listePackageName;
	}

	/*private void generateA4Pdf(PrintModel printModel){		
		String pdfcontent = PDFWriterManager.getInstance().generatePDF(printModel);
		File f = outputToFile(printModel.getNumeroBonDeCommande()+".pdf",pdfcontent,"ISO-8859-1");
	}*/

	/*private File outputToFile(String fileName, String pdfContent, String encoding) {
		String PATH = Environment.getExternalStorageDirectory() + "/VDS/";
		try{
			File file = new File(PATH);
			file.mkdirs();
		}
		catch(Exception e){

		}
		File newFile = new File(PATH+"/"+fileName);
		try {
			newFile.createNewFile();
			try {
				FileOutputStream pdfFile = new FileOutputStream(newFile);
				pdfFile.write(pdfContent.getBytes(encoding));
				pdfFile.close();
			} catch(FileNotFoundException e) {
				//
			}
		} catch(IOException e) {
			//
		}
		return newFile;
	}*/
	static public void launchRapport(Activity c,String codecli,int id) {

		//Intent i = new Intent(c, rapportactivite.class);
		Intent i = new Intent(c, rapportActivity.class);
		

		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);

		i.putExtras(b);



		c.startActivityForResult(i, id);

	}
	static public void launchRapportA(Activity c,String codecli,int id) {

		Intent i = new Intent(c, rapportactivite.class);
		//Intent i = new Intent(c, rapportActivity.class);
		

		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);

		i.putExtras(b);



		c.startActivityForResult(i, id);

	}
	static public void launchRapport2(Activity c,String codecli,String code_evt,String evt_id,String numInterne,boolean autosave) {

		Intent i = new Intent(c, rapportactivite.class);

		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);
		b.putString("code_evt",  code_evt);
		b.putString("evt_id",  evt_id);
		b.putString("numInterne",  numInterne);
		b.putBoolean("autosave", autosave);

		i.putExtras(b);

		c.startActivity(i );

	}
	
	public void AppelRapportVendeur_Ref(boolean juste_message)
	{
		
		//si il y a une ref pour ce client dans A visiter
		if(!Global.dbCliToVisit.RefTodoClient(m_codecli).equals(""))
		{
			
			if(juste_message==true)
			{
				//Si la ref a bien été pris dans la commande du jour 
				if(Global.dbKDLinCde.bExisteRef_Todo(m_codecli,Global.dbCliToVisit.RefTodoClient(m_codecli))==true)
				{
					
				}
				else
				{
					// appel de l'écran rapport 
					ArrayList<String> Valueget =new ArrayList();
					 if(Global.dbCliToVisit.Bool_RefTodoClient(m_codecli,Valueget)==true)
					 {
						 if(Global.dbKDClientVu.TodoFait_Exist(m_codecli,Valueget.get(2))==true)
						 {						 
						 }
						 else
						 {
							 String infolbl="";
							
							 promptText("A faire",
										"Vous n\'avez pas vendu le produit "+Global.dbProduit.getLibelle(Valueget.get(0))+" malgré l\'opération en cours", 
										false);
						 }
						
						 
					 }
				}
					
				
			}
			else
			{
				//Si la ref a bien été pris dans la commande du jour 
				if(Global.dbKDLinCde.bExisteRef_Todo(m_codecli,Global.dbCliToVisit.RefTodoClient(m_codecli))==true)
				{
					//Auto save
					// appel de l'écran rapport 
					ArrayList<String> Valueget =new ArrayList();
					 if(Global.dbCliToVisit.Bool_RefTodoClient(m_codecli,Valueget)==true)
					 {
						 if(Global.dbKDClientVu.TodoFait_Exist(m_codecli,Valueget.get(2))==true)
						 {						 
						 }
						 else
						 {
							 launchRapport2(this,m_codecli,Valueget.get(1),Valueget.get(2),Valueget.get(2),true);	
							 	 
						 }
						 
					 }
							
				}
				else
				{
					// appel de l'écran rapport 
					ArrayList<String> Valueget =new ArrayList();
					 if(Global.dbCliToVisit.Bool_RefTodoClient(m_codecli,Valueget)==true)
					 {
						 if(Global.dbKDClientVu.TodoFait_Exist(m_codecli,Valueget.get(2))==true)
						 {						 
						 }
						 else
						 {
							// promptText("A faire",
								//		"Vous n\'avez pas vendu le produit "+Valueget.get(0)+" malgré l\'opération en cours", 
								//		false);
							 launchRapport2(this,m_codecli,Valueget.get(1),Valueget.get(2),Valueget.get(2),false);	 
						 }
						
						 
					 }
				}
					
				
				
			}
			
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
			
			
			
	


}