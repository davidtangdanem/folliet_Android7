package com.menadinteractive.transfertstock;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.menadinteractive.commande.Printanem;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire;
import com.menadinteractive.segafredo.db.dbKD544LinMvtsStock;
import com.menadinteractive.segafredo.db.dbSiteProduit;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.tasks.task_sendReapproWSData;


public class TransferstockActivity extends BaseActivity implements OnItemSelectedListener {
	private listAdapter m_adapter;

	final int LAUNCH_SAISIEQTE = 44;
	ListView myListView;
	int lvPosition;
	String m_soccode;
	int top = 0;
	int index = 0;
	ArrayList<Bundle> idFam = null;// les id qui servent a retrouver les pays
	String m_stFam = "" ;	//Filtre Fam (voir spinner)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readecharg); 

		idFam = new ArrayList<Bundle>();
		m_soccode=Preferences.getValue(this, Espresso.CODE_SOCIETE, "0");

		initGUI();
		initListeners();
		fillFamille("");

	}

	void initGUI() {
		myListView = (ListView) findViewById(R.id.lv_4);
		myListView.setTextFilterEnabled(true);

	
		PopulateList();
	}

	void initListeners() {
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				index = myListView.getFirstVisiblePosition();
				View vw = myListView.getChildAt(0);
				top = (vw == null) ? 0 : vw.getTop();
				
				String valueTag = arg1.getTag().toString();
			
				launchQtePrix(valueTag);
				return;
			}
		});
		
	
		Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);
		spinner.setOnItemSelectedListener(this);

	}

	private void PopulateList() {

		ArrayList<dbSiteProduit.structArt> colNames = new ArrayList<dbSiteProduit.structArt>();

		this.m_adapter = new listAdapter(this, R.layout.item_transfertstock, colNames);

		myListView.setAdapter(this.m_adapter);
		ArrayList<dbSiteProduit.structArt> arts =null;
		// Pas besoin
	/*	
		if (m_stFam.equals(getString(R.string.inventaire_resteainventorier))==true)
			arts=Global.dbProduit.getProduitsAInventorier(
					m_soccode);
			
		else
			if (m_stFam.equals(getString(R.string.inventaire_anomalies))==true)
				arts=Global.dbProduit.getProduitsInventaireAnomalies(
						m_soccode);
			else
		*/		
		
		arts=Global.dbProduit.getProduits(
						m_soccode, m_stFam, "" );

		for (int v = 0; v < arts.size(); v++)
			colNames.add(arts.get(v));

	}

	private class listAdapter extends ArrayAdapter<dbSiteProduit.structArt> {

		private ArrayList<dbSiteProduit.structArt> items;

		public listAdapter(Context context, int textViewResourceId,
				ArrayList<dbSiteProduit.structArt> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_transfertstock, null);
			}

			dbSiteProduit.structArt o = items.get(position);

			if (o != null) {

	
				//Tof: gestion des photos par webview
				WebView webView =(WebView)v
						.findViewById(R.id.webViewPhoto);
				
				webView.setClickable(false);
				webView.setLongClickable(false);
				webView.setFocusable(false);
				webView.setFocusableInTouchMode(false);
				webView.setScrollContainer(false);
				webView.setBackgroundColor(0);
				webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				webView.getSettings().setAppCacheEnabled(true);
				webView.getSettings().setCacheMode(	android.webkit.WebSettings.LOAD_NORMAL);
				//test avec les image daphiliom
				//String idx =  String.format("%03d", position) ;
				//String addr = "http://daphiliom.dyndns.org/gps/images/NEWICO/"+idx+"/close_1.png" ;
				
				//http://sd3.danem.fr/negoscloud/docs_customers/ferrero/docs_server/documents/prod/<codeprod>/thumb/<photoname>.jpg
				String addr = "http://sd3.danem.fr/negoscloud/docs_customers/ferrero/docs_server/documents/prod/"+o.CODART+"/thumb/"+o.PHOTONAME+".jpg" ;
				
				//webView.loadUrl("http://www.alpha-geek.fr/public/Google/blue_dot_circle.png");
				String data = "<html><head><title>"
				+ "png"
				+ "</title><meta name=\"viewport\"\"content=\"initial-scale=1.0; maximum-scale=1.0; user-scalable=0 \" /></head>";
				data = data + "<body><center><img src=\""
				+ addr + "\" /></center></body></html>";
				
/*				webView.getSettings().setLoadWithOverviewMode(true);
				webView.getSettings().setUseWideViewPort(true);
				webView.getSettings().setBuiltInZoomControls(true);
				webView.getSettings().setAppCacheEnabled(true);
				webView.getSettings().setCacheMode(	android.webkit.WebSettings.LOAD_NORMAL);
				webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 				
				webView.loadUrl(addr);
				*/
				webView.loadData(data, "text/html", null);
				
				//RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl_root);
				LinearLayout rl = (LinearLayout) v.findViewById(R.id.rl_root);
				LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll_saisie);
				TextView tvCode = (TextView) v.findViewById(R.id.textCode); 
				tvCode.setText(o.CODART+" - PCB= "+o.PCB);
				
				TextView tvLbl = (TextView) v.findViewById(R.id.textLbl); 
				tvLbl.setText(o.NOMART1);
				
				
			
				TextView tvReappro = (TextView) v.findViewById(R.id.textViewReapproVal);
				TextView tvDechargement = (TextView) v.findViewById(R.id.textViewDechargVal);
				
				
				
				TextView tvQteTheo = (TextView) v.findViewById(R.id.textViewQteTheoVal);
				TextView tvTxTva = (TextView) v.findViewById(R.id.textViewTxPoidsVal);
				
				rl.setBackgroundColor(Color.TRANSPARENT);
				String valueTag=o.CODART;
				
				dbKD544LinMvtsStock.structPassePlat lin = new dbKD544LinMvtsStock.structPassePlat();
				if (Global.dbKDLinTransfert.load(lin, o.CODART, 
						new StringBuffer(),
						Preferences.getValue(TransferstockActivity.this, Espresso.DEPOT,"0"),"1","1",
						Preferences.getValue(TransferstockActivity.this, Espresso.LOGIN,"0"),
						Preferences.getValue(TransferstockActivity.this, Espresso.CODE_SOCIETE,"0"))) {
					if (lin.FIELD_LIGNEMVTS_QTEREA.equals(""))
						rl.setBackgroundColor(Color.YELLOW);
					else
						rl.setBackgroundColor(Color.GREEN);
					
					if (lin.FIELD_LIGNEMVTS_QTEDECH.equals(""))
						rl.setBackgroundColor(Color.YELLOW);
					else
						rl.setBackgroundColor(Color.GREEN);
					
					ll.setVisibility(View.VISIBLE);
				
					tvReappro.setText(String.valueOf(lin.FIELD_LIGNEMVTS_QTEREA));
					tvDechargement.setText(String.valueOf(lin.FIELD_LIGNEMVTS_QTEDECH));
					//Mis � jour du stock
					String m_qteTheo="";
					m_qteTheo=Global.dbKDLinTransfert.StockTheorique(TransferstockActivity.this,o.CODART,String.valueOf(lin.FIELD_LIGNEMVTS_QTEREA),String.valueOf(lin.FIELD_LIGNEMVTS_QTEDECH));
					
					tvQteTheo.setText(m_qteTheo);
					
					valueTag+=";"+lin.FIELD_LIGNEMVTS_QTEREA+";"+lin.FIELD_LIGNEMVTS_QTEDECH+";"+lin.FIELD_LIGNEMVTS_QTETHEO;
				} else
					ll.setVisibility(View.GONE);
				
				rl.setTag(valueTag);
			}
			return v;
		}

	}

	

	void launchQtePrix(String valueTag) {
		dbSiteProduit.structArt art = new dbSiteProduit.structArt();
		String codeart=Fonctions.GiveFld(valueTag, 0, ";", true);
		String qtereappro=Fonctions.GiveFld(valueTag, 1, ";", false);
		String qtedechargement=Fonctions.GiveFld(valueTag, 2, ";", false);
		String qteTheo=Fonctions.GiveFld(valueTag, 3, ";", false);
		
		if (Global.dbProduit.getProduit(codeart, art, new StringBuilder())
				) {

			
			Bundle b = new Bundle();
			b.putString("codeart", codeart);
			b.putString("lblart", art.NOMART1);
			b.putString("prix", art.PV_CONS);
			b.putString("reappro", qtereappro);
			b.putString("dechargement", qtedechargement);
		 	b.putString("qtetheo", qteTheo);	
			b.putString("photoname", art.PHOTONAME);
			b.putString("webfiche", art.WEBFICHE);
			
			Intent intent = new Intent(this,
			TransfetstockInput.class); intent.putExtras(b);
			startActivityForResult(intent, LAUNCH_SAISIEQTE);
			 
			
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == LAUNCH_SAISIEQTE) {
		
			if (resultCode == Activity.RESULT_OK) {

				Bundle b = data.getExtras();

				dbKD544LinMvtsStock.structPassePlat lin = new dbKD544LinMvtsStock.structPassePlat();
				lin.FIELD_LIGNEMVTS_SOCCODE=m_soccode;
				lin.FIELD_LIGNEMVTS_DATE=Fonctions.getYYYYMMDDhhmmss();
				lin.FIELD_LIGNEMVTS_DESIGNATION=getBundleValue(b, "lblart");
				lin.FIELD_LIGNEMVTS_PROCODE= getBundleValue(b, "codeart");
				lin.FIELD_LIGNEMVTS_QTEREA=getBundleValue(b, "reappro");
				lin.FIELD_LIGNEMVTS_QTEDECH=getBundleValue(b, "dechargement");
				
	/*			dbKD543LinInventaire.structPassePlat pp = new dbKD543LinInventaire.structPassePlat();
				Global.dbKDLinInv.load(pp, lin.FIELD_LIGNEMVTS_PROCODE, new StringBuffer(),
						Preferences.getValue(this, Espresso.DEPOT,"0"),
						Preferences.getValue(this, Espresso.LOGIN,"0"),
						Preferences.getValue(this, Espresso.CODE_SOCIETE,"0"));
				
				int qtetheo=Fonctions.convertToInt( pp.FIELD_LIGNEINV_QTETHEO)+Fonctions.convertToInt( lin.FIELD_LIGNEMVTS_QTEREA)-Fonctions.convertToInt( lin.FIELD_LIGNEMVTS_QTEDECH);
				
				pp.FIELD_LIGNEINV_QTETHEO=qtetheo+"";
				
				Global.dbKDLinInv.save(pp, lin.FIELD_LIGNEMVTS_PROCODE, new StringBuffer());
				*/
				lin.FIELD_LIGNEMVTS_QTETHEO=getBundleValue(b, "qtetheo");
				
				lin.FIELD_LIGNEMVTS_REPCODE=Preferences.getValue(this, Espresso.LOGIN, "0");
				lin.FIELD_LIGNEMVTS_DEPOTDST="1";
				lin.FIELD_LIGNEMVTS_DEPOTSRC=Preferences.getValue(this, Espresso.DEPOT, "1");
				lin.FIELD_LIGNEMVTS_MVTS_TYPE="1";
		
				dbSiteProduit.structArt art = new dbSiteProduit.structArt();
				if (Global.dbProduit.getProduit(lin.FIELD_LIGNEMVTS_PROCODE, art,
						new StringBuilder())) {
					lin.FIELD_LIGNEMVTS_UV = art.PCB;
					lin.FIELD_LIGNEMVTS_CODABAR=art.EAN;
					
					float fpoids=Fonctions.convertToFloat(art.POIDSBRUT)*
							Fonctions.convertToFloat(art.PCB)*
							Fonctions.convertToFloat(lin.FIELD_LIGNEMVTS_QTEREA);
					
					if (Global.dbKDLinTransfert.save(lin, lin.FIELD_LIGNEMVTS_PROCODE,
							new StringBuffer())) {

					} else {

					}
					
					PopulateList();
					myListView.setSelectionFromTop(index, top);
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
		addMenu(menu, R.string.transfertstock_quit, android.R.drawable.ic_menu_close_clear_cancel);
		addMenu(menu, R.string.transfertstock_connexion,android.R.drawable.ic_dialog_dialer);
	//	addMenu(menu, R.string.inventaire_print,android.R.drawable.ic_partial_secure);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

	
		case R.string.transfertstock_quit:
			save();
			finish();
		
			return true;
		case R.string.inventaire_print:
			launchPrinting();
		
			return true;
		
		case R.string.transfertstock_connexion:
			save();
			
			MessageYesNo(getString(R.string.commande_valider_msg),
					R.string.commande_valider);

			
	
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	void launchPrinting() {

	
	 
	}

	

	boolean save() {
		try {
		
			
				
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	

	public void MessageYesNo(String message, int type) {
		final int m_type = type;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(getString(android.R.string.yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								switch (m_type) {
								
								case R.string.commande_valider:
									if (save()==false) return;
									
									//Global.dbKDLinTransfert.UpdateQteTheo(TransferstockActivity.this);
									// on envoi la reappro tt de suite
									task_sendReapproWSData wsCde = new task_sendReapproWSData(
											m_appState);
									wsCde.execute();

								    setResult(Activity.RESULT_OK, null);
								    
									finish();
									break;
								case R.string.commande_annuler:
									
									setResult(Activity.RESULT_CANCELED, null);
									finish();
									break;
								}

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
			//if (Global.dbParam.getRecordsFiltreAllSoc(Global.dbParam.PARAM_FAM1,this.idFam, "1") == true) {
			if (Global.dbParam.getFamActives( this.idFam) == true) {
/*
				//on inser la famille 'Anomalies'
				Bundle bundle = new Bundle();
				bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.inventaire_anomalies));
				bundle.putString(Global.dbParam.FLD_PARAM_LBL, getString(R.string.inventaire_anomalies));
				bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.inventaire_anomalies));
				bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.inventaire_anomalies));
				idFam.add(0,bundle);
				
				//on inser la famille 'reste � inventorier'
				 bundle = new Bundle();
				bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.inventaire_resteainventorier));
				bundle.putString(Global.dbParam.FLD_PARAM_LBL, getString(R.string.inventaire_resteainventorier));
				bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.inventaire_resteainventorier));
				bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.inventaire_resteainventorier));
				idFam.add(0,bundle);
				
				*/
				
				int pos = -1;
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

		} catch (Exception ex) {

		}

	}
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	m_stFam = idFam.get(pos).getString(
				Global.dbParam.FLD_PARAM_CODEREC);
    	//m_stFam = "" ;		//6546876546: DEBUGAGE
    	PopulateList() ;
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
