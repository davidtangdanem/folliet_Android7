package com.menadinteractive.folliet2016;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;


import com.facebook.stetho.Stetho;
import com.maps.MapsActivity;
import com.menadinteractive.echangestock.EchangeStockActivity;
import com.menadinteractive.inventaire.InventaireActivity;
import com.menadinteractive.livraison.SuiviLivraisonActivity;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableContactcli;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.TableSouches.passeplat;
import com.menadinteractive.segafredo.db.dbKD;
import com.menadinteractive.segafredo.db.dbKD100Visite;
import com.menadinteractive.segafredo.db.dbKD101ClientVu;
import com.menadinteractive.segafredo.db.dbKD104AgendaSrv;
import com.menadinteractive.segafredo.db.dbKD105AgendaSrvSupp;
import com.menadinteractive.segafredo.db.dbKD106AgendaCorrespondance;
import com.menadinteractive.segafredo.db.dbKD451RetourMachineclient;
import com.menadinteractive.segafredo.db.dbKD451RetourMachineclient.structRetourMarchineClient;
import com.menadinteractive.segafredo.db.dbKD452ComptageMachineclient;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire;
import com.menadinteractive.segafredo.db.dbKD732InfoClientComplementaires;
import com.menadinteractive.segafredo.db.dbKD733Habitudes;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.db.dbKD981RetourBanqueEnt;
import com.menadinteractive.segafredo.db.dbKD981RetourBanqueEnt.structRetourBanque;
import com.menadinteractive.segafredo.db.dbKD98Encaissement;
import com.menadinteractive.segafredo.db.dbLog;
import com.menadinteractive.segafredo.db.dbParam;
import com.menadinteractive.segafredo.db.dbSiteHabitudes;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.encaissement.ReglementActivity;
import com.menadinteractive.segafredo.findcli.FindCliActivity;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.plugins.MiseAJour;
import com.menadinteractive.segafredo.rapportactivite.rapportactivite;
import com.menadinteractive.segafredo.remisebanque.RecapitulatifRemiseEnBanque;
import com.menadinteractive.segafredo.synchro.SynchroActivity;
import com.menadinteractive.segafredo.tarif.OptionTarifActivity;
import com.menadinteractive.segafredo.tasks.task_sendInventaireWSData;
import com.menadinteractive.segafredo.tasks.task_sendLogs;
import com.menadinteractive.segafredo.tasks.task_sendWSData;
import com.menadinteractive.segafredo.tournee.ListeTourneeActivity;
import com.menadinteractive.transfertstock.TransferstockActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends BaseActivity {
	private listAdapter m_adapter;
	private ProgressDialog m_ProgressDialog = null;
	String codeagent;
	TextView tvCodeAgent,tvNomAgent,tvVersion;
	LinearLayout llSynchro;
	ListView myListView;
	TextView tvSynchro;
	Handler hSync;
	int nbrDataToSend=0;
	ProgressBar pb;
	int numberOfClients = 0;
	int numberOfGeocodedClients = 0;
	boolean doubleBackToExitPressedOnce=false;
	ProgressDialog mProgressDialog = null;
	boolean bMapsActivity;

	structlistLogin rep = null;
	String stypeagent="";
	Activity mActivity;

	ExternalStorage externalStorage;

	
	Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		startServiceLockApp(this);
		initGui();
		initListerners();

		mActivity = this;
		mContext = this;
		externalStorage = new ExternalStorage(getApplicationContext(),false);

		copyAssets();
		controlIfDocumentInBuffer();

		//Debug database
		Stetho.initializeWithDefaults(this);

		//test MapsActivity à mettre false pour la prod
		//bMapsActivity = true;
		bMapsActivity = false;

		MyDB.copyFile(MyDB.source, MyDB.dest);
		//MyDB.copyFile(MyDB.dest, MyDB.source);
		hSync=getHandlerSync();

		//test
		String line="";
		line=Fonctions.insertStr(line, "marc", 5, 10, true,80);
		line=Fonctions.insertStr(line, "toto", 30, 5, true,80);

		String mm=String.valueOf(Fonctions.round( 2.25,1) );
		String totot="";
		
		restoreDB();
		
		Global.dbLog.Insert("Menu principal", "onCreate", "", "Démarrage application", "", "", "");

		if (bMapsActivity){

			ImageView imgClick = (ImageView) findViewById(R.id.imageView1);

			imgClick.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
					startActivity(intent);
				}
			});

		}

		//extract le danem BR
		File fc = externalStorage.getFileFromAssets("danembr_rel.apk");



	}
	void restoreDB()
	{
		MyDB.restoreDB(getLogin(),this);
	}
	//permet de dÈmarrer le service si nÈcessaire
	public void startServiceLockApp(Context ctx){
		Intent intent = new Intent();
		intent.setAction("android.LAUNCH_LOCKAPP");
		intent.putExtra("msg", "1");
		ctx.sendBroadcast(intent);
	}

	//si un document est dans le buffer cause plantage on doit le reprendre
	void controlIfDocumentInBuffer()
	{
		//on controle si un encaissement est en cours (on compte les documents non imprimés)
		dbKD98Encaissement enc = new dbKD98Encaissement(m_appState.m_db);
		ArrayList<Encaissement> encaissements = enc.getEncaissementNotPrint();
		if(encaissements != null && encaissements.size() > 0){

			String codeClient = "";
			for(Encaissement e : encaissements){
				if(e.getCodeClient() != null && !e.getCodeClient().equals("")){
					codeClient = e.getCodeClient();
					break;
				}
			}

			structClient cli=new structClient();
			Global.dbClient.getClient(codeClient, cli, new StringBuilder());

			Intent intent = new Intent(this, ReglementActivity.class);
			Bundle b = new Bundle();
			b.putString(Espresso.CODE_CLIENT, codeClient);
			b.putString(Espresso.CODE_SOCIETE, cli.SOC_CODE);
			intent.putExtras(b);
			startActivityForResult(intent, LAUNCH_REGLEMENT);
			return;
		}
		
		//on verifie si on a pas laiss� un piece non termin�e dans le buffer
		dbKD83EntCde cde=new dbKD83EntCde(m_appState.m_db);
		structEntCde structcde=new structEntCde();
		structcde=cde.getPieceNotSend();
		if (structcde!=null)
		{
			//on teste si le type de document est un CR
			if(structcde.TYPEDOC.equals(TableSouches.TYPEDOC_COMMANDE)){
				launchCdeBuffer(this, structcde.TYPEDOC, structcde.CODECLI, structcde.SOCCODE, true, structcde.CODECDE);
			}else{
				launchCdeBuffer(this, structcde.TYPEDOC, structcde.CODECLI, structcde.SOCCODE, true, structcde.CODECDE);
			}
			return;
		}

		dbKD451RetourMachineclient ret=new dbKD451RetourMachineclient(m_appState.m_db);
		structRetourMarchineClient structret=ret.getPieceNotPrint();
		if (structret!=null)
		{
			launchMaterielClient(structret.CODECLI,LAUNCH_MATERIELCLI);
			return;
		}

		//idem pour remise en banque
		dbKD981RetourBanqueEnt retour = new dbKD981RetourBanqueEnt(m_appState.m_db);
		ArrayList<structRetourBanque> remises = new ArrayList<dbKD981RetourBanqueEnt.structRetourBanque>();
		remises = retour.getRemiseNotPrint();
		if(remises != null && remises.size() > 0){
			SharedPreferences sharedpreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
			Set<String> set = sharedpreferences.getStringSet("sp_identifiants", null);
			ArrayList<String> identifiants = new ArrayList<String>();

			if(set != null){
				for(String id : set){
					identifiants.add(id);
				}
				if(identifiants != null && identifiants.size() > 0 && remises.get(0).NUMCDE != null && !remises.get(0).NUMCDE.equals("")){
					Intent intent = new Intent(this, RecapitulatifRemiseEnBanque.class);
					Bundle b = new Bundle();
					b.putString("num_remise", remises.get(0).NUMCDE);
					b.putStringArrayList("id_encaissement", identifiants);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
		}

	}


	void initGui()
	{
		tvSynchro=(TextView)findViewById(R.id.tvsynchro);
		tvVersion =(TextView)findViewById(R.id.textViewVersion);
		pb=(ProgressBar)findViewById(R.id.pb1);
		pb.setVisibility(View.GONE);

		llSynchro=(LinearLayout)findViewById(R.id.llsynchro);
		llSynchro.setBackgroundColor(Color.LTGRAY);

		tvSynchro.setText(getDocNonTransmis());

		StringBuffer sbVer=new StringBuffer();
		Global.curver=Fonctions.getVersion(this,sbVer);
		setTextViewText(this, R.id.textViewVersion,"v "+ sbVer.toString());

		majWithNewVersion(Global.curver);
		//on sauve la version actuelle
		Fonctions.WriteProfileString(this, "LASTVERSION", Global.curver+"");
		dispLoginInfo();
		PopulateList();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (tvSynchro!=null)
			tvSynchro.setText(getDocNonTransmis());

		if (dbKD543LinInventaire.isTimeToDoInvent(this))
		{
			promptText("Inventaire", "Pensez à faire votre inventaire le dernier jour travaillé du mois",false); 
		}
	}
	//si on vient de changer de version on fait des contrôles particuliers
	void majWithNewVersion(int curver)
	{
		String lastver=Fonctions.GetProfileString(this, "LASTVERSION", Global.curver+"");
		if (Fonctions.convertToInt(lastver)==109  && curver==110)
		{
			Global.dbKDEncaissement.DeleteAll();
			Global.dbKDEncaisserFacture.DeleteAll();
			Fonctions.FurtiveMessageBox(this, "maj version 109 => 110");
		}
	}

	void dispLoginInfo()
	{
		dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
		rep=new structlistLogin();
		login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
		codeagent=getLogin();
		stypeagent=rep.TYPE;
		setTextViewText(this, R.id.textViewCodeAgent,rep.LOGIN);
		setTextViewText(this, R.id.textViewNomAgent,rep.NOM);
		StringBuffer sbVer=new StringBuffer();
		Global.curver=Fonctions.getVersion(this,sbVer);
		boolean modetest=Preferences.getValueBoolean(this, Espresso.PREF_MODETEST, false);
		if (modetest)
		{
			setTextViewText(this, R.id.textViewVersion,"v "+ sbVer.toString() +" MODE TEST");
			tvVersion.setTextColor(Color.RED);

		}else
		{
			setTextViewText(this, R.id.textViewVersion,"v "+ sbVer.toString());
			tvVersion.setTextColor(Color.BLACK);
		}
	}
	void initListerners()
	{
		handler = getHandler();
		myListView.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				lineMenu o=(lineMenu)arg1.getTag();

				if (o.titre.equals(getString(R.string.menu_go)))
				{
					//		launchPrinting();
					//go();

					goFindCli(FindCliActivity.TYPE_CLIENT);
					return;
				}
				if (o.titre.equals(getString(R.string.Carto)))
				{
					//		launchPrinting();
					go_carto("");

					return;
				}
				if (o.titre.equals(getString(R.string.menu_plugins)))
				{
					goPlugins();
					return;
				}
				if (o.titre.equals(getString(R.string.menu_param)))
				{
					goParams();
					return;
				}
				
				if (o.titre.equals(getString(R.string.Synchro)) )
				{
					goSynchro(false);

					return;
				}
				if (o.titre.equals(getString(R.string.ActualisationTournee)) )
				{
					goSynchroTournee(false,true);
					return;
				}
				if (o.titre.equals(getString(R.string.menu_inventaire)))
				{
					if(controlReapproDechargementEnCours()==true)
						return;
					goInventaire();
					return;
				}
				/*	if (o.titre.equals(getString(R.string.menu_transfertStock)))
				{
					if (controlIsInventaireEnCours()==true) return;
					goTransfertStock();
					return;
				}
				 */

				if (o.titre.equals(getString(R.string.menu_ech_entre_livreur)))
				{
					goEchangeStock();
					return;
				}
				if (o.titre.equals(getString(R.string.facturesdues)) )
				{
					LaunchFacturesDues();
					return;
				}
				if (o.titre.equals(getString(R.string.reb_title)) )
				{
					if(Global.dbKDRetourBanqueEnt.countRemiseEnBanqueNonTransmises() > 0) AlertMessage(getString(R.string.reb_non_transmises), false);
				else LaunchRemiseEnBanque();
					return;
				}
				if (o.titre.equals(getString(R.string.mes_rendez_vous)) )
				{
					rapportactivite.launchAgenda(DashboardActivity.this, "");
					return;
				}
				if (o.titre.equals(getString(R.string.g_ocoder_le_fichier_client)) )
				{
					TableClient cli=new TableClient(getDB());

					numberOfClients=cli.Count();;
					numberOfGeocodedClients=cli.getClientsGeocodState(true).size();
					GeoCodeTask task=new GeoCodeTask();
					task.execute(false);
					//geocode();
					//rapportactivite.launchAgenda(DashboardActivity.this, "");
					return;
				}
				//g_ocoder_le_fichier_client

				if (o.titre.equals(getString(R.string.menu_commande_reappro)) ){
					//on récupère le code client ST<CODEVRP>
					String codeCli = "ST"+rep.LOGIN;
					launchCde(mActivity, TableSouches.TYPEDOC_FACTURE, codeCli, rep.SOC_CODE);
					return;
				}

				if (o.titre.equals(getString(R.string.commande_de_r_assort)) ){
					//on récupère le code client ST<CODEVRP>
					String codeCli = "ST"+rep.LOGIN;
					//launchCde(mActivity, TableSouches.TYPEDOC_FACTURE, codeCli, rep.SOC_CODE);
					launchMenuCli(codeCli);
					return;
				}

				if(o.titre.equals(getString(R.string.Prospect))){
					goFindCli(FindCliActivity.TYPE_PROSPECT);
					return;
				}
				
				if(o.titre.equals(getString(R.string.tarifs_general))){
					launchTarif();
					return;
				}

				if(o.titre.equals(getString(R.string.Tournee_princ))){
					launchTournee();
					return;
				}
				if(o.titre.equals(getString(R.string.menu_suivimarchandise))){
					launchSuiviMarchandise();
					return;
				}

				//mes_rendez_vous
				Fonctions.FurtiveMessageBox(DashboardActivity.this,"En construction...");
			}

		}
				);
	}

	boolean controlIsInventaireEnCours()
	{
		if (Global.dbKDLinInv.isInventaireEnCours())
		{
			Fonctions._showAlert(getString(R.string.inventaire_encours), this);
			return true;
		}
		return false;
	}
	boolean controlReapproDechargementEnCours()
	{
		if (Global.dbKDLinTransfert.isReaEnCours()==true && 1==0)
		{
			Fonctions._showAlert(getString(R.string.transfertstock_encours), this);
			return true;
		}
		return false;
	}

	boolean controlTVAParams()
	{
		dbParam param = new dbParam(getDB());

		boolean isTvaExists = param.getTVAParams();

		if(!isTvaExists)
		{
			Fonctions._showAlert(getString(R.string.tva_non_present), this);
			return true;
		}

		return false;
	}

	void goSynchro(boolean autolaunch)
	{
		Intent i = new Intent(this, SynchroActivity.class);
		i.putExtra("autolaunch", autolaunch);
		i.putExtra("tournee", false);
		
		startActivityForResult(i,LAUNCH_SYNCHRO);

		//	Intent i = new Intent(this, Mapview_polyline.class);
		//	startActivity(i);
	}
	void goSynchroTournee(boolean autolaunch,boolean tournee)
	{
		Intent i = new Intent(this, SynchroActivity.class);
		i.putExtra("autolaunch", autolaunch);
		i.putExtra("tournee", tournee);

		//startActivityForResult(i,LAUNCH_SYNCHRO);
		startActivity(i);

		//	Intent i = new Intent(this, Mapview_polyline.class);
		//	startActivity(i);
	}
	void goPlugins()
	{
		Intent i = new Intent(this, MiseAJour.class);
		startActivity(i);

		//	Intent i = new Intent(this, Mapview_polyline.class);
		//	startActivity(i);
	}
	int getRandom()
	{
		Random r = new Random();
		int Low = 1000;
		int High = 9999;
		int R = r.nextInt(High-Low) + Low;
		
		return R;
	}
	void goParams()
	{
		//Intent i = new Intent(this, prefs_authent.class);
		//startActivity(i);

		
		/*promptText(getString(R.string.saisissez_le_mot_de_passe_administrateur), R.string.menu_param, InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		*/
		int r=getRandom();
		promptText("Saisissez le code pour la clé : "+r, R.string.menu_param, InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD,r);
	}
	void goInventaire()
	{
		Intent i0=new Intent(DashboardActivity.this,
				InventaireActivity.class);


		startActivityForResult(i0, LAUNCH_INVENTAIRE);
		//	promptText(getString(R.string.saisissez_le_mot_de_passe_administrateur), R.string.menu_inventaire, InputType.TYPE_CLASS_TEXT
		//			| InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	void goEchangeStock()
	{
		Intent i0=new Intent(DashboardActivity.this,
				EchangeStockActivity.class);

		startActivityForResult(i0, LAUNCH_ECHANGESTOCK);
		//	promptText(getString(R.string.saisissez_le_mot_de_passe_administrateur), R.string.menu_inventaire, InputType.TYPE_CLASS_TEXT
		//			| InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	void goTransfertStock()
	{
		//promptText(getString(R.string.saisissez_le_mot_de_passe_administrateur), R.string.menu_transfertStock, InputType.TYPE_CLASS_TEXT
			//	| InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	//	Intent i = new Intent(this, InventaireActivity.class);
	//	startActivity(i);
	//}
	
	void launchTarif() {
		Intent intent = new Intent(mContext, OptionTarifActivity.class);
		startActivity(intent);
	}
    void launchTournee() {
		Intent intent = new Intent(mContext, ListeTourneeActivity.class);
		startActivity(intent);
	}
	void launchSuiviMarchandise() {
		Intent intent = new Intent(mContext, SuiviLivraisonActivity.class);
		startActivity(intent);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		boolean bTrySend = false ;  //modif tof 2018/03/15: pour tenter d'envoyer les données aprés client, prospect ou agenda
		if ( requestCode == LAUNCH_CLIENTorPROSPECT )
		{
			//v281: dans le doute, synchro des agenda aprés un passage par le menu client ou prospect
			//dbKD104AgendaSrv db104= new dbKD104AgendaSrv (Global.dbParam.getDB() ) ;
			//db104.CreateAllEvents("FLS", this) ;
			dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
			db106.Sync106(this) ;
			//BUG 40.5: reouverture a chaque changement de client de la DB (probleme de lenteur d'affichage des proforma)
			m_appState.startDB();
		}
		if(requestCode==LAUNCH_SYNCHRO)
		{
			String IpParam=Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_IP,"1");
			if(!Fonctions.GetStringDanem(IpParam).equals(""))
			{
				IpParam=IpParam.replace(" ","");
				Preferences.setValue(this, Espresso.IP,IpParam);
				//	Preferences.setValue(this, Espresso.IP,"195.6.81.38:8080");
			}
			else
				Preferences.setValue(this, Espresso.IP,"185.24.19.17:8083");

			/*if (resultCode==RESULT_OK)
			{*/
				//Intent restartIntent = new Intent(this, DashboardActivity.class);
				//restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Set this flag
				//restartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Set this flag
				//startActivity(restartIntent);
				//finish();

				Log.e("dashbordactivity","back from synchro resultok");

				System.gc();
			//}
		}
		if (requestCode==LAUNCH_AGENDA)
		{
			bTrySend = true ;
			if (resultCode==RESULT_OK)
			{
				String codeclient=data.getExtras().getString(AppBundle.CodeClient);
				if (codeclient.equals("")==false)
				{
					TableClient cli=new TableClient(m_appState.m_db);
					if (cli.getClient(codeclient, new structClient(),new StringBuilder()))
					{
						launchMenuCli(codeclient);
					}
				}
			}
		}
		else if (requestCode==LAUNCH_PARAM)
		{
			dispLoginInfo();

			//si checkbox souche on reinit les souches
			boolean souche=Preferences.getValueBoolean(this, Espresso.PREF_SOUCHE,false);

			//si on a coché reinit souche dans param ou si le code rep à changé
			//on réinit les souches
			if (souche || codeagent.equals(getLogin())==false)
			{
				TableSouches ts=new TableSouches(m_appState.m_db, this);
				ts.reset(Preferences.getValue(this, Espresso.LOGIN, "0"));
				Preferences.setValueBoolean(this, Espresso.PREF_SOUCHE,false);
				//		promptText(getString(R.string.raz_des_souches), getString(R.string.merci_de_refaire_une_sycnhro), false);

				goSynchro(true);
			}
			//si checkbox souche on reinit les souches
			boolean reset=Preferences.getValueBoolean(this, Espresso.PREF_RESET,false);
			if (reset)
			{
				dbKD kd=new dbKD();
				kd.clearAll(getDB());
				Preferences.setValueBoolean(this, Espresso.PREF_RESET,false);
				promptText("Reset de la base de données","Reset effectué", false);
			}
			boolean resetlog=Preferences.getValueBoolean(this, Espresso.PREF_RESETLOG,false);
			if (resetlog)
			{

				dbLog log=new dbLog(getDB());
				log.Clear(new StringBuilder());
				Preferences.setValueBoolean(this, Espresso.PREF_RESETLOG,false);
				promptText("Reset des logs","Reset log effectué", false);
			}

		}
		else if(requestCode == LAUNCH_REGLEMENT){
			controlIfDocumentInBuffer();
		}
		else {
			bTrySend = true;
		}
		if (bTrySend == true )
		{
			dispLoginInfo();
			PopulateList();
			//

			//on le met ici car lancé avant le onresume
			getDocNonTransmis();
			if (nbrDataToSend>0)
			{
				
				task_sendWSData wsCde = new task_sendWSData(m_appState,hSync, new task_sendWSData.OnTaskFinish() {
					
					@Override
					public void onTerminated() {
						//task_sendLogs sendLog = new task_sendLogs(mContext);
						//sendLog.execute();
					}
				});
				wsCde.execute();
				pb.setVisibility(View.VISIBLE);
				Log.d("TAG", "start synchro");
				m_ProgressDialog = ProgressDialog.show(this,
						"Veuillez patienter...", "Envoi en cours...", true);
			}

		}
		alertModeTest();
	}
	private void PopulateList()
	{
		ArrayList<lineMenu> colNames = new ArrayList<lineMenu>();
		this.m_adapter = new listAdapter(this, R.layout.main_row, colNames);

		myListView=(ListView)findViewById(R.id.listView1);
		myListView.setAdapter(this.m_adapter);

		colNames.add(setMenuLine(R.string.Carto,R.drawable.basic_2057_map, "",""));

		colNames.add(setMenuLine(R.string.menu_go,R.drawable.basic3_021_transport_delivery_van_shipping, "",""));

		if(!Fonctions.GetStringDanem(stypeagent).equals(Global.Technicien))
		{
			colNames.add(setMenuLine(R.string.Prospect,R.drawable.basic1_112_user_add_new, "",""));
		}
		
		colNames.add(setMenuLine(R.string.menu_param,R.drawable.basic2_299_gear_settings, "",""));
		colNames.add(setMenuLine(R.string.Synchro,R.drawable.basic1_102_wi_fi_wireless_router, "",""));

		if(Fonctions.GetStringDanem(stypeagent).equals(Global.Technicien))
		{
			colNames.add(setMenuLine(R.string.ActualisationTournee,R.drawable.basic1_102_wi_fi_wireless_router, "",""));
		}
		;

		if(Global.dbClient.BExisteRdv( this)==true)
		{
			colNames.add(setMenuLine(R.string.mes_rendez_vous,R.drawable.basic1_011_calendar, "","rouge"));

		}else
		      colNames.add(setMenuLine(R.string.mes_rendez_vous,R.drawable.basic1_011_calendar, "",""));

		//	colNames.add(setMenuLine(R.string.menu_catalogue,R.drawable.basic2_117_open_reading_book, ""));
		//	colNames.add(setMenuLine(R.string.facturesdues,R.drawable.basic2_018_money_coins, ""));
		//	colNames.add(createItem(getString(R.string.Carto), "basic_2057_map",""));


		TableSouches souche=new TableSouches(m_appState.m_db,this);		
		passeplat pp=new passeplat();

		if (souche.get(souche.TYPEDOC_REMISEBANQUE,getLogin(), pp))
			colNames.add(setMenuLine(R.string.reb_title,R.drawable.basic1_079_piggy_bank_saving, "",""));

		if (souche.get(souche.TYPEDOC_ECHANGE,getLogin(), pp))
			colNames.add(setMenuLine(R.string.menu_ech_entre_livreur,R.drawable.basic1_116_user_group, "",""));

		if (souche.get(souche.TYPEDOC_INVENTAIRE,getLogin(), pp))
			colNames.add(setMenuLine(R.string.menu_inventaire,R.drawable.basic2_243_write_compose_new_pencil_paper, "",""));

		//	colNames.add(setMenuLine(R.string.menu_transfertStock,R.drawable.basic2_138_arrows_directions, ""));
		//		colNames.add(setMenuLine(R.string.test_imprimante,R.drawable.basic1_049_print, ""));

		if (MiseAJour.isNewSoftwareAvailable(m_appState))
		{
			colNames.add(setMenuLine(R.string.menu_plugins,R.drawable.basic2_277_new_badge, "",""));
			goPlugins();
		}
		//colNames.add(setMenuLine(R.string.g_ocoder_le_fichier_client,R.drawable.basic2_059_pin_location, ""));

		//si un client de type codeclient st+codeclient est présent dans la base on affiche le bouton commande de réapprovisionnement
		//récupération d'un client st
		structClient clientST = Global.dbClient.load("ST"+rep.LOGIN);
		if(clientST != null && clientST.CODE != null && clientST.CODE.equals("ST"+rep.LOGIN)){
			//colNames.add(setMenuLine(R.string.menu_commande_reappro,R.drawable.basic2_170_box_gift, ""));
			colNames.add(setMenuLine(R.string.commande_de_r_assort,R.drawable.basic2_170_box_gift, "",""));
		}
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) )
		{
			
		}
		else
		 colNames.add(setMenuLine(R.string.tarifs_general,R.drawable.basic2_170_euro, "",""));

		if(!Fonctions.GetStringDanem(stypeagent).equals(Global.Technicien)) {
			if (Global.dbLivraison.Count_nosent() > 0) {
				colNames.add(setMenuLine(R.string.menu_suivimarchandise, R.drawable.basic2_117_open_reading_book, "", "rouge"));

			} else
				colNames.add(setMenuLine(R.string.menu_suivimarchandise, R.drawable.basic2_117_open_reading_book, "", ""));
		}

		if(!Fonctions.GetStringDanem(stypeagent).equals(Global.Technicien)) {
			colNames.add(setMenuLine(R.string.Tournee_princ, R.drawable.basic1_001_tournee, "", ""));
		}


	}

	class lineMenu
	{
		String titre;
		int icon;
		String hint;
		String color;

		lineMenu()
		{

		}
	}

	lineMenu setMenuLine(int titre, int img, String hint,String color)
	{
		lineMenu line=new lineMenu();
		line.titre=getString(titre);
		line.icon=img;
		line.hint=hint;
		line.color=color;


		return line;
	}

	private class listAdapter extends ArrayAdapter<lineMenu> {

		private ArrayList<lineMenu> items;

		public listAdapter(Context context, int textViewResourceId, ArrayList<lineMenu> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.main_row, null);
			}
			lineMenu o = items.get(position);

			if (o != null) {
				v.setTag(o);
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				ImageView imgVw = (ImageView)v.findViewById(R.id.icon);

				Typeface face=Typeface.createFromAsset(getAssets(), Global.FONT_REGULAR);
				tt.setTypeface(face);

				imgVw.setImageResource(o.icon);

				if (tt != null) {
					tt.setText(o.titre);
					if (!o.color.equals(""))
						tt.setTextColor(Color.RED);
					else
						tt.setTextColor(Color.BLACK);
				}
				if(bt != null){
					bt.setText(o.hint);
				}
			}
			return v;
		}
	}



	void goFindCli(String typeclient)
	{
		if (controlIsInventaireEnCours()==true) return;

		if(controlReapproDechargementEnCours()==true)
			return;

		if (controlTVAParams())
			return;

		Intent intent = new Intent(this, FindCliActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(FindCliActivity.KEY_TYPE, typeclient);
		intent.putExtras(bundle);
		startActivityForResult(intent, LAUNCH_CLIENTorPROSPECT);
	}

	private void copyAssets() {
		AssetManager assetManager = getAssets();
		String[] files = null;
		try {
			files = assetManager.list("");
		} catch (IOException e) {
			Log.e("tag", "Failed to get asset file list.", e);
		}catch(Exception ex)
		{

		}
		for(String filename : files) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = assetManager.open(filename);
				out = new FileOutputStream(ExternalStorage.getFolderPath(ExternalStorage.ROOT_FOLDER) + filename);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			} catch(IOException e) {
				Log.e("tag", "Failed to copy asset file: " + filename, e);
			} catch(Exception ex)
			{

			}
		}
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while((read = in.read(buffer)) != -1){
			out.write(buffer, 0, read);
		}
	}
	boolean debloq(int key,String mdp)
	{
		int L=key-Fonctions.convertToInt(Fonctions.Left(key+"",1));
		int val=L*12345;
		String str=Fonctions.Mid(val+"",2, 4);
		if (Fonctions.convertToInt(str)==Fonctions.convertToInt(mdp))
			return true;
		return false;
	}
	public void promptText(String title, int cible, int inputType,final int key) {

		boolean bres = false;
		final int cibles=cible;
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.textentrvalertdialog);
		dialog.setTitle(title);
		final EditText inputLine = (EditText) dialog
				.findViewById(R.id.my_edittext);
		inputLine.setSingleLine(true);
		inputLine.setHint("Password");

		Button okButton = (Button) dialog.findViewById(R.id.OKButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
				String PasswordSiege = "";
				PasswordSiege = "powertv";
				if (inputLine.getText().toString().equals(PasswordSiege)
						|| inputLine.getText().toString().equals("aie")|| debloq( key,  inputLine.getText().toString())) {
					Intent i0 = null;
					int id=0;
					if (cibles==R.string.menu_param)
					{


						i0=new Intent(DashboardActivity.this,
								prefs_authent.class);
						id=LAUNCH_PARAM;
					}
					else if (cibles==R.string.menu_inventaire)
						i0=new Intent(DashboardActivity.this,
								InventaireActivity.class);
					else if (cibles==R.string.menu_transfertStock)
						i0=new Intent(DashboardActivity.this,
								TransferstockActivity.class);

					startActivityForResult(i0, id);
				}
				else 
				{
					Fonctions.FurtiveMessageBox(DashboardActivity.this,getString(R.string.passworderrone));
				}
			}
		});
		Button cancelButton = (Button) dialog
				.findViewById(R.id.CancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		inputLine.setInputType(inputType);

		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		dialog.show();
	}

	public void MessageRecupCde(String message ) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {


			}
		})
		.setNegativeButton(getString(android.R.string.no),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce=false;                       
			}
		}, 2000);
	} 

	public void AlertMessage(String message, final boolean quit) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				if(quit) finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			addMenu(menu, R.string.hotline,R.drawable.basic2_233_help);
		 
		 	return true;
		}
	 
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {

			case R.string.hotline:
				Fonctions.sendEmailBaseDonneegPieceJointe(this,getLogin(),"hotline@danem.com","","","","Message adressé à la hotline de la part de FOLLIET "+getLogin());
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	 }
	 
	String getDocNonTransmis()
	{
		String val="";
		nbrDataToSend=0;

		dbKD83EntCde cde=new dbKD83EntCde(getDB());
		int nbr_docvente=cde.CountToSend();
		nbrDataToSend+=nbr_docvente;
		if (nbr_docvente>0)
		{
			val+=nbr_docvente+" doc(s) vente(s), ";
		}

		dbKD543LinInventaire inv=new dbKD543LinInventaire(getDB());
		int nbr_lininv=inv.Count(false);
		nbrDataToSend+=nbr_lininv;
		if (nbr_lininv>0)
		{
			val+=nbr_lininv+" ligne(s) d'inventaire, ";
		}
/*
		dbKD546EchangeStock ech=new dbKD546EchangeStock(getDB());
		int nbr_linech=ech.Count(false);
		nbrDataToSend+=nbr_linech;
		if (nbr_linech>0)
		{
			val+=nbr_linech+" lignes echangées, ";
		}
		 */
		TableClient cli=new TableClient(getDB());
		int nbr_cli=cli.CountModified2();
		nbrDataToSend+=nbr_cli;
		if (nbr_cli>0)
		{
			val+=nbr_cli+" client(s) modifié(s), ";
		}
		
		TableClient cliC=new TableClient(getDB());
		int nbr_cliC=cliC.CountCreated2();
		nbrDataToSend+=nbr_cliC;
		if (nbr_cliC>0)
		{
			val+=nbr_cliC+" prospect(s) créé(s), ";
		}

		dbKD732InfoClientComplementaires info=new dbKD732InfoClientComplementaires(getDB());
		int nbrgeo=info.Count();
		nbrDataToSend+=nbrgeo;
		if (nbrgeo>0)
		{
			val+=nbrgeo+" géocodage(s), ";
		}

		dbKD733Habitudes habit=new dbKD733Habitudes(getDB());
		int nbrhabit=habit.Count();
		nbrDataToSend+=nbrhabit;
		if (nbrhabit>0)
		{
			val+=nbrhabit+" habitudes(s), ";
		}

		dbKD451RetourMachineclient rm=new dbKD451RetourMachineclient(getDB());
		int nbr_rm=rm.Count();
		nbrDataToSend+=nbr_rm;
		if (nbr_rm>0)
		{
			val+=nbr_rm+" matériel(s) retourné(s), ";
		}

		dbKD452ComptageMachineclient pm=new dbKD452ComptageMachineclient(getDB());
		int nbr_pm=pm.Count();
		nbrDataToSend+=nbr_pm;
		if (nbr_pm>0)
		{
			val+=nbr_pm+" matériel(s) prêté(s), ";
		}

		dbKD981RetourBanqueEnt rb=new dbKD981RetourBanqueEnt(getDB());
		int nbr_rb=rb.countRemiseEnBanqueNonTransmises();
		nbrDataToSend+=nbr_rb;
		if (nbr_rb>0)
		{
			val+=nbr_rb+" Remise en banque, ";
		}

		dbKD98Encaissement enc=new dbKD98Encaissement(getDB());
		int nbr_enc=enc.countNonTransmises();
		nbrDataToSend+=nbr_enc;
		if (nbr_enc>0)
		{
			val+=nbr_enc+" encaissement(s), ";
		}

		TableContactcli contact=new TableContactcli(getDB());
		int nbr_contact=contact.countNonTransmis();
		nbrDataToSend+=nbr_contact;
		if (nbr_contact>0)
		{
			val+=nbr_contact+" contact(s) modifié(s), ";
		}

		//dbKD100Visite clivu=new dbKD100Visite(getDB());
		dbKD101ClientVu clivuR=new dbKD101ClientVu(getDB());
		int nbr_clivuR=clivuR.countModified();
		nbrDataToSend+=nbr_clivuR;
		if (nbr_clivuR>0)
		{
			//val+=nbr_clivu+" rapport(s) d'activité(s), ";
		}
		
		dbKD100Visite clivu=new dbKD100Visite(getDB());
		int nbr_clivu=clivu.countModified();
		nbrDataToSend+=nbr_clivu;
		if (nbr_clivu>0)
		{
			//val+=nbr_clivu+" rapport(s) d'activité(s), ";
		}
		//ajout 20180315 tof
		dbKD105AgendaSrvSupp agendasrvsupp=new dbKD105AgendaSrvSupp(getDB());
		int nbr_agenda_srv=agendasrvsupp.Count(true);
		nbrDataToSend+=nbr_agenda_srv;
		if (nbr_agenda_srv>0)
		{
			val+=nbr_agenda_srv+" agenda(s) serveur supprimé(s), ";
		}

		//dbKD94Marchandise marchandise=new dbKD94Marchandise(getDB());
		int imarchandise=Global.dbKDMarchandise.Count_Sent();
		nbrDataToSend+=imarchandise;
		if (imarchandise>0)
		{
			val+=imarchandise+" marchandise(s) reçue(s), ";
		}

		/*dbLog log=new dbLog(getDB());
		int nbr_log=log.Count();
		if (nbr_log>100)
		{
			val+=nbr_log+" logs, ";
		}*/

		//ajout 20180315 tof


		val=Fonctions.Left(val, val.length()-2);
		if (val.equals("")==false)
		{
			val="A transmettre : "+val;
		}
		else
		{
			val="Aucune donnée à transmettre";
		}
		return val;
	}

	Handler getHandlerSync() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				if (msg.what==455)
				{
					pb.setVisibility(View.GONE);
					m_ProgressDialog.dismiss();
					Log.d("TAG", "stop synchro");
					if (tvSynchro!=null)
						tvSynchro.setText(getDocNonTransmis());
				}
				if (msg.what==456)
				{
					m_ProgressDialog.dismiss();
					Log.d("TAG", "stop geocoding");
					dbKD732InfoClientComplementaires info=new dbKD732InfoClientComplementaires(getDB());
					promptText("Geocoding", info.Count() +" adresse(s) géocodée(s)", false);
					if (info.Count()>0)
					{
						task_sendWSData send=new task_sendWSData(m_appState, hSync, new task_sendWSData.OnTaskFinish() {
							
							@Override
							public void onTerminated() {
								//task_sendLogs sendLog = new task_sendLogs(mContext);
								//sendLog.execute();
							}
						});
						send.execute();
					}
				}

			}
		};
		return h;
	}


	Handler handler;

	Handler getHandler(){
		Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch(msg.what) {
				case MESSAGE_SHOW_PROGRESS_DIALOG:
					if(mProgressDialog == null)
						mProgressDialog = ProgressDialog.show(DashboardActivity.this,    
								getString(R.string.geocoding_in_progress), "", true);
					break;
				case MESSAGE_HIDE_PROGRESS_DIALOG:
					if(mProgressDialog != null){
						mProgressDialog.dismiss();
						mProgressDialog = null;
					}
					break;
				case MESSAGE_CHANGE_PROGRESS_DIALOG_MESSAGE:
					String dialogMessage = msg.getData().getString(BUNDLE_KEY_DIALOG_MESSAGE);
					if(mProgressDialog != null)
						mProgressDialog.setMessage(dialogMessage);
					else{
						mProgressDialog = ProgressDialog.show(DashboardActivity.this,    
								getString(R.string.geocoding_in_progress), dialogMessage, true);
					}
					break;




				}

			}
		};
		return h;
	}
	public static final String BUNDLE_KEY_DIALOG_MESSAGE = "dialogMessage";
	public static final String BUNDLE_CLIENT_CODE_MESSAGE = "clientCodeMessage";
	public static final int MESSAGE_SHOW_PROGRESS_DIALOG = 200;
	public static final int MESSAGE_HIDE_PROGRESS_DIALOG = 201;
	public static final int MESSAGE_CHANGE_PROGRESS_DIALOG_MESSAGE = 202;
	public static final int MESSAGE_CHANGE_TEXTVIEW_NUMBER_OF_GEOCODED = 203;
	public static final int MESSAGE_CLIENT_SELECTED_ON_MAP = 204;
	public static final int MESSAGE_INVALIDATE_MAP = 205;
	private void updateDialogMessage(String message){
		Message m = new Message();
		m.what = MESSAGE_CHANGE_PROGRESS_DIALOG_MESSAGE;
		Bundle b = new Bundle();
		b.putString(BUNDLE_KEY_DIALOG_MESSAGE, message);
		m.setData(b);
		handler.sendMessage(m);
	}

	private void updateUINumberOfGeoCodedClients(String message){
		Message m = new Message();
		m.what = MESSAGE_CHANGE_TEXTVIEW_NUMBER_OF_GEOCODED;
		Bundle b = new Bundle();
		b.putString(BUNDLE_KEY_DIALOG_MESSAGE, message);
		m.setData(b);
		handler.sendMessage(m);
	}

	/** Threads */
	private class GeoCodeTask extends AsyncTask<Boolean, Integer, Cursor> {
		boolean geocodeAllClients = true;
		final Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		int progressionFailed = 0;

		@Override
		protected Cursor doInBackground(Boolean... arg) {
			Cursor result = null;
			geocodeAllClients = arg[0];
			progressionFailed = 0;
			int countGeocode=0;

			TableClient cli=new TableClient(m_appState.m_db);
			ArrayList<structClient> clis=cli.getClientsGeocodState(false);
			for (int i=0;i<clis.size()  ;i++)
			{
				Address address = getCoordinate(clis.get(i));

				if(address != null){
					//						Log.d("TAG", "lat/lon : "+address.getLatitude()+"/"+address.getLongitude()+" for client : "+client.CODECLIENT);
					cli.updateLatLon(Double.toString(address.getLatitude()) ,Double.toString(address.getLongitude()),clis.get(i).CODE    );

					numberOfGeocodedClients++;
					publishProgress(0);
				}
				else{
					//						Log.e("TAG", "[GEOCODE] "+client.CODECLIENT+" - "+client.ADRES1 + ", " + client.CPOST + ", "+ client.VILLE+ ", " + client.PAYS);
					progressionFailed++;
					publishProgress(0);
				}



			}

			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
			updateDialogMessage(getDialogMessage());
			updateUINumberOfGeoCodedClients(numberOfGeocodedClients+"/"+numberOfClients);
		}

		@Override
		protected void onPostExecute(Cursor result) {
			TableClient cli=new TableClient(m_appState.m_db);
			ArrayList<structClient> clis=cli.getClientsGeocodState(true);

			numberOfGeocodedClients = clis.size();
			updateUINumberOfGeoCodedClients(numberOfGeocodedClients+"/"+numberOfClients);

			handler.sendEmptyMessage(MESSAGE_HIDE_PROGRESS_DIALOG);
			//						MyDB.copyFile(MyDB.source, MyDB.dest);
		}

		private String getDialogMessage(){
			String result = getString(R.string.success)+": "+numberOfGeocodedClients+"/"+numberOfClients
					+"\n"
					+getString(R.string.fail)+": "+progressionFailed;

			return result;
		}

		private Address getCoordinate( structClient client){
			List<Address> address;
			try {
				address = geoCoder.getFromLocationName(client.ADR1 + ", " + client.CP + ", "+ client.VILLE+ ", " + client.PAYS, 1);
				if(address != null && address.size()>0){
					return address.get(0);
				}
				else{
					address = geoCoder.getFromLocationName(client.ADR2 + ", " + client.CP + ", "+ client.VILLE+ ", " + client.PAYS, 1);
					if(address != null && address.size()>0){
						return address.get(0);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		


	}
}
