package com.menadinteractive.commande;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.TableTarif.structTarif;
import com.menadinteractive.segafredo.db.dbKD545StockTheoSrv;
import com.menadinteractive.segafredo.db.dbKD731HistoDocumentsLignes;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.db.dbSiteHabitudes.structHabitude;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.db.dbSiteProduit.structArt;

public class commandeInput extends BaseActivity implements TextWatcher {
	ArrayList<Bundle> histos;
	Handler handler;
	myListView lv;

	String m_codeart;
	String m_lblart;
	String m_grpcli;
	String m_codetarifcli;

	//String m_prix;
	String m_qte,m_remise;
	String m_grat;
	String m_etat;
	String m_numcde;
	String m_photo;

	String m_codcli;
	String m_prix_pcb;
	String m_prix_unitaire;
	String m_typedoc;
	String m_qterobot ;
	structArt m_art;
	structTarif m_tarif;

	TextView tvTitre;
	TextView tvCodeart;
	TextView tvBarcode;
	TextView tvStock;
	TextView tvQteMoyLivraison;
	TextView tvQte12M;
	TextView tvFrequence;
	TextView tvPrixMoyen;
	TextView tvStockMiniClient;


	TextView textViewPrix;
	TextView textViewPrixNet;
	TextView tvRemise;
	TextView textViewMntNet;
	TextView textViewQteGrat;
	
	TextView textnbunitepar_palette;
	TextView textnbunitepar_colis;

	EditText etQte;
	EditText etGrat;
	EditText etPrix;
	EditText etPUNet;
	EditText etTotNet;
	EditText etRemise;

	ImageButton bOk;
	ImageButton bCancel;

	ImageView imgPetite;

	Spinner spinEtat;

	boolean prixspecial=false;
	LinearLayout llGrat;
	String m_seuilminimun="";

	structLinCde line;
	ArrayList<Bundle> idEtat     = null;// les id qui servent a retrouver les pays
	ArrayList<Bundle> idFamRemise = null;// les id qui servent a retrouver les pays

