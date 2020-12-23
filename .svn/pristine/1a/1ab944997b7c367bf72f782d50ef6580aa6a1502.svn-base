package com.menadinteractive.agendanem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.folliet2016.AppBundle;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.dbKD102Agenda;
import com.menadinteractive.segafredo.db.dbKD105AgendaSrvSupp;
import com.menadinteractive.segafredo.db.dbKD106AgendaCorrespondance;
import com.menadinteractive.segafredo.db.dbParam;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.findcli.FindCliActivity;
import com.menadinteractive.segafredo.plugins.Espresso;

//tof, modif 61569495169, changement du mode d'affichage des rdv (recup et adaptation du mode findcliactivity)
public class AgendaListActivity extends BaseActivity implements View.OnClickListener {
	public static int REQUEST_CODE_SHOW_CALENDAR = 100;

	/** GUI */
	//ListView listview;61569495169
	myListView lv;
	Handler handler;

	ArrayList<Bundle> cli;

	Spinner spinner ;
	Spinner spinnerVille ;
	Spinner spinnerVendeur ;
	Spinner spinnerTournee ;

	TextView etFilter ;
	ImageButton ibFind ;

	/** Adapters */
	SimpleCursorAdapter eventsAdapter;
	ArrayList<Bundle> idTypeActivite = null;
	ArrayList<Bundle> idTypeVille = null;
	ArrayList<Bundle> idTypeVendeur = null;
	ArrayList<Bundle> idTypeTournee= null;


	//Pour detecter les changement de valeur des spinner
	String spinnerValueVendeur = "" ;
	String spinnerValueTournee = "" ;
	String spinnerValueVille = "" ;
	String spinnerValueActivite = "" ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        initActionBar();
        setContentView(R.layout.activity_agenda_list);
        initGUI();
        initListeners();

		String accesAutre=getAccesDirect();
		if (accesAutre.equals("")==false)
			insertCalendarEvent(AgendaViewActivity.REQUEST_CODE_INSERT_EVENT);

		idTypeActivite = new ArrayList<Bundle>();
		idTypeVille= new ArrayList<Bundle>();
		idTypeVendeur= new ArrayList<Bundle>();
		idTypeTournee= new ArrayList<Bundle>();

		fillTypeActivite("", "");
		fillVille("","");
		fillVendeur("","");
		fillTournee("","");

