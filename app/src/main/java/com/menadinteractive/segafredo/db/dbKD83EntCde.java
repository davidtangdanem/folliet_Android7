package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;

/**
 * Vue Entete de cde dans la table KD
 * 
 * @author Marc VOUAUX
 * 
 */
public class dbKD83EntCde extends dbKD {

	public final static int KD_TYPE = 83;
	public final String FIELD_ENTCDE_SOCCODE = this.fld_kd_soc_code;
	public final static String FIELD_ENTCDE_CODEREP = fld_kd_dat_idx01;
	public final static String FIELD_ENTCDE_DATELIVRSOUHAITE = fld_kd_dat_idx02;//Date de livraison souhaité
	public final static String FIELD_ENTCDE_ACCOMPTE = fld_kd_dat_idx08;// Accompte
	public final static String FIELD_ENTCDE_REPSELECT = fld_kd_dat_idx09;// representant																	// s�lectionn�
	public final static String FIELD_ENTCDE_TYPEDOC2 = fld_kd_dat_idx10;// typecde 2
	public final static String FIELD_ENTCDE_CODECLI = fld_kd_cli_code;// code cli
	public final static String FIELD_ENTCDE_CODECDE = fld_kd_cde_code;// numcde
	public final static String FIELD_ENTCDE_NOMCLI = fld_kd_dat_data01;
	public final static String FIELD_ENTCDE_CODECDE_SPE = fld_kd_dat_data02;
	public final static String FIELD_ENTCDE_REFCDE = fld_kd_dat_data03;// refcde
	public final static String FIELD_ENTCDE_COMMENTCDE = fld_kd_dat_data04;// //commenataire1																	// +//commenataire2+commentaire3+commentaire4
	public final static String FIELD_ENTCDE_ETAT = fld_kd_dat_data05;// TYPEVIS
	public final static String FIELD_ENTCDE_SEND = fld_kd_dat_data06;// CDE SEND ou pAS
	public final static String FIELD_ENTCDE_TYPEDOC = fld_kd_dat_data07;// TYPECDE
	public final static String FIELD_ENTCDE_DATECDE = fld_kd_dat_data08;// datedecde
	public final static String FIELD_ENTCDE_DATELIVR = fld_kd_dat_data09; // dateliv
	public final static String FIELD_ENTCDE_EMAILCLI = fld_kd_dat_data10; // Email																	// client
	public final static String FIELD_ENTCDE_ISPRINT = fld_kd_dat_data11;// 1/0
	public final static String FIELD_ENTCDE_CONDLIVR = fld_kd_dat_data12;
	public final static String FIELD_ENTCDE_ISRELIQUAT = fld_kd_dat_data13;// O/N
	public final static String FIELD_ENTCDE_CODECDEMERE = fld_kd_dat_data14;// numero de la cde de base lors de la duplication
	public final static String FIELD_ENTCDE_NBLIVR = fld_kd_dat_data15;//
	public final static String FIELD_ENTCDE_ISEDITSURFACT = fld_kd_dat_data16;//
	public final static String FIELD_ENTCDE_ADRLIV = fld_kd_dat_data17;// numadr
	public final static String FIELD_ENTCDE_DATEHEUREDEBUT = fld_kd_dat_data18;//
	public final static String FIELD_ENTCDE_DATEHEUREFIN = fld_kd_dat_data19;//
	public final static String FIELD_ENTCDE_REMISE = fld_kd_dat_data20;// //ne pas																// utiliser
	public final static String FIELD_ENTCDE_ESCOMPTE = fld_kd_dat_data21;//
	public final static String FIELD_ENTCDE_R_REGL = fld_kd_dat_data22;//
	public final static String FIELD_ENTCDE_R_COND = fld_kd_dat_data23;//
	public final static String FIELD_ENTCDE_R_NBJOURS = fld_kd_dat_data24;//
	public final static String FIELD_ENTCDE_R_CODEREGL = fld_kd_dat_data25;// =Concat
	public final static String FIELD_ENTCDE_ECHEANCE = fld_kd_dat_data26;//
	public final static String FIELD_ENTCDE_PORT = fld_kd_dat_data27;// mode de livraison
	public final static String FIELD_ENTCDE_DEPOT = fld_kd_dat_data28;//
	public final static String FIELD_ENTCDE_VERSION = fld_kd_dat_data29;// version de																	// l'application
	public final static String FIELD_ENTCDE_WARNING = fld_kd_dat_idx07;// ajout d'un warning pour les clients à risque
	public final static String FIELD_ENTCDE_MNTHT = fld_kd_val_data31; // montantht
	public final static String FIELD_ENTCDE_MNTTVA1 = fld_kd_val_data32;// montant tva
	public final static String FIELD_ENTCDE_MNTTVA2 = fld_kd_val_data33;
	public final static String FIELD_ENTCDE_MNTTC = fld_kd_val_data34; // montant ttc
	public final static String FIELD_ENTCDE_REMISE1 = fld_kd_val_data35;
	public final static String FIELD_ENTCDE_REMISE2 = fld_kd_val_data36;
	public final static String FIELD_ENTCDE_VALIDATION_FACTURE = fld_kd_val_data37; //0 non validée, 1 validée

	MyDB db;

