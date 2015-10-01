package econtact.org.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
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
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.RespuestaGeneral;
import econtact.org.cgi.services.RespuestaGeneral2;
import econtact.org.cgi.services.ebank.Cliente;
import econtact.org.cgi.services.ebank.ClienteProducto;
import econtact.org.cgi.services.ebank.WS_eBank;
import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;


public class InicioActivity extends Activity implements View.OnClickListener {

    private Context context = this;
    private TextView lblBienvenida, lblMensaje;
    private Button btnAgendamientos, btnContacto, btnConfiguracion;
    private TableLayout tlMenuInicio;


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
        setContentView(R.layout.activity_inicio);

        //Para obtener parametros de otra Activity
        Intent intent = getIntent();
        lblBienvenida = (TextView) findViewById(R.id.lblBienvenida);
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);

        btnAgendamientos = (Button) findViewById(R.id.btnAgendamientos);
        btnAgendamientos.setOnClickListener(this);

        btnContacto = (Button) findViewById(R.id.btnContacto);
        btnContacto.setOnClickListener(this);

        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
        btnConfiguracion.setOnClickListener(this);

        tlMenuInicio = (TableLayout) findViewById((R.id.tlMenuInicio));

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            inicializar(intent);
            menu(intent);

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
            lblBienvenida.setText(textBien);
            lblMensaje.setText("");
        }

    }

    private void menu(Intent intent){
        try{
            String rut = intent.getStringExtra("rut");
            RespuestaGeneral2 respuesta = ebankClient.ObtenerProductosCliente(rut);

            productos = respuesta.getObjeto();
            Log.d("Productos", ""+productos.length);

            for (int i=0; i < productos.length; i++){
                ClienteProducto producto = new Gson().fromJson(productos[i].toString(), ClienteProducto.class);

                TableRow tr1 = new TableRow(this);
                LinearLayout ln = new LinearLayout(this);
                LinearLayout ln1 = new LinearLayout(this);
                ln1.setOrientation(LinearLayout.HORIZONTAL);
                ln.setOrientation(LinearLayout.VERTICAL);
                TextviewMenu tv1 = new TextviewMenu(this);

                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                llp.setMargins(40, 20, 0, 0);
                tv1.setLayoutParams(llp);
                tv1.setTextColor(getResources().getColorStateList(R.color.primary_dark_material_dark));
                ImageView imv = new ImageView(this);
                ImageView img = new ImageView(this);

                String text = "";
                if(producto.getTipoProducto() == 1){
                    text = "Cuenta Corriente \n"+ producto.getNumeroProducto();
                    tv1.setId(i);
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_cuenta);
                }else if(producto.getTipoProducto() == 2){
                    text = "Tarjeta Credito \n"+ producto.getNumeroProducto();
                    tv1.setId(i);
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_tarjeta);
                }else if(producto.getTipoProducto() == 3){
                    text = "Credito Hipotecario \n"+ producto.getNumeroProducto();
                    tv1.setId(i);
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_credito);
                }

                tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tv1.setOnClickListener(this);
                //img.setImageResource(R.drawable.linea1);

                LinearLayout.LayoutParams ln1p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ln1p.setMargins(1, 8, 0, 8);
                llp.setMargins(1, 0, 0, 0); // llp.setMargins(left, top, right,

                ln1.setLayoutParams(ln1p);
                ln1.addView(imv);
                ln1.addView(tv1);
                //ln1.addView(rb1);
                ln.addView(ln1);
                ln.addView(img);
                tr1.addView(ln);
                tlMenuInicio.addView(tr1);

            }

        }catch(Exception e){
            e.printStackTrace();
            String error = e.getMessage();
            Log.e("Menu", " "+error);
        }finally{
        }
    }


    @Override
    public void onClick(View v) {
        if (v instanceof Button){
            if (v.getId() == R.id.btnContacto){
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

        }else if (v.getId() == R.id.btnConfiguracion){
            Intent nextIntent = new Intent(this, ConfiguracionActivity.class);
            startActivity(nextIntent);
            //dialogConfiguration();
        }else if (v.getId() == R.id.btnAgendamientos){
            Intent nextIntent = new Intent(this, AgendamientosActivity.class);
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
                            InicioActivity.this.finish();
                        }
                    }).show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }



}
