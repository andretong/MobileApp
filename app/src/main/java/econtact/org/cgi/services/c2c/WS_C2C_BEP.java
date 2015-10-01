package econtact.org.cgi.services.c2c;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import econtact.org.cgi.ivr.Actions;
import econtact.org.cgi.ivr.Agendamientos;
import econtact.org.cgi.ivr.Contacto;
import econtact.org.cgi.ivr.DoVirtualHold;
import econtact.org.cgi.ivr.ListaAgendamientos;
import econtact.org.cgi.util.ConexionHTTP;
import econtact.org.cgi.util.Properties;


@SuppressLint({ "NewApi" })
public class WS_C2C_BEP {
	/*
	 * Obtiene el contenido de una url, pasa solamente parametros en post, los get los pone dentro 
	 * de la url y verifica si los parametros son nulos.
	 */
	
	//public String servidor = "http://10.36.66.136:8080/WS_eBank/services";
	//public String servidor = "http://10.36.29.98:8080/C2C_BEP_Server/services";
	//public String servidor = "http://192.168.203.20:8080/C2C_BEP_Server/services";
	//public String servidor = "http://gesys-demo.e-contact.cl:8080/C2C_BEP_Server/services";
	public String servidor = Properties.getServidorC2C();
	private ConexionHTTP conexion = new ConexionHTTP();





	public DoVirtualHold doVirtualHold(String movil, String queue, ArrayList<String> datos) {
		DoVirtualHold doVh = null;
		try {

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idServiceClient", "1");
			parametros.put("phoneNumber", movil);
			parametros.put("queue", queue);

			List<String> listaKey = new ArrayList<String>();
			listaKey.add("RUT");
			listaKey.add("Nombre");
			listaKey.add("Categoria");
			listaKey.add("Segmento");
			listaKey.add("Aplicacion");
			listaKey.add("Opcion");

			List<String> listaValue = new ArrayList<String>();
			listaValue.add(datos.get(3));
			listaValue.add(datos.get(1));
			listaValue.add(datos.get(4));
			listaValue.add(datos.get(5));
			listaValue.add(datos.get(0));
			listaValue.add(datos.get(6));

			parametros.put("key", listaKey);
			parametros.put("value", listaValue);

			Log.d("WS_C2C_BEP", "Ejecutando doVirtualHold");
			Log.d("WS_C2C_BEP", "Servidor: "+servidor + "/doVirtualHold");
			//String resulPost = this.readUrlPost(servidor + "/doVirtualHold", parametros);
			String resulPost = conexion.performPostCall(servidor + "/doVirtualHold", parametros);
			Log.d("WS_C2C_BEP", "Resultado: "+resulPost);
			doVh = new Gson().fromJson(resulPost, DoVirtualHold.class);


		} catch (Exception e) {
			Log.e("WS_C2C_BEP-Error", "Error en doVirtualHold " + e.getMessage());
		}
		return doVh;
	}

	public Contacto doCallback(String movil,String queue,String inicioPeriodo,String terminoPeriodo,ArrayList<String> datos) {
		Contacto contacto = null;
		try {

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idServiceClient", "1");
			parametros.put("phoneNumber", movil);
			parametros.put("queue", queue);
			parametros.put("beginInterval", inicioPeriodo);
			parametros.put("endInterval", terminoPeriodo);

			List<String> listaKey = new ArrayList<String>();
			listaKey.add("RUT");
			listaKey.add("Nombre");
			listaKey.add("Categoria");
			listaKey.add("Segmento");
			listaKey.add("Aplicacion");
			listaKey.add("Opcion");

			List<String> listaValue = new ArrayList<String>();
			listaValue.add(datos.get(3));
			listaValue.add(datos.get(1));
			listaValue.add(datos.get(4));
			listaValue.add(datos.get(5));
			listaValue.add(datos.get(0));
			listaValue.add(datos.get(6));

			parametros.put("key", listaKey);
			parametros.put("value", listaValue);

			Log.d("WS_C2C_BEP", "Ejecutando doCallback");
			Log.d("WS_C2C_BEP", "Servidor: " + servidor + "/doCallback");
//			String resulPost = this.readUrlPost(servidor+ "/doCallback", parametros);
			String resulPost = conexion.performPostCall(servidor+ "/doCallback", parametros);
			Log.d("WS_C2C_BEP", "Resultado: " + resulPost);
			contacto = new Gson().fromJson(resulPost, Contacto.class);

		} catch (Exception e) {
			Log.e("WS_C2C_BEP-Error", "Error en doCallback " + e.getMessage());
		}
		return contacto;
	}

