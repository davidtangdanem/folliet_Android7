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
public class dbKD85Tempo extends dbKD{

	public final static int KD_TYPE= 85;
	public final static String FIELD_LIGNECDE_PROCODE 		= fld_kd_pro_code;


	MyDB db;
	public dbKD85Tempo(MyDB _db)
	{
		super();
		db=_db;	
	}

	static public class structLinCde
	{
		public structLinCde()
		{	
			PROCODE 		="";

		}

		public String PROCODE 	;
	}


	/**
	 * charge un article du panier de la cde numcde
	 * @author Marc VOUAUX
	 * @param ent
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public boolean load(String codeart,StringBuffer stBuf)
	{

       boolean bres= false;
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_LIGNECDE_PROCODE+"="+
				"'"+codeart+"'";


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
	public String get(String codeart)
	{

		String st= "0";
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_LIGNECDE_PROCODE+"="+
				"'"+codeart+"'";


		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext() )
		{
			st="1";

		}
		else{
			cur.close();
			return st;
		}

		return st;
	}


	public boolean save(String codeart, StringBuffer stBuf)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME_TEMP2+
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+
					" and "+
					this.FIELD_LIGNECDE_PROCODE+"="+
					"'"+codeart+"'";


			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext() )
			{


				query="UPDATE "+TABLENAME_TEMP2+
						" set "+
						this.FIELD_LIGNECDE_PROCODE+"="+
						"'"+codeart+"'"+

						" where "+
						fld_kd_dat_type+"="+KD_TYPE+
						" and "+
						this.FIELD_LIGNECDE_PROCODE+"="+
						"'"+codeart+"'";


				db.conn.execSQL(query);

			}
			else		  
			{	  		
				query="INSERT INTO " + TABLENAME_TEMP2 +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_LIGNECDE_PROCODE 	+""+

						") VALUES ("+
						String.valueOf(KD_TYPE)+","+
						"'"+codeart+"'"+

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


	public boolean delete(String codeart,StringBuffer err)
	{
		try
		{



			String query="DELETE from "+
					TABLENAME_TEMP2+		
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+" and "+

					this.FIELD_LIGNECDE_PROCODE+
					"='"+codeart+"'";


			db.conn.execSQL(query);

			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
		}
		return false;
	}
	


	/**
	 * Efface les types lignes
	 * @author Marc VOUAUX
	 * @param type
	 * @param table
	 * @return
	 */
	public boolean clear(StringBuilder err)
	{
		try
		{
			String table=TABLENAME;

			super.clear(db, KD_TYPE, table, err);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;
		}
	}

	
	 
 
}
