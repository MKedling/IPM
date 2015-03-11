package com.example.mattias.ipm61;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends Activity {

   private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listView);

        populateCallLogList();
        registerClicks(list);

    }

    // Frågar efter call log data, lägger till telenummer och typ från varje entry, sedan lägger till allt i listviewn med adaptern.
    private void populateCallLogList(){

        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);

        //Hämtar column index för olika kolumner
        int numberIndex = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int typeIndex = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int contactNameIndex = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);

        ArrayList<String> callLog = new ArrayList<String>();

        while (managedCursor.moveToNext()) { // Loppar igenom alla entrys
            String phoneNumber = managedCursor.getString(numberIndex);
            String name = managedCursor.getString(contactNameIndex);
            if(name == null){
                name = "Okännt nummer";
            }
            int callType = managedCursor.getInt(typeIndex);
            String cType;

            switch (callType) { // kollar vilken typ entryn hade
                case CallLog.Calls.OUTGOING_TYPE:
                    cType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    cType = "Incoming";
                    break;
                default:
                    cType = "Missed";
                    break;
            }
            callLog.add(phoneNumber + " : " + cType + " " +name); // adderar nummer, typ och kontakt till arraylistan som skall skickas till ArrayAdaptern.
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.call_list_layout, callLog); // skapar arrayadaptern
        list.setAdapter(adapter); // populerar listan.

    }

    //Metod för att hantera klicks av items i listan.
    private void registerClicks(AbsListView obj) {
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String text = textView.getText().toString();

                String[] t = text.split(" ");// Splittar strängen som är "telefonnummer : typ kontaktnamn" för att få bara telefonnummret.
                String tel = "tel:" + t[0]; // Skapar telestring enligt det format som acepteras enligt boken.

                //Skapar en intent att ringa och skickar i parade tele stringen.
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
                startActivity(intent);
            }
        });

    }


}
