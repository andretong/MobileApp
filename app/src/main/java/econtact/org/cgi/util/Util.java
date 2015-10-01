package econtact.org.cgi.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Clase Util, tiene diversos metodos que son utilitarios en distintas clases
 * @author      Manuel Reyes <mareyes@e-contact.cl>
 * @version     1.0                               
 * @since       2012-11-10        
 */
public class Util {
	public static String  CONFIG_FILE="config.prop";
	public static String COMENTARIO="";
/*
 * Obtiene el servidor 
 */
	public static String  getServidor(Context context){


			String chunk = "";
			FileInputStream fis = null;
			try {
		
				File file = new File(context.getExternalFilesDir(null),CONFIG_FILE);
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				return null;
			}
			@SuppressWarnings("resource")
			BufferedInputStream bf = new BufferedInputStream(fis, 8192);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			try {
				while ((bytesRead = bf.read(buffer)) != -1) {
					chunk = new String(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;

			}
			return chunk;
	}
	/*
	 * Setea el servidor 
	 */
	public static int setServidor(String servidor, Context context) {
		try {
			//com.google.gson.Gson gson = new com.google.gson.Gson();
			
			File file = new File(context.getExternalFilesDir(null),CONFIG_FILE);
			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			// context.openFileOutput(Properties.FILENAMEDATOS,Context.MODE_WORLD_WRITEABLE
			// );
			fos.write(servidor.getBytes());
			fos.close();
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	public static void alerta(String mensaje, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Aviso");
		builder.setMessage(mensaje);
		builder.setPositiveButton("OK", null);

		AlertDialog alert = builder.create();
		alert.show();
	}
	/*
	 * Obtiene un array de fonos para un contacto dado
	
	public static String[] getFonos(Contacto contacto){
		Actions[] actions = contacto.getActions();
		ArrayList<String> fonos=new ArrayList<String>();
		if (actions != null) {
			for (int i = 0; i < actions.length; i++) {
				if ("StartCall".equals(actions[i].getActionName())) {
					fonos.add(actions[i].getActionDestination());
				}
			}
		
		}
		return fonos.toArray(new String[fonos.size()]);
	}

	 */
	/*
	 * Creo dialogo para ingresar comentario
	 * 
	 */
	public static String setComentario(Context contexto){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(contexto);

		alert.setTitle("Title");
		alert.setMessage("Message");

		// Set an EditText view to get user input 
		final EditText input = new EditText(contexto);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				COMENTARIO = input.getText().toString();

			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.

			}
		});

		alert.show();
		
		return COMENTARIO;
	}

	public String formatearDate(String fecha, String hora) {
		String[] arrayFecha = fecha.split("-");

		if (arrayFecha[0].length() <= 1) {
			arrayFecha[0] = "0" + arrayFecha[0];
		}
		if (arrayFecha[1].length() <= 1) {
			arrayFecha[1] = "0" + arrayFecha[1];
		}
		return arrayFecha[2].trim() + "-" + arrayFecha[1].trim() + "-"+ arrayFecha[0].trim() + " " + hora;
	}


	public int transformarFecha(String fecha){
		String[] arrayFecha = fecha.split(":");
		int segundos=0;

		if(arrayFecha.length>2){
			segundos= (Integer.parseInt(arrayFecha[0]) * 3600) + (Integer.parseInt(arrayFecha[1]) * 60 ) + Integer.parseInt(arrayFecha[2]);
		}else{
			segundos= (Integer.parseInt(arrayFecha[0]) * 3600) + (Integer.parseInt(arrayFecha[1]) * 60 );
		}
		return segundos;
	}

}