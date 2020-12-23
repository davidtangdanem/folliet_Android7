package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import android.R;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.helper.Taxe;

public class TableHistoInter extends DBMain{

	static public String TABLENAME= "HISTOINTERV12";//"HISTOINTERV11";

	public static final String FIELD_CODECLIENT		= "CODECLI";
	public static final String FIELD_NUMINTER		= "NUM_INTER";
	public static final String FIELD_TYPINTER		= "TYP_INTER";
	public static final String FIELD_INFOINTER		= "INFO_INTER";
	public static final String FIELD_DATECREAINTER 	= "DAT_CREA_INTER";
	public static final String FIELD_LIB 			= "LIB";
	public static final String FIELD_INFOFININTER 	= "INFO_FIN_INTER";
	public static final String FIELD_CODTEC 		= "COD_TEC";
	public static final String FIELD_DATEFININTER 	= "DAT_FIN_INTER";
	public static final String FIELD_CLOTURE 		= "CLOTURE";
	public static final String FIELD_PLANIFE 		= "PLANIFIE";
	public static final String FIELD_DATEINTER 		= "DAT_INTER";
	public static final String FIELD_CODEAGENT 		= "CODEAGENT";
	public static final String FIELD_NUM_SERIE 		= "NUM_SERIE";
	public static final String FIELD_DGN 			= "DGN";
	public static final String FIELD_SYMPTOME 		= "SYMPTOME";
	public static final String FIELD_AGENCE 		= "AGENCE";



	public static final String INDEXNAME_CODE_CLI = "INDEX_CODE_CLI";
	public static final String INDEXNAME_NUM_INTER = "INDEX_NUM_INTER";
 
