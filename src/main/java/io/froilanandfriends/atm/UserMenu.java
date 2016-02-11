package io.froilanandfriends.atm;

import java.util.ArrayList;

public class UserMenu {

    public static void userMenu(){
        /* User-Level Menu (choose account):
         *       Gives the user a choice of all their accounts or to create a new account
         *           if they choose account, set current account to that account. go to -> accountMenu()
         *           if create new account, go to -> createAccount()                                    */
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
        int userInput = -1;
        while (userInput!=0&&(userInput<0||userInput>userAccounts.size())){
            userInput=MenuUtilities.promptForInt("Choose an account option.");
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

    public static void createAccount(){
        /* Asks the user what account type they want to create (checking/savings/business)
         *       Creates an account of the chosen type.
         *           Set's current account to the created account.
         *               Goes to -> accountMenu()                                                  */
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        System.out.println("Account Types: \n Checking (c)\n Savings (s)\n Business (b) \n");
        String userInput="";
        while (userInput.length()!=1||(userInput.charAt(0)!='c'&&userInput.charAt(0)!='s'&&userInput.charAt(0)!='b')){
            userInput=MenuUtilities.promptForText("Enter a valid account type: ").toLowerCase();
        }
        AccountType typeToCreate;
        if(userInput.equals("c")){
            am.createAccount(AccountType.CHECKING);
        }
        else if(userInput.equals("s")){
            am.createAccount(AccountType.SAVINGS);
        }
        else {
            am.createAccount(AccountType.BUSINESS);
        }
        ArrayList<Account> allAccounts = am.getAllAccounts();
        am.switchAccount(allAccounts.get(allAccounts.size()-1).getId());
        AccountMenu.accountMenu();
    }


}
