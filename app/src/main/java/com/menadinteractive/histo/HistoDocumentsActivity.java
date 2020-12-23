package com.menadinteractive.histo;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.communs.assoc;
import com.menadinteractive.segafredo.communs.myListView;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD729HistoDocuments;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.plugins.Espresso;

public class HistoDocumentsActivity extends BaseActivity implements OnClickListener {
	
	ArrayList<Bundle> histos;
	Handler handler;
	ImageButton ibFind;
	EditText etFilter;
	TextView tvTitre, tvEncoursClientTitle;
	myListView lv;
	String m_codecli;
	String m_numDocForDuplication;//si on vient de la facture c'est son um�ro pour recevoir la duplication si besoin
	String m_typeDocForDuplication;
	
	
	structlistLogin rep = null;
	boolean isClientST = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_histodocuments);
		 
		Bundle bundle = this.getIntent().getExtras();
		m_codecli=getBundleValue(bundle, Espresso.CODE_CLIENT);
		m_numDocForDuplication=getBundleValue(bundle, "m_numDocForDuplication");
		m_typeDocForDuplication=getBundleValue(bundle, "m_typeDocForDuplication");
		
		rep = getRep();
		if(rep != null && rep.LOGIN != null && m_codecli.equals("ST"+rep.LOGIN)){
			isClientST = true;
		}
		
		initGUI();
		setListeners();
		DispInfoCli();
	}
	
	void initGUI() {
		lv = (myListView) findViewById(R.id.listView1);
		etFilter= (EditText) findViewById(R.id.etFilter);
		ibFind= (ImageButton) findViewById(R.id.ibFind);
		tvTitre=(TextView) findViewById(R.id.textViewTitre);
		tvEncoursClientTitle = (TextView) findViewById(R.id.tvEncoursClientTitle);
		
		if(isClientST){
			tvEncoursClientTitle.setVisibility(View.GONE);
		}
		
		Fonctions.setFont(this, tvTitre, Global.FONT_REGULAR);
		
		handler =getHandler();
		PopulateList();
	}
	
	void setListeners()
	{
	
		
		ibFind.setOnClickListener(this);
	}
	
	private void PopulateList() {
		dbKD729HistoDocuments hd=new dbKD729HistoDocuments(m_appState.m_db);
		
		histos=hd.getWihtoutRG(m_codecli,"",TableSouches.TYPEDOC_REGLEMENT,TableSouches.TYPEDOC_IMPAYES,TableSouches.TYPEDOC_OD,TableSouches.TYPEDOC_ECART);
		ArrayList<assoc> assocs =new ArrayList<assoc>();
		
			if(!isClientST) assocs.add(new  assoc(0,R.id.tvTypeDoc,hd.FIELD_TYPEDOC));
			assocs.add(new  assoc(0,R.id.tvNumDoc,hd.FIELD_NUMDOC));
			assocs.add(new  assoc(0,R.id.tvDateDoc,hd.FIELD_DATEDOC));
			if(!isClientST) assocs.add(new  assoc(0,R.id.tvDateEch,hd.FIELD_DATEECH));
			if(!isClientST) assocs.add(new  assoc(0,R.id.tvMnt,hd.FIELD_MNTHT));
			assocs.add(new  assoc(1,R.id.iv1,"ICON"));
	 
			
			lv.attachValues(R.layout.item_list_histodocuments, histos,assocs,handler);
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	Handler getHandler() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				switch (msg.what) {
				case R.id.listView1:
				case R.id.iv2:
				 
 
					int  id=bGet.getInt("position");
					dbKD729HistoDocuments hde=new dbKD729HistoDocuments(m_appState.m_db);
					String codedoc=histos.get(id).getString(hde.FIELD_NUMDOC);
					String codecli=histos.get(id).getString(hde.FIELD_CODECLIENT);
					String typedoc=histos.get(id).getString(hde.FIELD_TYPEDOC);
					
					if (isAllowedToDuplicate(typedoc))
						launchHistoDetail(codedoc,codecli,m_numDocForDuplication,typedoc,m_typeDocForDuplication);
					else
						launchHistoDetail(codedoc,codecli,"",typedoc,m_typeDocForDuplication);
					
					break;
				 
				}

			}
		};
		return h;
	}
	
	/*on autorise les duplication
	FA->FA
	FA->AV
	AV->FA
	BL->RT
	RT->BL	
	*/
	boolean isAllowedToDuplicate(String typeDoc)
	{
		dbKD84LinCde lin=new dbKD84LinCde(m_appState.m_db);
		//si la commande a d�j� �t� commenc�e, on ne peut pas dupliquer
		if (lin.Count_Numcde(m_numDocForDuplication, false)>0)
		{
			return false;
		}
		if (m_typeDocForDuplication.equals(TableSouches.TYPEDOC_FACTURE) && typeDoc.equals(TableSouches.TYPEDOC_FACTURE))
			return true;
		if (m_typeDocForDuplication.equals(TableSouches.TYPEDOC_AVOIR) && typeDoc.equals(TableSouches.TYPEDOC_FACTURE))
			return true;
		if (m_typeDocForDuplication.equals(TableSouches.TYPEDOC_FACTURE) && typeDoc.equals(TableSouches.TYPEDOC_AVOIR))
			return true;
		if (m_typeDocForDuplication.equals(TableSouches.TYPEDOC_BL) && typeDoc.equals(TableSouches.TYPEDOC_RETOUR))
			return true;
		if (m_typeDocForDuplication.equals(TableSouches.TYPEDOC_RETOUR) && typeDoc.equals(TableSouches.TYPEDOC_BL))
			return true;
		
		return false;
	}
	
	void DispInfoCli()
	{
		structClient cli=new structClient();
		Global.dbClient.getClient(m_codecli, cli, new StringBuilder());
		float totalEncours = Fonctions.GetStringToFloatDanem(cli.MONTANTTOTALENCOURS);
		
		TextView tvCode,tvEnseigne;
		TextView tvVille;
		TextView tvCP;
		TextView tvMail;
		TextView tvTel;
		TextView tvRS;
		TextView textTitreTarif;
		TextView tvTarif;
		
		TextView tvAdr1;
		TextView tvAdr2,tvCliBloque,tvEncoursClient;
		TextView textViewNote;
		
		tvMail = (TextView) findViewById(R.id.textViewMail);
		tvRS = (TextView) findViewById(R.id.textViewRS);
		tvCP = (TextView) findViewById(R.id.textViewCP);
		tvTel = (TextView) findViewById(R.id.textViewTel);
		textTitreTarif= (TextView) findViewById(R.id.textTitreTarif);
		tvTarif = (TextView) findViewById(R.id.textViewTarif);
		
		tvVille = (TextView) findViewById(R.id.textViewVille);
		tvAdr1 = (TextView) findViewById(R.id.textViewAdr1);
		tvAdr2 = (TextView) findViewById(R.id.textViewAdr2);
		tvCode = (TextView) findViewById(R.id.textViewCode);
		tvCliBloque = (TextView) findViewById(R.id.tvCliBloque);
		tvEnseigne = (TextView) findViewById(R.id.textViewEnseigne);
		tvEncoursClient = (TextView) findViewById(R.id.tvEncoursClient);
		textViewNote= (TextView) findViewById(R.id.textViewNote);

		
		tvRS.setText(cli.NOM.trim());
		tvMail.setText(cli.EMAIL);
		tvCP.setText(cli.CP);
		tvTel.setText(cli.TEL1);
		tvVille.setText(cli.VILLE);
		tvCode.setText(cli.CODE);
		tvEnseigne.setText(cli.ENSEIGNE.trim());
		tvAdr1.setText(cli.ADR1);
		tvAdr2.setText(cli.ADR2);
		tvEncoursClient.setText(Fonctions.GetFloatToStringFormatDanem(totalEncours, "0.00"));
		tvTarif.setText(Fonctions.GetStringDanem(Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_LISTETARIF,cli.CODETRF).trim()));
		textViewNote.setText("Note : " + Fonctions.GetStringDanem(cli.CLI_SAV));
		
		
		if(isClientST){
			tvEncoursClient.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//on revient de la cde, si on a pris une commande on ne peut pas annuler le prospect
		//on recharge la fiche aussi car peut �tre modifiŽe dans la cde
		if(resultCode==RESULT_OK )
		{
			returnOK();
		}

		hideKeyb();

	}
}
