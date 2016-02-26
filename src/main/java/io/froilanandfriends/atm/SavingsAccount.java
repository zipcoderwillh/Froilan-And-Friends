package io.froilanandfriends.atm;

/**
 * Extends {@link Account} to represent a savings account.
 */
public class SavingsAccount extends Account {
    SavingsAccount(){
        super();
        //set account type to savings
        accountType = AccountType.SAVINGS;
    }

    SavingsAccount(String accountString)
    {
        super(accountString);
    }
}
