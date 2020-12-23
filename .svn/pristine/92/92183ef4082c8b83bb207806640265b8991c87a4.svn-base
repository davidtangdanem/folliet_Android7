package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.TableListeTarifs.structListetarif;

import android.database.Cursor;
import android.os.Bundle;

public class TableListeTarifsDetails extends DBMain{
	
	static public String TABLENAME="LISTETARIFSDETAILS";

	public static final String FIELD_LTD_COD_TRF		= "LTD_COD_TRF";
	public static final String FIELD_LTD_COD_ART		= "LTD_COD_ART";
	public static final String FIELD_LTD_DESIGN		= "LTD_COD_DESIGN";
	public static final String FIELD_LTD_COD_CLT		= "LTD_COD_CLT";
	public static final String FIELD_LTD_DAT_DEB		= "LTD_DAT_DEB";
	public static final String FIELD_LTD_DAT_FIN		= "LTD_DAT_FIN";
	public static final String FIELD_LTD_PRIX			= "LTD_PRIX";
	public static final String FIELD_LTD_PRIX_MINI		= "LTD_PRIX_MINI";
	public static final String FIELD_LTD_BLOQUE			= "LTD_BLOQUE";
	public static final String FIELD_LTD_REM_SODEXO		= "LTD_REM_SODEXO";
	public static final String FIELD_LTD_PRIX_NET		= "LTD_PRIX_NET";
	
	

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_LTD_COD_TRF			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_LTD_COD_ART		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LTD_DESIGN		+"] [varchar](250) NULL" 	+ COMMA +
			
			" ["+FIELD_LTD_COD_CLT			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_LTD_DAT_DEB		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LTD_DAT_FIN			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_LTD_PRIX		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LTD_PRIX_MINI			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_LTD_BLOQUE		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LTD_REM_SODEXO		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LTD_PRIX_NET			+"] [varchar](50) NULL"	+ 
			")";
	
	
	static public class structListetarifsDetails
	{
		
		
		public String LTD_COD_TRF = "";        
		public String LTD_COD_ART = "";
		public String LTD_DESIGN = "";
		public String LTD_COD_CLT = "";
		public String LTD_DAT_DEB = "";        
		public String LTD_DAT_FIN = "";
		public String LTD_PRIX = "";
		public String LTD_PRIX_MINI = "";        
		public String LTD_BLOQUE = "";
		public String LTD_REM_SODEXO = "";
		public String LTD_PRIX_NET	 = "";
		
		
		@Override
		public String toString() {
			return "structListetarif [LTD_COD_TRF=" + LTD_COD_TRF + ", LTD_COD_ART=" + LTD_COD_ART
					+  ", LTD_DESIGN=" + LTD_DESIGN +  ", LTD_COD_CLT=" + LTD_COD_CLT +  ", LTD_DAT_DEB=" + LTD_DAT_DEB +  ", LTD_DAT_FIN=" + LTD_DAT_FIN 
					+  ", LTD_PRIX=" + LTD_PRIX +  ", LTD_PRIX_MINI=" + LTD_PRIX_MINI +  ", LTD_BLOQUE=" + LTD_BLOQUE 
					+  ", LTD_REM_SODEXO=" + LTD_REM_SODEXO +  ", LTD_PRIX_NET=" + LTD_PRIX_NET 
									
					+"]";
		}
	}
		
