package com.menadinteractive.printmodels;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire.structPassePlat;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.plugins.Espresso;

public class Z420ModelInventaire {

	Context context;
	int MAXLEN=78;
	String CR="\n";
	int maxLineProduit = 35;
	int tailleBasDePage = 15;

	public Z420ModelInventaire(Context c){
		context=c;
	}

	private String getPages(String completeLine, int index, boolean isRecursiveCall){

		//completeLine="";
		dbKD543LinInventaire lin=new dbKD543LinInventaire(Global.dbParam.getDB());
		int nbrLine=lin.Count(false);

		if(index > 0){
			for(int j = 0;j<tailleBasDePage-2;j++){
				completeLine += CR;
			}
			
			//on ajoute le numéro de page
			int pageActuelle = 0;
			int nbPage = 0;

			nbPage = nbrLine/maxLineProduit;
			if(nbrLine%maxLineProduit > 0){
				nbPage++;
			}

			pageActuelle = index/maxLineProduit;
			if(index > maxLineProduit && index%maxLineProduit > 0){
				pageActuelle++;
			}
			
			//on est sur la page d'avant donc
			//pageActuelle--;
			
			String linePage=Fonctions.insertStr("", "Page "+pageActuelle+"/"+nbPage, 68, 10, true,MAXLEN);
			completeLine+=linePage+CR;
			completeLine+= CR;
			completeLine+= CR;
			completeLine+= CR;
			completeLine+= CR;
		}

		String rep = Preferences.getValue(context, Espresso.LOGIN, "0");
		//NOM DU COMMERCIAL
		dbSiteListeLogin login=new dbSiteListeLogin(Global.dbParam.getDB());
		structlistLogin structlogin=new structlistLogin();
		login.getLblNom(rep);

		String isagent="";
		structPassePlat pp= lin.loadHdr();
		if (Fonctions.convertToBool(pp.FIELD_LIGNEINV_DUO))
			isagent="Oui";
		else
			isagent="Non";

		String line ="";

		//Date et heure d'impression
		completeLine += CR;
		line = Fonctions.insertStr(line, "Date de l'inventaire : "+Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()), 2, 45, true,MAXLEN);
		completeLine += line+CR;

		line="";
		line = Fonctions.insertStr(line, rep+" - "+login.getLblNom(rep), 46, 30, true,MAXLEN);
		completeLine+=line+CR;

		completeLine += CR;

		line="";
		line = Fonctions.insertStr(line, "Date et heure d'impression du document : ", 2, 45, true,MAXLEN);
		completeLine+= line + CR;

		line="";
		line = Fonctions.insertStr(line, 
				Fonctions.replaceSpecialsChars(Fonctions.YYYYMMDDhhmmss_to_dd_mm_yyyy_hh_mm_ss(Fonctions.getYYYYMMDDhhmmss())), 
				2, 30, true, MAXLEN);
		completeLine += line + CR; 
		completeLine += CR;

		line="";
		line = Fonctions.insertStr(line, "Effectué en présence agent général : "+isagent, 2, 45, true,MAXLEN);
		completeLine+= line + CR;

		//8 lignes
		completeLine+= CR;
		line="";
		line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		line=Fonctions.insertStr(line, "INVENTAIRE DEPOT", 18, 25, true,MAXLEN);
		completeLine+=line + CR;
		line="";
		line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		completeLine+= line +CR;

		line="";
		completeLine+=CR;
		completeLine+=CR;
		
		line=Fonctions.insertStr(line, "Ref DANEM : " , 32, 30, true,MAXLEN);	
		completeLine+=line+CR;
		
		line="";
		line=Fonctions.insertStr(line,  pp.FIELD_LIGNEINV_NUMDOC, 32, 30, true,MAXLEN);
		completeLine+= line ;

		//ligne14
		//completeLine+= CR;
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

		//les lignes 
		StringBuffer sb=new StringBuffer();
		//int y=imprimeLine(270,sb);
		completeLine=imprimeLineInventaire(index, completeLine);

		if(!isRecursiveCall){
			//On complète le vide avec des lignes blanches
			for (int j=0;j<5;j++){
				completeLine+= CR;
			}

			//on insert le commentaire
			line="";
			line=Fonctions.insertStr(line, "Note : "+pp.FIELD_LIGNEINV_COMMENT1, 6, 30, true,MAXLEN);
			completeLine+=line;

			for (int j=0;j<5;j++){
				completeLine+= CR;
			}

			line="";
			line=Fonctions.insertStr(line, "SIGNATURE AGENT                    SIGNATURE AGENT GENERAL                   ", 10, 65, true,MAXLEN);
			completeLine+= line + CR;

			completeLine=utfToAscii(completeLine);
			Log.d("TAG",completeLine);
			
			completeLine+= CR;
			
			//on ajoute le numéro de page
			int pageActuelle = 0;
			int nbPage = 0;

			nbPage = nbrLine/maxLineProduit;
			if(nbrLine%maxLineProduit > 0){
				nbPage++;
			}

			pageActuelle = index/maxLineProduit;
			if(index > maxLineProduit && index%maxLineProduit > 0){
				pageActuelle++;
			}
			
			String linePage=Fonctions.insertStr("", "Page "+nbPage+"/"+nbPage, 68, 10, true,MAXLEN);
			//completeLine+=linePage+CR; //on enleve le saut de ligne 1.6.3
			completeLine+=linePage;

		}

