package com.menadinteractive.segafredo.db;

import android.database.Cursor;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;

import java.util.ArrayList;
import java.util.List;


/**
 * Vue ligne de cde dans la table KD
 * @author Marc VOUAUX
 *
 */
public class dbKD94Marchandise extends dbKD{

	public final static int KD_TYPE= 94;

	public final static String FIELD_MARCH_REPCODE 		= fld_kd_dat_idx01;
	public final static String FIELD_MARCH_DATELIV 		= fld_kd_dat_idx02;
	public final static String FIELD_MARCH_NUMCDE     	= fld_kd_dat_idx03;
	public final static String FIELD_MARCH_NUMCDE2     	= fld_kd_dat_idx04;
	public final static String FIELD_MARCH_DATERECU     = fld_kd_dat_idx05;
	public final static String FIELD_MARCH_ETAT    		= fld_kd_dat_idx06;



	MyDB db;
	public dbKD94Marchandise(MyDB _db)
	{
		super();
		db=_db;	
	}

	static public class structMarchandise
	{
		public structMarchandise()
		{
			REPCODE 		="";
			DATELIV 		="";
			NUMCDE 		="";
			NUMCDE2 		="";
			DATERECU 		="";
			ETAT ="";

		}

		public String REPCODE 	;
		public String DATELIV 	;
		public String NUMCDE 	;
		public String NUMCDE2 	;
		public String DATERECU 	;
		public String ETAT 	;




	}

	public int Count()
	{

		try
		{
			String table=TABLENAME;


			String query="select count(*) from "+table+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'";



			Cursor cur=db.conn.rawQuery(query, null);
			int result = 0;
			if (cur.moveToNext())
			{
				int i =cur.getInt(0);
			if (cur!=null)
				cur.close();
				result = i;

			}
			if (cur!=null)
				cur.close();
			return result;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	public int Count_Sent()
	{

		try
		{
			String table=TABLENAME;


			String query="select count(*) from "+table+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"' and "+fld_kd_dat_idx06+"='0'";



			Cursor cur=db.conn.rawQuery(query, null);
			int result = 0;
			if (cur.moveToNext())
			{
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				result = i;

			}
			if (cur!=null)
				cur.close();
			return result;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	public int Count_NoSent()
	{

		try
		{
			String table=TABLENAME;


			String query="select count(*) from "+table+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"' and "+fld_kd_dat_idx06+"='0'";



			Cursor cur=db.conn.rawQuery(query, null);
			int result = 0;
			if (cur.moveToNext())
			{
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				result = i;

			}
			if (cur!=null)
				cur.close();
			return result;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}
	public boolean load(String dateliv,String numcde )
	{

		boolean bres= false;
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_MARCH_DATELIV+"="+
				"'"+dateliv+"'"+
				" and "+
				this.FIELD_MARCH_NUMCDE+"="+
				"'"+numcde+"'";


		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext() )
		{
			cur.close();
			bres = true;
		}
		else{
			cur.close();
			return false;
		}

		return bres;
	}
	public boolean MarchandiseRecu(String dateliv,String numcde )
	{

		boolean bres= false;
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_MARCH_DATELIV+"="+
				"'"+dateliv+"'"+
				" and "+
				this.FIELD_MARCH_NUMCDE+"="+
				"'"+numcde+"'";


		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext() )
		{
			cur.close();
			bres = true;
		}
		else{
			cur.close();
			return false;
		}

		return bres;
	}
	public String get(String dateliv,String numcde )
	{

		String st= "";
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_MARCH_DATELIV+"="+
				"'"+dateliv+"'"+
				" and "+
				this.FIELD_MARCH_NUMCDE+"="+
				"'"+numcde+"'";


		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext() )
		{
			st =giveFld(cur, FIELD_MARCH_DATERECU);

		}
		else{
			cur.close();
			return st;
		}

		return st;
	}

	public String getEtat(String dateliv,String numcde )
	{

		String st= "";
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_MARCH_DATELIV+"="+
				"'"+dateliv+"'"+
				" and "+
				this.FIELD_MARCH_NUMCDE+"="+
				"'"+numcde+"'";


		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext() )
		{
			st =giveFld(cur, FIELD_MARCH_ETAT);

		}
		else{
			cur.close();
			return st;
		}

		return st;
	}






	/*public boolean clear(boolean inTemp,StringBuilder err)
	{
		try
		{
			String table=TABLENAME;
			if (inTemp)
				table=TABLENAME_TEMP2;
			super.clear(db, KD_TYPE, table, err);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;
		}
	}*/

	public boolean UpdateFlag(StringBuilder err)
	{
		try
		{
			String query="UPDATE "+TABLENAME_TEMP2+
					" set "+
					this.FIELD_MARCH_ETAT+"="+
					"'1' "+
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+
					" and "+
					this.FIELD_MARCH_ETAT+"="+
					"'0' ";
			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;
		}
	}

	public boolean UpdateFlagEnvoi(StringBuilder err)
	{
		try
		{
			String query="UPDATE "+TABLENAME_TEMP2+
					" set "+
					this.FIELD_MARCH_ETAT+"="+
					"'E' "+
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+
					" and "+
					this.FIELD_MARCH_ETAT+"="+
					"'0' ";
			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;
		}
	}

	public boolean save(String coderep,String dateliv,String numcde ,String daterecu,String numcde2, StringBuffer stBuf)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME_TEMP2+
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+
					" and "+
					this.FIELD_MARCH_DATELIV+"="+
					"'"+dateliv+"'"+
					" and "+
					this.FIELD_MARCH_NUMCDE+"="+
					"'"+numcde+"'";


			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{


				query="UPDATE "+TABLENAME_TEMP2+
						" set "+
						this.FIELD_MARCH_DATERECU+"="+
						"'"+daterecu+"' ,"+
						this.FIELD_MARCH_NUMCDE2+"="+
						"'"+numcde2+"' ,"+
						this.FIELD_MARCH_ETAT+"="+
						"'0' "+

						" where "+
						fld_kd_dat_type+"="+KD_TYPE+
						" and "+
						this.FIELD_MARCH_DATELIV+"="+
						"'"+dateliv+"'"+
						" and "+
						this.FIELD_MARCH_NUMCDE+"="+
						"'"+numcde+"'";


				db.conn.execSQL(query);

			}
			else
			{
				query="INSERT INTO " + TABLENAME_TEMP2 +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_MARCH_REPCODE 	+","+
						this.FIELD_MARCH_DATELIV 	+","+
						this.FIELD_MARCH_NUMCDE 	+","+
						this.FIELD_MARCH_NUMCDE2 	+","+
						this.FIELD_MARCH_DATERECU 	+","+
						this.FIELD_MARCH_ETAT 		+""+





						") VALUES ("+
						String.valueOf(KD_TYPE)+","+
						"'"+coderep+"',"+
						"'"+dateliv+"',"+
						"'"+numcde+"',"+
						"'"+numcde2+"',"+
						"'"+daterecu+"',"+
						"'0'"+

						")";

				db.conn.execSQL(query);

			}
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





}
