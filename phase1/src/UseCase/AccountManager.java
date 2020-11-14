package UseCase;
import Entity.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The AccountManager class is a class with basic functionalities implemented.
 */
public class AccountManager {
    protected static int TotalNumOfAccount = 0;
    protected static HashMap<Integer, Account> accountList = new HashMap<>();
    protected static int currAccountId = -1;

    public AccountManager(){}

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    public boolean messageable(Account other){return false;};

    public void createAccount(String username, String password, int userType){
        switch (userType){
            case 0:
                accountList.put(TotalNumOfAccount,
                        new Organizer(username, password, TotalNumOfAccount));
                break;
            case 2:
                accountList.put(TotalNumOfAccount,
                        new Speaker(username, password, TotalNumOfAccount));
                break;
            default:
                accountList.put(TotalNumOfAccount,
                        new Attendee(username, password, TotalNumOfAccount));
                break;
        }
        TotalNumOfAccount += 1;
    }

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

    public String getinfoacc(int id){
        Account target = getAccountWithId(id);
        String targetname = target.getUsername();
        String a = targetname + "id:" + id + "\n";
        return a;
    }

    public void addinbox(int getterid, int msgid){getAccountWithId(getterid).addInbox(msgid);}

    public void addsend(int senderid, int msgid){getAccountWithId(senderid).addSentMessage(msgid);}


}

