package econtact.org.cgi.services.ebank;

import android.annotation.SuppressLint;
import android.content.Context;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import econtact.org.cgi.services.RespuestaGeneral;
import econtact.org.cgi.services.RespuestaGeneral2;
import econtact.org.cgi.util.ConexionHTTP;
import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;


@SuppressLint({ "NewApi" })
public class WS_eBank {
	/*
	 * Obtiene el contenido de una url, pasa solamente parametros en post, los get los pone dentro 
	 * de la url y verifica si los parametros son nulos.
	 */
	
	//public String servidor = "http://10.36.66.136:8080/WS_eBank/services";
	//public String servidor = "http://54.232.208.223:8080/WS_eBank/services";
//	public String servidor = "http://gesys-demo.e-contact.cl:8080/WS_eBank/services";
	public String servidor = Properties.getServidorDatosCliente();
   	private ConexionHTTP conexion = new ConexionHTTP();
	
	
	public RespuestaGeneral autenticar(String rut, String pasw) {
		RespuestaGeneral respuestaLogin = null;
		try {
			String url = servidor + "/autenticar?rut=" + rut + "&password=" + pasw;
			Log.d("autenticar", url);
			String resulPost = conexion.performGetCall(url);
			Log.d("autenticar", ""+resulPost);
			respuestaLogin = new Gson().fromJson(resulPost, RespuestaGeneral.class);

		} catch (Exception e) {
			String error = e.getMessage();
			Log.e("WS_eBank-Error", "Error en login " + error);
		}
		return respuestaLogin;
	}


	public RespuestaGeneral ObtenerDatosCliente(String rut) {
		RespuestaGeneral respuestaLogin = null;
		try {
			String url = servidor + "/datoscliente?rut=" + rut;
			Log.d("ObtenerDatosCliente", url);
			String resulPost = conexion.performGetCall(url);
			Log.d("ObtenerDatosCliente", ""+resulPost);
			respuestaLogin = new Gson().fromJson(resulPost, RespuestaGeneral.class);

		} catch (Exception e) {
            e.printStackTrace();
            String error = e.getMessage();
            Log.e("WS_eBank-Error", "Error Obtener Datos Cliente " + error);

		}
		return respuestaLogin;
	}


	public RespuestaGeneral2 ObtenerProductosCliente(String rut) {
		RespuestaGeneral2 respuestaLogin = null;
		try {
			String url = servidor + "/productos?rut=" + rut;
			Log.d("ObtenerProductosCliente", url);
			String resulPost = conexion.performGetCall(url);
			Log.d("ObtenerProductosCliente", ""+resulPost);
			respuestaLogin = new Gson().fromJson(resulPost, RespuestaGeneral2.class);

		} catch (Exception e) {
			e.printStackTrace();
			String error = e.getMessage();
			Log.e("WS_eBank-Error", "Error ObtenerProductosCliente " + error);

		}
		return respuestaLogin;
	}

}
