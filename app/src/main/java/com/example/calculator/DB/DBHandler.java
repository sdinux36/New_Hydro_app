package com.example.calculator.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calculator.classes.Challenges;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context,"database.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table challenges(ID INTEGER PRIMARY KEY AUTOINCREMENT,title text,note text,day text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addChallenges(String title,String note,String day){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("title",title);
        contentValues.put("note",note);
        contentValues.put("day",day);

        long res=db.insert("challenges",null,contentValues);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public boolean editChallenges(String title,String note,String day,int ID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("title",title);
        contentValues.put("note",note);
        contentValues.put("day",day);

        long res=db.update("challenges",contentValues,"ID="+ID,null);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public boolean deleteChallenges(int ID){

        SQLiteDatabase db = this.getWritableDatabase();

        long res=db.delete("challenges","ID="+ID,null);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public ArrayList<Challenges> readAllChallenges(){

        ArrayList<Challenges> challenges= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = db.rawQuery("select * from challenges",null);
        results.moveToFirst();

        while (results.isAfterLast()==false){

            Challenges model= new Challenges();

            model.setId(results.getInt(0));
            model.setTitle(results.getString(1));
            model.setNote(results.getString(2));
            model.setDay(results.getString(3));
            challenges.add(model);
            results.moveToNext();

        }

        return challenges;
    }

    public int getChallengesID(String title){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = db.rawQuery("select * from challenges where title='"+title+"'",null);
        results.moveToFirst();

        return results.getInt(0);
    }

}
