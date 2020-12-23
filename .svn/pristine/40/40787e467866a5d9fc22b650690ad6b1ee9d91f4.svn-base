package com.menadinteractive.segafredo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.encaissement.Facture;
import com.menadinteractive.segafredo.plugins.Espresso;

public class FacturesAdapter extends ArrayAdapter<Facture>{

	ArrayList<Facture> factures;
	Context mContext;
	OnFactureCheck mListener;


	public FacturesAdapter(Context context, int textViewResourceId,
			ArrayList<Facture> _factures, OnFactureCheck listener) {
		super(context, textViewResourceId, _factures);
		factures = _factures;
		mContext = context;
		mListener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_lv_factures, parent, false);

		//selon si la position est pair ou impair on change la courleur de l'item
		if(position % 2 == 0) rowView.setBackgroundColor(mContext.getResources().getColor(R.color.list_pair));
		else rowView.setBackgroundColor(mContext.getResources().getColor(R.color.list_impair));

		//Initialisation des éléments de l'item
		TextView tvNumDoc = (TextView) rowView.findViewById(R.id.tvNumDoc);
		TextView tvFlagtech = (TextView) rowView.findViewById(R.id.tvFlagtech);
		TextView tvDateFacture = (TextView) rowView.findViewById(R.id.tvDateFacture);
		TextView tvMontantTTC = (TextView) rowView.findViewById(R.id.tvMontantTTC);
		TextView tvTypeFacture = (TextView) rowView.findViewById(R.id.tvTypeFacture);

		TextView tvMontantDu = (TextView) rowView.findViewById(R.id.tvMontantDu);
		TextView tvNiveau= (TextView) rowView.findViewById(R.id.tvNiveau);
		TextView tvNbJour = (TextView) rowView.findViewById(R.id.tvNbJour);
		CheckBox cbFacture = (CheckBox) rowView.findViewById(R.id.cbFacture);
		View vSquare = (View) rowView.findViewById(R.id.vSquare);

		//Récupération de l'objet facture en cours
		final Facture currentFacture = factures.get(position);

		//Mise en place des valeurs dans les différents champs de l'item
		tvNumDoc.setText(currentFacture.getNumDoc());

		//tvMontantTTC.setText(Fonctions.GetFloatToStringFormatDanem(currentFacture.getMontantTTC(), "0.00"));
		tvTypeFacture.setText(currentFacture.getTypeFacture());

		tvNiveau.setText(currentFacture.getNiveau());
		tvNbJour.setText(currentFacture.getNbJour());
		tvFlagtech.setText(currentFacture.getFactTech());
		tvMontantTTC.setText(currentFacture.getMnt_pie());
		
