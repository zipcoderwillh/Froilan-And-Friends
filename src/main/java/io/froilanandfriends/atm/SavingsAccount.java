package io.froilanandfriends.atm;

/**
 * <h1>Class SavingsAccount</h1>
 * <p>Type of account for savings accounts</p>
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
