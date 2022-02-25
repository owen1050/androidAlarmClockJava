package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEditTextListeners();
        setTimes();
        createNotificationChannel();
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
        System.out.println("Started alarm");


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1050, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "owenAlarmApp";
            String description = "alarm app made by owen";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1050", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}