package com.menadinteractive.segafredo.adapter;

import java.util.ArrayList;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.histo.Symptomes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SymptomesAdapter extends ArrayAdapter<Symptomes>{

	ArrayList<Symptomes> Symptomes = null;
	Context mContext;

	public SymptomesAdapter(Context context, int textViewResourceId,
			ArrayList<Symptomes> _Symptomes) {
		super(context, textViewResourceId, _Symptomes);
		Symptomes = _Symptomes;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_symptomes, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueCode = (TextView) rowView.findViewById(R.id.tvValueCode);
		TextView tvValueLibelle = (TextView) rowView.findViewById(R.id.tvValueLibelle);
		
		
		Symptomes symptome = Symptomes.get(position);
		
		if(symptome != null){
			if(symptome.getlib_symp() != null){
				tvValueLibelle.setText(symptome.getlib_symp() );
			}
			
			if(symptome.getcod_symp() != null){
				tvValueCode.setText(symptome.getcod_symp());
			}
			
			//si c'est checked alors le fond est de la couleur de la sélection
			if(symptome.getChecked()){
				rowView.setBackgroundColor(mContext.getResources().getColor(R.color.greenPanier));
			}
		}
		
	
		
		return rowView;

	}
}
