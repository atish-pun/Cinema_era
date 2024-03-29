package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountSignup extends AppCompatActivity {
    private EditText full_name, emailAdd, password, confirmPassword;
    private Button sign_up;
    TextView signin;

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
                str_name = full_name.getText().toString().trim();
                str_email = emailAdd.getText().toString().trim();
                str_password = password.getText().toString().trim();
                str_confirm = String.valueOf(confirmPassword.getText());

                if(!str_name.equals("") && !str_email.equals("") && !str_password.equals("") && !str_confirm.equals("")) {
                    if (str_password.equals(str_confirm)){
                        String url = getString(R.string.server_api_url) + "create-user.php?fullName="+ Uri.encode(str_name)+"&emailAddress="+ Uri.encode(str_email)+"&Password="+ Uri.encode(str_password);
                        RequestQueue queue = Volley.newRequestQueue(AccountSignup.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getInt("status") == 200) {
                                        Toast.makeText(AccountSignup.this, "Sign Up Success.", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else
                                        Toast.makeText(AccountSignup.this, obj.getString("content"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AccountSignup.this, error.toString(), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                        );
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        queue.add(stringRequest);

                    }
                    else {
                        Toast.makeText(AccountSignup.this, "Both password doesn't match!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AccountSignup.this, "All field required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
