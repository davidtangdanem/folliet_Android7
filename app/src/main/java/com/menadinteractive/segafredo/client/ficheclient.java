package com.menadinteractive.segafredo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;

import com.menadinteractive.segafredo.carto.MenuPopup;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.GPS;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.SQLRequestHelper;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.synchro.SynchroActivity;
import com.menadinteractive.segafredo.synchro.SynchroService;


public class ficheclient extends BaseActivity implements OnItemSelectedListener, OnClickListener
{

	//String TypeEnv; //1-Creation client //2-Vivu
	String numProspect;
	String m_stCurZone ;
	String m_stCurJourPassage ;
	String m_CreationProspect="";
	boolean m_bCreat=false;

	String m_infosterrain;
	
	String m_stvaleurDebut;
	String m_stvaleurFin;
	
	String m_stvaleurDebutP;
	String m_stvaleurFinP;

	Spinner spin_soc;
	ArrayList<String[]> arraySoc=null;
	TextView TextViewRaisonsocial ;
	TextView TextViewNom ;
	TextView TextViewAdresse1 ;
	TextView TextViewAdresse2 ;
	TextView TextViewcodepostal;
	TextView TextViewville ;
	TextView TextViewPays;	
	TextView TextViewTel;	
	TextView TextViewFax ;
	TextView TextViewContactgsm ;
	TextView TextViewEmail ;
	TextView TextViewSiret ;
	TextView TextViewCommentaire ;
	TextView TextViewlat ;
	TextView TextViewlon ;

	TextView TextViewCatCompta ;
	TextView TextViewTypePDV ;
	TextView TextViewNumTVA ;
	TextView TextViewZone ;
	TextView TextViewJourPassage ;

	TextView TexteEnseigne;
	TextView TexteType;
	TextView TexteGroupe;
	TextView TexteTypeEtablissement;
	TextView TexteActiviteP;
	TextView TexteClasse;
	TextView TexteFamclient;
	TextView TexteSFamclient;
	
	TextView TexteAgent;
	TextView TexteJourFerm;
	TextView TexteSAV;
	TextView TexteModereglement;
	TextView TextePeriodicite;
	TextView TexteTournee;
	
	TextView TexteAnnuel;
	
	TextView TexteSaisonEte;
	TextView TexteSaisonHiver;
	TextView TexteDateconcurrent;

	TextView TexteEncours;
	TextView TexteCircuit;

	TextView TexteFacturesdues;
	TextView TexteAvoirdispo;
	TextView TexteAvancepaie;

	TextView TexteVolumecafe;
	EditText EditVolumecafe;

	TextView TexteChiffreAffaires;
	EditText EditChiffreAffaires;
	TextView TextePlaceAssInt;
	EditText EditPlaceAssInt;
	TextView TextePlaceAssExt;
    EditText EditPlaceAssExt;
    TextView TexteCapacite_sdr;
    EditText EditCapacite_sdr;
    TextView TexteNb_Chambres;
    EditText EditNb_Chambres;
    TextView TexteNb_Lits;
    EditText EditNb_Lits;
    TextView TexteQualif;
    EditText EditQualif;
    Spinner spinnerQualif;
    TextView TexteSituation;
    EditText EditSituation;
    Spinner spinnerSituation;
    TextView TexteOption;
    EditText EditOption;
    Spinner spinnerOption;
    TextView TexteTypeCuisine;
    EditText EditTypeCuisine;
	Spinner spinnerTypeCuisine;
    TextView TextePV_CAFE;
    EditText EditPV_CAFE;
    TextView TextePV_THE;
    EditText EditPV_THE;
    TextView TextePV_CHOCOLAT;
    EditText EditPV_CHOCOLAT;
    TextView TextePV_PETIT_DEJ;
    EditText EditPV_PETIT_DEJ;
    TextView TextePV_CHAMBRE;
    EditText EditPV_CHAMBRE;
	TextView TexteBooking;
	EditText EditBooking;
	TextView TexteTripAdvisor;
	EditText EditTripAdvisor;

    EditText EditEncours;
	EditText EditFacturesdues;
	EditText EditAvoirdispo;
	EditText EditAvancepaie;
	EditText EditEnseigne;
	EditText eContactgsm;
	EditText EEmail;
	
	EditText EditLat;
	EditText EditLon;
	
	EditText ERaisonSocial;
	EditText Eville;
	EditText EAdresse1;
	EditText Ecodepostal;
	EditText ETel;
	EditText EFax;
	EditText ESiret ;
	EditText ENumTVA ;
	Spinner spPeriodicite;
	Spinner spTournee;
	Spinner spType;
	Spinner spJourFerm;
	Spinner spTypeEtablissement;
	Spinner spActiviteP;
	Spinner spClasse;
	Spinner spFamclient;
	Spinner spSFamclient;
	Spinner spModereglement;
	
	Spinner spinnerAnnuel;
	Spinner spinnerSaisonEte;
	Spinner spinnerSaisonHiver;
	
	Spinner spinnerAgent;

	CheckBox checkPasdemail,checkFacEmail;
	CheckBox  checkMiseStandBy;
	Button bDate;
	
	boolean m_bprendrecde=false;	
	ArrayList<Bundle> idPays = null;// les id qui servent a retrouver les pays

	ArrayList<Bundle> idListCatCompta = null;// categ compta pour la tva
	ArrayList<Bundle> idListTypePdv = null;// type de pdv
	ArrayList<Bundle> idListZone = null;// codetournee
	ArrayList<Bundle> idListJourPassage = null;// codetournee
	ArrayList<Bundle> idListTypeEtablissement = null;
	ArrayList<Bundle> idListActiviteP = null;
	
	ArrayList<Bundle> idListClasse = null;
	ArrayList<Bundle> idListFamclient = null;
	ArrayList<Bundle> idListSFamclient = null;
	
	ArrayList<Bundle> idListType = null;// Type
	ArrayList<Bundle> idListGroupe = null;// Groupe
	ArrayList<Bundle> idListAgent= null;// Agent
	ArrayList<Bundle> idListJourFerm= null;// Jour Ferm
	ArrayList<Bundle> idListSAV= null;// SAV
	ArrayList<Bundle> idListModereglement= null;// mode reglement
	ArrayList<Bundle> idListPeriodicite= null;// Periodicite
	ArrayList<Bundle> idListTournee= null;// Tournee
	ArrayList<Bundle> idListAnnuel= null;// Annuel
	
	ArrayList<Bundle> idListSaisonEte= null;// Annuel
	ArrayList<Bundle> idListSaisonHiver= null;// Annuel
	
	ArrayList<Bundle> idListCircuit= null;// Circuit

	ArrayList<Bundle> idListQualif= null;// Qualification
    ArrayList<Bundle> idListSituation= null;// Situation
	ArrayList<Bundle> idListOption= null;// Option
	ArrayList<Bundle> idListTypeCuisine= null;// type de cuisine


	private ProgressDialog m_ProgressDialog = null;

	boolean bRaisonSocial=false ;
	boolean bVolumeCafe=false ;
	boolean bNom=false ;
	boolean bEnseigne=false;
	boolean bAdresse1=false ;
	boolean bAdresse2 =false;
	boolean bcodepostal=false;
	boolean bville=false ;
	boolean bPays =false;
	boolean bTel=false;
	boolean bFax =false;
	boolean bContactGSM=false ;
	boolean bEmail =false;
	boolean bSiret =false;
	boolean bCommentaire =false;

	boolean bCatCompta =false;
	boolean bTypePdv =false;
	boolean bZone=false;
	boolean bJourPassage=false;
	boolean bNumTVA=false;

	boolean bTypeEtablissement=false;
	boolean bActiviteP=false;
	boolean bFamclient=false;
	boolean bSFamclient=false;

	boolean bAnnuel=false;
	boolean bSaisonEte=false;
	boolean bSaisonHiver=false;

	boolean bTypeclient=false;
	boolean bGroupeclient=false;
	boolean bJourFermeture=false;
	boolean bModereglement=false;
	boolean bSAV=false;
	boolean bPeriodicite=false;
	boolean bTournee=false;
	boolean bAgent=false;

	structlistLogin rep = null;
	boolean m_btech=false;
	boolean m_pasemail =false;
	
	int __year;
    int __month;
    int __day;
    
    static final int DATE_PICKER_FICHE = 3333; 

	boolean cancelAllowed;
	MyLocation whereAmI;

	boolean devinerAdresseAllowed=true;//on autorise le bouton que si c'est une creation sinon risque d'effacement de vraie adresse
	/** Task */
	task_geocodeClient gecodeClient = null;
	Handler handlerGeoCoder;
	Handler handlerRetrieveAddress;

	final int LAUNCH_ENTETECDE=33;
	final int LAUNCH_GEOLOCALISATION=724;
	
	String latitude="";
	String longitude="";
	String m_stTandby="";

	String m_stInfoterrain="";
	boolean bBlockSave = false ;
	

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		cancelAllowed=true;
		m_pasemail=false;
		SynchroService.setPaused(true);

		handlerGeoCoder = getHandlerGeoCoder();
		handlerRetrieveAddress = getHandlerRetrieveAddress();

		idPays = new ArrayList<Bundle>();

		idListCatCompta= new ArrayList<Bundle>();
		idListTypePdv= new ArrayList<Bundle>();
		idListZone= new ArrayList<Bundle>();
		idListJourPassage= new ArrayList<Bundle>();
		idListType= new ArrayList<Bundle>();
		idListAgent= new ArrayList<Bundle>();
		idListJourFerm= new ArrayList<Bundle>();
		idListSAV= new ArrayList<Bundle>();
		idListModereglement= new ArrayList<Bundle>();
		idListPeriodicite= new ArrayList<Bundle>();
		idListTournee= new ArrayList<Bundle>();
		idListAnnuel= new ArrayList<Bundle>();
		idListSaisonEte= new ArrayList<Bundle>();
		idListSaisonHiver= new ArrayList<Bundle>();
		
		
		idListCircuit= new ArrayList<Bundle>();
		idListGroupe= new ArrayList<Bundle>();
		idListTypeEtablissement= new ArrayList<Bundle>();
		idListActiviteP= new ArrayList<Bundle>();
		
		idListClasse= new ArrayList<Bundle>();
		idListFamclient= new ArrayList<Bundle>();
		idListSFamclient= new ArrayList<Bundle>();
		idListQualif= new ArrayList<Bundle>();
        idListSituation= new ArrayList<Bundle>();
		idListOption= new ArrayList<Bundle>();
		idListTypeCuisine= new ArrayList<Bundle>();
		

		Bundle bundle = this.getIntent().getExtras();
		numProspect = bundle.getString("numProspect");
		m_bprendrecde = Fonctions.convertToBool(getBundleValue(bundle,"prendrecde"));
		m_stCurZone =bundle.getString("curzone");

		String stDateSynchro = Preferences.getValue(this, Espresso.DATESYNCHRO, "");
		if ( !stDateSynchro.equals(Fonctions.getYYYYMMDD()) && !numProspect.equals(""))
			bBlockSave	= true ;


