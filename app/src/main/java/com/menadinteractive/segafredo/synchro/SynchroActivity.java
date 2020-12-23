package com.menadinteractive.segafredo.synchro;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.folliet2016.app;
import com.menadinteractive.segafredo.communs.DateCode;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.MyWS;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableHistoInter;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbCliToVisit;
import com.menadinteractive.segafredo.db.dbKD;
import com.menadinteractive.segafredo.db.dbKD104AgendaSrv;
import com.menadinteractive.segafredo.db.dbKD106AgendaCorrespondance;
import com.menadinteractive.segafredo.db.dbKD545StockTheoSrv;
import com.menadinteractive.segafredo.db.dbKD729HistoDocuments;
import com.menadinteractive.segafredo.db.dbKD730FacturesDues;
import com.menadinteractive.segafredo.db.dbKD731HistoDocumentsLignes;
import com.menadinteractive.segafredo.db.dbParam;
import com.menadinteractive.segafredo.db.dbSiteHabitudes;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbTournee;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.tasks.task_sendWSData;

import static java.sql.Types.NULL;

public class SynchroActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
	ListView myListView;
    boolean ssl;
    String login, password;
    String m_mois="";


	MySimpleArrayAdapter myAdapter;
    ArrayList<Bundle> list;
	int nbrError=0;//nbr de scripts en erreur de connexion lors d'une synchro
	int nbrErrorClient = 0 ; //pour blocage modif client/prospect
	Button bGO;
	EditText editMois;
	private ProgressDialog m_ProgressDialog = null;
	String scenario = "";
	ArrayList<String> script = new ArrayList<String>();
	MultiSelectionSpinner spinnerAgence;
    ArrayList<Bundle> idAgences = null;
    String sTypeAgent;
    LinearLayout linearAgence;

	final String scenario_getagenda 			= "GETAGENDA";
	final String scenario_getagendasupp 		= "GETAGENDA105";
	final String scenario_agents				= "GETAGENTSV9";
	final String scenario_produits 				= "GETARTICLESV4";
	final String scenario_produitstech 			= "GETARTICLESV4TECH";
	final String scenario_clitovisit			= "GETCLIENTAVISITERV10";
	final String scenario_clients				= "GETCLIENTSV110";
	final String scenario_contactcli 			= "GETCONTACTS2";
	final String scenario_facturesdues			= "GETFACTURESDUES";
	final String scenario_habitudes 			= "GETHABITUDES1";
	final String scenario_histodocent 			= "GETHISTODOCENTNEW";
	final String scenario_histodocenttech 		= "GETHISTODOCENTTECH";
	final String scenario_histodoclin			= "GETHISTODOCLINNEW";
	final String scenario_histodoclintech 		= "GETHISTODOCLINTECH";
	final String scenario_histointer 			="GETHISTOINTERV12";      //= "GETHISTOINTERV11";
	final String scenario_histointertournee 	= "GETHISTOINTERV12T";//= "GETHISTOINTERV11T";
	final String scenario_histotodo 			= "GETHISTOTODO";
	final String scenario_listemachine 		= "GETLISTEMACHINE";
	final String scenario_listtarifdetails 	= "GETLISTTARIFDETAILSV3";
	final String scenario_listtarif 			= "GETLISTTARIFV3";
	final String scenario_livraison 			= "GETLIVRAISONS";
	final String scenario_materielclient 		= "GETMATERIELCLIENTV5";
	final String scenario_param 				= "GETPARAM";
	final String scenario_questionnaire 		= "GETQUESTIONNAIRE1";
	final String scenario_souches 				= "GETSOUCHES";
	final String scenario_stocktheo				= "GETSTOCKTHEO";
	final String scenario_Symptomes 			= "GETSYMPTOMESV4";
 	final String scenario_tarif					= "GETTARIFS";
	final String scenario_tarifresp 			= "GETTARIFSRESP";
	final String scenario_tariftech 			= "GETTARIFSTECH";
	final String scenario_tournee 				= "GETTOURNEE11";

	final String scenario_updateapp 			= "UPDATEAPP";

	boolean m_btech=false;
	boolean m_bresp=false;
	String m_stunique="";

	boolean m_autotournee=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_synchro);

		login = Preferences.getValue(this, Espresso.LOGIN, "0");
		password = Preferences.getValue(this, Espresso.PASSWORD, "0");
		ssl=Preferences.getValueBoolean(this, Espresso.SSL,false);

		m_mois= Preferences.getValue(this, Espresso.MOIS, "");

		idAgences = new ArrayList<Bundle>();

		initVars();
		initGui();
		initListeners();
		fillAgence();

	}

	void initGui() {
		myListView = (ListView) findViewById(R.id.listView1);
		bGO = (Button) findViewById(R.id.buttonGO);
		editMois = (EditText) findViewById(R.id.edit_nbmois);
		editMois.setText(Fonctions.GetStringDanem(m_mois));
		spinnerAgence = (MultiSelectionSpinner) findViewById(R.id.spinnerAgence);


		spinnerAgence.setOnItemSelectedListener(this);

		myListView.setAdapter(myAdapter);
		
		Bundle bundle = this.getIntent().getExtras();
		boolean autolaunch =getBundleValueBool(bundle, "autolaunch") ; 
		m_autotournee =getBundleValueBool(bundle, "tournee") ;

		if (autolaunch)
		{
			Log.e("autolaunch","autolaunch");
			///spinnerAgence.
			goSync();
		}
		if (m_autotournee)
		{
			goSyncTournee();
		}
		
	}

	protected void initVars() {
		list = new ArrayList<Bundle>();
		myAdapter = new MySimpleArrayAdapter(this, list);
		structlistLogin rep = null;
		dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
		rep=new structlistLogin();
		
		login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) )
		{
			m_btech=true;
		}
		else
		{
			if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) )
			{
				m_bresp=true;
			}
		}

		linearAgence = (LinearLayout) findViewById(R.id.linear_agence);

		list.add(createItem(scenario_materielclient, getString(R.string.listematerielclient),
				Global.dbMaterielClient.Count() + ""));
		dbTournee to = new dbTournee(m_appState.m_db);
		list.add(createItem(scenario_tournee,
				"Tournées", to.Count( ) + ""));

		list.add(createItem(scenario_clients, getString(R.string.clients),
				Global.dbClient.Count() + ""));
		
		dbCliToVisit tv = new dbCliToVisit(m_appState.m_db);
		list.add(createItem(scenario_clitovisit,
				"Clients à visiter", tv.Count( ) + ""));
		
		TableHistoInter hi=new TableHistoInter(getDB());
		list.add(createItem(scenario_histointer,
				"Histo interventions", hi.Count( ) + ""));
		list.add(createItem(scenario_contactcli, getString(R.string.contacts),
				Global.dbContactcli.Count() + ""));

		if(m_btech==true)
		{
			list.add(createItem(scenario_produitstech, getString(R.string.produits),
					Global.dbProduit.Count() + ""));
		}
		else
			list.add(createItem(scenario_produits, getString(R.string.produits),
					Global.dbProduit.Count() + ""));
		
		list.add(createItem(scenario_param, getString(R.string.param_tres),
				Global.dbParam.Count() + ""));
	//	list.add(createItem(scenario_societe, getString(R.string.info_soci_t_),
	//			Global.dbSoc.Count() + ""));
		if(m_btech==true)
		{
			list.add(createItem(scenario_tariftech, getString(R.string.tarif),
					Global.dbTarif.Count() + ""));
			
		}
		else
		{
			if(m_bresp==true)
			{
				list.add(createItem(scenario_tarifresp, getString(R.string.tarif),
						Global.dbTarif.Count() + ""));
				
			}else
			list.add(createItem(scenario_tarif, getString(R.string.tarif),
					Global.dbTarif.Count() + ""));
			
		}
		
	//	list.add(createItem(scenario_listemachine, getString(R.string.listemachine),
		//			Global.dbListeMateriel.Count() + ""));

		dbKD545StockTheoSrv st = new dbKD545StockTheoSrv(m_appState.m_db);
		list.add(createItem(scenario_stocktheo,
				getString(R.string.stock_th_orique), st.Count(false) + ""));

		TableSouches souche = new TableSouches(m_appState.m_db, this);
		list.add(createItem(scenario_souches, getString(R.string.souches),
				souche.Count() + ""));

		dbSiteListeLogin agents = new dbSiteListeLogin(m_appState.m_db);
		list.add(createItem(scenario_agents, getString(R.string.agents),
				agents.Count() + ""));
		
		
		dbKD729HistoDocuments hfe = new dbKD729HistoDocuments(m_appState.m_db);
		
		if(m_btech==true)
		{
			list.add(createItem(scenario_histodocenttech, getString(R.string.histodoc),
					hfe.Count() + ""));
		}
		else
		{
			list.add(createItem(scenario_histodocent, getString(R.string.histodoc),
					hfe.Count() + ""));
		}
		
		

		dbKD731HistoDocumentsLignes hdl = new dbKD731HistoDocumentsLignes(
				m_appState.m_db);
		
		if(m_btech==true)
		{
			list.add(createItem(scenario_histodoclintech,
					getString(R.string.histodoclin), hdl.Count() + ""));

		}
		else
		{
			list.add(createItem(scenario_histodoclin,
					getString(R.string.histodoclin), hdl.Count() + ""));

		}
		
		
		dbKD730FacturesDues fd = new dbKD730FacturesDues(m_appState.m_db);
		list.add(createItem(scenario_facturesdues,
				getString(R.string.facturesdues), fd.Count() + ""));
		
		list.add(createItem(scenario_habitudes,
				getString(R.string.habitudes), Global.dbSiteHabitudes.Count() + ""));
		
		
		list.add(createItem(scenario_Symptomes, getString(R.string.symptomes),
				Global.dbSymptomes.Count() + ""));

		list.add(createItem(scenario_questionnaire, getString(R.string.questionnaire),
				Global.dbQuestionnaire.Count() + ""));
		list.add(createItem(scenario_getagenda, getString(R.string.agenda),
				Global.dbKDAgendaSrv.Count() + ""));
		//ajout 20180315 tof
	//	list.add(createItem(scenario_getagendasupp, getString(R.string.agendasupp),
	//			Global.dbKDAgendaSrvSupp.Count(false) + ""));

		
		if(m_btech==false)
		{
			list.add(createItem(scenario_listtarif, getString(R.string.listtarif),
					Global.dbListeTarif.Count() + ""));
			
			
			list.add(createItem(scenario_listtarifdetails, getString(R.string.listtarifdetails),
					Global.dbListeTarifsDetails.Count() + ""));
				
		}
		list.add(createItem(scenario_livraison, getString(R.string.livraisons),
				Global.dbLivraison.Count() + ""));

		linearAgence.setVisibility(View.GONE);
		if (m_bresp)
		{
			linearAgence.setVisibility(View.VISIBLE);
		}
	}

	void initListeners() {
		bGO.setOnClickListener(this);
	}

	Bundle createItem(String scenario, String lbl, String count) {
		Bundle b = new Bundle();
		b.putString("scenario", scenario);
		b.putString("lbl", lbl);
		b.putString("counttable", count);
		return b;
	}

	void infoItem(String scenario, int count) {
		for (int i = 0; i < list.size(); i++) {
			String courant = list.get(i).getString("scenario");
			if (courant.equals(scenario)) {
				list.get(i).putInt("countreceive", count);
				break;
			}

		}
	}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MySimpleArrayAdapter extends ArrayAdapter<Bundle> {
		private final Context context;
		ArrayList<Bundle> values;

		public MySimpleArrayAdapter(Context context, ArrayList<Bundle> values) {
			super(context, R.layout.item_synchro, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.item_synchro, parent,
					false);

			Bundle o = values.get(position);
			String lbl = o.getString("lbl");
			String counttable = o.getString("counttable");
			int countreceive = getBundleValueInt(o, "countreceive");

			if (o != null) {
				TextView tt = (TextView) rowView.findViewById(R.id.text1);
				TextView tvcounttable = (TextView) rowView
						.findViewById(R.id.textcounttable);
				TextView tvcountreceive = (TextView) rowView
						.findViewById(R.id.textcountreceive);

				tt.setText(lbl);
				tvcounttable.setText(getString(R.string.dans_la_base_actuelle_)
						+ counttable);

				tvcountreceive.setText(getString(R.string.re_u) + countreceive);
			}

			return rowView;
		}
	}

	@Override
	public void onClick(View v) {
		if (v == bGO) {

			Log.e("click","click GO");

			String codeAgence = spinnerAgence.getSelectedItemsAsString();

			/*if (codeAgence.equals("") && codeAgence != null){
				promptText(getString(R.string.erreur_choix_agence), getString(R.string.saisie_agence), false);
				//				//return;
			}*/

			goSync();
		}
	}

	void goSync()
	{
		m_stunique=GetNumWSUnique(Fonctions.GetStringDanem(login));
		bGO.setEnabled(false);
		
		script.add(scenario_param);
		//script.add(scenario_updateapp);
		script.add(scenario_clitovisit);
		script.add(scenario_materielclient);
		script.add(scenario_tournee);
		script.add(scenario_stocktheo);
		script.add(scenario_histointer);
		script.add(scenario_facturesdues);
		if(m_btech==true)
		{
			script.add(scenario_produitstech);
		}
		else {
			script.add(scenario_produits);
		}
		
		script.add(scenario_clients);
	//	script.add(scenario_societe); a ne pas remettre
		script.add(scenario_contactcli);
		if(m_btech==true)
		{
			script.add(scenario_tariftech);
		}
		else
		{
			if(m_bresp==true)
			{
				script.add(scenario_tarifresp);
			}
			else {
				script.add(scenario_tarif);
			}
		}
		
		
		//script.add(scenario_listemachine);	a ne pas remettre
		script.add(scenario_souches);
		script.add(scenario_agents);
		if(m_btech==true)
		{
			script.add(scenario_histodocenttech);
		}
		else
		  script.add(scenario_histodocent);
		
		if(m_btech==true)
		{
			script.add(scenario_histodoclintech);
		}
		else
			script.add(scenario_histodoclin);
		
		
		
		script.add(scenario_habitudes);
		
		script.add(scenario_Symptomes);
		script.add(scenario_histotodo);
		script.add(scenario_questionnaire);
		script.add(scenario_getagenda);
		//ajout 20180315 tof
		script.add(scenario_getagendasupp);



		if(m_btech==false)
		{
			script.add(scenario_listtarif);
			script.add(scenario_listtarifdetails);
			
		}
		script.add(scenario_livraison);

		
		//debug recup agenda
		//script.clear();
		//script.add(scenario_getagenda);
		//script.add(scenario_getagendasupp);


		String mydateDebut = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		int imydateDebut = Fonctions.GetStringToIntDanem(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
		int duree=0;
		String stduree="";
		Global.dbLLogWs.save(login,mydateDebut,"Début connexion","0",mydateDebut,"OK",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);

		getWSData(login, password,false);
		task_sendWSData.getKemsLogWs(login, password);

    }
	
	void goSyncTournee()
	{
		bGO.setEnabled(false);
		script.add(scenario_histointertournee);
		script.add(scenario_tournee);

		script.add(scenario_histotodo);

		getWSData(login, password,true);
		task_sendWSData.getKemsLogWs(login, password);
	}
	
	
	private Runnable progress = new Runnable() {
		@Override
		public void run() {
			m_ProgressDialog = ProgressDialog.show(SynchroActivity.this,
					"Veuillez patienter...", "Synchro en cours...", true);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	/**
	 * @author Marc VOUAUX cette fonction permet au thread d'intÔøΩragir avec
	 *         l'interface grace ÔøΩ runOnUiThread(returnRes);
	 */
	private Runnable returnRes = new Runnable() {

		@Override
		public void run() {
			bGO.setEnabled(true);
			
			if(m_autotournee==true)
				finish();
			
			if (nbrError>0)
				promptText(getString(R.string.r_sultat_de_la_synchro),nbrError+getString(R.string._erreur_s_),false);//+ dbKD104AgendaSrv.stMes,false);
			else{
				//promptText(getString(R.string.r_sultat_de_la_synchro), getString(R.string.synchronisation_r_ussie_)+ dbKD104AgendaSrv.stMes, true);
				promptText(getString(R.string.r_sultat_de_la_synchro), getString(R.string.synchronisation_r_ussie_), true);
			}



			//pour blocage modif client/prospect
			if ( nbrErrorClient == 0 )
				Preferences.setValue(SynchroActivity.this, Espresso.DATESYNCHRO, Fonctions.getYYYYMMDD());

		}
	};

	String scenarioEncours = "";
			private Runnable changeMessage = new Runnable() {
				@Override
				public void run() {
			// Log.v(TAG, strCharacters);
			// on suprime DANEM.FR des messages par sécurité
			if (scenarioEncours != null)
				if (scenarioEncours.contains("danem.fr"))
					scenarioEncours = scenarioEncours.replace(".danem.fr", "");

			
				m_ProgressDialog.setMessage(scenarioEncours);

			myAdapter.notifyDataSetChanged();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	void receive(boolean alreadyInThread) {
		if (alreadyInThread == false)
			runOnUiThread(progress);

		try {
			if (m_autotournee==true)
					 Thread.sleep(1000);

			String mois = editMois.getText().toString();
			String dateFilter = "";

			if (!mois.equals("")) {
				Preferences.setValue(this, Espresso.MOIS, mois);
				int nbmois = Integer.parseInt("-" + mois);

				Date referenceDate = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(referenceDate);
				c.add(Calendar.MONTH, nbmois);
				Date date = c.getTime();
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				dateFilter = dateFormat.format(date);
			}

			String codeAgence = spinnerAgence.getSelectedItemsAsString();

			Log.e("codeAgence", "cdoeAgence" + codeAgence);

			String[] listAgence = codeAgence.split(", ");

			ArrayList<Bundle> listeCodeCom = new ArrayList<Bundle>();

			for (int i = 0; i < listAgence.length; i++) {
				String nomAgence = listAgence[i];
				Global.dbParam.getCodeComByAgence(listeCodeCom, nomAgence);
				//Log.e("nomagence","nomagence"+nomAgence);
			}

			//Log.e("listeCodeCom","list"+listeCodeCom);

			nbrError = 0;
			int nbr = script.size();
			Log.e("nbr","nbr"+script.size());
			Log.e("script","script=>"+script);
			for (int i = 0; i < nbr; i++) {
				Thread.sleep(500);
				scenario = script.get(i);

				scenarioEncours = getString(R.string.sync_msgreception)
						+ scenario;

				runOnUiThread(changeMessage);

				switch (scenario) {

					case scenario_updateapp:

						if (downloadVersionMaj(login)) {
							doMajProgram();
						}

						break;
					case scenario_histodocent:

						String filterPreference="";
						String filterAgence="";
						String filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND dat_idx10 > '" + dateFilter + "'";

						}
						if (m_bresp) {

							if (listAgence.length > 0) {

								for (int ii = 0; ii < listAgence.length; ii++) {

									String nomAgence = listAgence[ii];
									if (ii == 0) {
										filterAgence += " AND ( dat_data30 ='" + nomAgence + "'";
									} else {
										filterAgence += " OR dat_data30 ='" + nomAgence + "'";
									}
									filterPreference+=nomAgence+";";


									if (ii == listAgence.length - 1) {
										filterAgence += ")";
									}
								}

							}

							if (filterAgence.equals(" AND ( dat_data30 ='')"))
							{

							}else
								filter += filterAgence+ " order by dat_idx02 ";

							getBorne(filter);

						} else {
							filter += " order by dat_idx02 ";


							getBorne(filter);

						}



						break;

					case scenario_histodoclin:

						String filterAgenceLin="";
						filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND dat_data01 > '" + dateFilter + "'";

						}
						if (m_bresp) {

							if (listAgence.length > 0) {

								for (int ii = 0; ii < listAgence.length; ii++) {
									String nomAgence = listAgence[ii];
									if (ii == 0) {
										filterAgenceLin += " AND ( dat_data30 ='" + nomAgence + "'";
									} else {
										filterAgenceLin += " OR dat_data30 ='" + nomAgence + "'";
									}

									if (ii == listAgence.length - 1) {
										filterAgenceLin += ")";
									}
								}
							}

							if (filterAgenceLin.equals(" AND ( dat_data30 ='')"))
							{

							}else
								filter += filterAgenceLin+ " order by dat_idx02,pro_code ";

							getBorne(filter);

						} else {
							filter += " order by dat_idx02,pro_code ";

							getBorne(filter);

						}


						break;

					case scenario_histodocenttech:

						filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND dat_idx10 > '" + dateFilter + "'";
							filter += " order by dat_idx02 ";
							//Log.e("filter","filter+"+filter);
						}
						//getBorne();
						getBorne(filter);

						break;

					case scenario_histodoclintech:

						filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND dat_data01 > '" + dateFilter + "'";
							filter += " order by dat_idx02,pro_code ";
							Log.e("filter", "filter+" + filter);
						}
						//getBorne();
						getBorne(filter);

						break;

					case scenario_histointertournee:

						filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND DAT_CREA_INTER > '" + dateFilter + "'";
							filter += " order by codecli,num_inter ";
							//Log.e("filter","filter+"+filter);
						}
						//getBorne();
						getBorne(filter);

						break;

					case scenario_histointer:

						String filterAgenceInter = "";
						filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND DAT_CREA_INTER > '" + dateFilter + "'";

						}
						if (m_bresp) {

							if (listAgence.length > 0) {

								for (int ii = 0; ii < listAgence.length; ii++) {
									String nomAgence = listAgence[ii];
									if (ii == 0) {
										filterAgenceInter += " AND ( AGENCE ='" + nomAgence + "'";
									} else {
										filterAgenceInter += " OR AGENCE ='" + nomAgence + "'";
									}

									if (ii == listAgence.length - 1) {
										filterAgenceInter += ")";
									}
								}
							}

							if (filterAgenceInter.equals(" AND ( AGENCE ='')"))
							{

							}else
								filter += filterAgenceInter+ " order by codecli,num_inter ";

							getBorne(filter);

						} else {
							filter += " order by codecli,num_inter ";

							getBorne(filter);

						}


						break;

					case scenario_histotodo:

						filter = "";
						if (!dateFilter.equals("")) {
							filter = " AND date_deb > '" + dateFilter + "'";
							filter += " order by evt_id,cod_cli ";
							//Log.e("filter","filter+"+filter);
						}
						//getBorne();
						getBorne(filter);

						break;

					case scenario_clients:

						if (m_bresp) {

							filter = "";

							if (listAgence.length > 0) {

								for (int ii = 0; ii < listAgence.length; ii++) {
									String nomAgence = listAgence[ii];
									if (ii == 0) {
										filter += " AND ( AGENCE ='" + nomAgence + "'";
									} else {
										filter += " OR AGENCE ='" + nomAgence + "'";
									}

									if (ii == listAgence.length - 1) {
										filter += ")";
									}
								}
							}

							//Log.e("filter", "filter+" + filter);
							//}
							//getBorne();
							if (filter.equals(" AND ( AGENCE ='')"))
								filter = " ";

							filter += " order by code ";

							getBorne(filter);

						} else {
							filter = " order by code ";

							getBorne(filter);

						}

						break;

					case scenario_tarifresp:

						filter = "";

						if (m_bresp) {


							/*if (listeCodeCom.size() > 0) { //TODO

								for (int ii = 0; ii < listeCodeCom.size(); ii++) {
									String lbl = listeCodeCom.get(ii).getString("prm_lbl");
									Log.e("lbl", "lbl" + lbl);
									if (ii == 0) {
										filter += " AND ( CODECOM ='" + lbl + "'";
									} else {
										filter += " OR CODECOM ='" + lbl + "'";
									}

									if (ii == listeCodeCom.size() - 1) {
										filter += ")";
									}

								}

								//filter = " AND CODECOM ='" +filter +"'";
								filter += " order by cod_cli,cod_pro,datefrom ";
								Log.e("filter", "filter+" + filter);
							}*/
							getBorne(filter);
						} else {
							getBorne(filter);
						}

						break;

					case scenario_materielclient:

						filter = "";
						if (m_bresp) {

						/*if (listeCodeCom.size() > 0){ //TODO

							for (int ii = 0; ii < listeCodeCom.size(); ii++){
								String lbl = listeCodeCom.get(ii).getString("prm_lbl");
								Log.e("lbl","lbl"+lbl);
								if (ii == 0){
									filter += " AND ( CODECOM ='" +lbl +"'";
								} else {
									filter += " OR CODECOM ='"+lbl +"'";
								}

								if (ii == listeCodeCom.size()-1){
									filter += ")";
								}

							}

							//filter = " AND CODECOM ='" +filter +"'";
							filter += " order by par_codeclient, par_numseriefab ";
							Log.e("filter","filter+"+filter);
						}*/
							getBorne(filter);
							//getBorne(filter);
						} else {
							getBorne(filter);
						}

						break;

					default:
						getBorne("");
				}

			//
				/*if (scenario.equals(scenario_updateapp))
				{
					if (downloadVersionMaj(login))
					{
						doMajProgram();
					}
				}
				else
				{
					if (scenario.equals(scenario_histodocent) ){

						String filter = "";
						if (!dateFilter.equals("")){
							filter = " AND dat_idx10 > '" +dateFilter +"'";
							filter += " order by dat_idx02 ";
							//Log.e("filter","filter+"+filter);
						}
						//getBorne();
						getBorne(filter);
					}
					else
					{
						if (scenario.equals(scenario_histodoclin) ){
							String filter = "";
							if (!dateFilter.equals("")){
								filter = " AND dat_data01 > '" +dateFilter +"'";
								filter += " order by dat_idx02,pro_code ";
								//Log.e("filter","filter+"+filter);
							}
							//getBorne();
							getBorne(filter);
						}
						else
						{
							if (scenario.equals(scenario_histodocenttech) ){
								String filter = "";
								if (!dateFilter.equals("")){
									filter = " AND dat_idx10 > '" +dateFilter +"'";
									filter += " order by dat_idx02 ";
									//Log.e("filter","filter+"+filter);
								}
								//getBorne();
								getBorne(filter);
							}
							else
							{
								if (scenario.equals(scenario_histodoclintech) ){
									String filter = "";
									if (!dateFilter.equals("")){
										filter = " AND dat_data01 > '" +dateFilter +"'";
										filter += " order by dat_idx02,pro_code ";
										Log.e("filter","filter+"+filter);
									}
									//getBorne();
									getBorne(filter);
								}
								else
								{
									if (scenario.equals(scenario_histointertournee) ){
										String filter = "";
										if (!dateFilter.equals("")){
											filter = " AND DAT_CREA_INTER > '" +dateFilter +"'";
											filter += " order by codecli,num_inter ";
											//Log.e("filter","filter+"+filter);
										}
										//getBorne();
										getBorne(filter);
									}
									else {

										if (scenario.equals(scenario_histointer) ){
											String filter = "";
											if (!dateFilter.equals("")){
												filter = " AND DAT_CREA_INTER > '" +dateFilter +"'";
												filter += " order by codecli,num_inter ";
												//Log.e("filter","filter+"+filter);
											}
											//getBorne();
											getBorne(filter);
										} else{
											if (scenario.equals(scenario_histotodo) ){
												String filter = "";
												if (!dateFilter.equals("")){
													filter = " AND date_deb > '" +dateFilter +"'";
													filter += " order by evt_id,cod_cli ";
													//Log.e("filter","filter+"+filter);
												}
												//getBorne();
												getBorne(filter);

											}
											else
											{
												if (scenario.equals(scenario_clients))
												{
													//getBorne();

													if (m_bresp){

														String filter = "";

														if (listAgence.length > 0){

															for( int ii = 0; ii < listAgence.length ; ii++)
															{
																String nomAgence = listAgence[ii];
																if (ii == 0){
																	filter += " AND ( AGENCE ='" +nomAgence +"'";
																} else {
																	filter += " OR AGENCE ='"+nomAgence +"'";
																}

																if (ii == listAgence.length-1){
																	filter += ")";
																}
															}

														}
														/*if (listeCodeCom.size() > 0){


															for (int ii = 0; ii < listeCodeCom.size(); ii++){
																String lbl = listeCodeCom.get(ii).getString("prm_lbl");
																Log.e("lbl","lbl"+lbl);
																if (ii == 0){
																	filter += " AND ( CODECOM ='" +lbl +"'";
																} else {
																	filter += " OR CODECOM ='"+lbl +"'";
																}

																 if (ii == listeCodeCom.size()-1){
																	filter += ")";
																 }

															}*/

			//filter = " AND CODECOM ='" +filter +"'";
			//filter += " order by cod_cli,cod_pro,datefrom ";
			//Log.e("filter","filter+"+filter);
			//}
			//getBorne();
														/*if(filter.equals(" AND ( AGENCE ='')"))
															filter=" ";

														filter += " order by code ";

														getBorne(filter);

													} else {
														String filter = " order by code ";

														getBorne(filter);

													}

												}
												else
												{
													if (scenario.equals(scenario_tarifresp))
													{
														if(m_bresp) {

															String filter = "";
															/*if (listeCodeCom.size() > 0) {

																for (int ii = 0; ii < listeCodeCom.size(); ii++) {
																	String lbl = listeCodeCom.get(ii).getString("prm_lbl");
																	Log.e("lbl", "lbl" + lbl);
																	if (ii == 0) {
																		filter += " AND ( CODECOM ='" + lbl + "'";
																	} else {
																		filter += " OR CODECOM ='" + lbl + "'";
																	}

																	if (ii == listeCodeCom.size() - 1) {
																		filter += ")";
																	}

																}

																//filter = " AND CODECOM ='" +filter +"'";
																filter += " order by cod_cli,cod_pro,datefrom ";
																Log.e("filter", "filter+" + filter);
															}*/
															//getBorne();
			                                         //} else {
														/*    getBorne();
														}

														//getBorne(filter); //en attente mise en prod
													} else{
														if (scenario.equals(scenario_materielclient)){
															String filter = "";
															if(m_bresp){

															/*if (listeCodeCom.size() > 0){

																for (int ii = 0; ii < listeCodeCom.size(); ii++){
																	String lbl = listeCodeCom.get(ii).getString("prm_lbl");
																	Log.e("lbl","lbl"+lbl);
																	if (ii == 0){
																		filter += " AND ( CODECOM ='" +lbl +"'";
																	} else {
																		filter += " OR CODECOM ='"+lbl +"'";
																	}

																	if (ii == listeCodeCom.size()-1){
																		filter += ")";
																	}

																}

																//filter = " AND CODECOM ='" +filter +"'";
																filter += " order by par_codeclient, par_numseriefab ";
																Log.e("filter","filter+"+filter);
															}*/
															/*getBorne();
															//getBorne(filter);
															} else {
																getBorne();
															}
														} else {
															getBorne();
														}
													}

												}

											}

										}

									}
								}


							}

						}

					}

				}*/

			}

		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}
		// dismiss the progressdialog
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String mydateDebut = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		int imydateDebut = Fonctions.GetStringToIntDanem(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
		int duree=0;
		String stduree="";
		Global.dbLLogWs.save(login,mydateDebut,"Fin connexion","0",mydateDebut,"OK",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);

        task_sendWSData.getTableLogWs(login, password);


		//Tentative de correction du bug 40.9
		//Global.dbKDAgendaSrv.CreateAllEvents("FLS", SynchroActivity.this);		//creation des rdv serveur avant pour recup aprés dans 102
		//dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
		//db106.Sync106(SynchroActivity.this) ;

		Global.dbKDAgendaSrv.sync104vers106( SynchroActivity.this);		//creation des rdv serveur avant pour recup aprés dans 102

		// dismiss the progressdialog
		if (alreadyInThread == false)
		{
			m_ProgressDialog.dismiss();
		 }
		runOnUiThread(returnRes);

		m_appState.startDB();
	}

    void fillAgence() {
        try {

            if (Global.dbParam.getAgencesByAgent( this.idAgences,login) == true) {

                int pos = -1;
                String[] items = new String[idAgences.size()];
                for (int i = 0; i < idAgences.size(); i++) {

                    items[i] = idAgences.get(i).getString(
							Global.dbParam.FLD_PARAM_COMMENT);

					 String codeRec = idAgences.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

                }


                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 //       android.R.layout.simple_spinner_item, items);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerAgence.setItems(items);
				boolean bres=false;


				if(bres==false)
				{
					for (int i = 0; i < idAgences.size(); i++) {

						spinnerAgence.setSelection(i);
					}
				}


            }



        } catch (Exception ex) {
			Log.e("exception","ex"+ex.getLocalizedMessage());
        }

    }

	boolean getBorne() {
		int STEP = 1000;
		String LineComplete = "";
		int incr = 0;
		int nbrLine = 0, nbrLineTotal = 0;
		String insert = "";

		Date interestingDate = new Date();
		ArrayList<String[]> arrLines = new ArrayList<String[]>();
		long time = (new Date()).getTime() - interestingDate.getTime();

        String mydateDebut = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		long imydateDebut=Long.parseLong(Fonctions.getYYYYMMDDhhmmss()); ;//Fonctions.getLongToStringDanem(Fonctions.getYYYYMMDDhhmmss());
		long duree=0;
		String stduree="";
		Global.dbLLogWs.save(login,mydateDebut,scenario,"0",mydateDebut,"Début",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);

		do {

			MyWS.Result resultWS = new MyWS.Result();
			if (ssl==false)
				resultWS = MyWS.WSQueryZippedExBorne(login, password, scenario, "",
						incr, STEP);
			else
				resultWS = MyWS.WSQueryZippedExBorneSSL(login, password, scenario, "",
						incr, STEP);
			
			if (resultWS.isConnSuccess() == false) {
				scenarioEncours = getString(R.string.errconnect);
				scenarioEncours = resultWS.getExceptionMessage();
				runOnUiThread(changeMessage);
				Log.d("TAG",
						scenario + " -> Error packet: " + String.valueOf(incr));
				

				nbrError++;
				if (scenario.equals(scenario_clients))
					nbrErrorClient++ ;

				String mydatfin = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
				long imydatefin=Long.parseLong(Fonctions.getYYYYMMDDhhmmss());Fonctions.GetStringToDoubleDanem(Fonctions.getYYYYMMDDhhmmss());

				duree=imydatefin-imydateDebut;
				stduree=Fonctions.getLongToStringDanem(duree);

				Global.dbLLogWs.save(login,mydateDebut,scenario,stduree,mydatfin,"PAS OK",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);


				//Log Error

				return false;
			}

			String[] lines = MyWS
					.get_EXECEXINSERT_Values(resultWS.getContent());
			nbrLine = Fonctions.convertToInt(lines[0]);
			// insert=lines[1];
			// for (int l=2;l<lines.length;l++)
			// LineComplete+=lines[l]+"\n";
			arrLines.add(lines);
			Log.d("TAG",
					scenario + " -> Recu: " + String.valueOf(incr + nbrLine));
			incr += STEP;

			nbrLineTotal += nbrLine;
		} while (nbrLine == STEP);

		String mydatfin = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		long imydatefin=Long.parseLong(Fonctions.getYYYYMMDDhhmmss());//Fonctions.GetStringToDoubleDanem(Fonctions.getYYYYMMDDhhmmss());
		duree=imydatefin-imydateDebut;
		stduree=Fonctions.getLongToStringDanem(duree);
		Global.dbLLogWs.save(login,mydateDebut,scenario,stduree,mydatfin,"OK",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);




		if (scenario.equals(scenario_produits))
			Global.dbProduit.Clear(new StringBuilder());
		if (scenario.equals(scenario_produitstech))
			Global.dbProduit.Clear(new StringBuilder());
		
		if (scenario.equals(scenario_clients))
			Global.dbClient.clear(new StringBuilder());
		if (scenario.equals(scenario_contactcli))
			Global.dbContactcli.clear(new StringBuilder());
	//	if (scenario.equals(scenario_societe))
	//		Global.dbSoc.Clear(new StringBuilder());
		if (scenario.equals(scenario_param))
			Global.dbParam.Clear(new StringBuilder());
		if(m_btech==true)
		{
			if (scenario.equals(scenario_tariftech))
				Global.dbTarif.clear(new StringBuilder());
		}
		else
		{
			if(m_bresp==true)
			{
				if (scenario.equals(scenario_tarifresp))
					Global.dbTarif.clear(new StringBuilder());
			}
			else
			{
				if (scenario.equals(scenario_tarif))
					Global.dbTarif.clear(new StringBuilder());
			}
		}
		
	
		
		
		if (scenario.equals(scenario_listtarif))
			Global.dbListeTarif.clear(new StringBuilder());
		if (scenario.equals(scenario_Symptomes))
			Global.dbSymptomes.clear(new StringBuilder());
		if (scenario.equals(scenario_listtarifdetails))
			Global.dbListeTarifsDetails.clear(new StringBuilder());
		if (scenario.equals(scenario_materielclient))
			Global.dbMaterielClient.clear(new StringBuilder());
		if (scenario.equals(scenario_listemachine))
			Global.dbListeMateriel.clear(new StringBuilder());
		
		if (scenario.equals(scenario_histotodo))
			Global.dbHistoTodo.clear(new StringBuilder());

		if (scenario.equals(scenario_questionnaire))
			Global.dbQuestionnaire.clear(new StringBuilder());

		if (scenario.equals(scenario_livraison))
			Global.dbLivraison.clear(new StringBuilder());

		if (scenario.equals(scenario_getagenda))
			Global.dbKDAgendaSrv.clear(new StringBuilder());
		//ajout 20180315 tof
		if (scenario.equals(scenario_getagendasupp))
		Global.dbKDAgendaSrvSupp.clear(new StringBuilder());

		if (scenario.equals(scenario_souches)) {
			TableSouches souche = new TableSouches(m_appState.m_db, this);
			souche.clear(new StringBuilder());
		}
		if (scenario.equals(scenario_agents)) {
			dbSiteListeLogin agent = new dbSiteListeLogin(m_appState.m_db);
			agent.Clear(new StringBuilder());
		}
		if(m_btech==true)
		{
			if (scenario.equals(scenario_histodocenttech)) {
				dbKD729HistoDocuments hfe = new dbKD729HistoDocuments(
						m_appState.m_db);
				hfe.clear(new StringBuilder());
			}
		}
		else
		{
			if (scenario.equals(scenario_histodocent)) {
				dbKD729HistoDocuments hfe = new dbKD729HistoDocuments(
						m_appState.m_db);
				hfe.clear(new StringBuilder());
			}
		}
		if(m_btech==true)
		{
			if (scenario.equals(scenario_histodoclintech)) {
				dbKD731HistoDocumentsLignes hdl = new dbKD731HistoDocumentsLignes(
						m_appState.m_db);
				hdl.clear(new StringBuilder());
			}
		}
		else
		{
			if (scenario.equals(scenario_histodoclin)) {
				dbKD731HistoDocumentsLignes hdl = new dbKD731HistoDocumentsLignes(
						m_appState.m_db);
				hdl.clear(new StringBuilder());
			}
		}
		
		if (scenario.equals(scenario_facturesdues)) {
			dbKD730FacturesDues fd = new dbKD730FacturesDues(m_appState.m_db);
			fd.clear(new StringBuilder());
		}
		
		if (scenario.equals(scenario_habitudes)) {
			dbSiteHabitudes habitudes = new dbSiteHabitudes(m_appState.m_db);
			habitudes.clear(new StringBuilder());
		}
		if (scenario.equals(scenario_stocktheo)) {
			dbKD545StockTheoSrv fd = new dbKD545StockTheoSrv(m_appState.m_db);
			fd.clear(false,new StringBuilder());
		}
	 
		if (scenario.equals(scenario_tournee)) {
			dbTournee to = new dbTournee(m_appState.m_db);
			to.Clear( new StringBuilder());
		}
		if (scenario.equals(scenario_clitovisit)) {
			dbCliToVisit tv = new dbCliToVisit(m_appState.m_db);
			tv.Clear(new StringBuilder()  );
		}
		if (scenario.equals(scenario_histointer)) {
			TableHistoInter hi = new TableHistoInter(m_appState.m_db);
			hi.clear(new StringBuilder());
		}
		if (scenario.equals(scenario_histointertournee)) {
			TableHistoInter hi = new TableHistoInter(m_appState.m_db);
			hi.deletedate_creat_encours();
		}
		infoItem(scenario, nbrLineTotal);
		ArrayList<String> timeIntegre = new ArrayList<String>();

		scenarioEncours = getString(R.string.sync_msgintegration) + scenario;


		//Global.dbParam.getDB().compact(new StringBuilder());
		
		runOnUiThread(changeMessage);

		boolean b=true;
		if (nbrLineTotal>0)
			b = integreWSDataArray(arrLines, STEP, timeIntegre, m_appState,scenario);

		return b;
	}

	boolean getBorne(String filter) {
		int STEP = 1000;
		String LineComplete = "";
		int incr = 0;
		int nbrLine = 0, nbrLineTotal = 0;
		String insert = "";

		Date interestingDate = new Date();
		ArrayList<String[]> arrLines = new ArrayList<String[]>();
		long time = (new Date()).getTime() - interestingDate.getTime();

		String mydateDebut = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		long imydateDebut=Long.parseLong(Fonctions.getYYYYMMDDhhmmss()); ;//Fonctions.getLongToStringDanem(Fonctions.getYYYYMMDDhhmmss());
		long duree=0;
		String stduree="";
		Global.dbLLogWs.save(login,mydateDebut,scenario,"0",mydateDebut,"Début",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);

		do {

			MyWS.Result resultWS = new MyWS.Result();
			if (ssl==false)
				resultWS = MyWS.WSQueryZippedExBorne(login, password, scenario, filter,
						incr, STEP);
			else
				resultWS = MyWS.WSQueryZippedExBorneSSL(login, password, scenario, filter,
						incr, STEP);

			if (resultWS.isConnSuccess() == false) {
				scenarioEncours = getString(R.string.errconnect);
				scenarioEncours = resultWS.getExceptionMessage();
				runOnUiThread(changeMessage);
				Log.d("TAG",
						scenario + " -> Error packet: " + String.valueOf(incr));


				nbrError++;
				if (scenario.equals(scenario_clients))
					nbrErrorClient++ ;

				String mydatfin = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
				long imydatefin=Long.parseLong(Fonctions.getYYYYMMDDhhmmss());Fonctions.GetStringToDoubleDanem(Fonctions.getYYYYMMDDhhmmss());

				duree=imydatefin-imydateDebut;
				stduree=Fonctions.getLongToStringDanem(duree);

				Global.dbLLogWs.save(login,mydateDebut,scenario,stduree,mydatfin,"PAS OK",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);


				//Log Error

				return false;
			}

			String[] lines = MyWS
					.get_EXECEXINSERT_Values(resultWS.getContent());
			nbrLine = Fonctions.convertToInt(lines[0]);
			// insert=lines[1];
			// for (int l=2;l<lines.length;l++)
			// LineComplete+=lines[l]+"\n";
			arrLines.add(lines);
			Log.d("TAG",
					scenario + " -> Recu: " + String.valueOf(incr + nbrLine));
			incr += STEP;

			nbrLineTotal += nbrLine;
		} while (nbrLine == STEP);

		String mydatfin = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		long imydatefin=Long.parseLong(Fonctions.getYYYYMMDDhhmmss());//Fonctions.GetStringToDoubleDanem(Fonctions.getYYYYMMDDhhmmss());
		duree=imydatefin-imydateDebut;
		stduree=Fonctions.getLongToStringDanem(duree);
		Global.dbLLogWs.save(login,mydateDebut,scenario,stduree,mydatfin,"OK",returnNumber(),returnOperateur_Etat(),returnWifi(),m_stunique);




		if (scenario.equals(scenario_produits))
			Global.dbProduit.Clear(new StringBuilder());
		if (scenario.equals(scenario_produitstech))
			Global.dbProduit.Clear(new StringBuilder());

		if (scenario.equals(scenario_clients))
			Global.dbClient.clear(new StringBuilder());
		if (scenario.equals(scenario_contactcli))
			Global.dbContactcli.clear(new StringBuilder());
		//	if (scenario.equals(scenario_societe))
		//		Global.dbSoc.Clear(new StringBuilder());
		if (scenario.equals(scenario_param))
			Global.dbParam.Clear(new StringBuilder());
		if(m_btech==true)
		{
			if (scenario.equals(scenario_tariftech))
				Global.dbTarif.clear(new StringBuilder());
		}
		else
		{
			if(m_bresp==true)
			{
				if (scenario.equals(scenario_tarifresp))
					Global.dbTarif.clear(new StringBuilder());
			}
			else
			{
				if (scenario.equals(scenario_tarif))
					Global.dbTarif.clear(new StringBuilder());
			}
		}




		if (scenario.equals(scenario_listtarif))
			Global.dbListeTarif.clear(new StringBuilder());
		if (scenario.equals(scenario_Symptomes))
			Global.dbSymptomes.clear(new StringBuilder());
		if (scenario.equals(scenario_listtarifdetails))
			Global.dbListeTarifsDetails.clear(new StringBuilder());
		if (scenario.equals(scenario_materielclient))
			Global.dbMaterielClient.clear(new StringBuilder());
		if (scenario.equals(scenario_listemachine))
			Global.dbListeMateriel.clear(new StringBuilder());

		if (scenario.equals(scenario_histotodo))
			Global.dbHistoTodo.clear(new StringBuilder());

		if (scenario.equals(scenario_questionnaire))
			Global.dbQuestionnaire.clear(new StringBuilder());

		if (scenario.equals(scenario_livraison))
			Global.dbLivraison.clear(new StringBuilder());

		if (scenario.equals(scenario_getagenda))
			Global.dbKDAgendaSrv.clear(new StringBuilder());
		//ajout 20180315 tof
		//if (scenario.equals(scenario_getagendasupp))
		Global.dbKDAgendaSrvSupp.clear(new StringBuilder());

		if (scenario.equals(scenario_souches)) {
			TableSouches souche = new TableSouches(m_appState.m_db, this);
			souche.clear(new StringBuilder());
		}
		if (scenario.equals(scenario_agents)) {
			dbSiteListeLogin agent = new dbSiteListeLogin(m_appState.m_db);
			agent.Clear(new StringBuilder());
		}
		if(m_btech==true)
		{
			if (scenario.equals(scenario_histodocenttech)) {
				dbKD729HistoDocuments hfe = new dbKD729HistoDocuments(
						m_appState.m_db);
				hfe.clear(new StringBuilder());
			}
		}
		else
		{
			if (scenario.equals(scenario_histodocent)) {
				dbKD729HistoDocuments hfe = new dbKD729HistoDocuments(
						m_appState.m_db);
				hfe.clear(new StringBuilder());
			}
		}
		if(m_btech==true)
		{
			if (scenario.equals(scenario_histodoclintech)) {
				dbKD731HistoDocumentsLignes hdl = new dbKD731HistoDocumentsLignes(
						m_appState.m_db);
				hdl.clear(new StringBuilder());
			}
		}
		else
		{
			if (scenario.equals(scenario_histodoclin)) {
				dbKD731HistoDocumentsLignes hdl = new dbKD731HistoDocumentsLignes(
						m_appState.m_db);
				hdl.clear(new StringBuilder());
			}
		}

		if (scenario.equals(scenario_facturesdues)) {
			dbKD730FacturesDues fd = new dbKD730FacturesDues(m_appState.m_db);
			fd.clear(new StringBuilder());
		}

		if (scenario.equals(scenario_habitudes)) {
			dbSiteHabitudes habitudes = new dbSiteHabitudes(m_appState.m_db);
			habitudes.clear(new StringBuilder());
		}
		if (scenario.equals(scenario_stocktheo)) {
			dbKD545StockTheoSrv fd = new dbKD545StockTheoSrv(m_appState.m_db);
			fd.clear(false,new StringBuilder());
		}

		if (scenario.equals(scenario_tournee)) {
			dbTournee to = new dbTournee(m_appState.m_db);
			to.Clear( new StringBuilder());
		}
		if (scenario.equals(scenario_clitovisit)) {
			dbCliToVisit tv = new dbCliToVisit(m_appState.m_db);
			tv.Clear(new StringBuilder()  );
		}
		if (scenario.equals(scenario_histointer)) {
			TableHistoInter hi = new TableHistoInter(m_appState.m_db);
			hi.clear(new StringBuilder());
		}
		if (scenario.equals(scenario_histointertournee)) {
			TableHistoInter hi = new TableHistoInter(m_appState.m_db);
			hi.deletedate_creat_encours();
		}
		infoItem(scenario, nbrLineTotal);
		ArrayList<String> timeIntegre = new ArrayList<String>();

		scenarioEncours = getString(R.string.sync_msgintegration) + scenario;


		//Global.dbParam.getDB().compact(new StringBuilder());

		runOnUiThread(changeMessage);

		boolean b=true;
		if (nbrLineTotal>0)
			b = integreWSDataArray(arrLines, STEP, timeIntegre, m_appState,scenario);

		return b;
	}

	public boolean integreWSDataArray(ArrayList<String[]> arr, int step,
			ArrayList<String> timeelapse, app myapp, String scenario) {

		if(arr==null || arr.get(0).length==0) 
			return true;
		Date interestingDate = new Date();

		StringBuilder err = new StringBuilder();
		myapp.m_db.execSQL("BEGIN "+scenario+";", err);

		//Cursor cur = myapp.m_db.conn.rawQuery("PRAGMA journal_mode=OFF;", null);
		//cur.close();

		for (int line = 0; line < arr.size(); line++) {
			/*if ( line%100 == 99 )
			{
				myapp.m_db.execSQL("COMMIT "+scenario+";", err);
				myapp.m_db.execSQL("BEGIN "+scenario+";", err);
				Cursor cur2 = myapp.m_db.conn.rawQuery("PRAGMA journal_mode=OFF;", null);
				cur2.close();

			}*/

			String[] extract = arr.get(line);

			String stInsert = extract[1];
			for (int i = 2; i < extract.length; i++) {
				String query = extract[i];
				myapp.m_db.execSQL(stInsert + query, err);
			}
		}

		myapp.m_db.execSQL("COMMIT "+scenario+";", err);

		long time = (new Date()).getTime() - interestingDate.getTime();
		timeelapse.add(String.valueOf(time));
		// FurtiveMessageBox("DonnÔøΩes intÔøΩgrÔøΩes");

		return true;
	}

	String getWSData(String _user, String _pwd, final boolean btournee) {

		try {
			new Thread() {

				@Override
				public void run() {
					nbrError=0;
					//compactData(true);
					Log.e("getWSData","getWSData");
					
					receive(false);
					if(btournee==false)
					{
						if (android.os.Build.VERSION.SDK_INT >= 14) {
							//bug 40.9, les agendas sont crees en synchroniser juste avant la re-ouverture de la base
							//Global.dbKDAgendaSrv.CreateAllEvents("FLS", SynchroActivity.this);		//creation des rdv serveur avant pour recup aprés dans 102
							//Global.dbKDAgenda.getAllEvents("FLT", SynchroActivity.this, true);		//delete avant recup

							//finalement, pas besoin de creer en 102 les rdv serveur
							//Global.dbKDAgenda.getAllEvents("FLS", m_appState, false);		//pas de delete, pour cumulé FLT et FLS
						}
					}

				}
			}.start();

			return "";

		} catch (Exception ex) {

		}
		return "";
	}
	
	String getWaitingNewVersion(String user)
	{
		dbParam dbVer=new dbParam(m_appState.m_db);
		int serverVer=Fonctions.convertToInt(dbVer.getLblAllSoc(dbVer.PARAM_MINVER,"VER",user));
		if (serverVer>0 && Fonctions.getVersion(this,new StringBuffer())!=serverVer)
		{
			String filename ="folliet_rel_"+serverVer+".apk";
			
			return filename;
		}
		return "";
	}
	//recup la maj de l'appli
		boolean downloadVersionMaj(String user)
		{
		//	Fonctions.getHHTTPFile("","toto.apk");
			String filename=getWaitingNewVersion(user);
		 
			if (filename.equals("")==false)
			{
			 
				if (Fonctions.IsFileExist(ExternalStorage.getFolderPath(ExternalStorage.DOWNLOAD_FOLDER),filename)==false)
				{
					Fonctions.DeleteFiles(ExternalStorage.getFolderPath(ExternalStorage.DOWNLOAD_FOLDER),filename);
					String pathDest=ExternalStorage.getFolderPath(ExternalStorage.DOWNLOAD_FOLDER)+filename;
					
					String url="http://sdmaj.danem.fr/zrserveurws/maj/folliet/" +filename;
					Log.d("TAG","start DL :"+url);
					
					int ret=Fonctions.getHHTTPFile(url,filename);
					if (ret==1) return true;
					else return false;
				}
				else
				{
					Log.d("TAG","MAJ  deja recue, en attente sur le storage");
					return true;
				}
			}
			return false;
		}
		
		boolean doMajProgram( )
		{
			String file=getWaitingNewVersion(login);
			String dir=ExternalStorage.getFolderPath(ExternalStorage.DOWNLOAD_FOLDER);
			if (Fonctions.IsFileExist(dir,file))
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(dir+file
						)),
						"application/vnd.android.package-archive");
				startActivity(intent);
				
				return true;
			}
			return false;
		}
		public  void compactData(boolean inThread) {
			StringBuilder err=new StringBuilder();
			if (inThread)
				m_appState.m_db.compact(err);
			else
			{
				//FurtiveMessageBox(getString(R.string.module_synchro_compactage));
				if (m_appState.m_db.compact(err) == false) {
					//FurtiveMessageBox(Global.lastErrorMessage);
				} else {
					//FurtiveMessageBox(getString(R.string.module_synchro_compactage_reussi));
				}
			}
		}

	public String returnNumber() {
		String number = "";
		String service = Context.TELEPHONY_SERVICE;
		TelephonyManager tel_manager = (TelephonyManager) getSystemService(service);
		//int device_type = tel_manager.getPhoneType();
		// get IMEI
		String imei = Fonctions.GetStringDanem(tel_manager.getNetworkOperatorName());

		number = imei;


		return number;
	}


    public String returnOperateur_Etat() {
        String number = "";
        String service = Context.TELEPHONY_SERVICE;
        TelephonyManager tel_manager = (TelephonyManager) getSystemService(service);
        //int device_type = tel_manager.getPhoneType();
        // get IMEI
        String imei = Fonctions.GetStringDanem(tel_manager.getNetworkOperatorName());
        // get The Phone Number
        //int phone = tel_manager.getDataActivity();
		if(!imei.equals(""))
		{
			int Etat = tel_manager.getDataState();
			if(Etat==0)
			{
				imei="disconnected";
			}
			if(Etat==1)
			{
				imei="connecting";
			}
			if(Etat==2)
			{
				imei="connected";
			}
			if(Etat==3)
			{
				imei="suspended";
			}
		}






        number = imei;


        return number;
    }


    public String returnWifi() {
        String number = "";

        String imei = "";

        {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWifi.isConnected()) {
                // Do whatever
                imei = "Wifi connected";
            }
            else
                imei = "Wifi disconnected";
        }

        number = imei;


        return number;
    }

	public String GetNumWSUnique(String identifiant)
	{
		//Code rep(5c) +hhmm+AAMMJJ
		String StValeur="";
		boolean existe=true;
		DateCode dt=new DateCode();
		int i=0;

		String stDayofYear ="";
		String stYear ="";
		String minutes ="";



		while(existe==true)
		{
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat Annee = new SimpleDateFormat("yyyy");
			SimpleDateFormat minute = new SimpleDateFormat("HHmm");

			stDayofYear =Fonctions.getDay_Of_Year();
			stYear=Annee.format(gc.getTime());
			minutes=minute.format(gc.getTime());
			StValeur=identifiant+dt.ToCode();

			existe=false;

		}

		return StValeur;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {

			//finish();
			/*if (downloadVersionMaj(login)) {
				doMajProgram();
			}
			finish();*/


			DownLoadMajTask majTask = new DownLoadMajTask();
			majTask.execute();

			return false;
		}

		else
			return super.onKeyDown(keyCode, event);

	}

    private class DownLoadMajTask extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("EN cours..."); // Calls onProgressUpdate()
            try {
                if (downloadVersionMaj(login)) {
                    doMajProgram();
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //finalResult.setText(result);
            //finish();
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(SynchroActivity.this,
                    "Nouvelle version de l'application","Téléchargement en cours...");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //finalResult.setText(text[0]);

        }
    }
}
