package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int numAlarms = 2;
    int[] switches = {R.id.alarm1Switch, R.id.alarm2Switch};
    int[] times = {R.id.alarm1Time, R.id.alarm2Time};
    String[] timeStrings = {"Alarm1", "Alarm2"};
    int[] savedTimes = new int[numAlarms];

    Alarm alarm = new Alarm(this.getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEditTextListeners();
        setTimes();
        setAlarm();
        System.out.println("##################on create finoshed");
    }

    public void setEditTextListeners(){
        for(int i = 0; i < numAlarms; i++){
            ((EditText)findViewById(times[i])).setOnKeyListener(this::onTextEdit);
        }
    }

    public void setTimes(){
        SharedPreferences sh = getSharedPreferences("AlarmTimes", Context.MODE_PRIVATE);
        for(int i = 0; i < numAlarms; i++){
            EditText tempEditText = findViewById(times[i]);
            String tempString = sh.getString(timeStrings[i], "");
            tempEditText.setText(tempString);
        }
    }

    public void onAlarmSwitch(View v){
        //set the actions for alarm
    }

    public void saveTimes(){
        SharedPreferences sharedPreferences = getSharedPreferences("AlarmTimes",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i = 0; i < numAlarms; i++){
            EditText tempEditText = findViewById(times[i]);
            myEdit.putString(timeStrings[i],tempEditText.getText().toString());
        }
        myEdit.commit();
    }

    public boolean onTextEdit(View v, int keyCode, KeyEvent event){
            saveTimes();
            return false;
    }

    public void setAlarm(){
        alarm.setAlarm(this.getBaseContext());
    }

}