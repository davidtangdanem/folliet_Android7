package com.menadinteractive.segafredo.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.menadinteractive.folliet2016.Debug;
import com.menadinteractive.segafredo.carto.Marker;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.dbCliToVisit.structSoc;


public class TableClient extends DBMain{
	static public String TABLENAME="CLIENTV110";
	static public String TABLENAME_COMPLEMENTAIRE="T_NEGOS_CLIENTS_INFOCOMP";//info complemantaire client sur le serveur
	public static final String INDEXNAME_CODE_CLI = "INDEX_CODE_CLI";
	public static final String INDEXNAME_CODEVRP = "INDEX_CODE_VRP";
	public static final String INDEXNAME_NOM_CLI = "INDEX_NOM_CLI";
	public static final String INDEXNAME_JOUR_PASSAGE = "INDEX_JOUR_PASSAGE";
	public static final String INDEXNAME_ZONE = "INDEX_ZONE";

	public static final String JOUR_DE_PASSAGE = "J";
	
	public static final String TVA_FISCAL_NEUF = "9";
	public static final String TVA_FISCAL_SIX = "6";

	public static final String CLI_TYPE_CLIENT = "C";
	public static final String CLI_TYPE_PROSPECT = "L";
	
	public static final String CLI_MODELEBL_BNC = "BNC";
	public static final String CLI_MODELEBL_BLC = "BLC";
	
	public static final String CLI_PARTICULIER_8888 = "C08888";
	
	static public final String CLI_CREATION="C";
	static public final String CLI_MODIFICATION="M";
	static public final String FLAG_UPDATE="1";
	static public final String CLI_IS_ETS_PUBLIC = "O";

