package com.menadinteractive.segafredo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.tournee.Tournee;

import java.util.ArrayList;


public class ListChargementAdapter extends ArrayAdapter<Tournee>{

	ArrayList<Tournee> tournees = null;
	Context mContext;

	public ListChargementAdapter(Context context, int textViewResourceId,
                                 ArrayList<Tournee> _tournees)
	{
		super(context, textViewResourceId, _tournees);
		tournees = _tournees;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_listchargement, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueName = (TextView) rowView.findViewById(R.id.tvValueName);
		TextView tvValueCodeArticle = (TextView) rowView.findViewById(R.id.tvValueCodeArticle);
		TextView tvValueDebut = (TextView) rowView.findViewById(R.id.tvValueDebut);
		TextView tvValueFin = (TextView) rowView.findViewById(R.id.tvValueFin);
		TextView tvValuePrix = (TextView) rowView.findViewById(R.id.tvValuePrix);
		TextView tvValuePrixMin = (TextView) rowView.findViewById(R.id.tvValuePrixMin);
		TextView tvValueBloque = (TextView) rowView.findViewById(R.id.tvValueBloque);
		
		Tournee tarif = tournees.get(position);

		if(tarif != null){
			if(tarif.getcharcodearticle() != null){
				tvValueName.setText(tarif.getcharcodearticle());
			}
			
			if(tarif.getcharlibellearticle() != null){
				tvValueCodeArticle.setText(tarif.getcharlibellearticle());
			}
			
			if(tarif.getcharstockmini() != null){
				tvValueDebut.setText(tarif.getcharstockmini());
			}

			if(tarif.getchardatelast() != null){
				tvValueFin.setText(tarif.getchardatelast());
				tvValueFin.setTextColor(Color.BLUE);
			}


		}
	
		
		return rowView;

	}

}
