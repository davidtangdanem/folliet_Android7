package com.menadinteractive.segafredo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire.structPassePlat;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbSiteProduit.structArt;
import com.menadinteractive.segafredo.plugins.Espresso;



/**
 * Vue ligne de cde dans la table KD
 * @author Marc VOUAUX
 *
 */
public class dbKD544LinMvtsStock extends dbKD {

	public final int KD_TYPE= 544;
	public final String FIELD_LIGNEMVTS_SOCCODE 	= fld_kd_soc_code;
	public final String FIELD_LIGNEMVTS_PROCODE 	= fld_kd_pro_code;
	public final String FIELD_LIGNEMVTS_REPCODE 	= fld_kd_dat_idx01;
	public final String FIELD_LIGNEMVTS_DATE 		= fld_kd_dat_idx02;
	public final String FIELD_LIGNEMVTS_CODABAR 	= fld_kd_dat_idx04;
	public final String FIELD_LIGNEMVTS_NUMLIGNE 	= fld_kd_dat_data01;
	public final String FIELD_LIGNEMVTS_DESIGNATION = fld_kd_dat_data02;
	public final String FIELD_LIGNEMVTS_UV 			= fld_kd_dat_data03;
	public final String FIELD_LIGNEMVTS_QTEREA 		= fld_kd_dat_data04;
	public final String FIELD_LIGNEMVTS_QTEDECH 	= fld_kd_dat_data05;
	public final String FIELD_LIGNEMVTS_DLC			= fld_kd_dat_data07;
	public final String FIELD_LIGNEMVTS_DEPOTSRC 	= fld_kd_dat_data09;
	public final String FIELD_LIGNEMVTS_DEPOTDST 	= fld_kd_dat_data10;
	public final String FIELD_LIGNEMVTS_LOT 		= fld_kd_dat_data11;
	public final String FIELD_LIGNEMVTS_SERIE 		= fld_kd_dat_data12;
	public final String FIELD_LIGNEMVTS_COMMENT1 	= fld_kd_dat_data13; 
	public final String FIELD_LIGNEMVTS_MVTS_TYPE 	= fld_kd_dat_data14; 
	public final String FIELD_LIGNEMVTS_QTETHEO 	= fld_kd_dat_data15; 
	
	

	
	MyDB db;
	public dbKD544LinMvtsStock(MyDB _db)
	{
		super();
		db=_db;	
	}

	 static public class structPassePlat {
		public structPassePlat()
		{	
			FIELD_LIGNEMVTS_SOCCODE 		="";
			FIELD_LIGNEMVTS_PROCODE 		="";
			FIELD_LIGNEMVTS_REPCODE 		="";
			FIELD_LIGNEMVTS_DATE 		="";
			FIELD_LIGNEMVTS_CODABAR 		="";
			FIELD_LIGNEMVTS_NUMLIGNE 	="";
			FIELD_LIGNEMVTS_DESIGNATION 	="";
			FIELD_LIGNEMVTS_UV 			="";
			FIELD_LIGNEMVTS_QTEREA 			="";
			FIELD_LIGNEMVTS_QTEDECH 			="";
            FIELD_LIGNEMVTS_LOT 			="";
			FIELD_LIGNEMVTS_SERIE 		="";
			FIELD_LIGNEMVTS_COMMENT1 		="";
			FIELD_LIGNEMVTS_DLC="";
			FIELD_LIGNEMVTS_DEPOTSRC="";
			FIELD_LIGNEMVTS_DEPOTDST="";
			FIELD_LIGNEMVTS_MVTS_TYPE="";
			FIELD_LIGNEMVTS_QTETHEO="";
		}

		public String FIELD_LIGNEMVTS_SOCCODE 		;
		public String FIELD_LIGNEMVTS_PROCODE 		;
		public String FIELD_LIGNEMVTS_REPCODE 		;
		public String FIELD_LIGNEMVTS_DATE 		;
		public String FIELD_LIGNEMVTS_CODABAR 	;	
		public String FIELD_LIGNEMVTS_NUMLIGNE 	;
		public String FIELD_LIGNEMVTS_DESIGNATION ;	
		public String FIELD_LIGNEMVTS_UV 			;
		public String FIELD_LIGNEMVTS_QTEREA 			;
		public String FIELD_LIGNEMVTS_LOT 			;
		public String FIELD_LIGNEMVTS_SERIE 		;
		public String FIELD_LIGNEMVTS_COMMENT1 		;
		public String FIELD_LIGNEMVTS_QTEDECH	;
		public String FIELD_LIGNEMVTS_DLC;
		public String FIELD_LIGNEMVTS_DEPOTSRC;
		public String FIELD_LIGNEMVTS_DEPOTDST;
		public String FIELD_LIGNEMVTS_MVTS_TYPE;
		public String FIELD_LIGNEMVTS_QTETHEO;
		
	}

