package com.menadinteractive.segafredo.tasks;

import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.MyWS;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.dbLog;
import com.menadinteractive.segafredo.plugins.Espresso;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

public class task_sendLogs extends AsyncTask<Void, Void, Integer>{

	Context context;
	String login, password;
	
	public task_sendLogs(Context _context) {
		super();
		this.context = _context;
		
		login = Preferences.getValue(context, Espresso.LOGIN, "0");
		password = Preferences.getValue(context, Espresso.PASSWORD, "0");
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		getKemsLogWs(login, password);
		return null;
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
					hdr+="'"+MyDB.controlFld(Global.dbLog.giveFld(cur, Global.dbLog.FLD_LOG_USERID		))+"')";
				}
				if (bFind)
				{
					StringBuffer buferr=new StringBuffer();
					String resultWS=MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
					//si le resultat est ok on efface la cde
					if (resultWS.equals(""))
		  			{
						Global.dbLog.delete();
						Global.dbLog.Clear(new StringBuilder());
					}
					else if(resultWS.contains("err"))
					{
						Global.dbLog.delete();
						Global.dbLog.Clear(new StringBuilder());
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

}
