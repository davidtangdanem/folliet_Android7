package com.menadinteractive.negos.quest;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.dbKD103Questionnaire;
import com.menadinteractive.segafredo.db.dbKD83EntCde;
import com.menadinteractive.segafredo.db.dbKD91EntPlan;
import com.menadinteractive.segafredo.db.dbKD92LinPlan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class pac_plans_filler2 extends BaseActivity{

	LinearLayout relativeLayout;
	LinearLayout ll;
	ArrayList<dbKD91EntPlan.data> entPlans;
	ArrayList<dbKD92LinPlan.data>  tabVal ;//on parcourera ce tableau � la fin qui liera le controle � la donn�e
	
	String date;
	String codecli;
	String codequestionnaire;
	String m_numrapport;
	
	createQuestForm formQuest;
	

	Button buttonSend,buttonKeep,buttonDelete;
	
	Questionnaire questionnaire;
	
	public pac_plans_filler2() {

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
	/*	case android.R.id.home:
			finish();
			return true;
*/
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onStart() {
		super.onStart();
//		setBackground(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pac_plans_filler);
		relativeLayout = (LinearLayout) 
				findViewById(R.id.rl_top);
	
		
		Bundle bundle = this.getIntent().getExtras();
		codecli= this.getBundleValue(bundle, "codecli");
		date= this.getBundleValue(bundle, "date");
		codequestionnaire= this.getBundleValue(bundle, "codequestionnaire");
		m_numrapport= this.getBundleValue(bundle, "numrapport");
		
	//V�roull� l'ecran
		setFinishOnTouchOutside(false);
		
		//formQuest=new createQuestForm(this,relativeLayout,codecli,datekey,enseigne,codesoc);

		questionnaire = new Questionnaire(this,codecli,date, codequestionnaire,m_numrapport);
		relativeLayout.addView(questionnaire.getLinearLayoutOfQuestionnaire());
		
		//ArrayList<Reponse> reponses = question.getReponses();
		
		
		initGUI();
		initListeners();
	}
	
	
	
	void initGUI()
	{
		buttonKeep=(Button) findViewById(R.id.buttonKeep);
		buttonSend=(Button) findViewById(R.id.buttonSend);
		buttonDelete=(Button) findViewById(R.id.buttonDelete);
	
	}
	
	void initListeners()
	{
		buttonKeep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	save(0);
				end();
				
			}
		});
		
		buttonDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageYesNoValider("Voulez-vous supprimer le questionnaire ?");
				
				
			}
		});
		
		
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<Reponse> reponses = questionnaire.getReponses();
				//Si liste vide pas de sauvegarde 
				
				boolean bres=false;
			
				
				//Save
				dbKD103Questionnaire.structQuestionnaireKD ent;

				try {
					for(Reponse reponse : reponses){
						String sss="";
						sss=reponse.getObligatoire();
						if(Fonctions.GetStringDanem(reponse.getObligatoire()).trim().equals("O"))
						{
							if(reponse.getLibelleReponse().trim().equals(""))
							{
								bres=true;
								break;
										
							}
								
							
						}
						
						
						ent = new dbKD103Questionnaire.structQuestionnaireKD();
						ent.SOC_CODE =getSocCode();
						ent.CODECLI =codecli;
						ent.CODEREP =getLogin();
						ent.DATE=date;
						ent.CODE_QUESTIONNAIRE=reponse.getCodeQuestionnaire();
						ent.CODE_QUESTION=reponse.codeQuestion;
						ent.REPONSE=reponse.getLibelleReponse();
						ent.TYPEQUESTION="";
						ent.NUMRAPPORT=m_numrapport;
						
						if (!Global.dbKDQuestionnaire.save2_rapport(ent)) {
							//Fonctions.FurtiveMessageBox(this,"Erreur");
							return ;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if(bres==true)
				{
					None();
					
				}else
				end();			
			}
		});
	}
	
	void end()
	{
		finish();
	}
	void None()
	{
		Fonctions.FurtiveMessageBox(this, "Question obligatoire");
	}
	/**
	 * on suve en quittant la fenetre
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {

	//		boolean bres = save();
	//		this.finish();
			return false;
		}	
		else
			return super.onKeyDown(keyCode, event);

	}
	
	public void MessageYesNoValider(String message) {
		 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(getString(android.R.string.yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								//launchPrinting();
								Global.dbKDQuestionnaire.deleteQuestionnaire_rapport(codecli, "1", date, codequestionnaire,m_numrapport);
								end();
								

							}
						})
				.setNegativeButton(getString(android.R.string.no),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	

	
	//controel que les todo sont remplies
	boolean controlIfTodosAreOk()
	{
		try {
	
			for (int i=0;i<tabVal.size();i++)
			{

						
				String value="ok";
				Object obj=tabVal.get(i).object; 

				if (tabVal.get(i).TYPE.equals(dbKD92LinPlan.type_int))
				{
					value=formQuest.getEditViewText(((EditText)obj));
				}
				else
					if (tabVal.get(i).TYPE.equals(dbKD92LinPlan.type_float))
					{
						value=formQuest.getEditViewText(((EditText)obj));
					}
					else
						if (tabVal.get(i).TYPE.equals(dbKD92LinPlan.type_text))
						{
							value=formQuest.getEditViewText(((EditText)obj));
						}
				if (value.equals(""))
				{
					((EditText)obj).requestFocus();
					return false;
				}
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}


}
