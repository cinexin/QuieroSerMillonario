package upv.ejercicios.proyectofinal.quierosermillonario.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistanceException;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.PersistanceInterface;

/**
 * Created by migui on 0021.
 */

public class DBService implements PersistanceInterface{


    // TODO: Implement
    public SQLiteDatabase openSession(String databaseName) throws PersistanceException {


        return null;
    }

    // TODO: Implement
    public void closeSession() throws PersistanceException {

    }

    // TODO: Implement
    public void save(Object persistable) throws PersistanceException {

    }

    // TODO: Implement
    public List<Object> getAll(Class entity) throws PersistanceException {
        List<Object> recordsReturned = new ArrayList<>();

        return recordsReturned;
    }

    // TODO: Implement
    public  List<Object> getWithCriteria (String criteria, String sortByColumn, Class entity) throws PersistanceException {
        List<Object> recordsReturned = new ArrayList<>();

        return  recordsReturned;
    }


}
