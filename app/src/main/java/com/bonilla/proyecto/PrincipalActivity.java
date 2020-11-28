package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnCerrar= findViewById(R.id.btnCerrar);


        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();

            }
        });
    }
}