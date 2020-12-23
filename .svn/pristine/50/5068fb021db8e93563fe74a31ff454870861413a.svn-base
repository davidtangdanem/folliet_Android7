package com.menadinteractive.livraison;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.communs.Fonctions;
import com.menadinteractive.segafredo.communs.Global;
import com.menadinteractive.segafredo.db.Preferences;
import com.menadinteractive.segafredo.db.TableLivraison;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire;
import com.menadinteractive.segafredo.db.dbKD543LinInventaire.structPassePlat;
import com.menadinteractive.segafredo.db.dbTournee;
import com.menadinteractive.segafredo.plugins.Espresso;
import com.menadinteractive.segafredo.tournee.Tournee;

import java.util.ArrayList;

public class SuiviLivraisonActivity extends BaseActivity
implements OnItemSelectedListener
{
	private listAdapter m_adapter;
	private String[] arrTemp;
	TextView tvTitre;
	ListView myListView;

	int top = 0;
	int index = 0;
	String m_coderep= "";
	//


	Context context;

	ArrayList<TableLivraison.structLivraison> colNames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suivilivraison);
		context = this;

		initGUI();
		initListeners();

	}

	void initGUI() {
		myListView = (ListView) findViewById(R.id.lv_4);
		myListView.setTextFilterEnabled(true);

		tvTitre = (TextView) findViewById(R.id.titre);

		tvTitre.setText("");
		 m_coderep= Preferences.getValue(this, Espresso.LOGIN, "0");;

		PopulateList();
	}

	void initListeners() {

	}


	private void PopulateList( ) {

	    colNames = new ArrayList<TableLivraison.structLivraison>();
		this.m_adapter = new listAdapter(this, R.layout.item_suivilivraison, colNames);

		myListView.setAdapter(this.m_adapter);
		ArrayList<TableLivraison.structLivraison> arts =null;
			arts=TableLivraison.getChargement();

		for (int v = 0; v < arts.size(); v++)
			colNames.add(arts.get(v));

		arrTemp = new String[colNames.size()];
	}

	private class listAdapter extends ArrayAdapter<TableLivraison.structLivraison> {

		private ArrayList<TableLivraison.structLivraison> items;

		int total = 0;
		boolean isCalculExist = false;
		Context context;

		public listAdapter(Context context, int textViewResourceId,
				ArrayList<TableLivraison.structLivraison> items) {
			super(context, textViewResourceId, items);
			this.items = items;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//View v = convertView;

			final ViewHolder holder;
			View rowView = convertView;
			if (rowView == null) {
				holder = new ViewHolder();
				LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//LayoutInflater inflater = InventaireActivity.this.getLayoutInflater();
				rowView = vi.inflate(R.layout.item_suivilivraison, parent, false);
				holder.rl = (LinearLayout) rowView.findViewById(R.id.rl_root);
				holder.ll = (LinearLayout) rowView.findViewById(R.id.ll_saisie);
				holder.tvCode = (TextView) rowView.findViewById(R.id.textCode);
				holder.tvLbl = (TextView) rowView.findViewById(R.id.textLbl);
				holder.textLast= (TextView) rowView.findViewById(R.id.textLast);
				holder.bOk = (ImageButton) rowView.findViewById(R.id.imageButtonValide);
				holder.etQte = (EditText) rowView.findViewById(R.id.editTextQte);
				holder.tvQteTheo = (TextView) rowView.findViewById(R.id.textViewQteTheoVal);
				holder.tvTxTva = (TextView) rowView.findViewById(R.id.textViewTxPoidsVal);
				holder.bPlus = (Button) rowView.findViewById(R.id.bPlus);
				holder.bMoins = (Button) rowView.findViewById(R.id.bMoins);
				holder.etCalc = (EditText) rowView.findViewById(R.id.etCalc);
				holder.bMoins.setVisibility(View.GONE);
				holder.etQte.setVisibility(View.GONE);
				rowView.setTag(holder);
			}else{
				holder = (ViewHolder) rowView.getTag();
			}



			holder.position = position;

			TableLivraison.structLivraison o = items.get(position);

			if (o != null) {
				total = 0;
				isCalculExist = false;

				//RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl_root);

				holder.tvCode.setText(Fonctions.YYYYMMDD_to_dd_mm_yyyy(o.LV_DAT_LIV));
				holder.tvQteTheo.setText(o.LV_DATERECU);
				holder.tvQteTheo.setVisibility(View.VISIBLE);


				holder.tvLbl.setText("N° cde : "+o.LV_NUM_CDE);
				holder.textLast.setText(o.LV_NUM_CDE2);

                holder.etQte.setBackgroundColor(Color.TRANSPARENT);
				String valueTag=o.LV_NUM_CDE;
				holder.etQte.setTag(o.LV_NUM_CDE);
				holder.bOk.setTag(holder);

				if(holder.etQte.getText() != null){
					if(holder.etQte.getText().toString().equals("")){
						holder.etQte.setText(o.LV_SAISIE);
					}else{
						isCalculExist = true;
						total = Integer.parseInt(holder.etQte.getText().toString());
					}
				}

				String qte = "";
				if(arrTemp.length > 0){
					if(arrTemp[position] == null || arrTemp[position].equals("")){
						qte = o.LV_SAISIE;
					}else{
						qte = arrTemp[position];
					}
				}
				holder.etQte.setText(qte);


				items.get(position).LV_SAISIE =holder.etQte.getText().toString();

				holder.bOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						ViewHolder holder = (ViewHolder) v.getTag();
						String qte=holder.etQte.getText().toString();

						if(qte.equals("")) return;

						String codeart=holder.etQte.getTag().toString();

						index = myListView.getFirstVisiblePosition();
						View vw = myListView.getChildAt(0);
						top = (vw == null) ? 0 : vw.getTop();
						//save(codeart,Fonctions.convertToInt(qte),0 );
						//PopulateList();
						arrTemp = Fonctions.removeArrayElement(holder.position, arrTemp);
						m_adapter.remove(items.get(holder.position));
						m_adapter.notifyDataSetChanged();
						myListView.setSelectionFromTop(index, top);


					}
				});

				if ((qte == null || qte.equals("")) && o.LV_SAISIE.equals(""))
				{
					//		 tvQte.setText("");
					if(Fonctions.GetStringDanem(o.LV_SAISIE).equals("1"))
					{
						holder.rl.setBackgroundColor(Color.GREEN);
						//holder.etQte.setBackgroundColor(Color.GREEN);

					}
					else
						holder.rl.setBackgroundColor(Color.TRANSPARENT);


				}
				else
				{
					//	ll.setVisibility(View.VISIBLE);
					//		tvQte.setText(String.valueOf(o.QTEINV));
					if(o.LV_SAISIE.equals("0"))
					holder.rl.setBackgroundColor(Color.TRANSPARENT);
					if(Fonctions.GetStringDanem(o.LV_SAISIE).equals("1"))
						holder.rl.setBackgroundColor(Color.GREEN);
					    //holder.etQte.setBackgroundColor(Color.GREEN);


				}
				if(Fonctions.GetStringDanem(o.LV_ETAT).equals("1"))
				{
					holder.bPlus.setVisibility(View.GONE);
				}
				else
					holder.bPlus.setVisibility(View.VISIBLE);

				holder.bPlus.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						showDialogDaterecu(m_coderep,holder.etQte.getTag().toString(),holder.tvCode.getText().toString(),holder.textLast.getText().toString());

						//on récupère le total pour éviter tous problèmes avec les autres lignes
						/*if(holder.etQte.getText().toString() != null && !holder.etQte.getText().toString().equals("")){
							try{
								total = 1;
							}catch (NumberFormatException ex){

							}
						}else{
							total = 1;
						}
						isCalculExist = true;

							if(isCalculExist){
								holder.etQte.setText(Integer.toString(total));
								//holder.etCalc.setText("");
								//saveInventaire(holder);
								StringBuffer stBuf =new StringBuffer();
                                //holder.rl.setBackgroundColor(Color.GREEN);
								holder.etQte.setBackgroundColor(Color.GREEN);
								//Global.dbKDTempo.save(Fonctions.GetStringDanem(holder.etQte.getTag().toString()),stBuf);
							}*/

					}
				});

				holder.bMoins.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

				holder.etQte.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {

					}

					@Override
					public void afterTextChanged(Editable s) {

						if(arrTemp.length > 0 && arrTemp.length >= holder.position && !s.toString().equals("")){
							arrTemp[holder.position] = s.toString();
							Log.i("tag","Number : "+s.toString());
						}
					}
				});


				holder.ll.setVisibility(View.VISIBLE);

				//	 etQte.setText(String.valueOf(o.QTEINV));
				//	 etQte.setTag(o.CODART);

				//	rl.setBackgroundColor(Color.GREEN);

				valueTag+=";"+o.LV_SAISIE+";"+"0";
				//				holder.rl.setTag(valueTag);
			}
            colNames.get(position).LV_SAISIE = o.LV_SAISIE;
			return rowView;
		}


		private class ViewHolder {
			LinearLayout rl;
			LinearLayout ll;
			TextView tvCode;
			TextView tvLbl;
			TextView textLast;
			ImageButton bOk;
			EditText etQte;
			TextView tvQteTheo;
			TextView tvTxTva;
			Button bPlus;
			Button bMoins;
			EditText etCalc;
			int position;
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			/*
			 * if( checkAll()==true) { if (saveCde()==true) finish(); }
			 */

			return false;
		}

		else
			return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		addMenu(menu, R.string.quitter, android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {


		case R.string.quitter:

			finish();


			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}


	public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {

	}
	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	private void showDialogDaterecu(String coderep,String numcde,String datliv,String numcde2){
       final String scoderep=coderep;
		final String snumcde=numcde;
		final String sdatliv=Fonctions.dd_mm_yyyy_TO_yyyymmdd(datliv);
		final String snumcde2=Fonctions.GetStringDanem(numcde2);



		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.textentrvalertdialog3);
		dialog.setTitle("Date reçu");
		dialog.setCanceledOnTouchOutside(false);

		final Spinner choixTournee = (Spinner) dialog.findViewById(R.id.spinner_dialog);
		choixTournee.setVisibility(View.GONE);
		final DatePicker datePickerPassage = (DatePicker) dialog.findViewById(R.id.date_tournee);
		final TextView tvtextdaterecu = (TextView) dialog.findViewById(R.id.tvtextdaterecu);

		tvtextdaterecu.setText("N° cde : "+snumcde);

		//dbTournee db = new dbTournee(getDB());
		//ArrayList<String> data = db.getListTournee(codeagent);

		/*ArrayAdapter adapter = new ArrayAdapter(
				this,
				android.R.layout.simple_spinner_item,
				data
		);*/

		Button okButton = (Button) dialog.findViewById(R.id.OKButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				//String codeTournee = choixTournee.getSelectedItem().toString();

				int day = datePickerPassage.getDayOfMonth();
				int month = datePickerPassage.getMonth() + 1;
				int year = datePickerPassage.getYear();
               String stmonth="0"+Fonctions.getInToStringDanem(month);
				stmonth=Fonctions.Right(stmonth,2);

				String datePassage = (day+"/"+stmonth+"/"+year);
				StringBuffer stBuf = new StringBuffer();
				Global.dbKDMarchandise.save(scoderep,sdatliv,snumcde,datePassage,snumcde2,stBuf);

               PopulateList();

				dialog.dismiss();
			}
		});
		Button cancelButton = (Button) dialog
				.findViewById(R.id.CancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		//choixTournee.setAdapter(adapter);

		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.show();

	}




}
