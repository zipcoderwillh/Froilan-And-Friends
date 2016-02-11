package io.froilanandfriends.atm;

import java.util.HashMap;

/**
 * This class houses the core ATM functionality, including withdrawals, deposits, and ensuring that the ATM's
 * maximum capacity is not exceeded. It also handles admin user functions such as emptying and refilling
 * the withdrawal and deposit trays. The class is implemented as a singleton in order to avoid creating more than
 * one instance at a time.
 *
 * @author willhorton
 * @version 1.0
 */

public class ATM {

    private int depositTray; // Total number of bills deposited in the machine
    private int depositValue; // Running dollar total of all deposits
    private HashMap<Integer,Integer> withdrawalTray = new HashMap<Integer, Integer>();  // Number of 20-dollar and 10-dollar bills
    private int ATMBalance;  // Total dollar amount available to WITHDRAW (does not include value of deposits
    private final int MAXCAPACITY = 2000;   // Max number of bills the ATM can hold, including deposit and
                                            // withdrawal trays. (Arbitrarily set to 2000 for now.)
    private static boolean firstOn = true;


    // Make constructor private so nothing can instantiate ATM outside of singleton instance
    private ATM() {
        depositTray = 0;
        depositValue = 0;
        withdrawalTray.put(20, 250);
        withdrawalTray.put(10, 500);
        ATMBalance = 10000;
    }

    // Set up singleton instance
    private static final ATM CURRENT = new ATM();

    /**
     * Getter method that returns the current singleton instance of the class.
     * @return A reference to current singleton ATM instance.
     */
    public static ATM getATM() {
        return CURRENT;
    }

    /**
     * Returns true if the ATM is booting up for the first time.
     * @return True or false.
     */
    public static boolean isFirstOn() {
        return firstOn;
    }

    /**
     * Allows admin to set the firstOn flag to false after ATM is configured initially.
     * @param firstOn A boolean flag indicating whether the ATM is booting up for the first time.
     */
    public static void setFirstOn(boolean firstOn) {
        ATM.firstOn = firstOn;
    }

    /**
     * Withdraws cash from the ATM in increments of ten- or twenty-dollar bills. Rejects the transaction
     * if the requested amount is not divisible by ten or if the withdrawal amount exceeds the current value of the
     * ATM's withdrawal tray. If the transaction is successful, decreases both the ATM's current balance and the
     * number of bills in the machine. Covers as much of the amount as possible with twenties, then covers the rest
     * with tens.
     * @param amountToWithdraw An integer specifying the requested withdrawal amount.
     * @return True if deposit is successful, false if unsuccessful.
     */
    public boolean withdraw(int amountToWithdraw){

        // First, check if there is enough money in the ATM to cover the withdrawal at all
        if(amountToWithdraw > ATMBalance) return false;

        boolean result = false;
        int twentiesAvailable = withdrawalTray.get(20);
        int tensAvailable = withdrawalTray.get(10);

        // Second, check if withdrawal amount is divisible by 20. If so, determine what combination of 20s and 10s
        // is available to cover the withdrawal amount (try all 20s first, then cover any remaining with 10s).
        // Decrease ATMBalance by withdrawal amount.
        if(amountToWithdraw % 20 == 0) {
            result = true;
            ATMBalance -= amountToWithdraw;
            if(amountToWithdraw / 20 <= twentiesAvailable) {
                withdrawalTray.put(20, twentiesAvailable - (amountToWithdraw / 20));
            } else {
                int tensNeeded = (amountToWithdraw - (twentiesAvailable * 20)) / 10;
                withdrawalTray.put(20, 0);
                withdrawalTray.put(10, tensAvailable - tensNeeded);
            }
        }
        // Third, if withdrawal amount is divisible by 10, determine what combo of 20s and 10s is available
        // to cover the withdrawal amount (do as much with 20s as possible, then cover any remaining with 10s).
        // Decrease ATMBalance by withdrawal amount.
        else if(amountToWithdraw % 10 == 0 && tensAvailable >= 1) {
            result = true;
            ATMBalance -= amountToWithdraw;
            int twentiesNeeded = (amountToWithdraw - 10) / 20;
            if(twentiesAvailable >= twentiesNeeded) {
                withdrawalTray.put(20, twentiesAvailable - twentiesNeeded);
                withdrawalTray.put(10, tensAvailable - 1);
            } else {
                int tensNeeded = amountToWithdraw - twentiesAvailable;
                withdrawalTray.put(20, 0);
                withdrawalTray.put(10, tensAvailable - tensNeeded);
            }
        }

        // If withdrawal amount isn't divisible by 20 or 10, automatically return false.
        return result;
    }

