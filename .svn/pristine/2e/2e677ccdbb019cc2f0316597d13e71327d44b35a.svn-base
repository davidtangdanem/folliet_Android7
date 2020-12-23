package com.menadinteractive.segafredo.remisebanque;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.DashboardActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.printmodels.BluetoothConnectionInsecureExample;
import com.menadinteractive.printmodels.Z420ModelRemiseEnBanque;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD981RetourBanqueEnt;
import com.menadinteractive.segafredo.db.dbKD981RetourBanqueEnt.structRetourBanque;
import com.menadinteractive.segafredo.encaissement.Encaissement;
import com.menadinteractive.segafredo.plugins.Espresso;

public class RecapitulatifRemiseEnBanque extends BaseActivity{

	structRetourBanque remise = null;
	private ProgressDialog m_ProgressDialog = null;

	ArrayList<String> identifiants;

	ArrayList<Encaissement> chequesToday = new ArrayList<Encaissement>();
	ArrayList<Encaissement> chequesNotToday = new ArrayList<Encaissement>();
	ArrayList<Encaissement> especes = new ArrayList<Encaissement>();

	TextView tvTotal, tvTotalEspece, tvTotalCheque,tvTotalChequeCHP, tvCodeBanque, tvBanque, tvDate, tvEmail, tvFax;

	Context mContext;

	String numSouche = null;

