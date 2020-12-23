package com.menadinteractive.segafredo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.histo.Materiel;

public class InterventionMaterielAdapter extends ArrayAdapter<Materiel>{
	
	ArrayList<Materiel> materiels = null;
	Context mContext;

	public InterventionMaterielAdapter(Context context, int textViewResourceId,
			ArrayList<Materiel> _materiels) {
		super(context, textViewResourceId, _materiels);
		materiels = _materiels;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_intervention_materiel, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueName = (TextView) rowView.findViewById(R.id.tvValueName);
		TextView tvValueDateInstallation = (TextView) rowView.findViewById(R.id.tvValueDateInstallation);
		TextView tvValueFinContrat = (TextView) rowView.findViewById(R.id.tvValueFinContrat);
		TextView tvTypeContrat = (TextView) rowView.findViewById(R.id.tvTypeContrat);
		TextView tvValueNumeroSerie = (TextView) rowView.findViewById(R.id.tvValueNumeroSerie);
		
		Materiel materiel = materiels.get(position);
		
		if(materiel != null){
			if(materiel.getLibelleArticle() != null){
				tvValueName.setText(materiel.getLibelleArticle());
			}
			
			if(materiel.getDateInstallation() != null){
				tvValueDateInstallation.setText(materiel.getDateInstallation());
			}
			
			if(materiel.getDateFin() != null){
				tvValueFinContrat.setText(materiel.getDateFin());
			}
			
			if(materiel.getTypeContrat() != null){
				tvTypeContrat.setText(materiel.getTypeContrat());
			}
			
			if(materiel.getNumeroSerie() != null){
				tvValueNumeroSerie.setText(materiel.getNumeroSerie());
			}
			
			//si c'est checked alors le fond est de la couleur de la sélection
			if(materiel.getChecked()){
				rowView.setBackgroundColor(mContext.getResources().getColor(R.color.greenPanier));
			}
			
		}
	
		
		return rowView;

	}


}
