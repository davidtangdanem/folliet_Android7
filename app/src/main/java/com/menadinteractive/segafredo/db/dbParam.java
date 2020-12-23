package com.menadinteractive.segafredo.db;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.helper.Taxe;
import com.menadinteractive.segafredo.plugins.Espresso;

import org.json.JSONArray;
import org.json.JSONObject;

public class dbParam extends DBMain {

	public static String TABLENAME="kems_param";
	public static String INDEXNAME_CODEREC="INDEX_CODEREC";

	public static final String ID = "id";
	public final String FLD_PARAM_SOC_CODE = "soc_code";
	public final static String FLD_PARAM_TABLE = "prm_table";
	public final static String FLD_PARAM_CODEREC = "prm_coderec";
	public final static String FLD_PARAM_LBL = "prm_lbl";
	public final static String FLD_PARAM_COMMENT = "prm_comment";
	public final static String FLD_PARAM_ACTIF = "prm_actif";
	public final static String FLD_PARAM_VALUE = "prm_value";
	public final static String FLD_PARAM_ORDER = "prm_order";
	public final String PARAM_FAMTECH = "FAMTECH";
	public final String PARAM_RAYON = "RAYON";
	public final String PARAM_TYPO = "TYPO";
	public final String PARAM_FAM1 = "FAM1";
	public final String PARAM_FAM2 = "FAM2";
	public final String PARAM_FAM3 = "FAM3";
	public final String PARAM_FAM4 = "FAM4";
	public final String PARAM_FAM5 = "FAM5";
	public final String PARAM_FAM6 = "FAM6";
	public final String PARAM_FAM7 = "FAM7";//Createur
	public final String PARAM_GAM = "GAM";
	public final String PARAM_UNIV = "UNIV";
	public final String PARAM_TYPECDE= "TYPECDE";
	public final String PARAM_CONDLIVR= "CONDLIVR";
	public final String PARAM_CLIFAM= "CLIFAM";
	public final String PARAM_JOURF= "JOURF";
	public final String PARAM_TVA= "TVA";
	public final String PARAM_OMR="DF";
	public final String PARAM_ALARME= "ALARME";
	public final String PARAM_FRAIS= "FRAIS";
	public final String PARAM_OUINON= "OUINON";
	public final String PARAM_TYPEVISITE= "TYPEVISITE";
	public final String PARAM_SOCIETE= "SOCIETE";
	public final String PARAM_COMT_TYPE= "COMTTYPE";
	public final String PARAM_MINVER= "MINVER";
	public final String PARAM_LISTVRP= "LISTVRP";
	public final static String PARAM_TRANSPORT = "TRANSPORT";
	public final String PARAM_IP= "IP";


