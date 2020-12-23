package com.menadinteractive.segafredo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.tarif.Tarif;
import com.menadinteractive.segafredo.tournee.Tournee;

import java.util.ArrayList;

public class ListTourneeAdapter extends ArrayAdapter<Tournee>{

	ArrayList<Tournee> tournees = null;
	Context mContext;

	public ListTourneeAdapter(Context context, int textViewResourceId,
                              ArrayList<Tournee> _tournees) {
		super(context, textViewResourceId, _tournees);
		tournees = _tournees;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_listtournee, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueName = (TextView) rowView.findViewById(R.id.tvValueName);
		TextView tvValueCodeTarif = (TextView) rowView.findViewById(R.id.tvValueCodeTarif);
		TextView tvValueFerme = (TextView) rowView.findViewById(R.id.tvValueFerme);
		
		Tournee tournee = tournees.get(position);
		
		if(tournee != null){
			if(tournee.getCodeClient() != null){
				tvValueCodeTarif.setText(tournee.getCodeClient());
			}
			
			if(tournee.getTournee() != null){
				tvValueName.setText(tournee.getTournee());
			}
			
			if(tournee.getDateTournee() != null){
				tvValueFerme.setText(tournee.getDateTournee());
			}
		}
	
		
		return rowView;

	}

}
