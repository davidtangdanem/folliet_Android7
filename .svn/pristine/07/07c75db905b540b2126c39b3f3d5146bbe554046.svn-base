package com.menadinteractive.segafredo.rapportactivite;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.menadinteractive.agendanem.AgendaListActivity;
import com.menadinteractive.agendanem.AgendaViewActivity;
import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.commande.commandeInput;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.histo.InterventionActivity;
import com.menadinteractive.histo.InterventionActivityRA;
import com.menadinteractive.inventaire.ValidationInventaire;
import com.menadinteractive.negos.quest.pac_plans_filler;
import com.menadinteractive.negos.quest.pac_plans_filler2;
import com.menadinteractive.printmodels.Z420ModelRapport;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.dbKD106AgendaCorrespondance;
import com.menadinteractive.segafredo.db.dbKD729HistoDocuments;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableHistoInter;
import com.menadinteractive.segafredo.db.dbKD101ClientVu.structClientvu;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbKD103Questionnaire;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.encaissement.SignatureActivity;
import com.menadinteractive.segafredo.findcli.FindCliActivity;
import com.menadinteractive.segafredo.findcli.MenuCliActivity;
import com.menadinteractive.segafredo.plugins.Agendanem;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.synchro.SynchroService;

import static com.menadinteractive.segafredo.communs.Global.dbClient;

public class rapportactivite extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {

	TextView TextViewDate;
	TextView TextViewHeure;
	TextView TextViewTypeActivite;
	TextView TextefamActivite;
	TextView TextViewCommentaire;
	TextView TextViewNextDate;
	TextView TextViewQuestionnaire;
	TextView TexteLblHeuredepart;
	TextView TexteLblHeurarrive;
	TextView TexteIncommerciale;
	ImageView ivSignatureClient;
	LinearLayout llSignature;
	LinearLayout linearHours;
	LinearLayout lineZoneDurete;
	EditText EditTH;
	EditText EditKH;
	
	Boolean m_isSignClient;
	Bitmap myBitmap;
	TextView TexteResteafaire;
	EditText EditResteafaire;
	
	TextView TexteNumeroserie;
	EditText EditNumeroserie;
	
	TextView TexteContact;
	Boolean spinnerValueActivite = false ;
	
	
	String m_typ_inter="";
	
	CheckBox checkCloture;
	CheckBox checkNoncloture;
	
	
	CheckBox checkFacturable;
	CheckBox checkNonfacturable;
	
	CheckBox checkVisitepreventive;
	boolean m_btech=false;
	boolean m_bresp=false;
	boolean m_bvendeur=false;
	boolean gotorapporttodo;
	
	Button bNextDate;
	
	Spinner spFonction;
	Spinner spinnerFamActivite;
	
	Button bQuestionnaire;
	Button bContact;
	Button bMachineclient;
	EditText EditDate;
	Button bDate;
	
	EditText EditHeure;
	EditText EditCommentaire;
	EditText EditInfocommerciale;
	String m_stDate="";
	
	String m_numrapport="";
	String m_numbonbc="";
	boolean m_autosave=false;
	
	int __year;
	int __month;
	int __day;

	ArrayList<Bundle> idTypeActivite = null;
	ArrayList<Bundle> idFamActivite = null;
	ArrayList<Bundle> idQuestionnaire = null;
	ArrayList<ArrayList<structLinCde>> listPrintFactures = null;
	ArrayList<Bundle> idListAgent= null;// Agent
	
	int m_ilongueurComt=80;

	// String m_stvaleurDebut;
	// String m_stvaleurFin;
	
	String m_codearticle ="";
	String m_libarticle ="";
	String m_numserie ="";
	String m_codesymp ="";
	String m_libsymp ="";
	
	String m_qui ="";
	String m_marque ="";
	String m_typemachine ="";
	String m_marquemachine ="";
	String m_seriemachine ="";
	static final int DATE_PICKER_CDE = 2222; 

	String m_contactcode="";
	String m_contactitre="";
	String m_contactnom="";

	ImageButton ibGo;
	ImageButton ibStop;
	TimePicker tpAvant;
	TimePicker tpApres;
	String m_stnumfab="";
	
	structlistLogin rep = null;
	String sttypeAgent="";
	private ProgressDialog m_ProgressDialog = null;
	/** Task */
	String m_codeclient = "";
	String m_code_evt="";
	String m_num_evt="";
	String m_Fam_code_evt="";
	String m_evt_id="";
	String m_numInterne="";
	boolean btechDinsDechDrep=false;
	String m_nomtechnicien="";
	String m_info_inter="";
	String datePresetDeb = "" ;
	String datePresetFin = "" ;
	int ideventforreturn = 0 ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SynchroService.setPaused(true);
		idTypeActivite = new ArrayList<Bundle>();
		idFamActivite= new ArrayList<Bundle>();
		idQuestionnaire = new ArrayList<Bundle>();

		Bundle bundle = this.getIntent().getExtras();
		m_codeclient = bundle.getString("codeclient");
		m_code_evt = Fonctions.GetStringDanem(bundle.getString("code_evt"));
		m_evt_id = ""+ bundle.getInt("evt_id");
		m_numInterne= Fonctions.GetStringDanem(bundle.getString("numInterne"));
		gotorapporttodo = bundle.getBoolean("rapporttodo");
		m_autosave= bundle.getBoolean("autosave");
		ideventforreturn = bundle.getInt("ideventforreturn") ;

		//Ajout tof, pour utilisation depuis Agenda/edit


		String datedeb = Fonctions.GetStringDanem(bundle.getString("datedeb"));		//date et heure au format yyyymmddhhmmss
		String datefin = Fonctions.GetStringDanem(bundle.getString("datefin"));		//date et heure au format yyyymmddhhmmss
		String typeAct = Fonctions.GetStringDanem(bundle.getString("typeAct"));	//code act

		//Autre ajout pour que edit fonctionne correctement
		boolean bMajInfos106 = false ;
		dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
		if ( m_evt_id != null )
			if ( !m_evt_id.equals(""))
			{
				dbKD106AgendaCorrespondance.passePlat rr = db106.getRdv(m_evt_id);
				if (rr != null)
				{
					if(!Fonctions.GetStringDanem(rr.FIELD_CODECLI).equals(""))
					{
						m_codeclient = rr.FIELD_CODECLI;
						datedeb = rr.FIELD_DTDEBUT;
						datefin = rr.FIELD_DTFIN;
						typeAct = rr.FIELD_CODE_EVT ;
						bMajInfos106 = true ;
					}

				}
			}
		if ( datedeb != null && datefin != null )
			if ( !datedeb.equals("") && !datefin.equals(""))
			{
				//m_code_evt = typeAct ;fgdfgd
                String code_evt="";
                String stDatedeb=Fonctions.Left(datedeb,8)+" "+Fonctions.Right(datedeb,6);
                String stDateFin=Fonctions.Left(datefin,8)+" "+Fonctions.Right(datefin,6);

                code_evt= Global.dbKDAgendaSrv.getCode_evt(stDatedeb,stDateFin , m_codeclient );
                m_num_evt=Global.dbKDAgendaSrv.getNum_evt(stDatedeb,stDateFin , m_codeclient);
                if ( bMajInfos106 == true )
				{
					code_evt = typeAct ;
				}
                if(!Fonctions.GetStringDanem(code_evt).equals(""))
				{
					m_code_evt=code_evt;
					m_Fam_code_evt=Global.dbParam.getOrderAllSoc(Global.dbParam.PARAM_TYPEACTIVITE,code_evt);
				}

                else
                m_code_evt = Global.dbParam.getCodeFromLbl(Global.dbParam.PARAM_TYPEACTIVITE,typeAct) ;

                datePresetDeb = datedeb ;
				datePresetFin = datefin ;

			}


		idListAgent= new ArrayList<Bundle>();
		
		TableHistoInter hi=new TableHistoInter(m_appState.m_db);
		m_typ_inter=hi.LoadType_inter(m_codeclient,m_numInterne);
		m_stnumfab=hi.LoadInfo_numfab(m_codeclient,m_numInterne);
		m_info_inter=hi.LoadInfo_inter(m_codeclient,m_numInterne);
		
		structlistLogin rep = null;
		dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
		rep=new structlistLogin();

