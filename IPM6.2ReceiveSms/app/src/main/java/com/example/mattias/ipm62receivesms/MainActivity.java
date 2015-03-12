package com.example.mattias.ipm62receivesms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tw;
    private InvitationSmsReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw = (TextView) findViewById(R.id.text_view);
        smsReceiver = new InvitationSmsReceiver();

        // Skapar intent filter för att få sms broadcasts
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        //Registrerar att SMS objektet skall mottaga sms broadcasts
        registerReceiver(smsReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);

    }

    public class InvitationSmsReceiver extends BroadcastReceiver {

        //Kod som liknar den i boken
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            SmsMessage[] msgs = null;
            String message = "";
            if(extras != null) {
                Object[] pdus = (Object[]) extras.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for(int i=0; i<msgs.length;i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    message = msgs[i].getMessageBody();
                    tw.setText(message); // visar sms texten i fönstret.
                }
            }
        }
    }
    

}


