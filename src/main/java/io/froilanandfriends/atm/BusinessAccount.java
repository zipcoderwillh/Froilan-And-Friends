package io.froilanandfriends.atm;

/**
 * <h1>Class BusinessAccount</h1>
 * <p>Type of account for business accounts</p>
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