		refreshList();
	}

	String getAccesDirect()
	{
		try{
			return appBundle.getAccesDirect();
		}
		catch(Exception e)
		{
			return "";
		}
	}
	private void refreshList(){
		try
		{

			initListFromDB();
			//initCursorAdapter(getAllEvents(appBundle.getApplicationKey()));
			//Modif tof, pour prendre les 2 agenda FLT et FLS, qui correspondenet au local et serveur


			String activity = "";
			String activityVille = "";
			String activityVendeur = "";
			String activityTournee = "";

			if (spinner.getSelectedItem() != null)
			{
				activity = spinner.getSelectedItem().toString();
			}

			if (spinnerVille.getSelectedItem() != null)
			{
				activityVille = spinnerVille.getSelectedItem().toString();
			}

			if (spinnerVendeur.getSelectedItem() != null)
			{
				activityVendeur = spinnerVendeur.getSelectedItem().toString();
			}

			if (spinnerTournee.getSelectedItem() != null)
			{
				activityTournee = spinnerTournee.getSelectedItem().toString();
			}

			String filtre = etFilter.getText().toString();

			//initCursorAdapter(getAllEvents("FL%", filtre, activity,activityVille,activityVendeur,activityTournee));//61569495169
			cli = Global.dbClient.getClientsFiltersForRdv(this, "", etFilter.getText().toString().trim(), activity, activityVille, activityVendeur, activityTournee);
			if ( cli == null ) cli = new ArrayList<Bundle>(); 	//secu pour ne pas avoir de plantage
			ArrayList<assoc> assocs = new ArrayList<assoc>();

			assocs.add(new assoc(0, R.id.tvCode, TableClient.FIELD_CODE, "isbloque"));
			assocs.add(new assoc(0, R.id.tvNom, TableClient.FIELD_ENSEIGNE));
			String sttatut = Fonctions.GetStringDanem(TableClient.FIELD_STATUT);

			assocs.add(new assoc(0, R.id.tvEtat, sttatut));
			assocs.add(new assoc(0, R.id.tvRS, TableClient.FIELD_NOM));
			assocs.add(new assoc(0, R.id.tvCP, TableClient.FIELD_CP));
			assocs.add(new assoc(0, R.id.tvVille, TableClient.FIELD_VILLE));
			assocs.add(new assoc(1, R.id.iv1, TableClient.FIELD_ICON, TableClient.FIELD_COULEUR));
			assocs.add(new assoc(1, R.id.iv2, "ishisto"));
			assocs.add(new assoc(1, R.id.iv3, "isfacdues"));
			assocs.add(new assoc(1, R.id.iv4, "ismatos"));
			assocs.add(new assoc(1, R.id.iv5, "isgeocoded"));
			assocs.add(new assoc(0, R.id.tvNote1, TableClient.FIELD_CLI_SAV));
			//assocs.add(new assoc(0, R.id.tvAction, dbKD106AgendaCorrespondance.FIELD_DESCRIPTION));
			assocs.add(new assoc(0, R.id.tvAction, dbParam.FLD_PARAM_LBL));
			assocs.add(new assoc(0, R.id.tvCommercial, TableClient.FIELD_CODECOM));
			assocs.add(new assoc(0, R.id.tvRank, TableClient.FIELD_VP_VRPRANG));
			assocs.add(new assoc(0, R.id.tvTourPeriod, "TOURNEE_PERIOD"));

			lv.attachValues(R.layout.item_list_findrdv, cli, assocs, handler);
		}
		catch(Exception e)
		{
			Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();;
		}

	}
	
	private void initGUI(){

		//listview = (ListView) findViewById(R.id.listview);61569495169
		lv = (myListView) findViewById(R.id.listViewRdv);
		handler =getHandler();

		spinner = (Spinner) findViewById(R.id.spinnerTypeActivite);
		spinnerVille= (Spinner) findViewById(R.id.spinnerVille);
		spinnerVendeur= (Spinner) findViewById(R.id.spinnerVendeur);
		spinnerTournee= (Spinner) findViewById(R.id.spinnerTournee);

		etFilter = (TextView) findViewById(R.id.etFilter);
		ibFind = (ImageButton) findViewById(R.id.ibFind);

	}

	private void initListFromDB()
	{
		dbKD102Agenda agenda = new dbKD102Agenda(getDB());
	}

	private void initActionBar(){
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(appBundle.getApplicationName());
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		addMenu(menu, R.string.see_agenda, R.drawable.action_calendar_view);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case(R.string.see_agenda):
			AgendaViewActivity.launchCalendar(REQUEST_CODE_SHOW_CALENDAR,this);
		return true;

		case android.R.id.home:
			this.finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE_SHOW_CALENDAR || requestCode==AgendaViewActivity.REQUEST_CODE_INSERT_EVENT){
			refreshList();
		}
		if ( resultCode == LAUNCH_RAPPORTACTIVITE ) {
			if (data != null) {
				Bundle bundle = data.getExtras();
				int ideventforreturn = bundle.getInt("ideventforreturn");
				if (ideventforreturn > 0) {
					//A dé commenté .
					if ( getEventTitle(ideventforreturn).contains("FLS") )
					{
						//Toast.makeText(AgendaListActivity.this, R.string.error_rdv_serveur, Toast.LENGTH_LONG).show();
						dbKD105AgendaSrvSupp db105 = new dbKD105AgendaSrvSupp(getDB()) ;
						db105.SaveRdvSupp(getEventTitle(ideventforreturn), getEventDateStart(ideventforreturn,AgendaListActivity.this),AgendaListActivity.this) ;
					}
					AgendaViewActivity.deleteCalendarEvent(ideventforreturn, this);
					refreshList();
				}


			}
		}
	}

	//gestion des click differents avec myListView
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
					case R.id.listViewRdv:
					//case R.id.iv2:

						int  id=bGet.getInt("position");
						/*String codecli=cli.get(id).getString(Global.dbClient.FIELD_CODE);


						//Fonctions.FurtiveMessageBox(FindCliActivity.this, codecli);
						Fonctions.WriteProfileString(FindCliActivity.this, "lastcli", cli.get(id).getString(Global.dbClient.FIELD_NOM));
						launchMenuCli(codecli );*/
						try
						{
							String stId = cli.get(id).getString(dbKD106AgendaCorrespondance.FIELD_ID) ;
							id = (int)Fonctions.convertToLong(stId) ;
						}
						catch (Exception e )
						{

						}
						promptEventAction(id);


				}

			}
		};
		return h;
	}


	private void initListeners(){
		//listview.setOnItemClickListener(new OnItemClickListener() {61569495169
		/*lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Conversion position->id pour nouvelle affichage  61569495169
				try
				{
					String stId = cli.get(position).getString(dbKD106AgendaCorrespondance.FIELD_ID) ;
					id = Fonctions.convertToLong(stId) ;
				}
				catch (Exception e )
				{

				}
				promptEventAction(id);
			}
		});*/
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String activity = spinner.getSelectedItem().toString();
				if ( !activity.equals(spinnerValueActivite))
					refreshList();
				else
					spinnerValueActivite = activity;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
		spinnerVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String ville = spinnerVille.getSelectedItem().toString();
				if ( !ville.equals(spinnerValueVille))
					refreshList();
				else
					spinnerValueVille = ville ;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
					// your code here
			}

		});

		spinnerVendeur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String vendeur = spinnerVendeur.getSelectedItem().toString();
				if ( !vendeur.equals(spinnerValueVendeur))
					refreshList();
				else
					spinnerValueVendeur = vendeur ;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		spinnerTournee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String tournee = spinnerTournee.getSelectedItem().toString();
				if ( !tournee.equals(spinnerValueTournee))
					refreshList();
				else
					spinnerValueTournee = tournee ;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
		ibFind.setOnClickListener(this);

	}
	
	protected void insertCalendarEvent(int requestCode){
		long startMillis = System.currentTimeMillis();
		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setData(Events.CONTENT_URI);
		intent.putExtra(Events.TITLE, appBundle.getApplicationKey()+"[AUTRE] "+appBundle.getAccesDirect());
		intent.putExtra(Events.DTSTART, startMillis);
		intent.putExtra(Events.DTEND, startMillis + (60*60*1000));
		intent.putExtra(Events.EVENT_LOCATION,"");
		startActivityForResult(intent, requestCode);
	}

    //61569495169
	/*private void initCursorAdapter(Cursor cursor){

		String[] columns = new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};
		int[] to = new int[] {R.id.tv_title, R.id.tv_description, R.id.rl_top, R.id.tv_enddate};
		if(eventsAdapter != null){
			eventsAdapter.changeCursor(cursor);
			eventsAdapter.notifyDataSetChanged();
		}
		else{
			eventsAdapter = new SimpleCursorAdapter(this, R.layout.item_event, cursor, columns, to);

			setViewBinder(eventsAdapter);

			listview.setAdapter(eventsAdapter);
		}
		eventsAdapter.notifyDataSetChanged();
	}
	
	private void setViewBinder(SimpleCursorAdapter adapter){
		adapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {
				boolean result = false;
				if (aColumnIndex == aCursor.getColumnIndex( CalendarContract.Events.TITLE)) {
					String title = aCursor.getString(aColumnIndex);
					TextView textView = (TextView) aView;
					textView.setText(title);
					result = true;
				}
				else if (aColumnIndex == aCursor.getColumnIndex( CalendarContract.Events.DESCRIPTION)) {
					String description = aCursor.getString(aColumnIndex);
					TextView textView = (TextView) aView;
					textView.setText(description);
					result = true;
				}
				else if (aColumnIndex == aCursor.getColumnIndex( CalendarContract.Events.DTSTART)) {
					long start = aCursor.getLong(aColumnIndex);
					RelativeLayout rl = (RelativeLayout) aView;
					ImageView picto = (ImageView) rl.findViewById(R.id.iv_picto);
					TextView textView = (TextView) rl.findViewById(R.id.tv_startdate);
					
					long now = System.currentTimeMillis();
					if(start > now)
						picto.setBackgroundResource(R.drawable.picto_calendar_new);
					else
						picto.setBackgroundResource(R.drawable.picto_calendar_old);
					//Affichage date : http://javatechniques.com/blog/dateformat-and-simpledateformat-examples/
					
					textView.setText(getString(R.string.from)+" "+DateFormat.getDateTimeInstance().format(start));
					result = true;
				}
				else if (aColumnIndex == aCursor.getColumnIndex( CalendarContract.Events.DTEND)) {
					long end = aCursor.getLong(aColumnIndex);
					TextView textView = (TextView) aView;
					textView.setText("       "+getString(R.string.to)+"       "+DateFormat.getDateTimeInstance().format(end));
					result = true;
				}

				return result;

			}
		});
	}*/
	
	private void promptEventAction(final long eventID){

		Log.e("EventID","EventID"+eventID);
		//Modif 48941941894 tof du 2/7/19: suppression de la ligne delete
		final CharSequence[] items = {getString(R.string.select_client),getString(R.string.edit_event)/*, getString(R.string.delete_event)*/, getString(R.string.show_client_on_map), getString(R.string.naviguate_client)};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getEventTitle(eventID));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if(items[item].equals(getString(R.string.select_client))){
					Intent i = new Intent();
					Bundle b = new Bundle();
					String stcodeclient=AgendaViewActivity.getCodeClientFromEvent(eventID, AgendaListActivity.this);
					String evt_id = ""+ eventID;
					if(Fonctions.GetStringDanem(stcodeclient).equals(""))
					{
						if ( evt_id != null )
							if ( !evt_id.equals(""))
							{
								dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
								dbKD106AgendaCorrespondance.passePlat rr = db106.getRdv(evt_id);
								if (rr != null)
								{
									if(!Fonctions.GetStringDanem(rr.FIELD_CODECLI).equals(""))
									{
										stcodeclient = rr.FIELD_CODECLI;

									}

								}
							}
					}




					b.putString(AppBundle.CodeClient,stcodeclient );
					i.putExtras(b);
					setResult(RESULT_OK, i);
					finish();
				}
				else if(items[item].equals(getString(R.string.edit_event))){
					//AgendaViewActivity.editCalendarEvent(eventID,AgendaListActivity.this);
					AgendaViewActivity .newRapport(AgendaListActivity.this,(int) eventID);
				}
				else if(items[item].equals(getString(R.string.delete_event))){
					//modif tof 10/1/2018: blocage de la suppression des rdv serveur
					//modif tof 14/03/2018: en plus de la suppression du rdv de l'agenda, flagage du rdv serveur pour le maquer
					if ( getEventTitle(eventID).contains("FLS") )
					{
						//Toast.makeText(AgendaListActivity.this, R.string.error_rdv_serveur, Toast.LENGTH_LONG).show();
						dbKD105AgendaSrvSupp db105 = new dbKD105AgendaSrvSupp(getDB()) ;
						db105.SaveRdvSupp(getEventTitle(eventID), getEventDateStart(eventID,AgendaListActivity.this),AgendaListActivity.this) ;


					}
					AgendaViewActivity.deleteCalendarEvent(eventID, AgendaListActivity.this);
					refreshList();
				}
				else if(items[item].equals(getString(R.string.show_client_on_map))){

					String stAdresse=getAddresseClientFromEvent(eventID );
					String evt_id = ""+ eventID;
					if(Fonctions.GetStringDanem(stAdresse).equals(""))
					{
						if ( evt_id != null )
							if ( !evt_id.equals(""))
							{
								dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
								dbKD106AgendaCorrespondance.passePlat rr = db106.getRdv(evt_id);
								if (rr != null)
								{
									if(!Fonctions.GetStringDanem(rr.FIELD_CODECLI).equals(""))
									{
										stAdresse = Global.dbClient.getClient_Adresse(rr.FIELD_CODECLI);

									}

								}
							}
					}


					AgendaViewActivity.showMap( stAdresse,AgendaListActivity.this);
				}
				else if(items[item].equals(getString(R.string.naviguate_client))){

					String stAdresse=getAddresseClientFromEvent(eventID );
					String evt_id = ""+ eventID;
					if(Fonctions.GetStringDanem(stAdresse).equals(""))
					{
						if ( evt_id != null )
							if ( !evt_id.equals(""))
							{
								dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
								dbKD106AgendaCorrespondance.passePlat rr = db106.getRdv(evt_id);
								if (rr != null)
								{
									if(!Fonctions.GetStringDanem(rr.FIELD_CODECLI).equals(""))
									{
										stAdresse = Global.dbClient.getClient_Adresse(rr.FIELD_CODECLI);

									}

								}
							}
					}

					AgendaViewActivity.naviguate( stAdresse,AgendaListActivity.this);
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	

	/** Events */
	//61569495169
	/*protected Cursor getAllEvents(String applicationKey, String filtre, String activity,String filtreVille,String filtreVendeur, String filtreTournee){
		Cursor result = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events._ID,
				CalendarContract.Events.TITLE,
				CalendarContract.Events.DESCRIPTION,
				CalendarContract.Events.DTSTART,
				CalendarContract.Events.DTEND

		};
		String where = CalendarContract.Events.TITLE +" LIKE '%"+applicationKey+"[%'"
				+" AND "+Events.DELETED+" = '0'";
		if ( !filtre.equals("") )
			where += " AND "+CalendarContract.Events.TITLE +" LIKE '%"+filtre+"%'" ;
		if ( !filtreVille.equals("") )
			where += " AND "+CalendarContract.Events.TITLE +" LIKE '% Ville:"+filtreVille+"%'" ;
		if ( !filtreVendeur.equals("") )
			where += " AND "+CalendarContract.Events.TITLE +" LIKE '% Commercial:"+filtreVendeur+"%'" ;
		if ( !filtreTournee.equals("") )
			where += " AND "+CalendarContract.Events.TITLE +" LIKE '% Tournée:"+filtreTournee+"%'" ;
		if ( !activity.equals("") )
			where += " AND "+ Events.DESCRIPTION +" = '"+activity+"'" ;
		result = getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");




		return result;
	}*/

	public String getEventTitle(long eventID){
		String result = "";
		Cursor cursor = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events.TITLE

		};
		String where = CalendarContract.Events._ID +" = '"+eventID+"'"
				+" AND "+Events.DELETED+" = '0'";
		cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");
		if(cursor != null && cursor.moveToFirst()){
			result = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
			cursor.close();
		}
		return result;
	}
	public static String getEventDateStart(long eventID, Context ptThis){
		String result = "";
		Cursor cursor = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events.DTSTART

		};
		String where = CalendarContract.Events._ID +" = '"+eventID+"'"
				+" AND "+Events.DELETED+" = '0'";
		cursor = ptThis.getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");
		if(cursor != null && cursor.moveToFirst()){
			long start = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART) );
			Date dt = new Date();
			dt.setTime(start);
			result = DateFormat.getDateTimeInstance().format(start);
			//result = DateFormat.SimpleDateFormat("yyyyMMddHHmmss").format(start);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss",
					java.util.Locale.getDefault());
			result = format.format(dt);
			cursor.close();
		}
		return result;
	}


	public String getAddresseClientFromEvent(long eventID){
		String result = "";
		Cursor cursor = null;
		String[] PROJECTION=new String[] {
				Events.EVENT_LOCATION

		};
		String where = CalendarContract.Events._ID +" = '"+eventID+"'"
				+" AND "+Events.DELETED+" = '0'";
		cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");
		if(cursor != null && cursor.moveToFirst()){
			result = cursor.getString(cursor.getColumnIndex(Events.EVENT_LOCATION));
			cursor.close();
		}
		
		return result;
		
	}


	void fillTypeActiviteold(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbParam.getRecordActiviteRemove(Global.dbParam.PARAM_TYPEACTIVITE,sttypeAgent,selvalRemove,
					idTypeActivite, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeActivite.add(0,bundle);

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


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);


			}

		} catch (Exception ex) {

		}

	}
	void fillVilleold(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbClient.getRecordVilleRemove("",sttypeAgent,selvalRemove,
					idTypeVille, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeVille.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeVille.size()];
				for (int i = 0; i < idTypeVille.size(); i++) {

					items[i] = idTypeVille.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeVille.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerVille.setAdapter(adapter);

				spinnerVille.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}

	void fillVendeur(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbClient.getRecordVendeurForFilter("",sttypeAgent,selvalRemove,
					idTypeVendeur, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeVendeur.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeVendeur.size()];
				for (int i = 0; i < idTypeVendeur.size(); i++) {

					items[i] = idTypeVendeur.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeVendeur.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}
				}

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerVendeur.setAdapter(adapter);

				spinnerVendeur.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	void fillTournee2(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
            dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
            rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			if (Global.dbClient.getRecordTourneeRemove("",sttypeAgent,selvalRemove,
					idTypeTournee, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeTournee.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeTournee.size()];
				for (int i = 0; i < idTypeTournee.size(); i++) {

					items[i] = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}



				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerTournee.setAdapter(adapter);

				spinnerTournee.setSelection(pos);

			}
			idTypeTournee = Global.dbClient.getListTourneeForFilter(AgendaListActivity.this);

			if (idTypeTournee != null) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				idTypeTournee.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeTournee.size()];
				for (int i = 0; i < idTypeTournee.size(); i++) {

				    String idTournee = idTypeTournee.get(i).getString(
                            Global.dbClient.FIELD_TOURNEE);

					items[i] = idTournee;

					/*String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);*/


					if (selVal.equals(idTournee)) {
						pos = i;
					}

				}

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerTournee.setAdapter(adapter);

				spinnerTournee.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}

	void fillTournee(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			/*if (Global.dbClient.getRecordTourneeRemove("",sttypeAgent,selvalRemove,
					idTypeTournee, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeTournee.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeTournee.size()];
				for (int i = 0; i < idTypeTournee.size(); i++) {

					items[i] = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerTournee.setAdapter(adapter);

				spinnerTournee.setSelection(pos);

			}*/
			idTypeTournee = Global.dbClient.getListTourneeForFilter(AgendaListActivity.this);

			if (idTypeTournee != null) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(Global.dbClient.FIELD_TOURNEE, "");
				idTypeTournee.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeTournee.size()];
				for (int i = 0; i < idTypeTournee.size(); i++) {

					String idTournee = idTypeTournee.get(i).getString(
							Global.dbClient.FIELD_TOURNEE);

					items[i] = idTournee;

					/*String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					*/

					Log.e("idTournee","idTournee=>"+idTournee);

					if (selVal.equals(idTournee)) {
						pos = i;
					}

				}

				Log.e("items","items+"+items);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerTournee.setAdapter(adapter);

				spinnerTournee.setSelection(pos);

			}

		} catch (Exception ex) {
			Log.e("exception","ex"+ex.getLocalizedMessage());
		}

	}

	void fillVille(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			/*if (Global.dbClient.getRecordTourneeRemove("",sttypeAgent,selvalRemove,
					idTypeTournee, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeTournee.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeTournee.size()];
				for (int i = 0; i < idTypeTournee.size(); i++) {

					items[i] = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerTournee.setAdapter(adapter);

				spinnerTournee.setSelection(pos);

			}*/
			idTypeVille = Global.dbClient.getListVilleForFilter(AgendaListActivity.this);

			if (idTypeVille != null) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(Global.dbClient.FIELD_VILLE, "");
                idTypeVille.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeVille.size()];
				for (int i = 0; i < idTypeVille.size(); i++) {

					String idTournee = idTypeVille.get(i).getString(
							Global.dbClient.FIELD_VILLE);

					items[i] = idTournee;

					/*String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					*/

					Log.e("idTournee","idTournee=>"+idTournee);

					if (selVal.equals(idTournee)) {
						pos = i;
					}

				}

				Log.e("items","items+"+items);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerVille.setAdapter(adapter);

				spinnerVille.setSelection(pos);

			}

		} catch (Exception ex) {
			Log.e("exception","ex"+ex.getLocalizedMessage());
		}

	}

	void fillTypeActivite(String selVal,String selvalRemove) {
		try {
			dbSiteListeLogin.structlistLogin rep = null;
			dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
			rep=new dbSiteListeLogin.structlistLogin();

			login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
			String sttypeAgent=Fonctions.GetStringDanem(rep.TYPE);

			// selVal=Global.dbClient.getCodeTypeEtablissement(selVal);

			Log.e("selVal","selVal"+selVal);

			/*if (Global.dbClient.getRecordTourneeRemove("",sttypeAgent,selvalRemove,
					idTypeTournee, true) == true) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(dbParam.FLD_PARAM_CODEREC, "");
				bundle.putString(dbParam.FLD_PARAM_LBL, "");
				bundle.putString(dbParam.FLD_PARAM_COMMENT, "");
				bundle.putString(dbParam.FLD_PARAM_VALUE, "");
				idTypeTournee.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeTournee.size()];
				for (int i = 0; i < idTypeTournee.size(); i++) {

					items[i] = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeTournee.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					Log.e("coderec","coderec=>"+codeRec);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}


				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerTournee.setAdapter(adapter);

				spinnerTournee.setSelection(pos);

			}*/

			idTypeActivite = Global.dbClient.getListTypeForFilter(AgendaListActivity.this);

			if (idTypeActivite != null) {
				//Ajout d'un item blanc en debut de liste
				Bundle bundle = new Bundle();
				bundle.putString(Global.dbClient.FIELD_TYPE, "");
                idTypeActivite.add(0,bundle);

				int pos = 0;
				String[] items = new String[idTypeActivite.size()];
				for (int i = 0; i < idTypeActivite.size(); i++) {

					String idTournee = Fonctions.GetStringDanem(idTypeActivite.get(i).getString(
							Global.dbClient.FIELD_TYPE));
					//Fonctions.FurtiveMessageBox(this,"type: "+idTournee);
					//idTournee=null;

					items[i] = idTournee;





					Log.e("idTournee","idTournee=>"+idTournee);

					if (selVal.equals(idTournee)) {
						pos = i;
					}

				}

				Log.e("items","items+"+items);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {
			Log.e("exception","ex"+ex.getLocalizedMessage());
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==ibFind)
		{
			refreshList();

		}

	}



}
