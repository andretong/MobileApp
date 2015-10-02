package econtact.org.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import econtact.org.cgi.services.RespuestaGeneral;
import econtact.org.cgi.services.ebank.WS_eBank;
import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;

public class MainActivity extends Activity implements OnClickListener{

    final Context context = this;

    public TextView txtSaludo;
    public EditText txtUser, txtPassword;
    public Button btnLogin;

    public static boolean auth = false;
    public RespuestaGeneral respuesta;
    public int waiting = 0;

    private static final int MY_CUSTOM_DIALOG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        configuracion();
    }


    @Override
    public void onClick(View v){
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        if (v instanceof Button){
//			Util.alerta("Usuario "+txtUser.getText().toString(), MainActivity.this);
            if (!txtUser.getText().toString().equalsIgnoreCase("") && !txtPassword.getText().toString().equalsIgnoreCase("")){
                ejecutarLogin(txtUser.getText().toString(), txtPassword.getText().toString());
            }

        }
    }



    private void ejecutarLogin(String rut, String pass){
        LoginNetwork loginNetwork = new LoginNetwork();

        if (rut.length() > 8){
            loginNetwork.setRut(rut.substring(0, 8));
        }else{
            loginNetwork.setRut(rut);
        }

        loginNetwork.setPass(pass);
        loginNetwork.setContext(MainActivity.this);

        clean();

        loginNetwork.execute();
    }

    private void clean(){
        txtUser.setText("");
        txtPassword.setText("");
    }

    private class LoginNetwork extends AsyncTask<Void, Void, Void> {

        ProgressDialog mProgressDialog;
        private String rut, pass;
        private Context context;

        public String getRut() {
            return rut;
        }

        public void setRut(String rut) {
            this.rut = rut;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
//		public void setComentario(String coment) {
//			this.comentario = coment;
//		}

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            if (auth == false) {
                Util.alerta("Usuario y/o Clave incorrecta",
                        MainActivity.this);
            } else {
                //Intent nextIntent = new Intent(this.getContext(),MainActivity.class);
                Intent nextIntent = new Intent(this.getContext(), InicioActivity.class);
				nextIntent.putExtra("rut", this.getRut());
                startActivity(nextIntent);

            }
        }

        @Override
        protected void onPreExecute() {
            if (!this.getRut().equals("") || !this.getPass().equals("")){
                mProgressDialog = ProgressDialog.show(MainActivity.this,
                        "Cargando...", "Cargando");
            }else{
                Util.alerta("Por favor, introduzca el Usuario y/o Clave.",
                        MainActivity.this);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            WS_eBank ebankClient = new WS_eBank();

            respuesta = ebankClient.autenticar(this.getRut(), this.getPass());
//			this.comentario = getComentario();
//			Cliente cli = login.getCliente(getRut());
            if (respuesta != null) {
                if (respuesta.getCodigo() == 0) {
                    if (respuesta.isRespuesta()) {
                        auth = true;
//						String treeID = respuesta.getTreeID();
//						Tree tree = login.getMenu(treeID);
//						LoginActivity.tree = tree;
//						LoginActivity.cliente = cli;
                    }
                    waiting = 1;
                }else{
                    auth = false;
                }
            }
            return null;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MainActivity.this.finish();
        }
        return true;
    }

    private void configuracion() {
        if (Util.getServidor(this) != null) {
            Log.d("MAIN CONFIG", Util.getServidor(this));
            String[] datosConfig = Util.getServidor(this).split(",");
            Properties.SERVIDORBASE_NODOSMOVIL = datosConfig[0];
            Properties.SERVIDORBASE_WS_EBANK = datosConfig[1];
            Properties.SERVIDORBASE_WS_C2C = datosConfig[2];
            Properties.MOVIL = datosConfig[3];
        } else{
            //CONFIGURABLE
            dialogConfiguration();

            //DEFECTO

        }


    }

    public void dialogConfiguration() {

        Intent intent = new Intent(this, ConfiguracionActivity.class);
        startActivityForResult(intent, MY_CUSTOM_DIALOG);

    }

//    public void defaultConfiguration() {
//
//        EditText txtServidorContactCenter = (EditText) findViewById(R.id.txtServContactCenter);
//        EditText txtServidorDatos = (EditText) findViewById(R.id.txtServDatos);
//        EditText txtServidorC2C = (EditText) findViewById(R.id.txtServidorC2C);
//
//        Properties.SERVIDORBASE_NODOSMOVIL = txtServidorContactCenter.getText().toString();
//        Properties.SERVIDORBASE_WS_EBANK = R.string.SERVIDOR_DATOS;
//        Properties.SERVIDORBASE_WS_C2C = R.string.SERVIDOR_C2C;
//        Properties.MOVIL = datosConfig[3];
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case (MY_CUSTOM_DIALOG): {
                if (resultCode == Activity.RESULT_OK) {
                    Util.alerta("Servidor Configurado", context);
                } else {
                    finish();
                }
                break;
            }

        }
    }

}