	MyDB db;
	public TableListeTarifsDetails(MyDB _db)
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
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;	
		}
		return true;
	}

	public structListetarifsDetails load(Cursor cursor){
		structListetarifsDetails ListeTarifsDetails = new structListetarifsDetails();
		if(cursor != null){
			
		
			
			ListeTarifsDetails.LTD_COD_TRF = giveFld(cursor, FIELD_LTD_COD_TRF);
			ListeTarifsDetails.LTD_COD_ART = giveFld(cursor, FIELD_LTD_COD_ART);
			ListeTarifsDetails.LTD_DESIGN = giveFld(cursor, FIELD_LTD_DESIGN);
			ListeTarifsDetails.LTD_COD_CLT = giveFld(cursor, FIELD_LTD_COD_CLT);
			ListeTarifsDetails.LTD_DAT_DEB = giveFld(cursor, FIELD_LTD_DAT_DEB);
			ListeTarifsDetails.LTD_DAT_FIN = giveFld(cursor, FIELD_LTD_DAT_FIN);
			ListeTarifsDetails.LTD_PRIX = giveFld(cursor, FIELD_LTD_PRIX);
			ListeTarifsDetails.LTD_PRIX_MINI = giveFld(cursor, FIELD_LTD_PRIX_MINI);
			ListeTarifsDetails.LTD_BLOQUE = giveFld(cursor, FIELD_LTD_BLOQUE);
			ListeTarifsDetails.LTD_REM_SODEXO = giveFld(cursor, FIELD_LTD_REM_SODEXO);
			ListeTarifsDetails.LTD_PRIX_NET = giveFld(cursor, FIELD_LTD_PRIX_NET);
		
		}

	
		return ListeTarifsDetails;
	}
	

	public structListetarifsDetails load(String categorie){
		structListetarifsDetails ListeTarifsDetails = new structListetarifsDetails();
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_LTD_COD_TRF)+" = "+categorie+" ";

		Cursor cursor =  db.conn.rawQuery(query, null);

		if(cursor != null && cursor.moveToFirst()){

			ListeTarifsDetails = load(cursor);
			cursor.close();
		}

		return ListeTarifsDetails;
	}
	

	/**
	 * Permet d'obtenir une liste de structTarif à partir d'un cursor
	 * @param cursor
	 * @return ArrayList<structTarif>
	 */
	public ArrayList<structListetarifsDetails> getListOfCursorListeTarifsDetails(Cursor cursor){
		ArrayList<structListetarifsDetails> list = new ArrayList<TableListeTarifsDetails.structListetarifsDetails>();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			structListetarifsDetails ListeTarifsDetails = new structListetarifsDetails();
			fillStruct(cursor,ListeTarifsDetails);
			list.add(ListeTarifsDetails);
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


	

	void fillStruct(Cursor cur,structListetarifsDetails ListeTarifsDetails)
	{
		
		ListeTarifsDetails.LTD_COD_TRF=cur.getString(cur.getColumnIndex(FIELD_LTD_COD_TRF));
		ListeTarifsDetails.LTD_COD_ART=cur.getString(cur.getColumnIndex(FIELD_LTD_COD_ART));
		ListeTarifsDetails.LTD_DESIGN=cur.getString(cur.getColumnIndex(FIELD_LTD_DESIGN));
		ListeTarifsDetails.LTD_COD_CLT=cur.getString(cur.getColumnIndex(FIELD_LTD_COD_CLT));
		ListeTarifsDetails.LTD_DAT_DEB=cur.getString(cur.getColumnIndex(FIELD_LTD_DAT_DEB));
		ListeTarifsDetails.LTD_DAT_FIN=cur.getString(cur.getColumnIndex(FIELD_LTD_DAT_FIN));
		ListeTarifsDetails.LTD_PRIX=cur.getString(cur.getColumnIndex(FIELD_LTD_PRIX));
		ListeTarifsDetails.LTD_PRIX_MINI=cur.getString(cur.getColumnIndex(FIELD_LTD_PRIX_MINI));
		ListeTarifsDetails.LTD_BLOQUE=cur.getString(cur.getColumnIndex(FIELD_LTD_BLOQUE));
		ListeTarifsDetails.LTD_REM_SODEXO=cur.getString(cur.getColumnIndex(FIELD_LTD_REM_SODEXO));
		ListeTarifsDetails.LTD_PRIX_NET=cur.getString(cur.getColumnIndex(FIELD_LTD_PRIX_NET));
		
	}
	public ArrayList<Bundle> getListeTarifsDetailsFilters(String codetarif,  String filter)
	{
		try
		{
			String stcodetarifInt="";
			stcodetarifInt=Fonctions.GetStringDanemEspace(codetarif);
			
			filter=MyDB.controlFld(filter);
			String whereFiltre="";
			if(!filter.equals(""))
			{
				whereFiltre=" and ( "+FIELD_LTD_COD_ART+ " like '%"+filter+"%' OR "+FIELD_LTD_DESIGN+ " like '%"+filter+"%' ) ";
			}
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+
					FIELD_LTD_COD_TRF+
					"='"+stcodetarifInt+"' "
							+ whereFiltre+
					" order by "+FIELD_LTD_DESIGN
					+" LIMIT 200 ";
			
			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_COD_TRF,cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_COD_TRF)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_COD_ART, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_COD_ART)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_DESIGN, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_DESIGN)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_COD_CLT, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_COD_CLT)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_DAT_DEB,cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_DAT_DEB)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_DAT_FIN, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_DAT_FIN)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_PRIX, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_PRIX)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_PRIX_MINI,cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_PRIX_MINI)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_BLOQUE, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_BLOQUE)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_REM_SODEXO, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_REM_SODEXO)));
				cli.putString(Global.dbListeTarifsDetails.FIELD_LTD_PRIX_NET, cur.getString(cur.getColumnIndex(Global.dbListeTarifsDetails.FIELD_LTD_PRIX_NET)));
				
				
					
				tarifdetails.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return tarifdetails;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return null;
	}
	
	

}
