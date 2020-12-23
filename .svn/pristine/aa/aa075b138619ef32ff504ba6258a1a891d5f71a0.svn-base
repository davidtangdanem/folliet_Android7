package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;

import android.database.Cursor;
import android.os.Bundle;

public class dbKD731HistoDocumentsLignes extends dbKD {

	/**
	 * Initialisation variable
	 */
	public final static int KD_TYPE = 731;
	public final String FIELD_DATATYPE 	= fld_kd_dat_type;
	public final String FIELD_CODVRP 	= fld_kd_dat_idx01;
	public final String FIELD_TYPEDOC	= fld_kd_dat_idx03;
	public final static String FIELD_NUMDOC 	= fld_kd_dat_idx02;
	public final static String FIELD_CODEART 	= fld_kd_pro_code;
	public final String FIELD_QTE 		= fld_kd_val_data34;
	public final String FIELD_QTEGRAT 		= fld_kd_val_data35;
	public final String FIELD_PUNET 		= fld_kd_val_data33;
	public final String FIELD_PUBRUT 		= fld_kd_dat_data10;
	public final String FIELD_REMISE 	= fld_kd_val_data31;
	public final String FIELD_MNTHT 	= fld_kd_val_data32;
	public final static String FIELD_CODECLIENT= fld_kd_cli_code;
	public final String FIELD_NUMLIN 	= fld_kd_val_data39;
	
	MyDB db;
	public dbKD731HistoDocumentsLignes(MyDB _db)
	{
		db=_db;
	}
 
