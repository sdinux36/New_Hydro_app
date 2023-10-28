package com.example.calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculator.DB.DBHandler;
import com.example.calculator.classes.Challenges;

import java.util.ArrayList;

public class challengesAdapter extends RecyclerView.Adapter<challengesAdapter.holder> {

    private ArrayList<Challenges> challenges;
    private Context context;
    private DBHandler dbHandler;

    public challengesAdapter(ArrayList<Challenges> challenges){
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tsinglerow,parent,false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.titleText.setText(challenges.get(position).getTitle());
        holder.noteText.setText(challenges.get(position).getNote());
        holder.dayText.setText(challenges.get(position).getDay());

        int id =position;

        dbHandler = new DBHandler(context);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), data_edit.class);
                i.putExtra("title", challenges.get(id).getTitle() );
                i.putExtra("note", challenges.get(id).getNote() );
                i.putExtra("day", challenges.get(id).getDay() );
                i.putExtra("id", Integer.toString(challenges.get(id).getId()) );
                view.getContext().startActivity(i);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete Challenges");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean res = dbHandler.deleteChallenges(challenges.get(id).getId());
                        if (res){
                            challenges.remove(id);
                            notifyItemRemoved(id);
                            notifyDataSetChanged();
                            Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context,"Unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.challenges.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete;
        TextView titleText,noteText,dayText;
        public holder(View itemView)
        {
            super(itemView);

            titleText=(TextView)itemView.findViewById(R.id.titleText);
            noteText=(TextView)itemView.findViewById(R.id.noteText);
            dayText=(TextView)itemView.findViewById(R.id.dayText);

            edit=(ImageView)itemView.findViewById(R.id.editBtnSingle);
            delete=(ImageView)itemView.findViewById(R.id.deleteBtnSingle);

        }
    }
}
