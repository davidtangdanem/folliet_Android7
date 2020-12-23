package com.menadinteractive.segafredo.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import com.menadinteractive.segafredo.communs.DateCode;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.dbKD100Visite.structVisite;
import com.menadinteractive.segafredo.plugins.Espresso;

public class dbKD101ClientVu  extends dbKD{

	public final static int KD_TYPE = 101;
	public final String FIELD_SOC_CODE     			= fld_kd_soc_code;
	public final String FIELD_CODECLI      			= fld_kd_cli_code;
	public final String FIELD_CODEREP    		  	= fld_kd_dat_idx01;
	public final String FIELD_TH         			= fld_kd_dat_idx02;//TH
	public final String FIELD_KH         			= fld_kd_dat_idx03;//KH
	public final String FIELD_REF        		 	= fld_kd_dat_idx04;//REF
	public final String FIELD_CONTACT_CODE         	= fld_kd_dat_idx05;
	public final String FIELD_CONTACT_TITRE         = fld_kd_dat_idx06;
	public final String FIELD_CONTACT_NOM         	= fld_kd_dat_idx07;
	public final String FIELD_ISDEMANDE             = fld_kd_dat_idx08;
	public final String FIELD_TOURNEE           	= fld_kd_dat_idx09;
	public final String FIELD_FAMTYPEACT           	= fld_kd_dat_idx10;
	public final String FIELD_VU         		 	= fld_kd_dat_data01;//0/1
	public final String FIELD_DATE         			= fld_kd_dat_data02;//YYYYMMDD
	public final String FIELD_HEUREDEBUT   			= fld_kd_dat_data03;//hhmm
	public final String FIELD_TYPEACT       		= fld_kd_dat_data04;
	public final String FIELD_COMMENTAIRE   		= fld_kd_dat_data05;
	public final String FIELD_NEXTDATE      		= fld_kd_dat_data06;
	public final String FIELD_HEUREFIN      		= fld_kd_dat_data07;//hhmm
	public final String fIELD_FLAG        			= fld_kd_dat_data08; //1 = modifi�, 2=delete
	public final String fIELD_EVT_ID        		= fld_kd_dat_data09; //evt_id
	public final String FIELD_INFOCOMMERCIALE       = fld_kd_dat_data10; //info commerciale
	public final String FIELD_CHOIX_CLOTUREE        = fld_kd_dat_data11;
	public final String FIELD_CMT_NONCLOTUREE       = fld_kd_dat_data12;
	public final String FIELD_CODE_QUESTIONNAIRE    = fld_kd_dat_data13;
	public final String FIELD_CHOIX_FACTURABLE      = fld_kd_dat_data14;
	public final String FIELD_YN_FACTURABLE         = fld_kd_dat_data15; //( 1 - OUI ; 2 - NON )
	public final String FIELD_NOSERIE         		= fld_kd_dat_data16;
	public final String FIELD_MACHINE_CODEARTICLE   = fld_kd_dat_data17;
	public final String FIELD_MACHINE_LIBARTICLE    = fld_kd_dat_data18;
	public final String FIELD_MACHINE_NUMSERIE      = fld_kd_dat_data19;
	public final String FIELD_MACHINE_CODESYMP      = fld_kd_dat_data20;
	public final String FIELD_MACHINE_LIBSYMP       = fld_kd_dat_data21;
	public final String FIELD_MACHINE_QUI         	= fld_kd_dat_data22;
	public final String FIELD_MACHINE_MARQUE        = fld_kd_dat_data23;
	public final String FIELD_MACHINE_TYPEMACHINE   = fld_kd_dat_data24;
	public final String FIELD_MACHINE_MARQUEMACHINE = fld_kd_dat_data25;
	public final String FIELD_MACHINE_SERIEMACHINE  = fld_kd_dat_data26;
	public final String FIELD_NUM_INTER         	= fld_kd_dat_data27;
	public final String FIELD_VISITEPREVENTIVE      = fld_kd_dat_data28;//Visite préventive
	public final String FIELD_NUMEROBC        		= fld_kd_dat_data29;//N° BC
	public final String FIELD_NUMERORAPPORT         = fld_kd_cde_code;//N° de rapport
	public final String FIELD_CHOIX_AGENT           = fld_kd_dat_data30;
	public final String FIELD_NUM_EVT				=fld_kd_val_data31;
	

	
	
	MyDB db;
	public dbKD101ClientVu(MyDB _db)
	{
		super();
		db=_db;	
	}
	static public class structClientvu {
		public structClientvu()
		{	
			super();

			DATE ="";
			SOC_CODE ="";
			CODECLI ="";
			CODEREP ="";
			VU ="";
			FLAG="";
			DATE="";
			HEUREDEBUT="";
			HEUREFIN="";
			TYPEACT="";
			FAMTYPEACT="";
			COMMENTAIRE="";
			CHOIX_AGENT="";
			NEXTDATE=""; 
			fIELD_EVT_ID="";
			INFOCOMMERCIALE="";
			CHOIX_CLOTUREE         	= ""; 
			CMT_NONCLOTUREE        	= ""; 
			CODE_QUESTIONNAIRE ="";
			CHOIX_FACTURABLE         	= ""; 
			YN_FACTURABLE="";
			NOSERIE="";
			MACHINE_CODEARTICLE         ="";
			MACHINE_LIBARTICLE         	="";
			MACHINE_NUMSERIE         	="";
			MACHINE_CODESYMP         	="";
			MACHINE_LIBSYMP         	="";
			
			MACHINE_QUI         	="";
			MACHINE_MARQUE        ="";
			MACHINE_TYPEMACHINE         	="";
			MACHINE_MARQUEMACHINE         ="";
			MACHINE_SERIEMACHINE         	="";
			NUM_INTER="";
			VISITEPREVENTIVE="";
			NUMEROBC        ="";
			NUMERORAPPORT   ="";
			TH="";
			KH="";
			REF="";
			CONTACT_CODE="";
			CONTACT_TITRE="";
			CONTACT_NOM="";
			IS_DEMANDE = "";
			TOURNEE="";
			NUM_EVT="";

		}
		
		public String DATE  ;
		public String SOC_CODE   ;
		public String CODECLI   ; 
		public String CODEREP        ;
		public String VU        ;
		public String FLAG        ;  
		public String HEUREDEBUT="";
		public String HEUREFIN="";
		public String TYPEACT="";
		public String FAMTYPEACT="";
		public String COMMENTAIRE="";
		public String NEXTDATE="";
		public String fIELD_EVT_ID="";
		public String INFOCOMMERCIALE="";
		public String CHOIX_AGENT="";
		public String CHOIX_CLOTUREE         	= ""; 
		public String CMT_NONCLOTUREE        	= ""; 
		public String CODE_QUESTIONNAIRE ="";
		public String CHOIX_FACTURABLE         	= ""; 
		public String YN_FACTURABLE="";
		public String NOSERIE="";
		public String MACHINE_CODEARTICLE         ="";
		public String MACHINE_LIBARTICLE         	="";
		public String MACHINE_NUMSERIE         	="";
		public String MACHINE_CODESYMP         	="";
		public String MACHINE_LIBSYMP         	="";
		public String MACHINE_QUI         	="";
		public String MACHINE_MARQUE        ="";
		public String MACHINE_TYPEMACHINE         	="";
		public String MACHINE_MARQUEMACHINE         ="";
		public String MACHINE_SERIEMACHINE         	="";
		public String NUM_INTER ="";
		public String VISITEPREVENTIVE="";
		
		public String NUMEROBC="";
		public String NUMERORAPPORT="";
		public String TH="";
		public String KH="";
		public String REF="";
		public String CONTACT_CODE="";
		public String CONTACT_TITRE="";
		public String CONTACT_NOM="";
		public String IS_DEMANDE = "";
		public String TOURNEE="";
		public String NUM_EVT="";
	}
	
	static public class passePlat {
		public passePlat()
		{
			DATE ="";
			SOC_CODE ="";
			CODECLI ="";
			CODEREP ="";
			VU ="";
			FLAG="";
			DATE="";
			HEUREDEBUT="";
			HEUREFIN="";
			TYPEACT="";
			FAMTYPEACT="";
			COMMENTAIRE="";
			NEXTDATE="";
			fIELD_EVT_ID="";
			INFOCOMMERCIALE="";
			CHOIX_CLOTUREE         	= ""; 
			CMT_NONCLOTUREE        	= ""; 
			CODE_QUESTIONNAIRE ="";
			CHOIX_FACTURABLE="";
			YN_FACTURABLE="";
			NOSERIE="";
			MACHINE_CODEARTICLE         ="";
			MACHINE_LIBARTICLE         	="";
			MACHINE_NUMSERIE         	="";
			MACHINE_CODESYMP         	="";
			MACHINE_LIBSYMP         	="";
			MACHINE_QUI         	="";
			MACHINE_MARQUE        ="";
			MACHINE_TYPEMACHINE         	="";
			MACHINE_MARQUEMACHINE         ="";
			MACHINE_SERIEMACHINE         	="";
			NUM_INTER="";
			VISITEPREVENTIVE="";
			NUMEROBC="";
			NUMERORAPPORT="";
			TH="";
			KH="";
			REF="";
			CONTACT_CODE="";
			CONTACT_TITRE="";
			CONTACT_NOM="";
			IS_DEMANDE="";
			TOURNEE="";
			NUM_EVT="";
		}

