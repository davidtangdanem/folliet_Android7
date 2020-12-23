package com.menadinteractive.segafredo.tarif;

import java.util.ArrayList;

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
import com.menadinteractive.segafredo.adapter.OptionTarifAdapter;

public class OptionTarifActivity extends BaseActivity {

	ListView lvOptionTarif;
	EditText etFilter;
	ImageButton ibFind;
	ArrayList<Tarif> tarifs;

	OptionTarifAdapter adapter = null;

	Context mContext;

	public static String CODE_TARIF = "codetarif";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_tarif);

		mContext = this;

		initGUI();
		initListeners();
		PopulateList("");
	}

	/**
	 * Initialisation des éléments graphiques
	 */
	private void initGUI() {
		lvOptionTarif = (ListView) findViewById(R.id.lvOptionTarif);
		etFilter = (EditText) findViewById(R.id.etFilter);
		ibFind = (ImageButton) findViewById(R.id.ibFind);
	}

	/**
	 * Initialisation des listeners
	 */
	private void initListeners() {
		lvOptionTarif.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Tarif tarif = tarifs.get(position);
				Intent intent = new Intent(mContext, OptionTarifDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(CODE_TARIF, tarif.getCodeTarif().trim());
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

		Log.d("TAG","populate list tarif");

		hideKeyb(etFilter);

		//tarif=Global.dbListeTarif.getListeTarifsFilters( filter);

		tarifs = Tarif.getAllTarif(filter);

		if(tarifs != null && tarifs.size() > 0){
			adapter = new OptionTarifAdapter(this, 0, tarifs);
			lvOptionTarif.setAdapter(adapter);
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
