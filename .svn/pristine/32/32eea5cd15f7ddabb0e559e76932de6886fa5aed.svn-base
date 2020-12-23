package com.menadinteractive.segafredo.db;

import android.database.Cursor;
import android.os.Bundle;

import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.tournee.Tournee;

import java.util.ArrayList;


public class TableLivraison extends DBMain{

	static public String TABLENAME="LIVRAISONS";

	public static final String FIELD_LV_CODREP		= "COD_REP";
	public static final String FIELD_LV_DAT_LIV		= "DAT_LIV";
	public static final String FIELD_LV_NUM_CDE		= "NUM_CDE";
	public static final String FIELD_LV_NUM_CDE2	= "NUM_CDE2";



	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_LV_CODREP			+"] [varchar](50) NULL" 	+ COMMA +
			" ["+FIELD_LV_DAT_LIV		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LV_NUM_CDE		+"] [varchar](250) NULL" 	+ COMMA +
			" ["+FIELD_LV_NUM_CDE2			+"] [varchar](50) NULL"	+
			")";


	static public class structLivraison
	{


		public String LV_CODREP = "";
		public String LV_DAT_LIV = "";
		public String LV_NUM_CDE = "";
		public String LV_NUM_CDE2 = "";
		public String LV_SAISIE="";
		public String LV_DATERECU="";
		public String LV_ETAT="";




		@Override
		public String toString() {
			return "structMaterielClient [LV_CODREP=" + LV_CODREP + ", LV_DAT_LIV=" + LV_DAT_LIV
					+  ", LV_NUM_CDE=" + LV_NUM_CDE
					+  ", LV_NUM_CDE2=" + LV_NUM_CDE2
					+"]";
		}
	}

	MyDB db;
	public TableLivraison(MyDB _db)
	{
		super();
		db=_db;
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

	public structLivraison load(Cursor cursor){
		structLivraison Livraison = new structLivraison();
		if(cursor != null){



			Livraison.LV_CODREP = giveFld(cursor, FIELD_LV_CODREP);
			Livraison.LV_DAT_LIV = giveFld(cursor, FIELD_LV_DAT_LIV);
			Livraison.LV_NUM_CDE = giveFld(cursor, FIELD_LV_NUM_CDE);
			Livraison.LV_NUM_CDE2 = giveFld(cursor, FIELD_LV_NUM_CDE2);




		}



		return Livraison;
	}


	public structLivraison load(String categorie){
		structLivraison Livraison = new structLivraison();
		String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_LV_NUM_CDE)+" = "+categorie+" ";

		Cursor cursor =  db.conn.rawQuery(query, null);

		if(cursor != null && cursor.moveToFirst()){

			Livraison = load(cursor);
			cursor.close();
		}
		if (cursor!=null)
			cursor.close();

		return Livraison;
	}


	/**
	 * Permet d'obtenir une liste de structTarif à partir d'un cursor
	 * @param cursor
	 * @return ArrayList<structTarif>
	 */
	public ArrayList<structLivraison> getListOfCursorListeLivraison(Cursor cursor){
		ArrayList<structLivraison> list = new ArrayList<TableLivraison.structLivraison>();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			structLivraison Livraison = new structLivraison();
			fillStruct(cursor,Livraison);
			list.add(Livraison);
			cursor.moveToNext();
		}
		cursor.close();
		return list;

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

