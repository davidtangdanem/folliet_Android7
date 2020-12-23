package com.menadinteractive.segafredo.client;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.menadinteractive.commande.commandeActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbSiteListeLogin;
import com.menadinteractive.segafredo.db.dbSiteListeLogin.structlistLogin;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.synchro.SynchroService;

import java.util.ArrayList;


public class statclient extends BaseActivity
{

     //TextView
	TextView  textTitreCA;
	LinearLayout linearCA;
	TextView  TexteCA_MOIS;

	LinearLayout linearMarge;
	TextView textTitreRentabilite;
	TextView  TexteCA_MOIS_Valeur;
	TextView  TexteMarge_MOIS_Valeur;
	LinearLayout linearCA_3MOIS;
	TextView  TexteCA_3MOIS;
	TextView  TexteCA_3MOIS_Valeur;
	TextView  TexteMarge_3MOIS_Valeur;

	LinearLayout linearCA_ANNEE_N;
	TextView  TexteCA_ANNEE_N;
	TextView  TexteCA_ANNEE_N_Valeur;
	TextView  TexteMarge_ANNEE_N_Valeur;

	LinearLayout linearCA_ANNEE_N_1;
	TextView  TexteCA_ANNEE_N_1;
	TextView  TexteCA_ANNEE_N_1_Valeur;
	TextView  TexteMarge_ANNEE_N_1_Valeur;

	TextView  textTitreClassification;
	LinearLayout linearCLASSIFICATION_PARTCA;
	TextView  TexteClassification_PartCA;
	TextView  TexteClassification_PartCA_Valeur;

	LinearLayout linearCLASSIFICATION_RANG;
	TextView  TexteClassification_Rang;
	TextView  TexteClassification_Rang_Valeur;


	LinearLayout linearCLASSIFICATION_RANGAGENCE;
	TextView  TexteClassification_RangAgence;
	TextView  TexteClassification_RangAgence_Valeur;

	LinearLayout linearCLASSIFICATION_RANGVENDEUR;
	TextView  TexteClassification_RangVendeur;
	TextView  TexteClassification_RangVendeur_Valeur;

	TextView  textTitreEngagement;
	LinearLayout linearENG_MOIS;
	TextView  TexteENG_MOIS;
	TextView  TexteENG_DEMANDE_MOIS_Valeur;
	TextView  TexteENG_REALISE_MOIS_Valeur;

	LinearLayout linearENG_3MOIS;
	TextView  TexteENG_3MOIS;
	TextView  TexteENG_DEMANDE_3MOIS_Valeur;
	TextView  TexteENG_REALISE_3MOIS_Valeur;

	LinearLayout linearENG_ANNEE_N;
	TextView  TexteENG_ANNEE_N;
	TextView  TexteENG_DEMANDE_ANNEE_N_Valeur;
	TextView  TexteENG_REALISE_ANNEE_N_Valeur;

	LinearLayout linearENG_ANNEE_N_1;
	TextView  TexteENG_ANNEE_N_1;
	TextView  TexteENG_DEMANDE_ANNEE_N_1_Valeur;
	TextView  TexteENG_REALISE_ANNEE_N_1_Valeur;

	TextView TexteMarge_MOIS;
	TextView TexteMarge_3MOIS;
	TextView TexteMarge_ANNEE_N;
	TextView TexteMarge_ANNEE_N_1;

	LinearLayout linear_renta;

	Button bStatistique;

	String numProspect;

	structlistLogin rep = null;
	boolean m_btech=false;
    boolean m_Vendeur=false;
    boolean m_Responsable=false;

    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		SynchroService.setPaused(true);

		Bundle bundle = this.getIntent().getExtras();
		numProspect = bundle.getString("numProspect");

