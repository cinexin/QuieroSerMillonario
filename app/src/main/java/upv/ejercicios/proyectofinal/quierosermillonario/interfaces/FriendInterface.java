package upv.ejercicios.proyectofinal.quierosermillonario.interfaces;

import java.io.IOException;
import java.net.UnknownServiceException;

import upv.ejercicios.proyectofinal.quierosermillonario.model.Friend;

/**
 * Created by migui on 0028.
 */

public interface FriendInterface {

    public abstract void addFriend(Friend friend) throws IOException;
}
