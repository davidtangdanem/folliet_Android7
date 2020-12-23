package com.menadinteractive.printmodels;

import java.util.List;

import android.content.Context;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.dbKD546EchangeStock;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.plugins.Espresso;

public class Z420ModelEchangeStock {

	Context context;
	int MAXLEN=78;
	String CR="\n";

	public Z420ModelEchangeStock(Context c){
		context=c;
	}

	public String get(){	

		String rep = Preferences.getValue(context, Espresso.LOGIN, "0");
		//NOM DU COMMERCIAL
		dbSiteListeLogin login=new dbSiteListeLogin(Global.dbParam.getDB());
		structlistLogin structlogin=new structlistLogin();
		login.getLblNom(rep);

		String completeLine="";
		dbKD546EchangeStock  lin=new dbKD546EchangeStock(Global.dbParam.getDB());
		int nbrLine=lin.Count(false);

		com.menadinteractive.segafredo.db.dbKD546EchangeStock.structPassePlat pp= lin.loadHdr();

		String line ="";

		//Date et heure d'impression
		completeLine += CR;
		line = Fonctions.insertStr(line, "Date et heure d'impression du document : ", 4, 45, true,MAXLEN);
		completeLine+= line + CR;
		completeLine+= CR;
		line="";
		line = Fonctions.insertStr(line, 
				Fonctions.replaceSpecialsChars(Fonctions.YYYYMMDDhhmmss_to_dd_mm_yyyy_hh_mm_ss(Fonctions.getYYYYMMDDhhmmss())), 
				4, 30, true, MAXLEN);
		line = Fonctions.insertStr(line, "Magasin cédant : "+rep+" - "+login.getLblNom(rep), 46, 30, true,MAXLEN);
		completeLine += line + CR; 
		line = "";

		completeLine+= CR;
		completeLine+= CR;
		completeLine+= CR;
		completeLine+= CR;
		completeLine+= CR;
		line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		line=Fonctions.insertStr(line, "TRANSFERT DE STOCK", 18, 25, true,MAXLEN);
		completeLine+=line + CR;
		line="";
		line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		line=Fonctions.insertStr(line, "Magasin recevant : "+pp.FIELD_RECEVANT+" - "+login.getLblNom(pp.FIELD_RECEVANT) , 46, 35, true,MAXLEN);
		completeLine+= line +CR;

		line="";
		completeLine+=CR;
		completeLine+=CR;

		line=Fonctions.insertStr(line,Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()) , 5, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, "Ref DANEM : " , 31, 30, true,MAXLEN);
		completeLine+=line+CR;
		line="";
		line=Fonctions.insertStr(line,  pp.FIELD_NUMDOC, 31, 30, true,MAXLEN);
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

		//line+="TRANSFERT DE STOCK\n";
		//line+="Magasin cédant : IMPR_DEPOTINIT - IMPR_NOMDEPOTINIT\n";
		//line+="Magasin recevant : IMPR_DEPOTCIBLE - IMPR_NOMDEPOTCIBLE\n";
		//line+="Date du transfert : IMPR_DATE\n";
		//line+="Date et heure d'impression du document : IMPR_DATHEURE\n";
		//line+="Effectué en présence agent général : IMPR_AGENT\n";
		//line+="Ref DANEM : IMPR_NUMDOC\n\n";

//		line+=Fonctions.AddSpace("Code",12,1);//12
//		line+=Fonctions.AddSpace("Libellé",40,1);
//		line+=Fonctions.AddSpace("Unité",6,1);
//		line+=Fonctions.AddSpace("Qté Transf",10,0)+"\n";

		//les lignes 
		StringBuffer sb=new StringBuffer();
		//int y=imprimeLine(270,sb);
		completeLine+=imprimeLineTransfert();

		//On complète le vide avec des lignes blanches
		for (int j=0;j<5;j++){
			completeLine+= CR;
		}
		
		//on insert le commentaire
		line="";
		line=Fonctions.insertStr(line, "Note : "+pp.FIELD_COMMENT1, 6, 30, true,MAXLEN);
		completeLine+=line;
		
		for (int j=0;j<5;j++){
			completeLine+= CR;
		}
		
		line="";
		line=Fonctions.insertStr(line, "SIGNATURE AGENT  CEDANT               SIGNATURE AGENT RECEVANT                   ", 10, 65, true,MAXLEN);
		completeLine+= line + CR;
		
//		line+=sb;

//		y+=50;
//		line+="\n\nNote:\n";
//		y+=40;
//		line+="IMPR_COMMENT\n\n";
//
//		y+=50;


//		line+="     SIGNATURE AGENT  CEDANT               SIGN. AGENT RECEVANT                   \n" ; 


