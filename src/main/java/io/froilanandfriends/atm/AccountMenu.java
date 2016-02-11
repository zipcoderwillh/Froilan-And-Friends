package io.froilanandfriends.atm;

import java.util.ArrayList;

public class AccountMenu {

    public static void accountMenu(){
        /*Account-Level Menu
         *      User Choice: Check Balance, Withdraw, Deposit, Transfer, View Transactions, Close Account, Switch Account, Logout
         *          goes to corresponding menu depending upon user choice               */
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currentAcc = am.getCurrentAccount();
        System.out.println("      "+currentAcc.getAccountType()+" -- Acct #: "+currentAcc.getId()+"\n");
        System.out.println("  Check Balance (c)   --    Withdraw (w)   --    Deposit (d)     -- Transfer (t) \n");
        System.out.println("View Transactions (v) -- Close Account (x) -- Switch Account (s) --  Logout (l) \n\n");
        String userInput="";
        while (!userInput.equals("c")&&!userInput.equals("w")&&!userInput.equals("d")&&!userInput.equals("t")&&
                !userInput.equals("v")&&!userInput.equals("x")&&!userInput.equals("s")&&!userInput.equals("l")){
            userInput = MenuUtilities.promptForText("Enter Decision: ").toLowerCase();
        }
        if(userInput.equals("c")){
            checkBalance();
        }
        else if(userInput.equals("w")){
            withdraw();
        }
        else if(userInput.equals("d")){
            deposit();
        }
        else if(userInput.equals("t")){
            transfer();
        }
        else if(userInput.equals("v")){
            viewTransactions();
        }
        else if(userInput.equals("x")){
            closeAccount();
        }
        else if(userInput.equals("s")){
            UserMenu.userMenu();
        }
        else if(userInput.equals("l")){
            MenuUtilities.logout();
        }
    }

