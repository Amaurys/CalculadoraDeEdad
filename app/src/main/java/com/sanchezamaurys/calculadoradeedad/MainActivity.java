package com.sanchezamaurys.calculadoradeedad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DatePicker datePicker;
    private Button btnCalular;
    private TextView tvEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = (DatePicker) findViewById(R.id.myDatePicker);
        tvEdad = (TextView) findViewById(R.id.textViewEdadCalculada);

        findViewById(R.id.btnCalcularEdad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        StringBuilder sb = new StringBuilder();
        int año = 2017 - datePicker.getYear();
        tvEdad.setText("Usted tiene "+año +" años de edad.");
    }
}
