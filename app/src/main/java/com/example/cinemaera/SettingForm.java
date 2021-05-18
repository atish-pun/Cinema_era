package com.example.cinemaera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class SettingForm extends AppCompatActivity {
    TextView aboutApp, privacy, terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_form);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        aboutApp = findViewById(R.id.about);
        privacy = findViewById(R.id.privacy);
        terms = findViewById(R.id.terms);

        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingForm.this);
                View view1 = getLayoutInflater().inflate(R.layout.about_app, null);
                final Button ok = view1.findViewById(R.id.cancel);
                final TextView About = view1.findViewById(R.id.About);
                About.setText(R.string.AboutApp);
                alert.setView(view1);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingForm.this);
                View view1 = getLayoutInflater().inflate(R.layout.privacy_policy, null);
                final Button ok = view1.findViewById(R.id.cancel);
                final TextView PrivacyPolicies = view1.findViewById(R.id.PrivacyPolicies);
                PrivacyPolicies.setText(R.string.PrivacyPolicy);
                alert.setView(view1);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingForm.this);
                View view1 = getLayoutInflater().inflate(R.layout.terms_to_use, null);
                final Button ok = view1.findViewById(R.id.cancel);
                final TextView Terms = view1.findViewById(R.id.Terms);
                Terms.setText(R.string.TermsOfUse);
                alert.setView(view1);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}