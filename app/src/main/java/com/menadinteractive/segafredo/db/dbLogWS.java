package com.menadinteractive.segafredo.db;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;

import com.menadinteractive.folliet2016.Debug;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;

import java.util.ArrayList;

public class dbLogWS extends DBMain{

	public static String TABLENAME="SITE_LOGWS2";

	public final static String FIELD_LOGIN ="LOGIN";
	public final static String FIELD_DATEHEUREDEBUT ="DATEHEUREDEBUT";
	public final static String FIELD_NOMTABLE ="NOMTABLE";
	public final static String FIELD_DUREE ="DUREE";
	public final static String FIELD_DATEHEUREFIN ="DATEHEUREFIN";
	public final static String FIELD_RESULTAT ="RESULTAT";
    public final static String FIELD_OPERATEUR ="OPERATEUR";
	public final static String FIELD_OPERATEUR_ETAT ="OPERATEUR_ETAT";
	public final static String FIELD_WIFI ="WIFI";
	public final static String FIELD_IDUNIQUE ="IDUNIQUE";







	public static final String TABLE_CREATE=
			"CREATE TABLE [SITE_LOGWS2]("+
					"		[LOGIN] [nvarchar](10) NULL,"+
					"		[DATEHEUREDEBUT] [nvarchar](255) NULL,"+
					"		[NOMTABLE] [nvarchar](255) NULL,"+
					"		[DUREE] [nvarchar](255) NULL,"+
					"		[DATEHEUREFIN] [nvarchar](255) NULL,"+
					"		[RESULTAT] [nvarchar](255) NULL,"+
                    "		[OPERATEUR] [nvarchar](255) NULL,"+
					"		[OPERATEUR_ETAT] [nvarchar](255) NULL,"+
					"		[WIFI] [nvarchar](255) NULL,"+
					"		[IDUNIQUE] [nvarchar](255) NULL"+


					")";


	public static final String INSERT_STRING=
			"INSERT INTO T_NEGOS_WS"+
					" ("+
					FIELD_LOGIN  		+","+
					FIELD_DATEHEUREDEBUT 		+","+
					FIELD_NOMTABLE  		+","+
					FIELD_DUREE  	+","+
					FIELD_DATEHEUREFIN  	+","+
                    FIELD_RESULTAT  	+","+
					FIELD_OPERATEUR  	+","+
					FIELD_OPERATEUR_ETAT  	+","+
					FIELD_WIFI  	+","+
					FIELD_IDUNIQUE+		") VALUES ";




	static public class structLogWs{
		public String FIELD_LOGIN = "";
		public String FIELD_DATEHEUREDEBUT = "";
		public String FIELD_NOMTABLE = "";
		public String FIELD_DUREE = "";
		public String FIELD_DATEHEUREFIN = "";
		public String FIELD_RESULTAT = "";
        public String FIELD_OPERATEUR = "";
		public String OPERATEUR_ETAT = "";
		public String WIFI = "";
		public String IDUNIQUE = "";




	}

	MyDB db;
	public dbLogWS(MyDB _db)
	{
		super();
        db=_db;
	}

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}

	public boolean clear(StringBuilder err){
		try
		{
			//modif tof, on supprime touot sauf les enregistrement créé sur le code CODE_CLIENT_CHARGEMENT
			String query="DROP TABLE IF EXISTS "+TABLENAME;
			query = "DELETE FROM "+TABLENAME + " ";
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



	public int Count(){
		try{
			String query="select count(*) from "+TABLENAME;
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext()){
				int count = cur.getInt(0);
				cur.close();
				return count;
			}
			return 0;
		}
		catch(Exception ex){
			return -1;
		}
	}


	public void save(String codelogin,String dateheure, String table , String duree ,String dateheurefin, String etat,String OPERATEUR,String operateur_etat,String wifi ,String idunique){


		boolean update =false;
		String query = "SELECT * FROM " + TABLENAME + " where "
				+ FIELD_NOMTABLE + "='" + table + "'  ";


		try {
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext() ) {

				update=true;

				query = "UPDATE "
						+ TABLENAME
						+ " set "
						+ this.FIELD_DUREE
						+ "="
						+ "'"
						+ MyDB.controlFld(duree)
						+ "',"
						+ this.FIELD_RESULTAT
						+ "="
						+ "'"
						+ MyDB.controlFld(etat)
						+ "',"
						+ this.FIELD_DATEHEUREFIN
						+ "="
						+ "'"
						+ MyDB.controlFld(dateheurefin)

						+ "' where "
						+ FIELD_NOMTABLE + "='" + table + "' " ;

				db.conn.execSQL(query);


			}

			if(update==false)
			{



				 query="INSERT INTO " + TABLENAME +" ("+
						FIELD_LOGIN
						+", "+ FIELD_DATEHEUREDEBUT
						+", "+ FIELD_NOMTABLE
						+", "+ FIELD_DUREE
						 +", "+ FIELD_DATEHEUREFIN
						+", "+ FIELD_RESULTAT
                         +", "+ FIELD_OPERATEUR
						 +", "+ FIELD_OPERATEUR_ETAT
						 +", "+ FIELD_WIFI
						 +", "+ FIELD_IDUNIQUE
						+") VALUES ("+
						"'"+MyDB.controlFld(Fonctions.GetStringDanem(codelogin))+"',"+
						"'"+MyDB.controlFld(Fonctions.GetStringDanem(dateheure))+"',"+
						"'"+MyDB.controlFld(Fonctions.GetStringDanem(table))+"',"+
						"'"+MyDB.controlFld(Fonctions.GetStringDanem(duree))+"',"+
						 "'"+MyDB.controlFld(Fonctions.GetStringDanem(dateheurefin))+"',"+
						"'"+MyDB.controlFld(Fonctions.GetStringDanem(etat))+"',"+
                         "'"+MyDB.controlFld(Fonctions.GetStringDanem(OPERATEUR))+"',"+
						 "'"+MyDB.controlFld(Fonctions.GetStringDanem(operateur_etat))+"',"+
						 "'"+MyDB.controlFld(Fonctions.GetStringDanem(wifi))+"',"+
						 "'"+MyDB.controlFld(Fonctions.GetStringDanem(idunique))+"'"+


						 ")";

				db.conn.execSQL(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
