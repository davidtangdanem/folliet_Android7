package com.menadinteractive.printmodels;

import java.util.ArrayList;
import java.util.List;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.plugins.Espresso;

import android.content.Context;

public class Z420ModelRapport extends BaseActivity{
	
	Context context;
	boolean m_bduplicata=false;
	int NBR_LIGNE_ARTICLE			=35;
	int NBR_LIGNE_DEBUT_FIN_PAGE	=39;
	int LNG_CLI_CODE = 250;
	int nbrline=0;
	boolean isClientST = false;
	int nbPages=1;

	public static final int TYPE_FACTURE = 1;
	public static final int TYPE_BL = 2;
	public static final int TYPE_BLCHIFFREE = 3;
	public static final int TYPE_RA = 4;
	public static final int TYPE_BCOMMANDE = 5;
	
	
	String m_date="";
	String m_numeroInter="";
	String m_refclient="";
	String m_nomtechnicien="";
	String m_objetIntervention="";
	String m_travauxrealise="";
	String m_machineconcerne="";
	String m_nommachine="";
	String m_heurearrive="";
	String m_heuredepart="";
	String m_nomcontact="";
	
	
	
	
	
	
	
	
	
	int type = 0;

	String codeRep = null;

	public Z420ModelRapport(Context c, String typedoc, String codeRep,String date,String numerointer,String refclient,String nomtechnicien,String objetintervention,String travauxrealise,String machineconcerne,String heurearrive,String heuredepart,String nomcontact,String nommachine){
		 m_date=date;
		 m_numeroInter=numerointer;
		 m_refclient=refclient;
		 m_nomtechnicien=nomtechnicien;
		 m_objetIntervention=objetintervention;
		 m_travauxrealise=travauxrealise;
		 m_machineconcerne=machineconcerne;
		 m_heurearrive=heurearrive;
		 m_heuredepart=heuredepart;
		 m_nomcontact=nomcontact;
		 m_nommachine=nommachine;
		
		context=c;
		this.codeRep = codeRep;
		if (typedoc.equals(TableSouches.TYPEDOC_FACTURE)) this.type=TYPE_FACTURE;
		if (typedoc.equals(TableSouches.TYPEDOC_BL)) this.type=TYPE_BL;
		if (typedoc.equals(TableSouches.TYPEDOC_BC)) this.type=TYPE_BLCHIFFREE; 
		if (typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE)) this.type=TYPE_BCOMMANDE; 
		if (typedoc.equals(TableSouches.TYPEDOC_RAPPORT)) this.type=TYPE_RA; 
		