	public int mCount(boolean inTmp)
	{

		try
		{
			String table=TABLENAME;
			if (inTmp)
				table=TABLENAME_TEMP2;

			String query="select count(*) from "+table+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'";



			Cursor cur=db.conn.rawQuery(query, null);
			int result = 0;
			if (cur.moveToNext())
			{
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				result =i;

			}
			if (cur!=null)
				cur.close();
			return result;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}


	
	/**
	 * charge un article du panier de la cde numcde
	 * @author Marc VOUAUX
	 * @param ent
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public boolean load(structPassePlat ent,String codeart,StringBuffer stBuf,String depotsrc,String depotdst,String sens,String login,String soccode)
	{
	
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_LIGNEMVTS_PROCODE+"="+
				"'"+codeart+"'"
				;
		

		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext() && !codeart.equals(""))
		{
			fillStructLin(ent,cur);
	
		}
		else
		{
			dbSiteProduit.structArt art=new structArt();
			Global.dbProduit.getProduit(codeart, art, new StringBuilder());
			ent.FIELD_LIGNEMVTS_CODABAR=art.EAN;
			ent.FIELD_LIGNEMVTS_COMMENT1="";
			ent.FIELD_LIGNEMVTS_DATE=Fonctions.getYYYYMMDDhhmmss();
			ent.FIELD_LIGNEMVTS_DEPOTSRC=depotsrc;
			ent.FIELD_LIGNEMVTS_DEPOTDST=depotdst;
			ent.FIELD_LIGNEMVTS_DESIGNATION=art.NOMART1;
			ent.FIELD_LIGNEMVTS_DLC="";
			ent.FIELD_LIGNEMVTS_LOT="";
			ent.FIELD_LIGNEMVTS_NUMLIGNE="";
			ent.FIELD_LIGNEMVTS_PROCODE=art.CODART;
			ent.FIELD_LIGNEMVTS_QTEREA="";
			ent.FIELD_LIGNEMVTS_QTEDECH="";
			ent.FIELD_LIGNEMVTS_QTETHEO="0";
			ent.FIELD_LIGNEMVTS_REPCODE=login;
			ent.FIELD_LIGNEMVTS_SERIE="";
			ent.FIELD_LIGNEMVTS_SOCCODE=soccode;
			ent.FIELD_LIGNEMVTS_MVTS_TYPE="1";
			ent.FIELD_LIGNEMVTS_UV=art.PCB;
			cur.close();
			return false;
		}
		cur.close();
		return true;
	}

	
	
	/*
	 * rempli la structure 
	 */
	void fillStructLin(structPassePlat ent,Cursor cur)
	{
		ent.FIELD_LIGNEMVTS_CODABAR 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_CODABAR	);
		ent.FIELD_LIGNEMVTS_COMMENT1 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_COMMENT1 	);
		ent.FIELD_LIGNEMVTS_DATE 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_DATE 	);
		ent.FIELD_LIGNEMVTS_DESIGNATION 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_DESIGNATION 	);
		ent.FIELD_LIGNEMVTS_LOT 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_LOT 	);
		ent.FIELD_LIGNEMVTS_NUMLIGNE 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_NUMLIGNE 	);
		ent.FIELD_LIGNEMVTS_PROCODE 		=this.giveFld(cur,this.FIELD_LIGNEMVTS_PROCODE 		);
		ent.FIELD_LIGNEMVTS_QTEREA 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_QTEREA 	);
		ent.FIELD_LIGNEMVTS_REPCODE 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_REPCODE 	);
		ent.FIELD_LIGNEMVTS_SERIE 	=this.giveFld(cur,this.FIELD_LIGNEMVTS_SERIE 	);
		ent.FIELD_LIGNEMVTS_SOCCODE =this.giveFld(cur,this.FIELD_LIGNEMVTS_SOCCODE );
		ent.FIELD_LIGNEMVTS_QTEDECH 			=this.giveFld(cur,this.FIELD_LIGNEMVTS_QTEDECH);
		ent.FIELD_LIGNEMVTS_UV 			=this.giveFld(cur,this.FIELD_LIGNEMVTS_UV 			);
		ent.FIELD_LIGNEMVTS_DEPOTSRC			=this.giveFld(cur,this.FIELD_LIGNEMVTS_DEPOTSRC 			);
		ent.FIELD_LIGNEMVTS_DEPOTDST			=this.giveFld(cur,this.FIELD_LIGNEMVTS_DEPOTDST 			);
		ent.FIELD_LIGNEMVTS_MVTS_TYPE			=this.giveFld(cur,this.FIELD_LIGNEMVTS_MVTS_TYPE 			);
		ent.FIELD_LIGNEMVTS_QTETHEO			=this.giveFld(cur,this.FIELD_LIGNEMVTS_QTETHEO 			);
			

	}
	/**
	 * charge toutes les lignes d'une commande
	 * @author Marc VOUAUX
	 * @param ent
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public List<structPassePlat> load()
	{

		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				
				
				" order by "+
				this.FIELD_LIGNEMVTS_NUMLIGNE;

		List<structPassePlat> lines=new ArrayList<structPassePlat>();
		
		Cursor cur=db.conn.rawQuery (query,null);
		while (cur.moveToNext() )
		{
			structPassePlat ent=new structPassePlat();

			fillStructLin(ent,cur);
			lines.add(ent);
		}
		
		cur.close();
		return lines;
	}
	
	
	

	/**
	 * @author Marc VOUAUX
	 * @param ent
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public boolean save(structPassePlat ent,String codeart, StringBuffer stBuf)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME_TEMP2+
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+

					" and "+
					this.FIELD_LIGNEMVTS_PROCODE+"="+
					"'"+codeart+"'";
			
	

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext())
			{

				query="UPDATE "+TABLENAME_TEMP2+
						" set "+
						this.FIELD_LIGNEMVTS_CODABAR+"="+
						"'"+ent.FIELD_LIGNEMVTS_CODABAR+"',"+
						this.FIELD_LIGNEMVTS_COMMENT1+"="+
						"'"+ent.FIELD_LIGNEMVTS_COMMENT1 +"',"+
						this.FIELD_LIGNEMVTS_DATE+"="+
						"'"+ent.FIELD_LIGNEMVTS_DATE+"',"+
						this.FIELD_LIGNEMVTS_DESIGNATION+"="+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_DESIGNATION)+"',"+
						this.FIELD_LIGNEMVTS_LOT+"="+
						"'"+ent.FIELD_LIGNEMVTS_LOT+"',"+
						this.FIELD_LIGNEMVTS_NUMLIGNE+"="+
						"'"+ent.FIELD_LIGNEMVTS_NUMLIGNE+"',"+
						this.FIELD_LIGNEMVTS_PROCODE+"="+
						"'"+ent.FIELD_LIGNEMVTS_PROCODE+"',"+
						this.FIELD_LIGNEMVTS_QTEREA+"="+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_QTEREA)+"',"+
						this.FIELD_LIGNEMVTS_QTEDECH+"="+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_QTEDECH)+"',"+
						this.FIELD_LIGNEMVTS_REPCODE+"="+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_REPCODE)+"',"+
						this.FIELD_LIGNEMVTS_SERIE+"="+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_SERIE)+"',"+
						this.FIELD_LIGNEMVTS_SOCCODE+"="+
						"'"+ent.FIELD_LIGNEMVTS_SOCCODE+"',"+
						this.FIELD_LIGNEMVTS_DEPOTDST+"="+
						"'"+ent.FIELD_LIGNEMVTS_DEPOTDST+"',"+
						this.FIELD_LIGNEMVTS_DEPOTSRC+"="+
						"'"+ent.FIELD_LIGNEMVTS_DEPOTSRC+"',"+
						this.FIELD_LIGNEMVTS_UV+"="+
						"'"+ent.FIELD_LIGNEMVTS_UV+"' ,"+
						this.FIELD_LIGNEMVTS_MVTS_TYPE+"="+
						"'"+ent.FIELD_LIGNEMVTS_MVTS_TYPE+"' ,"+
						this.FIELD_LIGNEMVTS_QTETHEO+"="+
						"'"+ent.FIELD_LIGNEMVTS_QTETHEO+"' "+
						" where "+
						fld_kd_dat_type+"="+KD_TYPE+
				

		  			" and "+
		  			this.FIELD_LIGNEMVTS_PROCODE+"="+
		  			"'"+codeart+"'";
				
				
				db.conn.execSQL(query);
				
			
			}
			else		  
			{	  		
				query="INSERT INTO " + TABLENAME_TEMP2 +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_LIGNEMVTS_CODABAR 	+","+
						this.FIELD_LIGNEMVTS_COMMENT1 	+","+
						this.FIELD_LIGNEMVTS_DATE 	+","+   	  		
						this.FIELD_LIGNEMVTS_DESIGNATION 	+","+
						this.FIELD_LIGNEMVTS_LOT 	+","+
						this.FIELD_LIGNEMVTS_NUMLIGNE 	+","+
						this.FIELD_LIGNEMVTS_PROCODE 		+","+
						this.FIELD_LIGNEMVTS_QTEREA 	+","+
						this.FIELD_LIGNEMVTS_QTEDECH 	+","+
						this.FIELD_LIGNEMVTS_REPCODE 	+","+
						this.FIELD_LIGNEMVTS_SERIE 	+","+
						this.FIELD_LIGNEMVTS_SOCCODE +","+
						this.FIELD_LIGNEMVTS_DEPOTDST 			+","+
						this.FIELD_LIGNEMVTS_DEPOTSRC 			+","+
						this.FIELD_LIGNEMVTS_UV 		+","+
						this.FIELD_LIGNEMVTS_MVTS_TYPE 		+","+
						this.FIELD_LIGNEMVTS_QTETHEO 		+""+
						
						
						
						") VALUES ("+
						String.valueOf(KD_TYPE)+","+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_CODABAR)	+"',"+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_COMMENT1 )	+"',"+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_DATE )	+"',"+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_DESIGNATION )	+"',"+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_LOT )	+"',"+
						"'"+ent.FIELD_LIGNEMVTS_NUMLIGNE 		+"',"+
						"'"+ent.FIELD_LIGNEMVTS_PROCODE 	+"',"+
						"'"+ent.FIELD_LIGNEMVTS_QTEREA 	+"',"+
						"'"+ent.FIELD_LIGNEMVTS_QTEDECH 	+"',"+
						"'"+ent.FIELD_LIGNEMVTS_REPCODE 	+"',"+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_SERIE )+"',"+
						"'"+MyDB.controlFld(ent.FIELD_LIGNEMVTS_SOCCODE 		)	+"',"+
						"'"+ent.FIELD_LIGNEMVTS_DEPOTDST 		+"',"+
						"'"+ent.FIELD_LIGNEMVTS_DEPOTSRC		+"',"+
						"'"+ent.FIELD_LIGNEMVTS_UV 		+"',"+
						"'"+ent.FIELD_LIGNEMVTS_MVTS_TYPE 		+"',"+
						"'"+ent.FIELD_LIGNEMVTS_QTETHEO 		+"'"+
						
						
						
						")";

				db.conn.execSQL(query);
				//on met � kour le RL

				

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
	
	
	public boolean save2(structPassePlat ent,String codeart, StringBuffer stBuf)
	{
		try
		{
			String query="SELECT * FROM "+
					TABLENAME_TEMP2+
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+

					" and "+
					this.FIELD_LIGNEMVTS_PROCODE+"="+
					"'"+codeart+"'";
			
	

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur.moveToNext())
			{

				query="UPDATE "+TABLENAME_TEMP2+
						" set "+
						
						this.FIELD_LIGNEMVTS_QTETHEO+"="+
						"'"+ent.FIELD_LIGNEMVTS_QTETHEO+"' "+
						" where "+
						fld_kd_dat_type+"="+KD_TYPE+
				

		  			" and "+
		  			this.FIELD_LIGNEMVTS_PROCODE+"="+
		  			"'"+codeart+"'";
				
				
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

	
	public boolean delete(String numcde, String codeart,String numlin,StringBuffer err,String codePanachage)
	{
		try
		{
			
			
			
			String query="DELETE from "+
					TABLENAME_TEMP2+		
					" where "+
					fld_kd_dat_type+"="+KD_TYPE+" and "+
					this.FIELD_LIGNEMVTS_PROCODE+
					"='"+codeart+"' "
					;
		
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
	public boolean clear(boolean inTemp,StringBuilder err)
	{
		try
		{
			String table=TABLENAME;
			if (inTemp)
				table=TABLENAME_TEMP2;
			super.clear(db, KD_TYPE, table, err);
			return true;
		}
		catch(Exception ex)
		{
			err.append(ex.getMessage());
			return false;
		}
	}
	
	/*
	 * si au moins une colonne qt� est renseign�e c'est que l'on a un inventaire en cours
	 */
	public boolean isReaEnCours()
	{
		String query="SELECT * FROM "+
				TABLENAME_TEMP2+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE;
;
		
		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext())
		{
			if (cur!=null)
				cur.close();
			return true;
		}
		if (cur!=null)
			cur.close();
		return false;
	}
	//Reappro  transmis, on met � jour les zones
	public boolean Reset()
	{
		try {
			
			
			//on efface les lignes 
			String query="delete from "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE;
				
					
			db.conn.execSQL(query);
			
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	//On met � jour le champs qte theo avant transfert
	public void UpdateQteTheo(Context act)
	{
		List<structPassePlat> arts=Global.dbKDLinTransfert.load();
		for (int i=0;i<arts.size();i++)
		{
			
			dbKD543LinInventaire.structPassePlat pp = new dbKD543LinInventaire.structPassePlat();
			Global.dbKDLinInv.load(pp, arts.get(i).FIELD_LIGNEMVTS_PROCODE, new StringBuffer(),
					Preferences.getValue(act, Espresso.DEPOT,"0"),
					Preferences.getValue(act, Espresso.LOGIN,"0"),
					Preferences.getValue(act, Espresso.CODE_SOCIETE,"0"));
			
			dbKD544LinMvtsStock.structPassePlat lin = new dbKD544LinMvtsStock.structPassePlat();
			Global.dbKDLinTransfert.load(lin, arts.get(i).FIELD_LIGNEMVTS_PROCODE, new StringBuffer(),
					Preferences.getValue(act, Espresso.DEPOT,"0"),
					"1",
					"1",
					Preferences.getValue(act, Espresso.LOGIN,"0"),
					Preferences.getValue(act, Espresso.CODE_SOCIETE,"0"));
			
			
			
			int qteStock=Fonctions.convertToInt( pp.FIELD_LIGNEINV_QTETHEO)+Fonctions.convertToInt( lin.FIELD_LIGNEMVTS_QTEREA)-Fonctions.convertToInt( lin.FIELD_LIGNEMVTS_QTEDECH);
			pp.FIELD_LIGNEINV_QTETHEO=qteStock+"";
			lin.FIELD_LIGNEMVTS_QTETHEO=qteStock+"";
			
			Global.dbKDLinInv.save2(pp, arts.get(i).FIELD_LIGNEMVTS_PROCODE, new StringBuffer());
			Global.dbKDLinTransfert.save2(lin, arts.get(i).FIELD_LIGNEMVTS_PROCODE, new StringBuffer());
			
			
		}
						
	}
	public String StockTheorique(Context act,String codearticle,String qteReappro,String qteDechargement)
	{
		String stocktheo="";
		dbKD543LinInventaire.structPassePlat pp;
		dbKD544LinMvtsStock.structPassePlat lin;
		int qteStock;
		try {
			pp = new dbKD543LinInventaire.structPassePlat();
			Global.dbKDLinInv.load(pp, codearticle, new StringBuffer(),
					Preferences.getValue(act, Espresso.DEPOT,"0"),
					Preferences.getValue(act, Espresso.LOGIN,"0"),
					Preferences.getValue(act, Espresso.CODE_SOCIETE,"0"));
			
			
			qteStock = Fonctions.convertToInt( pp.FIELD_LIGNEINV_QTETHEO)+Fonctions.convertToInt(qteReappro)-Fonctions.convertToInt( qteDechargement);
			stocktheo =Fonctions.getInToStringDanem(qteStock);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocktheo;
		
		
	}

	

}
