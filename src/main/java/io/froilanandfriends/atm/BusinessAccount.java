package io.froilanandfriends.atm;

/**
 * Created by johnb on 2/10/16.
 */
public class BusinessAccount extends Account {
    BusinessAccount(){
        super();
        //set account type to business
        accountType = AccountType.BUSINESS;
    }

    BusinessAccount(String accountString)
    {
        super(accountString);
    }
}
