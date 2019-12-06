package com.example.rizk.pharmacysystem;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DrugActivity extends AppCompatActivity {

    TextView drugID,drugName,price,classification,concentration,type,amount;
    Button call,whatsapp;
    private String drugIDs,drugNames,prices,classifications,concentrations,types,amounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        init();
        setAction();
    }

    private void init() {
        Intent intent=getIntent();
        getIntentT(intent);
        drugID = (TextView) findViewById(R.id.drugID);
        drugName = (TextView) findViewById(R.id.drugName);
        price = (TextView) findViewById(R.id.price);
        classification = (TextView) findViewById(R.id.classification);
        concentration = (TextView) findViewById(R.id.concentration);
        type = (TextView) findViewById(R.id.type);
        amount = (TextView) findViewById(R.id.amount);
        whatsapp = (Button) findViewById(R.id.whatsapp);
        call = (Button) findViewById(R.id.call);
        showData();
    }

    private void showData() {
        drugID.setText(drugIDs);
        drugName.setText(drugNames);
        classification.setText(classifications);
        concentration.setText(concentrations);
        type.setText(types);
        price.setText(prices);
        amount.setText(amounts);
    }

    private void getIntentT(Intent intent) {
        //get Data from intent
        drugIDs = intent.getStringExtra("drugID");
        types = intent.getStringExtra("type");
        drugNames=intent.getStringExtra("drugName");
        classifications=intent.getStringExtra("classification");
        concentrations=intent.getStringExtra("concentration");
        prices = intent.getStringExtra("price");
        amounts=intent.getStringExtra("amount");
    }


    private void setAction(){
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+00 9876543210"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {

                    PackageManager pm = getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(DrugActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcall = new Intent();
                intentcall.setAction(Intent.ACTION_CALL);
                intentcall.setData(Uri.parse("tel:01229857369" )); // set the Uri
                startActivity(intentcall);
            }
        });
    }

}
