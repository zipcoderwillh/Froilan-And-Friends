package io.froilanandfriends.atm;

/**
 * <h1>Class CheckingAccount</h1>
 * <p>Type of account for checking accounts</p>
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
