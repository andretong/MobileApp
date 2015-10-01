package econtact.org.cgi.services.ivrmovil;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import econtact.org.cgi.ivr.Actions;
import econtact.org.cgi.ivr.Agendamientos;
import econtact.org.cgi.ivr.Contacto;
import econtact.org.cgi.ivr.DoVirtualHold;
import econtact.org.cgi.ivr.ListaAgendamientos;
import econtact.org.cgi.util.ConexionHTTP;
import econtact.org.cgi.util.Properties;


@SuppressLint({ "NewApi" })
public class WS_IVR {
	/*
	 * Obtiene el contenido de una url, pasa solamente parametros en post, los get los pone dentro 
	 * de la url y verifica si los parametros son nulos.
	 */
	
	//public String servidor = "http://10.36.66.136:8080/WS_eBank/services";
//	public String servidor = "http://54.232.208.223:8080/WS_eBank/services";
	public String servidor = Properties.getServidorNodosMovil();
	private ConexionHTTP conexion = new ConexionHTTP();


	public Actions getFono_v1(String nodeid) {
		Actions actions = null;
		try {
			Log.d("getFono_v1", servidor + "/getNodeActions?nodeID=" + nodeid);
			//String resulPost = this.getWebServiceResult(Properties.getServidor() + "/getNodeActions?nodeID=" + Integer.parseInt(nodeid));
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil() + "/getNodeActions?nodeID=" + Integer.parseInt(nodeid), null);
			String resulPost = conexion.performGetCall(servidor + "/getNodeActions?nodeID=" + nodeid);
			Log.d("getFono_v1", resulPost);
			actions = new Gson().fromJson(resulPost, Actions.class);

		} catch (Exception e) {
			String error = e.getMessage();
			Log.e("WS_IVRNodos-Error", "getFono_v1 = " + error);
		}
		return actions;
	}

//	public Actions getFono_v1(String nodeid) {
//		Actions actions = null;
//		try {
//			String urlEje = Properties.getServidorNodosMovil() + "/getNodeActions?nodeID=" + nodeid;
//			Log.d("getFono_v1", urlEje);
//			//String resulPost = this.getWebServiceResult(Properties.getServidor() + "/getNodeActions?nodeID=" + Integer.parseInt(nodeid));
//			String resulPost = this.readUrlPost(urlEje, null);
//			Log.d("getFono_v1", resulPost);
//			actions = new Gson().fromJson(resulPost, Actions.class);
//
//		} catch (Exception e) {
//			String error = e.getMessage();
//			Log.e("WS_IVR-Error", "getFono_v1 = " + error);
//		}
//		return actions;
//	}

//	public DoVirtualHold doVirtualHold(String movil, String queue, ArrayList<String> datos) {
//		DoVirtualHold doVh = null;
//		try {
//
//			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
//
//			BasicNameValuePair p0 = new BasicNameValuePair("phoneNumber",movil);
//			BasicNameValuePair p1 = new BasicNameValuePair("queue", queue);
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
//
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()+ "/doVirtualHold", parametros);
//
//			doVh = new Gson().fromJson(resulPost, DoVirtualHold.class);
//
//
//		} catch (Exception e) {
//			Log.e("WS_IVR-Error", "Error en doVirtualHold " + e.getMessage());
//		}
//		return doVh;
//	}
//
//
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
//
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()+ "/doCallback", parametros);
//
//			contacto = new Gson().fromJson(resulPost, Contacto.class);
//
//		} catch (Exception e) {
//			Log.e("WS_IVR-Error", "Error en doCallback " + e.getMessage());
//		}
//		return contacto;
//	}
//
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
//			Log.e("WS_IVR-Error", "Error en setUserDatos " + error);
//		}
//		return resulPost;
//	}
//
//
	public ListaAgendamientos getAgendamientos(String movil) {
		ListaAgendamientos agendas= new ListaAgendamientos();
		Agendamientos agenda= new Agendamientos();
		//movil= "0"+movil;
		try {
			String url = servidor + "/getScheduleRequestByMobile?phoneNumber="+movil;
			Log.d("ListaAgendamientos", "Servidor: "+url);
//			String resultPost = this.readUrlPost(url, null);
//			String resultPost = conexion.performPostCall(url, null);
			String resultPost = conexion.performGetCall(url);


			if((!resultPost.equals("[]")) || resultPost != null) {

				JSONArray array = new JSONArray(resultPost);
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
			Log.e("WS_IVR-Error", "Error en getAgendamientos " + error);
		}
		return agendas;
	}

	public DoVirtualHold doCancelarAgendamiento(String phoneNumber, String reason) {
		DoVirtualHold dvh= new DoVirtualHold();

		try {
			String url = servidor + "/doCancelRequest";

//			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
//			BasicNameValuePair p1 = new BasicNameValuePair("phoneNumber", phoneNumber);
//			BasicNameValuePair p2 = new BasicNameValuePair("reason", reason);
//			parametros.add(p1);
//			parametros.add(p2);
//			String resulPost = this.readUrlPost(Properties.getServidorNodosMovil()+ "/doCancelRequest", parametros);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idServiceClient", "1");
			parametros.put("phoneNumber", phoneNumber);
			parametros.put("reason", reason);

			String resultPost = conexion.performPostCall(url, parametros);

			dvh = new Gson().fromJson(resultPost, DoVirtualHold.class);

		} catch (Exception e) {
			String error = e.getMessage();
			Log.e("WS_IVR-Error", "Error en cancelar Agendamiento " + error);
		}
		return dvh;
	}

}
