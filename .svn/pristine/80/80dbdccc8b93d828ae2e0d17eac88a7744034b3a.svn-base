package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.menadinteractive.folliet2016.Debug;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.TableClient.structClient;

public class dbSiteHabitudes extends DBMain{

	public static String TABLENAME="SITE_HABITUDES1";

	public final String FIELD_CODEREP ="COD_REP";                  
	public final static String FIELD_COD_CLT ="COD_CLT"; 
	public final static String FIELD_DER_PRX_VEN ="DER_PRX_VEN"; 
	public final static String FIELD_COD_ART ="COD_ART"; 
	public final static String FIELD_DER_QTE_LIV ="DER_QTE_LIV"; 
	public final static String FIELD_DER_DAT_PIE ="DER_DAT_PIE"; 
	public final static String FIELD_QTE_12M ="QTE_12M"; 
	public final static String FIELD_NB_FAC_TOT_CLI ="NB_FAC_TOT_CLI"; 
	public final static String FIELD_QTE_MOY_FAC ="QTE_MOY_FAC"; 
	public final static String FIELD_NB_FAC_TOT_ART ="NB_FAC_TOT_ART"; 
	public final static String FIELD_FREQUENCE ="FREQUENCE";
	public final static String FIELD_STK_CLT ="STK_CLT";
	public final static String FIELD_CODETOURNEE="CODETOURNEE";		//Ajout du champ code tournee, pour les lignes ajoutée sur CODE_CLIENT_CHARGEMENT

	public final static String CODE_CLIENT_CHARGEMENT = "DANEMCHARG";

	public static final String INDEXNAME_CODE_CLI = "INDEX_CODE_CLI";
	public static final String INDEXNAME_CODE_ART = "INDEX_CODE_ART";

