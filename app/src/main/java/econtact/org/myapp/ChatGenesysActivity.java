package econtact.org.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.ebank.Cliente;


public class ChatGenesysActivity extends Activity implements View.OnClickListener {

    WebView webViewChat;
    private ProgressDialog mProgress;

    private WS_IVR ivrws;

    public Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_genesys);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        Log.d("CHAT", "Cliente "+cliente.getNombre()+" "+cliente.getCorreo());

        String url = "http://chat-gsys.e-contact.cl/eservicesweb/chat/HtmlChatFrameSet.jsp?RoutingData=&AdditionalData=";
//        String url = "http://g1aux001.e-contact.cl:8080/eservicesweb/chat/HtmlChatFrameSet.jsp?RoutingData=&AdditionalData=";
//        String url = "http://10.33.16.22:8080/eservicesweb/chat/HtmlChatFrameSet.jsp?RoutingData=&AdditionalData=";
        //url += "EmailAddress;JOSEALVAREZ%40correo.com;FirstName;JOSE;LastName;ALVAREZ";
        url += "EmailAddress;"+cliente.getCorreo().replace("@", "%40")+";FirstName;"+cliente.getNombre()+";LastName;"+cliente.getApellido()+";RUT;"+cliente.getRut()+";Cliente;eBank";

        Log.d("ChatGenesys", "URL "+url);
        //url += "mobile="+attachData.get(2)+"&firstName="+nombre+"&lastName="+apellido;

        webViewChat = (WebView) findViewById(R.id.webViewChat);
        WebSettings webSettings = webViewChat.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mProgress = ProgressDialog.show(this, "Cargando...", "Por favor espera un momento..");

        webViewChat.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (mProgress.isShowing()) {
                    mProgress.dismiss();
                }
            }
        });
        Log.d("ChatGenesys", "Cargando URL "+url);
        webViewChat.loadUrl(url);
    }


    @Override
    public void onClick(View v) {
        try {

            WebView wv1=(WebView)findViewById(R.id.webViewChat);
            WebSettings webSettings = wv1.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wv1.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //view.loadUrl(url_cierre);
                    return true;
                }
            });
            wv1.loadUrl("");

        } catch (Exception e) {
            e.getMessage();
        }

        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {

                WebView wv1=(WebView)findViewById(R.id.webViewChat);
                WebSettings webSettings = wv1.getSettings();
                webSettings.setJavaScriptEnabled(true);
                wv1.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                wv1.loadUrl("");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.getMessage();
            }

            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
