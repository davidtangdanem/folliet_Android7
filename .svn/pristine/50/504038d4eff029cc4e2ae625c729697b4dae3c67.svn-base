package com.menadinteractive.folliet2016;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.menadinteractive.folliet2016.R;
import com.menadinteractive.segafredo.carto.Marker;

public class ExternalStorage {
	public static String ROOT_FOLDER = "/Expresso/";
	public static String MARKER_FOLDER = ROOT_FOLDER+"Marker/";
	public static String STAT_FOLDER = ROOT_FOLDER+"Stat/";
	public static String MEDIA_FOLDER = ROOT_FOLDER+"media/";
	public static String DOWNLOAD_FOLDER = ROOT_FOLDER+"download/";
	public static String SIGNATURE_FOLDER = ROOT_FOLDER+"Signatures/";
	public static String PHOTOS_FOLDER = ROOT_FOLDER+"photos/";
	public static String PDF_FOLDER = ROOT_FOLDER+"pdf/";
	public static String SIGNATURES_PDF_FOLDER = ROOT_FOLDER+"signaturespdf/";
	
	public static String PDF_RAPPORTFOLDER = ROOT_FOLDER+"pdf/rapport/";
	public static String PDF_BACKUPFOLDER = ROOT_FOLDER+"backup/";
	public static String PDF_BACKUPPHOTOFOLDER = ROOT_FOLDER+"backup/photos/";
	public static String PDF_BACKUPSIGNATUREFOLDER = ROOT_FOLDER+"backup/Signatures/";
	public static String PDF_BACKUPPDFFOLDER = ROOT_FOLDER+"backup/pdf/";
	public static String PDF_BACKUPRAPPORTFOLDER = ROOT_FOLDER+"backup/rapport/";









	//	/** couleur */
	//	public static String ROUGE = "#ff0000";
	//	public static String BLEU = "#2e8ced";
	//	public static String ORANGE ="#ff7c48";
	//	public static String GRIS = "#ececec";
	//	public static String VERT = "#00ff00";

	Context context;

	public ExternalStorage(Context context, boolean duplicateMarkersOnSD){
		this.context = context;
		createFolders();
		if(duplicateMarkersOnSD)
			createMarkers(false);
	}

	public static boolean isFileExist(String path){
		try {
			File f = new File(path);
			return f.isFile();
		} catch (Exception e) {
			Debug.StackTrace(e);
		}
		return false;
	}

	private void createFolders(){
		createFolder(ROOT_FOLDER);
		createFolder(MARKER_FOLDER);
		createFolder(STAT_FOLDER);
		createFolder(MEDIA_FOLDER);
		createFolder(DOWNLOAD_FOLDER);
		createFolder(SIGNATURE_FOLDER);
		createFolder(PHOTOS_FOLDER);
		createFolder(PDF_FOLDER);
		createFolder(SIGNATURES_PDF_FOLDER);
		createFolder(PDF_RAPPORTFOLDER);
		createFolder(PDF_BACKUPFOLDER);
		createFolder(PDF_BACKUPPHOTOFOLDER);
		createFolder(PDF_BACKUPSIGNATUREFOLDER);
		createFolder(PDF_BACKUPPDFFOLDER);
		createFolder(PDF_BACKUPRAPPORTFOLDER);


	}

	public static String getFolderPath(String dir){
		return Environment.getExternalStorageDirectory() +File.separator+dir;	
	}

	private boolean createFolder(String dir){
		try {
			String PATH = Environment.getExternalStorageDirectory() +File.separator+dir;
			File file = new File(PATH);
			return file.mkdirs();
		} catch (Exception e) {
			Debug.StackTrace(e);
		}
		return false;
	}

