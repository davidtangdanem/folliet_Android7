package com.menadinteractive.segafredo.tournee;

import android.database.Cursor;
import android.os.Bundle;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.dbSiteHabitudes;
import com.menadinteractive.segafredo.db.dbSiteProduit;
import com.menadinteractive.segafredo.db.dbTournee;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static com.menadinteractive.segafredo.db.dbSiteHabitudes.CODE_CLIENT_CHARGEMENT;

public class Tournee {

	String codeclient;
	String datetournee;
	String num_inter;
	String tournee;

	//Détail
    String detailcodeclient;
    String detailnomclient;
    String detailvilleclient;
    String detailcodetournee;
    String detailperiodicite;
    String detaildatederfac;
    String detailnbjour;

    //Chargement
	String charcodearticle;
	String charlibellearticle;
	String charstockmini;
	String chardatelast;
	String charsaisie;
	String charfamille;

	public String getCodeClient(){
		return codeclient;
	}
	public void setCodeClient(String _codeclient){
		this.codeclient = _codeclient;
	}

	public String getDateTournee(){
		return datetournee;
	}
	public void setDateTournee(String _datetournee){
		this.datetournee = _datetournee;
	}

	public String getNum_Inter(){
		return num_inter;
	}
	public void setNum_Inter(String _num_inter){
		this.num_inter = _num_inter;
	}

	public String getTournee(){
		return tournee;
	}
	public void setTournee(String _tournee){
		this.tournee = _tournee;
	}

	//Détails
    public String getdetailcodeclient(){
        return detailcodeclient;
    }
    public void setdetailcodeclient(String _detailcodeclient){
        this.detailcodeclient = _detailcodeclient;
    }
    public String getdetailnomclient(){
        return detailnomclient;
    }
    public void setdetailnomclient(String _detailnomclient){
        this.detailnomclient = _detailnomclient;
    }

    public String getdetailvilleclient(){
        return detailvilleclient;
    }
    public void setdetailvilleclient(String _detailvilleclient){
        this.detailvilleclient = _detailvilleclient;
    }

    public String getdetailcodetournee(){
        return detailcodetournee;
    }
    public void setdetailcodetournee(String _detailcodetournee){
        this.detailcodetournee = _detailcodetournee;
    }

    public String getdetailperiodicite(){
        return detailperiodicite;
    }
    public void setdetailperiodicite(String _detailperiodicite){
        this.detailperiodicite = _detailperiodicite;
    }
    public String getdetaildatederfac(){
        return detaildatederfac;
    }
    public void setdetaildatederfac(String _detaildatederfac){
        this.detaildatederfac = _detaildatederfac;
    }

    public String getdetailnbjour(){
        return detailnbjour;
    }
    public void setdetailnbjour(String _detailnbjour){
        this.detailnbjour = _detailnbjour;
    }

    //chargement
	public String getcharcodearticle(){
		return charcodearticle;
	}
	public void setcharcodearticle(String _charcodearticle){
		this.charcodearticle = _charcodearticle;
	}
	public String getcharlibellearticle(){
		return charlibellearticle;
	}
	public void setcharlibellearticle(String _charlibellearticle){
		this.charlibellearticle = _charlibellearticle;
	}

	public String getcharstockmini(){
		return charstockmini;
	}
	public void setcharstockmini(String _charstockmini){
		this.charstockmini = _charstockmini;
	}


	public String getchardatelast(){
		return chardatelast;
	}
	public void setchardatelast(String _chardatelast){
		this.chardatelast = _chardatelast;
	}

	public String getcharsaisie(){
		return charsaisie;
	}
	public void setcharsaisie(String _charsaisie){
		this.charsaisie = _charsaisie;
	}

	public String getcharfamille(){
		return charfamille;
	}
	public void setcharfamille(String _charfamille){
		this.charfamille = _charfamille;
	}

	static public class structTournee {

		public structTournee() {
			this.ID = -1;
		}

		public long ID;
		public String CHAR_CODECLIENT;
		public String CHAR_CODEARTICLE;
		public String CHAR_LIBELLEARTICLE;
		public String CHAR_STOCKMINI;
		public String CHAR_DATELAST;
		public String CHAR_SAISIE;
		public String CHAR_FAMILLE;


	}


	public Tournee(){}

	public Tournee(String _codeclient, String _datetournee, String _num_inter,String _tournee){
		this.codeclient = _codeclient;
		this.datetournee = _datetournee;
		this.num_inter = _num_inter;
		this.tournee = _tournee;

	}
	
