package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.calculator.DB.DBHandler;
import com.example.calculator.R;
import com.example.calculator.challengesAdapter;
import com.example.calculator.classes.Challenges;

import java.util.ArrayList;
import android.view.View;
import android.content.Intent;
import android.widget.Button;


public class data_list extends AppCompatActivity {

    private RecyclerView recview;
    private challengesAdapter adapter;
    DBHandler dbHandler;
    private Button homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tactivity_data_list);
        homeButton = findViewById(R.id.homeButton);

        dbHandler = new DBHandler(this);

        ArrayList<Challenges> allChallenges = dbHandler.readAllChallenges();

        recview=(RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        adapter = new challengesAdapter(allChallenges);

        adapter.notifyDataSetChanged();
        recview.setAdapter(adapter);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(data_list.this, dLogin.class);
                startActivity(intent);
            }
        });
    }
}