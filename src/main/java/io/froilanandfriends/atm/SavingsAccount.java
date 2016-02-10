package io.froilanandfriends.atm;

/**
 * Created by johnb on 2/10/16.
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
