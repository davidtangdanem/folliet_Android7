package com.menadinteractive.segafredo.db;

import android.database.Cursor;

import com.menadinteractive.folliet2016.Debug;
import com.menadinteractive.segafredo.communs.Fonctions;


public class dbKD100Visite  extends dbKD{
	public static final int KD_TYPE = 100;
	public final String FIELD_CODECLI = fld_kd_cli_code;
	public final String FIELD_CODEREP = fld_kd_dat_idx01;
	public final String FIELD_JOUR_PASSAGE = fld_kd_dat_data01; // "LUNDI", "MARDI"...
	public final String FIELD_DEJA_VU = fld_kd_dat_data02; // déjà vu = "1"
	public final String FIELD_EVT_ID = fld_kd_dat_data03; // 
	
	public final String fIELD_FLAG = fld_kd_dat_data08; //1 = modification/insertion; 2=delete; 0=pas de mise à jour
	
	MyDB db;
	public dbKD100Visite(MyDB _db)
	{
		super();
		db=_db;	
	}
	
	static public class structVisite {
		public structVisite()
		{	
			super();
			CODEREP = "";      
			CODECLI = "";       
			JOUR_PASSAGE  = "";       
			DEJA_VU = "";
			FIELD_EVT_ID="";
			FLAG = "";         
		}
		
		public String CODEREP ;
		public String CODECLI; 
		public String JOUR_PASSAGE ;
		public String DEJA_VU ;
		public String FIELD_EVT_ID;
		public String FLAG ;
	}
	
