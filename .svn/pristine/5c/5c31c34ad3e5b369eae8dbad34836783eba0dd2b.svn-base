package com.menadinteractive.segafredo.communs;

import java.util.HashMap;

import com.menadinteractive.segafredo.carto.ClientItem;
import com.menadinteractive.segafredo.carto.Marker;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableContactcli;
import com.menadinteractive.segafredo.db.TableFermeture;
import com.menadinteractive.segafredo.db.TableHistoTodo;
import com.menadinteractive.segafredo.db.TableHoraire;
import com.menadinteractive.segafredo.db.TableListeMateriel;
import com.menadinteractive.segafredo.db.TableListeTarifs;
import com.menadinteractive.segafredo.db.TableListeTarifsDetails;
import com.menadinteractive.segafredo.db.TableLivraison;
import com.menadinteractive.segafredo.db.TableMaterielClient;
import com.menadinteractive.segafredo.db.TableQuestionnaire;
import com.menadinteractive.segafredo.db.TableStock;
import com.menadinteractive.segafredo.db.TableSymptomes;
import com.menadinteractive.segafredo.db.TableTarif;
import com.menadinteractive.segafredo.db.dbCliToVisit;
import com.menadinteractive.segafredo.db.dbKD100Visite;
import com.menadinteractive.segafredo.db.dbKD101ClientVu;
import com.menadinteractive.segafredo.db.dbKD102Agenda;
import com.menadinteractive.segafredo.db.dbKD103Questionnaire;
import com.menadinteractive.segafredo.db.dbKD104AgendaSrv;
import com.menadinteractive.segafredo.db.dbKD105AgendaSrvSupp;
import com.menadinteractive.segafredo.db.dbKD451RetourMachineclient;
import com.menadinteractive.segafredo.db.dbKD452ComptageMachineclient;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire;
import com.menadinteractive.segafredo.db.dbKD544LinMvtsStock;
import com.menadinteractive.segafredo.db.dbKD71User;
import com.menadinteractive.segafredo.db.dbKD729HistoDocuments;
import com.menadinteractive.segafredo.db.dbKD730FacturesDues;
import com.menadinteractive.segafredo.db.dbKD731HistoDocumentsLignes;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.dbKD85Tempo;
import com.menadinteractive.segafredo.db.dbKD91EntPlan;
import com.menadinteractive.segafredo.db.dbKD92LinPlan;
import com.menadinteractive.segafredo.db.dbKD93PlanFiller;
import com.menadinteractive.segafredo.db.dbKD94Marchandise;
import com.menadinteractive.segafredo.db.dbKD981RetourBanqueEnt;
import com.menadinteractive.segafredo.db.dbKD982RetourBanqueLin;
import com.menadinteractive.segafredo.db.dbKD98Encaissement;
import com.menadinteractive.segafredo.db.dbKD99EncaisserFacture;
import com.menadinteractive.segafredo.db.dbLog;
import com.menadinteractive.segafredo.db.dbLogWS;
import com.menadinteractive.segafredo.db.dbParam;
import com.menadinteractive.segafredo.db.dbSiteHabitudes;
import com.menadinteractive.segafredo.db.dbSiteProduit;
import com.menadinteractive.segafredo.db.dbSociete;
import com.menadinteractive.segafredo.db.dbCliToVisit.structSoc;

public class Global {
	public static int curver;
	
	static public long maxMediaFileSize=715000;//taille max du fichier photo transferable au serveur
	static public String Autres="Autres";
	static public String Technicien="T";
	static public String Vendeur="V";
	static public String Responsable="R";
	
	
	
	

	static public TableClient dbClient;
	static public TableContactcli dbContactcli;
	static public TableTarif dbTarif;
	static public TableMaterielClient dbMaterielClient;
	static public TableSymptomes dbSymptomes;
	static public TableQuestionnaire dbQuestionnaire;
	static public dbLogWS dbLLogWs;
	
	
	static public TableListeMateriel dbListeMateriel;
	static public TableLivraison dbLivraison;



	static public TableHistoTodo dbHistoTodo;
	
	static public TableListeTarifs dbListeTarif;
	static public TableListeTarifsDetails dbListeTarifsDetails;
	
	
	static public dbSiteHabitudes dbSiteHabitudes;
	
	static public dbKD83EntCde dbKDEntCde;
	static public dbKD451RetourMachineclient dbKDRetourMachineClient;
	static public dbKD452ComptageMachineclient dbKDComptageMachineClient;
	
	static public dbKD103Questionnaire dbKDQuestionnaire;
	
	
	
	static public dbKD84LinCde dbKDLinCde;
	static public dbKD85Tempo dbKDTempo;
	static public dbKD98Encaissement dbKDEncaissement;
	static public dbKD99EncaisserFacture dbKDEncaisserFacture;
	static public dbKD543LinInventaire dbKDLinInv;
	static public dbKD544LinMvtsStock dbKDLinTransfert;

	static public dbKD94Marchandise dbKDMarchandise;
	
	static public dbSiteProduit dbProduit;
	static public dbCliToVisit dbCliToVisit;
	static public TableHoraire dbHoraire;
	static public TableFermeture dbFermeture;
	static public dbParam dbParam;
	static public dbSociete dbSoc;
	static public dbKD71User dbKDIdent;
	static public TableStock dbStock;
	static public dbKD100Visite dbKDVisite;
	static public dbKD101ClientVu dbKDClientVu;
	static public dbKD102Agenda dbKDAgenda;
	static public dbKD104AgendaSrv dbKDAgendaSrv;
	static public dbKD105AgendaSrvSupp dbKDAgendaSrvSupp;

	static public dbKD91EntPlan dbKDEntPlan;
	static public dbKD92LinPlan dbKDLinPlan;
	static public dbKD93PlanFiller dbKDFillPlan;
	static public dbKD729HistoDocuments dbKDHistoDocuments;
	static public dbKD731HistoDocumentsLignes dbKDHistoLigneDocuments;
	
	static public dbKD730FacturesDues dbKDFacturesDues;
	
	static public dbKD981RetourBanqueEnt dbKDRetourBanqueEnt;
	static public dbKD982RetourBanqueLin dbKDRetourBanqueLin;
	
	static public String lastLinCommentSaved="";//dernier commentaire sauvegardé, pour garder un meme commenre ligne entre 2 saisies
	static public String lastErrorMessage; 

//	static public structClient AXE_Client;
//	static public dbKD71User.structIdentifiant AXE_Ident;

	static public dbLog dbLog;
	public static ClientItem clientSelectedOnMap = null;
	
	static public String version;
	
	static public String CODCLI_TEMP;
	
 
	
	static public String FONT_REGULAR="fonts/opensansregular.ttf";
	static public String FONT_BOLD="fonts/opensansbold.ttf";
	static public String FONT_PRINT="fonts/couriernewregular.ttf";

	//DEV 60.1, pour verif encaissement
	static public boolean bNonRespect=false;
	static public double dMntToGet = 0 ;		//Montant à recuperer (total de la commande)
	static public double dMntGet = 0 ;		//Montant recuperer (adition a chaque saisie)

	
	
}