    /**
     * Provides admin functionality to reload the withdrawal tray with any number of tens and/or twenties. Rejects
     * the reload if the amount would exceed the ATM's total bill capacity. If successful, increases both the ATM's
     * current balance and the number of bills in the withdrawal tray.
     * @param twenties An integer specifiying the number of twenties to be loaded.
     * @param tens An integer specifiying the number of tens to be loaded.
     * @return True if transaction was successful, false if it was not.
     */
    public boolean reloadWithdrawalTray(int twenties, int tens) {

        boolean result = false;
        int currentNumberOfBills = withdrawalTray.get(20) + withdrawalTray.get(10) + depositTray;

        // Make sure attempted reload doesn't exceed ATM's max capacity.
        // If ok, go ahead and add bills to trays and increase ATMBalance.
        if( (currentNumberOfBills + twenties + tens) <= MAXCAPACITY ) {
            withdrawalTray.put(20, withdrawalTray.get(20)+twenties);
            withdrawalTray.put(10, withdrawalTray.get(10)+tens);
            ATMBalance += (twenties * 20) + (tens * 10);
            result = true;
        }

        return result;

    }

    /**
     * Allows customers to deposit any whole number amount, provided that the amount does not exceed the ATM's
     * total bill capacity. If successful, increases both the ATM's current balance and the number of bills in the
     * deposit tray. Assumes that the amount consists of the greatest number of twenties possible, with the rest
     * covered by a combination of tens, fives, and ones.
     * @param amount An integer specifying the amount to deposit (the ATM will only accept deposits in bills).
     * @return True if transaction is successful, false if unsuccessful.
     */
    public boolean deposit(int amount){

        boolean result = false;
        int currentNumberOfBills = withdrawalTray.get(20) + withdrawalTray.get(10) + depositTray;
        int remainder = amount % 20;

        // Assume that deposit amount can be covered by 20s as much as possible
        int numberOfTwenties = (amount - remainder) / 20;

        // If 20s can't cover full amount, try a 10 for the rest
        int numberOfTens = remainder >= 10 ? 1 : 0;
        remainder -= numberOfTens * 10;

        // If 10s can't cover the rest, try a 5
        int numberOfFives = remainder >= 5 ? 1 : 0;
        remainder -= numberOfFives * 5;

        // If 5s can't cover the rest, try 1s
        int numberOfOnes = remainder % 5;

        int totalBills = numberOfTwenties + numberOfTens + numberOfFives + numberOfOnes;

        // Make sure deposit will not exceed ATM's max bill capacity.
        // If not, add bills to depositTray and increase depositValue by deposit amount.
        if(currentNumberOfBills + totalBills <= MAXCAPACITY) {
            depositTray += totalBills;
            depositValue += amount;
            result = true;
        }

        return result;
    }

    /**
     * Provides admin functionality to empty the ATM's deposit tray.
     */
    public void emptyDepositTray(){
        depositTray = 0;
        depositValue = 0;
    }

    /**
     * Returns the current number of bills in the ATM's deposit tray.
     * @return An integer specifying the number of bills in the deposity tray.
     */
    public int getDepositTray() {
        return depositTray;
    }

    /**
     * Returns the current dollar value of the ATM's deposit tray.
     * @return An integer specifying the value of the deposit tray.
     */
    public int getDepositValue() {
        return depositValue;
    }

    /**
     * Returns the current number of bills in the ATM's withdrawal tray.
     * @return A HashMap with keys 20 and 10 that track the total number of twenties and tens in the ATM's withdrawal tray.
     */
    public HashMap<Integer, Integer> getWithdrawlTray() {
        return withdrawalTray;
    }

    /**
     * Returns the current dollar value of the ATM's withdrawal tray. Does <b>NOT</b> include the value of the deposit tray.
     * @return An integer specifying the dollar value of the withdrawal tray.
     */
    public int getATMBalance() {
        return ATMBalance;
    }

    /**
     * Initializes UserManager
     */
    public void start() {
        try {
            UserManager.getUserManager().loadUsers();
        } catch(Exception e) {
        }

        try {
            AccountManager.getAccountManager().loadAccounts();
        } catch(Exception e) {
        }

        try {
            TransactionManager.getTransactionManager().loadTransactions();
        } catch (Exception e) {
        }

        LoginMenu.promptCredentials();
    }

    public static void main(String[] args) {

        ATM.getATM().start();

    }

}