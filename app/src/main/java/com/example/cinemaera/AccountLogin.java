    package com.example.cinemaera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ObjectOutputStream;

public class AccountLogin extends AppCompatActivity {
    private EditText emailL, passwordL;
    private TextView SignUp;
    private Button log_in;
    String url="http://192.168.100.129:8080/ERA/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        emailL = findViewById(R.id.editEmailAddress);
        passwordL = findViewById(R.id.editPassword);
        log_in = findViewById(R.id.button_login);
        SignUp = findViewById(R.id.signup);
        getSupportActionBar().hide();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountLogin.this,AccountSignup.class);
                startActivity(intent);
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str_emailL, str_passwordL;
                str_emailL = String.valueOf(emailL.getText());
                str_passwordL = String.valueOf(passwordL.getText());

                if( !str_emailL.equals("") && !str_passwordL.equals("")){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "emailAddress";
                            field[1] = "Password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = str_emailL;
                            data[1] = str_passwordL;

                            PutData putData = new PutData(url,"POST",field,data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(0,0);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });

                } else{
                    Toast.makeText(getApplicationContext(), "All Field Required", Toast.LENGTH_SHORT).show();
                }
            }

        });

        BottomNavigationView nav = findViewById(R.id.buttom_nav);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_bar:
                        Intent intent = new Intent(AccountLogin.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.favourite_bar:
                        Intent intent1 = new Intent(AccountLogin.this,FavouriteFilm.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return true;
            }
        });
    }
    }

