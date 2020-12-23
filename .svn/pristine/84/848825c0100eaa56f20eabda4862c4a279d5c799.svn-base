package com.menadinteractive.negos.quest;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.DatePickerFragment;
import com.menadinteractive.segafredo.communs.DatePickerFragment.OnDateChange;
import com.menadinteractive.segafredo.communs.Global;

public class Question {
	String questionLibelle;
	String codeTypeReponse;
	String codeFormatReponse;
	String codeQuestionnaire;
	String codeQuestion;
	String libelleQuestionnaire;
	String obligatoire;
	String rang;
	
	String[] listReponse;
	
	Reponse reponse;
	LinearLayout view;
	
	Context mContext;
	DatePickerFragment dateFragment;
	
	public String getQuestionLibelle(){return questionLibelle;}
	public void setQuestionLibelle(String _questionLibelle){this.questionLibelle = _questionLibelle;}
	
	public String getCodeTypeReponse(){return codeTypeReponse;}
	public void setCodeTypeReponse(String _codeTypeReponse){this.codeTypeReponse = _codeTypeReponse;}
	
	public String getCodeFormatReponse(){return codeFormatReponse;}
	public void setCodeFormatReponse(String _codeFormatReponse){this.codeFormatReponse = _codeFormatReponse;}
	
	public String getCodeQuestionnaire(){return codeQuestionnaire;}
	public void setCodeQuestionnaire(String _codeQuestionnaire){this.codeQuestionnaire = _codeQuestionnaire;}
	
	public String getCodeQuestion(){return codeQuestion;}
	public void setCodeQuestion(String _codeQuestion){this.codeQuestion = _codeQuestion;}
	
	public String getLibelleQuestionnaire(){return libelleQuestionnaire;}
	public void setLibelleQuestionnaire(String _libelleQuestionnaire){this.libelleQuestionnaire = _libelleQuestionnaire;}
	
	public String getObligatoire(){return obligatoire;}
	public void setObligatoire(String _obligatoire){this.obligatoire = _obligatoire;}
	
	
	public String getRang(){return rang;}
	public void setRang(String _rang){this.rang = _rang;}
	
	public String[] getReponseListe(){return listReponse;}
	public void setReponseList(String _list){
		if(_list == null || _list.equals("") ){
			//
			listReponse = null;
			return;
		}
		
		listReponse = _list.split(";");
		
	}
	
	//une seule reponse liée
	public Reponse getReponse(){
		Reponse reponse = new Reponse();
		reponse.setCodeQuestion(this.getCodeQuestion());
		reponse.setCodeQuestionnaire(this.getCodeQuestionnaire());
		reponse.setObligatoire(this.getObligatoire());
		
		//reponse.setCodeTypeReponse(this.getCodeTypeReponse());
		
		String libelleReponse = "";
		LinearLayout layout = this.getViewOfQuestion();
		int typeReponse=0;
		int formatReponse=0;
		
		try{
			typeReponse = Integer.parseInt(this.getCodeTypeReponse());
			formatReponse = Integer.parseInt(this.getCodeFormatReponse());
		}catch (NumberFormatException ex){
			
		}
		
		if(typeReponse == 1){
			//on fait un spinner
			Spinner spinner = new Spinner(mContext);
			int childcount = layout.getChildCount();
			for (int i=0; i < childcount; i++){
			      View v = layout.getChildAt(i);
			      if(v instanceof Spinner){
			    	  spinner = (Spinner) v;
			    	  libelleReponse = spinner.getSelectedItem().toString();
			      }
			}
			
		}else{
			if(formatReponse == 1){
				//Edit alpha
				EditText editAlpha = new EditText(mContext);
				int childcount = layout.getChildCount();
				for (int i=0; i < childcount; i++){
				      View v = layout.getChildAt(i);
				      if(v instanceof EditText){
				    	  editAlpha = (EditText) v;
				    	  if(editAlpha.getText() != null){
				    		  libelleReponse = editAlpha.getText().toString();
				    	  }  
				      }
				}
			}else if(formatReponse == 2){
				//Edit numérique
				EditText editNum = new EditText(mContext);
				int childcount = layout.getChildCount();
				for (int i=0; i < childcount; i++){
				      View v = layout.getChildAt(i);
				      if(v instanceof EditText){
				    	  editNum = (EditText) v;
				    	  if(editNum.getText() != null){
				    		  libelleReponse = editNum.getText().toString();
				    	  }  
				      }
				}
			}else if(formatReponse == 3){
				//Bouton datepicker
				Button bDate = new Button(mContext);
				int childcount = layout.getChildCount();
				for (int i=0; i < childcount; i++){
				      View v = layout.getChildAt(i);
				      if(v instanceof Button){
				    	  bDate = (Button) v;
				    	  if(bDate.getText() != null){
				    		  libelleReponse = bDate.getText().toString();
				    	  }  
				      }
				}
			
			}
		}
		reponse.setLibelleReponse(libelleReponse);
		return reponse;
	}
	
