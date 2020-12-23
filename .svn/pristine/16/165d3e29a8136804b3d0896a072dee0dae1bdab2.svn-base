package com.menadinteractive.segafredo.tournee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.adapter.ListTourneeAdapter;
import com.menadinteractive.segafredo.adapter.OptionTarifAdapter;
import com.menadinteractive.segafredo.tarif.OptionTarifDetailActivity;
import com.menadinteractive.segafredo.tarif.Tarif;

import java.util.ArrayList;

public class ListeTourneeActivity extends BaseActivity {

	ListView lvTournee;
	EditText etFilter;
	ImageButton ibFind;
	ArrayList<Tournee> tournees;

	ListTourneeAdapter adapter = null;

	Context mContext;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tournee);

		mContext = this;

		initGUI();
		initListeners();
		PopulateList("");
	}

	/**
	 * Initialisation des éléments graphiques
	 */
	private void initGUI() {
		lvTournee = (ListView) findViewById(R.id.lvTournee);
		etFilter = (EditText) findViewById(R.id.etFilter);
		ibFind = (ImageButton) findViewById(R.id.ibFind);
	}

	/**
	 * Initialisation des listeners
	 */
	private void initListeners() {
		lvTournee.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Tournee tarif = tournees.get(position);
				Intent intent = new Intent(mContext, ListeTourneeDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("TOURNEE", tarif.getTournee().trim());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		ibFind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopulateList( etFilter.getText().toString().trim());		
			}
		});

	}

	private void PopulateList(String filter) {

		Log.d("TAG","populate list tounee");

		hideKeyb(etFilter);

		//tarif=Global.dbListeTarif.getListeTarifsFilters( filter);

		tournees = Tournee.getAllTournee(filter);

		if(tournees != null && tournees.size() > 0){
			adapter = new ListTourneeAdapter(this, 0, tournees);
			lvTournee.setAdapter(adapter);
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
