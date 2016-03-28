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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import econtact.org.cgi.services.c2c.WS_C2C_BEP;
import econtact.org.cgi.services.ebank.Cliente;

public class ChatInteractiveActivity  extends Activity implements View.OnClickListener {

    WebView webViewChatCIC;
    private ProgressDialog mProgress;

    public Cliente cliente;
    private WS_C2C_BEP ws_c2c_bep = new WS_C2C_BEP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_genesys);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        Log.d("ChatCIC", "Cliente " + cliente.getNombre() + " " + cliente.getCorreo());

        //CHAT INTERACTIVE
        String url = "http://10.33.16.35/I3Root/Server1/websvcs/chat/start";
        String resultado = ws_c2c_bep.doChat_CIC(url, cliente);
        Log.d("ChatCIC", "Resultado Chat CIC:" + resultado);

        String idChat = "";
        try {
            JSONObject jsonObj = new JSONObject(resultado).getJSONObject("chat");
            JSONObject status = jsonObj.getJSONObject("status");

            if(status.getString("type").equals("success")){
                idChat = jsonObj.getString("participantID");
                //url = "http:/10.33.16.35/I3Root/Server1/websvcs/chat/poll/"+idChat;
                url = "http://10.33.16.35/i3root/";

                Log.d("ChatCIC", "URL "+url);

                webViewChatCIC = (WebView) findViewById(R.id.webViewChatCIC);
                WebSettings webSettings = webViewChatCIC.getSettings();
                webSettings.setJavaScriptEnabled(true);
                mProgress = ProgressDialog.show(this, "Cargando...", "Por favor espera un momento..");

                webViewChatCIC.setWebViewClient(new WebViewClient() {
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
                Log.d("ChatCIC", "Cargando URL " + url);
                webViewChatCIC.loadUrl(url);
            }else{
                Toast.makeText(getApplicationContext(), "El Chat no pudo generarse, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.d("JSONException", e.getLocalizedMessage());
        }

    }


    @Override
    public void onClick(View v) {
        try {

            WebView wv1=(WebView)findViewById(R.id.webViewChatCIC);
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

                WebView wv1=(WebView)findViewById(R.id.webViewChatCIC);
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
