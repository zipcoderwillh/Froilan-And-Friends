package io.froilanandfriends.atm;

import java.util.HashMap;

public class ATM {

    private int depositTray; // Total number of bills deposited in the machine
    private int depositValue; // Running dollar total of all deposits
    private HashMap<Integer,Integer> withdrawalTray = new HashMap<Integer, Integer>();  // Number of 20-dollar and 10-dollar bills
    private int ATMBalance;  // Total dollar amount available to WITHDRAW (does not include value of deposits
    private final int MAXCAPACITY = 2000;   // Max number of bills the ATM can hold, including deposit and
    // withdrawal trays. (Arbitrarily set to 2000 for now.)

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

    public static ATM getATM() {
        return CURRENT;
    }

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

    public boolean reloadWithdrawalTray(int twenties, int tens) {

        boolean result = false;
        int currentNumberOfBills = withdrawalTray.get(20) + withdrawalTray.get(10) + depositTray;

        // Make sure attempted reload doesn't exceed ATM's max capacity.
        // If ok, go ahead and add bills to trays and increase ATMBalance.
        if( (currentNumberOfBills + twenties + tens) <= MAXCAPACITY ) {
            withdrawalTray.put(20, twenties);
            withdrawalTray.put(10, tens);
            ATMBalance += (twenties * 20) + (tens * 10);
            result = true;
        }

        return result;

    }

    public boolean deposit(int numBills, int amount){

        boolean result = false;
        int currentNumberOfBills = withdrawalTray.get(20) + withdrawalTray.get(10) + depositTray;

        // Make sure deposit will not exceed ATM's max bill capacity.
        // If not, add bills to depositTray and increase depositValue by deposit amount.
        if(currentNumberOfBills + numBills <= MAXCAPACITY) {
            depositTray += numBills;
            depositValue += amount;
            result = true;
        }

        return result;
    }

    // Admin functionality
    public void emptyDepositTray(){
        depositTray = 0;
        depositValue = 0;
    }

    public int getDepositTray() {
        return depositTray;
    }

    public int getDepositValue() {
        return depositValue;
    }

    public HashMap<Integer, Integer> getWithdrawlTray() {
        return withdrawalTray;
    }

    public int getATMBalance() {
        return ATMBalance;
    }

    public static void main(String[] args) {

        ATM atm = new ATM();
        // This will eventually kick off... UserInterface?

    }

}