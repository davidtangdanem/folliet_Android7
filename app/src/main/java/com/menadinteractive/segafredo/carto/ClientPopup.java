package com.menadinteractive.segafredo.carto;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Global;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.folliet2016.app;
import com.menadinteractive.histo.HistoInterActivity;
import com.menadinteractive.segafredo.client.ficheclient;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.db.MyDB;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableHistoInter;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbCliToVisit;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbTournee;
import com.menadinteractive.segafredo.db.dbCliToVisit.structSoc;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.findcli.MenuCliActivity;
import com.menadinteractive.segafredo.materielclient.ListingMaterielClient;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.rapportactivite.rapportactivite;

public class ClientPopup extends BaseActivity  implements OnClickListener{
	/** GUI */
	View popup;
	TextView tv_raisonsociale, tvEvenement;
	Button b_close;
	TextView tv_address1;
	TextView tv_address2;
	TextView tv_cp;
	TextView tv_ville;
	TextView tv_codeclient;
	TextView tv_pays;
	Button b_call;
	Button b_mail;
	Button b_direction;
	Button b_select;
    Button b_materiel;
    Button b_sav;
    
	/** */
	Context context;
	structClient structClient;
	Handler handler;
	boolean m_btech =false;
	String m_streplogin="";
	String m_stnumfab="";
	
	String m_stnuminterne="";
	String m_typeinter="";
	boolean m_bvisite=false;
	String m_code_evt="";
	String m_evt_id="";
	
	MyDB DB;
	

