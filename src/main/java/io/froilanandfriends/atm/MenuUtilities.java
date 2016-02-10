package io.froilanandfriends.atm;

import java.util.Scanner;

public class MenuUtilities {
    public static void logout(){
        /* Sets current user & current account fields to null
         *       Logging out message, goes to -> loginMenu()         */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        UserManager um = UserManager.getUserManager();
        //null current fields
        am.clearCurrentAccount();
        um.clearUser();
        System.out.println("Logging out..");
        delayedPrint(2000);

        LoginMenu.loginMenu();
    }

    public static void delayedPrint(int millisecondsDelay) {
        try {
            Thread.sleep(millisecondsDelay);
        } catch (InterruptedException e) {
        }

    }
    public static void delayedPrint(int millisecondsDelay,String stringToPrint){
        try {
            Thread.sleep(millisecondsDelay);
        } catch (InterruptedException e) {
        }
        System.out.println(stringToPrint);
    }
    public static String promptForText(String textToDisplay) {
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        String userAnswer = input.nextLine();
        return userAnswer;
    }
    public static double promptForDouble(String textToDisplay){
        Scanner input = new Scanner(System.in);

        System.out.println(textToDisplay);
        while(!input.hasNextDouble()) {
            input.next();
        }
        double userAnswer=input.nextDouble();
        return userAnswer;
    }
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
    public static int promptForInt(String textToDisplay){
        Scanner input = new Scanner(System.in);
        System.out.println(textToDisplay);
        while(!input.hasNextInt()) {
            input.next();
        }
        int userAnswer=input.nextInt();
        return userAnswer;
    }
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

    public static void clearScreen(){
        for (int x=0;x<100;x++){
            System.out.println();
        }
    }


}
