package upv.ejercicios.proyectofinal.quierosermillonario.interfaces;

import android.view.View;

import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;


/**
 * Created by migui on 0016.
 */

public interface SettingsInterface {

    public abstract void saveSettings(GameSettings settings);

    public abstract GameSettings getSettings();



}