	public boolean clear(StringBuilder err)
	{
		try
		{
			String query="delete from "+TABLENAME_HISTO+
					" where "+fld_kd_dat_type+"="+KD_TYPE;
			//db.conn.delete(TABLENAME, null, null);
			db.execSQL(query, err);
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
			String query="select count(*) from "+TABLENAME_HISTO+
					" where "+fld_kd_dat_type+"="+KD_TYPE;
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

	public int Count(String codecli)
	{
		try
		{
			String query="select count(*) from "+TABLENAME_HISTO+
					" where "+fld_kd_dat_type+"="+KD_TYPE+
					" and "+FIELD_CODECLIENT+"='"+codecli+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i=cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			///if (cur!=null)
				//cur.close();
				closeCursor(cur);
			return 0;
		}
		catch(Exception ex)
		{
			return 0;
		}

	}
	 	
	 public ArrayList<Bundle> get(String numdoc  ,String codecli,String typedoc) {
	 
		String query = "SELECT * FROM " + TABLENAME_HISTO + 
				" left join "+dbSiteProduit.TABLENAME+
				" on "+FIELD_CODEART+"="+dbSiteProduit.FIELD_PRO_CODART+
				" where "+
				fld_kd_dat_type + "=" + KD_TYPE +
				" and "+FIELD_NUMDOC+"='"+numdoc+"'" +
				" and "+FIELD_TYPEDOC+"='"+typedoc+"'" +
				" and "+FIELD_CODECLIENT+"='"+codecli+"'" +
				"   order by "+	 FIELD_NUMLIN 	 ;
				
		ArrayList<Bundle>  bus=new ArrayList<Bundle>();
		Cursor cur = db.conn.rawQuery(query, null);
		while (cur.moveToNext() ) {
			Bundle b=new Bundle();
			b.putString(FIELD_DATATYPE 	, cur.getString(cur.getColumnIndex(FIELD_DATATYPE 	)));
		 
			b.putString(dbSiteProduit.FIELD_PRO_NOMART1 	, cur.getString(cur.getColumnIndex(dbSiteProduit.FIELD_PRO_NOMART1  	)));
			b.putString(FIELD_CODVRP 	, cur.getString(cur.getColumnIndex(FIELD_CODVRP 	)));
			b.putString(FIELD_NUMDOC 	, cur.getString(cur.getColumnIndex(FIELD_NUMDOC 	)));
			b.putString(FIELD_CODECLIENT, cur.getString(cur.getColumnIndex(FIELD_CODECLIENT)));
	 
			b.putString(FIELD_REMISE 	,  Fonctions.GetDoubleToStringFormatDanem( cur.getDouble(cur.getColumnIndex(FIELD_REMISE 	) ) ,"0.00")+"");
			b.putString(FIELD_MNTHT 	,  Fonctions.GetDoubleToStringFormatDanem( cur.getDouble(cur.getColumnIndex(FIELD_MNTHT 	)),"0.00")+"");
			b.putString(FIELD_PUNET	,  Fonctions.GetDoubleToStringFormatDanem( cur.getDouble(cur.getColumnIndex(FIELD_PUNET	)),"0.000")+"");
			b.putString(FIELD_PUBRUT	,  Fonctions.GetDoubleToStringFormatDanem( cur.getDouble(cur.getColumnIndex(FIELD_PUBRUT)),"0.000")+"");
			b.putString(FIELD_QTE 	,  Fonctions.GetDoubleToStringFormatDanem( cur.getDouble(cur.getColumnIndex(FIELD_QTE 	)),"0.00")+"");
			b.putString(FIELD_QTEGRAT 	,  Fonctions.GetDoubleToStringFormatDanem( cur.getDouble(cur.getColumnIndex(FIELD_QTEGRAT 	)),"0.00")+"");
			
			b.putString(FIELD_CODEART 	, cur.getString(cur.getColumnIndex(FIELD_CODEART 	)));
			b.putString(dbSiteProduit.FIELD_PRO_CODETVA 	, cur.getString(cur.getColumnIndex(dbSiteProduit.FIELD_PRO_CODETVA 	)));
 
			bus.add(b); 
		} 
		if (cur!=null)
			cur.close();
		
		return bus;
	}
	 public ArrayList<Bundle> getHistoArt(String numart ,String codeclient ) {
		 
			String query = "SELECT hdr."+dbKD729HistoDocuments.FIELD_DATEDOC+" datedoc "+
				 
					" ,lin.* FROM " + TABLENAME_HISTO +" lin "+ 
					" inner join "+TABLENAME_HISTO +" hdr "+
					" on lin."+FIELD_NUMDOC+"=hdr."+dbKD729HistoDocuments.FIELD_NUMDOC+
					
					 
					" where "+
					" lin."+ fld_kd_dat_type + "=" + KD_TYPE +
					" and hdr."+ fld_kd_dat_type + "=" + dbKD729HistoDocuments.KD_TYPE +
					" and lin."+FIELD_CODEART+"='"+numart+"'"+
					" and lin."+FIELD_CODECLIENT+"='"+codeclient+"'"+
					" and hdr."+dbKD729HistoDocuments.FIELD_TYPEDOC+"='"+TableSouches.TYPEDOC_FACTURE+"'"+
					" order by hdr."+dbKD729HistoDocuments.FIELD_DATEDOC+" desc";
					
			ArrayList<Bundle>  bus=new ArrayList<Bundle>();
			Cursor cur = db.conn.rawQuery(query, null);
			while (cur.moveToNext() ) {
				Bundle b=new Bundle();
				b.putString(FIELD_DATATYPE 	, cur.getString(cur.getColumnIndex(FIELD_DATATYPE 	)));
				b.putString("datedoc" 	, Fonctions.YYYY_MM_DD_to_dd_mm_yyyy(cur.getString(cur.getColumnIndex("datedoc" 	))));
	 			b.putString(FIELD_CODVRP 	, cur.getString(cur.getColumnIndex(FIELD_CODVRP 	)));
				b.putString(FIELD_NUMDOC 	, cur.getString(cur.getColumnIndex(FIELD_NUMDOC 	)));
				b.putString(FIELD_CODECLIENT, cur.getString(cur.getColumnIndex(FIELD_CODECLIENT)));
		 
				b.putString(FIELD_REMISE 	, Fonctions.GetDoubleToStringFormatDanem(cur.getDouble(cur.getColumnIndex(FIELD_REMISE 	)),"0.00"));
				b.putString(FIELD_MNTHT 	, Fonctions.GetDoubleToStringFormatDanem(cur.getDouble(cur.getColumnIndex(FIELD_MNTHT 	)),"0.00"));
				b.putString(FIELD_PUNET	, Fonctions.GetDoubleToStringFormatDanem(cur.getDouble(cur.getColumnIndex(FIELD_PUNET	)),"0.000"));
				b.putString(FIELD_PUBRUT	, Fonctions.GetDoubleToStringFormatDanem(cur.getDouble(cur.getColumnIndex(FIELD_PUBRUT	)),"0.000"));
					b.putString(FIELD_QTE 	, cur.getDouble(cur.getColumnIndex(FIELD_QTE 	))+"");
				b.putString(FIELD_QTEGRAT 	, cur.getDouble(cur.getColumnIndex(FIELD_QTEGRAT 	))+"");
				b.putString(FIELD_CODEART 	, cur.getString(cur.getColumnIndex(FIELD_CODEART 	)));
	  
				bus.add(b); 
			}  
			if (cur!=null)
				cur.close();
			
			return bus;
		}
	 
	 //copie les lignes de la piece histo from vers la piece courante to
	 public boolean Duplicate(String from,String to,String clicode,String coderep,String m_soccode,String typedoc,String newTypeDoc)
	 {
		 ArrayList<Bundle> histo=get(from,clicode,typedoc);
		 dbKD84LinCde lin=new dbKD84LinCde(db);
		 
		 for (int i=0;i<histo.size();i++)
		 {
			 String remise=histo.get(i).getString(FIELD_REMISE);
			 String punet=histo.get(i).getString(FIELD_PUNET);
			 String pubrut=histo.get(i).getString(FIELD_PUBRUT);
			 if (Fonctions.convertToFloat(pubrut)==0.00)
				 pubrut=punet;
			 
			 String qte="";
			 String grat="";
			 String codeart=histo.get(i).getString(FIELD_CODEART);
			 String lblart=histo.get(i).getString(dbSiteProduit.FIELD_PRO_NOMART1);
			 
			 float tva =  (float) Fonctions.GetStringToDoubleDanem(Global.dbParam
						.getLblAllSoc(Global.dbParam.PARAM_TVA, histo.get(i).getString(dbSiteProduit.FIELD_PRO_CODETVA)));
			 
			 
			
			 qte=histo.get(i).getString(FIELD_QTE);
			 grat=histo.get(i).getString(FIELD_QTEGRAT);
			 
			 //calcul du prix total
			 int qteI = 0;
			 double prixUnitD = 0.0;
			 
			 try{
				 qteI = Integer.parseInt(qte);
				 prixUnitD = Double.parseDouble(punet);
			 }catch(NumberFormatException ex){
				 
			 }
			 double montant = qteI*prixUnitD;
			 
			 commandeActivity.saveLin(coderep, m_soccode, clicode, to, 
					 codeart, lblart, pubrut,  qte, grat, remise, pubrut, tva,newTypeDoc, 
					 Fonctions.GetDoubleToStringFormatDanem(montant, "0.00"),punet);
		//	 lin.save(ent, numcde, codeart, numlin, stBuf)
		 }
		 return true;
	 }
	 

	 public boolean delete_ligne_duplicata(String numcde,StringBuffer err)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME_DUPLICATA+		
					" where "+
					fld_kd_dat_type+"='"+dbKD84LinCde.KD_TYPE+  "'";
	
			db.conn.execSQL(query);
			
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
		}
		return false;
	}
	 public void Copy_ligne_Duplicata(String numcde,String codecli)
		{
			
			try
			{
				//on efface l'existant et on sauve
				StringBuffer er=new StringBuffer();
				delete_ligne_duplicata(numcde,er);
				
				String query = "SELECT * FROM " + TABLENAME_HISTO + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_NUMDOC + "=" + "'" +numcde+ "' "+
						" and "
						+ this.FIELD_CODECLIENT + "=" + "'" +codecli+ "' "
				+ " ORDER BY  cast("
				+ this.FIELD_NUMLIN
				+ " as float) ";
				
				Cursor cur = db.conn.rawQuery(query, null);
				while (cur.moveToNext() )  {
					 
					{
						 query="INSERT INTO " + TABLENAME_DUPLICATA +" ("+
									dbKD.fld_kd_dat_type+","+
									dbKD84LinCde.FIELD_LIGNECDE_REPCODE   		+","+
									dbKD84LinCde.FIELD_LIGNECDE_CDECODE   		+","+ 
									dbKD84LinCde.FIELD_LIGNECDE_PROCODE       		+","+  
									dbKD84LinCde.FIELD_LIGNECDE_QTECDE			+","+
									dbKD84LinCde.FIELD_LIGNECDE_PRIXMODIF			+","+
									dbKD84LinCde.FIELD_LIGNECDE_MNTUNITHT  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_REMISEMODIF 			+","+
									dbKD84LinCde.FIELD_LIGNECDE_MNTTOTALHT 			+","+
									dbKD84LinCde.FIELD_LIGNECDE_CLICODE  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_DESIGNATION  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_UV  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_TAUX  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_MNTTVA  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_MNTTOTALTTC  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_QTEGR  			+","+
									dbKD84LinCde.FIELD_LIGNECDE_MNTUNITNETHT  			+""+
									
									


									
					  		") VALUES ("+
					  		dbKD84LinCde.KD_TYPE+","+
					  		"'"+cur.getString(cur.getColumnIndex(FIELD_CODVRP 	))+"',"+
					  		"'"+cur.getString(cur.getColumnIndex(FIELD_NUMDOC 	))+"',"+
					  		"'"+cur.getString(cur.getColumnIndex(FIELD_CODEART 	))+"',"+
					  		"'"+cur.getString(cur.getColumnIndex(FIELD_QTE 	))+"',"+
					  		"'"+Fonctions.ReplaceVirgule(cur.getString(cur.getColumnIndex(FIELD_PUBRUT 	)))+"',"+
					  		"'"+Fonctions.ReplaceVirgule(cur.getString(cur.getColumnIndex(FIELD_PUBRUT 	)))+"',"+
					  		"'"+Fonctions.ReplaceVirgule(cur.getString(cur.getColumnIndex(FIELD_REMISE	)))+"',"+
					  		"'"+Fonctions.ReplaceVirgule(cur.getString(cur.getColumnIndex(FIELD_MNTHT 	)))+"',"+
					  		"'"+cur.getString(cur.getColumnIndex(FIELD_CODECLIENT 	))+"',"+
					  		"'"+ MyDB.controlFld(Global.dbProduit.getLibelle(cur.getString(cur.getColumnIndex(FIELD_CODEART))) ) +"',"+
					  		"'"+ Global.dbProduit.getUniteVente(cur.getString(cur.getColumnIndex(FIELD_CODEART)))  +"',"+
					  		"'"+  Fonctions.ReplaceEspace(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_TVA, Global.dbProduit.getTaux(cur.getString(cur.getColumnIndex(FIELD_CODEART))) ) ) +"',"+
					  		"'"+lin_mnttva(cur.getString(cur.getColumnIndex(FIELD_MNTHT 	)),Fonctions.ReplaceEspace(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_TVA, Global.dbProduit.getTaux(cur.getString(cur.getColumnIndex(FIELD_CODEART))) ) ) )+"',"+
					  		"'"+lin_mntttc(cur.getString(cur.getColumnIndex(FIELD_MNTHT 	)),lin_mnttva(cur.getString(cur.getColumnIndex(FIELD_MNTHT 	)),Fonctions.ReplaceEspace(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_TVA, Global.dbProduit.getTaux(cur.getString(cur.getColumnIndex(FIELD_CODEART))) ) ) ))+"',"+
					  		"'"+cur.getString(cur.getColumnIndex(FIELD_QTEGRAT 	))+"',"+
					  		"'"+Fonctions.ReplaceVirgule(cur.getString(cur.getColumnIndex(FIELD_PUNET	)))+"'"+
					  		")";
							
							db.conn.execSQL(query);
					}
					
					
				}
				if (cur!=null)
					cur.close();
				
			
		
			}
			catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
			}

			
			
		}
	 String lin_mnttva(String montantht,String tva)
	 {
		 String montanttva="''";
		 float dmontanttva=0;
		 
		 
		 dmontanttva=(float) Fonctions.round( ((Fonctions.GetStringToFloatDanem(montantht)*Fonctions.GetStringToFloatDanem(tva))/100),2);
		 montanttva=Fonctions.GetFloatToStringFormatDanem(dmontanttva, "0.00");
		
			return montanttva;
	 }
	 String lin_mntttc(String montantht,String montanttva)
	 {
		 String montantttc="''";
		 float dmontantttc=0;
		 dmontantttc=Fonctions.GetStringToFloatDanem(montantht)+Fonctions.GetStringToFloatDanem(montanttva);
		 montantttc= Fonctions.GetFloatToStringFormatDanem(dmontantttc, "0.00");
		 
		 return montantttc;

	 }
	 public String GetTarif_Client_Article(String cod_cli,String cod_pro,StringBuilder err)
		{	
			String tarif="";
			try
			{
				String query = "SELECT * FROM " + TABLENAME_HISTO + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODEART + "=" + "'" +cod_pro+ "' "+
						" and "
						+ this.FIELD_CODECLIENT + "=" + "'" +cod_cli+ "' "
				+ " ORDER BY  "
				+ this.FIELD_NUMDOC
				+ " desc ";
				
				Cursor cur=db.conn.rawQuery(query, null);
				if (cur.moveToNext())
				{
					tarif =cur.getString(cur.getColumnIndex(FIELD_PUNET	));
					cur.close();
					
				}
				cur.close();

			}
			catch(Exception ex)
			{
	          return tarif;
			}

			return tarif;
		}
	 public boolean bTarif_Client_Article(String cod_cli,String cod_pro,StringBuilder err)
		{	
			String tarif="";
			try
			{
				String query = "SELECT * FROM " + TABLENAME_HISTO + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODEART + "=" + "'" +cod_pro+ "' "+
						" and "
						+ this.FIELD_CODECLIENT + "=" + "'" +cod_cli+ "' "
				+ " ORDER BY  cast("
				+ this.FIELD_NUMLIN
				+ " as float) ";
				
				Cursor cur=db.conn.rawQuery(query, null);
				if (cur.moveToNext())
				{
					
					if (cur!=null)
						cur.close();
					return true;
				}
				if (cur!=null)
					cur.close();

			}
			catch(Exception ex)
			{
	          return false;
			}

			return false;
		}
		
	 
}
