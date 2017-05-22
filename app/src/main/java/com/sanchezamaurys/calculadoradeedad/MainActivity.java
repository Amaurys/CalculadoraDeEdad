package com.sanchezamaurys.calculadoradeedad;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvFechaNacimiento, tvEdadCalculada;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat, simpleYearFormat, simpleMonthFormat, simpleDayFormat;
    //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        simpleYearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        simpleMonthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        simpleDayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        datePickerDialog = new DatePickerDialog(this, mDatePickerListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));

        tvFechaNacimiento = (TextView) findViewById(R.id.tvFechaNacimiento);
        tvEdadCalculada = (TextView) findViewById(R.id.tvEdadCalculada);

        findViewById(R.id.btnSeleccionarDatePickerDialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener mDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, dayOfMonth);

            refreshDisplays();
        }
    };

    private void refreshDisplays(){
        tvFechaNacimiento.setText(simpleDateFormat.format(calendar.getTime()));

        int currentYear = calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = calendar.getInstance().get(Calendar.MONTH);
        int currentDay = calendar.getInstance().get(Calendar.DATE);

        int bornYear =  Integer.parseInt(simpleYearFormat.format(calendar.getTime()));
        int bornMonth = Integer.parseInt(simpleMonthFormat.format(calendar.getTime()));
        int bornDay =  Integer.parseInt(simpleDayFormat.format(calendar.getTime()));

        String actualAgeYear = Integer.toString(currentYear - bornYear);
        int actualAgeMonth = 0;
        int actualAgeDay = Math.abs(bornDay - currentDay);

        if (bornYear >= currentYear){
            showDialog();
        }else{
            tvEdadCalculada.setText(actualAgeYear);

            if (bornMonth > currentMonth){

                actualAgeMonth = 13 - ((bornMonth - currentMonth));

                if (actualAgeMonth == 12)
                    actualAgeMonth = 0;

                tvEdadCalculada.setText(Integer.toString(Integer.parseInt(actualAgeYear) -1)
                                        +" años, "
                                        +Integer.toString(actualAgeMonth) +" meses, "
                                        +actualAgeDay +" días");
            }else {
                actualAgeMonth = currentMonth - bornMonth;
                tvEdadCalculada.append(" años, " + Integer.toString(actualAgeMonth) +" meses, "
                                        +actualAgeDay +" días");
            }
        }
    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("The born year can't be highest or equals to current year. ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
