package com.example.smkharvard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class KosongActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kosong);

        button.setOnClickListener((view) -> {
            Intent intent = new Intent(KosongActivity.this, LoginActivity.class);
            KosongActivity.this.startActivity(intent);
        });
    }
}