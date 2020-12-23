package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.helper.Taxe;

import android.database.Cursor;
import android.util.Log;


public class TableTarif extends DBMain{
	static public String ST_GENERIQUE="GEN";
	static public String ST_CODETARIF99="99";
	static public String ST_HISTO="HISTO";
	static public String ST_ADJ="ADJ";
	
	static public String TABLENAME="TARIF";
	public static final String INDEXNAME="TAR1";

	public static final String FIELD_COD_CLI		= "COD_CLI";
	public static final String FIELD_COD_PRO		= "COD_PRO";
	public static final String FIELD_TX_TVA			= "TX_TVA";
	public static final String FIELD_PX_REF 		= "PX_REF";
	public static final String FIELD_PX_NET 		= "PX_NET";
	public static final String FIELD_PX_REF_P 		= "PX_REF_P";
	public static final String FIELD_PX_PROMO 		= "PX_PROMO";
	public static final String FIELD_TYP_PXN 		= "TYP_PXN";
	public static final String FIELD_TYP_PXP 		= "TYP_PXP";
	public static final String FIELD_C_PROMO 		= "C_PROMO";
	public static final String FIELD_BLOQUE			= "BLOQUE"; 
	public static final String FIELD_REM1			= "REM1";
	public static final String FIELD_DATEFROM 		= "DATEFROM";
	public static final String FIELD_DATETO			= "DATETO"; 
	public static final String FIELD_REM4 			= "REM4";
	public static final String FIELD_REM5 			= "REM5";
	public static final String FIELD_REM6			= "REM6";
	public static final String FIELD_COD_TAX1 		= "COD_TAX1";
	public static final String FIELD_COD_TAX2 		= "COD_TAX2";
	public static final String FIELD_COD_TAX3 		= "COD_TAX3";
	public static final String FIELD_CODREP 		= "CODREP";
	public static final String FIELD_TRF_MIN 		= "TRF_MIN";
	public static final String FIELD_CODE_CLT 		= "CODE_CLT";

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}

	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_COD_CLI			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_COD_PRO		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_CODREP		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_TX_TVA		+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_PX_REF			+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_PX_NET			+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_PX_REF_P			+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_PX_PROMO			+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_TYP_PXN		+"] [char](4) NULL" 	+ COMMA +
			" ["+FIELD_TYP_PXP			+"] [char](4) NULL" 	+ COMMA +
			" ["+FIELD_C_PROMO			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_BLOQUE	+"] [varchar](2) NULL" 	+ COMMA +
			" ["+FIELD_REM1			+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_DATEFROM			+"] [varchar](8) NULL" 	+ COMMA +
			" ["+FIELD_DATETO			+"] [varchar](8) NULL" 	+ COMMA +
			" ["+FIELD_TRF_MIN			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_CODE_CLT			+"] [varchar](50) NULL" 	+ COMMA +
			
			
			" ["+FIELD_REM4		+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_REM5	+"] [decimal](18,0) NULL"		+ COMMA +
			" ["+FIELD_REM6	+"] [decimal](18,0) NULL" 	+ COMMA +
			" ["+FIELD_COD_TAX1		+"] [int] NULL" 	+ COMMA +
			" ["+FIELD_COD_TAX2		+"] [int] NULL"		+ COMMA +
			" ["+FIELD_COD_TAX3			+"] [int] NULL"	+ 
			")";

	//Ajout du champs tournée dans une autre requete, pour eviter les pertes de données
	public static final String TABLE_CREATE2=
			"ALTER TABLE TARIF ADD COLUMN CODECOM[nvarchar](100) NULL";
	
	public static final String INDEX_CREATE="CREATE UNIQUE INDEX IF NOT EXISTS ["+INDEXNAME+"] ON ["+TABLENAME+"] (["+FIELD_COD_CLI+"], ["+FIELD_COD_PRO+"])";

	static public class structTarif
	{
		public String COD_CLI = "";        
		public String COD_PRO = "";
		public String TX_TVA = "";
		public String PX_REF  = "";                  
		public String PX_NET = "";  
		public String PX_REF_P = "";  
		public String PX_PROMO = "";  
		public String TYP_PXN = "";  
		public String TYP_PXP  = "";  
		public String C_PROMO  = "";
		public String BLOQUE  = ""; 
		public String REM1  = ""; 
		public String DATEFROM= ""; 
		public String DATETO	= ""; 
		public String TRF_MIN	= ""; 
		public String CODE_CLT	= ""; 
		public String REM4 ="";
		public String REM5 ="";
		public String REM6 ="";
		public String COD_TAX1 ="";
		public String COD_TAX2 ="";
		public String COD_TAX3 ="";		

		@Override
		public String toString() {
			return "structClient [COD_CLI=" + COD_CLI + ", COD_PRO=" + COD_PRO
					+ ", TX_TVA=" + TX_TVA + ", PX_REF=" + PX_REF + ", PX_NET="
					+ PX_NET + ", PX_REF_P=" + PX_REF_P + ", PX_PROMO=" + PX_PROMO + ", TYP_PXN="
					+ TYP_PXN + ", TYP_PXP=" + TYP_PXP + ", C_PROMO=" + C_PROMO + ", FORCE="
					+ BLOQUE + ", REM1=" + REM1 + ", REM2=" + DATEFROM + ", REM3="
					+ DATETO + ", REM4=" + TRF_MIN + ", REM5="
					+ CODE_CLT + ", REM6=" + REM6 + ", COD_TAX1=" + COD_TAX1
					+ ", COD_TAX2=" + COD_TAX2 + ", COD_TAX3=" + COD_TAX3 
					+"]";
		}

		/**
		 * Permet d'obtenir une liste de toutes les taxes à appliquer au tarif
		 * @return ArrayList<Taxe>
		 */
		public ArrayList<Taxe> getAllTaxesOfTarif(){
			ArrayList<Taxe> taxes = new ArrayList<Taxe>();

			//TVA
			Taxe tva = new Taxe("TVA", this.TX_TVA, "pourcentage");
			
			if(tva != null) taxes.add(tva);

			//cod_taxe
			Taxe taxe1 = null;
			Taxe taxe2 = null;
			Taxe taxe3 = null;
			if(!this.COD_TAX1.equals("")) taxe1 = Global.dbParam.getTaxeFromCodeTaxe(this.COD_TAX1);
			if(!this.COD_TAX2.equals("")) taxe2 = Global.dbParam.getTaxeFromCodeTaxe(this.COD_TAX2);
			if(!this.COD_TAX3.equals("")) taxe3 = Global.dbParam.getTaxeFromCodeTaxe(this.COD_TAX3);
			
			if(taxe1 != null) taxes.add(taxe1);
			if(taxe2 != null) taxes.add(taxe2);
			if(taxe3 != null) taxes.add(taxe3);
			
			return taxes;
		}
	} 

	MyDB db;
	public TableTarif(MyDB _db)
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
			db.execSQL(TABLE_CREATE2,err);
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;	
		}
		return true;
	}

	public structTarif load(Cursor cursor){
		structTarif tarif = new structTarif();
		if(cursor != null){
			//			Debug.Log("requete", " CODE IN LOADING : -"+giveFld(cursor, FIELD_CODE+"-"));

			tarif.COD_CLI = giveFld(cursor, FIELD_COD_CLI);
			tarif.COD_PRO = giveFld(cursor, FIELD_COD_PRO);
			tarif.TX_TVA = giveFld(cursor, FIELD_TX_TVA);
			tarif.PX_REF = giveFld(cursor, FIELD_PX_REF);
			tarif.PX_NET = giveFld(cursor, FIELD_PX_NET);
			tarif.PX_REF_P = giveFld(cursor, FIELD_PX_REF_P);
			tarif.PX_PROMO = giveFld(cursor, FIELD_PX_PROMO);
			tarif.TYP_PXN = giveFld(cursor, FIELD_TYP_PXN);
			tarif.TYP_PXP = giveFld(cursor, FIELD_TYP_PXP);
			tarif.C_PROMO = giveFld(cursor, FIELD_C_PROMO);
			tarif.BLOQUE = giveFld(cursor, FIELD_BLOQUE);
			tarif.REM1 = giveFld(cursor, FIELD_REM1);
			tarif.DATEFROM = giveFld(cursor, FIELD_DATEFROM);
			tarif.DATETO= giveFld(cursor, FIELD_DATETO);
			tarif.TRF_MIN= giveFld(cursor, FIELD_TRF_MIN);
			tarif.CODE_CLT= giveFld(cursor, FIELD_CODE_CLT);

			tarif.REM4 = giveFld(cursor, FIELD_REM4);
			tarif.REM5 = giveFld(cursor, FIELD_REM5);
			tarif.REM6 = giveFld(cursor, FIELD_REM6);
			tarif.COD_TAX1 = giveFld(cursor, FIELD_COD_TAX1);
			tarif.COD_TAX2 = giveFld(cursor, FIELD_COD_TAX2);
			tarif.COD_TAX3 = giveFld(cursor, FIELD_COD_TAX3);

		}

		//		Debug.Log("requete", client.toString());

		return tarif;
	}

	public structTarif load(int cod_cli, int cod_pro){
		structTarif tarif = new structTarif();
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_COD_CLI)+" = '"+cod_cli+"' "
				+ "and "+getFullFieldName(FIELD_COD_PRO)+" = '"+cod_pro+"'";

		Cursor cursor =  db.conn.rawQuery(query, null);

		if(cursor != null && cursor.moveToFirst()){

			tarif = load(cursor);
			cursor.close();
		}

		return tarif;
	}

	/**
	 * Permet de retourner tous les tarifs d'un client
	 * @param int
	 * @return Cursor
	 */
	public Cursor getTarifsFromCodeClient(int cod_cli){

		String query = "SELECT * FROM "+TABLENAME
				+" WHERE "+FIELD_COD_CLI+"='"+cod_cli+"'"
				+" ORDER BY "+FIELD_COD_CLI+" ASC";

		Cursor cursor = db.conn.rawQuery(query, null);

		return cursor;

	}

	/**
	 * Permet de retourner tous les tarifs d'un produit
	 * @param int
	 * @return Cursor
	 */
	public Cursor getTarifsFromCodeProduit(int cod_pro){

		String query = "SELECT * FROM "+TABLENAME
				+" WHERE "+FIELD_COD_PRO+"='"+cod_pro+"'"
				+" ORDER BY "+FIELD_COD_PRO+" ASC";

		Cursor cursor = db.conn.rawQuery(query, null);

		return cursor;

	}

	/**
	 * Permet de récupérer tous les tarifs de la table
	 * @return Cursor
	 */
	public Cursor getAllTarifs(){
		//TODO pour améliorer les performances, prendre uniquement les tarifs qui correspondent aux clients du vendeur connecté
		//faire une sous requete
		String query = "SELECT * FROM "+TABLENAME+ ";";
		Cursor cursor = db.conn.rawQuery(query, null);
		return cursor;
	}

	/**
	 * Permet d'obtenir une liste de structTarif à partir d'un cursor
	 * @param cursor
	 * @return ArrayList<structTarif>
	 */
	public ArrayList<structTarif> getListOfCursorTarif(Cursor cursor){
		ArrayList<structTarif> list = new ArrayList<TableTarif.structTarif>();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			structTarif tarif = new structTarif();
			fillStruct(cursor,tarif);
			list.add(tarif);
			cursor.moveToNext();
		}
		cursor.close();
		return list;

	}

	public int Count()
	{

		try
		{
			String query="select count(*) from "+TABLENAME;
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				return cur.getInt(0);
			}
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}


	public boolean getTarif(String cod_cli, String cod_pro,structTarif tarif,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				fillStruct(cur,tarif);
				return true;
			}


		}
		catch(Exception ex)
		{

		}

		return false;
	}
	
	public boolean BTarifCode(String cod_cli,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"'  " ;


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public boolean BTarifCode99_Article_codeclient(String cod_cli,String cod_pro,String codeclient,StringBuilder err)
	{
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"'  and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"' and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public boolean BTarifCode99_Article(String cod_cli,String cod_pro,String codeclient,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"'  and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"' and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public boolean BTarifCodeGEN_Article(String cod_cli,String cod_pro,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"'  and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public boolean BTarifCodeHisto_client_Article(String cod_cli,String codeclient, String cod_pro,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"'  and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'  and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"'";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{
			String st="";
			st=ex.getLocalizedMessage();
		}

		return false;
	}
	public boolean BTarifCode_Article(String cod_cli,String cod_pro,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public boolean BTarifCode_ArticleBloque(String cod_cli,String cod_pro,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"' and " +
					FIELD_BLOQUE+
					"='2'";;

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public boolean BTarifCode_ArticleBloque_codeclient(String cod_cli,String cod_pro,String codeclient,StringBuilder err)
	{
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"' and " +

					FIELD_COD_PRO+
					"='"+cod_pro+"' and " +
					FIELD_BLOQUE+
					"='2'";;

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				cur.close();
				return true;
			}
			cur.close();

		}
		catch(Exception ex)
		{

		}

		return false;
	}
	public String getTarifMin(String cod_cli,String cod_pro)
	{	
		String libelle="";

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'  " ;


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				libelle=giveFld(cur,FIELD_TRF_MIN         );

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
	public String GetTarifCode_Article(String cod_cli,String cod_pro,StringBuilder err)
	{	
		String tarif="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'  " ;
			
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				tarif =cur.getString(cur.getColumnIndex(FIELD_PX_NET));
				cur.close();
				
			}
			cur.close();

		}
		catch(Exception ex)
		{
          return tarif;
		}

		return tarif;
	}
	public String GetTarifMinCode_Article(String cod_cli,String cod_pro,StringBuilder err)
	{	
		String tarif="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'  " ;
			
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				tarif =cur.getString(cur.getColumnIndex(FIELD_TRF_MIN));
				cur.close();
				
			}
			cur.close();

		}
		catch(Exception ex)
		{
          return tarif;
		}

		return tarif;
	}
	public String GetTarifHISTO_Code_codeclient_Article(String cod_cli,String codeclient,String cod_pro,StringBuilder err)
	{	
		String tarif="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"'  and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"'  ";
			
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				tarif =cur.getString(cur.getColumnIndex(FIELD_PX_NET));
				cur.close();
				
			}
			cur.close();

		}
		catch(Exception ex)
		{
          return tarif;
		}

		return tarif;
	}
	public String GetTarif99Code_Article(String cod_cli,String cod_pro,String codeclient,StringBuilder err)
	{	
		String tarif="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"' and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"'  " ;
			
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				tarif =cur.getString(cur.getColumnIndex(FIELD_PX_NET));
				cur.close();
				
			}
			cur.close();

		}
		catch(Exception ex)
		{
          return tarif;
		}

		return tarif;
	}
	public String GetTarifMini99Code_Article(String cod_cli,String cod_pro,String codeclient,StringBuilder err)
	{	
		String tarif="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLI+
					"='"+cod_cli+"' and " +
					FIELD_COD_PRO+
					"='"+cod_pro+"' and " +
					FIELD_CODE_CLT+
					"='"+codeclient+"'  " ;
			
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				tarif =cur.getString(cur.getColumnIndex(FIELD_TRF_MIN));
				cur.close();
				
			}
			cur.close();

		}
		catch(Exception ex)
		{
          return tarif;
		}

		return tarif;
	}
	
	public boolean getTarif2(String codeclient,String cod_cli, String cod_pro,ArrayList<String> ValueExt,StringBuilder err)
	{
		String PrixAffiche="";
		String PrixModifiable="";
		String Seuilminimum ="";
		String VentePossible="";
		boolean bres =false;
		
		try
		{
			
			//tarif 99 - code article - code client existent dans la table DTARIF
			if(BTarifCode99_Article_codeclient(ST_CODETARIF99,cod_pro,codeclient,err)==true)
			{

				if(BTarifCode_ArticleBloque_codeclient(ST_CODETARIF99,cod_pro,codeclient, err)==true)
				{
					PrixAffiche=GetTarif99Code_Article( ST_CODETARIF99, cod_pro,codeclient, err);
					PrixModifiable="N";//
					Seuilminimum= "";
					VentePossible="O";
					bres=true;
				}
				else
				{
					PrixAffiche=GetTarif99Code_Article( ST_CODETARIF99, cod_pro,codeclient, err);
					PrixModifiable="O";//
					Seuilminimum= GetTarifMini99Code_Article( ST_CODETARIF99, cod_pro,codeclient, err);
					VentePossible="O";
					bres=true;
				}
			}
			else
			{
				//Le client possède t il un tarif specifique ( OUI )

				if(BTarifCode(cod_cli,err)==true && ( !Fonctions.GetStringDanem(cod_cli).equals(ST_GENERIQUE) &&  !Fonctions.GetStringDanem(cod_cli).equals(ST_ADJ)))
				{
					//Ce tarif est il le code 99( OUI )
					if(cod_cli.equals(ST_CODETARIF99))
					{
						//L'article est-il dans le tarif "99" pour ce lcient ?
						if(BTarifCode99_Article(cod_cli,cod_pro,codeclient,err)==true)
						{
							PrixAffiche=GetTarif99Code_Article( cod_cli, cod_pro,codeclient, err);
							PrixModifiable="O";//
							//Seuilminimum =GetTarif99Code_Article( cod_cli, cod_pro,codeclient, err);
							Seuilminimum= GetTarifMini99Code_Article( cod_cli, cod_pro,codeclient, err);
							VentePossible="O";
							bres=true;
						}
						else
						{
							//l'article est il présent dans le tarif "GEN"
							if(BTarifCodeGEN_Article(ST_GENERIQUE,cod_pro,err)==true)
							{
								//Le client a t il un historique pour cet article ? ( OUI )
							/*if(Global.dbKDHistoLigneDocuments.bTarif_Client_Article(codeclient,cod_pro,err)==true)
							{
								PrixAffiche = Global.dbKDHistoLigneDocuments.GetTarif_Client_Article( codeclient, cod_pro, err);
								PrixModifiable="O";//
								Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
								 VentePossible="O";
								 bres=true;

							}*/
								if(BTarifCodeHisto_client_Article(ST_HISTO, codeclient, cod_pro, err)==true)
								{
									PrixAffiche = GetTarifHISTO_Code_codeclient_Article(ST_HISTO, codeclient, cod_pro, err);
									PrixModifiable="O";//
									Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
									VentePossible="O";
									bres=true;

								}
								else
								{
									PrixAffiche=GetTarifCode_Article( ST_GENERIQUE, cod_pro, err);
									PrixModifiable="O";//
									Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
									VentePossible="O";
									bres=true;
								}

							}
							else
							{
								//vente impossible
								PrixAffiche="";
								PrixModifiable="";
								Seuilminimum ="";
								VentePossible="N";
								bres=true;

							}

						}

					}
					else //Ce tarif est il le code 99( NON )
					{
						//Le tarif du client est-il fermé( OUI )
						if(Global.dbParam.getValueLblAllSoc(Global.dbParam.PARAM_LISTETARIF,cod_cli).equals("2"))
						{
							// l'article est il présent dans le tarif ( OUI )
							if(BTarifCode_Article(cod_cli,cod_pro, err)==true)
							{
								//le prix pour cet article est il bloqué ? ( OUI )
								if(BTarifCode_ArticleBloque(cod_cli,cod_pro, err)==true)
								{
									PrixAffiche=GetTarifCode_Article( cod_cli, cod_pro, err);
									PrixModifiable="N";
									Seuilminimum ="";
									VentePossible="O";
									bres=true;
								}
								else
								{
									PrixAffiche=GetTarifCode_Article( cod_cli, cod_pro, err);
									PrixModifiable="O";
									//Seuilminimum =GetTarifCode_Article( cod_cli, cod_pro, err);
									Seuilminimum= GetTarifMinCode_Article( cod_cli, cod_pro, err);
									VentePossible="O";
									bres=true;
								}

							}
							else
							{
								//vente impossible
								PrixAffiche="";
								PrixModifiable="";
								Seuilminimum ="";
								VentePossible="N";
								bres=true;
							}



						}
						else
						{
							// l'article est il présent dans le tarif ( OUI )
							if(BTarifCode_Article(cod_cli,cod_pro, err)==true)
							{
								//le prix pour cet article est il bloqué ? ( OUI )
								if(BTarifCode_ArticleBloque(cod_cli,cod_pro, err)==true)
								{
									PrixAffiche=GetTarifCode_Article( cod_cli, cod_pro, err);
									PrixModifiable="N";
									Seuilminimum ="";
									VentePossible="O";
									bres=true;
								}
								else
								{
									PrixAffiche=GetTarifCode_Article( cod_cli, cod_pro, err);
									PrixModifiable="O";
									// Seuilminimum =GetTarifCode_Article( cod_cli, cod_pro, err);
									Seuilminimum= GetTarifMinCode_Article( cod_cli, cod_pro, err);
									VentePossible="O";
									bres=true;
								}

							}
							else
							{
								//l'article est il présent dans le tarif "GEN" ( OUI )
								if(BTarifCodeGEN_Article(ST_GENERIQUE,cod_pro,err)==true)
								{
									//Le client a t il un historique pour cet article ?
									//Le client a t il un historique pour cet article ? ( OUI )
								/*if(Global.dbKDHistoLigneDocuments.bTarif_Client_Article(codeclient,cod_pro,err)==true)
								{
									PrixAffiche = Global.dbKDHistoLigneDocuments.GetTarif_Client_Article( codeclient, cod_pro, err);
									PrixModifiable="O";//
									Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
									 VentePossible="O";
									 bres=true;

								}*/
									if(BTarifCodeHisto_client_Article(ST_HISTO, codeclient, cod_pro, err)==true)
									{
										PrixAffiche = GetTarifHISTO_Code_codeclient_Article(ST_HISTO, codeclient, cod_pro, err);
										PrixModifiable="O";//
										Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
										VentePossible="O";
										bres=true;

									}
									else
									{
										PrixAffiche=GetTarifCode_Article( ST_GENERIQUE, cod_pro, err);
										PrixModifiable="O";//
										Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
										VentePossible="O";
										bres=true;

									}

								}
								else
								{
									//vente impossible
									PrixAffiche="";
									PrixModifiable="";
									Seuilminimum ="";
									VentePossible="N";
									bres=true;
								}
							}

						}

					}


				}
				else //Le client possède t il un tarif specifique ( NON )
				{
					//Ce tarif est il le code GEN ( OUI )
					if(Fonctions.GetStringDanem(cod_cli).equals(ST_GENERIQUE))
					{
						//l'article est il présent dans le tarif "GEN" ( OUI )
						if(BTarifCodeGEN_Article(ST_GENERIQUE,cod_pro,err)==true)
						{
							//Le client a t il un historique pour cet article ?
							//Le client a t il un historique pour cet article ? ( OUI )
						/*if(Global.dbKDHistoLigneDocuments.bTarif_Client_Article(codeclient,cod_pro,err)==true)
						{
							PrixAffiche = Global.dbKDHistoLigneDocuments.GetTarif_Client_Article( codeclient, cod_pro, err);
							PrixModifiable="O";//
							Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
							 VentePossible="O";
							 bres=true;

						}*/
							if(BTarifCodeHisto_client_Article(ST_HISTO, codeclient, cod_pro, err)==true)
							{
								PrixAffiche = GetTarifHISTO_Code_codeclient_Article(ST_HISTO, codeclient, cod_pro, err);
								PrixModifiable="O";//
								Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
								VentePossible="O";
								bres=true;

							}
							else
							{
								PrixAffiche=GetTarifCode_Article( ST_GENERIQUE, cod_pro, err);
								PrixModifiable="O";//
								Seuilminimum =getTarifMin(ST_GENERIQUE,cod_pro);
								VentePossible="O";
								bres=true;

							}

						}
						else
						{
							//vente impossible
							PrixAffiche="";
							PrixModifiable="";
							Seuilminimum ="";
							VentePossible="N";
							bres=true;
						}


					}
					else //Ce tarif est il le code GEN ( NON )
					{

						//l'article est il présent dans le tarif "ADJ" ( OUI )
						if(BTarifCodeGEN_Article(ST_ADJ,cod_pro,err)==true)
						{
							//Le client a t il un historique pour cet article ? ( OUI )

							if(BTarifCodeHisto_client_Article(ST_HISTO, codeclient, cod_pro, err)==true)
							{
								PrixAffiche = GetTarifHISTO_Code_codeclient_Article(ST_HISTO, codeclient, cod_pro, err);
								PrixModifiable="O";//
								Seuilminimum =getTarifMin(ST_ADJ,cod_pro);
								VentePossible="O";
								bres=true;

							}
							else
							{
								PrixAffiche=GetTarifCode_Article( ST_ADJ, cod_pro, err);
								PrixModifiable="O";//
								Seuilminimum =getTarifMin(ST_ADJ,cod_pro);
								VentePossible="O";
								bres=true;

							}

						}
						else
						{
							//vente impossible
							PrixAffiche="";
							PrixModifiable="";
							Seuilminimum ="";
							VentePossible="N";
							bres=true;
						}
					}


				}
			}


			
			
			
			//return
			ArrayList<String> Value=new ArrayList();
			ValueExt.add(PrixAffiche);
			ValueExt.add(PrixModifiable);
			ValueExt.add(Seuilminimum);
			ValueExt.add(VentePossible);
			

		}
		catch(Exception ex)
		{

		}

		return bres;
	}

	public boolean getTarifWithoutClient(int cod_pro,structTarif tarif,StringBuilder err)
	{	
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_PRO+
					"='"+cod_pro+"' "
					+ "ORDER BY " +FIELD_PX_NET+ " desc LIMIT 1";


			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				fillStruct(cur,tarif);
				return true;
			}


		}
		catch(Exception ex)
		{

		}

		return false;
	}

	/**
	 * Permet de récupérer le total des taxes
	 * @return String
	 * @param String
	 * @param String
	 */
	public String getTaxeOfTarif(String cod_cli, String cod_pro){
		structTarif tarif = new structTarif();
		String taxeFinal = "";

		getTarif(cod_cli, cod_pro, tarif, new StringBuilder());
		Double taxes = 0.0;
		String taxe1 = "";
		String taxe2 = "";
		String taxe3 = "";
		try{
			taxes = Double.valueOf(tarif.TX_TVA.replace(",", ".")); 

			int code1 = Integer.parseInt(tarif.COD_TAX1);
			int code2 = Integer.parseInt(tarif.COD_TAX2);
			int code3 = Integer.parseInt(tarif.COD_TAX3);
			taxe1 = Global.dbParam.getOMRFromCode(code1);
			taxe2 = Global.dbParam.getOMRFromCode(code2);
			taxe3 = Global.dbParam.getOMRFromCode(code3);
			if(!taxe1.equals("")) taxes += Double.valueOf(taxe1);
			if(!taxe2.equals("")) taxes += Double.valueOf(taxe2);
			if(!taxe3.equals("")) taxes += Double.valueOf(taxe3);
		}catch(NumberFormatException ex){

		}
		taxeFinal = taxes.toString();
		return taxeFinal;
	}

	public String calculateMontantTaxe(Float base, Taxe taxe){
		Double total = 0.0;
		Double based = (double) base;
		String valeur = "";
		if(taxe.getType().equals("pourcentage")){
			try{
				valeur = taxe.getValeur().replace(",", ".");
				total = (Double.valueOf(valeur) /100) * based;
				total = Math.round(total * 100.0)/100.0;
				return total.toString();
			}catch(NumberFormatException ex){

			}
		}else if(taxe.getValeur().equals("")){
			
		}
		return "";
	}

	void fillStruct(Cursor cur,structTarif tarif)
	{
		tarif.C_PROMO=cur.getString(cur.getColumnIndex(FIELD_C_PROMO));
		tarif.COD_CLI=cur.getString(cur.getColumnIndex(FIELD_COD_CLI));
		tarif.COD_PRO=cur.getString(cur.getColumnIndex(FIELD_COD_PRO));
		tarif.COD_TAX1=cur.getString(cur.getColumnIndex(FIELD_COD_TAX1));
		tarif.COD_TAX2=cur.getString(cur.getColumnIndex(FIELD_COD_TAX2));
		tarif.COD_TAX3=cur.getString(cur.getColumnIndex(FIELD_COD_TAX3));
		tarif.BLOQUE=cur.getString(cur.getColumnIndex(FIELD_BLOQUE));
		tarif.PX_NET=cur.getString(cur.getColumnIndex(FIELD_PX_NET));
		tarif.PX_PROMO=cur.getString(cur.getColumnIndex(FIELD_PX_PROMO));
		tarif.PX_REF=cur.getString(cur.getColumnIndex(FIELD_PX_REF));
		tarif.PX_REF_P=cur.getString(cur.getColumnIndex(FIELD_PX_REF_P));
		tarif.REM1=cur.getString(cur.getColumnIndex(FIELD_REM1));
		tarif.DATEFROM=cur.getString(cur.getColumnIndex(FIELD_DATEFROM));
		tarif.DATETO=cur.getString(cur.getColumnIndex(FIELD_DATETO));
		tarif.TRF_MIN=cur.getString(cur.getColumnIndex(FIELD_TRF_MIN));
		tarif.CODE_CLT= cur.getString(cur.getColumnIndex(FIELD_CODE_CLT));
		
		
		tarif.REM4=cur.getString(cur.getColumnIndex(FIELD_REM4));
		tarif.REM5=cur.getString(cur.getColumnIndex(FIELD_REM5));
		tarif.REM6=cur.getString(cur.getColumnIndex(FIELD_REM6));
		tarif.TX_TVA=cur.getString(cur.getColumnIndex(FIELD_TX_TVA));
		tarif.TYP_PXN=cur.getString(cur.getColumnIndex(FIELD_TYP_PXN));
		tarif.TYP_PXP=cur.getString(cur.getColumnIndex(FIELD_TYP_PXP));
	}

}