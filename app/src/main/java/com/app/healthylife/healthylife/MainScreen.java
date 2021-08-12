package com.app.healthylife.healthylife;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import java.net.URI;

/**
 * Created by MOSES on 2/15/2016.
 */
public class MainScreen extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        String[] projection=new String[]{ContactsContract.Profile.DISPLAY_NAME};
        Uri dataUri=Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,ContactsContract.Contacts.Data.CONTENT_DIRECTORY);
        Cursor c=getContentResolver().query(dataUri,projection,null,null,null);
        try{
            if(c.moveToFirst())
            {
                String deviceName =c.getString(c.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME));
                TextView text =(TextView)findViewById(R.id.textView);
                text.setText(deviceName);
            }

        }
        finally {

            c.close();
        }


    }


    public void sendMessage(View view) {
        startActivity(new Intent(MainScreen.this, MainActivity.class));



    }


    public void synchronise(View view) {
        startActivity(new Intent(MainScreen.this, activity_dataentry.class));
    }
}
