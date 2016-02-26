package io.froilanandfriends.atm;

/**
 * Factory class which generates a new account when created by a user. Also populates existing accounts from csv file on ATM startup.
 */
public class AccountFactory {

    /**
     * Creates a new account given an enum of type Account.AccountType
     * @param accountType The type of account to create
     * @return retuns the created account
     */
    public Account create(Account.AccountType accountType) {
        Account newAccount;
        switch (accountType)
        {
            case SAVINGS:
                newAccount = new SavingsAccount();
                break;
            case CHECKING:
                newAccount = new CheckingAccount();
                break;
            case BUSINESS:
                newAccount = new BusinessAccount();
                break;
            default:
                return null;
        }
        return newAccount;
    }

    /**
     * Generates an account object from a string read in from an external source
     * @param accountString string representation of an account
     * @return returns the created account
     */
    public Account createFromRecord(String accountString) {
        Account newAccount;
        switch (accountString.substring(0,accountString.indexOf(',')).toUpperCase())
        {
            case "SAVINGS":
                newAccount = new SavingsAccount(accountString);
                break;
            case "CHECKING":
                newAccount = new CheckingAccount(accountString);
                break;
            case "BUSINESS":
                newAccount = new BusinessAccount(accountString);
                break;
            default:
                return null;
        }
        return newAccount;
    }
}