	public final String PARAM_ETATSUIV= "ETATSUIV";
	public final String PARAM_TCA= "TCA";
	public final String PARAM_TETAT= "TETAT";
	public final String PARAM_TFREQ= "TFREQ";
	public final String PARAM_TJVIS= "TJVIS";
	public final String PARAM_TPOID= "TPOID";
	public final String PARAM_TYPDOC= "TYPDOC";
	public final String PARAM_WSAUTH= "WSAUTH";
	public final String PARAM_DO_TYPE= "DO_TYPE";
	public final String PARAM_CAT_COMPTA= "CAT_COMPTA";
	public final String PARAM_CAT_TARIF= "CAT_TARIF";
	public final String PARAM_ARTGP= "ARTGP";
	public final String PARAM_ADMIN= "ADMIN";
	public final String PARAM_PAYS= "PAYS";
	public final String PARAM_CLIACTIV= "CLIACTIV";//= CODEACTIVITE de la table client
	public final String PARAM_JOURPASSAGE= "JOURPASSAGE";//= Jour de passage
	public final String PARAM_CODETOURNEE= "CODETOURNEE";//= code tournee ou zone
	public final String PARAM_LINCHOIX1= "LINCHOIX1";//Combo choix1 dans ligne article
	public final String PARAM_LINCHOIX2= "LINCHOIX2";//Combo choix2 dans ligne article
	public final String PARAM_TYPEFAMEVT= "TYPEFAMEVT";//Combo Type fam activite
	public final String PARAM_TYPEACTIVITE= "TYPEACTIVITE";//Combo Type activite
	public final String PARAM_TYPEETABLISSEMENT= "TYPEETABLISSEMENT";//Combo TYPEETABLISSEMENT
	public final String PARAM_MODEREGLEMENT= "MODERGL";//dans prm_comment, la regle : N (pas fin de mois) ou E (fin de mois) / nbr de mois / nbr de jours. Exemple : E/1/10 = 30 jours fin de mois le 10  
	public final String PARAM_JOURFERMETURE= "JOURFERMETURE";//Combo JOURFERMETURE
	public final String PARAM_CODECIRCUIT= "CIRCUITLIV";//Combo 
	public final String PARAM_AGENT= "AGENT";//Combo AGENT
	public final String PARAM_ANNUEL= "ANNUEL";//Combo ANNUEL
	public final String PARAM_GROUPEMENTCLIENT= "GROUPECLI";//Combo 
	public final String PARAM_TYPECLI= "TYPECLI";//Combo 	
	public final String PARAM_SAV= "SAV";//Combo SAV
	public final String PARAM_BANQUE = "BANQUE";//Banque
	public final String PARAM_FCTCONTACT= "FCTCONTACT";//Combo 
	public final String PARAM_TITRECONTACT= "CIVIL";//Combo 
	public final String PARAM_BANQUEREMISE= "BANQUEREMISE";//Combo 
	public final String PARAM_LISTETARIF= "LISTETARIF";//Combo 
	public final String PARAM_URLPDF= "URLPDF";//urlpdf 
	public final String PARAM_URLCLI= "URLCLI";//urlcli
	public final String PARAM_PERIODICITE= "PERIODICITE";//Combo PERIODICITE
	
	public final String PARAM_TOURNEE= "TOURNEE";//Combo TOURNEE
	public final String PARAM_INTERVENTION= "INTERVENTION";//Combo INTERVENTION
	
	public final String PARAM_APPARTIENT= "APPARTIENT";//Combo APPARTIENT
	
	public final String PARAM_MARQUE ="MARQUE";//Combo MARQUE
	public final String PARAM_MARQUEUNIQUE ="MARQUEUNIQUE";//Combo MARQUE UNIQUE
	public final String PARAM_TYPEMACHINE ="TYPEMACHINE";//Combo TYPEMACHINE
	
	public final String PARAM_ACTIVITEP ="ACTIVITEP";
	public final String PARAM_CLASSE ="CLASSE";
	public final String PARAM_FAMCLIENT ="FAMCLIENT";
	public final String PARAM_SFAMCLIENT ="SFAMCLIENT";
	public final String PARAM_DEPASSEMENT ="DEPASSEMENT";

	public final String PARAM_DETAILTODO = "DETAILTODO";

	public final String PARAM_PROSPECTOPTIONS = "PROSPECTOPTIONS";


    public final String PARAM_PROSPECTOPTIONS_QUAL = "QUALIFICATION";
    public final String PARAM_PROSPECTOPTIONS_SITUATION = "SITUATION";
    public final String PARAM_PROSPECTOPTIONS_OPTION = "OPTION";
    public final String PARAM_PROSPECTOPTIONS_TYPECUISINE = "TYPECUISINE";


    public final String PARAM_PRODFAMREMISE= "PRODFAMREMISE";//Combo choix2 dans ligne article
	
	public static final String TABLE_CREATE =
			"CREATE TABLE [kems_param]("+
					" 	[soc_code] [varchar](5) NULL,"+
					"	[prm_table] [varchar](10) NULL,"+
					" 	[prm_coderec] [varchar](20) NULL,"+
					" 	[prm_lbl] [varchar](50) NULL,"+
					" 	[prm_comment] [varchar](255) NULL,"+
					" 	[prm_actif] [varchar](1) NULL,"+
					" 	[prm_value] [varchar](50) NULL,"+
					" 	[prm_order] [int] NULL"+
					" )";
	
	public static final String INDEX_CREATE_CODEREC="CREATE INDEX IF NOT EXISTS ["+INDEXNAME_CODEREC+"] "
			+ "ON ["+TABLENAME+"] (["+FLD_PARAM_CODEREC+"])";