		//	    completeLine=line;
		//	    completeLine=completeLine.replace("IMPR_DATE",Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()));
		//	    completeLine=completeLine.replace("IMPR_DATHEURE",Fonctions.YYYYMMDDhhmmss_to_dd_mm_yyyy_hh_mm_ss(Fonctions.getYYYYMMDDhhmmss()));
		//	    completeLine=completeLine.replace("IMPR_DEPOTINIT", rep);
		//	    completeLine=completeLine.replace("IMPR_NOMDEPOTINIT", login.getLblNom(rep));


//		String isagent="";
//
//		login.getLblNom(pp.FIELD_RECEVANT);
//		completeLine=completeLine.replace("IMPR_DEPOTINIT", rep);
//		completeLine=completeLine.replace("IMPR_NOMDEPOTINIT", login.getLblNom(rep));
//
//		completeLine=completeLine.replace("IMPR_DEPOTCIBLE", pp.FIELD_RECEVANT);
//		completeLine=completeLine.replace("IMPR_NOMDEPOTCIBLE", login.getLblNom(pp.FIELD_RECEVANT));		 
//		completeLine=completeLine.replace("IMPR_NUMDOC", pp.FIELD_NUMDOC);	
//		completeLine=completeLine.replace("IMPR_COMMENT", pp.FIELD_COMMENT1);	

		completeLine=Z420ModelInventaire.utfToAscii(completeLine);
		return completeLine;


	}


	int imprimeLine(int currentY, StringBuffer sb)
	{
		String line="";

		line+="Code";
		line+="Libelle";
		line+="Unite";
		line+="Qte";


		dbKD546EchangeStock  lin=new dbKD546EchangeStock(Global.dbParam.getDB());
		List<com.menadinteractive.segafredo.db.dbKD546EchangeStock.structPassePlat> lines=lin.load();
		String totaline="";
		int nbrline=0;
		for (int i=0;i<lines.size();i++)
		{
			String detail=line;
			detail=detail.replace("#",(i+1)+"");
			detail=detail.replace("Code", Fonctions.AddSpace(lines.get(i).FIELD_PROCODE,12,1));
			detail=detail.replace("Libelle", Fonctions.AddSpace( Fonctions.Left(lines.get(i).FIELD_DESIGNATION,48) +"",40,1));
			detail=detail.replace("Unite", Fonctions.AddSpace( lines.get(i).FIELD_UV,6,1));
			detail=detail.replace("Qte", Fonctions.AddSpace( lines.get(i).FIELD_QTE,10,0))+"\n";


			totaline+=detail;
			nbrline++;
		}

		sb.append(totaline);
		//currentY=currentY+(nbrline*LINE_HEIGHT);

		return currentY;
	}

	String imprimeLineTransfert(){

		dbKD546EchangeStock  lin=new dbKD546EchangeStock(Global.dbParam.getDB());
		List<com.menadinteractive.segafredo.db.dbKD546EchangeStock.structPassePlat> lines=lin.load();
		String totaline="";
		for (int i=0;i<lines.size();i++){

			String line="";
			line=Fonctions.insertStr(line, lines.get(i).FIELD_PROCODE , 6, 10, true,MAXLEN);
			line=Fonctions.insertStr(line, Fonctions.Left(lines.get(i).FIELD_DESIGNATION,48) , 13, 22, true,MAXLEN);
			line=Fonctions.insertStr(line, lines.get(i).FIELD_QTE, 61, 7, false,MAXLEN); 

			//			String detail=line;
			//			detail=detail.replace("#",(i+1)+"");
			//			detail=detail.replace("Code", Fonctions.AddSpace(lines.get(i).FIELD_PROCODE,12,1));
			//			detail=detail.replace("Libelle", Fonctions.AddSpace( Fonctions.Left(lines.get(i).FIELD_DESIGNATION,48) +"",40,1));
			//			detail=detail.replace("Unite", Fonctions.AddSpace( lines.get(i).FIELD_UV,6,1));
			//			detail=detail.replace("Qte", Fonctions.AddSpace( lines.get(i).FIELD_QTE,10,0))+"\n";


			totaline+=line + CR;
		}

		return totaline;
	}


}
