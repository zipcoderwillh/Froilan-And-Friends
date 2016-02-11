package io.froilanandfriends.atm;

import java.util.ArrayList;

public class AccountMenu {

    /**
     * Displays the account menu and presents the user with account related choices
     * based on the user's choices, advances to the corresponding menu
     */
    public static void accountMenu(){
        /*Account-Level Menu
         *      User Choice: Check Balance, Withdraw, Deposit, Transfer, View Transactions, Close Account, Switch Account, Logout
         *          goes to corresponding menu depending upon user choice               */
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currentAcc = am.getCurrentAccount();
        System.out.println("      "+currentAcc.getAccountType()+" -- Acct #: "+currentAcc.getId()+"\n");
        System.out.println("  Check Balance (c)   --    Withdraw (w)   --    Deposit (d)     \n");
        System.out.println("     Transfer (t)   --  View Transactions (v) --  Add User to Account (a)\n");
        System.out.println("   Close Account (x) --  Switch Account (s)  --  Logout (l) \n");
        String userInput="";
        while (!userInput.equals("c")&&!userInput.equals("w")&&!userInput.equals("d")&&!userInput.equals("t")&&
                !userInput.equals("v")&&!userInput.equals("x")&&!userInput.equals("s")&&!userInput.equals("l")&&!userInput.equals("a")){
            userInput = MenuUtilities.promptForText("Enter Decision: ").toLowerCase();
        }
        char userInputChar = userInput.charAt(0);
        switch (userInputChar){
            case 'c':checkBalance();
                break;
            case 'w':withdraw();
                break;
            case 'd':deposit();
                break;
            case 't':transfer();
                break;
            case 'v':viewTransactions();
                break;
            case 'a':addUser();
                break;
            case 'x':closeAccount();
                break;
            case 's':UserMenu.userMenu();
                break;
            case 'l':MenuUtilities.logout();
                break;
            default:MenuUtilities.logout();
        }

    }
    /** If user has <$10, prompts them to empty their account,
         *  if yes, withdraws balance
         *  if no, goes to -> accountMenu()
         * User has >$10:
         *  prompts user to enter withdraw amount in $10 increments
         *  validates that amount is in $10 increments
         *  validates that amount is <= $300 maximum withdrawal
         *  Checks atm tray to ensure withdraw amount can be processed
         *  if yes: processes withdrawal, success message, goes to -> accountMenu()
         *  if no: error message, goes to -> accountMenu()                    */
    public static void withdraw(){

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
    /** Prompts for deposit amount and number of bills
         *  Validates number of bills && space in atm
         *  If failure: error message -> accountMenu()
         *  If success: process deposit -> accountMenu()        */
    public static void deposit(){

        ATM atm = ATM.getATM();
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        int depositAmount=MenuUtilities.promptForPositiveInt("How much are you depositing? Press 1 to return to the Account Menu.");

        if(depositAmount==1){
            System.out.println("Transaction Cancelled.  Returning to Account Menu.");
            MenuUtilities.delayedPrint(1500);
            accountMenu();
        }

        boolean depositSuccess = atm.deposit(depositAmount);
        if(!depositSuccess){
            System.out.println("Apologies!  The ATM cannot accept that number of bills at this time.  Come back soon!");
            MenuUtilities.delayedPrint(1500);
            accountMenu();
        }
        else {
            double depositDouble = (double) depositAmount;
            am.deposit(depositDouble);
            System.out.println("$"+depositAmount+" deposited into your account.");
            MenuUtilities.delayedPrint(1500);
            accountMenu();
        }
    }
    /** Prompts user for account # destination && amount to transfer
         * Validates account && balance
         * if failure : error message, goes to-> accountMenu()
         * if success : processes transaction, goes to -> accountMenu()       */
    public static void transfer(){

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
            System.out.println("Transfer Successful.");
            MenuUtilities.delayedPrint(1200);
            am.transfer(destinationAccount.getId(),amountToTransfer);
        }
        accountMenu();
    }
    /**  Prints out all transaction history of current user
     *   When user is finished, goes to -> accountMenu()        */
    public static void viewTransactions(){

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
    /** Prompts user to ensure they want to close their account
     *  if yes: closes account, goes to -> userMenu()
     *  if no: goes to -> accountMenu()                     */
    public static void closeAccount(){

        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currentAccount = am.getCurrentAccount();
        UserManager un = UserManager.getUserManager();
        if(currentAccount.getBalance()!=0){
            System.out.println("Closing your account will withdraw all available funds in the form of a mailed check.  Current Balance: "+currentAccount.getBalance());
        }
        String userInput="";
        while (!userInput.equals("y")&&!userInput.equals("n") ){
            userInput=MenuUtilities.promptForText("Are you sure you want to close this account? (y/n)").toLowerCase();
        }
        if(userInput.equals("y")){
            int pinInput = 0;
            String userName = UserManager.getUserManager().getCurrentUser().getUserName();
            while (pinInput<1000||pinInput>9999){
                pinInput=MenuUtilities.promptForPositiveInt("Enter your pin: ");
                boolean pinSuccess = Authenticator.getAuthenticator().authenticate(userName,pinInput);
                if(!pinSuccess){
                    System.out.println("Incorrect pin. This account has been flagged.");
                    MenuUtilities.delayedPrint(1400,"Returning to Login Menu");
                    MenuUtilities.delayedPrint(800);
                    UserManager.getUserManager().getCurrentUser().setFlagged();
                    MenuUtilities.logout();
                }
            }
            am.deleteAccount(currentAccount.getId());
            System.out.println("Account Closed.");
            MenuUtilities.delayedPrint(2000);
            MenuUtilities.delayedPrint(1500,"You will receive a check in the mail for "+currentAccount.getBalance()+" in the next 7 business days.");
            MenuUtilities.delayedPrint(1700);
            UserMenu.userMenu();
        }
        else {
            accountMenu();
        }
    }

    /**
     * Prints out current balance, when finished, goes to -> accountMenu()
     */
    public static void checkBalance(){

        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        System.out.println(currAccount.getAccountType()+ " no. "+currAccount.getId()+"\n\n");
        System.out.println("Current Balance: "+currAccount.getBalance());
        MenuUtilities.promptForReturn();
        accountMenu();
    }

    /**
     * allows user to add a user to the current account
     */
    public static void addUser(){
        MenuUtilities.clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        UserManager um = UserManager.getUserManager();
        Account currAccount = am.getCurrentAccount();
        String usernameToAdd="";
        usernameToAdd=MenuUtilities.promptForText("Enter Username to Add to Your Account");
        User userToAdd = um.getUser(usernameToAdd);
        if(userToAdd==null){
            System.out.println("Not a valid username.");
            MenuUtilities.delayedPrint(1500);
            accountMenu();
        }
        else{
            am.addUserToCurrentAccount(userToAdd);
            System.out.println(usernameToAdd+" successfully added to this account.");
            MenuUtilities.delayedPrint(1400);
            accountMenu();
        }

    }
}

