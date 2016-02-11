package io.froilanandfriends.atm;

import java.util.ArrayList;

/**
 * This class handles all interaction with non-admin users once they are logged in.
 */

public class UserMenu {

    /**
     * Gives the user a choice of all their accounts or to create a new account. If they choose to access an account,
     * set current account to that account and call {@link accountMenu()}. If they choose to create new account,
     * calls {@link createAccount()}.
     */
    public static void userMenu(){
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        ArrayList<Account> userAccounts = am.getCurrentUsersAccounts();
        if(userAccounts!=null){ //prints out all accounts of currentUser with a letter selector for each
            System.out.println("Select Account: ");
            for(int x=0;x<userAccounts.size();x++){
                Account thisAccount = userAccounts.get(x);
                int accountSelector = x+1;
                System.out.println("("+accountSelector+") - "+thisAccount.getAccountType()+" -- Acct. No.: "+thisAccount.getId());
            }
        }
        System.out.println("\n(0) - Create New Account \n");
        System.out.println("(-1) - Logout");
        int userInput = -2;
        while (userInput!=0&&(userInput<-1||userInput>userAccounts.size())){
            userInput=MenuUtilities.promptForInt("Choose an account option.");
        }
        if(userInput==-1){
            MenuUtilities.logout();
        }
        if(userInput==0){
            createAccount();
        }
        else{
            int accountIndexChosen = userInput-1;
            am.switchAccount(userAccounts.get(accountIndexChosen).getId());
            AccountMenu.accountMenu();
        }
    }

    /**
     * Asks the user what account type they want to create (checking/savings/business), and creates an account
     * of the chosen type. Sets current account to the created account and calls {@link accountMenu()}.
     */
    public static void createAccount(){
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        System.out.println("Account Types: \n Checking (c)\n Savings (s)\n Business (b) \n");
        String userInput="";
        while (userInput.length()!=1||(userInput.charAt(0)!='c'&&userInput.charAt(0)!='s'&&userInput.charAt(0)!='b')){
            userInput=MenuUtilities.promptForText("Enter a valid account type: ").toLowerCase();
        }
        Account.AccountType typeToCreate;
        if(userInput.equals("c")){
            am.createAccount(Account.AccountType.CHECKING);
        }
        else if(userInput.equals("s")){
            am.createAccount(Account.AccountType.SAVINGS);
        }
        else {
            am.createAccount(Account.AccountType.BUSINESS);
        }
        ArrayList<Account> allAccounts = am.getAllAccounts();
        am.switchAccount(allAccounts.get(allAccounts.size()-1).getId());
        AccountMenu.accountMenu();
    }


}