	public dbKD83EntCde(MyDB _db) {
		super();
		db = _db;
	}

	/**
	 * 
	 * compte le nombre de cde
	 */
	public int Count() {

		try {
			String query = "select count(*) from " + TABLENAME + " where "
					+ fld_kd_dat_type + "='" + KD_TYPE + "'";

			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext()) {
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		} catch (Exception ex) {
			return -1;
		}

	}
	//Pour le dashboard, affichage des doc a envoyer uniquement (cas du pause en cde reassort)
	public int CountToSend() {

		try {
			String query = "select count(*) from " + TABLENAME + " where "
					+ fld_kd_dat_type + "='" + KD_TYPE + "' AND "+FIELD_ENTCDE_SEND+"='1' ";

			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext()) {
				int i =cur.getInt(0);
				if (cur!=null)
					cur.close();
				return i;
			}
			if (cur!=null)
				cur.close();
			return 0;
		} catch (Exception ex) {
			return -1;
		}

	}

	/*
	 * compte le nombre de cde pour un client
	 */
	public int Count(String codecli) {

		try {
			String query = "select count(*) from " + TABLENAME + " where "
					+ fld_kd_dat_type + "='" + KD_TYPE + "'" + " and "
					+ this.FIELD_ENTCDE_CODECLI + "='" + codecli + "'";

			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext()) {
				int count=cur.getInt(0);
				if (cur!=null)
					cur.close();
				return count;
			}
			if (cur!=null)
				cur.close();
			return 0;
		} catch (Exception ex) {
			return -1;
		}

	}

	public String isNumcdeExist(String numcde) {
		 
		boolean existe = true;
 
		int i = 0;
		while (existe == true) {
			i++;

			

			existe = false;
			/*
			 * Check dans Kems_data si il existe d�j�
			 */
			String query = "";
			query = "select * from " + dbKD.TABLENAME + " where "
					+ dbKD.fld_kd_dat_type + "='" + KD_TYPE + "' AND "
					+ dbKD.fld_kd_cde_code + "='" +  numcde + "'";

			// dbKD40CompteurTarif dbCompteur=new dbKD40CompteurTarif(db);

			Cursor cur = db.conn.rawQuery(query, null);
			if (cur != null) {
				if (cur.moveToNext()) {
					if (cur!=null)
						cur.close();
					existe = true;
				}

			}
			if (cur!=null)
				cur.close();

		}

		return  numcde;
	}

	 
	/**
	 * Verifie si la commande existe bien dans Kems_data
	 * 
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public boolean Existe(String numcde, StringBuffer stBuf) {
		boolean bres = false;
		if (numcde.equals(""))
			return true;

		String query = "SELECT * FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_ENTCDE_CODECDE + "=" + "'" + numcde + "'";

		Cursor cur = db.conn.rawQuery(query, null);
		if (cur.moveToNext() && !numcde.equals("")) {
			bres = true;
		} else
			return bres;
		if (cur!=null)
			cur.close();
		return bres;
	}

	 static public class structEntCde {
		public structEntCde() {
			SOCCODE = "";
			CODEREP = "";
			CODECLI = "";
			CODECDE = "";
			NOMCLI = "";
			;
			CODECDE_SPE = "";
			REFCDE = "";
			COMMENTCDE = "";
			ETAT = "";
			SEND = "";
			TYPEDOC = "";
			DATECDE = "";
			DATELIVR = "";
			DATELIVRSOUHAITE="";
			ISPRINT = "";
			ISRELIQUAT = "0";
			CODECDEMERE = "";
			ADRLIV = "";
			MNTHT = 0;
			MNTTVA1 = 0;
			MNTTVA2 = 0;
			MNTTC = 0;
			REMISE1 = 0;
			REMISE2 = 0;
			NBLIVR = "1";
			ISEDITSURFACT = "0";

			REMISE = "";
			ESCOMPTE = "";
			R_REGL = "";
			R_COND = "";
			R_NBJOURS = "";
			R_CODEREGL = "";
			ECHEANCE = "";
			PORT = "";
			DEPOT = "";
			VERSION = "";
			WARNING = "" ;
			VALIDATION_FACTURE = "";
			MODE_LIVRAISON = "";

		}

		public String SOCCODE;
		public String CODEREP;
		public String CODECLI;
		public String CODECDE;
		public String REPSELECT;
		public String NOMCLI;
		public String CODECDE_SPE;
		public String REFCDE;
		public String COMMENTCDE;
		public String ETAT;
		public String SEND;
		public String TYPEDOC;
		public String TYPEDOC2;
		public String DATECDE;
		public String DATELIVR;
		public String DATELIVRSOUHAITE;
		public String EMAILCLI;
		public String ISPRINT;
		public String ISRELIQUAT;
		public String CODECDEMERE;
		public String ADRLIV;
		public String NBLIVR;
		public String ISEDITSURFACT;
		public float MNTHT;
		public float MNTTVA1;
		public float MNTTVA2;
		public float MNTTC;
		public float REMISE1;
		public float REMISE2;
		public String DATEHEUREDEBUT;
		public String DATEHEUREFIN;
		public String REMISE;
		public String ESCOMPTE;
		public String R_REGL;
		public String R_COND;
		public String R_NBJOURS;
		public String R_CODEREGL;
		public String ECHEANCE;
		public String PORT;
		public String DEPOT;
		 public String VERSION;
		 public String WARNING;
		public String VALIDATION_FACTURE;
		public String MODE_LIVRAISON;
	}

	/**
	 * @author Marc VOUAUX
	 * @param ent
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public boolean load(structEntCde ent, String numcde, StringBuffer stBuf,boolean inTmp) {
		if (numcde.equals(""))
			return true;
		
		String table=TABLENAME;
		if (inTmp)
			table=TABLENAME_DUPLICATA;

		

		String query = "SELECT * FROM " + table + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_ENTCDE_CODECDE + "=" + "'" + numcde + "'";

		Cursor cur = db.conn.rawQuery(query, null);
		if (cur.moveToNext() && !numcde.equals("")) {
			ent.SOCCODE = this.giveFld(cur, this.FIELD_ENTCDE_SOCCODE);
			ent.CODECLI = this.giveFld(cur, this.FIELD_ENTCDE_CODECLI);
			ent.CODECDE = this.giveFld(cur, this.FIELD_ENTCDE_CODECDE);
			ent.REPSELECT = this.giveFld(cur, this.FIELD_ENTCDE_REPSELECT);
			ent.NOMCLI = this.giveFld(cur, this.FIELD_ENTCDE_NOMCLI);
			ent.CODECDE_SPE = this.giveFld(cur, this.FIELD_ENTCDE_CODECDE_SPE);
			ent.REFCDE = this.giveFld(cur, this.FIELD_ENTCDE_REFCDE);
			ent.COMMENTCDE = this.giveFld(cur, this.FIELD_ENTCDE_COMMENTCDE);
			ent.ETAT = this.giveFld(cur, this.FIELD_ENTCDE_ETAT);
			ent.SEND = this.giveFld(cur, this.FIELD_ENTCDE_SEND);
			ent.CODEREP = this.giveFld(cur, this.FIELD_ENTCDE_CODEREP);
			ent.TYPEDOC = this.giveFld(cur, this.FIELD_ENTCDE_TYPEDOC);
			ent.TYPEDOC2 = this.giveFld(cur, this.FIELD_ENTCDE_TYPEDOC2);
			ent.DATECDE = this.giveFld(cur, this.FIELD_ENTCDE_DATECDE);
			ent.DATELIVR = this.giveFld(cur, this.FIELD_ENTCDE_DATELIVR);
			ent.DATELIVRSOUHAITE = this.giveFld(cur, this.FIELD_ENTCDE_DATELIVRSOUHAITE);
			ent.EMAILCLI = this.giveFld(cur, this.FIELD_ENTCDE_EMAILCLI);
			ent.ISPRINT = this.giveFld(cur, this.FIELD_ENTCDE_ISPRINT);
			// ent.CONDLIVR=this.giveFld(cur,this.FIELD_ENTCDE_CONDLIVR);
			ent.ISRELIQUAT = this.giveFld(cur, this.FIELD_ENTCDE_ISRELIQUAT);
			ent.ISEDITSURFACT = this.giveFld(cur,
					this.FIELD_ENTCDE_ISEDITSURFACT);
			ent.CODECDEMERE = this.giveFld(cur, this.FIELD_ENTCDE_CODECDEMERE);
			ent.ADRLIV = this.giveFld(cur, this.FIELD_ENTCDE_ADRLIV);
			ent.DATEHEUREDEBUT = this.giveFld(cur,
					this.FIELD_ENTCDE_DATEHEUREDEBUT);
			ent.DATEHEUREFIN = this
					.giveFld(cur, this.FIELD_ENTCDE_DATEHEUREFIN);
			ent.REMISE = this.giveFld(cur, this.FIELD_ENTCDE_REMISE);
			ent.ESCOMPTE = this.giveFld(cur, this.FIELD_ENTCDE_ESCOMPTE);
			ent.R_REGL = this.giveFld(cur, this.FIELD_ENTCDE_R_REGL);
			ent.R_COND = this.giveFld(cur, this.FIELD_ENTCDE_R_COND);
			ent.R_NBJOURS = this.giveFld(cur, this.FIELD_ENTCDE_R_NBJOURS);
			ent.R_CODEREGL = this.giveFld(cur, this.FIELD_ENTCDE_R_CODEREGL);
			ent.ECHEANCE = this.giveFld(cur, this.FIELD_ENTCDE_ECHEANCE);
			ent.PORT = this.giveFld(cur, this.FIELD_ENTCDE_PORT);
			ent.DEPOT = this.giveFld(cur, this.FIELD_ENTCDE_DEPOT);
			ent.VERSION = this.giveFld(cur, this.FIELD_ENTCDE_VERSION);
			ent.WARNING = this.giveFld(cur, this.FIELD_ENTCDE_WARNING);
			ent.MNTHT = Fonctions.convertToFloat(this.giveFld(cur,
					this.FIELD_ENTCDE_MNTHT));
			ent.MNTTVA1 = Fonctions.convertToFloat(this.giveFld(cur,
					this.FIELD_ENTCDE_MNTTVA1));
			ent.MNTTVA2 = Fonctions.convertToFloat(this.giveFld(cur,
					this.FIELD_ENTCDE_MNTTVA2));
			ent.MNTTC = Fonctions.convertToFloat(this.giveFld(cur,
					this.FIELD_ENTCDE_MNTTC));
			ent.REMISE1 = Fonctions.convertToFloat(this.giveFld(cur,
					this.FIELD_ENTCDE_REMISE1));
			ent.REMISE2 = Fonctions.convertToFloat(this.giveFld(cur,
					this.FIELD_ENTCDE_REMISE2));
			ent.NBLIVR = this.giveFld(cur, this.FIELD_ENTCDE_NBLIVR);
			ent.VALIDATION_FACTURE = this.giveFld(cur, this.FIELD_ENTCDE_VALIDATION_FACTURE);

		} else
		{
			if (cur!=null)
				cur.close();
			return false;
		}
		if (cur!=null)
			cur.close();
			
		return true;
	}
	
	public structEntCde getEntete(Cursor cur){
		structEntCde ent = new structEntCde();
		
		ent.SOCCODE = this.giveFld(cur, this.FIELD_ENTCDE_SOCCODE);
		ent.CODECLI = this.giveFld(cur, this.FIELD_ENTCDE_CODECLI);
		ent.CODECDE = this.giveFld(cur, this.FIELD_ENTCDE_CODECDE);
		ent.REPSELECT = this.giveFld(cur, this.FIELD_ENTCDE_REPSELECT);
		ent.NOMCLI = this.giveFld(cur, this.FIELD_ENTCDE_NOMCLI);
		ent.CODECDE_SPE = this.giveFld(cur, this.FIELD_ENTCDE_CODECDE_SPE);
		ent.REFCDE = this.giveFld(cur, this.FIELD_ENTCDE_REFCDE);
		ent.COMMENTCDE = this.giveFld(cur, this.FIELD_ENTCDE_COMMENTCDE);
		ent.ETAT = this.giveFld(cur, this.FIELD_ENTCDE_ETAT);
		ent.SEND = this.giveFld(cur, this.FIELD_ENTCDE_SEND);
		ent.CODEREP = this.giveFld(cur, this.FIELD_ENTCDE_CODEREP);
		ent.TYPEDOC = this.giveFld(cur, this.FIELD_ENTCDE_TYPEDOC);
		ent.TYPEDOC2 = this.giveFld(cur, this.FIELD_ENTCDE_TYPEDOC2);
		ent.DATECDE = this.giveFld(cur, this.FIELD_ENTCDE_DATECDE);
		ent.DATELIVR = this.giveFld(cur, this.FIELD_ENTCDE_DATELIVR);
		ent.DATELIVRSOUHAITE = this.giveFld(cur, this.FIELD_ENTCDE_DATELIVRSOUHAITE);
		ent.EMAILCLI = this.giveFld(cur, this.FIELD_ENTCDE_EMAILCLI);
		ent.ISPRINT = this.giveFld(cur, this.FIELD_ENTCDE_ISPRINT);
		// ent.CONDLIVR=this.giveFld(cur,this.FIELD_ENTCDE_CONDLIVR);
		ent.ISRELIQUAT = this.giveFld(cur, this.FIELD_ENTCDE_ISRELIQUAT);
		ent.ISEDITSURFACT = this.giveFld(cur,
				this.FIELD_ENTCDE_ISEDITSURFACT);
		ent.CODECDEMERE = this.giveFld(cur, this.FIELD_ENTCDE_CODECDEMERE);
		ent.ADRLIV = this.giveFld(cur, this.FIELD_ENTCDE_ADRLIV);
		ent.DATEHEUREDEBUT = this.giveFld(cur,
				this.FIELD_ENTCDE_DATEHEUREDEBUT);
		ent.DATEHEUREFIN = this
				.giveFld(cur, this.FIELD_ENTCDE_DATEHEUREFIN);
		ent.REMISE = this.giveFld(cur, this.FIELD_ENTCDE_REMISE);
		ent.ESCOMPTE = this.giveFld(cur, this.FIELD_ENTCDE_ESCOMPTE);
		ent.R_REGL = this.giveFld(cur, this.FIELD_ENTCDE_R_REGL);
		ent.R_COND = this.giveFld(cur, this.FIELD_ENTCDE_R_COND);
		ent.R_NBJOURS = this.giveFld(cur, this.FIELD_ENTCDE_R_NBJOURS);
		ent.R_CODEREGL = this.giveFld(cur, this.FIELD_ENTCDE_R_CODEREGL);
		ent.ECHEANCE = this.giveFld(cur, this.FIELD_ENTCDE_ECHEANCE);
		ent.PORT = this.giveFld(cur, this.FIELD_ENTCDE_PORT);
		ent.DEPOT = this.giveFld(cur, this.FIELD_ENTCDE_DEPOT);
		ent.VERSION = this.giveFld(cur, this.FIELD_ENTCDE_VERSION);
		ent.WARNING = this.giveFld(cur, this.FIELD_ENTCDE_WARNING);
		ent.MNTHT = Fonctions.convertToFloat(this.giveFld(cur,
				this.FIELD_ENTCDE_MNTHT));
		ent.MNTTVA1 = Fonctions.convertToFloat(this.giveFld(cur,
				this.FIELD_ENTCDE_MNTTVA1));
		ent.MNTTVA2 = Fonctions.convertToFloat(this.giveFld(cur,
				this.FIELD_ENTCDE_MNTTVA2));
		ent.MNTTC = Fonctions.convertToFloat(this.giveFld(cur,
				this.FIELD_ENTCDE_MNTTC));
		ent.REMISE1 = Fonctions.convertToFloat(this.giveFld(cur,
				this.FIELD_ENTCDE_REMISE1));
		ent.REMISE2 = Fonctions.convertToFloat(this.giveFld(cur,
				this.FIELD_ENTCDE_REMISE2));
		ent.NBLIVR = this.giveFld(cur, this.FIELD_ENTCDE_NBLIVR);
		ent.VALIDATION_FACTURE = this.giveFld(cur, this.FIELD_ENTCDE_VALIDATION_FACTURE);
		
		return ent;
	}

	public boolean saveComment(String numcde,String comment)
	{
		String query="";
		try {
			 query="update "+TABLENAME+" set "+
				FIELD_ENTCDE_COMMENTCDE+"='"+MyDB.controlFld(comment)+"' "+
				" where "+
				FIELD_ENTCDE_CODECDE+"='"+numcde+"' "+
				" and "+fld_kd_dat_type+"='"+KD_TYPE+"'";
			
			db.conn.execSQL(query);
			Global.dbLog.Insert("ENTETE", "saveComment", numcde,"", query, "", "");
			
			return true;
		} catch (SQLException e) {

		}
		return false;
	}
	
	public boolean saverefcdeclientComment(String numcde,String refcdeclient)
	{
		String query="";
		try {
			 query="update "+TABLENAME+" set "+
				FIELD_ENTCDE_REFCDE+"='"+MyDB.controlFld(refcdeclient)+"' "+
				" where "+
				FIELD_ENTCDE_CODECDE+"='"+numcde+"' "+
				" and "+fld_kd_dat_type+"='"+KD_TYPE+"'";
			
			db.conn.execSQL(query);
			Global.dbLog.Insert("ENTETE", "saverefcdeclientComment", numcde, "", query, "", "");
			
			return true;
		} catch (SQLException e) {

		}
		return false;
	}
	
	
	public String getCommentFromNumCde(String numcde){
		String query = "SELECT "+FIELD_ENTCDE_COMMENTCDE+" FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_ENTCDE_CODECDE + "=" + "'" + numcde + "'";
		
		String comment = null;
		
		try{
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur != null && cur.moveToNext()) {
				comment = giveFld(cur, this.FIELD_ENTCDE_COMMENTCDE);
				if (cur!=null)
					cur.close();
			}
			if (cur!=null)
				cur.close();
		}catch(Exception ex){
			
		}
		
		return comment;
	}

	public String getRefcdeclientCommentFromNumCde(String numcde){
		String query = "SELECT "+FIELD_ENTCDE_REFCDE+" FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_ENTCDE_CODECDE + "=" + "'" + numcde + "'";
		
		String comment = null;
		
		try{
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur != null && cur.moveToNext()) {
				comment = giveFld(cur, this.FIELD_ENTCDE_REFCDE);
				if (cur!=null)
					cur.close();
			}
			if (cur!=null)
				cur.close();
		}catch(Exception ex){
			
		}
		
		return comment;
	}

	/**
	 * sauvegarde de la cde
	 * 
	 * @author Marc VOUAUX
	 * @param ent
	 * @param numcde
	 * @param stBuf
	 * @return
	 */
	public boolean save(structEntCde ent, String numcde, StringBuffer stBuf) {
		try {
			ent.CODECDE = numcde;
			String query = "SELECT * FROM " + TABLENAME + " where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_ENTCDE_CODECDE + "=" + "'" + numcde + "'";

			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext() && !numcde.equals("")) {
				float oldMntHT = Fonctions.convertToFloat(giveFld(cur,
						this.FIELD_ENTCDE_MNTHT));

				query = "UPDATE "
						+ TABLENAME
						+ " set "
						+ this.FIELD_ENTCDE_REFCDE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.REFCDE)
						+ "',"
						+ this.FIELD_ENTCDE_COMMENTCDE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.COMMENTCDE)
						+ "',"
						+ this.FIELD_ENTCDE_ETAT
						+ "="
						+ "'"
						+ ent.ETAT
						+ "',"
						+ this.FIELD_ENTCDE_SEND
						+ "="
						+ "'"
						+ ent.SEND
						+ "',"
						+ this.FIELD_ENTCDE_CODEREP
						+ "="
						+ "'"
						+ ent.CODEREP
						+ "',"
						+ this.FIELD_ENTCDE_REPSELECT
						+ "="
						+ "'"
						+ ent.REPSELECT
						+ "',"
						+ this.FIELD_ENTCDE_TYPEDOC
						+ "="
						+ "'"
						+ ent.TYPEDOC
						+ "',"
						+ this.FIELD_ENTCDE_TYPEDOC2
						+ "="
						+ "'"
						+ ent.TYPEDOC2
						+ "',"
						+ this.FIELD_ENTCDE_DATECDE
						+ "="
						+ "'"
						+ ent.DATECDE
						+ "',"
						+ this.FIELD_ENTCDE_DATELIVR
						+ "="
						+ "'"
						+ ent.DATELIVR
						+ "',"
						+ this.FIELD_ENTCDE_DATELIVRSOUHAITE
						+ "="
						+ "'"
						+ ent.DATELIVRSOUHAITE
						+ "',"
						+ this.FIELD_ENTCDE_EMAILCLI
						+ "="
						+ "'"
						+ ent.EMAILCLI
						+ "',"
						+ this.FIELD_ENTCDE_ISPRINT
						+ "="
						+ "'"
						+ ent.ISPRINT
						+ "',"
						+
						// this.FIELD_ENTCDE_CONDLIVR+"="+
						// "'"+ent.CONDLIVR+"',"+
						this.FIELD_ENTCDE_ISRELIQUAT + "=" + "'"
						+ ent.ISRELIQUAT + "',"
						+ this.FIELD_ENTCDE_ISEDITSURFACT + "=" + "'"
						+ ent.ISEDITSURFACT + "',"
						+ this.FIELD_ENTCDE_CODECDEMERE + "=" + "'"
						+ ent.CODECDEMERE + "'," + this.FIELD_ENTCDE_ADRLIV
						+ "=" + "'" + MyDB.controlFld(ent.ADRLIV) + "',"
						+ this.FIELD_ENTCDE_DATEHEUREFIN + "=" + "'"
						+ MyDB.controlFld(ent.DATEHEUREFIN) + "',"
						+ this.FIELD_ENTCDE_REMISE + "=" + "'"
						+ MyDB.controlFld(ent.REMISE) + "',"
						+ this.FIELD_ENTCDE_ESCOMPTE + "=" + "'"
						+ MyDB.controlFld(ent.ESCOMPTE) + "',"
						+ this.FIELD_ENTCDE_R_REGL + "=" + "'"
						+ MyDB.controlFld(ent.R_REGL) + "',"
						+ this.FIELD_ENTCDE_R_COND + "=" + "'"
						+ MyDB.controlFld(ent.R_COND) + "',"
						+ this.FIELD_ENTCDE_R_NBJOURS + "=" + "'"
						+ MyDB.controlFld(ent.R_NBJOURS) + "',"
						+ this.FIELD_ENTCDE_R_CODEREGL + "=" + "'"
						+ MyDB.controlFld(ent.R_CODEREGL) + "',"
						+ this.FIELD_ENTCDE_ECHEANCE + "=" + "'"
						+ MyDB.controlFld(ent.ECHEANCE) + "',"
						+ this.FIELD_ENTCDE_PORT + "=" + "'"
						+ MyDB.controlFld(ent.MODE_LIVRAISON) + "',"
						+ this.FIELD_ENTCDE_DEPOT + "=" + "'"
						+ MyDB.controlFld(ent.DEPOT) + "',"
						+ this.FIELD_ENTCDE_VERSION + "=" + "'"
						+ MyDB.controlFld(ent.VERSION) + "',"
						+ this.FIELD_ENTCDE_WARNING + "=" + "'"
						+ MyDB.controlFld(ent.WARNING) + "',"
						+ this.FIELD_ENTCDE_MNTHT + "=" + "'" + ent.MNTHT
						+ "'," + this.FIELD_ENTCDE_MNTTVA1 + "=" + "'"
						+ ent.MNTTVA1 + "'," + this.FIELD_ENTCDE_MNTTVA2 + "="
						+ "'" + ent.MNTTVA2 + "'," + this.FIELD_ENTCDE_MNTTC
						+ "=" + "'" + ent.MNTTC + "',"
						+ this.FIELD_ENTCDE_REMISE1 + "=" + "'" + ent.REMISE1
						+ "'," + this.FIELD_ENTCDE_REMISE2 + "=" + "'"
						+ ent.REMISE2 + "'," + this.FIELD_ENTCDE_NBLIVR + "="
						+ "'" + ent.NBLIVR + "'" + " where "
						+ FIELD_ENTCDE_CODECDE + "='" + numcde + "' " + " and "
						+ dbKD.fld_kd_dat_type + "='" + KD_TYPE + "' ";

				db.conn.execSQL(query);
				Global.dbLog.Insert("ENTETE", "SAVE UPDATE", "", "",query, "", "");
			} else {
				query = "INSERT INTO "
						+ TABLENAME
						+ " ("
						+ dbKD.fld_kd_dat_type
						+ ","
						+ this.FIELD_ENTCDE_SOCCODE
						+ ","
						+ this.FIELD_ENTCDE_CODECDE
						+ ","
						+ this.FIELD_ENTCDE_CODECLI
						+ ","
						+ this.FIELD_ENTCDE_NOMCLI
						+ ","
						+ this.FIELD_ENTCDE_CODECDE_SPE
						+ ","
						+ this.FIELD_ENTCDE_REFCDE
						+ ","
						+ this.FIELD_ENTCDE_COMMENTCDE
						+ ","
						+ this.FIELD_ENTCDE_ETAT
						+ ","
						+ this.FIELD_ENTCDE_SEND
						+ ","
						+ this.FIELD_ENTCDE_CODEREP
						+ ","
						+ this.FIELD_ENTCDE_REPSELECT
						+ ","
						+ this.FIELD_ENTCDE_TYPEDOC
						+ ","
						+ this.FIELD_ENTCDE_TYPEDOC2
						+ ","
						+ this.FIELD_ENTCDE_DATECDE
						+ ","
						+ this.FIELD_ENTCDE_DATELIVR
						+ ","
						+ this.FIELD_ENTCDE_DATELIVRSOUHAITE
						+ ","
						+ this.FIELD_ENTCDE_EMAILCLI
						+ ","
						+ this.FIELD_ENTCDE_ISPRINT
						+ ","
						+
						// this.FIELD_ENTCDE_CONDLIVR+","+
						this.FIELD_ENTCDE_ISRELIQUAT + ","
						+ this.FIELD_ENTCDE_ISEDITSURFACT + ","
						+ this.FIELD_ENTCDE_CODECDEMERE + ","
						+ this.FIELD_ENTCDE_ADRLIV + ","
						+ this.FIELD_ENTCDE_DATEHEUREDEBUT + ","
						+ this.FIELD_ENTCDE_DATEHEUREFIN + ","
						+ this.FIELD_ENTCDE_REMISE + ","
						+ this.FIELD_ENTCDE_ESCOMPTE + ","
						+ this.FIELD_ENTCDE_R_REGL + ","
						+ this.FIELD_ENTCDE_R_COND + ","
						+ this.FIELD_ENTCDE_R_NBJOURS + ","
						+ this.FIELD_ENTCDE_R_CODEREGL + ","
						+ this.FIELD_ENTCDE_ECHEANCE + ","
						+ this.FIELD_ENTCDE_PORT + ","
						+ this.FIELD_ENTCDE_DEPOT + ","
						+ this.FIELD_ENTCDE_VERSION + ","
						+ this.FIELD_ENTCDE_WARNING + ","
						+ this.FIELD_ENTCDE_MNTHT + ","
						+ this.FIELD_ENTCDE_MNTTVA1 + ","
						+ this.FIELD_ENTCDE_MNTTVA2 + ","
						+ this.FIELD_ENTCDE_MNTTC + ","
						+ this.FIELD_ENTCDE_REMISE1 + ","
						+ this.FIELD_ENTCDE_REMISE2 + ","
						+ this.FIELD_ENTCDE_NBLIVR + ","
						+ this.FIELD_ENTCDE_VALIDATION_FACTURE + "" + ") VALUES ("
						+ String.valueOf(KD_TYPE) + "," + "'" + ent.SOCCODE
						+ "'," + "'" + numcde + "'," + "'" + ent.CODECLI + "',"
						+ "'" + MyDB.controlFld(ent.NOMCLI) + "'," + "'"
						+ ent.CODECDE_SPE + "'," + "'"
						+ MyDB.controlFld(ent.REFCDE) + "'," + "'"
						+ MyDB.controlFld(ent.COMMENTCDE) + "'," + "'"
						+ ent.ETAT + "'," + "'" + ent.SEND + "'," + "'"
						+ ent.CODEREP + "',"
						+ "'"
						+ ent.REPSELECT
						+ "',"
						+ "'"
						+ MyDB.controlFld(ent.TYPEDOC)
						+ "',"
						+ "'"
						+ MyDB.controlFld(ent.TYPEDOC2)
						+ "',"
						+ "'"
						+ ent.DATECDE
						+ "',"
						+ "'"
						+ ent.DATELIVR
						+ "',"
						+ "'"
						+ MyDB.controlFld(ent.DATELIVRSOUHAITE)
						+ "',"
						+ "'"
						+ ent.EMAILCLI
						+ "',"
						+ "'"
						+ ent.ISPRINT
						+ "',"
						+
						// "'"+ent.CONDLIVR+"',"+
						"'" + ent.ISRELIQUAT + "'," + "'" + ent.ISEDITSURFACT
						+ "'," + "'" + ent.CODECDEMERE + "'," + "'"
						+ MyDB.controlFld(ent.ADRLIV) + "'," + "'"
						+ MyDB.controlFld(ent.DATEHEUREDEBUT) + "'," + "'"
						+ MyDB.controlFld(ent.DATEHEUREFIN) + "'," + "'"
						+ MyDB.controlFld(ent.REMISE) + "'," + "'"
						+ MyDB.controlFld(ent.ESCOMPTE) + "'," + "'"
						+ MyDB.controlFld(ent.R_REGL) + "'," + "'"
						+ MyDB.controlFld(ent.R_COND) + "'," + "'"
						+ MyDB.controlFld(ent.R_NBJOURS) + "'," + "'"
						+ MyDB.controlFld(ent.R_CODEREGL) + "'," + "'"
						+ MyDB.controlFld(ent.ECHEANCE) + "'," + "'"
						+ MyDB.controlFld(ent.MODE_LIVRAISON) + "'," + "'"
						+ MyDB.controlFld(ent.DEPOT) + "'," + "'"
						+ MyDB.controlFld(ent.VERSION) + "'," + "'"
						+ MyDB.controlFld(ent.WARNING) + "'," + "'" + ent.MNTHT
						+ "'," + "'" + ent.MNTTVA1 + "'," + "'" + ent.MNTTVA2
						+ "'," + "'" + ent.MNTTC + "'," + "'" + ent.REMISE1
						+ "'," + "'" + ent.REMISE2 + "'," + "'" + ent.NBLIVR
						+ "'," + "'0')";

				db.conn.execSQL(query);
				Global.dbLog.Insert("ENTETE", "SAVE INSERT", "", "",query, "", "");

				Log.e("query saveCde",""+query);
			}
			if (cur!=null)
				cur.close();
			
			
		} catch (Exception ex) {
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * efface tout ce qui concerne cette cde
	 * @param numcde
	 * @param codecli
	 * @param err
	 * @return
	 */
	public boolean deleteCde(String numcde, String codecli, StringBuffer err) {
		try {
			String query = "DELETE from " + TABLENAME + " where "
					+ this.FIELD_ENTCDE_CODECDE + "='" + numcde + "'";

			db.conn.execSQL(query);

			Global.dbLog.Insert("ENTETE", "DELETE CDE", "",  query, "","", "");

			return true;
		} catch (Exception ex) {
			err.append(ex.getMessage());
		}
		return false;
	}

	public boolean setPrintOK(String numcde, String codecli ) {
		try {
			String query = "update " + TABLENAME +
					" set "+FIELD_ENTCDE_ISPRINT+"='1'"+
					" where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_ENTCDE_CODECDE + "='" + numcde + "'";

			db.conn.execSQL(query);

			return true;
		} catch (Exception ex) {
		 
		}
		return false;
	}
	//indique si une piece est en attente d'envoi, � cause d'un plantage avant la fin par exemple
		public boolean isPrintOk(String numcde)
		{
			
			String query = "SELECT * FROM " + TABLENAME + " where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_ENTCDE_CODECDE+ "=" + "'"+numcde+"'";
			
			Cursor cur = db.conn.rawQuery(query, null);
			if(cur.moveToNext()){
				structEntCde entete = getEntete(cur);
				if (entete.ISPRINT.equals("1"))
				{
					if (cur!=null)
						cur.close();
					return true;
				}
				else
				{
					if (cur!=null)
						cur.close();
					return false;
				}
						
				
			}
			if (cur!=null)
				cur.close();
			
			return false;
		}
	public boolean deleteAll(StringBuffer err) {
		try {
			String query = "DELETE from " + TABLENAME + " where "
					+ dbKD.fld_kd_dat_type + "=" + KD_TYPE;

			db.conn.execSQL(query);
			return true;
		} catch (Exception ex) {
			err.append(ex.getMessage());
		}
		return false;
	}
	
	/**
	 * Récupération des factures d'un client grâce au code client
	 * @param codeClient
	 * @return ArrayList<structEntCde> 
	 */
	public ArrayList<structEntCde> getEntCdeFromCodeClient(String codeClient){
		ArrayList<structEntCde> entetes = new ArrayList<dbKD83EntCde.structEntCde>();
		
		String query = "SELECT * FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "
				+ this.FIELD_ENTCDE_CODECLI + "=" + "'" + codeClient + "'";
		
		Cursor cur = db.conn.rawQuery(query, null);
		while(cur.moveToNext()){
			structEntCde entete = getEntete(cur);
			entetes.add(entete);
		}
		if (cur!=null)
			cur.close();
		
		return entetes;
	}

	//indique si une piece est en attente d'envoi, � cause d'un plantage avant la fin par exemple
	public structEntCde getPieceNotSend()
	{
		
		String query = "SELECT * FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and ("
				+ this.FIELD_ENTCDE_SEND+ "=" + "'0' or "+this.FIELD_ENTCDE_SEND+" is null)";
		
		Cursor cur = db.conn.rawQuery(query, null);
		if(cur.moveToNext()){
			structEntCde entete = getEntete(cur);
			if (cur!=null)
				cur.close();
			return entete;
		}
		if (cur!=null)
			cur.close();
		
		return null;
	}
	
	public void setValidationState(String numcde, String codecli, String state){
		String query="";
		
		if(state == null || (!state.equals("0") && !state.equals("1"))){
			return;
		}
		
		try {
			 query="update "+TABLENAME+" set "+
				FIELD_ENTCDE_VALIDATION_FACTURE+"='"+MyDB.controlFld(state)+"' "+
				" where "+
				FIELD_ENTCDE_CODECDE+"='"+numcde+"' "+
				" and "+fld_kd_dat_type+"='"+KD_TYPE+"' and "+
				FIELD_ENTCDE_CODECLI+"='"+codecli+"' ";
			
			db.conn.execSQL(query);

			
		} catch (SQLException e) {

		}
	}
	
	public boolean isFactureValide(String numcde){
		String query = "SELECT "+FIELD_ENTCDE_VALIDATION_FACTURE+" FROM " + TABLENAME + " where "
				+ fld_kd_dat_type + "=" + KD_TYPE + " and "+FIELD_ENTCDE_CODECDE+"='"+numcde+"'";
		
		Cursor cur = db.conn.rawQuery(query, null);
		if(cur != null && cur.moveToNext()){
			String state = cur.getString(0);
			if(state != null){
				if(state.equals("1")){
				{
					if (cur!=null)
						cur.close();
					return true;
				}
				}else if(state.equals("0")){
					if (cur!=null)
						cur.close();
					return false;
				}
			}else
			{
				if (cur!=null)
					cur.close();
				return false;
			}
			
		}
		if (cur!=null)
			cur.close();
		return false;
	}
}
