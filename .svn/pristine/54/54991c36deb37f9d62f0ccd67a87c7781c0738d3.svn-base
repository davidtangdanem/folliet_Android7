package com.menadinteractive.histo;

import java.io.File;
import java.util.ArrayList;

import com.menadinteractive.folliet2016.BaseActivity;
import com.menadinteractive.folliet2016.ExternalStorage;
import com.menadinteractive.folliet2016.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



@SuppressWarnings("deprecation")
public class GalleryIntervention extends BaseActivity implements OnClickListener{

	BaseActivity mBase;
	String mType;
	String mNumcde;
	ArrayList<File> mListPhoto;

	ImageView i_poubelle;
	ImageView imageView;
	Gallery gallery;
	TextView tv_title;

	ImageButton imageButtonPrec;
	
	Bitmap myBitmap;

	int imagePosition = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		mBase = this;

		gallery = (Gallery) findViewById(R.id.gallery);

		Bundle bundle = this.getIntent().getExtras();
		mType = bundle.getString("type");
		mNumcde = bundle.getString("numcde");

		mListPhoto = getPhotoFromTypeNumCde(mNumcde, mType);

		imageView = (ImageView) findViewById(R.id.image1);
		imageButtonPrec = (ImageButton) findViewById(R.id.imageButtonPrec);
		tv_title = (TextView)findViewById(R.id.tv_title);
		
		if(mListPhoto.size() > 0){
			ImageAdapter adapter = new ImageAdapter(this, mListPhoto);
			gallery.setAdapter(adapter);
		}

		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,long id)
			{
				BitmapDrawable bd = (BitmapDrawable)imageView.getDrawable();
				if(bd != null) bd.getBitmap().recycle();
				imageView.setImageBitmap(null);
				
				Toast.makeText(getBaseContext(),getString(R.string.Photo)+" " + (position + 1) + " "+getString(R.string.selectionnee),
						Toast.LENGTH_SHORT).show();
				// display the images selected
				if(mListPhoto.size() > 0){
					BitmapFactory.Options options = new BitmapFactory.Options();
		            options.inSampleSize = 2;
		            myBitmap = BitmapFactory.decodeFile(mListPhoto.get(position).getAbsolutePath(), options);
		            imageView.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 120, 120, false));
		            //myBitmap = null;
					//imageView.setImageURI(Uri.fromFile(mListPhoto.get(position)));
				}
				imagePosition = position;
			}

		});

		i_poubelle = (ImageView)findViewById(R.id.i_poubelle);
		i_poubelle.setOnClickListener(this);
		imageButtonPrec.setOnClickListener(this);

		fillTitleFromType(mType);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		gallery.destroyDrawingCache();
		BitmapDrawable bd = (BitmapDrawable)imageView.getDrawable();
		if(bd != null) bd.getBitmap().recycle();
		imageView.setImageBitmap(null);
		myBitmap = null;
		mListPhoto = null;
	}

	/**
	 * Permet de récupérer les photos grace au type et au numéro de commande
	 * @param numcde
	 * @param type
	 * @return
	 */
	private ArrayList<File> getPhotoFromTypeNumCde(String numcde, String type){
		//ExternalStorage externalStorage = new ExternalStorage(mBase);
		ExternalStorage externalStorage = new ExternalStorage(this, false);
		

		ArrayList<File> list = new ArrayList<File>();

		File photos = new File(externalStorage.getPhotosFolder());
		File[] files_sig = photos.listFiles();

		if (files_sig == null) return null;

		for (File f : files_sig) {
			if (f.isDirectory()) continue;
			String[] tab = f.getName().split("_");
			String name = "";
			if(tab.length > 0) {
				name = tab[0]+"_"+tab[1];
				if(name.equals(numcde+"_"+type)) list.add(f);
			}
		}

		return list;
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		//private int itemBackground;
		private ArrayList<File> filePhoto;

		public ImageAdapter(Context c, ArrayList<File> list)
		{
			context = c;
			filePhoto = list;
		}

		// returns the number of images
		public int getCount() {
			return filePhoto.size();
		}

		// returns the ID of an item
		public Object getItem(int position) {
			return position;
		}

		// returns the ID of an item
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			
			BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            myBitmap = BitmapFactory.decodeFile(filePhoto.get(position).getAbsolutePath(), options);
            imageView.setImageBitmap(myBitmap);
			//imageView.setImageURI(Uri.fromFile(filePhoto.get(position)));
			Gallery.LayoutParams params = new Gallery.LayoutParams(200, 200);
			imageView.setLayoutParams(params);
			imageView.setTag(filePhoto.get(position).getPath());
			//imageView.setBackgroundResource(itemBackground);
			return imageView;
		}
	}

	@Override
	public void onClick(View v) {

		if(v==i_poubelle){			
			if(imagePosition != -1){
				//on supprime l'image correspondante
				boolean result = mListPhoto.get(imagePosition).delete();

				if(result){
					Toast.makeText(this, getString(R.string.delete_image_succes), Toast.LENGTH_LONG).show();
					refreshAll();
				}
				else Toast.makeText(this, getString(R.string.delete_image_error), Toast.LENGTH_LONG).show();

			}else Toast.makeText(this, getString(R.string.aucune_image), Toast.LENGTH_LONG).show();
		}

		if(v==imageButtonPrec) returnOK();

	}

	private void refreshAll(){
		imageView.setImageResource(0);
		mListPhoto = getPhotoFromTypeNumCde(mNumcde, mType);
		gallery.setAdapter(new ImageAdapter(this, mListPhoto));
	}

	/**
	 * On remplit le titre par rapport au type du matricule
	 * @param type
	 */
	private void fillTitleFromType(String type){
		if(type != null){
			tv_title.setText(getString(R.string.gallerie));
		
		}
	}

}