	public static final String FIELD_CODE 						= "CODE";
	public static final String FIELD_SOC_CODE 					= "SOC_CODE";
	public static final String FIELD_CODEVRP 					= "CODEVRP";
	public static final String FIELD_NOM 						= "NOM";//RAISON SOCIAL
	public static final String FIELD_ADR1 						= "ADR1";
	public static final String FIELD_ADR2 						= "ADR2";
	public static final String FIELD_TEL1 						= "TEL1";
	public static final String FIELD_FAX 						= "TEL2";//FAX
	public static final String FIELD_CP 						= "CP";
	public static final String FIELD_VILLE 						= "VILLE";
	public static final String FIELD_LAT 						= "LAT";
	public static final String FIELD_LON 						= "LON";
	public static final String FIELD_IS_GEOCODED 				= "IS_GEOCODED";
	public static final String FIELD_ICON 						= "ICON";
	public static final String FIELD_COULEUR 					= "COULEUR";//(liste)
	public static final String FIELD_TYPESAV 					= "TYPESAV";//(liste)
	public static final String FIELD_IMPORTANCE 				= "IMPORTANCE";
	public static final String FIELD_TOVISIT 					= "TOVISIT";
	public static final String FIELD_JOURPASSAGE 				= "JOURPASSAGE";
	public static final String FIELD_EMAIL 						= "EMAIL";
	public static final String FIELD_ZONE 						= "ZONE";
	public static final String FIELD_STATUT						= "STATUT";//FERM�, etc...
	public static final String FIELD_CONTACT_NOM				= "CONTACT_NOM";//NOM
	public static final String FIELD_CREAT						= "CREAT";//client cr�e � transmettre
	public static final String FIELD_CATCOMPT					= "CATCOMPT";
	public static final String FIELD_PAYS						= "PAYS";
	public static final String FIELD_SIRET						= "SIRET";
	public static final String FIELD_NUMTVA						= "NUMTVA";
	public static final String FIELD_COMMENT					= "COMMENT";
	public static final String FIELD_GSM						= "GSM";
	public static final String FIELD_ENSEIGNE					= "ENSEIGNE";
	public static final String FIELD_TYPE						= "TYPE";// Client/prospect  (liste)
	public static final String FIELD_GROUPECLIENT				= "GROUPECLIENT";// GROUPE CLIENT (liste)
	public static final String FIELD_AGENT						= "AGENT";// AGENT (liste)
	public static final String FIELD_CIRCUIT					= "CIRCUIT"; //(liste)
	public static final String FIELD_JOURFERMETURE				= "JOURFERMETURE";//(multi liste)
	public static final String FIELD_MODEREGLEMENT				= "MODEREGLEMENT";//(liste)
	public static final String FIELD_MONTANTTOTALENCOURS		= "MONTANTTOTALENCOURS";
	public static final String FIELD_MONTANTTOTALFACTURESDUES	= "MONTANTTOTALFACTURESDUES";
	public static final String FIELD_MONTANTTOTALAVOIR			= "MONTANTTOTALAVOIR";
	public static final String FIELD_MONTANTTOTALPAIEMENT		= "MONTANTTOTALPAIEMENT";
	public static final String FIELD_TYPEETABLISSEMENT			= "TYPEETABLISSEMENT";
	public static final String FIELD_FREETEXT					= "FREETEXT";
	public static final String FIELD_EXONERATION				= "EXONERATION";
	public static final String FIELD_ACTIF						= "ETAT";//   Y/N
	public static final String FIELD_DATECREAT					= "DATECREAT";
	public static final String FIELD_CODEFACT					= "CODEFACT";
	public static final String FIELD_CODETRF					= "CODETRF";
	public static final String FIELD_CLI_SAV					= "CLI_SAV";
	public static final String FIELD_CA_MOY_FAC					= "CA_MOY_FAC";
	public static final String FIELD_PERIODICITE				= "PERIODICITE";
	public static final String FIELD_TOURNEE					= "TOURNEE";
	public static final String FIELD_TVA						= "TVA";
	public static final String FIELD_CODECOM					= "CODECOM";
	public static final String FIELD_CLI_ANNUEL					= "CLI_ANNUEL";
	public static final String FIELD_CLI_ISHISTO				= "CLI_ISHISTO";
	public static final String FIELD_CLI_ISFACDUES				= "CLI_ISFACDUES";
	public static final String FIELD_CLI_ISMATOS				= "CLI_ISMATOS";
	public static final String FIELD_CLI_AGENT2					= "CLI_AGENT2";
	public static final String FIELD_EXCLURE_MAT                = "EXCLURE_MAT";
	public static final String FIELD_SAISON_ETE					= "SAISON_ETE";
	public static final String FIELD_SAISON_HIVER				= "SAISON_HIVER";
	public static final String FIELD_ACTIVITE_P					= "ACTIVITE_P";
	public static final String FIELD_FAMCLIENT					= "FAMCLIENT";
	public static final String FIELD_SFAMCLIENT					= "SFAMCLIENT";
	public static final String FIELD_STANDBY					= "STANDBY";
	public static final String FIELD_DATECLI					= "DATECLI";
	public static final String FIELD_CLASSE						= "CLASSE";
	public static final String FIELD_ETSPUBLIC                  = "ETS_PUBLIC";
	public static final String FIELD_CLI_PASMAIL                = "CLI_PASMAIL";
	public static final String FIELD_CAMT_0001                = "CAMT_0001";
	public static final String FIELD_CAMT_0002                = "CAMT_0002";
	public static final String FIELD_CAMT_0003                = "CAMT_0003";
	public static final String FIELD_CAMT_0004                = "CAMT_0004";
	public static final String FIELD_PARTCARANG               = "PARTCARANG";
	public static final String FIELD_CARANG               	  = "CARANG";
	public static final String FIELD_VP_AGRANG                = "VP_AGRANG";
	public static final String FIELD_VP_VRPRANG               = "VP_VRPRANG";
	public static final String FIELD_VP_CAMT_0001             = "VP_CAMT_0001";
	public static final String FIELD_VP_CAMT_0002             = "VP_CAMT_0002";
	public static final String FIELD_VP_CAMT_0003             = "VP_CAMT_0003";
	public static final String FIELD_VP_CAMT_0004             = "VP_CAMT_0004";
	public static final String FIELD_VP_MARGE_0001            = "VP_MARGE_0001";
	public static final String FIELD_VP_MARGE_0002            = "VP_MARGE_0002";
	public static final String FIELD_VP_MARGE_0003            = "VP_MARGE_0003";
	public static final String FIELD_VP_MARGE_0004            = "VP_MARGE_0004";
	public static final String FIELD_RENT_0001                = "RENT_0001";
	public static final String FIELD_RENT_0002                = "RENT_0002";
	public static final String FIELD_RENT_0003            	  = "RENT_0003";
	public static final String FIELD_RENT_0004                = "RENT_0004";
	public static final String FIELD_MOD_LIV                  = "MOD_LIV";
	public static final String FIELD_VOL_CAFE_ANNUEL          = "VOL_CAFE_ANNUEL";
	public static final String FIELD_POT_CA          		  = "POT_CA";
	public static final String FIELD_PLACEASSINT            = "PLACEASSINT";
	public static final String FIELD_PLACEASSEXT            = "PLACEASSEXT";
	public static final String FIELD_CAPACITE_SDR           = "CAPACITE_SDR";
	public static final String FIELD_NB_CHAMBRES            = "NB_CHAMBRES";
	public static final String FIELD_NB_LITS                = "NB_LITS";
	public static final String FIELD_QUALIF                 = "QUALIF";
	public static final String FIELD_SITUATION              = "SITUATION";
	public static final String FIELD_OPTION_P               = "OPTION_P";
	public static final String FIELD_TYPECUISINE            = "TYPECUISINE";
	public static final String FIELD_PV_CAFE                = "PV_CAFE";
	public static final String FIELD_PV_THE                 = "PV_THE";
	public static final String FIELD_PV_CHOCOLAT            = "PV_CHOCOLAT";
	public static final String FIELD_PV_PETIT_DEJ           = "PV_PETIT_DEJ";
	public static final String FIELD_PV_CHAMBRE             = "PV_CHAMBRE";
    public static final String FIELD_INFO_TERRAIN           = "INFOTERRAIN";
	public static final String FIELD_BOOK               	  = "BOOK";
    public static final String FIELD_TRIPADVISOR             = "TRIPADVISOR";
	public static final String FIELD_NO_FAC                  = "NO_FAC";
	public static final String FIELD_STATUT_PRO              = "STATUT_PRO";
	public static final String FIELD_COD_REPP                = "COD_REPP";
	public static final String FIELD_STD_BY                  = "STD_BY";
	public static final String FIELD_DATE_RELANCE                  = "DATE_RELANCE";
	public static final String FIELD_AGENCE                  = "AGENCE";
	public static final String FIELD_ENVOIFACT_PAR_MAIL      = "ENVOIFACT_PAR_MAIL";


	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}

	public static final String INDEX_CREATE_CODE_CLI="CREATE UNIQUE INDEX IF NOT EXISTS ["+INDEXNAME_CODE_CLI+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_CODE+"])";
	public static final String INDEX_CREATE_CODEVRP="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_CODEVRP+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_CODEVRP+"])";
	public static final String INDEX_CREATE_NOM_CLI="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_NOM_CLI+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_NOM+"])";
	public static final String INDEX_CREATE_JOUR_PASSAGE="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_JOUR_PASSAGE+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_JOURPASSAGE+"])";
	public static final String INDEX_CREATE_ZONE="CREATE UNIQUE INDEX IF NOT EXISTS ["+INDEXNAME_ZONE+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_ZONE+"])";


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_CODE			+"] [nvarchar](100) NULL" 	+ COMMA +
			" ["+FIELD_SOC_CODE		+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_CODEVRP		+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_NOM			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_ADR1			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_ADR2			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_CP			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_VILLE		+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_LAT			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_LON			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_IS_GEOCODED	+"] [nvarchar](1) NULL" 	+ COMMA +
			" ["+FIELD_TEL1			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_FAX			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_ICON			+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_COULEUR		+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_IMPORTANCE	+"] [nvarchar](2) NULL"		+ COMMA +
			" ["+FIELD_JOURPASSAGE	+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_EMAIL		+"] [nvarchar](255) NULL" 	+ COMMA +
			" ["+FIELD_TOVISIT		+"] [nvarchar](2) NULL"		+ COMMA +
			" ["+FIELD_ZONE			+"] [nvarchar](255) NULL"	+ COMMA +
			" ["+FIELD_CONTACT_NOM	+"] [nvarchar](255) NULL"	+ COMMA +
			" ["+FIELD_STATUT		+"] [nvarchar](2) NULL"	+ COMMA +
			" ["+FIELD_CATCOMPT		+"] [nvarchar](20) NULL"	+ COMMA +
			" ["+FIELD_PAYS			+"] [nvarchar](20) NULL"	+ COMMA +
			" ["+FIELD_SIRET		+"] [nvarchar](88) NULL"	+ COMMA +
			" ["+FIELD_NUMTVA		+"] [nvarchar](33) NULL"	+ COMMA +
			" ["+FIELD_COMMENT		+"] [nvarchar](255) NULL"	+ COMMA +
			" ["+FIELD_CREAT		+"] [nvarchar](1) NULL"	+ COMMA +
			" ["+FIELD_GSM		+"] [nvarchar](20) NULL"	+ COMMA +
			" ["+FIELD_ENSEIGNE		+"] [nvarchar](10) NULL"	+ COMMA +
			" ["+FIELD_TYPE		+"] [nvarchar](5) NULL"	+ COMMA +
			" ["+FIELD_GROUPECLIENT		+"] [nvarchar](5) NULL"	+ COMMA +
			" ["+FIELD_AGENT		+"] [nvarchar](5) NULL"	+ COMMA +
			" ["+FIELD_CIRCUIT		+"] [nvarchar](5) NULL"	+ COMMA +
			" ["+FIELD_JOURFERMETURE		+"] [nvarchar](100) NULL"	+ COMMA +
			" ["+FIELD_MODEREGLEMENT		+"] [nvarchar](30) NULL"	+ COMMA +
			" ["+FIELD_MONTANTTOTALENCOURS		+"] [nvarchar](30) NULL"	+ COMMA +
			" ["+FIELD_MONTANTTOTALFACTURESDUES		+"] [nvarchar](30) NULL"	+ COMMA +
			" ["+FIELD_MONTANTTOTALAVOIR		+"] [nvarchar](30) NULL"	+ COMMA +
			" ["+FIELD_MONTANTTOTALPAIEMENT		+"] [nvarchar](30) NULL"	+ COMMA +
			" ["+FIELD_TYPEETABLISSEMENT		+"] [nvarchar](5) NULL"	+ COMMA +
			" ["+FIELD_FREETEXT		+"] [nvarchar](255) NULL"	+ COMMA +
			" ["+FIELD_EXONERATION		+"] [nvarchar](1) NULL"	+ COMMA +
			" ["+FIELD_TYPESAV	+"] [nvarchar](20) NULL"	+ COMMA +
			" ["+FIELD_ACTIF		+"] [nvarchar](2) NULL"	+ COMMA +
			" ["+FIELD_CODEFACT		+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CODETRF	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CLI_SAV	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CA_MOY_FAC	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_PERIODICITE	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_TOURNEE	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_TVA	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CODECOM	+"] [nvarchar](200) NULL"	+ COMMA +
			" ["+FIELD_CLI_ANNUEL	+"] [nvarchar](200) NULL"	+ COMMA +
			" ["+FIELD_CLI_ISHISTO	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CLI_ISFACDUES	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CLI_ISMATOS	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_CLI_AGENT2	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_EXCLURE_MAT	+"] [nvarchar](1) NULL" 	+ COMMA +
			" ["+FIELD_SAISON_ETE	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_SAISON_HIVER	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_ACTIVITE_P	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_FAMCLIENT	+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_SFAMCLIENT		+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_STANDBY		+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_DATECLI		+"] [nvarchar](50) NULL"	+ COMMA +
			" ["+FIELD_DATECREAT		+"] [nvarchar](10) NULL"	+ COMMA +  
			" ["+FIELD_CLASSE		+"] [nvarchar](50) NULL"  + COMMA +
			" ["+FIELD_ETSPUBLIC		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CLI_PASMAIL		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CAMT_0001		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CAMT_0002		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CAMT_0003		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CAMT_0004		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PARTCARANG		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CARANG			+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_AGRANG		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_VRPRANG		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_CAMT_0001		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_CAMT_0002		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_CAMT_0003		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_CAMT_0004		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_MARGE_0001	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_MARGE_0002	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_MARGE_0003	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VP_MARGE_0004	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_RENT_0001		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_RENT_0002		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_RENT_0003		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_RENT_0004		+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_MOD_LIV			+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_VOL_CAFE_ANNUEL	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_POT_CA	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PLACEASSINT	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PLACEASSEXT	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_CAPACITE_SDR	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_NB_CHAMBRES	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_NB_LITS	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_QUALIF	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_SITUATION	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_OPTION_P	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_TYPECUISINE	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PV_CAFE	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PV_THE	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PV_CHOCOLAT	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PV_PETIT_DEJ	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_PV_CHAMBRE	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_INFO_TERRAIN	+"] [nvarchar](255) NULL"	+COMMA+
			" ["+FIELD_BOOK	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_TRIPADVISOR	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_NO_FAC	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_STATUT_PRO	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_COD_REPP	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_STD_BY	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_DATE_RELANCE	+"] [nvarchar](50) NULL"	+COMMA+
			" ["+FIELD_AGENCE	+"] [nvarchar](200) NULL"	+COMMA+
			" ["+FIELD_ENVOIFACT_PAR_MAIL	+"] [nvarchar](50) NULL"	+
			")";

	//Ajout du champs tournée dans une autre requete, pour eviter les pertes de données
	//public static final String TABLE_CREATE2="ALTER TABLE CLIENTV108 ADD COLUMN AGENCE [nvarchar](200) NULL";

	//Ajout du champs tournée dans une autre requete, pour eviter les pertes de données
	//public static final String TABLE_CREATE3=	"ALTER TABLE CLIENTV108 ADD COLUMN ENVOIFACT_PAR_MAIL [nvarchar](50) NULL";

	static public class structClient
	{
		public String CODE = "";        
		public String SOC_CODE = "";
		public String CODEVRP = "";
		public String NOM  = "";                  
		public String ADR1 = "";  
		public String ADR2 = "";  
		public String CP = "";  
		public String VILLE = "";  
		public String LAT  = "";  
		public String LON  = "";
		public String TEL1  = ""; 
		public String FAX  = ""; 
		public String ICON = ""; 
		public String COULEUR	= ""; 
		public String IMPORTANCE ="";
		public String JOURPASSAGE ="";
		public String TOVISIT ="";
		public String EMAIL ="";
		public String ZONE ="";
		public String STATUT ="";		
		public String CONTACT_NOM ="";
		public String CREAT ="";
		public String PAYS ="";
		public String CATCOMPT ="";
		public String IS_GEOCODED="";
		public String SIRET ="";
		public String NUMTVA ="";
		public String COMMENT ="";
		public String GSM ="";
		public String ENSEIGNE ="";
		public String TYPE ="";
		public String GROUPECLIENT ="";
		public String AGENT ="";
		public String CIRCUIT ="";
		public String JOURFERMETURE ="";
		public String MODEREGLEMENT ="";
		public String MONTANTTOTALENCOURS ="";
		public String MONTANTTOTALFACTURESDUES ="";
		public String MONTANTTOTALAVOIR ="";
		public String MONTANTTOTALPAIEMENT ="";
		public String TYPEETABLISSEMENT ="";
		public String FREETEXT ="";
		public String EXONERATION ="";
		public String ETAT ="";
		public String DATECREAT ="";
		public String TYPESAV ="";		
		public String CODEFACT ="";	
		public String CODETRF ="";	
		public String CLI_SAV="";
		public String CA_MOY_FAC="";
		public String PERIODICITE="";
		public String TOURNEE="";
		public String TVA="";
		public String CODECOM="";
		public String CLI_ANNUEL="";
		public String CLI_ISHISTO="";
		public String CLI_ISFACDUES="";
		public String CLI_ISMATOS="";
		public structSoc CLI_TO_VISIT_STRUCT = null;
		public String TYPE_NUMINTER="";
		public String CLI_AGENT2="";
		public String EXCLURE_MAT = "";
		public String SAISON_ETE="";
		public String SAISON_HIVER="";
		public String ACTIVITE_P="";
		public String FAMCLIENT="";
		public String SFAMCLIENT="";
		public String STANDBY="";
		public String DATECLI="";
		public String CLASSE="";
		public String ETSPUBLIC = "";
		public String CLI_PASMAIL = "";
		public String CAMT_0001 = "";
		public String CAMT_0002 = "";
		public String CAMT_0003 = "";
		public String CAMT_0004 = "";
		public String PARTCARANG = "";
		public String CARANG = "";
		public String VP_AGRANG = "";
		public String VP_VRPRANG = "";
		public String VP_CAMT_0001 = "";
		public String VP_CAMT_0002 = "";
		public String VP_CAMT_0003 = "";
		public String VP_CAMT_0004 = "";
		public String VP_MARGE_0001 = "";
		public String VP_MARGE_0002 = "";
		public String VP_MARGE_0003 = "";
		public String VP_MARGE_0004 = "";
		public String RENT_0001 = "";
		public String RENT_0002 = "";
		public String RENT_0003 = "";
		public String RENT_0004 = "";
		public String MOD_LIV = "";
		public String VOL_CAFE_ANNUEL = "";

		public String POT_CA = "";
		public String PLACEASSINT = "";
		public String PLACEASSEXT = "";
		public String CAPACITE_SDR = "";
		public String NB_CHAMBRES = "";
		public String NB_LITS = "";
		public String QUALIF = "";
		public String SITUATION = "";
		public String OPTION_P = "";
		public String TYPECUISINE = "";
		public String PV_CAFE = "";
		public String PV_THE = "";
		public String PV_CHOCOLAT = "";
		public String PV_PETIT_DEJ = "";
		public String PV_CHAMBRE = "";
		public String STD_BY = "";
		public String INFO_TERRAIN   = "";
		public String BOOK           = "";
		public String TRIPADVISOR    = "";
		public String NO_FAC         = "";
		public String STATUT_PRO     = "";
		public String COD_REPP       = "";
		public String DATE_RELANCE       = "";
		public String AGENCE       = "";
		public String ENVOIFACT_PAR_MAIL   = "";



		public String getIconName(){
			String result = "";
			String suffixe = Marker.getMarkerNameFromColor(COULEUR);
			if(!Fonctions.isEmptyOrNull(suffixe))
				result = ICON+"_"+suffixe+".png";
			return result;
		}

		public String getRawIconName(){
			return ICON+"_"+COULEUR;
		}

		public String getFullAddress(){
			return this.ADR1+" "+this.ADR2+" "+this.CP+" "+this.VILLE;
		}

		public double getIconSize(){
			double result = 1;
			if(!Fonctions.isEmptyOrNull(IMPORTANCE)){
				if(IMPORTANCE.equals("H"))
					result = 1.8;
				else if(IMPORTANCE.equals("M"))
					result = 1.4;
				if(IMPORTANCE.equals("L"))
					result = 1.0;
			}
			return result;
		}

		@Override
		public String toString() {
			return "structClient [CODE=" + CODE + ", SOC_CODE=" + SOC_CODE
					+ ", CODEVRP=" + CODEVRP + ", NOM=" + NOM + ", ADR1="
					+ ADR1 + ", ADR2=" + ADR2 + ", CP=" + CP + ", VILLE="
					+ VILLE + ", LAT=" + LAT + ", LON=" + LON + ", TEL1="
					+ TEL1 + ", TEL2=" + FAX + ", ICON=" + ICON + ", COULEUR="
					+ COULEUR + ", IMPORTANCE=" + IMPORTANCE + ", JOURPASSAGE="
					+ JOURPASSAGE + ", TOVISIT=" + TOVISIT + ", EMAIL=" + EMAIL
					+ ", ZONE=" + ZONE + ", STATUT=" + STATUT 
					+ ", CONTACT_NOM=" + CONTACT_NOM					
					+ ", CATCOMPT=" + CATCOMPT
					+ ", SIRET=" + SIRET
					+ ", NUMTVA=" + NUMTVA
					+ ", IS_GEOCODED=" + IS_GEOCODED
					+ ", COMMENT=" + COMMENT
					+ ", PAYS=" + PAYS
					+ ", CREAT=" + CREAT
					+ ", GSM=" + GSM
					+ ", ENSEIGNE=" + ENSEIGNE
					+ ", TYPE=" + TYPE
					+ ", TYPESAV=" + TYPESAV
					+ ", GROUPECLIENT=" + GROUPECLIENT
					+ ", AGENT=" + AGENT
					+ ", CIRCUIT=" + CIRCUIT
					+ ", JOURFERMETURE=" + JOURFERMETURE
					+ ", MODEREGLEMENT=" + MODEREGLEMENT
					+ ", MONTANTTOTALENCOURS=" + MONTANTTOTALENCOURS
					+ ", MONTANTTOTALFACTURESDUES=" + MONTANTTOTALFACTURESDUES
					+ ", MONTANTTOTALAVOIR=" + MONTANTTOTALAVOIR
					+ ", MONTANTTOTALPAIEMENT=" + MONTANTTOTALPAIEMENT
					+ ", TYPEETABLISSEMENT=" + TYPEETABLISSEMENT
					+ ", FREETEXT=" + FREETEXT
					+ ", EXONERATION=" + EXONERATION
					+ ", ETAT=" + ETAT
					+ ", CODETRF=" + CODETRF
					+ ", CLI_SAV=" + CLI_SAV
					+ ", CA_MOY_FAC=" + CA_MOY_FAC
					+ ", PERIODICITE=" + PERIODICITE
					+ ", TOURNEE=" + TOURNEE
					+ ", TVA=" + TVA
					+ ", CODECOM=" + CODECOM
					+ ", CLI_ANNUEL=" + CLI_ANNUEL
					+ ", CLI_ISHISTO=" + CLI_ISHISTO
					+ ", CLI_ISFACDUES=" + CLI_ISFACDUES
					+ ", CLI_ISMATOS=" + CLI_ISMATOS
					+ ", DATECREAT=" + DATECREAT
					+ ", CLI_AGENT2=" + CLI_AGENT2
					+ ", EXCLURE_MAT=" + EXCLURE_MAT
					+ ", SAISON_ETE=" + SAISON_ETE
					+ ", SAISON_HIVER=" + SAISON_HIVER
					+ ", ACTIVITE_P=" + ACTIVITE_P
					+ ", FAMCLIENT=" + FAMCLIENT
					+ ", SFAMCLIENT=" + SFAMCLIENT
					+ ", STANDBY=" + STANDBY
					+ ", DATECLI=" + DATECLI
					+ ", CLASSE=" + CLASSE
					+ ", ETSPUBLIC=" + ETSPUBLIC
					+ ", CLI_PASMAIL=" + CLI_PASMAIL
					+ ", CAMT_0001=" + CAMT_0001
					+ ", CAMT_0002=" + CAMT_0002
					+ ", CAMT_0003=" + CAMT_0003
					+ ", CAMT_0004=" + CAMT_0004
					+ ", PARTCARANG=" + PARTCARANG
					+ ", CARANG=" + CARANG
					+ ", VP_AGRANG=" + VP_AGRANG
					+ ", VP_VRPRANG=" + VP_VRPRANG
					+ ", VP_CAMT_0001=" + VP_CAMT_0001
					+ ", VP_CAMT_0002=" + VP_CAMT_0002
					+ ", VP_CAMT_0003=" + VP_CAMT_0003
					+ ", VP_CAMT_0004=" + VP_CAMT_0004
					+ ", VP_MARGE_0001=" + VP_MARGE_0001
					+ ", VP_MARGE_0002=" + VP_MARGE_0002
					+ ", VP_MARGE_0003=" + VP_MARGE_0003
					+ ", VP_MARGE_0004=" + VP_MARGE_0004
					+ ", RENT_0001=" + RENT_0001
					+ ", RENT_0002=" + RENT_0002
					+ ", RENT_0003=" + RENT_0003
					+ ", RENT_0004=" + RENT_0004
					+ ", MOD_LIV=" + MOD_LIV
					+ ", VOL_CAFE_ANNUEL=" + VOL_CAFE_ANNUEL
					+ ", POT_CA=" + POT_CA
					+ ", PLACEASSINT=" + PLACEASSINT
					+ ", PLACEASSEXT=" + PLACEASSEXT
					+ ", CAPACITE_SDR=" + CAPACITE_SDR
					+ ", NB_CHAMBRES=" + NB_CHAMBRES
					+ ", NB_LITS=" + NB_LITS
					+ ", QUALIF=" + QUALIF
					+ ", SITUATION=" + SITUATION
					+ ", OPTION_P=" + OPTION_P
					+ ", TYPECUISINE=" + TYPECUISINE
					+ ", PV_CAFE=" + PV_CAFE
					+ ", PV_THE=" + PV_THE
					+ ", PV_CHOCOLAT=" + PV_CHOCOLAT
					+ ", PV_PETIT_DEJ=" + PV_PETIT_DEJ
					+ ", PV_CHAMBRE=" + PV_CHAMBRE
					+ ", STD_BY=" + STD_BY
					+ ", INFO_TERRAIN=" + INFO_TERRAIN
					+ ", BOOK=" + BOOK
					+ ", TRIPADVISOR=" + TRIPADVISOR
					+ ", NO_FAC=" + NO_FAC
					+ ", STATUT_PRO=" + STATUT_PRO
					+ ", COD_REPP=" + COD_REPP
					+ ", DATE_RELANCE=" + DATE_RELANCE
					+ ", AGENCE=" + AGENCE
					+ ", ENVOIFACT_PAR_MAIL=" + ENVOIFACT_PAR_MAIL
					+"]";
		}
	} 

	MyDB db;
	public TableClient(MyDB _db)
	{
		super();
		db=_db;
	}
	public boolean clear(StringBuilder err)
	{
		try
		{
			String query="DROP TABLE IF EXISTS "+TABLENAME;
			//db.conn.delete(TABLENAME, null, null);
			db.execSQL(query, err);
			db.execSQL(TABLE_CREATE,err);
			//db.execSQL(TABLE_CREATE2,err);
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;	
		}
		return true;
	}
	static public boolean isActif(structClient cli)
	{
		if (cli.ETAT==null) return true;
		if (cli.ETAT.toUpperCase().equals("N"))
			return false;

		return true;
	}
	static public boolean isModeleBL(structClient cli,String type)
	{
		if (cli.CATCOMPT==null) return false;
		if (cli.CATCOMPT.toUpperCase().equals(type))
			return true;

		return false;
	}

	public structClient load(Cursor cursor){
		structClient client = new structClient();
		if(cursor != null){
			//			Debug.Log("requete", " CODE IN LOADING : -"+giveFld(cursor, FIELD_CODE+"-"));


			client.SOC_CODE = giveFld(cursor, FIELD_SOC_CODE);
			client.CODE = giveFld(cursor, FIELD_CODE);
			client.CODEVRP = giveFld(cursor, FIELD_CODEVRP);
			client.NOM = giveFld(cursor, FIELD_NOM);
			client.ADR1 = giveFld(cursor, FIELD_ADR1);
			client.ADR2 = giveFld(cursor, FIELD_ADR2);
			client.CP = giveFld(cursor, FIELD_CP);
			client.VILLE = giveFld(cursor, FIELD_VILLE);
			client.LAT = giveFld(cursor, FIELD_LAT);
			client.LON = giveFld(cursor, FIELD_LON);
			client.TEL1 = giveFld(cursor, FIELD_TEL1);
			client.FAX = giveFld(cursor, FIELD_FAX);
			client.ICON = giveFld(cursor, FIELD_ICON);
			client.COULEUR = giveFld(cursor, FIELD_COULEUR);
			client.TYPESAV = giveFld(cursor, FIELD_TYPESAV);
			client.CODEFACT = giveFld(cursor, FIELD_CODEFACT);
			client.CODETRF = giveFld(cursor, FIELD_CODETRF);
			client.IMPORTANCE = giveFld(cursor, FIELD_IMPORTANCE);
			client.TOVISIT = giveFld(cursor, FIELD_TOVISIT);
			client.JOURPASSAGE = giveFld(cursor, FIELD_JOURPASSAGE);
			client.EMAIL = giveFld(cursor, FIELD_EMAIL);
			client.ZONE = giveFld(cursor, FIELD_ZONE);
			client.STATUT = giveFld(cursor, FIELD_STATUT);
			client.CONTACT_NOM = giveFld(cursor, FIELD_CONTACT_NOM);
			//		client.CONTACT_GSM = giveFld(cursor, FIELD_CONTACT_GSM);
			client.CATCOMPT = giveFld(cursor, FIELD_CATCOMPT);
			client.PAYS = giveFld(cursor, FIELD_PAYS);
			client.CREAT = giveFld(cursor, FIELD_CREAT);
			client.SIRET = giveFld(cursor, FIELD_SIRET);
			client.NUMTVA = giveFld(cursor, FIELD_NUMTVA);
			client.COMMENT = giveFld(cursor, FIELD_COMMENT);		
			client.GSM = giveFld(cursor, FIELD_GSM);
			client.ENSEIGNE = giveFld(cursor, FIELD_ENSEIGNE);
			client.TYPE = giveFld(cursor, FIELD_TYPE);
			client.GROUPECLIENT = giveFld(cursor, FIELD_GROUPECLIENT);
			client.AGENT = giveFld(cursor, FIELD_AGENT);
			client.CIRCUIT = giveFld(cursor, FIELD_CIRCUIT);
			client.JOURFERMETURE = giveFld(cursor, FIELD_JOURFERMETURE);
			client.MODEREGLEMENT = giveFld(cursor, FIELD_MODEREGLEMENT);
			client.MONTANTTOTALENCOURS = giveFld(cursor, FIELD_MONTANTTOTALENCOURS);
			client.MONTANTTOTALFACTURESDUES = giveFld(cursor, FIELD_MONTANTTOTALFACTURESDUES);
			client.MONTANTTOTALAVOIR = giveFld(cursor, FIELD_MONTANTTOTALAVOIR);
			client.MONTANTTOTALPAIEMENT = giveFld(cursor, FIELD_MONTANTTOTALPAIEMENT);
			client.TYPEETABLISSEMENT = giveFld(cursor, FIELD_TYPEETABLISSEMENT);
			client.FREETEXT = giveFld(cursor, FIELD_FREETEXT);		
			client.EXONERATION = giveFld(cursor, FIELD_EXONERATION);
			client.ETAT = giveFld(cursor, FIELD_ACTIF);
			client.CLI_SAV = giveFld(cursor, FIELD_CLI_SAV);
			client.CA_MOY_FAC = giveFld(cursor, FIELD_CA_MOY_FAC);
			client.PERIODICITE = giveFld(cursor, FIELD_PERIODICITE);
			client.TOURNEE = giveFld(cursor, FIELD_TOURNEE);
			client.TVA = giveFld(cursor, FIELD_TVA);
			client.CODECOM = giveFld(cursor, FIELD_CODECOM);
			client.CLI_ANNUEL = giveFld(cursor, FIELD_CLI_ANNUEL);
			client.CLI_ISHISTO = giveFld(cursor, FIELD_CLI_ISHISTO);
			client.CLI_ISFACDUES = giveFld(cursor, FIELD_CLI_ISFACDUES);
			client.CLI_ISMATOS = giveFld(cursor, FIELD_CLI_ISMATOS);
			client.DATECREAT = giveFld(cursor, FIELD_DATECREAT);
			client.CLI_AGENT2 = giveFld(cursor, FIELD_CLI_AGENT2);
			client.EXCLURE_MAT = giveFld(cursor, FIELD_EXCLURE_MAT);
			client.SAISON_ETE = giveFld(cursor, FIELD_SAISON_ETE);
			client.SAISON_HIVER = giveFld(cursor, FIELD_SAISON_HIVER);
			client.ACTIVITE_P = giveFld(cursor, FIELD_ACTIVITE_P);
			client.FAMCLIENT = giveFld(cursor, FIELD_FAMCLIENT);
			client.SFAMCLIENT = giveFld(cursor, FIELD_SFAMCLIENT);
			client.STANDBY = giveFld(cursor, FIELD_STANDBY);
			client.DATECLI = giveFld(cursor, FIELD_DATECLI);
			client.CLASSE = giveFld(cursor, FIELD_CLASSE);
			client.ETSPUBLIC = giveFld(cursor, FIELD_ETSPUBLIC);
			client.CLI_PASMAIL = giveFld(cursor, FIELD_CLI_PASMAIL);

			client.CAMT_0001 = giveFld(cursor, FIELD_CAMT_0001);
			client.CAMT_0002 = giveFld(cursor, FIELD_CAMT_0002);
			client.CAMT_0003 = giveFld(cursor, FIELD_CAMT_0003);
			client.CAMT_0004 = giveFld(cursor, FIELD_CAMT_0004);
			client.PARTCARANG = giveFld(cursor, FIELD_PARTCARANG);
			client.CARANG = giveFld(cursor, FIELD_CARANG);
			client.VP_AGRANG = giveFld(cursor, FIELD_VP_AGRANG);
			client.VP_VRPRANG = giveFld(cursor, FIELD_VP_VRPRANG);
			client.VP_CAMT_0001 = giveFld(cursor, FIELD_VP_CAMT_0001);
			client.VP_CAMT_0002 = giveFld(cursor, FIELD_VP_CAMT_0002);
			client.VP_CAMT_0003 = giveFld(cursor, FIELD_VP_CAMT_0003);
			client.VP_CAMT_0004 = giveFld(cursor, FIELD_VP_CAMT_0004);
			client.VP_MARGE_0001 = giveFld(cursor, FIELD_VP_MARGE_0001);
			client.VP_MARGE_0002 = giveFld(cursor, FIELD_VP_MARGE_0002);
			client.VP_MARGE_0003 = giveFld(cursor, FIELD_VP_MARGE_0003);
			client.VP_MARGE_0004 = giveFld(cursor, FIELD_VP_MARGE_0004);
			client.RENT_0001 = giveFld(cursor, FIELD_RENT_0001);
			client.RENT_0002 = giveFld(cursor, FIELD_RENT_0002);
			client.RENT_0003 = giveFld(cursor, FIELD_RENT_0003);
			client.RENT_0004 = giveFld(cursor, FIELD_RENT_0004);
			client.MOD_LIV = giveFld(cursor, FIELD_MOD_LIV);
			client.VOL_CAFE_ANNUEL = giveFld(cursor, FIELD_VOL_CAFE_ANNUEL);

			client.POT_CA = giveFld(cursor, FIELD_POT_CA);
			client.PLACEASSINT = giveFld(cursor, FIELD_PLACEASSINT);
			client.PLACEASSEXT = giveFld(cursor, FIELD_PLACEASSEXT);
			client.CAPACITE_SDR = giveFld(cursor, FIELD_CAPACITE_SDR);
			client.NB_CHAMBRES = giveFld(cursor, FIELD_NB_CHAMBRES);
			client.NB_LITS = giveFld(cursor, FIELD_NB_LITS);
			client.QUALIF = giveFld(cursor, FIELD_QUALIF);
			client.SITUATION = giveFld(cursor, FIELD_SITUATION);
			client.OPTION_P = giveFld(cursor, FIELD_OPTION_P);
			client.TYPECUISINE = giveFld(cursor, FIELD_TYPECUISINE);
			client.PV_CAFE = giveFld(cursor, FIELD_PV_CAFE);
			client.PV_THE = giveFld(cursor, FIELD_PV_THE);
			client.PV_CHOCOLAT = giveFld(cursor, FIELD_PV_CHOCOLAT);
			client.PV_PETIT_DEJ = giveFld(cursor, FIELD_PV_PETIT_DEJ);
			client.PV_CHAMBRE = giveFld(cursor, FIELD_PV_CHAMBRE);
			client.STD_BY = giveFld(cursor, FIELD_STD_BY);
			client.INFO_TERRAIN   = giveFld(cursor, FIELD_INFO_TERRAIN);
			client.BOOK           = giveFld(cursor, FIELD_BOOK);
			client.TRIPADVISOR    = giveFld(cursor, FIELD_TRIPADVISOR);
			client.NO_FAC         = giveFld(cursor, FIELD_NO_FAC);
			client.STATUT_PRO     = giveFld(cursor, FIELD_STATUT_PRO);
			client.COD_REPP       = giveFld(cursor, FIELD_COD_REPP);
			client.DATE_RELANCE       = giveFld(cursor, FIELD_DATE_RELANCE);
			client.AGENCE       = giveFld(cursor, FIELD_AGENCE);
			client.ENVOIFACT_PAR_MAIL       = giveFld(cursor, FIELD_ENVOIFACT_PAR_MAIL);



			try {
				structSoc visit;
				if(giveFld(cursor, dbCliToVisit.FIELD_CODE_EVT) != null && 
						!giveFld(cursor, dbCliToVisit.FIELD_CODE_EVT).equals("") ){
					visit = new structSoc();
					visit = Global.dbCliToVisit.getCliToVisitFromCodeEvent2(giveFld(cursor, dbCliToVisit.FIELD_CODE_EVT),giveFld(cursor, dbCliToVisit.FIELD_EVT_ID));

					client.CLI_TO_VISIT_STRUCT = visit;
				}else{
					client.CLI_TO_VISIT_STRUCT = null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				client.CLI_TO_VISIT_STRUCT = null;
			}
			
			client.TYPE_NUMINTER=giveFld(cursor, TableHistoInter.FIELD_NUMINTER);
			
		}

				Debug.Log("requete", client.toString());

		return client;
	}

	public structClient load(String code){
		structClient client = new structClient();

		try {
			String query = "SELECT *"
                    +" FROM "+TABLENAME
                    +" WHERE "+getFullFieldName(FIELD_CODE)+" = '"+code+"'";

			Log.e("queryload","structClient=>"+query);

			Cursor cursor =  db.conn.rawQuery(query, null);

			if(cursor != null && cursor.moveToFirst()){

                client = load(cursor);
                cursor.close();
            }
			if (cursor!=null)
                cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return client;
	}
	public int Count()
	{

		try
		{
			String query="select count(*) from "+TABLENAME;
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	public int CountModified()
	{

		try
		{
			String query="SELECT count(*) FROM "+
					TABLENAME+
					" where "+
					" ( "+
					Global.dbClient.FIELD_CREAT +
					"='"+ Global.dbClient.CLI_MODIFICATION+ "' or  "+
					Global.dbClient.FIELD_CREAT +
					"='"+ Global.dbClient.CLI_CREATION+ "'  ) ";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	public int CountModified2()
	{

		try
		{
			String query="SELECT count(*) FROM "+
					TABLENAME+
					" where "+
					"  "+
					Global.dbClient.FIELD_CREAT +
					"='"+ Global.dbClient.CLI_MODIFICATION+ "' ";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i=cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	public int CountCreated2()
	{

		try
		{
			String query="SELECT count(*) FROM "+
					TABLENAME+
					" where "+
					"  "+
					Global.dbClient.FIELD_CREAT +
					"='"+ Global.dbClient.CLI_CREATION+ "' ";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i = cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	
	/*
	 * String query="SELECT * FROM "+
					Global.dbClient.TABLENAME+
					" where "+
					" ( "+
					Global.dbClient.FIELD_CREAT +
					"='"+ Global.dbClient.CLI_MODIFICATION+ "' ) "	;
	 */
	public void save(structClient client, boolean update){
		if(update){
			delete(client);
		}
		String tagCreation=client.CREAT;
		if (client.CREAT!=null && !client.CREAT.equals(CLI_CREATION))
			tagCreation=CLI_MODIFICATION;
		//si l'ancien tag est C on y touche pas, sinon ou met M
		try
		{
			String query="INSERT INTO " + TABLENAME +" ("+
					FIELD_CODE 		
					+", "+ FIELD_SOC_CODE 	
					+", "+ FIELD_CODEVRP 	
					+", "+ FIELD_NOM 		
					+", "+ FIELD_ADR1 		
					+", "+ FIELD_ADR2 		
					+", "+ FIELD_TEL1 		
					+", "+ FIELD_FAX 		
					+", "+ FIELD_CP 		
					+", "+ FIELD_VILLE 		
					+", "+ FIELD_LAT 		
					+", "+ FIELD_LON 		
					+", "+ FIELD_IS_GEOCODED
					+", "+ FIELD_ICON 		
					+", "+ FIELD_COULEUR 
					+", "+ FIELD_TYPESAV
					+", "+ FIELD_IMPORTANCE 
					+", "+ FIELD_TOVISIT 	
					+", "+ FIELD_JOURPASSAGE
					+", "+ FIELD_EMAIL
					+", "+ FIELD_ZONE
					+", "+ FIELD_STATUT
					+", "+ FIELD_CONTACT_NOM
					+", "+ FIELD_CATCOMPT
					+", "+ FIELD_PAYS
					+", "+ FIELD_SIRET
					+", "+ FIELD_NUMTVA
					+", "+ FIELD_COMMENT
					+", "+ FIELD_CREAT
					+", "+ FIELD_GSM
					+", "+ FIELD_ENSEIGNE
					+", "+ FIELD_TYPE
					+", "+ FIELD_GROUPECLIENT
					+", "+ FIELD_AGENT
					+", "+ FIELD_CIRCUIT	
					+", "+ FIELD_JOURFERMETURE
					+", "+ FIELD_MODEREGLEMENT
					+", "+ FIELD_MONTANTTOTALENCOURS
					+", "+ FIELD_MONTANTTOTALFACTURESDUES
					+", "+ FIELD_MONTANTTOTALAVOIR
					+", "+ FIELD_MONTANTTOTALPAIEMENT
					+", "+ FIELD_TYPEETABLISSEMENT
					+", "+ FIELD_FREETEXT
					+", "+ FIELD_EXONERATION
					+", "+ FIELD_CLI_ANNUEL
					+", "+ FIELD_CLI_ISHISTO
					+", "+ FIELD_CLI_ISFACDUES
					+", "+ FIELD_CLI_ISMATOS
					+", "+ FIELD_SAISON_ETE
					+", "+ FIELD_SAISON_HIVER
					+", "+ FIELD_ACTIVITE_P
					+", "+ FIELD_FAMCLIENT
					+", "+ FIELD_SFAMCLIENT
					+", "+ FIELD_STANDBY
					+", "+ FIELD_DATECLI
					+", "+ FIELD_CLASSE
					+", "+ FIELD_ETSPUBLIC
					+", "+ FIELD_ACTIF
					+", "+ FIELD_DATECREAT
					+", "+ FIELD_CLI_PASMAIL
					+", "+ FIELD_ENVOIFACT_PAR_MAIL

					+") VALUES ("+




					"'"+MyDB.controlFld(client.CODE)+"',"+
					"'"+MyDB.controlFld(client.SOC_CODE)+"',"+
					"'"+MyDB.controlFld(client.CODEVRP)+"',"+
					"'"+MyDB.controlFld(client.NOM)+"',"+
					"'"+MyDB.controlFld(client.ADR1)+"',"+
					"'"+MyDB.controlFld(client.ADR2)+"',"+
					"'"+MyDB.controlFld(client.TEL1)+"',"+
					"'"+MyDB.controlFld(client.FAX)+"',"+
					"'"+MyDB.controlFld(client.CP)+"',"+
					"'"+MyDB.controlFld(client.VILLE)+"',"+
					"'"+MyDB.controlFld(client.LAT)+"',"+
					"'"+MyDB.controlFld(client.LON)+"',"+
					"'"+"',"+
					"'"+MyDB.controlFld(client.ICON)+"',"+
					"'"+MyDB.controlFld(client.COULEUR)+"',"+
					"'"+MyDB.controlFld(client.TYPESAV)+"',"+
					"'"+MyDB.controlFld(client.IMPORTANCE)+"',"+
					"'"+MyDB.controlFld(client.TOVISIT)+"',"+
					"'"+MyDB.controlFld(client.JOURPASSAGE)+"',"+
					"'"+MyDB.controlFld(client.EMAIL)+"', "+
					"'"+MyDB.controlFld(client.ZONE)+"', "+
					"'"+MyDB.controlFld(client.STATUT)+"', "+				
					"'"+MyDB.controlFld(client.CONTACT_NOM)+"', "+
					"'"+MyDB.controlFld(client.CATCOMPT)+"', "+
					"'"+MyDB.controlFld(client.PAYS)+"', "+
					"'"+MyDB.controlFld(client.SIRET)+"', "+
					"'"+MyDB.controlFld(client.NUMTVA)+"', "+
					"'"+MyDB.controlFld(client.COMMENT)+"', "+
					"'"+tagCreation+"' ,"+
					"'"+MyDB.controlFld(client.GSM)+"', "+
					"'"+MyDB.controlFld(client.ENSEIGNE)+"', "+
					"'"+MyDB.controlFld(client.TYPE)+"', "+
					"'"+MyDB.controlFld(client.GROUPECLIENT)+"', "+
					"'"+MyDB.controlFld(client.AGENT)+"', "+
					"'"+MyDB.controlFld(client.CIRCUIT)+"', "+
					"'"+MyDB.controlFld(client.JOURFERMETURE)+"', "+
					"'"+MyDB.controlFld(client.MODEREGLEMENT)+"', "+
					"'"+MyDB.controlFld(client.MONTANTTOTALENCOURS)+"', "+			
					"'"+MyDB.controlFld(client.MONTANTTOTALFACTURESDUES)+"', "+
					"'"+MyDB.controlFld(client.MONTANTTOTALAVOIR)+"', "+
					"'"+MyDB.controlFld(client.MONTANTTOTALPAIEMENT)+"', "+
					"'"+MyDB.controlFld(client.TYPEETABLISSEMENT)+"', "+
					"'"+MyDB.controlFld(client.FREETEXT)+"', "+
					"'"+MyDB.controlFld(client.EXONERATION)+"', "+
					"'"+MyDB.controlFld(client.CLI_ANNUEL)+"', "+
					"'"+MyDB.controlFld(client.CLI_ISHISTO)+"', "+
					"'"+MyDB.controlFld(client.CLI_ISFACDUES)+"', "+
					"'"+MyDB.controlFld(client.CLI_ISMATOS)+"', "+
					"'"+MyDB.controlFld(client.SAISON_ETE)+"', "+
					"'"+MyDB.controlFld(client.SAISON_HIVER)+"', "+
					"'"+MyDB.controlFld(client.ACTIVITE_P)+"', "+
					"'"+MyDB.controlFld(client.FAMCLIENT)+"', "+
					"'"+MyDB.controlFld(client.SFAMCLIENT)+"', "+
					"'"+MyDB.controlFld(client.STANDBY)+"', "+
					"'"+MyDB.controlFld(client.DATECLI)+"', "+
					"'"+MyDB.controlFld(client.CLASSE)+"', "+
					"'"+MyDB.controlFld(client.ETSPUBLIC)+"', "+
					"'"+MyDB.controlFld(client.ETAT)+"', "+
					"'"+MyDB.controlFld(client.DATECREAT)+"' ,"+
					"'"+MyDB.controlFld(client.CLI_PASMAIL)+"' "+
					"'"+MyDB.controlFld(client.ENVOIFACT_PAR_MAIL)+"' "+




					")";



			db.conn.execSQL(query);
		}
		catch(Exception ex)
		{
			Debug.StackTrace(ex);

		}
	}

	public boolean isCreation(String codecli)
	{
		String query="";
		query="select * from "+
				TABLENAME+
				" where "+
				FIELD_CODE+"='"+codecli+"'";

		Cursor cur=db.conn.rawQuery (query,null);
		if (cur!=null)
		{
			if(cur.moveToNext())
			{
				String creat=Fonctions.GetStringDanem(giveFld(cur, FIELD_CREAT));
				if (creat.equals(CLI_CREATION)==false) return false;
			}
			if (cur!=null)
				cur.close();
		}
		return true;
	}


	public boolean delete(structClient client)
	{
		try
		{
			String query="DELETE from "+TABLENAME		
					+" WHERE "+getFullFieldName(FIELD_CODEVRP)+" = '"+client.CODEVRP+"'"
					+" AND "+getFullFieldName(FIELD_CODE)+" = '"+client.CODE+"'";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception e)
		{
			Debug.StackTrace(e);
		}
		return false;
	}

	public boolean delete(String code)
	{
		try
		{
			String query="DELETE from "+TABLENAME		
					+" WHERE "+
					getFullFieldName(FIELD_CODE)+" = '"+code+"'";

			db.conn.execSQL(query);
			Global.dbLog.Insert("CLIENT", "DELETE", "", "", query, "", "");

			return true;
		}
		catch(Exception e)
		{
			Debug.StackTrace(e);
		}
		return false;
	}
	public Cursor getAll(String user, GeoPoint centerPosition, int radiusMeter){
		Double latitudeDelta = getLatitudeDegree(centerPosition, radiusMeter);
		Double longitudeDelta = getLongitudeDegree(centerPosition, radiusMeter);

		//		Log.d("TAG", "delta lat/long : "+latitudeDelta+" / "+longitudeDelta);
		Double latitudeMin = (double)(Double.valueOf(centerPosition.getLatitudeE6())/1E6) - latitudeDelta; 
		Double latitudeMax = (double)(Double.valueOf(centerPosition.getLatitudeE6())/1E6) + latitudeDelta; 
		Double longitudeMin = (double)(Double.valueOf(centerPosition.getLongitudeE6())/1E6) - longitudeDelta; 
		Double longitudeMax = (double)(Double.valueOf(centerPosition.getLongitudeE6())/1E6) + longitudeDelta; 

		//		String query = "SELECT "
		//				+dbKD94GeoCode.FIELD_CODECLI
		//				//				+dbKD94GeoCode.FIELD_LATITUDE+","
		//				//				+dbKD94GeoCode.FIELD_LONGITUDE
		//				+" FROM "+dbKD94GeoCode.TABLENAME
		//				+" WHERE "+Global.dbKDGeoCode.getFullFieldName(dbKD94GeoCode.fld_kd_dat_type)+" = '"+dbKD94GeoCode.KD_TYPE+"'"
		//				+" AND "+Global.dbKDGeoCode.getFullFieldName(dbKD94GeoCode.FIELD_CODEREP)+" = '"+Global.AXE_Ident.IDENT+"'"
		//				+" AND "+Global.dbKDGeoCode.getFullFieldName(dbKD94GeoCode.FIELD_CODECLI)+" <> '"+Global.AXE_Client.CODECLIENT+"'"
		//				+" AND CAST("+Global.dbKDGeoCode.getFullFieldName(dbKD94GeoCode.FIELD_LATITUDE)+" as Double) BETWEEN "+latitudeMin+" AND "+latitudeMax
		//				+" AND CAST("+Global.dbKDGeoCode.getFullFieldName(dbKD94GeoCode.FIELD_LONGITUDE)+" as Double) BETWEEN "+longitudeMin+" AND "+longitudeMax;




		Double latitudeMinE6 = latitudeMin*1E6; 
		Double latitudeMaxE6 = latitudeMax*1E6; 
		Double longitudeMinE6 = longitudeMin*1E6; 
		Double longitudeMaxE6 = longitudeMax*1E6; 

		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_CODEVRP)+" = '"+user+"'"
				+" AND (CAST("+getFullFieldName(FIELD_LAT)+" as Double) BETWEEN "+latitudeMinE6+" AND "+latitudeMaxE6
				+" AND CAST("+getFullFieldName(FIELD_LON)+" as Double) BETWEEN "+longitudeMinE6+" AND "+longitudeMaxE6 +")"
				+" or CAST("+getFullFieldName(FIELD_LAT)+" as Double)=0";

		//		if(!Fonctions.isEmptyOrNull(limit)){
		//			query +=" LIMIT "+limit;
		//		}
		return db.conn.rawQuery(query, null);


		//			if(result != null && result.moveToFirst()){
		//				while(result.isAfterLast() == false)
		//				{
		//					Debug.Log(result.getString(result.getColumnIndex(CalendarContract.Events.TITLE)));
		//
		//					result.moveToNext();
		//				}
		//				result.close();
		//
		//			}



	}


	public Cursor getAll(String codeSociete, String limit){
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE 1=1";
		if(!Fonctions.isEmptyOrNull(codeSociete))
			query+=" AND "+getFullFieldName(FIELD_SOC_CODE)+" = '"+codeSociete+"'";


		if(!Fonctions.isEmptyOrNull(limit)){
			query +=" LIMIT "+limit;
		}
		Debug.Log("TAG4", query);
		return db.conn.rawQuery(query, null);


		//			if(result != null && result.moveToFirst()){
		//				while(result.isAfterLast() == false)
		//				{
		//					Debug.Log(result.getString(result.getColumnIndex(CalendarContract.Events.TITLE)));
		//
		//					result.moveToNext();
		//				}
		//				result.close();
		//
		//			}



	}

	public Cursor getAllNotGeocoded(String codeSociete, String limit){
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE 1=1" 
				+" AND ("+getFullFieldName(FIELD_LAT)+" ='' or "+getFullFieldName(FIELD_LON)+" ='' )";

		if(!Fonctions.isEmptyOrNull(codeSociete))
			query+=" AND "+getFullFieldName(FIELD_SOC_CODE)+" = '"+codeSociete+"'";

		if(!Fonctions.isEmptyOrNull(limit)){
			query +=" LIMIT "+limit;
		}

		Debug.Log("TAG4", query);
		return db.conn.rawQuery(query, null);


		//			if(result != null && result.moveToFirst()){
		//				while(result.isAfterLast() == false)
		//				{
		//					Debug.Log(result.getString(result.getColumnIndex(CalendarContract.Events.TITLE)));
		//
		//					result.moveToNext();
		//				}
		//				result.close();
		//
		//			}



	}

	public GeoPoint getGeoPoint(String codeClient){
		GeoPoint result = null; 
		structClient client = load(codeClient);
		if(!Fonctions.isEmptyOrNull(client.CODE) && !Fonctions.isEmptyOrNull(client.LAT) && !Fonctions.isEmptyOrNull(client.LON))
			result = new GeoPoint(Integer.valueOf(client.LAT), Integer.valueOf(client.LON));

		return result;
	}

	/** Radius */
	private double getLatitudeDegree(GeoPoint centerPosition, int radiusMeter){
		//	1° latitude = 111120m
		//  1° longitude = 111120m*cos(latitude)
		return (double)radiusMeter/111120;
	}

	private double getLongitudeDegree(GeoPoint centerPosition, int radiusMeter){
		//	1° latitude = 111120m
		//  1° longitude = 111120m*cos(latitude)
		return (radiusMeter/Math.cos(Math.toRadians((double)(Double.valueOf(centerPosition.getLatitudeE6())/1E6))))/(111120);
	}


	public ArrayList<String> getZones(){
		ArrayList<String> result = new ArrayList<String>();
		String query = "SELECT distinct "+FIELD_ZONE
				+" FROM "+TABLENAME
				+" WHERE 1=1"
				+" ORDER BY "+FIELD_ZONE+" ASC";

		Cursor cursor = db.conn.rawQuery(query, null);
		if(cursor != null && cursor.moveToFirst()){
			while(cursor.isAfterLast() == false)
			{
				result.add(giveFld(cursor, FIELD_ZONE));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return result;
	}

	public String GetCodeProspect(String user)
	{  
		String stNum="";

		boolean existe=true;		
		while(existe==true)
		{
			/**
			 * R�cup�ration du jour de l'ann�e
			 */
			String stDayofYear ="";
			String stYear ="";
			String minutes ="";

			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat Annee = new SimpleDateFormat("yyyy");
			SimpleDateFormat minute = new SimpleDateFormat("mms");

			stDayofYear =Fonctions.getDay_Of_Year();
			stYear=Annee.format(gc.getTime());
			minutes=minute.format(gc.getTime());

			stNum="NC"+user+stYear.substring(3, 4)+stDayofYear+minutes.substring(0, 3);
			//1+2+1+3+3



			existe=false;

			String query="";
			query="select * from "+
					TABLENAME+
					" where "+
					FIELD_CODE+"='"+stNum+"'";

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur!=null)
			{
				if(cur.moveToNext())
				{
					existe=true;	
				}

			}
			if (cur!=null)
				cur.close();
		}

		return stNum;		
	}

	public boolean getClientIsEtsPublic(String idClient)
	{
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_CODE+
					"='"+idClient+"' "+
					"AND " + FIELD_ETSPUBLIC+ " = '" + CLI_IS_ETS_PUBLIC + "'" ;

			Log.e("query",""+query);

			Cursor cur=db.conn.rawQuery(query, null);
			if ( cur.getCount() > 0)
			{
				return true;
			}

			return false;
		}
		catch(Exception ex)
		{
			Log.e("Exception",""+ex.getLocalizedMessage());

			return false;
		}

	}

	public boolean getClient(String id,structClient cli,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_CODE+
					"='"+id+"'";

			Log.e("query",""+query);

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				fillStruct(cur,cli);
				if (cur!=null)
					cur.close();
				return true;
			}
			if (cur!=null)
				cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public ArrayList<structClient> getClientsGeocodState(boolean geocoded )
	{	
		try
		{
			String query="";
			if (!geocoded)
				query="select * FROM "+
						TABLENAME+
						" where "+
						FIELD_LAT+
						"=''";
			else
				query="select * FROM "+
						TABLENAME+
						" where "+
						FIELD_LAT+
						"<>''";

			ArrayList<structClient> clis=new ArrayList<TableClient.structClient>();

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				structClient cli=new structClient();
				fillStruct(cur,cli);

				clis.add(cli);
			}
			if (cur!=null)
				cur.close();
			return clis;

		}
		catch(Exception ex)
		{

		}

		return null;
	}
	/*
	 * 
	 * cherche un client par son nom
	 */
	public boolean getClientbyName(String nom,structClient cli,StringBuilder err)
	{	
		try
		{
			nom=MyDB.controlFld(nom);
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_ENSEIGNE+
					" like '%"+nom+"%'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				fillStruct(cur,cli);
				if (cur!=null)
					cur.close();
				return true;
			}
			if (cur!=null)
				cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public String[] getClients()
	{	
		try
		{
			String query="";
			query="select "+FIELD_ENSEIGNE+" FROM "+
					TABLENAME;


			ArrayList<String> liste=new ArrayList<String>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				String nom		=giveFld(cur,this.FIELD_ENSEIGNE).trim();;
				liste.add(nom);
			}

			if (cur!=null)
				cur.close();
			return liste.toArray(new String[liste.size()]);

		}
		catch(Exception ex)
		{

		}

		return null;
	}

	void fillStruct(Cursor cur,structClient cli)
	{
		cli.ADR1=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ADR1));
		cli.ADR2=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ADR2));
		cli.CATCOMPT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CATCOMPT));
		cli.CODE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE));
		cli.CODEVRP=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODEVRP));
		cli.COMMENT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COMMENT));
		//		cli.CONTACT_GSM=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CONTACT_GSM));
		cli.CONTACT_NOM=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CONTACT_NOM));
		cli.COULEUR=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COULEUR));
		cli.TYPESAV=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TYPESAV));
		cli.CP=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CP));
		cli.EMAIL=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EMAIL));
		cli.FAX=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FAX));
		cli.ICON=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ICON));
		cli.IMPORTANCE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_IMPORTANCE));
		cli.IS_GEOCODED=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_IS_GEOCODED));
		cli.JOURPASSAGE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_JOURPASSAGE));
		cli.LAT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_LAT));
		cli.LON=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_LON));
		cli.NOM=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NOM));
		cli.NUMTVA=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NUMTVA));
		cli.PAYS=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PAYS));
		cli.SIRET=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SIRET));
		cli.SOC_CODE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SOC_CODE));
		cli.STATUT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT));
		cli.TEL1=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TEL1));
		cli.TOVISIT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOVISIT));
		cli.VILLE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE));
		cli.ZONE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ZONE));

		cli.CREAT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CREAT));

		cli.GSM=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_GSM));
		cli.ENSEIGNE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ENSEIGNE));
		cli.TYPE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TYPE));
		cli.GROUPECLIENT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_GROUPECLIENT));
		cli.AGENT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_AGENT));
		cli.CIRCUIT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CIRCUIT));
		cli.JOURFERMETURE=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_JOURFERMETURE));
		cli.MODEREGLEMENT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MODEREGLEMENT));
		cli.MONTANTTOTALENCOURS=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MONTANTTOTALENCOURS));
		cli.MONTANTTOTALFACTURESDUES=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MONTANTTOTALFACTURESDUES));
		cli.MONTANTTOTALAVOIR=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MONTANTTOTALAVOIR));
		cli.MONTANTTOTALPAIEMENT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MONTANTTOTALPAIEMENT));

		cli.TYPEETABLISSEMENT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TYPEETABLISSEMENT));
		cli.FREETEXT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FREETEXT));
		cli.EXONERATION=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EXONERATION));
		cli.ETAT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ACTIF));
		cli.DATECREAT=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATECREAT));

		cli.CODETRF=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODETRF));

		cli.CODEFACT = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODEFACT));
		cli.CLI_SAV = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_SAV));
		cli.CA_MOY_FAC = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CA_MOY_FAC));
		
		cli.PERIODICITE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PERIODICITE));
		cli.TOURNEE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE));
		cli.TVA = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TVA));
		cli.CODECOM = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODECOM));
		cli.CLI_ANNUEL = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ANNUEL));
		
		cli.CLI_ISHISTO = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISHISTO));
		cli.CLI_ISFACDUES = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISFACDUES));
		cli.CLI_ISMATOS = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISMATOS));
		cli.CLI_AGENT2 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_AGENT2));
		cli.EXCLURE_MAT = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EXCLURE_MAT));
		cli.SAISON_ETE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_ETE));
		cli.SAISON_HIVER = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_HIVER));
		cli.ACTIVITE_P = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ACTIVITE_P));
		cli.FAMCLIENT = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FAMCLIENT));
		cli.SFAMCLIENT = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SFAMCLIENT));
		cli.STANDBY = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STANDBY));
		cli.DATECLI = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATECLI));
		cli.CLASSE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLASSE));
		cli.ETSPUBLIC = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ETSPUBLIC));
		cli.CLI_PASMAIL = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_PASMAIL));

		cli.CAMT_0001 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0001));
		cli.CAMT_0002 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0002));
		cli.CAMT_0003 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0003));
		cli.CAMT_0004 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0004));
		cli.PARTCARANG = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PARTCARANG));
		cli.CARANG = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CARANG));
		cli.VP_AGRANG = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_AGRANG));
		cli.VP_VRPRANG = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_VRPRANG));
		cli.VP_CAMT_0001 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0001));
		cli.VP_CAMT_0002 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0002));
		cli.VP_CAMT_0003 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0003));
		cli.VP_CAMT_0004 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0004));
		cli.VP_MARGE_0001 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0001));
		cli.VP_MARGE_0002 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0002));
		cli.VP_MARGE_0003 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0003));
		cli.VP_MARGE_0004 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0004));
		cli.RENT_0001 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0001));
		cli.RENT_0002 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0002));
		cli.RENT_0003 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0003));
		cli.RENT_0004 = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0004));
		cli.MOD_LIV = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MOD_LIV));
		cli.VOL_CAFE_ANNUEL = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VOL_CAFE_ANNUEL));
		cli.POT_CA = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_POT_CA));
		cli.PLACEASSINT = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PLACEASSINT));
		cli.PLACEASSEXT = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PLACEASSEXT));
		cli.CAPACITE_SDR = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAPACITE_SDR));
		cli.NB_CHAMBRES = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NB_CHAMBRES));
		cli.NB_LITS = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NB_LITS));
		cli.QUALIF = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_QUALIF));
		cli.SITUATION = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SITUATION));
		cli.OPTION_P = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_OPTION_P));
		cli.TYPECUISINE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TYPECUISINE));
		cli.PV_CAFE =cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PV_CAFE));
		cli.PV_THE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PV_THE));
		cli.PV_CHOCOLAT =cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PV_CHOCOLAT));
		cli.PV_PETIT_DEJ =cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PV_PETIT_DEJ));
		cli.PV_CHAMBRE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PV_CHAMBRE));
		cli.STD_BY = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STD_BY));
		cli.INFO_TERRAIN = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_INFO_TERRAIN));
		cli.BOOK = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_BOOK));
		cli.TRIPADVISOR = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TRIPADVISOR));
		cli.NO_FAC = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NO_FAC));
		cli.STATUT_PRO = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT_PRO));
		cli.COD_REPP = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COD_REPP));
		cli.DATE_RELANCE = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATE_RELANCE));
		cli.ENVOIFACT_PAR_MAIL = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ENVOIFACT_PAR_MAIL));

	}


	public boolean saveProspect(structClient ent,String codeclient,String tagCreation,boolean typeP,StringBuffer stBuf)
	{
		try
		{
			//String tagCreation=getCreatValue(codeclient);
			if (!tagCreation.equals(CLI_CREATION))
				tagCreation=CLI_MODIFICATION;

			String stypeP_FAMCLIENT=" ";
			if (typeP == true) {
				stypeP_FAMCLIENT=FIELD_FAMCLIENT + "=" +
						"'" + MyDB.controlFld(ent.FAMCLIENT) + "', " ;
			}

			String query="SELECT * FROM "+
					TABLENAME+
					" where "+
					FIELD_CODE+"="+
					"'"+codeclient+"' ";

			//transforme lat et lon en entier
			if (Fonctions.GetStringDanem( ent.LAT ).contains("."))
			{
				float lat=Fonctions.convertToFloat(ent.LAT);
				int ilat= Integer.valueOf((int) (lat*1000000));

				float lon=Fonctions.convertToFloat(ent.LON);
				int ilon= Integer.valueOf((int) (lon*1000000));

				ent.LAT=String.valueOf(ilat);
				ent.LON=String.valueOf(ilon);
			}

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{
				query="UPDATE "+TABLENAME+
						" set "+
				FIELD_CODEVRP+"="+
				"'"+ent.CODEVRP+"',"+
				FIELD_CLI_AGENT2+"="+
				"'"+MyDB.controlFld(ent.CLI_AGENT2)+"',"+
				FIELD_TEL1+"="+
				"'"+MyDB.controlFld(ent.TEL1 )+"',"+
				FIELD_LAT+"="+
				"'"+MyDB.controlFld(ent.LAT )+"',"+
				FIELD_LON+"="+
				"'"+MyDB.controlFld(ent.LON) +"',"+
				FIELD_JOURPASSAGE+"="+
				"'"+ent.JOURPASSAGE +"',"+
				FIELD_EMAIL+"="+
				"'"+MyDB.controlFld(ent.EMAIL) +"',"+
				FIELD_ZONE+"="+
				"'"+MyDB.controlFld(ent.ZONE )+"',"+
				FIELD_CREAT+"="+
				"'"+tagCreation+"', "+
				FIELD_GSM+"="+
				"'"+MyDB.controlFld(ent.GSM) +"', "+
				FIELD_JOURFERMETURE+"="+
				"'"+MyDB.controlFld(ent.JOURFERMETURE) +"', "+
				FIELD_TYPEETABLISSEMENT+"="+
				"'"+MyDB.controlFld(ent.TYPEETABLISSEMENT) +"', "+
				FIELD_PERIODICITE+"="+
				"'"+MyDB.controlFld(ent.PERIODICITE) +"', "+
				FIELD_TOURNEE+"="+
				"'"+MyDB.controlFld(ent.TOURNEE) +"', "+
				FIELD_CLI_ANNUEL+"="+
				"'"+MyDB.controlFld(ent.CLI_ANNUEL) +"', "+
				FIELD_SAISON_ETE+"="+
				"'"+MyDB.controlFld(ent.SAISON_ETE )+"', "+
				FIELD_SAISON_HIVER+"="+
				"'"+MyDB.controlFld(ent.SAISON_HIVER )+"', "+
				FIELD_ACTIVITE_P+"="+
				"'"+MyDB.controlFld(ent.ACTIVITE_P )+"', "+
				stypeP_FAMCLIENT+
				FIELD_SFAMCLIENT+"="+
				"'"+MyDB.controlFld(ent.SFAMCLIENT )+"', "+
				FIELD_STANDBY+"="+
				"'"+MyDB.controlFld(ent.STANDBY )+"', "+
				FIELD_DATECLI+"="+
				"'"+MyDB.controlFld(ent.DATECLI )+"', "+
				FIELD_CLASSE+"="+
				"'"+MyDB.controlFld(ent.CLASSE )+"', "+
				FIELD_ETSPUBLIC+"="+
				"'"+MyDB.controlFld(ent.ETSPUBLIC )+"', "+
				FIELD_CLI_PASMAIL+"="+
				"'"+MyDB.controlFld(ent.CLI_PASMAIL )+"', "+
				FIELD_VOL_CAFE_ANNUEL+"="+
				"'"+MyDB.controlFld(ent.VOL_CAFE_ANNUEL) +"',"+
				FIELD_POT_CA+"="+
				"'"+MyDB.controlFld(ent.POT_CA) +"',"+
				FIELD_PLACEASSINT+"="+
				"'"+MyDB.controlFld(ent.PLACEASSINT) +"',"+
				FIELD_PLACEASSEXT+"="+
				"'"+MyDB.controlFld(ent.PLACEASSEXT) +"',"+
				FIELD_CAPACITE_SDR+"="+
				"'"+MyDB.controlFld(ent.CAPACITE_SDR) +"',"+
				FIELD_NB_CHAMBRES+"="+
				"'"+MyDB.controlFld(ent.NB_CHAMBRES) +"',"+
				FIELD_NB_LITS+"="+
				"'"+MyDB.controlFld(ent.NB_LITS) +"',"+
				FIELD_QUALIF+"="+
				"'"+MyDB.controlFld(ent.QUALIF) +"',"+
				FIELD_SITUATION+"="+
				"'"+MyDB.controlFld(ent.SITUATION) +"',"+
				FIELD_OPTION_P+"="+
				"'"+MyDB.controlFld(ent.OPTION_P) +"',"+
				FIELD_TYPECUISINE+"="+
				"'"+MyDB.controlFld(ent.TYPECUISINE) +"',"+
				FIELD_PV_CAFE+"="+
				"'"+MyDB.controlFld(ent.PV_CAFE) +"',"+
				FIELD_PV_THE+"="+
				"'"+MyDB.controlFld(ent.PV_THE) +"',"+
				FIELD_PV_CHOCOLAT+"="+
				"'"+MyDB.controlFld(ent.PV_CHOCOLAT) +"',"+
				FIELD_PV_PETIT_DEJ+"="+
				"'"+MyDB.controlFld(ent.PV_PETIT_DEJ) +"',"+
				FIELD_PV_CHAMBRE+"="+
				"'"+MyDB.controlFld(ent.PV_CHAMBRE) +"',"+
				FIELD_STD_BY+"="+
				"'"+MyDB.controlFld(ent.STD_BY )+"', "+
				FIELD_INFO_TERRAIN+"="+
				"'"+MyDB.controlFld(ent.INFO_TERRAIN )+"', "+
				FIELD_DATECREAT+"="+
				"'"+MyDB.controlFld(ent.DATECREAT )+"' ,"+
				FIELD_ENVOIFACT_PAR_MAIL+"="+
				"'"+MyDB.controlFld(ent.ENVOIFACT_PAR_MAIL )+"' "+
				" where "+
					FIELD_CODE+"="+
					"'"+codeclient+"' ";

				db.conn.execSQL(query);

				Global.dbLog.Insert("CLIENT", "SAVE UPDATE", "", "", query, "", "");

			}
			else		  
			{	  		
				query="INSERT INTO " + TABLENAME +" ("+
				FIELD_CODE+","+
				FIELD_SOC_CODE+","+
				FIELD_CODEVRP+","+
				FIELD_NOM+","+
				FIELD_ADR1+","+
				FIELD_ADR2+","+
				FIELD_TEL1+","+
				FIELD_FAX+","+
				FIELD_CP+","+
				FIELD_VILLE+","+
				FIELD_LAT+","+
				FIELD_LON+","+
				FIELD_ICON+","+
				FIELD_COULEUR+","+
				FIELD_TYPESAV+","+
				FIELD_IMPORTANCE+","+
				FIELD_TOVISIT+","+
				FIELD_JOURPASSAGE+","+
				FIELD_EMAIL+","+
				FIELD_ZONE+","+
				FIELD_STATUT+","+
				FIELD_CONTACT_NOM+","+
				FIELD_PAYS+", "+
				FIELD_CATCOMPT+", "+
				FIELD_SIRET+", "+
				FIELD_NUMTVA+", "+
				FIELD_COMMENT+", "+
				FIELD_CREAT+", "+
				FIELD_GSM+", "+
				FIELD_ENSEIGNE+", "+
				FIELD_TYPE+", "+
				FIELD_GROUPECLIENT+", "+
				//FIELD_AGENT+", "+
				FIELD_CIRCUIT+", "+
				FIELD_JOURFERMETURE+", "+
				FIELD_MODEREGLEMENT+", "+
				FIELD_MONTANTTOTALENCOURS+", "+
				FIELD_MONTANTTOTALFACTURESDUES+", "+
				FIELD_MONTANTTOTALAVOIR+", "+
				FIELD_MONTANTTOTALPAIEMENT+", "+
				FIELD_TYPEETABLISSEMENT+", "+
				FIELD_PERIODICITE+", "+
				FIELD_TOURNEE+", "+
				FIELD_FREETEXT+", "+
				FIELD_EXONERATION+", "+
				FIELD_CLI_ANNUEL+", "+
				FIELD_CLI_ISHISTO+", "+
				FIELD_CLI_ISFACDUES+", "+
				FIELD_CLI_ISMATOS+", "+
				FIELD_ACTIF+", "+
				FIELD_DATECREAT+" ,"+
				FIELD_CLI_AGENT2+", "+
				FIELD_EXCLURE_MAT+", "+
				FIELD_SAISON_ETE+", "+
				FIELD_SAISON_HIVER+", "+
				FIELD_ACTIVITE_P+", "+
				FIELD_FAMCLIENT+", "+
				FIELD_SFAMCLIENT+" ,"+
				FIELD_STANDBY+" ,"+
				FIELD_DATECLI+" ,"+
				FIELD_CLASSE+" ,"+
				FIELD_ETSPUBLIC+", "+
				FIELD_CLI_PASMAIL+" ,"+
				FIELD_VOL_CAFE_ANNUEL+", "+
				FIELD_POT_CA+", "+
				FIELD_PLACEASSINT+", "+
				FIELD_PLACEASSEXT+", "+
				FIELD_CAPACITE_SDR+", "+
				FIELD_NB_CHAMBRES+", "+
				FIELD_NB_LITS+", "+
				FIELD_QUALIF+", "+
				FIELD_SITUATION+", "+
				FIELD_OPTION_P+", "+
				FIELD_TYPECUISINE+", "+
				FIELD_PV_CAFE+", "+
				FIELD_PV_THE+", "+
				FIELD_PV_CHOCOLAT+", "+
				FIELD_PV_PETIT_DEJ+", "+
				FIELD_PV_CHAMBRE+" ,"+
				FIELD_STD_BY+", "+
				FIELD_INFO_TERRAIN+", "+
				FIELD_ENVOIFACT_PAR_MAIL+" "+



				") VALUES ("+
    	  		"'"+MyDB.controlFld(ent.CODE)    +"',"+
    	  		"'"+MyDB.controlFld(ent.SOC_CODE)       +"',"+
    	  		"'"+MyDB.controlFld(ent.CODEVRP)      +"',"+
    	  		"'"+MyDB.controlFld(ent.NOM)        +"',"+
    	  		"'"+MyDB.controlFld(ent.ADR1)        +"',"+
    	  		"'"+MyDB.controlFld(ent.ADR2) 		+"',"+
    	  		"'"+MyDB.controlFld(ent.TEL1)	 	+"',"+
    	  		"'"+MyDB.controlFld(ent.FAX )	+"',"+
    	  		"'"+MyDB.controlFld(ent.CP) 		+"',"+
    	  		"'"+MyDB.controlFld(ent.VILLE )		+"',"+
    	  		"'"+MyDB.controlFld(ent.LAT) 		+"',"+
    	  		"'"+MyDB.controlFld(ent.LON)		 +"',"+
    	  		"'"+MyDB.controlFld(ent.ICON )		+"',"+
    	  		"'"+MyDB.controlFld(ent.COULEUR )			+"',"+
    	  		"'"+MyDB.controlFld(ent.TYPESAV)			+"',"+
    	  		"'"+MyDB.controlFld(ent.IMPORTANCE )			+"',"+
    	  		"'"+MyDB.controlFld(ent.TOVISIT )		+"',"+
    	  		"'"+MyDB.controlFld(ent.JOURPASSAGE )			+"',"+
    	  		"'"+MyDB.controlFld(ent.EMAIL )			+"',"+
    	  		"'"+MyDB.controlFld(ent.ZONE) 			+"',"+
    	  		"'"+MyDB.controlFld(ent.STATUT) 	+"',"+
    	  		"'"+MyDB.controlFld(ent.CONTACT_NOM) 		+"',"+
				"'"+MyDB.controlFld(ent.PAYS) 			+"',"+
				"'"+MyDB.controlFld(ent.CATCOMPT) 			+"',"+
				"'"+MyDB.controlFld(ent.SIRET) 			+"',"+
				"'"+MyDB.controlFld(ent.NUMTVA) 			+"',"+
				"'"+MyDB.controlFld(ent.COMMENT) 			+"',"+
    	  		"'"+CLI_CREATION	+"',"+
    	  		"'"+MyDB.controlFld(ent.GSM) 			+"',"+
    	  		"'"+MyDB.controlFld(ent.ENSEIGNE) 			+"',"+
    	  		"'"+MyDB.controlFld(ent.TYPE) 			+"',"+
    	  		"'"+MyDB.controlFld(ent.GROUPECLIENT) 			+"',"+
    	  		//"'"+MyDB.controlFld(ent.AGENT) 			+"',"+
    	  		"'"+MyDB.controlFld(ent.CIRCUIT) 			+"',"+
    	  		"'"+MyDB.controlFld(ent.JOURFERMETURE) 			+"',"+
				"'"+MyDB.controlFld(ent.MODEREGLEMENT) 			+"',"+
				"'"+MyDB.controlFld(ent.MONTANTTOTALENCOURS) 			+"',"+
				"'"+MyDB.controlFld(ent.MONTANTTOTALFACTURESDUES) 			+"',"+
				"'"+MyDB.controlFld(ent.MONTANTTOTALAVOIR) 			+"',"+
				"'"+MyDB.controlFld(ent.MONTANTTOTALPAIEMENT) 			+"',"+
				"'"+MyDB.controlFld(ent.TYPEETABLISSEMENT) 			+"',"+
				"'"+MyDB.controlFld(ent.PERIODICITE) 			+"',"+
				"'"+MyDB.controlFld(ent.TOURNEE) 			+"',"+
				"'"+MyDB.controlFld(ent.FREETEXT) 			+"',"+
				"'"+MyDB.controlFld(ent.EXONERATION) 			+"',"+
				"'"+MyDB.controlFld(ent.CLI_ANNUEL) 			+"',"+
				"'"+MyDB.controlFld(ent.CLI_ISHISTO) 			+"',"+
				"'"+MyDB.controlFld(ent.CLI_ISFACDUES) 			+"',"+
				"'"+MyDB.controlFld(ent.CLI_ISMATOS) 			+"',"+
				"'"+MyDB.controlFld(ent.ETAT) 			+"',"+
				"'"+MyDB.controlFld(ent.DATECREAT) 			+"',"+
				"'"+MyDB.controlFld(ent.CLI_AGENT2) 			+"',"+
				"'"+MyDB.controlFld(ent.EXCLURE_MAT) 			+"',"+
				"'"+MyDB.controlFld(ent.SAISON_ETE) 			+"',"+
				"'"+MyDB.controlFld(ent.SAISON_HIVER) 			+"',"+
				"'"+MyDB.controlFld(ent.ACTIVITE_P) 			+"',"+
				"'"+MyDB.controlFld(ent.FAMCLIENT) 			+"',"+
				"'"+MyDB.controlFld(ent.SFAMCLIENT) 			+"',"+
				"'"+MyDB.controlFld(ent.STANDBY) 			+"',"+
				"'"+MyDB.controlFld(ent.DATECLI) 			+"',"+
				"'"+MyDB.controlFld(ent.CLASSE) 			+"',"+
				"'"+MyDB.controlFld(ent.ETSPUBLIC) 			+"',"+
				"'"+MyDB.controlFld(ent.CLI_PASMAIL) 		+"',"+
				"'"+MyDB.controlFld(ent.VOL_CAFE_ANNUEL) 		+"',"+
				"'"+MyDB.controlFld(ent.POT_CA) 		+"',"+
				"'"+MyDB.controlFld(ent.PLACEASSINT) 		+"',"+
				"'"+MyDB.controlFld(ent.PLACEASSEXT) 		+"',"+
				"'"+MyDB.controlFld(ent.CAPACITE_SDR) 		+"',"+
				"'"+MyDB.controlFld(ent.NB_CHAMBRES) 		+"',"+
				"'"+MyDB.controlFld(ent.NB_LITS) 		+"',"+
				"'"+MyDB.controlFld(ent.QUALIF) 		+"',"+
				"'"+MyDB.controlFld(ent.SITUATION) 		+"',"+
				"'"+MyDB.controlFld(ent.OPTION_P) 		+"',"+
				"'"+MyDB.controlFld(ent.TYPECUISINE) 		+"',"+
				"'"+MyDB.controlFld(ent.PV_CAFE) 		+"',"+
				"'"+MyDB.controlFld(ent.PV_THE) 		+"',"+
				"'"+MyDB.controlFld(ent.PV_CHOCOLAT) 		+"',"+
				"'"+MyDB.controlFld(ent.PV_PETIT_DEJ) 		+"',"+
				"'"+MyDB.controlFld(ent.PV_CHAMBRE) 		+"',"+
				"'"+MyDB.controlFld(ent.STD_BY) 		   +"',"+
				"'"+MyDB.controlFld(ent.INFO_TERRAIN) 		+"',"+
				"'"+MyDB.controlFld(ent.ENVOIFACT_PAR_MAIL) +"'"+
				")";

				db.conn.execSQL(query);
				Global.dbLog.Insert("CLIENT", "SAVE INSERT", "", "", query, "", "");

			}
		}
		catch(Exception ex)
		{
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;
		}

		return true;
	}
	
	public boolean saveModifJourFermeture(String codeclient,String modif,String jourfer,String date,StringBuffer stBuf)
	{
		try
		{
			//String tagCreation=getCreatValue(codeclient);
			//if (!tagCreation.equals(CLI_CREATION))
			String tagCreation=modif;

			String query="SELECT * FROM "+
					TABLENAME+
					" where "+
					FIELD_CODE+"="+
					"'"+codeclient+"' ";

			

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{
				query="UPDATE "+TABLENAME+
						" set "+
						
						

						FIELD_CREAT+"="+
						"'"+tagCreation+"', "+
						
						FIELD_JOURFERMETURE+"="+
						"'"+MyDB.controlFld(jourfer) +"', "+

						FIELD_DATECREAT+"="+
						"'"+MyDB.controlFld(date)+"' "+





						" where "+
						FIELD_CODE+"="+
						"'"+codeclient+"' ";

				;	  		

				db.conn.execSQL(query);

			}
			

			
		}
		catch(Exception ex)
		{
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;
		}

		return true;
	}
	
	public boolean saveModifEmail(String codeclient,String email,String date,StringBuffer stBuf)
	{
		try
		{
			
			String query="SELECT * FROM "+
					TABLENAME+
					" where "+
					FIELD_CODE+"="+
					"'"+codeclient+"' ";

			

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{
				query="UPDATE "+TABLENAME+
						" set "+
						
						

						FIELD_CREAT+"="+
						"'"+CLI_MODIFICATION+"', "+
						
						FIELD_EMAIL+"="+
						"'"+MyDB.controlFld(email) +"', "+

						FIELD_DATECREAT+"="+
						"'"+MyDB.controlFld(date)+"' "+





						" where "+
						FIELD_CODE+"="+
						"'"+codeclient+"' ";

				;	  		

				db.conn.execSQL(query);

			}
			

			
		}
		catch(Exception ex)
		{
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;
		}

		return true;
	}

	public boolean saveUpdateTypeclient(String typecli,String date,String codeclient,StringBuffer stBuf)
	{
		try
		{
			/*String tagCreation=getCreatValue(codeclient);
			if (!tagCreation.equals(CLI_CREATION))
				tagCreation=CLI_MODIFICATION;
			 */
			String query="SELECT * FROM "+
					TABLENAME+
					" where "+
					FIELD_CODE+"="+
					"'"+codeclient+"' ";

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{
				query="UPDATE "+TABLENAME+
                        " set "+
                        FIELD_TYPE+"="+
                        "'"+MyDB.controlFld(typecli )+"', "+


                        FIELD_DATECREAT+"="+
                        "'"+MyDB.controlFld(date )+"' "+

                        " where "+
                        FIELD_CODE+"="+
                        "'"+codeclient+"' ";

                ;

                db.conn.execSQL(query);

			}
			if (cur!=null)
				cur.close();

		}
		catch(Exception ex)
		{
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;
		}

		return true;
	}
	public boolean updateLatLon(String lat,String lon, String codeclient )
	{
		try
		{
			//on update la table kd pour synchro avec le serveur
			dbKD732InfoClientComplementaires info=new dbKD732InfoClientComplementaires(db);
			info.save(codeclient, lat, lon);

			//on update la table client pour le fonctionnement local

			String query="UPDATE "+TABLENAME+
					" set "+
					FIELD_LAT+"="+
					"'"+MyDB.controlFld(lat )+"', "+

					FIELD_LON+"="+
					"'"+MyDB.controlFld(lon )+"' "+

					" where "+
					FIELD_CODE+"="+
					"'"+codeclient+"' ";

			;	  		

			db.conn.execSQL(query);


		}
		catch(Exception ex)
		{

			Global.lastErrorMessage=(ex.getMessage());
			return false;
		}

		return true;
	}

    public boolean updateInfosTerrain(String infos, String codeclient )
    {
        try
        {
            //on update la table client pour le fonctionnement local

            String query="UPDATE "+TABLENAME+
                    " set "+
                    FIELD_INFO_TERRAIN+"="+
                    "'"+MyDB.controlFld(infos )+"'"+
                    " where "+
                    FIELD_CODE+"="+
                    "'"+codeclient+"' ";
            ;

            Log.e("query",""+query);

            db.conn.execSQL(query);

        }
        catch(Exception ex)
        {
            Global.lastErrorMessage=(ex.getMessage());
            return false;
        }

        return true;
    }
	//recupere le code de l'icone grace au lbl
	public String getCodeIcon(String icon)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_CLIACTIV, icon);
	}
	public String getLblIcon(String codeIcon)
	{
		return Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_CLIACTIV, codeIcon);
	}
	//
	public String getCodeTypeEtablissement(String typeetab)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_TYPEETABLISSEMENT, typeetab);
	}
	public String getCodeModeReglement(String moderegl)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_MODEREGLEMENT, moderegl);
	}
	public String getCodeJourFermeture(String jourferm)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_JOURFERMETURE, jourferm);
	}
	public String getCodeCircuit(String codecircuit)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_CODECIRCUIT, codecircuit);
	}
	public String getCodeAgent(String codeagent)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_AGENT, codeagent);
	}
	public String getCodeGroupementclient(String groupeclient)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_GROUPEMENTCLIENT, groupeclient);
	}
	public String getCodeTypecli(String typecli)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_TYPECLI, typecli);
	}

	public String getCodeSAV(String sav)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_SAV, sav);
	}
	public String getCodePeriodicite(String sav)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_PERIODICITE, sav);
	}
	public String getCodeTournee(String sav)
	{
		return Global.dbParam.getLblAllSocReverse(Global.dbParam.PARAM_TOURNEE, sav);
	}
	
	public int CountProspect()
	{

		try
		{
			String query="select count(*) from "+TABLENAME+
					" where "+FIELD_CREAT+"='"+CLI_CREATION+"'";
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i=cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}

	public String getCreatValue(String codecli)
	{
		structClient cli=new structClient();
		getClient(codecli, cli, new StringBuilder());
		return Fonctions.GetStringDanem(cli.CREAT);
	}
	public String getTypeClientValue(String codecli)
	{
		structClient cli=new structClient();
		getClient(codecli, cli, new StringBuilder());
		return Fonctions.GetStringDanem(cli.TYPE);
	}
	public String getStatutProValue(String codecli)
	{
		structClient cli=new structClient();
		getClient(codecli, cli, new StringBuilder());
		return Fonctions.GetStringDanem(cli.STATUT_PRO);
	}

	//dev60, fonction pour test les clients mauvais payeur, et pas seullement en testant statut pro
	public boolean isMauvaisPayeur(String codecli)
	{
		boolean bMauvaisPayeur = false ;
		structClient cli=new structClient();
		getClient(codecli, cli, new StringBuilder());
		if (  Fonctions.GetStringDanem(cli.STATUT_PRO).equals("2" ) )
			bMauvaisPayeur = true ;
		String[] tab = cli.STATUT.split(";");
		int niveau = 0;
		if(tab.length > 0){
			try{
				niveau = Integer.parseInt(tab[tab.length-1]);
			}catch(NumberFormatException ex){
				niveau = 0;
			}
		}

		if(cli.STATUT.contains("RJ") || cli.MODEREGLEMENT.equals("ES0") || niveau >= 4)
		{
			bMauvaisPayeur = true;
		}

		return bMauvaisPayeur ;

	}

	public boolean CountCliParJourPassage(Bundle b, String curZone)
	{

		try
		{
			String query="select count(*) as cpt,"+FIELD_JOURPASSAGE+" as jpass from "+TABLENAME+
					" where "+FIELD_ZONE+"='"+curZone+"' "+
					" group by "+FIELD_JOURPASSAGE;
			Cursor cursor = db.conn.rawQuery(query, null);
			if(cursor != null && cursor.moveToFirst()){
				while(cursor.isAfterLast() == false)
				{
					String s1 = giveFld(cursor, "cpt") ;
					String s2 = giveFld(cursor, "jpass") ;
					b.putString(giveFld(cursor, "jpass") , giveFld(cursor, "cpt")) ;
					cursor.moveToNext();
				}
				cursor.close();
			}
			if (cursor!=null)
				cursor.close();
			return true;		
		}
		catch(Exception ex)
		{
			return false;
		}

	}

	/*
	 * on deflag les prospects en creation apres la transmission afi de ne pas envoyer n fois au serveur l'ordre de cr�ation
	 */
	public void deFlagProspectsModif()
	{
		String update="UPDATE "+TABLENAME+ 
				" set "+FIELD_CREAT+"='N' where "+
				FIELD_CREAT+"='"+ Global.dbClient.CLI_MODIFICATION+ "'" ;

		db.conn.execSQL(update);

	}
	public void deFlagProspectsNouveu()
	{
		String update="UPDATE "+TABLENAME+ 
				" set "+FIELD_CREAT+"='N' where "+
				FIELD_CREAT+"='"+ Global.dbClient.CLI_CREATION+ "'" ;

		db.conn.execSQL(update);

	}

	

	public ArrayList<Bundle> getClientsFilters(boolean bvendeur, String filter,String order, String... params)
	{
		try
		{
			String LIMIT ="";
			if(bvendeur==false)
		    	LIMIT = " LIMIT 100 ";
			
			filter=MyDB.controlFld(filter);
			String query="";
			query="select distinct case when cli.etat='Y' then '' else 'rouge' end isbloque,cli.*,histo."+dbKD729HistoDocuments.FIELD_CODECLIENT+" ishisto, facdues."+dbKD730FacturesDues.FIELD_CODECLIENT +" isfacdues, matos."+TableMaterielClient.FIELD_CODECLIENT+ " ismatos "+
					" FROM "+
					TABLENAME+
					" cli left join "+dbKD729HistoDocuments.TABLENAME_HISTO+
					" histo on "+FIELD_CODE+"=histo."+dbKD729HistoDocuments.FIELD_CODECLIENT+
					" and histo."+dbKD729HistoDocuments.fld_kd_dat_type+"="+dbKD729HistoDocuments.KD_TYPE+
					"     left join "+dbKD730FacturesDues.TABLENAME_HISTO+
					" facdues on "+FIELD_CODE+"=facdues."+dbKD730FacturesDues.FIELD_CODECLIENT+
					" and facdues."+dbKD730FacturesDues.fld_kd_dat_type+"="+dbKD730FacturesDues.KD_TYPE+
					" left join "+TableMaterielClient.TABLENAME+ " matos on "+
					" matos."+TableMaterielClient.FIELD_CODECLIENT+"=cli."+TableClient.FIELD_CODE+
					" where ("+
					FIELD_CODE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ENSEIGNE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_VILLE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_CP+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ICON+ " like '%"+filter+"%' )";


			//gestion des filtres types de clients
			if(params != null && params.length > 0){

				if(params.length == 1){
					query += " and "+FIELD_TYPE+"='"+params[0]+"'";
				}else{
					query += " and ("; 
					for(int i = 0;i<params.length;i++){
						if(i == 0) {
							query += FIELD_TYPE+"='"+params[i]+"'";
						}else{
							query += " or "+FIELD_TYPE+"='"+params[i]+"'"; 
						}
					}
					query += ")";
				}
			}

			query += " order by "+order+LIMIT;



			ArrayList<Bundle>  clients=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				cli.putString(Global.dbClient.FIELD_VILLE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				cli.putString(Global.dbClient.FIELD_CP, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CP)));				
				cli.putString(Global.dbClient.FIELD_NOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NOM)));
				cli.putString(Global.dbClient.FIELD_ENSEIGNE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ENSEIGNE)));
				//cli.putString(Global.dbClient.FIELD_STATUT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT)));
				cli.putString(Global.dbClient.FIELD_ICON, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ICON)));
				cli.putString(Global.dbClient.FIELD_COULEUR, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COULEUR)));
				
				cli.putString(Global.dbClient.FIELD_CLI_SAV, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_SAV)));
				cli.putString(Global.dbClient.FIELD_CA_MOY_FAC, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CA_MOY_FAC)));
			
				cli.putString(Global.dbClient.FIELD_PERIODICITE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PERIODICITE)));
				cli.putString(Global.dbClient.FIELD_TOURNEE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE)));
				cli.putString(Global.dbClient.FIELD_TVA, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TVA)));
				cli.putString(Global.dbClient.FIELD_CODECOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODECOM)));
				cli.putString(Global.dbClient.FIELD_CLI_ANNUEL, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ANNUEL)));
				cli.putString(Global.dbClient.FIELD_CLI_AGENT2, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_AGENT2)));
				cli.putString(Global.dbClient.FIELD_EXCLURE_MAT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EXCLURE_MAT)));
				cli.putString(Global.dbClient.FIELD_SAISON_ETE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_ETE)));
				cli.putString(Global.dbClient.FIELD_SAISON_HIVER, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_HIVER)));
				cli.putString(Global.dbClient.FIELD_ACTIVITE_P, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ACTIVITE_P)));
				cli.putString(Global.dbClient.FIELD_FAMCLIENT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FAMCLIENT)));
				cli.putString(Global.dbClient.FIELD_SFAMCLIENT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SFAMCLIENT)));
			
				cli.putString(Global.dbClient.FIELD_STANDBY, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STANDBY)));
				cli.putString(Global.dbClient.FIELD_DATECLI, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATECLI)));
				
				cli.putString(Global.dbClient.FIELD_CLASSE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLASSE)));
				cli.putString(Global.dbClient.FIELD_ETSPUBLIC, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ETSPUBLIC)));

				cli.putString(Global.dbClient.FIELD_CLI_PASMAIL, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_PASMAIL)));

				cli.putString(Global.dbClient.FIELD_CAMT_0001 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0001)));
				cli.putString(Global.dbClient.FIELD_CAMT_0002 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0002)));
				cli.putString(Global.dbClient.FIELD_CAMT_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0003)));
				cli.putString(Global.dbClient.FIELD_CAMT_0004 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0004)));
				cli.putString(Global.dbClient.FIELD_PARTCARANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PARTCARANG)));
				cli.putString(Global.dbClient.FIELD_CARANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CARANG)));
				cli.putString(Global.dbClient.FIELD_VP_AGRANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_AGRANG)));
				cli.putString(Global.dbClient.FIELD_VP_VRPRANG ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_VRPRANG)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0001 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0001)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0002 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0002)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0003 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0003)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0004 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0004)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0001 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0001)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0002 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0002)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0003)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0004 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0004)));
				cli.putString(Global.dbClient.FIELD_RENT_0001 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0001)));
				cli.putString(Global.dbClient.FIELD_RENT_0002 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0002)));
				cli.putString(Global.dbClient.FIELD_RENT_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0003)));
				cli.putString(Global.dbClient.FIELD_RENT_0004 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0004)));
				cli.putString(Global.dbClient.FIELD_MOD_LIV , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MOD_LIV)));

				cli.putString(Global.dbClient.FIELD_VOL_CAFE_ANNUEL , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VOL_CAFE_ANNUEL)));
				String[] tab = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT)).split(";");
				String statut = "";
				if(tab.length > 0){
					statut = tab[0];
				}
				else
					statut="";
				
				cli.putString(Global.dbClient.FIELD_STATUT, statut);

				String histo=cur.getString(cur.getColumnIndex("ishisto"));
				if (histo!=null && histo.equals("")==false)     
					cli.putString("ishisto", "basic1_104_database_download");
				else
					cli.putString("ishisto", "");

				String facdues=cur.getString(cur.getColumnIndex("isfacdues"));
				if (facdues!=null && facdues.equals("")==false)     
					cli.putString("isfacdues", "basic2_018_money_coins");
				else
					cli.putString("isfacdues", "");

				String matos=cur.getString(cur.getColumnIndex("ismatos"));
				if (matos!=null && matos.equals("")==false)     
					cli.putString("ismatos", "basic2_295_dashboard_materiel");
				else
					cli.putString("ismatos", "");

				String isgeocoded=cur.getString(cur.getColumnIndex(FIELD_LAT));
				if (isgeocoded!=null && isgeocoded.equals("")==false)     
					cli.putString("isgeocoded", "basic_2057_map");
				else
					cli.putString("isgeocoded", "");

				String isbloque=cur.getString(cur.getColumnIndex("isbloque"));
				if (isbloque!=null && isbloque.equals("")==false)     
					cli.putString("isbloque", "rouge");
				else
					cli.putString("isbloque", "");

				

				clients.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return clients;
		}
		catch(Exception ex)
		{

		}

		return null;
	}
	
	
	
	public ArrayList<Bundle> getClientsFilters2( String filter,String order, String... params)
	{
		try
		{
			String LIMIT ="";
			LIMIT = " LIMIT 100 ";
			
			filter=MyDB.controlFld(filter);
			String query="";
			query="select distinct case when cli.etat='Y' then '' else 'rouge' end isbloque,cli.*"+
					" FROM "+
					TABLENAME+
					" cli  where ("+
					FIELD_CODE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ENSEIGNE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_VILLE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_CP+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ICON+ " like '%"+filter+"%' )";


			//gestion des filtres types de clients
			if(params != null && params.length > 0){

				if(params.length == 1){
					query += " and "+FIELD_TYPE+"='"+params[0]+"'";
				}else{
					query += " and ("; 
					for(int i = 0;i<params.length;i++){
						if(i == 0) {
							query += FIELD_TYPE+"='"+params[i]+"'";
						}else{
							query += " or "+FIELD_TYPE+"='"+params[i]+"'"; 
						}
					}
					query += ")";
				}
			}

			query += " order by "+order+LIMIT;



			ArrayList<Bundle>  clients=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				cli.putString(Global.dbClient.FIELD_VILLE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				cli.putString(Global.dbClient.FIELD_CP, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CP)));				
				cli.putString(Global.dbClient.FIELD_NOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NOM)));
				cli.putString(Global.dbClient.FIELD_ENSEIGNE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ENSEIGNE)));
				cli.putString(Global.dbClient.FIELD_ICON, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ICON)));
				cli.putString(Global.dbClient.FIELD_COULEUR, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COULEUR)));
				cli.putString(Global.dbClient.FIELD_CLI_SAV, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_SAV)));
				cli.putString(Global.dbClient.FIELD_CA_MOY_FAC, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CA_MOY_FAC)));
				cli.putString(Global.dbClient.FIELD_PERIODICITE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PERIODICITE)));
				cli.putString(Global.dbClient.FIELD_TOURNEE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE)));
				cli.putString(Global.dbClient.FIELD_TVA, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TVA)));
			
				cli.putString(Global.dbClient.FIELD_CODECOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODECOM)));
				cli.putString(Global.dbClient.FIELD_CLI_AGENT2, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_AGENT2)));
				cli.putString(Global.dbClient.FIELD_EXCLURE_MAT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EXCLURE_MAT)));
				cli.putString(Global.dbClient.FIELD_SAISON_ETE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_ETE)));
				cli.putString(Global.dbClient.FIELD_SAISON_HIVER, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_HIVER)));
				cli.putString(Global.dbClient.FIELD_ACTIVITE_P, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ACTIVITE_P)));
				cli.putString(Global.dbClient.FIELD_FAMCLIENT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FAMCLIENT)));
				cli.putString(Global.dbClient.FIELD_SFAMCLIENT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SFAMCLIENT)));
				cli.putString(Global.dbClient.FIELD_STANDBY, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STANDBY)));
				cli.putString(Global.dbClient.FIELD_DATECLI, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATECLI)));
				cli.putString(Global.dbClient.FIELD_CLASSE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLASSE)));
				cli.putString(Global.dbClient.FIELD_ETSPUBLIC ,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ETSPUBLIC)));
				cli.putString(Global.dbClient.FIELD_CLI_PASMAIL ,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_PASMAIL)));

				cli.putString(Global.dbClient.FIELD_CAMT_0001 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0001)));
				cli.putString(Global.dbClient.FIELD_CAMT_0002 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0002)));
				cli.putString(Global.dbClient.FIELD_CAMT_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0003)));
				cli.putString(Global.dbClient.FIELD_CAMT_0004 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0004)));
				cli.putString(Global.dbClient.FIELD_PARTCARANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PARTCARANG)));
				cli.putString(Global.dbClient.FIELD_CARANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CARANG)));
				cli.putString(Global.dbClient.FIELD_VP_AGRANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_AGRANG)));
				cli.putString(Global.dbClient.FIELD_VP_VRPRANG ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_VRPRANG)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0001 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0001)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0002 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0002)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0003 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0003)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0004 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0004)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0001 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0001)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0002 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0002)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0003)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0004 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0004)));
				cli.putString(Global.dbClient.FIELD_RENT_0001 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0001)));
				cli.putString(Global.dbClient.FIELD_RENT_0002 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0002)));
				cli.putString(Global.dbClient.FIELD_RENT_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0003)));
				cli.putString(Global.dbClient.FIELD_RENT_0004 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0004)));
				cli.putString(Global.dbClient.FIELD_MOD_LIV , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MOD_LIV)));


				String[] tab = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT)).split(";");
				String statut = "";
				if(tab.length > 0){
					statut = tab[0];
				}
				else
					statut="";
				
				cli.putString(Global.dbClient.FIELD_STATUT, statut);
				
				//Historique 
				int icounthisto=Global.dbKDHistoDocuments.Count(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				
				if(icounthisto==0)
				{
					cli.putString("ishisto", "");
				}
				else
					cli.putString("ishisto", "basic1_104_database_download");
				
				//Factures dues
				int icountfacturedu=Global.dbKDFacturesDues.Count(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				
				if(icountfacturedu==0)
				{
					cli.putString("isfacdues", "");
				}
				else
					cli.putString("isfacdues", "basic2_018_money_coins");
			

				//Matos
				
				int icountmatos=Global.dbMaterielClient.Count(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				
				if(icountmatos==0)
				{
					cli.putString("ismatos", "");
				}
				else
					cli.putString("ismatos", "basic2_295_dashboard_materiel");
				
				

				String isgeocoded=cur.getString(cur.getColumnIndex(FIELD_LAT));
				if (isgeocoded!=null && isgeocoded.equals("")==false)     
					cli.putString("isgeocoded", "basic_2057_map");
				else
					cli.putString("isgeocoded", "");

				String isbloque=cur.getString(cur.getColumnIndex("isbloque"));
				if (isbloque!=null && isbloque.equals("")==false)     
					cli.putString("isbloque", "rouge");
				else
					cli.putString("isbloque", "");

				
				clients.add(cli);
			}
			if (cur!=null)
				cur.close();
			return clients;
		}
		catch(Exception ex)
		{

		}

		return null;
	}

	public ArrayList<Bundle> getClientsFilters3(boolean bvendeur, String filter,String order, String... params)
	{
		try
		{
			String LIMIT ="";
			if(bvendeur==false)
		    	LIMIT = " LIMIT 800 ";
			
			filter=MyDB.controlFld(filter);
			
			String query="";
			//query="select distinct case when cli.etat='Y' then '' else 'rouge' end isbloque,cli.* "+
			query="select distinct case when cli.etat='Y' then '' else 'rouge' end isbloque,cli.code,cli.ville,cli.cp ,cli.nom,cli.enseigne,cli.icon,cli.couleur,cli.cli_sav,cli.CA_MOY_FAC"
					+ ",cli.PERIODICITE,cli.TOURNEE,cli.tva,cli.CODECOM,cli.CLI_ANNUEL, cli.CLI_ISHISTO,cli.CLI_ISFACDUES,cli.CLI_ISMATOS,cli.CLI_AGENT2,cli.EXCLURE_MAT,cli.SAISON_ETE,cli.SAISON_HIVER,cli.ACTIVITE_P,cli.FAMCLIENT,cli.SFAMCLIENT,cli.STANDBY,cli.DATECLI,cli.CLASSE"
					+ ",cli.STATUT,cli.LAT,cli.ETS_PUBLIC,cli.CLI_PASMAIL  "
					+ ",cli.CAMT_0001,cli.CAMT_0002,cli.CAMT_0003,cli.CAMT_0004  "
					+ ",cli.PARTCARANG,cli.CARANG,cli.VP_AGRANG,cli.VP_VRPRANG  "
					+ ",cli.VP_CAMT_0001,cli.VP_CAMT_0002,cli.VP_CAMT_0003,cli.VP_CAMT_0004  "
					+ ",cli.VP_MARGE_0001,cli.VP_MARGE_0002,cli.VP_MARGE_0003,cli.VP_MARGE_0004  "
					+ ",cli.RENT_0001,cli.RENT_0002,cli.RENT_0003,cli.RENT_0004  "
					+ ",cli.MOD_LIV,cli.VOL_CAFE_ANNUEL ,cli.STD_BY ,cli.TYPE "+

			
			" FROM "+
					TABLENAME+
					" cli  where ("+
					FIELD_CODE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ENSEIGNE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_VILLE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_CP+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ICON+ " like '%"+filter+"%' )";

			
			//gestion des filtres types de clients
			if(params != null && params.length > 0){

				if(params.length == 1){
					query += " and "+FIELD_TYPE+"='"+params[0]+"'";
				}else{
					query += " and ("; 
					for(int i = 0;i<params.length;i++){
						if(i == 0) {
							query += FIELD_TYPE+"='"+params[i]+"'";
						}else{
							query += " or "+FIELD_TYPE+"='"+params[i]+"'";
						}
					}
					query += ")";
				}
			}

			query+= " GROUP BY cli.etat,cli.code,cli.ville,cli.cp ,cli.nom,cli.enseigne,cli.icon,cli.couleur,cli.cli_sav,cli.CA_MOY_FAC"
			+ ",cli.PERIODICITE,cli.TOURNEE,cli.tva,cli.CODECOM,cli.CLI_ANNUEL, cli.CLI_ISHISTO,cli.CLI_ISFACDUES,cli.CLI_ISMATOS,cli.CLI_AGENT2,cli.EXCLURE_MAT,cli.SAISON_ETE,cli.SAISON_HIVER,cli.ACTIVITE_P,cli.FAMCLIENT,cli.SFAMCLIENT,cli.STANDBY,cli.DATECLI,CLI.CLASSE"
			+ ",cli.STATUT,cli.LAT ,cli.ETS_PUBLIC,cli.CLI_PASMAIL"
					+ ",cli.CAMT_0001,cli.CAMT_0002,cli.CAMT_0003,cli.CAMT_0004  "
					+ ",cli.PARTCARANG,cli.CARANG,cli.VP_AGRANG,cli.VP_VRPRANG  "
					+ ",cli.VP_CAMT_0001,cli.VP_CAMT_0002,cli.VP_CAMT_0003,cli.VP_CAMT_0004  "
					+ ",cli.VP_MARGE_0001,cli.VP_MARGE_0002,cli.VP_MARGE_0003,cli.VP_MARGE_0004  "
					+ ",cli.RENT_0001,cli.RENT_0002,cli.RENT_0003,cli.RENT_0004  "
					+ ",cli.MOD_LIV,cli.VOL_CAFE_ANNUEL ,cli.STD_BY,cli.TYPE ";
			query += " order by "+order+LIMIT;


			Log.e("query","");


			ArrayList<Bundle>  clients=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				
						if(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)).equals("C0959925"))
						{
					String seee="";
						}
						
				Bundle cli=new Bundle();
				cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				cli.putString(Global.dbClient.FIELD_VILLE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				cli.putString(Global.dbClient.FIELD_CP, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CP)));				
				//cli.putString(Global.dbClient.FIELD_NOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NOM)));
				cli.putString(Global.dbClient.FIELD_ENSEIGNE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ENSEIGNE)));
				//cli.putString(Global.dbClient.FIELD_STATUT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT)));
				cli.putString(Global.dbClient.FIELD_ICON, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ICON)));
				cli.putString(Global.dbClient.FIELD_COULEUR, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COULEUR)));
				
				cli.putString(Global.dbClient.FIELD_CLI_SAV, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_SAV)));
				cli.putString(Global.dbClient.FIELD_CA_MOY_FAC, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CA_MOY_FAC)));
			
				cli.putString(Global.dbClient.FIELD_PERIODICITE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PERIODICITE)));
				cli.putString(Global.dbClient.FIELD_TOURNEE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE)));
				cli.putString(Global.dbClient.FIELD_TVA, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TVA)));
				cli.putString(Global.dbClient.FIELD_CODECOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODECOM)));
				cli.putString(Global.dbClient.FIELD_CLI_ANNUEL, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ANNUEL)));
				
				cli.putString(Global.dbClient.FIELD_CLI_ISHISTO, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISHISTO)));
				cli.putString(Global.dbClient.FIELD_CLI_ISFACDUES, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISFACDUES)));
				cli.putString(Global.dbClient.FIELD_CLI_ISMATOS, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISMATOS)));
				cli.putString(Global.dbClient.FIELD_CLI_AGENT2, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_AGENT2)));
				cli.putString(Global.dbClient.FIELD_EXCLURE_MAT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EXCLURE_MAT)));
				cli.putString(Global.dbClient.FIELD_SAISON_ETE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_ETE)));
				cli.putString(Global.dbClient.FIELD_SAISON_HIVER, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_HIVER)));
				cli.putString(Global.dbClient.FIELD_ACTIVITE_P, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ACTIVITE_P)));
				cli.putString(Global.dbClient.FIELD_FAMCLIENT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FAMCLIENT)));
				cli.putString(Global.dbClient.FIELD_SFAMCLIENT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SFAMCLIENT)));
				cli.putString(Global.dbClient.FIELD_STANDBY, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STANDBY)));
				cli.putString(Global.dbClient.FIELD_DATECLI, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATECLI)));
				cli.putString(Global.dbClient.FIELD_CLASSE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLASSE)));
				cli.putString(Global.dbClient.FIELD_ETSPUBLIC, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ETSPUBLIC)));
				cli.putString(Global.dbClient.FIELD_CLI_PASMAIL ,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_PASMAIL)));
				cli.putString(Global.dbClient.FIELD_CAMT_0001 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0001)));
				cli.putString(Global.dbClient.FIELD_CAMT_0002 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0002)));
				cli.putString(Global.dbClient.FIELD_CAMT_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0003)));
				cli.putString(Global.dbClient.FIELD_CAMT_0004 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0004)));
				cli.putString(Global.dbClient.FIELD_PARTCARANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PARTCARANG)));
				cli.putString(Global.dbClient.FIELD_CARANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CARANG)));
				cli.putString(Global.dbClient.FIELD_VP_AGRANG , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_AGRANG)));
				cli.putString(Global.dbClient.FIELD_VP_VRPRANG ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_VRPRANG)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0001 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0001)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0002 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0002)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0003 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0003)));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0004 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0004)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0001 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0001)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0002 ,  cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0002)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0003)));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0004 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0004)));
				cli.putString(Global.dbClient.FIELD_RENT_0001 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0001)));
				cli.putString(Global.dbClient.FIELD_RENT_0002 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0002)));
				cli.putString(Global.dbClient.FIELD_RENT_0003 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0003)));
				cli.putString(Global.dbClient.FIELD_RENT_0004 , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0004)));
				cli.putString(Global.dbClient.FIELD_MOD_LIV , cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MOD_LIV)));

				String[] tab = cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT)).split(";");
				String statut = "";
				if(tab.length > 0){
					statut = tab[0];
				}
				else
					statut="";

				cli.putString(Global.dbClient.FIELD_STATUT, "");
				if(Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TYPE))).equals("P"))
				{
					cli.putString(Global.dbClient.FIELD_NOM, statut);
					cli.putString(Global.dbClient.FIELD_STATUT, "");

				}
				else
				{
					cli.putString(Global.dbClient.FIELD_NOM, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NOM)));
					cli.putString(Global.dbClient.FIELD_STATUT, statut);
				}


				String histo=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISHISTO)));
				if (histo.equals("1"))     
					cli.putString("ishisto", "basic1_104_database_download");
				else
					cli.putString("ishisto", "");

				String facdues=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISFACDUES)));
				if (facdues.equals("1"))     
					cli.putString("isfacdues", "basic2_018_money_coins");
				else
					cli.putString("isfacdues", "");

				String matos=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISMATOS)));
				if (matos.equals("1"))     
					cli.putString("ismatos", "basic2_295_dashboard_materiel");
				else
					cli.putString("ismatos", "");

				String isgeocoded=cur.getString(cur.getColumnIndex(FIELD_LAT));
				if (isgeocoded!=null && isgeocoded.equals("")==false)     
					cli.putString("isgeocoded", "basic_2057_map");
				else
					cli.putString("isgeocoded", "");

				String isbloque=cur.getString(cur.getColumnIndex("isbloque"));
				if (isbloque!=null && isbloque.equals("")==false)
				{
					cli.putString("isbloque", "vert");
					String stb_by=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STD_BY)));
					if(stb_by.equals("1"))
						cli.putString("isbloque", "rouge");

				}
				else
					cli.putString("isbloque", "");




				

				clients.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return clients;
		}
		catch(Exception ex)
		{
			String dd=ex.getLocalizedMessage();

		}

		return null;
	}

	
	
	public String getModereglement(String code)
	{	
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_MODEREGLEMENT          );

			}
			if (cur != null )
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}
	
	public String getClientJourFer(String code)
	{	
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_JOURFERMETURE         );
			}
			if (cur != null )
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}

	public String getClientCodeTournee(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
		{
			libelle=giveFld(cur,FIELD_TOURNEE         );
		}
			if (cur != null )
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}
	public String getClientCA_MOY_FAC(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_CA_MOY_FAC         );
			}
			if (cur != null )
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}

	public String getClient_Ville(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_VILLE        );
			}
			if (cur != null)
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}
	public String getClient_Nom(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_NOM        );
			}
			if (cur != null )
				cur.close();
		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}
	public String getClient_Adresse(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_ADR1        )+" "+giveFld(cur,FIELD_ADR2        )+" "+giveFld(cur,FIELD_CP        )+" "+giveFld(cur,FIELD_VILLE        );
			}
			if (cur != null )
				cur.close();
		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}
	public String getClient_Tournee(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_TOURNEE        );
			}
			if (cur != null )
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}
	public String getClient_Periodicite(String code)
	{
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					TableClient.FIELD_CODE+
					"='"+code+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_PERIODICITE        );
			}
			if (cur != null )
				cur.close();

		}
		catch(Exception ex)
		{
			libelle="";
		}

		return libelle;
	}

	public boolean getRecordVilleRemove(String codeTable,String typeagent,String stremove,ArrayList<Bundle> liste,boolean addBlanc)
    {
        try
        {
            /*
             * Vidage des donn�es du spinner
             */

            liste.clear();

            String query="SELECT "+this.FIELD_VILLE+ " FROM "+
                    TABLENAME+
                    " GROUP BY "+this.FIELD_VILLE+
                    " ORDER BY "+
                    this.FIELD_VILLE;

            String prm_coderec	="";
            String prm_lbl		="";
            String prm_comment	="";
            String prm_value	="";



            Cursor cur=db.conn.rawQuery(query, null);
            while (cur.moveToNext())
            {
				//cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				prm_coderec	=giveFld(cur,this.FIELD_VILLE);
				prm_lbl	=giveFld(cur,this.FIELD_VILLE);
			//	prm_coderec	=giveFld(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
              //  prm_lbl		=giveFld(cur,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
                prm_comment	="";
                prm_value	="";
                if(!Fonctions.GetStringDanem(stremove).equals("") && Fonctions.GetStringDanem(stremove).equals(prm_coderec))
                {
					/*Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);*/
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("prm_coderec", prm_coderec);
                    bundle.putString("prm_lbl", prm_lbl);
                    bundle.putString("prm_comment", prm_comment);
                    bundle.putString("prm_value", prm_value);
                    liste.add(bundle);
                }




            }

            cur.close();
        }
        catch(Exception ex)
        {
            return false;
        }


        return true;
    }

	public boolean getRecordTourneeRemove(String codeTable,String typeagent,String stremove,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */

			liste.clear();

			String query="SELECT "+this.FIELD_TOURNEE+ " FROM "+
					TABLENAME+
					" GROUP BY "+this.FIELD_TOURNEE+
					" ORDER BY "+
					this.FIELD_TOURNEE;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";



			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				//cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				prm_coderec	=giveFld(cur,this.FIELD_TOURNEE);
				prm_lbl	=giveFld(cur,this.FIELD_TOURNEE);
				//	prm_coderec	=giveFld(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				//  prm_lbl		=giveFld(cur,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				prm_comment	="";
				prm_value	="";
				if(!Fonctions.GetStringDanem(stremove).equals("") && Fonctions.GetStringDanem(stremove).equals(prm_coderec))
				{
					/*Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);*/
				}
				else
				{
					Bundle bundle = new Bundle();
					bundle.putString("prm_coderec", prm_coderec);
					bundle.putString("prm_lbl", prm_lbl);
					bundle.putString("prm_comment", prm_comment);
					bundle.putString("prm_value", prm_value);
					liste.add(bundle);
				}




			}

			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}

	public boolean getRecordVendeurRemove(String codeTable,String typeagent,String stremove,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */

			liste.clear();

			String query="SELECT "+this.FIELD_CODECOM+ " FROM "+
					TABLENAME+
					" GROUP BY "+this.FIELD_CODECOM+
					" ORDER BY "+
					this.FIELD_CODECOM;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";



			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				//cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				prm_coderec	=giveFld(cur,this.FIELD_CODECOM);
				prm_lbl	=giveFld(cur,this.FIELD_CODECOM);
				//	prm_coderec	=giveFld(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				//  prm_lbl		=giveFld(cur,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				prm_comment	="";
				prm_value	="";
				if(!Fonctions.GetStringDanem(stremove).equals("") && Fonctions.GetStringDanem(stremove).equals(prm_coderec))
				{
					/*Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);*/
				}
				else
				{
					Bundle bundle = new Bundle();
					bundle.putString("prm_coderec", prm_coderec);
					bundle.putString("prm_lbl", prm_lbl);
					bundle.putString("prm_comment", prm_comment);
					bundle.putString("prm_value", prm_value);
					liste.add(bundle);
				}

			}

			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}

		return true;
	}

	//Pour changement d'affichage des rdv
	public ArrayList<Bundle> getClientsFiltersForRdv( Context c, String codecli, String filter,String activity,String activityVille,String activityVendeur,String activityTournee)
	{
		try
		{
			//Avant affichage, synchro des rdv
			dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
			db106.Sync106(c) ;

			filter=MyDB.controlFld(filter);

			String query="";

			query="select distinct case when cli.etat='Y' then '' else 'rouge' end isbloque,cli.code,cli.ville,cli.cp ,cli.nom,cli.enseigne,cli.icon,cli.couleur,cli.cli_sav,cli.CA_MOY_FAC"
					+ ",cli.PERIODICITE,cli.TOURNEE,cli.tva,cli.CODECOM,cli.CLI_ANNUEL, cli.CLI_ISHISTO,cli.CLI_ISFACDUES,cli.CLI_ISMATOS,cli.CLI_AGENT2,cli.EXCLURE_MAT,cli.SAISON_ETE,cli.SAISON_HIVER,cli.ACTIVITE_P,cli.FAMCLIENT,cli.SFAMCLIENT,cli.STANDBY,cli.DATECLI,cli.CLASSE"
					+ ",cli.STATUT,cli.LAT,cli.ETS_PUBLIC,cli.CLI_PASMAIL  "
					+ ",cli.CAMT_0001,cli.CAMT_0002,cli.CAMT_0003,cli.CAMT_0004  "
					+ ",cli.PARTCARANG,cli.CARANG,cli.VP_AGRANG,cli.VP_VRPRANG  "
					+ ",cli.VP_CAMT_0001,cli.VP_CAMT_0002,cli.VP_CAMT_0003,cli.VP_CAMT_0004  "
					+ ",cli.VP_MARGE_0001,cli.VP_MARGE_0002,cli.VP_MARGE_0003,cli.VP_MARGE_0004  "
					+ ",cli.RENT_0001,cli.RENT_0002,cli.RENT_0003,cli.RENT_0004  "
					+ ",cli.MOD_LIV,cli.VOL_CAFE_ANNUEL  "

					//Ajout rdv
					+ ",rdv.dat_data02,rdv.dat_data03,rdv.dat_idx02,param.prm_lbl"+
					//+ ",rdv.dat_data02 ,rdv.dat_data03,rdv.dat_idx02"+

					" FROM "+
					TABLENAME+

					" cli "+
					//Ajout rdv
					" inner join kems_data as rdv on rdv.cli_code = cli.code and rdv.dat_type=106 "+
					" left join kems_param as param on rdv.dat_data10=param.prm_coderec and prm_table='TYPEACTIVITE' "+
					"where ("+
					FIELD_CODE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ENSEIGNE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_VILLE+ " like '%"+filter+"%' "+
					" or "+
					FIELD_CP+ " like '%"+filter+"%' "+
					" or "+
					FIELD_ICON+ " like '%"+filter+"%' )";

			if ( !codecli.equals(""))
				query += " AND "+FIELD_CODE+ " = '"+codecli+"' ";
			if ( !activity.equals(""))
				query += " AND param.prm_lbl = '"+ MyDB.controlFld(activity)+"' ";
			if ( !activityVille.equals(""))
				query += " AND  "+FIELD_VILLE+ "  = '"+MyDB.controlFld(activityVille)+"' ";
			if ( !activityVendeur.equals(""))
				query += " AND  "+FIELD_CODECOM+ "  = '"+MyDB.controlFld(activityVendeur)+"' ";
			if ( !activityTournee.equals(""))
				query += " AND  "+FIELD_TOURNEE+ "  = '"+MyDB.controlFld(activityTournee)+"' ";

			query+= " GROUP BY cli.etat,cli.code,cli.ville,cli.cp ,cli.nom,cli.enseigne,cli.icon,cli.couleur,cli.cli_sav,cli.CA_MOY_FAC"
					+ ",cli.PERIODICITE,cli.TOURNEE,cli.tva,cli.CODECOM,cli.CLI_ANNUEL, cli.CLI_ISHISTO,cli.CLI_ISFACDUES,cli.CLI_ISMATOS,cli.CLI_AGENT2,cli.EXCLURE_MAT,cli.SAISON_ETE,cli.SAISON_HIVER,cli.ACTIVITE_P,cli.FAMCLIENT,cli.SFAMCLIENT,cli.STANDBY,cli.DATECLI,CLI.CLASSE"
					+ ",cli.STATUT,cli.LAT ,cli.ETS_PUBLIC,cli.CLI_PASMAIL"
					+ ",cli.CAMT_0001,cli.CAMT_0002,cli.CAMT_0003,cli.CAMT_0004  "
					+ ",cli.PARTCARANG,cli.CARANG,cli.VP_AGRANG,cli.VP_VRPRANG  "
					+ ",cli.VP_CAMT_0001,cli.VP_CAMT_0002,cli.VP_CAMT_0003,cli.VP_CAMT_0004  "
					+ ",cli.VP_MARGE_0001,cli.VP_MARGE_0002,cli.VP_MARGE_0003,cli.VP_MARGE_0004  "
					+ ",cli.RENT_0001,cli.RENT_0002,cli.RENT_0003,cli.RENT_0004  "
					+ ",cli.MOD_LIV,cli.VOL_CAFE_ANNUEL ";
			//Ajout rdv
			query+= ",rdv.dat_data02,rdv.dat_data03,rdv.dat_idx02 " ;

			query += " order by rdv.dat_data03 ";

			ArrayList<Bundle>  clients=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				if(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)).equals("C0959925"))
				{
					String seee="";
				}

				Bundle cli=new Bundle();
				cli.putString(Global.dbClient.FIELD_CODE, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE))));
				cli.putString(Global.dbClient.FIELD_VILLE, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE))));
				cli.putString(Global.dbClient.FIELD_CP, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CP))));
				cli.putString(Global.dbClient.FIELD_NOM, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_NOM))));
				cli.putString(Global.dbClient.FIELD_ENSEIGNE, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ENSEIGNE))));
				//cli.putString(Global.dbClient.FIELD_STATUT, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT)));
				//cli.putString(Global.dbClient.FIELD_ICON, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ICON)));
				//modif 20/06/18: affichage du mois à la place de l'icone (voir png mois01 à mois12)
				String mois = cur.getString(cur.getColumnIndex(dbKD106AgendaCorrespondance.FIELD_DTDEBUT) );
				if ( mois!= null && mois.length() > 6)
					mois = "mois"+mois.substring(4,6) ;
				else
					mois = Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ICON))) ;		//ancien fonctionnement si on n'a pas de date
				cli.putString(Global.dbClient.FIELD_ICON, mois ) ;

				cli.putString(Global.dbClient.FIELD_COULEUR, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_COULEUR))));

				cli.putString(Global.dbClient.FIELD_CLI_SAV, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_SAV))));
				cli.putString(Global.dbClient.FIELD_CA_MOY_FAC, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CA_MOY_FAC))));

				cli.putString(Global.dbClient.FIELD_PERIODICITE, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PERIODICITE))));
				cli.putString(Global.dbClient.FIELD_TOURNEE, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE))));
				cli.putString(Global.dbClient.FIELD_TVA, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TVA))));
				cli.putString(Global.dbClient.FIELD_CODECOM, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODECOM))));
				cli.putString(Global.dbClient.FIELD_CLI_ANNUEL, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ANNUEL))));

				cli.putString(Global.dbClient.FIELD_CLI_ISHISTO, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISHISTO))));
				cli.putString(Global.dbClient.FIELD_CLI_ISFACDUES, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISFACDUES))));
				cli.putString(Global.dbClient.FIELD_CLI_ISMATOS, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISMATOS))));
				cli.putString(Global.dbClient.FIELD_CLI_AGENT2, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_AGENT2))));
				cli.putString(Global.dbClient.FIELD_EXCLURE_MAT, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_EXCLURE_MAT))));
				cli.putString(Global.dbClient.FIELD_SAISON_ETE, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_ETE))));
				cli.putString(Global.dbClient.FIELD_SAISON_HIVER, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SAISON_HIVER))));
				cli.putString(Global.dbClient.FIELD_ACTIVITE_P, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ACTIVITE_P))));
				cli.putString(Global.dbClient.FIELD_FAMCLIENT, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_FAMCLIENT))));
				cli.putString(Global.dbClient.FIELD_SFAMCLIENT, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_SFAMCLIENT))));
				cli.putString(Global.dbClient.FIELD_STANDBY, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STANDBY))));
				cli.putString(Global.dbClient.FIELD_DATECLI,Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_DATECLI))));
				cli.putString(Global.dbClient.FIELD_CLASSE,Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLASSE))));
				cli.putString(Global.dbClient.FIELD_ETSPUBLIC, Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_ETSPUBLIC))));
				cli.putString(Global.dbClient.FIELD_CLI_PASMAIL ,Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_PASMAIL))));
				cli.putString(Global.dbClient.FIELD_CAMT_0001 ,Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0001))));
				cli.putString(Global.dbClient.FIELD_CAMT_0002 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0002))));
				cli.putString(Global.dbClient.FIELD_CAMT_0003 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0003))));
				cli.putString(Global.dbClient.FIELD_CAMT_0004 ,  Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CAMT_0004))));
				cli.putString(Global.dbClient.FIELD_PARTCARANG , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PARTCARANG))));
				cli.putString(Global.dbClient.FIELD_CARANG , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CARANG))));
				cli.putString(Global.dbClient.FIELD_VP_AGRANG , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_AGRANG))));
				cli.putString(Global.dbClient.FIELD_VP_VRPRANG ,  Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_VRPRANG))));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0001 ,  Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0001))));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0002 ,  Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0002))));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0003 , Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0003))));
				cli.putString(Global.dbClient.FIELD_VP_CAMT_0004 ,  Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_CAMT_0004))));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0001 , Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0001))));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0002 , Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0002))));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0003 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0003))));
				cli.putString(Global.dbClient.FIELD_VP_MARGE_0004 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VP_MARGE_0004))));
				cli.putString(Global.dbClient.FIELD_RENT_0001 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0001))));
				cli.putString(Global.dbClient.FIELD_RENT_0002 ,Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0002))));
				cli.putString(Global.dbClient.FIELD_RENT_0003 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0003))));
				cli.putString(Global.dbClient.FIELD_RENT_0004 , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_RENT_0004))));
				cli.putString(Global.dbClient.FIELD_MOD_LIV ,Fonctions.GetStringDanem( cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_MOD_LIV))));
				cli.putString(dbParam.FLD_PARAM_LBL , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(dbParam.FLD_PARAM_LBL))));
				cli.putString(dbKD106AgendaCorrespondance.FIELD_DESCRIPTION , Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(dbKD106AgendaCorrespondance.FIELD_DESCRIPTION))));
				cli.putString(dbKD106AgendaCorrespondance.FIELD_DTDEBUT , cur.getString(cur.getColumnIndex(dbKD106AgendaCorrespondance.FIELD_DTDEBUT)));
				cli.putString(dbKD106AgendaCorrespondance.FIELD_ID , cur.getString(cur.getColumnIndex(dbKD106AgendaCorrespondance.FIELD_ID)));
				//Pour affichage rdv, TOURNEE/PERIODICITE avec formatage
				String tournee_period = Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE))) ;
				if ( tournee_period.equals(""))
					tournee_period = "-" ;
				tournee_period+= " / "+ Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_PERIODICITE)) ) ;
				cli.putString("TOURNEE_PERIOD" ,tournee_period);

				String[] tab = Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_STATUT))).split(";");
				String statut = "";
				if(tab.length > 0){
					statut = tab[0];
				}
				else
					statut="";

				cli.putString(Global.dbClient.FIELD_STATUT, statut);

				String histo=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISHISTO)));
				if (histo.equals("1"))
					cli.putString("ishisto", "basic1_104_database_download");
				else
					cli.putString("ishisto", "");

				String facdues=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISFACDUES)));
				if (facdues.equals("1"))
					cli.putString("isfacdues", "basic2_018_money_coins");
				else
					cli.putString("isfacdues", "");

				String matos=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CLI_ISMATOS)));
				if (matos.equals("1"))
					cli.putString("ismatos", "basic2_295_dashboard_materiel");
				else
					cli.putString("ismatos", "");

				String isgeocoded=cur.getString(cur.getColumnIndex(FIELD_LAT));
				if (isgeocoded!=null && isgeocoded.equals("")==false)
					cli.putString("isgeocoded", "basic_2057_map");
				else
					cli.putString("isgeocoded", "");

				String isbloque=cur.getString(cur.getColumnIndex("isbloque"));
				if (isbloque!=null && isbloque.equals("")==false)
					cli.putString("isbloque", "rouge");
				else
					cli.putString("isbloque", "");

				clients.add(cli);
			}
			if (cur!=null)
				cur.close();
			return clients;
		}
		catch(Exception ex)
		{
			String dd=ex.getLocalizedMessage();

		}

		return null;
	}

	//Pour changement d'affichage des rdv
	public boolean BExisteRdv( Context c)
	{
		boolean bres =false;
		try
		{
			//Avant affichage, synchro des rdv
			dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
			db106.Sync106(c) ;

			String query="";

			query="select distinct case when cli.etat='Y' then '' else 'rouge' end isbloque,cli.code,cli.ville,cli.cp ,cli.nom,cli.enseigne,cli.icon,cli.couleur,cli.cli_sav,cli.CA_MOY_FAC"
					+ ",cli.PERIODICITE,cli.TOURNEE,cli.tva,cli.CODECOM,cli.CLI_ANNUEL, cli.CLI_ISHISTO,cli.CLI_ISFACDUES,cli.CLI_ISMATOS,cli.CLI_AGENT2,cli.EXCLURE_MAT,cli.SAISON_ETE,cli.SAISON_HIVER,cli.ACTIVITE_P,cli.FAMCLIENT,cli.SFAMCLIENT,cli.STANDBY,cli.DATECLI,cli.CLASSE"
					+ ",cli.STATUT,cli.LAT,cli.ETS_PUBLIC,cli.CLI_PASMAIL  "
					+ ",cli.CAMT_0001,cli.CAMT_0002,cli.CAMT_0003,cli.CAMT_0004  "
					+ ",cli.PARTCARANG,cli.CARANG,cli.VP_AGRANG,cli.VP_VRPRANG  "
					+ ",cli.VP_CAMT_0001,cli.VP_CAMT_0002,cli.VP_CAMT_0003,cli.VP_CAMT_0004  "
					+ ",cli.VP_MARGE_0001,cli.VP_MARGE_0002,cli.VP_MARGE_0003,cli.VP_MARGE_0004  "
					+ ",cli.RENT_0001,cli.RENT_0002,cli.RENT_0003,cli.RENT_0004  "
					+ ",cli.MOD_LIV,cli.VOL_CAFE_ANNUEL "

					//Ajout rdv
					//+ ",rdv.dat_data02,rdv.dat_data03,rdv.dat_idx02,param.prm_lbl"+
					+ ",rdv.dat_data02 ,rdv.dat_data03,rdv.dat_idx02"+

					" FROM "+
					TABLENAME+

					" cli "+
					//Ajout rdv
					" inner join kems_data as rdv on rdv.cli_code = cli.code and rdv.dat_type=106 ";
					//" left join kems_param as param on rdv.dat_data02=param.prm_coderec and prm_table='TYPEACTIVITE' ";


			query+= " GROUP BY cli.etat,cli.code,cli.ville,cli.cp ,cli.nom,cli.enseigne,cli.icon,cli.couleur,cli.cli_sav,cli.CA_MOY_FAC"
					+ ",cli.PERIODICITE,cli.TOURNEE,cli.tva,cli.CODECOM,cli.CLI_ANNUEL, cli.CLI_ISHISTO,cli.CLI_ISFACDUES,cli.CLI_ISMATOS,cli.CLI_AGENT2,cli.EXCLURE_MAT,cli.SAISON_ETE,cli.SAISON_HIVER,cli.ACTIVITE_P,cli.FAMCLIENT,cli.SFAMCLIENT,cli.STANDBY,cli.DATECLI,CLI.CLASSE"
					+ ",cli.STATUT,cli.LAT ,cli.ETS_PUBLIC,cli.CLI_PASMAIL"
					+ ",cli.CAMT_0001,cli.CAMT_0002,cli.CAMT_0003,cli.CAMT_0004  "
					+ ",cli.PARTCARANG,cli.CARANG,cli.VP_AGRANG,cli.VP_VRPRANG  "
					+ ",cli.VP_CAMT_0001,cli.VP_CAMT_0002,cli.VP_CAMT_0003,cli.VP_CAMT_0004  "
					+ ",cli.VP_MARGE_0001,cli.VP_MARGE_0002,cli.VP_MARGE_0003,cli.VP_MARGE_0004  "
					+ ",cli.RENT_0001,cli.RENT_0002,cli.RENT_0003,cli.RENT_0004  "
					+ ",cli.MOD_LIV,cli.VOL_CAFE_ANNUEL ";
			//Ajout rdv
			query+= ",rdv.dat_data02,rdv.dat_data03,rdv.dat_idx02 " ;

			query += " order by rdv.dat_data03 ";



			ArrayList<Bundle>  clients=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				return true ;
			}
			if (cur!=null)
				cur.close();

		}
		catch(Exception ex)
		{
			String dd=ex.getLocalizedMessage();

		}

		return bres;
	}
	public void deleteProspectTest()
	{
		String update="DELETE FROM "+TABLENAME+
				" where "+
				FIELD_ACTIF+"='"+ Global.dbClient.CLI_CREATION+ "' or "+
				FIELD_CREAT+"='"+ Global.dbClient.CLI_MODIFICATION+ "'" ;

		db.conn.execSQL(update);

	}

	//Pour changement d'affichage des rdv
	public ArrayList<Bundle> getListTourneeForFilter( Context c)
	{
		try
		{
			//Avant affichage, synchro des rdv
			dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
			db106.Sync106(c) ;

			String query="";

			query="select distinct cli.TOURNEE "+

					" FROM "+
					TABLENAME+

					" cli "+
					//Ajout rdv
					" inner join kems_data as rdv on rdv.cli_code = cli.code and rdv.dat_type=106 ";
					//" left join kems_param as param on rdv.dat_data02=param.prm_coderec and prm_table='TYPEACTIVITE' "+

			query += " order by cli.TOURNEE ";

			Log.e("query","getListTourneeForFilter"+query);

			ArrayList<Bundle>  tournees=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				Bundle cli=new Bundle();

				cli.putString(Global.dbClient.FIELD_TOURNEE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_TOURNEE)));

				tournees.add(cli);
			}
			if (cur!=null)
				cur.close();
			return tournees;
		}
		catch(Exception ex)
		{
			String dd=ex.getLocalizedMessage();
		}

		return null;
	}

	//Pour changement d'affichage des rdv
	public ArrayList<Bundle> getListTypeForFilter( Context c)
	{
		try
		{
			//Avant affichage, synchro des rdv
			dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
			db106.Sync106(c) ;

			String query="";

			query="select distinct cli.TYPE ,"+Global.dbParam.FLD_PARAM_LBL+

					" FROM "+
					TABLENAME+

					" cli "+
					//Ajout rdv
					" inner join kems_data as rdv on rdv.cli_code = cli.code and rdv.dat_type=106 "+
			        " left join kems_param as param on rdv.dat_data10=param.prm_coderec and prm_table='TYPEACTIVITE' ";

			query += " order by "+Global.dbParam.FLD_PARAM_LBL;

			Log.e("query","getListTypeForFilter"+query);

			ArrayList<Bundle>  tournees=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				String lbl= cur.getString(cur.getColumnIndex(Global.dbParam.FLD_PARAM_LBL));
				if(lbl != null   )
					if(!lbl.equals("")  )
					{
						cli.putString(Global.dbClient.FIELD_TYPE,lbl);
						tournees.add(cli);
					}

			}
			if (cur!=null)
				cur.close();
			return tournees;
		}
		catch(Exception ex)
		{
			String dd=ex.getLocalizedMessage();
		}

		return null;
	}

	//Pour changement d'affichage des rdv
	public ArrayList<Bundle> getListVilleForFilter( Context c)
	{
		try
		{
			//Avant affichage, synchro des rdv
			dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
			db106.Sync106(c) ;

			String query="";

			query="select distinct cli.VILLE "+

					" FROM "+
					TABLENAME+

					" cli "+
					//Ajout rdv
					" inner join kems_data as rdv on rdv.cli_code = cli.code and rdv.dat_type=106 ";
			//" left join kems_param as param on rdv.dat_data02=param.prm_coderec and prm_table='TYPEACTIVITE' "+

			query += " order by cli.VILLE ";

			Log.e("query","getListVilleForFilter"+query);

			ArrayList<Bundle>  tournees=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();

				cli.putString(Global.dbClient.FIELD_VILLE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));

				tournees.add(cli);
			}
			if (cur!=null)
				cur.close();
			return tournees;
		}
		catch(Exception ex)
		{
			String dd=ex.getLocalizedMessage();
		}

		return null;
	}

	public boolean getRecordVendeurForFilter(String codeTable,String typeagent,String stremove,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */

			liste.clear();

			String query="SELECT "+this.FIELD_CODECOM+ " FROM "+
					TABLENAME+ " cli "+
					//Ajout rdv
					" inner join kems_data as rdv on rdv.cli_code = cli.code and rdv.dat_type=106 "+
					" GROUP BY "+this.FIELD_CODECOM+
					" ORDER BY "+
					this.FIELD_CODECOM;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";



			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				//cli.putString(Global.dbClient.FIELD_CODE, cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE)));
				prm_coderec	=giveFld(cur,this.FIELD_CODECOM);
				prm_lbl	=giveFld(cur,this.FIELD_CODECOM);
				//	prm_coderec	=giveFld(cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				//  prm_lbl		=giveFld(cur,cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_VILLE)));
				prm_comment	="";
				prm_value	="";
				if(!Fonctions.GetStringDanem(stremove).equals("") && Fonctions.GetStringDanem(stremove).equals(prm_coderec))
				{
					/*Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);*/
				}
				else
				{
					Bundle bundle = new Bundle();
					bundle.putString("prm_coderec", prm_coderec);
					bundle.putString("prm_lbl", prm_lbl);
					bundle.putString("prm_comment", prm_comment);
					bundle.putString("prm_value", prm_value);
					liste.add(bundle);
				}

			}

			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}

		return true;
	}

}