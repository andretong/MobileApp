package econtact.org.cgi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.util.Log;

public class HttpUtil {
	public static String readUrlPost(String urloriginal,
			List<NameValuePair> parametros) throws MalformedURLException {
		String resultado="";
		try {
			// URL url = new URL(urloriginal);
			// HttpURLConnection urlConnection = (HttpURLConnection)
			// url.openConnection();
			
			// InputStream in = new
			// BufferedInputStream(urlConnection.getInputStream());
			//String inputLine;
			/***/
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urloriginal);

			// Add your data
			if(parametros!=null)
				httppost.setEntity(new UrlEncodedFormEntity(parametros));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			 resultado = getResponseBody(response);
			/****/

		
		} catch (Exception e) {
			
			Log.v("LOGISTICA",e.getMessage());
			
			return "-1";
		}
		return resultado;
	}

	public static String _getResponseBody(HttpEntity entity)
			throws IOException, ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		InputStream instream = entity.getContent();

		if (instream == null) {
			return "";
		}

		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(

			"HTTP entity too large to be buffered in memory");
		}

		String charset = getContentCharSet(entity);

		if (charset == null) {

			charset = HTTP.DEFAULT_CONTENT_CHARSET;

		}

		Reader reader = new InputStreamReader(instream, charset);

		StringBuilder buffer = new StringBuilder();

		try {

			char[] tmp = new char[1024];

			int l;

			while ((l = reader.read(tmp)) != -1) {

				buffer.append(tmp, 0, l);

			}

		} finally {

			reader.close();

		}

		return buffer.toString();

	}

	public static String getContentCharSet(final HttpEntity entity)
			throws ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		String charset = null;

		if (entity.getContentType() != null) {

			HeaderElement values[] = entity.getContentType().getElements();

			if (values.length > 0) {

				NameValuePair param = values[0].getParameterByName("charset");

				if (param != null) {

					charset = param.getValue();

				}

			}

		}

		return charset;

	}
/*
 * No usar directamente, obtiene el cuerpo de una peticion
 */
	public static String getResponseBody(HttpResponse response) {

		String response_text = null;

		HttpEntity entity = null;

		try {

			entity = response.getEntity();

			response_text = _getResponseBody(entity);

		} catch (ParseException e) {

			e.printStackTrace();

		} catch (IOException e) {

			if (entity != null) {

				try {

					entity.consumeContent();

				} catch (IOException e1) {

				}

			}

		}

		return response_text;

	}
	/*
	 * Obtiene un imagen desde la web
	 */
	public static Drawable LoadImageFromWebOperations(String url) {
		/*
		if(Build.VERSION.SDK_INT>8){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}*/
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	
}
