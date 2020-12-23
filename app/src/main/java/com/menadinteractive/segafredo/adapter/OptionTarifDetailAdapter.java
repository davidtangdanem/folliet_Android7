package com.menadinteractive.segafredo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.tarif.Tarif;


public class OptionTarifDetailAdapter extends ArrayAdapter<Tarif>{
	
	ArrayList<Tarif> tarifs = null;
	Context mContext;

	public OptionTarifDetailAdapter(Context context, int textViewResourceId,
			ArrayList<Tarif> _tarifs) {
		super(context, textViewResourceId, _tarifs);
		tarifs = _tarifs;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_optiontarifdetail, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueName = (TextView) rowView.findViewById(R.id.tvValueName);
		TextView tvValueCodeArticle = (TextView) rowView.findViewById(R.id.tvValueCodeArticle);
		TextView tvValueDebut = (TextView) rowView.findViewById(R.id.tvValueDebut);
		TextView tvValueFin = (TextView) rowView.findViewById(R.id.tvValueFin);
		TextView tvValuePrix = (TextView) rowView.findViewById(R.id.tvValuePrix);
		TextView tvValuePrixMin = (TextView) rowView.findViewById(R.id.tvValuePrixMin);
		TextView tvValueBloque = (TextView) rowView.findViewById(R.id.tvValueBloque);
		
		Tarif tarif = tarifs.get(position);
		
		if(tarif != null){
			if(tarif.getNomTarif() != null){
				tvValueName.setText(tarif.getNomTarif());
			}
			
			if(tarif.getCodeArticle() != null){
				tvValueCodeArticle.setText(tarif.getCodeArticle());
			}
			
			if(tarif.getDateDebut() != null){
				tvValueDebut.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(tarif.getDateDebut()));
			}
			
			if(tarif.getDateFin() != null){
				tvValueFin.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(tarif.getDateFin()));
			}
			
			if(tarif.getPrix() != null){
				tvValuePrix.setText(tarif.getPrix());
			}
			
			if(tarif.getPrixMin() != null){
				tvValuePrixMin.setText(tarif.getPrixMin());
			}
			
			if(tarif.getBloque() != null){
				tvValueBloque.setText(tarif.getBloque());
			}
		}
	
		
		return rowView;

	}

}
