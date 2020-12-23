package com.menadinteractive.printmodels;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.plugins.Espresso;

public class Z420ModelFacture extends BaseActivity{

	Context context;
	boolean m_bduplicata=false;
	int NBR_LIGNE_ARTICLE			=35;//35;
	int NBR_LIGNE_DEBUT_FIN_PAGE	=39;//39;
	int LNG_CLI_CODE = 250;
	int nbrline=0;
	boolean isClientST = false;

	public static final int TYPE_FACTURE = 1;
	public static final int TYPE_BL = 2;
	public static final int TYPE_BLCHIFFREE = 3;
	public static final int TYPE_BCOMMANDE = 5;
	
	int type = 0;

	String codeRep = null;
	String m_contactnom="";

	public Z420ModelFacture(Context c, String typedoc, String codeRep){
		context=c;
		this.codeRep = codeRep;
		if (typedoc.equals(TableSouches.TYPEDOC_FACTURE)) this.type=TYPE_FACTURE;
		if (typedoc.equals(TableSouches.TYPEDOC_BL)) this.type=TYPE_BL;
		if (typedoc.equals(TableSouches.TYPEDOC_BC)) this.type=TYPE_BLCHIFFREE; 
		if (typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE)) this.type=TYPE_BCOMMANDE; 
		
