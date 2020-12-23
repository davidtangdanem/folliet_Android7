package com.menadinteractive.segafredo.contactcli;

import java.util.ArrayList;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.client.ficheclient;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableContactcli;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD451RetourMachineclient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.findcli.FindCliActivity;
import com.menadinteractive.segafredo.findcli.MenuCliActivity;
import com.menadinteractive.segafredo.materielclient.ListingMaterielClient;

public class ContactCliActivity extends BaseActivity implements OnClickListener{
	ArrayList<Bundle> cli;
	Handler handler;
	ImageButton ibFind;
	ImageButton ibNew;
	EditText etFilter;
	myListView lv;;
	String m_codeclient;
	boolean m_brapport=false;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		 
		setContentView(R.layout.activity_contactcli);
		
		Bundle bundle = this.getIntent().getExtras();
		m_codeclient = bundle.getString("codeclient");
		m_brapport =bundle.getBoolean("brapport", false);
	
 
		initGUI();
		setListeners();
		DispInfoCli();
	}

	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
		etFilter= (EditText) findViewById(R.id.etFilter);
		ibFind= (ImageButton) findViewById(R.id.ibFind);
		ibNew= (ImageButton) findViewById(R.id.ibNew);
		
		registerForContextMenu(lv);
		handler =getHandler();
		PopulateList();
	}
	
	void setListeners()
	{
	
		
		ibFind.setOnClickListener(this);
		ibNew.setOnClickListener(this);
		
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
				 
					/*int  id=bGet.getInt("position");
					String codecontactcli=cli.get(id).getString(Global.dbContactcli.FIELD_CODECONTACT);
				 
					Fonctions.FurtiveMessageBox(ContactCliActivity.this, codecontactcli);
	,				
					launchMenuContactcli(codecontactcli);*/
					break;
				 
				}

			}
		};
		return h;
	}
	private void PopulateList() {
		
		 cli=Global.dbContactcli.getContactcliFilters(m_codeclient, etFilter.getText().toString());
		ArrayList<assoc> assocs =new ArrayList<assoc>();
		
	 
		assocs.add(new  assoc(0,R.id.tvCode,Global.dbContactcli.FIELD_CODECONTACT/*TableContactcli.FIELD_CODECONTACT*/));
		assocs.add(new  assoc(0,R.id.tvNom,Global.dbContactcli.FIELD_NOM/*TableContactcli.FIELD_PRENOM*/));
		assocs.add(new  assoc(0,R.id.tvPrenom,Global.dbContactcli.FIELD_PRENOM/*TableContactcli.FIELD_PRENOM*/));
		assocs.add(new  assoc(0,R.id.tvMobile,Global.dbContactcli.FIELD_MOBILE /*TableContactcli.FIELD_MOBILE*/));
		
		//assocs.add(new  assoc(0,R.id.tvCP,Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_FCTCONTACT, TableContactcli.FIELD_CODEFONCTION)));
		//assocs.add(new  assoc(0,R.id.tvFonction,Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_FCTCONTACT, TableContactcli.FIELD_CODEFONCTION)));
		assocs.add(new  assoc(0,R.id.tvFonction, Global.dbContactcli.FIELD_CODEFONCTION/*TableContactcli.FIELD_CODEFONCTION*/));
		
		assocs.add(new  assoc(0,R.id.tvEmail,Global.dbContactcli.FIELD_EMAIL/*TableContactcli.FIELD_EMAIL*/));
		
		assocs.add(new  assoc(1,R.id.iv2,null));
		
		lv.attachValues(R.layout.item_list_contactcli, cli,assocs,handler);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==ibFind)
		{
			PopulateList();
			 
		}
		
		if (v==ibNew)
		{
			launchMenuContactcli("");
			 
		}
		
	}
	void launchMenuContactcli(String contactcli)
	{
		Intent intent = new Intent(this,		fichecontactcli.class);
		Bundle b=new Bundle();
		b.putString("contactcli",contactcli);
		b.putString("codeclient",m_codeclient);
		
		

		intent.putExtras(b);
		startActivityForResult(intent,1);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//on revient de la cde, si on a pris une commande on ne peut pas annuler le prospect
		//on recharge la fiche aussi car peut �tre modifiŽe dans la cde
		if(requestCode==1 )
		{
			PopulateList();
		}

		

	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		addMenu(menu, R.string.ajouter, android.R.drawable.ic_menu_add);
		

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
				StringBuffer buff = new StringBuffer();
				ArrayList<String> ValueMessage=new ArrayList();
				switch (item.getItemId()) {
				case R.string.ajouter:
					
					launchMenuContactcli("");
					


					return true;
				
				case R.string.geocode_prospect:
					
					break;
				default:
					return super.onOptionsItemSelected(item);
				}
				return super.onOptionsItemSelected(item);
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
		TextView textTitreTarif;
		TextView tvTarif;
		
		TextView tvAdr1;
		TextView tvAdr2,tvCliBloque,tvEncoursClient;
		TextView textViewNote;
		
		
		tvMail = (TextView) findViewById(R.id.textViewMail);
		tvRS = (TextView) findViewById(R.id.textViewRS);
		tvCP = (TextView) findViewById(R.id.textViewCP);
		tvTel = (TextView) findViewById(R.id.textViewTel);
		textTitreTarif= (TextView) findViewById(R.id.textTitreTarif);
		tvTarif = (TextView) findViewById(R.id.textViewTarif);
		
		tvVille = (TextView) findViewById(R.id.textViewVille);
		tvAdr1 = (TextView) findViewById(R.id.textViewAdr1);
		tvAdr2 = (TextView) findViewById(R.id.textViewAdr2);
		tvCode = (TextView) findViewById(R.id.textViewCode);
		tvCliBloque = (TextView) findViewById(R.id.tvCliBloque);
		tvEnseigne = (TextView) findViewById(R.id.textViewEnseigne);
		tvEncoursClient = (TextView) findViewById(R.id.tvEncoursClient);
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
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 0, 0, "Modifier");
		if(m_brapport==true)
		menu.add(0, 1, 0,  "Sélectionner");
		
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		//Bundle bu=tabVal.get((int)info.id);
		
		int  id=info.position;
		 
		String codecontactcli=cli.get(id).getString(Global.dbContactcli.FIELD_CODECONTACT);
		String titrecontactcli=cli.get(id).getString(Global.dbContactcli.FIELD_CIVIL);
		String nomcontactcli=cli.get(id).getString(Global.dbContactcli.FIELD_NOM);
		
		 
	
		switch (item.getItemId()) {
		case 0:
			
			Fonctions.FurtiveMessageBox(ContactCliActivity.this, codecontactcli);				
			launchMenuContactcli(codecontactcli);


			return true;
		case 1:
			 Intent result = new Intent();
			 Bundle b = new Bundle();
			 b.putString("contactcode", codecontactcli);
			 b.putString("contacttitre", titrecontactcli);
			 b.putString("contactnom", nomcontactcli );
			 
			
			 result.putExtras(b);
			 setResult(Activity.RESULT_OK, result);
						 finish();

			return true;
		
			
		default:
			return super.onContextItemSelected(item);
		}
	}
		 
}
