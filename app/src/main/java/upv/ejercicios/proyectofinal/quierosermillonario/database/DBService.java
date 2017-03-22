package upv.ejercicios.proyectofinal.quierosermillonario.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.PersistenceInterface;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0021.
 */

public class DBService implements PersistenceInterface {
    private DBHelper dbHelper ;
    private SQLiteDatabase db;

    public DBService(String databaseName, Context context, int mode)  {
        this.dbHelper = new DBHelper(context, databaseName, null, AppConstants.DATABASE_DEFAULT_VERSION);
        if (mode == AppConstants.DATABASE_OPEN_READ_MODE)
            this.db = this.dbHelper.getReadableDatabase() ;
        else
            this.db = this.dbHelper.getWritableDatabase();

    }

    public void closeSession() {
        if (this.db != null)
            db.close();
    }

    // DONE: Save object into database
    public void save(Object persistable) throws PersistenceException {

        Logging log = new Logging();
        if (this.db == null) {
            log.error("Database Object Service not initialized! Initialize it before performing any action");
            return;
        }
        // for the moment we only know how to store scores...
        if (persistable instanceof GameScore) {
            GameScore gameScore = (GameScore) persistable;
            if (StringUtils.isEmpty(gameScore.getUserName()))
                gameScore.setUserName(AppConstants.DATABASE_DEFAULT_USER_NAME);
            String sqlInsertSentence = "INSERT INTO " + AppConstants.DATABASE_SCORES_TABLE +
                    "( " +
                        AppConstants.DATABASE_USERNAME_FIELD + "," +
                        AppConstants.DATABASE_SCORE_FIELD + "," +
                        AppConstants.DATABASE_LONGITUDE_FIELD + "," +
                        AppConstants.DATABASE_LATITUDE_FIELD +
                    " )" +
                    " VALUES " +
                    " ( '" + gameScore.getUserName() + "'" + "," +
                        gameScore.getMoneyAchieved() + "," +
                        gameScore.getLongitude() + "," +
                        gameScore.getLatitude() + ")";
            try {
                db.beginTransaction();
                log.debug("SQL Insert sentence: " + sqlInsertSentence);
                db.execSQL(sqlInsertSentence);
                db.setTransactionSuccessful();
            } catch (PersistenceException persistenceEx) {
                log.error(persistenceEx.getMessage());
                throw persistenceEx;
            } finally {
                db.endTransaction();
            }

        } else {
            log.error("Input Object type to save to database unknown.");
        }
    }

    // TODO: Implement
    public List<Object> getAll(Class entity) throws PersistenceException {
        List<Object> recordsReturned = new ArrayList<>();

        return recordsReturned;
    }

    // TODO: Implement
    public  List<Object> getWithCriteria (String criteria, String sortByColumn, Class entity) throws PersistenceException {
        List<Object> recordsReturned = new ArrayList<>();

        return  recordsReturned;
    }


}
