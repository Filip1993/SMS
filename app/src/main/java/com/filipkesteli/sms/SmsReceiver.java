package com.filipkesteli.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //INTENT sadrzi PDU-e -> package
        //daj ti meni mapu svih parametara
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            //novi tip objekta:
            //nesto sto moze biti bilo sto
            //sms message ima koliko ima poruka
            Object[] pdus = (Object[]) bundle.get("pdus");
            //koliko je doslo message-a, toliko ima pdus-a
            //kreiramo sms message-e:
            SmsMessage[] messages = new SmsMessage[pdus.length];
            String message = "";
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                message+=messages[i].getOriginatingAddress() + ":";
                message+=messages[i].getMessageBody();
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
