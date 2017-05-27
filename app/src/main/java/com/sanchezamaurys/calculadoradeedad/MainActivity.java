package com.sanchezamaurys.calculadoradeedad;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvBornDate, tvCalculatedAge, tvActualAge, tvPenultimeAge;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat, simpleYearFormat, simpleMonthFormat, simpleDayFormat;
    ImageButton imgBtnRefreshAll;

    int penultimateAge;
    int ultimateAge = 0;
    int diffAges;
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

        tvBornDate = (TextView) findViewById(R.id.tvBornDate);
        tvCalculatedAge = (TextView) findViewById(R.id.tvCalculatedAge);
        tvActualAge = (TextView) findViewById(R.id.tvActualAge);
        tvPenultimeAge = (TextView) findViewById(R.id.tvPenultimateAge);

        imgBtnRefreshAll = (ImageButton) findViewById(R.id.imgBtnRefreshAll);
        imgBtnRefreshAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanAll();
            }
        });
        findViewById(R.id.btnSelectDatePickerDialog).setOnClickListener(this);
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
        tvBornDate.setText(simpleDateFormat.format(calendar.getTime()));

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DATE);

        int bornYear =  Integer.parseInt(simpleYearFormat.format(calendar.getTime()));
        int bornMonth = Integer.parseInt(simpleMonthFormat.format(calendar.getTime()));
        int bornDay =  Integer.parseInt(simpleDayFormat.format(calendar.getTime()));

        int currentAgeYear = currentYear - bornYear;
        int currentAgeMonth;
        int currentAgeDay = Math.abs(bornDay - currentDay);




        if (bornYear >= currentYear){
            showDialog();
        }else{

            tvCalculatedAge.setText(String.format(Locale.getDefault(),"%d",currentAgeYear));

            if (bornMonth > currentMonth){

                currentAgeMonth = 13 - ((bornMonth - currentMonth));

                if (currentAgeMonth == 12)
                    currentAgeMonth = 0;

                tvCalculatedAge.setText((currentAgeYear -1)
                                        +" años, "
                                        +Integer.toString(currentAgeMonth) +" meses, "
                                        +currentAgeDay +" días");

                calculateAges(currentAgeYear);
            }else {
                currentAgeMonth = currentMonth - bornMonth;
                tvCalculatedAge.append(" años, " + Integer.toString(currentAgeMonth) +" meses, "
                                        +currentAgeDay +" días");
                calculateAges(currentAgeYear);
            }
        }
    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("El año en que naciste no puede ser mayor o igual que el alo actual. ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void cleanAll(){
        tvBornDate.setText(R.string.diaMesAño);
        tvActualAge.setText(R.string.EdadCalculada);
        tvPenultimeAge.setText(R.string.NoExisteHistorico);

        ultimateAge = 0;
        penultimateAge= 0;
    }

    public void calculateAges(int currentAgeYear){
        if (currentAgeYear == 0){
            ultimateAge = currentAgeYear;
            tvActualAge.setText(String.format(Locale.getDefault(),"$%d",ultimateAge));

        }else{
            penultimateAge = ultimateAge;
            ultimateAge = currentAgeYear;

            tvPenultimeAge.setText(String.format(Locale.getDefault(),"%d",penultimateAge));
            tvActualAge.setText(String.format(Locale.getDefault(),"%d",ultimateAge));

            if (ultimateAge > penultimateAge && penultimateAge > 0){
                diffAges = ultimateAge - penultimateAge;
                String msn = "Eres "+diffAges+"años mayor que el usuario anterior.";
                Toast.makeText(this,msn,Toast.LENGTH_LONG).show();
            }else{
                if (penultimateAge > ultimateAge){
                    diffAges = penultimateAge - ultimateAge;
                    String msn = "Eres "+diffAges+" años menor que el usuario anterior.";
                    Toast.makeText(this,msn,Toast.LENGTH_LONG).show();
                }else {
                    if (ultimateAge == penultimateAge){
                        String msn = "Los dos últimos usuarios consultados son de la misma edad.";
                        Toast.makeText(this,msn,Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
