package com.example.dack.BroadcastReceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class Battery extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPercentage = (int) ((batteryLevel / (float) batteryScale) * 100);
        if(batteryPercentage == 20){
            Toast.makeText(context, "Pin yếu " + batteryPercentage + " %", Toast.LENGTH_SHORT).show();
        }else if(batteryPercentage == 10){
            Toast.makeText(context, "Pin yếu " + batteryPercentage + " %", Toast.LENGTH_SHORT).show();
        }
    }

}
