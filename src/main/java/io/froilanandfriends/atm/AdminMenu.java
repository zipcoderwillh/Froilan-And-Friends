package io.froilanandfriends.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminMenu {

    public static void adminMenu(){
        //Asks the administrator to choose an option, then calls the corresponding method menu.
        MenuUtilities.clearScreen();
        System.out.println("                             Admin Menu  \n");
        System.out.println("Collect Deposits (c) - Restock Withdrawal Tray (r) - Withdrawal Tray Status (w)");
        System.out.println(" View Transactions (v) -    Unflag User (u)    -      Logout (l) \n");
        String userInput = "";
        while (true){
            userInput = MenuUtilities.promptForText("Enter Decision: ").toLowerCase();
            if(userInput.equals("c")){
                collectDeposits();
            }
            else if(userInput.equals("r")){
                restockWithdrawalTray();
            }
            else if(userInput.equals("w")){
                withdrawalStatus();
            }
            else if(userInput.equals("v")){
                viewAllTransactions();
            }
            else if(userInput.equals("u")){
                unflagUser();
            }
            else if(userInput.equals("l")){
                MenuUtilities.logout();
            }
        }
    }

    public static void collectDeposits(){
        //Empties the deposit tray from ATM and returns to -> adminMenu()
        MenuUtilities.clearScreen();
        ATM atm = ATM.getATM();
        System.out.println("Please Retrieve Deposits..");
        atm.emptyDepositTray();
        MenuUtilities.delayedPrint(2000,"Returning to Admin Menu");
        MenuUtilities.delayedPrint(1200);
        adminMenu();
    }
    public static void restockWithdrawalTray(){
        /*Prompts admin to enter number of each bill they want to stock
         *      checks legality
         *          if illegal -> returns to adminMenu()
         *          if illegal -> processes restock, returns to -> adminMenu() */
        MenuUtilities.clearScreen();
        ATM atm = ATM.getATM();
        int numTwenties = MenuUtilities.promptForPositiveInt("Enter the number of twenty dollar bills:");
        int numTens = MenuUtilities.promptForPositiveInt("Enter the number of tens:");

        boolean restockSuccess = atm.reloadWithdrawalTray(numTwenties,numTens);

        if(restockSuccess){
            System.out.println("ATM successfully restocked.");
            MenuUtilities.delayedPrint(2000,"Returning to Admin Menu");
            MenuUtilities.delayedPrint(1200);
            adminMenu();
        }
        else{
            System.out.println("Restocking Unsuccessful.  Check Withdrawal Tray Status and Try Again. ");
            MenuUtilities.delayedPrint(2000,"Returning to Admin Menu");
            MenuUtilities.delayedPrint(1200);
            adminMenu();
        }
    }
    public static void withdrawalStatus(){
        //Prints out the status of the withdrawal trays in the ATM then returns to -> adminMenu()
        MenuUtilities.clearScreen();
        ATM atm = ATM.getATM();
        System.out.println("Current Withdrawal Tray Balance :   "+atm.getATMBalance());
        HashMap<Integer,Integer> withdrawalTray = atm.getWithdrawlTray();
        int totalBills = withdrawalTray.get(20)+withdrawalTray.get(10);
        System.out.println(" Twenties  : "+withdrawalTray.get(20));
        System.out.println("   Tens    : "+withdrawalTray.get(10));
        System.out.println("\nTotal Bills: "+totalBills);
        System.out.println("Max Capacity: 2,000 bills");

        MenuUtilities.delayedPrint(1500);
        MenuUtilities.promptForReturn();
        adminMenu();
    }
    public static void viewAllTransactions(){
        //Prints out the entire transaction history of the ATM, then returns to -> adminMenu()
        MenuUtilities.clearScreen();
        TransactionManager tm = TransactionManager.getTransactionManager();
        ArrayList<Transaction> allTrans = tm.getAllTransactions();
        for(Transaction t:allTrans){
            System.out.println(t.getDate()+ " - "+t.getTransactionType()+" - "+t.getAmount());
        }
        MenuUtilities.delayedPrint(1500);
        MenuUtilities.promptForReturn();
        AccountMenu.accountMenu();
    }
    public static void unflagUser(){
        /* Prompts for a username to unflag
         *      Deflags if user is flagged, returns to -> adminMenu()*/
        MenuUtilities.clearScreen();
        UserManager um = UserManager.getUserManager();
        Authenticator am = Authenticator.getAuthenticator();
        String userName = MenuUtilities.promptForText("Enter the user's username:");
        User user = am.validateUser(userName);
        if(user==null){
            System.out.println("No such username.");
        }
        else{
            boolean flagged = user.isFlagged();
            if(!flagged){
                System.out.println("User was not flagged.");
            }
            else{
                user.removeFlagged();
                System.out.println("User was unflagged.");
            }
        }
        MenuUtilities.delayedPrint(1400,"Returning to Admin Menu.");
        adminMenu();
    }

}

