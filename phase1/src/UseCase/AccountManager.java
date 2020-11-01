package UseCase;
import Entity.*;

import java.util.HashMap;

public abstract class AccountManager {
    protected static int TotalNumOfAccount = 0;
    protected HashMap<Integer, Account> accountList;
    protected static int currAccountId = -1; // Track which account this program is working on now.

    public AccountManager(){
        this.accountList = new HashMap<>();
    }


    public abstract boolean createAccount(String username, String password);

    public void setCurrAccountId(int accountId){
        currAccountId = accountId;
    }

    public Account getCurrAccount(){
        return this.accountList.get(currAccountId);
    }

    public boolean changeUsername(String username){
        for (Account x: this.accountList.values()){
            if(x.getUsername().equals(username)) return false;
        }
        Account currAcc = getCurrAccount();
        currAcc.setUsername(username);
        return true;
    }

    public void changePassword(String password){
        Account currAcc = getCurrAccount();
        currAcc.setPassword(password);
    }

    public abstract boolean messageable(Account other);


    public void showAllReceivedMsg(){
        Account currAcc = getCurrAccount();
        //TODO: print all Messages of this Account.(through presenter user case?)
    }

    public void showMsgWith(Account other){
        Account currAcc = getCurrAccount();
        //TODO: print all Messages between this account and <other>.
        // (through presenter use case?)
    }

    public boolean sendMessageTo(Account other, int MsgId){
        if (!messageable(other)) return false;
        Account currAcc = getCurrAccount();
        currAcc.addSentMessage(MsgId);
        other.addInbox(MsgId);
        return true;
    }

}
