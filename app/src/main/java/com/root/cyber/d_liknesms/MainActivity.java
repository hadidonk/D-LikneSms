package com.root.cyber.d_liknesms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText edtnomor =(EditText)findViewById(R.id.edtnomor);
        final TextView txtChiper =(TextView)findViewById(R.id.txtChiper);
        final EditText edtpesan =(EditText)findViewById(R.id.edtpesan);

        final EditText edtkeypesan =(EditText)findViewById(R.id.edtkeypesan);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomor= edtnomor.getText().toString();
                String pesan = edtpesan.getText().toString();

                String strKey=edtkeypesan.getText().toString().trim();
                String chiperText= KriptoActivity.enkripsi(pesan, strKey);
                if(nomor.length()>0){
                    sendSMS(nomor, chiperText);
                    txtChiper.setText(chiperText);
                }else {
                    Toast.makeText(getBaseContext(), "Tambahkan dulu penerima", Toast.LENGTH_LONG).show();
                }


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_buatpesan) {
            // Handle the camera action
        } else if (id == R.id.nav_lihatinbox) {
            Intent i = new Intent(MainActivity.this,InboxSMSActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_carapenggunaan) {

        } else if (id == R.id.nav_pengaturan) {

        } else if (id == R.id.nav_proses) {
            Intent i = new Intent(MainActivity.this,ProsesActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sendSMS(String noHP, String pesan){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        //ketika SMS SENT
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS SENT", Toast.LENGTH_LONG).show();

                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_LONG).show();
                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "TAK ADA SIGNAL", Toast.LENGTH_LONG).show();
                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "PDU NULL", Toast.LENGTH_LONG).show();
                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "ERROR , GSM MATI", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        Toast.makeText(getBaseContext(), "Ada persyaratan belum lengkap", Toast.LENGTH_LONG).show();
                }
            }
        }, new IntentFilter(SENT));

        //ketika SMS DELIVERED
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS DELIVERED", Toast.LENGTH_LONG).show();

                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS TIDAK TERKIRIM ", Toast.LENGTH_LONG).show();
                        break;


                    default:
                        Toast.makeText(getBaseContext(), "NULL", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getBaseContext(), "titopurbantara.blogspot.com", Toast.LENGTH_LONG).show();
            }

        }, new IntentFilter(DELIVERED));

        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(noHP, null, pesan, sentPI, deliveredPI);

    }
}