	public String setUserDatos(String tree,String nombre,String movil, String rut,
							   String categoria, String segmento, String opcion,String comentario) {

		String resulPost="";
		try {
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idServiceClient", "1");
			parametros.put("userANI", movil);

			List<String> listaKey = new ArrayList<String>();
			listaKey.add("RUT");
			listaKey.add("Nombre");
			listaKey.add("Categoria");
			listaKey.add("Segmento");
			listaKey.add("Aplicacion");
			listaKey.add("Opcion");
			listaKey.add("Comentarios");

			List<String> listaValue = new ArrayList<String>();
			listaValue.add(rut);
			listaValue.add(nombre);
			listaValue.add(categoria);
			listaValue.add(segmento);
			listaValue.add(tree);
			listaValue.add(opcion);
			listaValue.add(comentario);

			parametros.put("key", listaKey);
			parametros.put("value", listaValue);


//			resulPost = this.readUrlPost(Properties.getServidorNodosMovil() + "/attachUserData", parametros);
			resulPost = conexion.performPostCall(servidor + "/attachUserData", parametros);

		} catch (Exception e) {
			String error = e.getMessage();
			Log.e("WS_C2C_BEP-Error", "Error en setUserDatos " + error);
		}
		return resulPost;
	}


	public ListaAgendamientos getAgendamientos(String movil) {
		ListaAgendamientos agendas= new ListaAgendamientos();
		Agendamientos agenda= new Agendamientos();
		//movil= "0"+movil;
		try {

			
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()+ "/getScheduleRequestByMobile?phoneNumber="+movil, null);
			String resulPost = conexion.performPostCall(servidor+ "/getScheduleRequestByMobile?phoneNumber="+movil, null);
			
			if(!resulPost.equals("[]")){

				JSONArray array = new JSONArray(resulPost);
				Agendamientos[] listado = new Agendamientos[array.length()];
				for (int i = 0; i < array.length(); i++) {
					JSONObject row = array.getJSONObject(i);
					agenda = new Gson().fromJson(row.toString(),Agendamientos.class);
					listado[i]=agenda;
				}
				agendas.setValue(listado);

			}else{
				agendas=null;
			}

		} catch (Exception e) {
			String error = e.getMessage();
			Log.e("WS_C2C_BEP-Error", "Error en getAgendamientos " + error);
		}
		return agendas;
	}

	public DoVirtualHold doCancelarAgendamiento(String phoneNumber, String reason) {
		DoVirtualHold dvh= new DoVirtualHold();

		try {

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idServiceClient", "1");
			parametros.put("phoneNumber", phoneNumber);
			parametros.put("reason", reason);

//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()+ "/doCancelRequest", parametros);
			String resulPost = conexion.performPostCall(servidor + "/doCancelRequest", parametros);
			dvh = new Gson().fromJson(resulPost, DoVirtualHold.class);

		} catch (Exception e) {
			String error = e.getMessage();
			Log.e("WS_C2C_BEP-Error", "Error en cancelar Agendamiento " + error);
		}
		return dvh;
	}

