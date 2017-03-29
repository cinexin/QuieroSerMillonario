package upv.ejercicios.proyectofinal.quierosermillonario.services;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.FriendInterface;
import upv.ejercicios.proyectofinal.quierosermillonario.model.Friend;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.HttpUtils;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;

/**
 * Created by migui on 0028.
 */

public class FriendService implements FriendInterface {
    Context context;

    public FriendService(Context context) {
        this.context = context;
    }


    @Override
    public void addFriend(Friend friend) throws IOException {
        Logging logging = new Logging();

        if (context == null) {
            String errMsg = this.getClass().getName() + ". Context is null. Please, initialize object first.";
            logging.error(errMsg);
            throw new IOException(errMsg);
        }
        HttpUtils httpUtils = new HttpUtils(this.context);
        List<NameValuePair> friendUrlParameters = new ArrayList<NameValuePair>();
        friendUrlParameters.add(new BasicNameValuePair(AppConstants.URL_USER_NAME_PARAMETER,friend.getUserName()));
        friendUrlParameters.add(new BasicNameValuePair(AppConstants.URL_USER_FRIEND_NAME_PARAMETER,friend.getFriendName()));
        try {
            httpUtils.postRequest(AppConstants.URL_ADD_NEW_FRIEND, AppConstants.HTTP_POST_METHOD , friendUrlParameters);
        } catch (IOException ioEx) {
            logging.error(this.getClass().getName() + ". I/O Exception: " + ioEx.getMessage());
            throw ioEx;
        }
    }
}