	/**
	 * Récupère tous les tarifs à partir du filtre
	 * @param filter
	 * @return ArrayList<Tarif>
	 */
	public static ArrayList<Tournee> getAllTournee(String filter){
		ArrayList<Tournee> tournees = new ArrayList<Tournee>();
		dbTournee to = new dbTournee(Global.dbParam.getDB());
		ArrayList<Bundle> bundles =to.getListeTourneeFilters("");
		
		for(Bundle bundle : bundles){
			Tournee tournee = new Tournee();
			tournee.setCodeClient(bundle.getString(dbTournee.FIELD_CODECLI));
			tournee.setDateTournee(bundle.getString(dbTournee.FIELD_DATE));
			tournee.setNum_Inter(bundle.getString(""));
			tournee.setTournee(bundle.getString(dbTournee.FIELD_TOURNEE));

			tournees.add(tournee);
		}
		
		return tournees;
	}
	
	/**
	 * Récupère tous les tarifs à partir du code tarif et du filtre
	 * @param filter
	 * @return ArrayList<Tarif>
	 */
	public static ArrayList<Tournee> getAllTarifDetail(String codetournee, String filter){
		ArrayList<Tournee> tarifs = new ArrayList<Tournee>();
        dbTournee to = new dbTournee(Global.dbParam.getDB());
		ArrayList<Bundle> bundles = to.getListeTourneeDetailsFilters2(codetournee, filter);
		
		for(Bundle bundle : bundles){
			Tournee tarif = new Tournee();
			tarif.setdetailcodeclient(bundle.getString("CODECLI"));
			tarif.setdetailnomclient(bundle.getString("NOM"));
			tarif.setdetailvilleclient(bundle.getString("VILLE"));
			tarif.setdetailcodetournee(bundle.getString("TOURNEE"));
			tarif.setdetailperiodicite(bundle.getString("PERIODICITE"));
			tarif.setdetaildatederfac(bundle.getString("DATELASTFAC"));
			tarif.setdetailnbjour(bundle.getString("NBJOUR"));

            tarifs.add(tarif);
		}
		
		return tarifs;
	}

	public static ArrayList<Tournee.structTournee> getChargement(String codetournee, String filter){
		ArrayList<Tournee.structTournee> tarifs = new ArrayList<Tournee.structTournee>();
		dbTournee to = new dbTournee(Global.dbParam.getDB());
		ArrayList<Bundle> bundles = to.getListeTourneeDetailsFiltersChargement(codetournee, "");

		ArrayList<String> bundlescli=null;
		bundlescli = new ArrayList<String>();
		Boolean bres=false;
		for(Bundle bundle : bundles){

			String codeclient =  bundle.getString("CODECLI");
			bundlescli.add(codeclient);
			bres=true;

		}
		//inutil, ajouté en dur dans getHabitudeFromCodeCli
		//Ajout des lignes CODE_CLIENT_CHARGEMENT
		/*if ( bres == true )
		{
			bundlescli.add(CODE_CLIENT_CHARGEMENT);

		}*/

		if(bres==true)
		 {
			Tournee tarif = new Tournee();

			//dbSiteHabitudes.structHabitude habitude = Global.dbSiteHabitudes.getHabitudeFromCodeCli(bundlescli);
			ArrayList<Bundle> bundless = Global.dbSiteHabitudes.getHabitudeFromCodeCli(bundlescli,codetournee);

			for(Bundle bundle : bundless){

				Tournee.structTournee Char = new Tournee.structTournee();
				/*Char.setcharcodearticle(bundle.getString("COD_ART"));
				Char.setcharlibellearticle(bundle.getString("COD_CLT"));
				Char.setcharstockmini(bundle.getString("STK_CLT"));
				Char.setchardatelast(bundle.getString("LASTDATE"));
				Char.setcharsaisie(bundle.getString("SAISIE"));*/
				Char.CHAR_CODECLIENT=bundle.getString("COD_CLIENT");
				Char.CHAR_CODEARTICLE=bundle.getString("COD_ART");
				Char.CHAR_LIBELLEARTICLE=bundle.getString("COD_CLT");
				Char.CHAR_STOCKMINI=bundle.getString("STK_CLT");
				Char.CHAR_DATELAST=bundle.getString("LASTDATE");
				Char.CHAR_SAISIE=bundle.getString("SAISIE");
				Char.CHAR_FAMILLE=bundle.getString("FAMILLE");

				tarifs.add(Char);

			}

		}

		return tarifs;
	}

