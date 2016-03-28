package econtact.org.myapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import econtact.org.android.TextviewMenu;
import econtact.org.cgi.ivr.ActionProperties;
import econtact.org.cgi.ivr.Actions;
import econtact.org.cgi.ivr.DoVirtualHold;
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.c2c.WS_C2C_BEP;
import econtact.org.cgi.services.ebank.Cliente;
import econtact.org.cgi.util.Properties;
import econtact.org.cgi.util.Util;


public class AccionesActivity extends Activity implements View.OnClickListener {

    public RadioGroup rgbCall;
    public String opcion, fono,optionID,actionID;

    public Cliente cliente;
    public WS_IVR ws_ivr = new WS_IVR();
    WS_C2C_BEP ws_c2c_bep = new WS_C2C_BEP();

    public Actions actions;
    public ActionProperties aProperties = null;
    private ArrayList<String> datos;

    public static final String SEARCH_QUERY_RESULT_FROM_DIALOG = "SEARCH_DIALOG";
    public static final int CALLBACK_RESULT_FROM_DIALOG = 1;
    public static final int AGENDAMIENTO_RESULT_FROM_DIALOG = 2;
    public static final int CHAT_RESULT_FROM_DIALOG = 3;

    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    public EditText txtComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);

        txtComentario = (EditText) findViewById(R.id.txtComentario);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            actions = (Actions) extras.getSerializable("actions");
            //tree = (Tree) getIntent().getSerializableExtra("tree");
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");
            fono = (String) getIntent().getSerializableExtra("fono");
            opcion = (String) getIntent().getSerializableExtra("opcion");
            optionID= (String) getIntent().getSerializableExtra("optionID");

            TableLayout tl = (TableLayout) findViewById(R.id.tlContacto);
            for (int i = 0; i < actions.getActions().length; i++) {
                TableRow tr1 = new TableRow(this);
                LinearLayout ln = new LinearLayout(this);
                LinearLayout ln1 = new LinearLayout(this);
                ln1.setOrientation(LinearLayout.HORIZONTAL);
                ln.setOrientation(LinearLayout.VERTICAL);
                TextviewMenu tv1 = new TextviewMenu(this);

                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                llp.setMargins(40, 20, 0, 0);
                tv1.setLayoutParams(llp);
                tv1.setTextColor(getResources().getColorStateList(R.color.highlighted_text_material_light));
                ImageView imv = new ImageView(this);
                ImageView img = new ImageView(this);

                String text = "";
                if (actions.getActions()[i].getActionType().equals("CALLBACK")) {
                    text = "Agendar llamada. \n 	Espera de: "+ actions.getActions()[i].getTiempoEstimadoEspera()+ " seg.";
                    tv1.setId(Integer.parseInt(actions.getActions()[i].getActionID()));
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_callback);
                } else if (actions.getActions()[i].getActionType().equals("VIRTUAL_HOLD")) {
                    text = "Devolver llamada. \n 	Espera de: "+ actions.getActions()[i].getTiempoEstimadoEspera()+ " seg.";
                    tv1.setId(Integer.parseInt(actions.getActions()[i].getActionID()));
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_virtual);
                } else if (actions.getActions()[i].getActionType().equals("CHAT")) {
                    text = "Chat con ejecutivo. \n		Espera de: "+ actions.getActions()[i].getTiempoEstimadoEspera()+ " seg.";
                    tv1.setId(Integer.parseInt(actions.getActions()[i].getActionID()));
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_chat2);
                } else {
                    text = "Realizar llamada. \n	Espera de: "+ actions.getActions()[i].getTiempoEstimadoEspera()+ " seg.";
                    tv1.setId(Integer.parseInt(actions.getActions()[i].getActionID()));
                    tv1.setText(text.trim());
                    imv.setBackgroundResource(R.drawable.ico_llamar);
                }

                tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tv1.setOnClickListener(this);
                img.setImageResource(R.drawable.linea1);

                LinearLayout.LayoutParams ln1p = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                ln1p.setMargins(1, 8, 0, 8);
                llp.setMargins(1, 0, 0, 0); // llp.setMargins(left, top, right,

                ln1.setLayoutParams(ln1p);
                ln1.addView(imv);
                ln1.addView(tv1);
                //ln1.addView(rb1);
                ln.addView(ln1);
                ln.addView(img);
                tr1.addView(ln);
                tl.addView(tr1);

            }
        }
    }



    @Override
    public void onClick(View v) {
        String result = "";

        int id = v.getId();
        String tipoAccion = "";

        for (int i = 0; i < actions.getActions().length; i++) {

            if (actions.getActions()[i].getActionID().equals(String.valueOf(id))) {
                tipoAccion = actions.getActions()[i].getActionType();
                fono = actions.getActions()[i].getNumeroTransferencia();
                aProperties = actions.getActions()[i];
            }

        }

        datos = new ArrayList<String>();
        //datos.add(tree.getTreeID());
        datos.add("2");
        datos.add(cliente.getNombre()+" "+cliente.getApellido());
        datos.add(Properties.MOVIL);
        datos.add(cliente.getRut() + "-" + cliente.getDv());
        //datos.add(cliente.getCategoria());
        //datos.add(cliente.getSegmento());
        datos.add("Empresa");
        datos.add("Oro");
        datos.add(opcion);

        if (tipoAccion.equals("TRANSFERENCIA")) {

//            result = ws_ivr.setUserDatos("2", cliente.getNombre(),Properties.MOVIL, cliente.getRut() + "-" + cliente.getDv(), "", "", opcion, txtComentario.getText().toString());
            result = ws_c2c_bep.setUserDatos("2", cliente.getNombre(),Properties.MOVIL, cliente.getRut() + "-" + cliente.getDv(), "", "", opcion, txtComentario.getText().toString());

            if (!result.equals("")) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + fono));
                startActivity(intent);
            }
            finish();


        }

        if (tipoAccion.equals("CALLBACK")) {
            Intent intent = new Intent(this, CallbackActivity.class);
            intent.putExtra("actionProp", aProperties);
            intent.putExtra("attachData", datos);
            intent.putExtra("optionID", optionID);
            intent.putExtra("actionID", actionID);
            //startActivity(intent);
            startActivityForResult(intent, CALLBACK_RESULT_FROM_DIALOG);
        }

        if (tipoAccion.equals("CHAT")) {

            //GENESYS
            Intent nextIntent = new Intent(this, ChatGenesysActivity.class);

            //INTERACTIVE
            //Intent nextIntent = new Intent(this, ChatInteractiveActivity.class);

            nextIntent.putExtra("cliente", this.cliente);
            startActivity(nextIntent);
            finish();
        }

        if (tipoAccion.equals("VIRTUAL_HOLD")) {
            //Validar el horario de agendamiento
            String titulo="";
            String valido="La llamada ha sido agendada "+ new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());

            String cola = aProperties.getNumeroTransferencia();

            //FORMA DEPRECATED
            //DoVirtualHold resultado = ws_c2C_bep.doVirtualHold("0"+Properties.MOVIL, cola, datos);

            /*
            //CON PERFORMPOSTCALL -- GENESYS
            DoVirtualHold resultado = ws_c2c_bep.doVirtualHold("0"+Properties.MOVIL, cola, datos);

            if(resultado.getErrCode().equals("0")){
                Toast.makeText(getApplicationContext(), "Se ha agendado la llamada, nuestros agentes lo van a contactar a la brevedad.", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "La llamada no puede ser agendada, intente nuevamente.", Toast.LENGTH_SHORT).show();
            }
            */

            //INTERACTIVE
            //String url = "http://i3-web-001/I3Root/Server1/websvcs/callback/create";
            String url = "http://10.33.16.35/I3Root/Server1/websvcs/callback/create";
            String resultado = ws_c2c_bep.doVirtualHold_CIC(url, cliente);
            Log.d("AccionesActivity", "Resultado Virtual Hold CIC:" + resultado);

            try {
                JSONObject jsonObj = new JSONObject(resultado);
                JSONObject status = jsonObj.getJSONObject("callback").getJSONObject("status");

                if(status.getString("type").equals("success")){
                    Toast.makeText(getApplicationContext(), "Se ha agendado la llamada, nuestros agentes lo van a contactar a la brevedad.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "La llamada no puede ser agendada, intente nuevamente.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
            }

        }

        txtComentario.setText("");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AGENDAMIENTO_RESULT_FROM_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    String call = (String) data.getStringExtra("horario");
                    // String movil = (String) data.getStringExtra("movil");
                    Util.alerta("opcion Elegida: " + call, this);
                    // Intent resultIntent = new Intent(this, PlanActivity.class);
                    // resultIntent.putExtra("opcionCall", "Agendado!");
                    // setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    finish();
                }
                break;

        }
    }

}