//	public DoVirtualHold doVirtualHold(String movil, String queue, ArrayList<String> datos) {
//		DoVirtualHold doVh = null;
//		try {
//
//			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
//
//			BasicNameValuePair p0 = new BasicNameValuePair("phoneNumber",movil);
//			BasicNameValuePair p1 = new BasicNameValuePair("queue", queue);
//			BasicNameValuePair p14 = new BasicNameValuePair("idServiceClient", "1");
//			BasicNameValuePair p2 = new BasicNameValuePair("key", "RUT");
//			BasicNameValuePair p3 = new BasicNameValuePair("key", "Nombre");
//			BasicNameValuePair p4 = new BasicNameValuePair("key","Categoria");
//			BasicNameValuePair p5 = new BasicNameValuePair("key", "Segmento");
//			BasicNameValuePair p6 = new BasicNameValuePair("key","Aplicacion");
//			BasicNameValuePair p7 = new BasicNameValuePair("key","Opcion");
//			BasicNameValuePair p8 = new BasicNameValuePair("value", datos.get(3));
//			BasicNameValuePair p9 = new BasicNameValuePair("value", datos.get(1));
//			BasicNameValuePair p10 = new BasicNameValuePair("value",datos.get(4));
//			BasicNameValuePair p11 = new BasicNameValuePair("value", datos.get(5));
//			BasicNameValuePair p12 = new BasicNameValuePair("value",datos.get(0));
//			BasicNameValuePair p13 = new BasicNameValuePair("value",datos.get(6));
//
//			parametros.add(p0);
//			parametros.add(p1);
//			parametros.add(p2);
//			parametros.add(p3);
//			parametros.add(p4);
//			parametros.add(p5);
//			parametros.add(p6);
//			parametros.add(p7);
//			parametros.add(p8);
//			parametros.add(p9);
//			parametros.add(p10);
//			parametros.add(p11);
//			parametros.add(p12);
//			parametros.add(p13);
//			parametros.add(p14);
//
//			Log.d("WS_C2C_BEP", "Ejecutando doVirtualHold");
//			Log.d("WS_C2C_BEP", "Servidor: "+servidor + "/doVirtualHold");
//			String resulPost = this.readUrlPost(servidor + "/doVirtualHold", parametros);
//			Log.d("WS_C2C_BEP", "Resultado: "+resulPost);
//			doVh = new Gson().fromJson(resulPost, DoVirtualHold.class);
//
//
//		} catch (Exception e) {
//			Log.e("WS_C2C_BEP-Error", "Error en doVirtualHold " + e.getMessage());
//		}
//		return doVh;
//	}

//	public Contacto doCallback(String movil,String queue,String inicioPeriodo,String terminoPeriodo,ArrayList<String> datos) {
//		Contacto contacto = null;
//		try {
//
//			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
//
//			BasicNameValuePair p0 = new BasicNameValuePair("phoneNumber",movil);
//			BasicNameValuePair p1 = new BasicNameValuePair("queue", queue);
//			BasicNameValuePair p2 = new BasicNameValuePair("beginInterval", inicioPeriodo);
//			BasicNameValuePair p3 = new BasicNameValuePair("endInterval", terminoPeriodo);
//			BasicNameValuePair p16 = new BasicNameValuePair("idServiceClient", "1");
//			BasicNameValuePair p4 = new BasicNameValuePair("key", "RUT");
//			BasicNameValuePair p5 = new BasicNameValuePair("key", "Nombre");
//			BasicNameValuePair p6 = new BasicNameValuePair("key","Categoria");
//			BasicNameValuePair p7 = new BasicNameValuePair("key", "Segmento");
//			BasicNameValuePair p8 = new BasicNameValuePair("key","Aplicacion");
//			BasicNameValuePair p9 = new BasicNameValuePair("key","Opcion");
//			BasicNameValuePair p10 = new BasicNameValuePair("value", datos.get(3));
//			BasicNameValuePair p11 = new BasicNameValuePair("value", datos.get(1));
//			BasicNameValuePair p12 = new BasicNameValuePair("value",datos.get(4));
//			BasicNameValuePair p13 = new BasicNameValuePair("value", datos.get(5));
//			BasicNameValuePair p14 = new BasicNameValuePair("value",datos.get(0));
//			BasicNameValuePair p15 = new BasicNameValuePair("value",datos.get(6));
//
//
//			parametros.add(p0);
//			parametros.add(p1);
//			parametros.add(p2);
//			parametros.add(p3);
//			parametros.add(p4);
//			parametros.add(p5);
//			parametros.add(p6);
//			parametros.add(p7);
//			parametros.add(p8);
//			parametros.add(p9);
//			parametros.add(p10);
//			parametros.add(p11);
//			parametros.add(p12);
//			parametros.add(p13);
//			parametros.add(p14);
//			parametros.add(p15);
//			parametros.add(p16);
//
//			Log.d("WS_C2C_BEP", "Ejecutando doCallback");
//			Log.d("WS_C2C_BEP", "Servidor: " + servidor + "/doCallback");
//			String resulPost = this.readUrlPost(servidor+ "/doCallback", parametros);
//			Log.d("WS_C2C_BEP", "Resultado: " + resulPost);
//			contacto = new Gson().fromJson(resulPost, Contacto.class);
//
//		} catch (Exception e) {
//			Log.e("WS_C2C_BEP-Error", "Error en doCallback " + e.getMessage());
//		}
//		return contacto;
//	}

