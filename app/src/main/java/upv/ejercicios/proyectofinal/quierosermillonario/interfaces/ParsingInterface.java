package upv.ejercicios.proyectofinal.quierosermillonario.interfaces;

import java.util.List;

/**
 * Created by migui on 0020.
 */

public interface ParsingInterface {

    public abstract List<?> parseQuestionsFile(String sysLanguage) throws Exception;
}