	public static final String INDEX_CREATE_CODE_CLI="CREATE UNIQUE INDEX IF NOT EXISTS ["+INDEXNAME_CODE_CLI+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_CODECLIENT+"])";
	public static final String INDEX_CREATE_NUM_INTER="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_NUM_INTER+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_NUMINTER+"])";

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_CODECLIENT		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_CLOTURE			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_CODEAGENT		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_CODTEC			+"] [varchar](50)NULL" 		+ COMMA +
			" ["+FIELD_DATECREAINTER	+"] [varchar](50)NULL" 		+ COMMA +
			" ["+FIELD_DATEFININTER		+"] [varchar](50)NULL" 		+ COMMA +
			" ["+FIELD_DATEINTER		+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_INFOFININTER		+"] [varchar](255) NULL" 	+ COMMA +
			" ["+FIELD_INFOINTER		+"] [varchar](255) NULL" 	+ COMMA +
			" ["+FIELD_LIB				+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_NUMINTER			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_PLANIFE			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_TYPINTER			+"] [varchar](50) NULL"		+ COMMA +
			" ["+FIELD_NUM_SERIE		+"] [varchar](50) NULL"		+ COMMA +
			" ["+FIELD_DGN			 	+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_SYMPTOME			+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_AGENCE			+"] [varchar](250) NULL" 	+

			")";
	
	
 
		
	MyDB db;
	public TableHistoInter(MyDB _db)
	{
		super();
		db=_db;
	}
	public boolean deletedate_creat_encours(){

		
		try {
			String query = "DELETE from " + TABLENAME + " where "+
					FIELD_DATECREAINTER+" >='"+Fonctions.getYYYYMM()+"'  ";

			db.conn.execSQL(query);

			
			return true;
		} catch (SQLException ex) {

		}		
		return false;
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
			filter=MyDB.controlFld(filter);
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"'  order by   "+FIELD_DATEINTER+" desc ";
			
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(FIELD_CODECLIENT		,cur.getString(cur.getColumnIndex(FIELD_CODECLIENT		)));
				cli.putString(FIELD_NUMINTER		,cur.getString(cur.getColumnIndex(FIELD_NUMINTER		)));
				cli.putString(FIELD_TYPINTER		,cur.getString(cur.getColumnIndex(FIELD_TYPINTER		)));
				cli.putString(FIELD_INFOINTER		,cur.getString(cur.getColumnIndex(FIELD_INFOINTER		)));
				cli.putString(FIELD_DATECREAINTER 	,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATECREAINTER )	)));
				cli.putString(FIELD_LIB 			,cur.getString(cur.getColumnIndex(FIELD_LIB 			)));
				cli.putString(FIELD_INFOFININTER 	,cur.getString(cur.getColumnIndex(FIELD_INFOFININTER 	)));
				cli.putString(FIELD_CODTEC 			,cur.getString(cur.getColumnIndex(FIELD_CODTEC 		)));
				cli.putString(FIELD_DATEFININTER 	,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATEFININTER 	))));
				cli.putString(FIELD_CLOTURE 		,cur.getString(cur.getColumnIndex(FIELD_CLOTURE 		)));
				cli.putString(FIELD_PLANIFE 		,cur.getString(cur.getColumnIndex(FIELD_PLANIFE 		)));
				cli.putString(FIELD_DATEINTER 		,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATEINTER 		))));
				cli.putString(FIELD_CODEAGENT 		,cur.getString(cur.getColumnIndex(FIELD_CODEAGENT 		)));
				cli.putString(FIELD_NUM_SERIE 		,cur.getString(cur.getColumnIndex(FIELD_NUM_SERIE 		)));
				cli.putString(FIELD_DGN				,cur.getString(cur.getColumnIndex(FIELD_DGN 		)));
				cli.putString(FIELD_SYMPTOME				,cur.getString(cur.getColumnIndex(FIELD_SYMPTOME		)));
				cli.putString(FIELD_AGENCE				,cur.getString(cur.getColumnIndex(FIELD_AGENCE		)));
				
	 			
				String datefin=cur.getString(cur.getColumnIndex(FIELD_DATEFININTER 	));
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
	public ArrayList<Bundle> getHistoFilters(String codeclient,  String filter,String numserie)
	{
		try
		{
			filter=MyDB.controlFld(filter);
			String wherenumserie=" ";
			if(!Fonctions.GetStringDanem(numserie).equals(""))
			{
				wherenumserie=" and "+FIELD_NUM_SERIE+"='"+numserie+"' " ;
			}
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"'"+wherenumserie+"  order by   "+FIELD_DATEINTER+" desc ";
			
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(FIELD_CODECLIENT		,cur.getString(cur.getColumnIndex(FIELD_CODECLIENT		)));
				cli.putString(FIELD_NUMINTER		,cur.getString(cur.getColumnIndex(FIELD_NUMINTER		)));
				cli.putString(FIELD_TYPINTER		,cur.getString(cur.getColumnIndex(FIELD_TYPINTER		)));
				cli.putString(FIELD_INFOINTER		,cur.getString(cur.getColumnIndex(FIELD_INFOINTER		)));
				cli.putString(FIELD_DATECREAINTER 	,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATECREAINTER )	)));
				cli.putString(FIELD_LIB 			,cur.getString(cur.getColumnIndex(FIELD_LIB 			)));
				cli.putString(FIELD_INFOFININTER 	,cur.getString(cur.getColumnIndex(FIELD_INFOFININTER 	)));
				cli.putString(FIELD_CODTEC 			,cur.getString(cur.getColumnIndex(FIELD_CODTEC 		)));
				cli.putString(FIELD_DATEFININTER 	,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATEFININTER 	))));
				cli.putString(FIELD_CLOTURE 		,cur.getString(cur.getColumnIndex(FIELD_CLOTURE 		)));
				cli.putString(FIELD_PLANIFE 		,cur.getString(cur.getColumnIndex(FIELD_PLANIFE 		)));
				cli.putString(FIELD_DATEINTER 		,Fonctions.YYYYMMDD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex(FIELD_DATEINTER 		))));
				cli.putString(FIELD_CODEAGENT 		,cur.getString(cur.getColumnIndex(FIELD_CODEAGENT 		)));
				cli.putString(FIELD_NUM_SERIE 		,cur.getString(cur.getColumnIndex(FIELD_NUM_SERIE 		)));
				cli.putString(FIELD_DGN				,cur.getString(cur.getColumnIndex(FIELD_DGN 		)));
				cli.putString(FIELD_SYMPTOME				,cur.getString(cur.getColumnIndex(FIELD_SYMPTOME		)));
				cli.putString(FIELD_AGENCE				,cur.getString(cur.getColumnIndex(FIELD_AGENCE		)));
	 			
				String datefin=cur.getString(cur.getColumnIndex(FIELD_DATEFININTER 	));
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
	public String LoadInfo_inter(String codeclient,String numinterne)
	{
		String infointer="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUMINTER+
					"='"+numinterne+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				
				infointer=cur.getString(cur.getColumnIndex(FIELD_INFOINTER		));
				
				
				
			}
			if (cur!=null)
				cur.close();
			return infointer;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return infointer;
	}
	public String LoadInfo_Symptome(String codeclient,String numinterne)
	{
		String infointer="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUMINTER+
					"='"+numinterne+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				
				infointer=cur.getString(cur.getColumnIndex(FIELD_SYMPTOME		));
				
				
				
			}
			if (cur!=null)
				cur.close();
			return infointer;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return infointer;
	}
	public String LoadInfo_numfab(String codeclient,String numinterne)
	{
		String numserie="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUMINTER+
					"='"+numinterne+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				
				numserie=cur.getString(cur.getColumnIndex(FIELD_NUM_SERIE		));
				
				
				
			}
			if (cur!=null)
				cur.close();
			return numserie;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return numserie;
	}
	public String LoadType_inter(String codeclient,String numinterne)
	{
		String typeinter="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUMINTER+
					"='"+numinterne+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				typeinter=cur.getString(cur.getColumnIndex(FIELD_TYPINTER		)).trim();
				
				
			}
			if (cur!=null)
				cur.close();
			return typeinter;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return typeinter;
	}
	
	public String LoadType_inter_numserie(String codeclient,String numserie)
	{
		String typeinter="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUM_SERIE+
					"='"+numserie+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				typeinter=cur.getString(cur.getColumnIndex(FIELD_TYPINTER		)).trim();
				
				
			}
			if (cur!=null)
				cur.close();
			return typeinter;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return typeinter;
	}
	public String LoadType_inter_numserieCloture(String codeclient,String numserie)
	{
		String typeinter="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUM_SERIE+
					"='"+numserie+"'  and "+
					FIELD_CLOTURE+
					"='1'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				typeinter=cur.getString(cur.getColumnIndex(FIELD_TYPINTER		)).trim();
				
				
			}
			if (cur!=null)
				cur.close();
			return typeinter;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return typeinter;
	}
	public String LoadDateFin_inter_numserie(String codeclient,String numserie)
	{
		String typeinter="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUM_SERIE+
					"='"+numserie+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				typeinter=cur.getString(cur.getColumnIndex(FIELD_DATEFININTER		)).trim();
				
				
			}
			if (cur!=null)
				cur.close();
			return typeinter;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return typeinter;
	}
	public String LoadDateFin_inter_numserieCloture(String codeclient,String numserie)
	{
		String typeinter="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUM_SERIE+
					"='"+numserie+"'  and "+
					FIELD_CLOTURE+
					"='1'";
			
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				typeinter=cur.getString(cur.getColumnIndex(FIELD_DATEFININTER		)).trim();
				
				
			}
			if (cur!=null)
				cur.close();
			return typeinter;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return typeinter;
	}
	public int IntLoadDateFin_inter_numserie(String codeclient,String numserie)
	{
		int itypeinter=0;
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUMINTER+
					"='"+numserie+"'";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				itypeinter=Fonctions.GetStringToIntDanem(cur.getString(cur.getColumnIndex(FIELD_DATEFININTER		)).trim());
				
				
			}
			if (cur!=null)
				cur.close();
			return itypeinter;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return itypeinter;
	}
	public boolean Load_numserieExiste(String codeclient,String numserie)
	{
		String typeinter="";
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"'  and "+
					FIELD_NUMINTER+
					"='"+numserie+"'";
					
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if(
						Fonctions.GetStringDanem((cur.getString(cur.getColumnIndex(FIELD_NUM_SERIE)))).equals("") 
						&&
						Fonctions.GetStringDanem((cur.getString(cur.getColumnIndex(FIELD_DGN)))).equals("")
							
						) 
				{
					if (cur!=null)
						cur.close();
					return false;	
				}
								
				if (cur!=null)
					cur.close();				
				return true;
				
			}
			if (cur!=null)
				cur.close();
			return false;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return false;
	}
	
	
	
	
	public boolean ExisteDateFin_inter_numserie(String codeclient,String numserie)
	{
		boolean bres=false;
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME  +
					 
					" where "+
					FIELD_CODECLIENT+
					"='"+codeclient+"' and "+
					FIELD_NUM_SERIE+
					"='"+numserie+"' and "+
					FIELD_CLOTURE+
					"='1'";
					
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				bres=true;
				
				
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
