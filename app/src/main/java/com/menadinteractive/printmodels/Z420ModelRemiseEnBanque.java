package com.menadinteractive.printmodels;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbKD546EchangeStock;
import com.menadinteractive.segafredo.db.dbKD981RetourBanqueEnt.structRetourBanque;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.remisebanque.Cheque;

public class Z420ModelRemiseEnBanque {

	Context context;
	int MAXLEN=78;
	String CR="\n";
	
	int NBR_LIGNE_DEBUT_FIN_PAGE	=39;
	int nbrline=0;
	boolean _mbchp=false;
	
	int type;
	
	public static int CHEQUE = 1;
	public static int ESPECE = 2;
	public static int CHEQUE_PORTEFEUILLE = 3;
	
	String montant_cheque = "", montant_cheque_portefeuille = "", montant_espece = "";
	

	public Z420ModelRemiseEnBanque(Context c){
		context=c;
	}

	public String getRemiseEnBanque(String numRemise, ArrayList<Encaissement> encaissements, String codeVRP, int type,boolean bchp){	 
		String completeLine="";
		
		nbrline=0;
		_mbchp=bchp;
		this.type = type;
		if(numRemise == null || numRemise.equals("")) return null;

		structRetourBanque remiseBanque = Global.dbKDRetourBanqueEnt.getRemiseBanque(numRemise);

		String rep = Preferences.getValue(context, Espresso.LOGIN, "0");
		//NOM DU COMMERCIAL
		dbSiteListeLogin login=new dbSiteListeLogin(Global.dbParam.getDB());
		structlistLogin structlogin=new structlistLogin();
		login.getLblNom(rep);

		String banque = Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_BANQUEREMISE, remiseBanque.CODEBANQUE);
		String agence = Global.dbParam.getComment(Global.dbParam.PARAM_BANQUEREMISE, remiseBanque.CODEBANQUE);
		String typeRemise = "";

		if(this.type == CHEQUE){
			typeRemise = "DE CHEQUES";
			
		}else if(this.type == ESPECE){
			typeRemise = "D'ESPECES";
		}
		
		if(this.type == CHEQUE_PORTEFEUILLE){
			typeRemise = "CHEQUE EN PORTEFEUILLE";
		}
		
		//Date et heure d'impression
		String line="";
		completeLine += CR;
		line = Fonctions.insertStr(line, "Date et heure d'impression du document : ", 4, 45, true,MAXLEN);
		line = Fonctions.insertStr(line, "Banque : ", 46, 30, true,MAXLEN);
		completeLine+= line + CR;
		completeLine+= CR;
		line="";
		line = Fonctions.insertStr(line, 
				Fonctions.replaceSpecialsChars(Fonctions.YYYYMMDDhhmmss_to_dd_mm_yyyy_hh_mm_ss(Fonctions.getYYYYMMDDhhmmss())), 
				4, 30, true, MAXLEN);
		line = Fonctions.insertStr(line, banque, 46, 30, true,MAXLEN);
		completeLine += line + CR; 
		line = "";

		completeLine+= CR;
		completeLine+= CR;
		completeLine+= CR;
		completeLine+= CR;
		completeLine+= CR;
		line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		if(this.type == CHEQUE_PORTEFEUILLE)
		{
			line=Fonctions.insertStr(line, typeRemise, 18, 25, true,MAXLEN);
				
		}
		else
		line=Fonctions.insertStr(line, "REMISE "+typeRemise, 18, 25, true,MAXLEN);
		line=Fonctions.insertStr(line, "Commercial : ", 46, 35, true,MAXLEN);
		completeLine+=line + CR;
		line="";
		line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		line=Fonctions.insertStr(line, rep+" - "+login.getLblNom(rep), 46, 35, true,MAXLEN);
		completeLine+= line +CR;

		line="";
		completeLine+=CR;
		completeLine+=CR;

