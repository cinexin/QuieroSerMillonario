package upv.ejercicios.proyectofinal.quierosermillonario.utils;

import android.util.Log;

import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.LoggingInterface;

/**
 * Created by migui on 0017.
 */

public class Logging  implements LoggingInterface{

    @Override
    public void debug(String message) {
        Log.d("[DEBUG]: ", message);
    }

    @Override
    public void warning(String message) {
        Log.d("[WARNING]: ", message );
    }

    @Override
    public void error(String msg) {
        Log.e("[ERROR]: ", msg);
    }

    @Override
    public void info(String msg) {
        Log.i("[INFO]: ", msg);
    }
}
