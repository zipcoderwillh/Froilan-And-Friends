package io.froilanandfriends.atm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles interaction with the user during the login phase.
 */

public class LoginMenu {

    /**
     * Prompts the user to login or create a new profile. If login, calls {@link promptCredentials()}, if create, calls {@link createProfile()}.
     */
    public static void loginMenu(){
        /*Login Menu:
         *       Prompts the user to login or create a new profile
         *           if login, go to -> promptCredentials()
         *           if create, go to -> createProfile() */
        MenuUtilities.clearScreen();
        String userAnswer = "";
        while (!userAnswer.equalsIgnoreCase("l")&&!userAnswer.equalsIgnoreCase("c")){
            userAnswer = MenuUtilities.promptForText("Login (L) - - Create a Profile (C)").toLowerCase();
        }
        if(userAnswer.equalsIgnoreCase("l")){
            promptCredentials();
        }
        else if(userAnswer.equalsIgnoreCase("c")){
            createProfile();
        }
    }

    /**
     * Asks the user for their username and pin number, and calls {@link Authenticator} for verification. If
     * authentication fails, returns to {@link loginMenu()}.
     */
    public static void promptCredentials(){
        /*Asks the user for their username and pin #.  Sends to the Authenticator for verification.
         If failure, return to -> loginMenu()
         If success, set currentUser, go to -> userMenu()                */
        MenuUtilities.clearScreen();
        ATM atm = ATM.getATM();
        boolean firstOn = atm.isFirstOn();
        if (firstOn){
            System.out.println("ATM BOOTUP PROCESS..");
            MenuUtilities.delayedPrint(1700,"Login with Administrator account.\n");
            MenuUtilities.delayedPrint(1000);
        }
        String lastAttempt="error!!!!!!@promptCredentials";
        loginPrompt:
        while (true) {
            String userNameInput = MenuUtilities.promptForText("Enter Username: ");
            int pinInput=0;
            while(pinInput<1000||(pinInput>9999&&pinInput<1000000)||pinInput>9999999){ //while not 4 or 7 digits
                pinInput = MenuUtilities.promptForPositiveInt("Enter Pin: ");
            }
            Authenticator auth = Authenticator.getAuthenticator();
            boolean authenticated = auth.authenticate(userNameInput,pinInput);
            if(authenticated){

                UserManager um = UserManager.getUserManager();
                User authenticatedUser = um.getUser(userNameInput);
                if (firstOn&&!authenticatedUser.isAdmin()){
                    System.out.println("Please login on an administrator account.");
                    MenuUtilities.delayedPrint(1200);
                    continue loginPrompt;
                }
                if(authenticatedUser.isFlagged()&&!firstOn){
                    System.out.println("This account is flagged.  Please request administrator help to restore login privileges.");
                    MenuUtilities.delayedPrint(1500,"Returning to Login Menu");
                    MenuUtilities.delayedPrint(900);
                    loginMenu();
                }
                um.setCurrentUser(authenticatedUser);
                User currentUser = um.getCurrentUser();
                if(currentUser.isAdmin()){
                    atm.setFirstOn(false);
                    AdminMenu.adminMenu();
                }
                else if (!firstOn) {
                    UserMenu.userMenu();
                }
            }
            else{
                System.out.println("Login failed.");
                MenuUtilities.delayedPrint(1000);
                if (firstOn){
                    continue loginPrompt;
                }
                else {
                    if (lastAttempt.equals(userNameInput)){
                        User toFlag = Authenticator.getAuthenticator().validateUser(userNameInput);
                        if(toFlag!=null){
                            try {
                                UserManager.getUserManager().flagUser(toFlag);
                            } catch (Exception e){}

                            System.out.println("This account has been flagged due to suspicious activity.");
                            MenuUtilities.delayedPrint(2000);
                            loginMenu();
                        }
                    }
                    String userIn = MenuUtilities.promptForText("Try again? (y/n)").toLowerCase();
                    if (userIn.equals("y")||userIn.equals("yes")){
                        lastAttempt = userNameInput;
                        continue loginPrompt;
                    }
                    loginMenu();
                }
            }
        }
    }

    /**
     * Asks user for their account properties (first name, last name, security question, and email).
     * Creates user from input, sets current user to created user, then calls {@link userMenu()}.
     */
    public static void createProfile(){
        MenuUtilities.clearScreen();
        UserManager um =UserManager.getUserManager();

        String userName = promptUserName();
        int pin = promptPin();
        String firstName = promptFirstName();
        String lastName = promptLastName();
        String email = promptEmail();
        String securityQuestion = promptSecQuestion();
        String securityAnswer = promptSecAnswer();
        try {
            um.addUser(userName,firstName,lastName,email,pin,securityQuestion,securityAnswer);
        } catch (Exception e){}

        um.setCurrentUser(um.getUser(userName));
        UserMenu.userMenu();
    }

