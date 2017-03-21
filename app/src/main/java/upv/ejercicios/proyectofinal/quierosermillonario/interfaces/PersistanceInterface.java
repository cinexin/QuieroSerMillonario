package upv.ejercicios.proyectofinal.quierosermillonario.interfaces;

import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistanceException;

/**
 * Created by migui on 0021.
 */

public interface PersistanceInterface {

    public abstract Object openSession(String databaseName) throws PersistanceException;
    public abstract void closeSession() throws PersistanceException;
    public abstract void save(Object persistable) throws PersistanceException;
    public abstract List<Object> getAll(Class entity) throws PersistanceException;
    public abstract List<Object> getWithCriteria (String criteria, String sortByColumn, Class entity) throws PersistanceException;

}