	public int Count()
	{

		try
		{
			String query="select count(*) from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i =0;
				i=cur.getInt(0);
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
	
	public int countModified()
	{

		try
		{
			String query="select count(*) from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'"+
					" and "+
					this.fIELD_FLAG+"<>'"+ KDSYNCHRO_RESET+  "'" ;
					
						

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i =0;
				i=cur.getInt(0);
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
	
	
	public boolean _isDejaVisite(String stcodeclient, String jourPassage){
		boolean result = false;
		structVisite ent = new structVisite();
		if(_load(ent, stcodeclient, jourPassage, new StringBuffer())){
			Debug.Log("TAG4", "deja_vu : "+ent.DEJA_VU);
			if(!Fonctions.isEmptyOrNull(ent.DEJA_VU) && ent.DEJA_VU.equals("1"))
				result = true;
		}
		
		
		return result;
	}
	public boolean isDejaVisiteId(String stcodeclient, String jourPassage,String evt_id){
		boolean result = false;
		structVisite ent = new structVisite();
		if(load_evt_id(ent, stcodeclient, jourPassage,evt_id, new StringBuffer())){
			Debug.Log("TAG4", "deja_vu : "+ent.DEJA_VU);
			if(!Fonctions.isEmptyOrNull(ent.DEJA_VU) && ent.DEJA_VU.equals("1"))
				result = true;
		}
		
		
		return result;
	}
	
	
	public boolean _load(structVisite ent,String stcodeclient, String jourPassage, StringBuffer stBuf)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				+" and "+this.FIELD_JOUR_PASSAGE+"='"+jourPassage+"'";
		
//		Debug.Log("TAG4", "query : "+query);
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			ent.CODEREP =this.giveFld(cur,this.FIELD_CODEREP );
			ent.CODECLI =this.giveFld(cur,this.FIELD_CODECLI );
			ent.JOUR_PASSAGE =this.giveFld(cur,this.FIELD_JOUR_PASSAGE );
			ent.DEJA_VU =this.giveFld(cur,this.FIELD_DEJA_VU );
			ent.FLAG=this.giveFld(cur,this.fIELD_FLAG);
			
			cur.close();
		}
		else
		{
			if (cur!=null)
				cur.close();
			return false;
		}
		return true;
	}
	public boolean load_evt_id(structVisite ent,String stcodeclient, String jourPassage,String evt_id, StringBuffer stBuf)
	{

		String query="SELECT DISTINCT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				+" and "+this.FIELD_JOUR_PASSAGE+"='"+jourPassage+"'"
				+" and "+this.FIELD_EVT_ID+"='"+evt_id+"'";
		
		
//		Debug.Log("TAG4", "query : "+query);
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			ent.CODEREP =this.giveFld(cur,this.FIELD_CODEREP );
			ent.CODECLI =this.giveFld(cur,this.FIELD_CODECLI );
			ent.JOUR_PASSAGE =this.giveFld(cur,this.FIELD_JOUR_PASSAGE );
			ent.DEJA_VU =this.giveFld(cur,this.FIELD_DEJA_VU );
			ent.FIELD_EVT_ID =this.giveFld(cur,this.FIELD_EVT_ID );
			ent.FLAG=this.giveFld(cur,this.fIELD_FLAG);
			
			cur.close();
		}
		else
		{
			if (cur!=null)
				cur.close();
			return false;
		}
		return true;
	}
	
	public boolean save_evt_id(structVisite ent,String stcodeclient,String evt_id,StringBuffer stBuf)
	{
		return _save_evt_id(ent,stcodeclient,evt_id,KDSYNCHRO_RESET, stBuf);
	}
	
	
	public boolean _save_evt_id(structVisite ent,String stcodeclient,String evt_id,String flag, StringBuffer stBuf)
	{
		try
		{

			if(ent != null && ent.CODEREP != null /*&& !ent.CODEREP.contains("test") MV*/){
				String query="INSERT INTO " + TABLENAME +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_CODEREP+","+
						this.FIELD_CODECLI+","+ 
						this.FIELD_JOUR_PASSAGE+","+   
						this.FIELD_DEJA_VU+","+ 
						this.FIELD_EVT_ID+","+ 
						this.fIELD_FLAG+""+

	  		") VALUES ("+
	  		String.valueOf(KD_TYPE)+","+
	  		"'"+ent.CODEREP+"',"+
	  		"'"+stcodeclient+"',"+
	  		"'"+ent.JOUR_PASSAGE+"',"+
	  		"'"+ent.DEJA_VU+"',"+
	  		"'"+ent.FIELD_EVT_ID+"',"+
	  		"'"+flag +"')";
				db.conn.execSQL(query);
			}
		}
		catch(Exception ex)
		{
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			Debug.StackTrace(ex);
			return false;
		}

		return true;
	}
	
	
	public Cursor _getVisites(String stcodeclient){
		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_CODECLI+"="+
				"'"+stcodeclient+"'";
		
		return db.conn.rawQuery (query,null);
	}
	public Cursor getVisitesEvt_id(String stcodeclient,String evt_id){
		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_CODECLI+"="+
				"'"+stcodeclient+"'"+
				" and "+
				this.FIELD_EVT_ID+"="+
				"'"+evt_id+"'";
		
		return db.conn.rawQuery (query,null);
	}
	
	public boolean _delete(String Codeclient,StringBuffer err)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					dbKD.fld_kd_cli_code+
					"='"+Codeclient+"'"+
					" and "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' ";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
		}
		return false;
	}
	public boolean delete_evt_id(String Codeclient,String evt_id,StringBuffer err)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					dbKD.fld_kd_cli_code+
					"='"+Codeclient+"'"+
					" and "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' "+
					" and "+
					dbKD.fld_kd_dat_data03+
					"='"+evt_id+"' ";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
		}
		return false;
	}
	public boolean resetFlag(StringBuffer err)
	{
		try
		{
			String query="UPDATE "+
					TABLENAME+		
					" SET "+
					dbKD.fld_kd_dat_data08+
					"='"+KDSYNCHRO_RESET+"'"+
					" where "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' ";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
		}
		return false;
	}
	
	public boolean clear(String contrainte, StringBuilder err){
		return clear(KD_TYPE, TABLENAME, contrainte, err);
	}
	public boolean clear( int type,String table, String contrainte, StringBuilder err)
	{
		try
		{
			String query="delete from "+
					table+
					" where "+
					fld_kd_dat_type+"="+type
					+contrainte;


			Debug.Log("TAG4", "DELETE : "+query);
			return db.execSQL(query, err); 



		}
		catch(Exception ex)
		{
			Debug.StackTrace(ex);
			err.append(ex.getMessage());
			return false;
		}	
	}
	

}
