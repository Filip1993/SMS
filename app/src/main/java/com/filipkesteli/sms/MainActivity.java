package com.filipkesteli.sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private EditText etMessage;

    private Button btnPrepare;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setupListeners();
    }

    private void initWidgets() {
        etNumber = (EditText) findViewById(R.id.etNumber);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnPrepare = (Button) findViewById(R.id.btnPrepare);
        btnSend = (Button) findViewById(R.id.btnSend);
    }

    private void setupListeners() {
        btnPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormOk()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW); //ACTION_SENDTO
                    intent.putExtra("address", etNumber.getText().toString());
                    intent.putExtra("sms_body", etMessage.getText().toString());
                    intent.setType("vnd.android-dir/mms-sms"); //Ovoga nema

                    startActivity(intent);
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormOk()) {
                    SmsManager smsManager = SmsManager.getDefault();
                    //salje se na neki centar
                    smsManager.sendTextMessage(
                            etNumber.getText().toString(),
                            null,
                            etMessage.getText().toString(),
                            null,
                            null
                    );
                }
            }
        });
    }

    private boolean isFormOk() {
        if (etNumber.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity.this, R.string.please_insert_number, Toast.LENGTH_SHORT).show();
            etMessage.requestFocus();
            //returnamo false, cime korisniku dajemo do znanja da se ne moze poslati prazna poruka
            return false;
        }

        if (etMessage.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity.this, R.string.please_insert_message, Toast.LENGTH_SHORT).show();
            etMessage.requestFocus();
            return false;
        }
        //Ako je forma okej, vracamo true:
        return true;
    }
}
