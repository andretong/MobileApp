package econtact.org.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import econtact.org.android.TextviewMenu;
import econtact.org.cgi.ivr.ActionProperties;
import econtact.org.cgi.ivr.Actions;
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.ebank.Cliente;
import econtact.org.cgi.services.ebank.ClienteProducto;
import econtact.org.cgi.services.ebank.WS_eBank;


public class ProductoActivity extends Activity implements View.OnClickListener {

    private Context context = this;
    private TextView lblNumero, lblSaldo;

    private Button btnAgendamientos, btnContacto, btnConfiguracion;


    private WS_IVR ivrws = new WS_IVR();
    private WS_eBank ebankClient = new WS_eBank();

    private Cliente cliente;
    public Actions actions;
    private ClienteProducto producto;


    public String fono,opcion,optionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        //Para obtener parametros de otra Activity
        Intent intent = getIntent();
        lblNumero = (TextView) findViewById(R.id.lblProducto);
        lblSaldo = (TextView) findViewById(R.id.lblSaldo);

        btnAgendamientos = (Button) findViewById(R.id.button1);
        btnAgendamientos.setOnClickListener(this);

        btnContacto = (Button) findViewById(R.id.button2);
        btnContacto.setOnClickListener(this);

        btnConfiguracion = (Button) findViewById(R.id.button3);
        btnConfiguracion.setOnClickListener(this);

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
        producto = (ClienteProducto) intent.getSerializableExtra("producto");
        lblNumero.setText(producto.getNumeroProducto());
        lblSaldo.setText(""+producto.getSaldo());
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

            }else if (v.getId() == R.id.btnConfiguracion){
                Intent nextIntent = new Intent(this, ConfiguracionActivity.class);
                startActivity(nextIntent);
                //dialogConfiguration();
            }else if (v.getId() == R.id.btnAgendamientos){
                Intent nextIntent = new Intent(this, AgendamientosActivity.class);
                startActivity(nextIntent);
                //dialogConfiguration();
            }

        }else if (v instanceof TextviewMenu){
            Log.d("Presione MENU", "Presione MENU " + v.getId());
        }
    }

}