		nbrline = 0;
	}

	public String getFacture(String numfact,String codecli,boolean bduplicata,String typedoc, int page)
	{	 
		nbrline=0;
		String completeLine="";
		m_bduplicata=bduplicata;
		try{
			if(codecli != null && codecli.equals("ST"+codeRep)){
				isClientST = true;
			}

		}catch(Exception ex){
			return "Erreur lors de l'impression!";
		}
		
		//COORD DU CLIENT
		TableClient cli=new TableClient(Global.dbParam.getDB());
		structClient structcli=new structClient();
		if (cli.getClient(codecli, structcli, new StringBuilder())==false)
		{
			return "";
		}


		//COORD DU CLIENT FACT
		/*TableClient clifact=new TableClient(Global.dbParam.getDB());
		structClient structclifact=new structClient();

		String codeclifact=Fonctions.GetStringDanem(structcli.CODEFACT);
		if (codeclifact.trim().equals(""))
			codeclifact=codecli;

		if (cli.getClient(codeclifact, structclifact, new StringBuilder())==false)
		{
			return "";
		}*/


		//on découpe le commentaire en 4 lignes si nécessaire si client 8888
		//30 caractères par ligne
		String[] tabCom = null;
		boolean isClientTemp = false;
		/*String[] tabCom = null;
		boolean isClientTemp = false;
		if(Fonctions.GetStringDanem(structcli.CODE).trim().equals("C08888"+codeRep)){
			isClientTemp = true;
			if(Fonctions.GetStringDanem(structcde.COMMENTCDE) != null && !Fonctions.GetStringDanem(structcde.COMMENTCDE).equals("")){
				tabCom = Fonctions.cutString(Fonctions.ReplaceGuillemet(structcde.COMMENTCDE), 30);
			}
		}*/
	
		String stTypeDoc="Original";
		if(m_bduplicata==true)
			stTypeDoc="Duplicata";
		int MAXLEN=78;
		String CR="\n";
		String line ="";

		//ligne1
		line=Fonctions.insertStr(line, "CAFES FOLLIET", 4, 30, true,MAXLEN);
		if(isClientTemp && tabCom != null && tabCom.length > 0){
			line=Fonctions.insertStr(line, tabCom[0], 46, 88, true,MAXLEN);
		}else{
			if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(Fonctions.GetStringDanem(structcli.ENSEIGNE).trim()), 46, 35, true,MAXLEN);
		}
		completeLine+= line +CR;

		//ligne2
		line="";
		line=Fonctions.insertStr(line, "683, rue de Chantabord", 4, 30, true,MAXLEN);
		if(isClientTemp && tabCom != null && tabCom.length > 1){
			line=Fonctions.insertStr(line, tabCom[1], 46, 88, true,MAXLEN);
		}else{
			//			if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(structcli.ADR1), 46, 35, true,MAXLEN);
			if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(structcli.NOM.trim()), 46, 35, true,MAXLEN);
		}
		completeLine+= line +CR ;
		//ligne3
		line="";
		line=Fonctions.insertStr(line, "Z.I. de Bissy CS 70425", 4, 30, true,MAXLEN);
		if(isClientTemp && tabCom != null && tabCom.length > 2){
			line=Fonctions.insertStr(line, tabCom[2], 46, 88, true,MAXLEN);
		}else{
			if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(structcli.ADR1), 46, 35, true,MAXLEN);
		}
		completeLine+= line +CR ;
		
		//ligne4
		line="";
		line=Fonctions.insertStr(line, "73004 CHAMBERY Cedex", 4, 30, true,MAXLEN);
		if(isClientTemp && tabCom != null && tabCom.length > 3){
			line=Fonctions.insertStr(line, tabCom[3], 46, 88, true,MAXLEN);
		}else{
			if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(structcli.ADR2), 46, 35, true,MAXLEN);
		}
		completeLine+= line +CR;
		dbSiteListeLogin login=new dbSiteListeLogin(Global.dbParam.getDB());
		structlistLogin rep=new structlistLogin();
		login.getlogin(Preferences.getValue(context, Espresso.LOGIN, "0"), rep, new StringBuilder());

		
		//ligne6
		line="";
		line=Fonctions.insertStr(line, "Tel: "+Fonctions.GetStringDanem(rep.GSM), 4, 30, true,MAXLEN);
		if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(structcli.CP+" "+structcli.VILLE), 46, 35, true,MAXLEN);
		completeLine+= line +  CR;

		
		//ligne7
		line="";
		line=Fonctions.insertStr(line, "Fax: "+Fonctions.GetStringDanem(rep.EMAIL), 4, 30, true,MAXLEN);
		completeLine+= line +CR ;

		//ligne8
		line="";
		line=Fonctions.insertStr(line, "Service Technique: 04 79 62 85 48", 4, 33, true,MAXLEN);
		completeLine+= line +CR ;
		

		//Insertion mail et site web
		line="";
		line=Fonctions.insertStr(line, "Email : contacteznous@cafes-folliet.com", 4, 40, true,MAXLEN);
		completeLine+= line +CR ;

		line="";
		line=Fonctions.insertStr(line, "Site Web : www.cafes-folliet.com", 4, 40, true,MAXLEN);

		//ligne9
		//if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(Fonctions.GetStringDanem(structclifact.ENSEIGNE).trim()) , 46, 30, true,MAXLEN);
		//completeLine += line+CR;

		line="";
		//if (typedoc.equals(TableSouches.TYPEDOC_FACTURE)==false)
		{
			//line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		}

		//if(!isClientTemp) line=Fonctions.insertStr(line,  Fonctions.replaceSpecialsChars(Fonctions.GetStringDanem(structclifact.NOM).trim()) , 46, 30, true,MAXLEN);
		completeLine+= line +CR ;

		//ligne10
		line="";
		if(typedoc.equals(TableSouches.TYPEDOC_FACTURE)){
			if(bduplicata) line=Fonctions.insertStr(line, "DUPLICATA", 16, 25, true,MAXLEN);
		}
		
		/*if (typedoc.equals(TableSouches.TYPEDOC_FACTURE)==false)
		{
			line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
			if(typedoc.equals(TableSouches.TYPEDOC_COMMANDE)) {
				if(!bduplicata) line=Fonctions.insertStr(line, "COMMANDE", 16, 25, true,MAXLEN);
				else line=Fonctions.insertStr(line, "COMMANDE DUPLICATA", 16, 30, true,MAXLEN);
			}else{
				line=Fonctions.insertStr(line, Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_TYPDOC, typedoc) , 16, 30, true,MAXLEN);
			}
		}*/
		//if(!isClientTemp) line=Fonctions.insertStr(line,Fonctions.replaceSpecialsChars(structclifact.ADR1) , 46, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, "RAPPORT D\'INTERVENTION" , 31, 22, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		completeLine+= line +CR;
		completeLine+=line+CR;
		line="";
		line=Fonctions.insertStr(line,"Date" , 5, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, "N° inter" , 18, 30, true,MAXLEN);
		line=Fonctions.insertStr(line,  "Réf.client", 31, 30, true,MAXLEN);
		line=Fonctions.insertStr(line,  "Technicien", 61, 30, true,MAXLEN);
		completeLine+= line ;
		line="";
		completeLine+= line +CR;
		line="";
		line=Fonctions.insertStr(line,m_date , 5, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, m_numeroInter , 18, 30, true,MAXLEN);
		line=Fonctions.insertStr(line,  codecli, 31, 30, true,MAXLEN);
		line=Fonctions.insertStr(line,  m_nomtechnicien, 61, 30, true,MAXLEN);
		completeLine+= line ;

		line="";
		completeLine+= line +CR;
		completeLine+= CR;
		completeLine+= CR;
		/*line=Fonctions.insertStr(line, "OBJET DE L\'INTERVENTION" , 31, 25, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		line=Fonctions.insertStr(line,Fonctions.GetStringDanem(m_objetIntervention) , 5, 100, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		completeLine+= CR;
		completeLine+= CR;*/
		
		
		line=Fonctions.insertStr(line, "TRAVAUX REALISES" , 31, 20, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		line=Fonctions.insertStr(line,Fonctions.GetStringDanem(m_travauxrealise) , 5, 100, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		completeLine+= CR;
		completeLine+= CR;
		

		line=Fonctions.insertStr(line, "MACHINE(S) CONCERNEE(S)" , 31, 25, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		
		line=Fonctions.insertStr(line,Fonctions.GetStringDanem(m_machineconcerne) +" - "+m_nommachine, 5, 100, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		completeLine+= CR;
		completeLine+= CR;
		
		line=Fonctions.insertStr(line, "Heure d\'arrivée : " + m_heurearrive , 2, 30, true,MAXLEN);
		completeLine+= line +CR ;
		line=Fonctions.insertStr(line, "Heure de départ : " + m_heuredepart , 2, 30, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		completeLine+= line +CR ;
		completeLine+= line +CR ;
		
		line=Fonctions.insertStr(line, "A : " + Fonctions.GetStringDanem(structcli.VILLE)  + " Le " + m_date, 2, 150, true,MAXLEN);
		completeLine+= line +CR ;
		line=Fonctions.insertStr(line, "Le client : " + Fonctions.GetStringDanem(m_nomcontact), 2, 150, true,MAXLEN);
		completeLine+= line +CR ;
		line="";
		completeLine+= line +CR ;
			
		//Ajout Cachet signature
		if(page == 1){
			line="";
			completeLine+=line+CR;
			completeLine+=line+CR;
			nbrline++;
			nbrline++;
			
				
			/*	String[] tabCachetSignature1 = null;
				tabCachetSignature1 = Fonctions.cutString(Fonctions.ReplaceGuillemet("Signature"), 60);
				for(int i = 0;i<tabCachetSignature1.length;i++){
					line="";
					line=Fonctions.insertStr(line, tabCachetSignature1[i] , 12, 60, true,MAXLEN);
					completeLine+=line+CR;
					nbrline++;
				}	*/
					
		}

		return Z420ModelInventaire.utfToAscii(completeLine);
	}
	
		

}