	public static ArrayList<Tournee.structTournee> getChargement2(String codetournee, String filter,String filtreFam){
		ArrayList<Tournee.structTournee> tarifs = new ArrayList<Tournee.structTournee>();
		dbTournee to = new dbTournee(Global.dbParam.getDB());
		ArrayList<Bundle> bundles = to.getListeTourneeDetailsFiltersChargement(codetournee, filter);

		ArrayList<String> bundlescli=null;
		bundlescli = new ArrayList<String>();
		Boolean bres=false;
		for(Bundle bundle : bundles){
			String codeclient =  bundle.getString("CODECLI");
			bundlescli.add(codeclient);
			bres=true;
		}

		if(bres==true)
		{
			Tournee tarif = new Tournee();

			//dbSiteHabitudes.structHabitude habitude = Global.dbSiteHabitudes.getHabitudeFromCodeCli(bundlescli);
			ArrayList<Bundle> bundless = Global.dbSiteHabitudes.getAllArtFromCodeCli(bundlescli,filter,filtreFam);

			for(Bundle bundle : bundless){

				Tournee.structTournee Char = new Tournee.structTournee();
				/*Char.setcharcodearticle(bundle.getString("COD_ART"));
				Char.setcharlibellearticle(bundle.getString("COD_CLT"));
				Char.setcharstockmini(bundle.getString("STK_CLT"));
				Char.setchardatelast(bundle.getString("LASTDATE"));
				Char.setcharsaisie(bundle.getString("SAISIE"));*/
				Char.CHAR_CODEARTICLE=bundle.getString("COD_ART");
				Char.CHAR_LIBELLEARTICLE=bundle.getString("COD_CLT");
				Char.CHAR_DATELAST=bundle.getString("LASTDATE");
				Char.CHAR_SAISIE=bundle.getString("SAISIE");
				Char.CHAR_FAMILLE=bundle.getString("FAMILLE");

				tarifs.add(Char);
			}

		}

		return tarifs;
	}

	public static ArrayList<Tournee> getChargementPrincipal(String codetournee, String filter){
		ArrayList<Tournee> tarifs = new ArrayList<Tournee>();
		dbTournee to = new dbTournee(Global.dbParam.getDB());
		ArrayList<Bundle> bundles = to.getListeTourneeDetailsFiltersChargement(codetournee, "");

		ArrayList<String> bundlescli=null;
		bundlescli = new ArrayList<String>();
		Boolean bres=false;
		for(Bundle bundle : bundles){

			String codeclient =  bundle.getString("CODECLI");
			bundlescli.add(codeclient);
			bres=true;


		}

		if(bres==true)
		{
			Tournee tarif = new Tournee();

			//dbSiteHabitudes.structHabitude habitude = Global.dbSiteHabitudes.getHabitudeFromCodeCli(bundlescli);
			ArrayList<Bundle> bundless = Global.dbSiteHabitudes.getHabitudeFromCodeCli(bundlescli, null);

			for(Bundle bundle : bundless){

				Tournee Char = new Tournee();
				Char.setcharcodearticle(bundle.getString("COD_ART"));
				Char.setcharlibellearticle(bundle.getString("COD_CLT"));
				Char.setcharstockmini(bundle.getString("STK_CLT"));
				Char.setchardatelast(bundle.getString("LASTDATE"));
				Char.setcharsaisie(bundle.getString("SAISIE"));


				tarifs.add(Char);

			}
		}

		return tarifs;
	}

	public static class Comparators {



		public static Comparator<Tournee.structTournee> CHAR_FAMILLE = new Comparator<Tournee.structTournee>() {
			@Override
			public int compare(Tournee.structTournee o1, Tournee.structTournee o2) {
				if (o1 == null) {
					return (o2 == null) ? 0 : -1;
				}
				if (o2 == null) {
					return 1;
				}
				if (o1 == null) {
					return (o2.CHAR_FAMILLE == null) ? 0 : -1;
				}
				if (o2.CHAR_FAMILLE == null) {
					return 1;
				}
				if (o1.CHAR_FAMILLE.equals("")) {
					return (o2.CHAR_FAMILLE.equals("")) ? 0 : -1;
				}
				if (o2.CHAR_FAMILLE.equals("")) {
					return 1;
				}
				return o1.CHAR_FAMILLE.compareTo(o2.CHAR_FAMILLE);

			}
		};
	}



}
