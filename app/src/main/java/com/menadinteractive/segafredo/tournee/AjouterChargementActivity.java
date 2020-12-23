package com.menadinteractive.segafredo.tournee;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.menadinteractive.segafredo.db.dbKD733Habitudes;
import com.menadinteractive.segafredo.db.dbSiteHabitudes;

import java.util.ArrayList;
import java.util.Collections;

public class AjouterChargementActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private listAdapter m_adapter;
    private String[] arrTemp;
    CheckBox cbAllarticle;
    boolean partielok=false;
    TextView tvTitre;
    ListView myListView;
    int lvPosition;
    String m_soccode;
    int top = 0;
    int index = 0;
    ArrayList<Bundle> idFam = null;// les id qui servent a retrouver les pays
    String m_stFam = "" ;	//Filtre Fam (voir spinner)
    ImageButton ibFilter;
    EditText etFilter;
    String m_codetournee = null;

    Context context;

    ArrayList<Tournee.structTournee> colNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_chargement);
        Bundle bundle = null;

        if(getIntent() != null){
            bundle = getIntent().getExtras();
            if(bundle.getString("TOURNEE") != null){
                m_codetournee = bundle.getString("TOURNEE");
            }
        }

        idFam = new ArrayList<Bundle>();

        initGUI();
        initListeners();

        fillFamille("");

        Spinner spinnerFam = (Spinner) findViewById(R.id.spinnerFam);
        spinnerFam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

                String	m_Valeur=idFam.get(position).getString(Global.dbParam.FLD_PARAM_CODEREC);
                m_stFam=m_Valeur;


                PopulateList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    void initGUI() {
        myListView = (ListView) findViewById(R.id.lvDetailArticle);
        myListView.setTextFilterEnabled(true);
        etFilter=(EditText)findViewById(R.id.etFilter);

        ibFilter=(ImageButton)findViewById(R.id.ibFind);
        //cbAllarticle=(CheckBox)findViewById(R.id.cbAllarticle);
        //tvTitre = (TextView) findViewById(R.id.titre);
        //cbAllarticle.setChecked(true);

        //tvTitre.setText("");
        PopulateList();
    }

    void initListeners() {

        ibFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //	String filter=etFilter.getText().toString();
                //	PopulateList(  );
                //promptText("CODE ARTICLE ou DESIGNATION");
				if(etFilter.getText() != null){
					String filter = etFilter.getText().toString();
					etFilter.setText(filter);
					PopulateList( );
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(etFilter.getWindowToken(), 0);
				}

            }
        });
        etFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //promptText("CODE ARTICLE ou DESIGNATION");
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);
        spinner.setOnItemSelectedListener(this);

    }

    private void PopulateList() {

              colNames = new ArrayList<Tournee.structTournee>();
        this.m_adapter = new listAdapter(this, R.layout.item_chargement_ajouter, colNames);

        myListView.setAdapter(this.m_adapter);
        String filter=etFilter.getText().toString();

        Log.e("filter","filter=>"+filter);

        ArrayList<Tournee.structTournee> arts =null;
        arts=Tournee.getChargement2(m_codetournee, filter,m_stFam);

        for (int v = 0; v < arts.size(); v++)
            colNames.add(arts.get(v));

        Collections.sort(colNames, Tournee.Comparators.CHAR_FAMILLE);

        //this.m_adapter.sort(Tournee.Comparators.CHAR_FAMILLE));
        //this.m_adapter.sort(Tournee.Comparators.CHAR_FAMILLE);

        arrTemp = new String[colNames.size()];
    }

        public void promptText(String title) {

            boolean bres = false;

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.textentrvalertdialog);
            dialog.setTitle(title);
            final EditText inputLine = (EditText) dialog
                    .findViewById(R.id.my_edittext);
            inputLine.setSingleLine(true);


            Button okButton = (Button) dialog.findViewById(R.id.OKButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.dismiss();
                    String filter = inputLine.getText().toString();
                    etFilter.setText(filter);
                    PopulateList( );
                }
            });
            Button cancelButton = (Button) dialog
                    .findViewById(R.id.CancelButton);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            dialog.show();
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //addMenu(menu, R.string.validation_de_l_inventaire,R.drawable.success48_optionmenu);
        addMenu(menu, R.string.quitter, android.R.drawable.ic_menu_close_clear_cancel);
        //addMenu(menu, R.string.ajouter, android.R.drawable.ic_menu_add);
        //addMenu(menu, R.string.inventaire_connexion,android.R.drawable.ic_dialog_dialer);
        //addMenu(menu, R.string.raz, R.drawable.basic1_020_bin_trash_delete);
        //addMenu(menu, R.string.inventaire_ended, R.drawable.success48_optionmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.string.quitter:

                finish();

                return true;

            case R.string.ajouter:

                /*Intent intent = new Intent(ListeChargement2Activity.this, AjouterChargementActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TOURNEE", m_codetournee);
                intent.putExtras(bundle);
                startActivity(intent);*/

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    void fillFamille(String selVal) {
        try {
            //if (Global.dbParam.getRecordsFiltreAllSoc(Global.dbParam.PARAM_FAM1,this.idFam, "1") == true) {
            if (Global.dbParam.getFamActives( this.idFam) == true) {

                //on inser la famille 'Anomalies'
                Bundle bundle = new Bundle();

                //on insert  '---'
                bundle = new Bundle();
                bundle.putString(Global.dbParam.FLD_PARAM_CODEREC,getString(R.string.d_vide_vide_s));
                bundle.putString(Global.dbParam.FLD_PARAM_LBL, getString(R.string.d_vide_vide_s));
                bundle.putString(Global.dbParam.FLD_PARAM_COMMENT, getString(R.string.d_vide_vide_s));
                bundle.putString(Global.dbParam.FLD_PARAM_VALUE, getString(R.string.d_vide_vide_s));
                idFam.add(0,bundle);

                int pos = -1;
                String[] items = new String[idFam.size()];
                for (int i = 0; i < idFam.size(); i++) {

                    items[i] = idFam.get(i).getString(
                            Global.dbParam.FLD_PARAM_LBL);

                    String codeRec = idFam.get(i).getString(
                            Global.dbParam.FLD_PARAM_CODEREC);

                    if (selVal.equals(codeRec)) {
                        pos = i;
                    }

                }

                Spinner spinner = (Spinner) findViewById(R.id.spinnerFam);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setSelection(pos);

            }

        } catch (Exception ex) {

        }

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        m_stFam = idFam.get(pos).getString(
                Global.dbParam.FLD_PARAM_CODEREC);
        //m_stFam = "" ;		//6546876546: DEBUGAGE
        PopulateList() ;
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private class listAdapter extends ArrayAdapter<Tournee.structTournee> {

        private ArrayList<Tournee.structTournee> items;

        int total = 0;
        boolean isCalculExist = false;
        Context context;

        public listAdapter(Context context, int textViewResourceId,
                           ArrayList<Tournee.structTournee> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //View v = convertView;

            final listAdapter.ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                holder = new listAdapter.ViewHolder();
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //LayoutInflater inflater = InventaireActivity.this.getLayoutInflater();
                rowView = vi.inflate(R.layout.item_chargement_ajouter, parent, false);
                holder.rl = (LinearLayout) rowView.findViewById(R.id.rl_root);
                holder.ll = (LinearLayout) rowView.findViewById(R.id.ll_saisie);
                holder.tvCode = (TextView) rowView.findViewById(R.id.textCode);
                holder.textMini = (TextView) rowView.findViewById(R.id.textMini);

                holder.tvLbl = (TextView) rowView.findViewById(R.id.textLbl);
                holder.textLast = (TextView) rowView.findViewById(R.id.textLast);
                holder.bOk = (ImageButton) rowView.findViewById(R.id.imageButtonValide);
                holder.etQte = (EditText) rowView.findViewById(R.id.editTextQte);
                //TextView tvQte = (TextView) convertView.findViewById(R.id.textViewQteVal);
                holder.tvQteTheo = (TextView) rowView.findViewById(R.id.textViewQteTheoVal);
                holder.tvTxTva = (TextView) rowView.findViewById(R.id.textViewTxPoidsVal);
                holder.bPlus = (Button) rowView.findViewById(R.id.bPlus);
                holder.bMoins = (Button) rowView.findViewById(R.id.bMoins);
                holder.etCalc = (EditText) rowView.findViewById(R.id.etCalc);
                holder.bMoins.setVisibility(View.GONE);
                holder.etQte.setEnabled(true);
                rowView.setTag(holder);
            } else {
                holder = (listAdapter.ViewHolder) rowView.getTag();
            }

            holder.position = position;

            final Tournee.structTournee o = items.get(position);

            if (o != null) {
                total = 0;
                isCalculExist = false;

                //RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl_root);

                holder.tvCode.setText(o.CHAR_CODEARTICLE);
                //holder.textMini.setText("Somme stock mini : " + o.CHAR_STOCKMINI);

                holder.tvLbl.setText(Fonctions.GetStringDanem(o.CHAR_LIBELLEARTICLE).trim() );
                holder.textLast.setText("Date : " + o.CHAR_DATELAST);
                holder.textLast.setVisibility(View.GONE);
                // holder.textLast.setText("Date : "+Global.dbProduit.getProduitsWithHisto_chargement(o.charcodearticle, ""));
                //holder.etQte.setBackgroundColor(Color.TRANSPARENT);
                String valueTag = o.CHAR_CODEARTICLE;
                holder.etQte.setTag(o.CHAR_CODEARTICLE);
                holder.bOk.setTag(holder);
                //holder.etQte.setVisibility(View.GONE);

                if (holder.etQte.getText() != null) {
                    if (holder.etQte.getText().toString().equals("")) {
                        holder.etQte.setText(o.CHAR_SAISIE);
                    } else {
                        isCalculExist = true;
                        total = Integer.parseInt(holder.etQte.getText().toString());
                    }
                }

                String qte = "";
                if (arrTemp.length > 0) {
                    if (arrTemp[position] == null || arrTemp[position].equals("")) {
                        qte = o.CHAR_SAISIE;
                    } else {
                        qte = arrTemp[position];
                    }
                }
                holder.etQte.setText(qte);

                items.get(position).CHAR_SAISIE = holder.etQte.getText().toString();

                holder.bOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        listAdapter.ViewHolder holder = (listAdapter.ViewHolder) v.getTag();
                        String qte = holder.etQte.getText().toString();

                        if (qte.equals("")) return;

                        String codeart = holder.etQte.getTag().toString();

                        index = myListView.getFirstVisiblePosition();
                        View vw = myListView.getChildAt(0);
                        top = (vw == null) ? 0 : vw.getTop();
                        //save(codeart,Fonctions.convertToInt(qte),0 );
                        //PopulateList();
                        dbSiteHabitudes.structHabitude habitude =  new dbSiteHabitudes.structHabitude();
                        habitude.FIELD_CODEREP = getLogin() ;
                        habitude.FIELD_COD_CLT = dbSiteHabitudes.CODE_CLIENT_CHARGEMENT;
                        habitude.FIELD_DER_PRX_VEN = "";
                        habitude.FIELD_COD_ART = o.CHAR_CODEARTICLE ;
                        habitude.FIELD_DER_QTE_LIV = "";
                        habitude.FIELD_DER_DAT_PIE = "";
                        habitude.FIELD_QTE_12M = "";
                        habitude.FIELD_NB_FAC_TOT_CLI = "";
                        habitude.FIELD_QTE_MOY_FAC = "";
                        habitude.FIELD_NB_FAC_TOT_ART = "";
                        habitude.FIELD_FREQUENCE = "";
                        habitude.FIELD_STK_CLT = qte;
                        habitude.FIELD_CODETOURNEE = m_codetournee;
                        Global.dbSiteHabitudes.save(habitude,false);
                        arrTemp = Fonctions.removeArrayElement(holder.position, arrTemp);
                        m_adapter.remove(items.get(holder.position));
                        m_adapter.notifyDataSetChanged();
                        myListView.setSelectionFromTop(index, top);

                        //tof, enregistremet dans KD733 pour envoi au serveur
                        dbKD733Habitudes kdHabitudes=new dbKD733Habitudes(Global.dbParam.getDB());
                        kdHabitudes.save( dbSiteHabitudes.CODE_CLIENT_CHARGEMENT, getLogin(),o.CHAR_CODEARTICLE, qte,Fonctions.GetStringDanem(m_codetournee ));

                    }
                });

                if ((qte == null || qte.equals("")) && o.CHAR_SAISIE.equals("")) {
                    //		 tvQte.setText("");
                    if (Fonctions.GetStringDanem(o.CHAR_SAISIE).equals("1")) {
                        holder.rl.setBackgroundColor(Color.GREEN);
                        //                        //holder.etQte.setBackgroundColor(Color.GREEN);

                    } else
                        holder.rl.setBackgroundColor(Color.TRANSPARENT);


                } else {
                    //	ll.setVisibility(View.VISIBLE);
                    //		tvQte.setText(String.valueOf(o.QTEINV));
                    if (o.CHAR_SAISIE.equals("0"))
                        holder.rl.setBackgroundColor(Color.TRANSPARENT);
                    if (Fonctions.GetStringDanem(o.CHAR_SAISIE).equals("1"))
                        holder.rl.setBackgroundColor(Color.GREEN);
                    //holder.etQte.setBackgroundColor(Color.GREEN);
                }

                holder.bPlus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //on récupère le total pour éviter tous problèmes avec les autres lignes
                        if (holder.etQte.getText().toString() != null && !holder.etQte.getText().toString().equals("")) {
                            try {
                                total = 1;
                            } catch (NumberFormatException ex) {

                            }
                        } else {
                            total = 1;
                        }
                        isCalculExist = true;
                        //if(holder.etCalc.getText().toString() != null && !holder.etCalc.getText().toString().equals("")){
                        //float value = Fonctions.convertToFloat(holder.etCalc.getText().toString());
                        //total += value;
                        if (isCalculExist) {
                            holder.etQte.setText(Integer.toString(total));
                            //holder.etCalc.setText("");
                            //saveInventaire(holder);
                            StringBuffer stBuf = new StringBuffer();
                            holder.rl.setBackgroundColor(Color.GREEN);
                            //holder.etQte.setBackgroundColor(Color.GREEN);
                            Global.dbKDTempo.save(Fonctions.GetStringDanem(holder.etQte.getTag().toString()), stBuf);
                        }
                        //}
                    }
                });

                holder.bMoins.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       /* if (isInvTermine()) {
                            promptText("Validation", "Impossible l'inventaire est terminé", false);
                            return;
                        }*/

                        //on récupère le total pour éviter tous problèmes avec les autres lignes
                        if (holder.etQte.getText().toString() != null && !holder.etQte.getText().toString().equals("")) {
                            try {
                                total = Integer.parseInt(holder.etQte.getText().toString());
                            } catch (NumberFormatException ex) {

                            }
                        } else {
                            total = 0;
                        }
                        //on retire au total la valeur de l'edit
                        isCalculExist = true;
                        if (holder.etCalc.getText().toString() != null && !holder.etCalc.getText().toString().equals("")) {
                            float value = Fonctions.convertToFloat(holder.etCalc.getText().toString());
                            if (total >= value) {
                                total -= value;
                                if (isCalculExist) {
                                    holder.etQte.setText(Integer.toString(total));
                                    holder.etCalc.setText("");
                                    //saveInventaire(holder);
                                }
                            }
                        }
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

                        if (arrTemp.length > 0 && arrTemp.length >= holder.position && !s.toString().equals("")) {
                            arrTemp[holder.position] = s.toString();
                            Log.i("tag", "Number : " + s.toString());
                        }
                    }
                });


                holder.ll.setVisibility(View.VISIBLE);

                //	 etQte.setText(String.valueOf(o.QTEINV));
                //	 etQte.setTag(o.CODART);

                //	rl.setBackgroundColor(Color.GREEN);

                valueTag += ";" + o.CHAR_SAISIE + ";" + "0";
                //                //				holder.rl.setTag(valueTag);
            }
            colNames.get(position).CHAR_SAISIE = o.CHAR_SAISIE;
            return rowView;
        }

        private class ViewHolder {
            LinearLayout rl;
            LinearLayout ll;
            TextView tvCode;
            TextView textMini;
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


}
