package com.menadinteractive.segafredo.carto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.MyLocationOverlay;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.Debug;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.folliet2016.app;
import com.menadinteractive.segafredo.adapter.Log;
import com.menadinteractive.segafredo.adapter.LogAdapter;
import com.menadinteractive.segafredo.adapter.SpinAdapter;
import com.menadinteractive.segafredo.adapter.ToVisitAdapter;
import com.menadinteractive.segafredo.adapter.TourneeAdapter;
import com.menadinteractive.segafredo.client.ficheclient;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.SQLRequestHelper;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableHistoInter;
import com.menadinteractive.segafredo.db.dbCliToVisit;
import com.menadinteractive.segafredo.db.dbCliToVisit.structSoc;
import com.menadinteractive.segafredo.db.dbKD;
import com.menadinteractive.segafredo.db.dbKD106AgendaCorrespondance;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbKD100Visite.structVisite;
import com.menadinteractive.segafredo.db.dbKD101ClientVu.passePlat;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.db.dbTournee;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.synchro.GpsMonitorService;
import com.menadinteractive.segafredo.synchro.SynchroService;
import com.menadinteractive.segafredo.tasks.GeocodeTask;
import com.menadinteractive.segafredo.tasks.task_getTourneeWSData;

public class CartoMapActivity extends BaseActivity implements
OnItemClickListener, OnItemLongClickListener, OnItemSelectedListener {

	// public static final int ACCEPTABLE_ZOOM_LEVEL = 14;
	public static final int ACCEPTABLE_ZOOM_LEVEL = 16;

	/** ActionBar */
	MenuItem itemCenterCarto;
	MenuItem itemDemo;

	String mapKey;
	/** GUI */
	CartoMapView mapView;
	LinearLayout rl_logger_bottom;

	RelativeLayout parent;
	ListView lv_proximite ; // à proximité
	ListView lv_toVisit ; // à visiter
	ListView lv_tournee ; // Tournée
	LinearLayout ll_toVisit,ll_proximite;


	MenuPopup menuPopup;
	ImageView iv_demo;
	Spinner s_1, sAVisiter ;
	AutoCompleteTextView actvFilter;
	ImageButton ibFilter;
	ImageButton ibRefreshPOI;
	boolean m_btech=false;
	boolean m_bvendeur=false;

	String m_streplogin="";

	/** Handler */
	Handler handler;
	ExternalStorage externalStorage;
	Marker markerController;
	OverlayTask overlayTask = null;

	FindNearestPOITask findNearestPOITask = null; // affiche les clients les plus proches à gauche (si pref=1)
	FindTourneeTask findTourneeTask = null;//affiche les clients de la tournée à droite
	FindToVisitTask findToVisitTask=null;	//affiche les clients à visiter dans la liste de gauche
	GeocodeTask geocodeTask = null;  //affiche tous les clients sur la carte

	/** Map */
	MapController mc;
	ClientOverlay surroundingClients;
	MyLocationOverlay myLocOverlay;
	ClientPopup popup;
	BitmapDrawable b1;
	BitmapDrawable b2;
	boolean centerMapToSelf = false;
	boolean isFirstFixDone = false;
	boolean isDemo = false;
	GeoPoint longpressLocation = null;
	String clientCodeFromNegos = null;
	structClient clientFromNegos = null;
	// GeoPoint clientGeoPointFromNegos = null;
	boolean m_proximiteInsteadOfToVisit=false;

	/** Adapter */
	ArrayList<Log> logData;
	LogAdapter adapter;

	ArrayList<Log> tourneeData;
	TourneeAdapter tourneeAdapter;

	TextView tv_cptToVisit, tv_cptTournee;

	ArrayList<Log> toVisitData;
	ToVisitAdapter toVisitAdapter;


	ArrayList<String> zones;
	ArrayList<String> zonesvisiter;
	
	SpinAdapter spinAdapter;
	int zoneSpinnerPosition = 0;
	int zoneSpinnerPositionAVisiter = 0;
	
	public CartoMapActivity(){
		
	}

	/** Handler */
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
				case MESSAGE_SHOW_POPUP:
					if (Global.clientSelectedOnMap != null)
						showPopup(Global.clientSelectedOnMap.getPoint(),
								Global.clientSelectedOnMap.getClient());
					break;
				case MESSAGE_CLIENT_SELECTED_ON_MAP:
					hidePopup();
					if (Global.clientSelectedOnMap != null)
						showPopup(Global.clientSelectedOnMap.getPoint(),
								Global.clientSelectedOnMap.getClient());

					break;
				case MESSAGE_ACTION_BAR_PROGRESS_BAR:
					UI_updateProgressBar();
					break;
				case MESSAGE_PUSH_GPS_COORDINATE://MV 17/10
					GpsMonitorService.triggerService(CartoMapActivity.this);
					Debug.Log("PUSH GPS COORDINATE");
					try {
						setGPSSynchro(handler, MESSAGE_PUSH_GPS_COORDINATE);
					} catch (Exception e) {
						Debug.StackTrace(e);
					}
					break;

				case MESSAGE_UPDATE_PI:
					if (overlayTask == null
					&& SynchroService.isServiceRunning() == false) {
						//if ((isDemo && msg.arg1 == MESSAGE_UPDATE_PI_ARG1_FORCE) || (!isDemo))// mv 03/11 
						{

							boolean isToVisit = false;
							if (mapView.getZoomLevel() < ACCEPTABLE_ZOOM_LEVEL)
								isToVisit = true;

							checkSimulationMarker();

							overlayTask = new OverlayTask();
							overlayTask.execute(isToVisit);

							/*
							 * if(mapView.getZoomLevel()<ACCEPTABLE_ZOOM_LEVEL)
							 * mapView
							 * .getController().setZoom(ACCEPTABLE_ZOOM_LEVEL);
							 * checkSimulationMarker(); boolean isToVisit =
							 * true; overlayTask = new OverlayTask();
							 * overlayTask.execute(isToVisit);
							 */

						}
					}
					try {
						setUISynchro(handler, MESSAGE_UPDATE_PI);
					} catch (Exception e) {
						Debug.StackTrace(e);
					}
					break;
				case MESSAGE_UPDATE_LIST_TOURNEE:
					//mv test	SynchroService.closeall=true;
					Debug.Log("TAG3", "findTourneeTask : " + findTourneeTask);
					if (findTourneeTask == null
							&& SynchroService.isServiceRunning() == false) {
						findTourneeTask = new FindTourneeTask();
					findTourneeTask.execute();
				}

					Debug.Log("TAG3", "finToVisitTask : " + findToVisitTask);
					if (findToVisitTask == null
							&& SynchroService.isServiceRunning() == false) {
						findToVisitTask = new FindToVisitTask();
						findToVisitTask.execute();
					}


					break;
				case MESSAGE_FIND_NEAREST_POI:
					if (findNearestPOITask == null
					&& SynchroService.isServiceRunning() == false) {
						findNearestPOITask = new FindNearestPOITask();
						findNearestPOITask.execute();
					}
					try {
						setFindNearestPOI(handler, MESSAGE_FIND_NEAREST_POI);
					} catch (Exception e) {
						Debug.StackTrace(e);
					}
					break;
				case MESSAGE_SHOW_MENU_POPUP:
					if (bGet != null) {
						Debug.Log("requete", bGet.toString());
						String code = bGet.getString(TableClient.FIELD_CODE);
						structClient sc = new structClient();
						if(msg.obj != null){
							sc = (structClient) msg.obj;
						}
						//structClient sc = Global.dbClient.load(code);
						showMenuPopup(sc);
					}
					break;
				case MESSAGE_HIDE_MENU_POPUP:
					hideMenuPopup();
					break;
				case MESSAGE_SELECT_CLIENT_BACK_NEGOS:
					Intent i = new Intent();
					Bundle b = new Bundle();
					b.putString(Espresso.CodeClient,
							msg.getData().getString(Espresso.CodeClient));
					i.putExtras(b);
					setResult(RESULT_OK, i);
					finish();
					break;

				case MESSAGE_PROSPECT_BACK_NEGOS:

					Intent i2 = new Intent(); Bundle b2 = new Bundle();
					b2.putString(Espresso.CodeClient, "NEW");
					i2.putExtras(b2); setResult(RESULT_OK, i2);  

					launchProspect();
					break;

				case MESSAGE_CLEAR_TASK_GEOCODE:
					geocodeTask = null;
					break;

				case MESSAGE_CREATE_TASK_GEOCODE:
					if (geocodeTask == null
					&& SynchroService.isServiceRunning() == false) {
						geocodeTask = new GeocodeTask(CartoMapActivity.this,
								handler, false);
						geocodeTask.execute();
					}
					break;
				case MESSAGE_ANIMATE_TO:
					String codeClient = msg.getData().getString(
							TableClient.FIELD_CODE);
					try {
						structClient sClient = Global.dbClient.load(codeClient);

						Double latitude = Double.parseDouble(sClient.LAT) * 1E6;
						Double longitude = Double.parseDouble(sClient.LON) * 1E6;
						int latitude1 = latitude.intValue();
						int longitude1 = longitude.intValue();

						mapView.getController().animateTo(
								new GeoPoint(Integer.valueOf(latitude1),
										Integer.valueOf(longitude1)));
					} catch (Exception e) {

					}
					Message m = new Message();
					m.what = MESSAGE_UPDATE_PI;
					m.arg1 = MESSAGE_UPDATE_PI_ARG1_FORCE;

					handler.sendMessageDelayed(m, 2000);
					handler.sendEmptyMessageDelayed(
							CartoMapActivity.MESSAGE_FIND_NEAREST_POI, 2500);
					break;
				}

			}
		};
		return h;
	}

	void launchProspect() {
		String curzone = "";
		Intent i = new Intent(this, ficheclient.class);
		Bundle b = new Bundle();
		b.putString("numProspect", "");
		b.putString("prendrecde", "true");
		b.putString("curzone", curzone);
		i.putExtras(b);

		startActivityForResult(i, LAUNCH_FICHECLI);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Debug.Log("Orientation changed");
		fillGUI();
	}

	private void setAlarmsAndPreferences() {
		if (Preferences.isFirstLaunch(this)) {
			Debug.Log("First LAUNCH");
			Preferences.setValue(this, Espresso.LOGIN, appBundle.getBundle()
					.getString(Espresso.LOGIN));
			Preferences.setValue(this, Espresso.CODE_SOCIETE, appBundle
					.getBundle().getString(Espresso.CODE_SOCIETE));
			Preferences.setValue(this, Espresso.PASSWORD, appBundle.getBundle()
					.getString(Espresso.PASSWORD));
			Preferences.setValue(this, Espresso.WS_URL, appBundle.getBundle()
					.getString(Espresso.WS_URL));
			Preferences.setValue(
					this,
					Espresso.TIMER_MONITORING_GPS_SECONDS,
					appBundle.getBundle().getString(
							Espresso.TIMER_MONITORING_GPS_SECONDS));
			Preferences.setValue(
					this,
					Espresso.TIMER_SYNCHRO_SECONDS,
					appBundle.getBundle().getString(
							Espresso.TIMER_SYNCHRO_SECONDS));
			Preferences.setValue(
					this,
					Espresso.TIMER_UPDATE_PI_SECONDS,
					appBundle.getBundle().getString(
							Espresso.TIMER_UPDATE_PI_SECONDS));
			Preferences
			.setValueBoolean(this, Espresso.CENTER_MAP_TO_SELF, true);
			Preferences.setValueLong(this, Preferences.KEY_DATE_TOURNEE,
					System.currentTimeMillis());

			//SynchroService.triggerService(this, true, false);
			setAlarmSynchro(Long.valueOf(Preferences.getValue(this,
					Espresso.TIMER_SYNCHRO_SECONDS, "0")) * 1000);
			Preferences.setFirstLaunch(this, false);

			/*	clientCodeFromNegos = appBundle.getBundle().getString(
					Espresso.CODE_CLIENT);
			Preferences
			.setValueBoolean(this, Espresso.CENTER_MAP_TO_SELF, false);
			 */
		} else {
			Debug.Log("SECOND LAUNCH");

			// Comparer s'il y a du changement dans les valeurs des
			// préférences et prendre des actions en fonction !
			String login = appBundle.getBundle().getString(Espresso.LOGIN);
			String password = appBundle.getBundle()
					.getString(Espresso.PASSWORD);
			String wsurl = appBundle.getBundle().getString(Espresso.WS_URL);
			String timerGPS = appBundle.getBundle().getString(
					Espresso.TIMER_MONITORING_GPS_SECONDS);
			String timerSynchro = appBundle.getBundle().getString(
					Espresso.TIMER_SYNCHRO_SECONDS);
			String timerUI = appBundle.getBundle().getString(
					Espresso.TIMER_UPDATE_PI_SECONDS);
			String codeSociete = appBundle.getBundle().getString(
					Espresso.CODE_SOCIETE);

			Debug.Log("TIMER SYNCHRO : " + timerSynchro);

			if (!Fonctions.isEmptyOrNull(login)
					&& !login.equals(Preferences.getValue(this, Espresso.LOGIN,
							"0"))) {
				Preferences.setValue(this, Espresso.LOGIN, login);
			}
			if (codeSociete != null
					&& !codeSociete.equals(Preferences.getValue(this,
							Espresso.CODE_SOCIETE, ""))) {
				Preferences.setValue(this, Espresso.CODE_SOCIETE, codeSociete);
			}
			if (!Fonctions.isEmptyOrNull(password)
					&& !password.equals(Preferences.getValue(this,
							Espresso.PASSWORD, "0"))) {
				Preferences.setValue(this, Espresso.PASSWORD, password);
			}
			if (!Fonctions.isEmptyOrNull(wsurl)
					&& !wsurl.equals(Preferences.getValue(this,
							Espresso.WS_URL, "0"))) {
				Preferences.setValue(this, Espresso.WS_URL, wsurl);
			}
			if (!Fonctions.isEmptyOrNull(timerSynchro)
					&& !timerSynchro.equals(Preferences.getValue(this,
							Espresso.TIMER_SYNCHRO_SECONDS, "0"))) {
				Debug.Log("sync interval changed !! " + timerSynchro);
				Preferences.setValue(this, Espresso.TIMER_SYNCHRO_SECONDS,
						timerSynchro);
				setAlarmSynchro(Long.valueOf(Preferences.getValue(this,
						Espresso.TIMER_SYNCHRO_SECONDS, "0")) * 1000);
			}

			if (!Fonctions.isEmptyOrNull(timerGPS)
					&& !timerGPS.equals(Preferences.getValue(this,
							Espresso.TIMER_MONITORING_GPS_SECONDS, "0"))) {
				Preferences.setValue(this,
						Espresso.TIMER_MONITORING_GPS_SECONDS, timerGPS);
				cancelGPSSynchro(handler, MESSAGE_PUSH_GPS_COORDINATE);
			}

			if (!Fonctions.isEmptyOrNull(timerUI)
					&& !timerUI.equals(Preferences.getValue(this,
							Espresso.TIMER_UPDATE_PI_SECONDS, "0"))) {
				Preferences.setValue(this, Espresso.TIMER_UPDATE_PI_SECONDS,
						timerUI);
				cancelUISynchro(handler, MESSAGE_UPDATE_PI);
			}
		}



		// Global.AXE_Ident.IDENT = Preferences.getValue(this,
		// ExpressoPerfect.LOGIN, "");
		// Debug.Log("CODE VRP !!! "+Global.AXE_Ident.IDENT);
		/*		clientCodeFromNegos = appBundle.getBundle().getString(
				Espresso.CODE_CLIENT);
		Preferences
		.setValueBoolean(this, Espresso.CENTER_MAP_TO_SELF, false);
		 */
	}

	boolean isDebugMode()
	{
		PackageManager pacMan = getPackageManager();

		String pacName = getPackageName();
		ApplicationInfo appInfo=null;
		try {
			appInfo = pacMan.getApplicationInfo(pacName, 0);
		} catch (NameNotFoundException e) {
			Toast.makeText(this, "Could not find package " + pacName, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}


		if(appInfo!=null) {
			if( (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0)
				return true;

			return false;
		}

		return false;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Debug.Log("onCreate");
		initActionBar();
		setContentView(R.layout.activity_carto_debug);
		
		//On lance la task avec listener
	/*	task_getTourneeWSData taskGetTournee = new task_getTourneeWSData(this, handler, new task_getTourneeWSData.OnTaskTerminated() {
			
			@Override
			public void onTaskTerminated() {
				//TODO ici on lance l'affichage des points de la tournée
				
			}
		});
		taskGetTournee.execute();*/
		
		
		
		
		if (isDebugMode()) {
			//setContentView(R.layout.activity_carto_debug);
			mapKey="09WYiZfadJ7YB3woB2NQSpTacfJED5jEKWNFDKg";
		} else {
			//setContentView(R.layout.activity_carto);
			mapKey="0kBawG2Na8ECx3XL5SXtLcbQlzNeCO6dAiQS_6g";
		}		
		//mapKey="AIzaSyAQpakMVI8zIyONGevL42bezEKdIL0MVw0";
		//mapKey="0kBawG2Na8ECx3XL5SXtLcbQlzNeCO6dAiQS_6g";
		// setContentView(R.layout.activity_carto);
		handler = getHandler();




		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setAlarmsAndPreferences();
		TypeLogin();

		initGUI();
		initModels();
		invalidateOptionsMenu();
		initMyLocation();
		fillGUI();
		initListeners();
		// SQLRequestHelper request = new
		// SQLRequestHelper(m_appState.m_db.conn);
		// request.updateClientsTournee("LUNDI");

		// Global.dbKDVisite.clear(" and 1=1", new StringBuilder());

		//
		// GregorianCalendar calendar = new java.util.GregorianCalendar();
		// calendar.setTime( new Date() );
		// Debug.Log("TAG3",
		// "AM : "+calendar.get(Calendar.AM_PM)+" AM : "+Calendar.AM+" PM : "+Calendar.PM);

		// MyDB.copyFile(MyDB.source,MyDB.dest);

		//	initAllServices();//MV a la place de onresume et onstop
	}
	
	private void initListeners() {
		lv_proximite.setOnItemClickListener(this);
		lv_toVisit.setOnItemClickListener(this);
		lv_tournee.setOnItemClickListener(this);

		//lv_tournee.setOnItemLongClickListener(this);
		//lv_proximite.setOnItemLongClickListener(this);
		//lv_toVisit.setOnItemLongClickListener(this);

		s_1.setOnItemSelectedListener(this);
		sAVisiter.setOnItemSelectedListener(this);

		ibFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideKeyboard();
				structClient cli=new structClient();
				if (Global.dbClient.getClientbyName(actvFilter.getText().toString(), cli, new StringBuilder()))				
					showMenuPopup(cli);
				else
					Fonctions.FurtiveMessageBox(m_appState, "Ce client n'existe pas");
			}
		});
		ibRefreshPOI.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				hideKeyboard();
				Fonctions.FurtiveMessageBox(m_appState, "Mise à jour des points de ventes sur la carte");
				refreshPOI();
			}
		});
		/*
		 * mapView.setOnLongpressListener(new CartoMapView.OnLongpressListener()
		 * { public void onLongpress(final MapView view, final GeoPoint
		 * longpressLocation) { runOnUiThread(new Runnable() { public void run()
		 * { CartoMapActivity.this.longpressLocation = longpressLocation;
		 * Debug.Log
		 * ("new location : "+CartoMapActivity.this.longpressLocation.toString
		 * ()); if(handler != null){
		 * handler.sendEmptyMessage(MESSAGE_UPDATE_PI);
		 * handler.sendEmptyMessage(MESSAGE_FIND_NEAREST_POI); } } }); } });
		 */
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("v" + getStringVersion(this, new StringBuffer()));
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * La progress bar est cachée si ni le Service de Synchro tourne, ni
	 * l'affichage de la carto n'est en cours.
	 */
	private void UI_updateProgressBar() {
		if (SynchroService.isServiceRunning())
			setProgressBarIndeterminateVisibility(true);
		else if (overlayTask != null)
			setProgressBarIndeterminateVisibility(true);
		else if (findNearestPOITask != null)
			setProgressBarIndeterminateVisibility(true);
		else if (findTourneeTask != null)
			setProgressBarIndeterminateVisibility(true);
		else if (findToVisitTask != null)
			setProgressBarIndeterminateVisibility(true);
		else if (geocodeTask != null)
			setProgressBarIndeterminateVisibility(true);
		else
			setProgressBarIndeterminateVisibility(false);
	}

	@Override
	protected void onStart() {
		super.onStart();
		UI_updateProgressBar();
	}
	/*	
	void initAllServices()
	{
		try {
			registerReceiver(getServiceStatus, getServiceStatusIntentFilter());
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			registerReceiver(getUpdatePIStatus, getUpdatePIStatusIntentFilter());
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		if (myLocOverlay != null) {
			myLocOverlay.enableMyLocation();
			myLocOverlay.enableCompass();
		}

		try {
			setGPSSynchro(handler, MESSAGE_PUSH_GPS_COORDINATE);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			setUISynchro(handler, MESSAGE_UPDATE_PI);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			setFindNearestPOI(handler, MESSAGE_FIND_NEAREST_POI);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		checkGpsActivated();
	}
	 */


	@Override
	protected void onResume() {
		super.onResume();
		try {
			registerReceiver(getServiceStatus, getServiceStatusIntentFilter());
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			registerReceiver(getUpdatePIStatus, getUpdatePIStatusIntentFilter());
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		if (myLocOverlay != null) {
			myLocOverlay.enableMyLocation();
			myLocOverlay.enableCompass();
		}

		try { //MV1710
			setGPSSynchro(handler, MESSAGE_PUSH_GPS_COORDINATE);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			setUISynchro(handler, MESSAGE_UPDATE_PI);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			setFindNearestPOI(handler, MESSAGE_FIND_NEAREST_POI);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		checkGpsActivated();
		initModels();
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			unregisterReceiver(getServiceStatus);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			unregisterReceiver(getUpdatePIStatus);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			cancelGPSSynchro(handler, MESSAGE_PUSH_GPS_COORDINATE);
			Debug.Log("CANCEL GPS PUSH ");
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			cancelUISynchro(handler, MESSAGE_UPDATE_PI);
			Debug.Log("CANCEL UI SYNC ");
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		try {
			cancelFindNearestPOI(handler, MESSAGE_FIND_NEAREST_POI);
			Debug.Log("CANCEL FIND UI ");
		} catch (Exception e) {
			Debug.StackTrace(e);
		}

		if (myLocOverlay != null) {
			myLocOverlay.disableMyLocation();
			myLocOverlay.disableCompass();
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void initGUI() {
		LinearLayout llfilter = (LinearLayout) findViewById(R.id.llfilter);
		//	mapView = (CartoMapView) findViewById(R.id.mv_carto);
		mapView=new CartoMapView(this, mapKey);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mapView.setLayoutParams(lp);
		mapView.setClickable(true);
		mapView.setEnabled(true);
		llfilter.addView(mapView);

		ll_proximite = (LinearLayout) findViewById(R.id.ll_proximite);
		ll_toVisit = (LinearLayout) findViewById(R.id.ll_tovisit);
		
			

		rl_logger_bottom = (LinearLayout) findViewById(R.id.rl_logger_bottom);

		parent = ((RelativeLayout) rl_logger_bottom.getParent());
		lv_proximite = (ListView) findViewById(R.id.lv_proximite);
		lv_toVisit = (ListView) findViewById(R.id.lv_tovisit);
		lv_tournee = (ListView) findViewById(R.id.lv_tournee);
		popup = new ClientPopup(this, handler, null, null, m_btech,m_streplogin,getDB());
		menuPopup = new MenuPopup(this, handler, null,null, m_btech,m_streplogin,getDB());
		iv_demo = (ImageView) findViewById(R.id.iv_demo);
		iv_demo.setVisibility(View.GONE);
		s_1 = (Spinner) findViewById(R.id.s_1);
		sAVisiter =(Spinner) findViewById(R.id.sAVisiter);

		ibRefreshPOI=(ImageButton) findViewById(R.id.imageButtonRefreshPOI);
		ibFilter=(ImageButton) findViewById(R.id.imageButtonRSGO);
		actvFilter= (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewRS);

		tv_cptToVisit = (TextView) findViewById(R.id.tv_cptToVisit);
		tv_cptTournee = (TextView) findViewById(R.id.tv_cptTournee);

		//la liste de recherche
		String []cli=Global.dbClient.getClients();
		if (cli!=null)
		{
			actvFilter.setAdapter(new ArrayAdapter<String>   (this,android.R.layout.simple_spinner_item,cli));
		}

		m_proximiteInsteadOfToVisit = Preferences.getValueBoolean(this, Espresso.PREF_CARTOPROXIMITE,false);
		if (m_proximiteInsteadOfToVisit)
		{
			ll_toVisit.setVisibility(View.GONE);
			ll_proximite.setVisibility(View.VISIBLE);
		}
		else
		{
			ll_toVisit.setVisibility(View.VISIBLE);
			ll_proximite.setVisibility(View.GONE);
		}
		if(m_btech==true || m_bvendeur==true)
		{
			ll_toVisit.setVisibility(View.GONE);
		}

		hideKeyboard();
	}

	private void fillGUI() {
		mapView.setBuiltInZoomControls(true);


		if (isDemo)
			iv_demo.setVisibility(View.VISIBLE);
		else
			iv_demo.setVisibility(View.GONE);
	}

	/**
	 * Initialises the MyLocationOverlay and adds it to the overlays of the map
	 */
	private void initMyLocation() {
		myLocOverlay = new MyLocationOverlayExtension(this, mapView);
		if (clientCodeFromNegos != null
				&& !Fonctions.isEmptyOrNull(clientFromNegos.CODE)
				&& !Fonctions.isEmptyOrNull(clientFromNegos.LAT)
				&& !Fonctions.isEmptyOrNull(clientFromNegos.LON)) {
			mapView.getController().setZoom(ACCEPTABLE_ZOOM_LEVEL);

			Double latitude = Double.parseDouble(clientFromNegos.LAT) * 1E6;
			Double longitude = Double.parseDouble(clientFromNegos.LON) * 1E6;
			int latitude1 = latitude.intValue();
			int longitude1 = longitude.intValue();

			mapView.getController().animateTo(
					new GeoPoint(Integer.valueOf(latitude1), Integer
							.valueOf(longitude1)));
			if (handler != null) {
				if (isDemo) {
					Message m = new Message();
					m.what = MESSAGE_UPDATE_PI;
					m.arg1 = MESSAGE_UPDATE_PI_ARG1_FORCE;
					handler.sendMessageDelayed(m, 2500);
				} else
					handler.sendEmptyMessageDelayed(MESSAGE_UPDATE_PI, 2500);
				handler.sendEmptyMessageDelayed(MESSAGE_FIND_NEAREST_POI, 2500);
			}
		}
		myLocOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				try {
					if (clientCodeFromNegos == null)
						mapView.getController().animateTo(
								myLocOverlay.getMyLocation());
				} catch (Exception e) {
					Debug.StackTrace(e);
				}
				Debug.Log("Lat : "
						+ myLocOverlay.getMyLocation().getLatitudeE6());
				Debug.Log("Lon : "
						+ myLocOverlay.getMyLocation().getLongitudeE6());

				mapView.currentZoomLevel = mapView.getZoomLevel();
				// On récupère la coordonnée GPS au premier fix
				String timerGPS = Preferences.getValue(CartoMapActivity.this,
						Espresso.TIMER_MONITORING_GPS_SECONDS, "0");
				Debug.Log("timer GPS : DESACTIVE " + timerGPS);

				//MV1710
				if (!Fonctions.isEmptyOrNull(timerGPS) && !timerGPS.equals("0")) {
					Debug.Log("trigger GPS Service!");
					GpsMonitorService.triggerService(CartoMapActivity.this);
					// La récurrence de récupération est initiée/inhibée
					// dans le onResume/onPause
				}

				if (clientCodeFromNegos == null && handler != null) {
					handler.sendEmptyMessage(MESSAGE_UPDATE_PI);
					handler.sendEmptyMessage(MESSAGE_FIND_NEAREST_POI);
					handler.sendEmptyMessage(MESSAGE_UPDATE_LIST_TOURNEE);
				}

				isFirstFixDone = true;

				// DisplayMetrics metrics = new DisplayMetrics();
				// getWindowManager().getDefaultDisplay().getMetrics(metrics);
				//
				// calculateZoomLevel(metrics.widthPixels, 2000);
			}
		});
		mapView.getOverlays().add(myLocOverlay);
		mapView.postInvalidate();
	}

	private class MyLocationOverlayExtension extends MyLocationOverlay {
		MapView mapView;

		public MyLocationOverlayExtension(Context context, MapView mapView) {
			super(context, mapView);
			this.mapView = mapView;
		}

		@Override
		public synchronized void onLocationChanged(Location location) {
			super.onLocationChanged(location);
			// Debug.Log("Location : "+location.toString());
			if (centerMapToSelf) {
				GeoPoint point = new GeoPoint(
						(int) (location.getLatitude() * 1E6),
						(int) (location.getLongitude() * 1E6));
				// point = new GeoPoint((int) (50625060 ), (int) (3047970));

				mapView.getController().animateTo(point);
			}
		}

	}

	private void initModels() {
		((app) getApplication()).createTables();
		((app) getApplication()).createIndex();
		MyDB.copyFile(MyDB.source, MyDB.dest);

		Debug.Log("INIT MODELS");
		markerController = new Marker(this);
		mapView.getOverlays().clear();
		externalStorage = new ExternalStorage(this, false);
		surroundingClients = new ClientOverlay(getResources().getDrawable(
				R.drawable.marker), handler);
		mc = mapView.getController();
		centerMapToSelf = Preferences.getValueBoolean(this,
				Espresso.CENTER_MAP_TO_SELF, false);
		isDemo = Preferences.getValueBoolean(this, Espresso.MAP_MODE_TOURNEE,
				false);
		logData = new ArrayList<Log>();
		adapter = new LogAdapter(this, R.layout.item_list, logData);

		tourneeData = new ArrayList<Log>();
		tourneeAdapter = new TourneeAdapter(this, R.layout.item_list_tournee, tourneeData, m_btech, getDB());

		toVisitData = new ArrayList<Log>();
		toVisitAdapter = new ToVisitAdapter(this, R.layout.item_list_tournee,
				toVisitData);

		lv_proximite.setAdapter(adapter);
		lv_toVisit.setAdapter(toVisitAdapter);

		lv_tournee.setAdapter(tourneeAdapter);
		//		lv_4.setAdapter(tourneeAdapter);

		if (clientCodeFromNegos != null)
			clientFromNegos = Global.dbClient.load(clientCodeFromNegos);

		/**
		 * Suppression de toutes les visites (dbKD) qui ne sont pas
		 * d'aujourd'hui
		 */
		String jourPassage = SQLRequestHelper.getCodeTournee(Fonctions
				.getYYYYMMDD());
//		String contrainte = " and 1=1 " + " and "
//				+ Global.dbKDVisite.FIELD_JOUR_PASSAGE + "<>'" + jourPassage + "'";
		String contrainte = " and 1=1 " + " and "
		+ Global.dbKDVisite.FIELD_JOUR_PASSAGE + "<>'" + Fonctions.getYYYYMMDD() + "'";
		Global.dbKDVisite.clear(contrainte, new StringBuilder());

		zones = new ArrayList<String>();
		zonesvisiter= new ArrayList<String>();

		try {
			dbTournee to=new dbTournee(getDB());
			//tof, dev 50.2, desactivation affichage des date
			/*if(m_bvendeur==true)
			{
				zones = to.get2();
			}else
				zones = to.get();*/
			//Get2 -> Tournee
			//Get -> Date
			if(m_btech==true)
			{
				zones = to.get();
			}else
				zones = to.get2();



		} catch (Exception e) {
			Debug.StackTrace(e);
		}
		spinAdapter = new SpinAdapter(this,android.R.layout.simple_spinner_item, zones);
		s_1.setAdapter(spinAdapter);

		try {

			if(m_btech==false)
			{
				if (zones.size()>0){
					String selection =  Preferences.getValue(CartoMapActivity.this,Espresso.CODE_TOURNEE,"0");
					if(zones.size()>=Fonctions.GetStringToIntDanem(selection) )
					s_1.setSelection(Integer.parseInt(selection));
				}
			}


		} catch (Exception e) {

		}

		
		try {
			dbCliToVisit to=new dbCliToVisit(getDB());
			zonesvisiter = to.get();

		} catch (Exception e) {
			Debug.StackTrace(e);
		}
		
		try {

			if (zonesvisiter.size()>0)
			{
				spinAdapter = new SpinAdapter(this,android.R.layout.simple_spinner_item, zonesvisiter);
				sAVisiter.setAdapter(spinAdapter);
				sAVisiter.setSelection(0);
				sAVisiter.setVisibility(View.INVISIBLE);

			}
				

		} catch (Exception e) {

		}
		
			// clientGeoPointFromNegos =
		// Global.dbClient.getGeoPoint(clientCodeFromNegos);

		// GeoPoint centerPosition = new GeoPoint(50488596, 2743698); // OK
		// Paris : 48.856583,2.352276
	}

	private void showSurroundingClients() {
		mapView.getOverlays().add(surroundingClients);
		// mapView.invalidate();
	}

	/**
	 * mini popup affiché lorsqu'un marker est touché
	 * 
	 * @param point
	 * @param structClient
	 */
	public void showPopup(GeoPoint point, structClient structClient) {
		structSoc soc = Global.dbCliToVisit.getCliToVisitFromCodeCli(structClient.CODE);
		popup.updatePopup(structClient, soc, true);

		MapView.LayoutParams mapParams = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point, 0,
				-15, MapView.LayoutParams.BOTTOM_CENTER);

		Animation dialogEnter = AnimationUtils.loadAnimation(this,
				R.anim.dialog_enter);
		mapView.addView(popup.getView(), mapParams);
		popup.getView().startAnimation(dialogEnter);
	}

	/**
	 * mini popup caché
	 */
	public void hidePopup() {
		mapView.removeView(popup.getView());
	}

	/**
	 * grand popup qui affiche Stock / Horaire / Naviguation / Appeler ...
	 * 
	 * @param structClient
	 */
	public void showMenuPopup(structClient structClient) {
		hideMenuPopup();
		structSoc soc = Global.dbCliToVisit.getCliToVisitFromCodeCli(structClient.CODE);
		
		menuPopup.updatePopup(structClient, true);

		RelativeLayout.LayoutParams mapParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mapParams.addRule(RelativeLayout.CENTER_VERTICAL);
		mapParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		Animation dialogEnter = AnimationUtils.loadAnimation(this,
				R.anim.dialog_enter);
		parent.addView(menuPopup.getView(), mapParams);
		menuPopup.getView().startAnimation(dialogEnter);
		// parent.invalidate();
	}

	/**
	 * grand popup caché
	 */
	public void hideMenuPopup() {
		try {
			parent.removeView(menuPopup.getView());
		} catch (Exception e) {
			Debug.StackTrace(e);
		}
	}

	/**
	 * Callback appelée lorsque le service a terminé un scenario
	 */
	private BroadcastReceiver getServiceStatus = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);
			// UI_updateProgressBar();
		}
	};

	public static IntentFilter getServiceStatusIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(SynchroService.UPDATE_UI_PROGRESS_BAR_INTENT);
		return intentFilter;
	}

	/**
	 * Callback appelée lorsque le service de synchro est terminé suite à une
	 * demande explicite (depuis l'action bar)
	 */
	private BroadcastReceiver getUpdatePIStatus = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (handler != null) {
				handler.sendEmptyMessage(MESSAGE_UPDATE_PI);
				handler.sendEmptyMessage(MESSAGE_UPDATE_LIST_TOURNEE);
				handler.sendEmptyMessage(MESSAGE_FIND_NEAREST_POI);
			}

			// UI_updateProgressBar();
		}

	};

	public static IntentFilter getUpdatePIStatusIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(SynchroService.UPDATE_UI_POINT_OF_INTERESTS);
		return intentFilter;
	}

	/** Activate GPS */
	private void alertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.activation_GPS_requise))
		.setCancelable(false)
		.setPositiveButton(getString(R.string.Yes),
				new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog,
					final int id) {
				dialog.cancel();
				startActivity(new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		})
		.setNegativeButton(getString(R.string.No),
				new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog,
					final int id) {
				dialog.cancel();
				finish();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private boolean isGPSActivated() {
		LocationManager manager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	private void checkGpsActivated() {

		if (!isGPSActivated()) {
			alertMessageNoGps();
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (centerMapToSelf)
			itemCenterCarto.setIcon(R.drawable.ic_action_gps_on);
		else
			itemCenterCarto.setIcon(R.drawable.ic_action_gps);

		if (isDemo)
			itemDemo.setIcon(R.drawable.ic_action_demo_on);
		else
			itemDemo.setIcon(R.drawable.ic_action_demo);

		return super.onPrepareOptionsMenu(menu);

	}

	/** Action Bar */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// addMenu(menu, R.string.Yes, R.drawable.marker);
		//		addMenu(menu, R.string.Synchro, R.drawable.content_import_export);

		//	addMenu(menu, R.string.Prospect, R.drawable.action_select_client);

		itemDemo = addMenu(menu, R.string.Simulation, R.drawable.ic_action_demo);
		itemCenterCarto = addMenu(menu, R.string.centrer,
				R.drawable.ic_action_gps);
		//	addMenu(menu, R.string.POI, R.drawable.action_map);
		//	if (clientCodeFromNegos != null)
		//		addMenu(menu, R.string.Client, clientFromNegos.NOM,
		//				R.drawable.action_select_client);
		//addMenu(menu, R.string.Tournee, R.drawable.ic_action_calendar);
		//addMenu(menu, R.string.menu_bilanjour, R.drawable.ic_action_calendar);

		// addMenu(menu, R.string.Geocode, R.drawable.ic_action_geocode);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:

			finish();
			return true;

		case R.string.Prospect:
			if (handler != null) {
				handler.sendEmptyMessage(MESSAGE_PROSPECT_BACK_NEGOS);
			}
			return true;


		case R.string.centrer:
			if (centerMapToSelf) {
				setCenterToSelf();
			} else {
				setCenterToSelf();
				if (isDemo)
					setDemoMode();
			}

			return true;
		case R.string.Simulation:
			if (isDemo) {
				setDemoMode();
			} else {
				// Décommenter la ligne ci-dessous pour faire apparaître la
				// dialogue (et commenter les 3 autres lignes d'après!)
				// chooseDateDialog(getString(R.string.select_date_time_tournee));

				GregorianCalendar savedCalendar = new java.util.GregorianCalendar();
				// Date Fevrier_8_2011_15h_1min = new Date(1297173716000L);
				// savedCalendar.setTime(Fevrier_8_2011_15h_1min);
				Preferences.setValueLong(CartoMapActivity.this,
						Preferences.KEY_DATE_TOURNEE,
						savedCalendar.getTimeInMillis());

				if (centerMapToSelf)
					setCenterToSelf();

				setDemoMode();

			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	void refreshPOI()
	{
		if (handler != null) {
			if (isDemo) {
				Message m = new Message();
				m.what = MESSAGE_UPDATE_PI;
				m.arg1 = MESSAGE_UPDATE_PI_ARG1_FORCE;
				handler.sendMessage(m);
			} else
				handler.sendEmptyMessage(MESSAGE_UPDATE_PI);

			handler.sendEmptyMessage(MESSAGE_UPDATE_LIST_TOURNEE);
		}
		if (SynchroService.isServiceRunning())
			Toast.makeText(
					CartoMapActivity.this,
					getString(R.string.Synchro_maj) + " "
							+ getString(R.string.merci_patienter2),
							Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(CartoMapActivity.this,
					getString(R.string.Carto_maj), Toast.LENGTH_SHORT)
					.show();
	}
	private void checkSimulationMarker() {
		if (mapView.getZoomLevel() < ACCEPTABLE_ZOOM_LEVEL) {
			iv_demo.setBackgroundResource(R.drawable.car);
		} else {
			iv_demo.setBackgroundResource(R.drawable.car_green);
		}
	}

	private void setDemoMode() {
		if (isDemo) {
			isDemo = false;
			iv_demo.setVisibility(View.GONE);
			Toast.makeText(this, R.string.tournee_off, Toast.LENGTH_SHORT)
			.show();
		} else {
			isDemo = true;
			iv_demo.setVisibility(View.VISIBLE);
			Toast.makeText(this, R.string.tournee_on, Toast.LENGTH_SHORT)
			.show();
		}

		checkSimulationMarker();

		Preferences.setValueBoolean(this, Espresso.MAP_MODE_TOURNEE, isDemo);
		invalidateOptionsMenu();
	}

	private void setCenterToSelf() {
		if (centerMapToSelf) {
			centerMapToSelf = false;
			Toast.makeText(this, R.string.centrer_carte_not, Toast.LENGTH_SHORT)
			.show();
		} else {
			centerMapToSelf = true;
			if (isDemo)
				setDemoMode();
			Toast.makeText(this, R.string.centrer_carte, Toast.LENGTH_SHORT)
			.show();
		}
		Preferences.setValueBoolean(this, Espresso.CENTER_MAP_TO_SELF,
				centerMapToSelf);
		invalidateOptionsMenu();

	}



	/** Async Task */
	private void removeSurroundingClients() {
		try {
			surroundingClients.clearClients();
			mapView.getOverlays().remove(surroundingClients);
		} catch (Exception e) {
		}
	}

	/**
	 * Renvoie la dernière coordonnée GPS connue du system (GPS, à défaut
	 * NETWORK)
	 * 
	 * @return
	 */
	private GeoPoint getCurrentPoint() {
		GeoPoint a = null;
		/*
		 * if(CartoMapActivity.this.longpressLocation != null){ // a =
		 * CartoMapActivity.this.longpressLocation; a = mapView.getMapCenter();
		 * }
		 */
		if (isDemo) {
			a = mapView.getMapCenter();
		} else {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location == null)
				location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if(location != null)
				a = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
		}
		return a;
	}

	/**
	 * Tâche qui calcule les POI et les affiche sur la carto
	 * 
	 */
	private class OverlayTask extends AsyncTask<Boolean, Void, Void> {
		@Override
		public void onPreExecute() {
			Debug.Log("Overlay task START");
			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);
			removeSurroundingClients();
		}

		@Override
		public Void doInBackground(Boolean... isToVisit) {
			boolean toVisit = false;
			try {
				toVisit = isToVisit[0];
				
			} catch (Exception e) {
				Debug.StackTrace(e);
			}

			GeoPoint centerPosition = getCurrentPoint();
			//String jourPassage = Fonctions.dd_mm_yyyy_TO_yyyymmdd(zones.get(zoneSpinnerPosition));

			if (centerPosition != null) {
				SQLRequestHelper request = new SQLRequestHelper(
						m_appState.m_db.conn);
				Date dateRequest = new Date();
				if (isDemo) {
					dateRequest = new Date(Preferences.getValueLong(
							CartoMapActivity.this,
							Preferences.KEY_DATE_TOURNEE,
							System.currentTimeMillis()));
				}
				Cursor clientsAndHoraires;



				// String zone = (String)s_1.getSelectedItem();
				String zone = "";
				String jourPassageTournee = "";
				String jourPassageVisit = "";

				if (zones.size() > 0 ){
					jourPassageTournee=Fonctions.dd_mm_yyyy_TO_yyyymmdd(zones.get(zoneSpinnerPosition));
				}
				if (zonesvisiter.size() > 0){
					jourPassageVisit=Fonctions.dd_mm_yyyy_TO_yyyymmdd(zonesvisiter.get(zoneSpinnerPositionAVisiter));
				}

				//Date date=new Date();
				//clientsAndHoraires = request.getClients(date, centerPosition, 1000000, false, false, "", jourPassageVisit, jourPassageTournee);

				//Récupération des points grace aux listes
				ArrayList<Log> logs = new ArrayList<Log>();
				logs.addAll(tourneeData);
				logs.addAll(toVisitData);

				for(Log log : logs){
					Debug.Log("Log","Log"+log);
					structClient client = log.getClient();
					structVisite visite = new structVisite();
					String currentJourPassage = "";
					
					//Il faut voir dans quel liste est le client pour récupérer la bonne visite par rapport à la bonne journée de passage
					if(tourneeData.contains(log)){
						currentJourPassage = jourPassageTournee;
					}else if(toVisitData.contains(log)){
						currentJourPassage = jourPassageVisit;
					}
					if(client.CLI_TO_VISIT_STRUCT != null )
					  Global.dbKDVisite.load_evt_id(visite, client.CODE, currentJourPassage,Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID), new StringBuffer());
					else
					  Global.dbKDVisite.load_evt_id(visite, client.CODE, currentJourPassage,"", new StringBuffer());
						
					if (!Fonctions.isEmptyOrNull(client.LAT)
							&& !Fonctions.isEmptyOrNull(client.LON)) {

						try {

							Double latitude = Double.parseDouble(client.LAT) * 1E6;
							Double longitude = Double.parseDouble(client.LON) * 1E6;
							int latitude1 = latitude.intValue();
							int longitude1 = longitude.intValue();

							GeoPoint geoPoint = new GeoPoint(
									Integer.valueOf(latitude1),
									Integer.valueOf(longitude1));
							ClientItem item = new ClientItem(client.CODE,
									geoPoint, "title", "snippet");
							// Debug.Log("client code : "+item.getClientCode());
							item.setClient(client);

							String couleur = "";
							if(client.CODE.equals("C0959395"))
							{
								String testt="";
								testt="e";
							}

							//si le client est dans les à visiter
//							if(toVisitData.contains(log)){
//								if(client.TYPE != null){
//									if(client.TYPE.equals("C")){
//										//couleur = getString(R.string.iconBlue);
//										couleur = colorItem(getString(R.string.iconBlue),client.CODE,client.TYPE_NUMINTER);
//									}else if(client.TYPE.equals("P")){
//										couleur = colorItem(getString(R.string.iconGreen),client.CODE,client.TYPE_NUMINTER);
//										
//									}
//								}
//							}else{
//								//l'icone est blanche
//								if(m_btech==true)
//								{
//									couleur  = colorItem(getString(R.string.iconRed),client.CODE,client.TYPE_NUMINTER);
//								}
//								else
//								couleur  = colorItem(getString(R.string.iconWhite),client.CODE,client.TYPE_NUMINTER);
//							}
							
							couleur = client.COULEUR;
							
							//djijnknknnknkkn
							

							Drawable d = markerController.getMarker(
									client.ICON, couleur,
									client.IMPORTANCE, client.STATUT,
									visite.DEJA_VU);

							d.setBounds(
									(int) ((0 - d.getIntrinsicWidth() / 2) * (client
											.getIconSize())),
											(int) ((0 - d.getIntrinsicHeight()) * (client
													.getIconSize())),
													(int) ((d.getIntrinsicWidth() / 2) * (client
															.getIconSize())), 0);

							item.setMarker(d);
							surroundingClients.addClient(item);
						} catch (Exception e) {

						}
					}
				}
				
				logs = null;

				/*if (clientsAndHoraires != null
						&& clientsAndHoraires.moveToFirst()) {
					while (clientsAndHoraires.isAfterLast() == false) {
						structClient client = Global.dbClient
								.load(clientsAndHoraires);
						structVisite visite = new structVisite();
						Global.dbKDVisite.load(visite, client.CODE,
								jourPassage, new StringBuffer());
						if (!Fonctions.isEmptyOrNull(client.LAT)
								&& !Fonctions.isEmptyOrNull(client.LON)) {

							try {

								Double latitude = Double.parseDouble(client.LAT) * 1E6;
								Double longitude = Double.parseDouble(client.LON) * 1E6;
								int latitude1 = latitude.intValue();
								int longitude1 = longitude.intValue();

								GeoPoint geoPoint = new GeoPoint(
										Integer.valueOf(latitude1),
										Integer.valueOf(longitude1));
								ClientItem item = new ClientItem(client.CODE,
										geoPoint, "title", "snippet");
								// Debug.Log("client code : "+item.getClientCode());
								item.setClient(client);

								String couleur = "";

								//si le client est dans les à visiter
								if(Global.dbCliToVisit.isClientAvisiter(client.CODE)){
									if(client.TYPE != null){
										if(client.TYPE.equals("C")){
											couleur = getString(R.string.iconBlue);
										}else if(client.TYPE.equals("P")){
											couleur = getString(R.string.iconGreen);
										}
									}
								}else{
									//l'icone est blanche
									couleur  = getString(R.string.iconWhite);
								}

								Drawable d = markerController.getMarker(
										client.ICON, couleur,
										client.IMPORTANCE, client.STATUT,
										visite.DEJA_VU);

								d.setBounds(
										(int) ((0 - d.getIntrinsicWidth() / 2) * (client
												.getIconSize())),
												(int) ((0 - d.getIntrinsicHeight()) * (client
														.getIconSize())),
														(int) ((d.getIntrinsicWidth() / 2) * (client
																.getIconSize())), 0);

								item.setMarker(d);
								surroundingClients.addClient(item);
							} catch (Exception e) {

							}
						}
						clientsAndHoraires.moveToNext();
					}
					clientsAndHoraires.close();

				}*/

			}

			return null;
		}

		@Override
		public void onPostExecute(Void unused) {
			showSurroundingClients();
			overlayTask = null;
			Debug.Log("Overlay task STOP : " + overlayTask);
			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);
			// adapter.notifyDataSetChanged();
		}
	}

	private class FindTourneeTask extends AsyncTask<Void, Void, Log> {
		ArrayList<Log> tmpData = new ArrayList<Log>();
		ArrayList<Log> tmpDataDejaVisite = new ArrayList<Log>();
		ArrayList<Log> tmpDataAVisiter = new ArrayList<Log>();

		@Override
		public void onPreExecute() {
			Debug.Log("TAG2", "FindTourneeTask task START");

			// Required ?
			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);

		}

		@Override
		public Log doInBackground(Void... unused) {
			GeoPoint centerPosition = getCurrentPoint();
			SQLRequestHelper request = new SQLRequestHelper(
					m_appState.m_db.conn);

			String jourPassage = "";

			// String zone = (String)s_1.getSelectedItem();
			Cursor clientsTournee = null;
			if(m_btech==false)
			{
				if (zones.size() > 0)
					jourPassage=zones.get(zoneSpinnerPosition);

					clientsTournee =request.getClientsTourneeEx2(jourPassage, true );
			}

			else
			{
				//tof, dev50.2, desactivation fonction autre que vendeur
				if (zones.size() > 0)
					jourPassage=Fonctions.dd_mm_yyyy_TO_yyyymmdd(zones.get(zoneSpinnerPosition));

				clientsTournee =request.getClientsTourneeEx(jourPassage, true );

			}



			String clientExite="";
			if (clientsTournee != null && clientsTournee.moveToFirst()) {
				while (clientsTournee.isAfterLast() == false) {
					structClient client = Global.dbClient.load(clientsTournee);

					// Debug.Log("TAG2","TOURNEE : "+client.toString());

					//On met les icones des clients en blanc
					//client.COULEUR = getString(R.string.iconWhite);
					if(!client.CODE.equals(clientExite))
					{
					
					//si le client est dans les à visiter
					if(Global.dbCliToVisit.isClientAvisiter(client.CODE)){
						if(client.TYPE != null){
							if(client.TYPE.equals("C")){
								//dev60.2: on ne met les client en bleu que si ils ont un agenda
								dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
								dbKD106AgendaCorrespondance.passePlat rr = db106.getFistRdvForCli(client.CODE) ;
								if (  rr != null )
									if ( !rr.FIELD_DTDEBUT.equals(""))
										client.COULEUR =colorItem(getString(R.string.iconBlue),client.CODE,client.TYPE_NUMINTER);
								//client.COULEUR = getString(R.string.iconBlue);
								
							}else if(client.TYPE.equals("P")){
								//client.COULEUR = getString(R.string.iconGreen);
								client.COULEUR =colorItem(getString(R.string.iconGreen),client.CODE,client.TYPE_NUMINTER);
							}
						}
					}else{
						//l'icone est blanche
						//client.COULEUR  = getString(R.string.iconWhite);
						if(m_btech==true)
						{
							client.COULEUR   = colorItem(getString(R.string.iconRed),client.CODE,client.TYPE_NUMINTER);
						}
						else
						client.COULEUR =colorItem(getString(R.string.iconWhite),client.CODE,client.TYPE_NUMINTER);
					}

					Log data = new Log();

					if (!Fonctions.isEmptyOrNull(client.LAT)
							&& !Fonctions.isEmptyOrNull(client.LON)) {

						data.setDistance("-1");
						data.setfDistance(-1);

						data.setClient(client);

						Double latitude = Double.parseDouble(client.LAT) * 1E6;
						Double longitude = Double.parseDouble(client.LON) * 1E6;
						int latitude1 = latitude.intValue();
						int longitude1 = longitude.intValue();

						GeoPoint geoPoint = new GeoPoint(
								Integer.valueOf(latitude1),
								Integer.valueOf(longitude1));
						data.setGeoPoint(geoPoint);

						int position = 0;

						try {
							float[] results = new float[3];
							Location.distanceBetween(
									centerPosition.getLatitudeE6() / 1E6,
									centerPosition.getLongitudeE6() / 1E6,
									geoPoint.getLatitudeE6() / 1E6,
									geoPoint.getLongitudeE6() / 1E6, results);
							float minDistance = results[0];
							data.setfDistance(minDistance);
							data.setDistance(String.valueOf(Math
									.round(minDistance)));

							/**
							 * Une technique pour éviter le tri, c'est
							 * d'ajouter au bon index à l'insertion
							 */
							/**
							 * L'autre technique est d'utiliser
							 * Collections.sort(tourneeData);
							 */
							/*
							 * for(int i =0; i<tmpData.size(); i++){ //
							 * if(Integer.valueOf( tmpData.get(i).getDistance())
							 * > Integer.valueOf(data.getDistance()) ){ if(
							 * tmpData.get(i).getfDistance() >
							 * data.getfDistance() ){ position = i+1; break; } }
							 */

						} catch (Exception e) {
							Debug.StackTrace(e);
						}

						data.setHoraire(Global.dbHoraire.load(client.CODE));
						data.setFermetures(Global.dbFermeture.load(client.CODE));
						data.setOpened(request.isClientOpen(client.CODE, true,
								false));
						data.setFermeture(!request
								.isClientOpenedFermeture(client.CODE));
						boolean isDjaVisite = Global.dbKDVisite.isDejaVisiteId(
								client.CODE, jourPassage,"");
						structVisite visite = new structVisite();
						Global.dbKDVisite.load_evt_id(visite, client.CODE,
								jourPassage,"", new StringBuffer());
						data.setVisite(visite);
						// Debug.Log("TAG3", "is Dejà visite : "+isDjaVisite);
						data.setDejaVisite(isDjaVisite);
						// Debug.Log("TAG3", "client OPEN ? "+data.isOpened());
						// tmpData.add(position, data);
						tmpData.add(data);

					}else{
						data.setClient(client);
						data.setHoraire(Global.dbHoraire.load(client.CODE));
						data.setFermetures(Global.dbFermeture.load(client.CODE));
						data.setOpened(request.isClientOpen(client.CODE, true,
								false));
						data.setFermeture(!request
								.isClientOpenedFermeture(client.CODE));
						boolean isDjaVisite = Global.dbKDVisite.isDejaVisiteId(
								client.CODE, jourPassage,"");
						structVisite visite = new structVisite();
						Global.dbKDVisite.load_evt_id(visite, client.CODE,
								jourPassage,"", new StringBuffer());
						data.setVisite(visite);
						// Debug.Log("TAG3", "is Dejà visite : "+isDjaVisite);
						data.setDejaVisite(isDjaVisite);
						// Debug.Log("TAG3", "client OPEN ? "+data.isOpened());
						// tmpData.add(position, data);
						tmpData.add(data);
					}

				}
				clientExite=client.CODE;
				clientsTournee.moveToNext();
				 	
				}
				clientsTournee.close();

			}

			ListIterator<Log> litr = tmpData.listIterator();
			while (litr.hasNext()) {
				Log element = (Log) litr.next();
				if (element.isFermeture() == false) {
					if (element.isDejaVisite())
						tmpDataDejaVisite.add(element);
					else
						tmpDataAVisiter.add(element);
				}
			}

			// Comparator comparator = Collections.reverseOrder();

			// Collections.sort(tmpDataAVisiter,comparator);
			// Collections.sort(tmpDataDejaVisite,comparator);
			Collections.sort(tmpDataAVisiter);
			Collections.sort(tmpDataDejaVisite);
			tmpData.clear();
			tmpData.addAll(tmpDataAVisiter);
			//Folliet : on affiche pas les clients deja visités
			//tmpData.addAll(tmpDataDejaVisite);

			return null;
		}

		@Override
		public void onPostExecute(Log nearest) {
			Debug.Log("TAG2", "FindTourneeTask STOP : " + findTourneeTask);

			findTourneeTask = null;
			tourneeData.clear();
			tourneeData.addAll(tmpData);
			tourneeAdapter.notifyDataSetChanged();

			//on initialise le compteur
			int cpt = 0;

			for(Log log : tourneeData){
				if(log.isDejaVisite()){
					cpt++;
				}
					
			}

			int count = tourneeData.size()-cpt;
			if(count >= 0){
				tv_cptTournee.setText(Integer.toString(count));
			}

			/*
			 * if(tourneeData != null && tourneeData.size()>0){ SQLRequestHelper
			 * request = new SQLRequestHelper(m_appState.m_db.conn); boolean
			 * clientOpen =
			 * request.isClientOpen(tourneeData.get(0).getClient().CODE, true,
			 * true); Debug.Log("TAG3", "client OPEN ? "+clientOpen); }
			 */

			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);

		}

	}
	private class FindToVisitTask extends AsyncTask<Void, Void, Log> {
		ArrayList<Log> tmpData = new ArrayList<Log>();
		ArrayList<Log> tmpDataDejaVisite = new ArrayList<Log>();
		ArrayList<Log> tmpDataAVisiter = new ArrayList<Log>();

		@Override
		public void onPreExecute() {
			Debug.Log("TAG2", "FindToVisitTask task START");

			// Required ?
			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);

		}

		@Override
		public Log doInBackground(Void... unused) {
			GeoPoint centerPosition = getCurrentPoint();
			SQLRequestHelper request = new SQLRequestHelper(
					m_appState.m_db.conn);

			String jourPassage = "";
			jourPassage = Fonctions.dd_mm_yyyy_TO_yyyymmdd(zonesvisiter.get(zoneSpinnerPositionAVisiter));

			//Cursor clientsTournee = request.getClientsTourneeEx(jourPassage, true);
			Cursor clientsTournee = request.getClientsToVisit(jourPassage );
			String stclientdoublon = "";
			if (clientsTournee != null && clientsTournee.moveToFirst()) {
				while (clientsTournee.isAfterLast() == false) {
					structClient client = Global.dbClient.load(clientsTournee);


					if (!Fonctions.GetStringDanem(client.CODE).equals(stclientdoublon))
					{

						// Debug.Log("TAG2","TOURNEE : "+client.toString());

						//On met les icones des clients en bleue et des prospects en vert
						if (client.TYPE != null) {
							if (client.TYPE.equals("C")) {
								//dev60.2: on ne met les client en bleu que si ils ont un agenda
								dbKD106AgendaCorrespondance db106 = new dbKD106AgendaCorrespondance(Global.dbParam.getDB());
								dbKD106AgendaCorrespondance.passePlat rr = db106.getFistRdvForCli(client.CODE);
								if (rr != null)
									if (!rr.FIELD_DTDEBUT.equals(""))
										client.COULEUR = getString(R.string.iconBlue);
							} else if (client.TYPE.equals("P")) {
								client.COULEUR = getString(R.string.iconGreen);
							}
						}

						Log data = new Log();

						if (!Fonctions.isEmptyOrNull(client.LAT)
								&& !Fonctions.isEmptyOrNull(client.LON)) {

							data.setDistance("-1");
							data.setfDistance(-1);

							data.setClient(client);

							Double latitude = Double.parseDouble(client.LAT) * 1E6;
							Double longitude = Double.parseDouble(client.LON) * 1E6;
							int latitude1 = latitude.intValue();
							int longitude1 = longitude.intValue();

							GeoPoint geoPoint = new GeoPoint(
									Integer.valueOf(latitude1),
									Integer.valueOf(longitude1));
							data.setGeoPoint(geoPoint);

							int position = 0;

							try {
								float[] results = new float[3];
								Location.distanceBetween(
										centerPosition.getLatitudeE6() / 1E6,
										centerPosition.getLongitudeE6() / 1E6,
										geoPoint.getLatitudeE6() / 1E6,
										geoPoint.getLongitudeE6() / 1E6, results);
								float minDistance = results[0];
								data.setfDistance(minDistance);
								data.setDistance(String.valueOf(Math
										.round(minDistance)));

								/**
								 * Une technique pour éviter le tri, c'est
								 * d'ajouter au bon index à l'insertion
								 */
								/**
								 * L'autre technique est d'utiliser
								 * Collections.sort(tourneeData);
								 */
								/*
								 * for(int i =0; i<tmpData.size(); i++){ //
								 * if(Integer.valueOf( tmpData.get(i).getDistance())
								 * > Integer.valueOf(data.getDistance()) ){ if(
								 * tmpData.get(i).getfDistance() >
								 * data.getfDistance() ){ position = i+1; break; } }
								 */

							} catch (Exception e) {
								Debug.StackTrace(e);
							}

							data.setHoraire(Global.dbHoraire.load(client.CODE));
							data.setFermetures(Global.dbFermeture.load(client.CODE));
							data.setOpened(request.isClientOpen(client.CODE, true,
									false));
							data.setFermeture(!request
									.isClientOpenedFermeture(client.CODE));
							boolean isDjaVisite = false;
							if (client.CLI_TO_VISIT_STRUCT != null) {
								isDjaVisite = Global.dbKDVisite.isDejaVisiteId(
										client.CODE, jourPassage, Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID));

							} else
								isDjaVisite = Global.dbKDVisite.isDejaVisiteId(
										client.CODE, jourPassage, "");


							structVisite visite = new structVisite();
							if (client.CLI_TO_VISIT_STRUCT != null) {
								Global.dbKDVisite.load_evt_id(visite, client.CODE,
										jourPassage, Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID), new StringBuffer());
							} else {
								Global.dbKDVisite.load_evt_id(visite, client.CODE,
										jourPassage, "", new StringBuffer());

							}
							data.setVisite(visite);
							// Debug.Log("TAG3", "is Dejà visite : "+isDjaVisite);
							data.setDejaVisite(isDjaVisite);
							// Debug.Log("TAG3", "client OPEN ? "+data.isOpened());
							// tmpData.add(position, data);
							tmpData.add(data);

						} else {
							data.setClient(client);
							data.setHoraire(Global.dbHoraire.load(client.CODE));
							data.setFermetures(Global.dbFermeture.load(client.CODE));
							data.setOpened(request.isClientOpen(client.CODE, true,
									false));
							data.setFermeture(!request
									.isClientOpenedFermeture(client.CODE));
							boolean isDjaVisite = false;
							if (client.CLI_TO_VISIT_STRUCT != null) {
								isDjaVisite = Global.dbKDVisite.isDejaVisiteId(
										client.CODE, jourPassage, Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID));
							} else {
								isDjaVisite = Global.dbKDVisite.isDejaVisiteId(
										client.CODE, jourPassage, "");
							}

							structVisite visite = new structVisite();
							if (client.CLI_TO_VISIT_STRUCT != null) {
								Global.dbKDVisite.load_evt_id(visite, client.CODE,
										jourPassage, Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID), new StringBuffer());

							} else {
								Global.dbKDVisite.load_evt_id(visite, client.CODE,
										jourPassage, "", new StringBuffer());

							}
							data.setVisite(visite);
							// Debug.Log("TAG3", "is Dejà visite : "+isDjaVisite);
							data.setDejaVisite(isDjaVisite);
							// Debug.Log("TAG3", "client OPEN ? "+data.isOpened());
							// tmpData.add(position, data);
							tmpData.add(data);
						}

					}
					stclientdoublon=client.CODE;

					clientsTournee.moveToNext();

				}
				clientsTournee.close();

			}

			ListIterator<Log> litr = tmpData.listIterator();
			while (litr.hasNext()) {
				Log element = (Log) litr.next();
				if (element.isFermeture() == false) {
					if (element.isDejaVisite())
						tmpDataDejaVisite.add(element);
					else
						tmpDataAVisiter.add(element);
				}
			}

			// Comparator comparator = Collections.reverseOrder();

			// Collections.sort(tmpDataAVisiter,comparator);
			// Collections.sort(tmpDataDejaVisite,comparator);
			Collections.sort(tmpDataAVisiter);
			Collections.sort(tmpDataDejaVisite);
			tmpData.clear();
			tmpData.addAll(tmpDataAVisiter);
			//Folliet : on affiche pas les clients deja visités
			//tmpData.addAll(tmpDataDejaVisite);

			return null;
		}

		@Override
		public void onPostExecute(Log nearest) {
			Debug.Log("TAG2", "findToVisitTask STOP : " + findToVisitTask);

			findToVisitTask = null;
			toVisitData.clear();
			toVisitData.addAll(tmpData);
			toVisitAdapter.notifyDataSetChanged();
			
//			for(Log log : tourneeData){
//				if(toVisitData.contains(log) && log.getClient().TYPE != null){
//					if(log.getClient().TYPE.equals("C")){
//						log.getClient().COULEUR =colorItem(getString(R.string.iconBlue),log.getClient().CODE,log.getClient().TYPE_NUMINTER);
//						//client.COULEUR = getString(R.string.iconBlue);
//						
//					}else if(log.getClient().TYPE.equals("P")){
//						//client.COULEUR = getString(R.string.iconGreen);
//						log.getClient().COULEUR =colorItem(getString(R.string.iconGreen),log.getClient().CODE,log.getClient().TYPE_NUMINTER);
//					}
//				}
//			}

			int cpt = 0;
			for(Log log : toVisitData){
				if(log.isDejaVisite()){
					cpt++;
				}
			}

			//on ajoute le total au compteur
			int total = toVisitData.size()-cpt;
			if(total >= 0){
				tv_cptToVisit.setText(Integer.toString(toVisitData.size()));
			}



			/*
			 * if(tourneeData != null && tourneeData.size()>0){ SQLRequestHelper
			 * request = new SQLRequestHelper(m_appState.m_db.conn); boolean
			 * clientOpen =
			 * request.isClientOpen(tourneeData.get(0).getClient().CODE, true,
			 * true); Debug.Log("TAG3", "client OPEN ? "+clientOpen); }
			 */

			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);

		}

	}
	private class FindNearestPOITask extends AsyncTask<Void, Void, Log> {
		@Override
		public void onPreExecute() {
			Debug.Log("FindNearestPOITask task START");

			// Required ?
			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);

		}

		@Override
		public Log doInBackground(Void... unused) {
			Log nearest = new Log();
			nearest.setDistance("-1");
			String jourPassage = SQLRequestHelper.getCodeTournee(Fonctions
					.getYYYYMMDD());

			if (surroundingClients != null
					&& surroundingClients.getClients() != null) {
				float minDistance = Float.MAX_VALUE;

				GeoPoint centerPosition = getCurrentPoint();
				ArrayList<ClientItem> currentlyDisplayedClients = new ArrayList<ClientItem>(
						surroundingClients.getClients());
				float[] results = new float[3];
				for (ClientItem item : currentlyDisplayedClients) {
					try {
						Location.distanceBetween(
								centerPosition.getLatitudeE6() / 1E6,
								centerPosition.getLongitudeE6() / 1E6, item
								.getPoint().getLatitudeE6() / 1E6, item
								.getPoint().getLongitudeE6() / 1E6,
								results);
						if (results != null && results.length > 0) {
							if (results[0] < minDistance) {
								minDistance = results[0];
								nearest.setDistance(String
										.valueOf((int) (minDistance)));

								structVisite visite = new structVisite();
								if(item.getClient().CLI_TO_VISIT_STRUCT != null )
								{
									Global.dbKDVisite.load_evt_id(visite,
											item.getClient().CODE, jourPassage,Fonctions.GetStringDanem(item.getClient().CLI_TO_VISIT_STRUCT.FIELD_EVT_ID),
											new StringBuffer());
								
								}
								else
								{
									Global.dbKDVisite.load_evt_id(visite,
											item.getClient().CODE, jourPassage,"",
											new StringBuffer());
								
								}
								nearest.setClient(item.getClient());
								nearest.setVisite(visite);
							}
						}
					} catch (Exception e) {
						Debug.StackTrace(e);
					}
				}

			}

			return nearest;
		}

		@Override
		public void onPostExecute(Log nearest) {
			Debug.Log("Find nearest task STOP : " + overlayTask);
			if (nearest != null && !nearest.getDistance().equals("-1")) {
				if (logData != null
						&& logData.size() > 0
						&& logData.get(0) != null
						&& logData.get(0).getClient().CODE.equals(nearest
								.getClient().CODE))
					logData.remove(0);
				logData.add(0, nearest);
			}
			findNearestPOITask = null;
			adapter.notifyDataSetChanged();

			if (handler != null)
				handler.sendEmptyMessage(MESSAGE_ACTION_BAR_PROGRESS_BAR);

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final Log l = (Log) parent.getAdapter().getItem(position);
		final structClient client = (structClient) l.getClient();
		Global.dbLog.Insert("Carto", "Onclick", "", "Code client:"+Fonctions.GetStringDanem(client.CODE), "", "", "");


		// Toast.makeText(this, client.NOM, Toast.LENGTH_SHORT).show();
		showMenuPopup(client);
	}

	/**
	 * Zoom level
	 */

	protected int calculateZoomLevel(int screenWidth, int areaMeters) {
		double equatorLength = 40075004; // in meters
		double widthInPixels = screenWidth;
		double metersPerPixel = equatorLength / 256;
		int zoomLevel = 1;
		while ((metersPerPixel * widthInPixels) > areaMeters) {
			metersPerPixel /= 2;
			++zoomLevel;
		}
		Debug.Log("zoom level = " + zoomLevel);
		return zoomLevel;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Debug.Log("TAG3", "on long click ");
		final Log l = (Log) parent.getAdapter().getItem(position);
		final structClient client = (structClient) l.getClient();
		updateVisite("", client, false, this);

		if (handler != null) {
			handler.sendEmptyMessage(MESSAGE_UPDATE_LIST_TOURNEE);
			Message m = new Message();
			m.what = MESSAGE_UPDATE_PI;
			m.arg1 = MESSAGE_UPDATE_PI_ARG1_FORCE;
			handler.sendMessage(m);
			handler.sendEmptyMessage(CartoMapActivity.MESSAGE_FIND_NEAREST_POI);
		}

		// MyDB.copyFile(MyDB.source,MyDB.dest);
		return true;
	}

	public static final void updateVisite(String commentaire, structClient client,
			boolean forceFerme, Context context) {
	
		String jourPassage = Fonctions.getYYYYMMDD();
		structVisite visite = new structVisite();
		if(client.CLI_TO_VISIT_STRUCT != null )
		{
			Global.dbKDVisite.load_evt_id(visite, client.CODE, jourPassage,Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID),
					new StringBuffer());
		}
		else
		{
			Global.dbKDVisite.load_evt_id(visite, client.CODE, jourPassage,"",
					new StringBuffer());
		}
		

		if (Fonctions.isEmptyOrNull(visite.DEJA_VU))
			visite.DEJA_VU = "1";
		else
			visite.DEJA_VU = "";

		if (forceFerme)
			visite.DEJA_VU = "1";

		visite.JOUR_PASSAGE = jourPassage;
		visite.CODECLI = client.CODE;
		visite.FLAG = dbKD.KDSYNCHRO_RESET;
		String contrainte = " and 1=1 " + " and "
				+ Global.dbKDVisite.FIELD_CODECLI + "='" + client.CODE + "'"
				+ " and " + Global.dbKDVisite.FIELD_JOUR_PASSAGE + "='"
				+ Fonctions.getYYYYMMDD() + "'";

		Global.dbKDVisite.clear(contrainte, new StringBuilder());
		if(client.CLI_TO_VISIT_STRUCT != null )
		{
			Global.dbKDVisite.save_evt_id(visite, client.CODE,Fonctions.GetStringDanem(client.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID), new StringBuffer());
	
		}
		else
		Global.dbKDVisite.save_evt_id(visite, client.CODE,"", new StringBuffer());

		//enregistrement cli/vu
		/*passePlat CliVu = new passePlat() ;
		CliVu.CODECLI = client.CODE;
		CliVu.DATE = Fonctions.getYYYYMMDD();
		CliVu.SOC_CODE = Preferences.getValue( context, Espresso.CODE_SOCIETE, "0");
		CliVu.CODEREP = Preferences.getValue( context, Espresso.LOGIN, "0") ;//Preferences.getValue(context, Espresso.LOGIN, "0");
		CliVu.VU = "True" ;
		CliVu.FLAG = "";
		CliVu.COMMENTAIRE = commentaire;
		
		Global.dbKDClientVu.save_rapport(CliVu);
		*/
	}
	static public void launchBilan(Context c)
	{
		/*		Intent intent = new Intent(c,StatCABYDay.class);
		Bundle b=new Bundle();

		intent.putExtras(b);
		c.startActivity(intent);
		 */
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LAUNCH_TOURNEE) {

			if (resultCode == RESULT_OK) {
				Intent i = new Intent();
				Bundle b = new Bundle();
				b.putString(Espresso.CodeClient,
						data.getExtras().getString(Espresso.CodeClient));
				i.putExtras(b);
				setResult(RESULT_OK, i);
				finish();
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		if(parent.getId() == R.id.s_1){
			zoneSpinnerPosition = position;
			s_1.setSelection(zoneSpinnerPosition);
			//tof, dev50.2, desactivation fonction autre que vendeur
			if(m_btech==false)
			   Preferences.setValue(this,Espresso.CODE_TOURNEE,""+zoneSpinnerPosition);
		}

		if(parent.getId() == R.id.sAVisiter){
			zoneSpinnerPositionAVisiter = position;
			sAVisiter.setSelection(zoneSpinnerPositionAVisiter);
		}

		Debug.Log("requete", "spinner position : " + zoneSpinnerPosition);
		handler.sendEmptyMessage(MESSAGE_UPDATE_LIST_TOURNEE);


		if (isDemo) {
			Message m = new Message();
			m.what = MESSAGE_UPDATE_PI;
			m.arg1 = MESSAGE_UPDATE_PI_ARG1_FORCE;
			handler.sendMessage(m);
		} else
			handler.sendEmptyMessage(MESSAGE_UPDATE_PI);
		}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}

	void hideKeyboard()
	{
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(actvFilter.getWindowToken(), 0);
	}
    public void TypeLogin()
    {
    	structlistLogin rep = null;
		dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
		rep=new structlistLogin();

		login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) )
		{
			m_btech=true;
		}
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur) )
		{
			m_bvendeur=true;
		}
		m_streplogin=Fonctions.GetStringDanem(rep.LOGIN);

	}
    public String colorItem(String colorDefaut,String codeclient,String typeinter)
    {
    	String stColorInterne=colorDefaut;
    	//String stnuminterne="";
    	
    	if(m_btech==true)
    	{
    	//	dbTournee db=new dbTournee(getDB());
    		//stnuminterne=db.CodeNum_interne(codeclient);
    		//TableHistoInter hi=new TableHistoInter(getDB());
    		//typeinter=hi.LoadType_inter(codeclient,stnuminterne);
    		
    		if(typeinter.equals("DI") || typeinter.equals("DIU"))
    		{
    			stColorInterne=getString(R.string.iconRed);
    		}
    		if(typeinter.equals("DINS") || typeinter.equals("DECH") || typeinter.equals("DREP"))
    		{
    			stColorInterne=getString(R.string.iconGreen);
    		}
    		if(typeinter.equals("XVIS") )
    		{
    			stColorInterne=getString(R.string.iconOrange);
    		}
    		
    		
    	}
    	
    	return stColorInterne;
    	
    }

    public  String LibInter(String codeclient, boolean btech, MyDB myDB)
    {
    	String libinter="";
    	
    	String stnuminterne="";
    	
    	if(btech==true)
    	{
    		dbTournee db=new dbTournee(myDB);
    		stnuminterne=db.CodeNum_interne(codeclient);
    		TableHistoInter hi=new TableHistoInter(myDB);
    		libinter=hi.LoadInfo_inter(codeclient,stnuminterne).trim();
    		if(!hi.LoadInfo_Symptome(codeclient,stnuminterne).equals(""))
    		 libinter+=" - "+hi.LoadInfo_Symptome(codeclient,stnuminterne).trim();
    		
    		
    	}  	
    	return libinter;
    	
    }
    
    
}
