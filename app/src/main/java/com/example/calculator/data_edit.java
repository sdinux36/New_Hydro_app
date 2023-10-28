package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.calculator.DB.DBHandler;
import com.example.calculator.data_list;

public class data_edit extends AppCompatActivity {

    Spinner updateDay;
    EditText updateNote,updateNoteTitle;
    Button edit,listView;
    DBHandler dbHandler;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tactivity_data_edit);
        
        updateDay = findViewById(R.id.updateDay);
        updateNote = findViewById(R.id.updateNote);
        updateNoteTitle = findViewById(R.id.updateNoteTitle);
        edit = findViewById(R.id.updateBtn);

        dbHandler = new DBHandler(this);

        String[] data = new String[] {"Select Day","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateDay.setAdapter(adapter);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateNoteTitle.getText().toString()==""||updateNoteTitle.getText().toString()==null){
                    Toast.makeText(data_edit.this, "Note Title is Required", Toast.LENGTH_SHORT).show();
                }else if(updateNote.getText().toString()==""||updateNote.getText().toString()==null){
                    Toast.makeText(data_edit.this, "Note is Required", Toast.LENGTH_SHORT).show();
                }else if(updateDay.getSelectedItemPosition()==0){
                    Toast.makeText(data_edit.this, "Note Day is Required", Toast.LENGTH_SHORT).show();
                }else{
                    startUpdate();
                }
            }
        });


        Intent intent = getIntent();
        updateDay.setSelection(adapter.getPosition(intent.getStringExtra("day")));
        updateNote.setText(intent.getStringExtra("note"));
        updateNoteTitle.setText(intent.getStringExtra("title"));
        id=intent.getStringExtra("id");

    }

    private  void startUpdate(){
        boolean res = dbHandler.editChallenges(updateNoteTitle.getText().toString(),updateNote.getText().toString(),updateDay.getSelectedItem().toString(),Integer.parseInt(id));
        if (res){
            clearForm();
            Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();
            startActivity(new Intent(data_edit.this, data_list.class));
        }else {
            Toast.makeText(this,"Unsuccessful",Toast.LENGTH_LONG).show();
        }
    }

    private void clearForm() {
        updateNoteTitle.setText("");
        updateNote.setText("");
        updateDay.setSelection(0);


    }
}