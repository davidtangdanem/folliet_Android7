package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.folliet2016.Debug;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class dbCliToVisit extends DBMain{
	public static String TABLENAME="SITE_CLITOVISIT10";

	public final static String FIELD_CODECLI        	="codeclient" 	 		;                 
	public final String FIELD_CODEREP      				="coderep" 		;                  
	public final static String FIELD_DATE       		="datetournee"  	; 
	public final static String FIELD_LBL      			="lbl"  	;  
	public final static String FIELD_EVT_ID      			="EVT_ID"  	;  
	public final static String FIELD_CODE_EVT      			="CODE_EVT"  	;  
	public final static String FIELD_REF      			="REF"  	;  
	public final static String FIELD_DESCRIPTION      			="DESCRIPTION"  	;  
	
	public static final String TABLE_CREATE=
			"CREATE TABLE [SITE_CLITOVISIT10]("+
					"		[codeclient] [nvarchar](50) NULL,"+
					"		[coderep] [nvarchar](10) NULL,"+
					"		[lbl] [nvarchar](255) NULL,"+ 
					"		[datetournee] [nvarchar](8) NULL,"+
					"		[EVT_ID] [nvarchar](200) NULL,"+
					"		[CODE_EVT] [nvarchar](200) NULL,"+
					"		[REF] [nvarchar](200) NULL,"+
					"		[DESCRIPTION] [nvarchar](255) NULL"+
					
					")";

	static public class structSoc
	{
		public String FIELD_CODECLI        	="" 	 		;                 
		public String FIELD_CODEREP      		="" 		;                  
		public String FIELD_DATE       =""  	;  
		public String FIELD_LBL      =""  	;   
		public String FIELD_EVT_ID      =""  	; 
		public String FIELD_CODE_EVT      =""  	; 
		public String FIELD_ORDER = "";
		public String FIELD_EVENT = "";
		public String FIELD_REF = "";
		public String FIELD_DESCRIPTION = "";
	}

	MyDB db;
	public dbCliToVisit(MyDB _db)
	{
		super();
		db=_db;
	}
	public boolean Clear(StringBuilder err)
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
			if (cur.moveToNext()){
				int count = cur.getInt(0);
				if (cur!=null)
					cur.close();
				return count;
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

	public structSoc getCliToVisitFromCodeCli(String codeCli){
		try{
			String query="select distinct * from "+TABLENAME+" where "+FIELD_CODECLI+"='"+codeCli+"'";
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext()){
				structSoc soc = getCliToVisitFromCursor(cur);
				if (cur!=null)
					cur.close();
				
				return soc;
			}
			if (cur!=null)
				cur.close();
			
		} catch(Exception ex){

		}
		return null;
	}
	
	public structSoc getCliToVisitFromCodeEvent(String codeEvent){
		try{
			String query="select codeclient,coderep,datetournee,lbl,EVT_ID,CODE_EVT,REF,DESCRIPTION from "+TABLENAME+" where "+FIELD_CODE_EVT+"='"+codeEvent+"'"+
			" GROUP BY codeclient,coderep,datetournee,lbl,EVT_ID,CODE_EVT,REF,DESCRIPTION";
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext()){
				return getCliToVisitFromCursor(cur);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public structSoc getCliToVisitFromCodeEvent2(String codeEvent,String evd_id){
		try{
			String query="select codeclient,coderep,datetournee,lbl,EVT_ID,CODE_EVT,REF,DESCRIPTION from "+TABLENAME+" where "+FIELD_CODE_EVT+"='"+codeEvent+"' and "+
					 FIELD_EVT_ID+"='"+evd_id+"'"+
			" GROUP BY codeclient,coderep,datetournee,lbl,EVT_ID,CODE_EVT,REF,DESCRIPTION";
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext()){
				return getCliToVisitFromCursor(cur);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	structSoc getCliToVisitFromCursor(Cursor cur){
		structSoc soc = new structSoc();
		soc.FIELD_CODECLI = cur.getString(cur.getColumnIndex(FIELD_CODECLI));
		soc.FIELD_CODEREP = cur.getString(cur.getColumnIndex(FIELD_CODEREP));
		soc.FIELD_DATE = cur.getString(cur.getColumnIndex(FIELD_DATE));
		soc.FIELD_LBL = cur.getString(cur.getColumnIndex(FIELD_LBL));
		soc.FIELD_EVT_ID = cur.getString(cur.getColumnIndex(FIELD_EVT_ID));
		soc.FIELD_CODE_EVT = cur.getString(cur.getColumnIndex(FIELD_CODE_EVT));
		soc.FIELD_REF = cur.getString(cur.getColumnIndex(FIELD_REF));
		soc.FIELD_DESCRIPTION = cur.getString(cur.getColumnIndex(FIELD_DESCRIPTION));

		return soc;
	}

	public boolean isClientAvisiter(String codeCli  ){
		boolean result = false;
		String query = "SELECT count(*)"
				+" FROM "+TableClient.TABLENAME +" inner join "+dbCliToVisit.TABLENAME+
				" on "+TableClient.FIELD_CODE+"="+dbCliToVisit.FIELD_CODECLI
				+" WHERE "+dbCliToVisit.FIELD_CODECLI +"='"+codeCli+"'";
		
		Cursor cur=db.conn.rawQuery(query, null);
		if (cur.moveToNext()){
			int c = cur.getInt(0);
			cur.close(); 
			
			if(c > 0){
				result = true;
			}
		}
		if (cur!=null)
			cur.close();
		return result;

	}
	
	public ArrayList<String> get(){
		ArrayList<String> result = new ArrayList<String>();
		boolean bres=false;
		
		try {
			String query = "SELECT distinct "+FIELD_DATE
					+" FROM "+TABLENAME
					+" WHERE "+FIELD_DATE+">='"+Fonctions.getYYYYMMDD()+"' "
					+" ORDER BY "+FIELD_DATE+" ";

			Log.e("get CliToVisit","query=>"+query);
			
			Cursor cursor = db.conn.rawQuery(query, null);
			if(cursor != null && cursor.moveToFirst()){
				while(cursor.isAfterLast() == false)
				{
					bres=true;
					result.add(Fonctions.YYYYMMDD_to_dd_mm_yyyy(giveFld(cursor, FIELD_DATE)));
					cursor.moveToNext();
				}
				if (cursor!=null)
					cursor.close();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bres==false)
			result.add("19990101");
		return result;
	}
	//Todo
	public ArrayList<Bundle> getTodoClientFilters(String codeclient)
	{
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+  FIELD_CODECLI+
					"='"+codeclient+"'    ";
					//"='"+codeclient+"'  and  "+FIELD_DATE
					//+"='"+datejour+"'    ";
					
			Log.e("query","getTodoClientFilters"+query);
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if( Global.dbKDClientVu.TodoFait_Exist(codeclient,cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_EVT_ID)))==false)
				{
					Bundle cli=new Bundle();
					cli.putString(Global.dbCliToVisit.FIELD_CODECLI,cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_CODECLI)));
					cli.putString(Global.dbCliToVisit.FIELD_DATE, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_DATE)));
					cli.putString(Global.dbCliToVisit.FIELD_EVT_ID, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_EVT_ID)));
					cli.putString(Global.dbCliToVisit.FIELD_LBL, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_LBL)));
					cli.putString(Global.dbCliToVisit.FIELD_DESCRIPTION, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_DESCRIPTION)));
					cli.putString(Global.dbCliToVisit.FIELD_REF, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF)));
					cli.putString(Global.dbCliToVisit.FIELD_CODE_EVT, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_CODE_EVT)));
					
					String layoutcolor="vert";
					if (!Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF))).equals(""))
						layoutcolor="rouge";
					
					cli.putString("layoutcolor" 		,layoutcolor);
					
					materiel.add(cli);
				}
				 
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


	public String getTodoDescription(String codeclient, String item)
	{
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+  FIELD_CODECLI+
					"='"+codeclient+"' AND "+
					FIELD_LBL + " LIKE \"%" + item + "%\""
					;
			//"='"+codeclient+"'  and  "+FIELD_DATE
			//+"='"+datejour+"'    ";

			//TODO getdescription

			Log.e("query description",""+query);

			Cursor cur=db.conn.rawQuery(query, null);

			while (cur.moveToNext()) {

				Log.e("cursor description", "cursor description " + cur.getColumnIndex(Global.dbCliToVisit.FIELD_DESCRIPTION));

				String description = cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_DESCRIPTION));

				Log.e("description", ""+description);

				return description;

			}

			/*while (cur.moveToNext())
			{
				if( Global.dbKDClientVu.TodoFait_Exist(codeclient,cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_EVT_ID)))==false)
				{
					Bundle cli=new Bundle();
					cli.putString(Global.dbCliToVisit.FIELD_CODECLI,cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_CODECLI)));
					cli.putString(Global.dbCliToVisit.FIELD_DATE, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_DATE)));
					cli.putString(Global.dbCliToVisit.FIELD_EVT_ID, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_EVT_ID)));
					cli.putString(Global.dbCliToVisit.FIELD_LBL, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_LBL)));
					cli.putString(Global.dbCliToVisit.FIELD_DESCRIPTION, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_DESCRIPTION)));
					cli.putString(Global.dbCliToVisit.FIELD_REF, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF)));
					cli.putString(Global.dbCliToVisit.FIELD_CODE_EVT, cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_CODE_EVT)));

					String layoutcolor="vert";
					if (!Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF))).equals(""))
						layoutcolor="rouge";

					cli.putString("layoutcolor" 		,layoutcolor);

					materiel.add(cli);
				}

			} */
			if (cur!=null) {
				cur.close();
			}

		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			Log.e("error",""+err);
		}

		return null;
	}
	
	public int  Todo_countClient(String codeclient)
	{
		int icount = 0 ; 
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+  FIELD_CODECLI+
					
					"='"+codeclient+"'    ";
					//"='"+codeclient+"'  and  "+FIELD_DATE
					//+"='"+datejour+"'    ";
					
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if( Global.dbKDClientVu.TodoFait_Exist(codeclient,cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_EVT_ID)))==true)
				{
				
				}
				else
				{
				//	if(!Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF))).equals(""))
					{
						
					}
					//else
						icount++;					
				}
			}
			if (cur!=null)
				cur.close();
			return icount;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return icount;
	}
	public boolean   Bool_RefTodoClient(String codeclient,ArrayList<String> Valueget)
	{
		String stref = "" ; 
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+  FIELD_CODECLI+
					"='"+codeclient+"'    ";
					//"='"+codeclient+"'  and  "+FIELD_DATE
					//+"='"+datejour+"'    ";
					
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Valueget.add(Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF))));
				Valueget.add(Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_CODE_EVT))));
				Valueget.add(Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_EVT_ID))));
				
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
	 
	public String  RefTodoClient(String codeclient)
	{
		String stref = "" ; 
		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+  FIELD_CODECLI+
					"='"+codeclient+"'    ";
					//"='"+codeclient+"'  and  "+FIELD_DATE
					//+"='"+datejour+"'    ";
					
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				
				stref=Fonctions.GetStringDanem(cur.getString(cur.getColumnIndex(Global.dbCliToVisit.FIELD_REF)));
				if(!Fonctions.GetStringDanem(stref).equals("")	)
					break;
			}
			if (cur!=null)
				cur.close();
			return stref;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return stref;
	}
	
	

}