	private void createMarkers(boolean force){
		/** boulangerie */
		createMarker(R.drawable.bou, "boulangerie_vert.png", Marker.VERT, force);
		createMarker(R.drawable.bou, "boulangerie_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.bou, "boulangerie_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.bou, "boulangerie_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.bou, "boulangerie_bleu.png", Marker.BLEU, force);

		/** essence */
		createMarker(R.drawable.essence, "essence_vert.png", Marker.VERT, force);
		createMarker(R.drawable.essence, "essence_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.essence, "essence_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.essence, "essence_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.essence, "essence_bleu.png", Marker.BLEU, force);

		/** powerd_place */
		createMarker(R.drawable.powerd_place, "powerd_place_vert.png", Marker.VERT, force);
		createMarker(R.drawable.powerd_place, "powerd_place_rouge.png",Marker.ROUGE, force);
		createMarker(R.drawable.powerd_place, "powerd_place_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.powerd_place, "powerd_place_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.powerd_place, "powerd_place_bleu.png", Marker.BLEU, force);

		/** restaurant */
		createMarker(R.drawable.brr, "restaurant_vert.png", Marker.VERT, force);
		createMarker(R.drawable.brr, "restaurant_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.brr, "restaurant_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.brr, "restaurant_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.brr, "restaurant_bleu.png", Marker.BLEU, force);

		/** sandwich */
		createMarker(R.drawable.sna, "sandwich_vert.png", Marker.VERT, force);
		createMarker(R.drawable.sna, "sandwich_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.sna, "sandwich_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.sna, "sandwich_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.sna, "sandwich_bleu.png", Marker.BLEU, force);

		/** sport */
		createMarker(R.drawable.sport, "sport_vert.png", Marker.VERT, force);
		createMarker(R.drawable.sport, "sport_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.sport, "sport_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.sport, "sport_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.sport, "sport_bleu.png", Marker.BLEU, force);

		/** supermarket */
		createMarker(R.drawable.gro, "supermarket_vert.png", Marker.VERT, force);
		createMarker(R.drawable.gro, "supermarket_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.gro, "supermarket_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.gro, "supermarket_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.gro, "supermarket_bleu.png", Marker.BLEU, force);

		/** tabac */
		createMarker(R.drawable.tabac, "tabac_vert.png", Marker.VERT, force);
		createMarker(R.drawable.tabac, "tabac_rouge.png", Marker.ROUGE, force);
		createMarker(R.drawable.tabac, "tabac_orange.png", Marker.ORANGE, force);
		createMarker(R.drawable.tabac, "tabac_gris.png", Marker.GRIS, force);
		createMarker(R.drawable.tabac, "tabac_bleu.png", Marker.BLEU, force);

	}

	private void createMarker(int drawableID, String fileName, String color, boolean force){
		if(force || !ExternalStorage.isFileExist(Environment.getExternalStorageDirectory() +File.separator+ExternalStorage.MARKER_FOLDER+File.separator+fileName)){
			createColoredMarker(context.getResources().getDrawable(drawableID), fileName, color);
		}

	}

	public Drawable getMarker(String fileName){
		Drawable d = null;
		if(ExternalStorage.isFileExist(Environment.getExternalStorageDirectory() +File.separator+ExternalStorage.MARKER_FOLDER+File.separator+fileName))
			d= Drawable.createFromPath(Environment.getExternalStorageDirectory() +File.separator+ExternalStorage.MARKER_FOLDER+File.separator+fileName);
		return d;
	}

	public void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while((read = in.read(buffer)) != -1){
			out.write(buffer, 0, read);
		}
	}

	/**
	 * Récupère le bitmap d'un drawable et le copie sur la carte SD
	 * @param d
	 * @param path
	 */
	public void copyDrawable(Drawable d, String path){
		try {
			FileOutputStream out = new FileOutputStream(path);
			Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (Exception e) {
			Debug.StackTrace(e);
		}
	}


	public void createColoredMarker(Drawable grayscaleMarker, String fileName, String color){
		try {
			// A new Bitmap to hold the pixels
			Bitmap newBitmap = Bitmap.createBitmap(grayscaleMarker.getIntrinsicWidth(), grayscaleMarker.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

			// a Canvas to host the draw calls (writing into the bitmap)
			Canvas c = new Canvas(newBitmap);

			// un pinceau : to describe the colors and styles for the drawing
			Paint p = new Paint();
			// Le mode Mode.OVERLAY ajoute la couleur sur tout ce qui est transparent !
			//PorterDuff.Mode.SRC : toute l'image est remplie de la couleur
			ColorFilter filter = new PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
			p.setColorFilter(filter);

			// extract the bitmap of the drawable
			Bitmap grayscaleBitmap = ((BitmapDrawable)grayscaleMarker).getBitmap();

			// Told the Canvas to draw with the Colored Paint the grayscaleBitmap into the newBitmap
			c.drawBitmap(grayscaleBitmap, 0, 0, p);

			// write the newBitmap onto the SD Card
			FileOutputStream out = new FileOutputStream( Environment.getExternalStorageDirectory() +File.separator+ExternalStorage.MARKER_FOLDER+File.separator+fileName);
			newBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

		} catch (Exception e) {
			Debug.StackTrace(e);
		}
	}

	public String getSignaturesFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+SIGNATURE_FOLDER;
	}
	public String getBackupSignaturesFolder(){

		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_BACKUPSIGNATUREFOLDER;
	}
	public String getPhotosFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+PHOTOS_FOLDER;
	}
	public String getBackupPhotosFolder(){

		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_BACKUPPHOTOFOLDER;
	}
	public String getPDFFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_FOLDER;
	}
	public String getBackupPDFFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_BACKUPPDFFOLDER;
	}

	public String getPDFSignaturesFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+SIGNATURES_PDF_FOLDER;
	}
	public String getPDFRapportFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_RAPPORTFOLDER;
	}
	public String getBackupPDFRapportFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_BACKUPRAPPORTFOLDER;
	}
	public String getBackupFolder(){
		return Environment.getExternalStorageDirectory().toString()+File.separator+PDF_BACKUPFOLDER;
	}

