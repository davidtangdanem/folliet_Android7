package com.menadinteractive.histo;

import java.util.ArrayList;

import android.os.Bundle;

import com.menadinteractive.segafredo.communs.Global;

public class Materiel {
	
	String codeArticle;
	String codeClient;
	String libArticle;
	String typeContrat;
	String numeroSerie;
	String codeVRP;
	String dateInstallation;
	String dateFin;
	boolean isChecked;
	
	public String getCodeArticle(){ return codeArticle; }
	public void setCodeArticle(String _codeArticle){ codeArticle = _codeArticle;}
	
	public String getCodeClient(){ return codeClient; }
	public void setCodeClient(String _codeClient){ codeClient = _codeClient;}
	
	public String getLibelleArticle(){ return libArticle; }
	public void setLibelleArticle(String _libArticle){ libArticle = _libArticle;}
	
	public String getTypeContrat(){ return typeContrat; }
	public void setTypeContrat(String _typeContrat){ typeContrat = _typeContrat;}
	
	public String getNumeroSerie(){ return numeroSerie; }
	public void setNumeroSerie(String _numeroSerie){ numeroSerie = _numeroSerie;}
	
	public String getCodeVRP(){ return codeVRP; }
	public void setCodeVRP(String _codeVRP){ codeVRP = _codeVRP;}
	
	public String getDateInstallation(){ return dateInstallation; }
	public void setDateInstallation(String _dateInstallation){ dateInstallation = _dateInstallation;}
	
	public String getDateFin(){ return dateFin; }
	public void setDateFin(String _dateFin){ dateFin = _dateFin;}
	
	public boolean getChecked(){ return isChecked; }
	public void setChecked(boolean _isChecked){ isChecked = _isChecked;}
	
	public Materiel(){ }
	
	/**
	 * Récupération de la liste des matériels à partir du code client et du filtre
	 * @param codeClient
	 * @param filter
	 * @return ArrayList<Materiel>
	 */
	public static ArrayList<Materiel> getAllMateriel(String codeClient, String filter){
		ArrayList<Materiel> materiels = new ArrayList<Materiel>();
		
		ArrayList<Bundle> bundles = Global.dbMaterielClient.getMaterielClientFilters(codeClient,  filter,"");
		
		for(Bundle bundle : bundles){
			Materiel materiel = new Materiel();
			materiel.setCodeArticle(bundle.getString(Global.dbMaterielClient.FIELD_CODEART));
			materiel.setCodeClient(bundle.getString(Global.dbMaterielClient.FIELD_CODECLIENT));
			materiel.setLibelleArticle(bundle.getString(Global.dbMaterielClient.FIELD_LIBART));
			materiel.setTypeContrat(bundle.getString(Global.dbMaterielClient.FIELD_TYPECONTRAT));
			materiel.setNumeroSerie(bundle.getString(Global.dbMaterielClient.FIELD_NUMSERIEFAB));
			materiel.setCodeVRP(bundle.getString(Global.dbMaterielClient.FIELD_CODVRP));
			materiel.setDateInstallation(bundle.getString(Global.dbMaterielClient.FIELD_DATEINST));
			materiel.setDateFin(bundle.getString(Global.dbMaterielClient.FIELD_DATEFIN));
			materiels.add(materiel);
		}
		
		return materiels;
	}

}
