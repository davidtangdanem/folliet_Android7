package com.menadinteractive.segafredo.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.MyParser;
import com.menadinteractive.segafredo.communs.MyWS;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableContactcli;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD;
import com.menadinteractive.segafredo.db.dbKD105AgendaSrvSupp;
import com.menadinteractive.segafredo.db.dbKD732InfoClientComplementaires;
import com.menadinteractive.segafredo.db.dbKD733Habitudes;
import com.menadinteractive.segafredo.db.dbLog;
import com.menadinteractive.segafredo.db.dbLogWS;
import com.menadinteractive.segafredo.db.dbProspect;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.synchro.SynchroActivity;

import static com.menadinteractive.segafredo.communs.MyWS.isModeTest;

public class task_sendWSData extends AsyncTask<Void, Void, Integer>{
	Context context;
	Handler hSync;
	String login;
	String password;
	private static task_sendWSData INSTANCE = new task_sendWSData();
	final String scenar_sendmediasfiles = "SEND_MEDIASFILES";
	final String scenar_sendsignatures = "SEND_SIGNATURE";
	final String scenar_sendpdf = "SEND_PDF";
	final String scenar_sendpdfSign = "SEND_PDFSIGN";
	
	final String scenar_sendpdfrapport = "SEND_RAPPORTPDF";
	
	MyDB db = null;
	
	OnTaskFinish mListener;
	
	private task_sendWSData()
	{
		
	}
	public static task_sendWSData getInstance()
	{	return INSTANCE;
	}
	
	
	public task_sendWSData(Context context,Handler h, OnTaskFinish listener){
		super();
		this.context = context;
		
		hSync=h;
		login = Preferences.getValue(context, Espresso.LOGIN, "0");
		password = Preferences.getValue(context, Espresso.PASSWORD, "0");

		mListener = listener;
	}
	
	public void setDB(MyDB _db){
		db = _db;
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(Void... params) {
		
		//		WS(login, password, "Exec", "GETTOURNEE", " and datevis='20121115' and soc_code='"+socCode+"' ");
		//		WS(login, password, "EXPRESSO_getTournee", "EXPRESSO", " and datevis='20121114' and soc_code='4000' ");
		//return WS(login, password, "EXPRESSO_getTournee", date, socCode, zone);
 
		//getReapproDechargementForWS(login,password);
		
		//les synchro qui ont des pb si lancé en meme temps que le module
		//risque d'envoi pendant le création
		if (hSync!=null)
		{
			Global.dbLog.Insert("TASK SEND", "onCreate", "", "SYNC", "", "", "");

			String DATABASE_NAME = MyDB.backupDB(MyDB.source, login);
			Fonctions.DeleteFilesOlderThan(Environment.getExternalStorageDirectory()+"/",20);
			if (isModeTest())
			{
				Global.dbClient.deleteProspectTest();

			}


			getRetourMachineClientForWS(login,password);
			getComptageMachineClientForWS(login,password);
		
			getEncaissementForWS(login,password);
			getEncaissementFactureForWS(login,password);
			getRemiseBanqueForWS(login,password);
			
			getUpdateCliForWS(login, password);
			getProspectForWs(login, password);
		 
			String errorcontact=getContactcliForWs(login,password);
			if (errorcontact.equals("") /*|| Global.dbClient.CountProspect()>0*/)
			{
				errorcontact = getUpdateContactcliForWS(login, password);
				if (errorcontact.equals("") /*|| Global.dbClient.CountProspect()>0*/)
				{
			//		getDeleteContactcliForWS(login, password);
				}
				
				
			}
			
			if(getDeleteClientvuForWS(login, password)==1)
		 	 getCliVuForWS(login,password);

			getAgendaForWS(login,password);//desactiv mv 11/02/2016
			//ajout 20180315 tof, envoie et flagage des suppression de rdv
			if ( getAgendaSuppForWS(login,password) == 1 )
			{
				StringBuffer err = new StringBuffer();
				Global.dbKDAgendaSrvSupp.resetFlag(err);
			}

			//getQuestForWS(login,password);
			getQuestionnaireForWS(login,password);
			getMarchandiseForWS(login,password);

			if (getSynchroDataForWs( dbKD732InfoClientComplementaires.KD_TYPE,dbKD732InfoClientComplementaires.FIELD_CODECLIENT,
					" ("+	dbKD732InfoClientComplementaires.FIELD_CODECLIENT+"<>'')", login,password,"UPDATE" ))
			{
				dbKD732InfoClientComplementaires info=new dbKD732InfoClientComplementaires(Global.dbParam.getDB());
				info.clear(new StringBuilder());
			}
			//Marche pas...
			/*if (getSynchroDataForWs( dbKD733Habitudes.KD_TYPE, dbKD733Habitudes.FIELD_COD_CLT,
					" ("+	dbKD733Habitudes.FIELD_COD_CLT+"<>'')", login,password,"UPDATE" ))
			{
				dbKD733Habitudes habitudes=new dbKD733Habitudes(Global.dbParam.getDB());
				habitudes.clear(new StringBuilder());
			}*/
			getHabitudesForWS(login,password);
		}
		
		getKemsLogWs(login, password);
		//Global.dbLog.Clear(new StringBuilder());
		 int iresult=0;
		 iresult= getCommandeForWS(login,password);
		 String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		 Log.i("tag", "Heure fin: "+mydate);
		 StringBuilder err=new StringBuilder();
		 Log.e("db",""+db);
		 /*if (db != null)
		 {
			 db.compact(err);
		 }*/

		 
		 sendMediasFiles();
		 sendSignaturesFiles();
		 sendPdf();
		 sendPdfsignaturesFiles();
		 sendPdfRapport() ;
		//tof, pour ne pas avoir de freeze à chaque fois qu'on revient dans l'écran principal
		/*if (android.os.Build.VERSION.SDK_INT >= 14) {
			Global.dbKDAgendaSrv.CreateAllEvents("FLS", context);		//creation des rdv serveur avant pour recup aprés dans 102
			Global.dbKDAgenda.getAllEvents("FLT", context, true);		//delete avant recup
			//finalement, pas besoin de creer en 102 les rdv serveur
			//Global.dbKDAgenda.getAllEvents("FLS", m_appState, false);		//pas de delete, pour cumulé FLT et FLS
		}*/

		return iresult;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (hSync!=null)
		{
			Message msg=new Message();
			msg.what=455;
			hSync.sendMessage(msg);
			
			if(mListener != null){
				mListener.onTerminated();
			}
		}
	}

	 String getQuestForWS(String user,String pwd)
		{
			String stReturnOK="";
			try
			{
				String datekey=Fonctions.getYYYYMMDD();
				//la requete d'insertion du KD
				String queryInsert=dbKD.KD_INSERT_STRING;
				//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
				String ident=Preferences.getValue( context, Espresso.LOGIN, "0");;
				ident=ident.toLowerCase();
				if (ident.contains("test"))
				{
					queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
				}
					//on cherche les entetes de commandes 
					String queryHdr="SELECT * FROM "+
							dbKD.TABLENAME+
							" where "+
							" ( "+
							dbKD.fld_kd_dat_type+
							"="+
							Global.dbKDFillPlan.KD_TYPE+
							" and "+
							Global.dbKDFillPlan.FIELD_SEND+
							"='1'"+
							" and "+
							Global.dbKDFillPlan.FIELD_DATEKEY+
							"<>'"+ datekey+ "'"+
							" ) ";					
	
					String hdr="";
					Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
					while (cur.moveToNext())
					{
						hdr+=context.getString(R.string.separateur_morpion)+"(";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDFillPlan.giveFld(cur, dbKD.fld_kd_val_data39))+"')";
	
					}
					StringBuffer buferr=new StringBuffer();
					StringBuilder err=new StringBuilder();
	
					String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
					//si le resultat est ok on efface la cde
					if (resultWS.equals(""))
					{
						stReturnOK ="VALIDEOK";
						Global.dbKDFillPlan.deleteSent()	;
					}
					else
					{
			//			FurtiveMessageBox(buferr.toString());
					}
	
				
	
	
	
			}
			catch(Exception ex)
			{
				return "";
			}
	
			return stReturnOK;
		}
	 
