package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.tarif.Tarif;

import android.database.Cursor;
import android.os.Bundle;

public class dbTournee extends DBMain{
public static String TABLENAME="SITE_TOURNEE11";
	
	public final static String FIELD_CODECLI        	="codeclient" 	 		;                 
	public final String FIELD_CODEREP      		="coderep" 		;                  
	public final static String FIELD_DATE       		="datetournee"  	; 
	public final static String FIELD_NUM_INTER       		="num_inter"  	;
	public final static String FIELD_TOURNEE       		="tournee"  	;


	public static final String INDEXNAME_CODE_CLI = "INDEX_CODE_CLI";
	public static final String INDEXNAME_NUM_INTER = "INDEX_NUM_INTER";
 
	public static final String INDEX_CREATE_CODE_CLI="CREATE UNIQUE INDEX IF NOT EXISTS ["+INDEXNAME_CODE_CLI+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_CODECLI+"])";
	public static final String INDEX_CREATE_NUM_INTER="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_NUM_INTER+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_NUM_INTER+"])";
	
	
	public static final String TABLE_CREATE=
			"CREATE TABLE [SITE_TOURNEE11]("+
					"		[codeclient] [nvarchar](50) NULL,"+
					"		[coderep] [nvarchar](10) NULL,"+
					"		[datetournee] [nvarchar](8) NULL,"+
					"		[num_inter] [nvarchar](100) NULL,"+
					"		[tournee] [nvarchar](8) NULL"+
					")";

	static public class structSoc
	{
		public String FIELD_CODECLI        	="" 	 		;                 
		public String FIELD_CODEREP      		="" 		;                  
		public String FIELD_DATE       =""  	;  
		public String FIELD_NUM_INTER       =""  	;
		public String FIELD_TOURNEE       =""  	;

	}
	
	MyDB db;
	public dbTournee(MyDB _db)
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
	
	public ArrayList<String> get(){
		ArrayList<String> result = new ArrayList<String>();
		String query = "SELECT distinct "+FIELD_DATE
				+" FROM "+TABLENAME
				+" WHERE "+FIELD_DATE+">='"+Fonctions.getYYYYMMDD()+"' "
				+" ORDER BY "+FIELD_DATE+" ASC";
		
		Cursor cursor = db.conn.rawQuery(query, null);
		if(cursor != null && cursor.moveToFirst()){
			while(cursor.isAfterLast() == false)
			{
				result.add(Fonctions.YYYYMMDD_to_dd_mm_yyyy(giveFld(cursor, FIELD_DATE)));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return result;
	}
	public ArrayList<String> get2(){
		ArrayList<String> result = new ArrayList<String>();
		String query = "SELECT distinct "+FIELD_TOURNEE
				+" FROM "+TABLENAME
				//+" ORDER BY "+FIELD_TOURNEE+" ASC";
		+" ORDER BY CAST("+this.FIELD_TOURNEE+" as Integer) ";

		Cursor cursor = db.conn.rawQuery(query, null);
		if(cursor != null && cursor.moveToFirst()){
			while(cursor.isAfterLast() == false)
			{
				result.add(giveFld(cursor, FIELD_TOURNEE));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return result;
	}
	public  String CodeNum_interne(String codeclient)
	{
		String stnuminterne="";
		String query = "SELECT distinct "+FIELD_NUM_INTER
				+" FROM "+TABLENAME
				+" WHERE "+FIELD_CODECLI+"='"+codeclient+"' ";
		
		Cursor cursor = db.conn.rawQuery(query, null);
		if (cursor.moveToNext())
		{
			stnuminterne=(giveFld(cursor, FIELD_NUM_INTER));
		}
		
		cursor.close();
		
		return stnuminterne;
	}


	public ArrayList<Bundle> getListeTourneeFilters(String filter)
	{
		try
		{

			filter=MyDB.controlFld(filter);
			String query="";
			query="select "+this.FIELD_TOURNEE+" FROM "+
					TABLENAME +
					" GROUP BY  "+this.FIELD_TOURNEE+
			" ORDER BY CAST("+this.FIELD_TOURNEE+" as Integer) ";


			ArrayList<Bundle>  tarif=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(FIELD_TOURNEE, cur.getString(cur.getColumnIndex(this.FIELD_TOURNEE)));
				cli.putString(FIELD_CODECLI, Fonctions.getInToStringDanem(CountClient_Partournee(cur.getString(cur.getColumnIndex(this.FIELD_TOURNEE)))));
				cli.putString(FIELD_DATE, MontantCAClient_Partournee(cur.getString(cur.getColumnIndex(this.FIELD_TOURNEE))));


				tarif.add(cli);
			}
			if (cur!=null)
				cur.close();
			return tarif;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();

		}

		return null;
	}

	public int CountClient_Partournee(String codetournee)
	{

		String clientExiste="";
		int icount=0;

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+FIELD_TOURNEE+"='"+codetournee+"' ORDER BY "+FIELD_CODECLI  ;

			ArrayList<Bundle>  tarif=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				if(!clientExiste.equals(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))))
				{
					icount++;

					clientExiste=cur.getString(cur.getColumnIndex(this.FIELD_CODECLI));

				}
			}

			if (cur!=null)
				cur.close();

		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();

		}
		return icount;


	}

	public String  MontantCAClient_Partournee(String codetournee)
	{

		String clientExiste="";
		String stMontantCA="";
		double dMontantCA=0;

		try
		{
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+FIELD_TOURNEE+"='"+codetournee+"' ORDER BY "+FIELD_CODECLI  ;

			ArrayList<Bundle>  tarif=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				if(!clientExiste.equals(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))))
				{
					stMontantCA=Global.dbClient.getClientCA_MOY_FAC(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI)));
					dMontantCA+=Fonctions.GetStringToDoubleDanem(stMontantCA);

					clientExiste=cur.getString(cur.getColumnIndex(this.FIELD_CODECLI));

				}
			}
			stMontantCA = Fonctions.GetDoubleToStringFormatDanem(dMontantCA,"0.00");

			if (cur!=null)
				cur.close();

		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();

		}
		return stMontantCA;

	}
	public ArrayList<Bundle> getListeTourneeDetailsFilters(String codetournee,  String filter)
	{
		try
		{
			String clientExiste="";
			String query="";
			query="select * FROM "+
					TABLENAME +
					" where "+
					FIELD_TOURNEE+
					"='"+codetournee+"' ORDER BY  "+FIELD_CODECLI ;

			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if(!clientExiste.equals(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))))
				{
					Bundle cli=new Bundle();
					cli.putString("CODECLI",cur.getString(cur.getColumnIndex(this.FIELD_CODECLI)));
					cli.putString("NOM",Global.dbClient.getClient_Nom(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("VILLE",Global.dbClient.getClient_Ville(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("TOURNEE",Global.dbClient.getClient_Tournee(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("PERIODICITE",Global.dbClient.getClient_Periodicite(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("DATELASTFAC",Global.dbKDHistoDocuments.LastFacture(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("NBJOUR",Global.dbClient.getClientCA_MOY_FAC(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));

					tarifdetails.add(cli);
					clientExiste=cur.getString(cur.getColumnIndex(this.FIELD_CODECLI));
				}
			}
			if (cur!=null)
				cur.close();
			return tarifdetails;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();

		}

		return null;
	}

	public ArrayList<Bundle> getListeTourneeDetailsFilters2(String codetournee,  String filter)
	{
		try
		{
			String clientExiste="";
			String query="";
			query="select * FROM "+
					TABLENAME + " tournee " +
					" INNER JOIN "+ TableClient.TABLENAME + " client " +
					" ON tournee." + FIELD_CODECLI + " = client." + TableClient.FIELD_CODE +
					" where tournee."+ FIELD_TOURNEE +
					"='"+codetournee+"' ORDER BY  client."+TableClient.FIELD_PERIODICITE ;

			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if(!clientExiste.equals(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))))
				{
					Bundle cli=new Bundle();
					cli.putString("CODECLI",cur.getString(cur.getColumnIndex(this.FIELD_CODECLI)));
					cli.putString("NOM",cur.getString(cur.getColumnIndex(TableClient.FIELD_NOM)));
					cli.putString("VILLE",cur.getString(cur.getColumnIndex(TableClient.FIELD_VILLE)));
					cli.putString("TOURNEE",cur.getString(cur.getColumnIndex(TableClient.FIELD_TOURNEE)));
					cli.putString("PERIODICITE",cur.getString(cur.getColumnIndex(TableClient.FIELD_PERIODICITE)));
					cli.putString("DATELASTFAC",Global.dbKDHistoDocuments.LastFacture(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("NBJOUR",cur.getString(cur.getColumnIndex(TableClient.FIELD_CA_MOY_FAC)));

					tarifdetails.add(cli);
					clientExiste=cur.getString(cur.getColumnIndex(this.FIELD_CODECLI));
				}
			}
			if (cur!=null)
				cur.close();
			return tarifdetails;
		}
		catch(Exception ex)
		{
			String err="";
            err=ex.getMessage();
		}

		return null;
	}

	public ArrayList<Bundle> getListeTourneeDetailsFiltersChargement(String codetournee,  String filter)
	{
		try
		{
			String clientExiste="";
			String query="";
			query="select distinct * FROM "+
					TABLENAME +
					" where "+
					FIELD_TOURNEE+
					"='"+codetournee+"' ORDER BY  "+FIELD_CODECLI;

			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if(!clientExiste.equals(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))))
				{
					Bundle cli=new Bundle();
					cli.putString("CODECLI",cur.getString(cur.getColumnIndex(this.FIELD_CODECLI)));
					cli.putString("NOM",Global.dbClient.getClient_Nom(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("VILLE",Global.dbClient.getClient_Ville(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("TOURNEE",Global.dbClient.getClient_Tournee(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("PERIODICITE",Global.dbClient.getClient_Periodicite(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("DATELASTFAC","");
					cli.putString("NBJOUR","");


					tarifdetails.add(cli);
					clientExiste=cur.getString(cur.getColumnIndex(this.FIELD_CODECLI));
				}

			}
			if (cur!=null)
				cur.close();
			return tarifdetails;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();

		}

		return null;
	}

	public ArrayList<Bundle> getListeTourneeDetailsFiltersHorsChargement(String codetournee,  String filter)
	{
		try
		{
			String clientExiste="";
			String query="";
			query="select distinct * FROM "+
					TABLENAME +
					" where "+
					FIELD_TOURNEE+
					"!='"+codetournee+"' ORDER BY  "+FIELD_CODECLI;

			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				if(!clientExiste.equals(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))))
				{
					Bundle cli=new Bundle();
					cli.putString("CODECLI",cur.getString(cur.getColumnIndex(this.FIELD_CODECLI)));
					cli.putString("NOM",Global.dbClient.getClient_Nom(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("VILLE",Global.dbClient.getClient_Ville(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("TOURNEE",Global.dbClient.getClient_Tournee(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("PERIODICITE",Global.dbClient.getClient_Periodicite(cur.getString(cur.getColumnIndex(this.FIELD_CODECLI))));
					cli.putString("DATELASTFAC","");
					cli.putString("NBJOUR","");

					tarifdetails.add(cli);
					clientExiste=cur.getString(cur.getColumnIndex(this.FIELD_CODECLI));
				}

			}
			if (cur!=null)
				cur.close();
			return tarifdetails;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();

		}

		return null;
	}



}