	public ClientPopup(Context context, Handler handler, structSoc soc, structClient structClient, boolean m_btech,String m_streplogin,MyDB db){
		this.context = context;
		this.handler = handler;
		this.m_btech = m_btech;
		this.m_streplogin=m_streplogin;
		this.DB = db;
		m_bvisite=false;
		m_code_evt="";
		m_evt_id="";
		initGUI();
		popup.setVisibility(View.GONE);

		if(structClient != null){		
			updatePopup(structClient, null, false);
			if(structClient.CLI_TO_VISIT_STRUCT != null )
			{
				m_bvisite=true;
			
			}
				
			if(this.m_btech==true)
	    	{
				if(m_bvisite==true)
				{
					m_stnuminterne=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID);
					TableHistoInter hi=new TableHistoInter(this.DB);
		    		m_stnumfab=hi.LoadInfo_numfab(structClient.CODE,m_stnuminterne);
		    		m_typeinter=hi.LoadType_inter(structClient.CODE,m_stnuminterne);
		    		m_code_evt=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_CODE_EVT).trim();
					m_evt_id=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID).trim();
				
				}
				else
				{
					dbTournee db1=new dbTournee(this.DB);
		    		m_stnuminterne=db1.CodeNum_interne(structClient.CODE);
		    		TableHistoInter hi=new TableHistoInter(this.DB);
		    		m_stnumfab=hi.LoadInfo_numfab(structClient.CODE,m_stnuminterne);
		    		m_typeinter=hi.LoadType_inter(structClient.CODE,m_stnuminterne);
				}
	    		
	    		
	    	}
		}
		initListeners();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_appState = ((app)getApplicationContext());
	}

	private void initGUI(){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popup = inflater.inflate(R.layout.carto_popup, null);
		tv_raisonsociale = (TextView) popup.findViewById(R.id.tv_raisonsociale);
		b_close = (Button) popup.findViewById(R.id.b_close);
		tv_address1 = (TextView) popup.findViewById(R.id.tv_address1);
		tv_address2 = (TextView) popup.findViewById(R.id.tv_address2);
		tv_cp = (TextView) popup.findViewById(R.id.tv_cp);
		tv_ville = (TextView) popup.findViewById(R.id.tv_ville);
		tv_codeclient = (TextView) popup.findViewById(R.id.tv_codeclient);
		tv_pays = (TextView) popup.findViewById(R.id.tv_pays);
		b_call = (Button) popup.findViewById(R.id.b_call);
		b_mail = (Button) popup.findViewById(R.id.b_mail);
		b_direction = (Button) popup.findViewById(R.id.b_direction);
		b_select = (Button) popup.findViewById(R.id.b_select);
		tvEvenement = (TextView) popup.findViewById(R.id.tvEvenement);
		b_materiel = (Button) popup.findViewById(R.id.b_materiel);
		b_sav = (Button) popup.findViewById(R.id.b_sav);
		if(m_btech==true)
		{
			b_materiel.setVisibility(View.VISIBLE);
			b_sav.setVisibility(View.VISIBLE);
			
			
			
		}
		else
		{
			b_materiel.setVisibility(View.GONE);
			b_sav.setVisibility(View.GONE);
		}
	}

	public void updatePopup(structClient structClient1, structSoc soc, boolean display){
		if(structClient1 != null){
			this.structClient = structClient1;
			tv_raisonsociale.setText(structClient.ENSEIGNE);
			tv_codeclient.setText(structClient.CODE);
			tv_address1.setText(structClient.ADR1);
			tv_address2.setText(structClient.ADR2);
			tv_cp.setText(structClient.CP);
			tv_ville.setText(structClient.VILLE);
			tv_pays.setText("FRANCE");
			
			if(soc != null && soc.FIELD_LBL != null && !soc.FIELD_LBL.equals("")){
				tvEvenement.setVisibility(View.VISIBLE);
				//tvEvenement.setText(soc.FIELD_LBL.trim() );
				if(structClient.CLI_TO_VISIT_STRUCT !=null)
				{
					tvEvenement.setText(Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_LBL).trim() );
						
				}
				else
				{
					tvEvenement.setVisibility(View.GONE);
					
					//tvEvenement.setText(Fonctions.GetStringDanem(soc.FIELD_LBL).trim() );
						
				
				}
				
				
			}else{
				if(structClient.CLI_TO_VISIT_STRUCT !=null)
				{
					tvEvenement.setText(Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_LBL).trim() );
						
				}
				else
				{
					tvEvenement.setVisibility(View.GONE);
				
				}
				
			}

			if(display)
				setVisibility(View.VISIBLE);
		}
	}

	private void initListeners(){
		b_close.setOnClickListener(this);
		b_call.setOnClickListener(this);
		b_mail.setOnClickListener(this);
		b_direction.setOnClickListener(this);
		b_select.setOnClickListener(this);
		b_materiel.setOnClickListener(this);
		b_sav.setOnClickListener(this);

	}

	public void setVisibility(int visibility){
		if(visibility == View.VISIBLE || visibility == View.GONE || visibility == View.INVISIBLE)
			popup.setVisibility(visibility);
	}

	public View getView(){
		return popup;
	}
	@Override
	public void onClick(View v) {
		if(v == b_close){
			setVisibility(View.GONE);
		}
		else if(v == b_call){
			m_code_evt="";
			m_evt_id="";
			m_stnuminterne="";
			m_stnumfab="";
			m_typeinter="";
			m_bvisite=false;
			if(structClient != null){		
				updatePopup(structClient, null, false);

				if(structClient.CLI_TO_VISIT_STRUCT != null )
				{
					m_bvisite=true;
				
				}
					
				if(this.m_btech==true)
		    	{
					if(m_bvisite==true)
					{
						m_stnuminterne=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID);
						TableHistoInter hi=new TableHistoInter(this.DB);
			    		m_stnumfab=hi.LoadInfo_numfab(structClient.CODE,m_stnuminterne);
			    		m_typeinter=hi.LoadType_inter(structClient.CODE,m_stnuminterne);
			    		m_code_evt=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_CODE_EVT).trim();
						m_evt_id=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID).trim();
					
					}
					else
					{
						dbTournee db1=new dbTournee(this.DB);
			    		m_stnuminterne=db1.CodeNum_interne(structClient.CODE);
			    		TableHistoInter hi=new TableHistoInter(this.DB);
			    		m_stnumfab=hi.LoadInfo_numfab(structClient.CODE,m_stnuminterne);
			    		m_typeinter=hi.LoadType_inter(structClient.CODE,m_stnuminterne);
					}
		    		
		    		
		    	}
				else
				{
					if(m_bvisite==true)
					{
						m_stnuminterne=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID);
						TableHistoInter hi=new TableHistoInter(this.DB);
			    		m_stnumfab=hi.LoadInfo_numfab(structClient.CODE,m_stnuminterne);
			    		m_typeinter=hi.LoadType_inter(structClient.CODE,m_stnuminterne);
			    		m_code_evt=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_CODE_EVT).trim();
						m_evt_id=Fonctions.GetStringDanem(structClient.CLI_TO_VISIT_STRUCT.FIELD_EVT_ID).trim();
					
					}
					else
					{
						dbTournee db1=new dbTournee(this.DB);
			    		m_stnuminterne=db1.CodeNum_interne(structClient.CODE);
			    		TableHistoInter hi=new TableHistoInter(this.DB);
			    		m_stnumfab=hi.LoadInfo_numfab(structClient.CODE,m_stnuminterne);
			    		m_typeinter=hi.LoadType_inter(structClient.CODE,m_stnuminterne);
					}
				}
			}
			launchrapport(structClient.CODE);
			//Intent messIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Fonctions.GetStringDanem(structClient.TEL1)));
			//context.startActivity(messIntent);
		}
		else if(v == b_mail){
			//			Fonctions.sendEmailMsg(context,Fonctions.GetStringDanem(structClient.ADR_EMAIL),"","");
			if(handler != null){
				Message msg = new Message();
				msg.what = CartoMapActivity.MESSAGE_SHOW_MENU_POPUP;
				Bundle b = new Bundle();
				b.putString(TableClient.FIELD_CODE, structClient.CODE);
			
				msg.setData(b);
				msg.obj = structClient;
				handler.sendMessage(msg);
			}
		}
		else if(v == b_direction){
			String street = Fonctions.GetStringDanem(structClient.ADR1) + 
					" " + Fonctions.GetStringDanem(structClient.CP) + 
					" " + Fonctions.GetStringDanem(structClient.VILLE);

			if(Fonctions.GetStringDanem(structClient.ADR1) == "") street = Fonctions.GetStringDanem(structClient.ADR2) + 
					" " + Fonctions.GetStringDanem(structClient.CP) + 
					" " + Fonctions.GetStringDanem(structClient.VILLE);
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("google.navigation:q=" + street));
			context.startActivity(intent);
		}
		else if(v == b_select){
			if(handler != null){
			/*	Message msg = new Message();
				msg.what = CartoMapActivity.MESSAGE_SELECT_CLIENT_BACK_NEGOS;
				Bundle b = new Bundle();
				b.putString(Espresso.CodeClient, structClient.CODE);
				msg.setData(b);
				handler.sendMessage(msg);
				*/
				launchFichecli(structClient.CODE);
				//launchMenuCli(structClient.CODE);
			}




		}
		else if(v == b_materiel){
			if(handler != null){
				if(!Fonctions.GetStringDanem(structClient.CODE).equals("ST"+this.m_streplogin)){
					launchFicheMaterielClient(structClient.CODE);
				}

				
				
			}




		}
		else if(v == b_sav){
			if(handler != null){
				if(!Fonctions.GetStringDanem(structClient.CODE).equals("ST"+this.m_streplogin)){
					
					launchFicheHistoInter(structClient.CODE);
				}
			
			}




		}
		
		

	}
	public void launchMenuCli(String codecli )
	{
		Intent intent = new Intent(context,		MenuCliActivity.class);
		Bundle b=new Bundle();
		b.putString("codecli",codecli);

		intent.putExtras(b);
		context.startActivity(intent);
	}
	void launchFichecli(String codecli)
	{
		Intent intent = new Intent(context,		ficheclient.class);
		Bundle b=new Bundle();
		b.putString("numProspect",codecli);

		intent.putExtras(b);
		context.startActivity(intent);
	}
	void launchFicheMaterielClient(String codecli)
	{
		Intent i = new Intent(context, ListingMaterielClient.class);
		Bundle b = new Bundle();
		b.putString("codeclient",  codecli);
		b.putString("numfab",  m_stnumfab);
		i.putExtras(b);
		context.startActivity(i);

	}
	void launchFicheHistoInter(String codecli)
	{
		Intent i = new Intent(context, HistoInterActivity.class);


		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);
		b.putString("typ_inter",  m_typeinter);

		i.putExtras(b);
		context.startActivity(i);
	}
	
	void launchrapport(String codecli)
	{

		Intent i = new Intent(context, rapportactivite.class);
		
		Bundle b = new Bundle();

		b.putString("codeclient",  codecli);
		b.putString("code_evt",  m_code_evt);
		b.putString("evt_id",  m_evt_id);
		b.putString("numInterne",  m_stnuminterne);
		
		i.putExtras(b);
		context.startActivity(i);
		
	}
	

}
