package io.froilanandfriends.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all user interaction when a user is logged in as an admin.
 */

public class AdminMenu {
    /**
     * Main menu for admins.
     * Allows the user to perform admin specific actions, including
     * collect deposits, restock withdrawal tray, view withdrawal status, view transactions, and flag users.
     */
    public static void adminMenu() {
        //Asks the administrator to choose an option, then calls the corresponding method menu.
        MenuUtilities.clearScreen();
        if (UserManager.getUserManager().getCurrentUser().getUserName().equals("froilan")) {
            MenuUtilities.delayedPrint(1000, "Admin account logged in.");
            System.out.println("                             Admin Menu  \n");
            System.out.println("Collect Deposits (c) - Restock Withdrawal Tray (r) - Withdrawal Tray Status (w)");
            System.out.println(" View Transactions (v) - Flag User (f) - Show Flagged Users(s) - Unflag User (u) - Logout (l)\n");
            String userInput = "";
            while (true) {
                userInput = MenuUtilities.promptForText("Enter Decision: ").toLowerCase();
                char charInput = ' ';
                if (userInput != null) {
                    charInput = userInput.charAt(0);
                } else {
                    charInput = '!';
                }
                switch (charInput) {
                    case 'c':
                        collectDeposits();
                        break;
                    case 'r':
                        restockWithdrawalTray();
                        break;
                    case 'w':
                        withdrawalStatus();
                        break;
                    case 'v':
                        viewAllTransactions();
                        break;
                    case 'u':
                        unflagUser();
                        break;
                    case 'f':
                        flagUser();
                        break;
                    case 'l':
                        MenuUtilities.logout();
                        break;
                    case 's':
                        showFlaggedUsers();
                        break;
                }
            }
        }
    }

    /**
     * Collects all the deposits from the ATM and
     * resets the deposit tray to 0.
     */
    public static void collectDeposits(){
        //Empties the deposit tray from ATM and returns to -> adminMenu()
        MenuUtilities.clearScreen();
        ATM atm = ATM.getATM();
        System.out.println("Current Deposit Balance: "+atm.getDepositValue());
        if (atm.getDepositValue()>0){
            MenuUtilities.delayedPrint(1200,"Please Retrieve Deposits..");
        }
        atm.emptyDepositTray();
        MenuUtilities.delayedPrint(2000,"Returning to Admin Menu");
        MenuUtilities.delayedPrint(1200);
        adminMenu();
    }

    /**
     * Prompts the admin to enter the number of twenties and tens be added to the machine.
     * Checks withdrawal tray status to see if there is enough room to accommodate the bills.
     * If successful it will restock the ATM with the amount entered and return to the menu.
     */
    public static void restockWithdrawalTray(){
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

    /**
     * Checks the withdrawl tray.
     * Will show how many tens and twenties are in the ATM (has a max capacity of 2,000 bills). Refill tray checks here to make sure there are enough bill space.
     */
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

    /**
     * Clears the screen and then prints all the transactions.
     * Shows them all and then will ask for a prompt to return to the admin menu.
     */
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
        AdminMenu.adminMenu();
    }

    /**
     * Prompts the admin to enter the username of a user to unflag them.
     * Runs a series of checks to ensure that the username is valid.
     * If valid the username is then checked to see if it is even flagged, if true it will unflag the user.
     * If the user is not flagged, will prompt that the user is already unflagged.
     */
    public static void unflagUser(){
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
                try {
                    UserManager.getUserManager().unFlagUser(user);
                } catch (Exception e){}
                System.out.println("User was unflagged.");
            }
        }
        MenuUtilities.delayedPrint(1400,"Returning to Admin Menu.");
        adminMenu();
    }

    /**
     * Prompts the admin to enter the username of a user to flag their account.
     * Runs checks to ensure that the username entered is actually valid.
     * If the user isn't flagged than it will flag them and return to admin menu.
     * If the user is flagged, than it will prompt saying that the user is already flagged and return ot the admin menu.
     */
    public static void flagUser(){
        MenuUtilities.clearScreen();
        UserManager um = UserManager.getUserManager();
        Authenticator am = Authenticator.getAuthenticator();
        String userName = MenuUtilities.promptForText("Enter the user's username:");
        User user = am.validateUser(userName);
        if(user==null){
            System.out.println("No such username.");
        }
        else if(user == um.getCurrentUser()){
            System.out.println("Administrators may not flag their own accounts.");
        }
        else{
            boolean flagged = user.isFlagged();
            if(flagged){
                System.out.println("User was already flagged.");
            }
            else{
                try {
                    UserManager.getUserManager().flagUser(user);
                } catch (Exception e){}
                System.out.println("User was flagged.");
            }
        }
        MenuUtilities.delayedPrint(1400,"Returning to Admin Menu.");
        adminMenu();
    }

    /**
     * Displays a list of all users currently flagged.
     */
    public static void showFlaggedUsers(){
        MenuUtilities.clearScreen();
        System.out.println("Flagged Users: \n");
        ArrayList<User> flagged = UserManager.getUserManager().getFlaggedUsers();
        for(User thisUser:flagged){
            System.out.println(thisUser.getUserName() +" - "+ thisUser.getFirstName() +" "+thisUser.getLastName()+ " - " + thisUser.getEmail());
        }
        MenuUtilities.promptForReturn();
        adminMenu();
    }
}