		setContentView(R.layout.activity_rapportactivite);
		initActionBar();
		m_isSignClient=false;
		login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) )
		{
			m_btech=true;
		}
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) )
		{
			m_bresp=true;
		}
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur) )
		{
			m_bvendeur=true;
		}
		m_nomtechnicien=Fonctions.GetStringDanem(rep.NOM);
		//Type Agent
		sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);
		m_numrapport=Global.dbKDClientVu.GetNumRapport(Preferences.getValue(this, Espresso.LOGIN, "0"));
				
		setContentView(R.layout.activity_rapportactivite);
		InitTextView();
		setListeners();
		initListeners();
		
		Loadinformation();
		
		bDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	 //R�cup�rer le string sur le bouton 
            	 String interneStDateCde= bDate.getText().toString();
            	 String styear=Fonctions.Right(interneStDateCde, 4);
            	 String stmonth=Fonctions.Mid(interneStDateCde, 3,2);
            	 String stday=Fonctions.Left(interneStDateCde, 2);
             	 
				__year=Fonctions.GetStringToIntDanem(styear);
            	__month=Fonctions.GetStringToIntDanem(stmonth);
            	__month=__month-1;
             	
            	__day=Fonctions.GetStringToIntDanem(stday);
            	
            	// On button click show datepicker dialog 
                showDialog(DATE_PICKER_CDE);
 
            }
		});
		
		
		if(m_autosave==true)
		{
			StringBuffer buff= new StringBuffer();
			saveAuto(buff);
			finish();
		}

		Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeActivite);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				String description="";
          		if(!Fonctions.GetStringDanem(""+item).equals(""))
	           		description	=Global.dbCliToVisit.getTodoDescription(m_codeclient,""+item);

				TextView textDescription = (TextView) findViewById(R.id.texte_description);
				textDescription.setText(description);

			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		//Spinner spinnerf = (Spinner) findViewById(R.id.spinnerFamActivite);

		spinnerFamActivite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				//String item = parent.getItemAtPosition(pos);


				//String activity = spinnerFamActivite.getSelectedItem().toString();//spinnerf.getSelectedItem().toString();
				if ( spinnerValueActivite==true)
				{
					String item = idFamActivite.get(pos).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if(!Fonctions.GetStringDanem(item).equals(""))
					{
						fillTypeActiviteOrder(item);
					}
				}
				spinnerValueActivite=true;



			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
	//	LoadSignatureClient();
		
	}

	void setListeners() {

		bNextDate.setOnClickListener(this);
		bDate.setOnClickListener(this);
		
		bQuestionnaire.setOnClickListener(this);
		bMachineclient.setOnClickListener(this);
		bContact.setOnClickListener(this);
	}

	void Loadinformation() {
		spinnerValueActivite=false;

		structClientvu vu = new structClientvu();
		StringBuffer stBuf = new StringBuffer();
		String stdate = "";
		String stheuredebut = "";
		String stheurefin = "";
		if(Global.dbKDClientVu.load_rapport(vu, m_codeclient, Fonctions.getYYYYMMDD(),m_numrapport,stBuf)==true)
		{
			stdate = Fonctions.getDD_MM_YYYY(vu.DATE);
			m_stDate=vu.DATE;
			bDate.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(m_stDate));
			setEditViewText(this, R.id.EditDate, stdate);

			stheuredebut = vu.HEUREDEBUT;
			stheurefin =vu.HEUREFIN;
			String sstheuredebut=Fonctions.GetStringDanem(stheuredebut).replace(":","");
			String sstheurefin=Fonctions.GetStringDanem(stheurefin).replace(":","");;
			HHMM_to_TimePicker(this, R.id.timePickerHourDebut, stheuredebut);
			HHMM_to_TimePicker(this, R.id.timePickerHourFin, stheurefin);
			
			setEditViewText(this, R.id.EditCommentaire, vu.COMMENTAIRE);
			setEditViewText(this, R.id.EditInfocommerciale, vu.INFOCOMMERCIALE);
			
			setEditViewText(this, R.id.EditNumeroserie, Fonctions.GetStringDanem(vu.NOSERIE));
			fillFamActivite(Fonctions.GetStringDanem(vu.FAMTYPEACT),"");
			fillTypeActivite(Fonctions.GetStringDanem(vu.TYPEACT),"");
			
			LoadChoixcloture(Fonctions.GetStringDanem(vu.CHOIX_CLOTUREE));
			setEditViewText(this, R.id.EditResteafaire, vu.CMT_NONCLOTUREE);

			fillQuestionnaire(Fonctions.GetStringDanem(vu.CODE_QUESTIONNAIRE));

			LoadChoixfacturable(Fonctions.GetStringDanem(vu.CHOIX_FACTURABLE));
			LoadVisitePreventive(Fonctions.GetStringDanem(vu.VISITEPREVENTIVE));
			
			m_codearticle = Fonctions.GetStringDanem(vu.MACHINE_CODEARTICLE);
			m_libarticle = Fonctions.GetStringDanem(vu.MACHINE_LIBARTICLE);
			m_numserie = Fonctions.GetStringDanem(vu.MACHINE_NUMSERIE);
			m_codesymp = Fonctions.GetStringDanem(vu.MACHINE_CODESYMP);
			m_libsymp = Fonctions.GetStringDanem(vu.MACHINE_LIBSYMP);
			
			m_qui = Fonctions.GetStringDanem(vu.MACHINE_QUI);
			m_marque =Fonctions.GetStringDanem(vu.MACHINE_MARQUE);
			m_typemachine = Fonctions.GetStringDanem(vu.MACHINE_TYPEMACHINE);
			m_marquemachine = Fonctions.GetStringDanem(vu.MACHINE_MARQUEMACHINE);
			m_seriemachine = Fonctions.GetStringDanem(vu.MACHINE_SERIEMACHINE);
			 
			m_contactcode=Fonctions.GetStringDanem(vu.CONTACT_CODE);
			m_contactitre=Fonctions.GetStringDanem(vu.CONTACT_TITRE);
			m_contactnom=Fonctions.GetStringDanem(vu.CONTACT_NOM);
			TexteContact.setText(m_contactnom);
		}
		else
		{
			stdate = Fonctions.getDD_MM_YYYY();
			m_stDate=Fonctions.getYYYYMMDD();
			stheuredebut = Fonctions.gethh_mm();
			stheurefin = Fonctions.gethh_mm();	//attention, l'heure ne doit pas etre au format hh:mm, mais hhmm
			if ( !datePresetDeb.equals("") && datePresetDeb.length() >=12)
			{
				m_stDate =datePresetDeb.substring(0,8) ;
				stdate = Fonctions.YYYYMMDD_to_dd_mm_yyyy(m_stDate);
				stheuredebut = datePresetDeb.substring(8,10)+datePresetDeb.substring(10,12) ;
				stheurefin = stheuredebut ;
				if ( !datePresetFin.equals("") && datePresetFin.length() >=12)
					stheurefin = datePresetFin.substring(8,10)+datePresetDeb.substring(10,12) ;
			}
			bDate.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(m_stDate));
            String sstheuredebut=Fonctions.GetStringDanem(stheuredebut).replace(":","");
            String sstheurefin=Fonctions.GetStringDanem(stheurefin).replace(":","");;

            HHMM_to_TimePicker(this, R.id.timePickerHourDebut, sstheuredebut);
			HHMM_to_TimePicker(this, R.id.timePickerHourFin, sstheurefin);
			setEditViewText(this, R.id.EditDate, stdate);
			
			setEditViewText(this, R.id.EditCommentaire, "");
			setEditViewText(this, R.id.EditInfocommerciale, "");
			
			setEditViewText(this, R.id.EditNumeroserie, Fonctions.GetStringDanem(""));
			setEditViewText(this, R.id.TexteContact, Fonctions.GetStringDanem(""));
			
			if(m_btech==true)
			{
				if(!Fonctions.GetStringDanem(m_numInterne).equals(""))
				{
					if(Fonctions.GetStringDanem(m_typ_inter).equals("XVIS"))
					{
						fillFamActivite(Fonctions.GetStringDanem("tech"),"");
						fillTypeActivite(Fonctions.GetStringDanem("tprev"),"");
						spFonction.setEnabled(false);
					}
					else
					{
						if(Fonctions.GetStringDanem(m_code_evt).equals("tprev"))
						{
							fillFamActivite(Fonctions.GetStringDanem("tech"),"");
							fillTypeActivite(Fonctions.GetStringDanem("tprev"),"");
						}
						else
						{
							fillFamActivite(Fonctions.GetStringDanem("tech"),"");
							fillTypeActivite(Fonctions.GetStringDanem("trm"),"");
						}

						
						spFonction.setEnabled(false);
					}
				}
				else
				{
					//venant depuis la liste des clients à visiter
					fillFamActivite(Fonctions.GetStringDanem("tech"),"");
					fillTypeActivite("",Fonctions.GetStringDanem("tprev"));	
					//spFonction.setEnabled(false);
				}

			}
			else {
				RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioRapport);
				radioGroup.setVisibility(View.VISIBLE);
				LinearLayout layout = (LinearLayout) findViewById(R.id.champ_choix_agent);
				layout.setVisibility(View.VISIBLE);
				String stgetfamevt= Global.dbParam.getOrderAllSoc(Global.dbParam.PARAM_TYPEACTIVITE,Fonctions.GetStringDanem(m_code_evt));
				fillFamActivite(Fonctions.GetStringDanem(stgetfamevt),"");
				fillTypeActivite(Fonctions.GetStringDanem(m_code_evt), "");

				if (!m_bresp && !m_btech)
				{
					fillAgent("",true);
				}	 else {
					fillAgent("",false);
				}

			}

			if (m_bresp || m_bvendeur)
			{
				RadioButton radioBtnRapport = (RadioButton) findViewById(R.id.radioBtnRapport);
				radioBtnRapport.setChecked(true);

				RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioRapport);
				radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// checkedId is the RadioButton selected
						RadioButton radiobutton = (RadioButton) findViewById(R.id.radioBtnDemande);
						TextView textView = (TextView) findViewById(R.id.title_agent);
						LinearLayout linearAgent = (LinearLayout) findViewById(R.id.champ_choix_agent);
						Button btnQuestionnaire = (Button) findViewById(R.id.bQuestionnaire);

						if(radiobutton.isChecked())
						{
							if(m_bvendeur==true)
							{
								btnQuestionnaire.setEnabled(true);
							}
							else
							btnQuestionnaire.setEnabled(false);
							//linearAgent.setVisibility(View.VISIBLE);
							textView.setText("Collaborateur");
						} else {
							btnQuestionnaire.setEnabled(true);
							textView.setText("Accompagné par");
							//linearAgent.setVisibility(View.VISIBLE);
						}
					}
				});

			}

			if (gotorapporttodo) //venant de rapporttodo
			{
				RadioButton radioBtnRapport = (RadioButton) findViewById(R.id.radioBtnRapport);
				radioBtnRapport.setChecked(true);

				Log.e("m_code_evt","m_code_evt => "+m_code_evt);
				String stgetfamevt= Global.dbParam.getOrderAllSoc(Global.dbParam.PARAM_TYPEACTIVITE,Fonctions.GetStringDanem(m_code_evt));
				fillFamActivite(Fonctions.GetStringDanem(stgetfamevt),"");
				fillTypeActivite(Fonctions.GetStringDanem(m_code_evt), "");
			}

			fillQuestionnaire("");

			LoadChoixfacturable("");
			LoadVisitePreventive("");

		}
		
		if(!m_libarticle.equals(""))
		{
			bMachineclient.setText("N° de série de la machine client : "+ m_libarticle);
		}
			
		//DINS - DREP - DECH
		
		if(m_btech==true && ( Fonctions.GetStringDanem(m_typ_inter).equals("DINS")  || Fonctions.GetStringDanem(m_typ_inter).equals("DREP")  || Fonctions.GetStringDanem(m_typ_inter).equals("DECH") ) )
		{
			TexteNumeroserie.setVisibility(View.VISIBLE);
			EditNumeroserie.setVisibility(View.VISIBLE);
			btechDinsDechDrep=true;
		}
		else
		{
			TexteNumeroserie.setVisibility(View.GONE);
			EditNumeroserie.setVisibility(View.GONE);
			btechDinsDechDrep=false;
		}
			
	}

	void showProgressDialog() {
		m_ProgressDialog = ProgressDialog.show(this, "Veuillez patienter",
				"traitement en cours", true);
	}

	public void hideProgressDialog() {
		if (m_ProgressDialog != null)
			m_ProgressDialog.dismiss();
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(R.string.rapport_titre);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		addMenu(menu, android.R.string.cancel,
				android.R.drawable.ic_menu_close_clear_cancel);
		addMenu(menu, android.R.string.ok, android.R.drawable.ic_menu_save);
		// addMenu(menu, R.string.supprimer, android.R.drawable.ic_menu_delete);

		return true;
	}
	public void launchMenuCli(String codecli ,String facturableDirectBL,String numrapport)
	{
		Intent intent = new Intent(this,		MenuCliActivity.class);
		Bundle b=new Bundle();
		b.putString("codecli",codecli);
		b.putString("facturableDirectBL",facturableDirectBL);
		b.putString("numrapport",numrapport);

		intent.putExtras(b);
		this.startActivity(intent);
	}

	private void record(ArrayList<String> ValueMessage, StringBuffer buff) {

		if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
		{
			if(Fonctions.GetStringDanem(m_contactcode).equals("") )
			{
				//ValueMessage.add("Attention : il n\'y a pa de contact sélectionné.");
				Fonctions.FurtiveMessageBox(this,Fonctions.GetStringDanem("Attention : il n\'y a pa de contact sélectionné."));
				//return false;
			}
		}
		if (ControleAvantSauvegarde(ValueMessage, buff) == true) {
//			if (1 == 1) {
				if (save(false, buff) == true) {
					
					//Pour les techniciens 
					structClient cli = new structClient();
					
					StringBuilder err = new StringBuilder();
					if (dbClient.getClient(m_codeclient, cli, err))
					{
						if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
						{
							launchPrinting();
							/*
							// si c'est facturable -> BL 
							if(Fonctions.GetStringDanem(GetChoixfacturable()).equals("1") )
							{
								finish();
								
								launchMenuCli(m_codeclient,GetChoixfacturable(),m_numrapport);
							}
							// si c'est non-facturable -> BL 
							if(Fonctions.GetStringDanem(GetChoixfacturable()).equals("2") )
							{
								TableHistoInter hi=new TableHistoInter(m_appState.m_db);
							   
							  int datefin =0;
							  datefin=Global.dbMaterielClient.Getdate_fin(m_stnumfab,m_codeclient);
							    
								//Verif su la machine est bien sous garantie
								if(!Fonctions.GetStringDanem(m_numInterne).equals("") && Fonctions.GetStringToIntDanem(Fonctions.getYYYYMMDD())<=datefin && datefin>0 )
								{
									finish();
									
								}
								else
								{
									//sinon
									if(Fonctions.GetStringDanem(m_numInterne).equals("")  || (hi.Load_numserieExiste(m_codeclient,m_numInterne)==false) || ( Fonctions.GetStringToIntDanem(Fonctions.getYYYYMMDD())>datefin && datefin>0) )
									{
										if(Fonctions.GetStringDanem(m_numInterne).equals(""))
										{
											MessageYesNo(getString(R.string.commande_facturable),"Rapport");	
										}
										else
										{
											if(btechDinsDechDrep==true)
											{
												finish();
											}
											else
											{
												MessageYesNo(getString(R.string.commande_facturable),"Rapport");	
											}	
										}
										
									
										
									}
									else
										finish();
									
								}
								
								
								
								
							}*/
							
						}
						else
							//finish();
							ReturnValide();
						
							
					}
					else
						//finish();
						ReturnValide();

				} else {
					Fonctions.FurtiveMessageBox(this, buff.toString());
				}
			}
			else
			Fonctions.FurtiveMessageBox(this,					Fonctions.GetStringDanem(ValueMessage.get(0)));

	}

	private void recordTechnicien(ArrayList<String> ValueMessage, StringBuffer buff) {

		if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
		{
			if(Fonctions.GetStringDanem(m_contactcode).equals("") )
			{
				//ValueMessage.add("Attention : il n\'y a pa de contact sélectionné.");
				Fonctions.FurtiveMessageBox(this,Fonctions.GetStringDanem("Attention : il n\'y a pa de contact sélectionné."));
				//return false;
			}
		}
		if (ControleAvantSauvegarde(ValueMessage, buff) == true) {
//			if (1 == 1) {
			if (save(false, buff) == true) {

				//Pour les techniciens
				structClient cli = new structClient();

				StringBuilder err = new StringBuilder();
				if (dbClient.getClient(m_codeclient, cli, err))
				{
					if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
					{
						launchPrinting();
					}
					else
						//finish();
						ReturnValide();


				}
				else
					//finish();
					ReturnValide();

			} else {
				Fonctions.FurtiveMessageBox(this, buff.toString());
			}
		}
		else
			Fonctions.FurtiveMessageBox(this,					Fonctions.GetStringDanem(ValueMessage.get(0)));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		StringBuffer buff = new StringBuffer();
		ArrayList<String> ValueMessage = new ArrayList();
		switch (item.getItemId()) {
		case R.string.prospect_positionner:

			return true;
		case android.R.string.cancel:

			// si un questionnaire existe on le supprime aussi
			dbKD103Questionnaire questionnaire = new dbKD103Questionnaire(getDB());
			questionnaire.deleteQuestionnaireWithoutRapport(m_codeclient,m_numrapport);

			Cancel();
			return true;

		case android.R.string.ok:
			if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
			{
				MessageYesNo_Technicien(getString(R.string.commande_cloture_rapport_tech),"Rapport");
			}
			else
				record(ValueMessage, buff);
			
			
			return true;

		case R.string.prospect_prendrecde:// si on veut prendre commande avant
											// m�me de remplir les coord
			// on sauve le prospect (obligŽ pour que tt fonctionne)
			// on va en prise de cde
			// on revient et on contr™le
			// comme c'est une sauvegarde forcŽe on donne un nom au client par
			// dŽfaut si non renseignŽ
			if (this.getEditViewText(this, R.id.EditRaisonsocial).equals("")) {
				this.setEditViewText(this, R.id.EditRaisonsocial,
						getString(R.string.prospect_nomdefaut));
			}

			break;
		/*
		 * case R.string.commande_quest:
		 * MenuPopup.launchQuest(this,numProspect,"",""); break;
		 */
		case R.string.geocode_prospect:

			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	void Cancel() {

		this.finish(0);

	}
	void ReturnValide()
	{
		Intent result = new Intent();
		Bundle b = new Bundle();
		b.putInt("ideventforreturn", ideventforreturn);

		result.putExtras(b);
		setResult(LAUNCH_RAPPORTACTIVITE, result);
		finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 10000){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String zplData = "";
			if(data != null){
				Bundle bundle = data.getExtras();
				zplData = bundle.getString("zplData");
			}
			Log.i("tag", "zplData "+zplData);
			launchPrintPreviewRapport(zplData, m_numrapport, m_btech,true,true);			
		}
		if(requestCode == LAUNCH_PRINTPREVIEWRAPPORT){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
			if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
			{
				
				// si c'est facturable -> BL 
				if(Fonctions.GetStringDanem(GetChoixfacturable()).equals("1") )
				{
					finish();
					
					launchMenuCli(m_codeclient,GetChoixfacturable(),m_numrapport);
				}
				// si c'est non-facturable -> BL 
				if(Fonctions.GetStringDanem(GetChoixfacturable()).equals("2") )
				{
					TableHistoInter hi=new TableHistoInter(m_appState.m_db);
				   
				  int datefin =0;
				  datefin=Global.dbMaterielClient.Getdate_fin(m_stnumfab,m_codeclient);
				    
					//Verif su la machine est bien sous garantie
					if(!Fonctions.GetStringDanem(m_numInterne).equals("") && Fonctions.GetStringToIntDanem(Fonctions.getYYYYMMDD())<=datefin && datefin>0 )
					{
						finish();
					}
					else
					{
						//sinon
						if(Fonctions.GetStringDanem(m_numInterne).equals("")  || (hi.Load_numserieExiste(m_codeclient,m_numInterne)==false) || ( Fonctions.GetStringToIntDanem(Fonctions.getYYYYMMDD())>datefin && datefin>0) )
						{
							if(Fonctions.GetStringDanem(m_numInterne).equals(""))
							{
								MessageYesNo(getString(R.string.commande_facturable),"Rapport");	
							}
							else
							{
								if(btechDinsDechDrep==true)
								{
									finish();
								}
								else
								{
									MessageYesNo(getString(R.string.commande_facturable),"Rapport");	
								}	
							}
							
						
							
						}
						else
							finish();
						
					}
					
					
					
					
				}
				
			}
		}
		if(requestCode==55555)
		{
			if (resultCode == Activity.RESULT_OK )
			{
				Bundle b = data.getExtras();
				
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
				
				
				bMachineclient.setText("N° de série de la machine client : "+ m_libarticle);
					
			}
		}
		
		if(requestCode==LAUNCH_CONTACT)
		{
			if (resultCode == Activity.RESULT_OK )
			{
				Bundle b = data.getExtras();

                m_contactcode=getBundleValue(b, "contactcode");
                m_contactitre=getBundleValue(b, "contacttitre");
                m_contactnom=getBundleValue(b, "contactnom");


                TexteContact.setText(m_contactnom);
                if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
    			{
                	 if(!Fonctions.GetStringDanem(m_contactcode).equals("") )
     				{
     					if(Global.dbContactcli.EmailContactcliExiste(m_codeclient,    m_contactcode)==false)
     					{
     						Toast.makeText(rapportactivite.this, "Attention : Contact sans adresse mail. Le client ne recevra pas de rapport d\'intervention ni de BL par mail.", Toast.LENGTH_LONG).show();		
     					}
     				}	
    			}
               
					
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {

			// si un questionnaire existe on le supprime aussi
			dbKD103Questionnaire questionnaire = new dbKD103Questionnaire(getDB());
			questionnaire.deleteQuestionnaireWithoutRapport(m_codeclient,m_numrapport);

			Cancel();

			return false;
		}

		else
			return super.onKeyDown(keyCode, event);
	}

	String getTypeActivite() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTypeActivite);
		if (pos > -1)
			try {
				pays = idTypeActivite.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	String getFamTypeActivite() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerFamActivite);
		if (pos > -1)
			try {
				pays = idFamActivite.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}

	String getTypeActivite_CodeQuestionnaire() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTypeActivite);
		if (pos > -1)
		try {
			pays = idTypeActivite.get(pos).getString(
					Global.dbParam.FLD_PARAM_VALUE);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return pays;
	}
	

	void finish(int i) {
		SynchroService.setPaused(false);
		finish();
	}

	/**
	 * Creation du menu
	 */
	static final private int MENU_ITEM = Menu.FIRST;

	public boolean save(boolean temporary, StringBuffer stbuff) {
		try {

			RadioButton radioButton = (RadioButton) findViewById(R.id.radioBtnDemande);

			structClientvu vu = new structClientvu();

			vu.SOC_CODE = "1";
			vu.CODEREP = Preferences.getValue(this, Espresso.LOGIN, "0");
			vu.CODECLI = m_codeclient;
			vu.TYPEACT = getTypeActivite();
			vu.FAMTYPEACT=getFamTypeActivite();
			vu.CHOIX_AGENT = getAgent();
			//Log.e("choixAgent",""+getAgent());

			if (radioButton.isChecked())
			{
				vu.IS_DEMANDE = "1";
			} else {
				vu.IS_DEMANDE = "0";
			}

			String stDate = this.getEditViewText(this, R.id.EditDate);
			//stDate = Fonctions.Right(stDate, 4) + Fonctions.Mid(stDate, 3, 2)
			//		+ Fonctions.Left(stDate, 2);
			stDate=Fonctions.dd_mm_yyyy_to_YYYYMMDD(bDate.getText().toString());

			vu.DATE = stDate;

			vu.HEUREDEBUT= BaseActivity.TimePicker_to_HHMM(this, R.id.timePickerHourDebut);
			vu.HEUREFIN= BaseActivity.TimePicker_to_HHMM(this, R.id.timePickerHourFin);
			String stcommentaire="";
			stcommentaire=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditCommentaire));
			
			vu.COMMENTAIRE = stcommentaire;
			
			String stinfocommerciale="";
			stinfocommerciale=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditInfocommerciale));
			
			vu.INFOCOMMERCIALE = stinfocommerciale;
			
			String stnoserie="";
			stnoserie=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditNumeroserie));
		
			vu.NOSERIE = stnoserie;

			if(Fonctions.GetStringDanem(m_code_evt).equals(getTypeActivite()))
			{

			}
			else
			{
				m_num_evt="";
				m_evt_id="";
			}

			vu.fIELD_EVT_ID=Fonctions.GetStringDanem(m_evt_id);
			vu.NUM_EVT=Fonctions.GetStringDanem(m_num_evt);
			vu.CHOIX_CLOTUREE=GetChoixcloture();
			
			String cmt_noncloture="";
			cmt_noncloture=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditResteafaire));
			
			if(Fonctions.GetStringDanem(GetChoixcloture()).equals("2"))
			{
				vu.CMT_NONCLOTUREE=cmt_noncloture;
			}
			else
				vu.CMT_NONCLOTUREE="";
			
			if(!Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(m_codeclient, stDate,m_numrapport).equals(""))
			{
				vu.CODE_QUESTIONNAIRE=getTypeActivite_CodeQuestionnaire();
				 	
			}
			else
			 vu.CODE_QUESTIONNAIRE="";
			
			vu.CHOIX_FACTURABLE=GetChoixfacturable();
			
			if(m_btech==true)
			{
				vu.VISITEPREVENTIVE=GetChoixVisitePreventive();
			}
			else
				vu.VISITEPREVENTIVE="";
			
			vu.MACHINE_CODEARTICLE=m_codearticle;
			vu.MACHINE_LIBARTICLE=m_libarticle;
			vu.MACHINE_NUMSERIE=m_numserie;
			vu.MACHINE_CODESYMP=m_codesymp;
			vu.MACHINE_LIBSYMP=m_libsymp;
			
			vu.MACHINE_QUI=m_qui;
			vu.MACHINE_MARQUE=m_marque;
			vu.MACHINE_TYPEMACHINE=m_typemachine;
			vu.MACHINE_MARQUEMACHINE=m_marquemachine;
			vu.MACHINE_SERIEMACHINE=m_seriemachine;
			vu.NUM_INTER=m_numInterne;
			
			vu.NUMEROBC=m_numbonbc;
			vu.NUMERORAPPORT=m_numrapport;
			
			
			String stth="";
			String stkh="";
			
			stth=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditTH));
			stkh=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditKH));
			
		
			vu.TH=stth;
			vu.KH=stkh;
			vu.CONTACT_CODE=Fonctions.GetStringDanem(m_contactcode);
			vu.CONTACT_TITRE=Fonctions.GetStringDanem(m_contactitre);
			vu.CONTACT_NOM=Fonctions.GetStringDanem(m_contactnom);
			//vu.TOURNEE= m


			vu.TOURNEE = getCodeTourneeByClient(m_codeclient);
			
			String m_qui ="";
			String m_marque ="";
			String m_typemachine ="";
			String m_marquemachine ="";
			String m_seriemachine ="";
			
			
			 
			StringBuffer buffer = new StringBuffer();
			if (Global.dbKDClientVu.save2_rapport(vu) == false) {
				Fonctions.FurtiveMessageBox(
						this,
						getString(R.string.unable_to_save) + ":"
								+ buffer.toString());
				return false;
			}

			Fonctions.FurtiveMessageBox(this,
					getString(R.string.save_successfull));

			return true;

		} catch (Exception ex) {
			return false;

		}

	}
	public boolean saveAuto( StringBuffer stbuff) {
		try {

			structClientvu vu = new structClientvu();

			vu.SOC_CODE = "1";
			vu.CODEREP = Preferences.getValue(this, Espresso.LOGIN, "0");
			vu.CODECLI = m_codeclient;
			vu.TYPEACT = getTypeActivite();
			vu.FAMTYPEACT=getFamTypeActivite();
			String stDate = this.getEditViewText(this, R.id.EditDate);
			//stDate = Fonctions.Right(stDate, 4) + Fonctions.Mid(stDate, 3, 2)
			//		+ Fonctions.Left(stDate, 2);
			stDate=Fonctions.dd_mm_yyyy_to_YYYYMMDD(bDate.getText().toString());
			

			vu.DATE = stDate;
			
			vu.HEUREDEBUT= BaseActivity.TimePicker_to_HHMM(this, R.id.timePickerHourDebut);
			vu.HEUREFIN= BaseActivity.TimePicker_to_HHMM(this, R.id.timePickerHourFin);
			String stcommentaire="";
			stcommentaire=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditCommentaire));
			stcommentaire="Référence article pris";
			vu.COMMENTAIRE = stcommentaire;
			
			String stinfocommerciale="";
			stinfocommerciale=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditInfocommerciale));
			
			vu.INFOCOMMERCIALE = stinfocommerciale;
			
			String stnoserie="";
			stnoserie=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditNumeroserie));
		
			vu.NOSERIE = stnoserie;
			
			
			if(Fonctions.GetStringDanem(m_code_evt).equals(getTypeActivite()))
			{
				
			}
			else
			{
				m_num_evt="";
				m_evt_id="";
			}

			vu.fIELD_EVT_ID=Fonctions.GetStringDanem(m_evt_id);
			vu.NUM_EVT=Fonctions.GetStringDanem(m_num_evt);
			
			vu.CHOIX_CLOTUREE=GetChoixcloture();
			
			String cmt_noncloture="";
			cmt_noncloture=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditResteafaire));
			
			if(Fonctions.GetStringDanem(GetChoixcloture()).equals("2"))
			{
				vu.CMT_NONCLOTUREE=cmt_noncloture;
			}
			else
			   vu.CMT_NONCLOTUREE="";
			
			if(!Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(m_codeclient, stDate,m_numrapport).equals(""))
			{
				vu.CODE_QUESTIONNAIRE=getTypeActivite_CodeQuestionnaire();
				 	
			}
			else
			 vu.CODE_QUESTIONNAIRE="";
			
			vu.CHOIX_FACTURABLE=GetChoixfacturable();
			
			if(m_btech==true)
			{
				vu.VISITEPREVENTIVE=GetChoixVisitePreventive();
			}
			else
			   vu.VISITEPREVENTIVE="";
			
			vu.MACHINE_CODEARTICLE=m_codearticle;
			vu.MACHINE_LIBARTICLE=m_libarticle;
			vu.MACHINE_NUMSERIE=m_numserie;
			vu.MACHINE_CODESYMP=m_codesymp;
			vu.MACHINE_LIBSYMP=m_libsymp;
			
			vu.MACHINE_QUI=m_qui;
			vu.MACHINE_MARQUE=m_marque;
			vu.MACHINE_TYPEMACHINE=m_typemachine;
			vu.MACHINE_MARQUEMACHINE=m_marquemachine;
			vu.MACHINE_SERIEMACHINE=m_seriemachine;
			vu.NUM_INTER=m_numInterne;
			
			vu.NUMEROBC=m_numbonbc;
			vu.NUMERORAPPORT=m_numrapport;
			
			String stth="";
			String stkh="";
			
			stth=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditTH));
			stkh=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditKH));
			
		
			vu.TH=stth;
			vu.KH=stkh;
			
			vu.CONTACT_CODE=Fonctions.GetStringDanem(m_contactcode);
			vu.CONTACT_TITRE=Fonctions.GetStringDanem(m_contactitre);
			vu.CONTACT_NOM=Fonctions.GetStringDanem(m_contactnom);

			vu.TOURNEE = getCodeTourneeByClient(m_codeclient);
			
			String m_qui ="";
			String m_marque ="";
			String m_typemachine ="";
			String m_marquemachine ="";
			String m_seriemachine ="";
			
			
			 
			StringBuffer buffer = new StringBuffer();
			if (Global.dbKDClientVu.save2_rapport(vu) == false) {
				Fonctions.FurtiveMessageBox(
						this,
						getString(R.string.unable_to_save) + ":"
								+ buffer.toString());
				return false;
			}

			Fonctions.FurtiveMessageBox(this,
					getString(R.string.save_successfull));

			return true;

		} catch (Exception ex) {
			return false;

		}

	}
	
	
	public boolean saveYN(StringBuffer stbuff,String yn) {
		try {

			structClientvu vu = new structClientvu();

			vu.YN_FACTURABLE = yn;
			vu.CODEREP = Preferences.getValue(this, Espresso.LOGIN, "0");
			vu.CODECLI = m_codeclient;
			String stDate = this.getEditViewText(this, R.id.EditDate);
			//stDate = Fonctions.Right(stDate, 4) + Fonctions.Mid(stDate, 3, 2)
			//		+ Fonctions.Left(stDate, 2);
			
			stDate=Fonctions.dd_mm_yyyy_to_YYYYMMDD(bDate.getText().toString());
			
			vu.DATE = stDate;
			vu.NUMERORAPPORT=m_numrapport;
			vu.TOURNEE = getCodeTourneeByClient(m_codeclient);
		
			StringBuffer buffer = new StringBuffer();
			if (Global.dbKDClientVu.save3_rapport(vu) == false) {
				Fonctions.FurtiveMessageBox(
						this,
						getString(R.string.unable_to_save) + ":"
								+ buffer.toString());
				return false;
			}

			
			return true;


		} catch (Exception ex) {
			return false;

		}

	}

	public String getCodeTourneeByClient(String codeClient){

		String codeTournee = "";
		TableClient client  = new TableClient(getDB());
		codeTournee = client.getClientCodeTournee(codeClient);
		//Log.e("codeTournee","codeTournee=>"+codeTournee);
		return codeTournee;

	}

	public boolean ControleAvantSauvegarde(ArrayList<String> ValueMessage,
			StringBuffer stBuf) {
		boolean bres = true;

		String stmessage = "";

		try {

			if (getTypeActivite().equals("")) {

				stmessage = "" + TextViewTypeActivite.getText().toString()
						+ " " + getString(R.string.prospect_obligatoire);
				ValueMessage.add(stmessage);
				return false;
			}
			//controle questionnaire
			
			if(ControleHoraire()==true)
			{
				ValueMessage.add(getString(R.string.rapport_heurefinInfheuredebut));
				return false;
			}
			if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
			{
				//if(Fonctions.GetStringDanem(m_contactcode).equals("") )
				{
				//	ValueMessage.add("Attention : il n\'y a pa de contact sélectionné.");
					//return false;
				}
				
				String cmt_commentaire="";
				cmt_commentaire=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditCommentaire));
				if(Fonctions.GetStringDanem(cmt_commentaire).equals(""))
				{
					ValueMessage.add("Veuillez saisir un commentaire .");
					return false;
				}
				
				
				if(Fonctions.GetStringDanem(m_numInterne).equals(""))
				{
					if(m_libarticle.equals(""))
					{
						ValueMessage.add("Veuillez sélectionner une machine dans le parc .");
						return false;
					}
				}
				if(Fonctions.GetStringDanem(GetChoixcloture()).equals("0") )
				{
					ValueMessage.add("Veuillez sélectionner le type de clôture .");
					return false;
				}
				String cmt_noncloture="";
				cmt_noncloture=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditResteafaire));
				
				if(Fonctions.GetStringDanem(GetChoixcloture()).equals("2") && cmt_noncloture.equals(""))
				{
					ValueMessage.add("saisir un commentaire dans reste à faire .");
					return false;
				}
				if(Fonctions.GetStringDanem(GetChoixfacturable()).equals("0") )
				{
					ValueMessage.add("Veuillez sélectionner si c\'est facturable ou pas .");
					return false;
				}
				
				
				/*
				if(!Fonctions.GetStringDanem(m_contactcode).equals("") )
				{
					if(Global.dbContactcli.EmailContactcliExiste(m_codeclient,    m_contactcode)==false)
					{
					
						ValueMessage.add("Attention : Contact sans adresse mail. Le client ne recevra pas de rapport d\'intervention ni de BL par mail.");
						return false;
					}
				
				
				}*/
				
				//
				/*String stth="";
				stth=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditTH));
				
				if(Fonctions.GetStringDanem(stth).equals("") )
				{
					ValueMessage.add("saisir °TH .");
					return false;
				}
				String stkh="";
				stkh=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditKH));
				
				if(Fonctions.GetStringDanem(stkh).equals("") )
				{
					ValueMessage.add("saisir °KH .");
					return false;
				}*/
				
			}

			if (Fonctions.GetStringDanem(sttypeAgent).equals(Global.Responsable))
			{
				RadioButton radioButton = (RadioButton) findViewById(R.id.radioBtnDemande);

				if (radioButton.isChecked())
				{
					if(GetChoixAgent() == null )
					{
						ValueMessage.add("Veuillez sélectionner un collaborateur");
						return false;
					}
				}


			}
			if(!Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(m_codeclient, m_stDate,m_numrapport).equals(""))
			{
				if(!Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(m_codeclient, m_stDate,m_numrapport).equals(getTypeActivite_CodeQuestionnaire()))
				{
					ValueMessage.add("Le questionnaire enregistré n\'est pas celui du type d\'activité.");
					return false;
				}
			
				 	
			}
			else
			{
				if(!getTypeActivite_CodeQuestionnaire().equals(""))
				{
					RadioButton radioButton = (RadioButton) findViewById(R.id.radioBtnDemande);

					if (!radioButton.isChecked())
					{
						if(Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(m_codeclient, m_stDate,m_numrapport).equals(""))
						{
							ValueMessage.add("Veuillez saisir le questionnaire .");
							return false;
						}
					}

				}
				
				
			}
			if(Fonctions.GetStringDanem(EditNumeroserie.getText().toString()).equals(""))
			{
				if(btechDinsDechDrep==true)
				{
					stmessage = " Veuillez saisir le N° de série.";
					ValueMessage.add(stmessage);
					return false;
				}		
			}
			


		} catch (Exception ex) {
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;
		}
		return bres;

	}

	void fillTypeActivite(String selVal,String selvalRemove) {
		try {
			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbParam.getRecordActiviteRemove(Global.dbParam.PARAM_TYPEACTIVITE,sttypeAgent,selvalRemove,
					this.idTypeActivite, true) == true) {

				int pos = 0;
				String[] items = new String[idTypeActivite.size()];
				for (int i = 0; i < idTypeActivite.size(); i++) {

					items[i] = idTypeActivite.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeActivite.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeActivite);

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

	void fillTypeActiviteOrder(String selVal) {
		try {
			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbParam.getRecordActiviteorder(Global.dbParam.PARAM_TYPEACTIVITE,sttypeAgent,selVal,
					this.idTypeActivite) == true) {

				int pos = 0;
				String[] items = new String[idTypeActivite.size()];
				for (int i = 0; i < idTypeActivite.size(); i++) {

					items[i] = idTypeActivite.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeActivite.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeActivite);

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

	void fillFamActivite(String selVal,String selvalRemove) {
		try {
			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbParam.getRecordActiviteRemove(Global.dbParam.PARAM_TYPEFAMEVT,sttypeAgent,selvalRemove,
					this.idFamActivite, true) == true) {

				int pos = 0;
				String[] items = new String[idFamActivite.size()];
				for (int i = 0; i < idFamActivite.size(); i++) {

					items[i] = idFamActivite.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idFamActivite.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerFamActivite);

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


	void fillQuestionnaire(String selVal) {
		try {
			
			if (Global.dbQuestionnaire.getRecordQuestionnaire(
					this.idQuestionnaire, true) == true) {

				int pos = 0;
				String[] items = new String[idQuestionnaire.size()];
				for (int i = 0; i < idQuestionnaire.size(); i++) {

					items[i] = idQuestionnaire.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idQuestionnaire.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerQuestionnaire);

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


	void fillAgent(String selVal, boolean isVendeur) {
		try {
			String agence="";
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			agence=login.getAgence(Preferences.getValue(this, Espresso.LOGIN, "0"));
			if (Global.dbParam.getRecord2sAgence(Global.dbParam.PARAM_AGENT,Preferences.getValue(this, Espresso.LOGIN, "0"),isVendeur,
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

				Log.e("spinnerItems",""+items);

				Spinner spinner = (Spinner) findViewById(R.id.spinner_agent);

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

	String getAgent()
	{
		String agent="";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinner_agent);
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

	/**
	 * renvoi l'index sï¿½lectionnï¿½ dans un spinner
	 * 
	 * @param et
	 * @return
	 */
	public int getSpinnerSelectedIdx(Spinner et) {
		try {
			int pos = et.getSelectedItemPosition();
			return pos;

		} catch (Exception ex) {

		}
		return -1;
	}

	void InitTextView() {

		/*
		 * TextView TextViewDate ; TextView TextViewHeure ; TextView
		 * TextViewTypeActivite ; TextView TextViewCommentaire ; TextView
		 * TextViewNextDate;
		 * 
		 * 
		 * EditText EditDate; EditText EditHeure; EditText EditCommentaire;
		 */
		ArrayList<Bundle> idTypeActivite = null;
		ivSignatureClient = (ImageView) findViewById(R.id.ivSignatureClient);
		ivSignatureClient.setVisibility(View.GONE);
		llSignature= (LinearLayout) findViewById(R.id.llSignature);
		llSignature.setVisibility(View.GONE);
		lineZoneDurete= (LinearLayout) findViewById(R.id.lineZoneDurete);
		lineZoneDurete.setVisibility(View.GONE);
		
		linearHours= (LinearLayout) findViewById(R.id.linearHours);
		
		checkCloture = (CheckBox) findViewById(R.id.checkCloture);
		checkCloture.setVisibility(View.GONE);
		checkNoncloture = (CheckBox) findViewById(R.id.checkNoncloture);
		checkNoncloture.setVisibility(View.GONE);
		
		TexteResteafaire = (TextView) findViewById(R.id.TexteResteafaire);
		EditResteafaire = (EditText) findViewById(R.id.EditResteafaire);
		TexteResteafaire.setVisibility(View.GONE);
		EditResteafaire.setVisibility(View.GONE);
		
		EditTH= (EditText) findViewById(R.id.EditTH);
		EditKH= (EditText) findViewById(R.id.EditKH);
		
		
		TexteNumeroserie = (TextView) findViewById(R.id.TexteNumeroserie);
		EditNumeroserie = (EditText) findViewById(R.id.EditNumeroserie);
		TexteNumeroserie.setVisibility(View.GONE);
		EditNumeroserie.setVisibility(View.GONE);
				
		checkFacturable = (CheckBox) findViewById(R.id.checkFacturable);
		checkFacturable.setVisibility(View.GONE);
		checkNonfacturable = (CheckBox) findViewById(R.id.checkNonfacturable);
		checkNonfacturable.setVisibility(View.GONE);
		
		checkVisitepreventive= (CheckBox) findViewById(R.id.checkVisitepreventive);
		checkVisitepreventive.setVisibility(View.GONE);
		
		TexteContact = (TextView) findViewById(R.id.TexteContact);

		// Date
		TextViewDate = (TextView) findViewById(R.id.TexteDate);
		//TextViewDate.setText(m_numrapport);
		// Heure
		TextViewHeure = (TextView) findViewById(R.id.TexteHeure);
		// Type activite
		TextViewTypeActivite = (TextView) findViewById(R.id.TexteTypeActivite);
		TextefamActivite = (TextView) findViewById(R.id.TextefamActivite);
		// Commentaire
		TextViewCommentaire = (TextView) findViewById(R.id.TexteCommentaire);
		// Next date
		TextViewNextDate = (TextView) findViewById(R.id.TexteNextDate);
		TexteLblHeurarrive= (TextView) findViewById(R.id.TexteLblHeurarrive);
		TexteLblHeuredepart= (TextView) findViewById(R.id.TexteLblHeuredepart);
		
		TexteIncommerciale= (TextView) findViewById(R.id.TexteIncommerciale);
		EditInfocommerciale= (EditText) findViewById(R.id.EditInfocommerciale);
		TexteIncommerciale.setVisibility(View.GONE);
		EditInfocommerciale.setVisibility(View.GONE);
		TextViewQuestionnaire = (TextView) findViewById(R.id.TexteQuestionnaire);
		bQuestionnaire = (Button) findViewById(R.id.bQuestionnaire);
		bMachineclient= (Button) findViewById(R.id.bMachineclient);
		bContact= (Button) findViewById(R.id.bContact);
		
		bNextDate = (Button) findViewById(R.id.bNextDate);
		bDate = (Button) findViewById(R.id.bDate);
		
		EditDate = (EditText) this.findViewById(R.id.EditDate);
		EditDate.setVisibility(View.GONE);
		EditHeure = (EditText) this.findViewById(R.id.EditHeure);
		EditCommentaire = (EditText) this.findViewById(R.id.EditCommentaire);
		spFonction = (Spinner) this.findViewById(R.id.spinnerTypeActivite);
		spinnerFamActivite = (Spinner) this.findViewById(R.id.spinnerFamActivite);


		InputFilter[] FilterArrayCmt = new InputFilter[1];

		FilterArrayCmt[0] = new InputFilter.LengthFilter(
				m_ilongueurComt);
		EditCommentaire.setFilters(FilterArrayCmt);
		
		ibGo=(ImageButton)findViewById(R.id.imageButtonGo);
		ibStop=(ImageButton)findViewById(R.id.imageButtonStop);
		
		tpAvant=(TimePicker) this.findViewById(R.id.timePickerHourDebut);
		tpApres=(TimePicker) this.findViewById(R.id.timePickerHourFin);
		tpAvant.setEnabled(true);
		tpAvant.setIs24HourView(true); 
		tpApres.setIs24HourView(true); 
		final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
       
		tpAvant.setCurrentHour(hour);
		tpAvant.setCurrentMinute(minute);
		tpApres.setCurrentHour(hour);
		tpApres.setCurrentMinute(minute);
		bMachineclient.setVisibility(View.GONE);

		TextViewNextDate.setVisibility(View.GONE);
		bNextDate.setVisibility(View.GONE);
		
		if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Technicien))
		{
			ibGo.setVisibility(View.GONE);
			ibStop.setVisibility(View.GONE);
			//linearHours.setVisibility(View.GONE);
			//tpAvant.setVisibility(View.GONE);
			//tpApres.setVisibility(View.GONE);
			//TextViewHeure.setVisibility(View.GONE);
			TextViewNextDate.setVisibility(View.GONE);
			bNextDate.setVisibility(View.GONE);
			//TexteLblHeuredepart.setVisibility(View.GONE);
			//TexteLblHeurarrive.setVisibility(View.GONE);
			TexteIncommerciale.setVisibility(View.VISIBLE);
			EditInfocommerciale.setVisibility(View.VISIBLE);
			ivSignatureClient.setVisibility(View.VISIBLE);
			//llSignature.setVisibility(View.VISIBLE);
			lineZoneDurete.setVisibility(View.VISIBLE);
			checkCloture.setVisibility(View.VISIBLE);
			checkNoncloture.setVisibility(View.VISIBLE);
			
			checkFacturable.setVisibility(View.VISIBLE);
			checkNonfacturable.setVisibility(View.VISIBLE);
			
			checkVisitepreventive.setVisibility(View.VISIBLE);
			
			if(Fonctions.GetStringDanem(m_numInterne).equals(""))
			{
				bMachineclient.setVisibility(View.VISIBLE);	
			}

			
		}

		if(Fonctions.GetStringDanem(sttypeAgent).equals(Global.Responsable)|| Fonctions.GetStringDanem(sttypeAgent).equals(Global.Vendeur))
		{
			Spinner spinner = (Spinner) findViewById(R.id.spinner_agent);
			spinner.setVisibility(View.VISIBLE);
		}
		

	}
	void initListeners()
	{
		
		ibGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HHMM_to_TimePicker(tpAvant,Fonctions.gethhmm());
			}
		});
		ibStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			HHMM_to_TimePicker(tpApres,Fonctions.gethhmm());
			
			}
		});
		
		ivSignatureClient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
							
			}
		});
		
		checkCloture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkCloture.isChecked()) {
					checkNoncloture.setChecked(false);
					checkCloture.setFocusable(true);
					TexteResteafaire.setVisibility(View.GONE);
					EditResteafaire.setVisibility(View.GONE);
					
				} else {
					checkNoncloture.setChecked(true);
					checkNoncloture.setFocusable(true);
					TexteResteafaire.setVisibility(View.VISIBLE);
					EditResteafaire.setVisibility(View.VISIBLE);
					
				}

			}
		});

		checkNoncloture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkNoncloture.isChecked()) {
					checkCloture.setChecked(false);
					checkNoncloture.setFocusable(true);
					TexteResteafaire.setVisibility(View.VISIBLE);
					EditResteafaire.setVisibility(View.VISIBLE);
					

				} else {
					checkCloture.setChecked(true);
					checkCloture.setFocusable(true);
					TexteResteafaire.setVisibility(View.GONE);
					EditResteafaire.setVisibility(View.GONE);
					
					

				}

			}
		});
		
		checkFacturable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkFacturable.isChecked()) {
					checkNonfacturable.setChecked(false);
					checkFacturable.setFocusable(true);
				
				} else {
					checkNonfacturable.setChecked(true);
					checkNonfacturable.setFocusable(true);
					
					
				}

			}
		});

		checkNonfacturable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkNonfacturable.isChecked()) {
					checkFacturable.setChecked(false);
					checkNonfacturable.setFocusable(true);
					

				} else {
					checkFacturable.setChecked(true);
					checkFacturable.setFocusable(true);
					
					

				}

			}
		});

		
	}
	public String GetChoixcloture() {
		String FinalCheckbox = "0";
		
		String StCheckCloture = String.valueOf(this.getCheckBoxValue(this,R.id.checkCloture));
		String StCheckNoncloture = String.valueOf(this.getCheckBoxValue(this,R.id.checkNoncloture));

		if (StCheckCloture.equals("1")) {
			FinalCheckbox = "1";
		} else {
			if (StCheckNoncloture.equals("1")) {
				FinalCheckbox = "2";
			} else
				FinalCheckbox = "0";

		}

		return FinalCheckbox;

	}

	public void LoadChoixcloture(String IdentChoix) {
		String stFinalCheck = IdentChoix;

		if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("0")) {
			checkCloture.setChecked(false);
			checkNoncloture.setChecked(false);
		} else {
			if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("1")) {
				checkCloture.setChecked(true);
				checkNoncloture.setChecked(false);
			}
			if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("2")) {
				checkCloture.setChecked(false);
				checkNoncloture.setChecked(true);
			}

		}
	}
	
	public String GetChoixfacturable() {
		String FinalCheckbox = "0";
		
		String StCheckFacturable = String.valueOf(this.getCheckBoxValue(this,R.id.checkFacturable));
		String StCheckNonfacturable = String.valueOf(this.getCheckBoxValue(this,R.id.checkNonfacturable));

		if (StCheckFacturable.equals("1")) {
			FinalCheckbox = "1";
		} else {
			if (StCheckNonfacturable.equals("1")) {
				FinalCheckbox = "2";
			} else
				FinalCheckbox = "0";

		}

		return FinalCheckbox;

	}

	public String GetChoixAgent() {

		Spinner spinner = (Spinner)findViewById(R.id.spinner_agent);
		String text = spinner.getSelectedItem().toString();

		if (text.equals(""))
		{
			text = null;
		}

		return text;

	}
	public String GetChoixVisitePreventive() {
		String FinalCheckbox = "0";
		
		String StCheckVisitePreventive = String.valueOf(this.getCheckBoxValue(this,R.id.checkVisitepreventive));
		
		if (StCheckVisitePreventive.equals("1")) {
			FinalCheckbox = "1";
		} else {		
				FinalCheckbox = "0";

		}

		return FinalCheckbox;

	}

	public void LoadChoixfacturable(String IdentFacturable) {
		String stFinalCheck = IdentFacturable;

		if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("0")) {
			checkFacturable.setChecked(false);
			checkNonfacturable.setChecked(false);
		} else {
			if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("1")) {
				checkFacturable.setChecked(true);
				checkNonfacturable.setChecked(false);
			}
			if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("2")) {
				checkFacturable.setChecked(false);
				checkNonfacturable.setChecked(true);
			}

		}
	}
	public void LoadVisitePreventive(String IdentVisite) {
		String stFinalCheck = IdentVisite;

		if (Fonctions.GetStringDanem(stFinalCheck).trim().equals("1")) {
			checkVisitepreventive.setChecked(true);
			
		} else {
			checkVisitepreventive.setChecked(false);
		}
	}
	
	
	void LoadSignatureClient()
	{
		try{
			ExternalStorage externalStorage = new ExternalStorage(this, false);
			//File sdCard = Environment.getExternalStorageDirectory();
			
			File file = new File(externalStorage.getSignaturesFolder()+File.separator+ "rapportact_"+m_stDate+"_"+m_codeclient+".jpg");
			if(file.exists()){
				m_isSignClient=true;
				//Bitmap r = BitmapFactory.decodeFile(file.getAbsolutePath());
				//			Drawable r = (Drawable)BitmapDrawable.createFromPath(file.getAbsolutePath());
				//			iv_signatureclient.setBackgroundDrawable(r);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
				ivSignatureClient.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 120, 120, false));
				myBitmap = null;
			}
		}
		catch(Exception e){

		}
	}
	private void UI_updateProgressBar() {

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		switch (parent.getId()) {

		}
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		// m_stFam = idFam.get(pos).getString(
		// Global.dbParam.FLD_PARAM_CODEREC);
		// PopulateList() ;
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == bNextDate) {
			if (android.os.Build.VERSION.SDK_INT >= 14
				/*	&& ApplicationLauncher
							.isAgendanemAvailable(rapportactivite.this)*/) {
				
							launchAgenda(this, m_codeclient);
				// Non n√©cessaire d'afficher la boite de dialogue de
				// envclient car ancien m√©canisme
				// afficherDialogDate(null);
			}

		}
		if (v == bQuestionnaire) {
				launchQuestionnaire(this, m_codeclient, m_stDate, getTypeActivite_CodeQuestionnaire() ,m_numrapport);
			
		}
		if (v == bMachineclient) {
			launchMachineClient(this, m_codeclient );
		}
		if (v == bContact) {
			commandeActivity.launchContact(rapportactivite.this,m_codeclient,LAUNCH_CONTACT,true);		 
		
				
			
			
		
	}
		

	}
	public void launchMachineClient(Context c,String codecli)
	{
		
			Intent i = new Intent(c, InterventionActivityRA.class);

			Bundle b = new Bundle();

			b.putString("codeclient",  codecli);
			
			i.putExtras(b);

			startActivityForResult(i, 55555);
			
			
	}
	
	
	static public void launchQuestionnaire(Context c,String codecli,String date,String codequestionnaire,String numrapport)
	{
		if (Fonctions.GetStringDanem(codequestionnaire).equals(""))
		{
			Fonctions.FurtiveMessageBox(c, "Pas de questionnaire");
			return;
		}
		if(Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(codecli , date,numrapport).equals("") || Global.dbKDQuestionnaire.QuestionnaireExiste_rapport(codecli , date,numrapport).equals(codequestionnaire))
		{
			if(Global.dbKDClientVu.isQuestionnaireFait_rapport(codecli, date,numrapport)==true )
			{
				Fonctions.FurtiveMessageBox(c, "Questionnaire déjà envoyé.");
				return;
			}
			else
			{
				Bundle bundle = new Bundle();
				bundle.putString("codecli",codecli);
				bundle.putString("date",date);
				bundle.putString("codequestionnaire", codequestionnaire);
				bundle.putString("numrapport", numrapport);
				
				
				Intent i = new Intent(c, pac_plans_filler2.class);
				i.putExtras(bundle);
				c.startActivity (i);	
			}
				
		}
		else
		{
			Fonctions.FurtiveMessageBox(c, "Un autre questionnaire existe déjà pour ce rapport.");
			return;
		}
		
	}

	public static void launchAgenda(Activity a, String m_codeclient)
	{
		    StringBuilder err = new StringBuilder();
			structClient client = new structClient();
			
			Bundle b = new Bundle();
			b.putString(Agendanem.ApplicationName, "folliet");
			b.putString(Agendanem.ApplicationKey, "FLT");
			
			if (dbClient.getClient(m_codeclient, client, err))
			{	 
				b.putString(Agendanem.CodeClient, client.CODE);
				b.putString(Agendanem.AdresseClient, client.getFullAddress());
				b.putString(Agendanem.NameClient, client.NOM);
				Intent intent = new Intent(a,
						AgendaViewActivity.class);
				intent.putExtras(b);
				a.startActivityForResult(intent, LAUNCH_AGENDA);
			}
			else
			{
				Intent intent = new Intent(a,
						AgendaListActivity.class);
				intent.putExtras(b);
				a.startActivityForResult(intent, LAUNCH_AGENDA);
			}
	}
	Boolean ControleHoraire()
	{
		 boolean bres=false;
		 String stHeuredebut="";
		 String stHeurefin="";
		 int iheuredebut=0;
		 int iheurefin=0;
		 
		 
		 stHeuredebut=TimePicker_to_HHMM(this, R.id.timePickerHourDebut);
		 stHeurefin= TimePicker_to_HHMM(this, R.id.timePickerHourFin);
		 iheuredebut=Fonctions.GetStringToIntDanem(stHeuredebut);
		 iheurefin=Fonctions.GetStringToIntDanem(stHeurefin);
			
		 if(iheurefin<iheuredebut)
		 {
			 bres=true;
		 }
	 
		 
		 return bres; 
		 
	 }
	public void MessageYesNo(String message,String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton(getString(R.string.Yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				StringBuffer buff=new StringBuffer();
				
				saveYN( buff,"OUI");
				launchMenuCli(m_codeclient,"1",m_numrapport);
				finish();
							}
		})
		.setNegativeButton(getString(R.string.No),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				StringBuffer buff=new StringBuffer();
				
				saveYN( buff,"NON");
				
				finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	public void MessageYesNo_Technicien(String message,String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton(getString(R.string.Yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				StringBuffer buff=new StringBuffer();
				ArrayList<String> ValueMessage = new ArrayList();
				
				record(ValueMessage, buff);
				}
		})
		.setNegativeButton(getString(R.string.No),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				StringBuffer buff=new StringBuffer();
							
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	void launchPrinting() {

		 String date=Fonctions.getDD_MM_YYYY();
		 String numerointer=m_numrapport;
		 String refclient= m_codeclient;
		 String nomtechnicien=m_nomtechnicien;
		 String objetintervention=m_info_inter;
		 String travauxrealise=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditCommentaire));
		 String machineconcerne=m_libarticle;
		 String nomcontact=m_contactnom;
		 String nommachine = Global.dbMaterielClient.GetLblArticle(m_libarticle,m_codeclient);
		 	
		 String heurearrive=BaseActivity.TimePicker_to_HHMM(this, R.id.timePickerHourDebut);
		 String heuredepart=BaseActivity.TimePicker_to_HHMM(this, R.id.timePickerHourFin);
		 heurearrive= Fonctions.hhmm_to_hh_point_mm(heurearrive);
		 heuredepart= Fonctions.hhmm_to_hh_point_mm(heuredepart);
		 
		 
		 
		 
		Z420ModelRapport z=new Z420ModelRapport(this, "RA", Fonctions.GetStringDanem(Preferences.getValue(this, Espresso.LOGIN, "0")),date,numerointer,refclient,nomtechnicien,objetintervention,travauxrealise,machineconcerne,heurearrive,heuredepart,nomcontact,nommachine);
			
		String   zplData = "";

		try{
			//on récupère la liste découpée normal + gratuit
			//listPrintFactures = z.getListeProduit(m_numcde, duplicata);
			int i = 0;
			//for(int i = 0;i<listPrintFactures.size();i++){
				//on passe dans la méthode la liste à imprimer puis numéro de la page puis nombre de pages
			//}
			zplData+=z.getFacture(m_numrapport,m_codeclient,false,"RA",  i+1);
			
		}catch(Exception ex){
			zplData = getString(R.string.erreur_getfacture);
		
		}

		//launchPrintPreview(zplData, m_numrapport, m_btech,true);
		
		if(checkIfSignatureExists(m_numrapport)){
			launchPrintPreview(zplData, m_numrapport, m_btech,m_bresp,true,true);
		}else{
			Bundle bundle = new Bundle();
			bundle.putString("filename", "sign_rapport_"+m_numrapport);
			bundle.putString("zplData", zplData);
			bundle.putString("folderSignature", ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER));
			
			Intent i = new Intent(this, SignatureActivity.class);
			i.putExtras(bundle);
			
			startActivityForResult(i,10000);
			
		}
		
	}
	private boolean checkIfSignatureExists(String numCommande){
		File file = new File(ExternalStorage.getFolderPath(ExternalStorage.SIGNATURES_PDF_FOLDER)+"sign_rapport_"+numCommande+".jpg");
		return file.exists();
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	       
	    case DATE_PICKER_CDE:
	         
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

	
	

}