		setContentView(R.layout.activity_statclient);
		initActionBar();
		rep = getRep();
		if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien))
		{
			m_btech=true;
		}
        if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Vendeur))
        {
            m_Vendeur=true;
        }

        if(Fonctions.GetStringDanem(rep.TYPE).equals(Global.Responsable))
        {
            m_Responsable=true;
        }

		InitTextView();
		initListeners();
		Loadinformation(numProspect);

	}

	void Loadinformation(String numprospect)
	{
		cliToSave=new structClient();

		Global.dbClient .getClient(numprospect, cliToSave, new StringBuilder());

		TexteCA_MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.CAMT_0001));
		TexteMarge_MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.RENT_0001));

		TexteCA_3MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.CAMT_0002));
		TexteMarge_3MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.RENT_0002));

		TexteCA_ANNEE_N_Valeur.setText(Fonctions.GetStringDanem(cliToSave.CAMT_0003));
		TexteMarge_ANNEE_N_Valeur.setText(Fonctions.GetStringDanem(cliToSave.RENT_0003));

		TexteCA_ANNEE_N_1_Valeur.setText(Fonctions.GetStringDanem(cliToSave.CAMT_0004));
		TexteMarge_ANNEE_N_1_Valeur.setText(Fonctions.GetStringDanem(cliToSave.RENT_0004));

		TexteClassification_PartCA_Valeur.setText(Fonctions.GetStringDanem(cliToSave.PARTCARANG));
		TexteClassification_Rang_Valeur.setText(Fonctions.GetStringDanem(cliToSave.CARANG));

		TexteClassification_RangAgence_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_AGRANG));
		TexteClassification_RangVendeur_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_VRPRANG));

		TexteENG_DEMANDE_MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_CAMT_0001));
		TexteENG_REALISE_MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_MARGE_0001));

		TexteENG_DEMANDE_3MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_CAMT_0002));
		TexteENG_REALISE_3MOIS_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_MARGE_0002));

		TexteENG_DEMANDE_ANNEE_N_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_CAMT_0003));
		TexteENG_REALISE_ANNEE_N_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_MARGE_0003));

		TexteENG_DEMANDE_ANNEE_N_1_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_CAMT_0004));
		TexteENG_REALISE_ANNEE_N_1_Valeur.setText(Fonctions.GetStringDanem(cliToSave.VP_MARGE_0004));

        if (m_Responsable == true){
            linear_renta.setVisibility(View.VISIBLE);
        }
        else
		{
			TexteMarge_MOIS_Valeur.setVisibility(View.GONE);
			TexteMarge_3MOIS_Valeur.setVisibility(View.GONE);
			TexteMarge_ANNEE_N_Valeur.setVisibility(View.GONE);
			TexteMarge_ANNEE_N_1_Valeur.setVisibility(View.GONE);
			TexteMarge_MOIS.setVisibility(View.GONE);
			TexteMarge_3MOIS.setVisibility(View.GONE);
			TexteMarge_ANNEE_N.setVisibility(View.GONE);
			TexteMarge_ANNEE_N_1.setVisibility(View.GONE);
		}
	}

	private void initActionBar(){
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Statistique");
	}
	@Override
	protected void onStart() {
		super.onStart();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();


        return true;
	}





	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK ||keyCode == KeyEvent.KEYCODE_HOME) {
			finish();

			return false;
		}

		else
			return super.onKeyDown(keyCode, event);
	}

	/**
	 * Creation du menu
	 */
	static final private int MENU_ITEM = Menu.FIRST;

	structClient cliToSave;

    void InitTextView()
	{
		linearMarge=(LinearLayout)findViewById(R.id.linearMarge);
		textTitreRentabilite=(TextView)findViewById(R.id.textTitreRentabilite);
		linear_renta = (LinearLayout) findViewById(R.id.linear_renta);

		textTitreCA=(TextView)findViewById(R.id.textTitreCA);
		if(m_Vendeur==true)
            textTitreCA.setText("Chiffre d\'affaires EUR");


		linearCA=(LinearLayout)findViewById(R.id.linearCA);
		TexteCA_MOIS=(TextView)findViewById(R.id.TexteCA_MOIS);
		TexteCA_MOIS_Valeur=(TextView)findViewById(R.id.TexteCA_MOIS_Valeur);
		TexteMarge_MOIS_Valeur=(TextView)findViewById(R.id.TexteMarge_MOIS_Valeur);

		linearCA_3MOIS=(LinearLayout)findViewById(R.id.linearCA_3MOIS);
		TexteCA_3MOIS=(TextView)findViewById(R.id.TexteCA_3MOIS);
		TexteCA_3MOIS_Valeur=(TextView)findViewById(R.id.TexteCA_3MOIS_Valeur);
		TexteMarge_3MOIS_Valeur=(TextView)findViewById(R.id.TexteMarge_3MOIS_Valeur);


		linearCA_ANNEE_N=(LinearLayout)findViewById(R.id.linearCA_ANNEE_N);
		TexteCA_ANNEE_N=(TextView)findViewById(R.id.TexteCA_ANNEE_N);
		TexteCA_ANNEE_N_Valeur=(TextView)findViewById(R.id.TexteCA_ANNEE_N_Valeur);
		TexteMarge_ANNEE_N_Valeur=(TextView)findViewById(R.id.TexteMarge_ANNEE_N_Valeur);

		linearCA_ANNEE_N_1=(LinearLayout)findViewById(R.id.linearCA_ANNEE_N_1);
		TexteCA_ANNEE_N_1=(TextView)findViewById(R.id.TexteCA_ANNEE_N_1);
		TexteCA_ANNEE_N_1_Valeur=(TextView)findViewById(R.id.TexteCA_ANNEE_N_1_Valeur);
		TexteMarge_ANNEE_N_1_Valeur=(TextView)findViewById(R.id.TexteMarge_ANNEE_N_1_Valeur);

		TexteMarge_MOIS=(TextView)findViewById(R.id.TexteMarge_MOIS);
		TexteMarge_3MOIS=(TextView)findViewById(R.id.TexteMarge_3MOIS);
		TexteMarge_ANNEE_N=(TextView)findViewById(R.id.TexteMarge_ANNEE_N);
		TexteMarge_ANNEE_N_1=(TextView)findViewById(R.id.TexteMarge_ANNEE_N_1);

        if(m_Vendeur==true)
        {
			textTitreRentabilite.setVisibility(View.VISIBLE);
			linearMarge.setVisibility(View.VISIBLE);

            /*TexteMarge_MOIS_Valeur.setVisibility(View.INVISIBLE);
            TexteMarge_3MOIS_Valeur.setVisibility(View.INVISIBLE);
            TexteMarge_ANNEE_N_Valeur.setVisibility(View.INVISIBLE);
            TexteMarge_ANNEE_N_1_Valeur.setVisibility(View.INVISIBLE);*/

        }

		textTitreClassification=(TextView)findViewById(R.id.textTitreClassification);

		linearCLASSIFICATION_PARTCA=(LinearLayout)findViewById(R.id.linearCLASSIFICATION_PARTCA);
		TexteClassification_PartCA=(TextView)findViewById(R.id.TexteClassification_PartCA);
		TexteClassification_PartCA_Valeur=(TextView)findViewById(R.id.TexteClassification_PartCA_Valeur);

		linearCLASSIFICATION_RANG=(LinearLayout)findViewById(R.id.linearCLASSIFICATION_RANG);
		TexteClassification_Rang=(TextView)findViewById(R.id.TexteClassification_Rang);
		TexteClassification_Rang_Valeur=(TextView)findViewById(R.id.TexteClassification_Rang_Valeur);

		linearCLASSIFICATION_RANGAGENCE=(LinearLayout)findViewById(R.id.linearCLASSIFICATION_RANGAGENCE);
		TexteClassification_RangAgence=(TextView)findViewById(R.id.TexteClassification_RangAgence);
		TexteClassification_RangAgence_Valeur=(TextView)findViewById(R.id.TexteClassification_RangAgence_Valeur);

		linearCLASSIFICATION_RANGVENDEUR=(LinearLayout)findViewById(R.id.linearCLASSIFICATION_RANGVENDEUR);
		TexteClassification_RangVendeur=(TextView)findViewById(R.id.TexteClassification_RangVendeur);
		TexteClassification_RangVendeur_Valeur=(TextView)findViewById(R.id.TexteClassification_RangVendeur_Valeur);

		textTitreEngagement=(TextView)findViewById(R.id.textTitreEngagement);

		linearENG_MOIS=(LinearLayout)findViewById(R.id.linearENG_MOIS);
		TexteENG_MOIS=(TextView)findViewById(R.id.TexteENG_MOIS);
		TexteENG_DEMANDE_MOIS_Valeur=(TextView)findViewById(R.id.TexteENG_DEMANDE_MOIS_Valeur);
		TexteENG_REALISE_MOIS_Valeur=(TextView)findViewById(R.id.TexteENG_REALISE_MOIS_Valeur);

		linearENG_3MOIS=(LinearLayout)findViewById(R.id.linearENG_3MOIS);
		TexteENG_3MOIS=(TextView)findViewById(R.id.TexteENG_3MOIS);
		TexteENG_DEMANDE_3MOIS_Valeur=(TextView)findViewById(R.id.TexteENG_DEMANDE_3MOIS_Valeur);
		TexteENG_REALISE_3MOIS_Valeur=(TextView)findViewById(R.id.TexteENG_REALISE_3MOIS_Valeur);

		linearENG_ANNEE_N=(LinearLayout)findViewById(R.id.linearENG_ANNEE_N);
		TexteENG_ANNEE_N=(TextView)findViewById(R.id.TexteENG_ANNEE_N);
		TexteENG_DEMANDE_ANNEE_N_Valeur=(TextView)findViewById(R.id.TexteENG_DEMANDE_ANNEE_N_Valeur);
		TexteENG_REALISE_ANNEE_N_Valeur=(TextView)findViewById(R.id.TexteENG_REALISE_ANNEE_N_Valeur);



		linearENG_ANNEE_N_1=(LinearLayout)findViewById(R.id.linearENG_ANNEE_N_1);
		TexteENG_ANNEE_N_1=(TextView)findViewById(R.id.TexteENG_ANNEE_N_1);
		TexteENG_DEMANDE_ANNEE_N_1_Valeur=(TextView)findViewById(R.id.TexteENG_DEMANDE_ANNEE_N_1_Valeur);
		TexteENG_REALISE_ANNEE_N_1_Valeur=(TextView)findViewById(R.id.TexteENG_REALISE_ANNEE_N_1_Valeur);




		bStatistique = (Button) findViewById(R.id.bStatistique);


		
	}

	void initListeners()
	{


		bStatistique.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            	String  urlcli=(Global.dbParam.getComment(Global.dbParam.PARAM_URLCLI,"1"));
						if(!Fonctions.GetStringDanem(urlcli).equals(""))
						{
							String url = urlcli+numProspect;
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(url));
							startActivityForResult(i, 1450);
						}

 
            }
		});
		
		
	}





	 

}