	public static boolean moveFile(String from, String to) {

	    InputStream in = null;
	    OutputStream out = null;
	    try {

	        

	        in = new FileInputStream(from);        
	        out = new FileOutputStream(to);

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;

	            // write the output file
	            out.flush();
	        out.close();
	        out = null;

	        // delete the original file
	        new File(from).delete();  


	    } 

		catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	        return false;
	    }
			catch (Exception e) {
	        Log.e("tag", e.getMessage());
	        return false;
	    }
	    return true;

	}

	public void deleteOldFiles(String filePath){

		File file = new File(filePath);
		Date lastModDate = new Date(file.lastModified());

		Date currentDate = new Date();

		/**
		 * la valeur est négative si date1 < date2, 0 présent si date1 = date2, positive si date1 > date2
		 * ne tient pas compte de l'heure (les deux date sont réglés à 0h00.00)
		 */

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(lastModDate);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE,0);
		calendar1.set(Calendar.SECOND,0);
		calendar1.set(Calendar.MILLISECOND,0);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(currentDate);
		calendar2.set(Calendar.HOUR_OF_DAY, 0);
		calendar2.set(Calendar.MINUTE,0);
		calendar2.set(Calendar.SECOND,0);
		calendar2.set(Calendar.MILLISECOND,0);

		int difference = (int)((calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / 3600000) / 24;

		if (difference > 7) // fichier datant de plus de 7 jours
		{
			boolean deleted = file.delete();
			if (!deleted)
			{
				Log.e("DeleteErreurFile",""+file);
			}
		}

		//Log.i("File last modified @ : "+ lastModDate.toString());

	}
	public File getFileFromAssets(String fileFullName) {
		File result = null;
		AssetManager assetManager = context.getAssets();
		String[] files = null;
		try {
			files = assetManager.list("");
		} catch (IOException e) {
			Log.e("TAG", e.getMessage());
		}

		for (String filename : files) {
			Debug.Log("filename : " + filename);
			if (filename.contains(fileFullName)) {
				// result = new File(filename);
				InputStream in = null;
				OutputStream out = null;
				try {
					// File f=new
					// File(Environment.getExternalStorageDirectory().toString()+File.separator+"testingpdf.pdf");
					in = assetManager.open(filename);
					// out = new FileOutputStream(f);
					out = new FileOutputStream(Environment
							.getExternalStorageDirectory().toString()
							+ File.separator + fileFullName);
					copyFile(in, out);
					in.close();
					in = null;
					out.flush();
					out.close();
					out = null;
					result = new File(Environment.getExternalStorageDirectory()
							.toString() + File.separator + fileFullName);
				} catch (Exception e) {
					Debug.StackTrace(e);
				}
				break;
			}
		}
		return result;
	}




}
