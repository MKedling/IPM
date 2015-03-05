package com.example.mattias.ipm11;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Calendar;


/**
 * Klass som skapar en aktivitet med olika komponeter för att träna på de olika komponenterna.
 * @author mattias kedling
 */
public class Tab1 extends Activity implements AdapterView.OnItemSelectedListener {

    private TextView output, nameInput, weightOutput, birthdayOutput;
    private Button buttonSubmit;
    private String color, name, gender, birthday;
    private int weight, birthdayYear, birthdayMonth, birthdayDay;
    private SeekBar weightSeekbar;

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_layout);

        nameInput = (TextView) findViewById(R.id.input_name);
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        output = (TextView) findViewById(R.id.output);
        weightSeekbar = (SeekBar) findViewById(R.id.weight_seekbar);
        weightOutput = (TextView) findViewById(R.id.text_weight);

        weight = 50; // sätter startvärde
        //Button listener som sätter texten i output
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name = nameInput.getText().toString();
                output.setText(" Namn: " + name + "\n Vikt: " +weight +
                                "\n Kön: " + gender + "\n Födelsedag: " + birthday
                                + "\n Favvo färg: " + color);
            }
        });

        //Lägg till lyssnare till textfältet för födelsedag.
        birthdayOutput = (TextView) findViewById(R.id.text_birthday);
        birthdayOutput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pickBirthday();
            }
        });


        // Listener för seekbar som uppdaterar lokal variabel och även uppdaterar weightOutput.
        weightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                weight = progress;
                weightOutput.setText(weight + " kg");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //Spinner kod från developer.android
        Spinner spinner = (Spinner) findViewById(R.id.color_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //Actionlistener för när ett item från spinnern väljs
        spinner.setOnItemSelectedListener(this);

    }

    //Metod som anropas på en checkbox klickas.
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_man:
                if (checked)
                    gender = "Man";
                break;
            case R.id.radio_woman:
                if (checked)
                    gender = "Kvinna";
                break;
        }
    }

    //Metod som skapar och visar en DatePicker för att välja föddelsedag.
    private void pickBirthday(){

        final Calendar c = Calendar.getInstance();
        birthdayYear = c.get(Calendar.YEAR);
        birthdayMonth = c.get(Calendar.MONTH);
        birthdayDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        birthday = (dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        birthdayOutput.setText("Födelsedag: " + birthday);
                    }
                }, birthdayYear, birthdayMonth, birthdayDay);
        datePicker.show();
    }
    //2 OnItemSelectedListener metoder
    // Metod som anropas då ett element väljs. Uppdaterar variablen colors värde.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        color = str;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