    public static void withdraw(){
        /* If user has <$10, prompts them to empty their account,
         *           if yes, withdraws balance
         *           if no, goes to -> accountMenu()
         * User has >$10:
         *       prompts user to enter withdraw amount in $10 increments
         *           validates that amount is in $10 increments
         *           validates that amount is <= $300 maximum withdrawal
         *   Checks atm tray to ensure withdraw amount can be processed
         *      if yes: processes withdrawal, success message, goes to -> accountMenu()
         *      if no: error message, goes to -> accountMenu()                    */
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        double currBalance = currAccount.getBalance();
        int userWithdraw = 0;
        if(currBalance<10.0){
            System.out.println("You have insufficient funds to process a withdrawal on this account.");
            System.out.println("You must have at least $10 to perform a partial withdraw.");
            System.out.println("You may withdraw all available funds in the form of a mailed check.  Current Balance: "+currBalance);
            String userAnswer = "";
            while (!userAnswer.equals("w")&&!userAnswer.equals("x")) {
                userAnswer = MenuUtilities.promptForText("\n    Withdraw All Funds (w) -- Return to Account Manager (x)  ").toLowerCase();
            }
            if (userAnswer.equals("x")){
                accountMenu();
                //don't want to use return here, always want to start at the top of all menu's
            }
            else {
                am.withdrawl(currBalance);
                System.out.println("Your check will be in the mail within 3 business days.");
                MenuUtilities.delayedPrint(1500);
                accountMenu();
            }
        }
        else if(currBalance>=10.0) {
            boolean gettingAmount = true;
            while (gettingAmount) {
                userWithdraw = MenuUtilities.promptForPositiveInt("What amount would you like to withdraw? ($10 increments)  Enter 1 to cancel the transaction.");
                if (userWithdraw==1){
                    System.out.println("Transaction cancelled.  Returning to Account Menu");
                    MenuUtilities.delayedPrint(1000);
                    accountMenu();
                    break;
                }
                else if (userWithdraw > currBalance) {
                    System.out.println("Insufficient funds.  Enter a lower amount.");
                } else if (userWithdraw % 10 != 0) {
                    System.out.println("Your withdrawal must be in $10 increments.");
                } else if (userWithdraw > 300) {
                    System.out.println("Maximum withdrawal: $300");
                } else {
                    break;
                }
            }
        }
        ATM atm = ATM.getATM();
        boolean withdrawSuccess = atm.withdraw(userWithdraw);
        if (!withdrawSuccess){
            System.out.println("We're sorry. Our ATM is running low on bills and cannot support a withdrawal of this amount at the current time.");
            MenuUtilities.delayedPrint(2000);
            accountMenu();
        }
        else {
            am.withdrawl(userWithdraw);
            System.out.println("Dispensing Cash.."); MenuUtilities.delayedPrint(2000);
            accountMenu();
        }
    }
    public static void deposit(){
        /* Prompts for deposit amount and number of bills
         *       Validates number of bills && space in atm
         *           If failure: error message -> accountMenu()
         *           If success: process deposit -> accountMenu()        */
        ATM atm = ATM.getATM();
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        int depositAmount=MenuUtilities.promptForPositiveInt("How much are you depositing? Press 1 to return to the Account Menu.");

        if(depositAmount==1){
            System.out.println("Transaction Cancelled.  Returning to Account Menu.");
        }

        boolean depositSuccess = atm.deposit(depositAmount);
        if(!depositSuccess){
            System.out.println("Apologies!  The ATM cannot accept that number of bills at this time.  Come back soon!");
            accountMenu();
        }
        else {
            double depositDouble = (double) depositAmount;
            am.deposit(depositDouble);
            System.out.println("$"+depositAmount+" deposited into your account.");
            accountMenu();
        }
    }
    public static void transfer(){
        /* Prompts user for account # destination && amount to transfer
         *       Validates account && balance
         *           if failure : error message, goes to-> accountMenu()
         *           if success : processes transaction, goes to -> accountMenu()       */
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        double currBalance = currAccount.getBalance();
        if(currBalance<=0){
            System.out.println("Account balance : 0.");
            MenuUtilities.delayedPrint(1400,"Unable to perform transfer.");
            MenuUtilities.delayedPrint(1400);
            accountMenu();
        }
        long idToTransfer = 0;
        double amountToTransfer=0;
        idToTransfer=MenuUtilities.promptForPositiveLong("Enter Account Number to transfer funds to: ");
        Account destinationAccount = am.getAccount(idToTransfer);
        if(destinationAccount==null){
            System.out.println("Not a valid account number.");
            MenuUtilities.delayedPrint(1500);
            accountMenu();
        }
        else {
            while (true) {
                amountToTransfer = MenuUtilities.promptForPositiveDouble("Enter amount to transfer.");
                if (amountToTransfer > currBalance) {
                    System.out.println("Insufficient funds.  Current balance: " + currBalance);
                    MenuUtilities.delayedPrint(1500);
                } else if (amountToTransfer <= currBalance) {
                    break;
                }
            }
        }
        if(amountToTransfer>0){
            am.transfer(destinationAccount.getId(),amountToTransfer);
        }
        accountMenu();
    }
    public static void viewTransactions(){
        /*  Prints out all transaction history of current user
         *       When user is finished, goes to -> accountMenu()        */
        MenuUtilities.clearScreen();
        TransactionManager tm = TransactionManager.getTransactionManager();
        UserManager um = UserManager.getUserManager();
        ArrayList<Transaction> userTrans = tm.getCurrentAccountTransactions();
        for(Transaction t:userTrans){
            System.out.println(t.getDate()+ " - "+t.getTransactionType()+" - "+t.getAmount());
        }
        MenuUtilities.promptForReturn();
        accountMenu();
    }

    public static void closeAccount(){
        /* Prompts user to ensure they want to close their account
         *       if yes: closes account, goes to -> userMenu()
         *       if no: goes to -> accountMenu()                     */
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currentAccount = am.getCurrentAccount();
        String userInput="";
        while (!userInput.equals("y")&&!userInput.equals("n") ){
            MenuUtilities.promptForText("Are you sure? (y/n)").toLowerCase();
        }
        if(userInput.equals("y")){
            am.deleteAccount(currentAccount.getId());
            System.out.println("Account Closed.");
            MenuUtilities.delayedPrint(2000);
            UserMenu.userMenu();
        }
        else {
            accountMenu();
        }
    }

    public static void checkBalance(){
        //Prints out current balance, when finished, goes to -> accountMenu()
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        System.out.println(currAccount.getAccountType()+ " no. "+currAccount.getId()+"\n\n");
        System.out.println("Current Balance: "+currAccount.getBalance());
        MenuUtilities.promptForReturn();
        accountMenu();
    }
}

