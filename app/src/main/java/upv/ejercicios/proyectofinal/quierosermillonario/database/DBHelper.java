package upv.ejercicios.proyectofinal.quierosermillonario.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;

/**
 * Created by migui on 0021.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabaseStatement ;
        sqlCreateDatabaseStatement = "CREATE TABLE " + AppConstants.DATABASE_SCORES_TABLE + " (id INTEGER PRIMARY KEY autoincrement, name TEXT NOT NULL, " +
                "score INTEGER NOT NULL, longitude FLOAT, latitude FLOAT) ";
        db.execSQL(sqlCreateDatabaseStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
