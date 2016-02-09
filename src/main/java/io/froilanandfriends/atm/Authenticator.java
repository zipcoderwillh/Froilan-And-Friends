package io.froilanandfriends.atm;


public class Authenticator {
    UserManager userManager;

    //Singleton Setup
    public static Authenticator current = new Authenticator();

    Authenticator() {
    }

    public static Authenticator getAuthenticator() {
        return current;
    }

    public boolean authenticate(String userName, int pin) {
        User userToLogin = validateUser(userName);
        if (userToLogin == null) {
            return false;
        }
        if (userToLogin.authenticate(pin)) {
            return true;
        }
        return false;
    }

    User validateUser(String userName) {
        UserManager um = UserManager.getUserManager();

        User thisUser = um.getUser(userName);
        return thisUser;
    }
}