    /**
     * Helper method to determine whether input string contains non-word characters or spaces.
     */
    public static boolean findIllegalCharacters (String stringToEdit){
        Pattern p = Pattern.compile("\\W|_| |[0-9]");
        Matcher m = p.matcher(stringToEdit);
        return m.find();
    }

    /**
     * Helper method to determine whether a string is a valid email format (xxx@xxx.xxx).
     */
    public static boolean validateEmail(String email) {
        return email.matches("^\\w+@\\w+\\.\\w+$");
    }

    /**
     * Prompts the user to enter a username and validates that it is not already taken and does not contain any
     * non-word characters.
     * @return The username submitted by the user, if validated.
     */
    public static String promptUserName(){
        String userName;
        UserManager um = UserManager.getUserManager();
        while (true) {

            userName = MenuUtilities.promptForText("Enter Desired Username: ").toLowerCase();
            if(findIllegalCharacters(userName)) {
                System.out.println("Usernames can only contain alphabetic characters and may not contain spaces.");
                MenuUtilities.delayedPrint(800);
            }
            //check username availability:
            else if(userName.length()>8||userName==null||userName.isEmpty()){
                System.out.println("Usernames can be a maximum of 8 characters.");
                MenuUtilities.delayedPrint(800);
            }
            else if (um.getUser(userName) != null) {
                System.out.println("Username unavailable.");
                MenuUtilities.delayedPrint(800);
            }
            else {
                break;
            }
        }
        return userName;
    }

    /**
     * Prompts the user to enter a PIN number, and validates that it consists of four digits only.
     * @return The PIN number submitted by the user, if validated.
     */
    public static int promptPin(){
        int pin=0;
        while (pin<1000||pin>9999){
            pin=MenuUtilities.promptForPositiveInt("Enter desired 4-digit pin number: ");

            if(pin<1000||pin>9999){
                System.out.println("Please ensure your pin is 4 digits.");
            }
        }
        return pin;
    }

    /**
     * Prompts the user to enter a first name and validates that it does not contain any non-word characters.
     * @return The first name submitted by the user, if validated.
     */
    public static String promptFirstName(){
        String firstName = "";
        while (true){
            firstName = MenuUtilities.promptForText("Enter your first name: ");
            if(findIllegalCharacters(firstName)) {
                System.out.println("Names can only contain alphabetic characters and may not contain spaces.");
                MenuUtilities.delayedPrint(800);
                continue;
            }
            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = MenuUtilities.promptForText("Your first name is: "+firstName+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        return firstName;
    }

    /**
     * Prompts the user to enter a last name and validates that it does not contain any non-word characters.
     * @return The last name submitted by the user, if validated.
     */
    public static String promptLastName(){
        String lastName = "";
        while (true){
            lastName = MenuUtilities.promptForText("Enter your last name: ");

            if(findIllegalCharacters(lastName)) {
                System.out.println("Names can only contain alphabetic characters and may not contain spaces.");
                MenuUtilities.delayedPrint(800);
                continue;
            }

            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = MenuUtilities.promptForText("Your last name is: "+lastName+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        return lastName;
    }

    /**
     * Prompts the user to enter an email address and validates that it is in a valid format (xxx@xxx.xxx).
     * @return The email address submitted by the user, if validated.
     */
    public static String promptEmail(){
        String email = "";
        while (true){
            email = MenuUtilities.promptForText("Enter your e-mail address: ");

            if(!validateEmail(email)) {
                System.out.println("Please enter a valid email address.");
                MenuUtilities.delayedPrint(800);
                continue;
            }

            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = MenuUtilities.promptForText("Your e-mail is: "+email+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        return email;
    }

    /**
     * Prompts the user to choose a security question and submit an answer, and validates that it does not contain any non-word characters.
     * @return The security answer submitted by the user, if validated.
     */
    public static String promptSecQuestion(){
        int userAnswer = 0;
        String secQues1 = "What was your first pet's name?";
        String secQues2 = "In which city were you born?";
        String secQues3 = "Who was the first person to see you naked?";

        while (userAnswer!=1&&userAnswer!=2&&userAnswer!=3){
            System.out.println("Please choose a security question: \n");
            System.out.println("(1) : "+secQues1);
            System.out.println("(2) : "+secQues2);
            System.out.println("(3) : "+secQues3);
            userAnswer=MenuUtilities.promptForInt("\n Enter a question 1-3");
        }
        String securityQuestion;
        if (userAnswer==1){
            securityQuestion=secQues1;
        }
        else if(userAnswer==2){
            securityQuestion=secQues2;
        }
        else{
            securityQuestion=secQues3;
        }
        return securityQuestion;
    }
    public static String promptSecAnswer(){
        String securityAnswer="";
        while (true){
            securityAnswer = MenuUtilities.promptForText("Enter your answer: ");

            if(findIllegalCharacters(securityAnswer)) {
                System.out.println("Answers can only contain alphabetic characters and may not contain spaces.");
                MenuUtilities.delayedPrint(800);
                continue;
            }

            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = MenuUtilities.promptForText("Your answer is: "+securityAnswer+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        return securityAnswer;
    }
}

