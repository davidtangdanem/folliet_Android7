package com.menadinteractive.segafredo.tarif;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.OptionTarifDetailAdapter;

public class OptionTarifDetailActivity extends BaseActivity {
	
	ListView lvOptionTarifDetail;
	EditText etFilter;
	ImageButton ibFind;
	ArrayList<Tarif> tarifs = null;
	
	OptionTarifDetailAdapter adapter;
	
	
	String codeTarif = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_tarif_detail);
		
		//on récupère codeTarif à partir de l'intent
		Bundle bundle = null;
		if(getIntent() != null){
			bundle = getIntent().getExtras();
			if(bundle.getString(OptionTarifActivity.CODE_TARIF) != null){
				codeTarif = bundle.getString(OptionTarifActivity.CODE_TARIF);
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
	}
	
	private void PopulateList(String filter) {
		 
		Log.d("TAG","populate list tarif details");
		 
		hideKeyb(etFilter);
		
		//cli=Global.dbListeTarifsDetails.getListeTarifsDetailsFilters(codeTarif, filter);
		
		tarifs = Tarif.getAllTarifDetail(codeTarif, filter);
		
		if(tarifs != null && tarifs.size() > 0){
			adapter = new OptionTarifDetailAdapter(this, 0, tarifs);
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
}
