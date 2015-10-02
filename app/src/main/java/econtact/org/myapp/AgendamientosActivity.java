package econtact.org.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import econtact.org.android.TextviewMenu;
import econtact.org.cgi.ivr.DoVirtualHold;
import econtact.org.cgi.ivr.ListaAgendamientos;
import econtact.org.cgi.services.c2c.WS_C2C_BEP;
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.ebank.Cliente;
import econtact.org.cgi.util.Properties;


public class AgendamientosActivity extends Activity implements View.OnClickListener {

    public String fono;
    public Cliente cliente;
    public WS_IVR ws_ivr = new WS_IVR();
    public ListaAgendamientos listaAgendamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamientos);

        Bundle extras = getIntent().getExtras();

        String movil = Properties.MOVIL;
        Log.d("Agendamientos", "Consultando Agendamientos "+movil);

//        listaAgendamiento = ws_ivr.getAgendamientos(movil);
//        fono = (String) getIntent().getSerializableExtra("fono");


        WS_C2C_BEP ws_c2C_bep = new WS_C2C_BEP();
        listaAgendamiento = ws_c2C_bep.getAgendamientos(movil);

        if (listaAgendamiento != null){
            inicializar();
        }

    }

    private void inicializar(){
        TableLayout tl = (TableLayout) findViewById(R.id.tlAgenda);
        tl.removeAllViews();

        for (int i = 0; i < listaAgendamiento.getValue().length; i++) {
            TableRow tr1 = new TableRow(this);
            LinearLayout ln = new LinearLayout(this);
            LinearLayout ln1 = new LinearLayout(this);
            ln1.setOrientation(LinearLayout.HORIZONTAL);
            ln.setOrientation(LinearLayout.VERTICAL);
            TextviewMenu tv1 = new TextviewMenu(this);

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 51);
            llp.setMargins(20, 0, 0, 0);
            tv1.setLayoutParams(llp);
            tv1.setText(convertirFecha(listaAgendamiento.getValue()[i].getStartDate().toString().trim()));
//            tv1.setTextColor(getResources().getColorStateList(R.color.primary_dark_material_dark));
            ImageView imv = new ImageView(this);
            ImageView img = new ImageView(this);
            imv.setBackgroundResource(R.drawable.icono_producto_chico);

            // Por cada tipo conocido de pantalla ponemos el icono y
            // seteamos el id del textview
            // menus[i].getOptionImage();
            imv.setBackgroundResource(R.drawable.agendamientos);
            tv1.setId(Integer.parseInt(listaAgendamiento.getValue()[i].getId()));

            tv1.setTextSize(20);
            tv1.setOnClickListener(this);

            img.setImageResource(R.drawable.linea1);

            LinearLayout.LayoutParams ln1p = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ln1p.setMargins(10, 8, 0, 8);
            llp.setMargins(20, 0, 0, 0); // llp.setMargins(left, top, right,

            ln1.setLayoutParams(ln1p);
            ln1.addView(imv);
            ln1.addView(tv1);

            ln.addView(ln1);
            ln.addView(img);
            tr1.addView(ln);
            tl.addView(tr1);

        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        // Validar el horario de agendamiento
        String titulo = "Agendamiento";
        String valido = "Desea cancelar el agendamiento "+ ((TextviewMenu) v).getText() +" ?";
        String invalido = "Error al cancelar, intente nuevamente.";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgendamientosActivity.this);
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(valido);

        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DoVirtualHold resultado = ws_ivr.doCancelarAgendamiento(Properties.MOVIL+"."+String.valueOf(id), "Cancelado desde Android.");
                        Toast.makeText(getApplicationContext(), "Agendamiento Cancelado.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Salir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.show();
    }


    public String convertirFecha(String epoch){
        long dato=Long.parseLong(epoch);
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(dato));
        return date;
    }
}