	public LinearLayout getViewOfQuestion(){ return view;}
	public void setViewOfQuestion(LinearLayout layout) {this.view = layout;}
	
	public Question(Context context) {
		mContext = context;
	}
	/*
	LinearLayout createViewFromQuestion(){
		LinearLayout parent = new LinearLayout(mContext);

		parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		parent.setOrientation(LinearLayout.VERTICAL);
		parent.setPadding(15, 10, 15, 5);
		
		TextView tvQuestion = new TextView(mContext);
		tvQuestion.setText(this.getQuestionLibelle());
		parent.addView(tvQuestion);
		
		//Si type == 1 spinner
		//type == 3 voir format
		//format == 1 -> alphanumérique
		//format == 2 -> numérique
		//format == 3 -> date
		
		int typeReponse=0;
		int formatReponse=0;
		
		try{
			typeReponse = Integer.parseInt(this.getCodeTypeReponse());
			formatReponse = Integer.parseInt(this.getCodeFormatReponse());
		}catch (NumberFormatException ex){
			
		}
		
		if(typeReponse == 1){
			//on fait un spinner
			Spinner spinner = new Spinner(mContext);
			ArrayAdapter<String> spinnerArrayAdapter = 
					new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, this.getReponseListe());
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinnerArrayAdapter);
			parent.addView(spinner);
		}else{
			if(formatReponse == 1){
				//Edit alpha
				EditText editAlpha = new EditText(mContext);
				editAlpha.setTag(this.getCodeQuestionnaire()+this.getCodeQuestion());
				editAlpha.setInputType(InputType.TYPE_CLASS_TEXT);
				parent.addView(editAlpha);
			}else if(formatReponse == 2){
				//Edit numérique
				EditText editNum = new EditText(mContext);
				editNum.setTag(this.getCodeQuestionnaire()+this.getCodeQuestion());
				editNum.setInputType(InputType.TYPE_CLASS_NUMBER);
				parent.addView(editNum);
			}else if(formatReponse == 3){
				//Bouton datepicker
				final Button bDate = new Button(mContext);
				bDate.setTag(this.getCodeQuestionnaire()+this.getCodeQuestion());
				bDate.setText(mContext.getResources().getString(R.string.questionnaire_choixdate));
				bDate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						showDatePickerDialog(v, bDate);
					}
				});
				parent.addView(bDate);
			}
		}
		
		return parent;
	}*/
	
