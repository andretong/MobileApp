package econtact.org.myapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.ebank.Cliente;


public class ContactoActivity extends Activity implements View.OnClickListener {

    private TextView lblBienvenida, lblMensaje;
    private Button btnLlamar;
    private Button btnChat;


    private WS_IVR ivrws = new WS_IVR();
    private Cliente cliente = new Cliente();

    private String fono = "227566262";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        //Para obtener parametros de otra Activity
        Intent intent = getIntent();
        lblBienvenida = (TextView) findViewById(R.id.lblBienvenida);
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);

        cliente.setRut(intent.getIntExtra("rut", 0));
        cliente.setNombre(intent.getStringExtra("nombre"));
        cliente.setApellido(intent.getStringExtra("apellido"));

        btnChat = (Button) findViewById(R.id.btnChat);
        btnChat.setOnClickListener(this);

        btnLlamar = (Button) findViewById(R.id.btnLlamar);
        btnLlamar.setOnClickListener(this);

        //Cliente cliente = (Cliente) intent.getSerializableExtra("cliente");

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v instanceof Button){
            if (v.getId() == R.id.btnChat){
                    Intent nextIntent = new Intent(this, ChatGenesysActivity.class);
                    nextIntent.putExtra("rut", this.cliente.getRut());
                    nextIntent.putExtra("nombre", this.cliente.getNombre());
                    nextIntent.putExtra("apellido", this.cliente.getApellido());
                    startActivity(nextIntent);
            }else if (v.getId() == R.id.btnLlamar){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + fono));
                startActivity(intent);
                finish();
            }

        }
    }


}
