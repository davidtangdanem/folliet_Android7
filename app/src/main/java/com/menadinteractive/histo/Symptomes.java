package com.menadinteractive.histo;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.tarif.Tarif;

import android.os.Bundle;

public class Symptomes {
	
	String machine;
	String equipement;
	String cod_symp;
	String lib_symp;
	boolean isChecked;
	
	

	
	
	public String getmachine(){
		return machine;
	}
	public void setmachine(String _machine){
		this.machine = _machine;
	}
	
	public String getequipement(){
		return equipement;
	}
	public void setequipement(String _equipement){
		this.equipement = _equipement;
	}
	
	public String getcod_symp(){
		return cod_symp;
	}
	public void setcod_symp(String _cod_symp){
		this.cod_symp = _cod_symp;
	}
	
	public String getlib_symp(){
		return lib_symp;
	}
	public void setlib_symp(String _lib_symp){
		this.lib_symp = _lib_symp;
	}
	public boolean getChecked(){ return isChecked; }
	public void setChecked(boolean _isChecked){ isChecked = _isChecked;}
	
	
	
	public Symptomes(){}
	
	public Symptomes(String _machine, String _equipement, String _cod_symp, String _lib_symp){
		this.machine = _machine;
		this.equipement = _equipement;
		this.cod_symp = _cod_symp;
		this.lib_symp = _lib_symp;
		
	}
	
	/**
	 * Récupère tous les symptomes à partir du filtre
	 * @param filter
	 * @return ArrayList<Tarif>
	 */
	public static ArrayList<Symptomes> getAllSymptomes(String machine,String equipement,String filter,String stbAutres){
		ArrayList<Symptomes> Symptomes = new ArrayList<Symptomes>();
		
		
		ArrayList<Bundle> bundles = Global.dbSymptomes.getSymptomesFilters(machine,equipement,filter,stbAutres);
			
		
		for(Bundle bundle : bundles){
			Symptomes symptome = new Symptomes();
			
			symptome.setmachine(bundle.getString(Global.dbSymptomes.FIELD_MACHINE));
			symptome.setequipement(bundle.getString(Global.dbSymptomes.FIELD_EQUIPEMENT));
			symptome.setcod_symp(bundle.getString(Global.dbSymptomes.FIELD_COD_SYMP));
			symptome.setlib_symp(bundle.getString(Global.dbSymptomes.FIELD_LIB_SYMP));
			
			
			Symptomes.add(symptome);
		}
		Symptomes symptomeAutre = new Symptomes();
		
		symptomeAutre.setmachine("---");
		symptomeAutre.setequipement("---");
		symptomeAutre.setcod_symp("---");
		symptomeAutre.setlib_symp("Autre");
		
		
		Symptomes.add(symptomeAutre);
		
		return Symptomes;
	}
	
	
}
