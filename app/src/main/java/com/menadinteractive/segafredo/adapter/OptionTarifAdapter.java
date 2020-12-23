package com.menadinteractive.segafredo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.tarif.Tarif;

public class OptionTarifAdapter extends ArrayAdapter<Tarif>{
	
	ArrayList<Tarif> tarifs = null;
	Context mContext;

	public OptionTarifAdapter(Context context, int textViewResourceId,
			ArrayList<Tarif> _tarifs) {
		super(context, textViewResourceId, _tarifs);
		tarifs = _tarifs;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_optiontarif, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueName = (TextView) rowView.findViewById(R.id.tvValueName);
		TextView tvValueCodeTarif = (TextView) rowView.findViewById(R.id.tvValueCodeTarif);
		TextView tvValueFerme = (TextView) rowView.findViewById(R.id.tvValueFerme);
		
		Tarif tarif = tarifs.get(position);
		
		if(tarif != null){
			if(tarif.getCodeTarif() != null){
				tvValueCodeTarif.setText(tarif.getCodeTarif());
			}
			
			if(tarif.getNomTarif() != null){
				tvValueName.setText(tarif.getNomTarif());
			}
			
			if(tarif.getFerme() != null){
				tvValueFerme.setText(tarif.getFerme());
			}
		}
	
		
		return rowView;

	}

}
