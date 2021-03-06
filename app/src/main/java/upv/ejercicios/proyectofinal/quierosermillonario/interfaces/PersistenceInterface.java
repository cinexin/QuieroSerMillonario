package upv.ejercicios.proyectofinal.quierosermillonario.interfaces;

import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;

/**
 * Created by migui on 0021.
 */

public interface PersistenceInterface {

    public abstract void closeSession() ;
    public abstract void save(Object persistable) throws PersistenceException;
    public abstract List<Object> getAll(String entity) throws PersistenceException;
    public abstract List<Object> getByField (String fieldName, String fieldValue, String sortByColumn, String entity) throws PersistenceException;
    public abstract void deleteAllRows(String entity) throws PersistenceException;

}
