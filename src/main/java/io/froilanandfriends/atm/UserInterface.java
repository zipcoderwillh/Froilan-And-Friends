package io.froilanandfriends.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    //FIX:
    //admin menu
    public static void loginMenu(){
        /*Login Menu:
         *       Prompts the user to login or create a new profile
         *           if login, go to -> promptCredentials()
         *           if create, go to -> createProfile() */
        clearScreen();
        String userAnswer = "";
        while (!userAnswer.equalsIgnoreCase("l")&&!userAnswer.equalsIgnoreCase("c")){
            userAnswer = promptForText("Login (L) - - Create a Profile (C)").toLowerCase();
        }
        if(userAnswer.equalsIgnoreCase("l")){
            promptCredentials();
        }
        else if(userAnswer.equalsIgnoreCase("c")){
            createProfile();
        }
    }
    public static void userMenu(){
        /* User-Level Menu (choose account):
         *       Gives the user a choice of all their accounts or to create a new account
         *           if they choose account, set current account to that account. go to -> accountMenu()
         *           if create new account, go to -> createAccount()                                    */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        ArrayList<Account> userAccounts = am.getCurrentUsersAccounts();
        if(userAccounts!=null){ //prints out all accounts of currentUser with a letter selector for each
            System.out.println("Select Account: ");
            for(int x=0;x<userAccounts.size();x++){
                Account thisAccount = userAccounts.get(x);
                String charSelection = Integer.toString(x+97);
                System.out.println("("+charSelection+") - "+thisAccount.getAccountType()+" -- Acct. No.: "+thisAccount.getId());
            }
        }
        System.out.println("\n(0) - Create New Account \n");
        String userInput = "";
        while (userInput!="0"&&(userInput.length()>1||userInput.charAt(0)<'a'||userInput.charAt(0)>(96+userAccounts.size()))){
            userInput=promptForText("Choose an account option.").toLowerCase();
        }
        if(userInput.equals("0")){
            createAccount();
        }
        else{
            int accountIndexChosen = Character.getNumericValue(userInput.charAt(0))-97;
            am.setCurrentAccount(userAccounts.get(accountIndexChosen));
            accountMenu();
        }
    }
    public static void accountMenu(){
        /*Account-Level Menu
         *      User Choice: Check Balance, Withdraw, Deposit, Transfer, View Transactions, Close Account, Switch Account, Logout
         *          goes to corresponding menu depending upon user choice               */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currentAcc = am.getCurrentAccount();
        System.out.println("      "+currentAcc.getAccountType()+" -- Acct #: "+currentAcc.getId()+"\n");
        System.out.println("  Check Balance (c)   --    Withdraw (w)   --    Deposit (d)     -- Transfer (t) \n");
        System.out.println("View Transactions (v) -- Close Account (x) -- Switch Account (s) --  Logout (l) \n\n");
        String userInput="";
        while (!userInput.equals("c")&&!userInput.equals("w")&&!userInput.equals("d")&&!userInput.equals("t")&&
                !userInput.equals("v")&&!userInput.equals("x")&&!userInput.equals("s")&&!userInput.equals("l")){
            userInput = promptForText("Enter Decision: ").toLowerCase();
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
            userMenu();
        }
        else if(userInput.equals("l")){
            logout();
        }
    }
    public static void promptCredentials(){
        /*Asks the user for their username and pin #.  Sends to the Authenticator for verification.
         If failure, return to -> loginMenu()
         If success, set currentUser, go to -> userMenu()                */
        clearScreen();
        String userNameInput = promptForText("Enter Username: ");
        int pinInput=0;
        while(pinInput<1000||(pinInput>9999&&pinInput<1000000)||pinInput>9999999){ //while not 4 or 7 digits
            pinInput = promptForPositiveInt("Enter Pin: ");
        }
        Authenticator auth = Authenticator.getAuthenticator();
        boolean authenticated = auth.authenticate(userNameInput,pinInput);
        if(authenticated){
            UserManager um = UserManager.getUserManager();
            um.setCurrentUser(um.getUser(userNameInput));
            User currentUser = um.getCurrentUser();
            if(currentUser.isAdmin()){
                adminMenu();
            }
            else {
                userMenu();
            }
        }
        else{
            System.out.println("Login failed.");
            delayedPrint(2000);
            loginMenu();
        }
    }
    public static void adminMenu(){
        //Asks the administrator to choose an option, then calls the corresponding method menu.
        clearScreen();
        System.out.println("                             Admin Menu  \n");
        System.out.println("Collect Deposits (c) - Restock Withdrawal Tray (r) - Withdrawal Tray Status (w)");
        System.out.println(" View Transactions (v) -    Unflag User (u)    -      Logout (l) \n");
        String userInput = "";
        while (true){
            userInput = promptForText("Enter Decision: ").toLowerCase();
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
                logout();
            }
        }
    }
    public static void createProfile(){
        /* Asks user for their attributes / account properties
         Creates user from input
         Sets current user to created user
         Goes to -> userMenu()                     */
        clearScreen();
        UserManager um =UserManager.getUserManager();
        String userName = ""; String firstName = ""; String lastName = ""; String email = "";
        while (true) {
            userName = promptForText("Enter Desired Username: ").toLowerCase();
            //check username availability:
            if (um.getUser(userName) != null) {
                System.out.println("Username unavailable.");
                delayedPrint(800);
            }
            else {
                break;
            }
        }
        int pin=0;
        while (pin<1000||pin>9999){
            pin=promptForPositiveInt("Enter desired 4-digit pin number: ");

            if(pin<1000||pin>9999){
                System.out.println("Please ensure your pin is 4 digits.");
            }
        }
        while (true){
            firstName = promptForText("Enter your first name: ");
            firstName = firstName.replace(",","");
            firstName = firstName.replace("\n","");
            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = promptForText("Your first name is: "+firstName+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        while (true){
            lastName = promptForText("Enter your last name: ");
            lastName = lastName.replace(",","");
            lastName = lastName.replace("\n","");
            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = promptForText("Your last name is: "+lastName+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        while (true){
            email = promptForText("Enter your e-mail address: ");
            email = email.replace(",","");
            email = email.replace("\n","");
            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = promptForText("Your e-mail is: "+email+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        String secQues1 = "What was your first pet's name?";
        String secQues2 = "In which city were you born?";
        String secQues3 = "Who was the first person to see you naked?";

        int userAnswer = 0;
        while (userAnswer!=1&&userAnswer!=2&&userAnswer!=3){
            System.out.println("Please choose a security question: \n");
            System.out.println("(1) : "+secQues1);
            System.out.println("(2) : "+secQues2);
            System.out.println("(3) : "+secQues3);
            userAnswer=promptForInt("\n Enter a question 1-3");
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
        String securityAnswer = "";
        while (true){
            securityAnswer = promptForText("Enter your answer: ");
            securityAnswer = securityAnswer.replace(",","");
            securityAnswer = securityAnswer.replace("\n","");
            String input = "";
            while (!input.equals("y")&&!input.equals("n")){
                input = promptForText("Your answer is: "+securityAnswer+", is that correct? (y/n)").toLowerCase();
            }
            if(input.equals("y")){
                break;
            }
        }
        um.addUser(userName,firstName,lastName,email,pin,securityQuestion,securityAnswer);
        um.setCurrentUser(um.getUser(userName));
        userMenu();
    }
    public static void createAccount(){
        /* Asks the user what account type they want to create (checking/savings/business)
         *       Creates an account of the chosen type.
         *           Set's current account to the created account.
         *               Goes to -> accountMenu()                                                  */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        System.out.println("Account Types: \n Checking (c)\n Savings (s)\n Business (b) \n");
        String userInput="";
        while (userInput.length()!=1||(userInput.charAt(0)!='c'&&userInput.charAt(0)!='s'&&userInput.charAt(0)!='b')){
            userInput=promptForText("Enter a valid account type: ").toLowerCase();
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
        am.setCurrentAccount(allAccounts.get(allAccounts.size()-1));
        accountMenu();
    }

    public static void withdraw(){
        /* If user has <$5, prompts them to empty their account,
         *           if yes, sets withdraw amount to balance
         *           if no, goes to -> accountMenu()
         * User has >$5:
         *       prompts user to enter withdraw amount in $5 increments
         *           validates that amount is in $5 increments
         *           validates that amount is <= $300 maximum withdrawal
         *   Checks atm tray to ensure withdraw amount can be processed
         *      if yes: processes withdrawal, success message, goes to -> accountMenu()
         *      if no: error message, goes to -> accountMenu()                    */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        double currBalance = currAccount.getBalance();
        double userWithdraw = 0;
        if(currBalance<10.0){
            System.out.println("You have insufficient funds to process a withdrawal on this account.");
            System.out.println("You must have at least $10 to perform a partial withdraw.");
            System.out.println("You may withdraw all available funds if you wish.  Current Balance: "+currBalance);
            String userAnswer = "";
            while (!userAnswer.equals("w")&&!userAnswer.equals("r")) {
                userAnswer = promptForText("\n    Withdraw All Funds (w) -- Return to Account Manager (x)  ").toLowerCase();
            }
            if (userAnswer.equals("x")){
                accountMenu();
                //don't want to use return here, always want to start at the top of all menu's
            }
            else {
                userWithdraw=currBalance;
            }
        }
        else if(currBalance>=10.0) {
            boolean gettingAmount = true;
            while (gettingAmount) {
                userWithdraw = promptForPositiveDouble("What amount would you like to withdraw? ($10 increments)");
                if (userWithdraw > currBalance) {
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
        if (withdrawSuccess==false){
            System.out.println("We're sorry. Our ATM is running low on bills and cannot support a withdrawal of this amount at the current time.");
            delayedPrint(2000);
            accountMenu();
        }
        else {
            am.withdrawl(userWithdraw);
            System.out.println("Dispensing Cash.."); delayedPrint(2000);

            accountMenu();
        }
    }
    public static void deposit(){
        /* Prompts for deposit amount and number of bills
         *       Validates number of bills && space in atm
         *           If failure: error message -> accountMenu()
         *           If success: process deposit -> accountMenu()        */
        ATM atm = ATM.getATM();
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        int depositAmount=promptForPositiveInt("How much are you depositing? ");
        int numBills = promptForPositiveInt("Enter the number of bills you are depositing.");

        boolean depositSuccess = atm.deposit(numBills,depositAmount);
        if(!depositSuccess){
            System.out.println("Apologies!  The ATM cannot accept that number of bills at this time.  Come back soon!");
            accountMenu();
        }
        else {
            am.deposit(depositAmount);
            System.out.println("$"+depositAmount+" deposited into your account.");
            accountMenu();
        }
    }
    public static void transfer(){
        /* Prompts user for account # destination && amount to transfer
         *       Validates account && balance
         *           if failure : error message, goes to-> accountMenu()
         *           if success : processes transaction, goes to -> accountMenu()       */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        double currBalance = currAccount.getBalance();
        if(currBalance<=0){
            System.out.println("Account balance : 0.");
            delayedPrint(1400,"Unable to perform transfer.");
            delayedPrint(1400);
            accountMenu();
        }
        int idToTransfer = 0;
        double amountToTransfer=0;
        idToTransfer=promptForPositiveInt("Enter Account Number to transfer funds to: ");
        Account destinationAccount = am.getAccount(idToTransfer);
        if(destinationAccount==null){
            System.out.println("Not a valid account number.");
            delayedPrint(1500);
            accountMenu();
        }
        else {
            while (true) {
                amountToTransfer = promptForPositiveDouble("Enter amount to transfer.");
                if (amountToTransfer > currBalance) {
                    System.out.println("Insufficient funds.  Current balance: " + currBalance);
                    delayedPrint(1500);
                } else if (amountToTransfer <= currBalance) {
                    break;
                }
            }
        }
        if(amountToTransfer>0){
            currAccount.withdraw(amountToTransfer);
            destinationAccount.deposit(amountToTransfer);
        }
        accountMenu();
    }
    public static void viewTransactions(){
        /*  Prints out all transaction history of current user
         *       When user is finished, goes to -> accountMenu()        */
        clearScreen();
        TransactionManager tm = TransactionManager.getTransactionManager();
        ArrayList<Transaction> userTrans = tm.getCurrentAccountTransactions();
        for(Transaction t:userTrans){
            System.out.println(t.getDate()+ " - "+t.getTransactionType()+" - "+t.getAmount());
        }
        promptForText("Press RETURN when finished.");
        accountMenu();
    }
    public static void logout(){
        /* Sets current user & current account fields to null
         *       Logging out message, goes to -> loginMenu()         */
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        UserManager um = UserManager.getUserManager();
        //null current fields
        am.setCurrentAccount(null);
        um.setCurrentUser(null);
        System.out.println("Logging out..");
        delayedPrint(2000);

        loginMenu();
    }
    public static void checkBalance(){
        //Prints out current balance, when finished, goes to -> accountMenu()
        clearScreen();
        AccountManager am = AccountManager.getAccountManager();
        Account currAccount = am.getCurrentAccount();
        System.out.println(currAccount.getAccountType()+ " no. "+currAccount.getId()+"\n\n");
        System.out.println("Current Balance: "+currAccount.getBalance());
        promptForText("Press RETURN when finished.");
        accountMenu();
    }

    public static void closeAccount(){
        /* Prompts user to ensure they want to close their account
         *       if yes: closes account, goes to -> userMenu()
         *       if no: goes to -> accountMenu()                     */
        clearScreen();
        UserManager um = UserManager.getUserManager();
        String userInput="";
        while (!userInput.equals("y")&&!userInput.equals("n") ){
            promptForText("Are you sure? (y/n)").toLowerCase();
        }
        if(userInput.equals("y")){
            um.closeCurrentAccount();
            System.out.println("Account Closed.");
            delayedPrint(2000);
            userMenu();
        }
        else {
            accountMenu();
        }
    }
    //ADMIN SUB-MENUs
    public static void collectDeposits(){
        //Empties the deposit tray from ATM and returns to -> adminMenu()
        clearScreen();
        ATM atm = ATM.getATM();
        System.out.println("Please Retrieve Deposits..");
        atm.emptyDepositTray();
        delayedPrint(2000,"Returning to Admin Menu");
        delayedPrint(1200);
        adminMenu();
    }
    public static void restockWithdrawalTray(){
        /*Prompts admin to enter number of each bill they want to stock
         *      checks legality
         *          if illegal -> returns to adminMenu()
         *          if illegal -> processes restock, returns to -> adminMenu() */
        clearScreen();
        ATM atm = ATM.getATM();
        int numTwenties = promptForPositiveInt("Enter the number of twenty dollar bills:");
        int numTens = promptForPositiveInt("Enter the number of tens:");

        boolean restockSuccess = atm.reloadWithdrawalTray(numTwenties,numTens);

        if(restockSuccess){
            System.out.println("ATM successfully restocked.");
            delayedPrint(2000,"Returning to Admin Menu");
            delayedPrint(1200);
            adminMenu();
        }
        else{
            System.out.println("Restocking Unsuccessful.  Check Withdrawal Tray Status and Try Again. ");
            delayedPrint(2000,"Returning to Admin Menu");
            delayedPrint(1200);
            adminMenu();
        }
    }
    public static void withdrawalStatus(){
        //Prints out the status of the withdrawal trays in the ATM then returns to -> adminMenu()
        clearScreen();
        ATM atm = ATM.getATM();
        System.out.println("Current Withdrawal Tray Balance :   "+atm.getATMBalance);
        HashMap<Integer,Integer> withdrawalTray = atm.getWithdrawlTray();
        int totalBills = withdrawalTray.get(20)+withdrawalTray.get(10);
        System.out.println(" Twenties  : "+withdrawalTray.get(20));
        System.out.println("   Tens    : "+withdrawalTray.get(10));
        System.out.println("\nTotal Bills: "+totalBills);
        System.out.println("Max Capacity: 2,000 bills");

        delayedPrint(1500);
        promptForText("\n When finished, hit RETURN.");
        adminMenu();
    }
    public static void viewAllTransactions(){
        //Prints out the entire transaction history of the ATM, then returns to -> adminMenu()
        clearScreen();
        TransactionManager tm = TransactionManager.getTransactionManager();
        ArrayList<Transaction> allTrans = tm.getAllTransactions();
        for(Transaction t:allTrans){
            System.out.println(t.getDate()+ " - "+t.getTransactionType()+" - "+t.getAmount());
        }
        delayedPrint(1500);
        promptForText("Press RETURN when finished.");
        accountMenu();
    }
    public static void unflagUser(){
        /* Prompts for a username to unflag
         *      Deflags if user is flagged, returns to -> adminMenu()*/
        clearScreen();
        UserManager um = UserManager.getUserManager();
        Authenticator am = Authenticator.getAuthenticator();
        String userName = promptForText("Enter the user's username:");
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
        delayedPrint(1400,"Returning to Admin Menu.");
        adminMenu();
    }

    //UTILITY METHODS
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

