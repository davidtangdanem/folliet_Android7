package com.menadinteractive.segafredo.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.menadinteractive.negos.quest.Question;
import com.menadinteractive.segafredo.communs.Global;

public class TableQuestionnaire extends DBMain{
	
	static public String TABLENAME="QUESTIONNAIREV20";

	public static final String FIELD_CODEQUESTIONNAIRE 		= "CODEQUESTIONNAIRE";
	public static final String FIELD_LIBQUESTIONNAIRE 		= "LIBQUESTIONNAIRE";
	public static final String FIELD_RANG 					= "RANG";
	public static final String FIELD_CODEQUESTION 			= "CODEQUESTION";
	public static final String FIELD_LIBQUESTION 			= "LIBQUESTION";
	public static final String FIELD_TYPERESPONSEATTENDU 	= "TYPERESPONSEATTENDU";
	public static final String FIELD_RESPONSEPOSSIBLE 		= "RESPONSEPOSSIBLE";
	public static final String FIELD_FORMATREPONSEATTENDU 	= "FORMATREPONSEATTENDU";
	public static final String FIELD_PROFIL 				= "PROFIL";
	public static final String FIELD_OBLIGATOIRE 				= "OBLIGATOIRE";
	

	public static String getFullFieldName(String field){
		return TABLENAME+"."+field;
	}


	public static final String TABLE_CREATE="CREATE TABLE ["+TABLENAME+"] ("+
			" ["+FIELD_CODEQUESTIONNAIRE			+"] [varchar](50) NULL"	+ COMMA +
			" ["+FIELD_LIBQUESTIONNAIRE			+"] [varchar](900) NULL"	+ COMMA +
			" ["+FIELD_RANG			+"] [varchar](50) NULL"	+ COMMA +
			" ["+FIELD_CODEQUESTION			+"] [varchar](50) NULL"	+ COMMA +
			" ["+FIELD_LIBQUESTION			+"] [varchar](900) NULL"	+ COMMA +
			" ["+FIELD_TYPERESPONSEATTENDU			+"] [varchar](50) NULL"	+ COMMA +
			" ["+FIELD_RESPONSEPOSSIBLE			+"] [varchar](900) NULL"	+ COMMA +
			" ["+FIELD_FORMATREPONSEATTENDU			+"] [varchar](50) NULL"	+ COMMA +
			" ["+FIELD_PROFIL			+"] [varchar](50) NULL"	+ COMMA +
			" ["+FIELD_OBLIGATOIRE			+"] [varchar](5) NULL"	+ 
			
			
			
			")";
	
	
	static public class structQuestionnaire
	{
	
		
		public String CODEQUESTIONNAIRE = "";        
		public String LIBQUESTIONNAIRE = "";
		public String RANG = "";
		public String CODEQUESTION  = "";    
		public String LIBQUESTION  = "";    
		public String TYPERESPONSEATTENDU  = "";    
		public String RESPONSEPOSSIBLE  = "";    
		public String FORMATREPONSEATTENDU  = "";    
		public String PROFIL  = ""; 
		public String OBLIGATOIRE  = ""; 
		
		
		
		
		
		
		@Override
		public String toString() {
			return "structQuestionnaire [CODEQUESTIONNAIRE=" + CODEQUESTIONNAIRE + ", LIBQUESTIONNAIRE=" + LIBQUESTIONNAIRE
					+ ", RANG=" + RANG + ",  CODEQUESTION=" + CODEQUESTION + ", LIBQUESTION=" + LIBQUESTION + ",  LIBQUESTION=" + LIBQUESTION 
					+ ", TYPERESPONSEATTENDU=" + TYPERESPONSEATTENDU + ",  RESPONSEPOSSIBLE=" + RESPONSEPOSSIBLE
					+ ", FORMATREPONSEATTENDU=" + FORMATREPONSEATTENDU + ",  PROFIL=" + PROFIL+ ",  OBLIGATOIRE=" + OBLIGATOIRE
										
					+"]";
		}
	}
		
	MyDB db;
	public TableQuestionnaire(MyDB _db)
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

	public structQuestionnaire load(Cursor cursor){
		structQuestionnaire Questionnaire = new structQuestionnaire();
		if(cursor != null){
			
		
			Questionnaire.CODEQUESTIONNAIRE = giveFld(cursor, FIELD_CODEQUESTIONNAIRE);
			Questionnaire.LIBQUESTIONNAIRE = giveFld(cursor, FIELD_LIBQUESTIONNAIRE);
			Questionnaire.RANG = giveFld(cursor, FIELD_RANG);
			Questionnaire.CODEQUESTION = giveFld(cursor, FIELD_CODEQUESTION);
			Questionnaire.LIBQUESTION = giveFld(cursor, FIELD_LIBQUESTION);
			Questionnaire.TYPERESPONSEATTENDU = giveFld(cursor, FIELD_TYPERESPONSEATTENDU);
			Questionnaire.RESPONSEPOSSIBLE = giveFld(cursor, FIELD_RESPONSEPOSSIBLE);
			Questionnaire.FORMATREPONSEATTENDU = giveFld(cursor, FIELD_FORMATREPONSEATTENDU);
			Questionnaire.PROFIL = giveFld(cursor, FIELD_PROFIL);
			Questionnaire.OBLIGATOIRE = giveFld(cursor, FIELD_OBLIGATOIRE);
			
			
	
		}

	
		return Questionnaire;
	}
	

