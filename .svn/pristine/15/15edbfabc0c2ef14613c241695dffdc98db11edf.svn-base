package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Fonctions;

import android.database.Cursor;
import android.os.Bundle;

public class TableHistoTodo extends DBMain{
	
	static public String TABLENAME="HISTOTODO";

	public static final String FIELD_EVT_ID			= "EVT_ID";
	public static final String FIELD_COD_CLI		= "COD_CLI";
	public static final String FIELD_COD_COLL		= "COD_COLL";
	public static final String FIELD_TYPE_COLL		= "TYPE_COLL";
	public static final String FIELD_EVENEMENT		= "EVENEMENT";
	public static final String FIELD_LIB_EVENEMENT 	= "LIB_EVENEMENT";
	public static final String FIELD_DATE_FIN 		= "DATE_FIN";
	public static final String FIELD_RESP_TACHE 	= "RESP_TACHE";
	public static final String FIELD_DESCRIPTION 	= "DESCRIPTION";
	public static final String FIELD_DATE_DEB 		= "DATE_DEB";
	public static final String FIELD_DATE_VISITE 	= "DATE_VISITE";
	public static final String FIELD_COD_VRP 		= "COD_VRP";
	

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_EVT_ID		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_COD_CLI			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_COD_COLL		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_TYPE_COLL			+"] [varchar](50)NULL" 	+ COMMA +
			" ["+FIELD_EVENEMENT	+"] [varchar](50)NULL" 	+ COMMA +
			" ["+FIELD_LIB_EVENEMENT		+"] [varchar](250)NULL" 	+ COMMA +
			" ["+FIELD_DATE_FIN		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_RESP_TACHE		+"] [varchar](255) NULL" 	+ COMMA +
			" ["+FIELD_DESCRIPTION		+"] [varchar](255) NULL" 	+ COMMA +
			" ["+FIELD_DATE_DEB				+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_DATE_VISITE			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_COD_VRP			+"] [varchar](50) NULL"	+ 
			")";
	
	
 
		
	MyDB db;
	public TableHistoTodo(MyDB _db)
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
	public ArrayList<Bundle> getHistoFilters(String codeclient,  String filter)
	{
		try
		{
			int t=Count();
			
			filter=MyDB.controlFld(filter);
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_COD_CLI+
					"='"+codeclient+"'  order by   "+FIELD_DATE_FIN+" desc ";
			
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				dbSiteListeLogin login=new dbSiteListeLogin(db);
				Bundle cli=new Bundle();
				cli.putString(FIELD_EVT_ID			,cur.getString(cur.getColumnIndex(FIELD_EVT_ID		)));
				cli.putString(FIELD_COD_CLI			,cur.getString(cur.getColumnIndex(FIELD_COD_CLI		)));
				cli.putString(FIELD_COD_COLL		,login.getLblNom(cur.getString(cur.getColumnIndex(FIELD_COD_COLL		))));
				cli.putString(FIELD_TYPE_COLL		,cur.getString(cur.getColumnIndex(FIELD_TYPE_COLL		)));
				cli.putString(FIELD_EVENEMENT		,cur.getString(cur.getColumnIndex(FIELD_TYPE_COLL		)));
				cli.putString(FIELD_LIB_EVENEMENT 	,cur.getString(cur.getColumnIndex(FIELD_EVT_ID		))+" - "+cur.getString(cur.getColumnIndex(FIELD_LIB_EVENEMENT 			)));
				cli.putString(FIELD_DATE_FIN 		,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATE_FIN )	)));
				cli.putString(FIELD_RESP_TACHE 		,cur.getString(cur.getColumnIndex(FIELD_RESP_TACHE 		)));
				cli.putString(FIELD_DESCRIPTION 	,cur.getString(cur.getColumnIndex(FIELD_DESCRIPTION 		)));
				cli.putString(FIELD_DATE_DEB 		,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATE_DEB )	)));
				cli.putString(FIELD_DATE_VISITE 	,cur.getString(cur.getColumnIndex(FIELD_DATE_VISITE 		)));
				cli.putString(FIELD_COD_VRP 		,cur.getString(cur.getColumnIndex(FIELD_COD_VRP 		)));
				
				String datefin=cur.getString(cur.getColumnIndex(FIELD_DATE_FIN 	));
				String layoutcolor="vert";
				if (datefin.trim().equals(""))
					layoutcolor="rouge";
				cli.putString("layoutcolor" 		,layoutcolor);
	 			
				
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
	

}