	public int Count_nosent()
	{

		int i =0;
		try
		{
			String query="select * from "+TABLENAME;

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				String numcde=cur.getString(cur.getColumnIndex(Global.dbLivraison.FIELD_LV_NUM_CDE));
				String dateliv=cur.getString(cur.getColumnIndex(Global.dbLivraison.FIELD_LV_DAT_LIV));

				if(Global.dbKDMarchandise.MarchandiseRecu(dateliv,numcde)==true)
				{

				}else
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




	

	void fillStruct(Cursor cur,structLivraison Livraison)
	{

		Livraison.LV_CODREP=cur.getString(cur.getColumnIndex(FIELD_LV_CODREP));
		Livraison.LV_DAT_LIV=cur.getString(cur.getColumnIndex(FIELD_LV_DAT_LIV));
		Livraison.LV_NUM_CDE=cur.getString(cur.getColumnIndex(FIELD_LV_NUM_CDE));
		Livraison.LV_NUM_CDE2=cur.getString(cur.getColumnIndex(FIELD_LV_NUM_CDE2));

	}
	public ArrayList<Bundle> getLivraisonFilters(String categorie,  String filter)
	{
		try
		{
			filter=MyDB.controlFld(filter);
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+
					FIELD_LV_NUM_CDE+
					"='"+categorie+"'  ";
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbLivraison.FIELD_LV_CODREP,cur.getString(cur.getColumnIndex(Global.dbLivraison.FIELD_LV_CODREP)));
				cli.putString(Global.dbLivraison.FIELD_LV_DAT_LIV, cur.getString(cur.getColumnIndex(Global.dbLivraison.FIELD_LV_DAT_LIV)));
				cli.putString(Global.dbLivraison.FIELD_LV_NUM_CDE, cur.getString(cur.getColumnIndex(Global.dbLivraison.FIELD_LV_NUM_CDE)));
				cli.putString(Global.dbLivraison.FIELD_LV_NUM_CDE2, cur.getString(cur.getColumnIndex(Global.dbLivraison.FIELD_LV_NUM_CDE2)));

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
	public static ArrayList<TableLivraison.structLivraison> getChargement( ){
		ArrayList<TableLivraison.structLivraison> livraisons = new ArrayList<TableLivraison.structLivraison>();

		TableLivraison to = new TableLivraison(Global.dbParam.getDB());
		ArrayList<Bundle> bundles = to.getListe();

		ArrayList<String> bundlescli=null;
		bundlescli = new ArrayList<String>();
		Boolean bres=false;
		for(Bundle bundle : bundles){

			TableLivraison.structLivraison Char = new TableLivraison.structLivraison();

			Char.LV_CODREP=bundle.getString("COD_VRP");
			Char.LV_DAT_LIV=bundle.getString("DAT_LIV");
			Char.LV_NUM_CDE=bundle.getString("NUM_CDE");
			Char.LV_NUM_CDE2=bundle.getString("NUM_CDE2");
			Char.LV_SAISIE=bundle.getString("SAISIE");
			Char.LV_DATERECU=bundle.getString("RECU");
			Char.LV_ETAT=bundle.getString("ETAT");

			livraisons.add(Char);


		}

		return livraisons;
	}

	public ArrayList<Bundle> getListe() {
		try {

			String query = "";
			query = "select * FROM " +
					TABLENAME + " ORDER BY  " + FIELD_LV_DAT_LIV;

			ArrayList<Bundle> list = new ArrayList<Bundle>();
			Cursor cur = db.conn.rawQuery(query, null);
			while (cur.moveToNext()) {

				Bundle cli = new Bundle();
				cli.putString("COD_VRP", cur.getString(cur.getColumnIndex(this.FIELD_LV_CODREP)));
				cli.putString("DAT_LIV",cur.getString(cur.getColumnIndex(this.FIELD_LV_DAT_LIV)));
				cli.putString("NUM_CDE", cur.getString(cur.getColumnIndex(this.FIELD_LV_NUM_CDE)));
				cli.putString("NUM_CDE2", cur.getString(cur.getColumnIndex(this.FIELD_LV_NUM_CDE2)));
				if(Global.dbKDMarchandise.load(cur.getString(cur.getColumnIndex(this.FIELD_LV_DAT_LIV)),cur.getString(cur.getColumnIndex(this.FIELD_LV_NUM_CDE)))==true)
				{
					cli.putString("SAISIE", "1" );
					cli.putString("RECU",Global.dbKDMarchandise.get(cur.getString(cur.getColumnIndex(this.FIELD_LV_DAT_LIV)),cur.getString(cur.getColumnIndex(this.FIELD_LV_NUM_CDE)))  );
					cli.putString("ETAT",Global.dbKDMarchandise.getEtat(cur.getString(cur.getColumnIndex(this.FIELD_LV_DAT_LIV)),cur.getString(cur.getColumnIndex(this.FIELD_LV_NUM_CDE)))  );
				}else
				{
					cli.putString("SAISIE", "" );
					cli.putString("RECU",""  );
					cli.putString("ETAT",""  );
				}


				list.add(cli);


			}
			if (cur != null)
				cur.close();
			return list;
		} catch (Exception ex) {
			String err = "";
			err = ex.getMessage();

		}

		return null;
	}



}
