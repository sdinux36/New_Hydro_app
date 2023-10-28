package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.calculator.DB.DBHandler;

import java.io.IOException;
import java.util.UUID;
import android.content.Intent;


public class data_add extends AppCompatActivity {

    Spinner insertDay;
    EditText insertNote,insertNoteTitle;
    Button add;
    DBHandler dbHandler;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tactivity_data_add);

        insertDay = findViewById(R.id.insertDay);
        insertNote = findViewById(R.id.insertNote);
        insertNoteTitle = findViewById(R.id.insertNoteTitle);
        add = findViewById(R.id.insertBtn);
        homeButton = findViewById(R.id.homeButton);

        dbHandler = new DBHandler(this);

        String[] data = new String[] {"Select Day","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insertDay.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(insertNoteTitle.getText().toString()==""||insertNoteTitle.getText().toString()==null){
                    Toast.makeText(data_add.this, "Note Title is Required", Toast.LENGTH_SHORT).show();
                }else if(insertNote.getText().toString()==""||insertNote.getText().toString()==null){
                    Toast.makeText(data_add.this, "Note is Required", Toast.LENGTH_SHORT).show();
                }else if(insertDay.getSelectedItemPosition()==0){
                    Toast.makeText(data_add.this, "Note Day is Required", Toast.LENGTH_SHORT).show();
                }else{
                    startInsert();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(data_add.this, dLogin.class);
                startActivity(intent);
            }
        });

    }

    private  void startInsert(){
        boolean res = dbHandler.addChallenges(insertNoteTitle.getText().toString(),insertNote.getText().toString(),insertDay.getSelectedItem().toString());
        if (res){
            clearForm();
            Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Unsuccessful",Toast.LENGTH_LONG).show();
        }
    }

    private void clearForm() {
        insertNoteTitle.setText("");
        insertNote.setText("");
        insertDay.setSelection(0);


    }
}