		if(currentFacture.getTypeFacture() != null 
				&& (currentFacture.getTypeFacture().equals(Facture.TYPE_AVOIR)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_REGLEMENT)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_IMPAYES)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_OD)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_ECART)
						
						)){
			tvMontantDu.setText(Fonctions.GetFloatToStringFormatDanem(currentFacture.getMontantDu(), "0.00"));
		}else tvMontantDu.setText(Fonctions.GetFloatToStringFormatDanem(currentFacture.getMontantDu(), "0.00"));
		tvMontantDu.setTextColor(mContext.getResources().getColor(R.color.blue_montant));

		if(currentFacture.getIsFactureDue() 
				|| (currentFacture.getTypeFacture() != null 
				&& (currentFacture.getTypeFacture().equals(Facture.TYPE_AVOIR)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_AVOIR)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_AVOIR_ENC)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_REGLEMENT)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_IMPAYES)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_OD)
						|| currentFacture.getTypeFacture().equals(Facture.TYPE_ECART)
						))){
			tvDateFacture.setText(currentFacture.getDateFacture());
		}
		else{
			tvDateFacture.setText(Fonctions.getDD_MM_YYYY(currentFacture.getDateFacture()));
		}

		if(currentFacture.getColor() != null && !currentFacture.getColor().equals("")) {
			GradientDrawable bgShape = (GradientDrawable)vSquare.getBackground();
			bgShape.setColor(Color.parseColor(currentFacture.getColor()));
		}else{
			vSquare.setVisibility(View.INVISIBLE);
		}

		//si le total de la facture est égal au total des encaissements alors on disable la case à cocher
		float totalEncaissement = (float) (Math.round(Encaissement.getTotalEncaissementFromList(
				Encaissement.getEncaissementFromFacture(currentFacture, currentFacture.getCodeClient(), null))*100.0)/100.0);

		float montantFacture = (float) (Math.round(currentFacture.getMontantDu()*100.0)/100.0);

		if(totalEncaissement >= montantFacture) cbFacture.setEnabled(false);

		//si la facture est de type avoir ou reglement alors on bloque la cb
		if(currentFacture.getTypeFacture().equals(Facture.TYPE_AVOIR) 
				|| currentFacture.getTypeFacture().equals(Facture.TYPE_REGLEMENT)
				|| currentFacture.getTypeFacture().equals(Facture.TYPE_IMPAYES) 
				|| currentFacture.getTypeFacture().equals(Facture.TYPE_OD)
				|| currentFacture.getTypeFacture().equals(Facture.TYPE_ECART)
				){
			cbFacture.setEnabled(false);
		}

        dbSiteListeLogin login=new dbSiteListeLogin(new MyDB(getContext()));
        dbSiteListeLogin.structlistLogin rep=new dbSiteListeLogin.structlistLogin();
        login.getlogin(Preferences.getValue(getContext(), Espresso.LOGIN, "0"), rep, new StringBuilder());

        if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable))
        {
            cbFacture.setEnabled(false);
        }

        //on coche la case si nécessaire
		if(currentFacture.getIsChecked()) cbFacture.setChecked(true);
		else cbFacture.setChecked(false);

		//on recalcul le total à chaque fois pour avoir la somme des factures sélectionnées
		calculateTotalFactures();

		//gestion de la case à cocher
		cbFacture.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(currentFacture.getIsChecked() == true) {
					currentFacture.setIsChecked(false);
				}
				else {
					currentFacture.setIsChecked(true);
				}

				//on disable les autres factures
				//si on clique sur la facture checké on enabled les autres et on décoche la current
				//mettre cette ligne en commentaire pour pouvoir cocher toutes les cases
				changeCheckValueOfFactures(currentFacture, false);
				
                calculateTotalFactures();
			}
		});

		return rowView;

	}
	
	/**
	 * Change la valeur des factures différentes de celle passée en paramètres
	 * @param value
	 */
	void changeCheckValueOfFactures(Facture facture, boolean value){
		for(Facture fac : factures){
			if(fac != facture) fac.setIsChecked(false);
		}
	}

	private void calculateTotalFactures(){
		//calcul du total de la sélection
		float total = 0;

		for(Facture facture : factures){
			if(facture.getIsChecked() == true){
				//on calcul le total
				//				if(!facture.getIsFactureDue()){
				//					if(facture.getTypeFacture().equals(Facture.TYPE_AVOIR)) total -= Math.round(facture.getMontantTTC()*100.0)/100.0;
				//					else total += Math.round(facture.getMontantTTC()*100.0)/100.0;
				//				}
				//				else total += Math.round(facture.getMontantDu()*100.0)/100.0;

				if(facture.getTypeFacture().equals(Facture.TYPE_AVOIR) || facture.getTypeFacture().equals(Facture.TYPE_REGLEMENT)
						|| facture.getTypeFacture().equals(Facture.TYPE_IMPAYES)
						|| facture.getTypeFacture().equals(Facture.TYPE_OD)
						|| facture.getTypeFacture().equals(Facture.TYPE_ECART)
						){
					total += Math.round(facture.getMontantDu()*100.0)/100.0;
				}
				else{
					total += Math.round(facture.getMontantDu()*100.0)/100.0;
				}
			}
		}

		if(mListener != null) mListener.onCheck(total);
	}

	public interface OnFactureCheck{
		public void onCheck(float total);
	}

}
