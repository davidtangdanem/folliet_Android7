package com.menadinteractive.segafredo.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CalendarContract.Events;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.plugins.Espresso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

//Tof: 12/03/2018: pour garder une trace des agendas supprimés, et ne plus les re-afficher

public class dbKD105AgendaSrvSupp extends dbKD{

	public final int KD_TYPE = 105;
	public final String FIELD_SOC_CODE      = fld_kd_soc_code;
	public final String FIELD_CODEREP     	= fld_kd_dat_idx01;
	public final String FIELD_CODECLI      	= fld_kd_cli_code;
	public final String FIELD_OBJET   		= fld_kd_dat_data01;
	public final String FIELD_DTDEBUT       = fld_kd_dat_data03;
	public final String FIELD_FLAGENVOI   	= fld_kd_dat_data09; //1 = modifi�, 2=delete

	MyDB db;
	public dbKD105AgendaSrvSupp(MyDB _db)
	{
		super();
		db=_db;
	}

	static public class passePlat {
		public static final String FIELD_FLAG = null;
		public passePlat()
		{
			FIELD_SOC_CODE ="";
			FIELD_CODECLI      	="";
			FIELD_OBJET   ="";
			FIELD_DTDEBUT       ="";
			FIELD_CODEREP="";
		}

		public String FIELD_SOC_CODE     ;
		public String FIELD_CODEREP     		;
		public String FIELD_CODECLI     ;
		public String FIELD_OBJET;
		public String FIELD_DTDEBUT ;

	}
	public int Count( boolean bSeulementAEnvoyer )
	{

		try
		{
			String query="select count(*) from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'";

			if ( bSeulementAEnvoyer == true )
				query += " AND ( "+FIELD_FLAGENVOI+" is null or  "+FIELD_FLAGENVOI+" ='')" ;
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i= cur.getInt(0);
				if (cur!=null)
					cur.close();//MV 26/03/2015
				return i;
			}

			if (cur!=null)
				cur.close();//MV 26/03/2015
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}


	public boolean save(passePlat ent)
	{
		try
		{
			//on efface l'existant et on sauve
			//delete(ent.FIELD_ID);

			String query="INSERT INTO " + TABLENAME +" ("+
					dbKD.fld_kd_dat_type+","+
					this.FIELD_SOC_CODE   		+","+
					this.FIELD_CODECLI   		+","+
					this.FIELD_CODEREP   		+","+
					this.FIELD_OBJET			  +","+
					this.FIELD_DTDEBUT    		+""+

	  		") VALUES ("+
	  		String.valueOf(KD_TYPE)+","+
	  		"'"+ent.FIELD_SOC_CODE+"',"+
	  		"'"+ent.FIELD_CODECLI+"',"+
	  		"'"+ent.FIELD_CODEREP+"',"+
	  		"'"+MyDB.controlFld(ent.FIELD_OBJET)+"',"+
	  		"'"+ent.FIELD_DTDEBUT+"')";

			db.conn.execSQL(query);

		}
		catch(Exception ex)
		{
			Global.lastErrorMessage=(ex.getMessage());
			return false;
		}

		return true;
	}

	public boolean deleteAll()
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+
					" where "+

					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' ";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			Global.lastErrorMessage=(ex.getMessage());
		}
		return false;
	}


	/*
	 * 	renvoi la date à partir de laquelle on renvoi les agenda
	 * j-7
	 * 
	 */
	String AgendaToKD_getDateFrom()
	{
		String date=Fonctions.getYYYYMMDD(-7);
		
		return date;
	}
	
	long Agenda_getFrom(String yyyymmdd)
	{
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			Date date = format.parse(yyyymmdd+" 00:00:00");
			long timestamp = date.getTime();
			
			return timestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}


	//Pour scenarion scenario_getagenda, suppression des données agenda
	public boolean clear(StringBuilder err)
	{
		try
		{
			String query="DELETE FROM "+TABLENAME + " WHERE "+dbKD.fld_kd_dat_type+"='"+KD_TYPE+"' ";
			//db.conn.delete(TABLENAME, null, null);
			db.execSQL(query, err);
			//db.execSQL(TABLE_CREATE,err);
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;
		}
		return true;
	}
	public void SaveRdvSupp(String title, String datedeb, Context c)
	{
		String codecli ="" ;
		String objet ="" ;
		int start = title.indexOf("[");
		int end = title.indexOf("]");
		if(start != -1 && end != -1)
			codecli = title.substring(start+1, end);
		if ( title.length() > end +1)
			objet = title.substring(end+1);

		passePlat ent = new passePlat();
		ent.FIELD_CODEREP = Preferences.getValue(c, Espresso.LOGIN, "0") ;
		ent.FIELD_CODECLI = codecli ;
		ent.FIELD_DTDEBUT = datedeb ;
		ent.FIELD_OBJET = objet ;
		save(ent);
	}
	//Attention, pas de control de l'objet (mal enregistré lors de la suppression)
	public boolean IsRecordExists(String codecli, String datedeb, String objet)
	{

		try
		{
			String query="select * from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'";
			query +=" AND "+FIELD_CODECLI+"='"+codecli+"' " ;
			query +=" AND "+FIELD_DTDEBUT+"='"+datedeb+"' " ;
			//pas de control de l'objet
			//query +=" AND "+FIELD_OBJET+"='"+objet+"' " ;

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				if (cur!=null)
					cur.close();//MV 26/03/2015
				return true;
			}

			if (cur!=null)
				cur.close();//MV 26/03/2015
			return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public String queryDeleteFromOnServer(Context c)
	{
		String del="delete from "+TABLENAME+
				" where "+FIELD_DTDEBUT+">='"+AgendaToKD_getDateFrom()+"'"+
				" and "+dbKD.fld_kd_dat_type+"="+KD_TYPE+
				" and "+FIELD_CODEREP+"='"+Preferences.getValue(c, Espresso.LOGIN, "0")+"'";
		return del;
	}
	public boolean resetFlag(StringBuffer err)
	{
		try
		{
			String query="UPDATE "+
					TABLENAME+
					" SET "+
					FIELD_FLAGENVOI+
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

}