	MyDB db;
	public dbParam(MyDB _db)
	{
		db=_db;	
	}
	public MyDB getDB()
	{
		return db;
	}
	public String getComment(String codeTable,String codeRec)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					 
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+codeRec+"'";

			String prm_lbl		="";


			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_lbl		=giveFld(cur,this.FLD_PARAM_COMMENT);

				cur.close();
				return prm_lbl;

			}
		}
		catch(Exception ex)
		{
			return "";
		}

		return "";
	}

	public boolean getTVAParams()
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+ FLD_PARAM_TABLE+" = "+"'"+PARAM_TVA + "' ";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur != null && cur.getCount() > 0)
			{
				return true;
			}
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}
		return false;
	}
	
	public  String getValueLblAllSoc(String codeTable,String codeRec)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	

					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+codeRec+"'";

			String prm_lbl		="";


			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_lbl		=giveFld(cur,this.FLD_PARAM_VALUE);

				cur.close();
				return prm_lbl;

			}
		}
		catch(Exception ex)
		{

		}
		return "";
	}
	public  String getOrderAllSoc(String codeTable,String codeRec)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+

					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+codeRec+"'";

			String prm_lbl		="";


			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_lbl		=giveFld(cur,this.FLD_PARAM_ORDER);

				cur.close();
				return prm_lbl;

			}
		}
		catch(Exception ex)
		{

		}
		return "";
	}
	public String getLblAllSoc(String codeTable,String codeRec)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	

					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+codeRec+"'";

			String prm_lbl		="";

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);

				cur.close();
				return prm_lbl;

			}
		}
		catch(Exception ex)
		{

		}
		return "";
	}
    public String getCommentOption(String codeTable,String coderec,String lbl)
    {
        try
        {
            String query="SELECT * FROM "+
                    TABLENAME+
                    " WHERE "+
                    this.FLD_PARAM_TABLE+
                    "="+
                    "'"+codeTable+"'"+

                    " and "+
                    this.FLD_PARAM_CODEREC+
                    "="+
                    "'"+coderec+"'"+
                    " and "+
                    this.FLD_PARAM_LBL+
                    "="+
                    "'"+lbl+"'";


            String prm_lbl		="";

            Cursor cur=db.conn.rawQuery(query, null);
            while (cur.moveToNext())
            {

                prm_lbl		=giveFld(cur,this.FLD_PARAM_COMMENT);

                cur.close();
                return prm_lbl;

            }
        }
        catch(Exception ex)
        {

        }
        return "";
    }
	public String getLblAllSocReverse(String codeTable,String lbl)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	

					" and "+
					this.FLD_PARAM_LBL+
					"="+
					"'"+lbl+"'";

			String prm_lbl		="";

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_lbl		=giveFld(cur,this.FLD_PARAM_CODEREC);

				cur.close();
				return prm_lbl;

			}
		}
		catch(Exception ex)
		{

		}
		return "";
	}
	
	/**
	 * Récupération des coderec et lbl à partir d'un code table
	 * @param codeTable
	 * @return
	 */
	public LinkedHashMap<String,String> getAllRecLblFromTable(String codeTable)
	{

		LinkedHashMap<String,String> h = new LinkedHashMap<String,String>();
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				String code = giveFld(cur,FLD_PARAM_CODEREC);
				String lbl = giveFld(cur,this.FLD_PARAM_LBL);
				h.put(code,lbl);
			}
			cur.close();
		}
		catch(Exception ex)
		{

		}
		return h;
	}


	/**
	 * Récupération des coderec et lbl à partir d'un code table
	 * @param codeTable
	 * @return
	 */
	public ArrayList<Bundle> getAllRecLblFromTable2(String codeTable)
	{

		ArrayList<Bundle> h = new ArrayList<Bundle>();
		try
		{
			String query="SELECT DISTINCT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'";

			Log.e("query","query=>"+query);

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle bundle = new Bundle();
				//String code = giveFld(cur,FLD_PARAM_CODEREC);
				//String lbl = giveFld(cur,this.FLD_PARAM_LBL);
				bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,giveFld(cur,FLD_PARAM_CODEREC));
				bundle.putString(Global.dbParam.FLD_PARAM_LBL,giveFld(cur,FLD_PARAM_LBL));
				bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, giveFld(cur,FLD_PARAM_COMMENT));
				bundle.putString(Global.dbParam.FLD_PARAM_VALUE, giveFld(cur,FLD_PARAM_COMMENT));
				h.add(bundle);
			}
			cur.close();
		}
		catch(Exception ex)
		{

		}
		return h;
	}

	/**
	 * Permet de récupérer le taux de taxe OMR par rapport à un code
	 * @param int code
	 * @return String
	 */
	public String getOMRFromCode(int code){
		String taxe = "";
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_OMR+"'"+	
					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					""+code+"";


			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				taxe=giveFld(cur,this.FLD_PARAM_VALUE);
				cur.close();
				return taxe;

			}
		}
		catch(Exception ex)
		{

		}
		return "";
	}
	
	/**
	 * Permet de récupérer l'objet Taxe à partir du code taxe
	 * @return Taxe
	 * @param String
	 */
	public Taxe getTaxeFromCodeTaxe(String cod_taxe){
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_OMR+"'"+	
					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					""+cod_taxe+"";

			Cursor cur=db.conn.rawQuery(query, null);
			Taxe taxe;
			while (cur.moveToNext())
			{
				taxe = new Taxe(cod_taxe, giveFld(cur,this.FLD_PARAM_LBL), giveFld(cur,this.FLD_PARAM_VALUE), "pourcentage");
				cur.close();
				return taxe;
			}
		}
		catch(Exception ex)
		{

		}
		return null;
	}
	
	
 

	public boolean getCommentAndValueAndLblParam(ArrayList<String> ValueExtGetEntTarif,String codeTable, String stFitre)
	{
		boolean bres=false;
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	

					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+stFitre+"'";



			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{	
				ValueExtGetEntTarif.add(cur.getString(cur.getColumnIndex(this.FLD_PARAM_COMMENT)));
				ValueExtGetEntTarif.add(cur.getString(cur.getColumnIndex(this.FLD_PARAM_VALUE)));
				ValueExtGetEntTarif.add(cur.getString(cur.getColumnIndex(this.FLD_PARAM_LBL)));
				ValueExtGetEntTarif.add(cur.getString(cur.getColumnIndex(this.FLD_PARAM_CODEREC)));
				bres=true;
			}
			cur.close();
		}
		catch(Exception ex)
		{
			bres= false;
		}

		return bres;
	}


	/**
	 * Remplir le spinner
	 * @param codeTable
	 * @param liste
	 * @return
	 */
