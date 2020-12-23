package com.menadinteractive.segafredo.rapportactivite;

import java.util.ArrayList;

import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableHistoInter;
import com.menadinteractive.segafredo.db.TableHistoTodo;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbParam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class rapportActivity extends BaseActivity implements OnClickListener{
	
	ArrayList<Bundle> cli;
	 
	Handler handler;
 
	myListView lv;;

	String m_codeclient;
	
	Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historapportact);
		mContext = this;
		Bundle bundle = this.getIntent().getExtras();
		m_codeclient = bundle.getString("codeclient");

		initGUI();
		setListeners();
	}

	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
	 
		handler =getHandler();
		DispInfoCli();
		
		PopulateList();

		initItemClick();
	}
	
	void setListeners()
	{
		 
		
	}

	void initItemClick(){

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long id) {

				//Log.e("idItem",""+position);
				//String val =(View) arg1.getText();

				Bundle data = (Bundle) arg0.getItemAtPosition(position);

				String event = (String) data.get("LIB_EVENEMENT");

				String[] parts = event.split(" - ");
				String idEvent = parts[0];
				String libEvent = parts[1];

				dbParam dbParam=new dbParam(getDB());
				//ArrayList<String> dataEvent = dbParam.getDetailsByEvent(idEvent);
				ArrayList<JSONArray> dataEvent = dbParam.getDetailsByEvent(idEvent);

				Log.e("idEvent",""+idEvent);
				Log.e("dataEvent",""+dataEvent);

				String detailEvent = "";
				String reponseEvent = "";
				String questionEvent = "";

				//TODO affichage des données dans une Dialog
				if (dataEvent != null) {
					if (!dataEvent.isEmpty()) {
						for (int i = 0; i < dataEvent.size(); i++) {

							JSONObject infosEvent = null;
							try {

								if (i > 0) {
									detailEvent += "<br />";
								}

								infosEvent = dataEvent.get(i).getJSONObject(0);
								questionEvent = infosEvent.optString(dbParam.FLD_PARAM_LBL);
								reponseEvent = infosEvent.optString(dbParam.FLD_PARAM_COMMENT);

								detailEvent += "<b>"+ questionEvent +"</b>" + " <br /> " + reponseEvent + "<br />";

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					} else {
					   detailEvent = "Aucun détail sur le rapport";
					}

					Log.e("detailEvent",""+detailEvent);
			  } else {

					detailEvent = "Aucun détail sur le rapport";
			  }

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						rapportActivity.this);

				LayoutInflater inflater = rapportActivity.this.getLayoutInflater();
				final View dialogView = inflater.inflate(R.layout.details_rapport, null);
				alertDialogBuilder.setView(dialogView);

				Spanned detailEventSpan = Html.fromHtml(detailEvent);
				// set t0itle
				alertDialogBuilder.setTitle("Détails du rapport");

				// set dialog message
				alertDialogBuilder
						.setMessage(detailEventSpan)
						.setCancelable(false)
						.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {

							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				alertDialog.show();
				alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

				//Dialog d = alertDialog.setView()).create();

				/*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				lp.copyFrom(alertDialog.getWindow().getAttributes());
				lp.width = WindowManager.LayoutParams.MATCH_PARENT;
				lp.height = WindowManager.LayoutParams.MATCH_PARENT;
				alertDialog.show();
				alertDialog.getWindow().setAttributes(lp);*/

				// show it
				//alertDialog.show();
			};

		});
	}


	 
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
				case R.id.listView1:
				case R.id.iv2:
					int  id=bGet.getInt("position");
				 
					//String infofininter=cli.get(id).getString(TableHistoInter.FIELD_INFOFININTER);
				 
					//promptText("Info de fin d'intervention", infofininter, false);
				
					break;
				
				}
			}
		};
		return h;
	}
 
	private void PopulateList() {
		
		TableHistoTodo hi=new TableHistoTodo(getDB());
		cli=hi.getHistoFilters(m_codeclient, "");
		ArrayList<assoc> assocs =new ArrayList<assoc>();

		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvTache,TableHistoTodo.FIELD_COD_COLL));
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvLibart,TableHistoTodo.FIELD_LIB_EVENEMENT));
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvDateCreat, TableHistoTodo.FIELD_DATE_FIN));	
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvInfoInter, TableHistoTodo.FIELD_DESCRIPTION));
		
		assocs.add(new  assoc(myListView.TYPE_RELATIVELAYOUT,R.id.rl_root, "","layoutcolor"));
 		
		lv.attachValues(R.layout.item_list_historapport, cli,assocs,handler);
		
	}
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	 
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		addMenu(menu, R.string.commande_askrapport,
				android.R.drawable.ic_menu_add);
		
		addMenu(menu, R.string.commande_quitter,
				android.R.drawable.ic_menu_close_clear_cancel);
		
	 		 
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
				StringBuffer buff = new StringBuffer();
				ArrayList<String> ValueMessage=new ArrayList();
				switch (item.getItemId()) {
				 
				case R.string.commande_askrapport:
					commandeActivity.launchRapportA(rapportActivity.this,m_codeclient,LAUNCH_RAPPORT);
					//launchDemandeIntervention(m_codeclient,LAUNCH_DEMANDEINTERVENTION);

					return true;
				case R.string.commande_quitter:
				  returnOK();
				default:
					return super.onOptionsItemSelected(item);
				}
	}
 
	void DispInfoCli()
	{
		structClient cli=new structClient();
		Global.dbClient.getClient(m_codeclient, cli, new StringBuilder());
		float totalEncours = Fonctions.GetStringToFloatDanem(cli.MONTANTTOTALENCOURS);
		
		TextView tvCode,tvEnseigne;
		TextView tvVille;
		TextView tvCP;
		TextView tvMail;
		TextView tvTel;
		TextView tvRS;
		TextView tvAdr1;
		TextView textTitreTarif;
		TextView tvTarif;
		TextView textViewNote;
		
		TextView tvAdr2,tvCliBloque,tvEncoursClient;

		tvMail = (TextView) findViewById(R.id.textViewMail);
		tvRS = (TextView) findViewById(R.id.textViewRS);
		tvCP = (TextView) findViewById(R.id.textViewCP);
		tvTel = (TextView) findViewById(R.id.textViewTel);
		tvVille = (TextView) findViewById(R.id.textViewVille);
		tvAdr1 = (TextView) findViewById(R.id.textViewAdr1);
		tvAdr2 = (TextView) findViewById(R.id.textViewAdr2);
		tvCode = (TextView) findViewById(R.id.textViewCode);
		tvCliBloque = (TextView) findViewById(R.id.tvCliBloque);
		tvEnseigne = (TextView) findViewById(R.id.textViewEnseigne);
		tvEncoursClient = (TextView) findViewById(R.id.tvEncoursClient);
		textTitreTarif= (TextView) findViewById(R.id.textTitreTarif);
		tvTarif = (TextView) findViewById(R.id.textViewTarif);
		textViewNote= (TextView) findViewById(R.id.textViewNote);

		
		tvRS.setText(cli.NOM.trim());
		tvMail.setText(cli.EMAIL);
		tvCP.setText(cli.CP);
		tvTel.setText(cli.TEL1);
		tvVille.setText(cli.VILLE);
		tvCode.setText(cli.CODE);
		tvEnseigne.setText(cli.ENSEIGNE.trim());
		tvAdr1.setText(cli.ADR1);
		tvAdr2.setText(cli.ADR2);
		tvEncoursClient.setText(Fonctions.GetFloatToStringFormatDanem(totalEncours, "0.00"));
		tvTarif.setText(Fonctions.GetStringDanem(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_LISTETARIF,cli.CODETRF).trim()));
		textViewNote.setText("Note : " + Fonctions.GetStringDanem(cli.CLI_SAV));
		
		
	}

}
