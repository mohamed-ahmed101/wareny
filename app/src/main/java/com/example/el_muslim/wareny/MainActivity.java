package com.example.el_muslim.wareny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.el_muslim.wareny.helper.CheckNetworkStatus;

public class MainActivity extends AppCompatActivity {

    Button storeBtn;
    Button customerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeBtn = (Button) findViewById(R.id.storeButton);
        customerBtn = (Button) findViewById(R.id.customerButton);

        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                              //////////////////////////////////////
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    //Display error message if not connected to internet
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),customerActivity.class);
                    startActivity(intent);
                } else {
                    //Display error message if not connected to internet
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