/*	public boolean getRecords(String codeTable,ArrayList<Bundle> liste)
	{
		try
		{

			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_SOC_CODE+
					"="+
					"'"+Global.AXE_Client.SOC_CODE+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_ORDER;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);



				liste.add(bundle);

			}
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	*/
	/**
	 * Remplir le spinner 
	 * @param codeTable
	 * @param liste
	 * @param FiltreInverse
	 * @param bcontrat
	 * @param stTypeContrat
	 * @return
	 */
/*	public boolean getRecordsFiltre(String codeTable,ArrayList<Bundle> liste,String FiltreInverse)
	{
		try
		{

			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+		
					" and "+
					this.FLD_PARAM_SOC_CODE+
					"="+
					"'"+Global.AXE_Client.SOC_CODE+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_ORDER;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);



				if(prm_value.equals(FiltreInverse))
				{

				}
				else
				{
					Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);


					//String res=prm_coderec+"�"+prm_lbl+"�"+prm_comment+"�"+prm_value;

					liste.add(bundle);

				}


			}
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}



		return true;
	}
	*/

    public ArrayList<JSONArray> getDetailsByEvent(String idEvent)
	{

		ArrayList<String> datas = new ArrayList<>();

		try
		{
			String query="SELECT * FROM "+
						TABLENAME+
						" WHERE "+
						this.FLD_PARAM_TABLE+
						"="+
						"'"+PARAM_DETAILTODO+"'"+
						" and "+
						this.FLD_PARAM_VALUE+
						"="+
						"'"+idEvent+"'"+
						//this.FLD_PARAM_ACTIF+
						//"="+
						//"'1'"+
						" ORDER BY "+
						FLD_PARAM_CODEREC;

			Log.e("getDetailByEvent",""+query);

			ArrayList<JSONArray> arrayList=new ArrayList<JSONArray>();
			Cursor cur = db.conn.rawQuery(query, null);
			while (cur.moveToNext()) {
				JSONArray resultSet = new JSONArray();

				int totalColumn = cur.getColumnCount();
				JSONObject rowObject = new JSONObject();
				for (int i = 0; i < totalColumn; i++) {
					if (cur.getColumnName(i) != null) {
						try {
							rowObject.put(cur.getColumnName(i),
									cur.getString(i));
						} catch (Exception e) {
							Log.d("TAG", e.getMessage());
						}
					}
				}
				resultSet.put(rowObject);

				arrayList.add(resultSet);
			}
			cur.close();

			return arrayList;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	/**
	 * @author Marc VOUAUX
	 * @param err
	 * @return
	 */
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
	/**
	 * @author Marc VOUAUX
	 * @return
	 */
	public int Count()
	{

		try
		{
			String query="select count(*) from "+TABLENAME;
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int c=cur.getInt(0);
				cur.close(); 
				return c;
			}
			cur.close();
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}	
	/**
	 * r�cuper le contenu du champ Comment avec le filtre coderec
	 * 
	 */
	/*
	public String getRecordsComment(String codeTable,String CoderecFiltre)
	{
		String prm_comment	="";

		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_SOC_CODE+
					"="+
					"'"+Global.AXE_Client.SOC_CODE+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1' And "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+CoderecFiltre+"' "+			
					" ORDER BY "+
					this.FLD_PARAM_ORDER;




			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{


				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);


			}
			cur.close();
		}
		catch(Exception ex)
		{
			return "";
		}



		return prm_comment;
	}
*/
	/*
	 * retourne la taille max des lbl d'une table param
	 */
	public int getMaxLenLbl(String table)
	{
		String query="select "+
				" max(length("+FLD_PARAM_LBL+")) as maxlen "+
				" from "+
				TABLENAME+
				" where "+
				FLD_PARAM_TABLE+"='"+table+"' ";

		int maxlen =0;
		Cursor cur=db.conn.rawQuery(query, null);
		if (cur.moveToNext())
		{
			maxlen	=Fonctions.convertToInt(  giveFld(cur,"maxlen"));
		}
		cur.close();


		return maxlen;
	}
	public boolean getRecordsFiltreAllSoc(String codeTable,ArrayList<Bundle> liste,String FiltreInverse)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+		
					" and "+
					
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_ORDER;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
		
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);



				if(prm_value.equals(FiltreInverse))
				{

				}
				else
				{
					Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);


					//String res=prm_coderec+"�"+prm_lbl+"�"+prm_comment+"�"+prm_value;

					liste.add(bundle);

				}


			}
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}



		return true;
	}
	/**
	 * Remplir le spinner sans le code soci�t�
	 * @param codeTable
	 * @param liste
	 * @return
	 */
	public boolean getRecord2s(String codeTable,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecord2sClasse(String codeTable,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT "+this.FLD_PARAM_CODEREC+" FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" GROUP BY "+this.FLD_PARAM_CODEREC+
					" ORDER BY "+
					this.FLD_PARAM_CODEREC;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecord2sFamclient(String codeTable,String classe,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT "+this.FLD_PARAM_CODEREC+","+this.FLD_PARAM_LBL +" FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_VALUE+
					"='"+classe+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" GROUP BY "+this.FLD_PARAM_CODEREC+","+this.FLD_PARAM_LBL +
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecord2sSFamclient(String codeTable,String famclient,String classe,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();
			if(Fonctions.GetStringDanem(famclient).equals(""))
				return false;
			

			String query="SELECT "+this.FLD_PARAM_CODEREC+","+this.FLD_PARAM_LBL +" FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" and "+
					this.FLD_PARAM_COMMENT+
					"="+
					"'"+famclient+"'"+
					" and "+
					this.FLD_PARAM_VALUE+
					"="+
					"'"+classe+"'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecord2sAgence(String codeTable,String id,boolean filtreResp,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();
			
			//listing agence 

			String query1="SELECT "+this.FLD_PARAM_COMMENT+" FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+id+"'"+
					" GROUP BY "+this.FLD_PARAM_COMMENT;
			
			String WhereAgence="";
			int icount=0;
			Cursor cur1=db.conn.rawQuery(query1, null);
			while (cur1.moveToNext())
			{
                if(icount==0)
                {
                	WhereAgence=" "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";
                }
                if(icount==1)
                {
                	WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";
                }
                if(icount==2)
                {
                	WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";
                }
                if(icount==3)
                {
                	WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";
                }
                if(icount==4)
                {
                	WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";
                	
                }
				if(icount==5)
				{
					WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";

				}
				if(icount==6)
				{
					WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";

				}
				if(icount==7)
				{
					WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";

				}
				if(icount==8)
				{
					WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";

				}
				if(icount==9)
				{
					WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";

				}
				if(icount==10)
				{
					WhereAgence+=" OR "+this.FLD_PARAM_COMMENT+"='"+giveFld(cur1,this.FLD_PARAM_COMMENT)+"'  ";

				}
                icount++;
               
			}
			
			
			cur1.close();

			String filtre = "V";
			String query="";
			
			if (filtreResp)
			{
				filtre = "R";

				query="SELECT * FROM "+
						TABLENAME+
						" WHERE "+
						this.FLD_PARAM_TABLE+
						"="+
						"'"+codeTable+"'"+
						" and ( "+
						WhereAgence
						+" ) and "+
						this.FLD_PARAM_VALUE+
						"="+
						"'"+ filtre+"'"+
						" and "+
						this.FLD_PARAM_ACTIF+
						"="+
						"'1'"+
						" ORDER BY "+
						this.FLD_PARAM_LBL;

			}
			else
			{
				query="SELECT * FROM "+
						TABLENAME+
						" WHERE "+
						this.FLD_PARAM_TABLE+
						"="+
						"'"+codeTable+"'"+
						" and ( "+
						WhereAgence
						+" ) and  ( "+
						this.FLD_PARAM_VALUE+
						"='"+filtre+
						"' OR "+
						this.FLD_PARAM_VALUE+
						"="+
						"'R' "+
						" OR "+
						this.FLD_PARAM_VALUE+
						"="+
						"'T') "+
						" and "+
						this.FLD_PARAM_ACTIF+
						"="+
						"'1'"+
						" ORDER BY "+
						this.FLD_PARAM_LBL;


			}




			Log.e("query","getAgent=>"+query);

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecordActivite(String codeTable,String typeagent,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_COMMENT+
					"="+
					"'"+typeagent+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecordActiviteRemove(String codeTable,String typeagent,String stremove,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_COMMENT+
					"="+
					"'"+typeagent+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				
				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);
				if(!Fonctions.GetStringDanem(stremove).equals("") && Fonctions.GetStringDanem(stremove).equals(prm_coderec))
				{
					/*Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);*/
				}
				else
				{Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);

				}


			

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}

	public boolean getRecordActiviteorder(String codeTable,String typeagent,String fam,ArrayList<Bundle> liste)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */

			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+
					" and "+
					this.FLD_PARAM_COMMENT+
					"="+
					"'"+typeagent+"'"+
					" and "+
					this.FLD_PARAM_ORDER+
					"="+
					"'"+fam+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";



			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{


				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);

				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}

			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}

	public boolean getRecordFamActivite(String codeTable,String typeagent,String stremove,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */

			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+
					" and "+
					this.FLD_PARAM_COMMENT+
					"="+
					"'"+typeagent+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";

			/*if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}*/

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{


				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);
				if(!Fonctions.GetStringDanem(stremove).equals("") && Fonctions.GetStringDanem(stremove).equals(prm_coderec))
				{
					/*Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);*/
				}
				else
				{
					Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);
				}




			}

			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	public boolean getRecord2sTypeMachine(String codeTable,String marque,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			liste.clear();
			
			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}


			ArrayList<Bundle> listeMachine = new ArrayList<Bundle>();
			Global.dbParam.getMachine_Marque(Global.dbParam.PARAM_MARQUE, marque,listeMachine);
			String[] itemsMachine = new String[listeMachine.size()];
			
			for (int i = 0; i < listeMachine.size(); i++) {

				itemsMachine[i] = listeMachine.get(i).getString(
						Global.dbParam.FLD_PARAM_COMMENT);
				
				String query="SELECT * FROM "+
						TABLENAME+
						" WHERE "+
						this.FLD_PARAM_TABLE+
						"="+
						"'"+codeTable+"'"+	
						" and "+
						this.FLD_PARAM_CODEREC+
						"="+
						"'"+itemsMachine[i]+"'"+	
						" and "+
						this.FLD_PARAM_ACTIF+
						"="+
						"'1'"+
						" ORDER BY "+
						this.FLD_PARAM_LBL;

			
				Cursor cur=db.conn.rawQuery(query, null);
				while (cur.moveToNext())
				{

					prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
					prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
					prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
					prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


					Bundle bundle = new Bundle();
					bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
					bundle.putString(FLD_PARAM_LBL, prm_lbl);
					bundle.putString(FLD_PARAM_COMMENT, prm_comment);
					bundle.putString(FLD_PARAM_VALUE, prm_value);
					liste.add(bundle);

				}
				
				cur.close();

				

			}
		
			
			
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	
	public boolean getMachine_Marque(String codeTable,String marque,ArrayList<Bundle> liste)
	{
		try
		{
			
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+marque+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY "+
					this.FLD_PARAM_LBL;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	
	public boolean getRecordPeriodicite(String codeTable,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY CAST("+this.FLD_PARAM_CODEREC+" as Integer) ";
					
					
			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}
	
	public boolean getRecordTournee(String codeTable,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY CAST("+this.FLD_PARAM_CODEREC+" as Integer) ";
					
					
			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}
			
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}


	public boolean getRecordCommentOption(String codeTable,String coderec,ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */

			liste.clear();

			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+
					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+coderec+"'"+
					" and "+
					this.FLD_PARAM_ACTIF+
					"="+
					"'1'"+
					" ORDER BY CAST("+this.FLD_PARAM_CODEREC+" as Integer) ";


			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";

			if (addBlanc)
			{
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundleblanc.putString(FLD_PARAM_LBL, prm_lbl);
				bundleblanc.putString(FLD_PARAM_COMMENT, prm_comment);
				bundleblanc.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);


				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);
				liste.add(bundle);

			}

			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}



	public boolean getFamActives(ArrayList<Bundle> liste)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
			liste.clear();

			String query="SELECT distinct fam.* FROM "+
					TABLENAME+
					" as fam "+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_FAM1+"'"+		
			/*		" and "+
					Global.dbProduit.FIELD_PRO_ACTIF_CDE+"<>'N' "+
					*/
					" ORDER BY "+
					this.FLD_PARAM_ORDER;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);

				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);

				liste.add(bundle);
			}
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}


		return true;
	}

	public boolean getAgencesByAgent(ArrayList<Bundle> liste,String idAgent)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
			liste.clear();

			String query="SELECT distinct fam.* FROM "+
					TABLENAME+
					" as fam "+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_AGENT+"'"+
					" and "+
					this.FLD_PARAM_CODEREC+"= '"+ idAgent +"'"+
					" ORDER BY "+
					this.FLD_PARAM_ORDER;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";

			Log.e("query","getAgencesByAgent=>"+query);

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);

				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);

				liste.add(bundle);
			}
			cur.close();
		}
		catch(Exception ex)
		{
			Log.e("exception","ex"+ex.getLocalizedMessage());
			return false;
		}

		return true;
	}

	public boolean getCodeComByAgence(ArrayList<Bundle> liste,String idAgence)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
			//liste.clear();

			String query="SELECT distinct fam.* FROM "+
					TABLENAME+
					" as fam "+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_AGENT+"'"+
					" and "+
					this.FLD_PARAM_COMMENT+"= '"+ idAgence +"'"+
					" ORDER BY "+
					this.FLD_PARAM_ORDER;

			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";

			Log.e("query","getCodeComByAgence=>"+query);

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);

				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);

				liste.add(bundle);
			}
			cur.close();
		}
		catch(Exception ex)
		{
			Log.e("exception","ex"+ex.getLocalizedMessage());
			return false;
		}

		return true;
	}
	public boolean getFamActivesFacturable(ArrayList<Bundle> liste)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
			liste.clear();

			/*String query="SELECT distinct fam.* FROM "+
					TABLENAME+
					" as fam "+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_FAM1+"' and ( "+
					this.FLD_PARAM_CODEREC+
					"='20' or "+
					this.FLD_PARAM_CODEREC+
					"='22'  or "+
					this.FLD_PARAM_CODEREC+
					"='50'  or "+
					this.FLD_PARAM_CODEREC+
					"='51'  or "+
					this.FLD_PARAM_CODEREC+
					"='52'  or "+
					this.FLD_PARAM_CODEREC+
					"='53'  or "+
					this.FLD_PARAM_CODEREC+
					"='54'  or "+
					this.FLD_PARAM_CODEREC+
					"='55'  or "+
					this.FLD_PARAM_CODEREC+
					"='56'  or "+
					this.FLD_PARAM_CODEREC+
					"='57'  or "+
					this.FLD_PARAM_CODEREC+
					"='58'  or "+
					this.FLD_PARAM_CODEREC+
					"='59'  or "+
					this.FLD_PARAM_CODEREC+
					"='D'  "+

					" ) ORDER BY "+
					this.FLD_PARAM_ORDER;*/

			String query="SELECT distinct fam.* FROM "+
					TABLENAME+
					" as fam "+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+PARAM_FAMTECH+"' "
					+" and "
					+this.FLD_PARAM_ACTIF
					+ "='1'  ORDER BY "+
					this.FLD_PARAM_ORDER;



			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				prm_coderec	=giveFld(cur,this.FLD_PARAM_CODEREC);
				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);
				prm_comment	=giveFld(cur,this.FLD_PARAM_COMMENT);
				prm_value	=giveFld(cur,this.FLD_PARAM_VALUE);
			
				Bundle bundle = new Bundle();
				bundle.putString(FLD_PARAM_CODEREC, prm_coderec);
				bundle.putString(FLD_PARAM_LBL, prm_lbl);
				bundle.putString(FLD_PARAM_COMMENT, prm_comment);
				bundle.putString(FLD_PARAM_VALUE, prm_value);

				liste.add(bundle);
			}
			cur.close();
		}
		catch(Exception ex)
		{
			return false;
		}



		return true;
	}
	
	public String getLblAllSoc(String codeTable,String codeRec,String  value)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+	
					" and "+
					this.FLD_PARAM_VALUE+
					"="+
					"'"+value+"'"+
					" and "+
					this.FLD_PARAM_CODEREC+
					"="+
					"'"+codeRec+"'";

			String prm_lbl		="";


			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_lbl		=giveFld(cur,this.FLD_PARAM_LBL);

				cur.close();
				return prm_lbl;

			}
			cur.close();
		}
		catch(Exception ex)
		{
			
		}
		return "";
	}
	
	//dans prm_comment, la regle : 
	//N (pas fin de mois) ou E (fin de mois) / nbr de mois / nbr de jours. 
	//Exemple : E/1/10 = 30 jours fin de mois le 10
	public String calcDateEcheance(String fromdate,String regle)
	{
		boolean bfdm=false;
		String fdm=Fonctions.GiveFld(regle, 0, ";", true);
		int ndm=Fonctions.convertToInt(Fonctions.GiveFld(regle, 1 , ";", false));
		int nj=Fonctions.convertToInt(Fonctions.GiveFld(regle, 2 , ";", false));
		
		if (fdm.equals("E"))
			bfdm=true;
		
		String newdate=Fonctions.YYYYMMDD_PLUSex(fromdate, nj, ndm,bfdm);
		
		
		
		return newdate;
	}

	public String getCodeFromLbl(String codeTable,String lbl)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME+
					" WHERE "+
					this.FLD_PARAM_TABLE+
					"="+
					"'"+codeTable+"'"+
					" and "+

					this.FLD_PARAM_LBL+
					"="+
					"'"+lbl+"'";

			String prm_code		="";


			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_code		=giveFld(cur,this.FLD_PARAM_CODEREC);

				cur.close();
				return prm_code;

			}
		}
		catch(Exception ex)
		{
			return "";
		}

		return "";
	}

}
