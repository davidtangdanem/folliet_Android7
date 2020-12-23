package com.menadinteractive.segafredo.db;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.plugins.Espresso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class dbKD104AgendaSrv extends dbKD{

	public final int KD_TYPE = 104;
	public final String FIELD_SOC_CODE      = fld_kd_soc_code;
	public final String FIELD_CODEREP     	= fld_kd_dat_idx01;
	public final String FIELD_ID     		= fld_kd_dat_idx02;
	public final String FIELD_CODECLI      	= fld_kd_cli_code;
	public final String FIELD_OBJET   		= fld_kd_dat_data01;
	public final String FIELD_DESCRIPTION   = fld_kd_dat_data02;
	public final String FIELD_DTDEBUT       = fld_kd_dat_data03;
	public final String FIELD_DTFIN         = fld_kd_dat_data04;
	public final String FIELD_DURATION      = fld_kd_dat_data05;
	public final String FIELD_CODE_EVT		= fld_kd_dat_data10;
	public final String FIELD_NUM_EVT		= fld_kd_dat_data11;
	public final String FIELD_FLAG        	= fld_kd_dat_data08; //1 = modifi�, 2=delete

	MyDB db;
	public dbKD104AgendaSrv(MyDB _db)
	{
		super();
		db=_db;
	}
	static public String stMes ="" ;

	static public class passePlat {
		public static final String FIELD_FLAG = null;
		public passePlat()
		{
			FIELD_SOC_CODE ="";
			FIELD_ID     		="";
			FIELD_CODECLI      	="";
			FIELD_OBJET   ="";
			FIELD_DESCRIPTION   ="";
			FIELD_DTDEBUT       ="";
			FIELD_DTFIN         ="";
			FIELD_DURATION      ="";
			FIELD_CODEREP="";
			FIELD_CODE_EVT="";
			FIELD_NUM_EVT="";


		}

		public String FIELD_SOC_CODE     ;
		public String FIELD_ID     		;
		public String FIELD_CODEREP     		;
		public String FIELD_CODECLI     ;
		public String FIELD_OBJET;
		public String FIELD_DESCRIPTION;
		public String FIELD_DTDEBUT ;
		public String FIELD_DTFIN  ;
		public String FIELD_DURATION  ;
		public String FIELD_CODE_EVT;
		public String FIELD_NUM_EVT;


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
	public int countModified()
	{

		try
		{
			String query="select count(*) from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'"+
					" and "+
					this.FIELD_FLAG+"<>'"+ KDSYNCHRO_RESET+  "'" ;



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
					this.FIELD_DESCRIPTION      +","+
					this.FIELD_DTDEBUT    		+","+
					this.FIELD_DTFIN  			+","+
					this.FIELD_ID 				+","+
					this.FIELD_FLAG+""+

	  		") VALUES ("+
	  		String.valueOf(KD_TYPE)+","+
	  		"'"+ent.FIELD_SOC_CODE+"',"+
	  		"'"+ent.FIELD_CODECLI+"',"+
	  		"'"+ent.FIELD_CODEREP+"',"+
	  		"'"+MyDB.controlFld(ent.FIELD_OBJET)+"',"+
	  		"'"+MyDB.controlFld(ent.FIELD_DESCRIPTION)+"',"+
	  		"'"+ent.FIELD_DTDEBUT+"',"+
	  		"'"+ent.FIELD_DTFIN+"',"+
	  		"'"+ passePlat.FIELD_FLAG+"',"+
	  		"'"+KDSYNCHRO_UPDATE +"')";

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

	public boolean delete(String id)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+
					" where "+
					FIELD_ID+
					"='"+id+"'"+

					" and "+
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
	public boolean DeleteFlag(String Codeclient,String stcoderayon,StringBuffer err)
	{
		try
		{
			String query="UPDATE "+
					TABLENAME+
					" SET "+
					dbKD.fld_kd_dat_data08+
					"='"+KDSYNCHRO_DELETE+"'"+
					" where "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' and "+
					dbKD.fld_kd_cli_code+
					"='"+Codeclient+"'"+
					" and "+
					dbKD.fld_kd_dat_data01+
					"='"+stcoderayon+"' "+
					" ";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
		}
		return false;
	}

	public Cursor getAllEventsByBD(String idClient)
	{


		return null;
	}

	public void sync104vers106 (Context c)
	{
		//Modif 48941941894 tof du 2/7/19: suppression de la ligne delete
		String query = "SELECT distinct "+TABLENAME+".*,"
				+TableClient.FIELD_ENSEIGNE+","
				+TableClient.FIELD_VILLE+","
				+TableClient.FIELD_CODECOM+","
				+TableClient.FIELD_TOURNEE+""
				+" FROM "+ TABLENAME
				+ " LEFT JOIN "+TableClient.TABLENAME + " ON "
				+ this.FIELD_CODECLI+"="+TableClient.FIELD_CODE
				+ " WHERE " + fld_kd_dat_type + "=" + KD_TYPE ;

		//pour exclure les doublons
		query += " order by cli_code,dat_data03, dat_data04 " ;

		dbKD105AgendaSrvSupp dbKD105 = new dbKD105AgendaSrvSupp(db) ;
		String cli_code = "" ;
		String datedeb = "" ;
		String datefin = "" ;
		dbKD106AgendaCorrespondance db106= new dbKD106AgendaCorrespondance (Global.dbParam.getDB() ) ;
		db106.deleteAll();


		//Modif tof, attribution d'un ID a chaque RDV
		int iEventId = 1000 ;
		try {
			Cursor cur=db.conn.rawQuery(query, null);

			while (cur.moveToNext())
			{

				if ( cli_code.equals(this.giveFld(cur,this.FIELD_CODECLI        )))
					if ( datedeb.equals(this.giveFld(cur,this.FIELD_DTDEBUT        )))
						if ( datefin.equals(this.giveFld(cur,this.FIELD_DTFIN        )))
							continue ;
				cli_code=this.giveFld(cur,this.FIELD_CODECLI        );
				datedeb=this.giveFld(cur,this.FIELD_DTDEBUT        );
				datefin=this.giveFld(cur,this.FIELD_DTFIN        );

				//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");		//correction tof: formatage HH de heurs au lieu de hh
				//Date dtstart = sdf.parse(this.giveFld(cur,this.FIELD_DTDEBUT        ));
				//Date dtend = sdf.parse(this.giveFld(cur,this.FIELD_DTFIN        ));

				//on ne creer pas les rendez-vous supprimé
				if ( dbKD105.IsRecordExists(this.giveFld(cur,this.FIELD_CODECLI        ),
						this.giveFld(cur,this.FIELD_DTDEBUT        ),
						this.giveFld(cur,this.FIELD_OBJET        ))
						== true)
					continue ;

				//insert depuis synch106
				dbKD106AgendaCorrespondance.passePlat pp = new dbKD106AgendaCorrespondance.passePlat();
				String stTitle = "FLS"+"["+this.giveFld(cur,this.FIELD_CODECLI).trim()+"] "+this.giveFld(cur,this.FIELD_OBJET).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_ENSEIGNE).trim().equals(""))
					stTitle+= " - " +this.giveFld(cur,TableClient.FIELD_ENSEIGNE).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_VILLE).trim().equals(""))
					stTitle+= " - Ville:" +this.giveFld(cur,TableClient.FIELD_VILLE).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_CODECOM).trim().equals(""))
					stTitle+= " - Commercial:" +this.giveFld(cur,TableClient.FIELD_CODECOM).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_TOURNEE).trim().equals(""))
					stTitle+= " - Tournée:" +this.giveFld(cur,TableClient.FIELD_TOURNEE).trim() ;
				pp.FIELD_OBJET = stTitle;
				pp.FIELD_DESCRIPTION = this.giveFld(cur,this.FIELD_DESCRIPTION);
				pp.FIELD_CODECLI = cli_code;
				pp.FIELD_ID = iEventId+"";
				pp.FIELD_DTDEBUT = datedeb;
				pp.FIELD_DTFIN = datefin;
				//pp.FIELD_DURATION = result.getString(result.getColumnIndex(Events.DURATION));
				pp.FIELD_CODEREP = Preferences.getValue(c, Espresso.LOGIN, "0");

				//pp.FIELD_DTDEBUT = dateFormat.format(Fonctions.convertToLong(pp.FIELD_DTDEBUT));
				//pp.FIELD_DTFIN = dateFormat.format(Fonctions.convertToLong(pp.FIELD_DTFIN));
				pp.FIELD_SOC_CODE = "1";
				pp.FIELD_CODE_EVT = this.giveFld(cur,this.FIELD_CODE_EVT) ;

				db106.save(pp);
				iEventId ++ ;
			}


			if (cur!=null)
				cur.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//Fonction plus utilisée, remplacé par sync104vers106 (desactivation de l'utilisation de l'agenda google)
	public Cursor CreateAllEvents(String applicationKey,Context c){
		Cursor result = null;

		//ajout de tous les rdv de la base dans l'agenda google
		long lTime1 = System.currentTimeMillis() ;
		long lTime2, lTime3, lTime4 ;
		lTime2= lTime3= lTime4 = 0 ;
		String query = "SELECT * FROM "+ TABLENAME
				+ " WHERE " + fld_kd_dat_type + "=" + KD_TYPE + " AND  ("
				+ FIELD_FLAG + "<> '2' or "
				+ FIELD_FLAG + " is null ) ";

		//ajout 20180315 tof: ajotu des infos clients
		query = "SELECT distinct "+TABLENAME+".*,"
				+TableClient.FIELD_ENSEIGNE+","
				+TableClient.FIELD_VILLE+","
				+TableClient.FIELD_CODECOM+","
				+TableClient.FIELD_TOURNEE+""
				+" FROM "+ TABLENAME
				+ " LEFT JOIN "+TableClient.TABLENAME + " ON "
				+ this.FIELD_CODECLI+"="+TableClient.FIELD_CODE
				+ " WHERE " + fld_kd_dat_type + "=" + KD_TYPE + " AND  ("
				+ FIELD_FLAG + "<> '2' or "
				+ FIELD_FLAG + " is null ) ";

		//Modif 48941941894 tof du 2/7/19: suppression de la ligne delete
		query = "SELECT distinct "+TABLENAME+".*,"
				+TableClient.FIELD_ENSEIGNE+","
				+TableClient.FIELD_VILLE+","
				+TableClient.FIELD_CODECOM+","
				+TableClient.FIELD_TOURNEE+""
				+" FROM "+ TABLENAME
				+ " LEFT JOIN "+TableClient.TABLENAME + " ON "
				+ this.FIELD_CODECLI+"="+TableClient.FIELD_CODE
				+ " WHERE " + fld_kd_dat_type + "=" + KD_TYPE ;

		//pour exclure les doublons
		query += " order by cli_code,dat_data03, dat_data04 " ;


		dbKD105AgendaSrvSupp dbKD105 = new dbKD105AgendaSrvSupp(db) ;
		String stRet = "" ;
		int iNbrRdvAdded = 0 ;
		String cli_code = "" ;
		String dat_data03 = "" ;
		String dat_data04 = "" ;

		int icoutt=0;
		int icouttttt=0;
		try {
			Cursor cur=db.conn.rawQuery(query, null);

			while (cur.moveToNext())
			{
				if(icoutt==0)
				{

					try {
						String[] PROJECTION=new String[] {
								BaseColumns._ID,
								Events.TITLE,
								Events.DESCRIPTION,
								Events.DTSTART,
								Events.DTEND,
								Events.DURATION,
								Events.EVENT_LOCATION
						};
						String where = Events.TITLE +" LIKE '%"+applicationKey+"[%'"
								+" AND "+Events.DELETED+" = '0' AND "+Events.DTSTART+">="+Agenda_getFrom( AgendaToKD_getDateFrom());
						//Pas de limite de date...
						where = Events.TITLE +" LIKE '%"+applicationKey+"[%'";

						int rows = c.getContentResolver().delete(CalendarContract.Events.CONTENT_URI, where, null);
						lTime2 = System.currentTimeMillis() ;


					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}




				}
				icoutt++;


				//Exclusion des doublons, du à un problème dans la table clientv108

				if ( cli_code.equals(this.giveFld(cur,this.FIELD_CODECLI        )))
					if ( dat_data03.equals(this.giveFld(cur,this.FIELD_DTDEBUT        )))
						if ( dat_data04.equals(this.giveFld(cur,this.FIELD_DTFIN        )))
							continue ;
				cli_code=this.giveFld(cur,this.FIELD_CODECLI        );
				dat_data03=this.giveFld(cur,this.FIELD_DTDEBUT        );
				dat_data04=this.giveFld(cur,this.FIELD_DTFIN        );

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");		//correction tof: formatage HH de heurs au lieu de hh
				Date dtstart = sdf.parse(this.giveFld(cur,this.FIELD_DTDEBUT        ));
				Date dtend = sdf.parse(this.giveFld(cur,this.FIELD_DTFIN        ));

				//on ne creer pas les rendez-vous supprimé
				if ( dbKD105.IsRecordExists(this.giveFld(cur,this.FIELD_CODECLI        ),
						this.giveFld(cur,this.FIELD_DTDEBUT        ),
						this.giveFld(cur,this.FIELD_OBJET        ))
						== true)
					continue ;

				ContentResolver cr = c.getContentResolver();
				ContentValues values = new ContentValues();

				values.put(CalendarContract.Events.DTSTART, dtstart.getTime() );
				values.put(CalendarContract.Events.DTEND, dtend.getTime() );
				String stTitle = applicationKey+"["+this.giveFld(cur,this.FIELD_CODECLI).trim()+"] "+this.giveFld(cur,this.FIELD_OBJET).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_ENSEIGNE).trim().equals(""))
					stTitle+= " - " +this.giveFld(cur,TableClient.FIELD_ENSEIGNE).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_VILLE).trim().equals(""))
					stTitle+= " - Ville:" +this.giveFld(cur,TableClient.FIELD_VILLE).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_CODECOM).trim().equals(""))
					stTitle+= " - Commercial:" +this.giveFld(cur,TableClient.FIELD_CODECOM).trim() ;
				if ( !this.giveFld(cur,TableClient.FIELD_TOURNEE).trim().equals(""))
					stTitle+= " - Tournée:" +this.giveFld(cur,TableClient.FIELD_TOURNEE).trim() ;
				values.put(CalendarContract.Events.TITLE, stTitle ) ;
				//values.put(CalendarContract.Events.TITLE, applicationKey+"["+this.giveFld(cur,this.FIELD_CODECLI).trim()+"] "+this.giveFld(cur,this.FIELD_OBJET).trim()+
				//		this.giveFld(cur,TableClient.FIELD_ENSEIGNE).trim()+" - "+this.giveFld(cur,TableClient.FIELD_VILLE).trim()+" - "+this.giveFld(cur,TableClient.FIELD_CODECOM).trim());
				values.put(CalendarContract.Events.DESCRIPTION, this.giveFld(cur,this.FIELD_DESCRIPTION));
				values.put(CalendarContract.Events.EVENT_LOCATION, this.giveFld(cur,this.FIELD_CODE_EVT));
				TimeZone timeZone = TimeZone.getDefault();
				values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
				// Default calendar
				values.put(CalendarContract.Events.CALENDAR_ID, 1);
				values.put(CalendarContract.Events.HAS_ALARM, false);

				//utilisation possible de EVENT_LOCATION pour garder des infos supplementaires

				// Insert event to calendar
				Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
				stRet = uri.toString() ;
				iNbrRdvAdded ++ ;
				if ( lTime3 == 0)
					lTime3 = System.currentTimeMillis() ;

			}

			if (cur!=null)
				cur.close();//MV 26/03/2015

			result = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lTime4 = System.currentTimeMillis() ;

		stMes = "\n\nTemps total : "+(lTime4-lTime1) +"ms \n" ;
		stMes += "Temps delete : "+(lTime2-lTime1) +"ms \n" ;
		stMes += "Temps 1 Ajout  : "+(lTime3-lTime2) +"ms X "+iNbrRdvAdded +"\n" ;

		String stRdvAdded = iNbrRdvAdded +"" ;
		return result;
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

	public String queryDeleteFromOnServer(Context c)
	{
		String del="delete from "+TABLENAME+
				" where "+FIELD_DTDEBUT+">='"+AgendaToKD_getDateFrom()+"'"+
				" and "+dbKD.fld_kd_dat_type+"="+KD_TYPE+
				" and "+FIELD_CODEREP+"='"+Preferences.getValue(c, Espresso.LOGIN, "0")+"'";
		return del;
	}
	public void RDVClient(ArrayList<String> ValueRDV,String codeclient)
	{
		String dataDay=Fonctions.getYYYYMMDD();
		String NextRDV="";
		String LastRDV="";
		String date="";

		boolean bexiste=false;

			String query="SELECT * from "+
					TABLENAME+
					" where "+
					" "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' "+
					" and "+
					dbKD.fld_kd_cli_code+
					"='"+codeclient+"' and "+
					" ( "+
					this.FIELD_FLAG+"<>'"+ KDSYNCHRO_RESET+  "' and "+this.FIELD_FLAG+"<>'"+ KDSYNCHRO_DELETE+  "' ) "  +
					"order by  "+FIELD_DTDEBUT+" desc ";

		try {
			Cursor cur=db.conn.rawQuery(query, null);
			//if(cur != null && cur.moveToFirst()){

				while (cur.moveToNext())
				//while(cur.isAfterLast() == false)
				{
						bexiste=true;
						date = cur.getString(cur.getColumnIndex(this.FIELD_DTDEBUT));
						if(Fonctions.GetStringToIntDanem(Fonctions.Left(date, 8))>=Fonctions.GetStringToIntDanem(dataDay))
						{
							ValueRDV.add("Prochain rendez-vous : "+Fonctions.YYYYMMDDhhmmss_to_dd_mm_yyyy_hh_mm_ss2(date)  );
							ValueRDV.add("1");
						}
						else
						{
							ValueRDV.add("Dernier rendez-vous : "+Fonctions.YYYYMMDDhhmmss_to_dd_mm_yyyy_hh_mm_ss2(date)  );
							ValueRDV.add("0");
						}

						//cur.moveToNext();
						break;
				}
			//}


				if (cur!=null)
					cur.close();//MV 26/03/2015
			if(bexiste==false)
			{
				ValueRDV.add("");
				ValueRDV.add("");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(bexiste==false)
			{
				ValueRDV.add("");
				ValueRDV.add("");
			}
		}

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
	public String getCode_evt(String datedebut,String datefin , String codeclient ){
		String query = "SELECT "+FIELD_CODE_EVT+" FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_CODECLI + "=" + "'" + codeclient + "' and "
				+ this.FIELD_DTDEBUT + "=" + "'" + datedebut + "' and "
			    + this.FIELD_DTFIN + "=" + "'" + datefin + "'  ";


		String comment = "";

		try{
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur != null && cur.moveToNext()) {
				comment = giveFld(cur, this.FIELD_CODE_EVT);
				if (cur!=null)
					cur.close();
			}
			if (cur!=null)
				cur.close();
		}catch(Exception ex){

		}

		return comment;
	}
	public String getNum_evt(String datedebut,String datefin , String codeclient ){
		String query = "SELECT "+FIELD_NUM_EVT+" FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_CODECLI + "=" + "'" + codeclient + "' and "
				+ this.FIELD_DTDEBUT + "=" + "'" + datedebut + "' and "
				+ this.FIELD_DTFIN + "=" + "'" + datefin + "'  ";


		String comment = "";

		try{
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur != null && cur.moveToNext()) {
				comment = giveFld(cur, this.FIELD_NUM_EVT);
				if (cur!=null)
					cur.close();
			}
			if (cur!=null)
				cur.close();
		}catch(Exception ex){

		}

		return comment;
	}

	public int Count_do()
	{

		int i =0;
		try
		{
			String query="select * from "+TABLENAME+" where "+ fld_kd_dat_type + "=" + KD_TYPE + " and ("+fld_kd_dat_data08+"='' or "+fld_kd_dat_data08+" IS NULL ) ";

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				i++;
			}
			if (cur!=null)
				cur.close();
			return i;
		}
		catch(Exception ex)
		{
			return i;
		}

	}



}
