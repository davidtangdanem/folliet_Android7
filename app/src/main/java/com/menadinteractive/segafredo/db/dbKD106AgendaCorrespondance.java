package com.menadinteractive.segafredo.db;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.CalendarContract.Events;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.plugins.Espresso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Table pour garder les RDV de l'agenda dans la base de l'appliction, et pouvoir recuperer les agenda AVEC les données clients
public class dbKD106AgendaCorrespondance extends dbKD
{

	public static final int KD_TYPE = 106;
	public static final String FIELD_SOC_CODE = fld_kd_soc_code;
	public static final String FIELD_CODEREP = fld_kd_dat_idx01;
	public static final String FIELD_ID = fld_kd_dat_idx02;
	public static final String FIELD_CODECLI = fld_kd_cli_code;
	public static final String FIELD_OBJET = fld_kd_dat_data01;
	public static final String FIELD_DESCRIPTION = fld_kd_dat_data02;
	public static final String FIELD_DTDEBUT = fld_kd_dat_data03;
	public static final String FIELD_DTFIN = fld_kd_dat_data04;
	public static final String FIELD_DURATION = fld_kd_dat_data05;
	public static final String FIELD_FLAG = fld_kd_dat_data08; //1 = modifi�, 2=delete
	public static final String FIELD_CODE_EVT = fld_kd_dat_data10; //enregistré dans EVENT_LOCATION sur l'agenda

	MyDB db;

	public dbKD106AgendaCorrespondance(MyDB _db)
	{
		super();
		db = _db;
	}

	static public class passePlat
	{
		public static final String FIELD_FLAG = null;

		public passePlat()
		{
			FIELD_SOC_CODE = "";
			FIELD_ID = "";
			FIELD_CODECLI = "";
			FIELD_OBJET = "";
			FIELD_DESCRIPTION = "";
			FIELD_DTDEBUT = "";
			FIELD_DTFIN = "";
			FIELD_DURATION = "";
			FIELD_CODEREP = "";
			FIELD_CODE_EVT = "";
		}

		public String FIELD_SOC_CODE;
		public String FIELD_ID;
		public String FIELD_CODEREP;
		public String FIELD_CODECLI;
		public String FIELD_OBJET;
		public String FIELD_DESCRIPTION;
		public String FIELD_DTDEBUT;
		public String FIELD_DTFIN;
		public String FIELD_DURATION;
		public String FIELD_CODE_EVT;


	}

