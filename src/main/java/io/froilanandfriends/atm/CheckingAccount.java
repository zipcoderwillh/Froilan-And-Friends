package io.froilanandfriends.atm;

/**
 * Extends {@link Account} to represent a checking account.
 */
public class CheckingAccount extends Account {
    CheckingAccount(){
        super();
        //set account type to checking
        accountType = AccountType.CHECKING;
    }
    CheckingAccount(String accountString)
    {
        super(accountString);
    }
}
