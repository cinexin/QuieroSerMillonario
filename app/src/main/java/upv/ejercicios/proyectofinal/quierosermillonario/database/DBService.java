package upv.ejercicios.proyectofinal.quierosermillonario.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


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

    public void deleteAllRows(String entityName) throws PersistenceException {
        Logging logging = new Logging();

        if (this.db == null) {
            logging.error("Database Object Service not initialized! Initialize it before performing any action");
            return;
        }

        if (entityName.equalsIgnoreCase(AppConstants.DATABASE_SCORES_TABLE)) {
            try {
                String sqlDeleteSentence = "DELETE FROM " + AppConstants.DATABASE_SCORES_TABLE;
                db.beginTransaction();
                db.execSQL(sqlDeleteSentence);
                db.setTransactionSuccessful();
            } catch (PersistenceException persistenceEx) {
                logging.error("Error while deleting data from Database. Original error is: " + persistenceEx.getMessage());
                throw persistenceEx;
            } finally {
                db.endTransaction();
            }
        } else {
            logging.error("Don't know how to manage this entity");
            return ;
        }
    }

    // convert an "Objects" cursor (its type is defined by "entityName" param into a java List
    private List<Object> collectCursorDataIntoList(Cursor cursor, String entityName) {
        List<Object> objectList = new ArrayList<>();
        Logging logging = new Logging();
        if (entityName.equalsIgnoreCase(AppConstants.DATABASE_SCORES_TABLE)) {

            int userNameFieldId = cursor.getColumnIndex(AppConstants.DATABASE_USERNAME_FIELD);
            int scoreFieldId = cursor.getColumnIndex(AppConstants.DATABASE_SCORE_FIELD);
            int longitudeFieldId = cursor.getColumnIndex(AppConstants.DATABASE_LONGITUDE_FIELD);
            int latitudeFieldId = cursor.getColumnIndex(AppConstants.DATABASE_LATITUDE_FIELD);

            while (cursor.moveToNext()) {
                GameScore score = new GameScore();
                if (!StringUtils.isEmpty(cursor.getString(userNameFieldId)))
                    score.setUserName(cursor.getString(userNameFieldId));
                else
                    score.setUserName(AppConstants.DATABASE_DEFAULT_USER_NAME);
                score.setMoneyAchieved(cursor.getInt(scoreFieldId));
                score.setLongitude(cursor.getFloat(longitudeFieldId));
                score.setLatitude(cursor.getFloat(latitudeFieldId));
                objectList.add(score);
            }

            return objectList;
        } else {
            logging.error("Don't know how to manage this entity");
            return null;
        }
    }

    /* Get all rows in database for the given entity
        In our case, we only store "Scores", so we retrieve them all sorted by points (desc) to show best score at #1st position and so on...
     */
    public List<Object> getAll(String entityName) throws PersistenceException {
        List<Object> recordsReturned = new ArrayList<>();
        Logging logging = new Logging();
        if (dbHelper == null) {
            logging.error("Database Service Object not initialized. Initialize it before doing any action!");
            throw new PersistenceException();
        }

        if (entityName.equalsIgnoreCase( AppConstants.DATABASE_SCORES_TABLE)) {

            String[] databaseFields = {AppConstants.DATABASE_USERNAME_FIELD, AppConstants.DATABASE_SCORE_FIELD,
                    AppConstants.DATABASE_LONGITUDE_FIELD, AppConstants.DATABASE_LATITUDE_FIELD};
            Cursor scoresCursor = dbHelper.getReadableDatabase().query(AppConstants.DATABASE_SCORES_TABLE, databaseFields, null, null, null,
                   null, " AppConstants.DATABASE_SCORE_FIELD DESC ", null );
            recordsReturned = collectCursorDataIntoList(scoresCursor, entityName);

        } else {
            logging.error("Don't know how to manage this entity");
            throw new PersistenceException();
        }
        return recordsReturned;
    }

    /* Get rows with a given criteria in database for the given entity (typically the userName)
     In our case, we only store "Scores", so we retrieve them all sorted by points (desc) to show best score at #1st position and so on...
 */
    public  List<Object> getByField (String fieldName, String fieldValue , String sortByColumn, String entityName) throws PersistenceException {
        List<Object> recordsReturned = new ArrayList<>();
        Logging log = new Logging();
        if (dbHelper == null) {
            log.error("Database Service Object not initialized. Initialize it before doing any action!");
            throw new PersistenceException();
        }
        if (entityName.equalsIgnoreCase( AppConstants.DATABASE_SCORES_TABLE)) {


            String[] databaseFields = {AppConstants.DATABASE_USERNAME_FIELD, AppConstants.DATABASE_SCORE_FIELD,
                    AppConstants.DATABASE_LONGITUDE_FIELD, AppConstants.DATABASE_LATITUDE_FIELD};
            String selection = " " + fieldName + " = ?";
            String selectionArgs[] = {fieldValue};
            Cursor scoresCursor = dbHelper.getReadableDatabase().query(AppConstants.DATABASE_SCORES_TABLE, databaseFields, selection, selectionArgs, null,
                    null, " " + sortByColumn + " DESC ", null );

            recordsReturned = collectCursorDataIntoList(scoresCursor, entityName);

        } else {
            log.error("Don't know how to manage this entity");
            this.closeSession();
            throw new PersistenceException();
        }


        return  recordsReturned;
    }


}