	public int Count()
	{

		try
		{
			String query = "select count(*) from " + TABLENAME + " where " +
					fld_kd_dat_type + "='" + KD_TYPE + "'";

			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i = cur.getInt(0);
				if (cur != null)
					cur.close();//MV 26/03/2015
				return i;
			}

			if (cur != null)
				cur.close();//MV 26/03/2015
			return 0;
		} catch (Exception ex)
		{
			return -1;
		}

	}

	public int countModified()
	{

		try
		{
			String query = "select count(*) from " + TABLENAME + " where " +
					fld_kd_dat_type + "='" + KD_TYPE + "'" +
					" and " +
					this.FIELD_FLAG + "<>'" + KDSYNCHRO_RESET + "'";


			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i = cur.getInt(0);
				if (cur != null)
					cur.close();//MV 26/03/2015
				return i;
			}

			if (cur != null)
				cur.close();//MV 26/03/2015
			return 0;
		} catch (Exception ex)
		{
			return -1;
		}

	}


	public boolean save(passePlat ent)
	{
		try
		{
			//on efface l'existant et on sauve
			delete(ent.FIELD_OBJET, ent.FIELD_DTDEBUT, ent.FIELD_DESCRIPTION);

			String query = "INSERT INTO " + TABLENAME + " (" +
					dbKD.fld_kd_dat_type + "," +
					this.FIELD_SOC_CODE + "," +
					this.FIELD_CODECLI + "," +
					this.FIELD_CODEREP + "," +
					this.FIELD_OBJET + "," +
					this.FIELD_DESCRIPTION + "," +
					this.FIELD_DTDEBUT + "," +
					this.FIELD_DTFIN + "," +
					this.FIELD_ID + "," +
					this.FIELD_FLAG + "," +
					this.FIELD_CODE_EVT + "" +

					") VALUES (" +
					String.valueOf(KD_TYPE) + "," +
					"'" + ent.FIELD_SOC_CODE + "'," +
					"'" + ent.FIELD_CODECLI + "'," +
					"'" + ent.FIELD_CODEREP + "'," +
					"'" + MyDB.controlFld(ent.FIELD_OBJET) + "'," +
					"'" + MyDB.controlFld(ent.FIELD_DESCRIPTION) + "'," +
					"'" + ent.FIELD_DTDEBUT + "'," +
					"'" + ent.FIELD_DTFIN + "'," +
					"'" + ent.FIELD_ID + "'," +
					"'" + KDSYNCHRO_UPDATE + "'," +
					"'" + ent.FIELD_CODE_EVT + "')";

			db.conn.execSQL(query);

		} catch (Exception ex)
		{
			Global.lastErrorMessage = (ex.getMessage());
			return false;
		}

		return true;
	}

	public boolean deleteAll()
	{
		try
		{
			String query = "DELETE from " +
					TABLENAME +
					" where " +

					dbKD.fld_kd_dat_type +
					"='" + KD_TYPE + "' ";

			db.conn.execSQL(query);
			return true;
		} catch (Exception ex)
		{
			Global.lastErrorMessage = (ex.getMessage());
		}
		return false;
	}

	public boolean delete(String id)
	{
		try
		{
			String query = "DELETE from " +
					TABLENAME +
					" where " +
					FIELD_ID +
					"='" + id + "'" +

					" and " +
					dbKD.fld_kd_dat_type +
					"='" + KD_TYPE + "' ";
			db.conn.execSQL(query);
			return true;
		} catch (Exception ex)
		{
			Global.lastErrorMessage = (ex.getMessage());
		}
		return false;
	}

	public boolean delete(String objet, String dateDeb, String description)
	{
		try
		{
			String query = "DELETE from " +
					TABLENAME +
					" where " +
					FIELD_OBJET +
					"='" + MyDB.controlFld(objet) + "' and " +
					FIELD_DTDEBUT +
					"='" + dateDeb + "' and " +
					FIELD_DESCRIPTION +
					"='" + MyDB.controlFld(description) + "'" +

					" and " +
					dbKD.fld_kd_dat_type +
					"='" + KD_TYPE + "' ";
			db.conn.execSQL(query);

			return true;
		} catch (Exception ex)
		{
			Global.lastErrorMessage = (ex.getMessage());
		}
		return false;
	}

	//Fonction de synchr de la base par rapport à l'agenda
	//A NOTER: table local uniquement, utilsée dans pour l'affichage dans AgendaListActivity et AgendaViewActivity
	public Cursor Sync106(Context c)
	{
		//Desactivation complete de la fonction
		return null ;
		/*deleteAll();
		Cursor result = null;
		int iCptInsert = 0;

		try
		{
			String[] PROJECTION = new String[]{
					BaseColumns._ID,
					Events.TITLE,
					Events.DESCRIPTION,
					Events.DTSTART,
					Events.DTEND,
					Events.DURATION,
					Events.EVENT_LOCATION
			};
			String where = Events.DELETED + " = '0'  and  (" + Events.TITLE + " LIKE '%FLT[%' or " + Events.TITLE + " LIKE '%FLS[%' )";
			//String where = Events.DELETED + " = '0'  and  (" + Events.TITLE + " LIKE '%FLS[%' )";

			result = c.getContentResolver().query(Events.CONTENT_URI, PROJECTION, where, null, Events.DTSTART + " DESC");

			String stDateDuJour = Fonctions.getYYYYMMDDhhmmss();
			if (result != null && result.moveToFirst())
			{
				while (result.isAfterLast() == false)
				{
					//recuperation uniquement des rdv à venir
					DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
					String stDate = result.getString(result.getColumnIndex(Events.DTSTART));
					stDate = dateFormat.format(Fonctions.convertToLong(stDate));
					stDate = stDate.replace(" ", "");
					//desactivation de la non synchro des rdv passés

					//	Debug.Log(result.getString(result.getColumnIndex(CalendarContract.Events.TITLE)));
					passePlat pp = new passePlat();
					String title = result.getString(result.getColumnIndex(Events.TITLE));
					pp.FIELD_OBJET = title;
					pp.FIELD_DESCRIPTION = result.getString(result.getColumnIndex(Events.DESCRIPTION));
					//dbg
					//pp.FIELD_DESCRIPTION="cbaf" ;
					pp.FIELD_CODECLI = Fonctions.GiveFld(title, 1, "[", true);
					pp.FIELD_CODECLI = Fonctions.GiveFld(pp.FIELD_CODECLI, 0, "]", true);
					pp.FIELD_ID = result.getString(result.getColumnIndex(BaseColumns._ID));
					pp.FIELD_DTDEBUT = result.getString(result.getColumnIndex(Events.DTSTART));
					pp.FIELD_DTFIN = result.getString(result.getColumnIndex(Events.DTEND));
					pp.FIELD_DURATION = result.getString(result.getColumnIndex(Events.DURATION));
					pp.FIELD_CODEREP = Preferences.getValue(c, Espresso.LOGIN, "0");

					pp.FIELD_DTDEBUT = dateFormat.format(Fonctions.convertToLong(pp.FIELD_DTDEBUT));
					pp.FIELD_DTFIN = dateFormat.format(Fonctions.convertToLong(pp.FIELD_DTFIN));
					pp.FIELD_SOC_CODE = "1";
					pp.FIELD_CODE_EVT = result.getString(result.getColumnIndex(Events.EVENT_LOCATION));

					save(pp);
					iCptInsert++;
					result.moveToNext();
				}
				result.close();
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String stCptInsert = iCptInsert + "";
		return result;*/
	}

	//Pour recup du premier rdv du client courant
	public passePlat getFistRdvForCli(String codecli)
	{
		passePlat rr = new passePlat();

		try
		{
			String query="";
			query="select *  FROM "+
					TABLENAME +" WHERE "+FIELD_CODECLI+"='"+codecli+"' and "+fld_kd_dat_type + "='" + KD_TYPE + "'"+   //FIELD_DTDEBUT+" >= '"+ Fonctions.getYYYYMMDD()+"' "+
					"ORDER BY "+FIELD_DTDEBUT;


			ArrayList<String> liste=new ArrayList<String>();
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				rr.FIELD_DTDEBUT = giveFld(cur,this.FIELD_DTDEBUT) ;
				rr.FIELD_DTFIN= giveFld(cur,this.FIELD_DTFIN) ;

				rr.FIELD_CODECLI = giveFld(cur,this.FIELD_CODECLI) ;
				rr.FIELD_CODE_EVT = giveFld(cur,this.FIELD_CODE_EVT) ;

			}

			if (cur!=null)
				cur.close();
			return rr;

		}
		catch ( Exception e)
		{

		}
		return null ;
	}
	//Pour recup du premier rdv du client courant
	public passePlat getRdv(String eventid)
	{
		passePlat rr = new passePlat();

		try
		{
			String query="";
			query="select *  FROM "+
					TABLENAME +" WHERE "+FIELD_ID+"='"+eventid+"' and "+fld_kd_dat_type + "='" + KD_TYPE + "'"+   //FIELD_DTDEBUT+" >= '"+ Fonctions.getYYYYMMDD()+"' "+
					"ORDER BY "+FIELD_DTDEBUT;


			ArrayList<String> liste=new ArrayList<String>();
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				rr.FIELD_DTDEBUT = giveFld(cur,this.FIELD_DTDEBUT) ;
				rr.FIELD_DTFIN= giveFld(cur,this.FIELD_DTFIN) ;
				rr.FIELD_CODECLI= giveFld(cur,this.FIELD_CODECLI) ;
				rr.FIELD_OBJET= giveFld(cur,this.FIELD_OBJET) ;
				rr.FIELD_DESCRIPTION= giveFld(cur,this.FIELD_DESCRIPTION) ;
				rr.FIELD_DURATION= giveFld(cur,this.FIELD_DURATION) ;
				rr.FIELD_CODE_EVT= giveFld(cur,this.FIELD_CODE_EVT) ;
			}

			if (cur!=null)
				cur.close();
			return rr;

		}
		catch ( Exception e)
		{

		}
		return null ;
	}
}
