package com.menadinteractive.segafredo.tournee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.ListChargementAdapter;
import com.menadinteractive.segafredo.adapter.ListTourneeDetailAdapter;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.dbKD544LinMvtsStock;
import com.menadinteractive.segafredo.db.dbSiteProduit;
import com.menadinteractive.segafredo.plugins.Espresso;

import java.util.ArrayList;
import java.util.Collections;

public class ListeChargementActivity extends BaseActivity {
	
	ListView lvOptionTarifDetail;
	EditText etFilter;
	ImageButton ibFind;
	ArrayList<Tournee> tournees = null;
	int top = 0;
	int index = 0;
	ListChargementAdapter adapter;
	
	
	String codetournee = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_chargement);
		
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
		PopulateList("");
		
	}
	
	/**
	 * Initialisation des éléments graphiques
	 */
	private void initGUI() {
		lvOptionTarifDetail = (ListView) findViewById(R.id.lvOptionTarifDetail);
		etFilter = (EditText) findViewById(R.id.etFilter);
		ibFind = (ImageButton) findViewById(R.id.ibFind);

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


		lvOptionTarifDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				if(position>=0 )

				{
					index = lvOptionTarifDetail.getFirstVisiblePosition();
					View vw = lvOptionTarifDetail.getChildAt(0);
					top = (vw == null) ? 0 : vw.getTop();

					StringBuffer stBuf =new StringBuffer();
					Global.dbKDTempo.save(Fonctions.GetStringDanem(tournees.get(position).getcharcodearticle()),stBuf);
					//PopulateList("");


					//lvOptionTarifDetail.getChildAt(index).setBackgroundColor(Color.GREEN);




				}

			}
		});


	}
	
	private void PopulateList(String filter) {
		 
		Log.d("TAG","populate Chargement");
		 
		hideKeyb(etFilter);
		

		tournees = Tournee.getChargementPrincipal(codetournee, "");
		
		if(tournees != null && tournees.size() > 0){
			//Collections.sort(tournees, dbSiteProduit.Comparators.DATE);
			adapter = new ListChargementAdapter(this, 0, tournees);
			lvOptionTarifDetail.setAdapter(adapter);
		}
		
		

		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		lvOptionTarifDetail.setSelectionFromTop(index, top);

	}

}
