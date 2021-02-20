package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;

public class AccountSignup extends AppCompatActivity {
    private EditText full_name, emailAdd, password, confirmPassword;
    private Button sign_up;
    TextView signin;
    String url ="http://192.168.100.129:8080/ERA/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_signup);
        full_name = findViewById((R.id.fullName));
        emailAdd = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        sign_up = findViewById(R.id.Button_signup);
        signin = findViewById(R.id.signin);
        getSupportActionBar().hide();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSignup.this, AccountLogin.class);
                startActivity(intent);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_name, str_email, str_password, str_confirm;
                str_name = String.valueOf(full_name.getText());
                str_email = String.valueOf(emailAdd.getText());
                str_password = String.valueOf(password.getText());
                str_confirm = String.valueOf(confirmPassword.getText());


                if (!str_name.equals("") && !str_email.equals("") && !str_password.equals("") && !str_confirm.equals("")) {
                    if (str_password.equals(str_confirm)) {
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Creating array for parameters
                                String[] field = new String[3];
                                field[0] = "fullName";
                                field[1] = "emailAddress";
                                field[2] = "Password";

                                //Creating array for data
                                String[] data = new String[3];
                                data[0] = str_name;
                                data[1] = str_email;
                                data[2] = str_password;

                                PutData putData = new PutData(url, "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Sign Up Success")) {
                                            Toast.makeText(getApplicationContext(), "Sign Up Success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), AccountLogin.class);
                                            startActivity(intent);
                                            overridePendingTransition(0,0);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Both password doesn't match! re-enter password.",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "All Field Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
