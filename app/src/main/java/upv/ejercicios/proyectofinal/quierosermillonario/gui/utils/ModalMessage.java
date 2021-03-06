package upv.ejercicios.proyectofinal.quierosermillonario.gui.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0023.
 */

public class ModalMessage {



    /*
        Context = application context from which you're calling constructor
        questionMsgKey = the KEY of the message you want to display (as it's on res/strings.xml) (NOT THE MESSAGE ITSELF!!)
        onClickListener = the listener Action to provide when user clicks YES or NO

     */
    public static void ModalYesNoMessage(Context context, int questionMsgKey, DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        String msg = context.getResources().getString(questionMsgKey);
        if (StringUtils.isEmpty(msg))
            msg = AppConstants.DEFAULT_YES_NO_MSG;

        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.yes), onClickListener);
        alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.no), onClickListener);
        alertDialogBuilder.show();
    }

    private static void showModalInfoDialog(Context context, String msg, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok), onClickListener);
        alertDialogBuilder.show();
    }
    /*
         Context = application context from which you're calling constructor
         questionMsgKey = the KEY of the message you want to display (as it's on res/strings.xml) (NOT THE MESSAGE ITSELF!!)
      */
    public static void ModalInfoMessage(Context context, int msgKey, DialogInterface.OnClickListener onClickListener) {

        String msg = context.getResources().getString(msgKey);
        if (StringUtils.isEmpty(msg))
            msg = AppConstants.DEFAULT_YES_NO_MSG;

        showModalInfoDialog(context, msg, onClickListener);
    }

    public static void ModalInfoMessage(Context context, String msg, DialogInterface.OnClickListener onClickListener) {
        showModalInfoDialog(context, msg, onClickListener);
    }

}
