package com.menadinteractive.segafredo.findcli;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.TableSouches.passeplat;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;



public class FindCliActivity extends BaseActivity implements OnClickListener{
	
	public final static String TYPE_CLIENT = "C";
	public final static String TYPE_PROSPECT = "P";
	public final static String KEY_TYPE = "type_client";
	ArrayList<Bundle> cli;
	Handler handler;
	ImageButton ibFind;
	EditText etFilter;
	TextView tvTitre;
	myListView lv;
	structlistLogin rep = null;
	
	String type = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_findcli);
		//MyDB.copyFile(MyDB.source,MyDB.dest);
		
		Bundle bundle = null;
		if(getIntent() != null){
			bundle = getIntent().getExtras();
			if(bundle != null){
				type = bundle.getString(FindCliActivity.KEY_TYPE);
			}
		}
 
		initGUI();
		setListeners();
	}

	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
		etFilter= (EditText) findViewById(R.id.etFilter);
		ibFind= (ImageButton) findViewById(R.id.ibFind);
		tvTitre=(TextView) findViewById(R.id.textViewTitre);
		
		Fonctions.setFont(this, tvTitre, Global.FONT_REGULAR);
		
		handler =getHandler();

		PopulateList(R.string.tri_par_nom_client, type);
		
		 etFilter.setOnKeyListener(new OnKeyListener() {

		        @Override
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		        	if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
		    			PopulateList(R.string.tri_par_code_client, type);
		                return false;
		            }
		            return false;
		        }
		    });
//		etFilter.setText(Fonctions.GetProfileString(FindCliActivity.this, "lastcli", ""));
//		etFilter.setSelection(0,etFilter.getText().length());
		
	}
	
	void setListeners()
	{
	
		
		ibFind.setOnClickListener(this);
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
					String codecli=cli.get(id).getString(Global.dbClient.FIELD_CODE);
				 
					
					//Fonctions.FurtiveMessageBox(FindCliActivity.this, codecli);
					Fonctions.WriteProfileString(FindCliActivity.this, "lastcli", cli.get(id).getString(Global.dbClient.FIELD_NOM));
					launchMenuCli(codecli );
					break;
				 
				}

			}
		};
		return h;
	}
	private void PopulateList(int tri, String... type) {
	 
		Log.d("TAG","populate findcli");
		 
		hideKeyb(etFilter);
		
		String order="";
		if (tri==R.string.tri_par_nom_client)
			order=TableClient.FIELD_ENSEIGNE;	
		if (tri==R.string.tri_par_code_client)
			order=TableClient.FIELD_CODE;
		if (tri==R.string.tri_par_ville)
			order=TableClient.FIELD_VILLE;
		rep = getRep();
		boolean bvendeur=false;
		if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))
		{ 
			bvendeur=true;
			
		}
		cli=Global.dbClient.getClientsFilters3(bvendeur,  etFilter.getText().toString().trim(),order, type);
		ArrayList<assoc> assocs =new ArrayList<assoc>();
	 
		assocs.add(new  assoc(0,R.id.tvCode,TableClient.FIELD_CODE,"isbloque"));
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
		
		
		lv.attachValues(R.layout.item_list_findcli, cli,assocs,handler);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==ibFind)
		{
			PopulateList(R.string.tri_par_code_client, type);
			 
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		TableSouches souche=new TableSouches(m_appState.m_db,this);
	 
			addMenu(menu, R.string.tri_par_nom_client,-1);
			addMenu(menu,R.string.tri_par_code_client,-1);
			addMenu(menu, R.string.tri_par_ville,-1);
			
			if(Fonctions.GetStringDanem(type).equals(TYPE_PROSPECT))
			{
				passeplat pp=new passeplat();
				if (souche.get(souche.TYPEDOC_PROSPECT,getLogin(), pp) )
					  addMenu(menu, R.string.demande_prospect,-1);
				
			}
			
			
			return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.string.tri_par_nom_client: 
		case R.string.tri_par_code_client: 
		case R.string.tri_par_ville: 
			 PopulateList(item.getItemId(), type);
			 break;
		case R.string.demande_prospect: 
			commandeActivity.launchFiche(FindCliActivity.this,"",LAUNCH_FICHECLI);
			 break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

		 
	 
}