	public structQuestionnaire load(String codequestionnaire, String codequestion){
		structQuestionnaire Questionnaire = new structQuestionnaire();
			String query = "SELECT *"
				+" FROM "+TABLENAME
				+" WHERE "+getFullFieldName(FIELD_CODEQUESTIONNAIRE)+" = "+codequestionnaire+" "
				+ "or "+getFullFieldName(FIELD_CODEQUESTION)+" = "+codequestion+"";

		Cursor cursor =  db.conn.rawQuery(query, null);

		if(cursor != null && cursor.moveToFirst()){

			Questionnaire = load(cursor);
			cursor.close();
		}

		return Questionnaire;
	}
		

	/**
	 * Permet d'obtenir une liste de structTarif à partir d'un cursor
	 * @param cursor
	 * @return ArrayList<structTarif>
	 */
	public ArrayList<structQuestionnaire> getListOfCursorQuestionnaire(Cursor cursor){
		ArrayList<structQuestionnaire> list = new ArrayList<TableQuestionnaire.structQuestionnaire>();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			structQuestionnaire Questionnaire = new structQuestionnaire();
			fillStruct(cursor,Questionnaire);
			list.add(Questionnaire);
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
				return cur.getInt(0);
			}
			return 0;
		}
		catch(Exception ex)
		{
			return -1;
		}

	}


	

	void fillStruct(Cursor cursor,structQuestionnaire Questionnaire)
	{
		
		
		Questionnaire.CODEQUESTIONNAIRE = giveFld(cursor, FIELD_CODEQUESTIONNAIRE);
		Questionnaire.LIBQUESTIONNAIRE = giveFld(cursor, FIELD_LIBQUESTIONNAIRE);
		Questionnaire.RANG = giveFld(cursor, FIELD_RANG);
		Questionnaire.CODEQUESTION = giveFld(cursor, FIELD_CODEQUESTION);
		Questionnaire.LIBQUESTION = giveFld(cursor, FIELD_LIBQUESTION);
		Questionnaire.TYPERESPONSEATTENDU = giveFld(cursor, FIELD_TYPERESPONSEATTENDU);
		Questionnaire.RESPONSEPOSSIBLE = giveFld(cursor, FIELD_RESPONSEPOSSIBLE);
		Questionnaire.FORMATREPONSEATTENDU = giveFld(cursor, FIELD_FORMATREPONSEATTENDU);
		Questionnaire.PROFIL = giveFld(cursor, FIELD_PROFIL);
		Questionnaire.OBLIGATOIRE = giveFld(cursor, FIELD_OBLIGATOIRE);
		
		
	}
	
	public ArrayList<Bundle> getQuestionnaireFilters(String codequestionnaire,String codequestion,  String filter)
	{
		try
		{
			
			
			
			filter=MyDB.controlFld(filter);
			String query ="";
			
				 query = "SELECT *"
							+" FROM "+TABLENAME
							+" WHERE ( "+FIELD_CODEQUESTIONNAIRE+" = '"+codequestionnaire+"' "
							+ "or "+FIELD_CODEQUESTION+" = '"+codequestion+"' ) order by "+FIELD_RANG;
			
			
			
			ArrayList<Bundle>  materiel=new ArrayList<Bundle>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Bundle cli=new Bundle();
				cli.putString(Global.dbQuestionnaire.FIELD_CODEQUESTIONNAIRE,cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_CODEQUESTIONNAIRE)));
				cli.putString(Global.dbQuestionnaire.FIELD_LIBQUESTIONNAIRE, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_LIBQUESTIONNAIRE)));
				cli.putString(Global.dbQuestionnaire.FIELD_RANG, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_RANG)));
				cli.putString(Global.dbQuestionnaire.FIELD_CODEQUESTION, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_CODEQUESTION)));
				cli.putString(Global.dbQuestionnaire.FIELD_LIBQUESTION,cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_LIBQUESTION)));
				cli.putString(Global.dbQuestionnaire.FIELD_TYPERESPONSEATTENDU, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_TYPERESPONSEATTENDU)));
				cli.putString(Global.dbQuestionnaire.FIELD_RESPONSEPOSSIBLE, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_RESPONSEPOSSIBLE)));
				cli.putString(Global.dbQuestionnaire.FIELD_FORMATREPONSEATTENDU, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_FORMATREPONSEATTENDU)));
				cli.putString(Global.dbQuestionnaire.FIELD_PROFIL, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_PROFIL)));
				cli.putString(Global.dbQuestionnaire.FIELD_OBLIGATOIRE, cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_OBLIGATOIRE)));
				
				
				
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
	
	public ArrayList<Question> getQuestionnaire(String codequestionnaire, Context context)
	{
		try
		{
			String query ="";
			
				 query = "SELECT *"
							+" FROM "+TABLENAME
							+" WHERE "+FIELD_CODEQUESTIONNAIRE+" = '"+codequestionnaire+"' "+
							"ORDER BY CAST("+FIELD_RANG+" as Integer) ";

			ArrayList<Question>  questions = new ArrayList<Question>();
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				Question question = new Question(context);
				question.setCodeFormatReponse(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_FORMATREPONSEATTENDU)));
				question.setCodeQuestion(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_CODEQUESTION)));
				question.setCodeQuestionnaire(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_CODEQUESTIONNAIRE)));
				question.setCodeTypeReponse(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_TYPERESPONSEATTENDU)));
				question.setLibelleQuestionnaire(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_LIBQUESTIONNAIRE)));
				question.setQuestionLibelle(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_LIBQUESTION)));
				question.setRang(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_RANG)));
				question.setReponseList(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_RESPONSEPOSSIBLE)));
				question.setObligatoire(cur.getString(cur.getColumnIndex(Global.dbQuestionnaire.FIELD_OBLIGATOIRE)));
				questions.add(question); 
			}
			if (cur!=null)
				cur.close();
			return questions;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return null;
	}
	
	public boolean getRecordQuestionnaire(ArrayList<Bundle> liste,boolean addBlanc)
	{
		try
		{
			/*
			 * Vidage des donn�es du spinner
			 */
		
			liste.clear();

			String query="SELECT "+this.FIELD_CODEQUESTIONNAIRE+","+ this.FIELD_LIBQUESTIONNAIRE+" FROM "+
					TABLENAME+
					" GROUP BY "+
					this.FIELD_CODEQUESTIONNAIRE+","+ this.FIELD_LIBQUESTIONNAIRE+
					" ORDER BY "+
					this.FIELD_CODEQUESTIONNAIRE;
			
			String prm_coderec	="";
			String prm_lbl		="";
			String prm_comment	="";
			String prm_value	="";
			
			if (addBlanc)
			{
				
				Bundle bundleblanc = new Bundle();
				bundleblanc.putString("prm_coderec", prm_coderec);
				bundleblanc.putString("prm_lbl", prm_lbl);
				bundleblanc.putString("prm_comment", prm_comment);
				bundleblanc.putString("prm_value", prm_value);
				liste.add(bundleblanc);
			}

			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{

				prm_coderec	=giveFld(cur,this.FIELD_CODEQUESTIONNAIRE);
				prm_lbl		=giveFld(cur,this.FIELD_LIBQUESTIONNAIRE);
				prm_comment	="";
				prm_value	="";


				Bundle bundle = new Bundle();
				bundle.putString("prm_coderec", prm_coderec);
				bundle.putString("prm_lbl", prm_lbl);
				bundle.putString("prm_comment", prm_comment);
				bundle.putString("prm_value", prm_value);
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
	
	
/*	public boolean getSymptomes_Machine(String machine,ArrayList<Bundle> liste)
	
	{
		boolean bres=false;
		
		try
		{
			liste.clear();
			Bundle bundleblanc = new Bundle();
			bundleblanc.putString(Global.dbSymptomes.FIELD_MACHINE, "");
			bundleblanc.putString(Global.dbSymptomes.FIELD_EQUIPEMENT, "");
			bundleblanc.putString(Global.dbSymptomes.FIELD_COD_SYMP, "");
			bundleblanc.putString(Global.dbSymptomes.FIELD_LIB_SYMP, "");
			liste.add(bundleblanc);
			
			if(Fonctions.GetStringDanem(machine).equals(""))
			{
				
				return false;
				
			}
				
			
			
			String query = "SELECT *"
					+" FROM "+TABLENAME
					+" WHERE  "+FIELD_MACHINE+" = '"+machine+"' ";
					 
					
			
				
			
			
			Cursor cur=db.conn.rawQuery(query, null);
			while (cur.moveToNext())
			{
				bres=true;
				Bundle cli=new Bundle();
				cli.putString(Global.dbSymptomes.FIELD_MACHINE,cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_MACHINE)));
				cli.putString(Global.dbSymptomes.FIELD_EQUIPEMENT, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_EQUIPEMENT)));
				cli.putString(Global.dbSymptomes.FIELD_COD_SYMP, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_COD_SYMP)));
				cli.putString(Global.dbSymptomes.FIELD_LIB_SYMP, cur.getString(cur.getColumnIndex(Global.dbSymptomes.FIELD_LIB_SYMP)));
			
				
				liste.add(cli); 
			}
			if (cur!=null)
				cur.close();
			return bres;
		}
		catch(Exception ex)
		{
			String err="";
			err=ex.getMessage();
			
		}

		return bres;
	}*/

}
