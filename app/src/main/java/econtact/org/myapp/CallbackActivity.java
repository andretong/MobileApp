package econtact.org.myapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import econtact.org.cgi.ivr.ActionProperties;
import econtact.org.cgi.ivr.Actions;
import econtact.org.cgi.ivr.RangoHorarios;
import econtact.org.cgi.services.ivrmovil.WS_IVR;
import econtact.org.cgi.services.c2c.WS_C2C_BEP;
import econtact.org.cgi.util.Properties;


public class CallbackActivity extends Activity implements View.OnClickListener {

    private TextView txtDateDisplay;
    private TextView txtTimeDisplay;
    private Button btnPickFecha;
    private Button btnPickHora;
    private Button btnAgendar;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int diaSemana;
    private int mHour;
    private int mMinute;

    private ActionProperties aProperties = null;
    private String optionID,actionID;
    private RangoHorarios[] rHorario;

    private WS_IVR ws_ivr = new WS_IVR();
    private WS_C2C_BEP ws_c2c_bep = new WS_C2C_BEP();

    private TableLayout tl;
    public Actions actions;
    private ArrayList<String> attachData = new ArrayList<String>();


    static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            aProperties = (ActionProperties) extras.getSerializable("actionProp");
            attachData = (ArrayList<String>) extras.getStringArrayList("attachData");
            optionID= (String) getIntent().getSerializableExtra("optionID");
            actionID= (String) getIntent().getSerializableExtra("actionID");
        }


        rHorario = aProperties.getRangosHorarios();

        if (rHorario != null) {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            diaSemana= c.get(Calendar.DAY_OF_WEEK);

            txtDateDisplay = (TextView) findViewById(R.id.txtFecha);
            btnPickFecha = (Button) findViewById(R.id.btnFecha);
            btnPickFecha.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(DATE_DIALOG_ID);
                }
            });

            txtTimeDisplay = (TextView) findViewById(R.id.txtHora);
            btnPickHora = (Button) findViewById(R.id.btnHora);

            btnPickHora.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(TIME_DIALOG_ID);
                }
            });

            mHour=c.get(Calendar.HOUR_OF_DAY);
            mMinute=c.get(Calendar.MINUTE);

            updateDisplay();
            updateDisplayTime();

            btnAgendar = (Button) findViewById(R.id.btnAgendar);
            btnAgendar.setOnClickListener(this);
        }else{
            Toast.makeText(this, "Error solicitud, intente mas tarde.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }



    @Override
    public void onClick(View v) {
        try {

            int id = v.getId();

            txtDateDisplay = (EditText) findViewById(R.id.txtFecha);
            txtTimeDisplay = (EditText) findViewById(R.id.txtHora);
            //editkey4 = (EditText) findViewById(R.id.comentario);

            String fecha = txtDateDisplay.getText().toString();
            String hora = txtTimeDisplay.getText().toString();

            // Captar Id del cheackbox

            String cola = "";
            String iniFecha = "";
            String finFecha = "";

            cola = aProperties.getNumeroTransferencia();


            iniFecha = formatearDate(txtDateDisplay.getText().toString(),hora);
            finFecha = formatearDate(txtDateDisplay.getText().toString(),hora+":"+"59");

            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
            Date fechaDate = null;
            fechaDate = formatoDelTexto.parse(fecha);

            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(fechaDate);

            int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
            int totalSegundosFinHorario = 0;
            boolean flagTramosHorarios = false;

            for (int i = 0; i < aProperties.getRangosHorarios().length; i++) {
                if (aProperties.getRangosHorarios()[i].getDiaSemana().equals(String.valueOf(diaSemana))) {
                    totalSegundosFinHorario=this.transformarFecha(aProperties.getRangosHorarios()[i].getHoraTermino());

                    if(this.transformarFecha(hora) <= totalSegundosFinHorario ){
                        flagTramosHorarios=true;
                        break;
                    }else{
                        flagTramosHorarios=false;
                    }
                }
            }

            String[] arrayFecha = finFecha.split(":");
            String[] arrayFecha1 = arrayFecha[0].split(" ");
            finFecha= arrayFecha1[0]+" "+String.valueOf(Integer.parseInt(arrayFecha1[1])+1)+":"+arrayFecha[1]+":"+arrayFecha[2];

            if(flagTramosHorarios==true){
                //ws.doCallback(Properties.MOVIL, cola, iniFecha+":00",finFecha, attachData);
                ws_c2c_bep.doCallback("0"+Properties.MOVIL, cola, iniFecha+":00",finFecha, attachData);
                Toast.makeText(this, "Solicitud agendada! Gracias.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se encuetra en el tramo horario habilitado.",Toast.LENGTH_SHORT).show();
            }
            finish();
        } catch (Exception e) {
            Toast.makeText(this,"Se ha producido un erro, intenta nuevamente. Gracias",Toast.LENGTH_SHORT).show();
        }
    }




    private void updateDisplay() {

        txtDateDisplay.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append("-").append(mMonth + 1).append("-")
                .append(mYear).append(" "));

    }

    private void updateDisplayTime() {

        txtTimeDisplay.setText(
                new StringBuilder()
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));


    }

    private String pad(int c){
        if (c>=10)
            return String.valueOf(c);
        else
            return "0"+String.valueOf(c);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplayTime();
                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                // return new DatePickerDialog(this, mDateSetListener, mYear,
                // mMonth,mDay);
                return new MyDatePickerDialog(this, mDateSetListener, mYear,mMonth, mDay);
            case TIME_DIALOG_ID:
                // return new DatePickerDialog(this, mDateSetListener, mYear,
                // mMonth,mDay);
                return new TimePickerDialog(this, mTimeSetListener, mHour,mMinute,false);
        }
        return null;
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {

            case DATE_DIALOG_ID:
                // ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                ((MyDatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    public String formatearDate(String fecha, String hora) {
        String[] arrayFecha = fecha.split("-");

        if (arrayFecha[0].length() <= 1) {
            arrayFecha[0] = "0" + arrayFecha[0];
        }
        if (arrayFecha[1].length() <= 1) {
            arrayFecha[1] = "0" + arrayFecha[1];
        }
        return arrayFecha[2].trim() + "-" + arrayFecha[1].trim() + "-"+ arrayFecha[0].trim() + " " + hora;
    }


    public int transformarFecha(String fecha){
        String[] arrayFecha = fecha.split(":");
        int segundos=0;

        if(arrayFecha.length>2){
            segundos= (Integer.parseInt(arrayFecha[0]) * 3600) + (Integer.parseInt(arrayFecha[1]) * 60 ) + Integer.parseInt(arrayFecha[2]);
        }else{
            segundos= (Integer.parseInt(arrayFecha[0]) * 3600) + (Integer.parseInt(arrayFecha[1]) * 60 );
        }
        return segundos;
    }

}
