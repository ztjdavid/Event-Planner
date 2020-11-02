package UseCase;
import Entity.*;

import java.util.HashMap;

/**
 * The AccountManager class is an abstract class with basic functionalities implemented.
 */
public abstract class AccountManager {
    protected static int TotalNumOfAccount = 0;
    protected HashMap<Integer, Account> accountList;
    protected static int currAccountId = -1; // Track which account this program is working on now.

    public AccountManager(){
        this.accountList = new HashMap<>();
    }

    /**
     * Create an account with specific user type.
     * @param username The username of the new account.
     * @param password The password of the new account.
     * @return True iff an account is successfully created.
     */
    public abstract boolean createAccount(String username, String password);

    /**
     * Set the current login account.
     * @param accountId The id of the current login account.
     */
    public void setCurrAccountId(int accountId){
        currAccountId = accountId;
    }

    /**
     * Get the current login account.
     * @return An account which id is currAccountId.
     */
    public Account getCurrAccount(){
        return this.accountList.get(currAccountId);
    }

    /**
     * Get the account with a given account id.
     * @param accountId The account id.
     * @return An account with the given id.
     */
    public Account getAccountWithId(int accountId){ return this.accountList.get(accountId);}

    /**
     * Change the username of current login account.<br>
     * (This method checks duplicated username.)
     * @param username The new username.
     * @return True iff username is successfully changed.
     */
    public boolean changeUsername(String username){
        for (Account x: this.accountList.values()){
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
     * Check if the current login account can message a given account.
     * @param otherId ID of another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    public abstract boolean messageable(int otherId);

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

    /**
     * Send a given message to a given account.
     * @param otherId ID of another account.
     * @param MsgId The id of message being sent.
     * @return True iff the message is successfully sent.
     */
    public boolean sendMessageTo(int otherId, int MsgId){
        if (!messageable(otherId)) return false;
        Account other = getAccountWithId(otherId);
        Account currAcc = getCurrAccount();
        currAcc.addSentMessage(MsgId);
        other.addInbox(MsgId);
        return true;
    }

    public boolean duplicatedUsername(String username){
        for (Account x: this.accountList.values()){
            if(x.getUsername().equals(username)) return false;
        }
        return true;
    }

}
