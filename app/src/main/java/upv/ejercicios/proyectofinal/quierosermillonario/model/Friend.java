package upv.ejercicios.proyectofinal.quierosermillonario.model;

/**
 * Created by migui on 0028.
 */

public class Friend {
    private String userName; // alias of the user adding the friend "friendName" to his friend's list
    private String friendName; // friend being added

    public Friend() {
    }

    public Friend(String userName, String friendName) {
        this.userName = userName;
        this.friendName = friendName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "userName='" + userName + '\'' +
                ", friendName='" + friendName + '\'' +
                '}';
    }
}
