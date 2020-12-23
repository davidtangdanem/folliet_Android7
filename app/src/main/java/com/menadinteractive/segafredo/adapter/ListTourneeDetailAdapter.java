package com.menadinteractive.segafredo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.tournee.Tournee;

import java.util.ArrayList;


public class ListTourneeDetailAdapter extends ArrayAdapter<Tournee>{

	ArrayList<Tournee> tournees = null;
	Context mContext;

	public ListTourneeDetailAdapter(Context context, int textViewResourceId,
                                    ArrayList<Tournee> _tournees)
	{
		super(context, textViewResourceId, _tournees);
		tournees = _tournees;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_listtourneedetail, parent, false);

		//Initialisation des éléments de l'item
		TextView tvValueName = (TextView) rowView.findViewById(R.id.tvValueName);
		TextView tvValueCodeArticle = (TextView) rowView.findViewById(R.id.tvValueCodeArticle);
		TextView tvValueDebut = (TextView) rowView.findViewById(R.id.tvValueDebut);
		TextView tvValueFin = (TextView) rowView.findViewById(R.id.tvValueFin);
		TextView tvValuePrix = (TextView) rowView.findViewById(R.id.tvValuePrix);
		TextView tvValuePrixMin = (TextView) rowView.findViewById(R.id.tvValuePrixMin);
		TextView tvValueBloque = (TextView) rowView.findViewById(R.id.tvValueBloque);
		LinearLayout linearDetail = (LinearLayout) rowView.findViewById(R.id.linear_item_tournee_detail);
		
		Tournee tarif = tournees.get(position);

		if(tarif != null){
			if(tarif.getdetailcodeclient() != null){
				tvValueName.setText(tarif.getdetailcodeclient());
			}
			
			if(tarif.getdetailnomclient() != null){
				tvValueCodeArticle.setText(tarif.getdetailnomclient());
			}
			
			if(tarif.getdetailvilleclient() != null){
				tvValueDebut.setText(tarif.getdetailvilleclient());
			}
			
			if(tarif.getdetailcodetournee() != null){
				tvValueFin.setText(tarif.getdetailcodetournee());
			}
			
			if(tarif.getdetailperiodicite() != null){
				String periodicite = tarif.getdetailperiodicite();
				tvValuePrix.setText(periodicite);

				if (periodicite.equals("36")){
					linearDetail.setBackgroundColor(Color.parseColor("#FFA500"));
				}

			}
			
			if(tarif.getdetaildatederfac() != null){
				tvValuePrixMin.setText(tarif.getdetaildatederfac());
			}
			
			if(tarif.getdetailnbjour() != null){
				tvValueBloque.setText(tarif.getdetailnbjour());
			}
		}
	
		
		return rowView;

	}

}
