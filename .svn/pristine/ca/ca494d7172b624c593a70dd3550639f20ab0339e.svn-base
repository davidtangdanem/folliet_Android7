package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.TableListeMateriel.structListeMateriel;

import android.database.Cursor;
import android.os.Bundle;

public class TableListeTarifs extends DBMain{
	
	static public String TABLENAME="LISTETARIFS";

	public static final String FIELD_LT_COD_TRF		= "LT_COD_TRF";
	public static final String FIELD_LT_NOM_TRF		= "LT_NOM_TRF";
	public static final String FIELD_LT_FERME	= "LT_FERME";
	

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_LT_COD_TRF			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_LT_NOM_TRF		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LT_FERME			+"] [varchar](50) NULL"	+ 
			")";
	
	
	static public class structListetarif
	{
		
		
		public String LT_COD_TRF = "";        
		public String LT_NOM_TRF = "";
		public String LT_FERME = "";
		
		@Override
		public String toString() {
			return "structListetarif [LT_COD_TRF=" + LT_COD_TRF + ", LT_NOM_TRF=" + LT_NOM_TRF
					+  ", LT_FERME=" + LT_FERME 
					+"]";
		}
	}
		
	MyDB db;
	public TableListeTarifs(MyDB _db)
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

	public structListetarif load(Cursor cursor){
		structListetarif ListeTarif = new structListetarif();
		if(cursor != null){
			
		
			
			ListeTarif.LT_COD_TRF = giveFld(cursor, FIELD_LT_COD_TRF);
			ListeTarif.LT_NOM_TRF = giveFld(cursor, FIELD_LT_NOM_TRF);
			ListeTarif.LT_FERME = giveFld(cursor, FIELD_LT_FERME);
		
		
		}

	
		return ListeTarif;
	}
	

	public structListetarif load(String categorie){
		structListetarif ListeTarif = new structListetarif();
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_LT_COD_TRF)+" = "+categorie+" ";

		Cursor cursor =  db.conn.rawQuery(query, null);

		if(cursor != null && cursor.moveToFirst()){

			ListeTarif = load(cursor);
			cursor.close();
		}

		return ListeTarif;
	}
	

	/**
	 * Permet d'obtenir une liste de structTarif à partir d'un cursor
	 * @param cursor
	 * @return ArrayList<structTarif>
	 */
	public ArrayList<structListetarif> getListOfCursorListeTarifs(Cursor cursor){
		ArrayList<structListetarif> list = new ArrayList<TableListeTarifs.structListetarif>();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			structListetarif ListeTarif = new structListetarif();
			fillStruct(cursor,ListeTarif);
			list.add(ListeTarif);
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


	

	void fillStruct(Cursor cur,structListetarif ListeTarif)
	{
		
		ListeTarif.LT_COD_TRF=cur.getString(cur.getColumnIndex(FIELD_LT_COD_TRF));
		ListeTarif.LT_NOM_TRF=cur.getString(cur.getColumnIndex(FIELD_LT_NOM_TRF));
		ListeTarif.LT_FERME=cur.getString(cur.getColumnIndex(FIELD_LT_FERME));
		
		
	}
	public ArrayList<Bundle> getListeTarifsFilters(String filter)
	{
		try
		{
			filter=MyDB.controlFld(filter);
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where ("+
					FIELD_LT_COD_TRF+ " like '%"+filter+"%' "+
					" or "+
					FIELD_LT_NOM_TRF+ " like '%"+filter+"%' )  order by "+FIELD_LT_COD_TRF;
			
			
			ArrayList<Bundle>  tarif=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbListeTarif.FIELD_LT_COD_TRF,cur.getString(cur.getColumnIndex(Global.dbListeTarif.FIELD_LT_COD_TRF)));
				cli.putString(Global.dbListeTarif.FIELD_LT_NOM_TRF, cur.getString(cur.getColumnIndex(Global.dbListeTarif.FIELD_LT_NOM_TRF)));
				cli.putString(Global.dbListeTarif.FIELD_LT_FERME, cur.getString(cur.getColumnIndex(Global.dbListeTarif.FIELD_LT_FERME)));
			
				tarif.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return tarif;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return null;
	}
	
	public String getListeTarif_Nom(String categorie)
	{
		try
		{
			String stNom="";
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+
					FIELD_LT_COD_TRF+
					"='"+categorie+"'  ";
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				stNom=cur.getString(cur.getColumnIndex(Global.dbListeTarif.FIELD_LT_NOM_TRF));
			
			}
			if (cur!=null)
				cur.close();
			return stNom;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return null;
	}
	

}
