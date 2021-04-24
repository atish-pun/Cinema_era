package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile_Update extends AppCompatActivity {
    EditText fullname, pass, npass, rpass;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__update);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        email = findViewById(R.id.email); email.setText(Util.SESSION_EMAIL);
        fullname = findViewById(R.id.fullname); fullname.setText(Util.SESSION_NAME);

        pass = findViewById(R.id.oldpass);
        npass = findViewById(R.id.newpass);
        rpass = findViewById(R.id.rnewpass);
    }

    public void saveProfileInfo(View view) {
        String nameStr;
        nameStr = fullname.getText().toString().trim();
        String url = getString(R.string.server_api_url) + "update-profile-info.php?fullName=" + nameStr + "&id=" + Util.SESSION_USERID + "&emailAddress="+ Util.SESSION_EMAIL;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getInt("status") == 200){
                    Util.SetKey(getApplicationContext(), "Cinemapref_name", nameStr); Util.SESSION_NAME = nameStr;
                    Toast.makeText(this, obj.getString("content"), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, obj.getString("content"), Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    public void PasswordUpdate(View view) {
        String password = pass.getText().toString().trim(), npassword = npass.getText().toString().trim(), rpassword = rpass.getText().toString().trim();
        if(!npassword.equals(rpassword)){ Toast.makeText(this, "Please enter same Password", Toast.LENGTH_SHORT).show(); return; }
        String url = getString(R.string.server_api_url) + "update-password-info.php?UserId="+ Util.SESSION_USERID +"&emailAddress="+ Util.SESSION_EMAIL +"&password="+ password +"&Repassword="+ npassword;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getInt("status") == 200) {
                    Toast.makeText(this, obj.getString("content"), Toast.LENGTH_SHORT).show();
                    pass.setText("");
                    npass.setText("");
                    rpass.setText("");
                }
                else{ Toast.makeText(this, obj.getString("content"), Toast.LENGTH_SHORT).show(); }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();

        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}