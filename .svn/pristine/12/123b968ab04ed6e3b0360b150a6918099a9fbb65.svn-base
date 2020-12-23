package com.menadinteractive.segafredo.tarif;

import java.util.ArrayList;

import android.os.Bundle;

import com.menadinteractive.segafredo.communs.Global;

public class Tarif {

	String codeTarif;
	String nomTarif;
	String ferme;
	
	//Détail
	String dateDebut;
	String dateFin;
	String prix;
	String prixMin;
	String bloque;
	String codeArticle;
	String codeClient;
	String remSodexo;
	String prixNet;
	
	
	public String getCodeTarif(){
		return codeTarif;
	}
	public void setCodeTarif(String _codeTarif){
		this.codeTarif = _codeTarif;
	}
	
	public String getNomTarif(){
		return nomTarif;
	}
	public void setNomTarif(String _nomTarif){
		this.nomTarif = _nomTarif;
	}
	
	public String getFerme(){
		return ferme;
	}
	public void setFerme(String _ferme){
		this.ferme = _ferme;
	}
	
	//Détails
	public String getDateDebut(){
		return dateDebut;
	}
	public void setDateDebut(String _dateDebut){
		this.dateDebut = _dateDebut;
	}
	
	public String getDateFin(){
		return dateFin;
	}
	public void setDateFin(String _dateFin){
		this.dateFin = _dateFin;
	}
	
	public String getPrix(){
		return prix;
	}
	public void setPrix(String _prix){
		this.prix = _prix;
	}
	
	public String getPrixMin(){
		return prixMin;
	}
	public void setPrixMin(String _prixMin){
		this.prixMin = _prixMin;
	}
	
	public String getBloque(){
		return bloque;
	}
	public void setBloque(String _bloque){
		this.bloque = _bloque;
	}
	
	public String getCodeClient(){
		return codeClient;
	}
	public void setCodeClient(String _codeClient){
		this.codeClient = _codeClient;
	}
	
	public String getRemSodexo(){
		return remSodexo;
	}
	public void setRemSodexo(String _remSodexo){
		this.remSodexo = _remSodexo;
	}
	
	public String getPrixNet(){
		return prixNet;
	}
	public void setPrixNet(String _prixNet){
		this.prixNet = _prixNet;
	}
	
	public String getCodeArticle(){
		return codeArticle;
	}
	public void setCodeArticle(String _codeArticle){
		this.codeArticle = _codeArticle;
	}
	
	public Tarif(){}
	
	public Tarif(String _codeTarif, String _nomTarif, String _ferme){
		this.codeTarif = _codeTarif;
		this.nomTarif = _nomTarif;
		this.ferme = _ferme;
	}
	
	/**
	 * Récupère tous les tarifs à partir du filtre
	 * @param filter
	 * @return ArrayList<Tarif>
	 */
	public static ArrayList<Tarif> getAllTarif(String filter){
		ArrayList<Tarif> tarifs = new ArrayList<Tarif>();
		
		ArrayList<Bundle> bundles = Global.dbListeTarif.getListeTarifsFilters( filter);
		
		for(Bundle bundle : bundles){
			Tarif tarif = new Tarif();
			tarif.setCodeTarif(bundle.getString(Global.dbListeTarif.FIELD_LT_COD_TRF));
			tarif.setNomTarif(bundle.getString(Global.dbListeTarif.FIELD_LT_NOM_TRF));
			tarif.setFerme(bundle.getString(Global.dbListeTarif.FIELD_LT_FERME));
			tarifs.add(tarif);
		}
		
		return tarifs;
	}
	
	/**
	 * Récupère tous les tarifs à partir du code tarif et du filtre
	 * @param filter
	 * @return ArrayList<Tarif>
	 */
	public static ArrayList<Tarif> getAllTarifDetail(String codeTarif, String filter){
		ArrayList<Tarif> tarifs = new ArrayList<Tarif>();
		
		ArrayList<Bundle> bundles = Global.dbListeTarifsDetails.getListeTarifsDetailsFilters(codeTarif, filter);
		
		for(Bundle bundle : bundles){
			Tarif tarif = new Tarif();
			tarif.setCodeTarif(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_COD_TRF));
			tarif.setCodeArticle(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_COD_ART));
			tarif.setNomTarif(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_DESIGN));
			tarif.setCodeClient(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_COD_CLT));
			tarif.setDateDebut(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_DAT_DEB));
			tarif.setDateFin(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_DAT_FIN));
			tarif.setPrix(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_PRIX));
			tarif.setPrixMin(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_PRIX_MINI));
			tarif.setBloque(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_BLOQUE));
			tarif.setRemSodexo(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_REM_SODEXO));
			tarif.setPrixNet(bundle.getString(Global.dbListeTarifsDetails.FIELD_LTD_PRIX_NET));
			tarifs.add(tarif);
		}
		
		return tarifs;
	}
}
