package com.menadinteractive.histo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.printmodels.BluetoothConnectionInsecureExample;
import com.menadinteractive.printmodels.Z420ModelPretMachine;
import com.menadinteractive.printmodels.Z420ModelRetourMachine;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableHistoInter;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD451RetourMachineclient;
import com.menadinteractive.segafredo.db.dbKD452ComptageMachineclient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.materielclient.ListingMaterielClient;

public class HistoInterActivity extends BaseActivity implements OnClickListener{
	
	ArrayList<Bundle> cli;
 
	Handler handler;
 
	myListView lv;;
 
 
	String m_codeclient;
	String m_numserie;
	String m_typ_inter="";
	
	Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_histointer);	
		mContext = this;
		Bundle bundle = this.getIntent().getExtras();
		m_codeclient = bundle.getString("codeclient");
		m_numserie = bundle.getString("numserie");
		m_typ_inter= bundle.getString("typ_inter");
		
		
		initGUI();
		setListeners();
	}

	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
	 
		handler =getHandler();
		DispInfoCli();
		
		PopulateList();
	 
	}
	
	void setListeners()
	{
		 
		
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
				 
					String infofininter=cli.get(id).getString(TableHistoInter.FIELD_INFOFININTER);
				 
					promptText("Info de fin d'intervention", infofininter, false);
				
					break;
				
				}
			}
		};
		return h;
	}
 
	private void PopulateList() {
		
		TableHistoInter hi=new TableHistoInter(getDB());
		cli=hi.getHistoFilters(m_codeclient, "",m_numserie);
		ArrayList<assoc> assocs =new ArrayList<assoc>();
	 
 
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvLibart,TableHistoInter.FIELD_LIB));
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvTypeInter, TableHistoInter.FIELD_TYPINTER));		
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvTec, TableHistoInter.FIELD_CODTEC));	
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvDateCreat, TableHistoInter.FIELD_DATECREAINTER));	
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvDateInter, TableHistoInter.FIELD_DATEINTER));
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvDateFin, TableHistoInter.FIELD_DATEFININTER));
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvInfoInter, TableHistoInter.FIELD_INFOINTER));
 
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvMachine, TableHistoInter.FIELD_DGN));		
		assocs.add(new  assoc(myListView.TYPE_TEXTVIEW,R.id.tvNumserie, TableHistoInter.FIELD_NUM_SERIE));	

		assocs.add(new  assoc(myListView.TYPE_RELATIVELAYOUT,R.id.rl_root, "","layoutcolor"));
 		
		lv.attachValues(R.layout.item_list_histointer, cli,assocs,handler);
		
	}
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if( requestCode== LAUNCH_DEMANDEINTERVENTION)
		{
			if (resultCode == Activity.RESULT_OK )
			{
				Fonctions.FurtiveMessageBox(this, "Demande d\'intervention envoyée.");
			}
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if(Fonctions.GetStringDanem(m_numserie).equals(""))
		{
			addMenu(menu, R.string.commande_askintervention,
					android.R.drawable.ic_menu_add);
				
		}
		
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
				 
				case R.string.commande_askintervention:
					//Intent intent = new Intent(mContext, InterventionActivity.class);
					//startActivity(intent);

					// check si exclure_mat est à zéro
					structClient cli=new structClient();
					Global.dbClient.getClient(m_codeclient, cli, new StringBuilder());

					if (cli.EXCLURE_MAT.equals("N"))
					{
						launchDemandeIntervention(m_codeclient, m_typ_inter, LAUNCH_DEMANDEINTERVENTION);


					} else {
						Toast.makeText(this,"La demande d\'intervention n\'est pas disponible pour ce client", Toast.LENGTH_SHORT).show();


					}

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
