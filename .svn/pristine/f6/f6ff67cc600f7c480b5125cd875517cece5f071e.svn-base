package com.menadinteractive.segafredo.rapportactivite;

import java.util.ArrayList;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.printmodels.BluetoothConnectionInsecureExample;
import com.menadinteractive.printmodels.Z420ModelPretMachine;
import com.menadinteractive.printmodels.Z420ModelRetourMachine;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbCliToVisit;
import com.menadinteractive.segafredo.db.dbKD451RetourMachineclient;
import com.menadinteractive.segafredo.db.dbKD452ComptageMachineclient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.materielclient.CommentaireRetourMaterielClient;
import com.menadinteractive.segafredo.materielclient.ListingMaterielClient;
import com.menadinteractive.segafredo.materielclient.PretCategorieMaterielClient;
import com.menadinteractive.segafredo.plugins.Espresso;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class rapporttodo extends BaseActivity implements OnClickListener{
	ArrayList<Bundle> cli;
	ArrayList<Bundle> pret;
	
	Handler handler;
	ImageButton ibPrintRetours,ibPrintPrets,ibAddPret;
 
	myListView lv;;
	Handler hPrintResultRet,hPrintResultPret;
	private ProgressDialog m_ProgressDialog = null; 
	boolean m_problemPrinter=false;//si on declare un probleme d'imprimante on laisse passer

	String m_codeclient;
	String m_numfab;
	String m_typedoc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rapporttodo);		
		Bundle bundle = this.getIntent().getExtras();
		m_codeclient = bundle.getString(Espresso.CODE_CLIENT);
		m_typedoc =  bundle.getString("typedoc");
		
		
		initGUI();
		setListeners();
	}

	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
		lv.setTextFilterEnabled(true);
		registerForContextMenu(lv);

		
 		ibPrintRetours= (ImageButton) findViewById(R.id.ibPrintRetours);
 		ibPrintPrets= (ImageButton) findViewById(R.id.ibPrintPrets);
 		ibAddPret= (ImageButton) findViewById(R.id.ibAddPrets);
 		
 		ibPrintRetours.setVisibility(View.GONE);
		hPrintResultRet=getHandlerPrintRet();
		hPrintResultPret=getHandlerPrintPret();
		handler =getHandler();
		
		DispInfoCli();
		
		PopulateList();
		
	}
	
	void setListeners()
	{
		ibPrintRetours.setOnClickListener(this);
		ibPrintPrets.setOnClickListener(this);
		ibAddPret.setOnClickListener(this);
		
	}
	Handler getHandlerPrintRet() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (m_ProgressDialog!=null) m_ProgressDialog.dismiss();
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				if (msg.what!=BluetoothConnectionInsecureExample.ERRORMSG_OK)
				{
					
				}
				else
				{
					
					
					//finish();
				}
			}
		};
		return h;
	}
	Handler getHandlerPrintPret() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (m_ProgressDialog!=null) m_ProgressDialog.dismiss();
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				if (msg.what!=BluetoothConnectionInsecureExample.ERRORMSG_OK)
				{
					
				}
				else
				{
				}
			}
		};
		return h;
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
					String ref=cli.get(id).getString(Global.dbCliToVisit.FIELD_REF);
					String evt_id=cli.get(id).getString(Global.dbCliToVisit.FIELD_EVT_ID);
					String code_evt=cli.get(id).getString(Global.dbCliToVisit.FIELD_CODE_EVT);
					if(Fonctions.GetStringDanem(ref).equals(""))
					  launchrapport(m_codeclient, code_evt,evt_id);
					
					
					
					
					
				}

			}
		};
		return h;
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		//menu.add(0, 0, 0, "Historique des Interventions");
		//menu.add(0, 1, 0,  "Machine plus chez le client");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		//Bundle bu=tabVal.get((int)info.id);
		
		int  id=info.position;
		 
		String numserie=cli.get(id).getString(TableMaterielClient.FIELD_NUMSERIEFAB);
		String codemateriel=cli.get(id).getString(TableMaterielClient.FIELD_CODEART);
		String designation=cli.get(id).getString(TableMaterielClient.FIELD_LIBART);
		String codeclient=cli.get(id).getString(TableMaterielClient.FIELD_CODECLIENT);
		String par_ref=cli.get(id).getString(TableMaterielClient.FIELD_REF);
	
		switch (item.getItemId()) {
		case 0:
			launchHistoInter(m_codeclient,numserie,LAUNCH_HISTOINTER);

			return true;
		case 1:
			boolean bres=true;
			TableSouches souche=new TableSouches(m_appState.m_db,rapporttodo.this);
			if (souche.get(TableSouches.TYPEDOC_RETOURMACHINE, getLogin()).equals(""))
			{
				promptText(getString(R.string.retour_mat_riel), getString(R.string.vous_n_tes_pas_autoris_faire_des_retours_mat_riel), false);
				bres=false;
			}
			
			dbKD451RetourMachineclient ret=new dbKD451RetourMachineclient(m_appState.m_db);
			if (ret.getPieceNotSend(m_codeclient)!=null)
			{
				promptText(getString(R.string.nouveau_retour_impossible), getString(R.string.vous_avez_d_j_un_retour_envoyer_avant_d_en_faire_un_nouveau), false);
				bres=false;
			}
			
			return true;
		
		default:
			return super.onContextItemSelected(item);
		}
	}	 
	
	void launchrapport(String codecli,String code_evt,String evt_id)
	{
		Intent i = new Intent(this, rapportactivite.class);
		
		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);
		b.putString("code_evt",  code_evt);
		b.putString("evt_id",  evt_id);
		b.putString("numInterne",  evt_id);
		b.putBoolean("rapporttodo",true);
		
		i.putExtras(b);
		startActivityForResult(i,555555);
	}
	private void PopulateList() {
		
		cli=Global.dbCliToVisit.getTodoClientFilters(m_codeclient);
		ArrayList<assoc> assocs =new ArrayList<assoc>();
		
		assocs.add(new  assoc(0,R.id.tvAfaire,dbCliToVisit.FIELD_LBL));
		assocs.add(new  assoc(0,R.id.tvDescription, dbCliToVisit.FIELD_DESCRIPTION));		
		assocs.add(new  assoc(myListView.TYPE_RELATIVELAYOUT,R.id.iv2R, "","layoutcolor"));
 		lv.attachValues(R.layout.item_list_todo, cli,assocs,handler);
		
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==ibPrintRetours)
		{
			dbKD451RetourMachineclient ret=new dbKD451RetourMachineclient(m_appState.m_db);
			int counret=  ret.Count(m_codeclient) ;
			if (counret>0)
				launchPrintingRetour();
			else
			{
				promptText(getString(R.string.impression_des_retours_mat_riels), getString(R.string.aucun_retour_valid_), false);
			}
			return;
 		}
		if (v==ibPrintPrets)
		{
			dbKD452ComptageMachineclient pret=new dbKD452ComptageMachineclient(m_appState.m_db);
			int counret=  pret.Count(m_codeclient) ;
			if (counret>0)
				launchPrintingPret();
			else
			{
				promptText(getString(R.string.impression_des_pr_ts_mat_riels), getString(R.string.aucun_pr_t_valid_), false);
			}
			return;
 		}		
		if (v==ibAddPret)
		{
		 
			dbKD452ComptageMachineclient pret=new dbKD452ComptageMachineclient(m_appState.m_db);
			if (pret.getPieceNotSend(m_codeclient)!=null)
			{
				promptText( "Modification impossible", "Le prêt est déjà validé", false);
				return;
			}
			
			launchMenuPretCategorieMaterielClient(m_codeclient,"","","");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//on revient de la cde, si on a pris une commande on ne peut pas annuler le prospect
		//on recharge la fiche aussi car peut �tre modifiŽe dans la cde
	//	if(requestCode==1 )
		{
			PopulateList();
		}
		
		//
		
		hideKeyb();

	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		addMenu(menu, R.string.commande_quitter,
				android.R.drawable.ic_menu_close_clear_cancel);
		//addMenu(menu, R.string.pret, android.R.drawable.ic_menu_add);
		
		addMenu(menu, R.string.commande_suivant,
				R.drawable.right);
		
		 
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
				StringBuffer buff = new StringBuffer();
				ArrayList<String> ValueMessage=new ArrayList();
				switch (item.getItemId()) {
			
				case R.string.commande_quitter:
					returnCancel();
				case R.string.commande_suivant:
					Intent intent = new Intent();
					intent.putExtra("closeactivity", m_typedoc);
					setResult(RESULT_OK, intent);			
					finish();
				
				default:
					return super.onOptionsItemSelected(item);
				}
	}
	@Override
	public void onBackPressed() {
		boolean ret=isAllOperationOk();
		if (ret) returnOK();
	}
	boolean isAllOperationOk()
	{
		
		dbKD451RetourMachineclient ret=new dbKD451RetourMachineclient(m_appState.m_db);
		if(ret.Count()>0)
		{
			//setPrintRetourOk();
			return true;
		}
		
		
		
		return true;
		//return false;
	}
	void launchMenuPretCategorieMaterielClient(String contactcli,String SZ,String numserie,String codearticle )
	{
		Intent intent = new Intent(this,PretCategorieMaterielClient.class);
		Bundle b=new Bundle();
		b.putString("numSZ",SZ);
		b.putString("numSerie",numserie);
		b.putString("codeclient",m_codeclient);
		b.putString("codearticle",codearticle);
		
		intent.putExtras(b);
		startActivityForResult(intent,1);
	}
	void launchPrintingRetour() {
	 
		m_ProgressDialog=ProgressDialog.show(this, getString(R.string.communication_avec_l_imprimante), getString(R.string.patientez_));

		String mac=getPrinterMacAddress();
		   BluetoothConnectionInsecureExample example = new BluetoothConnectionInsecureExample(hPrintResultRet);
              Z420ModelRetourMachine z=new Z420ModelRetourMachine(this);
              
       
            String   zplData=z.getRetourEx( m_codeclient );
	        example.sendZplOverBluetooth(mac,zplData);
		 
	
	}

	void launchPrintingPret() {
		 
		m_ProgressDialog=ProgressDialog.show(this, getString(R.string.communication_avec_l_imprimante), getString(R.string.patientez_));

		String mac=getPrinterMacAddress();
		   BluetoothConnectionInsecureExample example = new BluetoothConnectionInsecureExample(hPrintResultPret);
              Z420ModelPretMachine z=new Z420ModelPretMachine(this);
              
       
            String   zplData=z.getPretEx( m_codeclient );
	        example.sendZplOverBluetooth(mac,zplData);
		 
	
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
		TextView tvAdr2,tvCliBloque,tvEncoursClient;
		TextView textTitreTarif;
		TextView tvTarif;
		TextView textViewNote;
		
		
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
