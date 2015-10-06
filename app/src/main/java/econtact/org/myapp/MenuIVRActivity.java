package econtact.org.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import econtact.org.cgi.ivr.Agendamientos;
import econtact.org.cgi.services.RespuestaGeneral;
import econtact.org.cgi.services.RespuestaGeneral2;
import econtact.org.cgi.services.ebank.Cliente;
import econtact.org.cgi.services.ebank.ClienteProducto;
import econtact.org.cgi.services.ebank.WS_eBank;
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;


public class MenuIVRActivity extends Activity implements View.OnClickListener {

    private Context context = this;
    private TextView lblCliente;
    private Button btnContactCenter, btnAgendamientos;
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
        setContentView(R.layout.activity_menu_ivr);

        //Para obtener parametros de otra Activity
        Intent intent = getIntent();

        btnAgendamientos = (Button) findViewById(R.id.btnAgendamientos);
        btnAgendamientos.setOnClickListener(this);

        btnContactCenter = (Button) findViewById(R.id.btnContactCenter);
        btnContactCenter.setOnClickListener(this);

        tlMenuInicio = (TableLayout) findViewById((R.id.tlMenuInicio));

        lblCliente = (TextView) findViewById(R.id.lblCliente);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            actions = (Actions) extras.getSerializable("actions");
            //tree = (Tree) getIntent().getSerializableExtra("tree");
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");
            fono = (String) getIntent().getSerializableExtra("fono");
            opcion = (String) getIntent().getSerializableExtra("opcion");
            optionID = (String) getIntent().getSerializableExtra("optionID");
        }


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


    @Override
    public void onClick(View v) {
        if (v instanceof Button){
            if (v.getId() == R.id.btnContactCenter){

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

    private void inicializar(Intent intent){
        try{
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");

            lblCliente.setText("Bienvenido "+cliente.getNombre() +" "+cliente.getApellido());

        }catch(Exception e){
            String error = e.getMessage();
            e.printStackTrace();
            Log.e("Inicio", "Ha ocurrido un error");
        }finally{
//            lblBienvenida.setText(textBien);
//            lblMensaje.setText("");
        }
    }

    private void menu(Intent intent){
        try{
            String rut = cliente.getRut()+"";
            RespuestaGeneral2 respuesta = ebankClient.ObtenerProductosCliente(rut);

            productos = respuesta.getObjeto();
            Log.d("Productos", "" + productos.length);

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
                    text = "Cuentas Corrientes ";
                    tv1.setId(i);
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.icono_producto_chico);
                }else if(producto.getTipoProducto() == 2){
                    text = "Tarjetas de Credito";
                    tv1.setId(i);
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.icono_planes_chico);
                }else if(producto.getTipoProducto() == 3){
                    text = "Creditos Hipotecario";
                    tv1.setId(i);
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.icono_deudarecarga_chico);
                }

                tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tv1.setOnClickListener(this);
                img.setImageResource(R.drawable.linea1);

                LinearLayout.LayoutParams ln1p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ln1p.setMargins(1, 8, 0, 8);
                llp.setMargins(1, 0, 0, 0); // llp.setMargins(left, top, right,

                ln1.setLayoutParams(ln1p);
                ln1.addView(imv);
                ln1.addView(tv1);
                ln1.addView(img);

                ln.addView(ln1);
//                ln.addView(img);

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

}
