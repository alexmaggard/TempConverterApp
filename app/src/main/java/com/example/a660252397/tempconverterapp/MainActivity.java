package com.example.a660252397.tempconverterapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
implements OnEditorActionListener {

    //declare variables for widgets
    private EditText fahrenheitInputText;
    private EditText celciusResultText;

    private String fahrenheitInput;

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to the widgets
        fahrenheitInputText = (EditText) findViewById(R.id.fahrenheitInputText);
        celciusResultText = (EditText) findViewById(R.id.celciusResultText);

        //add action listener for editable text
        fahrenheitInputText.setOnEditorActionListener(this);

        //create shared preferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyevent){
        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }

        return false;
    }

    private void calculateAndDisplay() {
        fahrenheitInput = fahrenheitInputText.getText().toString();
        float fahrenheit = Float.parseFloat(fahrenheitInput);

        float celcius = (fahrenheit-32)*5/9;

        //set information back to the widget
        NumberFormat number = NumberFormat.getNumberInstance();
        celciusResultText.setText(number.format(celcius));
    }

    //set on pause and on resume methods
    // alt + insert to add override methods onPause and onResume
    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitInput", fahrenheitInput);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fahrenheitInput = savedValues.getString("fahrenheitInput","");

        calculateAndDisplay();
    }
}