	 String getQuestionnaireForWS(String user,String pwd)
		{
			String stReturnOK="";
			try
			{
				String datekey=Fonctions.getYYYYMMDD();
				//la requete d'insertion du KD
				String queryInsert=dbKD.KD_INSERT_STRING;
				//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
				String ident=Preferences.getValue( context, Espresso.LOGIN, "0");;
				ident=ident.toLowerCase();
				if (ident.contains("test"))
				{
					queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
				}
					//on cherche les entetes de commandes 
					String queryHdr="SELECT * FROM "+
							dbKD.TABLENAME+
							" where "+
							" ( "+
							dbKD.fld_kd_dat_type+
							"="+
							Global.dbKDQuestionnaire.KD_TYPE+
							" ) ";					
	
					String hdr="";
					Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
					while (cur.moveToNext())
					{
						hdr+=context.getString(R.string.separateur_morpion)+"(";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
						hdr+="'"+MyDB.controlFld(Global.dbKDQuestionnaire.giveFld(cur, dbKD.fld_kd_val_data39))+"')";
	
					}
					StringBuffer buferr=new StringBuffer();
					StringBuilder err=new StringBuilder();
	
					String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
					//si le resultat est ok on efface la cde
					if (resultWS.equals(""))
					{
						stReturnOK ="VALIDEOK";
						Global.dbKDQuestionnaire.deleteAll()	;
					}
					else
					{
			//			FurtiveMessageBox(buferr.toString());
					}
	
				
	
	
	
			}
			catch(Exception ex)
			{
				return "";
			}
	
			return stReturnOK;
		}

