package com.menadinteractive.segafredo.findcli;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.client.ficheclient;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.GPS;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableClient;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.TableSouches.passeplat;
import com.menadinteractive.segafredo.db.dbKD83EntCde.structEntCde;
import com.menadinteractive.segafredo.db.dbKD729HistoDocuments;
import com.menadinteractive.segafredo.db.dbKD730FacturesDues;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.encaissement.ReglementActivity;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.rapportactivite.rapporttodo;
import com.menadinteractive.segafredo.tasks.task_getFileFromURL;
import com.menadinteractive.segafredo.tasks.task_sendWSData;
import com.menadinteractive.segafredo.tasks.task_getFileFromURL.OnDowload;





public class MenuCliActivity extends BaseActivity implements OnClickListener{
	TextView tvCode,tvEnseigne;
	TextView tvVille;
	TextView tvCP;
	TextView tvMail;
	TextView tvTel;
	TextView tvRS;
	TextView tvAdr1;
	TextView textTitreAdresse;
	TextView textTitreTarif;
	TextView textViewNote;
	TextView tvTarif;
	TextView tvAdr2,tvCliBloque,tvEncoursClient, tvEncoursClientTitle;
	TextView tvCommercial;
	structClient cli=null;
	String m_codecli;
	Handler handler;
	myListView lv;
	ArrayList<Bundle> menuitems;
	String m_fromCarto="";
	String m_facturableDirectBL="0";
	boolean m_bfacturableBLPossible=false;
	String m_numrapport="";
	String m_mod_liv ="";
	
	private ProgressDialog m_ProgressDialog = null;

	structlistLogin rep = null;
	boolean isClientST = false;

	Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        m_codecli=getBundleValue(bundle, "codecli");
        m_fromCarto=getBundleValue(bundle, "fromcarto");
        m_facturableDirectBL=getBundleValue(bundle, "facturableDirectBL");
        m_numrapport=getBundleValue(bundle, "numrapport");

        Global.dbLog.Insert("MenuCliActivity", "OnCreate", "", "Code client:"+m_codecli, "", "", "");

        mContext = this;

		//get rep
		rep = getRep();
		if(rep != null && rep.LOGIN != null && m_codecli.equals("ST"+rep.LOGIN)){
			isClientST = true;
		}

		setContentView(R.layout.activity_menuclient);
		cli=new structClient();
		Global.dbClient.getClient(m_codecli, cli, new StringBuilder());
        m_mod_liv=Fonctions.GetStringDanem(cli.MOD_LIV);
		initGUI();
		setListeners();
		
