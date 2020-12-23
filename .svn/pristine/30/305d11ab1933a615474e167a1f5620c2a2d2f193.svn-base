package com.menadinteractive.negos.quest;

import java.util.ArrayList;

import com.menadinteractive.segafredo.communs.Global;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Questionnaire {
	String codeQuestionnaire;
	ArrayList<Question> questions;
	LinearLayout layoutQuestionnaire;
	String numrapport;
	
	
	Context mContext;
	
	public String getCodeQuestionnaire(){return codeQuestionnaire;}
	public void setCodeQuestionnaire(String _codeQuestionnaire){this.codeQuestionnaire = _codeQuestionnaire;}
	
	public ArrayList<Question> getQuestions(){return questions;}
	public void setQuestions(ArrayList<Question> _list){this.questions = _list;}
	
	public LinearLayout getLinearLayoutOfQuestionnaire(){ return layoutQuestionnaire;}
	
	public String getNumrapport(){return numrapport;}
	public void setNumrapport(String _numrapport){this.numrapport = _numrapport;}
	
	
	public Questionnaire(Context context){
		this.mContext = context;
	}
	
	public Questionnaire(Context context,String _codecli,String _date, String _codeQuestionnaire,String _numrapport){
		this.mContext = context;
		this.codeQuestionnaire = _codeQuestionnaire;
		this.numrapport = _numrapport;
		this.questions = getAllQuestionFromCodeQuestionnaire(_codeQuestionnaire);
		this.layoutQuestionnaire = getLinearLayoutQuestionnaireFromCodeQuestionnaire(_codecli,_date,_codeQuestionnaire,_numrapport);
	}
	
	/**
	 * Get all question of questionnaire from code questionnaire
	 * @param codeQuestionnaire
	 * @return
	 */
	private ArrayList<Question> getAllQuestionFromCodeQuestionnaire(String codeQuestionnaire){
		this.questions = Global.dbQuestionnaire.getQuestionnaire(codeQuestionnaire, mContext);
		return this.questions;
	}
	
	/**
	 * Get all views in a linear layout for a questionnaire from code questionnaire
	 * @param codeQuestionnaire
	 * @return LinearLayout
	 */
	/*private LinearLayout getLinearLayoutQuestionnaireFromCodeQuestionnaire(String codeQuestionnaire){
		LinearLayout parent = new LinearLayout(mContext);
		parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		parent.setOrientation(LinearLayout.VERTICAL);
		for(Question question : this.questions){
			LinearLayout layout = question.createViewFromQuestion();
			parent.addView(layout);
			question.setViewOfQuestion(layout);
		}
		return parent;
	}*/
	private LinearLayout getLinearLayoutQuestionnaireFromCodeQuestionnaire(String codecli,String date,String codeQuestionnaire,String numrapport){
	LinearLayout parent = new LinearLayout(mContext);
	parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	parent.setOrientation(LinearLayout.VERTICAL);
	for(Question question : this.questions){
		LinearLayout layout = question.createViewFromQuestion( codecli, date,numrapport);
		parent.addView(layout);
		question.setViewOfQuestion(layout);
	}
	return parent;
   }
	
	public ArrayList<Reponse> getReponses(){
		ArrayList<Reponse> reponses = new ArrayList<Reponse>();
		Reponse reponse ;
		for(Question question : this.questions){
			reponse = new Reponse();
			reponse = question.getReponse();
			
			reponses.add(reponse);
		}
		return reponses;
	}
	
	
}
