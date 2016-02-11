package io.froilanandfriends.atm;

import java.util.ArrayList;

public class LoginMenu {


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

    public static void promptCredentials(){
        /*Asks the user for their username and pin #.  Sends to the Authenticator for verification.
         If failure, return to -> loginMenu()
         If success, set currentUser, go to -> userMenu()                */
        MenuUtilities.clearScreen();
        String userNameInput = MenuUtilities.promptForText("Enter Username: ");
        int pinInput=0;
        while(pinInput<1000||(pinInput>9999&&pinInput<1000000)||pinInput>9999999){ //while not 4 or 7 digits
            pinInput = MenuUtilities.promptForPositiveInt("Enter Pin: ");
        }
        Authenticator auth = Authenticator.getAuthenticator();
        boolean authenticated = auth.authenticate(userNameInput,pinInput);
        if(authenticated){
            UserManager um = UserManager.getUserManager();
            um.setCurrentUser(um.getUser(userNameInput));
            User currentUser = um.getCurrentUser();
            if(currentUser.isAdmin()){
                AdminMenu.adminMenu();
            }
            else {
                UserMenu.userMenu();
            }
        }
        else{
            System.out.println("Login failed.");
            MenuUtilities.delayedPrint(2000);
            loginMenu();
        }
    }

    public static void createProfile(){
        /* Asks user for their attributes / account properties
         Creates user from input
         Sets current user to created user
         Goes to -> userMenu()                     */
        MenuUtilities.clearScreen();
        UserManager um =UserManager.getUserManager();

        String userName = promptUserName();
        int pin = promptPin();
        String firstName = promptFirstName();
        String lastName = promptLastName();
        String email = promptEmail();
        String securityQuestion = promptSecQuestion();
        String securityAnswer = promptSecAnswer();

        um.addUser(userName,firstName,lastName,email,pin,securityQuestion,securityAnswer);
        um.setCurrentUser(um.getUser(userName));
        UserMenu.userMenu();
    }

    public static String removeIllegalCharacters (String stringToEdit){
        stringToEdit=stringToEdit.replace(",","");
        stringToEdit=stringToEdit.replace("\n","");
        return stringToEdit;
    }
    public static String promptUserName(){
        String userName;
        UserManager um = UserManager.getUserManager();
        while (true) {

            userName = MenuUtilities.promptForText("Enter Desired Username: ").toLowerCase();
            userName = removeIllegalCharacters(userName);
            //check username availability:
            if(userName.indexOf(' ')>=0||userName.length()>8||userName==null||userName.isEmpty()){
                System.out.println("Usernames can be a maximum of 8 characters and may not contain spaces.");
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
    public static String promptFirstName(){
        String firstName = "";
        while (true){
            firstName = MenuUtilities.promptForText("Enter your first name: ");
            firstName = removeIllegalCharacters(firstName);
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
    public static String promptLastName(){
        String lastName = "";
        while (true){
            lastName = MenuUtilities.promptForText("Enter your last name: ");
            lastName = removeIllegalCharacters(lastName);
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
    public static String promptEmail(){
        String email = "";
        while (true){
            email = MenuUtilities.promptForText("Enter your e-mail address: ");
            email = removeIllegalCharacters(email);
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
            securityAnswer = removeIllegalCharacters(securityAnswer);
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

