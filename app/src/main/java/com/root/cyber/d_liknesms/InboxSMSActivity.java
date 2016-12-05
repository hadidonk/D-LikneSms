package com.root.cyber.d_liknesms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InboxSMSActivity extends AppCompatActivity {

	private EditText editTextKata;
    private TextView textViewHasil;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inboxsms);

        final ListView list = (ListView) findViewById(R.id.list);
        final Button btnKey = (Button) findViewById(R.id.btnKey);
        btnKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final EditText edtKey = (EditText) findViewById(R.id.edtKey);
                List<String> msgs = getSMS(edtKey.getText().toString().trim());
                if (msgs.isEmpty()) {
                    msgs.add("Tidak ada SMS yang bisa dipecahkan dengan key tsbt");
                }
                ArrayAdapter<String> smsAdapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_1, msgs);
                list.setAdapter(smsAdapter);

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<String>  getSMS(String strkey) {
        List<String> list = new ArrayList<String>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = null;
        try{
            c = getApplicationContext().getContentResolver().query(uri, null, null ,null,null); 
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            for (boolean hasData = c.moveToFirst(); hasData; hasData = c.moveToNext()) {

                final String noHP = c.getString(c.getColumnIndex("address"));
                final String msg = c.getString(c.getColumnIndexOrThrow("body"));

               String plainTeks= KriptoActivity.deskripsi(msg,strkey);;
               if(!plainTeks.equalsIgnoreCase("ERROR")){
            	   list.add(noHP + "\nText: " + plainTeks);
            	   
               }       
               
            }
        }catch(Exception e){
            e.printStackTrace();
        }
     c.close(); 
     return list;
    }

    }
