package com.menadinteractive.histo;

import java.util.ArrayList;

import com.menadinteractive.agendanem.AgendaListActivity;
import com.menadinteractive.agendanem.AgendaViewActivity;
import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableSouches;
import com.menadinteractive.segafredo.db.dbKD84LinCde;
import com.menadinteractive.segafredo.db.TableClient.structClient;
import com.menadinteractive.segafredo.db.dbKD101ClientVu.structClientvu;
import com.menadinteractive.segafredo.db.dbKD84LinCde.structLinCde;
import com.menadinteractive.segafredo.plugins.Agendanem;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.synchro.SynchroService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class InterventionAutreRAActivity extends BaseActivity implements
OnItemSelectedListener, OnClickListener {
	
	TextView TextViewCommentaire;
	TextView TexteSymptome;
	
	EditText EditCommentaire;
	
	
	ArrayList<Bundle> idQui = null;
	ArrayList<Bundle> idMarque = null;
	ArrayList<Bundle> idTypeMachine = null;
	ArrayList<Bundle> idSymptomes = null;
	ArrayList<Bundle> idJourFermeture= null;
	Spinner spinnerSymptomes;
	Button butSymptome;
	
	
	int m_ilongueurComt=20;

	private ProgressDialog m_ProgressDialog = null;
	/** Task */
	String m_codeclient = "";
	
	String m_codearticle = "";
	String m_libarticle = "";
	String m_code_symp="";
	String m_lib_symp="";
	String m_typ_inter="";
	
	String m_qui="";
	String m_marque="";
	String m_typemachine="";
	String m_marquemachine="";
	String m_seriemachine="";
	
	

		
	String m_numcde="";
	
	String stvarCodeMarque="";
	String stvarCodeTypeMachine="";
	
	Context mContext;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SynchroService.setPaused(true);
		idQui = new ArrayList<Bundle>();
		idMarque = new ArrayList<Bundle>();
		idTypeMachine = new ArrayList<Bundle>();
		idSymptomes = new ArrayList<Bundle>();
		idJourFermeture = new ArrayList<Bundle>();
		

		Bundle bundle = this.getIntent().getExtras();
		m_codeclient = bundle.getString("codeclient");
		m_codearticle = bundle.getString("codearticle");
		m_libarticle = bundle.getString("libarticle");
		m_numcde = bundle.getString("numcde");
		m_typ_inter= bundle.getString("typ_inter");
		m_code_symp= bundle.getString("code_symp");
		m_lib_symp= bundle.getString("lib_symp");
		m_qui= bundle.getString("qui");
		m_marque= bundle.getString("marque");
		m_typemachine= bundle.getString("typemachine");
		m_marquemachine= bundle.getString("marquemachine");
		m_seriemachine= bundle.getString("seriemachine");
	
		
		setContentView(R.layout.activity_interventionautre);
		mContext=this;
		initActionBar();

		InitTextView();
		setListeners();
		initListeners();

		Loadinformation();
		
		Spinner etMarque = (Spinner) this.findViewById(R.id.spinnerMarque);
		etMarque.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {

				String m_code = "";
				try {
				
					m_code = idMarque.get(position).getString(
							Global.dbParam.FLD_PARAM_CODEREC);
					fillTypeMachine(m_code,stvarCodeTypeMachine);
					m_code_symp="";
					m_lib_symp="";
					TexteSymptome.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
					

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		Spinner ettypeMachine = (Spinner) this.findViewById(R.id.spinnerTypemachine);
		ettypeMachine.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {

				String m_code = "";
				try {
				
					m_code = idTypeMachine.get(position).getString(
							Global.dbParam.FLD_PARAM_CODEREC);
					m_code_symp="";
					m_lib_symp="";
					TexteSymptome.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
					
					
					
					
					

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		Spinner etQui = (Spinner) this.findViewById(R.id.spinnerQui);
		etQui.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {

				String m_code = "";
				try {
				
					m_code = idQui.get(position).getString(
							Global.dbParam.FLD_PARAM_CODEREC);
					/*if(Fonctions.GetStringDanem(m_code).equals("1"))
					{
						TexteSymptome.setVisibility(View.VISIBLE);
						spinnerSymptomes.setVisibility(View.GONE);
						butSymptome.setVisibility(View.VISIBLE);
					}
					else
					{
						TexteSymptome.setVisibility(View.GONE);
						spinnerSymptomes.setVisibility(View.GONE);
						butSymptome.setVisibility(View.GONE);
						 m_code_symp="";
						 m_lib_symp="";
						 TexteSymptome.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
						
					}*/
					TexteSymptome.setVisibility(View.GONE);
					spinnerSymptomes.setVisibility(View.GONE);
					butSymptome.setVisibility(View.GONE);
					
					
					
					
					

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		

	}

	
	void setListeners()
	{


		butSymptome.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		
		if (v == butSymptome) {
			
			
			
			if(!getTypeMachine().equals(""))
				launchSymptomes(Global.Autres,Global.Autres,Global.Autres,m_codeclient,Fonctions.GetStringDanem(m_code_symp),getTypeMachine(),"1",m_numcde,"0");
			
			
			
			
		}
	}
	void Loadinformation() {

		structLinCde vu = new structLinCde();
		StringBuffer stBuf = new StringBuffer();
	
		if(!Fonctions.GetStringDanem(m_qui).equals(""))
		{
			setEditViewText(this, R.id.EditCommentaire, m_seriemachine);

			fillQui(m_qui); 
			fillMarque(m_marque);
			stvarCodeMarque=Fonctions.GetStringDanem(m_marque);
			
			fillTypeMachine(stvarCodeMarque,m_typemachine);
			stvarCodeTypeMachine=Fonctions.GetStringDanem(m_typemachine);
				
			if(m_code_symp.equals(""))
			{
				 TexteSymptome.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
						
			}
			else
			{
				 TexteSymptome.setBackgroundColor(mContext.getResources().getColor(R.color.greenPanier));
					
					
				
			}
			
			
			if(Fonctions.GetStringDanem(m_qui).equals("1"))
			{
				TexteSymptome.setVisibility(View.GONE);
				spinnerSymptomes.setVisibility(View.GONE);
				
				butSymptome.setVisibility(View.GONE);
			}
			else
			{
				TexteSymptome.setVisibility(View.GONE);
				spinnerSymptomes.setVisibility(View.GONE);
				butSymptome.setVisibility(View.GONE);
				
			}
			

			
		}
		else
		{
			setEditViewText(this, R.id.EditCommentaire, "");

			fillQui("");
			fillMarque("");
			m_code_symp="";
			
		}
		
				
	}

	void showProgressDialog() {
		m_ProgressDialog = ProgressDialog.show(this, "Veuillez patienter",
				"traitement en cours", true);
	}

	public void hideProgressDialog() {
		if (m_ProgressDialog != null)
			m_ProgressDialog.dismiss();
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(R.string.rapport_titre);
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		addMenu(menu, R.string.supprimer,
				android.R.drawable.ic_menu_close_clear_cancel);
		addMenu(menu, android.R.string.ok, android.R.drawable.ic_menu_save);
	
		return true;
	}

	private void record(ArrayList<String> ValueMessage, StringBuffer buff) {

		if (ControleAvantSauvegarde(ValueMessage, buff) == true) {
//			if (1 == 1) {
				/*m_stvaleurFin=getJourFermeture();
				if(!Fonctions.GetStringDanem(m_stvaleurDebut).equals(m_stvaleurFin))
				{
					Global.dbClient.saveModifJourFermeture(m_codeclient,"M",m_stvaleurFin,Fonctions.getYYYYMMDD(), buff);
				}*/
			
				if (save(false, buff) == true) {
					setResult(Activity.RESULT_OK);
					finish();
				} else {
					Fonctions.FurtiveMessageBox(this, buff.toString());
				}
//			}

		} else
			Fonctions.FurtiveMessageBox(this,
					Fonctions.GetStringDanem(ValueMessage.get(0)));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		StringBuffer buff = new StringBuffer();
		ArrayList<String> ValueMessage = new ArrayList();
		switch (item.getItemId()) {
		case R.string.prospect_positionner:

			return true;
		case R.string.supprimer:
			StringBuffer err=new StringBuffer();
			Global.dbKDLinCde.deleteArticleIntervention(m_numcde, Global.Autres,  err);
			
			Cancel();
			
			return true;

		case android.R.string.ok:
			
			String codeart=Fonctions.GetStringDanem(m_codearticle);
			String lblart=Fonctions.GetStringDanem(m_libarticle);
			String numserie=Global.Autres;
			String code_symp=Fonctions.GetStringDanem(m_code_symp);
			String lib_symp=Fonctions.GetStringDanem(m_lib_symp);
		
			String stQui=getQui();
			String stMarque=getMarque();	
			String typemachine=getTypeMachine();
			String stMarqueMachine=getMarque();//getMarqueMachine();	
			String stcommentaire=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditCommentaire));
			String noserie=stcommentaire;
			
			 Intent result = new Intent();
			 Bundle b = new Bundle();
			 b.putString("codeclient", m_codeclient);
			 b.putString("codearticle", codeart );
			 b.putString("libarticle", lblart);
			 b.putString("numserie", numserie);
			 b.putString("codesymp", code_symp);
			 b.putString("libsymp",lib_symp );
			 
			 b.putString("qui",stQui );
			 b.putString("marque",stMarque );
			 b.putString("typemachine",typemachine );
			 b.putString("marquemachine",stMarqueMachine );
			 b.putString("seriemachine",noserie );
			 
			 result.putExtras(b);
			 setResult(Activity.RESULT_OK, result);

			 finish();
			
			
			return true;

		
		default:
			return super.onOptionsItemSelected(item);
		}
		//return super.onOptionsItemSelected(item);
	}

	void Cancel() {

		setResult(Activity.RESULT_CANCELED);
		

		this.finish(0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == LAUNCH_SAISIEQTE) {
			if ( resultCode == Activity.RESULT_OK ) {

				Bundle b = data.getExtras();
				
				 m_code_symp=getBundleValue(b, "codesymp");
				 m_lib_symp=getBundleValue(b, "libsymp");
				 TexteSymptome.setBackgroundColor(mContext.getResources().getColor(R.color.greenPanier));
					
					
				
				
			}
			if (resultCode == Activity.RESULT_CANCELED) {

				 m_code_symp="";
				 m_lib_symp="";
				 TexteSymptome.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
					
				
			}	
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			Cancel();

			return false;
		}

		else
			return super.onKeyDown(keyCode, event);
	}

	String getQui() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerQui);
		if (pos > -1)
			try {
				pays = idQui.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	String getMarque() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerMarque);
		if (pos > -1)
			try {
				pays = idMarque.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	String getMarqueMachine() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerMarque);
		if (pos > -1)
			try {
				pays = idMarque.get(pos).getString(
						Global.dbParam.FLD_PARAM_COMMENT);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	
	String getTypeMachine() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerTypemachine);
		if (pos > -1)
			try {
				pays = idTypeMachine.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}
	
	
	String getJourFermeture() {
		String pays = "";
		int pos = this.getSpinnerSelectedIdx(this, R.id.spinnerJourfermeture);
		if (pos > -1)
			try {
				pays = idJourFermeture.get(pos).getString(
						Global.dbParam.FLD_PARAM_CODEREC);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return pays;
	}


	void finish(int i) {
		SynchroService.setPaused(false);
		finish();
	}

	/**
	 * Creation du menu
	 */
	static final private int MENU_ITEM = Menu.FIRST;

	public boolean save(boolean temporary, StringBuffer stbuff) {
		try {

			
			String rep=Preferences.getValue(this, Espresso.LOGIN, "0");
			String m_soccode=getSocCode();
			String m_codecli=m_codeclient;
			
			String codeart=m_codearticle;
			String lblart=m_libarticle;
			String numserie=Global.Autres;
			String typedoc=TableSouches.TYPEDOC_INTERVENTION;
		
			String code_symp=m_code_symp;
			String lib_symp=m_lib_symp;
		
			String typemachine=getTypeMachine();
			String stcommentaire=Fonctions.ReplaceGuillemet(this.getEditViewText(this,R.id.EditCommentaire));
			
			String noserie=stcommentaire;
			String stQui=getQui();
			String stMarque=getMarque();	
			String stMarqueMachine=getMarque();//getMarqueMachine();	
			
			
			
			String jourferm=getJourFermeture();
			jourferm="";
			
			
			saveLin( rep, m_soccode, m_codecli,
					 m_numcde, codeart, lblart , numserie, code_symp, lib_symp,
					noserie,stQui,stMarque,stMarqueMachine,typemachine,jourferm, typedoc, true);
			
			
			structClientvu vu = new structClientvu();
			
			

		

			Fonctions.FurtiveMessageBox(this,
					getString(R.string.save_successfull));

			return true;

		} catch (Exception ex) {
			return false;

		}

	}

	public boolean ControleAvantSauvegarde(ArrayList<String> ValueMessage,
			StringBuffer stBuf) {
		boolean bres = true;

		String stmessage = "";

		try {

			if (getQui().equals("")) {

				stmessage = " Veuillez renseigné à qui appartient la machine.";
				ValueMessage.add(stmessage);
				return false;
			}
			
			if (getMarque().equals("")) {

				stmessage = " Veuillez renseigné la Marque.";
				ValueMessage.add(stmessage);
				return false;
			}
			if (getTypeMachine().equals("")) {

				stmessage = " Veuillez renseigné le type de Machine.";
				ValueMessage.add(stmessage);
				return false;
			}
			/*if (getQui().equals("1") && Fonctions.GetStringDanem(m_code_symp).equals("") ) {
				

				stmessage = " Veuillez renseigné le symptôme.";
				ValueMessage.add(stmessage);
				return false;
			}*/

			if(Fonctions.GetStringDanem(EditCommentaire.getText().toString()).equals(""))
			{
				if (getQui().equals("1")) {
					
					stmessage = " Veuillez saisir le N° de série.";
					ValueMessage.add(stmessage);
					return false;
				}
				//DINS - DREP - DECH
				/*structlistLogin rep = null;
				dbSiteListeLogin login=new dbSiteListeLogin(m_appState.m_db);
				rep=new structlistLogin();
				boolean btech=false;
				login.getlogin(Preferences.getValue(this, Espresso.LOGIN, "0"), rep, new StringBuilder());
				if( Fonctions.GetStringDanem(rep.TYPE).equals(Global.Technicien) )
				{
					btech=true;
				}
				if(btech==true && ( Fonctions.GetStringDanem(m_typ_inter).equals("DINS")  || Fonctions.GetStringDanem(m_typ_inter).equals("DREP")  || Fonctions.GetStringDanem(m_typ_inter).equals("DECH") ) )
				{
					
					stmessage = " Veuillez saisir le N° de série.";
					ValueMessage.add(stmessage);
					return false;
				}*/
			}
			
			

		} catch (Exception ex) {
			stBuf.setLength(0);
			stBuf.append(ex.getMessage());
			return false;

		}
		return bres;

	}

	void fillQui(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_APPARTIENT,
					this.idQui, true) == true) {

				int pos = 0;
				String[] items = new String[idQui.size()];
				for (int i = 0; i < idQui.size(); i++) {

					items[i] = idQui.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idQui.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerQui);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	
	void fillMarque(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_MARQUEUNIQUE,
					this.idMarque, true) == true) {

				int pos = 0;
				String[] items = new String[idMarque.size()];
				for (int i = 0; i < idMarque.size(); i++) {

					items[i] =idMarque.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idMarque.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerMarque);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	
	void fillTypeMachine(String marque,String selVal) {
		try {
		
						
			if (Global.dbParam.getRecord2sTypeMachine(Global.dbParam.PARAM_TYPEMACHINE,marque,
					this.idTypeMachine, true) == true) {

				int pos = 0;
				String[] items = new String[idTypeMachine.size()];
				for (int i = 0; i < idTypeMachine.size(); i++) {

					items[i] = idTypeMachine.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idTypeMachine.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerTypemachine);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	
	
	
	void fillJourFermeture(String selVal) {
		try {
		
			if (Global.dbParam.getRecord2s(Global.dbParam.PARAM_JOURFERMETURE,
					this.idJourFermeture, true) == true) {

				int pos = 0;
				String[] items = new String[idJourFermeture.size()];
				for (int i = 0; i < idJourFermeture.size(); i++) {

					items[i] = idJourFermeture.get(i).getString(
							Global.dbParam.FLD_PARAM_LBL);

					String codeRec = idJourFermeture.get(i).getString(
							Global.dbParam.FLD_PARAM_CODEREC);

					if (selVal.equals(codeRec)) {
						pos = i;
					}

				}

				Spinner spinner = (Spinner) findViewById(R.id.spinnerJourfermeture);

				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//		android.R.layout.simple_spinner_item, items);
				ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text, items);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);

				spinner.setSelection(pos);

			}

		} catch (Exception ex) {

		}

	}
	
	
	
	

	/**
	 * renvoi l'index sï¿½lectionnï¿½ dans un spinner
	 * 
	 * @param et
	 * @return
	 */
	public int getSpinnerSelectedIdx(Spinner et) {
		try {
			int pos = et.getSelectedItemPosition();
			return pos;

		} catch (Exception ex) {

		}
		return -1;
	}

	void InitTextView() {

		

		// Commentaire
		TextViewCommentaire = (TextView) findViewById(R.id.TexteCommentaire);
		EditCommentaire = (EditText) this.findViewById(R.id.EditCommentaire);
		 spinnerSymptomes = (Spinner) findViewById(R.id.spinnerSymptome);
		 TexteSymptome = (TextView) findViewById(R.id.TexteSymptome);
		 
		 butSymptome = (Button) findViewById(R.id.butSymptome);
		 
			 
		 spinnerSymptomes.setVisibility(View.GONE);
		 butSymptome.setVisibility(View.GONE);
		 TexteSymptome.setVisibility(View.GONE);

		
		InputFilter[] FilterArrayCmt = new InputFilter[1];

		FilterArrayCmt[0] = new InputFilter.LengthFilter(
				m_ilongueurComt);
		EditCommentaire.setFilters(FilterArrayCmt);
		
		

	}
	void initListeners()
	{
			
	
		
		
		
	}
	private void UI_updateProgressBar() {

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		switch (parent.getId()) {

		}
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		// m_stFam = idFam.get(pos).getString(
		// Global.dbParam.FLD_PARAM_CODEREC);
		// PopulateList() ;
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}


	public static void launchAgenda(Activity a, String m_codeclient)
	{
		 StringBuilder err = new StringBuilder();
			structClient client = new structClient();
			
			Bundle b = new Bundle();
			b.putString(Agendanem.ApplicationName, "folliet");
			b.putString(Agendanem.ApplicationKey, "FLT");
			
			if (Global.dbClient.getClient(m_codeclient, client, err))
			{	 
			
				b.putString(Agendanem.CodeClient, client.CODE);
				b.putString(Agendanem.AdresseClient, client.getFullAddress());
				b.putString(Agendanem.NameClient, client.NOM);
				Intent intent = new Intent(a,
						AgendaViewActivity.class);
				intent.putExtras(b);
				a.startActivityForResult(intent, LAUNCH_AGENDA);
			}
			else
			{
				Intent intent = new Intent(a,
						AgendaListActivity.class);
				intent.putExtras(b);
				a.startActivityForResult(intent, LAUNCH_AGENDA);
			}
	}
	
	public static void saveLin(String rep,String m_soccode,String m_codecli,
			String m_numcde,String codeart,String lblart ,String numserie,String code_symp,String lib_symp,
			String saisienumserie,String Qui,String Marque,String MarqueMachine,String typemachine,String jourferm,String typedoc,boolean bcoche){
		dbKD84LinCde.structLinCde lin = new dbKD84LinCde.structLinCde();


		lin.TAXE1 = 0.0f;
		lin.TAXE2 = 0.0f;
		lin.TAXE3 = 0.0f;

		lin.SOCCODE=m_soccode;
		lin.CLICODE=m_codecli;
		lin.CDECODE = m_numcde;

		lin.DESIGNATION = lblart;
		lin.PROCODE = numserie;
		lin.PRIXMODIF = Fonctions.convertToFloat( "");
		if(bcoche==true)
		{
			lin.QTECDE = Fonctions.convertToFloat("1");

		}
		else
			lin.QTECDE = Fonctions.convertToFloat("0");

		lin.QTEGR = Fonctions.convertToFloat("");
		lin.REMISEMODIF=Fonctions.convertToFloat("");
		lin.DATE=Fonctions.getYYYYMMDDhhmmss();
		lin.MNTUNITHT = Fonctions.GetStringToFloatDanem("");
		lin.TYPEPIECE=typedoc;
		lin.COMMENT1=numserie;
		lin.COMMENT2=codeart;
		if(Fonctions.GetStringDanem(Qui).equals("1"))
		{
			lin.COMMENT3=code_symp;//Autres: code symptomes 
			lin.LOT=lib_symp;// Autres : lib symp
		
		}
		else
		{
			lin.COMMENT3="";//Autres: code symptomes 
			lin.LOT="";// Autres : lib symp
		
		}
			
		
		lin.UV=typemachine;	//Autres: type machine
		lin.CODETVA=saisienumserie;	//Autres : N° de série
		lin.TYPECHECKBOX=Qui;	//Autres ; Qui
		lin.FIELD_LIGNECDE_LINCHOIX1=Marque;	//Autres : Marque
		lin.TYPECDEVALEUR=jourferm;	//Autres : Jour ferm
		lin.CODEPANACHAGE=MarqueMachine ;//Autres : Marque
		
		
		
		lin.REPCODE =rep;
		lin.TAUX =       Fonctions.GetStringToFloatDanem("");
		lin.PRIXINITIAL =  "";;
		lin.MNTUNITNETHT = Fonctions.GetStringToFloatDanem("");
		lin.MNTTOTALHT = Fonctions.GetStringToFloatDanem("");
		lin.MNTTVA=Fonctions.GetStringToFloatDanem("");

		lin.MNTTOTALTTC= Fonctions.GetStringToFloatDanem("") ;
		if (lin.QTECDE == 0 ) {
			Global.dbKDLinCde.delete(m_numcde, lin.PROCODE, "0",
					new StringBuffer(), "");
		} else {
			Global.dbKDLinCde.save(lin, m_numcde, lin.PROCODE,
					"0", new StringBuffer()); 
		}

	}


}