//	public String setUserDatos(String tree,String nombre,String movil, String rut,
//							   String categoria, String segmento, String opcion,String comentario) {
//
//		String resulPost="";
//		try {
//
//			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
//
//			BasicNameValuePair p0 = new BasicNameValuePair("userANI",movil);
//			BasicNameValuePair p1 = new BasicNameValuePair("key", "RUT");
//			BasicNameValuePair p2 = new BasicNameValuePair("key", "Nombre");
//			BasicNameValuePair p3 = new BasicNameValuePair("key","Categoria");
//			BasicNameValuePair p4 = new BasicNameValuePair("key", "Segmento");
//			BasicNameValuePair p5 = new BasicNameValuePair("key","Aplicacion");
//			BasicNameValuePair p6 = new BasicNameValuePair("key","Opcion");
//			BasicNameValuePair p7 = new BasicNameValuePair("key","Comentarios");
//			BasicNameValuePair p8 = new BasicNameValuePair("value", rut);
//			BasicNameValuePair p9 = new BasicNameValuePair("value", nombre);
//			BasicNameValuePair p10 = new BasicNameValuePair("value",categoria);
//			BasicNameValuePair p11 = new BasicNameValuePair("value", segmento);
//			BasicNameValuePair p12 = new BasicNameValuePair("value",tree);
//			BasicNameValuePair p13 = new BasicNameValuePair("value",opcion);
//			BasicNameValuePair p14 = new BasicNameValuePair("value",comentario);
//
//			parametros.add(p0);
//			parametros.add(p1);
//			parametros.add(p2);
//			parametros.add(p3);
//			parametros.add(p4);
//			parametros.add(p5);
//			parametros.add(p6);
//			parametros.add(p7);
//			parametros.add(p8);
//			parametros.add(p9);
//			parametros.add(p10);
//			parametros.add(p11);
//			parametros.add(p12);
//			parametros.add(p13);
//			parametros.add(p14);
//
//
//			resulPost = this.readUrlPost(Properties.getServidorNodosMovil() + "/attachUserData", parametros);
//
//
//		} catch (Exception e) {
//			String error = e.getMessage();
//			Log.e("WS_C2C_BEP-Error", "Error en setUserDatos " + error);
//		}
//		return resulPost;
//	}


//	public ListaAgendamientos getAgendamientos(String movil) {
//		ListaAgendamientos agendas= new ListaAgendamientos();
//		Agendamientos agenda= new Agendamientos();
//		//movil= "0"+movil;
//		try {
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()
//					+ "/getScheduleRequestByMobile?phoneNumber="+movil, null);
//
//			if(!resulPost.equals("[]")){
//
//				JSONArray array = new JSONArray(resulPost);
//				Agendamientos[] listado = new Agendamientos[array.length()];
//				for (int i = 0; i < array.length(); i++) {
//					JSONObject row = array.getJSONObject(i);
//					agenda = new Gson().fromJson(row.toString(),Agendamientos.class);
//					listado[i]=agenda;
//				}
//				agendas.setValue(listado);
//
//			}else{
//				agendas=null;
//			}
//
//		} catch (Exception e) {
//			String error = e.getMessage();
//			Log.e("WS_C2C_BEP-Error", "Error en getAgendamientos " + error);
//		}
//		return agendas;
//	}

//	public DoVirtualHold doCancelarAgendamiento(String phoneNumber, String reason) {
//		DoVirtualHold dvh= new DoVirtualHold();
//
//		try {
//
//			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
//			BasicNameValuePair p1 = new BasicNameValuePair("phoneNumber", phoneNumber);
//			BasicNameValuePair p2 = new BasicNameValuePair("reason", reason);
//			parametros.add(p1);
//			parametros.add(p2);
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()+ "/doCancelRequest", parametros);
//
//			dvh = new Gson().fromJson(resulPost, DoVirtualHold.class);
//
//		} catch (Exception e) {
//			String error = e.getMessage();
//			Log.e("WS_C2C_BEP-Error", "Error en cancelar Agendamiento " + error);
//		}
//		return dvh;
//	}

}
