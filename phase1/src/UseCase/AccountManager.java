package UseCase;
import Entity.*;

import java.util.HashMap;

/**
 * The AccountManager class is an abstract class with basic functionalities implemented.
 */
public class AccountManager{
    protected static int TotalNumOfAccount = 0;
    protected static HashMap<Integer, Account> accountList = new HashMap<>();
    protected static int currAccountId = -1; // Track which account this program is working on now.

    public AccountManager(){}


    /**
     * Get the id of current login account.
     * @return An integer representing id.
     */
    public int getCurrAccountId(){ return currAccountId;}

    /**
     * Get the current login account.
     * @return An account which id is currAccountId.
     */
    public Account getCurrAccount(){
        return accountList.get(currAccountId);
    }

    /**
     * Get the total number of accounts in the system.
     * @return An integer representing the total number of accounts.
     */
    public int getTotalNumOfAccount(){ return TotalNumOfAccount;}

    /**
     * Get the account with a given account id.
     * @param accountId The account id.
     * @return An account with the given id.
     */
    public Account getAccountWithId(int accountId){ return accountList.get(accountId);}

    /**
     * Check if the current login account can message a given account.
     * @param otherId ID of another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    public boolean messageable(int otherId){return false;}

    /**
     * Change the username of current login account.<br>
     * (This method checks duplicated username.)
     * @param username The new username.
     * @return True iff username is successfully changed.
     */
    public boolean changeUsername(String username){
        for (Account x: accountList.values()){
            if(x.getUsername().equals(username)) return false;
        }
        Account currAcc = getCurrAccount();
        currAcc.setUsername(username);
        return true;
    }

    /**
     * Change the password of the current login account.
     * @param password The new password.
     */
    public void changePassword(String password){
        Account currAcc = getCurrAccount();
        currAcc.setPassword(password);
    }

    /**
     * Show all messages that the current login account has received.
     */
    public void showAllReceivedMsg(){
        Account currAcc = getCurrAccount();
        //TODO: print all Messages of this Account.(through presenter use case?)
    }

    /**
     * Show messages between the current login account and a given account.
     * @param otherId ID of another account.
     */
    public void showMsgWith(int otherId){
        Account currAcc = getCurrAccount();
        //TODO: print all Messages between this account and <other>.
        // (through presenter use case?)
    }

    public boolean existsUsername(String username){
        if (accountList.size() == 0) return false;
        for (Account x: accountList.values()){
            if(x.getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean loginAccount(String username, String password){
        for (Account x: accountList.values()){
            if (x.getUsername().equals(username) &&
                    x.getPassword().equals(password)){
                currAccountId = x.getUserId();
                return true;
            }
        }
        return false;
    }

    public HashMap<Integer, Account> getAccountList(){ return new HashMap<>(accountList);}

}