		public String DATE  ;
		public String SOC_CODE   ;
		public String CODECLI   ; 
		public String CODEREP        ;
		public String VU        ;
		public String FLAG        ;  
		public String HEUREDEBUT="";
		public String HEUREFIN="";
		public String TYPEACT="";
		public String FAMTYPEACT="";
		public String COMMENTAIRE="";
		public String NEXTDATE="";
		public String fIELD_EVT_ID="";
		public String INFOCOMMERCIALE="";
		public String CHOIX_CLOTUREE         	= ""; 
		public String  CMT_NONCLOTUREE        	= ""; 
		public String  CODE_QUESTIONNAIRE ="";
		public String CHOIX_FACTURABLE="";
		public String YN_FACTURABLE="";
		public String NOSERIE="";
		public String MACHINE_CODEARTICLE         ="";
		public String MACHINE_LIBARTICLE         	="";
		public String MACHINE_NUMSERIE         	="";
		public String MACHINE_CODESYMP         	="";
		public String MACHINE_LIBSYMP         	="";
		public String MACHINE_QUI         	="";
		public String MACHINE_MARQUE        ="";
		public String MACHINE_TYPEMACHINE         	="";
		public String MACHINE_MARQUEMACHINE         ="";
		public String MACHINE_SERIEMACHINE         	="";
		public String NUM_INTER="";
		public String VISITEPREVENTIVE="";
		public String NUMEROBC="";
		public String NUMERORAPPORT="";
		
		public String TH="";
		public String KH="";
		public String REF="";
		public String CONTACT_CODE="";
		public String CONTACT_TITRE="";
		public String CONTACT_NOM="";
		public String IS_DEMANDE = "";
		public String TOURNEE="";
		public String NUM_EVT="";
		
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
				int i=0;
				i=cur.getInt(0);
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
	public int countModified()
	{
		try
		{
			String query="select count(*) from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'"+
					" and "+
					this.fIELD_FLAG+"<>'"+ KDSYNCHRO_RESET+  "'" ;
					
			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				int i=0;
				i=cur.getInt(0);
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
	public boolean _load(structClientvu ent,String stcodeclient, String date, StringBuffer stBuf)
	{
		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'";
		
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			ent.SOC_CODE =this.giveFld(cur,this.FIELD_SOC_CODE );	
			ent.CODECLI =this.giveFld(cur,this.FIELD_CODECLI );
			ent.CODEREP =this.giveFld(cur,this.FIELD_CODEREP );
			ent.VU =this.giveFld(cur,this.FIELD_VU );
			ent.DATE =this.giveFld(cur,this.FIELD_DATE );
			ent.HEUREDEBUT =this.giveFld(cur,this.FIELD_HEUREDEBUT );
			ent.HEUREFIN =this.giveFld(cur,this.FIELD_HEUREFIN );		
			ent.TYPEACT =this.giveFld(cur,this.FIELD_TYPEACT );
			ent.FAMTYPEACT =this.giveFld(cur,this.FIELD_FAMTYPEACT );
			ent.COMMENTAIRE =this.giveFld(cur,this.FIELD_COMMENTAIRE );
			ent.NEXTDATE =this.giveFld(cur,this.FIELD_NEXTDATE );
			ent.FLAG=this.giveFld(cur,this.fIELD_FLAG);
			ent.fIELD_EVT_ID=this.giveFld(cur,this.fIELD_EVT_ID);
			ent.INFOCOMMERCIALE=this.giveFld(cur,this.FIELD_INFOCOMMERCIALE);
			ent.CHOIX_CLOTUREE=this.giveFld(cur,this.FIELD_CHOIX_CLOTUREE);
			ent.CMT_NONCLOTUREE=this.giveFld(cur,this.FIELD_CMT_NONCLOTUREE);
			ent.CODE_QUESTIONNAIRE=this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE);
			ent.CHOIX_FACTURABLE=this.giveFld(cur,this.FIELD_CHOIX_FACTURABLE);
			ent.YN_FACTURABLE=this.giveFld(cur,this.FIELD_YN_FACTURABLE);
			ent.NOSERIE=this.giveFld(cur,this.FIELD_NOSERIE);
			ent.MACHINE_CODEARTICLE=this.giveFld(cur,this.FIELD_MACHINE_CODEARTICLE);
			ent.MACHINE_LIBARTICLE=this.giveFld(cur,this.FIELD_MACHINE_LIBARTICLE);
			ent.MACHINE_NUMSERIE=this.giveFld(cur,this.FIELD_MACHINE_NUMSERIE);
			ent.MACHINE_CODESYMP=this.giveFld(cur,this.FIELD_MACHINE_CODESYMP);
			ent.MACHINE_LIBSYMP=this.giveFld(cur,this.FIELD_MACHINE_LIBSYMP);
			ent.MACHINE_QUI=this.giveFld(cur,this.FIELD_MACHINE_QUI);
			ent.MACHINE_MARQUE=this.giveFld(cur,this.FIELD_MACHINE_MARQUE);
			ent.MACHINE_TYPEMACHINE=this.giveFld(cur,this.FIELD_MACHINE_TYPEMACHINE);
			ent.MACHINE_MARQUEMACHINE=this.giveFld(cur,this.FIELD_MACHINE_MARQUEMACHINE);
			ent.MACHINE_SERIEMACHINE=this.giveFld(cur,this.FIELD_MACHINE_SERIEMACHINE);
			ent.NUM_INTER=this.giveFld(cur,this.FIELD_NUM_INTER);
			ent.VISITEPREVENTIVE=this.giveFld(cur,this.FIELD_VISITEPREVENTIVE);
			ent.NUMEROBC=this.giveFld(cur,this.FIELD_NUMEROBC);
			ent.NUMERORAPPORT=this.giveFld(cur,this.FIELD_NUMERORAPPORT);
			ent.TH=this.giveFld(cur,this.FIELD_TH);
			ent.KH=this.giveFld(cur,this.FIELD_KH);
			ent.REF=this.giveFld(cur,this.FIELD_REF);
			ent.CONTACT_CODE=this.giveFld(cur,this.FIELD_CONTACT_CODE);
			ent.CONTACT_TITRE=this.giveFld(cur,this.FIELD_CONTACT_TITRE);
			ent.CONTACT_NOM=this.giveFld(cur,this.FIELD_CONTACT_NOM);
			ent.TOURNEE=this.giveFld(cur,this.FIELD_TOURNEE);
			ent.NUM_EVT=this.giveFld(cur,this.FIELD_NUM_EVT);

			cur.close();
		}
		else
		{
			if (cur!=null)
				cur.close();
			return false;
		}
		return true;
	}
	
	public boolean load_rapport(structClientvu ent,String stcodeclient, String date,String numrapport, StringBuffer stBuf)
	{
		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'"
				+" and "+this.FIELD_NUMERORAPPORT+"='"+numrapport+"'";

		Log.e("load_rapport","query=>"+query);
		
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			ent.SOC_CODE =this.giveFld(cur,this.FIELD_SOC_CODE );	
			ent.CODECLI =this.giveFld(cur,this.FIELD_CODECLI );
			ent.CODEREP =this.giveFld(cur,this.FIELD_CODEREP );
			ent.VU =this.giveFld(cur,this.FIELD_VU );
			ent.DATE =this.giveFld(cur,this.FIELD_DATE );
			ent.HEUREDEBUT =this.giveFld(cur,this.FIELD_HEUREDEBUT );
			ent.HEUREFIN =this.giveFld(cur,this.FIELD_HEUREFIN );		
			ent.TYPEACT =this.giveFld(cur,this.FIELD_TYPEACT );
			ent.FAMTYPEACT =this.giveFld(cur,this.FIELD_FAMTYPEACT );
			ent.COMMENTAIRE =this.giveFld(cur,this.FIELD_COMMENTAIRE );

			ent.NEXTDATE =this.giveFld(cur,this.FIELD_NEXTDATE );
			ent.FLAG=this.giveFld(cur,this.fIELD_FLAG);
			ent.fIELD_EVT_ID=this.giveFld(cur,this.fIELD_EVT_ID);
			ent.INFOCOMMERCIALE=this.giveFld(cur,this.FIELD_INFOCOMMERCIALE);
			ent.CHOIX_CLOTUREE=this.giveFld(cur,this.FIELD_CHOIX_CLOTUREE);
			ent.CMT_NONCLOTUREE=this.giveFld(cur,this.FIELD_CMT_NONCLOTUREE);
			ent.CODE_QUESTIONNAIRE=this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE);
			ent.CHOIX_FACTURABLE=this.giveFld(cur,this.FIELD_CHOIX_FACTURABLE);
			ent.YN_FACTURABLE=this.giveFld(cur,this.FIELD_YN_FACTURABLE);
			ent.NOSERIE=this.giveFld(cur,this.FIELD_NOSERIE);
			
			ent.MACHINE_CODEARTICLE=this.giveFld(cur,this.FIELD_MACHINE_CODEARTICLE);
			ent.MACHINE_LIBARTICLE=this.giveFld(cur,this.FIELD_MACHINE_LIBARTICLE);
			ent.MACHINE_NUMSERIE=this.giveFld(cur,this.FIELD_MACHINE_NUMSERIE);
			ent.MACHINE_CODESYMP=this.giveFld(cur,this.FIELD_MACHINE_CODESYMP);
			ent.MACHINE_LIBSYMP=this.giveFld(cur,this.FIELD_MACHINE_LIBSYMP);
			
