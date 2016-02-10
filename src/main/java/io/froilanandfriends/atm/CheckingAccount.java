package io.froilanandfriends.atm;

/**
 * Created by johnb on 2/10/16.
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
