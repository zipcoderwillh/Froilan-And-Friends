package io.froilanandfriends.atm;

/**
 * Extends {@link Account} to represent a business account.
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