		line=Fonctions.insertStr(line,Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()) , 5, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, "Ref DANEM : " , 31, 30, true,MAXLEN);
		completeLine+=line+CR;
		line="";
		line=Fonctions.insertStr(line,  remiseBanque.NUMCDE, 31, 30, true,MAXLEN);
		completeLine+= line ;

		//ligne14
		completeLine+= CR;
		//ligne15
		completeLine+= CR;
		//ligne16
		completeLine+= CR;
		//ligne17
		completeLine+= CR;
		//ligne18
		completeLine+= CR;
		//ligne19
		completeLine+= CR;

		//on place les nouvelles colonnes

		line="";
		line=Fonctions.insertStr(line, "Code client" , 6, 11, true,MAXLEN);
		//line=Fonctions.insertStr(line, "Nom client" , 23, 22, true,MAXLEN);
		line=Fonctions.insertStr(line, "Nom client" , 19, 26, true,MAXLEN);
		
		line=Fonctions.insertStr(line, "N de facture" , 38, 22, true,MAXLEN);
		line=Fonctions.insertStr(line, "Date" , 52, 22, true,MAXLEN);
		line=Fonctions.insertStr(line, "Montant" , 68, 22, true,MAXLEN);
		nbrline++;
		completeLine+=line+CR;

		//les lignes 
		StringBuffer sb=new StringBuffer();
		//int y=imprimeLine(270,sb);
		completeLine+=imprimeLineRemise(encaissements);

		//On complète le vide avec des lignes blanches
		for (int j=nbrline;j<NBR_LIGNE_DEBUT_FIN_PAGE;j++){
			completeLine+= CR;
		}
		
		completeLine+= CR;
		completeLine+= CR;
		
		//On utilise des variables globales pour calculer les totaux
		
		line="";
		String montant = "";
		//on choisit le montant par rapport au type des lignes
		if(this.type == CHEQUE){
			//montant = remiseBanque.MNT_CHEQUE+" "+context.getString(R.string.devise_eur);
			montant = montant_cheque;
		}else if(this.type == ESPECE){
			//montant = remiseBanque.MNT_ESPECE+" "+context.getString(R.string.devise_eur);
			montant = montant_espece;
		}else if(this.type == CHEQUE_PORTEFEUILLE){
			montant = montant_cheque_portefeuille;
		}
		
		line=Fonctions.insertStr(line, montant, 68, 10, true,MAXLEN);
		completeLine+=line;

		completeLine=Z420ModelInventaire.utfToAscii(completeLine);
		return completeLine;

	}

	String imprimeLineRemise(ArrayList<Encaissement> encaissements){

		dbKD546EchangeStock  lin=new dbKD546EchangeStock(Global.dbParam.getDB());
		List<com.menadinteractive.segafredo.db.dbKD546EchangeStock.structPassePlat> lines=lin.load();
		String totaline="";
		
		float total_cheque = 0, total_cheque_portefeuille = 0, total_espece = 0;
		for (Encaissement enc : encaissements){
			
			//on calcul les totaux
			if(enc.getTypePaiement() != null){
				if(enc.getTypePaiement().equals(Encaissement.TYPE_CHEQUE)){
					total_cheque += enc.getMontant();
				}
				
				if(enc.getTypePaiement().equals(Encaissement.TYPE_CHEQUEPORTEFEUILLE)){
					total_cheque_portefeuille += enc.getMontant();
				}
				
				if(enc.getTypePaiement().equals(Encaissement.TYPE_ESPECE)){
					total_espece += enc.getMontant();
				}
			}

			structClient client = Global.dbClient.load(enc.getCodeClient());

			String line="";	
			line=Fonctions.insertStr(line, enc.getCodeClient() , 6, 10, true,MAXLEN);
			if(client != null && client.NOM != null){
				//line=Fonctions.insertStr(line,  client.ENSEIGNE, 23, 22, true,MAXLEN);
				line=Fonctions.insertStr(line,  client.ENSEIGNE, 19, 26, true,MAXLEN);
			}
			if(enc.getListNumerosFactures() != null && enc.getListNumerosFactures().size() > 0){
				line=Fonctions.insertStr(line, enc.getListNumerosFactures().get(0), 38, 22, true,MAXLEN);
			}
			line=Fonctions.insertStr(line, Fonctions.YYYYMMDD_to_dd_mm_yyyy(enc.getDateCheque()) , 52, 22, true,MAXLEN);
			line=Fonctions.insertStr(line, Float.toString(enc.getMontant()) , 70, 22, true,MAXLEN);
			totaline+=line + CR;
			nbrline++;
		}
		
		montant_cheque = Float.toString(total_cheque);
		montant_cheque_portefeuille = Float.toString(total_cheque_portefeuille);
		montant_espece = Float.toString(total_espece);

		return totaline;
	}

	int imprimeLineCheque(int currentY,ArrayList<Cheque> cheques,StringBuffer sb)
	{
		//taille d'une ligne
		int LINE_HEIGHT=40;
		String line="";
		line+="^CF0,24";
		line+="^FO50,BORDER^GB200,40,1^FS";
		line+="^FO110,ESPACE^FDIMPR_NUMERO_CHEQUE^FS";
		line+="^FO250,BORDER^GB200,40,1^FS";
		line+="^FO255,ESPACE^FDIMPR_CBANQUE^FS";
		line+="^FO450,BORDER^GB140,40,1^FS";
		line+="^FO455,ESPACE^FDIMPR_DATECHEQUE^FS";
		line+="^FO590,BORDER^GB140,40,1^FS";
		line+="^FO610,ESPACE^FDIMPR_CMONTANT^FS";

		String totaline="";
		int nbrline=0;
		double resteDu = 0.00;
		for(Cheque c : cheques){
			String detail=line;

			detail=detail.replace("IMPR_NUMERO_CHEQUE",c.getNumeroCheque());
			//si la longueur est supérieur à 14 alors on coupe à 14 caracteres
			String banque = "";
			if(c.getLibelleBanque().length() > 14) banque = c.getLibelleBanque().substring(0, 13);
			else banque  = c.getLibelleBanque();
			detail=detail.replace("IMPR_CBANQUE", banque);
			detail=detail.replace("IMPR_DATECHEQUE", Fonctions.YYYYMMDD_to_dd_mm_yyyy(c.getDateCheque()));
			detail=detail.replace("IMPR_CMONTANT", Fonctions.GetDoubleToStringFormatDanem(c.getMontant(), "0.00"));

			detail=detail.replace("BORDER", (currentY)+"");
			detail=detail.replace("ESPACE", (currentY+5)+"");
			currentY+=LINE_HEIGHT;
			totaline+=detail;
			nbrline++;
		}

		sb.append(totaline);
		//currentY=currentY+(nbrline*LINE_HEIGHT);

		return currentY;
	}
}
