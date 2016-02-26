package io.froilanandfriends.atm;

import java.util.Scanner;

/**
 * A set of utility methods for user interaction in the various menus.
 */

public class MenuUtilities {

    /**
     * Sets current user and current account fields to null.
     */
    public static void logout(){
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        UserManager um = UserManager.getUserManager();
        //null current fields
        am.clearCurrentAccount();
        um.clearUser();

        LoginMenu.loginMenu();
    }

    /**
     * Delays printing next line of output by specified millisecond delay.
     * @param millisecondsDelay
     */
    public static void delayedPrint(int millisecondsDelay) {
        try {
            Thread.sleep(millisecondsDelay);
        } catch (InterruptedException e) {
        }

    }

    /**
     * Delays printing specified string by specified millisecond delay.
     * @param millisecondsDelay
     */
    public static void delayedPrint(int millisecondsDelay,String stringToPrint){
        try {
            Thread.sleep(millisecondsDelay);
        } catch (InterruptedException e) {
        }
        System.out.println(stringToPrint);
    }

    /**
     * Prompts user to enter some text.
     * @param textToDisplay Text to display at prompt.
     * @return text that user has entered.
     */
    public static String promptForText(String textToDisplay) {
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        String userAnswer = "";
        while (userAnswer.isEmpty()) {
            while (!input.hasNextLine()) {
                input.next();
            }
            userAnswer = input.nextLine();
        }
        return userAnswer;
    }

    /**
     * Waits until user hits <RETURN> to continue.
     */
    public static void promptForReturn(){
        Scanner input = new Scanner(System.in);
        System.out.println("Press RETURN when finished.");
        String userAnswer = input.nextLine();
    }

    /**
     * Prompts user to enter a double amount.
     * @param textToDisplay
     * @return
     */
    public static double promptForDouble(String textToDisplay){
        Scanner input = new Scanner(System.in);

        System.out.println(textToDisplay);
        while(!input.hasNextDouble()) {
            input.next();
        }
        double userAnswer=input.nextDouble();
        return userAnswer;
    }

    /**
     * Prompts user to enter a positive double amount.
     * @param textToDisplay
     * @return
     */
    public static double promptForPositiveDouble(String textToDisplay){
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        while(!input.hasNextDouble()) {
            input.next();
        }
        double userAnswer=input.nextDouble();

        while (userAnswer<=0){
            System.out.println("Please enter a positive value.");
            while(!input.hasNextDouble()) {
                input.next();
            }
            userAnswer=input.nextDouble();
        }
        return userAnswer;
    }

    /**
     * Prompts user to enter an integer value.
     * @param textToDisplay
     * @return
     */
    public static int promptForInt(String textToDisplay){
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        while(!input.hasNextInt()) {
            input.next();
        }
        int userAnswer=input.nextInt();
        return userAnswer;
    }

    /**
     * Prompts user to enter a positive integer value.
     * @param textToDisplay
     * @return
     */
    public static int promptForPositiveInt(String textToDisplay){
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        while(!input.hasNextInt()) {
            input.next();
        }
        int userAnswer=input.nextInt();
        while (userAnswer<=0){
            System.out.println("Please enter a positive value.");
            while(!input.hasNextInt()) {
                input.next();
            }
            userAnswer=input.nextInt();
        }
        return userAnswer;
    }

    /**
     * Prompts user to enter a positive long value.
     * @param textToDisplay
     * @return
     */
    public static long promptForPositiveLong(String textToDisplay){
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        while(!input.hasNextLong()) {
            input.next();
        }
        Long userAnswer=input.nextLong();
        while (userAnswer<=0){
            System.out.println("Please enter a positive value.");
            while(!input.hasNextLong()) {
                input.next();
            }
            userAnswer=input.nextLong();
        }
        return userAnswer;
    }

    /**
     * Clears the console screen.
     */
    public static void clearScreen(){
        for (int x=0;x<100;x++){
            System.out.println();
        }
    }


}