	boolean m_isPrinted = false;
	boolean m_problemPrinter = false;

	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recapitulatif_remise_en_banque);

		mContext = this;

		identifiants = new ArrayList<String>();

		//On récupère le numéro de remise en banque	
		String numRemise = null;
		Intent i = getIntent();
		Bundle b = null;
		if(i != null) b = i.getExtras();
		if(b != null){
			numRemise = b.getString("num_remise");
			identifiants = b.getStringArrayList("id_encaissement");
		}

		initGUI();

		//TODO si on n'a pas de numéro de remise alors une erreur s'est produite

		//On récupère la remise en banque
		remise = Global.dbKDRetourBanqueEnt.getRemiseBanque(numRemise);

		//on initialise les données de la remise en banque
		fillDataRemise();

		for(String identifiant : identifiants){
			Encaissement enc = Encaissement.getEncaissement(identifiant, null);
			if(enc !=null && enc.getTypePaiement().equals(Encaissement.TYPE_CHEQUE)){
				//si il est à la date du jour alors dans la liste today
				//sinon liste nottoday
				chequesToday.add(enc);
			}else if(enc != null && enc.getTypePaiement().equals(Encaissement.TYPE_ESPECE)){
				especes.add(enc);
			}else if(enc != null && enc.getTypePaiement().equals(Encaissement.TYPE_CHEQUEPORTEFEUILLE)){
				chequesNotToday.add(enc);
			}
		}

	}

	void initGUI(){
		handler = getHandlerPrintRemiseEnBanque();
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvTotalEspece = (TextView) findViewById(R.id.tvTotalEspece);
		tvTotalCheque = (TextView) findViewById(R.id.tvTotalCheque);
		tvTotalChequeCHP = (TextView) findViewById(R.id.tvTotalChequeCHP);
		tvCodeBanque = (TextView) findViewById(R.id.tvCodeBanque);
		tvBanque = (TextView) findViewById(R.id.tvBanque);
		tvDate = (TextView) findViewById(R.id.tvDate);
		tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvFax = (TextView) findViewById(R.id.tvFax);
	}

	void fillDataRemise(){
		tvTotal.setText(remise.MNT_TOTAL);
		tvTotalEspece.setText(remise.MNT_ESPECE);
		  
		tvTotalCheque.setText(remise.MNT_CHEQUE);
		tvTotalChequeCHP.setText(remise.MNT_CHEQUE_CHP);
		tvCodeBanque.setText(remise.CODEBANQUE);

		//on récupère le libellé banque à partir du code banque
		String banque = Global.dbParam.getLblAllSoc(Global.dbParam.PARAM_BANQUEREMISE, remise.CODEBANQUE);

		tvBanque.setText(banque);
		tvDate.setText(remise.DATE);
		tvEmail.setText(remise.EMAIL);
		tvFax.setText(remise.FAX);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	
		addMenu(menu, R.string.commande_valider, android.R.drawable.ic_menu_add);
		addMenu(menu, R.string.commande_print,R.drawable.print_icon);
		addMenu(menu, R.string.commande_annuler,R.drawable.action_close);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.string.commande_print:
			launchPrinting();
			//MessageYesNo(getString(R.string.reb_impression), R.string.commande_print);
			return true;
		case R.string.commande_valider:
			if (remise != null && Global.dbKDRetourBanqueEnt.isPrintOk(remise.NUMCDE)  == false && m_problemPrinter==false ) {
				AlertMessage(getString(R.string.commande_notPrinted), false);
				return true;
			}
			if(remise != null)
				AlertMessage(getString(R.string.commande_valider_msg), true);
			else AlertMessage(getString(R.string.commande_noLines));
			return true;
		case R.string.commande_annuler:
			if (remise != null && Global.dbKDRetourBanqueEnt.isPrintOk(remise.NUMCDE)  == false){
				MessageYesNo(getString(R.string.reb_annulation_remise), R.string.commande_annuler);
			}else{
				if(remise != null && Global.dbKDRetourBanqueEnt.isPrintOk(remise.NUMCDE)  == true) 
					AlertMessage(getString(R.string.annulation_impossible), false);
				else {
					Intent i = new Intent(mContext, DashboardActivity.class);
					//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(i);
				}
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected MenuItem addMenu(Menu menu, int stringID, int iconID){
		MenuItem item  = menu.add(Menu.NONE, stringID, Menu.NONE, stringID);
		int size = menu.size() - 1;
		if(iconID != -1)
			menu.getItem(size).setIcon(iconID);
		menu.getItem(size).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return item;
	}

	protected MenuItem addMenu(Menu menu, int stringID, String label, int iconID){
		MenuItem item  = menu.add(Menu.NONE, stringID, Menu.NONE, label);
		int size = menu.size() - 1;
		if(iconID != -1)
			menu.getItem(size).setIcon(iconID);
		menu.getItem(size).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return item;
	}

	public void MessageYesNo(String message, final int type) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				switch (type) {
				case R.string.commande_print:
					//on envoie l'impression
					launchPrinting();
					break;
				case R.string.commande_annuler:
					//suppression d'une entete et de ces lignes
					boolean success = Global.dbKDRetourBanqueEnt.deleteRemiseEnBanqueFromNum(remise.NUMCDE, identifiants);
					if(success) Fonctions.makeSpecialToast(mContext, R.string.annulation_document, Toast.LENGTH_SHORT, Gravity.CENTER);
					Intent i = new Intent(mContext, DashboardActivity.class);
					//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(i);
					break;
				case R.string.commande_valider:
					finish();
					break;
				}

			}

		})
		.setNegativeButton(getString(android.R.string.no),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void launchPrinting() {
		m_isPrinted = true;
		
		m_ProgressDialog=ProgressDialog.show(this, getString(R.string.communication_avec_l_imprimante), getString(R.string.patientez_));
		
		String mac=getPrinterMacAddress();
		BluetoothConnectionInsecureExample example = new BluetoothConnectionInsecureExample(handler);

		Z420ModelRemiseEnBanque model=new Z420ModelRemiseEnBanque(this);

		TableSouches souche=new TableSouches(m_appState.m_db,this);
		numSouche =  souche.get(TableSouches.TYPEDOC_REMISEBANQUE, Preferences.getValue(this, Espresso.LOGIN, "0"));

		//on update l'entete pour intégrer le numéro de souche
		Global.dbKDRetourBanqueEnt.updateNumSouche(numSouche, remise.NUMCDE);

		setPrintOk();

		String zplDataChequesToday = "";
		String zplDataChequesNotToday = "";
		String zplDataEspeces = "";
		if(chequesToday != null && chequesToday.size() > 0) zplDataChequesToday = model.getRemiseEnBanque(remise.NUMCDE, chequesToday, Preferences.getValue(this, Espresso.LOGIN, "0"), Z420ModelRemiseEnBanque.CHEQUE,false);
		if(chequesNotToday != null && chequesNotToday.size() > 0) zplDataChequesNotToday = model.getRemiseEnBanque(remise.NUMCDE, chequesNotToday, Preferences.getValue(this, Espresso.LOGIN, "0"), Z420ModelRemiseEnBanque.CHEQUE_PORTEFEUILLE,true);
		if(especes != null && especes.size() > 0) zplDataEspeces = model.getRemiseEnBanque(remise.NUMCDE, especes, Preferences.getValue(this, Espresso.LOGIN, "0"), Z420ModelRemiseEnBanque.ESPECE,false);
		
		//String zplData = zplDataCheques + zplDataEspeces;
		if(zplDataChequesToday != null && !zplDataChequesToday.equals("")){
			example.sendZplOverBluetooth(mac,zplDataChequesToday);
			//launchPrintPreview(zplData);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(zplDataChequesNotToday != null && !zplDataChequesNotToday.equals("")){
			example.sendZplOverBluetooth(mac,zplDataChequesNotToday);
			//launchPrintPreview(zplData);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(zplDataEspeces != null && !zplDataEspeces.equals("")){
			example.sendZplOverBluetooth(mac,zplDataEspeces);
		}
		
		if (m_ProgressDialog!=null) m_ProgressDialog.dismiss();
	}

	public void AlertMessage(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	void setPrintOk()
	{

		for(String id : identifiants){
			Global.dbKDEncaissement.updateRemiseEnBanqueOfEncaissement_Cheque(id, Encaissement.REB_SAVE);
		}	
		
		//On delete les autre encaissement non coché
		Global.dbKDEncaissement.DeleteEncaissementNonRB();
		
		dbKD981RetourBanqueEnt reg=new dbKD981RetourBanqueEnt(m_appState.m_db);

		if (reg.isPrintOk(remise.NUMCDE)==false && numSouche != null)
		{
			//Intégration du numéro de souche
			TableSouches souche=new TableSouches(m_appState.m_db,this);
			souche.incNum(Preferences.getValue(this, Espresso.LOGIN, "0"), TableSouches.TYPEDOC_REMISEBANQUE);


			reg.setPrint(remise.NUMCDE,numSouche);
		}
	}

	Handler getHandlerPrintRemiseEnBanque() {
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				//if (m_ProgressDialog!=null) m_ProgressDialog.dismiss();
				super.handleMessage(msg);
				Bundle bGet = msg.getData();
				if (msg.what!=BluetoothConnectionInsecureExample.ERRORMSG_OK)
				{				
					MessageYesNoPrint(BluetoothConnectionInsecureExample.getErrMsg(msg.what)+"\n\nAvez vous un problème bloquant qui vous empèche d'imprimer ?", 433, getString(R.string.probl_me_d_impression));
				}
				else
				{
					setPrintOk();
					m_isPrinted = true;
				}

			}
		};
		return h;
	}

	public void MessageYesNoPrint(String message, int type,String title) {
		final int m_type = type;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setTitle(title)
		.setPositiveButton(getString(R.string.Yes),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				switch (m_type) {
				case 433://probleme d'imprimante
					m_problemPrinter=true;
					promptText(getString(R.string.probl_me_d_impression),
							getString(R.string.vous_pouvez_valider_le_document_sans_imprimer), 
							false);

					setPrintOk();
					break;

				}

			}
		})
		.setNegativeButton(getString(R.string.No),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				m_isPrinted = false;
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void AlertMessage(String message, final boolean quit) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				if(quit){
					//launchPrinting();
					//Intent i = new Intent(mContext, DashboardActivity.class);
					//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					//mContext.startActivity(i);	
					finish();
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