		setContentView(R.layout.activity_ficheclient);
		initActionBar();
		rep = getRep();
		if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien))
		{
			m_btech=true;

		}
		
		spin_soc= (Spinner) findViewById(R.id.spinnerCompany);
		fillSociete();

		InitTextView();

		Spinner spinner = (Spinner) findViewById(R.id.spinnerZone);
		spinner.setOnItemSelectedListener(this);
		bDate = (Button) findViewById(R.id.bDateconcurrent);
		setListeners();
		initListeners();

		Loadinformation(numProspect);

		spFamclient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				//affichage des FAM2 appartenant � FAM1
				int pos = spFamclient.getSelectedItemPosition();
			
				if (pos > 0) {
					fillSFamclient(getSFamclient(), getFamclient(),getClasse());
				}
				else
					fillSFamclient(getSFamclient(), Fonctions.GetStringDanem(cliToSave.FAMCLIENT),Fonctions.GetStringDanem(cliToSave.CLASSE));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
				public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
				
				//affichage des FAM2 appartenant � FAM1
				int pos = spClasse.getSelectedItemPosition();
			
				if (pos > 0) {
					fillFamclient(getFamclient(), getClasse());
				}
				else
					fillFamclient(getFamclient(), Fonctions.GetStringDanem(cliToSave.CLASSE));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		if ( bBlockSave == true )
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Attention, modification bloquée, vous devez synchroniser vos données")
					.setCancelable(true);

			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	void Loadinformation(String numprospect)
	{
		cliToSave=new structClient();
		checkMiseStandBy.setVisibility(View.GONE);
	//	TexteDateconcurrent.setVisibility(View.GONE);	
	//	bDate.setVisibility(View.GONE);

		if(numprospect.equals(""))
		{
			//numProspect=numprospect=Global.dbClient.GetCodeProspect(Preferences.getValue(this, Espresso.LOGIN, "0"));
			TableSouches souche=new TableSouches(m_appState.m_db,this);
			 
			numProspect=numprospect= souche.get(souche.TYPEDOC_PROSPECT, Preferences.getValue(this, Espresso.LOGIN, "0"));
			//tof: rechargement des données
			cliToSave.ZONE = m_stCurZone ;
			/*cliToSave.JOURPASSAGE = SQLRequestHelper.getCodeTournee(Fonctions
					.getYYYYMMDD());*/
			cliToSave.JOURPASSAGE = "J";
			m_CreationProspect="C";
			cliToSave.TYPE="P";
			
			m_bCreat=true;
			checkMiseStandBy.setVisibility(View.VISIBLE);
			bDate.setVisibility(View.VISIBLE);
			TexteDateconcurrent.setVisibility(View.VISIBLE);

			TexteVolumecafe.setVisibility(View.VISIBLE);
			EditVolumecafe.setVisibility(View.VISIBLE);

			TexteModereglement.setVisibility(View.GONE);
			spModereglement.setVisibility(View.GONE);

			findAdresse();
		}
		else
		{
			TexteModereglement.setVisibility(View.VISIBLE);
			spModereglement.setVisibility(View.VISIBLE);
			devinerAdresseAllowed=false;
			Global.dbClient .getClient(numprospect, cliToSave, new StringBuilder());
		}
		m_stCurJourPassage = cliToSave.JOURPASSAGE ;
		setEditViewText(this,R.id.EditCodeProspect,numProspect);
		m_stInfoterrain=Fonctions.GetStringDanem(cliToSave.INFO_TERRAIN);


		this.setEditViewText(this,R.id.EditRaisonsocial,cliToSave.NOM);
		this.setEditViewText(this,R.id.EditNom,cliToSave.CONTACT_NOM);
		this.setEditViewText(this,R.id.EditContactemail,cliToSave.EMAIL);

		this.setEditViewText(this,R.id.EditAdresse1,cliToSave.ADR1);
		this.setEditViewText(this,R.id.EditAdresse2,cliToSave.ADR2);

		this.setEditViewText(this,R.id.EditCodepostal,cliToSave.CP);
		this.setEditViewText(this,R.id.EditVille,cliToSave.VILLE);
		fillPays(Fonctions.GetStringDanem(cliToSave.PAYS));
		this.setEditViewText(this,R.id.EditTelephone,cliToSave.TEL1);
		this.setEditViewText(this,R.id.EditFax,cliToSave.FAX);

		this.setEditViewText(this,R.id.EditSiret,cliToSave.SIRET);
		this.setEditViewText(this,R.id.EditNumTVA,cliToSave.NUMTVA);
		this.setEditViewText(this,R.id.EditCommentaire,cliToSave.COMMENT);
		this.setEditViewText(this,R.id.EditVolumecafe,cliToSave.VOL_CAFE_ANNUEL);


		fillZone(Fonctions.GetStringDanem(cliToSave.ZONE));
		fillJourPassage(Fonctions.GetStringDanem(cliToSave.JOURPASSAGE));
		fillCatCompta(Fonctions.GetStringDanem(cliToSave.CATCOMPT));
		fillTypePDV(Fonctions.GetStringDanem(cliToSave.ICON));
		fillTypeEtablissement(Fonctions.GetStringDanem(cliToSave.TYPEETABLISSEMENT));
		fillQualif(Fonctions.GetStringDanem(cliToSave.QUALIF));
        fillSituation(Fonctions.GetStringDanem(cliToSave.SITUATION));
		fillOption(Fonctions.GetStringDanem(cliToSave.OPTION_P));
		fillTypeCuisine(Fonctions.GetStringDanem(cliToSave.TYPECUISINE));

		//spinnerTypeEtablissement
		this.setEditViewText(this,R.id.EditContactgsm,cliToSave.GSM);
		this.setEditViewText(this,R.id.EditEnseigne,cliToSave.ENSEIGNE);

		fillType(Fonctions.GetStringDanem(cliToSave.TYPE));
		fillGroupeclient(Fonctions.GetStringDanem(cliToSave.GROUPECLIENT));
		
		if(Fonctions.GetStringDanem(cliToSave.CLI_AGENT2).equals(""))
		{
			//fillAgent(Fonctions.GetStringDanem(Preferences.getValue(this, Espresso.LOGIN, "0")));
			fillAgent("");
		}
		else
			fillAgent(Fonctions.GetStringDanem(cliToSave.CLI_AGENT2));
		fillCircuit(Fonctions.GetStringDanem(cliToSave.CIRCUIT));
		fillJourfermeture(Fonctions.GetStringDanem(cliToSave.JOURFERMETURE));
		fillModereglement(Fonctions.GetStringDanem(cliToSave.MODEREGLEMENT));
		fillSAV(Fonctions.GetStringDanem(cliToSave.TYPESAV));
		fillPeriodicite(Fonctions.GetStringDanem(cliToSave.PERIODICITE));
		fillTournee(Fonctions.GetStringDanem(cliToSave.TOURNEE));
		fillAnnuel(Fonctions.GetStringDanem(cliToSave.CLI_ANNUEL));
		
		fillSaisonEte(Fonctions.GetStringDanem(cliToSave.SAISON_ETE));
		fillSaisonHiver(Fonctions.GetStringDanem(cliToSave.SAISON_HIVER));
		fillActiviteP(Fonctions.GetStringDanem(cliToSave.ACTIVITE_P));
		fillClass(Fonctions.GetStringDanem(cliToSave.CLASSE));
		
		fillFamclient(Fonctions.GetStringDanem(cliToSave.FAMCLIENT),Fonctions.GetStringDanem(cliToSave.CLASSE));
		
		fillSFamclient(Fonctions.GetStringDanem(cliToSave.SFAMCLIENT),Fonctions.GetStringDanem(cliToSave.FAMCLIENT),Fonctions.GetStringDanem(cliToSave.CLASSE));
		
		//StandBy
		//
		
		if(Fonctions.GetStringDanem(cliToSave.STANDBY).equals("1"))
		{
			checkMiseStandBy.setChecked(true);
			m_stTandby="1";
		}
		else
			checkMiseStandBy.setChecked(false);

		if(Fonctions.GetStringDanem(cliToSave.CLI_PASMAIL).equals("1"))
		{
			checkPasdemail.setChecked(true);
			m_pasemail=true;
		}
		else
		{
			checkPasdemail.setChecked(false);
			m_pasemail=false;
		}

		if(Fonctions.GetStringDanem(cliToSave.ENVOIFACT_PAR_MAIL).equals("1") ||Fonctions.GetStringDanem(cliToSave.ENVOIFACT_PAR_MAIL).equals("O") )
		{
			checkFacEmail.setChecked(true);
		}
		else
		{
			checkFacEmail.setChecked(false);
		}

		//Date
		String stdate="";
		stdate=Fonctions.GetStringDanem(cliToSave.DATECLI);
		if(stdate.equals(""))
		{
			//bDate.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()));	
			bDate.setText("Sélection de la date de fin de contrat");	
		}
		else
		  bDate.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(stdate));
		
		
		double dvaleur=0;
		String stvaleur="";
		dvaleur=Fonctions.GetStringToDoubleDanem(cliToSave.MONTANTTOTALENCOURS);
		stvaleur=Fonctions.GetDoubleToStringFormatDanem(dvaleur, "0.00");
		this.setEditViewText(this,R.id.EditEncours,stvaleur);
		
		dvaleur=Fonctions.GetStringToDoubleDanem(cliToSave.MONTANTTOTALFACTURESDUES);
		stvaleur=Fonctions.GetDoubleToStringFormatDanem(dvaleur, "0.00");
		this.setEditViewText(this,R.id.EditFacturesdues,stvaleur);
		
		dvaleur=Fonctions.GetStringToDoubleDanem(cliToSave.MONTANTTOTALAVOIR);
		stvaleur=Fonctions.GetDoubleToStringFormatDanem(dvaleur, "0.00");
		this.setEditViewText(this,R.id.EditAvoirdispo,stvaleur);
		
		dvaleur=Fonctions.GetStringToDoubleDanem(cliToSave.MONTANTTOTALPAIEMENT);
		stvaleur=Fonctions.GetDoubleToStringFormatDanem(dvaleur, "0.00");
		this.setEditViewText(this,R.id.EditAvancepaie,stvaleur);

		//
		///
		/// /
        this.setEditViewText(this,R.id.EditChiffreAffaires,Fonctions.GetStringDanem(cliToSave.POT_CA));
        this.setEditViewText(this,R.id.EditPlaceAssInt,Fonctions.GetStringDanem(cliToSave.PLACEASSINT));
        this.setEditViewText(this,R.id.EditPlaceAssExt,Fonctions.GetStringDanem(cliToSave.PLACEASSEXT));
        this.setEditViewText(this,R.id.EditCapacite_sdr,Fonctions.GetStringDanem(cliToSave.CAPACITE_SDR));
        this.setEditViewText(this,R.id.EditNb_Chambres,Fonctions.GetStringDanem(cliToSave.NB_CHAMBRES));
        this.setEditViewText(this,R.id.EditNb_Lits,Fonctions.GetStringDanem(cliToSave.NB_LITS));
        this.setEditViewText(this,R.id.EditQualif,Global.dbParam.getCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_QUAL,Fonctions.GetStringDanem(cliToSave.QUALIF)));
        this.setEditViewText(this,R.id.EditSituation,Global.dbParam.getCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_SITUATION,Fonctions.GetStringDanem(cliToSave.SITUATION)));
        this.setEditViewText(this,R.id.EditOption,Global.dbParam.getCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_OPTION,Fonctions.GetStringDanem(cliToSave.OPTION_P)));
        this.setEditViewText(this,R.id.EditTypeCuisine,Global.dbParam.getCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_TYPECUISINE,Fonctions.GetStringDanem(cliToSave.TYPECUISINE)));
        this.setEditViewText(this,R.id.EditPV_CAFE,Fonctions.GetStringDanem(cliToSave.PV_CAFE));
        this.setEditViewText(this,R.id.EditPV_THE,Fonctions.GetStringDanem(cliToSave.PV_THE));
        this.setEditViewText(this,R.id.EditPV_CHOCOLAT,Fonctions.GetStringDanem(cliToSave.PV_CHOCOLAT));
        this.setEditViewText(this,R.id.EditPV_PETIT_DEJ,Fonctions.GetStringDanem(cliToSave.PV_PETIT_DEJ));
        this.setEditViewText(this,R.id.EditPV_CHAMBRE,Fonctions.GetStringDanem(cliToSave.PV_CHAMBRE));


		//on retient la longitude et latitude
		latitude=cliToSave.LAT;
		longitude=cliToSave.LON;
		this.setEditViewText(this,R.id.EditLat,latitude);
		this.setEditViewText(this,R.id.EditLon,longitude);
		
		m_stvaleurDebut=Fonctions.GetStringDanem(cliToSave.PERIODICITE)+Fonctions.GetStringDanem(cliToSave.TOURNEE)+Fonctions.GetStringDanem(cliToSave.JOURFERMETURE)+Fonctions.GetStringDanem(cliToSave.TYPEETABLISSEMENT)+Fonctions.GetStringDanem(cliToSave.SIRET)+Fonctions.GetStringDanem(cliToSave.CLI_ANNUEL)+Fonctions.GetStringDanem(cliToSave.SAISON_ETE)+Fonctions.GetStringDanem(cliToSave.SAISON_HIVER)+Fonctions.GetStringDanem(cliToSave.FAMCLIENT)+Fonctions.GetStringDanem(cliToSave.SFAMCLIENT)+Fonctions.GetStringDanem(cliToSave.EMAIL)+Fonctions.GetStringDanem(cliToSave.DATECLI)+Fonctions.GetStringDanem(cliToSave.CLASSE)+Fonctions.GetStringDanem(cliToSave.CLI_PASMAIL)+Fonctions.GetStringDanem(cliToSave.GSM)+Fonctions.GetStringDanem(cliToSave.INFO_TERRAIN)+Fonctions.GetStringDanem(cliToSave.ENVOIFACT_PAR_MAIL);
		m_stvaleurDebutP=Fonctions.GetStringDanem(cliToSave.PERIODICITE)+Fonctions.GetStringDanem(cliToSave.TOURNEE)+Fonctions.GetStringDanem(cliToSave.JOURFERMETURE)+Fonctions.GetStringDanem(cliToSave.TYPEETABLISSEMENT)+Fonctions.GetStringDanem(cliToSave.SIRET)+Fonctions.GetStringDanem(cliToSave.CLI_ANNUEL)+Fonctions.GetStringDanem(cliToSave.SAISON_ETE)+Fonctions.GetStringDanem(cliToSave.SAISON_HIVER)+Fonctions.GetStringDanem(cliToSave.FAMCLIENT)+Fonctions.GetStringDanem(cliToSave.SFAMCLIENT)+Fonctions.GetStringDanem(cliToSave.EMAIL)+Fonctions.GetStringDanem(cliToSave.STANDBY)+Fonctions.GetStringDanem(cliToSave.DATECLI)+Fonctions.GetStringDanem(cliToSave.CLASSE)+Fonctions.GetStringDanem(cliToSave.CLI_PASMAIL)+Fonctions.GetStringDanem(cliToSave.GSM)+Fonctions.GetStringDanem(cliToSave.VOL_CAFE_ANNUEL)+Fonctions.GetStringDanem(cliToSave.TEL1)
		+Fonctions.GetStringDanem(cliToSave.POT_CA)+Fonctions.GetStringDanem(cliToSave.PLACEASSINT)+Fonctions.GetStringDanem(cliToSave.PLACEASSEXT)+Fonctions.GetStringDanem(cliToSave.CAPACITE_SDR)+Fonctions.GetStringDanem(cliToSave.NB_CHAMBRES)+Fonctions.GetStringDanem(cliToSave.NB_LITS)+Fonctions.GetStringDanem(cliToSave.QUALIF)+Fonctions.GetStringDanem(cliToSave.SITUATION)+Fonctions.GetStringDanem(cliToSave.OPTION_P)+Fonctions.GetStringDanem(cliToSave.TYPECUISINE)+Fonctions.GetStringDanem(cliToSave.PV_CAFE)+Fonctions.GetStringDanem(cliToSave.PV_THE)+Fonctions.GetStringDanem(cliToSave.PV_CHOCOLAT)+Fonctions.GetStringDanem(cliToSave.PV_PETIT_DEJ)+Fonctions.GetStringDanem(cliToSave.PV_CHAMBRE)+Fonctions.GetStringDanem(cliToSave.INFO_TERRAIN);

		
		checkFacEmail.setVisibility(View.VISIBLE);
		checkPasdemail.setVisibility(View.VISIBLE);
		//Email demandé pour tous
		EEmail.setEnabled(true);
		TextViewEmail.setTextColor(Color.RED);
		bEmail = true;

		//Si en mode creation, possibilité de saisir les champs suivant
		if(m_bCreat==true)
		{
			bAgent=true;
			
			checkPasdemail.setVisibility(View.VISIBLE);
			checkFacEmail.setVisibility(View.GONE);
			
			//Raison sociale
			ERaisonSocial.setEnabled(true);
			
			//TextViewRaisonsocial.setTextColor(Color.RED);
			//bRaisonSocial=true ;
			
			
			//Enseigne
			EditEnseigne.setEnabled(true);
			TexteEnseigne.setTextColor(Color.RED);
			bEnseigne=true;
			
			//Adresse1
			EAdresse1.setEnabled(true);
			TextViewAdresse1.setTextColor(Color.RED);
			bAdresse1 = true;
			
			//Code postal
			Ecodepostal.setEnabled(true);
			TextViewcodepostal.setTextColor(Color.RED);
			bcodepostal = true;
			
			//Ville
			Eville.setEnabled(true);
			TextViewville.setTextColor(Color.RED);
			bville=true;
			
			//Téléphone
			ETel.setEnabled(true);
			TextViewTel.setTextColor(Color.RED);
			bTel=true;

			//ETel.setEnabled(true);
			TexteVolumecafe.setTextColor(Color.RED);
			bVolumeCafe=true;

			//Fax
			EFax.setEnabled(true);
			
			//Mobile
			eContactgsm.setEnabled(true);

			//Email
			EEmail.setEnabled(true);
			TextViewEmail.setTextColor(Color.RED);
			bEmail = true;
			
			//Siret
			ESiret.setEnabled(true);
			//TextViewSiret.setTextColor(Color.RED);
			//bSiret = true;
			
		
			//Numero de TVA
			ENumTVA.setEnabled(true);
				
			bTypeEtablissement=true;
			
			bActiviteP=false;
			
			TexteClasse.setTextColor(Color.BLACK);
			TexteFamclient.setTextColor(Color.BLACK);
			bFamclient=false;
			
			TexteSFamclient.setTextColor(Color.BLACK);
			bSFamclient=false;
			
			TexteAnnuel.setTextColor(Color.BLACK);
			bAnnuel=false;
			
			TexteSaisonEte.setTextColor(Color.BLACK);
			bSaisonEte=false;
			
			TexteSaisonHiver.setTextColor(Color.BLACK);
			bSaisonHiver=false;
			
			
			//Fermé le
			//Périodicité
			//Tournée
			TexteJourFerm.setTextColor(Color.BLACK);
			TextePeriodicite.setTextColor(Color.BLACK);
			TexteTournee.setTextColor(Color.BLACK);
			spinnerAnnuel.setEnabled(true);
			
			spinnerSaisonEte.setEnabled(true);
			spinnerSaisonHiver.setEnabled(true);
			
			
			TexteAgent.setVisibility(View.VISIBLE);
			spinnerAgent.setVisibility(View.VISIBLE);
			TexteAgent.setTextColor(Color.RED);
			bAgent=true;

			TexteChiffreAffaires.setVisibility(View.VISIBLE);
			EditChiffreAffaires.setVisibility(View.VISIBLE);
			TextePlaceAssInt.setVisibility(View.VISIBLE);
			EditPlaceAssInt.setVisibility(View.VISIBLE);
			TextePlaceAssExt.setVisibility(View.VISIBLE);
			EditPlaceAssExt.setVisibility(View.VISIBLE);
			TexteCapacite_sdr.setVisibility(View.VISIBLE);
			EditCapacite_sdr.setVisibility(View.VISIBLE);
			TexteNb_Chambres.setVisibility(View.VISIBLE);
			EditNb_Chambres.setVisibility(View.VISIBLE);
			TexteNb_Lits.setVisibility(View.VISIBLE);
			EditNb_Lits.setVisibility(View.VISIBLE);
			TexteQualif.setVisibility(View.VISIBLE);
			EditQualif.setVisibility(View.GONE);
			spinnerQualif.setVisibility(View.VISIBLE);
			TexteSituation.setVisibility(View.VISIBLE);
			EditSituation.setVisibility(View.GONE);
			spinnerSituation.setVisibility(View.VISIBLE);
			TexteOption.setVisibility(View.VISIBLE);
			EditOption.setVisibility(View.GONE);
			spinnerOption.setVisibility(View.VISIBLE);
			TexteTypeCuisine.setVisibility(View.VISIBLE);
			EditTypeCuisine.setVisibility(View.GONE);
			spinnerTypeCuisine.setVisibility(View.VISIBLE);
			TextePV_CAFE.setVisibility(View.VISIBLE);
			EditPV_CAFE.setVisibility(View.VISIBLE);
			TextePV_THE.setVisibility(View.VISIBLE);
			EditPV_THE.setVisibility(View.VISIBLE);
			TextePV_CHOCOLAT.setVisibility(View.VISIBLE);
			EditPV_CHOCOLAT.setVisibility(View.VISIBLE);
			TextePV_PETIT_DEJ.setVisibility(View.VISIBLE);
			EditPV_PETIT_DEJ.setVisibility(View.VISIBLE);
			TextePV_CHAMBRE.setVisibility(View.VISIBLE);
			EditPV_CHAMBRE.setVisibility(View.VISIBLE);

		}
		else
		{
			
			if(cliToSave.TYPE.equals("P"))
			{

                TexteChiffreAffaires.setVisibility(View.VISIBLE);
                EditChiffreAffaires.setVisibility(View.VISIBLE);
				TextePlaceAssInt.setVisibility(View.VISIBLE);
                EditPlaceAssInt.setVisibility(View.VISIBLE);
				TextePlaceAssExt.setVisibility(View.VISIBLE);
                EditPlaceAssExt.setVisibility(View.VISIBLE);
				TexteCapacite_sdr.setVisibility(View.VISIBLE);
                EditCapacite_sdr.setVisibility(View.VISIBLE);
				TexteNb_Chambres.setVisibility(View.VISIBLE);
                EditNb_Chambres.setVisibility(View.VISIBLE);
				TexteNb_Lits.setVisibility(View.VISIBLE);
                EditNb_Lits.setVisibility(View.VISIBLE);
				TexteQualif.setVisibility(View.VISIBLE);
                EditQualif.setVisibility(View.GONE);
				spinnerQualif.setVisibility(View.VISIBLE);
                TexteSituation.setVisibility(View.VISIBLE);
                EditSituation.setVisibility(View.GONE);
				spinnerSituation.setVisibility(View.VISIBLE);
                TexteOption.setVisibility(View.VISIBLE);
                EditOption.setVisibility(View.GONE);
				spinnerOption.setVisibility(View.VISIBLE);
                TexteTypeCuisine.setVisibility(View.VISIBLE);
                EditTypeCuisine.setVisibility(View.GONE);
				spinnerTypeCuisine.setVisibility(View.VISIBLE);
                TextePV_CAFE.setVisibility(View.VISIBLE);
                EditPV_CAFE.setVisibility(View.VISIBLE);
				TextePV_THE.setVisibility(View.VISIBLE);
                EditPV_THE.setVisibility(View.VISIBLE);
				TextePV_CHOCOLAT.setVisibility(View.VISIBLE);
                EditPV_CHOCOLAT.setVisibility(View.VISIBLE);
				TextePV_PETIT_DEJ.setVisibility(View.VISIBLE);
                EditPV_PETIT_DEJ.setVisibility(View.VISIBLE);
				TextePV_CHAMBRE.setVisibility(View.VISIBLE);
                EditPV_CHAMBRE.setVisibility(View.VISIBLE);
				checkFacEmail.setVisibility(View.GONE);

                TexteModereglement.setVisibility(View.GONE);
				spModereglement.setVisibility(View.GONE);
				TexteVolumecafe.setVisibility(View.VISIBLE);
				EditVolumecafe.setVisibility(View.VISIBLE);

				TexteAgent.setVisibility(View.VISIBLE);
				spinnerAgent.setVisibility(View.VISIBLE);
				bAgent=true;
				TexteAgent.setTextColor(Color.RED);
				checkMiseStandBy.setVisibility(View.VISIBLE);
				bDate.setVisibility(View.VISIBLE);
				TexteDateconcurrent.setVisibility(View.VISIBLE);

				TexteVolumecafe.setTextColor(Color.RED);
				bVolumeCafe=true;

				TexteBooking.setVisibility(View.VISIBLE);
				EditBooking.setVisibility(View.VISIBLE);
				TexteTripAdvisor.setVisibility(View.VISIBLE);
				EditTripAdvisor.setVisibility(View.VISIBLE);

				EditBooking.setText(cliToSave.BOOK);
				EditTripAdvisor.setText(cliToSave.TRIPADVISOR);
				//TexteAnnuel.setVisibility(View.GONE);
				//spinnerAnnuel.setVisibility(View.GONE);
				//TexteSaisonEte.setVisibility(View.GONE);
				//TexteSaisonHiver.setVisibility(View.GONE);
				//spinnerSaisonEte.setVisibility(View.GONE);
				//spinnerSaisonHiver.setVisibility(View.GONE);
				bRaisonSocial=false;
				
				
				
				bActiviteP=false;
			
				TexteClasse.setTextColor(Color.BLACK);
				TexteFamclient.setTextColor(Color.BLACK);
				bFamclient=false;
				
				TexteSFamclient.setTextColor(Color.BLACK);
				bSFamclient=false;
				
				TexteAnnuel.setTextColor(Color.BLACK);
				bAnnuel=false;
				
				TexteSaisonEte.setTextColor(Color.BLACK);
				bSaisonEte=false;
				
				TexteSaisonHiver.setTextColor(Color.BLACK);
				bSaisonHiver=false;

				ERaisonSocial.setEnabled(true);
				EditEnseigne.setEnabled(true);	
				//enable
				EAdresse1.setEnabled(true);
				Ecodepostal.setEnabled(true);
				Eville.setEnabled(true);
				ETel.setEnabled(true);
				EFax.setEnabled(true);
				eContactgsm.setEnabled(true);
				EEmail.setEnabled(false);
				ESiret.setEnabled(true);
				
				if(m_btech==true)
				{
					EAdresse1.setEnabled(false);
					Ecodepostal.setEnabled(false);
					Eville.setEnabled(false);
					ETel.setEnabled(false);
					EFax.setEnabled(false);
					eContactgsm.setEnabled(true);
					EEmail.setEnabled(false);
					ESiret.setEnabled(false);
					
					TexteTypeEtablissement.setTextColor(Color.BLACK);
					TexteActiviteP.setTextColor(Color.BLACK);
					TexteClasse.setTextColor(Color.BLACK);
					TexteFamclient.setTextColor(Color.BLACK);
					TexteSFamclient.setTextColor(Color.BLACK);

					TexteJourFerm.setTextColor(Color.BLACK);
					TextePeriodicite.setTextColor(Color.BLACK);
					TexteTournee.setTextColor(Color.BLACK);
					ENumTVA.setEnabled(false);
					spTypeEtablissement.setEnabled(false);
					spActiviteP.setEnabled(false);
					spClasse.setEnabled(false);
					spFamclient.setEnabled(false);
					spSFamclient.setEnabled(false);

					spJourFerm.setEnabled(false);
					spPeriodicite.setEnabled(false);
					spTournee.setEnabled(false);
					bActiviteP=false;
					bFamclient=true;
					//bSFamclient=true;
				}
				if(Fonctions.GetStringDanem(cliToSave.STD_BY).equals("1"))
					checkMiseStandBy.setChecked(true);

			}
			else
			{
				TexteAgent.setVisibility(View.GONE);
				spinnerAgent.setVisibility(View.GONE);
				
				//TexteAnnuel.setVisibility(View.GONE);
				//spinnerAnnuel.setVisibility(View.GONE);
				//TexteSaisonEte.setVisibility(View.GONE);
				//TexteSaisonHiver.setVisibility(View.GONE);
				//spinnerSaisonEte.setVisibility(View.GONE);
				//spinnerSaisonHiver.setVisibility(View.GONE);
				bActiviteP=false;
				//bActiviteP=true;
				bFamclient=true;
				bSFamclient=true;
				TexteActiviteP.setTextColor(Color.BLACK);
				bAnnuel=true;
				bSaisonEte=true;
				bSaisonHiver=true;

				ERaisonSocial.setEnabled(false);
				EditEnseigne.setEnabled(false);	
				EAdresse1.setEnabled(false);
				Ecodepostal.setEnabled(false);
				Eville.setEnabled(false);
				ETel.setEnabled(false);
				EFax.setEnabled(false);
				eContactgsm.setEnabled(true);
				EEmail.setEnabled(true);
				ESiret.setEnabled(false);
				
				TextViewNumTVA.setVisibility(View.GONE);
				ENumTVA.setVisibility(View.GONE);
				spClasse.setEnabled(false);
				spFamclient.setEnabled(false);
				spSFamclient.setEnabled(false);

				TexteQualif.setVisibility(View.VISIBLE);
				EditQualif.setVisibility(View.GONE);
				spinnerQualif.setVisibility(View.VISIBLE);
				TexteSituation.setVisibility(View.VISIBLE);
				EditSituation.setVisibility(View.GONE);
				spinnerSituation.setVisibility(View.VISIBLE);
				TexteOption.setVisibility(View.VISIBLE);
				EditOption.setVisibility(View.GONE);
				spinnerOption.setVisibility(View.VISIBLE);
				TexteTypeCuisine.setVisibility(View.VISIBLE);
				EditTypeCuisine.setVisibility(View.GONE);
				spinnerTypeCuisine.setVisibility(View.VISIBLE);

				if(m_btech==true)
				{
					TexteTypeEtablissement.setTextColor(Color.BLACK);
					TexteClasse.setTextColor(Color.BLACK);
					TexteFamclient.setTextColor(Color.BLACK);
					TexteSFamclient.setTextColor(Color.BLACK);
					
					TexteJourFerm.setTextColor(Color.BLACK);
					TextePeriodicite.setTextColor(Color.BLACK);
					TexteTournee.setTextColor(Color.BLACK);
					ENumTVA.setEnabled(false);
					spTypeEtablissement.setEnabled(false);
					spActiviteP.setEnabled(false);
					spClasse.setEnabled(false);
					spFamclient.setEnabled(false);
					spSFamclient.setEnabled(false);
					
					spJourFerm.setEnabled(false);
					spPeriodicite.setEnabled(false);
					spTournee.setEnabled(false);
					
					bActiviteP=false;
					bFamclient=false;
					bSFamclient=false;
					
					bAnnuel=false;
					bSaisonEte=false;
					bSaisonHiver=false;

				}
				
				
			}

		
		}


	}
	
	void showAlertDlg()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.prospect_problemenumauto));
		alertDialog.setButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				finish(0);

			} });
		alertDialog.show();

	}
	public void setNumProspect(String num)
	{
		numProspect=num;
		if (Fonctions.GetStringDanem(numProspect).equals(""))
		{
			numProspect=Global.dbClient.GetCodeProspect(Preferences.getValue(this, Espresso.LOGIN, "0"));
		}

		setEditViewText(this,R.id.EditCodeProspect,numProspect);
	}
	void showProgressDialog()
	{
		m_ProgressDialog = ProgressDialog.show(this,
				"Veuillez patienter", "traitement en cours", true);
	}
	public void hideProgressDialog()
	{
		if (m_ProgressDialog!=null)
			m_ProgressDialog.dismiss();
	}

	private void initActionBar(){
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(R.string.prospect_titre);
	}
	@Override
	protected void onStart() {
		super.onStart();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		addMenu(menu, android.R.string.cancel, android.R.drawable.ic_menu_delete);
		if ( bBlockSave == false )
			addMenu(menu, android.R.string.ok, android.R.drawable.ic_menu_save);

		if (m_bprendrecde)
			addMenu(menu, R.string.prospect_prendrecde,android.R.drawable.ic_media_next);
		//if (devinerAdresseAllowed)
		//	addMenu(menu, R.string.prospect_positionner,R.drawable.action_map);

		addMenu(menu,R.string.infos_terrain,android.R.drawable.ic_dialog_info);

		//addMenu(menu, R.string.commande_quest,android.R.drawable.ic_menu_help);
		if(m_bCreat==false)
		  addMenu(menu, R.string.geocode_prospect,android.R.drawable.ic_menu_mylocation);
		return true;
	}



	private void record(ArrayList<String> ValueMessage, StringBuffer buff){


		if(ControleAvantSauvegarde(ValueMessage,buff)==true)
		{
			if(1==1 || gecodeClient == null){
				if (save(false,buff)==true)
				{
					//FurtiveMessageBox("prospect sauvegardÃ©");
				}
				else
				{
					Fonctions.FurtiveMessageBox(this,buff.toString());
				}
			}
			else{
				Fonctions.FurtiveMessageBox(this,getString(R.string.geocoding_in_progress)+getString(R.string.menu_veuillezpatienter));
			}
		}
		else
			Fonctions.FurtiveMessageBox(this,Fonctions.GetStringDanem( ValueMessage.get(0) ));
		//		this.finish();


	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		StringBuffer buff = new StringBuffer();
		ArrayList<String> ValueMessage=new ArrayList();
		switch (item.getItemId()) {
		case R.string.prospect_positionner:

			findAdresse();

			return true;
		case android.R.string.cancel:
			Cancel();
			return true;

		case  android.R.string.ok:
			record(ValueMessage, buff);
			return true;

			case R.string.infos_terrain:

				launchDialog(numProspect);

			return true;

		case R.string.prospect_prendrecde://si on veut prendre commande avant m�me de remplir les coord
			//on sauve le prospect (obligŽ pour que tt fonctionne)
			//on va en prise de cde
			//on revient et on contr™le
			//comme c'est une sauvegarde forcŽe on donne un nom au client par dŽfaut si non renseignŽ
			if(this.getEditViewText(this,R.id.EditRaisonsocial).equals(""))
			{
				this.setEditViewText(this, R.id.EditRaisonsocial, getString(R.string.prospect_nomdefaut)) ;
			}
			if (save(true,new StringBuffer()))
			{
				launchCde();
			}
			break;
		/*case R.string.commande_quest:
			MenuPopup.launchQuest(this,numProspect,"","");
			break;*/
		case R.string.geocode_prospect:
			String adr1 = cliToSave.ADR1.trim();
			String adr2 = cliToSave.ADR2.trim();
			String cp = cliToSave.CP.trim();
			String ville = cliToSave.VILLE.trim();
			String pays = cliToSave.PAYS.trim();
			//String departement = getDepartement(cp);	
			String adress = adr1+" "+adr2+" "+cp+" "+ville+" "+pays;
			/*getLatLongFromAddress(adr1+" "+adr2+" "+ville+" "+departement);*/
			Intent i = new Intent(this, GeolocalisationClient.class);
			Bundle bundle = new Bundle();
			bundle.putString("adress_client", adress);
			i.putExtras(bundle);
			startActivityForResult(i, LAUNCH_GEOLOCALISATION);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}
	void launchCde()
	{
		Intent intent = new Intent(this,		commandeActivity.class);
		Bundle b=new Bundle();
		b.putString("codecli",numProspect);
		b.putString("soccode",getSelectedSocCode() );
		intent.putExtras(b);
		startActivityForResult(intent,LAUNCH_ENTETECDE);
	}

	void launchDialog(final String numProspect)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ficheclient.this);
		alertDialog.setTitle("Infos Terrain");

		//Global.dbClient .getClient(numProspect, cliToSave, new StringBuilder());

		final EditText input = new EditText(ficheclient.this);
		int maxLength = 250;
		input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
		input.setText(m_stInfoterrain);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		alertDialog.setView(input);

		alertDialog.setPositiveButton("Valider",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//TableClient cli=new TableClient(m_appState.m_db);
					//cli.updateInfosTerrain(input.getText().toString(),numProspect);
					m_stInfoterrain=Fonctions.GetStringDanem(input.getText().toString());
					//Loadinformation(numProspect);
				}
			});

		alertDialog.setNegativeButton("Annuler",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

		alertDialog.show();
	}


	void Cancel()
	{
		if (cancelAllowed==false)
		{
			Fonctions.FurtiveMessageBox(this,getString(R.string.prospect_impossibledannuler));
			return;
		}
		//si on a des cdes pour ce prospect on ne peut pas annuler
		/*		if (Global.dbKDEntCde.Count(getCodeClient())>0)
		{
			Fonctions.FurtiveMessageBox(this,getString(R.string.prospect_impossibledannuler));
			return ;
		}
		 */
		//si on a deja crŽe le prospect parcequ'on avait ŽtŽ en cde on l'efface
		if (Global.dbClient.load(getCodeClient()).CREAT.equals(Global.dbClient.CLI_CREATION) && Global.dbKDEntCde.Count(getCodeClient())==0)
			Global.dbClient.delete(getCodeClient());
		this.finish(0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//on revient de la cde, si on a pris une commande on ne peut pas annuler le prospect
		//on recharge la fiche aussi car peut �tre modifiŽe dans la cde
		if(requestCode==LAUNCH_ENTETECDE )
		{
			Loadinformation( this.numProspect );

			//si la cde a été passée on enleve le bouton cancel
			if (resultCode==Activity.RESULT_OK)
			{
				cancelAllowed=false;
			}
		}

		if(requestCode==LAUNCH_GEOLOCALISATION){
			if(resultCode==RESULT_OK){
				//TODO on get latitude et longitude et on enregistre
				if(data != null){
					Bundle b = data.getExtras();
					if(b != null && b.getBoolean("exists_result")){
						Double la = Double.parseDouble(b.getString("latitude"));
						Double lo = Double.parseDouble(b.getString("longitude"));
						cliToSave.LAT = la.toString();
						cliToSave.LON = lo.toString();
						this.setEditViewText(this,R.id.EditLat,la.toString());
						this.setEditViewText(this,R.id.EditLon,lo.toString());
					
						Global.dbClient.save(cliToSave, true);
						Global.dbClient.updateLatLon(la.toString() ,lo.toString(),numProspect   );

						Toast.makeText(ficheclient.this, getString(R.string.toast_ficheclient_localisation_ok), Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ficheclient.this, getString(R.string.toast_ficheclient_localisation_nok), Toast.LENGTH_LONG).show();
					}
				}
			}
		}

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

	String getZone()
	{
		String pays="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerZone);
		if (pos > -1)
			try {
				pays = idListZone.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	String getJourPassage()
	{
		String pays="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerJourPassage);
		if (pos > -1)
			try {
				pays = idListJourPassage.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	String getCodeClient()
	{
		try {
			String code=numProspect;
			return code;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	String getPays()
	{
		String pays="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerPays);
		if (pos > -1)
			try {
				pays = idPays.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	
	
	
	String getModereglement()
	{
		String modereglement="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerModereglement);
		if (pos > -1)
			try {
				modereglement = idListModereglement.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return modereglement;
	}
	
	String getCircuit()
	{
		String circuit="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerCircuit);
		if (pos > -1)
			try {
				circuit = idListCircuit.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return circuit;
	}

	String getAgent()
	{
		String agent="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerAgent);
		if (pos > -1)
			try {
				agent = idListAgent.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		return agent;
	}
	
	String getgroupementclient()
	{
		String groupementclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerGroupe);
		if (pos > -1)
			try {
				groupementclient = idListGroupe.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return groupementclient;
	}
	
	String gettypeclient()
	{
		String typeclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerType);
		if (pos > -1)
			try {
				typeclient = idListType.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeclient;
	}
	String gettypeEtablissement()
	{
		String typeclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTypeEtablissement);
		if (pos > -1)
			try {
				typeclient = idListTypeEtablissement.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeclient;
	}
	String getActiviteP()
	{
		String typeclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerActiviteP);
		if (pos > -1)
			try {
				typeclient = idListActiviteP.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeclient;
	}
	String getClasse()
	{
		String typeclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerClasse);
		if (pos > -1)
			try {
				typeclient = idListClasse.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeclient;
	}
	String getQualif()
	{
		String qualif="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerQualif);
		if (pos > -1)
			try {
				qualif = idListQualif.get(pos).getString(
						Global.dbParam.FLD_PARAM_LBL);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return qualif;
	}
    String getSituation()
    {
        String situation="";
        int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerSituation);
        if (pos > -1)
            try {
                situation = idListSituation.get(pos).getString(
                        Global.dbParam.FLD_PARAM_LBL);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        return situation;
    }

	String getFacEmail()
	{
		String isFacEmail="";

		if (checkFacEmail.isChecked()){
			isFacEmail = "1";
		} else {
			isFacEmail = "0";
		}

		return isFacEmail;
	}
	String getOption()
	{
		String option="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerOption);
		if (pos > -1)
			try {
				option = idListOption.get(pos).getString(
						Global.dbParam.FLD_PARAM_LBL);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return option;
	}
	String getTypeCuisine()
	{
		String typeCuisne="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTypeCuisine);
		if (pos > -1)
			try {
				typeCuisne = idListTypeCuisine.get(pos).getString(
						Global.dbParam.FLD_PARAM_LBL);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeCuisne;
	}
	String getFamclient()
	{
		String typeclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerFamclient);
		if (pos > -1)
			try {
				typeclient = idListFamclient.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeclient;
	}
	String getSFamclient()
	{
		String typeclient="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerSFamclient);
		if (pos > -1)
			try {
				typeclient = idListSFamclient.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return typeclient;
	}
	
	String getSAV()
	{
		String sav="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerSAV);
		if (pos > -1)
			try {
				sav = idListSAV.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return sav;
	}
	String getPeriodicite()
	{
		String periodicite="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerPeriodicite);
		if (pos > -1)
			try {
				periodicite = idListPeriodicite.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return periodicite;
	}
	
	String getTournee()
	{
		String tournee="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTournee);
		if (pos > -1)
			try {
				tournee = idListTournee.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return tournee;
	}
	String getAnnuel()
	{
		String annuel="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerAnnuel);
		if (pos > -1)
			try {
				annuel = idListAnnuel.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return annuel;
	}
	
	String getSaisonEte()
	{
		String annuel="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerSaisonEte);
		if (pos > -1)
			try {
				annuel = idListSaisonEte.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return annuel;
	}
	String getSaisonHiver()
	{
		String annuel="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerSaisonHiver);
		if (pos > -1)
			try {
				annuel = idListSaisonHiver.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return annuel;
	}
	
	
	
	
	String getJourfermeture()
	{
		String jourferm="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerJourFerm);
		if (pos > -1)
			try {
				jourferm = idListJourFerm.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return jourferm;
	}
	
	

	void finish(int i)
	{
		SynchroService.setPaused(false);
		finish();
	}
	String getCatCompta()
	{
		String cattarif="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerCatCompta);
		if (pos > -1)
			try {
				cattarif = idListCatCompta.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return cattarif;
	}	

	String getTypePDV()
	{
		String cattarif="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTypePDV);
		if (pos > -1)
			try {
				cattarif = idListTypePdv.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		cattarif=Global.dbClient.getLblIcon(cattarif);
		return cattarif;
	}	
	/**
	 * Creation du menu
	 */
	static final private int MENU_ITEM = Menu.FIRST;

	TableClient.structClient cliToSave;

	public boolean save(boolean temporary,StringBuffer stbuff)
	{
		try
		{
			cliToSave=new TableClient.structClient();

			cliToSave.SOC_CODE=getSelectedSocCode();
			cliToSave.CODE=this.getEditViewText(this,R.id.EditCodeProspect);
			cliToSave.NOM=this.getEditViewText(this,R.id.EditRaisonsocial);
			cliToSave.CONTACT_NOM=this.getEditViewText(this,R.id.EditNom);
			cliToSave.TEL1=this.getEditViewText(this,R.id.EditTelephone);
			cliToSave.EMAIL=this.getEditViewText(this,R.id.EditContactemail);
			cliToSave.CODEVRP=Preferences.getValue(this, Espresso.LOGIN, "0");

			cliToSave.ADR1=this.getEditViewText(this,R.id.EditAdresse1);
			cliToSave.ADR2=this.getEditViewText(this,R.id.EditAdresse2);
			cliToSave.CP=this.getEditViewText(this,R.id.EditCodepostal);
			cliToSave.VILLE=this.getEditViewText(this,R.id.EditVille);
			cliToSave.PAYS=getPays();

			cliToSave.FAX=this.getEditViewText(this,R.id.EditFax);

			cliToSave.SIRET=this.getEditViewText(this,R.id.EditSiret);
			cliToSave.NUMTVA=this.getEditViewText(this,R.id.EditNumTVA);
			cliToSave.COMMENT=this.getEditViewText(this,R.id.EditCommentaire);
			cliToSave.VOL_CAFE_ANNUEL=this.getEditViewText(this,R.id.EditVolumecafe);
			cliToSave.INFO_TERRAIN = m_stInfoterrain;

			//cliToSave.JOURPASSAGE=getJourPassage();
			cliToSave.JOURPASSAGE="J";
			cliToSave.ZONE="Z";//getZone();
			cliToSave.CATCOMPT=getCatCompta();
			if(m_pasemail==true)
			{
				cliToSave.CLI_PASMAIL="1";
			}
			else
				cliToSave.CLI_PASMAIL="";

		//	cliToSave.ICON=getTypePDV();


			//pour les créations
		//	cliToSave.COULEUR=getSAV();//"#ececec";
		//	cliToSave.IMPORTANCE="";
			cliToSave.TOVISIT="1";
			
			//longitude et latitude
			cliToSave.LAT=latitude;
			cliToSave.LON=longitude;
			cliToSave.TYPESAV=getSAV();
			cliToSave.GSM=this.getEditViewText(this,R.id.EditContactgsm);
			cliToSave.ENSEIGNE=this.getEditViewText(this,R.id.EditEnseigne);
			cliToSave.TYPE=gettypeclient();
			cliToSave.GROUPECLIENT				=getgroupementclient();
			cliToSave.AGENT						=getAgent();
			cliToSave.CIRCUIT					=getCircuit();
			cliToSave.JOURFERMETURE				=getJourfermeture();
			cliToSave.MODEREGLEMENT				="";//getModereglement();
			cliToSave.MONTANTTOTALENCOURS		="";
			cliToSave.MONTANTTOTALFACTURESDUES	="";
			cliToSave.MONTANTTOTALAVOIR			="";
			cliToSave.MONTANTTOTALPAIEMENT		="";
			cliToSave.TYPEETABLISSEMENT			=gettypeEtablissement();
			cliToSave.FREETEXT					="";
			cliToSave.EXONERATION				="";
			cliToSave.ETAT						=m_CreationProspect;
			cliToSave.DATECREAT					=Fonctions.getYYYYMMDD();
			cliToSave.PERIODICITE=getPeriodicite();
			cliToSave.TOURNEE=getTournee();
			cliToSave.CLI_ANNUEL=getAnnuel();
			cliToSave.CLI_AGENT2=getAgent();
			cliToSave.SAISON_ETE=getSaisonEte();
			cliToSave.SAISON_HIVER=getSaisonHiver();
			cliToSave.ACTIVITE_P=getActiviteP();
			cliToSave.CLASSE=getClasse();
			cliToSave.FAMCLIENT=getFamclient();
			cliToSave.SFAMCLIENT=getSFamclient();

			cliToSave.STANDBY=m_stTandby;
			String stDate="";
			stDate= bDate.getText().toString();
			if(stDate.equals("") || stDate.contains("fin"))
			{
				stDate="";
			}
			else
			 stDate=Fonctions.dd_mm_yyyy_to_YYYYMMDD(bDate.getText().toString());
			cliToSave.DATECLI=stDate;
			
			cliToSave.CREAT=m_CreationProspect;//rajouté par moi

			//nouveauté
			cliToSave.POT_CA=this.getEditViewText(this,R.id.EditChiffreAffaires);;
			cliToSave.PLACEASSINT=this.getEditViewText(this,R.id.EditPlaceAssInt);;
			cliToSave.PLACEASSEXT=this.getEditViewText(this,R.id.EditPlaceAssExt);;
			cliToSave.CAPACITE_SDR=this.getEditViewText(this,R.id.EditCapacite_sdr);;
			cliToSave.NB_CHAMBRES=this.getEditViewText(this,R.id.EditNb_Chambres);;
			cliToSave.NB_LITS=this.getEditViewText(this,R.id.EditNb_Lits);;
			cliToSave.QUALIF=getQualif();
			cliToSave.SITUATION=getSituation();
			cliToSave.OPTION_P=getOption();
			cliToSave.TYPECUISINE=getTypeCuisine();
			cliToSave.PV_CAFE=this.getEditViewText(this,R.id.EditPV_CAFE);;
			cliToSave.PV_THE=this.getEditViewText(this,R.id.EditPV_THE);;
			cliToSave.PV_CHOCOLAT=this.getEditViewText(this,R.id.EditPV_CHOCOLAT);;
			cliToSave.PV_PETIT_DEJ=this.getEditViewText(this,R.id.EditPV_PETIT_DEJ);;
			cliToSave.PV_CHAMBRE=this.getEditViewText(this,R.id.EditPV_CHAMBRE);;
			cliToSave.STD_BY=m_stTandby;
			cliToSave.INFO_TERRAIN=Fonctions.GetStringDanem(m_stInfoterrain);
			cliToSave.ENVOIFACT_PAR_MAIL=getFacEmail();

			m_stvaleurFin=Fonctions.GetStringDanem(cliToSave.PERIODICITE)+Fonctions.GetStringDanem(cliToSave.TOURNEE)+Fonctions.GetStringDanem(cliToSave.JOURFERMETURE)+Fonctions.GetStringDanem(cliToSave.TYPEETABLISSEMENT)+Fonctions.GetStringDanem(cliToSave.SIRET)+Fonctions.GetStringDanem(cliToSave.CLI_ANNUEL)+Fonctions.GetStringDanem(cliToSave.SAISON_ETE)+Fonctions.GetStringDanem(cliToSave.SAISON_HIVER)+Fonctions.GetStringDanem(cliToSave.FAMCLIENT)+Fonctions.GetStringDanem(cliToSave.SFAMCLIENT)+Fonctions.GetStringDanem(cliToSave.EMAIL)+Fonctions.GetStringDanem(cliToSave.DATECLI)+Fonctions.GetStringDanem(cliToSave.CLASSE)+Fonctions.GetStringDanem(cliToSave.CLI_PASMAIL)+Fonctions.GetStringDanem(cliToSave.GSM)+Fonctions.GetStringDanem(cliToSave.INFO_TERRAIN)+Fonctions.GetStringDanem(cliToSave.ENVOIFACT_PAR_MAIL);

			m_stvaleurFinP=Fonctions.GetStringDanem(cliToSave.PERIODICITE)+Fonctions.GetStringDanem(cliToSave.TOURNEE)+Fonctions.GetStringDanem(cliToSave.JOURFERMETURE)+Fonctions.GetStringDanem(cliToSave.TYPEETABLISSEMENT)+Fonctions.GetStringDanem(cliToSave.SIRET)+Fonctions.GetStringDanem(cliToSave.CLI_ANNUEL)+Fonctions.GetStringDanem(cliToSave.SAISON_ETE)+Fonctions.GetStringDanem(cliToSave.SAISON_HIVER)+Fonctions.GetStringDanem(cliToSave.FAMCLIENT)+Fonctions.GetStringDanem(cliToSave.SFAMCLIENT)+Fonctions.GetStringDanem(cliToSave.EMAIL)+Fonctions.GetStringDanem(cliToSave.STANDBY)+Fonctions.GetStringDanem(cliToSave.DATECLI)+Fonctions.GetStringDanem(cliToSave.CLASSE)+Fonctions.GetStringDanem(cliToSave.CLI_PASMAIL)+Fonctions.GetStringDanem(cliToSave.GSM)+Fonctions.GetStringDanem(cliToSave.VOL_CAFE_ANNUEL)+Fonctions.GetStringDanem(cliToSave.TEL1)
					+Fonctions.GetStringDanem(cliToSave.POT_CA)+Fonctions.GetStringDanem(cliToSave.PLACEASSINT)+Fonctions.GetStringDanem(cliToSave.PLACEASSEXT)+Fonctions.GetStringDanem(cliToSave.CAPACITE_SDR)+Fonctions.GetStringDanem(cliToSave.NB_CHAMBRES)+Fonctions.GetStringDanem(cliToSave.NB_LITS)+Fonctions.GetStringDanem(cliToSave.QUALIF)+Fonctions.GetStringDanem(cliToSave.SITUATION)+Fonctions.GetStringDanem(cliToSave.OPTION_P)+Fonctions.GetStringDanem(cliToSave.TYPECUISINE)+Fonctions.GetStringDanem(cliToSave.PV_CAFE)+Fonctions.GetStringDanem(cliToSave.PV_THE)+Fonctions.GetStringDanem(cliToSave.PV_CHOCOLAT)+Fonctions.GetStringDanem(cliToSave.PV_PETIT_DEJ)+Fonctions.GetStringDanem(cliToSave.PV_CHAMBRE)+Fonctions.GetStringDanem(cliToSave.INFO_TERRAIN);
			
			if(m_bCreat==true)
			{
				save(false);

   				//incremente le numro prospect
				TableSouches souche=new TableSouches(m_appState.m_db,ficheclient.this);
				souche.incNum( Preferences.getValue(ficheclient.this, Espresso.LOGIN, "0"),TableSouches.TYPEDOC_PROSPECT);	
   				
				ficheclient.this.finish();
				
			}
			else
			{
			   if(!m_stvaleurFin.equals(m_stvaleurDebut) && !Fonctions.GetStringDanem(cliToSave.TYPE).equals("P"))
	           {
	   				save(false);

	   				ficheclient.this.finish();
	           }
	           else
	           {
	        	   if(!m_stvaleurFinP.equals(m_stvaleurDebutP) && Fonctions.GetStringDanem(cliToSave.TYPE).equals("P"))
		           {
		   				save(true);

		   				ficheclient.this.finish();
		           }
	        	   else
	        		   Cancel();
	           }
	        	  
			}

			return true;

		}
		catch(Exception ex)
		{
			return false;

		}

	}

	public boolean ControleAvantSauvegarde(ArrayList<String> ValueMessage,StringBuffer stBuf)
	{
		boolean bres=true;

		String stmessage="";

		try
		{			
			if(this.getEditViewText(this,R.id.EditRaisonsocial).equals(""))
			{
				if(bRaisonSocial==true)
				{
					stmessage=""+TextViewRaisonsocial.getText().toString()+" "+getString(R.string.prospect_obligatoire);
					ValueMessage.add(stmessage);
					return false;
				}
			}
			//Enseigne
			if(this.getEditViewText(this,R.id.EditEnseigne).equals(""))
			{
				if(bEnseigne ==true)
				{
					{
						stmessage=""+TexteEnseigne.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(this.getEditViewText(this,R.id.EditAdresse1).equals(""))
			{
				if(bAdresse1==true)
				{
					{
						stmessage=""+TextViewAdresse1.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(this.getEditViewText(this,R.id.EditCodepostal).equals(""))
			{
				if(bcodepostal==true)
				{
					{
						stmessage=""+TextViewcodepostal.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(this.getEditViewText(this,R.id.EditVille).equals(""))
			{
				if(bville==true)
				{
					{
						stmessage=""+TextViewville.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(this.getEditViewText(this,R.id.EditTelephone).equals(""))
			{
				if(bTel==true)
				{
					{
						stmessage=""+TextViewTel.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}

			if(this.getEditViewText(this,R.id.EditContactemail).equals(""))
			{
				if(bEmail==true)
				{
					if(m_pasemail==false)
					{
						stmessage=""+TextViewEmail.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}

			if (m_pasemail==false)
			{
				if (this.getEditViewText(this,R.id.EditContactemail).indexOf("@") == -1)
				{
					stmessage=""+TextViewEmail.getText().toString()+" "+getString(R.string.mail_incorrect);
					ValueMessage.add(stmessage);
					return false;
				}
			}



			if(gettypeEtablissement().equals(""))
			{
				if(bTypeEtablissement ==true)
				{
					{
						stmessage=""+TexteTypeEtablissement.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			/*
			if(getActiviteP().equals(""))
			{
				if(bActiviteP ==true)
				{
					{

						stmessage=""+TexteActiviteP.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			/*if(getFamclient().equals(""))
			{
				if(bFamclient ==true)
				{
					{

						stmessage=""+TexteFamclient.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(getSFamclient().equals(""))
			{
				if(bSFamclient ==true)
				{
					{

						stmessage=""+TexteSFamclient.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}*/
			if(getAgent().equals(""))
			{
				
				if(bAgent ==true)
				{
					{
						stmessage=""+TexteAgent.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(this.getEditViewText(this,R.id.EditVolumecafe).equals(""))
			{
				if(bVolumeCafe==true)
				{
					{
						stmessage=""+TexteVolumecafe.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			
			if(getAnnuel().equals(""))
			{
				if(bAnnuel ==true)
				{
					{
						stmessage=""+TexteAnnuel.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(getSaisonEte().equals(""))
			{
				if(bSaisonEte ==true)
				{
					{
						stmessage=""+TexteSaisonEte.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(getSaisonHiver().equals(""))
			{
				if(bSaisonHiver ==true)
				{
					{
						stmessage=""+TexteSaisonHiver.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}


			if (checkMiseStandBy.isChecked()){
				if(m_btech==false)
				{
					Log.e("m_stInfoterrain","m_stInfoterrain=>"+m_stInfoterrain);
					//Global.dbClient .getClient(numProspect, cliToSave, new StringBuilder());
					if (m_stInfoterrain.equals("")){
						stmessage="Infos Terrain "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}

			
			
			/*if(this.getEditViewText(this,R.id.EditSiret).equals(""))
			{
				if(bSiret==true)
				{
					{
	
						stmessage=""+TextViewSiret.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			
			}*/
			

	/*		
			
			if(!this.getEditViewText(this,R.id.EditNumTVA).equals(""))
			{
				if(this.getEditViewText(this,R.id.EditNumTVA).length()==13)
				{
					if(Fonctions.Left(this.getEditViewText(this,R.id.EditNumTVA), 2).equals("FR")|| Fonctions.Left(this.getEditViewText(this,R.id.EditNumTVA), 2).equals("fr"))
					{
						
					}
					else
					{
						stmessage=getString(R.string.prospect_numtva_13)+" "+TextViewNumTVA.getText().toString();
						ValueMessage.add(stmessage);
						return false;
					}
				}
				else
				{
					stmessage=getString(R.string.prospect_numtva_13)+" "+TextViewNumTVA.getText().toString();
					ValueMessage.add(stmessage);
					return false;
				}
			
			}
			
			if(getgroupementclient().equals(""))
			{
				if(bGroupeclient ==true)
				{
					{

						stmessage=""+TexteGroupe.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(getJourfermeture().equals(""))
			{
				if(bJourFermeture ==true)
				{
					{

						stmessage=""+TexteJourFerm.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(getSAV().equals(""))
			{
				if(bSAV==true)
				{
					{

						stmessage=""+TexteSAV.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}

			if(getModereglement().equals(""))
			{
				if(bModereglement==true)
				{
					{

						stmessage=""+TexteModereglement.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}

			

			if(getTypePDV().equals(""))
			{
				if(bTypePdv ==true)
				{
					{

						stmessage=""+TextViewTypePDV.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			
			if(getZone().equals(""))
			{
				if(bZone ==true)
				{
					{

						stmessage=""+TextViewZone.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
			if(getJourPassage().equals(""))
			{
				if(bJourPassage ==true)
				{
					{

						stmessage=""+TextViewJourPassage.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}

			if(getCatCompta().equals(""))
			{
				if(bCatCompta ==true)
				{
					{

						stmessage=""+TextViewCatCompta.getText().toString()+" "+getString(R.string.prospect_obligatoire);
						ValueMessage.add(stmessage);
						return false;
					}
				}
			}
		*/


		}
		catch(Exception ex)
		{
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;

		}
		return bres;



	}
	void fillPays(String selVal) {
		try {
			if (Global.dbParam.getRecordsFiltreAllSoc(Global.dbParam.PARAM_PAYS,
					this.idPays, "1") == true) {

				int pos = 0;
				String[] items = new String[idPays.size()];
				for (int i = 0; i < idPays.size(); i++) {

					items[i] = idPays.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idPays.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerPays);

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

	void fillCatCompta(String selVal) {
		try {
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_CAT_COMPTA,
					this.idListCatCompta,false) == true) {

				int pos = 0;
				String[] items = new String[idListCatCompta.size()];
				for (int i = 0; i < idListCatCompta.size(); i++) {

					items[i] = idListCatCompta.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListCatCompta.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerCatCompta);


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
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
	
	void fillSAV(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_SAV,
					this.idListSAV,false) == true) {

				int pos = 0;
				String[] items = new String[idListSAV.size()];
				for (int i = 0; i < idListSAV.size(); i++) {

					items[i] = idListSAV.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListSAV.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerSAV);


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
	void fillModereglement(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_MODEREGLEMENT,
					this.idListModereglement,false) == true) {

				int pos = 0;
				String[] items = new String[idListModereglement.size()];
				for (int i = 0; i < idListModereglement.size(); i++) {

					items[i] = idListModereglement.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListModereglement.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerModereglement);


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
	void fillPeriodicite(String selVal) {
		try {
		
			if (Global.dbParam.getRecordPeriodicite(Global.dbParam.PARAM_PERIODICITE,
					this.idListPeriodicite,true) == true) {

				int pos = 0;
				String[] items = new String[idListPeriodicite.size()];
				for (int i = 0; i < idListPeriodicite.size(); i++) {

					items[i] = idListPeriodicite.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC)+" - "+idListPeriodicite.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					//items[i] = idListPeriodicite.get(i).getString(
					//		Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListPeriodicite.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerPeriodicite);


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
	void fillTournee(String selVal) {
		try {
		
			if (Global.dbParam.getRecordTournee(Global.dbParam.PARAM_TOURNEE,
					this.idListTournee,true) == true) {

				int pos = 0;
				String[] items = new String[idListTournee.size()];
				for (int i = 0; i < idListTournee.size(); i++) {

					items[i] = idListTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC)+" - "+idListTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					//items[i] = idListTournee.get(i).getString(
					//		Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTournee);


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

	void fillQualif(String selVal) {
		try {


			if (Global.dbParam.getRecordCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_QUAL,
					this.idListQualif,true) == true) {

				int pos = 0;
				String[] items = new String[idListQualif.size()];
				for (int i = 0; i < idListQualif.size(); i++) {

					items[i] = idListQualif.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL)+" - "+idListQualif.get(i).getString(
							Global.dbParam.FLD_PARAM_COMMENT);

					//items[i] = idListTournee.get(i).getString(
					//		Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListQualif.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerQualif);


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

    void fillSituation(String selVal) {
        try {


            if (Global.dbParam.getRecordCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_SITUATION,
                    this.idListSituation,true) == true) {

                int pos = 0;
                String[] items = new String[idListSituation.size()];
                for (int i = 0; i < idListSituation.size(); i++) {

                    items[i] = idListSituation.get(i).getString(
                            Global.dbParam.FLD_PARAM_LBL)+" - "+idListSituation.get(i).getString(
                            Global.dbParam.FLD_PARAM_COMMENT);

                    //items[i] = idListTournee.get(i).getString(
                    //		Global.dbParam.FLD_PARAM_LBL);

                    String codeRec = idListSituation.get(i).getString(
                            Global.dbParam.FLD_PARAM_LBL);

                    if (selVal.equals(codeRec)) {
                        pos = i;
                    }

                }

                Spinner spinner = (Spinner) findViewById(R.id.spinnerSituation);


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

	void fillOption(String selVal) {
		try {


			if (Global.dbParam.getRecordCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_OPTION,
					this.idListOption,true) == true) {

				int pos = 0;
				String[] items = new String[idListOption.size()];
				for (int i = 0; i < idListOption.size(); i++) {

					items[i] = idListOption.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL)+" - "+idListOption.get(i).getString(
							Global.dbParam.FLD_PARAM_COMMENT);

					//items[i] = idListTournee.get(i).getString(
					//		Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListOption.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerOption);


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

	void fillTypeCuisine(String selVal) {
		try {


			if (Global.dbParam.getRecordCommentOption(Global.dbParam.PARAM_PROSPECTOPTIONS,Global.dbParam.PARAM_PROSPECTOPTIONS_TYPECUISINE,
					this.idListTypeCuisine,true) == true) {

				int pos = 0;
				String[] items = new String[idListTypeCuisine.size()];
				for (int i = 0; i < idListTypeCuisine.size(); i++) {

					items[i] = idListTypeCuisine.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL)+" - "+idListTypeCuisine.get(i).getString(
							Global.dbParam.FLD_PARAM_COMMENT);

					//items[i] = idListTournee.get(i).getString(
					//		Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListTypeCuisine.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeCuisine);


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



	void fillJourfermeture(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_JOURFERMETURE,
					this.idListJourFerm,true) == true) {

				int pos = 0;
				String[] items = new String[idListJourFerm.size()];
				for (int i = 0; i < idListJourFerm.size(); i++) {

					items[i] = idListJourFerm.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListJourFerm.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerJourFerm);


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
	void fillTypeEtablissement(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_TYPEETABLISSEMENT,
					this.idListTypeEtablissement,true) == true) {

				int pos = 0;
				String[] items = new String[idListTypeEtablissement.size()];
				for (int i = 0; i < idListTypeEtablissement.size(); i++) {

					items[i] = idListTypeEtablissement.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListTypeEtablissement.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeEtablissement);


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
	void fillActiviteP(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_ACTIVITEP,
					this.idListActiviteP,true) == true) {

				int pos = 0;
				String[] items = new String[idListActiviteP.size()];
				for (int i = 0; i < idListActiviteP.size(); i++) {

					items[i] = idListActiviteP.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListActiviteP.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerActiviteP);


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
	void fillClass(String selVal) {
		try {
			
			
			if (Global.dbParam.getRecord2sClasse(Global.dbParam.PARAM_CLASSE,
					this.idListClasse,true) == true) {

				int pos = 0;
				String[] items = new String[idListClasse.size()];
				for (int i = 0; i < idListClasse.size(); i++) {

					items[i] = idListClasse.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					String codeRec = idListClasse.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerClasse);


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
	void fillFamclient(String selVal,String classe) {
		try {
			
			if (Global.dbParam.getRecord2sFamclient(Global.dbParam.PARAM_FAMCLIENT,classe,
					this.idListFamclient,true) == true) {

				int pos = 0;
				String[] items = new String[idListFamclient.size()];
				for (int i = 0; i < idListFamclient.size(); i++) {

					items[i] = idListFamclient.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListFamclient.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerFamclient);


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
	void fillSFamclient(String selVal,String famcli,String classe) {
		try {
			
			if (Global.dbParam.getRecord2sSFamclient(Global.dbParam.PARAM_SFAMCLIENT,famcli,classe,
					this.idListSFamclient,true) == true) {

				int pos = 0;
				String[] items = new String[idListSFamclient.size()];
				for (int i = 0; i < idListSFamclient.size(); i++) {

					items[i] = idListSFamclient.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListSFamclient.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerSFamclient);


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
	
	void fillCircuit(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_CODECIRCUIT,
					this.idListCircuit,true) == true) {

				int pos = 0;
				String[] items = new String[idListCircuit.size()];
				for (int i = 0; i < idListCircuit.size(); i++) {

					items[i] = idListCircuit.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListCircuit.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerCircuit);


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
	void fillAgent(String selVal) {
		try {
			String agence="";
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);	
			agence=login.getAgence(Preferences.getValue(this, Espresso.LOGIN, "0"));
			if (Global.dbParam.getRecord2sAgence(Global.dbParam.PARAM_AGENT,Preferences.getValue(this, Espresso.LOGIN, "0"),false,
					this.idListAgent,true) == true) {

				int pos = 0;
				String[] items = new String[idListAgent.size()];
				for (int i = 0; i < idListAgent.size(); i++) {

					items[i] = idListAgent.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListAgent.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerAgent);


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
	
	
	void fillAnnuel(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_ANNUEL,
					this.idListAnnuel,false) == true) {

				int pos = 0;
				String[] items = new String[idListAnnuel.size()];
				for (int i = 0; i < idListAnnuel.size(); i++) {

					items[i] = idListAnnuel.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListAnnuel.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerAnnuel);


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
	void fillSaisonEte(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_OUINON,
					this.idListSaisonEte,false) == true) {

				int pos = 0;
				String[] items = new String[idListSaisonEte.size()];
				for (int i = 0; i < idListSaisonEte.size(); i++) {

					items[i] = idListSaisonEte.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListSaisonEte.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerSaisonEte);


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
	void fillSaisonHiver(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_OUINON,
					this.idListSaisonHiver,false) == true) {

				int pos = 0;
				String[] items = new String[idListSaisonHiver.size()];
				for (int i = 0; i < idListSaisonHiver.size(); i++) {

					items[i] = idListSaisonHiver.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListSaisonHiver.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerSaisonHiver);


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
	void fillGroupeclient(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_GROUPEMENTCLIENT,
					this.idListGroupe,true) == true) {

				int pos = 0;
				String[] items = new String[idListGroupe.size()];
				for (int i = 0; i < idListGroupe.size(); i++) {

					items[i] = idListGroupe.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListGroupe.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerGroupe);


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
	void fillType(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_TYPECLI,
					this.idListType,true) == true) {

				int pos = 0;
				String[] items = new String[idListType.size()];
				for (int i = 0; i < idListType.size(); i++) {

					items[i] = idListType.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListType.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerType);


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				spinner.setAdapter(adapter);
				
				//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	
	
	void fillTypePDV(String selVal) {
		try {
			
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_CLIACTIV,
					this.idListTypePdv,false) == true) {

				int pos = 0;
				String[] items = new String[idListTypePdv.size()];
				for (int i = 0; i < idListTypePdv.size(); i++) {

					items[i] = idListTypePdv.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListTypePdv.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTypePDV);


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
	void fillZone(String selVal) {
		try {

			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_CODETOURNEE,
					this.idListZone,false) == true) {

				int pos = 0;
				String[] items = new String[idListZone.size()];
				for (int i = 0; i < idListZone.size(); i++) {

					items[i] = idListZone.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idListZone.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerZone);


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
	void fillJourPassage(String selVal) {
		try {
			//tof: pour affichage nbr cli par jour
			Bundle b = new Bundle(); 
			Global.dbClient.CountCliParJourPassage(b, m_stCurZone ) ;

			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_JOURPASSAGE,
					this.idListJourPassage,false) == true) {

				int pos = 0;
				String[] items = new String[idListJourPassage.size()];
				for (int i = 0; i < idListJourPassage.size(); i++) {

					String codeRec = idListJourPassage.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);
					String stLbl =  idListJourPassage.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);
					String stLblAdd = b.getString(codeRec) ; 
					if( stLblAdd == null || stLblAdd == "null" )
						stLblAdd = "" ;
					else
						stLblAdd = " ("+stLblAdd+" PdV)" ;
					//items[i] = idListJourPassage.get(i).getString(
					//		Global.dbParam.FLD_PARAM_LBL);
					items[i] = stLbl +stLblAdd ;


					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerJourPassage); 


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
	/**
	 * rempli les sociï¿½tï¿½
	 */
	void fillSociete() {
		try {

			arraySoc=Global.dbSoc.getSocs(Preferences.getValue( ficheclient.this, Espresso.LOGIN, "0"));

			if (arraySoc.size() >0) {

				int pos = 0;
				String[] items = new String[arraySoc.size()];
				for (int i = 0; i < arraySoc.size(); i++) {

					items[i] = arraySoc.get(i)[1];



				}


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spin_soc.setAdapter(adapter);

				spin_soc.setSelection(pos);


			}

		} catch (Exception ex) {

		}

	}

	/**
	 * renvoi l'index sï¿½lectionnï¿½ dans un spinner
	 * @param et
	 * @return
	 */
	public int getSpinnerSelectedIdx(Spinner et )
	{
		try
		{
			int pos=et.getSelectedItemPosition();
			return pos;

		}
		catch(Exception ex)
		{

		}
		return -1;
	}    
	String getSelectedSocCode()
	{
		int sel=this.getSpinnerSelectedIdx(spin_soc);
		if (sel==-1)
			return "";

		String result=arraySoc.get(sel)[0];

		return result;
	}

	void setObligatoire(EditText obj)
	{
		obj.setBackgroundColor(Color.RED);
	}


	void InitTextView()
	{
		checkMiseStandBy= (CheckBox) findViewById(R.id.checkMiseStandBy);
		checkPasdemail= (CheckBox) findViewById(R.id.checkPasdemail);
		checkFacEmail= (CheckBox) findViewById(R.id.checkFacEmail);
		TexteDateconcurrent= (TextView) findViewById(R.id.TexteDateconcurrent);
		TextViewRaisonsocial = (TextView) findViewById(R.id.Texteraisonsocial);// RS
		TextViewNom = (TextView) findViewById(R.id.TexteNom);// Nom
		TexteEnseigne = (TextView) findViewById(R.id.TexteEnseigne);// Enseigne
		TextViewAdresse1 = (TextView) findViewById(R.id.TexteAdresse1);// Adresse 1
		TextViewAdresse2 = (TextView) findViewById(R.id.TexteAdresse2);// Adresse 2
		TextViewcodepostal = (TextView) findViewById(R.id.TexteCodepostal);	// Code postal
		TextViewville = (TextView) findViewById(R.id.TexteVille);// Ville
		TextViewPays = (TextView) findViewById(R.id.TextePays);// Pays
		TextViewTel = (TextView) findViewById(R.id.TexteTelephone);// Tel
		TextViewFax = (TextView) findViewById(R.id.TexteFax);// Fax
		TextViewContactgsm = (TextView) findViewById(R.id.TexteContactgsm);// GSM
		TextViewEmail = (TextView) findViewById(R.id.TexteContactemail);// Email
		TexteType = (TextView) findViewById(R.id.TexteType);// type client
		TextViewSiret = (TextView) findViewById(R.id.TexteSiret);// Siret
		TexteGroupe = (TextView) findViewById(R.id.TexteGroupe);// Groupe
		TextViewNumTVA = (TextView) findViewById(R.id.TexteNumTVA);// N° tva
		TextViewNumTVA.setVisibility(View.GONE);
		
		TexteTypeEtablissement = (TextView) findViewById(R.id.TexteTypeEtablissement);// type etablissement
		TexteActiviteP = (TextView) findViewById(R.id.TexteActiviteP);// type etablissement
		TexteClasse = (TextView) findViewById(R.id.TexteClasse);
		TexteFamclient = (TextView) findViewById(R.id.TexteFamclient);// type etablissement
		TexteSFamclient = (TextView) findViewById(R.id.TexteSFamclient);// type etablissement
		
		TexteJourFerm = (TextView) findViewById(R.id.TexteJourFerm);// Fermé
		TexteSAV = (TextView) findViewById(R.id.TexteSAV);// SAV
		TexteModereglement = (TextView) findViewById(R.id.TexteModereglement);// Mode reglement
		TextViewCommentaire = (TextView) findViewById(R.id.TexteCommentaire);// Commentaire
		TexteEncours = (TextView) findViewById(R.id.TexteEncours);// En cours
		TexteCircuit = (TextView) findViewById(R.id.TexteCircuit);// Circuit
		TextViewlat = (TextView) findViewById(R.id.TexteLat);
		TextViewlon = (TextView) findViewById(R.id.TexteLon);
		TextViewCatCompta = (TextView) findViewById(R.id.TexteCatCompta);
		TextViewTypePDV = (TextView) findViewById(R.id.TexteTypePDV);
		TextViewZone = (TextView) findViewById(R.id.TexteZone);
		TextViewJourPassage = (TextView) findViewById(R.id.TexteJourPassage);
		TexteAgent = (TextView) findViewById(R.id.TexteAgent);
		TextePeriodicite = (TextView) findViewById(R.id.TextePeriodicite);
		TexteTournee= (TextView) findViewById(R.id.TexteTournee);
		TexteAnnuel= (TextView) findViewById(R.id.TexteAnnuel);
		
		TexteSaisonEte= (TextView) findViewById(R.id.TexteSaisonEte);
		TexteSaisonHiver= (TextView) findViewById(R.id.TexteSaisonHiver);

		TexteBooking = (TextView) findViewById(R.id.TexteBooking);
		EditBooking = (EditText) findViewById(R.id.EditBooking);
		TexteTripAdvisor = (TextView) findViewById(R.id.TexteTripAdvisor);
		EditTripAdvisor = (EditText) findViewById(R.id.EditTripAdvisor);

		TexteGroupe.setVisibility(View.GONE);
		TexteSAV.setVisibility(View.GONE);
		TexteCircuit.setVisibility(View.GONE);
		TexteAgent.setVisibility(View.GONE);

		EditLat = (EditText) this.findViewById(R.id.EditLat);
		EditLon = (EditText) this.findViewById(R.id.EditLon);

		spTypeEtablissement = (Spinner) findViewById(R.id.spinnerTypeEtablissement);
		spActiviteP = (Spinner) findViewById(R.id.spinnerActiviteP);
		spClasse = (Spinner) findViewById(R.id.spinnerClasse);
		spFamclient = (Spinner) findViewById(R.id.spinnerFamclient);
		spSFamclient = (Spinner) findViewById(R.id.spinnerSFamclient);

		spinnerQualif = (Spinner) findViewById(R.id.spinnerQualif);
        spinnerSituation= (Spinner) findViewById(R.id.spinnerSituation);
		spinnerOption= (Spinner) findViewById(R.id.spinnerOption);
		spinnerTypeCuisine= (Spinner) findViewById(R.id.spinnerTypeCuisine);

		spType = (Spinner) this.findViewById(R.id.spinnerType); //type etablissement
		spType.setEnabled(false);
		spJourFerm = (Spinner) this.findViewById(R.id.spinnerJourFerm);//Jour de fermeture
		spPeriodicite = (Spinner) this.findViewById(R.id.spinnerPeriodicite);//Périodicité
		spTournee = (Spinner) findViewById(R.id.spinnerTournee);//Tournée
		
		spinnerAnnuel= (Spinner) findViewById(R.id.spinnerAnnuel);//Annel
		//spinnerAnnuel.setEnabled(false);
		
		
		spinnerSaisonEte= (Spinner) findViewById(R.id.spinnerSaisonEte);//Annel
		//spinnerSaisonEte.setEnabled(false);
		
		spinnerSaisonHiver= (Spinner) findViewById(R.id.spinnerSaisonHiver);//Annel
		//spinnerSaisonHiver.setEnabled(false);
		
		spinnerAgent= (Spinner) findViewById(R.id.spinnerAgent);//


		Spinner spGroupe = (Spinner) this.findViewById(R.id.spinnerGroupe);
		spGroupe.setVisibility(View.GONE);


		Spinner spAgent = (Spinner) this.findViewById(R.id.spinnerAgent);
		spAgent.setVisibility(View.GONE);


		Spinner spSAV = (Spinner) this.findViewById(R.id.spinnerSAV);
		spSAV.setVisibility(View.GONE);

		spModereglement = (Spinner) this.findViewById(R.id.spinnerModereglement);
		TexteModereglement.setVisibility(View.GONE);
		spModereglement.setVisibility(View.GONE);
		spModereglement.setEnabled(false);
		spModereglement.setClickable(false);

		TexteVolumecafe = (TextView) findViewById(R.id.TexteVolumecafe);
		TexteVolumecafe.setVisibility(View.GONE);
		EditVolumecafe = (EditText) this.findViewById(R.id.EditVolumecafe);
		EditVolumecafe.setVisibility(View.GONE);


     TexteChiffreAffaires = (TextView) findViewById(R.id.TexteChiffreAffaires);
     EditChiffreAffaires= (EditText) this.findViewById(R.id.EditChiffreAffaires);
     TextePlaceAssInt= (TextView) findViewById(R.id.TextePlaceAssInt);
     EditPlaceAssInt= (EditText) this.findViewById(R.id.EditPlaceAssInt);
     TextePlaceAssExt= (TextView) findViewById(R.id.TextePlaceAssExt);
     EditPlaceAssExt= (EditText) this.findViewById(R.id.EditPlaceAssExt);
     TexteCapacite_sdr= (TextView) findViewById(R.id.TexteCapacite_sdr);
     EditCapacite_sdr= (EditText) this.findViewById(R.id.EditCapacite_sdr);
     TexteNb_Chambres= (TextView) findViewById(R.id.TexteNb_Chambres);
     EditNb_Chambres= (EditText) this.findViewById(R.id.EditNb_Chambres);
     TexteNb_Lits= (TextView) findViewById(R.id.TexteNb_Lits);
     EditNb_Lits= (EditText) this.findViewById(R.id.EditNb_Lits);
     TexteQualif= (TextView) findViewById(R.id.TexteQualif);
     EditQualif= (EditText) this.findViewById(R.id.EditQualif);
     TexteSituation= (TextView) findViewById(R.id.TexteSituation);
     EditSituation= (EditText) this.findViewById(R.id.EditSituation);
     TexteOption= (TextView) findViewById(R.id.TexteOption);
     EditOption= (EditText) this.findViewById(R.id.EditOption);
     TexteTypeCuisine= (TextView) findViewById(R.id.TexteTypeCuisine);
     EditTypeCuisine= (EditText) this.findViewById(R.id.EditTypeCuisine);
     TextePV_CAFE= (TextView) findViewById(R.id.TextePV_CAFE);
     EditPV_CAFE= (EditText) this.findViewById(R.id.EditPV_CAFE);
     TextePV_THE= (TextView) findViewById(R.id.TextePV_THE);
     EditPV_THE= (EditText) this.findViewById(R.id.EditPV_THE);
     TextePV_CHOCOLAT= (TextView) findViewById(R.id.TextePV_CHOCOLAT);
     EditPV_CHOCOLAT= (EditText) this.findViewById(R.id.EditPV_CHOCOLAT);
     TextePV_PETIT_DEJ= (TextView) findViewById(R.id.TextePV_PETIT_DEJ);
     EditPV_PETIT_DEJ= (EditText) this.findViewById(R.id.EditPV_PETIT_DEJ);
     TextePV_CHAMBRE= (TextView) findViewById(R.id.TextePV_CHAMBRE);
     EditPV_CHAMBRE= (EditText) this.findViewById(R.id.EditPV_CHAMBRE);

        TexteChiffreAffaires.setVisibility(View.GONE);
        EditChiffreAffaires.setVisibility(View.GONE);
        TextePlaceAssInt.setVisibility(View.GONE);
        EditPlaceAssInt.setVisibility(View.GONE);
        TextePlaceAssExt.setVisibility(View.GONE);
        EditPlaceAssExt.setVisibility(View.GONE);
        TexteCapacite_sdr.setVisibility(View.GONE);
        EditCapacite_sdr.setVisibility(View.GONE);
        TexteNb_Chambres.setVisibility(View.GONE);
        EditNb_Chambres.setVisibility(View.GONE);
        TexteNb_Lits.setVisibility(View.GONE);
        EditNb_Lits.setVisibility(View.GONE);
        TexteQualif.setVisibility(View.GONE);
        EditQualif.setVisibility(View.GONE);
		spinnerQualif.setVisibility(View.GONE);
        TexteSituation.setVisibility(View.GONE);
        EditSituation.setVisibility(View.GONE);
        spinnerSituation.setVisibility(View.GONE);
        TexteOption.setVisibility(View.GONE);
        EditOption.setVisibility(View.GONE);
		spinnerOption.setVisibility(View.GONE);
        TexteTypeCuisine.setVisibility(View.GONE);
        EditTypeCuisine.setVisibility(View.GONE);
		spinnerTypeCuisine.setVisibility(View.GONE);
        TextePV_CAFE.setVisibility(View.GONE);
        EditPV_CAFE.setVisibility(View.GONE);
        TextePV_THE.setVisibility(View.GONE);
        EditPV_THE.setVisibility(View.GONE);
        TextePV_CHOCOLAT.setVisibility(View.GONE);
        EditPV_CHOCOLAT.setVisibility(View.GONE);
        TextePV_PETIT_DEJ.setVisibility(View.GONE);
        EditPV_PETIT_DEJ.setVisibility(View.GONE);
        TextePV_CHAMBRE.setVisibility(View.GONE);
        EditPV_CHAMBRE.setVisibility(View.GONE);

        EditEncours = (EditText) this.findViewById(R.id.EditEncours);

		TexteFacturesdues = (TextView) findViewById(R.id.TexteFacturesdues);
		EditFacturesdues = (EditText) this.findViewById(R.id.EditFacturesdues);

		TexteAvoirdispo = (TextView) findViewById(R.id.TexteAvoirdispo);
		EditAvoirdispo = (EditText) this.findViewById(R.id.EditAvoirdispo);

		TexteAvancepaie = (TextView) findViewById(R.id.TexteAvancepaie);
		EditAvancepaie = (EditText) this.findViewById(R.id.EditAvancepaie);

		ERaisonSocial = (EditText) this.findViewById(R.id.EditRaisonsocial);
		EditText EditNom = (EditText) this.findViewById(R.id.EditNom);
		eContactgsm = (EditText) this.findViewById(R.id.EditContactgsm);
		EAdresse1 = (EditText) this.findViewById(R.id.EditAdresse1);
		EditText EAdresse2 = (EditText) this.findViewById(R.id.EditAdresse2);
		Ecodepostal = (EditText) this.findViewById(R.id.EditCodepostal);
		Eville = (EditText) this.findViewById(R.id.EditVille);
		EditEnseigne = (EditText) this.findViewById(R.id.EditEnseigne);
		Spinner spPays = (Spinner) this.findViewById(R.id.spinnerPays);

		Spinner spCatCompta = (Spinner) this.findViewById(R.id.spinnerCatCompta);
		Spinner spTypePDV = (Spinner) this.findViewById(R.id.spinnerTypePDV);
		Spinner spZone = (Spinner) this.findViewById(R.id.spinnerZone);
		Spinner spJourPassage = (Spinner) this.findViewById(R.id.spinnerJourPassage);
		Spinner spCircuit = (Spinner) this.findViewById(R.id.spinnerCircuit);
		spCircuit.setVisibility(View.GONE);

		ETel = (EditText) this.findViewById(R.id.EditTelephone);
		EFax = (EditText) this.findViewById(R.id.EditFax);
		EEmail = (EditText) this.findViewById(R.id.EditContactemail);
		ESiret = (EditText) this.findViewById(R.id.EditSiret);
		ENumTVA = (EditText) this.findViewById(R.id.EditNumTVA);
		EditText ECommentaire = (EditText) this.findViewById(R.id.EditCommentaire);

		// Mettre les textview et EditText en GONE
		TextViewRaisonsocial.setVisibility(View.GONE);
		TextViewNom.setVisibility(View.GONE);
		TextViewAdresse1.setVisibility(View.GONE);
		TextViewAdresse2.setVisibility(View.GONE);
		TextViewcodepostal.setVisibility(View.GONE);
		TextViewville.setVisibility(View.GONE);
		TextViewPays.setVisibility(View.GONE);
		TextViewTel.setVisibility(View.VISIBLE);
		TextViewFax.setVisibility(View.GONE);
		TextViewEmail.setVisibility(View.GONE);
		TextViewSiret.setVisibility(View.GONE);
		TextViewCommentaire.setVisibility(View.GONE);

		TextViewCatCompta.setVisibility(View.GONE);
		TextViewTypePDV.setVisibility(View.GONE);
		TextViewZone.setVisibility(View.GONE);
		TextViewJourPassage.setVisibility(View.GONE);
		TextViewNumTVA.setVisibility(View.GONE);

		ERaisonSocial.setVisibility(View.GONE);
		EditNom.setVisibility(View.GONE);
		EAdresse1.setVisibility(View.GONE);
		EAdresse2.setVisibility(View.GONE);
		Ecodepostal.setVisibility(View.GONE);
		Eville.setVisibility(View.GONE);
		spPays.setVisibility(View.GONE);
		ETel.setVisibility(View.VISIBLE);
		EFax.setVisibility(View.GONE);
		EEmail.setVisibility(View.GONE);
		ESiret.setVisibility(View.GONE);
		ECommentaire.setVisibility(View.GONE);

		spCatCompta.setVisibility(View.GONE);
		spTypePDV.setVisibility(View.GONE);
		spZone.setVisibility(View.GONE);
		spJourPassage.setVisibility(View.GONE);
		ENumTVA.setVisibility(View.GONE);

		ERaisonSocial.setVisibility(View.VISIBLE);
		TextViewRaisonsocial.setVisibility(View.VISIBLE);
		
		TexteAgent.setVisibility(View.GONE);
		spinnerAgent.setVisibility(View.GONE);

		bTypeclient = true;
		bContactGSM = true;
		bGroupeclient = true;
		bJourFermeture = true;
		bModereglement = true;
		bPeriodicite = true;
		bTournee = true;
		bSAV = true;

		eContactgsm.setVisibility(View.VISIBLE);
		TextViewContactgsm.setVisibility(View.VISIBLE);
		
		TextViewAdresse1.setVisibility(View.VISIBLE);
		EAdresse1.setVisibility(View.VISIBLE);
		
		TextViewcodepostal.setVisibility(View.VISIBLE);
		Ecodepostal.setVisibility(View.VISIBLE);

		TextViewville.setVisibility(View.VISIBLE);
		Eville.setVisibility(View.VISIBLE);


		TextViewPays.setVisibility(View.GONE);
		spPays.setVisibility(View.GONE);
		TextViewNumTVA.setVisibility(View.VISIBLE);
		ENumTVA.setVisibility(View.VISIBLE);
		bFax = true;
		TextViewFax.setVisibility(View.VISIBLE);
		EFax.setVisibility(View.VISIBLE);
		TextViewEmail.setVisibility(View.VISIBLE);
		EEmail.setVisibility(View.VISIBLE);
		TextViewSiret.setVisibility(View.VISIBLE);
		ESiret.setVisibility(View.VISIBLE);
		// bCommentaire =true;
		// TextViewCommentaire.setVisibility(View.VISIBLE);
		// ECommentaire.setVisibility(View.VISIBLE);
		// bCatCompta=true;
		// TextViewCatCompta.setVisibility(View.VISIBLE);
		// spCatCompta.setVisibility(View.VISIBLE);
		// bTypePdv =true;
		// TextViewTypePDV.setVisibility(View.VISIBLE);
		// spTypePDV.setVisibility(View.VISIBLE);
		
	}
	void setListeners()
	{
		bDate.setOnClickListener(this);
	}
	void initListeners()
	{
		checkPasdemail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkPasdemail.isChecked()) {
					EEmail.setEnabled(false);
					m_pasemail=true;

				} else {
					EEmail.setEnabled(true);
					m_pasemail=false;
					
				}

			}
		});
		checkMiseStandBy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkMiseStandBy.isChecked()) {
					
					m_stTandby="1";
					


				} else {
							
					m_stTandby="";
					
					
				}

			}
		});
		
		bDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	 //R�cup�rer le string sur le bouton 
            	 //String interneStDateCde= bDate.getText().toString();
            	 //String styear=Fonctions.Right(interneStDateCde, 4);
            	 //String stmonth=Fonctions.Mid(interneStDateCde, 3,2);
            	 //String stday=Fonctions.Left(interneStDateCde, 2);

				String date = Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD());
				String styear=Fonctions.Right(date, 4);
				String stmonth=Fonctions.Mid(date, 3,2);
				String stday=Fonctions.Left(date, 2);
            	 
            	__year=Fonctions.GetStringToIntDanem(styear);
            	__month=Fonctions.GetStringToIntDanem(stmonth);
            	__month=__month-1;
             	
            	__day=Fonctions.GetStringToIntDanem(stday);
            	
            	// On button click show datepicker dialog 
                showDialog(DATE_PICKER_FICHE);
 
            }
		});
		
		
	}




	/** Handler */
	Handler getHandlerGeoCoder(){
		Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch(msg.what) {
				case task_geocodeClient.MESSAGE_GEOCODING_FINISHED_FAIL:
					gecodeClient = null;
					UI_updateProgressBar();
					Fonctions.FurtiveMessageBox(ficheclient.this, getString(R.string.geocoding_failed)+" "+getString(R.string.check_address_and_connection));
					//save();
					//ficheclient.this.finish();
					break;
				case task_geocodeClient.MESSAGE_GEOCODING_FINISHED_SUCCESS:
					gecodeClient = null;
					UI_updateProgressBar();
					//save();
					//ficheclient.this.finish(0);
					break;
				case task_geocodeClient.MESSAGE_RETRIEVEADDRESS_SUCCESS:
					Eville.setText(cliToSave.VILLE);
					Ecodepostal.setText(cliToSave.CP);
					EAdresse1.setText(cliToSave.ADR1);
					//	gecodeClient.cancel(true);
					gecodeClient=null;
					break;
				}

			}
		};
		return h;
	}

	Handler getHandlerRetrieveAddress(){
		Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch(msg.what) {

				case 2:
				case 1:
					Location loc=whereAmI.getLocation(m_appState);
					cliToSave=new structClient();
					//Eville.setText(String.valueOf(loc.getLatitude()));
					gecodeClient = new task_geocodeClient(ficheclient.this, handlerGeoCoder,loc);
					gecodeClient.execute(cliToSave);

					UI_updateProgressBar();

					break;

				}

			}
		};
		return h;
	}

	void findAdresse()
	{
		whereAmI=new MyLocation();
		whereAmI.init(this, handlerRetrieveAddress);
	}

	private boolean save(boolean typeP){
		StringBuffer buffer = new StringBuffer();
		if (!Global.dbClient.saveProspect(cliToSave, numProspect,m_CreationProspect,typeP, buffer))
		{
			Fonctions.FurtiveMessageBox(this, getString(R.string.unable_to_save)+":"+buffer.toString());
			return false;
		}

		Fonctions.FurtiveMessageBox(this, getString(R.string.save_successfull));

		return true;
	}

	private void UI_updateProgressBar(){
		if(gecodeClient !=null)
			setProgressBarIndeterminateVisibility(false);
		else
			setProgressBarIndeterminateVisibility(true);
	}

	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		switch (parent.getId()) 
		{         
		case R.id.spinnerZone:
			m_stCurZone = idListZone.get(pos).getString(
					Global.dbParam.FLD_PARAM_CODEREC);

			fillJourPassage(m_stCurJourPassage) ;

			break;              
		case R.id.spinnerJourPassage:
			m_stCurJourPassage = idListJourPassage.get(pos).getString(
					Global.dbParam.FLD_PARAM_CODEREC);


			break;              

		}    	
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		//m_stFam = idFam.get(pos).getString(
		//		Global.dbParam.FLD_PARAM_CODEREC);
		//PopulateList() ;
	}
	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	       
	    case DATE_PICKER_FICHE:
	         
	        // open datepicker dialog. 
	        // set date picker for current date 
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


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	 

}
