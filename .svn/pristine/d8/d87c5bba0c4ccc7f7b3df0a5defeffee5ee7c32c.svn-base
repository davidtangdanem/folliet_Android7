package com.menadinteractive.segafredo.db;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.dbKD101ClientVu.passePlat;
import com.menadinteractive.segafredo.db.dbKD101ClientVu.structClientvu;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class dbKD103Questionnaire extends dbKD{
	
	public final static int KD_TYPE = 103;
	public final String FIELD_SOC_CODE      		= fld_kd_soc_code;
	public final String FIELD_CODECLI      			= fld_kd_cli_code;
	public final String FIELD_CODEREP     		 	= fld_kd_dat_idx01;
	public final String FIELD_DATE        			= fld_kd_dat_data02;//YYYYMMDD
	public final String FIELD_CODE_QUESTIONNAIRE    = fld_kd_dat_data03;
	public final String FIELD_CODE_QUESTION       	= fld_kd_dat_data04;
	public final String FIELD_REPONSE   			= fld_kd_dat_data05;
	public final String FIELD_TYPEQUESTION   		= fld_kd_dat_data06;
	public final String FIELD_NUMRAPPORT   		= fld_kd_cde_code;

	MyDB db;
	public dbKD103Questionnaire(MyDB _db)
	{
		super();
		db=_db;	
	}
	static public class structQuestionnaireKD {
		public structQuestionnaireKD()
		{	
			super();

			DATE ="";
			SOC_CODE ="";
			CODECLI ="";
			CODEREP ="";
			DATE="";
			CODE_QUESTIONNAIRE="";
			CODE_QUESTION="";
			REPONSE="";
			TYPEQUESTION="";
			NUMRAPPORT="";
			
			
			
			
		}
		
		public String SOC_CODE ="";
		public String CODECLI ="";
		public String CODEREP ="";
		public String DATE="";
		public String CODE_QUESTIONNAIRE="";
		public String CODE_QUESTION="";
		public String REPONSE="";
		public String TYPEQUESTION="";
		public String NUMRAPPORT="";
		
		
	}
	

	static public class passePlat {
		public passePlat()
		{
			
			SOC_CODE ="";
			CODECLI ="";
			CODEREP ="";
			DATE="";
			CODE_QUESTIONNAIRE="";
			CODE_QUESTION="";
			REPONSE="";
			TYPEQUESTION="";
			NUMRAPPORT="";
					
		}

		public String SOC_CODE ="";
		public String CODECLI ="";
		public String CODEREP ="";
		public String DATE="";
		public String CODE_QUESTIONNAIRE="";
		public String CODE_QUESTION="";
		public String REPONSE="";
		public String TYPEQUESTION="";
		public String NUMRAPPORT="";
		
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
	
	public String _QuestionnaireExiste(String codecli , String date)
	{
		String stvaleur="";
		try
		{
			String query="select * from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'  "+
					" and "+this.FIELD_CODECLI+"="+"'"+codecli+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				stvaleur =this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE);
			}
			if (cur!=null)
				cur.close();
			
		}
		catch(Exception ex)
		{
			return stvaleur;
		}
		return stvaleur;

	}
	public String QuestionnaireExiste_rapport(String codecli , String date,String numrapport)
	{
		String stvaleur="";
		try
		{
			String query="select * from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'  "+
					" and "+this.FIELD_CODECLI+"="+"'"+codecli+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'"
				+" and "+this.FIELD_NUMRAPPORT+"='"+numrapport+"'";

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				stvaleur =this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE);
			}
			if (cur!=null)
				cur.close();
			
		}
		catch(Exception ex)
		{
			return stvaleur;
		}
		return stvaleur;

	}
	public String _Reponse(String codecli , String date,String codequestionnaire,String codequestion)
	{
		String stvaleur="";
		try
		{
			String query="select * from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'  "+
					" and "+this.FIELD_CODECLI+"="+"'"+codecli+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'"
			+" and "+this.FIELD_CODE_QUESTIONNAIRE+"='"+codequestionnaire+"'"
			+" and "+this.FIELD_CODE_QUESTION+"='"+codequestion+"'";
			
			

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				stvaleur =this.giveFld(cur,this.FIELD_REPONSE);
			}
			if (cur!=null)
				cur.close();
			
		}
		catch(Exception ex)
		{
			return stvaleur;
		}
		return stvaleur;

	}
	public String Reponse_rapport(String codecli , String date,String codequestionnaire,String codequestion,String numrapport)
	{
		String stvaleur="";
		try
		{
			String query="select * from "+TABLENAME+" where "+
					fld_kd_dat_type +"='"+KD_TYPE+"'  "+
					" and "+this.FIELD_CODECLI+"="+"'"+codecli+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'"
			+" and "+this.FIELD_CODE_QUESTIONNAIRE+"='"+codequestionnaire+"'"
			+" and "+this.FIELD_CODE_QUESTION+"='"+codequestion+"'"
			+" and "+this.FIELD_NUMRAPPORT+"='"+numrapport+"'";
					
			
			

			Cursor cur=db.conn.rawQuery(query, null);
			if (cur.moveToNext())
			{
				stvaleur =this.giveFld(cur,this.FIELD_REPONSE);
			}
			if (cur!=null)
				cur.close();
			
		}
		catch(Exception ex)
		{
			return stvaleur;
		}
		return stvaleur;

	}
	public boolean _load(structQuestionnaireKD ent,String stcodeclient, String date ,String codequestionnaire, StringBuffer stBuf)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'"
				+" and "+this.FIELD_CODE_QUESTIONNAIRE+"='"+codequestionnaire+"'";
		
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			ent.SOC_CODE =this.giveFld(cur,this.FIELD_SOC_CODE );	
			ent.CODECLI =this.giveFld(cur,this.FIELD_CODECLI );
			ent.CODEREP =this.giveFld(cur,this.FIELD_CODEREP );
			ent.DATE =this.giveFld(cur,this.FIELD_DATE );
			ent.CODE_QUESTIONNAIRE =this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE);
			ent.CODE_QUESTION =this.giveFld(cur,this.FIELD_CODE_QUESTION );		
			ent.REPONSE =this.giveFld(cur,this.FIELD_REPONSE );
			ent.TYPEQUESTION =this.giveFld(cur,this.FIELD_TYPEQUESTION );
			
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
	public boolean load_rapport(structQuestionnaireKD ent,String stcodeclient, String date ,String codequestionnaire,String numrapport, StringBuffer stBuf)
	{

		String query="SELECT * FROM "+
				TABLENAME+
				" where "+
				fld_kd_dat_type+"="+KD_TYPE+
				" and "+this.FIELD_CODECLI+"="+"'"+stcodeclient+"' "
				+" and "+this.FIELD_DATE+"='"+date+"'"
				+" and "+this.FIELD_CODE_QUESTIONNAIRE+"='"+codequestionnaire+"'"
				+" and "+this.FIELD_NUMRAPPORT+"='"+numrapport+"'";
		
		
		Cursor cur=db.conn.rawQuery (query,null);
		if(cur != null && cur.moveToFirst() )
		{
			ent.SOC_CODE =this.giveFld(cur,this.FIELD_SOC_CODE );	
			ent.CODECLI =this.giveFld(cur,this.FIELD_CODECLI );
			ent.CODEREP =this.giveFld(cur,this.FIELD_CODEREP );
			ent.DATE =this.giveFld(cur,this.FIELD_DATE );
			ent.CODE_QUESTIONNAIRE =this.giveFld(cur,this.FIELD_CODE_QUESTIONNAIRE);
			ent.CODE_QUESTION =this.giveFld(cur,this.FIELD_CODE_QUESTION );		
			ent.REPONSE =this.giveFld(cur,this.FIELD_REPONSE );
			ent.TYPEQUESTION =this.giveFld(cur,this.FIELD_TYPEQUESTION );
			ent.NUMRAPPORT =this.giveFld(cur,this.FIELD_NUMRAPPORT );
			
			
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

	public boolean _save(passePlat ent)
	{
		try
		{
			//on efface l'existant et on sauve
			//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
			
			String query = "SELECT * FROM " + TABLENAME + " where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
					+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
					+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "' and  "
					+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION + "'  ";
			
			
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext() ) {
				
				query = "UPDATE "
						+ TABLENAME
						+ " set "
						+ this.FIELD_REPONSE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.REPONSE)
						+ "' "
						+ " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
						+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "' and "
						+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION + "'  ";
			

				db.conn.execSQL(query);
				
			}
			else
			{
				
				 query="INSERT INTO " + TABLENAME +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_SOC_CODE   		+","+
						this.FIELD_CODECLI   		+","+ 
						this.FIELD_CODEREP       		+","+  
						this.FIELD_DATE  			+","+
						this.FIELD_CODE_QUESTIONNAIRE  			+","+
						this.FIELD_CODE_QUESTION  			+","+
						this.FIELD_REPONSE+","+
						this.FIELD_TYPEQUESTION+""+
						

		  		") VALUES ("+
		  		String.valueOf(KD_TYPE)+","+
		  		"'"+ent.SOC_CODE+"',"+
		  		"'"+ent.CODECLI+"',"+
		  		"'"+ent.CODEREP+"',"+
		  		"'"+ent.DATE+"',"+
		  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
		  		"'"+ent.CODE_QUESTION+"',"+
		  		"'"+ent.REPONSE+"',"+
		  		"'"+ent.TYPEQUESTION +"')";
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
	
	public boolean save_rapport(passePlat ent)
	{
		try
		{
			//on efface l'existant et on sauve
			//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
			
			String query = "SELECT * FROM " + TABLENAME + " where "
					+ fld_kd_dat_type + "=" + KD_TYPE + " and "
					+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
					+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
					+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "' and  "
					+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION +"' and  "
					+ this.FIELD_NUMRAPPORT + "=" + "'" + ent.NUMRAPPORT + "'  ";
			
			
			Cursor cur = db.conn.rawQuery(query, null);
			if (cur.moveToNext() ) {
				
				query = "UPDATE "
						+ TABLENAME
						+ " set "
						+ this.FIELD_REPONSE
						+ "="
						+ "'"
						+ MyDB.controlFld(ent.REPONSE)
						+ "' "
						+ " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
						+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "' and "
						+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION +"' and  "
						+ this.FIELD_NUMRAPPORT + "=" + "'" + ent.NUMRAPPORT + "'  ";
			

				db.conn.execSQL(query);
				
			}
			else
			{
				
				 query="INSERT INTO " + TABLENAME +" ("+
						dbKD.fld_kd_dat_type+","+
						this.FIELD_SOC_CODE   		+","+
						this.FIELD_CODECLI   		+","+ 
						this.FIELD_CODEREP       		+","+  
						this.FIELD_DATE  			+","+
						this.FIELD_CODE_QUESTIONNAIRE  			+","+
						this.FIELD_CODE_QUESTION  			+","+
						this.FIELD_REPONSE+","+
						this.FIELD_TYPEQUESTION+","+
						this.FIELD_NUMRAPPORT+""+
						
						

		  		") VALUES ("+
		  		String.valueOf(KD_TYPE)+","+
		  		"'"+ent.SOC_CODE+"',"+
		  		"'"+ent.CODECLI+"',"+
		  		"'"+ent.CODEREP+"',"+
		  		"'"+ent.DATE+"',"+
		  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
		  		"'"+ent.CODE_QUESTION+"',"+
		  		"'"+ent.REPONSE+"',"+
		  		"'"+ent.TYPEQUESTION+"',"+
		  		"'"+ent.NUMRAPPORT +"')";
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
	
	public boolean _save2(structQuestionnaireKD ent)
	{
		
			try
			{
				//on efface l'existant et on sauve
				//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
						+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "'  and "
						+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION + "'  ";
			
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_REPONSE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.REPONSE)
							+ "' "
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
							+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
							+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "' and "
							+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION + "'  ";

					db.conn.execSQL(query);
					
				}
				else
				{
					
					 query="INSERT INTO " + TABLENAME +" ("+
							dbKD.fld_kd_dat_type+","+
							this.FIELD_SOC_CODE   		+","+
							this.FIELD_CODECLI   		+","+ 
							this.FIELD_CODEREP       		+","+  
							this.FIELD_DATE  			+","+
							this.FIELD_CODE_QUESTIONNAIRE  			+","+
							this.FIELD_CODE_QUESTION  			+","+
							this.FIELD_REPONSE+","+
							this.FIELD_TYPEQUESTION+""+
							
													
						

			  		") VALUES ("+
			  		String.valueOf(KD_TYPE)+","+
			  		"'"+ent.SOC_CODE+"',"+
			  		"'"+ent.CODECLI+"',"+
			  		"'"+ent.CODEREP+"',"+
			  		"'"+ent.DATE+"',"+
			  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
			  		"'"+ent.CODE_QUESTION+"',"+
			  		"'"+ent.REPONSE+"',"+
			  		"'"+ent.TYPEQUESTION +"')";
					
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

	public boolean save2_rapport(structQuestionnaireKD ent)
	{
		
			try
			{
				//on efface l'existant et on sauve
				//delete(ent.CODECLI,ent.SOC_CODE,ent.DATE);
				
				String query = "SELECT * FROM " + TABLENAME + " where "
						+ fld_kd_dat_type + "=" + KD_TYPE + " and "
						+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
						+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
						+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "'  and "
						+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION +  "'  and "
						+ this.FIELD_NUMRAPPORT + "=" + "'" + ent.NUMRAPPORT + "'  ";
			
				
				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.moveToNext() ) {
					
					query = "UPDATE "
							+ TABLENAME
							+ " set "
							+ this.FIELD_REPONSE
							+ "="
							+ "'"
							+ MyDB.controlFld(ent.REPONSE)
							+ "' "
							+ " where "
							+ fld_kd_dat_type + "=" + KD_TYPE + " and "
							+ this.FIELD_CODECLI + "=" + "'" + ent.CODECLI + "' AND "
							+ this.FIELD_DATE + "=" + "'" + ent.DATE + "' and "
							+ this.FIELD_CODE_QUESTIONNAIRE + "=" + "'" + ent.CODE_QUESTIONNAIRE + "' and "
							+ this.FIELD_CODE_QUESTION + "=" + "'" + ent.CODE_QUESTION +  "'  and "
							+ this.FIELD_NUMRAPPORT + "=" + "'" + ent.NUMRAPPORT + "'  ";

					db.conn.execSQL(query);
					
				}
				else
				{
					
					 query="INSERT INTO " + TABLENAME +" ("+
							dbKD.fld_kd_dat_type+","+
							this.FIELD_SOC_CODE   		+","+
							this.FIELD_CODECLI   		+","+ 
							this.FIELD_CODEREP       		+","+  
							this.FIELD_DATE  			+","+
							this.FIELD_CODE_QUESTIONNAIRE  			+","+
							this.FIELD_CODE_QUESTION  			+","+
							this.FIELD_REPONSE+","+
							this.FIELD_TYPEQUESTION+","+
							this.FIELD_NUMRAPPORT+""+
							
							
													
						

			  		") VALUES ("+
			  		String.valueOf(KD_TYPE)+","+
			  		"'"+ent.SOC_CODE+"',"+
			  		"'"+ent.CODECLI+"',"+
			  		"'"+ent.CODEREP+"',"+
			  		"'"+ent.DATE+"',"+
			  		"'"+ent.CODE_QUESTIONNAIRE+"',"+
			  		"'"+ent.CODE_QUESTION+"',"+
			  		"'"+MyDB.controlFld(ent.REPONSE)+"',"+
			  		"'"+ent.TYPEQUESTION+"',"+
			  		"'"+ent.NUMRAPPORT +"')";
					
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
	public boolean _delete(String Codeclient,String codesoc,String date,String codequestionnaire,String codequestion)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					FIELD_CODECLI+
					"='"+Codeclient+"'"+
					" and "+
					FIELD_CODE_QUESTIONNAIRE+
					"='"+codequestionnaire+"' "+
					" and "+
					FIELD_CODE_QUESTION+
					"='"+codequestion+"' "+
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
	public boolean delete_rapport(String Codeclient,String codesoc,String date,String codequestionnaire,String codequestion,String numrapport)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					FIELD_CODECLI+
					"='"+Codeclient+"'"+
					" and "+
					FIELD_CODE_QUESTIONNAIRE+
					"='"+codequestionnaire+"' "+
					" and "+
					FIELD_CODE_QUESTION+
					"='"+codequestion+"' "+
					" and "+
					FIELD_DATE+
					"='"+date+"' "+
					" and "+
					FIELD_NUMRAPPORT+
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
	public boolean _deleteQuestionnaire(String Codeclient,String codesoc,String date,String codequestionnaire)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					FIELD_CODECLI+
					"='"+Codeclient+"'"+
					" and "+
					FIELD_CODE_QUESTIONNAIRE+
					"='"+codequestionnaire+"' "+
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
	public boolean deleteQuestionnaire_rapport(String Codeclient,String codesoc,String date,String codequestionnaire,String numrapport)
	{
		try
		{
			String query="DELETE from "+
					TABLENAME+		
					" where "+
					FIELD_CODECLI+
					"='"+Codeclient+"'"+
					" and "+
					FIELD_CODE_QUESTIONNAIRE+
					"='"+codequestionnaire+"' "+
					
					" and "+
					FIELD_DATE+
					"='"+date+"' "+
					" and "+
					FIELD_NUMRAPPORT+
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

	public boolean deleteQuestionnaireWithoutRapport(String Codeclient,String numrapport)
	{
		try
		{

				String query = "SELECT * FROM " + TABLENAME  +
						" where "+
						FIELD_CODECLI+
						"='"+Codeclient+"'"+
						" and "+
						FIELD_NUMRAPPORT+
						"='"+numrapport+"' "+
						" and "+
						dbKD.fld_kd_dat_type+
						"='"+KD_TYPE+"' ";

				Log.e("query ","deleteQuestionnaire=> "+ query);

				Cursor cur = db.conn.rawQuery(query, null);
				if (cur.getCount() > 0 ) {

					query = "DELETE from " +
							TABLENAME +
							" where " +
							FIELD_CODECLI +
							"='" + Codeclient + "'" +
							" and " +
							FIELD_NUMRAPPORT +
							"='" + numrapport + "' " +
							" and " +
							dbKD.fld_kd_dat_type +
							"='" + KD_TYPE + "' ";

					db.conn.execSQL(query);
					return true;
				}
			} catch(Exception ex)
			{
				Global.lastErrorMessage=(ex.getMessage());
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
	


}