		nbrline = 0;
	}

	public String getFacture(String numfact,String codecli,boolean bduplicata,String typedoc, ArrayList<structLinCde> list, int page, int nbPages,String contactnom)
	{	 
		nbrline=0;
		String completeLine="";
		m_bduplicata=bduplicata;
		m_contactnom=contactnom;
		dbKD84LinCde lin;
		try{
			if(codecli != null && codecli.equals("ST"+codeRep)){
				isClientST = true;
			}

			lin=new dbKD84LinCde(Global.dbParam.getDB());
			//int nbrLine=lin.Count_Numcde(numfact, m_bduplicata);
		}catch(Exception ex){
			return "Erreur lors de l'impression!";
		}


		//remplacementdes zones variables
		//on va chercher les �l�ments de la facture
		dbKD83EntCde cde=new dbKD83EntCde(Global.dbParam.getDB());

		structEntCde structcde=new structEntCde();
		if (cde.load(structcde, numfact, new StringBuffer(),m_bduplicata)==false)
		{
			return "";
		}

		//COORD DU CLIENT
		TableClient cli=new TableClient(Global.dbParam.getDB());
		structClient structcli=new structClient();
		if (cli.getClient(codecli, structcli, new StringBuilder())==false)
		{
			return "";
		}


		//COORD DU CLIENT FACT
		TableClient clifact=new TableClient(Global.dbParam.getDB());
		structClient structclifact=new structClient();

		String codeclifact=Fonctions.GetStringDanem(structcli.CODEFACT);
		if (codeclifact.trim().equals(""))
			codeclifact=codecli;

		if (cli.getClient(codeclifact, structclifact, new StringBuilder())==false)
		{
			return "";
		}


		//on découpe le commentaire en 4 lignes si nécessaire si client 8888
		//30 caractères par ligne
		String[] tabCom = null;
		boolean isClientTemp = false;
		if(Fonctions.GetStringDanem(structcli.CODE).trim().equals("C08888"+codeRep)){
			isClientTemp = true;
			if(Fonctions.GetStringDanem(structcde.COMMENTCDE) != null && !Fonctions.GetStringDanem(structcde.COMMENTCDE).equals("")){
				tabCom = Fonctions.cutString(Fonctions.ReplaceGuillemet(structcde.COMMENTCDE), 30);
			}
		}


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
		if(!isClientTemp) line=Fonctions.insertStr(line, Fonctions.replaceSpecialsChars(Fonctions.GetStringDanem(structclifact.ENSEIGNE).trim()) , 46, 30, true,MAXLEN);
		completeLine += line+CR;

		line="";
		if (typedoc.equals(TableSouches.TYPEDOC_FACTURE)==false)
		{
			line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
		}

		if(!isClientTemp) line=Fonctions.insertStr(line,  Fonctions.replaceSpecialsChars(Fonctions.GetStringDanem(structclifact.NOM).trim()) , 46, 30, true,MAXLEN);
		completeLine+= line +CR ;

		//ligne10
		line="";
		if(typedoc.equals(TableSouches.TYPEDOC_FACTURE)){
			if(bduplicata) line=Fonctions.insertStr(line, "DUPLICATA", 16, 25, true,MAXLEN);
		}

		if (typedoc.equals(TableSouches.TYPEDOC_FACTURE)==false)
		{
			line=Fonctions.insertStr(line, "XXXXXXXXXXXXXX" , 2, 14, true,MAXLEN);
			if(typedoc.equals(TableSouches.TYPEDOC_COMMANDE) || typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE)) {
				if(!bduplicata) line=Fonctions.insertStr(line, "COMMANDE", 16, 25, true,MAXLEN);
				else line=Fonctions.insertStr(line, "COMMANDE DUPLICATA", 16, 30, true,MAXLEN);
			}else{
				line=Fonctions.insertStr(line, Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_TYPDOC, typedoc) , 16, 30, true,MAXLEN);
			}
		}
		if(!isClientTemp) line=Fonctions.insertStr(line,Fonctions.replaceSpecialsChars(structclifact.ADR1) , 46, 30, true,MAXLEN);
		completeLine+= line +CR ;


		//ligne11
		line="";
		if(!isClientTemp) line=Fonctions.insertStr(line,Fonctions.replaceSpecialsChars(structclifact.ADR2) , 46, 30, true,MAXLEN);
		completeLine+= line +CR;


		//ligne12
		line="";
		if(!isClientTemp) line=Fonctions.insertStr(line,Fonctions.replaceSpecialsChars(structclifact.CP+" "+structclifact.VILLE) , 46, 30, true,MAXLEN);
		completeLine+=line+CR;


		//ligne13
		line="";
		line=Fonctions.insertStr(line,Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.GetStringDanem(structcde.DATECDE)) , 5, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, codecli , 18, 30, true,MAXLEN);
		line=Fonctions.insertStr(line, numfact , 31, 30, true,MAXLEN);
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
		
		//Ajout votre commercial
		
		if(!typedoc.equals(TableSouches.TYPEDOC_COMMANDE)) 
		{
			if(page == 1){
				String[] tabNomCommercial = null;
				if(!isClientTemp && Fonctions.GetStringDanem(rep.NOM) != null && !Fonctions.GetStringDanem(rep.GSM2).equals("")){
					tabNomCommercial = Fonctions.cutString("Votre commercial: "+Fonctions.ReplaceGuillemet(rep.NOM)+" - "+Fonctions.ReplaceGuillemet(rep.GSM2), 60);

					for(int i = 0;i<tabNomCommercial.length;i++){
						line="";
						line=Fonctions.insertStr(line, tabNomCommercial[i] , 12, 60, true,MAXLEN);
						completeLine+=line+CR;
						nbrline++;
					}
				}
			}
			
		}
			

		//Ajout du commentaire client
		String[] tab = null;
		if(!isClientTemp && Fonctions.GetStringDanem(structcde.COMMENTCDE) != null && !Fonctions.GetStringDanem(structcde.COMMENTCDE).equals("")){
			tab = Fonctions.cutString(Fonctions.ReplaceGuillemet(structcde.COMMENTCDE), 60);


			for(int i = 0;i<tab.length;i++){
				line="";
				line=Fonctions.insertStr(line, tab[i] , 12, 60, true,MAXLEN);
				completeLine+=line+CR;
				nbrline++;
			}
		}
		//Ajout du ref cde
		if(page == 1){
			String[] tabrefcde = null;
			if(!isClientTemp && Fonctions.GetStringDanem(structcde.REFCDE) != null && !Fonctions.GetStringDanem(structcde.REFCDE).equals("")){
				tabrefcde = Fonctions.cutString(Fonctions.ReplaceGuillemet(structcde.REFCDE), 60);

				for(int i = 0;i<tabrefcde.length;i++){
					line="";
					line=Fonctions.insertStr(line, "Numero de commande client : "+tabrefcde[i] , 12, 60, true,MAXLEN);
					completeLine+=line+CR;
					nbrline++;
				}
			}
		}	

		completeLine+=imprimeLinePayant(numfact, MAXLEN , typedoc, list);

		//Ajout Cachet signature
		if(page == 1){
			
			
			line="";
			completeLine+=line+CR;
			completeLine+=line+CR;
			nbrline++;
			nbrline++;
			
			if(!Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien))
			{
				
				
				String[] tabCachetSignature = null;
				tabCachetSignature = Fonctions.cutString(Fonctions.ReplaceGuillemet("Pour la société"), 60);
				for(int i = 0;i<tabCachetSignature.length;i++){
					line="";
					line=Fonctions.insertStr(line, tabCachetSignature[i] , 12, 60, true,MAXLEN);
					completeLine+=line+CR;
					nbrline++;
				}
				String[] tabCachetSignature1 = null;
				tabCachetSignature1 = Fonctions.cutString(Fonctions.ReplaceGuillemet("(cachet et signature)"), 60);
				for(int i = 0;i<tabCachetSignature1.length;i++){
					line="";
					line=Fonctions.insertStr(line, tabCachetSignature1[i] , 12, 60, true,MAXLEN);
					completeLine+=line+CR;
					nbrline++;
				}	
			}
			else
			{

				//
				line=Fonctions.insertStr(line, "A : " + Fonctions.GetStringDanem(structcli.VILLE)  + " Le " + Fonctions.YYYYMMDD_to_dd_mm_yyyy(Fonctions.getYYYYMMDD()), 2, 150, true,MAXLEN);
				completeLine+= line +CR ;
				line=Fonctions.insertStr(line, "Le client : " + Fonctions.GetStringDanem(m_contactnom), 2, 150, true,MAXLEN);
				completeLine+= line +CR ;
				line="";
				completeLine+= line +CR ;
				nbrline++;
				nbrline++;
				nbrline++;
				
			}
				
		}

		//On complète le vide avec des lignes blanches
		for (int j=nbrline;j<NBR_LIGNE_DEBUT_FIN_PAGE;j++){
			completeLine+= CR;
		}

		if(page == nbPages){

			completeLine+= CR;
			//		completeLine+=imprimeLineGratuit(numfact, MAXLEN , typedoc);

			//Bas de page

			//Gestion du reglement
			line="";

			//On récupère les reglements (encaissements) pour les afficher lors de l'impression
			ArrayList<Encaissement> encaissements = Encaissement.getEncaissementFromNumeroFacture(numfact, codecli, null);

			//Ce texte prend deux lignes
			//ou une ligne et un blanc

			//completeLine+= CR;

			//On regarde le type pour savoir ce que l'on doit afficher
			switch (this.type){
			case 0:
				break;
			case TYPE_FACTURE:
				if(encaissements != null && encaissements.size() > 0){
					//on construit les lignes
					float montantCheque = 0;
					float montantEspece = 0;
					boolean isManyCheque = false;
					boolean isCheque = false;
					boolean isEspece = false;

					//On calcul les totaux
					for(Encaissement enc : encaissements){
						if(enc.getTypePaiement().equals(Encaissement.TYPE_CHEQUE) || enc.getTypePaiement().equals(Encaissement.TYPE_CHEQUEPORTEFEUILLE)){
							if(montantCheque > 0){
								isManyCheque = true;
							}
							montantCheque += enc.getMontant();	
							isCheque = true;
						}

						if(enc.getTypePaiement().equals(Encaissement.TYPE_ESPECE)){
							montantEspece += enc.getMontant();
							isEspece = true;
						}
					}

					//Payé uniquement par chèque
					if(isCheque && !isEspece){
						completeLine+= CR;
						if(isManyCheque){
							//Cheque portefeuille
							if(montantCheque > 0){
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheques)+
										" "+Fonctions.GetFloatToStringFormatDanem(montantCheque, "0.00")+
										" "+context.getString(R.string.devise_eur), 6, 35, true,MAXLEN);
							}else{
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheques_no_montant), 6, 35, true,MAXLEN);
							}
							completeLine+= CR;
						}else{
							//Un seul chèque
							if(montantCheque > 0){
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheque)+
										" "+Fonctions.GetFloatToStringFormatDanem(montantCheque, "0.00")+
										" "+context.getString(R.string.devise_eur), 6, 35, true,MAXLEN);
							}else{
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheque_no_montant), 6, 35, true,MAXLEN);
							}
							completeLine+= CR;
						}
						//Paiement uniquement en espèce
					}else if(isEspece && !isCheque){
						completeLine+= CR;
						if(montantEspece > 0){
							line+=Fonctions.insertStr(line, context.getString(R.string.rgl_esp)+
									" "+Fonctions.GetFloatToStringFormatDanem(montantEspece, "0.00")+
									" "+context.getString(R.string.devise_eur), 6, 35, true,MAXLEN);
						}else{
							line+=Fonctions.insertStr(line, context.getString(R.string.rgl_esp_no_montant), 6, 35, true,MAXLEN);
						}
						completeLine+= CR;
					}
					//Paiement par chèque et espèce
					else if(isCheque && isEspece){
						completeLine+= CR;
						//Plusieurs chèque
						if(isManyCheque){
							if(montantCheque > 0){
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheques)+
										" "+Fonctions.GetFloatToStringFormatDanem(montantCheque, "0.00")+
										" "+context.getString(R.string.devise_eur), 6, 35, true,MAXLEN);
							}else{
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheques_no_montant), 6, 35, true,MAXLEN);
							}
						}
						//Un seul chèque
						else{
							if(montantCheque > 0){
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheque)+
										" "+Fonctions.GetFloatToStringFormatDanem(montantCheque, "0.00")+
										" "+context.getString(R.string.devise_eur), 6, 35, true,MAXLEN);
							}else{
								line+=Fonctions.insertStr(line, context.getString(R.string.rgl_cheque_no_montant), 6, 35, true,MAXLEN);
							}
						}

						if(montantEspece > 0){
							line+=Fonctions.insertStr(line, context.getString(R.string.rgl_esp)+" "+context.getString(R.string.devise_eur), 6, 35, true,MAXLEN);
						}else{
							line+=Fonctions.insertStr(line, context.getString(R.string.rgl_esp_no_montant), 6, 35, true,MAXLEN);
						}
					}
				}else{
					completeLine+= CR;
					//Reglement aux conditions habituelles
					line+=Fonctions.insertStr(line, context.getString(R.string.rgl_no_montant_line1), 6, 35, true,MAXLEN);
					line+=CR;
					line+=Fonctions.insertStr(line, context.getString(R.string.rgl_no_montant_line2), 6, 35, true,MAXLEN);
				}
				break;
			case TYPE_BL:
				completeLine+= CR;
				//line+=Fonctions.insertStr(line, context.getString(R.string.rgl_reception_facture), 6, 35, true,MAXLEN);
				completeLine+= CR;
				break;
			case TYPE_BLCHIFFREE:
				completeLine+= CR;
				//Reglement aux conditions habituelles
				line+=Fonctions.insertStr(line, context.getString(R.string.rgl_no_montant_line1), 6, 35, true,MAXLEN);
				line+=CR;
				line+=Fonctions.insertStr(line, context.getString(R.string.rgl_no_montant_line2), 6, 35, true,MAXLEN);
				break;
				
			case TYPE_BCOMMANDE:
				completeLine+= CR;
				//Reglement aux conditions habituelles
				line+=Fonctions.insertStr(line, context.getString(R.string.rgl_no_montant_line1), 6, 35, true,MAXLEN);
				line+=CR;
				line+=Fonctions.insertStr(line, context.getString(R.string.rgl_no_montant_line2), 6, 35, true,MAXLEN);
				break;
			}
			completeLine+=line;

			//Ligne totaux

			//		if(typedoc.equals(TableSouches.TYPEDOC_BC)||typedoc.equals(TableSouches.TYPEDOC_FACTURE))
			//		{
			//			line="          ";
			//			line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(structcde.MNTHT, "0.00"), 45, 8, true,MAXLEN);
			//	
			//		}


			if(typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) || typedoc.equals(TableSouches.TYPEDOC_BCOMMANDE) || typedoc.equals(TableSouches.TYPEDOC_BC)||typedoc.equals(TableSouches.TYPEDOC_FACTURE))
			{
				line="          ";
				//On récupère les taux de tva
				List<structLinCde> lines = lin.loadTotauxTVA(structcde.CODECDE, m_bduplicata);

				float montantht1 = 0;
				float taux1 = 0;
				float montant1 = 0;

				float montantht2 = 0;
				float taux2 = 0;
				float montant2 = 0;
				float montantttc=0;

				if(lines.size() > 0){
					montantht1 = lines.get(0).MNTTOTALHT;
					taux1 = lines.get(0).TAUX;
					montant1=(float)Fonctions.round(((montantht1*taux1)/100),2);
					//montant1 = lines.get(0).MNTTVA;

				}

				if(lines.size() > 1){
					montantht2 = lines.get(1).MNTTOTALHT;
					taux2 = lines.get(1).TAUX;
					montant2=(float)Fonctions.round(((montantht2*taux2)/100),2);
					//montant2 = lines.get(1).MNTTVA;

				}

				if(montant1 > 0){
					line=Fonctions.insertStr(line, Float.toString(montantht1), 45, 8, true,MAXLEN);
					if(Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_NEUF) || Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_SIX))
					{

					}
					else
					{
						line=Fonctions.insertStr(line, Float.toString(taux1), 53, 4, false,MAXLEN);	
						line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montant1, "0.00"), 60, 9, true,MAXLEN);	

					}
				}
				if(Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_NEUF) || Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_SIX) )
				{
					montantttc=(montantht1)+(montantht2);
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montantttc, "0.00"), 70, 9, true,MAXLEN);

				}
				else
				{
					montantttc=(montantht1+montant1)+(montantht2+montant2);
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montantttc, "0.00"), 70, 9, true,MAXLEN);

				}

				completeLine+= CR;

				completeLine+= line;

				if(montant2 > 0){
					line="          ";
					line=Fonctions.insertStr(line, Float.toString(montantht2), 45, 8, true,MAXLEN);
					if(Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_NEUF) || Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_SIX) )
					{

					}
					else
					{
						line=Fonctions.insertStr(line, Float.toString(taux2), 53, 4, false,MAXLEN);	
						line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montant2,"0.00"), 60, 9, true,MAXLEN);

					}
					completeLine+= CR;

					if(montant1 <= 0){
						completeLine+= CR;
					}

					completeLine+= line;	
				}else{
					completeLine+= CR;
				}

				//Papillon
				completeLine+= CR;

				line="          ";
				completeLine+= CR;
				line=Fonctions.insertStr("", "Page "+page+"/"+nbPages, 68, 10, true,MAXLEN);
				completeLine += line;
				completeLine+= CR;
				completeLine+= CR;
				line="          ";
				line=Fonctions.insertStr(line, structcli.CODE, 7, LNG_CLI_CODE, true,MAXLEN);
				line=Fonctions.insertStr(line, numfact, 16, 8, true,MAXLEN);
				//line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(structcde.MNTTC,"0.00")+" "+context.getString(R.string.devise_eur), 28, 10, true,MAXLEN);
				if(Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_NEUF) || Fonctions.GetStringDanem(structcli.TVA).equals(TableClient.TVA_FISCAL_SIX) )
				{

					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montantttc,"0.00")+" "+context.getString(R.string.devise_eur), 28, 10, true,MAXLEN);


					//Montant net à payer		
					//line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(structcde.MNTTC, "0.00")+" "+context.getString(R.string.devise_eur), 68, 10, true,MAXLEN);
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montantttc, "0.00")+" "+context.getString(R.string.devise_eur), 68, 10, true,MAXLEN);

				}
				else
				{
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montantttc,"0.00")+" "+context.getString(R.string.devise_eur), 28, 10, true,MAXLEN);


					//Montant net à payer		
					//line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(structcde.MNTTC, "0.00")+" "+context.getString(R.string.devise_eur), 68, 10, true,MAXLEN);
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(montantttc, "0.00")+" "+context.getString(R.string.devise_eur), 68, 10, true,MAXLEN);

				}


			}
			else
			{
				//Papillon
				completeLine+= CR;

				completeLine+= CR;
				completeLine+= CR;
				line="";
				line=Fonctions.insertStr("", "Page "+page+"/"+nbPages, 68, 10, true,MAXLEN);
				//completeLine+=line;
				completeLine+= CR;
				//line=Fonctions.insertStr(line, structcli.CODE, 7, LNG_CLI_CODE, true,MAXLEN);
				//line=Fonctions.insertStr(line, numfact, 16, 8, true,MAXLEN);

			}



			completeLine+=line;
			//Fin bas de page

			/*   //MODE DE REGLEMENT
	    ArrayList<Bundle> bregl=new ArrayList<Bundle>();
	    if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_MODEREGLEMENT, bregl, false))
	    {
	    	completeLine=completeLine.replace("IMPR_MODEREGL",  Fonctions.getBundleValue(bregl.get(0), Global.dbParam.FLD_PARAM_LBL)); 
	    	String regledecalcalule=Fonctions.getBundleValue(bregl.get(0), Global.dbParam.FLD_PARAM_COMMENT);
	    	String dateech=Global.dbParam.calcDateEcheance(structcde.DATECDE,regledecalcalule);
	    	completeLine=completeLine.replace("IMPR_DATEECH", Fonctions.YYYYMMDD_to_dd_mm_yyyy(dateech)); 
	    }
	    else
	    {
	    	completeLine=completeLine.replace("IMPR_MODEREGL", ""); 
	    	completeLine=completeLine.replace("IMPR_DATEECH", ""); 
	    }*/

			/*	    byte ptext[];
		try {
			ptext = completeLine.getBytes(  "ISO_8859_1");
			 completeLine = new String(ptext, "UTF_8"); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			 */   

			//    completeLine=Z420ModelInventaire.utfToAscii(completeLine);

			//    completeLine=completeLine.replace("\n", "\x0A");
			//	    completeLine=completeLine.replace("\n", "");

			//line=String.format("%x", 12);
			//completeLine+=line;

			completeLine+=CR;
		}else{
			//on affiche le numéro de la page
			completeLine+=CR;
			completeLine+=CR;
			completeLine+=CR;
			completeLine+=CR;
			completeLine+=CR;
			completeLine+=CR;
			completeLine+=CR;
			completeLine+=CR;

			line="";
			line=Fonctions.insertStr("", "Page "+page+"/"+nbPages, 68, 10, true,MAXLEN);
			completeLine+=line;
			completeLine+=CR;
		}
		return Z420ModelInventaire.utfToAscii(completeLine);
	}


	String imprimeLinePayant( String numfact, int MAXLEN ,String typedoc, ArrayList<structLinCde> lines)
	{
		//		dbKD84LinCde lin=new dbKD84LinCde(Global.dbParam.getDB());
		//		List<structLinCde> lines=lin.load(numfact,m_bduplicata);
		String totaline="";
		boolean isGratuitMessageWrite = false;

		for (int i=0;i<lines.size();i++)
		{
			String line="";			
			if (lines.get(i).ISLIGNEGRATUIT)
			{
				line="";
				if(!isGratuitMessageWrite){
					String msg="               Articles supplementaires ; reduction de prix";
					line=Fonctions.insertStr(line, msg, 0, msg.length(), false,MAXLEN); 
					line+="\n";
					totaline+=line;
					nbrline++;

					isGratuitMessageWrite=true;
				}

				line="";
				line=Fonctions.insertStr(line, lines.get(i).PROCODE , 6, 10, true,MAXLEN);
				line=Fonctions.insertStr(line, lines.get(i).DESIGNATION , 13, 22, true,MAXLEN);

				if(typedoc.equals(TableSouches.TYPEDOC_BL))
				{
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTEGR, "0") , 61, 7, false,MAXLEN); 	

				}
				else
				{
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(0, "0.000") , 38, 8, false,MAXLEN);
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTEGR, "0") , 61, 7, false,MAXLEN); 	
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(0, "0.00") , 68, 10, false,MAXLEN); 

				}
				line+="\n";
				totaline+=line;
				nbrline++;
			}else if(lines.get(i).QTECDE>0){
				line="";
				line=Fonctions.insertStr(line, lines.get(i).PROCODE , 6, 10, true,MAXLEN);
				line=Fonctions.insertStr(line, lines.get(i).DESIGNATION , 13, 22, true,MAXLEN);

				if(typedoc.equals(TableSouches.TYPEDOC_BL))
				{
					if(lines.get(i).QTEGR > 0){
						line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTEGR, "0") , 61, 7, false,MAXLEN); 	
					}else if(lines.get(i).QTECDE > 0){
						line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTECDE, "0") , 61, 7, false,MAXLEN);
					}


				}
				else
				{
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).MNTUNITNETHT, "0.000") , 38, 8, false,MAXLEN); 

					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTECDE, "0") , 61, 7, false,MAXLEN);
						
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).MNTTOTALHT, "0.00") , 68, 10, false,MAXLEN); 

				}

				line+="\n";
				totaline+=line;
				nbrline++;

				if (lines.get(i).CODABAR.equals("")==false)
				{
					line="";
					line=Fonctions.insertStr(line, lines.get(i).CODABAR+"" , 13, 22, true,MAXLEN);
					line+="\n";
					totaline+=line;
					nbrline++;
				}
			}
			
		}

		//currentY=currentY+(nbrline*LINE_HEIGHT);

		return totaline;
	}
	String imprimeLineGratuit( String numfact, int MAXLEN, String typedoc)
	{


		dbKD84LinCde lin=new dbKD84LinCde(Global.dbParam.getDB());
		List<structLinCde> lines=lin.load(numfact,m_bduplicata);
		String totaline="";

		boolean displayMsg=false;

		for (int i=0;i<lines.size();i++)
		{
			if (lines.get(i).QTEGR>0)
			{
				String line="";
				if (displayMsg==false)
				{
					String msg="               Articles supplementaires ; reduction de prix";
					line=Fonctions.insertStr(line, msg, 0, msg.length(), false,MAXLEN); 
					line+="\n";
					totaline+=line;
					nbrline++;

					displayMsg=true;
				}

				line="";
				line=Fonctions.insertStr(line, lines.get(i).PROCODE , 6, 10, true,MAXLEN);
				line=Fonctions.insertStr(line, lines.get(i).DESIGNATION , 13, 22, true,MAXLEN);

				if(typedoc.equals(TableSouches.TYPEDOC_BL))
				{
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTEGR, "0") , 61, 7, false,MAXLEN); 	

				}
				else
				{
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(0, "0.000") , 38, 8, false,MAXLEN);
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(lines.get(i).QTEGR, "0") , 61, 7, false,MAXLEN); 	
					line=Fonctions.insertStr(line, Fonctions.GetFloatToStringFormatDanem(0, "0.00") , 68, 10, false,MAXLEN); 

				}
				line+="\n";
				totaline+=line;
				nbrline++;
			}

		}


		return totaline;
	}
	int imprimeTableauTVA(int currentY,String numfact,StringBuffer sb)
	{
		//taille d'une ligne
		int LINE_HEIGHT=50;

		String line="";
		line+="^FO694,800^GB112,50,1^FS";
		line+="^FO720,810^FDIMPR_TTC^FS";

		line+="^FO582,800^GB112,50,1^FS";
		line+="^FO610,810^FDIMPR_HT^FS";

		line+="^FO486,800^GB96,50,1^FS";
		line+="^FO490,810^FDIMPR_TVA^FS";

		line+="^FO406,800^GB80,50,1^FS";
		line+="^FO425,810^FDIMPR_TAUX^FS";


		dbKD84LinCde lin=new dbKD84LinCde(Global.dbParam.getDB());
		List<structLinCde> lines=lin.loadTotauxTVA(numfact,m_bduplicata);
		String totaline="";
		int nbrline=0;
		for (int i=0;i<lines.size();i++)
		{
			String detail=line;
			detail=detail.replace("IMPR_TAUX", Fonctions.GetDoubleToStringFormatDanem(lines.get(i).TAUX,"0.00"));
			detail=detail.replace("IMPR_TVA", Fonctions.GetDoubleToStringFormatDanem(lines.get(i).MNTTVA,"0.00")+"");
			detail=detail.replace("IMPR_HT", Fonctions.GetDoubleToStringFormatDanem(lines.get(i).MNTTOTALHT,"0.00")+"");
			detail=detail.replace("IMPR_TTC", Fonctions.GetDoubleToStringFormatDanem(lines.get(i).MNTTOTALTTC,"0.00")+"");
			detail=detail.replace("800", currentY+"");
			detail=detail.replace("810", (currentY+10)+"");

			currentY+=LINE_HEIGHT;
			totaline+=detail;
			nbrline++;
		}

		sb.append(totaline);
		//currentY=currentY+(nbrline*LINE_HEIGHT);

		return currentY;
	}

	/**
	 * Retourne la liste contenant les listes découpées pour impression 
	 * @param numfact
	 * @param duplicata
	 * @return
	 */
	public ArrayList<ArrayList<structLinCde>> getListeProduit(String numfact, boolean duplicata){
		ArrayList<ArrayList<structLinCde>> listDecoupe = new ArrayList<ArrayList<structLinCde>>();
		ArrayList<structLinCde> list = new ArrayList<dbKD84LinCde.structLinCde>();

		dbKD84LinCde lin=new dbKD84LinCde(Global.dbParam.getDB());
		List<structLinCde> lines=lin.load(numfact, duplicata);

		for(structLinCde line :lines){
			structLinCde newLine = line.clone();
			if(line.QTECDE > 0){
				line.ISLIGNEGRATUIT = false;
				list.add(newLine);
			}
		}

		//ajout des lignes de gratuit à la fin
		for(structLinCde line :lines){
			if(line.QTEGR > 0){
				structLinCde newLine = line.clone();
				for(structLinCde lineCde : list){
					if(lineCde.PROCODE.equals(line.PROCODE) && lineCde.QTECDE > 0){
						newLine.ISLIGNEGRATUIT = true;
						break;
					}
				}
				if(line.QTECDE <= 0){
					newLine.ISLIGNEGRATUIT = true;
				}
				list.add(newLine);
			}
		}

		//Ancienne methode, qui ne tiens pas compte de l'affichage de codebarre qui prennent une ligne
		/*if ( false) {
			int nbPages = lines.size() / NBR_LIGNE_ARTICLE;
			nbPages = nbPages + 1;

			//on découpe la liste selon le nombre maximum de ligne
			for (int i = 0; i < nbPages; i++) {
				ArrayList<structLinCde> linesTemp = new ArrayList<dbKD84LinCde.structLinCde>();
				int cpt = 0;
				if (i > 1) {
					cpt = ((i + 1) - 1) * NBR_LIGNE_ARTICLE;
				} else if (i == 1) {
					cpt = NBR_LIGNE_ARTICLE;
				}
				int page = i + 1;
				for (int j = cpt; j < NBR_LIGNE_ARTICLE * page; j++) {
					if (j < list.size()) {
						linesTemp.add(list.get(j));
					} else {
						break;
					}
				}
				listDecoupe.add(linesTemp);
			}
		}*/

		//Modif tof: nouvelle methode de decoupage des lignes, pour tenir compte des ligne codebar
		int iCptLignesPageCourante = 0 ;
		ArrayList<structLinCde> linesTemp = new ArrayList<dbKD84LinCde.structLinCde>();

		boolean bLigneGartuit  = false ;		//pour toutes les qte qratuites, on ajoute une ligne 'article supplementaire'. Il faut la compter une fois si il y a des gratuits
		for(int i = 0; i<list.size();i++) {
			int iPoidsLignesCourante = 1 ;
			if ( !list.get(i).CODABAR.equals("") && list.get(i).ISLIGNEGRATUIT == false )
				iPoidsLignesCourante ++ ; //Pour les lignes avec un code barre, il faut prévoir d'afficher une ligne en plus
			if ( list.get(i).ISLIGNEGRATUIT == true)
				if ( bLigneGartuit == false )
				{
					bLigneGartuit = true ;
					iPoidsLignesCourante ++ ;
				}
			/*if ( list.get(i).QTEGR != 0 )
				iPoidsLignesCourante ++ ; //Pour les lignes avec une qte gratuiite il faut prévoir d'afficher une ligne en plus  */

			//Verification si on peut ou non ajouter la ligne courante à la page courante
			if ( iCptLignesPageCourante + iPoidsLignesCourante > NBR_LIGNE_ARTICLE )
			{
				//changement de page
				listDecoupe.add(linesTemp);
				iCptLignesPageCourante = 0 ;
				linesTemp = new ArrayList<dbKD84LinCde.structLinCde>();

				bLigneGartuit = false ;  //la ligne 'article supplementaire' est repetée en haut de page si besoin, on la recompte au changement de page


			}

			linesTemp.add(list.get(i));
			iCptLignesPageCourante += iPoidsLignesCourante ;

		}
		//Ajout de la dernière page si il y a eu des lignes ajoutées
		if ( iCptLignesPageCourante != 0 )
		{
			listDecoupe.add(linesTemp);

		}


		return listDecoupe;
	}

}