	structlistLogin rep = null;
	boolean isClientST = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cde_inputdlg2);   

		Bundle bundle = this.getIntent().getExtras();
		m_codeart =getBundleValue(bundle, "codeart") ; 
		m_lblart =getBundleValue(bundle, "lblart") ;
		//m_prix =getBundleValue(bundle, "prix") ;
		m_qte =getBundleValue(bundle, "qte") ;
		m_remise =getBundleValue(bundle, "remise") ;
		m_grat =getBundleValue(bundle, "grat") ;
		m_numcde =getBundleValue(bundle, "numcde") ;
		m_photo=getBundleValue(bundle, "photoname") ;

		m_codcli=getBundleValue(bundle, "codcli") ;
		m_grpcli=getBundleValue(bundle, "grpcli") ;
		m_codetarifcli=getBundleValue(bundle, "codtrf") ;
		m_typedoc = getBundleValue(bundle,"typedoc");

		rep = getRep();
		if(rep != null && rep.LOGIN != null && m_codcli.equals("ST"+rep.LOGIN)){
			isClientST = true;
		}

		line=new structLinCde();
		idEtat = new ArrayList<Bundle>();
		idFamRemise = new ArrayList<Bundle>();

		m_art=new structArt();
		Global.dbProduit.getProduit(m_codeart, m_art, new StringBuilder());

		initGui();
		initListeners();
		try
		{
			m_qterobot= getBundleValue(bundle,"qterobot");
			if ( m_qterobot != null)
				if ( !m_qterobot.equals(""))
				{
					etQte.setText(m_qterobot);

					//Ajout d'une qte gartuite toutes les 5 lignes (utilisation du code article pour ça...)
					int icodeArt = Fonctions.convertToInt(m_codeart) ;
					if ( icodeArt > 10 && icodeArt%5==0)
						etGrat.setText("1");

					//pour validation auto
					Bundle messageBundle=new Bundle();
					Message myMessage;
					myMessage=hRobot.obtainMessage();

					messageBundle.putLong(ROBOT_SCAN, 1);
					myMessage.setData(messageBundle);

					hRobot.sendMessageDelayed(myMessage, GlobalGL_lRobotInterval);

				}

		}
		catch (Exception e)
		{

		}

		DispImage(imgPetite);
	}
	private final String ROBOT_SCAN="ROBOT_SCAN";
	Handler hRobot = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			long progress=msg.getData().getLong(ROBOT_SCAN);

			//simulation de l'appuie d'une touche pour lancer le scan
			//if ( Global.GL_bRobotMode == true )
			{
				ClickOK() ;
			}

		}
	};

	void initGui()
	{
		textViewPrix = (TextView) findViewById(R.id.textViewPrix);
		textViewPrixNet = (TextView) findViewById(R.id.textViewPrixNet);
		textViewMntNet = (TextView) findViewById(R.id.textViewMntNet);
		textViewQteGrat = (TextView) findViewById(R.id.textViewQteGrat);
		tvRemise = (TextView) findViewById(R.id.tvRemise);
		textnbunitepar_colis=(TextView) findViewById(R.id.textnbunitepar_colis);
		textnbunitepar_palette=(TextView) findViewById(R.id.textnbunitepar_palette);		
		
		lv = (myListView) findViewById(R.id.lvhisto);
		tvTitre=(TextView)findViewById(R.id.textViewTitre);
		tvTitre.setText( m_lblart);

		tvCodeart=(TextView)findViewById(R.id.textViewCodeart);
		tvCodeart.setText(m_codeart );

		tvBarcode=(TextView)findViewById(R.id.textViewBarcode);
		tvBarcode.setText( m_art.EAN);

		tvQteMoyLivraison = (TextView) findViewById(R.id.tvQteMoyLivraison);
		tvQte12M = (TextView) findViewById(R.id.tvQte12M);
		tvStockMiniClient = (TextView) findViewById(R.id.tvStockMiniClient);

		tvPrixMoyen= (TextView) findViewById(R.id.tvPrixMoyen);
		tvFrequence = (TextView) findViewById(R.id.tvFrequence);

		tvStock=(TextView)findViewById(R.id.tvStock);
		/*		dbKD543LinInventaire.structPassePlat pp=new structPassePlat();
		Global.dbKDLinInv.load(pp, m_codeart, new StringBuffer(),
				Preferences.getValue(this, Espresso.DEPOT,"0"),
				Preferences.getValue(this, Espresso.LOGIN,"0"),
				Preferences.getValue(this, Espresso.CODE_SOCIETE,"0")
				);
		tvStock.setText(getString(R.string.stock_)+pp.FIELD_LIGNEINV_QTETHEO);
		 */
		dbKD545StockTheoSrv stktheo=new dbKD545StockTheoSrv(getDB());
		com.menadinteractive.segafredo.db.dbKD545StockTheoSrv.structPassePlat ent=new com.menadinteractive.segafredo.db.dbKD545StockTheoSrv.structPassePlat();
		stktheo.load(ent, m_codeart, new StringBuffer(), "", "", "");
		tvStock.setText(getString(R.string.stock_)+ent.FIELD_LIGNEINV_QTETHEO);

		llGrat=(LinearLayout)findViewById(R.id.llGrat);

		etPrix=(EditText)findViewById(R.id.editTextPrix);
		etQte=(EditText)findViewById(R.id.editTextQte);
		etGrat=(EditText)findViewById(R.id.editTextQteGrat);
		etRemise=(EditText)findViewById(R.id.editTextRemise);
		etPUNet=(EditText)findViewById(R.id.editTextPrixNet);
		etTotNet=(EditText)findViewById(R.id.editTextMntNet);

		//etQte.requestFocus();

		spinEtat=(Spinner)findViewById(R.id.spinnerEtat);

		etRemise.setFilters(new InputFilter[]{ new InputFilterMinMaxFloat(0f, 35.f)});
		//TODO remplir avec la structTarif récupérer à partir du produit et du client
		/*etPrix.setText(calcPrixUInitial());
		etPrix.setEnabled(false);

		etPrixPCB.setText(calcPrixPCB());
		etPrixPCB.setEnabled(false);*/
		String tarif="0";
		m_tarif=new structTarif();

		etPrix.setTextColor(Color.BLACK);
		//	tvStock.setVisibility(View.INVISIBLE);

		//m_prix_unitaire = calculateUnitPrice(tarif, m_art.PCB);
		//m_prix_unitaire = "0.00";
		m_prix_unitaire=m_prix_pcb = convertPrice(tarif);


			//etPUNet.setEnabled(true);
			//etPrix.setEnabled(true);
			//etQte.setText(m_qte);
			if(!m_codeart.equals("")){
				if(!m_codcli.equals("") )
				{
					ArrayList<String> ValueExt=new ArrayList();
					if (Global.dbTarif.getTarif(m_grpcli, m_codeart, m_tarif, new StringBuilder()))
					{
						prixspecial=true;
						etRemise.setEnabled(true);
						tarif=m_tarif.PX_NET;
						etPrix.setEnabled(false);
						etPrix.setBackgroundColor(Color.MAGENTA);
						if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA))
						{
							etQte.setEnabled(true);
							etGrat.setEnabled(true);
							etPUNet.setEnabled(true);
							etRemise.setEnabled(false);
							etQte.setText("1");
						}

					}
					else
					{
						m_seuilminimun="";
						etRemise.setEnabled(true);
						tarif=m_art.PV_CONS;
						if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA))
						{
							etQte.setEnabled(true);
							etGrat.setEnabled(true);
							etPUNet.setEnabled(true);
							etRemise.setEnabled(false);
							etQte.setText("1");
						}


					}

				}
				else
				{
					etRemise.setEnabled(true);
					tarif=m_art.PV_CONS;
					if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA))
					{
						etQte.setEnabled(true);
						etGrat.setEnabled(true);
						etPUNet.setEnabled(true);
						etRemise.setEnabled(false);
						etQte.setText("1");
					}

				}
				//Global.dbTarif.getTarifWithoutClient(Integer.parseInt(m_codeart), m_tarif, new StringBuild
				// er());
			}

		if (m_typedoc.equals(TableSouches.TYPEDOC_RELEVELINEAIRE))
		{
			etQte.setEnabled(true);
			etPUNet.setEnabled(true);
			etRemise.setEnabled(false);
			etPrix.setEnabled(true);
		}

		etPrix.setText(m_prix_unitaire);

		//etPUNet.setEnabled(true);
		etTotNet.setEnabled(false);

		Log.i("", "qte : "+m_qte);
		//String qte = m_qte.split(".")[0];
		//etQte.setText(m_qte);
		etGrat.setText(m_grat);

		bOk=(ImageButton)findViewById(R.id.imageButtonOk);
		bCancel=(ImageButton)findViewById(R.id.imageButtonCancel);

		imgPetite=(ImageView)findViewById(R.id.imageViewPetite);

		if (m_numcde.equals(""))
		{
			etGrat.setVisibility(View.GONE);
			etQte.setVisibility(View.GONE);
			bOk.setVisibility(View.GONE);
		}

		if (Global.dbKDLinCde.load(line, m_numcde, m_codeart, "", new StringBuffer()))
		{
			etQte.setText((int)line.QTECDE+"");
			etGrat.setText((int)line.QTEGR+"");
			etPrix.setText( line.PRIXMODIF+"");
			m_prix_unitaire=m_prix_pcb= (line.PRIXMODIF)+"";
			etRemise.setText( line.REMISEMODIF+"");
			etPUNet.setText( line.MNTUNITHT+"");
			etTotNet.setText( line.MNTTOTALHT+"");
		}

		if (Fonctions.GetStringDanem(m_art.ISGRAT).equals("O")==false)
		{
			llGrat.setVisibility(View.INVISIBLE);
		}
		else
			llGrat.setVisibility(View.VISIBLE);

		fillEtat(line.FIELD_LIGNECDE_LINCHOIX1);

		if(fillHabitude(m_codcli, m_codeart)==false)
		{
			finish();
		}

		String price = "";
		if(etPUNet.getText() != null && !etPUNet.getText().toString().equals("")){
			price = etPUNet.getText().toString();
		}
		calculateTotal(Fonctions.GetStringToIntDanem(etQte.getText().toString()), price);

		handler =getHandler();
		PopulateList();

		if(isClientST){
			hidePrices();
		}
	}

	private void hidePrices() {
		textViewPrix.setVisibility(View.GONE);
		textViewPrixNet.setVisibility(View.GONE);
		textViewMntNet.setVisibility(View.GONE);
		tvRemise.setVisibility(View.GONE);
		textViewQteGrat.setVisibility(View.GONE);
		etPrix.setVisibility(View.GONE);
		etPUNet.setVisibility(View.GONE);
		etTotNet.setVisibility(View.GONE);
		etGrat.setVisibility(View.GONE);
		textnbunitepar_colis.setVisibility(View.VISIBLE);
		textnbunitepar_palette.setVisibility(View.VISIBLE);
		textnbunitepar_colis.setText("nb unités par colis : "+Fonctions.GetStringDanem(m_art.PCB));
		textnbunitepar_palette.setText("nb unités par palette : "+Fonctions.GetStringDanem(m_art.QTE_PAL));
		 

	}

	private boolean fillHabitude(String cliCode, String codArt) {
		boolean bres=true;

		structHabitude habitude = Global.dbSiteHabitudes.getHabitudeFromCodeArtAndCodeCli(codArt, cliCode);

		if(habitude != null){
			tvQteMoyLivraison.setText(habitude.FIELD_QTE_MOY_FAC);
			tvQte12M.setText(habitude.FIELD_QTE_12M);
			tvFrequence.setText(habitude.FIELD_FREQUENCE);
			tvStockMiniClient.setText(habitude.FIELD_STK_CLT);

			//on remplit la quantité, le prix et le montant total par défaut

			//quantité moyenne
			double qteMoy = 0.0;
			try{
				qteMoy = Double.parseDouble(habitude.FIELD_QTE_MOY_FAC.replace(",", "."));
			}catch(NumberFormatException ex){

			}

			//pcb
			double pcb = 0.0;
			try{
				pcb = Double.parseDouble(m_art.PCB);
			}catch(NumberFormatException ex){

			}

			if(pcb == 0) pcb =1;
			double calcul = qteMoy/pcb;

			int result = (int) Math.ceil(calcul);
			int pcbE = (int) pcb;
			int qte = result*pcbE;

			etQte.setText(Integer.toString(qte));
			if(!m_codetarifcli.equals("") )
			{
				ArrayList<String> ValueExt=new ArrayList();
				if (Global.dbTarif.getTarif2(m_codcli,Fonctions.GetStringDanemEspace(m_codetarifcli), m_codeart, ValueExt, new StringBuilder()))
				{

					/*String PrixAffiche="";ValueExt.get(0)
					String PrixModifiable="";ValueExt.get(1)
					String Seuilminimum ="";ValueExt.get(2)
					String VentePossible=""; ValueExt.get(3)*/
					m_seuilminimun=ValueExt.get(2);
					if(Fonctions.GetStringDanem(ValueExt.get(3)).equals("O"))
					{
						String prixint="";
						prixspecial=true;
						if (Global.dbKDLinCde.load(line, m_numcde, m_codeart, "", new StringBuffer()))
						{
							etPUNet.setText( line.MNTUNITHT+"");
							prixint=Fonctions.GetFloatToStringFormatDanem(line.MNTUNITHT, "0.000");
							etTotNet.setText( line.MNTTOTALHT+"");
						}
						else
						{
							prixint=ValueExt.get(0);
							etPUNet.setText(ValueExt.get(0));
						}

						if(ValueExt.get(1).equals("O"))
						{
							etPUNet.setEnabled(true);
						}
						else
						{
							etGrat.setEnabled(false);
							etPUNet.setEnabled(false);
							if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA) || m_typedoc.equals(TableSouches.TYPEDOC_RELEVELINEAIRE))
							{
								etPUNet.setEnabled(true);
							}
							
						}

						calculateTotal(qte, prixint);
					}
					else
					{	
						//Message Vente impossible ( sortie)
						Toast.makeText(commandeInput.this, "Vente impossible", Toast.LENGTH_LONG).show();	
						return false;
					}
				}
				else
				{
					String prixint="";
					if (Global.dbKDLinCde.load(line, m_numcde, m_codeart, "", new StringBuffer()))
					{
						etPUNet.setText( line.MNTUNITHT+"");
						prixint=Fonctions.GetFloatToStringFormatDanem(line.MNTUNITHT, "0.000");

						etTotNet.setText( line.MNTTOTALHT+"");
					}else
					{
						prixint=habitude.FIELD_DER_PRX_VEN;

						etPUNet.setText(habitude.FIELD_DER_PRX_VEN);
					}

					etPUNet.setEnabled(true);

					calculateTotal(qte,prixint);
				}

			}
			else
			{
				String prixint="";
				if (Global.dbKDLinCde.load(line, m_numcde, m_codeart, "", new StringBuffer()))
				{
					etPUNet.setText( line.MNTUNITHT+"");
					prixint=Fonctions.GetFloatToStringFormatDanem(line.MNTUNITHT, "0.000");

					etTotNet.setText( line.MNTTOTALHT+"");
				}
				else
				{
					prixint=habitude.FIELD_DER_PRX_VEN;
					etPUNet.setText(habitude.FIELD_DER_PRX_VEN);
				}

				etPUNet.setEnabled(true);
				calculateTotal(qte, prixint);
			}


		}
		else
		{
			if(!m_codetarifcli.equals("") )
			{
				ArrayList<String> ValueExt=new ArrayList();
				if (Global.dbTarif.getTarif2(m_codcli,Fonctions.GetStringDanemEspace(m_codetarifcli), m_codeart, ValueExt, new StringBuilder()))
				{

					/*String PrixAffiche="";ValueExt.get(0)
					String PrixModifiable="";ValueExt.get(1)
					String Seuilminimum ="";ValueExt.get(2)
					String VentePossible=""; ValueExt.get(3)*/
					m_seuilminimun=ValueExt.get(2);
					if(Fonctions.GetStringDanem(ValueExt.get(3)).equals("O"))
					{

						prixspecial=true;
						if (Global.dbKDLinCde.load(line, m_numcde, m_codeart, "", new StringBuffer()))
						{
							etPUNet.setText( line.MNTUNITHT+"");
							etTotNet.setText( line.MNTTOTALHT+"");
						}else
							etPUNet.setText(ValueExt.get(0));
						if(ValueExt.get(1).equals("O"))
						{
							etPUNet.setEnabled(true);
						}
						else
						{
							etGrat.setEnabled(false);
							etPUNet.setEnabled(false);
							if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA) || m_typedoc.equals(TableSouches.TYPEDOC_RELEVELINEAIRE))
							{
								etPUNet.setEnabled(true);
							}
						}
							

					}
					else
					{	
						//Message Vente impossible ( sortie)
						Toast.makeText(commandeInput.this, "Vente impossible", Toast.LENGTH_LONG).show();	
						return false;

					}

				}

			}

		}

		return bres; 

	}

	void calculateTotal(int qte, String price){
		double unitPrice = 0.0;
		try{
			unitPrice = Double.parseDouble(price.replace(",", "."));
		}catch(NumberFormatException ex){

		}
		//Prix Moyen 
		 double dmt=0;
		 int iqte=0;
		 int igrat=0;
		 iqte=qte;
		 igrat=Fonctions.GetStringToIntDanem(getQteGrat());
		 String pu=etTotNet.getText().toString();
		 
		 String pMoyen="";
		 
		 if(igrat>0)
		 {
			 dmt=Fonctions.GetStringToDoubleDanem(pu)/(iqte+igrat);
			 pMoyen=String.valueOf(Fonctions.round( dmt,2) );
				
			 tvPrixMoyen.setText(" Prix moyen : "+pMoyen);
			 tvPrixMoyen.setVisibility(View.VISIBLE);
					 
		 }
		 else
			 tvPrixMoyen.setVisibility(View.GONE);
		
		double montant = unitPrice*qte;	
		//convertToFloat(Fonctions.GetDoubleToStringFormatDanem(montant, "0.000"));
		String mm=String.valueOf(Fonctions.round( montant,2) );
		//String mm=String.valueOf(Fonctions.roundWithPrecision(montant, 2));
		
		etTotNet.setText(mm);
	}

	private void PopulateList() {
		dbKD731HistoDocumentsLignes hd=new dbKD731HistoDocumentsLignes(m_appState.m_db);

		histos=hd.getHistoArt(m_codeart, m_codcli);
		ArrayList<assoc> assocs =new ArrayList<assoc>();

		assocs.add(new  assoc(0,R.id.tvDate,"datedoc"));
		assocs.add(new  assoc(0,R.id.tvQte,hd.FIELD_QTE));
		assocs.add(new  assoc(0,R.id.tvQteGr,hd.FIELD_QTEGRAT));
		
		if(!isClientST){
			assocs.add(new  assoc(0,R.id.tvPU,hd.FIELD_PUNET));
			assocs.add(new  assoc(0,R.id.tvRemise,hd.FIELD_REMISE));
			assocs.add(new  assoc(0,R.id.tvMntHT,hd.FIELD_MNTHT));
		}
		//assocs.add(new  assoc(0,R.id.tvLblArt,dbSiteProduit.FIELD_PRO_NOMART1));
		assocs.add(new  assoc(1,R.id.iv1,"ICON"));



		lv.attachValues(R.layout.item_list_histoart, histos,assocs,handler);

		if (histos.size()>0)
		{
			String remise=etRemise.getText().toString();
			if (remise.equals("") && 	  (prixspecial==false))
				etRemise.setText(histos.get(0).getString(hd.FIELD_REMISE));
		}
	}
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
				case R.id.iv2:

					int  id=bGet.getInt("position");

					break;

				}

			}
		};
		return h;
	}
	/*
	String calcPrixPCB()
	{
		float calc=
				Fonctions.convertToFloat(getPrixApplique())*Fonctions.convertToFloat(m_art.PCB);

		return Fonctions.round(calc, 2)+"";
	}
	String calcPrixUInitial()
	{
		float calc=
				Fonctions.convertToFloat(m_art.PV_CONS);

		return Fonctions.round(calc, 2)+"";
	}
	 */
	/*
	//on applique le prix en fonction de la remise
	String getPrixApplique()
	{
		String prix=m_art.PV_CONS;

		float remise=Fonctions.convertToFloat(getRemise());
		double prixNet=Fonctions.convertToFloat(prix)-Global.dbProduit.Calculpourcent(Fonctions.convertToFloat(prix), remise);
		prixNet=Fonctions.round(prixNet, 2);
		prix=prixNet+"";

		return prix;
	}
	 */	String getRemise()
	 {
		 try {

			 return etRemise.getText().toString();
		 } catch (Exception e) {
			 return "0";
		 }
	 }


	 /**
	  * Permet de calculer le prix unitaire du produit
	  * @return String
	  */
	 /*	String calculateUnitPrice(String price, String pcb){
		double pricePCB = 0;
		double PCB = 0;
		double priceUnit = 0;
		price = price.replace(",", ".");
		try{
			pricePCB = Fonctions.round(Double.parseDouble(price), 2);
			PCB = Double.parseDouble(pcb);
			priceUnit = pricePCB/PCB;
			priceUnit = Fonctions.round(priceUnit, 2);
		}catch(Exception ex){

		}
		return Double.toString(priceUnit);
	}
	  */
	 /**
	  * Permet de convertir un prix (X.XX)
	  * @return String
	  */
	 String convertPrice(String price){
		 double priceReturn = 0;
		 price = price.replace(",", ".");
		 try{
			 priceReturn = Fonctions.round(Double.parseDouble(price), 3);
		 }catch(Exception ex){

		 }
		 return Fonctions.GetDoubleToStringFormatDanem(priceReturn,"0.000");
	 }

	 String getQte()
	 {
		 return etQte.getText().toString();
	 }
	 String getQteGrat()
	 {
		 return etGrat.getText().toString();
	 }
	 void initListeners()
	 {
		 bOk.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View v) {

				ClickOK() ;		//pour mode robot, sortie de la fonction precedement ici

			 }
		 });

		 bCancel.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View v) {
				 finish();

			 }
		 });

		 imgPetite.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View v) {
				 viewFullSizeImage();

			 }
		 });

		 etQte.addTextChangedListener(this);
		 etGrat.addTextChangedListener(this);
		 etPUNet.addTextChangedListener(this);
	 }

	 void ClickOK()
	 {
		 boolean bseuilminimumOk=true;

		 if(!m_seuilminimun.equals(""))
		 {
			 double dmt=0;
			 String stdmt="";
			 int iqte=0;
			 int igrat=0;
			 iqte=Fonctions.GetStringToIntDanem(getQte());
			 igrat=Fonctions.GetStringToIntDanem(getQteGrat());
			 String pu=etTotNet.getText().toString();


			 if(iqte>0)
			 {
				 dmt=Fonctions.GetStringToDoubleDanem(pu)/(iqte+igrat);
				 stdmt=Fonctions.GetDoubleToStringFormatDanem(dmt,"0.000");

				 if(Fonctions.GetStringToDoubleDanem(m_seuilminimun)>Fonctions.GetStringToDoubleDanem(stdmt))
				 {
					 if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA) || m_typedoc.equals(TableSouches.TYPEDOC_RELEVELINEAIRE))
					 {

					 }else
					 bseuilminimumOk=false;
				 }
			 }

		 }
		 if(bseuilminimumOk==false)
		 {

			 //message
			 //Le seuil minimum est de m_seuilminimun;
			 Toast.makeText(commandeInput.this, "Le seuil minimum est de : "+m_seuilminimun+" euro", Toast.LENGTH_LONG).show();
			 return;
		 }
		 else
		 {
			 if (m_typedoc.equals(TableSouches.TYPEDOC_PROFORMA))
			 {
				 Toast.makeText(commandeInput.this, "Le prix de vente saisie est en dehors des conditions tarifaires standard de ce client. Une demande de validation sera envoyée au service concerné pour validation", Toast.LENGTH_LONG).show();

			 }


			 Intent result = new Intent();
			 Bundle b = new Bundle();
			 b.putString("qte", getQte());
			 b.putString("grat", getQteGrat());
			 //b.putString("prix", getPrixApplique());
			 //b.putString("prixintit", calcPrixUInitial() );
			 // b.putString("prix", m_prix_pcb);
			 b.putString("prix", etPUNet.getText().toString());

			 b.putString("prixintit", m_prix_unitaire );
			 b.putString("codcli", m_codcli);
			 b.putString("codeart", m_codeart);
			 b.putString("lblart", m_lblart);
			 b.putString("remise", getRemise() );

			 if(etTotNet.getText() != null && !etTotNet.getText().toString().equals("")){
				 b.putString("prixTotal", etTotNet.getText().toString());
			 }

			 if(etPUNet.getText() != null && !etPUNet.getText().toString().equals("")){
				 b.putString("prixUnitaire", etPUNet.getText().toString());
			 }

			 int etatSel=getSpinnerSelectedIdx(spinEtat);
			 if (etatSel==-1)
				 b.putString("etat", "");
			 else
				 b.putString("etat", etatSel+"");

			 result.putExtras(b);
			 setResult(Activity.RESULT_OK, result);

			 finish();

		 }

	 }
	 void DispImage(ImageView img)
	 {

		 String fullPath = ExternalStorage.getFolderPath(ExternalStorage.ROOT_FOLDER);
		 File jpgFile = new File(fullPath + m_photo+".jpg");


		 Bitmap thumbnail = null;
		 // if (jpgFile.exists()) //on enleve le test car ca
		 // fait trop ralentir la liste
		 // on laisse la gestion de l'erreur au catch
		 {
			 // Log.d("TAG", "file exist " + photoname);
			 try {
				 thumbnail = BitmapFactory.decodeStream(
						 new FileInputStream(jpgFile), null,
						 null);

				 // else
				 // thumbnail = defaultThumbnailBitmap;

			 } catch (FileNotFoundException e) {
				 // TODO Auto-generated catch block
				 //img.setBackgroundResource(R.drawable.basic1_016_search_zoom_find);
				 img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				 e.printStackTrace();
				 Log.d("TAG",
						 "error decoding" + e.toString());

				 return;
			 }
		 }

		 img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		 img.setImageBitmap(thumbnail);

	 }

	 private void viewFullSizeImage(){

		 String fullPath = ExternalStorage.getFolderPath(ExternalStorage.ROOT_FOLDER);

		 //		File jpgFile = new File(fullPath + CommandeState.currentProduct.PHOTONAME+ ".jpg");
		 File jpgFile = new File(fullPath + m_photo+".jpg");

		 if(jpgFile.exists()){
			 Intent intent = new Intent();  
			 intent.setAction(android.content.Intent.ACTION_VIEW);  

			 //intent.setData(Uri.fromFile(file));
			 intent.setDataAndType(Uri.fromFile(jpgFile), "image/*");  
			 startActivity(intent);  
		 }

	 }

	 void fillEtat(String selVal) {
		 try {
			 //				if (Global.dbParam.getRecordsFiltreAllSoc(Global.dbParam.PARAM_FAM1,this.idFam, "1") == true) {
			 if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_LINCHOIX1, idEtat,true) == true) {

				 Bundle bundle = new Bundle();

				 int pos = 0;
				 String[] items = new String[idEtat.size()];
				 for (int i = 0; i < idEtat.size(); i++) {

					 items[i] = idEtat.get(i).getString(
							 Global.dbParam.FLD_PARAM_LBL);

					 String codeRec = idEtat.get(i).getString(
							 Global.dbParam.FLD_PARAM_CODEREC);

					 if (selVal.equals(codeRec)) {
						 pos = i;
					 }

				 }




				 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						 android.R.layout.simple_spinner_item, items);
				 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				 spinEtat.setAdapter(adapter);

				 spinEtat.setSelection(pos);

			 }

		 } catch (Exception ex) {

		 }

	 }
	 public class InputFilterMinMaxFloat implements InputFilter {

		 private float min, max;

		 public InputFilterMinMaxFloat(float min, float max) {
			 this.min = min;
			 this.max = max;
		 }



		 @Override
		 public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {   
			 try {
				 String decimalpart=Fonctions.GiveFld(dest.toString(), 1, ".",true);
				 if (decimalpart.length()>=2)
					 return "";
				 float input = Float.parseFloat(dest.toString() + source.toString());
				 if (isInRange(min, max, input))
					 return null;
			 } catch (NumberFormatException nfe) { }     
			 return "";
		 }

		 private boolean isInRange(float a, float b, float c) {
			 return b > a ? c >= a && c <= b : c >= b && c <= a;
		 }
	 }
	 @Override
	 public void beforeTextChanged(CharSequence s, int start, int count,
			 int after) {


	 }

	 @Override
	 public void onTextChanged(CharSequence s, int start, int before, int count) {
		 //on recalcule le total avec la nouvelle quantité ou le nouveau prix
		 //Récupération de la quantité et du prix unitaire
		 String qteS = "";
		 if(etQte.getText() != null && !etQte.getText().toString().equals("")){
			 qteS = etQte.getText().toString();
		 }

		 int qte = 0;
		 try{
			 qte = Integer.parseInt(qteS);
		 }catch(NumberFormatException ex){

		 }

		 String price = "";
		 if(etPUNet.getText() != null && !etPUNet.getText().toString().equals("")){
			 price = etPUNet.getText().toString();
		 }
		 calculateTotal(qte, price);
		
		 
	 }

	 @Override
	 public void afterTextChanged(Editable s) {


	 }
}
