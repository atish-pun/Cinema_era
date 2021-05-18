package com.example.cinemaera;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountSignup extends AppCompatActivity {
    private EditText full_name, emailAdd, password, confirmPassword;
    private Button sign_up;
    TextView signin;
    TextInputLayout EmailErrorTxt,PasswordErrorTxt;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EmailErrorTxt = findViewById(R.id.EmailErrorTxt);
        PasswordErrorTxt = findViewById(R.id.PasswordErrorTxt);
        getSupportActionBar().hide();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSignup.this, AccountLogin.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();

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
                    if (Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
                        if (str_password.length() > 6){
                            if (str_password.equals(str_confirm)){
                                String url = getString(R.string.server_api_url) + "create-user.php?fullName="+ Uri.encode(str_name)+"&emailAddress="+ Uri.encode(str_email)+"&Password="+ Uri.encode(str_password);
                                RequestQueue queue = Volley.newRequestQueue(AccountSignup.this);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject obj = new JSONObject(response);
                                            if (obj.getInt("status") == 200) {
                                                Toast.makeText(AccountSignup.this, "Successfully account has been Registered", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(AccountSignup.this, AccountLogin.class);
                                                startActivity(intent);
                                                overridePendingTransition(0, 0);
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
                                        Toast.makeText(AccountSignup.this, "Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
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
                        else {
                            Toast.makeText(AccountSignup.this, "Password must have at least 6 letters", Toast.LENGTH_SHORT).show();
                            PasswordErrorTxt.setErrorEnabled(true);
                            PasswordErrorTxt.setError("Password must have at least 6 letter");
                        }
                    }
                    else{
                        Toast.makeText(AccountSignup.this, "Please enter Valid Email!!", Toast.LENGTH_SHORT).show();
                        EmailErrorTxt.setError("Please enter Valid Email!!");
                    }
                }
                else{
                    Toast.makeText(AccountSignup.this, "Fields can't be empty!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
