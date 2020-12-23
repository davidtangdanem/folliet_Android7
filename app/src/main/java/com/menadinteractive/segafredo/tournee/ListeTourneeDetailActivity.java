package com.menadinteractive.segafredo.tournee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.ListTourneeAdapter;
import com.menadinteractive.segafredo.adapter.ListTourneeDetailAdapter;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.findcli.MenuCliActivity;


import java.util.ArrayList;

public class ListeTourneeDetailActivity extends BaseActivity {
	
	ListView lvOptionTarifDetail;
	EditText etFilter;
	ImageButton ibFind;
	ArrayList<Tournee> tournees = null;
	Button bAide;
	
	ListTourneeDetailAdapter adapter;

    Context mContext;
	String codetournee = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tournee_detail);
        mContext = this;
		//on récupère codeTarif à partir de l'intent
		Bundle bundle = null;
		if(getIntent() != null){
			bundle = getIntent().getExtras();
			if(bundle.getString("TOURNEE") != null){
				codetournee = bundle.getString("TOURNEE");
			}
		}
		
		//Il n'y a plus qu'à remplir la liste
		
		initGUI();
		initListeners();
		//PopulateList("");
		
	}
	
	/**
	 * Initialisation des éléments graphiques
	 */
	private void initGUI() {
		lvOptionTarifDetail = (ListView) findViewById(R.id.lvOptionTarifDetail);
		etFilter = (EditText) findViewById(R.id.etFilter);
		ibFind = (ImageButton) findViewById(R.id.ibFind);
		bAide= (Button) findViewById(R.id.bAide);
	}

	@Override
	public void onResume() {
		super.onResume();
		PopulateList("");
		Log.e("onResume","onResume");
	}

	/**
	 * Initialisation des listeners
	 */
	private void initListeners() {
		
		ibFind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PopulateList( etFilter.getText().toString().trim());
			}
		});
		bAide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                Intent intent = new Intent(mContext, ListeChargement2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TOURNEE", codetournee);
                intent.putExtras(bundle);
                startActivity(intent);
			}
		});

		lvOptionTarifDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				if(position>=0 )
				{
					Tournee tarif = tournees.get(position);
					commandeActivity.launchFiche(ListeTourneeDetailActivity.this,Fonctions.GetStringDanem(tarif.getdetailcodeclient()).trim(),LAUNCH_FICHECLI);
				}
			}
		});

	}
	
	private void PopulateList(String filter) {
		 
		Log.d("TAG","populate list tournee details");
		 
		hideKeyb(etFilter);

		tournees = Tournee.getAllTarifDetail(codetournee, filter);
		
		if(tournees != null && tournees.size() > 0){
			adapter = new ListTourneeDetailAdapter(this, 0, tournees);
			lvOptionTarifDetail.setAdapter(adapter);
		}
		
		
	/*	ArrayList<assoc> assocs =new ArrayList<assoc>();
	 
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
		
		
		lv.attachValues(R.layout.item_list_findcli, cli,assocs,handler);*/
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			StringBuilder ss=new StringBuilder();
			Global.dbKDTempo.clear(ss);
				finish();
			return false;
		}

		else
			return super.onKeyDown(keyCode, event);

	}
}
