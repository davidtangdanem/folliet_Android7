package com.menadinteractive.segafredo.plugins;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.R;


public class MiseAJour extends BaseActivity{
	/** GUI */
	GridView moduleGridView;
	private ProgressDialog m_ProgressDialog = null;

	/** models */
	ArrayList<Module> modules;
	ModuleAdapter moduleAdapter;

	Module newAgendanemModule;
	Module newQuestionemModule;
	Module newExpressoModule;
	Module newPrinterModule;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.maj);

		initGUI();
		initModels();
		populateModules();
		initListeners();
	}

	private void initGUI(){
		setProgressBarIndeterminateVisibility(false);
		moduleGridView = (GridView) findViewById(R.id.moduleGridView);
	}

	private void initModels(){
		modules = new ArrayList<Module>();
		moduleAdapter = new ModuleAdapter(this,	R.layout.module_item, modules);
		moduleGridView.setAdapter(moduleAdapter);
	}

	private void initListeners(){

		moduleGridView.setOnItemClickListener(modulesOnClickListener);
	}

	private void populateModules() {
		modules.clear();
/*
		if (controlMinVer(true) == true) {
			modules.add(new Module(getString(R.string.newversion),getAppIcon(this), R.drawable.usb48));
		}

		if (android.os.Build.VERSION.SDK_INT >= 14
				&& Agendanem.isAgendanemNewVersionAvailable(Agendanem.getVersion(this, new StringBuffer()))) {
			newAgendanemModule = new Module(getString(R.string.newversion_agendanem), Agendanem.getAppIcon(this), R.drawable.addevent48);
			modules.add(newAgendanemModule);
		}

		if ( Questionem.isQuestionemNewVersionAvailable(Questionem.getVersion(this, new StringBuffer()))) {
			newQuestionemModule = new Module(getString(R.string.newversion_questionem),	Questionem.getAppIcon(this), R.drawable.addevent48);
			modules.add(newQuestionemModule);
		}
*/
		if (android.os.Build.VERSION.SDK_INT >= 14
				&& Espresso.isExpressoNewVersionAvailable(Espresso.getVersion(this, new StringBuffer()))
				) {
			newExpressoModule = new Module(getString(R.string.newversion_expresso),	Espresso.getAppIcon(this), R.drawable.expresso);
			modules.add(newExpressoModule);
		}
		
		if ( Printanem.isNewVersionAvailable(Printanem.getVersion(this, new StringBuffer()))) {
			newPrinterModule = new Module(getString(R.string.newversion_printer),	Printanem.getAppIcon(this), R.drawable.addevent48);
			modules.add(newPrinterModule);
		}

		moduleAdapter.notifyDataSetChanged();

	}
	
	
	public static boolean isNewSoftwareAvailable(Context context){
		return (isAgendanemAvailable(context) || isQuestionemAvailable(context) || isExpressoAvailable(context)  || isPrinterAvailable(context));
	}
	
	public static boolean isAgendanemAvailable(Context context){
		return (android.os.Build.VERSION.SDK_INT >= 14
				&& Agendanem.isAgendanemNewVersionAvailable(Agendanem.getVersion(context, new StringBuffer())));
	}
	
	public static boolean isQuestionemAvailable(Context context){
		return Questionem.isQuestionemNewVersionAvailable(Questionem.getVersion(context, new StringBuffer()));
	}
	
	public static boolean isExpressoAvailable(Context context){
		return (android.os.Build.VERSION.SDK_INT >= 14
				&& Espresso.isExpressoNewVersionAvailable(Espresso.getVersion(context, new StringBuffer()))
		);
	}
	
	public static boolean isPrinterAvailable(Context context){
		return (Printanem.isNewVersionAvailable(Printanem.getVersion(context, new StringBuffer())));
	}
	

	private OnItemClickListener modulesOnClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			final Module module = (Module) parent.getAdapter().getItem(position);

			if(module.getName().equals(getString(R.string.newversion_agendanem))){
				Update("http://84.14.126.77/agendanem/" + Agendanem.availableVersion + "/"+Agendanem.APP,Agendanem.APP);
				modules.remove(module);
				moduleAdapter.notifyDataSetChanged();
				return;
			}
			else if (module.getName().equals(getString(R.string.newversion_expresso))) {
				Update("http://84.14.126.77/espresso/" + Espresso.availableVersion + "/"+Espresso.APP,Espresso.APP);
				modules.remove(module);
				moduleAdapter.notifyDataSetChanged();
				return;
			}
			else if (module.getName().equals(getString(R.string.newversion_questionem))) {
				Update("http://84.14.126.77/questionem/" + Questionem.availableVersion + "/"+Questionem.APP,Questionem.APP);
				modules.remove(module);
				moduleAdapter.notifyDataSetChanged();
				return;
			}
			else if (module.getName().equals(getString(R.string.newversion))) {
				Update("http://84.14.126.77/negos/" + minver + "/negos2.apk","negos2.apk");
				modules.remove(module);
				moduleAdapter.notifyDataSetChanged();
				return;

			}
			else if (module.getName().equals(getString(R.string.newversion_printer))) {
				Update("http://84.14.126.77/printanem/" + Printanem.availableVersion + "/"+Printanem.APP,Printanem.APP);
				modules.remove(module);
				moduleAdapter.notifyDataSetChanged();
				return;

			}
		}
	};



	/*
	 * lance la maj de lapplication
	 */
	public void Update(String apkurl,String apk) {
		m_ProgressDialog = ProgressDialog.show(MiseAJour.this,
				getString(R.string.menu_veuillezpatienter),
				getString(R.string.menu_miseajour), true);

//		task_majApp task = null;
//		task = new task_majApp(apkurl,apk,this, m_ProgressDialog);
//		task.execute();
	}

}
