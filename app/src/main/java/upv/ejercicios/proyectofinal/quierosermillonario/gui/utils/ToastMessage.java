package upv.ejercicios.proyectofinal.quierosermillonario.gui.utils;

import android.content.Context;
import android.widget.Toast;

import upv.ejercicios.proyectofinal.quierosermillonario.R;


/**
 * Created by migui on 0020.
 */

public class ToastMessage {

    public static void rightAnswerMessage(Context context) {
        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.msg_correct_answer), Toast.LENGTH_LONG);
        toast.show();
    }

}
