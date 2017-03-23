package upv.ejercicios.proyectofinal.quierosermillonario.gui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0023.
 */

public class ModalYesNoMessage {

    AlertDialog.Builder alertDialogBuilder;
    Context appContext;
    DialogInterface.OnClickListener onClickListener;

    /*
        Context = application context from which you're calling constructor
        questionMsgKey = the KEY of the message you want to display (as it's on res/strings.xml) (NOT THE MESSAGE ITSELF!!)
        onClickListener = the listener Action to provide when user clicks YES or NO

     */
    public ModalYesNoMessage(Context context, int questionMsgKey, DialogInterface.OnClickListener onClickListener) {
        this.appContext = context;
        alertDialogBuilder = new AlertDialog.Builder(this.appContext);
        String msg = context.getResources().getString(questionMsgKey);
        if (StringUtils.isEmpty(msg))
            msg = AppConstants.DEFAULT_YES_NO_MSG;

        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.yes), onClickListener);
        alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.no), onClickListener);

    }

    public void show() {
        this.alertDialogBuilder.show();
    }
}