			ent.MACHINE_QUI=this.giveFld(cur,this.FIELD_MACHINE_QUI);
			ent.MACHINE_MARQUE=this.giveFld(cur,this.FIELD_MACHINE_MARQUE);
			ent.MACHINE_TYPEMACHINE=this.giveFld(cur,this.FIELD_MACHINE_TYPEMACHINE);
			ent.MACHINE_MARQUEMACHINE=this.giveFld(cur,this.FIELD_MACHINE_MARQUEMACHINE);
			ent.MACHINE_SERIEMACHINE=this.giveFld(cur,this.FIELD_MACHINE_SERIEMACHINE);
			ent.NUM_INTER=this.giveFld(cur,this.FIELD_NUM_INTER);
			ent.VISITEPREVENTIVE=this.giveFld(cur,this.FIELD_VISITEPREVENTIVE);
			ent.NUMEROBC=this.giveFld(cur,this.FIELD_NUMEROBC);
			ent.NUMERORAPPORT=this.giveFld(cur,this.FIELD_NUMERORAPPORT);
			
			ent.TH=this.giveFld(cur,this.FIELD_TH);
			ent.KH=this.giveFld(cur,this.FIELD_KH);
			ent.REF=this.giveFld(cur,this.FIELD_REF);
			ent.CONTACT_CODE=this.giveFld(cur,this.FIELD_CONTACT_CODE);
			ent.CONTACT_TITRE=this.giveFld(cur,this.FIELD_CONTACT_TITRE);
			ent.CONTACT_NOM=this.giveFld(cur,this.FIELD_CONTACT_NOM);
			ent.TOURNEE=this.giveFld(cur,this.FIELD_TOURNEE);
			ent.NUM_EVT=this.giveFld(cur,this.FIELD_NUM_EVT);