	LinearLayout createViewFromQuestion(String codecli,String date ,String numrapport){
		LinearLayout parent = new LinearLayout(mContext);

		parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		parent.setOrientation(LinearLayout.VERTICAL);
		parent.setPadding(15, 10, 15, 5);
		
		TextView tvQuestion = new TextView(mContext);
		tvQuestion.setText(this.getQuestionLibelle());
		parent.addView(tvQuestion);
		
		//Si type == 1 spinner
		//type == 3 voir format
		//format == 1 -> alphanumérique
		//format == 2 -> numérique
		//format == 3 -> date
		
		int typeReponse=0;
		int formatReponse=0;
		
		try{
			typeReponse = Integer.parseInt(this.getCodeTypeReponse());
			formatReponse = Integer.parseInt(this.getCodeFormatReponse());
		}catch (NumberFormatException ex){
			
		}
		
		if(typeReponse == 1){
			//on fait un spinner
			Spinner spinner = new Spinner(mContext);
			ArrayAdapter<String> spinnerArrayAdapter = 
					new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, this.getReponseListe());
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinnerArrayAdapter);
			parent.addView(spinner);
			
			if(!Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport).equals(""))
			{
				int cpt = 0;
				for(String value : this.getReponseListe()){
					if(Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport).equals(value)){
						break;
					}
					cpt++;
				}
				spinner.setSelection(cpt);
			}
			
			 
		}else{
			if(formatReponse == 1){
				//Edit alpha
				EditText editAlpha = new EditText(mContext);
				editAlpha.setTag(this.getCodeQuestionnaire()+this.getCodeQuestion());
				editAlpha.setInputType(InputType.TYPE_CLASS_TEXT);
				parent.addView(editAlpha);
				if(!Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport).equals(""))
				{
					editAlpha.setText(Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport));
				}
				
				
			}else if(formatReponse == 2){
				//Edit numérique
				EditText editNum = new EditText(mContext);
				editNum.setTag(this.getCodeQuestionnaire()+this.getCodeQuestion());
				editNum.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
				parent.addView(editNum);
				if(!Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport).equals(""))
				{
					editNum.setText(Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport));
				}
			}else if(formatReponse == 3){
				//Bouton datepicker
				final Button bDate = new Button(mContext);
				bDate.setTag(this.getCodeQuestionnaire()+this.getCodeQuestion());
				bDate.setText(mContext.getResources().getString(R.string.questionnaire_choixdate));
				if(!Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport).equals(""))
				{
					bDate.setText(Global.dbKDQuestionnaire.Reponse_rapport(codecli,date,this.getCodeQuestionnaire(),this.getCodeQuestion(),numrapport));
				}
				bDate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						showDatePickerDialog(v, bDate);
					}
				});
				parent.addView(bDate);
			}
		}
		
		return parent;
	}
	
	
	
	public void showDatePickerDialog(View v, final Button b_date) {
		final Calendar c = Calendar.getInstance();
		dateFragment = new DatePickerFragment(b_date, c, new OnDateChange() {

			@Override
			public void onDateSet(int year, int month, int day) {
				//if(c == null) c = Calendar.getInstance();
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month);
				c.set(Calendar.DAY_OF_MONTH, day);
				String Day = "";
				showDate(b_date, year, month+1, day);
				String Month = "";
				String Year = Integer.toString(year);

				//on met la date au bon format
				if(month+1 < 10) Month = "0"+Integer.toString(month+1);
				else Month = Integer.toString(month+1);

				if(day < 10) Day = "0"+Integer.toString(day);
				else Day = Integer.toString(day);

				//currentdate = Year+Month+Day;

			}
		});
		final Activity activity = (Activity) mContext;
		dateFragment.show(activity.getFragmentManager(), "datePicker");
	}

	private void showDate(Button b_date, int year, int month, int day) {
		//on met les valeurs sous le bon format
		String yearS = Integer.toString(year);
		String monthS = Integer.toString(month);
		String dayS = Integer.toString(day);

		if(month < 10) monthS = "0"+month;
		if(day<10) dayS = "0"+day;

		b_date.setText(new StringBuilder().append(dayS).append("/")
				.append(monthS).append("/").append(yearS));

	}
	
}
