package com.menadinteractive.segafredo.db;

import android.database.Cursor;
import android.util.Log;

import com.menadinteractive.segafredo.communs.Fonctions;

public class dbKD733Habitudes extends dbKD {

	/**
	 * Initialisation variable
	 */
	public final static int KD_TYPE = 733;
	public final static String FIELD_DATATYPE 	= fld_kd_dat_type;
	public final static String FIELD_CODVRP 	= fld_kd_dat_idx01;
	public final static String FIELD_COD_CLT =fld_kd_cli_code;
	public final static String FIELD_COD_ART =fld_kd_pro_code;
	public final static String FIELD_QTE =fld_kd_dat_data01;
	public final static String FIELD_DATE =fld_kd_dat_data02;
	public final static String FIELD_TOURNEE =fld_kd_dat_data03;

	/*public final String FIELD_DER_PRX_VEN =fld_kd_dat_data01;
	public final String FIELD_DER_QTE_LIV =fld_kd_dat_data02;
	public final String FIELD_DER_DAT_PIE =fld_kd_dat_data03;
	public final String FIELD_QTE_12M =fld_kd_dat_data04;
	public final String FIELD_NB_FAC_TOT_CLI =fld_kd_dat_data05;
	public final String FIELD_QTE_MOY_FAC =fld_kd_dat_data06;
	public final String FIELD_NB_FAC_TOT_ART =fld_kd_dat_data07;
	public final String FIELD_FREQUENCE =fld_kd_dat_data08;
	public final String FIELD_STK_CLT =fld_kd_dat_data09;*/


	MyDB db;
	public dbKD733Habitudes(MyDB _db)
	{
		db=_db;
	}
 
	 
	public boolean clear(StringBuilder err)
	{
		try
		{
			String query="delete from "+TABLENAME+
					" where "+fld_kd_dat_type+"="+KD_TYPE;
			//db.conn.delete(TABLENAME, null, null);
			db.execSQL(query, err);
 
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
			String query="select count(*) from "+TABLENAME+
					" where "+fld_kd_dat_type+"="+KD_TYPE;
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
	 	
	public boolean save(String codeclient,String codevrp,String codeart, String qte,String codetournee)
	{
		try
		{
		 
			String query="SELECT * FROM "+
					TABLENAME+
					" where "+
					FIELD_COD_CLT+"="+
					"'"+codeclient+"' "+
					" and "+
					FIELD_COD_ART+"="+
					"'"+codeart+"' "+
					" and "+
					FIELD_TOURNEE+"="+
					"'"+codetournee+"' "+
					" and "+
					FIELD_DATATYPE+"="+KD_TYPE;
			
			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{
				query="UPDATE "+TABLENAME+
						" set "+
						FIELD_QTE+"="+
						"'"+MyDB.controlFld(qte )+"' "+
						
						" where "+
						FIELD_COD_CLT+"="+
						"'"+codeclient+"' "+
						" and "+
						FIELD_COD_ART+"="+
						"'"+codeart+"' "+
						" and "+
						FIELD_DATE+"="+
						"'"+ Fonctions.getYYYYMMDD() +"' "+
						" and "+
						FIELD_TOURNEE+"="+
						"'"+codetournee+"' "+
						" and "+
					FIELD_DATATYPE+"="+KD_TYPE;

				;	  		

				db.conn.execSQL(query);
			}
			else
			{
				query="INSERT INTO "+TABLENAME+
						"("+
						FIELD_DATATYPE+","+
						FIELD_COD_CLT+","+
						FIELD_COD_ART+","+
						FIELD_QTE+","+
						FIELD_DATE+","+
						FIELD_TOURNEE+","+
						FIELD_CODVRP+")"+
						" values "+
						"("+
						KD_TYPE+","+
						"'"+codeclient+"',"+
						"'"+codeart+"',"+
						"'"+qte+"',"+
						"'"+Fonctions.getYYYYMMDD() +"',"+
						"'"+codetournee +"',"+
						"'"+codevrp+"'"+
						")";
				 
				db.conn.execSQL(query);
			}
			if (cur!=null)
				cur.close();	
		}
		catch(Exception ex)
		{
			Log.d("TAG", ex.getLocalizedMessage());
			return false;
		}

		return true;
	}
	 
}