	public static final String INDEX_CREATE_CODE_CLI="CREATE UNIQUE INDEX IF NOT EXISTS ["+INDEXNAME_CODE_CLI+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_COD_CLT+"])";
	public static final String INDEX_CREATE_CCODE_ART="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_CODE_ART+"] "
			+ "ON ["+TABLENAME+"] (["+FIELD_COD_ART+"])";


	public static final String TABLE_CREATE=
			"CREATE TABLE [SITE_HABITUDES1]("+
					"		[COD_REP] [nvarchar](10) NULL,"+
					"		[COD_CLT] [nvarchar](255) NULL,"+
					"		[DER_PRX_VEN] [nvarchar](255) NULL,"+
					"		[COD_ART] [nvarchar](255) NULL,"+
					"		[DER_QTE_LIV] [nvarchar](255) NULL,"+
					"		[DER_DAT_PIE] [nvarchar](255) NULL,"+
					"		[QTE_12M] [nvarchar](255) NULL,"+
					"		[NB_FAC_TOT_CLI] [nvarchar](255) NULL,"+
					"		[QTE_MOY_FAC] [nvarchar](255) NULL,"+
					"		[NB_FAC_TOT_ART] [nvarchar](255) NULL,"+
					"		[FREQUENCE] [nvarchar](255) NULL,"+
					"		[STK_CLT] [nvarchar](255) NULL"+

					")";
	//Ajout du champs tournée dans une autre requete, pour eviter les pertes de données
	public static final String TABLE_CREATE2=
			"ALTER TABLE SITE_HABITUDES1 ADD COLUMN CODETOURNEE [nvarchar](255) NULL";

	static public class structHabitude{
		public String FIELD_CODEREP = "";                 
		public String FIELD_COD_CLT = "";                  
		public String FIELD_DER_PRX_VEN = "";  
		public String FIELD_COD_ART = "";   
		public String FIELD_DER_QTE_LIV = "";  
		public String FIELD_DER_DAT_PIE = "";  
		public String FIELD_QTE_12M = "";  
		public String FIELD_NB_FAC_TOT_CLI = "";  
		public String FIELD_QTE_MOY_FAC = "";  
		public String FIELD_NB_FAC_TOT_ART = "";  
		public String FIELD_FREQUENCE = "";
		public String FIELD_STK_CLT = "";
		public String FIELD_CODETOURNEE = "";
	}

	MyDB db;
	public dbSiteHabitudes(MyDB _db)
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
			query = "DELETE FROM "+TABLENAME + " WHERE "+FIELD_COD_CLT+"<>'"+CODE_CLIENT_CHARGEMENT+"'";
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

	public structHabitude load(Cursor cursor){
		structHabitude habitude = new structHabitude();
		if(cursor != null){
			habitude.FIELD_COD_ART = giveFld(cursor, FIELD_COD_ART);
			habitude.FIELD_COD_CLT = giveFld(cursor, FIELD_COD_CLT);
			habitude.FIELD_CODEREP = giveFld(cursor, FIELD_CODEREP);
			habitude.FIELD_DER_DAT_PIE = giveFld(cursor, FIELD_DER_DAT_PIE);
			habitude.FIELD_DER_PRX_VEN = giveFld(cursor, FIELD_DER_PRX_VEN);
			habitude.FIELD_DER_QTE_LIV = giveFld(cursor, FIELD_DER_QTE_LIV);
			habitude.FIELD_FREQUENCE = giveFld(cursor, FIELD_FREQUENCE);
			habitude.FIELD_NB_FAC_TOT_ART = giveFld(cursor, FIELD_NB_FAC_TOT_ART);
			habitude.FIELD_NB_FAC_TOT_CLI = giveFld(cursor, FIELD_NB_FAC_TOT_CLI);
			habitude.FIELD_QTE_12M = giveFld(cursor, FIELD_QTE_12M);
			habitude.FIELD_QTE_MOY_FAC = giveFld(cursor, FIELD_QTE_MOY_FAC);
			habitude.FIELD_STK_CLT = giveFld(cursor, FIELD_STK_CLT);
			habitude.FIELD_CODETOURNEE = giveFld(cursor, FIELD_CODETOURNEE);
		}

		return habitude;
	}


	public String  getNbStockArticleClient(String codearticle ,String Filtre){

	    String sFiltre="";
        sFiltre=Filtre;
        if (sFiltre.equals(""))
        {
            sFiltre=" where "+FIELD_COD_ART+"='"+codearticle+"'";
        }
        else
            sFiltre=Filtre+" and "+FIELD_COD_ART+"='"+codearticle+"'";

	    String stvaleur="";
        int istock=0;
		String stcodearticleExistant ="";

        try{
            String query="select * from "+TABLENAME+"   "+sFiltre+" order by "+FIELD_COD_ART;
            Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext() )
			{
                    if(!stcodearticleExistant.equals(giveFld(cur, FIELD_COD_ART)))
                    {
                        istock=0;
                        istock= Fonctions.GetStringToIntDanem(giveFld(cur, FIELD_STK_CLT));
                    }
                    else
                    {
                        istock+= Fonctions.GetStringToIntDanem(giveFld(cur, FIELD_STK_CLT));
                    }
			 stcodearticleExistant=giveFld(cur, FIELD_COD_ART);

             stvaleur=Fonctions.getInToStringDanem(istock);
			}
        }
        catch(Exception ex){
            return "";
        }

		return stvaleur;

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

	public boolean delete(structHabitude habitude, String codetournee){
		try{
			String query="DELETE from "+TABLENAME
					+" WHERE "+getFullFieldName(FIELD_COD_ART)+" = '"+habitude.FIELD_COD_ART+"'"
					+" AND "+getFullFieldName(FIELD_COD_CLT)+" = '"+habitude.FIELD_COD_CLT+"'";
			//code spécifique aux lignes ajoutée manuellement
			if ( habitude.FIELD_COD_CLT.equals(CODE_CLIENT_CHARGEMENT))
				if ( codetournee != null)
					if ( !codetournee.equals(""))
						query += " AND "+getFullFieldName(FIELD_CODETOURNEE)+" = '"+codetournee+"'";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception e){
			Debug.StackTrace(e);
		}
		return false;
	}

	public boolean delete(String codeArt){
		try{
			String query="DELETE from "+TABLENAME		
					+" WHERE "+
					getFullFieldName(FIELD_COD_ART)+" = '"+codeArt+"'";

			db.conn.execSQL(query);


			return true;
		}
		catch(Exception e){
			Debug.StackTrace(e);
		}
		return false;
	}

	public void save(structHabitude habitude, boolean update){
		if(update){
			delete(habitude,null);
		}

		try{
			String query="INSERT INTO " + TABLENAME +" ("+
					FIELD_COD_ART 		
					+", "+ FIELD_COD_CLT 	
					+", "+ FIELD_CODEREP 	
					+", "+ FIELD_DER_DAT_PIE 		
					+", "+ FIELD_DER_PRX_VEN 		
					+", "+ FIELD_DER_QTE_LIV
					+", "+ FIELD_FREQUENCE
					+", "+ FIELD_NB_FAC_TOT_ART
					+", "+ FIELD_NB_FAC_TOT_CLI
					+", "+ FIELD_QTE_12M
					+", "+ FIELD_QTE_MOY_FAC
					+", "+ FIELD_STK_CLT
					+", "+ FIELD_CODETOURNEE
					+") VALUES ("+
					"'"+MyDB.controlFld(habitude.FIELD_COD_ART)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_COD_CLT)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_CODEREP)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_DER_DAT_PIE)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_DER_PRX_VEN)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_DER_QTE_LIV)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_FREQUENCE)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_NB_FAC_TOT_ART)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_NB_FAC_TOT_CLI)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_QTE_12M)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_QTE_MOY_FAC)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_STK_CLT)+"',"+
					"'"+MyDB.controlFld(habitude.FIELD_CODETOURNEE)+"'"+

					")";

			db.conn.execSQL(query);
		}
		catch(Exception ex)
		{
			Debug.StackTrace(ex);

		}
	}

	/**
	 * Retourne la structure habitude par rapport au code article et code client
	 * @param codart
	 * @param codCli
	 * @return
	 */
	public structHabitude getHabitudeFromCodeArtAndCodeCli(String codart, String codCli){
		try{
			String query="select * from "+TABLENAME+" where "+FIELD_COD_ART+"='"+codart+"' and "+FIELD_COD_CLT+"='"+codCli+"'";
			Cursor cur=db.conn.rawQuery(query, null);
			if(cur != null){
				if (cur.moveToNext()){
					structHabitude habitude =  load(cur);
					cur.close();
					return habitude;
				}
			}
			return null;
		}
		catch(Exception ex){
			return null;
		}
	}

	public ArrayList<Bundle>  getHabitudeFromCodeCli(ArrayList<String> listecodCli, String codetournee){

		if(listecodCli.size()>0)
		{
			String Fitre=" WHERE ("+FIELD_COD_CLT+"='"+dbSiteHabitudes.CODE_CLIENT_CHARGEMENT+"'";
			if ( codetournee != null)
				if ( !codetournee.equals(""))
					Fitre=" WHERE (("+FIELD_COD_CLT+"='"+dbSiteHabitudes.CODE_CLIENT_CHARGEMENT+"' and "+FIELD_CODETOURNEE+"='"+codetournee+"' )";


			for(int i=0;i<listecodCli.size();i++)
			{
				/*if( i==0)
				{
					Fitre=" Where  ("+FIELD_COD_CLT+"='"+listecodCli.get(i)+"' ";
				}
				else*/
					Fitre+=" OR "+FIELD_COD_CLT+"='"+listecodCli.get(i)+"' ";

			}

			//if(!Fitre.equals(""))
				Fitre+=" ) ";

			String stcodearticleExistant="";
			structHabitude habitude= new structHabitude();
			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			try{
				String query="select distinct * from "+TABLENAME+"  LEFT OUTER JOIN "+dbSiteProduit.TABLENAME+" ON "+ FIELD_COD_ART+"="+dbSiteProduit.FIELD_PRO_CODART
						+Fitre+ " order by "+dbSiteProduit.FIELD_PRO_CODART;
				Cursor cur=db.conn.rawQuery(query, null);
				if(cur != null){
					while (cur.moveToNext()){
						StringBuffer stff=new StringBuffer();
						//if(Global.dbKDTempo.load(giveFld(cur, FIELD_COD_ART),stff)==false)
						{
							if(!stcodearticleExistant.equals(giveFld(cur, FIELD_COD_ART)))
							{
								if(Fonctions.GetStringToIntDanem(getNbStockArticleClient(giveFld(cur, FIELD_COD_ART), Fitre))>1)
								{
									Bundle cli=new Bundle();
									cli.putString("COD_CLIENT",cur.getString(cur.getColumnIndex(this.FIELD_COD_CLT)));
									cli.putString("COD_ART",cur.getString(cur.getColumnIndex(this.FIELD_COD_ART)));
									cli.putString("COD_CLT",Global.dbProduit.getLibelle(giveFld(cur, FIELD_COD_ART)));
									cli.putString("STK_CLT",getNbStockArticleClient(giveFld(cur, FIELD_COD_ART), Fitre));
									cli.putString("LASTDATE",giveFld(cur, FIELD_COD_CLT));

									//cli.putString("LASTDATE",Global.dbProduit.getProduitsWithHisto_chargement(giveFld(cur, FIELD_COD_ART), ""));
									cli.putString("SAISIE",Global.dbKDTempo.get(giveFld(cur, FIELD_COD_ART)));
									cli.putString("FAMILLE",Global.dbProduit.getFam(giveFld(cur, FIELD_COD_ART)));

									tarifdetails.add(cli);
								}

								stcodearticleExistant=giveFld(cur, FIELD_COD_ART);
							}
						}

					}
				}
				cur.close();
				return tarifdetails;

			}
			catch(Exception ex){
				return null;
			}
		}
		return null;

	}

	public ArrayList<Bundle>  getAllArtFromCodeCli(ArrayList<String> listecodCli,String filtre, String filtreFam){

		if(listecodCli.size()>0)
		{
            String Fitre=" WHERE ("+FIELD_COD_CLT+"='"+dbSiteHabitudes.CODE_CLIENT_CHARGEMENT+"'";
            for(int i=0;i<listecodCli.size();i++)
            {
				/*if( i==0)
				{
					Fitre=" Where  ("+FIELD_COD_CLT+"='"+listecodCli.get(i)+"' ";
				}
				else*/
                Fitre+=" OR "+FIELD_COD_CLT+"='"+listecodCli.get(i)+"' ";

            }

            //if(!Fitre.equals(""))
            Fitre+=" ) ";

			String stcodearticleExistant="";
			structHabitude habitude= new structHabitude();
			ArrayList<Bundle>  tarifdetails=new ArrayList<Bundle>();
			try{

				String query="select "+dbSiteProduit.FIELD_PRO_CODART+" from "+dbSiteProduit.TABLENAME+"  LEFT OUTER JOIN "+TABLENAME+" ON "+ FIELD_COD_ART+"="+dbSiteProduit.FIELD_PRO_CODART
						+Fitre+ " order by "+dbSiteProduit.FIELD_PRO_CODART;

				String query2 = "SELECT * FROM "+ dbSiteProduit.TABLENAME + " WHERE " + dbSiteProduit.FIELD_PRO_CODART + " NOT IN ("+ query +")";

				if (!filtreFam.equals("---")){
					query2 += " AND " + dbSiteProduit.FIELD_PRO_CODFAM + "= '" + filtreFam + "'" ;
				}

				if (!filtre.equals("")){
					query2 += " AND (" + dbSiteProduit.FIELD_PRO_CODART + "= '" + filtre + "' OR "+ dbSiteProduit.FIELD_PRO_NOMART1 + " LIKE '%"+ filtre + "%')";
				}

				Cursor cur=db.conn.rawQuery(query2, null);

				Log.e("query","getAllArtFromCodeCli"+query2);
				if(cur != null){
					while (cur.moveToNext()){
						StringBuffer stff=new StringBuffer();
						//if(Global.dbKDTempo.load(giveFld(cur, FIELD_COD_ART),stff)==false)
							if (!stcodearticleExistant.equals(giveFld(cur, dbSiteProduit.FIELD_PRO_CODART))) {

								 Bundle cli = new Bundle();
                                cli.putString("COD_ART", cur.getString(cur.getColumnIndex(dbSiteProduit.FIELD_PRO_CODART)));
                                cli.putString("COD_CLT",cur.getString(cur.getColumnIndex(dbSiteProduit.FIELD_PRO_NOMART1)));
                                //cli.putString("COD_CLT", Global.dbProduit.getLibelle(giveFld(cur, dbSiteProduit.FIELD_PRO_CODART)));
                                //cli.putString("STK_CLT",getNbStockArticleClient(giveFld(cur, FIELD_COD_ART), Fitre));
                                cli.putString("LASTDATE", "");

                                //cli.putString("LASTDATE",Global.dbProduit.getProduitsWithHisto_chargement(giveFld(cur, FIELD_COD_ART), ""));
                                //cli.putString("SAISIE", Global.dbKDTempo.get(giveFld(cur, dbSiteProduit.FIELD_PRO_CODART)));
                                //cli.putString("FAMILLE", Global.dbProduit.getFam(giveFld(cur, dbSiteProduit.FIELD_PRO_CODART)));

                                tarifdetails.add(cli);

                                stcodearticleExistant = giveFld(cur, dbSiteProduit.FIELD_PRO_CODART);
							}
					}
				}
				 cur.close();
				return tarifdetails;

			}
			catch(Exception ex){
				return null;
			}
		}
		return null;

	}
	
	public ArrayList<structHabitude> getAllHabitude(){
		ArrayList<structHabitude> list = new ArrayList<dbSiteHabitudes.structHabitude>();
		try{
			String query="select * from "+TABLENAME;
			Cursor cur=db.conn.rawQuery(query, null);
			if(cur != null){
				while (cur.moveToNext()){
					list.add(load(cur));
				}
				cur.close();
			}
			return null;
		}
		catch(Exception ex){
			return null;
		}
	}

}
