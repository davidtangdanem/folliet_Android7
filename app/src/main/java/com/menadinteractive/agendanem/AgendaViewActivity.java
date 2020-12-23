package com.menadinteractive.agendanem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.dbKD105AgendaSrvSupp;
import com.menadinteractive.segafredo.db.dbKD106AgendaCorrespondance;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.dbParam;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSociete;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.rapportactivite.rapportactivite;

public class AgendaViewActivity extends BaseActivity implements View.OnClickListener{
	public static int REQUEST_CODE_SHOW_CALENDAR = 100;
	public static int REQUEST_CODE_INSERT_EVENT = 101;

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
		setContentView(R.layout.activity_agenda_view);
		initGUI();
		idTypeActivite = new ArrayList<Bundle>();
		idTypeVille= new ArrayList<Bundle>();
		idTypeVendeur= new ArrayList<Bundle>();
		idTypeTournee= new ArrayList<Bundle>();
		fillTypeActivite("","");
		fillVille("","");
		fillVendeur("","");
		fillTournee("","");
		refreshList();
	}
	
	private void refreshList(){
		//initCursorAdapter(getClientEvents(appBundle.getApplicationKey(), appBundle.getCodeClient()));
		//Modif tof, pour prendre les 2 agenda FLT et FLS, qui correspondenet au local et serveur
		String activity = spinner.getSelectedItem().toString();
		String activityVille = spinnerVille.getSelectedItem().toString();
		String activityVendeur = spinnerVendeur.getSelectedItem().toString();
		String activityTournee = spinnerTournee.getSelectedItem().toString();

		String filtre = etFilter.getText().toString() ;
		//initCursorAdapter(getClientEvents("FL%", appBundle.getCodeClient(),filtre,activity,activityVille,activityVendeur,activityTournee));61569495169
		cli=Global.dbClient.getClientsFiltersForRdv( this, appBundle.getCodeClient(), etFilter.getText().toString().trim(),activity,activityVille,activityVendeur,activityTournee);
		ArrayList<assoc> assocs =new ArrayList<assoc>();

		assocs.add(new  assoc(0,R.id.tvCode, TableClient.FIELD_CODE,"isbloque"));
		assocs.add(new  assoc(0,R.id.tvNom,TableClient.FIELD_ENSEIGNE));
		String sttatut=  Fonctions.GetStringDanem(TableClient.FIELD_STATUT);

		assocs.add(new  assoc(0,R.id.tvEtat,sttatut));
		assocs.add(new  assoc(0,R.id.tvRS,TableClient.FIELD_NOM));
		assocs.add(new  assoc(0,R.id.tvCP,TableClient.FIELD_CP));
		assocs.add(new  assoc(0,R.id.tvVille,TableClient.FIELD_VILLE));
		assocs.add(new  assoc(1,R.id.iv1,TableClient.FIELD_ICON, TableClient.FIELD_COULEUR));
		assocs.add(new  assoc(1,R.id.iv2,"ishisto"));
		assocs.add(new  assoc(1,R.id.iv3,"isfacdues"));
		assocs.add(new  assoc(1,R.id.iv4,"ismatos"));
		assocs.add(new  assoc(1,R.id.iv5,"isgeocoded"));
		assocs.add(new  assoc(0,R.id.tvNote1,TableClient.FIELD_CLI_SAV));
		assocs.add(new  assoc(0,R.id.tvAction,dbKD106AgendaCorrespondance.FIELD_DESCRIPTION));
		assocs.add(new  assoc(0,R.id.tvCommercial,TableClient.FIELD_CODECOM));
		assocs.add(new  assoc(0,R.id.tvRank,TableClient.FIELD_VP_VRPRANG));
		assocs.add(new  assoc(0,R.id.tvTourPeriod,"TOURNEE_PERIOD"));


		lv.attachValues(R.layout.item_list_findrdv, cli,assocs,handler);


		initListeners();
		
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
	


	private void initActionBar(){
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(appBundle.getApplicationName()+" - "+appBundle.getNameClient());
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		addMenu(menu, R.string.see_agenda, R.drawable.action_calendar_view);
		addMenu(menu, R.string.take_appointment, R.drawable.action_calendar_add);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case(R.string.see_agenda):
			launchCalendar(REQUEST_CODE_SHOW_CALENDAR,AgendaViewActivity.this);
		return true;
		case(R.string.take_appointment):    
			insertCalendarEvent(REQUEST_CODE_INSERT_EVENT);
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
//		if(requestCode == REQUEST_CODE_SHOW_CALENDAR){
//			refreshList();
//		}
//		else if(requestCode == REQUEST_CODE_INSERT_EVENT){
//			refreshList();
//		}

		if ( resultCode == LAUNCH_RAPPORTACTIVITE ) {
			if (data != null) {
				Bundle bundle = data.getExtras();
				int ideventforreturn = bundle.getInt("ideventforreturn");
				if (ideventforreturn > 0) {
					deleteCalendarEvent(ideventforreturn, this);
					refreshList();

				}


			}
		}
		refreshList();
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
		//Modif 48941941894 tof du 2/7/19: suppression de la ligne delete
		final CharSequence[] items = {getString(R.string.edit_event)/*, getString(R.string.delete_event)*/, getString(R.string.show_client_on_map), getString(R.string.naviguate_client)};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(appBundle.getNameClient());
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if(items[item].equals(getString(R.string.edit_event))){
					//editCalendarEvent(eventID,AgendaViewActivity.this);
					newRapport(AgendaViewActivity.this, (int)eventID);

				}
				else if(items[item].equals(getString(R.string.delete_event))){
					//modif tof 10/1/2018: blocage de la suppression des rdv serveur
					//modif tof 14/03/2018: en plus de la suppression du rdv de l'agenda, flagage du rdv serveur pour le maquer
					if ( getEventTitle(eventID).contains("FLS") )
					{
						//Toast.makeText(AgendaViewActivity.this, R.string.error_rdv_serveur, Toast.LENGTH_LONG).show();
						dbKD105AgendaSrvSupp db105 = new dbKD105AgendaSrvSupp(getDB()) ;
						db105.SaveRdvSupp(getEventTitle(eventID), AgendaListActivity.getEventDateStart(eventID,AgendaViewActivity.this),AgendaViewActivity.this) ;

					}

					deleteCalendarEvent(eventID, AgendaViewActivity.this);
					refreshList();
				}
				else if(items[item].equals(getString(R.string.show_client_on_map))){
					showMap(appBundle.getAdresseClient(),AgendaViewActivity.this);
				}
				else if(items[item].equals(getString(R.string.naviguate_client))){
					naviguate(appBundle.getAdresseClient(),AgendaViewActivity.this);
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/** Events */
	//61569495169
	/*protected Cursor getClientEvents(String applicationKey, String codeClient,String filtre,String activity,String filtreVille,String filtreVendeur, String filtreTournee){
		Cursor result = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events._ID,
				CalendarContract.Events.TITLE,
				CalendarContract.Events.DESCRIPTION,
				CalendarContract.Events.DTSTART,
				CalendarContract.Events.DTEND

		};
		String where = CalendarContract.Events.TITLE +" LIKE '%"+applicationKey+"["+codeClient+"]%'"
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


		//		if(result != null && result.moveToFirst()){
		//			while(result.isAfterLast() == false)
		//			{
		//				Debug.Log(result.getString(result.getColumnIndex(CalendarContract.Events.TITLE)));
		//
		//				result.moveToNext();
		//			}
		//			result.close();
		//
		//		}

		return result;
	}*/
	
	static public   void launchCalendar(int requestCode,Activity c){
		//    	Intent intent = new Intent(Intent.ACTION_VIEW);
		//    	intent.setData(Calendars.CONTENT_URI);
		//    	startActivity(intent);
		//    	
		// http://developer.android.com/guide/topics/providers/calendar-provider.html#intent-view
		long startMillis = System.currentTimeMillis();
		Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
		builder.appendPath("time");
		ContentUris.appendId(builder, startMillis);
		Intent intent = new Intent(Intent.ACTION_VIEW)
		.setData(builder.build());
		c.startActivityForResult(intent,requestCode);
	}

	protected static void viewCalendarEvent(long eventID,Activity a){
		//http://developer.android.com/guide/topics/providers/calendar-provider.html#intents
		//http://developer.android.com/guide/topics/providers/calendar-provider.html#intent-view

		Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
		a.startActivity(intent);
	}

	protected static void editCalendarEvent(long eventID,Activity a){
		//http://developer.android.com/guide/topics/providers/calendar-provider.html#intents
		//http://developer.android.com/guide/topics/providers/calendar-provider.html#intent-view

		Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
		a.startActivity(intent);
	}

	static public void newRapport(Activity c,int id) {

		Intent i = new Intent(c, rapportactivite.class);
		//Intent i = new Intent(c, rapportActivity.class);
		String codecli =getCodeClientFromEvent(id,c) ;
		String datedeb ="";
		String datefin ="";
		String typeAct ="" ;


		//modif tof, on ne stocke plus les données dans l'agenda google
		/*String result = "";
		Cursor cursor = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events.TITLE,
				CalendarContract.Events.DESCRIPTION,
				CalendarContract.Events.DTSTART,
				CalendarContract.Events.DTEND

		};
		String where = CalendarContract.Events._ID +" = '"+id+"'"
				+" AND "+Events.DELETED+" = '0'";
		cursor = c.getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");
		if(cursor != null && cursor.moveToFirst()){
			codecli = cursor.getString(cursor.getColumnIndex(Events.TITLE));
			if(!codecli.equals("")){
				int start = codecli.indexOf("[");
				int end = codecli.indexOf("]");
				if(start != -1 && end != -1)
					codecli = codecli.substring(start+1, end);
			}

			datedeb = cursor.getString(cursor.getColumnIndex(Events.DTSTART));
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
			datedeb = dateFormat.format(Fonctions.convertToLong(datedeb));
			datedeb = datedeb.replace(" ", "" ) ;

			datefin = cursor.getString(cursor.getColumnIndex(Events.DTEND));
			datefin = dateFormat.format(Fonctions.convertToLong(datefin));
			datefin = datefin.replace(" ", "" ) ;

			typeAct = cursor.getString(cursor.getColumnIndex(Events.DESCRIPTION));
			cursor.close();
		}*/


		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);
		b.putString("datedeb",  datedeb);
		b.putString("datefin",  datefin);
		b.putString("typeAct",  typeAct); //"cgc"); debug
		b.putInt("ideventforreturn",  id); //"cgc"); debug
		b.putInt("evt_id",  id);

		i.putExtras(b);



		c.startActivityForResult(i, LAUNCH_RAPPORTACTIVITE);

	}

	protected static void deleteCalendarEvent(long eventID,Activity a){
		//http://developer.android.com/guide/topics/providers/calendar-provider.html#delete-event
		//Suppression de l'agenda
		Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		int rows = a.getContentResolver().delete(uri, null, null);

		//Suppression ou masquage dans la table 106
		dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
		db106.delete(eventID+"") ;





	}
	
	protected void insertCalendarEvent(int requestCode){
		long startMillis = System.currentTimeMillis();
		//Ajout tof 19/03/18: ajout de l'enseigne, de la ville et du CP dans le titre du rdv
		TableClient.structClient cli=new TableClient.structClient();
		cli=Global.dbClient.load(appBundle.getCodeClient());

		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setData(Events.CONTENT_URI);
		String stTitle = appBundle.getApplicationKey()+"["+appBundle.getCodeClient()+"] "+appBundle.getNameClient() ;
		if ( !cli.ENSEIGNE.trim().equals(""))
			stTitle+= " - " +cli.ENSEIGNE.trim() ;
		if ( !cli.VILLE.trim().equals(""))
			stTitle+= " - Ville:" +cli.VILLE.trim() ;
		if ( !cli.CODECOM.trim().equals(""))
			stTitle+= " - Commercial:" +cli.CODECOM.trim() ;
		if ( !cli.TOURNEE.trim().equals(""))
			stTitle+= " - Tournée:" +cli.TOURNEE.trim() ;

		intent.putExtra(Events.TITLE, stTitle );
		intent.putExtra(Events.DTSTART, startMillis);
		intent.putExtra(Events.DTEND, startMillis + (60*60*1000));
		intent.putExtra(Events.EVENT_LOCATION, appBundle.getAdresseClient());
		startActivityForResult(intent, requestCode);
	}

	protected void insertCalendarEventByBd(int requestCode){
		long startMillis = System.currentTimeMillis();
		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setData(Events.CONTENT_URI);
		intent.putExtra(Events.TITLE, appBundle.getApplicationKey()+"["+appBundle.getCodeClient()+"] "+appBundle.getNameClient());
		intent.putExtra(Events.DTSTART, startMillis);
		intent.putExtra(Events.DTEND, startMillis + (60*60*1000));
		intent.putExtra(Events.EVENT_LOCATION, appBundle.getAdresseClient());
		startActivityForResult(intent, requestCode);
	}

	protected static void showMap(String address,Activity a){
		Intent intent1 = new Intent(Intent.ACTION_VIEW,
				Uri.parse("geo:0,0?q=" + address));
		a.startActivity(intent1);
	}

	protected static  void naviguate(String address,Activity a){
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=" + address));
		a.startActivity(intent);
	}
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
	public static String getCodeClientFromEvent(long eventID, Activity c){
		String result = "";
		Cursor cursor = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events.TITLE

		};
		String where = CalendarContract.Events._ID +" = '"+eventID+"'"
				+" AND "+Events.DELETED+" = '0'";
		cursor = c.getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");
		if(cursor != null && cursor.moveToFirst()){
			result = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
			cursor.close();
		}

		if(!result.equals("")){
			int start = result.indexOf("[");
			int end = result.indexOf("]");
			if(start != -1 && end != -1)
				result = result.substring(start+1, end);
		}

		return result;

	}

	/*public String getEventDateStart(long eventID){
		String result = "";
		Cursor cursor = null;
		String[] PROJECTION=new String[] {
				CalendarContract.Events.DTSTART

		};
		String where = CalendarContract.Events._ID +" = '"+eventID+"'"
				+" AND "+Events.DELETED+" = '0'";
		cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, PROJECTION, where, null, CalendarContract.Events.DTSTART+" DESC");
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
	}*/


	void fillTypeActivite(String selVal,String selvalRemove) {
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
	void fillVille(String selVal,String selvalRemove) {
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

			if (Global.dbClient.getRecordVendeurRemove("",sttypeAgent,selvalRemove,
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
	void fillTournee(String selVal,String selvalRemove) {
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

		} catch (Exception ex) {

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