			cur.close();
		}
		else
		{
			if (cur!=null)
				cur.close();
			return false;
		}
		return true;
	}
	
	//todo
	public boolean TodoFait_Exist(String stcodeclient, String num_inter)
	{
		
		boolean bres = false;
		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				
				+" and "+this.FIELD_NUM_INTER+"='"+num_inter+"'";
		
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			bres=true;
			cur.close();
			
		}
		else
		{
			if (cur!=null)
				cur.close();
			return bres;
		}
		return bres;
	}
	
	
	
	public boolean _isVu(String stcodeclient,String codesoc,String date)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_CODECLI+"="+
				"'"+stcodeclient+"' "+
				" and "+
				this.FIELD_DATE+"="+
				"'"+date+"' "+
				" and "+
				this.FIELD_SOC_CODE+"="+
				"'"+codesoc+"' ";

		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext())
		{
			
			String vu   	=this.giveFld(cur,this.FIELD_VU    );
			if ( Fonctions.convertToBool(vu))
			{
				if (cur!=null)
					cur.close();
				return true;
			}
				
		
		}
		if (cur!=null)
			cur.close();
		return false;
	}
	public boolean isVu_Rapport(String stcodeclient,String codesoc,String date,String numerorapport)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_CODECLI+"="+
				"'"+stcodeclient+"' "+
				" and "+
				this.FIELD_DATE+"="+
				"'"+date+"' "+
				" and "+
				this.FIELD_SOC_CODE+"="+
				"'"+codesoc+"' "+
				" and "+
				this.FIELD_NUMERORAPPORT+"="+
				"'"+numerorapport+"' ";

		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext())
		{
			
			String vu   	=this.giveFld(cur,this.FIELD_VU    );
			if ( Fonctions.convertToBool(vu))
			{
				if (cur!=null)
					cur.close();
				return true;
			}
				
		
		}
		if (cur!=null)
			cur.close();
		return false;
	}
	public boolean _isQuestionnaireFait(String stcodeclient,String date)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_CODECLI+"="+
				"'"+stcodeclient+"' "+
				" and "+
				this.FIELD_DATE+"="+
				"'"+date+"' ";

		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext())
		{
			
			String fait   	=this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE    );
			if ( !Fonctions.GetStringDanem(fait).equals("")  )
			{
				if (cur!=null)
					cur.close();
				return true;
			}
				
		
		}
		if (cur!=null)
			cur.close();
		return false;
	}
	
	public boolean isQuestionnaireFait_rapport(String stcodeclient,String date,String numrapport)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+
				this.FIELD_CODECLI+"="+
				"'"+stcodeclient+"' "+
				" and "+
				this.FIELD_DATE+"="+
				"'"+date+"' "+
				" and "+
				this.FIELD_NUMERORAPPORT+"="+
				"'"+numrapport+"' ";

		Cursor cur=db.conn.rawQuery (query,null);
		if (cur.moveToNext())
		{
			
			String fait   	=this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE    );
			if ( !Fonctions.GetStringDanem(fait).equals("")  )
			{
				if (cur!=null)
					cur.close();
				return true;
			}
				
		
		}
		if (cur!=null)
			cur.close();
		return false;
	}



	/*public boolean _save(passePlat ent)
	{
		try
		{
			//on efface l'existant et on sauve
			//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
			
			String query = "SELECT * FROM " + TABLENAME + " where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
					+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
					+ this.FIELD_DATE + "=" + "'" + ent.DATE + "'  ";
			
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext() ) {
				
				query = "UPDATE "
						+ TABLENAME
						+ " set "
						+ this.FIELD_HEUREDEBUT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.HEUREDEBUT)
						+ "',"
						+ this.FIELD_HEUREFIN
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.HEUREFIN)
						+ "',"
						+ this.FIELD_FAMTYPEACT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.FAMTYPEACT)
						+ "',"
						+ this.FIELD_TYPEACT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.TYPEACT)
						+ "',"
						+ this.fIELD_EVT_ID
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.fIELD_EVT_ID)
						+ "',"
						+ this.FIELD_NUM_EVT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NUM_EVT)
						+ "',"
						+ this.FIELD_COMMENTAIRE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.COMMENTAIRE)
						+ "',"
						+ this.FIELD_INFOCOMMERCIALE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.INFOCOMMERCIALE)
						+ "',"
						+ this.FIELD_CHOIX_CLOTUREE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CHOIX_CLOTUREE)
						+ "',"
						+ this.FIELD_CHOIX_FACTURABLE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CHOIX_FACTURABLE)
						+ "',"
						+ this.FIELD_VISITEPREVENTIVE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.VISITEPREVENTIVE)
						+ "',"
						+ this.FIELD_CMT_NONCLOTUREE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CMT_NONCLOTUREE)
						+ "',"
						+ this.FIELD_NOSERIE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NOSERIE)
						+ "',"
						
						+ this.FIELD_MACHINE_CODEARTICLE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_CODEARTICLE)
						+ "',"
						
						+ this.FIELD_MACHINE_LIBARTICLE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_LIBARTICLE)
						+ "',"
						
						+ this.FIELD_MACHINE_NUMSERIE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_NUMSERIE)
						+ "',"
						
						+ this.FIELD_MACHINE_CODESYMP
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_CODESYMP)
						+ "',"
						
						+ this.FIELD_MACHINE_LIBSYMP
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_LIBSYMP)
						+ "',"
						+ this.FIELD_MACHINE_QUI
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_QUI)
						+ "',"
						+ this.FIELD_MACHINE_MARQUE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_MARQUE)
						+ "',"
						+ this.FIELD_MACHINE_TYPEMACHINE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_TYPEMACHINE)
						+ "',"
						+ this.FIELD_MACHINE_MARQUEMACHINE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_MARQUEMACHINE)
						+ "',"
						+ this.FIELD_MACHINE_SERIEMACHINE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_SERIEMACHINE)
						+ "',"
						+ this.FIELD_NUM_INTER
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NUM_INTER)
						+ "',"
						
						+ this.FIELD_TH
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.TH)
						+ "',"
						+ this.FIELD_KH
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.KH)
						+ "',"
						+ this.FIELD_REF
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.REF)
						+ "',"
						+ this.FIELD_CONTACT_CODE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CONTACT_CODE)
						+ "',"
						+ this.FIELD_CONTACT_TITRE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CONTACT_TITRE)
						+ "',"
						+ this.FIELD_CONTACT_NOM
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CONTACT_NOM)
						+ "',"
						
						+ this.fIELD_FLAG
						+ "="
						+ "'"
						+ KDSYNCHRO_UPDATE
						+ "' "
						+ " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "'  ";

				db.conn.execSQL(query);
				
			}
			else
			{
				
				 query="INSERT INTO " + TABLENAME +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_SOC_CODE   		+","+
						this.FIELD_CODECLI   		+","+ 
						this.FIELD_CODEREP       		+","+  
						this.FIELD_VU    			+","+
						this.FIELD_DATE  			+","+
						this.FIELD_HEUREDEBUT  			+","+
						this.FIELD_HEUREFIN  			+","+
						this.FIELD_FAMTYPEACT  			+","+
						this.FIELD_TYPEACT  			+","+
						this.FIELD_COMMENTAIRE  			+","+
						this.FIELD_INFOCOMMERCIALE  			+","+
						this.FIELD_NEXTDATE  			+","+
						this.fIELD_EVT_ID  			+","+
						this.FIELD_NUM_EVT  			+","+
						this.FIELD_CHOIX_CLOTUREE  			+","+
						this.FIELD_CMT_NONCLOTUREE  			+","+
						this.FIELD_CODE_QUESTIONNAIRE  			+","+
						this.FIELD_CHOIX_FACTURABLE  			+","+
						this.FIELD_VISITEPREVENTIVE  			+","+
						this.FIELD_YN_FACTURABLE  			+","+
						this.FIELD_NOSERIE  			+","+
						this.FIELD_MACHINE_CODEARTICLE  			+","+
						this.FIELD_MACHINE_LIBARTICLE  			+","+
						this.FIELD_MACHINE_NUMSERIE  			+","+
						this.FIELD_MACHINE_CODESYMP  			+","+
						this.FIELD_MACHINE_LIBSYMP  			+","+
						this.FIELD_MACHINE_QUI  			+","+
						this.FIELD_MACHINE_MARQUE  			+","+
						this.FIELD_MACHINE_TYPEMACHINE  			+","+
						this.FIELD_MACHINE_MARQUEMACHINE  			+","+
						this.FIELD_MACHINE_SERIEMACHINE  			+","+
						this.FIELD_NUM_INTER  			+","+
						this.FIELD_TH  			+","+
						this.FIELD_KH  			+","+
						this.FIELD_REF  			+","+
						this.FIELD_CONTACT_CODE  			+","+
						this.FIELD_CONTACT_TITRE  			+","+
						this.FIELD_CONTACT_NOM  			+","+
						 this.FIELD_TOURNEE  			+","+

						 this.fIELD_FLAG+""+

		  		") VALUES ("+
		  		String.valueOf(KD_TYPE)+","+
		  		"'"+ent.SOC_CODE+"',"+
		  		"'"+ent.CODECLI+"',"+
		  		"'"+ent.CODEREP+"',"+
		  		"'"+ent.VU+"',"+
		  		"'"+ent.DATE+"',"+
		  		"'"+ent.HEUREDEBUT+"',"+
		  		"'"+ent.HEUREFIN+"',"+
				"'"+ent.FAMTYPEACT+"',"+
		  		"'"+ent.TYPEACT+"',"+
		  		"'"+ent.COMMENTAIRE+"',"+
		  		"'"+ent.INFOCOMMERCIALE+"',"+
		  		"'"+ent.NEXTDATE+"',"+
		  		"'"+ent.fIELD_EVT_ID+"',"+
				"'"+ent.NUM_EVT+"',"+
		  		"'"+ent.CHOIX_CLOTUREE+"',"+
		  		"'"+ent.CMT_NONCLOTUREE+"',"+
				"'"+ent.CODE_QUESTIONNAIRE+"',"+
				"'"+ent.CHOIX_FACTURABLE+"',"+
				"'"+ent.VISITEPREVENTIVE+"',"+
				"'"+ent.YN_FACTURABLE+"',"+
				"'"+ent.NOSERIE+"',"+
				"'"+ent.MACHINE_CODEARTICLE+"',"+
				"'"+ent.MACHINE_LIBARTICLE+"',"+
				"'"+ent.MACHINE_NUMSERIE+"',"+
				"'"+ent.MACHINE_CODESYMP+"',"+
				"'"+ent.MACHINE_LIBSYMP+"',"+
				"'"+ent.MACHINE_QUI+"',"+
				"'"+ent.MACHINE_MARQUE+"',"+
				"'"+ent.MACHINE_TYPEMACHINE+"',"+
				"'"+ent.MACHINE_MARQUEMACHINE+"',"+
				"'"+ent.MACHINE_SERIEMACHINE +"',"+
				"'"+ent.NUM_INTER +"',"+
				"'"+ent.TH +"',"+
				"'"+ent.KH +"',"+
				"'"+ent.REF +"',"+	
				"'"+ent.CONTACT_CODE +"',"+	
				"'"+ent.CONTACT_TITRE +"',"+	
				"'"+ent.CONTACT_NOM +"',"+
				"'"+ent.TOURNEE +"',"+
				"'"+KDSYNCHRO_UPDATE +"')";
				
				db.conn.execSQL(query);
			}
			if (cur!=null)
				cur.close();
		
	
		}
		catch(Exception ex)
		{
			Global.lastErrorMessage=(ex.getMessage());
			return false;
		}

		return true;
	}*/
	
	/*public boolean save_rapport(passePlat ent)
	{
		try
		{
			//on efface l'existant et on sauve
			//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
			
			String query = "SELECT * FROM " + TABLENAME + " where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
					+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
					+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' AND "
					+ this.FIELD_NUMERORAPPORT + "=" + "'" + ent.NUMERORAPPORT + "'  ";
			
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext() ) {
				
				query = "UPDATE "
						+ TABLENAME
						+ " set "
						+ this.FIELD_HEUREDEBUT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.HEUREDEBUT)
						+ "',"
						+ this.FIELD_HEUREFIN
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.HEUREFIN)
						+ "',"
						+ this.FIELD_FAMTYPEACT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.FAMTYPEACT)
						+ "',"
						+ this.FIELD_TYPEACT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.TYPEACT)
						+ "',"
						+ this.fIELD_EVT_ID
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.fIELD_EVT_ID)
						+ "',"
						+ this.FIELD_NUM_EVT
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NUM_EVT)
						+ "',"
						+ this.FIELD_COMMENTAIRE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.COMMENTAIRE)
						+ "',"
						+ this.FIELD_INFOCOMMERCIALE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.INFOCOMMERCIALE)
						+ "',"
						+ this.FIELD_CHOIX_CLOTUREE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CHOIX_CLOTUREE)
						+ "',"
						+ this.FIELD_CHOIX_FACTURABLE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CHOIX_FACTURABLE)
						+ "',"
						+ this.FIELD_VISITEPREVENTIVE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.VISITEPREVENTIVE)
						+ "',"
						+ this.FIELD_CMT_NONCLOTUREE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CMT_NONCLOTUREE)
						+ "',"
						+ this.FIELD_NOSERIE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NOSERIE)
						+ "',"
						
						+ this.FIELD_MACHINE_CODEARTICLE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_CODEARTICLE)
						+ "',"
						
						+ this.FIELD_MACHINE_LIBARTICLE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_LIBARTICLE)
						+ "',"
						
						+ this.FIELD_MACHINE_NUMSERIE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_NUMSERIE)
						+ "',"
						
						+ this.FIELD_MACHINE_CODESYMP
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_CODESYMP)
						+ "',"
						
						+ this.FIELD_MACHINE_LIBSYMP
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_LIBSYMP)
						+ "',"
						+ this.FIELD_MACHINE_QUI
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_QUI)
						+ "',"
						+ this.FIELD_MACHINE_MARQUE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_MARQUE)
						+ "',"
						+ this.FIELD_MACHINE_TYPEMACHINE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_TYPEMACHINE)
						+ "',"
						+ this.FIELD_MACHINE_MARQUEMACHINE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_MARQUEMACHINE)
						+ "',"
						+ this.FIELD_MACHINE_SERIEMACHINE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.MACHINE_SERIEMACHINE)
						+ "',"
						+ this.FIELD_NUM_INTER
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NUM_INTER)
						+ "',"
						+ this.FIELD_NUMEROBC
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.NUMEROBC)
						+ "',"
						+ this.FIELD_TH
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.TH)
						+ "',"
						+ this.FIELD_KH
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.KH)
						+ "',"
						+ this.FIELD_REF
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.REF)
						+ "',"
						
						+ this.FIELD_CONTACT_CODE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CONTACT_CODE)
						+ "',"
						+ this.FIELD_CONTACT_TITRE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CONTACT_TITRE)
						+ "',"
						+ this.FIELD_CONTACT_NOM
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.CONTACT_NOM)
						+ "',"
						
						+ this.fIELD_FLAG
						+ "="
						+ "'"
						+ KDSYNCHRO_UPDATE
						+ "' "
						+ " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' AND "
						+ this.FIELD_NUMERORAPPORT + "=" + "'" + ent.NUMERORAPPORT + "'  ";

				db.conn.execSQL(query);
				
			}
			else
			{
				
				 query="INSERT INTO " + TABLENAME +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_SOC_CODE   		+","+
						this.FIELD_CODECLI   		+","+ 
						this.FIELD_CODEREP       		+","+  
						this.FIELD_VU    			+","+
						this.FIELD_DATE  			+","+
						this.FIELD_HEUREDEBUT  			+","+
						this.FIELD_HEUREFIN  			+","+
						this.FIELD_FAMTYPEACT  			+","+
						this.FIELD_TYPEACT  			+","+
						this.FIELD_COMMENTAIRE  			+","+
						this.FIELD_INFOCOMMERCIALE  			+","+
						this.FIELD_NEXTDATE  			+","+
						this.fIELD_EVT_ID  			+","+
						this.FIELD_NUM_EVT  			+","+
						this.FIELD_CHOIX_CLOTUREE  			+","+
						this.FIELD_CMT_NONCLOTUREE  			+","+
						this.FIELD_CODE_QUESTIONNAIRE  			+","+
						this.FIELD_CHOIX_FACTURABLE  			+","+
						this.FIELD_VISITEPREVENTIVE  			+","+
						this.FIELD_YN_FACTURABLE  			+","+
						this.FIELD_NOSERIE  			+","+
						this.FIELD_MACHINE_CODEARTICLE  			+","+
						this.FIELD_MACHINE_LIBARTICLE  			+","+
						this.FIELD_MACHINE_NUMSERIE  			+","+
						this.FIELD_MACHINE_CODESYMP  			+","+
						this.FIELD_MACHINE_LIBSYMP  			+","+
						this.FIELD_MACHINE_QUI  			+","+
						this.FIELD_MACHINE_MARQUE  			+","+
						this.FIELD_MACHINE_TYPEMACHINE  			+","+
						this.FIELD_MACHINE_MARQUEMACHINE  			+","+
						this.FIELD_MACHINE_SERIEMACHINE  			+","+
						this.FIELD_NUM_INTER  			+","+
						this.FIELD_NUMEROBC  			+","+
						this.FIELD_CHOIX_AGENT  			+","+
						this.FIELD_NUMERORAPPORT 			+","+
						this.FIELD_TH 			+","+
						this.FIELD_KH 			+","+
						this.FIELD_REF 			+","+
						this.FIELD_CONTACT_CODE 			+","+
						this.FIELD_CONTACT_TITRE 			+","+
						this.FIELD_CONTACT_NOM 			+","+
						 this.FIELD_TOURNEE 			+","+
						this.fIELD_FLAG+""+

		  		") VALUES ("+
		  		String.valueOf(KD_TYPE)+","+
		  		"'"+ent.SOC_CODE+"',"+
		  		"'"+ent.CODECLI+"',"+
		  		"'"+ent.CODEREP+"',"+
		  		"'"+ent.VU+"',"+
		  		"'"+ent.DATE+"',"+
		  		"'"+ent.HEUREDEBUT+"',"+
		  		"'"+ent.HEUREFIN+"',"+
				"'"+ent.FAMTYPEACT+"',"+
		  		"'"+ent.TYPEACT+"',"+
		  		"'"+ent.COMMENTAIRE+"',"+
		  		"'"+ent.INFOCOMMERCIALE+"',"+
		  		"'"+ent.NEXTDATE+"',"+
		  		"'"+ent.fIELD_EVT_ID+"',"+
				"'"+ent.NUM_EVT+"',"+
		  		"'"+ent.CHOIX_CLOTUREE+"',"+
		  		"'"+ent.CMT_NONCLOTUREE+"',"+
				"'"+ent.CODE_QUESTIONNAIRE+"',"+
				"'"+ent.CHOIX_FACTURABLE+"',"+
				"'"+ent.VISITEPREVENTIVE+"',"+
				"'"+ent.YN_FACTURABLE+"',"+
				"'"+ent.NOSERIE+"',"+
				"'"+ent.MACHINE_CODEARTICLE+"',"+
				"'"+ent.MACHINE_LIBARTICLE+"',"+
				"'"+ent.MACHINE_NUMSERIE+"',"+
				"'"+ent.MACHINE_CODESYMP+"',"+
				"'"+ent.MACHINE_LIBSYMP+"',"+
				"'"+ent.MACHINE_QUI+"',"+
				"'"+ent.MACHINE_MARQUE+"',"+
				"'"+ent.MACHINE_TYPEMACHINE+"',"+
				"'"+ent.MACHINE_MARQUEMACHINE+"',"+
				"'"+ent.MACHINE_SERIEMACHINE +"',"+
				"'"+ent.NUM_INTER +"',"+
				"'"+ent.NUMEROBC +"',"+
				"'"+ent.NUMERORAPPORT +"',"+
				"'"+ent.TH +"',"+
				"'"+ent.KH +"',"+
				"'"+ent.REF +"',"+
				"'"+ent.CONTACT_CODE +"',"+
				"'"+ent.CONTACT_TITRE +"',"+
				"'"+ent.CONTACT_NOM +"',"+
				"'"+ent.TOURNEE +"',"+
				"'"+KDSYNCHRO_UPDATE +"')";
				
				db.conn.execSQL(query);
			}
			if (cur!=null)
				cur.close();
	
		}
		catch(Exception ex)
		{
			Global.lastErrorMessage=(ex.getMessage());
			return false;
		}

		return true;
	}*/
	
	
	/*public boolean _save2(structClientvu ent)
	{
		
			try
			{
				//on efface l'existant et on sauve
				//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "'  ";
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_HEUREDEBUT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.HEUREDEBUT)
							+ "',"
							+ this.FIELD_HEUREFIN
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.HEUREFIN)
							+ "',"
							+ this.FIELD_FAMTYPEACT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.FAMTYPEACT)
							+ "',"
							+ this.FIELD_TYPEACT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.TYPEACT)
							+ "',"
							+ this.fIELD_EVT_ID
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.fIELD_EVT_ID)
							+ "',"
							+ this.FIELD_NUM_EVT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NUM_EVT)
							+ "',"
							+ this.FIELD_COMMENTAIRE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.COMMENTAIRE)
							+ "',"
							+ this.FIELD_INFOCOMMERCIALE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.INFOCOMMERCIALE)
							+ "',"
							+ this.FIELD_CHOIX_CLOTUREE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CHOIX_CLOTUREE)
							+ "',"
							+ this.FIELD_CHOIX_FACTURABLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CHOIX_FACTURABLE)
							+ "',"
							+ this.FIELD_VISITEPREVENTIVE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.VISITEPREVENTIVE)
							+ "',"
							+ this.FIELD_CMT_NONCLOTUREE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CMT_NONCLOTUREE)
							+ "',"
							+ this.FIELD_NOSERIE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NOSERIE)
							+ "',"
							+ this.FIELD_MACHINE_CODEARTICLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_CODEARTICLE)
							+ "',"
							
							+ this.FIELD_MACHINE_LIBARTICLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_LIBARTICLE)
							+ "',"
							+ this.FIELD_MACHINE_NUMSERIE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_NUMSERIE)
							+ "',"
							
							+ this.FIELD_MACHINE_CODESYMP
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_CODESYMP)
							+ "',"
							
							+ this.FIELD_MACHINE_LIBSYMP
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_LIBSYMP)
							+ "',"
							+ this.FIELD_MACHINE_QUI
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_QUI)
							+ "',"
							+ this.FIELD_MACHINE_MARQUE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_MARQUE)
							+ "',"
							+ this.FIELD_MACHINE_TYPEMACHINE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_TYPEMACHINE)
							+ "',"
							+ this.FIELD_MACHINE_MARQUEMACHINE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_MARQUEMACHINE)
							+ "',"
							+ this.FIELD_MACHINE_SERIEMACHINE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_SERIEMACHINE)
							+ "',"
							+ this.FIELD_NUM_INTER
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NUM_INTER)
							+ "',"
							+ this.FIELD_TH
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.TH)
							+ "',"
							+ this.FIELD_KH
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.KH)
							+ "',"
							+ this.FIELD_REF
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.REF)
							+ "',"
							
							+ this.FIELD_CONTACT_CODE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CONTACT_CODE)
							+ "',"
							+ this.FIELD_CONTACT_TITRE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CONTACT_TITRE)
							+ "',"
							+ this.FIELD_CONTACT_NOM
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CONTACT_NOM)
							+ "',"
							
							+ this.fIELD_FLAG
							+ "="
							+ "'"
							+ KDSYNCHRO_UPDATE
							+ "' "
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
//							+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
							+ this.FIELD_DATE + "=" + "'" + ent.DATE + "'  ";

					db.conn.execSQL(query);
					
				}
				else
				{
					
					 query="INSERT INTO " + TABLENAME +" ("+
							dbKD.fld_kd_dat_type+","+
							this.FIELD_SOC_CODE   		+","+
							this.FIELD_CODECLI   		+","+ 
							this.FIELD_CODEREP       		+","+  
							this.FIELD_VU    			+","+
							this.FIELD_DATE  			+","+
							this.FIELD_HEUREDEBUT  			+","+
							this.FIELD_HEUREFIN  			+","+
							this.FIELD_FAMTYPEACT  			+","+
							this.FIELD_TYPEACT  			+","+
							this.FIELD_COMMENTAIRE  			+","+
							this.FIELD_INFOCOMMERCIALE 			+","+
							this.FIELD_NEXTDATE  			+","+
							this.fIELD_EVT_ID  			+","+
							this.FIELD_NUM_EVT  			+","+
							this.FIELD_CHOIX_CLOTUREE  			+","+
							this.FIELD_CMT_NONCLOTUREE  			+","+
							this.FIELD_CODE_QUESTIONNAIRE  			+","+
							this.FIELD_CHOIX_FACTURABLE  			+","+
							this.FIELD_VISITEPREVENTIVE 			+","+
							this.FIELD_YN_FACTURABLE  			+","+
							this.FIELD_NOSERIE  			+","+
							this.FIELD_MACHINE_CODEARTICLE  			+","+
							this.FIELD_MACHINE_LIBARTICLE  			+","+
							this.FIELD_MACHINE_NUMSERIE  			+","+
							this.FIELD_MACHINE_CODESYMP  			+","+
							this.FIELD_MACHINE_LIBSYMP  			+","+
							this.FIELD_MACHINE_QUI  			+","+
							this.FIELD_MACHINE_MARQUE  			+","+
							this.FIELD_MACHINE_TYPEMACHINE  			+","+
							this.FIELD_MACHINE_MARQUEMACHINE  			+","+
							this.FIELD_MACHINE_SERIEMACHINE  			+","+
							this.FIELD_NUM_INTER 			+","+
							this.FIELD_TH 			+","+
							this.FIELD_KH 			+","+
							this.FIELD_REF 			+","+
							this.FIELD_CONTACT_CODE 			+","+
							this.FIELD_CONTACT_TITRE 			+","+
							this.FIELD_CONTACT_NOM 			+","+
							 this.FIELD_TOURNEE 			+","+
							this.fIELD_FLAG+""+

			  		") VALUES ("+
			  		String.valueOf(KD_TYPE)+","+
			  		"'"+ent.SOC_CODE+"',"+
			  		"'"+ent.CODECLI+"',"+
			  		"'"+ent.CODEREP+"',"+
			  		"'"+ent.VU+"',"+
			  		"'"+ent.DATE+"',"+
			  		"'"+ent.HEUREDEBUT+"',"+
			  		"'"+ent.HEUREFIN+"',"+
					"'"+ent.FAMTYPEACT+"',"+
			  		"'"+ent.TYPEACT+"',"+
			  		"'"+MyDB.controlFld(ent.COMMENTAIRE)+"',"+
			  		"'"+MyDB.controlFld(ent.INFOCOMMERCIALE)+"',"+
			  		"'"+ent.NEXTDATE+"',"+
			  		"'"+ent.fIELD_EVT_ID+"',"+
					"'"+ent.NUM_EVT+"',"+
			  		"'"+ent.CHOIX_CLOTUREE+"',"+
			  		"'"+MyDB.controlFld(ent.CMT_NONCLOTUREE)+"',"+
			  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
			  		"'"+ent.CHOIX_FACTURABLE+"',"+
			  		"'"+ent.VISITEPREVENTIVE+"',"+
			  		"'"+ent.YN_FACTURABLE+"',"+
			  		"'"+MyDB.controlFld(ent.NOSERIE)+"',"+
			  		"'"+ent.MACHINE_CODEARTICLE+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_LIBARTICLE)+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_NUMSERIE)+"',"+
					"'"+ent.MACHINE_CODESYMP+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_LIBSYMP)+"',"+
					"'"+ent.MACHINE_QUI+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_MARQUE)+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_TYPEMACHINE)+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_MARQUEMACHINE)+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_SERIEMACHINE) +"',"+
					"'"+MyDB.controlFld(ent.NUM_INTER )+"',"+
					"'"+MyDB.controlFld(ent.TH )+"',"+
					"'"+MyDB.controlFld(ent.KH )+"',"+
					"'"+MyDB.controlFld(ent.REF )+"',"+
					"'"+MyDB.controlFld(ent.CONTACT_CODE )+"',"+
					"'"+MyDB.controlFld(ent.CONTACT_TITRE )+"',"+
					"'"+MyDB.controlFld(ent.CONTACT_NOM )+"',"+
					"'"+MyDB.controlFld(ent.TOURNEE )+"',"+
					"'"+KDSYNCHRO_UPDATE +"')";
					
					db.conn.execSQL(query);
				}
				if (cur!=null)
					cur.close();
			
		
			}
			catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
				return false;
			}

			return true;
	}*/
	
	public boolean save2_rapport(structClientvu ent)
	{
		
			try
			{
				//on efface l'existant et on sauve
				//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE +  "' AND "
						+ this.FIELD_NUMERORAPPORT + "=" + "'" + ent.NUMERORAPPORT + "'  ";
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_HEUREDEBUT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.HEUREDEBUT)
							+ "',"
							+ this.FIELD_HEUREFIN
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.HEUREFIN)
							+ "',"
							+ this.FIELD_FAMTYPEACT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.FAMTYPEACT)
							+ "',"
							+ this.FIELD_TYPEACT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.TYPEACT)
							+ "',"
							+ this.fIELD_EVT_ID
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.fIELD_EVT_ID)
							+ "',"
							+ this.FIELD_NUM_EVT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NUM_EVT)
							+ "',"
							+ this.FIELD_COMMENTAIRE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.COMMENTAIRE)
							+ "',"
							+ this.FIELD_INFOCOMMERCIALE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.INFOCOMMERCIALE)
							+ "',"
							+ this.FIELD_CHOIX_CLOTUREE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CHOIX_CLOTUREE)
							+ "',"
							+ this.FIELD_CHOIX_FACTURABLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CHOIX_FACTURABLE)
							+ "',"
							+ this.FIELD_VISITEPREVENTIVE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.VISITEPREVENTIVE)
							+ "',"
							+ this.FIELD_CMT_NONCLOTUREE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CMT_NONCLOTUREE)
							+ "',"
							+ this.FIELD_NOSERIE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NOSERIE)
							+ "',"
							+ this.FIELD_MACHINE_CODEARTICLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_CODEARTICLE)
							+ "',"
							
							+ this.FIELD_MACHINE_LIBARTICLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_LIBARTICLE)
							+ "',"
							
							+ this.FIELD_MACHINE_NUMSERIE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_NUMSERIE)
							+ "',"
							
							+ this.FIELD_MACHINE_CODESYMP
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_CODESYMP)
							+ "',"
							
							+ this.FIELD_MACHINE_LIBSYMP
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_LIBSYMP)
							+ "',"
							+ this.FIELD_MACHINE_QUI
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_QUI)
							+ "',"
							+ this.FIELD_MACHINE_MARQUE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_MARQUE)
							+ "',"
							+ this.FIELD_MACHINE_TYPEMACHINE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_TYPEMACHINE)
							+ "',"
							+ this.FIELD_MACHINE_MARQUEMACHINE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_MARQUEMACHINE)
							+ "',"
							+ this.FIELD_MACHINE_SERIEMACHINE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.MACHINE_SERIEMACHINE)
							+ "',"
							+ this.FIELD_NUM_INTER
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NUM_INTER)
							+ "',"
							+ this.FIELD_NUMEROBC
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.NUMEROBC)
							+ "',"
							+ this.FIELD_CHOIX_AGENT
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CHOIX_AGENT)
							+ "',"
							+ this.FIELD_TH
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.TH)
							+ "',"
							+ this.FIELD_KH
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.KH)
							+ "',"
							+ this.FIELD_REF
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.REF)
							+ "',"
							+ this.FIELD_CONTACT_CODE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CONTACT_CODE)
							+ "',"
							+ this.FIELD_CONTACT_TITRE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CONTACT_TITRE)
							+ "',"
							+ this.FIELD_CONTACT_NOM
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.CONTACT_NOM)
							+ "',"
							+ this.FIELD_ISDEMANDE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.IS_DEMANDE)
							+ "',"
							+ this.fIELD_FLAG
							+ "="
							+ "'"
							+ KDSYNCHRO_UPDATE
							+ "' "
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
	//							+ this.FIELD_SOC_CODE + "=" + "'" + ent.SOC_CODE + "' AND "
							+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' AND "
							+ this.FIELD_NUMERORAPPORT + "=" + "'" + ent.NUMERORAPPORT + "'  ";

							db.conn.execSQL(query);
					
				}
				else
				{
					
					 query="INSERT INTO " + TABLENAME +" ("+
							dbKD.fld_kd_dat_type+","+
							this.FIELD_SOC_CODE   		+","+
							this.FIELD_CODECLI   		+","+ 
							this.FIELD_CODEREP       		+","+  
							this.FIELD_VU    			+","+
							this.FIELD_DATE  			+","+
							this.FIELD_HEUREDEBUT  			+","+
							this.FIELD_HEUREFIN  			+","+
							this.FIELD_FAMTYPEACT  			+","+
							this.FIELD_TYPEACT  			+","+
							this.FIELD_COMMENTAIRE  			+","+
							this.FIELD_INFOCOMMERCIALE 			+","+
							this.FIELD_NEXTDATE  			+","+
							this.fIELD_EVT_ID  			+","+
							this.FIELD_NUM_EVT  			+","+
							this.FIELD_CHOIX_CLOTUREE  			+","+
							this.FIELD_CMT_NONCLOTUREE  			+","+
							this.FIELD_CODE_QUESTIONNAIRE  			+","+
							this.FIELD_CHOIX_FACTURABLE  			+","+
							this.FIELD_VISITEPREVENTIVE 			+","+
							this.FIELD_YN_FACTURABLE  			+","+
							this.FIELD_NOSERIE  			+","+
							this.FIELD_MACHINE_CODEARTICLE  			+","+
							this.FIELD_MACHINE_LIBARTICLE  			+","+
							this.FIELD_MACHINE_NUMSERIE  			+","+
							this.FIELD_MACHINE_CODESYMP  			+","+
							this.FIELD_MACHINE_LIBSYMP  			+","+
							this.FIELD_MACHINE_QUI  			+","+
							this.FIELD_MACHINE_MARQUE  			+","+
							this.FIELD_MACHINE_TYPEMACHINE  			+","+
							this.FIELD_MACHINE_MARQUEMACHINE  			+","+
							this.FIELD_MACHINE_SERIEMACHINE  			+","+
							this.FIELD_NUM_INTER 			+","+
							this.FIELD_NUMEROBC 			+","+
							this.FIELD_CHOIX_AGENT 			+","+
							this.FIELD_NUMERORAPPORT			+","+
							this.FIELD_TH			+","+
							this.FIELD_KH			+","+
							this.FIELD_REF			+","+
							
							this.FIELD_CONTACT_CODE			+","+
							this.FIELD_CONTACT_TITRE			+","+
							this.FIELD_CONTACT_NOM		+","+
							this.FIELD_ISDEMANDE		+","+
							 this.FIELD_TOURNEE		+","+

							this.fIELD_FLAG+""+

			  		") VALUES ("+
			  		String.valueOf(KD_TYPE)+","+
			  		"'"+ent.SOC_CODE+"',"+
			  		"'"+ent.CODECLI+"',"+
			  		"'"+ent.CODEREP+"',"+
			  		"'"+ent.VU+"',"+
			  		"'"+ent.DATE+"',"+
			  		"'"+ent.HEUREDEBUT+"',"+
			  		"'"+ent.HEUREFIN+"',"+
					"'"+ent.FAMTYPEACT+"',"+
			  		"'"+ent.TYPEACT+"',"+
			  		"'"+MyDB.controlFld(ent.COMMENTAIRE)+"',"+
			  		"'"+MyDB.controlFld(ent.INFOCOMMERCIALE)+"',"+
			  		"'"+ent.NEXTDATE+"',"+
			  		"'"+ent.fIELD_EVT_ID+"',"+
					"'"+ent.NUM_EVT+"',"+
			  		"'"+ent.CHOIX_CLOTUREE+"',"+
			  		"'"+MyDB.controlFld(ent.CMT_NONCLOTUREE)+"',"+
			  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
			  		"'"+ent.CHOIX_FACTURABLE+"',"+
			  		"'"+ent.VISITEPREVENTIVE+"',"+
			  		"'"+ent.YN_FACTURABLE+"',"+
			  		"'"+MyDB.controlFld(ent.NOSERIE)+"',"+
			  		
					"'"+ent.MACHINE_CODEARTICLE+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_LIBARTICLE)+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_NUMSERIE)+"',"+
					"'"+ent.MACHINE_CODESYMP+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_LIBSYMP)+"',"+
					"'"+ent.MACHINE_QUI+"',"+
					"'"+ent.MACHINE_MARQUE+"',"+
					"'"+ent.MACHINE_TYPEMACHINE+"',"+
					"'"+ent.MACHINE_MARQUEMACHINE+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_SERIEMACHINE) +"',"+
					"'"+MyDB.controlFld(ent.NUM_INTER )+"',"+
					"'"+ent.NUMEROBC +"',"+
					"'"+ent.CHOIX_AGENT +"',"+
					"'"+ent.NUMERORAPPORT +"',"+
					"'"+MyDB.controlFld(ent.TH )+"',"+
					"'"+MyDB.controlFld(ent.KH )+"',"+
					"'"+MyDB.controlFld(ent.REF) +"',"+
					
					"'"+MyDB.controlFld(ent.CONTACT_CODE )+"',"+
					"'"+MyDB.controlFld(ent.CONTACT_TITRE )+"',"+
					"'"+MyDB.controlFld(ent.CONTACT_NOM )+"',"+
					"'"+MyDB.controlFld(ent.IS_DEMANDE)+"',"+
					"'"+MyDB.controlFld(ent.TOURNEE)+"',"+
					
			  		"'"+KDSYNCHRO_UPDATE +"')";
					
					db.conn.execSQL(query);
				}
				if (cur!=null)
					cur.close();
			
		
			}
			catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
				return false;
			}

			return true;
	}
	
	
	/*public boolean _save3(structClientvu ent)
	{
		
			try
			{
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "'  ";
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_YN_FACTURABLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.YN_FACTURABLE)
							+ "'"
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
							+ this.FIELD_DATE + "=" + "'" + ent.DATE + "'  ";

					db.conn.execSQL(query);
					
				}
				else
				{
					
					 query="INSERT INTO " + TABLENAME +" ("+
							dbKD.fld_kd_dat_type+","+
							this.FIELD_SOC_CODE   		+","+
							this.FIELD_CODECLI   		+","+ 
							this.FIELD_CODEREP       		+","+  
							this.FIELD_VU    			+","+
							this.FIELD_DATE  			+","+
							this.FIELD_HEUREDEBUT  			+","+
							this.FIELD_HEUREFIN  			+","+
							this.FIELD_FAMTYPEACT  			+","+
							this.FIELD_TYPEACT  			+","+
							this.FIELD_COMMENTAIRE  			+","+
							this.FIELD_INFOCOMMERCIALE 			+","+
							this.FIELD_NEXTDATE  			+","+
							this.fIELD_EVT_ID  			+","+
							 this.FIELD_NUM_EVT  			+","+
							this.FIELD_CHOIX_CLOTUREE  			+","+
							this.FIELD_CMT_NONCLOTUREE  			+","+
							this.FIELD_CODE_QUESTIONNAIRE  			+","+
							this.FIELD_CHOIX_FACTURABLE  			+","+
							this.FIELD_VISITEPREVENTIVE  			+","+
							this.FIELD_YN_FACTURABLE  			+","+
							this.FIELD_NOSERIE  			+","+
							this.FIELD_MACHINE_CODEARTICLE  			+","+
							this.FIELD_MACHINE_LIBARTICLE  			+","+
							this.FIELD_MACHINE_NUMSERIE  			+","+
							this.FIELD_MACHINE_CODESYMP  			+","+
							this.FIELD_MACHINE_LIBSYMP  			+","+
							this.FIELD_MACHINE_QUI  			+","+
							this.FIELD_MACHINE_MARQUE  			+","+
							this.FIELD_MACHINE_TYPEMACHINE  			+","+
							this.FIELD_MACHINE_MARQUEMACHINE  			+","+
							this.FIELD_MACHINE_SERIEMACHINE  			+","+
							this.FIELD_NUM_INTER  			+","+
							this.FIELD_TH 			+","+
							this.FIELD_KH  			+","+
							this.FIELD_REF  			+","+
							
							this.FIELD_CONTACT_CODE  			+","+
							this.FIELD_CONTACT_TITRE  			+","+
							this.FIELD_CONTACT_NOM  			+","+
							 this.FIELD_TOURNEE  			+","+

							this.fIELD_FLAG+""+

			  		") VALUES ("+
			  		String.valueOf(KD_TYPE)+","+
			  		"'"+ent.SOC_CODE+"',"+
			  		"'"+ent.CODECLI+"',"+
			  		"'"+ent.CODEREP+"',"+
			  		"'"+ent.VU+"',"+
			  		"'"+ent.DATE+"',"+
			  		"'"+ent.HEUREDEBUT+"',"+
			  		"'"+ent.HEUREFIN+"',"+
					"'"+ent.FAMTYPEACT+"',"+
			  		"'"+ent.TYPEACT+"',"+
			  		"'"+MyDB.controlFld(ent.COMMENTAIRE)+"',"+
			  		"'"+MyDB.controlFld(ent.INFOCOMMERCIALE)+"',"+
			  		"'"+ent.NEXTDATE+"',"+
			  		"'"+ent.fIELD_EVT_ID+"',"+
					"'"+ent.NUM_EVT+"',"+
			  		"'"+ent.CHOIX_CLOTUREE+"',"+
			  		"'"+MyDB.controlFld(ent.CMT_NONCLOTUREE)+"',"+
			  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
			  		"'"+ent.CHOIX_FACTURABLE+"',"+
			  		"'"+ent.VISITEPREVENTIVE+"',"+
			  		"'"+ent.YN_FACTURABLE+"',"+
			  		"'"+ent.NOSERIE+"',"+
			  		"'"+ent.MACHINE_CODEARTICLE+"',"+
					"'"+ent.MACHINE_LIBARTICLE+"',"+
					"'"+ent.MACHINE_NUMSERIE+"',"+
					"'"+ent.MACHINE_CODESYMP+"',"+
					"'"+ent.MACHINE_LIBSYMP+"',"+
					"'"+ent.MACHINE_QUI+"',"+
					"'"+ent.MACHINE_MARQUE+"',"+
					"'"+ent.MACHINE_TYPEMACHINE+"',"+
					"'"+ent.MACHINE_MARQUEMACHINE+"',"+
					"'"+ent.MACHINE_SERIEMACHINE +"',"+
					"'"+MyDB.controlFld(ent.NUM_INTER) +"',"+
					"'"+MyDB.controlFld(ent.TH )+"',"+
					"'"+MyDB.controlFld(ent.KH )+"',"+
					"'"+MyDB.controlFld(ent.REF) +"',"+
					
					"'"+MyDB.controlFld(ent.CONTACT_CODE) +"',"+
					"'"+MyDB.controlFld(ent.CONTACT_TITRE )+"',"+
					"'"+MyDB.controlFld(ent.CONTACT_NOM) +"',"+
					"'"+MyDB.controlFld(ent.TOURNEE) +"',"+
					
					"'"+KDSYNCHRO_UPDATE +"')";
					
					db.conn.execSQL(query);
				}
				if (cur!=null)
					cur.close();
			
		
			}
			catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
				return false;
			}

			return true;
	}*/
	
	public boolean save3_rapport(structClientvu ent)
	{
		
			try
			{
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' AND "
						+ this.FIELD_NUMERORAPPORT + "=" + "'" + ent.NUMERORAPPORT + "'  ";
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_YN_FACTURABLE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.YN_FACTURABLE)
							+ "'"
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
							+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' AND "
							+ this.FIELD_NUMERORAPPORT + "=" + "'" + ent.NUMERORAPPORT + "'  ";

					db.conn.execSQL(query);
					
				}
				else
				{
					
					 query="INSERT INTO " + TABLENAME +" ("+
							dbKD.fld_kd_dat_type+","+
							this.FIELD_SOC_CODE   		+","+
							this.FIELD_CODECLI   		+","+ 
							this.FIELD_CODEREP       		+","+  
							this.FIELD_VU    			+","+
							this.FIELD_DATE  			+","+
							this.FIELD_HEUREDEBUT  			+","+
							this.FIELD_HEUREFIN  			+","+
							 this.FIELD_FAMTYPEACT  			+","+
							this.FIELD_TYPEACT  			+","+
							this.FIELD_COMMENTAIRE  			+","+
							this.FIELD_INFOCOMMERCIALE 			+","+
							this.FIELD_NEXTDATE  			+","+
							this.fIELD_EVT_ID  			+","+
							 this.FIELD_NUM_EVT  			+","+
							this.FIELD_CHOIX_CLOTUREE  			+","+
							this.FIELD_CMT_NONCLOTUREE  			+","+
							this.FIELD_CODE_QUESTIONNAIRE  			+","+
							this.FIELD_CHOIX_FACTURABLE  			+","+
							this.FIELD_VISITEPREVENTIVE  			+","+
							this.FIELD_YN_FACTURABLE  			+","+
							this.FIELD_NOSERIE  			+","+
							this.FIELD_MACHINE_CODEARTICLE  			+","+
							this.FIELD_MACHINE_LIBARTICLE  			+","+
							this.FIELD_MACHINE_NUMSERIE  			+","+
							this.FIELD_MACHINE_CODESYMP  			+","+
							this.FIELD_MACHINE_LIBSYMP  			+","+
							this.FIELD_MACHINE_QUI  			+","+
							this.FIELD_MACHINE_MARQUE  			+","+
							this.FIELD_MACHINE_TYPEMACHINE  			+","+
							this.FIELD_MACHINE_MARQUEMACHINE  			+","+
							this.FIELD_MACHINE_SERIEMACHINE  			+","+
							this.FIELD_NUM_INTER  			+","+
							this.FIELD_NUMEROBC  			+","+
							this.FIELD_NUMERORAPPORT  			+","+
							this.FIELD_TH  			+","+
							this.FIELD_KH  			+","+
							this.FIELD_REF  			+","+
							
							this.FIELD_CONTACT_CODE  			+","+
							this.FIELD_CONTACT_TITRE 			+","+
							this.FIELD_CONTACT_NOM 			+","+
							 this.FIELD_TOURNEE 			+","+
							
														
							this.fIELD_FLAG+""+

			  		") VALUES ("+
			  		String.valueOf(KD_TYPE)+","+
			  		"'"+ent.SOC_CODE+"',"+
			  		"'"+ent.CODECLI+"',"+
			  		"'"+ent.CODEREP+"',"+
			  		"'"+ent.VU+"',"+
			  		"'"+ent.DATE+"',"+
			  		"'"+ent.HEUREDEBUT+"',"+
			  		"'"+ent.HEUREFIN+"',"+
					"'"+ent.FAMTYPEACT+"',"+
			  		"'"+ent.TYPEACT+"',"+
			  		"'"+MyDB.controlFld(ent.COMMENTAIRE)+"',"+
			  		"'"+MyDB.controlFld(ent.INFOCOMMERCIALE)+"',"+
			  		"'"+ent.NEXTDATE+"',"+
			  		"'"+ent.fIELD_EVT_ID+"',"+
					"'"+ent.NUM_EVT+"',"+
			  		"'"+ent.CHOIX_CLOTUREE+"',"+
			  		"'"+ent.CMT_NONCLOTUREE+"',"+
			  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
			  		"'"+ent.CHOIX_FACTURABLE+"',"+
			  		"'"+ent.VISITEPREVENTIVE+"',"+
			  		"'"+ent.YN_FACTURABLE+"',"+
			  		"'"+ent.NOSERIE+"',"+
			  		"'"+ent.MACHINE_CODEARTICLE+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_LIBARTICLE)+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_NUMSERIE)+"',"+
					"'"+ent.MACHINE_CODESYMP+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_LIBSYMP)+"',"+
					"'"+ent.MACHINE_QUI+"',"+
					"'"+ent.MACHINE_MARQUE+"',"+
					"'"+ent.MACHINE_TYPEMACHINE+"',"+
					"'"+ent.MACHINE_MARQUEMACHINE+"',"+
					"'"+MyDB.controlFld(ent.MACHINE_SERIEMACHINE) +"',"+
					"'"+ent.NUM_INTER +"',"+
					"'"+ent.NUMEROBC +"',"+
					"'"+ent.NUMERORAPPORT +"',"+
					"'"+ent.TH +"',"+
					"'"+ent.KH +"',"+
					"'"+MyDB.controlFld(ent.REF) +"',"+
					
					"'"+MyDB.controlFld(ent.CONTACT_CODE) +"',"+
					"'"+MyDB.controlFld(ent.CONTACT_TITRE) +"',"+
					"'"+MyDB.controlFld(ent.CONTACT_NOM) +"',"+
					 "'"+MyDB.controlFld(ent.TOURNEE) +"',"+
					"'"+KDSYNCHRO_UPDATE +"')";
					
					db.conn.execSQL(query);
				}
				if (cur!=null)
					cur.close();
			
		
			}
			catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
				return false;
			}

			return true;
	}
	public boolean save4_rapport(String numrapport,String numbl)
	{
		
			try
			{
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_NUMERORAPPORT + "=" + "'" +numrapport + "'  ";
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_NUMEROBC
							+ "="
							+ "'"
							+ MyDB.controlFld(numbl)
							+ "'"
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_NUMERORAPPORT + "=" + "'" +numrapport + "'  ";

					db.conn.execSQL(query);
					
				}
				if (cur!=null)
					cur.close();
				
			
		
			}
			catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
				return false;
			}

			return true;
	}


	public boolean _delete(String Codeclient,String codesoc,String date)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					FIELD_CODECLI+
					"='"+Codeclient+"'"+
					" and "+
					FIELD_SOC_CODE+
					"='"+codesoc+"' "+
					" and "+
					FIELD_DATE+
					"='"+date+"' "+
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
	public boolean delete_rapport(String Codeclient,String codesoc,String date,String numrapport)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					FIELD_CODECLI+
					"='"+Codeclient+"'"+
					" and "+
					FIELD_SOC_CODE+
					"='"+codesoc+"' "+
					" and "+
					FIELD_DATE+
					"='"+date+"' "+
					" and "+
					FIELD_NUMERORAPPORT+
					"='"+numrapport+"' "+
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

	public boolean updateNumBC(String numRapport,String numCde,StringBuffer err)
	{
		try
		{
			String query="UPDATE "+
					TABLENAME+		
					" SET "+
					this.FIELD_NUMEROBC+
					"=''"+
					" where "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' and "+
					this.FIELD_NUMEROBC+
					"='"+numCde+"'"+
					" and "+
					this.FIELD_NUMERORAPPORT+
					"='"+numRapport+"' "+
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
	
	public boolean deleteAll()
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					dbKD.fld_kd_dat_type+
					"='"+KD_TYPE+"' and "
					+FIELD_DATE+"<>'"+Fonctions.getYYYYMMDD()+"'";

			db.conn.execSQL(query);
			return true;
		}
		catch(Exception ex)
		{
			Global.lastErrorMessage=(ex.getMessage());
		}
		return false;
	}
	public String _queryDeleteFromOnServer(Context c,String codeclient,String date,String user)
	{
		String del="delete from "+TABLENAME+
				" where "+FIELD_CODECLI+"='"+codeclient+"'"+
				" and "+FIELD_DATE+"='"+date+"'"+
				" and "+dbKD.fld_kd_dat_type+"="+KD_TYPE+
				" and "+FIELD_CODEREP+"='"+user+"'";
		return del;
	}
	public String queryDeleteFromOnServer_rapport(Context c,String codeclient,String date,String numrapport,String user)
	{
		String del="delete from "+TABLENAME+
				" where "+FIELD_CODECLI+"='"+codeclient+"'"+
				" and "+FIELD_DATE+"='"+date+"'"+
				" and "+FIELD_NUMERORAPPORT+"='"+numrapport+"'"+
				" and "+dbKD.fld_kd_dat_type+"="+KD_TYPE+
				" and "+FIELD_CODEREP+"='"+user+"'";
		return del;
	}
	public String GetNumRapport(String identifiant)
	{
		//Code rep(5c) +hhmm+AAMMJJ
		String StValeur="";
		boolean existe=true;		
		DateCode dt=new DateCode();
		int i=0;
		
		String stDayofYear ="";
		String stYear ="";
		String minutes ="";

	
		
		while(existe==true)
		{
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat Annee = new SimpleDateFormat("yyyy");
			SimpleDateFormat minute = new SimpleDateFormat("HHmm");
			
			stDayofYear =Fonctions.getDay_Of_Year();
			stYear=Annee.format(gc.getTime());
			minutes=minute.format(gc.getTime());

			i++;
			StValeur=identifiant+dt.ToCode()+i;

			existe=false;
			/*
			 * Check dans Kems_data si il existe d�j� 
			 */
			String query="";
			query="select * from "+
					dbKD.TABLENAME+
					" where "+
					dbKD.fld_kd_dat_type+"='"+KD_TYPE+"' AND "+
					dbKD.fld_kd_cde_code+"='"+StValeur+"'";

			Cursor cur=db.conn.rawQuery (query,null);
			if (cur!=null)
			{
				if(cur.moveToNext())
				{
					existe=true;	
				}
			}
			if (cur!=null)
				cur.close();//MV 26/03/2015
		}

		return StValeur;		
	}

    public String getNumRapportFromBC(String numcde)
    {
        String numRapport = "";
        /*
         * Check dans Kems_data si il existe d�j�
         */
        String query="";
        query="select * from "+
                dbKD.TABLENAME+
                " where "+
                dbKD.fld_kd_dat_type+"='"+KD_TYPE+"' AND "+
                this.FIELD_NUMEROBC+"='"+numcde+"'";

        Log.e("query","getNumRapportFromBC"+query);

        Cursor cur=db.conn.rawQuery (query,null);
        if (cur!=null && cur.moveToFirst())
        {
            numRapport=this.giveFld(cur,this.FIELD_NUMERORAPPORT);
        }

        if (cur!=null)
            cur.close();//MV 26/03/2015

        return numRapport;
    }


	public String  _loadNomContact(String numrapport,  StringBuffer stBuf)
	{
	    String nom="";
		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_NUMERORAPPORT+"="+"'"+numrapport+"' ";
		
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			nom=this.giveFld(cur,this.FIELD_CONTACT_NOM);
			cur.close();
		}
		else
		{
			if (cur!=null)
				cur.close();
			return nom;
		}
		return nom;
	}



	

	
}
