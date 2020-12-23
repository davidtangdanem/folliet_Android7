package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.TableMaterielClient.structMaterielClient;

import android.database.Cursor;
import android.os.Bundle;

public class TableSymptomes extends DBMain{
	
	static public String TABLENAME="SYMPTOMESV4";

	public static final String FIELD_MACHINE 		= "MACHINE";
	public static final String FIELD_EQUIPEMENT 	= "EQUIPEMENT";
	public static final String FIELD_COD_SYMP 	= "COD_SYMP";
	public static final String FIELD_LIB_SYMP 	= "LIB_SYMP";
	
	
	

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_MACHINE			+"] [varchar](255) NULL"	+ COMMA +
			" ["+FIELD_EQUIPEMENT			+"] [varchar](255) NULL"	+ COMMA +
			" ["+FIELD_COD_SYMP			+"] [varchar](255) NULL"	+ COMMA +
			" ["+FIELD_LIB_SYMP			+"] [varchar](255) NULL"	+ 
			
			
			")";
	
	
	static public class structSymptomes
	{
	
		
		public String MACHINE = "";        
		public String EQUIPEMENT = "";
		public String COD_SYMP = "";
		public String LIB_SYMP  = "";                  
	
		
		
		@Override
		public String toString() {
			return "structSymptomes [MACHINE=" + MACHINE + ", EQUIPEMENT=" + EQUIPEMENT
					+ ", COD_SYMP=" + COD_SYMP + ",  LIB_SYMP=" + LIB_SYMP 
					+"]";
		}
	}
		
	MyDB db;
	public TableSymptomes(MyDB _db)
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

	public structSymptomes load(Cursor cursor){
		structSymptomes Symptomes = new structSymptomes();
		if(cursor != null){
			
		
			Symptomes.MACHINE = giveFld(cursor, FIELD_MACHINE);
			Symptomes.EQUIPEMENT = giveFld(cursor, FIELD_EQUIPEMENT);
			Symptomes.COD_SYMP = giveFld(cursor, FIELD_COD_SYMP);
			Symptomes.LIB_SYMP = giveFld(cursor, FIELD_LIB_SYMP);
		
			
		}

	
		return Symptomes;
	}
	

	public structSymptomes load(String machine, String equipement){
		structSymptomes Symptomes = new structSymptomes();
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_MACHINE)+" = "+machine+" "
				+ "or "+getFullFieldName(FIELD_EQUIPEMENT)+" = "+equipement+"";

		Cursor cursor =  db.conn.rawQuery(query, null);

		if(cursor != null && cursor.moveToFirst()){

			Symptomes = load(cursor);
			cursor.close();
		}

		return Symptomes;
	}
		

	/**
	 * Permet d'obtenir une liste de structTarif à partir d'un cursor
	 * @param cursor
	 * @return ArrayList<structTarif>
	 */
	public ArrayList<structSymptomes> getListOfCursorSymptomes(Cursor cursor){
		ArrayList<structSymptomes> list = new ArrayList<TableSymptomes.structSymptomes>();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			structSymptomes Symptomes = new structSymptomes();
			fillStruct(cursor,Symptomes);
			list.add(Symptomes);
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
				return cur.getInt(0);
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


	

	void fillStruct(Cursor cur,structSymptomes symptome)
	{
		
		
		symptome.MACHINE=cur.getString(cur.getColumnIndex(FIELD_MACHINE));
		symptome.EQUIPEMENT=cur.getString(cur.getColumnIndex(FIELD_EQUIPEMENT));
		symptome.COD_SYMP=cur.getString(cur.getColumnIndex(FIELD_COD_SYMP));
		symptome.LIB_SYMP=cur.getString(cur.getColumnIndex(FIELD_LIB_SYMP));
		
		
		
	}
	
	public ArrayList<Bundle> getSymptomesFilters(String machine,String equipement,  String filter,String stbAutres)
	{
		try
		{
			
			
			
			filter=MyDB.controlFld(filter);
			String query ="";
			if(stbAutres.equals("1"))
			{
			
				 query = "SELECT *"
							+" FROM "+TABLENAME
							+" WHERE ( "+FIELD_MACHINE+" = '"+machine+"' ) "+
							 
							
							" and ("+ 
							FIELD_COD_SYMP+ " like '%"+filter+"%' "+
							" or "+
							FIELD_LIB_SYMP+ " like '%"+filter+"%'  )  order by "+FIELD_COD_SYMP;
			}
			else
			{
				 query = "SELECT *"
							+" FROM "+TABLENAME
							+" WHERE ( "+FIELD_MACHINE+" = '"+machine+"' "
							+ "or "+FIELD_EQUIPEMENT+" = '"+equipement+"' ) "+
							 
							
							" and ("+ 
							FIELD_COD_SYMP+ " like '%"+filter+"%' "+
							" or "+
							FIELD_LIB_SYMP+ " like '%"+filter+"%'  )  order by "+FIELD_COD_SYMP;
			}
			
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbSymptomes.FIELD_MACHINE,cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_MACHINE)));
				cli.putString(Global.dbSymptomes.FIELD_EQUIPEMENT, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_EQUIPEMENT)));
				cli.putString(Global.dbSymptomes.FIELD_COD_SYMP, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_COD_SYMP)));
				cli.putString(Global.dbSymptomes.FIELD_LIB_SYMP, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_LIB_SYMP)));
			
				
				materiel.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return materiel;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return null;
	}
	
	
	public boolean getSymptomes_Machine(String machine,ArrayList<Bundle> liste)
	
	{
		boolean bres=false;
		
		try
		{
			liste.clear();
			Bundle bundleblanc = new Bundle();
			bundleblanc.putString(Global.dbSymptomes.FIELD_MACHINE, "");
			bundleblanc.putString(Global.dbSymptomes.FIELD_EQUIPEMENT, "");
			bundleblanc.putString(Global.dbSymptomes.FIELD_COD_SYMP, "");
			bundleblanc.putString(Global.dbSymptomes.FIELD_LIB_SYMP, "");
			liste.add(bundleblanc);
			
			if(Fonctions.GetStringDanem(machine).equals(""))
			{
				
				return false;
				
			}
				
			
			
			String query = "SELECT *"
					+" FROM "+TABLENAME
					+" WHERE  "+FIELD_MACHINE+" = '"+machine+"' ";
					 
					
			
				
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				bres=true;
				Bundle cli=new Bundle();
				cli.putString(Global.dbSymptomes.FIELD_MACHINE,cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_MACHINE)));
				cli.putString(Global.dbSymptomes.FIELD_EQUIPEMENT, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_EQUIPEMENT)));
				cli.putString(Global.dbSymptomes.FIELD_COD_SYMP, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_COD_SYMP)));
				cli.putString(Global.dbSymptomes.FIELD_LIB_SYMP, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_LIB_SYMP)));
			
				
				liste.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return bres;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return bres;
	}
	

}