		//Si Commerciale -> si activité vide allez dans la fiche client 
		if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))
		{

		/*	if(Fonctions.GetStringDanem(cli.TYPEETABLISSEMENT).trim().equals(""))
			{
				if(!m_codecli.equals("ST"+rep.LOGIN)){
					//promptText("Menu client", "Renseignez le type d\'établissement", false);
					MessageYes("Renseignez le type d\'établissement", 1,"Menu client");
					
					//commandeActivity.launchFiche(MenuCliActivity.this,m_codecli,LAUNCH_FICHECLI);
				}
			} */

			if(Fonctions.GetStringDanem(cli.EMAIL).trim().equals("") )
			{
				if(Fonctions.GetStringDanem(cli.CLI_PASMAIL).trim().equals("1") )
				{

				}
				else
				{
					if(!m_codecli.equals("ST"+rep.LOGIN)){

						//promptText("Menu client", "Renseignez le type d\'établissement", false);
						MessageYes("Renseignez un Email", 1,"Menu client");

						//commandeActivity.launchFiche(MenuCliActivity.this,m_codecli,LAUNCH_FICHECLI);
					}
				}

			} else {
				if (Fonctions.GetStringDanem(cli.EMAIL).indexOf('@')==-1)
				{
					MessageYes("Renseignez un Email", 1,"Menu client");
				}
			}
		}
		if(m_facturableDirectBL.equals("1") && m_bfacturableBLPossible==true )
		{
			finish();
			launchCdeFacturable(MenuCliActivity.this,TableSouches.TYPEDOC_BC, m_codecli,cli.SOC_CODE,m_numrapport,true);
		}

	}
	public void MessageYes(String message, int type,String title) {
		final int m_type = type;

		ImageView image = new ImageView(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		/*if(type == R.string.message_yesno_valider_facture || type == R.string.message_yesno_imprimer_facture){
			image.setImageResource(R.drawable.dot_red);
			builder.setView(image);
		}*/
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton(getString(R.string.Ok),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				switch (m_type) {
				case 1://fiche client
					
					commandeActivity.launchFiche(MenuCliActivity.this,m_codecli,LAUNCH_FICHECLI);
					break;
					
				}
			}
		
		});
		AlertDialog alert = builder.create();
		alert.show();
	}


	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
		tvMail = (TextView) findViewById(R.id.textViewMail);
		tvRS = (TextView) findViewById(R.id.textViewRS);
		tvCP = (TextView) findViewById(R.id.textViewCP);
		tvTel = (TextView) findViewById(R.id.textViewTel);
		tvVille = (TextView) findViewById(R.id.textViewVille);
		textTitreAdresse= (TextView) findViewById(R.id.textTitreAdresse);
		textTitreTarif= (TextView) findViewById(R.id.textTitreTarif);
		tvTarif = (TextView) findViewById(R.id.textViewTarif);
		tvAdr1 = (TextView) findViewById(R.id.textViewAdr1);
		tvAdr2 = (TextView) findViewById(R.id.textViewAdr2);
		tvCommercial = (TextView) findViewById(R.id.textViewCommercial);
		
		tvCode = (TextView) findViewById(R.id.textViewCode);
		tvCliBloque = (TextView) findViewById(R.id.tvCliBloque);
		tvEnseigne = (TextView) findViewById(R.id.textViewEnseigne);
		tvEncoursClient = (TextView) findViewById(R.id.tvEncoursClient);
		tvEncoursClientTitle = (TextView) findViewById(R.id.tvEncoursClientTitle);
		textViewNote= (TextView) findViewById(R.id.textViewNote);

		cli=new structClient();
		Global.dbClient.getClient(m_codecli, cli, new StringBuilder());

		if (TableClient.isActif(cli)==false)
			tvCliBloque.setVisibility(View.VISIBLE);
		else
			tvCliBloque.setVisibility(View.GONE);
		//si le client n'est pas géocodé on le géocode en arriere plan
		if (cli.LAT==null || cli.LAT.equals(""))
		{
			String adress =cli.ADR1+" "+cli.ADR2+" "+cli.CP+" "+cli.VILLE+" "+cli.PAYS;
			geocode(adress);
		}

		tvRS.setText(cli.NOM.trim());
		tvMail.setText(cli.EMAIL);
		tvCP.setText(cli.CP);
		tvTel.setText(cli.TEL1);
		tvVille.setText(cli.VILLE);
		tvCode.setText(cli.CODE);
		tvEnseigne.setText(cli.ENSEIGNE.trim());
		textTitreAdresse.setText("Adresse");
		tvAdr1.setText(cli.ADR1);
		tvAdr2.setText(cli.ADR2);
		tvCommercial.setText(cli.CODECOM);
		tvTarif.setText(Fonctions.GetStringDanem(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_LISTETARIF,cli.CODETRF).trim()));
		textViewNote.setText("Note : " + Fonctions.GetStringDanem(cli.CLI_SAV));
		/*
		if(Fonctions.GetStringDanem(cli.CODE).contains(TableClient.CLI_PARTICULIER_8888))
		{
			textTitreAdresse.setText("Commentaire");
			tvAdr1.setText(cli.FREETEXT);
			tvAdr2.setText("");
		}
		else
		{



		}
		 */

		lv = (myListView) findViewById(R.id.listView1);

		//on affiche l'encours client
		structClient client = new structClient();
		Global.dbClient.getClient(m_codecli, client, new StringBuilder());

		float totalEncours = Fonctions.GetStringToFloatDanem(client.MONTANTTOTALENCOURS);
		tvEncoursClient.setText(Fonctions.GetFloatToStringFormatDanem(totalEncours, "0.00"));

		if(isClientST){
			tvEncoursClient.setVisibility(View.GONE);
			tvEncoursClientTitle.setVisibility(View.GONE);
		}

		handler=getHandler();
		PopulateList();
	}

	void setListeners()
	{

		/*tvCode.setOnClickListener(this);
		
		tvCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                		String  urlcli=(Global.dbParam.getComment(Global.dbParam.PARAM_URLCLI,"1"));
						if(!Fonctions.GetStringDanem(urlcli).equals(""))
						{
							String url = urlcli+m_codecli;
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(url));
							startActivityForResult(i, 1450);
						}
                } catch (Exception except) {
                    
                }
            }
        });*/

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	Bundle createItem(String lbl,String icon,String lbl2)
	{
		Bundle item=new Bundle();
		item.putString("lbl", lbl);
		item.putString("icon",icon);
		item.putString("lbl2",lbl2);
		return item;
	}
	private void PopulateList() {

		Log.e("cliTOurnee","cli"+cli);

		menuitems=new ArrayList<Bundle>();

		TableSouches souche=new TableSouches(m_appState.m_db,this);

		passeplat pp=new passeplat();

		if(Fonctions.GetStringDanem(cli.TYPE).equals(FindCliActivity.TYPE_PROSPECT))
		{
			if (souche.get(souche.TYPEDOC_PROFORMA,getLogin(),pp) &&  (Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) || Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur) )    )
			{
				menuitems.add(createItem(getString(R.string.pro_forma),"proforma",pp.NUMSOUCHE_COURANT));
			}

            if (souche.get(souche.TYPEDOC_RELEVELINEAIRE,getLogin(),pp) &&  (Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) || Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur) )    )
            {
            	menuitems.add(createItem(getString(R.string.relevelineaire),"proforma",pp.NUMSOUCHE_COURANT));
            }

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.ficheclient), "basic2_105_user",""));
			}

			if (m_fromCarto.equals("1")==false && !m_codecli.equals("ST"+rep.LOGIN))
				menuitems.add(createItem(getString(R.string.Carto), "basic_2057_map",""));

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.contact), "contact",""));
			}

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.rapportactivite), "rapport_de_visites",""));
			}
			if(!m_codecli.equals("ST"+rep.LOGIN)){
				if( Global.dbCliToVisit.Todo_countClient(m_codecli)==0)
				{
				}
				else
				 menuitems.add(createItem(getString(R.string.rapportodo), "rapport_de_visites",""));
			}
			dbKD729HistoDocuments hist=new dbKD729HistoDocuments(m_appState.m_db);
			if (hist.Count(m_codecli)>0)
				menuitems.add(createItem(getString(R.string.commande_Historique), "basic1_104_database_download",""));

            if(!m_codecli.equals("ST"+rep.LOGIN)){
                menuitems.add(createItem(getString(R.string.listemateriel), "basic2_295_dashboard_materiel",""));
            }

		}
		else //CLIENT
		{

			if(TableClient.isModeleBL(cli,TableClient.CLI_MODELEBL_BNC)==true)
			{
				if(!Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien))
				{
					if(cli.NO_FAC.equals("N"))
					{
						if (souche.get(souche.TYPEDOC_BL,getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST"+rep.LOGIN))
						{
							if(!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
								menuitems.add(createItem(getString(R.string.BL), "bl", pp.NUMSOUCHE_COURANT));
							}
						}
					}
				}
			}
			else
			{
				if(TableClient.isModeleBL(cli,TableClient.CLI_MODELEBL_BLC)==true)
				{
					if(cli.NO_FAC.equals("N"))
					{
						if(!Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien))
						{
							if (souche.get(souche.TYPEDOC_BL,getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST"+rep.LOGIN))
							{
								if(!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
									menuitems.add(createItem(getString(R.string.bl_chiffr_), "avoir", pp.NUMSOUCHE_COURANT));

									if (m_facturableDirectBL.equals("1"))
										m_bfacturableBLPossible = true;
								}
							}
						}
						else
						{
							if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) & m_facturableDirectBL.equals("1"))
							{
								if (souche.get(souche.TYPEDOC_BL,getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST"+rep.LOGIN))
								{
									if(!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
										menuitems.add(createItem(getString(R.string.bl_chiffr_), "avoir", pp.NUMSOUCHE_COURANT));

										if (m_facturableDirectBL.equals("1"))
											m_bfacturableBLPossible = true;
									}
								}
							}
						}
					}

				}
				else {

					//if (cli.NO_FAC.equals("N"))
					{
						if (isClientST)
						{
							 if (souche.get(souche.TYPEDOC_COMMANDE, getLogin(), pp) && TableClient.isActif(cli)) {
                                menuitems.add(createItem(getString(R.string.menu_commande), "basic2_170_box_gift", pp.NUMSOUCHE_COURANT));
                            }
						}
						else
						{
							if (cli.NO_FAC.equals("N"))
							{
								if (souche.get(souche.TYPEDOC_FACTURE, getLogin(), pp) && TableClient.isActif(cli)) {
									if (!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
										menuitems.add(createItem(getString(R.string.Facture), "facture", pp.NUMSOUCHE_COURANT));
									}
								}
							}



						}
						//test
						if (!m_mod_liv.equals("2")) {
							if (!Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien)) {
								if (souche.get(souche.TYPEDOC_BL, getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST" + rep.LOGIN)) {
									if (cli.NO_FAC.equals("N"))
									{
										if (!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
											menuitems.add(createItem(getString(R.string.BL), "bl", pp.NUMSOUCHE_COURANT));
										}

									}

								}
							}

							if (!Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) && souche.get(souche.TYPEDOC_BL, getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST" + rep.LOGIN)) {
								if (cli.NO_FAC.equals("N"))
								{
									if (!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
										menuitems.add(createItem(getString(R.string.bl_chiffr_), "avoir", pp.NUMSOUCHE_COURANT));
										if (m_facturableDirectBL.equals("1"))
											m_bfacturableBLPossible = true;
									}
								}

							} else {
								if (Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) & m_facturableDirectBL.equals("1")) {
									if (souche.get(souche.TYPEDOC_BL, getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST" + rep.LOGIN)) {
										if (cli.NO_FAC.equals("N"))
										{
											if (!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
												menuitems.add(createItem(getString(R.string.bl_chiffr_), "avoir", pp.NUMSOUCHE_COURANT));
												if (m_facturableDirectBL.equals("1"))
													m_bfacturableBLPossible = true;
											}
										}

									}
								}
							}

						}

					}
				}
			}

			if(cli.NO_FAC.equals("N"))
			{
				if (souche.get(souche.TYPEDOC_BCOMMANDE,getLogin(), pp) && TableClient.isActif(cli) && !m_codecli.equals("ST"+rep.LOGIN))
				{
					if(!Fonctions.GetStringDanem(cli.TOURNEE).equals("99")) {
						menuitems.add(createItem(getString(R.string.bc_commande_), "basic2_170_box_gift", pp.NUMSOUCHE_COURANT));
					}
				}
			}

			if(cli.NO_FAC.equals("N")) {
				if (souche.get(souche.TYPEDOC_PROFORMA, getLogin(), pp) && (Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) || Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))) {
					menuitems.add(createItem(getString(R.string.pro_forma), "proforma", pp.NUMSOUCHE_COURANT));
				}

				if (souche.get(souche.TYPEDOC_RELEVELINEAIRE, getLogin(), pp) && (Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable) || Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))) {
					menuitems.add(createItem(getString(R.string.relevelineaire), "proforma", pp.NUMSOUCHE_COURANT));
				}

			}


			//dbKD730FacturesDues dues=new dbKD730FacturesDues(m_appState.m_db);
			//if (dues.Count(m_codecli)>0)
			if(cli.NO_FAC.equals("N")) {
				if ((souche.get(souche.TYPEDOC_REGLEMENT, getLogin(), pp) && !m_codecli.equals("ST" + rep.LOGIN)) || Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable)) {
					menuitems.add(createItem(getString(R.string.commande_reglement), "basic2_018_money_coins", pp.NUMSOUCHE_COURANT));
				}
			}

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.ficheclient), "basic2_105_user",""));
			}

			dbKD729HistoDocuments hist=new dbKD729HistoDocuments(m_appState.m_db);
			if (hist.Count(m_codecli)>0)
				menuitems.add(createItem(getString(R.string.commande_Historique), "basic1_104_database_download",""));

			if(!Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien))
			{
				if(!m_codecli.equals("ST"+rep.LOGIN)){
					menuitems.add(createItem(getString(R.string.stats), "basic2_235_graph_down_chart",""));
				}	
			}

			if (m_fromCarto.equals("1")==false && !m_codecli.equals("ST"+rep.LOGIN))
				menuitems.add(createItem(getString(R.string.Carto), "basic_2057_map",""));

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.contact), "contact",""));
			}

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.rapportactivite), "rapport_de_visites",""));
			}

			//if (souche.get(souche.TYPEDOC_RETOURMACHINE,getLogin(),pp))
			if(!m_codecli.equals("ST"+rep.LOGIN)){
				menuitems.add(createItem(getString(R.string.listemateriel), "basic2_295_dashboard_materiel",""));
			}

			if(!m_codecli.equals("ST"+rep.LOGIN)){
				if (souche.get(souche.TYPEDOC_INTERVENTION,getLogin(), pp) && TableClient.isActif(cli)){
					menuitems.add(createItem(getString(R.string.sav), "basic2_292_tools_settings",pp.NUMSOUCHE_COURANT));
				}
				else
					menuitems.add(createItem(getString(R.string.sav), "basic2_292_tools_settings",""));
			}

		}

		ArrayList<assoc> assocs =new ArrayList<assoc>();

		assocs.add(new  assoc(0,R.id.toptext,"lbl"));
		assocs.add(new  assoc(1,R.id.icon,"icon"));
		assocs.add(new  assoc(0,R.id.tvnum,"lbl2",true));

		lv.attachValues(R.layout.item_list_menuclient, menuitems,assocs,handler);
	}

	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
				case R.id.listView1:
				case R.id.icon:

					int  id=bGet.getInt("position");
					String menu=menuitems.get(id).getString("lbl");

					if (menu.equals(getString(R.string.Carto)))
					{
						go_carto(m_codecli);
					}
					if (menu.equals(getString(R.string.ficheclient)))
					{
						commandeActivity.launchFiche(MenuCliActivity.this,m_codecli,LAUNCH_FICHECLI);
					}
					if (menu.equals(getString(R.string.stats)))
					{

						commandeActivity.launchStatclient(MenuCliActivity.this,m_codecli,LAUNCH_STATCLIENT);
						/*String  urlcli=(Global.dbParam.getComment(Global.dbParam.PARAM_URLCLI,"1"));
						if(!Fonctions.GetStringDanem(urlcli).equals(""))
						{
							String url = urlcli+m_codecli;
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(url));
							startActivityForResult(i, 1450);
						}*/

					}
					if (menu.equals(getString(R.string.commande_Historique)))
					{		
						launchHisto(m_codecli, cli.SOC_CODE);
					}
					if(isClientST){
						if (menu.equals(getString(R.string.menu_commande))){
							//if (cli.STATUT_PRO.equals("2")){
							//	launchDialogComptant(TableSouches.TYPEDOC_COMMANDE);
							//} else {
								launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_COMMANDE, m_codecli,cli.SOC_CODE);
							//}

						}
					}else{
						if (menu.equals(getString(R.string.Facture))){	
							if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))
							{
								if( Global.dbCliToVisit.Todo_countClient(m_codecli)==0)
								{
									if (cli.STATUT_PRO.equals("2")){
										launchDialogComptant(TableSouches.TYPEDOC_FACTURE);
									} else {
										launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_FACTURE, m_codecli,cli.SOC_CODE);
									}
									//launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_FACTURE, m_codecli,cli.SOC_CODE);
								}
								else
								{
									if (cli.STATUT_PRO.equals("2")){
										launchDialogComptant2(TableSouches.TYPEDOC_FACTURE, m_codecli);
									} else {
										launchRapportTodo(m_codecli,TableSouches.TYPEDOC_FACTURE);
									}


								}

							}
							else {
								if (cli.STATUT_PRO.equals("2")){
									launchDialogComptant(TableSouches.TYPEDOC_FACTURE);
								} else {
									launchCde(MenuCliActivity.this, TableSouches.TYPEDOC_FACTURE, m_codecli, cli.SOC_CODE);
								}
								//launchCde(MenuCliActivity.this, TableSouches.TYPEDOC_FACTURE, m_codecli, cli.SOC_CODE);
							}

						}
					}

					if (menu.equals(getString(R.string.BL)))
					{	
						if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))
						{
							if( Global.dbCliToVisit.Todo_countClient(m_codecli)==0)
							{
								if (cli.STATUT_PRO.equals("2")){
									launchDialogComptant(TableSouches.TYPEDOC_BL);
								} else {
									launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BL, m_codecli,cli.SOC_CODE);
								}
								//launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BL, m_codecli,cli.SOC_CODE);
							}
							else
							{
								if (cli.STATUT_PRO.equals("2")){
									launchDialogComptant2(TableSouches.TYPEDOC_BL, m_codecli);
								} else {
									launchRapportTodo(m_codecli,TableSouches.TYPEDOC_BL);
								}

							}

						}
						else {
							if (cli.STATUT_PRO.equals("2")){
								launchDialogComptant(TableSouches.TYPEDOC_BL);
							} else {
								launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BL, m_codecli,cli.SOC_CODE);
							}//launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BL, m_codecli,cli.SOC_CODE);
						}
					
					}
					if (menu.equals(getString(R.string.bl_chiffr_)))
					{	
						if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))
						{
							if(Global.dbCliToVisit.Todo_countClient(m_codecli)==0)
							{
								if (cli.STATUT_PRO.equals("2")){
									launchDialogComptant(TableSouches.TYPEDOC_BC);
								} else {
									launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BC, m_codecli,cli.SOC_CODE);
								}
								//launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BC, m_codecli,cli.SOC_CODE);
							}
							else
							{
								if (cli.STATUT_PRO.equals("2")){
									launchDialogComptant2(TableSouches.TYPEDOC_BC,m_codecli);
								} else {
									launchRapportTodo(m_codecli,TableSouches.TYPEDOC_BC);
								}
							}

						}
						else {
							if (cli.STATUT_PRO.equals("2")){
                                launchDialogComptant(TableSouches.TYPEDOC_BC);
                            }
							//launchCde(MenuCliActivity.this, TableSouches.TYPEDOC_BC, m_codecli, cli.SOC_CODE);
						}
					}
					if (menu.equals(getString(R.string.bc_commande_))){
						/*if (cli.STATUT_PRO.equals("2")){
							launchDialogComptant(TableSouches.TYPEDOC_BCOMMANDE);
						} else {
						}*/
						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BCOMMANDE, m_codecli,cli.SOC_CODE);

					}
					
					if (menu.equals(getString(R.string.Avoir)))
					{
						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_AVOIR, m_codecli,cli.SOC_CODE);
						/*
						if (cli.STATUT_PRO.equals("2")){
							launchDialogComptant(TableSouches.TYPEDOC_AVOIR);
						} else {

						}*/

						//launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_AVOIR, m_codecli,cli.SOC_CODE);
					}
					if (menu.equals(getString(R.string.Retour)))
					{
						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_RETOUR, m_codecli,cli.SOC_CODE);
						/*if (cli.STATUT_PRO.equals("2")){
							launchDialogComptant(TableSouches.TYPEDOC_RETOUR);
						} else {

						}*/

					}
					if (menu.equals(getString(R.string.commande_reglement)))
					{					 
						launchReglement(m_codecli, cli.SOC_CODE);
					}

					if (menu.equals(getString(R.string.pro_forma))) {

						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_PROFORMA, m_codecli,cli.SOC_CODE);
						/*if (cli.STATUT_PRO.equals("2")){
							launchDialogComptant(TableSouches.TYPEDOC_PROFORMA);
						} else {

						}*/

						//launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_PROFORMA, m_codecli,cli.SOC_CODE);
					}

                    if (menu.equals(getString(R.string.relevelineaire))) {

						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_RELEVELINEAIRE, m_codecli,cli.SOC_CODE);
                       /* if (cli.STATUT_PRO.equals("2")){
                            launchDialogComptant(TableSouches.TYPEDOC_RELEVELINEAIRE);
                        } else {

                        }*/

                        //launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_RELEVELINEAIRE, m_codecli,cli.SOC_CODE);
                    }

					if (menu.equals(getString(R.string.contact)))
					{					 
						commandeActivity.launchContact(MenuCliActivity.this,m_codecli,LAUNCH_CONTACT,false);
					}

					if (menu.equals(getString(R.string.rapportactivite)))
					{					 
						commandeActivity.launchRapport(MenuCliActivity.this,m_codecli,LAUNCH_RAPPORT);
					}
					if (menu.equals(getString(R.string.listemateriel)))
					{					 
						launchMaterielClient(m_codecli,LAUNCH_MATERIELCLI);
					}
					if (menu.equals(getString(R.string.sav)))
					{					 
						launchHistoInter(m_codecli,"",LAUNCH_HISTOINTER);
					}
					if (menu.equals(getString(R.string.rapportodo)))
					{
						//commandeActivity.launchRapport(MenuCliActivity.this,m_codecli,LAUNCH_RAPPORT);
						launchRapportTodo(m_codecli,TableSouches.TYPEDOC_PROFORMA);
					}

					break;
				}

			}
		};
		return h;
	}



	void launchReglement(String codeCli, String codeSoc){
		Intent intent = new Intent(this, ReglementActivity.class);
		Bundle b = new Bundle();
		b.putString(Espresso.CODE_CLIENT, codeCli);
		b.putString(Espresso.CODE_SOCIETE, codeSoc);
		intent.putExtras(b);
		startActivityForResult(intent, 0);
	}
	
	void launchRapportTodo(String codeCli,String typedoc){

		Intent intent = new Intent(this, rapporttodo.class);
		Bundle b = new Bundle();
		b.putString(Espresso.CODE_CLIENT, codeCli);
		b.putString("typedoc", typedoc);

		intent.putExtras(b);
		startActivityForResult(intent, 3000);
	}

	void launchDialogComptant(final String typedoc){

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuCliActivity.this);
		alertDialog.setTitle("Attention !");
		alertDialog.setMessage("ATTENTION CLIENT A RISQUE : PAIEMENT COMPTANT OBLIGATOIRE. Le non respect" +
				" de cette régle engage votre responsabilité.");

		final ImageView img = new ImageView(MenuCliActivity.this);
		img.setImageDrawable(getResources().getDrawable(R.drawable.attention));
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		img.setLayoutParams(lp);
		alertDialog.setView(img);

		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						launchCde(MenuCliActivity.this,typedoc, m_codecli,cli.SOC_CODE);
					}
				});

		alertDialog.show();

	}
	void launchDialogComptant2(final String typedoc, final String codeclient){

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuCliActivity.this);
		alertDialog.setTitle("Attention !");
		alertDialog.setMessage("ATTENTION CLIENT A RISQUE : PAIEMENT COMPTANT OBLIGATOIRE. Le non respect" +
				" de cette régle engage votre responsabilité.");

		final ImageView img = new ImageView(MenuCliActivity.this);
		img.setImageDrawable(getResources().getDrawable(R.drawable.attention));
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		img.setLayoutParams(lp);
		alertDialog.setView(img);

		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						launchRapportTodo(codeclient,typedoc);

					}
				});

		alertDialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		
		if(requestCode == 1450){
			//on ouvre le pdf
			File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

			File files[] = file.listFiles();

			for (int i=0; i < files.length; i++){
				if(files[i].getName().equals(m_codecli+".pdf")){

					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(files[i]), "application/pdf");
						//intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
		if(requestCode == 3000){
			if (resultCode == Activity.RESULT_OK )
			{
				if (data != null )
				{
					Bundle extras = data.getExtras();
					if ( extras.getString("closeactivity").equals(TableSouches.TYPEDOC_FACTURE) )
					{
						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_FACTURE, m_codecli,cli.SOC_CODE);
					}
					if ( extras.getString("closeactivity").equals(TableSouches.TYPEDOC_BL) )
					{
						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BL, m_codecli,cli.SOC_CODE);
					}
					if ( extras.getString("closeactivity").equals(TableSouches.TYPEDOC_BC) )
					{
						launchCde(MenuCliActivity.this,TableSouches.TYPEDOC_BC, m_codecli,cli.SOC_CODE);
					}		
				}
							
			}
			if (resultCode == Activity.RESULT_CANCELED )
			{
				
				
			}
			
			
			
		}

		PopulateList();
	}

	void geocode(final String adress)
	{
		try {
			new Thread() {

				@Override
				public void run() {
					Log.d("TAG", "geocode : "+adress);

					Looper.prepare();

					String adr=adress;
					GPS gps = new GPS( MenuCliActivity.this, adr);
					//initDialogCancel(gps);
					if(gps.isNetworkEnabled()){
						if(gps.canGetLocation()){

							Log.d("TAG", Double.toString(gps.getLatitude()) +" , "+ Double.toString(gps.getLongitude() ));
							//		Fonctions.FurtiveMessageBox(this,Double.toString(gps.getLatitude()) +" , "+ Double.toString(gps.getLongitude() ));
							TableClient cli=new TableClient(m_appState.m_db);
							cli.updateLatLon(Double.toString(gps.getLatitude()) ,Double.toString(gps.getLongitude()),m_codecli    );

							gps.stopUsingGPS();
						}else{
							//sendResultFalse();
						}
					} 	

					Looper.loop();

				}
			}.start();

		} catch (Exception ex) {
			Log.e("tag",ex.getLocalizedMessage());
		}

	}

}
