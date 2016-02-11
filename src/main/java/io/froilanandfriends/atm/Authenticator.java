package io.froilanandfriends.atm;

/**
 * This class handles the authentication functionality for logging users in.
 */

public class Authenticator {
    UserManager userManager;

    /**
     * Sets up a singleton instance of Authenticator
     */
    public static Authenticator current = new Authenticator();

    Authenticator() {
    }

    /**
     * Getter method for accessing the singleton instance.
     * @return The current singleton Authenticator instance.
     */
    public static Authenticator getAuthenticator() {
        return current;
    }

    /**
     * Checks to see if a username exists and if the PIN number is valid.
     * @param userName The username submitted by the customer during login.
     * @param pin The PIN number submitted by the customer during login.
     * @return True if authentication is successful, false if unsuccessful.
     */
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

    /**
     * Helper method to determine whether a user already exists in the system.
     * @param userName Username submitted by customer.
     * @return A User object if the user already
     */
    User validateUser(String userName) {
        UserManager um = UserManager.getUserManager();

        User thisUser = um.getUser(userName);
        return thisUser;
    }
}