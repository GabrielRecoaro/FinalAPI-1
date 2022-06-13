package com.example.finalapi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "db_Studio";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE tbUsuario(" +
                "IdUsuario TEXT PRIMARY KEY," +
                "NomeUsuario TEXT NOT NULL," +
                "UsernameUsuario TEXT NOT NULL," +
                "ProfileUsuario BLOB," +
                "EmailUsuario TEXT UNIQUE)");

        db.execSQL("CREATE TABLE tbPersonagem(" +
                "IdPersonagem TEXT PRIMARY KEY," +
                "NomePersonagem TEXT NOT NULL," +
                "FotoPersonagem BLOB,"+
                "CaracteristicasPersonagem TEXT NOT NULL,"+
                "LocatePersonagem TEXT NOT NULL)");

        db.execSQL("CREATE TABLE tbFilme("+
                "IdFilme TEXT PRIMARY KEY,"+
                "DescricaoFilme TEXT NOT NULL,"+
                "LancData TEXT NOT NULL,"+
                "GeneroFilme TEXT NOT NULL)");
        /*
        db.execSQL("CREATE TABLE tbQuiz("")");
        db.execSQL("CREATE TABLE tbPergunta("")");
        db.execSQL("CREATE TABLE tbResposta("")");*/

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){

        db.execSQL("DROP TABLE IF EXISTS tbUsuario");
        db.execSQL("DROP TABLE IF EXISTS tbPersonagem");
        db.execSQL("DROP TABLE IF EXISTS tbFilme");

        /*
        db.execSQL("DROP TABLE IF EXISTS tbQuiz");
        db.execSQL("DROP TABLE IF EXISTS tbPergunta");
        db.execSQL("DROP TABLE IF EXISTS tbResposta");
        */

        onCreate(db);
    }

    public Cursor getNovaQuery(String sql){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

}
