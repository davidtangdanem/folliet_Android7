package com.menadinteractive.segafredo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.remisebanque.Cheque;

public class ChequeAdapter extends ArrayAdapter<Cheque>{
	
	ArrayList<Cheque> cheques;
	Context mContext;
	OnChequeCheck mListener;
	

	public ChequeAdapter(Context context, int textViewResourceId,
			ArrayList<Cheque> _cheques, OnChequeCheck listener) {
		super(context, textViewResourceId, _cheques);
		cheques = _cheques;
		mContext = context;
		mListener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_list_cheques, parent, false);

		//selon si la position est pair ou impair on change la courleur de l'item
		if(position % 2 == 0) rowView.setBackgroundColor(mContext.getResources().getColor(R.color.list_pair));
		else rowView.setBackgroundColor(mContext.getResources().getColor(R.color.list_impair));

		//Initialisation des éléments de l'item
		TextView tvCodeClient = (TextView) rowView.findViewById(R.id.tvCodeClient);
		TextView tvTypeEncaissement = (TextView) rowView.findViewById(R.id.tvTypeEncaissement);
		TextView tvDateCheque = (TextView) rowView.findViewById(R.id.tvDateCheque);
		TextView tvMntCheque = (TextView) rowView.findViewById(R.id.tvMntCheque);
		CheckBox cbCheque = (CheckBox) rowView.findViewById(R.id.cbCheque);
		
		
		//Récupération de l'objet facture en cours
		final Cheque currentCheque = cheques.get(position);

		//Mise en place des valeurs dans les différents champs de l'item
		if(currentCheque.getCodeClient() != null) 
			tvCodeClient.setText(currentCheque.getCodeClient());
		if(currentCheque.getTypeEncaissement() != null) {
//			if(currentCheque.getTypeEncaissement().equals(Encaissement.TYPE_CHEQUE) 
//					&& currentCheque.getDateCheque() != null 
//					&& currentCheque.getDateCheque().compareTo(currentCheque.getDate()) > 0){
//				tvTypeEncaissement.setText(mContext.getString(R.string.cheque_type_chp));
//			}else{
//				tvTypeEncaissement.setText(currentCheque.getTypeEncaissement());
//			}
			
			tvTypeEncaissement.setText(currentCheque.getTypeEncaissement());
		}
		if(currentCheque.getDateCheque() != null) 
			tvDateCheque.setText(Fonctions.getDD_MM_YYYY(currentCheque.getDateCheque()));
		 
		tvMntCheque.setText(Fonctions.GetDoubleToStringFormatDanem(currentCheque.getMontant(),"0.00")+" €" );
		
		//on coche la case si nécessaire
		if(currentCheque.isChecked()) cbCheque.setChecked(true);
		else cbCheque.setChecked(false);
		
		//on recalcul le total à chaque fois pour avoir la somme des factures sélectionnées
		calculateTotalCheque();
		
		//gestion de la case à cocher
		cbCheque.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(currentCheque.isChecked() == true) currentCheque.setChecked(false);
				else currentCheque.setChecked(true);
				
				calculateTotalCheque();
			}
		});
		
		return rowView;

	}
	
	private void calculateTotalCheque(){
		//calcul du total de la sélection chéque et espèce indépendament
		float totalChequeJour = 0;
		float totalCheque = 0;
		float totalEspece = 0;
		float totalChequePortefeuille = 0;
		
		for(Cheque cheque : cheques){
			if(cheque.isChecked() == true){
				if(cheque.getTypeEncaissement().equals(Encaissement.TYPE_CHEQUE)){
					//on calcul le total				
//					if(cheque.getDateCheque() != null && !cheque.getDateCheque().equals(Fonctions.getYYYYMMDD())){
//						totalChequePortefeuille += cheque.getMontant();
//					}else{
//						totalChequeJour += cheque.getMontant();
//					}
					totalChequeJour += cheque.getMontant();
				}else if(cheque.getTypeEncaissement().equals(Encaissement.TYPE_ESPECE)){
					totalEspece += cheque.getMontant();
				}else if(cheque.getTypeEncaissement().equals(Encaissement.TYPE_CHEQUEPORTEFEUILLE)){
					totalChequePortefeuille += cheque.getMontant();
				}
			}
		}
		
		totalCheque = totalChequeJour + totalChequePortefeuille;
		
		if(mListener != null) mListener.onCheck(totalChequeJour, totalChequePortefeuille, totalCheque, totalEspece);
	}
	
	public interface OnChequeCheck{
		public void onCheck(float totalChequeJour, float totalChequePortefeuille, float totalCheque, float totalEspece);
	}

}
