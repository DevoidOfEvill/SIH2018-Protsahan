package com.mota.tribal.protsahan.Schemes.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mota.tribal.protsahan.R;

public class SchemeDescriptionActivity extends AppCompatActivity {

    private TextView nam, descriptio, yea;
    private Toolbar toolbar;
    private FloatingActionButton backbutton;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_description);

        register = findViewById(R.id.register);
        backbutton = findViewById(R.id.back_button);

        String name, description, year, image;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
                description = null;
                year = null;
            } else {
                name = extras.getString("name");
                description = extras.getString("description");
                year = extras.getString("year");
                image = extras.getString("imagurl");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("name");
            description = (String) savedInstanceState.getSerializable("description");
            year = (String) savedInstanceState.getSerializable("year");
            image = (String) savedInstanceState.getSerializable("imageurl");
        }
        nam = findViewById(R.id.name);
        descriptio = findViewById(R.id.description);
        yea = findViewById(R.id.year);

        nam.setText(name);
        descriptio.setText(description);
        yea.setText(year);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchemeDescriptionActivity.this, SchemeActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.layout), "Successfully registered. Our officials will reach out to you shortly!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