		return completeLine;
	}

	public String get(){	 

		String completeLine = "";

		completeLine = getPages(completeLine, 0, false);

		return completeLine;
	}


	int imprimeLine(int currentY, StringBuffer sb){

		String line="";

		line+="Code";
		line+="Libelle";
		line+="Unite";
		line+="Qte";


		dbKD543LinInventaire lin=new dbKD543LinInventaire(Global.dbParam.getDB());
		List<structPassePlat> lines=lin.load();
		String totaline="";
		int nbrline=0;
		for (int i=0;i<lines.size();i++)
		{
			String detail=line;
			detail=detail.replace("#",(i+1)+"");
			detail=detail.replace("Code", Fonctions.AddSpace(lines.get(i).FIELD_LIGNEINV_PROCODE,12,1));
			detail=detail.replace("Libelle", Fonctions.AddSpace( Fonctions.Left(lines.get(i).FIELD_LIGNEINV_DESIGNATION,48) +"",40,1));
			detail=detail.replace("Unite", Fonctions.AddSpace( lines.get(i).FIELD_LIGNEINV_UV,6,1));
			detail=detail.replace("Qte", Fonctions.AddSpace( lines.get(i).FIELD_LIGNEINV_QTE,9,0))+"\n";


			totaline+=detail;
			nbrline++;
		}

		sb.append(totaline);
		//currentY=currentY+(nbrline*LINE_HEIGHT);

		return currentY;
	}

	String imprimeLineInventaire(int index, String completeLine){

		dbKD543LinInventaire lin=new dbKD543LinInventaire(Global.dbParam.getDB());
		List<structPassePlat> lines=lin.loadSupZero();
		//List<structPassePlat> lines=lin.load();
		
		String totaline="";
		int nbrline=0;
		for (int i=index;i<lines.size();i++){
			//			String detail=line;
			//			detail=detail.replace("#",(i+1)+"");
			//			detail=detail.replace("Code", Fonctions.AddSpace(lines.get(i).FIELD_LIGNEINV_PROCODE,12,1));
			//			detail=detail.replace("Libelle", Fonctions.AddSpace( Fonctions.Left(lines.get(i).FIELD_LIGNEINV_DESIGNATION,48) +"",40,1));
			//			detail=detail.replace("Unite", Fonctions.AddSpace( lines.get(i).FIELD_LIGNEINV_UV,6,1));
			//			detail=detail.replace("Qte", Fonctions.AddSpace( lines.get(i).FIELD_LIGNEINV_QTE,10,0))+"\n";

			String line="";
			
				line=Fonctions.insertStr(line, lines.get(i).FIELD_LIGNEINV_PROCODE , 6, 10, true,MAXLEN);
				line=Fonctions.insertStr(line, Fonctions.Left(lines.get(i).FIELD_LIGNEINV_DESIGNATION,48) , 13, 22, true,MAXLEN);
				if(lines.get(i).FIELD_LIGNEINV_QTE.length() < 7){
					line=Fonctions.insertStr(line, lines.get(i).FIELD_LIGNEINV_QTE, 61, 7, false,MAXLEN);
				}else{
					line=Fonctions.insertStr(line, "999999", 61, 7, false,MAXLEN);
				}
				 
				totaline+=line+CR;
				nbrline++;
		
			
				int modulo = i%(maxLineProduit-1);
				

			if(modulo == 0 && i != 0 && i+1 != lines.size()) {
				return getPages(completeLine + totaline, i+1, true);
			}
		}

		//currentY=currentY+(nbrline*LINE_HEIGHT);

		return completeLine+totaline;
	}

	//doc ZPL page  156
	static public String utfToAscii(String chaine)
	{

		chaine=chaine.replace("é", "e");
		chaine=chaine.replace("è", "e");
		chaine=chaine.replace("ê", "e");
		chaine=chaine.replace("à", "a");
		chaine=chaine.replace("â", "a");
		chaine=chaine.replace("î", "i");
		chaine=chaine.replace("ï", "i");
		chaine=chaine.replace("ô", "o");
		chaine=chaine.replace("ö", "o");

		return chaine;

	}
}
