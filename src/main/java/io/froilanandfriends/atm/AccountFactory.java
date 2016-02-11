package io.froilanandfriends.atm;

/**
 * Created by johnb on 2/11/16.
 */
public class AccountFactory {


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

    public Account create(String accountString) {
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
