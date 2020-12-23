package com.menadinteractive.segafredo.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.menadinteractive.folliet2016.R;

import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.MyWS;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD;
import com.menadinteractive.segafredo.plugins.Espresso;

public class task_sendInventaireWSData extends AsyncTask<Void, Void, Integer>{
	Context context;

	Handler m_handler;
	String login;
	String password;
	
	OnReceivedResponse listener;
	
	String result = null;


	public task_sendInventaireWSData(Context context,Handler h, OnReceivedResponse _listener){
		super();
		this.context = context;
		
		listener = _listener;

		m_handler=h;
		login = Preferences.getValue(context, Espresso.LOGIN, "0");
		password = Preferences.getValue(context, Espresso.PASSWORD, "0");

		
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
	
		
		return 	getInventaireForWS(login,password);
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (result==1)
			m_handler.sendEmptyMessage(3);
		else
			m_handler.sendEmptyMessage(4);
		
		if(listener != null) {
			listener.onResponse(this.result);
		}
	}




	
	int getInventaireForWS(String user,String pwd)
	{
		try
		{
	//		dbKD543LinInventaire inv=new dbKD543LinInventaire(Global.dbParam.getDB());
	//		if (inv.isInventaireTermine()==false) return 0;
			
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
					 
						dbKD.fld_kd_dat_type+
						"="+
						Global.dbKDLinInv.KD_TYPE+
						" and ("+
						Global.dbKDLinInv.FIELD_LIGNEINV_TYPEPIECE+" is not null and "+
						Global.dbKDLinInv.FIELD_LIGNEINV_TYPEPIECE+" <>'' )";
						
						
						 				

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
					hdr+="'',";
					
					//hdr+="'"+MyDB.controlFld(Global.dbKDLinInv.giveFld(cur, dbKD.fld_kd_dat_data02))+"',";
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

			//	Log.d("TAG", "buffer to send : "+hdr);
			//	Fonctions.writeToFile("=>"+hdr, Fonctions.PATH_APP_LOG);

				result = MyWS.WSSend(user,pwd,"UpdateSrvTableHdrNonSec","UPDATE",queryInsert+hdr);
				
				
				//si le resultat est ok on efface la cde
				if (result.equals(""))
				{
					Global.dbKDLinInv.Reset( )	;
					TableSouches souche=new TableSouches(Global.dbParam.getDB(),context);
					souche.incNum(user, TableSouches.TYPEDOC_INVENTAIRE);
					
				}
				else
				{
				//	Fonctions.FurtiveMessageBox(resultWS);
					//Toast.makeText(this.context, resultWS, Toast.LENGTH_LONG);
							return 0;
					
				}



		}
		catch(Exception ex)
		{
			return 0;
		}

		return 1;
	}
	
	public interface OnReceivedResponse{
		public void onResponse(String response);
	}
	

}
