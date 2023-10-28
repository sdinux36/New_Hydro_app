package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.calculator.data_list;

public class tMainActivity extends AppCompatActivity {
    Button addBtn,listBtn,tips;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tactivity_main);

        addBtn = findViewById(R.id.addBtn);
        listBtn = findViewById(R.id.listBtn);
        tips = findViewById(R.id.tips);
        homeButton = findViewById(R.id.homeButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(tMainActivity.this,data_add.class);
                startActivity(intent);
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(tMainActivity.this, data_list.class);
                startActivity(intent);
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tMainActivity.this,Tips.class));
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tMainActivity.this, dLogin.class);
                startActivity(intent);
            }
        });

    }
}