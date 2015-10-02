package econtact.org.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import econtact.org.android.TextviewMenu;
import econtact.org.cgi.ivr.ActionProperties;
import econtact.org.cgi.ivr.Actions;
import econtact.org.cgi.services.RespuestaGeneral;
import econtact.org.cgi.services.RespuestaGeneral2;
import econtact.org.cgi.services.ebank.Cliente;
import econtact.org.cgi.services.ebank.ClienteProducto;
import econtact.org.cgi.services.ebank.WS_eBank;
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;


public class Inicio_v2 extends Activity implements View.OnClickListener {
    private Context context = this;
//    private TextView lblBienvenida, lblMensaje;
    private Button btnIVR, btnContactCenter;
//    private TableLayout tlMenuInicio;


    private WS_IVR ivrws = new WS_IVR();
    private WS_eBank ebankClient = new WS_eBank();

    private Cliente cliente;
    public Actions actions;
    private Object[] productos;


    public String fono,opcion,optionID;

    private static final int MY_CUSTOM_DIALOG = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_v2);

        //Para obtener parametros de otra Activity
        Intent intent = getIntent();
//        lblBienvenida = (TextView) findViewById(R.id.lblBienvenida);
//        lblMensaje = (TextView) findViewById(R.id.lblMensaje);

        btnIVR = (Button) findViewById(R.id.btnIVR);
        btnIVR.setOnClickListener(this);

        btnContactCenter = (Button) findViewById(R.id.btnContactCenter);
        btnContactCenter.setOnClickListener(this);

//        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
//        btnConfiguracion.setOnClickListener(this);

//        tlMenuInicio = (TableLayout) findViewById((R.id.tlMenuInicio));

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            inicializar(intent);
        }
    }

    public void inicializar(Intent intent){
        String textBien = "Bienvenido";
        try{
            String rut = intent.getStringExtra("rut");
            RespuestaGeneral respuesta = ebankClient.ObtenerDatosCliente(rut);

            cliente = new Gson().fromJson(respuesta.getObjeto().toString(), Cliente.class);
            Log.d("Inicio", respuesta.getObjeto().toString());

            textBien = "Bienvenido \n "+cliente.getNombre() +" "+cliente.getApellido();

            if (Util.getServidor(this) != null) {
                String[] datosConfig = Util.getServidor(this).split(",");
                Properties.SERVIDORBASE_NODOSMOVIL = datosConfig[0];
                Properties.SERVIDORBASE_WS_EBANK = datosConfig[1];
                Properties.SERVIDORBASE_WS_C2C = datosConfig[2];

                String movil = (datosConfig[3].length() == 9) ? datosConfig[3] : "9"+datosConfig[3];
                Properties.MOVIL = movil;

                String json = datosConfig[0] +","+datosConfig[1]+","+datosConfig[2]+","+movil;
                Log.i("InicioConfig", "Guardando Telefono Ingresado "+json);
                if(0 < Util.setServidor(json, this)){
                    //Util.alerta("Informacion actualizada.", this);
                }else{
                    //Util.alerta("Ha ocurrido un inconveniente al guardar el servidor, por favor intente de nuevo.", this);
                }
            }

        }catch(Exception e){
            String error = e.getMessage();
            e.printStackTrace();
            Log.e("Inicio", "Ha ocurrido un error");
            textBien = "Bienvenido";
        }finally{
//            lblBienvenida.setText(textBien);
//            lblMensaje.setText("");
        }

    }


    @Override
    public void onClick(View v) {
        if (v instanceof Button){
            if (v.getId() == R.id.btnContactCenter){
                /*
                Intent nextIntent = new Intent(this, ContactoActivity.class);
                nextIntent.putExtra("rut", this.cliente.getRut());
                nextIntent.putExtra("nombre", this.cliente.getNombre());
                nextIntent.putExtra("apellido", this.cliente.getApellido());
                nextIntent.putExtra("telefono", this.cliente.getTelefono());
                nextIntent.putExtra("correo", this.cliente.getCorreo());
                startActivity(nextIntent);
                */

                actions = ivrws.getFono_v1(String.valueOf("10"));

                if (actions != null){
                    ActionProperties[] aProperties = actions.getActions();

                    if (actions.getActions() != null) {
                        for (int i = 0; i < aProperties.length; i++) {
                            fono = aProperties[0].getNumeroTransferencia();
                        }
                    }

                    opcion="Inicio";
                    optionID="10";

                    Intent intent = new Intent(this, AccionesActivity.class);
                    intent.putExtra("actions", actions);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("fono", fono);
                    intent.putExtra("opcion", opcion);
                    intent.putExtra("optionID", optionID);
                    startActivity(intent);
                }else{
                    Util.alerta("Lo sentimos, en estos momentos no tenemos acceso al Contact Center.", this);
                }

            }else if (v.getId() == R.id.btnIVR){
                Intent nextIntent = new Intent(this, MenuIVRActivity.class);
                nextIntent.putExtra("cliente", cliente);
                startActivity(nextIntent);
                //dialogConfiguration();
            }
        }else if (v instanceof  TextviewMenu){
            Log.d("Presione MENU", "Presione MENU " + v.getId());
            Intent intent = new Intent(this, ProductoActivity.class);
            intent.putExtra("actions", actions);
            intent.putExtra("cliente", cliente);
            intent.putExtra("fono", fono);
            intent.putExtra("opcion", opcion);
            intent.putExtra("optionID", optionID);
            ClienteProducto producto = new Gson().fromJson(productos[v.getId()].toString(), ClienteProducto.class);
            intent.putExtra("producto", producto);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir")
                    .setMessage("Deseas salir?")
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            Inicio_v2.this.finish();
                        }
                    }).show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

}