	String getMarchandiseForWS(String user,String pwd)
	{
		String stReturnOK="";
		try
		{
			StringBuilder aa=new StringBuilder();
			//Global.dbKDMarchandise.clear(true,aa);
			String datekey=Fonctions.getYYYYMMDD();
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=Preferences.getValue( context, Espresso.LOGIN, "0");;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}
			//on cherche les entetes de commandes
			String queryHdr="SELECT * FROM "+
					dbKD.TABLENAME+
					" where "+
					" ( "+
					dbKD.fld_kd_dat_type+
					"="+
					Global.dbKDMarchandise.KD_TYPE+
					" and "+
					dbKD.fld_kd_dat_idx06+
					"='0' ) ";

			String hdr="";
			Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
			while (cur.moveToNext())
			{
				hdr+=context.getString(R.string.separateur_morpion)+"(";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data39))+"')";

			}
			StringBuffer buferr=new StringBuffer();
			StringBuilder err=new StringBuilder();

			String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
			//si le resultat est ok on efface la cde
			if (resultWS.equals(""))
			{
				stReturnOK ="VALIDEOK";
				Global.dbKDMarchandise.UpdateFlag(err)	;
			}
			else
			{
				//			FurtiveMessageBox(buferr.toString());
			}





		}
		catch(Exception ex)
		{
			return "";
		}

		return stReturnOK;
	}
	String getHabitudesForWS(String user,String pwd)
	{
		String stReturnOK="";
		try
		{
			StringBuilder aa=new StringBuilder();
			//Global.dbKDMarchandise.clear(true,aa);
			String datekey=Fonctions.getYYYYMMDD();
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=Preferences.getValue( context, Espresso.LOGIN, "0");;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}
			//on cherche les entetes de commandes
			String queryHdr="SELECT * FROM "+
					dbKD.TABLENAME+
					" where "+
					" ( "+
					dbKD.fld_kd_dat_type+
					"="+
					dbKD733Habitudes.KD_TYPE+" ) ";

			String hdr="";
			Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
			while (cur.moveToNext())
			{
				hdr+=context.getString(R.string.separateur_morpion)+"(";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
				hdr+="'"+MyDB.controlFld(Global.dbKDMarchandise.giveFld(cur, dbKD.fld_kd_val_data39))+"')";

			}
			StringBuffer buferr=new StringBuffer();
			StringBuilder err=new StringBuilder();

			String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
			//si le resultat est ok on efface la cde
			if (resultWS.equals(""))
			{
				stReturnOK ="VALIDEOK";
				dbKD733Habitudes habitudes=new dbKD733Habitudes(Global.dbParam.getDB());
				habitudes.clear(new StringBuilder());
			}
			else
			{
				//			FurtiveMessageBox(buferr.toString());
			}





		}
		catch(Exception ex)
		{
			return "";
		}

		return stReturnOK;
	}

	int getCommandeForWS(String user,String pwd)
	{
		
		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		Log.i("tag", "Heure début requête : "+mydate);
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}


			ArrayList<String> tabCde=new ArrayList<String>();
			int nbrcde=ListeCdesAEnvoyer(tabCde);
			
			TableSouches souche = null;
			//TableSouches souches=new TableSouches(Global.dbParam.getDB(),context);
			
			//if(db != null){
			if(Global.dbParam.getDB() != null){
				
				//souche=new TableSouches(db,this.context);
				souche=new TableSouches(Global.dbParam.getDB(),context);
			}

			//on va faire une trame par cde, pour valider l'envoi cde par cde
			for (int i=0;i<tabCde.size();i++)
			{


				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDEntCde.KD_TYPE+
						" or "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDLinCde.KD_TYPE+
						
						" ) "+ 
						" and "+
						Global.dbKDEntCde.FIELD_ENTCDE_CODECDE+
						"="+
						"'"+tabCde.get(i)+"' ORDER BY "+dbKD.fld_kd_dat_type+" DESC";					
						
				String hdr="";
				String numSouche = "";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					if(souche != null){
						numSouche = souche.get(MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data07)), Preferences.getValue(this.context, Espresso.LOGIN, "0"));
					}
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Fonctions.ReplaceGuillemet(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data02))).trim()+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+numSouche+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data39))+"')";

				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					StringBuilder errV=new StringBuilder();
					StringBuffer  errB=new StringBuffer();
					Global.dbKDEntCde.deleteCde( tabCde.get(i),"",errB)	;
					Global.dbKDLinCde.deleteNumcde(tabCde.get(i), errB);
					
					//Global.dbLog.Clear(new StringBuilder());
				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}

			}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	
	int getRetourMachineClientForWS(String user,String pwd)
	{
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDRetourMachineClient.KD_TYPE+") ";
					/*	"  and "+
						Global.dbKDRetourMachineClient.FIELD_RMC_SENT+" is null";
*/
				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourMachineClient.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					StringBuffer err= new StringBuffer();
					//Global.dbKDRetourMachineClient.setSentToServer( )	;
					Global.dbKDRetourMachineClient.deleteAll(new StringBuffer());
				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	//
	int getEncaissementForWS(String user,String pwd)
	{
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDEncaissement.KD_TYPE+
						" and "+
						dbKD.fld_kd_dat_data11+
						"='0'"+
						" ) ";					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaissement.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					
					Global.dbKDEncaissement.updateEtatOfEncaissement()	;

				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	int getEncaissementFactureForWS(String user,String pwd)
	{
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDEncaisserFacture.KD_TYPE+
						" and "+
						dbKD.fld_kd_dat_data11+
						"='0'"+
						" ) ";					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEncaisserFacture.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					
					Global.dbKDEncaisserFacture.updateEtatOfEncaissementFacture()	;

				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	
	int getRemiseBanqueForWS(String user,String pwd)
	{
		try
		{
			//Mise à jour CH en CHP si date différent de today
			//Global.dbKDRetourBanqueLin.updateremiseChequeportefeuille();
						
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDRetourBanqueEnt.KD_TYPE+
						" or "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDRetourBanqueLin.KD_TYPE+
						
						" ) ";					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDRetourBanqueEnt.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					
					//Global.dbKDEncaisserFacture.DeleteOfEncaissementFacture()	;--> les lignes d'encaisserFacture sont supprimés en meme 
					//temps que les lignes d'encaissements				
					Global.dbKDEncaissement.DeleteOfEncaissement()	;
					Global.dbKDRetourBanqueEnt.DeleteRetourBanqueEnt()	;
					Global.dbKDRetourBanqueLin.DeleteRetourBanqueLin()	;								

				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	
	int getComptageMachineClientForWS(String user,String pwd)
	{
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDComptageMachineClient.KD_TYPE+
						" ) ";					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					StringBuffer err= new StringBuffer();
					Global.dbKDComptageMachineClient.deleteAll(err)	;

				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	int getReapproDechargementForWS(String user,String pwd)
	{
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDLinTransfert.KD_TYPE+
						" and ( "+
						Global.dbKDLinTransfert.FIELD_LIGNEMVTS_QTEREA+"<>'' OR "+Global.dbKDLinTransfert.FIELD_LIGNEMVTS_QTEDECH+"<>'' )" +
						
						
						" ) ";					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDLinTransfert.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{

					Global.dbKDLinTransfert.UpdateQteTheo(context);
					Global.dbKDLinTransfert.Reset( )	;

				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	int getCliVuForWS(String user,String pwd)
	{
		try
		{
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}


			ArrayList<String> tabCde=new ArrayList<String>();
			
			//TOF: Envoi en un coup, puis suppression de toutes les lignes
			//int nbrcde=ListeCdesAEnvoyer(tabCde);

			//on va faire une trame par cde, pour valider l'envoi cde par cde
			//for (int i=0;i<tabCde.size();i++)
			{


				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						dbKD.TABLENAME+
						" where "+
						" ( "+
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDClientVu.KD_TYPE+
						" and "+
						Global.dbKDClientVu.fIELD_FLAG+
					   "!='"+Global.dbKDClientVu.KDSYNCHRO_RESET+"'"+
						
						" ) "+ 
						" ORDER BY "+dbKD.fld_kd_dat_type+" DESC";					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_soc_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_cli_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_pro_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_vis_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_cde_code  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_type  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx01 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx02 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx03 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx04 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx05 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx06 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx07 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx08 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx09 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_idx10 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data01))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data03))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data04))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data05))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data06))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data07))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data08))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data09))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data10))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data11))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data12))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data13))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data14))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data15))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data16))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data17))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data18))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data19))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data20))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data21))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data22))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data23))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data24))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data25))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data26))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data27))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data28))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data29))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data30))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data31))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data32))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data33))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data34))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data35))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data36))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data37))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data38))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_val_data39))+"')";



				}


				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{
					StringBuilder errV=new StringBuilder();
					StringBuffer  errB=new StringBuffer();
					Global.dbKDClientVu.resetFlag(errB);
					Global.dbKDClientVu.deleteAll( )	;
				}
				else
				{
			//		Fonctions.FurtiveMessageBox(buferr.toString());
				}

			}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	String sendMediasFiles() {
		try {
			ExternalStorage externalStorage = new ExternalStorage(context, false);
			
			File signatures = new File(externalStorage.getPhotosFolder());
			File[] files_sig = signatures.listFiles();

			if (files_sig == null)
				return "";
			for (File f : files_sig) {
				if (f.isDirectory())
					continue;

				// si fichier trop gros on l'efface
				long size = f.length();
				if (size <= Global.maxMediaFileSize /*
													 * || Fonctions.Right(f.
													 * getAbsolutePath
													 * (),3).equals("mp4")
													 */) {
					String NameImage;

					// MV BUG pourquoi RIGHT ?
					String filename = f.getName();
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(f.getName(),
					// externalStorage.getMediasFolder().length()));
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(,
					// externalStorage.getMediasFolder().length()));

					// si envoi ok on efface le fichier
					String resultWS = MyWS.WSSendFile(login,password, "SendFile",
							scenar_sendmediasfiles, f.getName(),
							MyParser.convertFileToByteArray(f));
					if (resultWS.equals("true") || resultWS.equals("err")) {
						StringBuffer err=new StringBuffer();
						f.delete();
					}
					else
					{
						//Global.dbKDImagesSend.deletePhoto(filename, err);
						SaveImageSend(filename,externalStorage.getBackupPhotosFolder());
						f.delete();
					}
				} else {
					try {

						f.delete();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return "";
		} catch (Exception ex) {

		}

		return "";
	}
	String sendPdf() {
		try {
			ExternalStorage externalStorage = new ExternalStorage(context, false);

			File signatures = new File(externalStorage.getPDFFolder());
			File[] files_sig = signatures.listFiles();

			if (files_sig == null)
				return "";
			for (File f : files_sig) {
				if (f.isDirectory())
					continue;

				// si fichier trop gros on l'efface
				long size = f.length();
				if (size <= Global.maxMediaFileSize /*
													 * || Fonctions.Right(f.
													 * getAbsolutePath
													 * (),3).equals("mp4")
													 */) {
					String NameImage;

					// MV BUG pourquoi RIGHT ?
					String filename = f.getName();
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(f.getName(),
					// externalStorage.getMediasFolder().length()));
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(,
					// externalStorage.getMediasFolder().length()));

					// si envoi ok on efface le fichier

					String resultWS = MyWS.WSSendFile(login,password, "SendFile",
							scenar_sendpdf, f.getName(),
							MyParser.convertFileToByteArray(f));
					if (resultWS.equals("true") || resultWS.equals("err")) {
						StringBuffer err=new StringBuffer();
						//Global.dbKDImagesSend.deletePhoto(filename, err);
						f.delete();
					}
					else{
						SavePDFSend(filename,externalStorage.getBackupPDFFolder());
						f.delete();
					}
				} else {
					try {

						f.delete();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return "";
		} catch (Exception ex) {

		}

		return "";
	}
	String sendPdfRapport() {
		try {
			ExternalStorage externalStorage = new ExternalStorage(context, false);
			
			File signatures = new File(externalStorage.getPDFRapportFolder());
			File[] files_sig = signatures.listFiles();

			if (files_sig == null)
				return "";
			for (File f : files_sig) {
				if (f.isDirectory())
					continue;

				// si fichier trop gros on l'efface
				long size = f.length();
				if (size <= Global.maxMediaFileSize /*
													 * || Fonctions.Right(f.
													 * getAbsolutePath
													 * (),3).equals("mp4")
													 */) {
					String NameImage;

					// MV BUG pourquoi RIGHT ?
					String filename = f.getName();
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(f.getName(),
					// externalStorage.getMediasFolder().length()));
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(,
					// externalStorage.getMediasFolder().length()));

					// si envoi ok on efface le fichier
					
					String resultWS = MyWS.WSSendFile(login,password, "SendFile",
							scenar_sendpdfrapport, f.getName(),
							MyParser.convertFileToByteArray(f));
					if (resultWS.equals("true") || resultWS.equals("err") ) {
						StringBuffer err=new StringBuffer();
						//Global.dbKDImagesSend.deletePhoto(filename, err);
						f.delete();
					}
					else
					{
						SavePDFSendRapport(filename,externalStorage.getBackupPDFRapportFolder());
						f.delete();
					}
				} else {
					try {

						f.delete();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return "";
		} catch (Exception ex) {

		}

		return "";
	}
	String sendSignaturesFiles() {
		try {
			ExternalStorage externalStorage = new ExternalStorage(context, false);
			
			File signatures = new File(externalStorage.getSignaturesFolder());
			File[] files_sig = signatures.listFiles();

			if (files_sig == null)
				return "";
			for (File f : files_sig) {
				if (f.isDirectory())
					continue;

				// si fichier trop gros on l'efface
				long size = f.length();
				if (size <= Global.maxMediaFileSize /*
													 * || Fonctions.Right(f.
													 * getAbsolutePath
													 * (),3).equals("mp4")
													 */) {
					String NameImage;

					// MV BUG pourquoi RIGHT ?
					String filename = f.getName();
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(f.getName(),
					// externalStorage.getMediasFolder().length()));
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(,
					// externalStorage.getMediasFolder().length()));

					// si envoi ok on efface le fichier
					String resultWS = MyWS.WSSendFile(login,password, "SendFile",
							scenar_sendsignatures, f.getName(),
							MyParser.convertFileToByteArray(f));
					if (resultWS.equals("true") || resultWS.equals("err")) {
						StringBuffer err=new StringBuffer();
						//Global.dbKDImagesSend.deletePhoto(filename, err)
						f.delete();
					}
					else
					{
						SaveSignaturesSend(filename,externalStorage.getBackupSignaturesFolder());

						f.delete();
					}
				} else {
					try {

						f.delete();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return "";
		} catch (Exception ex) {

		}

		return "";
	}
	String sendPdfsignaturesFiles() {
		try {
			ExternalStorage externalStorage = new ExternalStorage(context, false);
			
			File signatures = new File(externalStorage.getPDFSignaturesFolder());
			File[] files_sig = signatures.listFiles();

			if (files_sig == null)
				return "";
			for (File f : files_sig) {
				if (f.isDirectory())
					continue;

				// si fichier trop gros on l'efface
				long size = f.length();
				if (size <= Global.maxMediaFileSize /*
													 * || Fonctions.Right(f.
													 * getAbsolutePath
													 * (),3).equals("mp4")
													 */) {
					String NameImage;

					// MV BUG pourquoi RIGHT ?
					String filename = f.getName();
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(f.getName(),
					// externalStorage.getMediasFolder().length()));
					// NameImage=Fonctions.GetStringDanem(Fonctions.Right(,
					// externalStorage.getMediasFolder().length()));

					// si envoi ok on efface le fichier
					String resultWS = MyWS.WSSendFile(login,password, "SendFile",
							scenar_sendpdfSign, f.getName(),
							MyParser.convertFileToByteArray(f));
					if (resultWS.equals("true") || resultWS.equals("err")) {
						StringBuffer err=new StringBuffer();
						//Global.dbKDImagesSend.deletePhoto(filename, err);

						f.delete();
					}
					else{
						SaveSignaturesSend(filename,externalStorage.getBackupSignaturesFolder());
						f.delete();
					}
				} else {
					try {

						f.delete();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return "";
		} catch (Exception ex) {

		}

		return "";
	}
	
	boolean SaveImageSend(String fileName,String filepath)
	{
		try {
			//si c'est un ficier avec au moins 5 tirets on peut en extraire le code client
			if (fileName.endsWith(".jpg") )
			{
				ExternalStorage externalStorage = new ExternalStorage(context, false);
				
				String path=externalStorage.getBackupPhotosFolder()+"/";
				Fonctions.createDirectoryEx(path);

				return ExternalStorage.moveFile(externalStorage.getPhotosFolder()+fileName,path+fileName);
			}
		
			return false;
			
		} catch (Exception e) {
			//si on arrive pas à copier on efface
		}
		return false;
	}
	boolean SavePDFSend(String fileName,String filepath)
	{
		try {
			//si c'est un ficier avec au moins 5 tirets on peut en extraire le code client
			if (fileName.endsWith(".pdf") )
			{
				ExternalStorage externalStorage = new ExternalStorage(context, false);

				String path=externalStorage.getBackupPDFFolder()+"/";
				Fonctions.createDirectoryEx(path);
						 
				return ExternalStorage.moveFile(externalStorage.getPDFFolder()+fileName,path+fileName);
			}
		
			return false;
			
		} catch (Exception e) {
			//si on arrive pas à copier on efface
			Log.d("Tag",e.getLocalizedMessage());
		}
		return false;
	}
	boolean SavePDFSendRapport(String fileName,String filepath)
	{
		try {
			//si c'est un ficier avec au moins 5 tirets on peut en extraire le code client
			if (fileName.endsWith(".pdf") )
			{
				ExternalStorage externalStorage = new ExternalStorage(context, false);
				
				String path=externalStorage.getBackupPDFRapportFolder()+"/";
				Fonctions.createDirectoryEx(path);
						 
				return ExternalStorage.moveFile(externalStorage.getPDFRapportFolder()+fileName,path+fileName);
			}
		
			return false;
			
		} catch (Exception e) {
			//si on arrive pas à copier on efface
		}
		return false;
	}
	boolean SaveSignaturesSend(String fileName,String filepath)
	{
		try {
			//si c'est un ficier avec au moins 5 tirets on peut en extraire le code client
			if (fileName.endsWith(".jpg") )
			{
				ExternalStorage externalStorage = new ExternalStorage(context, false);
				
				String path=externalStorage.getBackupSignaturesFolder()+"/";
				Fonctions.createDirectoryEx(path);
						 
				return ExternalStorage.moveFile(externalStorage.getSignaturesFolder()+fileName,path+fileName);
			}
		
			return false;
			
		} catch (Exception e) {
			//si on arrive pas à copier on efface
		}
		return false;
	}

	int getAgendaForWS(String user,String pwd)
	{
		try {
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

			// on cherche les entetes
			String queryHdr = "SELECT * FROM " + dbKD.TABLENAME + " where "
					+ " ( " + dbKD.fld_kd_dat_type + "="
					+ Global.dbKDAgenda.KD_TYPE + " ) ";
			// on efface sur le serveur l'equivalent en periode, de ce que l'on
			// envoi

			String hdr = Global.dbKDAgenda.queryDeleteFromOnServer(context)
					+ context.getString(R.string.separateur_morpion);
			Cursor cur = Global.dbParam.getDB().conn.rawQuery(queryHdr, null);
			while (cur.moveToNext()) {
				hdr += queryInsert + "(";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_soc_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_cli_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_pro_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_vis_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_cde_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_type)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx01)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx02)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx03)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx04)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx05)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx06)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx07)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx08)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx09)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_idx10)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data01)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data02)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data03)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data04)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data05)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data06)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data07)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data08)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data09)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data10)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data11)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data12)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data13)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data14)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data15)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data16)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data17)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data18)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data19)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data20)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data21)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data22)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data23)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data24)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data25)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data26)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data27)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data28)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data29)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_dat_data30)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data31)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data32)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data33)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data34)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data35)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data36)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data37)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data38)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgenda.giveFld(cur,
						dbKD.fld_kd_val_data39)) + "')"
						+ context.getString(R.string.separateur_morpion);

			}
			if (cur!=null)
				cur.close();//MV 26/03/2015
			StringBuffer buferr = new StringBuffer();

			String resultWS = MyWS.WSSend(user, pwd, "UpdateSrvTableNonSec",
					"UPDATE", hdr);
			// si le resultat est ok on efface la cde
			if (resultWS.equals("")) {

			} else {
				//FurtiveMessageBox(Global.lastErrorMessage);
			}


		} catch (Exception ex) {
			return 0;
		}

		return 1;
	}
	int getAgendaSuppForWS(String user,String pwd)
	{
		try {
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

			// on cherche les entetes
			String queryHdr = "SELECT * FROM " + dbKD.TABLENAME + " where "
					+ " ( " + dbKD.fld_kd_dat_type + "="
					+ Global.dbKDAgendaSrvSupp.KD_TYPE + " ) ";
			// on efface sur le serveur l'equivalent en periode, de ce que l'on
			// envoi

			String hdr = Global.dbKDAgendaSrvSupp.queryDeleteFromOnServer(context)
					+ context.getString(R.string.separateur_morpion);
			Cursor cur = Global.dbParam.getDB().conn.rawQuery(queryHdr, null);
			while (cur.moveToNext()) {
				hdr += queryInsert + "(";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_soc_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_cli_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_pro_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_vis_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_cde_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_type)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx01)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx02)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx03)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx04)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx05)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx06)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx07)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx08)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx09)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_idx10)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data01)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data02)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data03)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data04)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data05)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data06)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data07)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data08)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data09)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data10)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data11)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data12)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data13)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data14)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data15)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data16)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data17)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data18)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data19)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data20)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data21)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data22)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data23)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data24)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data25)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data26)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data27)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data28)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data29)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_dat_data30)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data31)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data32)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data33)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data34)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data35)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data36)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data37)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data38)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDAgendaSrvSupp.giveFld(cur,
						dbKD.fld_kd_val_data39)) + "')"
						+ context.getString(R.string.separateur_morpion);

			}
			if (cur!=null)
				cur.close();//MV 26/03/2015
			StringBuffer buferr = new StringBuffer();

			String resultWS = MyWS.WSSend(user, pwd, "UpdateSrvTableNonSec",
					"UPDATE", hdr);
			// si le resultat est ok on efface la cde
			if (resultWS.equals("")) {

			} else {
				//FurtiveMessageBox(Global.lastErrorMessage);
			}


		} catch (Exception ex) {
			return 0;
		}

		return 1;
	}

	int getDeleteClientvuForWS(String user,String pwd)
	{
		try {
			//la requete d'insertion du KD
			String queryInsert=dbKD.KD_INSERT_STRING;
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			String ident=user;
			ident=ident.toLowerCase();
			if (ident.contains("test"))
			{
				queryInsert=queryInsert.replace(dbKD.TABLENAME, dbKD.TABLENAME_TEST);
			}

			// on cherche les entetes
			String queryHdr = "SELECT * FROM " + dbKD.TABLENAME + " where "
					+ " ( " + dbKD.fld_kd_dat_type + "="
					+ Global.dbKDClientVu.KD_TYPE + " and "+
					Global.dbKDClientVu.fIELD_FLAG+
					   "!='"+Global.dbKDClientVu.KDSYNCHRO_RESET+"' )";
			// on efface sur le serveur l'equivalent en periode, de ce que l'on
			// envoi
			String hdr="";
			Cursor cur = Global.dbParam.getDB().conn.rawQuery(queryHdr, null);
			while (cur.moveToNext()) {
				 hdr += Global.dbKDClientVu.queryDeleteFromOnServer_rapport(context,Global.dbKDClientVu.giveFld(cur,
						dbKD.fld_kd_cli_code),Global.dbKDClientVu.giveFld(cur,
								dbKD.fld_kd_dat_data02),Global.dbKDClientVu.giveFld(cur,
										dbKD.fld_kd_cde_code),user)
						+ context.getString(R.string.separateur_morpion);
				

			}
			if (cur!=null)
				cur.close();//MV 26/03/2015
			StringBuffer buferr = new StringBuffer();
			
			String resultWS = MyWS.WSSend(user, pwd, "UpdateSrvTableNonSec",
					"UPDATE", hdr);
			// si le resultat est ok on efface la cde
			if (resultWS.equals("")) {
				return 1;

			} else {
				//FurtiveMessageBox(Global.lastErrorMessage);
			}

			
		} catch (Exception ex) {
			return 0;
		}

		return 1;
	}
	
	/**
	 * on stock dans un tableau les cdes � transmettre
	 * @author Marc VOUAUX
	 * @return
	 */
	int ListeCdesAEnvoyer(ArrayList<String> tabCde)
	{

		int i=0;
		try {
			//on cherche les entetes de commandes 
			String queryHdr="SELECT * FROM "+
					dbKD.TABLENAME+
					" where "+
					dbKD.fld_kd_dat_type+
					"="+ 
					Global.dbKDEntCde.KD_TYPE+" "
							+ "and (("+Global.dbKDEntCde.FIELD_ENTCDE_SEND+"='1'  and "+Global.dbKDEntCde.FIELD_ENTCDE_TYPEDOC+"='CR') "
							
									+ " or "+Global.dbKDEntCde.FIELD_ENTCDE_TYPEDOC+"<>'CR')";

			i = 0;
			String hdr="";
			Cursor cur=   Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
			while (cur.moveToNext())
			{
				String numCde=Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_cde_code );
				String type=Global.dbKDEntCde.giveFld(cur, dbKD.fld_kd_dat_data07 );
				
				//Verifier si il y a des ligne
				if(Global.dbKDLinCde.Count_Numcde(numCde,false)>0)
				{
					tabCde.add(numCde);
					i++;
				}
				if(Global.dbKDLinCde.Count_Numcde(numCde,false)==0 && type.equals(Fonctions.GetStringDanem(TableSouches.TYPEDOC_INTERVENTION)))
				{
					tabCde.add(numCde);
					i++;
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	/**
	 * recup les prospects pour les envoyer au serveur
	 * @return
	 */
	String getProspectForWs(String user,String pwd)
	{
		try
		{
			dbProspect dbSiteClient=new dbProspect();
			//la requete d'insertion du KD
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			
			{
				boolean bFind=false;
				//la requete d'insertion du KD
				String queryInsert=dbSiteClient.KD_INSERT_STRING;
				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						Global.dbClient.TABLENAME+
						" where "+
						" ( "+
						Global.dbClient.FIELD_CREAT +
						"='"+ Global.dbClient.CLI_CREATION+ "' ) "	;					

				String hdr="";
				Cursor cur= Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					String stLat=MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_LAT));
					String stLon=MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_LON));
								
					//transforme lat et lon en flottant pour le serveur
					if (!Fonctions.GetStringDanem( stLat ).contains("."))
					{
						float lat= ((float)Fonctions.convertToFloat(stLat))/1000000;
						float lon= ((float)Fonctions.convertToFloat(stLon))/1000000;
	
						stLat=String.valueOf(lat);
						stLon=String.valueOf(lon);
					}
					
					bFind=true;
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					//hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_SOC_CODE   ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CODE  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_NOM  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_ENSEIGNE  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_ADR1  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_ADR2  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CP))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_VILLE))+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_TEL1 ))+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur,Global.dbClient.FIELD_FAX))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur,Global.dbClient.FIELD_GSM ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_EMAIL ))+"',";
					hdr+="'P',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_SIRET ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_NUMTVA))+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld(user)+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur,Global.dbClient.FIELD_JOURFERMETURE))+"',";
					hdr+="'"+Fonctions.getYYYYMMDD()+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_TYPEETABLISSEMENT))+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld("X")+"',";
					hdr+="'"+MyDB.controlFld("X")+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PERIODICITE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_TOURNEE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CLI_ANNUEL))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CLI_AGENT2))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_SAISON_ETE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_SAISON_HIVER))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_ACTIVITE_P))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_FAMCLIENT))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_SFAMCLIENT))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_STANDBY))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_DATECLI))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CLASSE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CLI_PASMAIL))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_VOL_CAFE_ANNUEL))+"',";

					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_POT_CA))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PLACEASSINT))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PLACEASSEXT))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_CAPACITE_SDR))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_NB_CHAMBRES))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_NB_LITS))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_QUALIF))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_SITUATION))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_OPTION_P))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_TYPECUISINE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PV_CAFE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PV_THE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PV_CHOCOLAT))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PV_PETIT_DEJ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_PV_CHAMBRE))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_STD_BY))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbClient.giveFld(cur, Global.dbClient.FIELD_INFO_TERRAIN))+"',";
					hdr+="'"+MyDB.controlFld("")+"',";
					hdr+="'"+MyDB.controlFld(stLat)+"',";
					hdr+="'"+MyDB.controlFld(stLon)+"')";

				}
				if (bFind)
				{
					StringBuffer buferr=new StringBuffer();
					String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
					//si le resultat est ok on efface la cde
					if (resultWS.equals(""))
		  			{
						Global.dbClient.deFlagProspectsNouveu();
	
					}
					else
					{
						return "err";
						//FurtiveMessageBox(buferr.toString());
					}
				}
			}


			return "";
		}
		catch(Exception ex)
		{
			return "err";
		}

	
	}
	
	//construit la chaine a envoyer au WS pour modif
	public String getUpdateCliForWS(String user,String pwd)
	{
		dbSiteListeLogin.structlistLogin rep = null;
		rep=new dbSiteListeLogin.structlistLogin();
		boolean bResponsable=false;
		dbSiteListeLogin login=new dbSiteListeLogin(Global.dbParam.getDB());
		login.getlogin(user, rep, new StringBuilder());
		if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) )
		{
			bResponsable=true;
		}

		String completeString="";
		try {
			String query="SELECT * FROM "+
					Global.dbClient.TABLENAME+
					" where "+
					" ( "+
					Global.dbClient.FIELD_CREAT +
					"='"+ Global.dbClient.CLI_MODIFICATION+ "' ) "	;

			Log.e("query",""+query);

			Cursor cur=Global.dbParam.getDB().conn.rawQuery(query,null);
			if (cur!=null)
			{
				while(cur.moveToNext())
				{
					String stWhere="";
					String code=cur.getString(cur.getColumnIndex(Global.dbClient.FIELD_CODE));
					TableClient.structClient cli=new TableClient.structClient();
					Global.dbClient.getClient(code, cli, new StringBuilder());
				
					//transforme lat et lon en flottant pour le serveur
					if (!Fonctions.GetStringDanem( cli.LAT ).contains("."))
					{
						float lat= ((float)Fonctions.convertToFloat(cli.LAT))/1000000;
						float lon= ((float)Fonctions.convertToFloat(cli.LON))/1000000;
						
						cli.LAT=String.valueOf(lat);
						cli.LON=String.valueOf(lon);
					}

					if(bResponsable==true)
					{
						stWhere=" where "+
								dbProspect.FIELD_CLI_CODECLIENT+"='"+cli.CODE+"'  ";
					}
					else
					{
						stWhere=" where "+
								dbProspect.FIELD_CLI_CODECLIENT+"='"+cli.CODE+"' AND "+
								dbProspect.FIELD_CLI_CODEAGENT+"='"+user+"'  ";
					}
					
					String update=
							"UPDATE "+
									dbProspect.TABLENAMEPROSPECT+
									" set "+
									dbProspect.FIELD_CLI_RAISOC+"='"+ 			MyDB.controlFld(cli.NOM.trim())  +"',"+
									dbProspect.FIELD_CLI_ENSEIGNE+"='"+ 		MyDB.controlFld(cli.ENSEIGNE.trim())    +"',"+
									dbProspect.FIELD_CLI_ADR1+"='"+ 			MyDB.controlFld(cli.ADR1.trim())  +"',"+
									dbProspect.FIELD_CLI_ADR2+"='"+	 			MyDB.controlFld(cli.ADR2.trim())  +"',"+
									dbProspect.FIELD_CLI_CP+"='"+ 				MyDB.controlFld(cli.CP.trim())  +"',"+
									dbProspect.FIELD_CLI_VILLE+"='"+ 			MyDB.controlFld(cli.VILLE.trim())  +"',"+
									dbProspect.FIELD_CLI_TEL1+"='"+ 			MyDB.controlFld(cli.TEL1.trim())  +"',"+
									dbProspect.FIELD_CLI_FAX+"='"+ 				MyDB.controlFld(cli.FAX.trim())  +"',"+
									dbProspect.FIELD_CLI_GSM+"='"+ 				MyDB.controlFld(cli.GSM.trim())  +"',"+
									dbProspect.FIELD_CLI_EMAIL+"='"+  			MyDB.controlFld(cli.EMAIL.trim()) +"',"+
									dbProspect.FIELD_CLI_CODETYPE+"='"+  		MyDB.controlFld(cli.TYPE.trim()) +"',"+
									dbProspect.FIELD_CLI_SIRET+"='"+ 			MyDB.controlFld(cli.SIRET.trim())  +"',"+
									dbProspect.FIELD_CLI_TVA_INTRA+"='"+ 		MyDB.controlFld(cli.NUMTVA.trim())  +"',"+
									dbProspect.FIELD_CLI_GRPCLI+"='"+  			MyDB.controlFld(cli.GROUPECLIENT.trim()) +"',"+
									dbProspect.FIELD_CLI_JR_FERME+"='"+ 		MyDB.controlFld(cli.JOURFERMETURE.trim())  +"',"+
									dbProspect.FIELD_CLI_CODEAGENT+"='"+ 		user  +"',"+
									dbProspect.FIELD_CLI_COULEURSAV+"='"+ 		MyDB.controlFld(cli.TYPESAV.trim())  +"',"+
									dbProspect.FIELD_CLI_CODEMODERGLMT+"='"+ 	MyDB.controlFld(cli.MODEREGLEMENT.trim())  +"',"+
									dbProspect.FIELD_CLI_TYPEETABL+"='"+ 		MyDB.controlFld(cli.TYPEETABLISSEMENT.trim())  +"',"+
									dbProspect.FIELD_CLI_CODECIRCUIT+"='"+ 		MyDB.controlFld(cli.CIRCUIT.trim())  +"',"+
									dbProspect.FIELD_CLI_PERIODICITE+"='"+ 		MyDB.controlFld(cli.PERIODICITE.trim())  +"',"+
									dbProspect.FIELD_CLI_TOURNEE+"='"+ 			MyDB.controlFld(cli.TOURNEE.trim())  +"',"+
									dbProspect.FIELD_CLI_ANNUEL+"='"+ 		MyDB.controlFld(cli.CLI_ANNUEL.trim())  +"',"+
									dbProspect.FIELD_CLI_AGENT2+"='"+ 			MyDB.controlFld(cli.CLI_AGENT2.trim())  +"',"+
									dbProspect.FIELD_CLI_SAISON_ETE+"='"+ 		MyDB.controlFld(cli.SAISON_ETE.trim())  +"',"+
									dbProspect.FIELD_CLI_SAISON_HIVER+"='"+ 	MyDB.controlFld(cli.SAISON_HIVER.trim())  +"',"+
									dbProspect.FIELD_CLI_ACTIVITE_P+"='"+ 		MyDB.controlFld(cli.ACTIVITE_P.trim())  +"',"+
									dbProspect.FIELD_CLI_FAMCLIENT+"='"+ 		MyDB.controlFld(cli.FAMCLIENT.trim())  +"',"+
									dbProspect.FIELD_CLI_SFAMCLIENT+"='"+ 		MyDB.controlFld(cli.SFAMCLIENT.trim())  +"',"+
									dbProspect.FIELD_CLI_STANDBY+"='"+ 			MyDB.controlFld(cli.STANDBY.trim())  +"',"+
									dbProspect.FIELD_CLI_DATECLI+"='"+ 			MyDB.controlFld(cli.DATECLI.trim())  +"',"+
									dbProspect.FIELD_CLI_CLASSE+"='"+ 			MyDB.controlFld(cli.CLASSE.trim())  +"',"+
									dbProspect.FIELD_CLI_PASMAIL+"='"+ 			MyDB.controlFld(cli.CLI_PASMAIL.trim())  +"',"+
									dbProspect.FIELD_CLI_VOl_CAFE_ANNUEL+"='"+ 	MyDB.controlFld(cli.VOL_CAFE_ANNUEL.trim())  +"',"+
									dbProspect.FIELD_CLI_POT_CA+"='"+ 			MyDB.controlFld(cli.POT_CA.trim())  +"',"+
									dbProspect.FIELD_CLI_PLACEASSINT+"='"+ 		MyDB.controlFld(cli.PLACEASSINT.trim())  +"',"+
									dbProspect.FIELD_CLI_PLACEASSEXT+"='"+ 		MyDB.controlFld(cli.PLACEASSEXT.trim())  +"',"+
									dbProspect.FIELD_CLI_CAPACITE_SDR+"='"+ 	MyDB.controlFld(cli.CAPACITE_SDR.trim())  +"',"+
									dbProspect.FIELD_CLI_NB_CHAMBRES+"='"+ 		MyDB.controlFld(cli.NB_CHAMBRES.trim())  +"',"+
									dbProspect.FIELD_CLI_NB_LITS+"='"+ 			MyDB.controlFld(cli.NB_LITS.trim())  +"',"+
									dbProspect.FIELD_CLI_QUALIF+"='"+ 			MyDB.controlFld(cli.QUALIF.trim())  +"',"+
									dbProspect.FIELD_CLI_SITUATION+"='"+ 		MyDB.controlFld(cli.SITUATION.trim())  +"',"+
									dbProspect.FIELD_CLI_OPTION_P+"='"+ 		MyDB.controlFld(cli.OPTION_P.trim())  +"',"+
									dbProspect.FIELD_CLI_TYPECUISINE+"='"+ 		MyDB.controlFld(cli.TYPECUISINE.trim())  +"',"+
									dbProspect.FIELD_CLI_PV_CAFE+"='"+ 			MyDB.controlFld(cli.PV_CAFE.trim())  +"',"+
									dbProspect.FIELD_CLI_PV_THE+"='"+ 			MyDB.controlFld(cli.PV_THE.trim())  +"',"+
									dbProspect.FIELD_CLI_PV_CHOCOLAT+"='"+ 		MyDB.controlFld(cli.PV_CHOCOLAT.trim())  +"',"+
									dbProspect.FIELD_CLI_PV_PETIT_DEJ+"='"+ 	MyDB.controlFld(cli.PV_PETIT_DEJ.trim())  +"',"+
									dbProspect.FIELD_CLI_PV_CHAMBRE+"='"+ 		MyDB.controlFld(cli.PV_CHAMBRE.trim())  +"',"+
									dbProspect.FIELD_CLI_STD_BY+"='"+ 			MyDB.controlFld(cli.STD_BY.trim())  +"',"+
									dbProspect.FIELD_CLI_INFO_TERRAIN+"='"+ 	MyDB.controlFld(cli.INFO_TERRAIN.trim())  +"',"+
									dbProspect.FIELD_CLI_ENVOIFACT_PAR_MAIL+"='"+ 	MyDB.controlFld(cli.ENVOIFACT_PAR_MAIL.trim())  +"',"+
									dbProspect.FIELD_CLI_LAT+"='"+ 				MyDB.controlFld(cli.LAT.trim())  +"',"+
									dbProspect.FIELD_CLI_LON+"='"+ 				MyDB.controlFld(cli.LON.trim())  +"',"+
									dbProspect.FIELD_CLI_FLAG+"='"+				Global.dbClient.FLAG_UPDATE+"'"+
									stWhere;


							 
				
					completeString+=update+context.getString(R.string.separateur_morpion);
				}

				Log.e("completeString",""+completeString);

				StringBuffer buferr=new StringBuffer();
				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableNonSec","UPDATE",completeString);

				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
	  			{
					Global.dbClient.deFlagProspectsModif();
				}
				else
				{
					Log.e("err",""+resultWS);
					return "err";
					//FurtiveMessageBox(buferr.toString());
				}
				
			}
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "err";
				
	}

	public static String getKemsLogWs(String user,String pwd)
	{
		try
		{
		
			//la requete d'insertion du KD
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible
			
			{
				boolean bFind=false;
				//la requete d'insertion du KD
				String queryInsert=dbLog.INSERT_STRING;
				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						Global.dbLog.TABLENAME;					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{

					bFind=true;
					//hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="¤(";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_DATE  		 ))+"',";
					hdr+="'"+Global.version+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_ECRAN  		))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_FONCTION  	))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_PARAMS  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_MESSAGE  	))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_REQUETE  	))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_EXCEPTION   ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_COMPLEMENT ))+"',";
					hdr+="'"+user+"')";

				}
				if (bFind)
				{
					StringBuffer buferr=new StringBuffer();
					String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
					//si le resultat est ok on efface la cde
					if (resultWS.equals(""))
		  			{
						Log.e("envoiLog","OK");
						Global.dbLog.Clear(new StringBuilder());
					}
					else
					{
						Log.e("envoiLog","ERROR");
						return "err";
						//FurtiveMessageBox(buferr.toString());
					}
				}
			}


			return "";
		}
		catch(Exception ex)
		{
			return "err";
		}

	
	}

    public static String getTableLogWs(String user,String pwd)
    {
        try
        {

            //la requete d'insertion du KD
            //si on est sur un identifiant de test (qui contient test) on change la tabgle cible

            {
                boolean bFind=false;
                //la requete d'insertion du KD
                String queryInsert=dbLogWS.INSERT_STRING;
                //on cherche les entetes de commandes
                String queryHdr="SELECT * FROM "+
                        Global.dbLLogWs.TABLENAME;

                String hdr="";
                Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
                while (cur.moveToNext())
                {

                    bFind=true;
                    //hdr+=context.getString(R.string.separateur_morpion)+"(";
                    hdr+="¤(";
                    hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_LOGIN  		 ))+"',";
                    hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_DATEHEUREDEBUT  		))+"',";
                    hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_NOMTABLE  	))+"',";
                    hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_DUREE  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_DATEHEUREFIN  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_RESULTAT  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_OPERATEUR  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_OPERATEUR_ETAT  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_WIFI  	 ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbLLogWs.giveFld(cur, Global.dbLLogWs.FIELD_IDUNIQUE ))+"')";


                }
                if (bFind)
                {
                    StringBuilder buferr=new StringBuilder();
                    String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
                    //si le resultat est ok on efface la cde
                    if (resultWS.equals(""))
                    {
                        Log.e("envoiLogWS","OK");
                        Global.dbLLogWs.clear(buferr);
                    }
                    else
                    {
                        Log.e("envoiLogWS","ERROR");
                        return "err";
                        //FurtiveMessageBox(buferr.toString());
                    }
                }
            }


            return "";
        }
        catch(Exception ex)
        {
            return "err";
        }


    }


	public String getContactcliForWs(String user,String pwd)
	{
		try
		{
			
			//la requete d'insertion du KD
			//si on est sur un identifiant de test (qui contient test) on change la tabgle cible

			{
				boolean bFind=false;
				//la requete d'insertion du KD
				String queryInsert=Global.dbContactcli.KD_INSERT_STRING;
				//on cherche les entetes de commandes 
				String queryHdr="SELECT * FROM "+
						Global.dbContactcli.TABLENAME+
						" where "+
						" ( "+
						Global.dbContactcli.FIELD_FLAG +
						"='"+ Global.dbContactcli.CONTACT_CREATION+ "' ) "	;					

				String hdr="";
				Cursor cur=Global.dbParam.getDB().conn.rawQuery(queryHdr,null);
				while (cur.moveToNext())
				{
					
					
					bFind=true;
					hdr+=context.getString(R.string.separateur_morpion)+"(";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur, Global.dbContactcli.FIELD_CODECONTACT   ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur, Global.dbContactcli.FIELD_CODECLIENT  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur, Global.dbContactcli.FIELD_NOM  ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur, Global.dbContactcli.FIELD_PRENOM ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur, Global.dbContactcli.FIELD_MOBILE ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur, Global.dbContactcli.FIELD_EMAIL ))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur,Global.dbContactcli.FIELD_CODEFONCTION))+"',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur,Global.dbContactcli.FIELD_COMMENTAIRE ))+"',";
					hdr+="'C',";
					hdr+="'"+MyDB.controlFld(Global.dbContactcli.giveFld(cur,Global.dbContactcli.FIELD_CIVIL ))+"')";
				}
				if (bFind)
				{
					StringBuffer buferr=new StringBuffer();
					String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
					//si le resultat est ok on efface la cde
					if (resultWS.equals(""))
					{
						Global.dbContactcli.reset(Global.dbContactcli.CONTACT_CREATION);

					}
					else
					{
						return "err";
						//FurtiveMessageBox(buferr.toString());
					}
				}
			}


			return "";
		}
		catch(Exception ex)
		{
			return "err";
		}


	}
	//construit la chaine a envoyer au WS pour modif
	public String getUpdateContactcliForWS(String user,String pwd)
	{
		String completeString="";
		try {
			String query="SELECT * FROM "+
					Global.dbContactcli.TABLENAME+
					" where "+
					" ( "+
					Global.dbContactcli.FIELD_FLAG +
					"='"+ Global.dbContactcli.CONTACT_MODIFICATION+ "' ) or "	+
					" ( "+
					Global.dbContactcli.FIELD_FLAG +
					"='"+ Global.dbContactcli.CONTACT_SUPPRESSION+ "' ) "	;

			Cursor cur=Global.dbParam.getDB().conn.rawQuery(query,null);
			if (cur!=null)
			{
				while(cur.moveToNext())
				{
					String code=cur.getString(cur.getColumnIndex(Global.dbContactcli.FIELD_CODECONTACT));
					String codecli=cur.getString(cur.getColumnIndex(Global.dbContactcli.FIELD_CODECLIENT));
					TableContactcli.structContactcli cli=new TableContactcli.structContactcli();
					Global.dbContactcli.getContact(codecli,code, cli, new StringBuilder());
					String update=
							"UPDATE "+
									Global.dbContactcli.TABLENAME_SRV+
									" set "+
									
									Global.dbContactcli.FIELD_SRV_NOM+"='"+ 			MyDB.controlFld(cli.NOM.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_PRENOM+"='"+	 		MyDB.controlFld(cli.PRENOM.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_MOBILE+"='"+ 			MyDB.controlFld(cli.MOBILE.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_EMAIL+"='"+ 			MyDB.controlFld(cli.EMAIL.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_COMMENTAIRE+"='"+ 	MyDB.controlFld(cli.COMMENTAIRE.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_CODEFONCTION+"='"+ 	MyDB.controlFld(cli.CODEFONCTION.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_CIVIL+"='"+ 			MyDB.controlFld(cli.CIVIL.trim())  +"',"+
									Global.dbContactcli.FIELD_SRV_FLAG+"='"+ 	        MyDB.controlFld(cli.FLAG.trim())  +"' "+
									
																			" where "+
									Global.dbContactcli.FIELD_SRV_CODECLIENT+"='"+cli.CODECLIENT+"' and "+Global.dbContactcli.FIELD_SRV_CODECONTACT+"='"+cli.CODECONTACT.trim()+"' ";

					completeString+=update+context.getString(R.string.separateur_morpion);

				}


				StringBuffer buferr=new StringBuffer();
				String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableNonSec","UPDATE",completeString);
				//si le resultat est ok on efface la cde
				if (resultWS.equals(""))
				{

					Global.dbContactcli.reset(Global.dbContactcli.CONTACT_MODIFICATION);
					Global.dbContactcli.reset(Global.dbContactcli.CONTACT_SUPPRESSION);
				}
				else
				{
					return "err";
					//FurtiveMessageBox(buferr.toString());
				}

			}
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "err";

	}
		
	 
	/**
	 * on envoi les donn�es synchronisable. cad lesdonn�es qui montent vers
	 * le serveur et qui redescendent tout de suite apres pour que la synchro
	 * soit compl�te
	 * 
	 * @return
	 */

	
	
	  public boolean getSynchroDataForWs(int KDType, String filterFlds,
			String filter, String user, String pwd, String scenario ) {
		try {
			// la requete d'insertion du KD
			String queryInsert = dbKD.KD_INSERT_STRING;
			// si on est sur un identifiant de test (qui contient test) on
			// change la tabgle cible
			String ident = user;
			ident = ident.toLowerCase();
			queryInsert = queryInsert.replace(dbKD.TABLENAME,
					dbKD.TABLENAME_SYNC);
			/*
			 * if (ident.contains("test")) {
			 * queryInsert=queryInsert.replace(dbKD.TABLENAME,
			 * dbKD.TABLENAME_TEST); }
			 */


			// on cherche les entetes de commandes
			String queryHdr = "SELECT * FROM " + dbKD.TABLENAME + " where "
					+ " ( ( " + dbKD.fld_kd_dat_type + "=" + KDType + " AND "
					+ filter + "))";

			// dbKD.fld_kd_dat_data15+
			// "='"+1+"' ))";

			String hdr = "";
			Cursor cur = Global.dbParam.getDB().conn.rawQuery(queryHdr, null);
			while (cur.moveToNext()) {

				hdr += context.getString(R.string.separateur_morpion) + "(";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_soc_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_cli_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_pro_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_vis_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_cde_code)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_type)) + "',";
				hdr += "'" + user + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx02)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx03)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx04)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx05)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx06)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx07)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx08)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx09)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_idx10)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data01)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data02)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data03)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data04)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data05)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data06)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data07)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data08)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data09)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data10)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data11)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data12)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data13)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data14)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data15)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data16)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data17)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data18)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data19)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data20)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data21)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data22)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data23)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data24)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data25)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data26)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data27)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data28)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data29)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_dat_data30)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data31)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data32)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data33)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data34)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data35)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data36)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data37)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data38)) + "',";
				hdr += "'"
						+ MyDB.controlFld(Global.dbKDEntCde.giveFld(cur,
								dbKD.fld_kd_val_data39)) + "')";

			}
			cur.close();
			if (!hdr.equals("")) {
				StringBuffer buferr = new StringBuffer();
				String resultWS = MyWS.WSSynchroKD(user, pwd, "synchroKD",
						scenario, queryInsert + hdr, KDType, filterFlds,
						dbKD.TABLENAME);
				// si le resultat est ok on deflag les donn�es � envoyer
				if (resultWS.equals("")) {
					return true;
				} else {
					// FurtiveMessageBox(buferr.toString());
					return false;
				}
			}
			return true;
		} catch (Exception ex) {

		}

		return false;
	}
	  
	  public interface OnTaskFinish{
		  public void onTerminated();
	  }

}