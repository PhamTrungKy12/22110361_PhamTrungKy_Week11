package com.example.videoshortwithfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BeginActivity extends AppCompatActivity {

    private Button btnupload;
    private Button btnWatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_begin);
        AnhXa();
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void AnhXa(){
        btnupload = (Button) findViewById(R.id.btnupload);
        btnWatch = (Button) findViewById(R.id.btnWatch);

    }
}