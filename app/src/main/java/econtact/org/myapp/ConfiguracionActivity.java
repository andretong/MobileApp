package econtact.org.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;


public class ConfiguracionActivity extends Activity implements View.OnClickListener {

    private EditText txtServidorContactCenter;
    private EditText txtServidorDatos;
    private EditText txtServidorC2C;
    private EditText txtMovil;
    private Button btnConfiguracionGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        txtServidorContactCenter = (EditText) findViewById(R.id.txtServContactCenter);
        txtServidorDatos = (EditText) findViewById(R.id.txtServDatos);
        txtMovil = (EditText) findViewById(R.id.txtMovil);
        txtServidorC2C = (EditText) findViewById(R.id.txtServidorC2C);

        btnConfiguracionGuardar = (Button) findViewById(R.id.btnConfiguracionGuardar);
        btnConfiguracionGuardar.setOnClickListener(this);

        verificarProperties();
    }

    private void verificarProperties(){

        if (Util.getServidor(this) != null) {
            String[] datosConfig = Util.getServidor(this).split(",");
            Properties.SERVIDORBASE_NODOSMOVIL = datosConfig[0];
            Properties.SERVIDORBASE_WS_EBANK = datosConfig[1];
            Properties.SERVIDORBASE_WS_C2C = datosConfig[2];
            Properties.MOVIL = datosConfig[3];

            txtServidorContactCenter.setText(datosConfig[0]);
            txtServidorDatos.setText(datosConfig[1]);
            txtServidorC2C.setText(datosConfig[2]);
            txtMovil.setText(datosConfig[3]);
        }

    }


    @Override
    public void onClick(View v) {
        String json="";
        if(v instanceof Button){

            if(txtServidorContactCenter.getText().toString().length()==0){
                txtServidorContactCenter.setError("Por favor ingrese un valor");
            }else if(txtServidorDatos.getText().toString().length()==0){
                txtServidorDatos.setError("Por favor ingrese un valor");
            }else if(txtServidorC2C.getText().toString().length()==0){
                txtServidorC2C.setError("Por favor ingrese un valor");
            }else if(txtMovil.getText().toString().length()==0){
                txtMovil.setError("Por favor ingrese un valor");
            }else{
                String movil = (txtMovil.getText().toString().length() == 8) ? "9"+txtMovil.getText().toString() : txtMovil.getText().toString();
                json=txtServidorContactCenter.getText().toString()+","+txtServidorDatos.getText().toString()+","+txtServidorC2C.getText().toString()+","+movil;

                Properties.SERVIDORBASE_NODOSMOVIL = txtServidorContactCenter.getText().toString();
                Properties.SERVIDORBASE_WS_EBANK = txtServidorDatos.getText().toString();
                Properties.SERVIDORBASE_WS_C2C = txtServidorC2C.getText().toString();
                Properties.MOVIL = movil;

                Log.d("CONFIGURACION", json);
                if(0 < Util.setServidor(json, this)){
                    //Util.alerta("Servidor guardado con exito, debe salir y volver a entrar a la aplicacion para que los cambios tengan efectos.", this);
                    Intent resultIntent = new Intent(this, InicioActivity.class);
                    resultIntent.putExtra("mensaje", "Servidor guardado con exito, debe salir y volver a entrar a la aplicacion para que los cambios tengan efectos.");
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }else{
                    Util.alerta("Ha ocurrido un inconveniente al guardar el servidor, por favor intente de nuevo.", this);
                }


            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return true;
    }
}
