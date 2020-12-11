package UseCase;
import Entity.*;
import UseCase.IGateWay.IUserGateWay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The AccountManager class is a class with basic functionalities implemented.
 */
public class AccountManager {
    protected static int TotalNumOfAccount = 0;
    protected static HashMap<Integer, Account> accountList = new HashMap<>();
    protected static int currAccountId = -1;
    protected IUserGateWay gateWay;

    public AccountManager(IUserGateWay g){ this.gateWay = g;}

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    public boolean messageable(Account other){return false;}

    /**
     * Create account according to the given user type, with the provided username and password.
     * @param username string representation of the username.
     * @param password string representation of the password.
     * @param userType int representation of the user type, 0 if Organizer, 2 if Speaker, and default Attendee.
     */
    public void createAccount(String username, String password, int userType){
        try{
            gateWay.writeNewAcc(TotalNumOfAccount, username, password, userType);
        }catch (IOException ignored){}
        createHelper(username, password, userType);
    }

    public void scanInAccount(String username, String password, int userType){
        createHelper(username, password, userType);
    }

    private void createHelper(String username, String password, int userType) {
        switch (userType){
            case 0:
                accountList.put(TotalNumOfAccount,
                        new Organizer(username, password, TotalNumOfAccount));
                break;
            case 2:
                accountList.put(TotalNumOfAccount,
                        new Speaker(username, password, TotalNumOfAccount));
                break;
            case 3:
                accountList.put(TotalNumOfAccount,
                        new VIP(username, password, TotalNumOfAccount));
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
     * Get the name of current login account.
     * @return A string representing name.
     */
    public String getCurrAccountName(){ return getCurrAccount().getUsername();}


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
     * Check if the username already exists.
     * @param username String representation of the username.
     * @return True iff the given username exists.
     */
    public boolean existsUsername(String username){
        if (accountList.size() == 0) return false;
        for (Account x: accountList.values()){
            if(x.getUsername().equals(username)) return true;
        }
        return false;
    }

    /**
     * Check if the username and the password are correct. If correct, change the current Account to the given account.
     * @param username String representation of the username.
     * @param password String representation of the password.
     * @return True iff the given username and password can match.
     */
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

    /**
     * Get the Account list in the format of hashmap, where keys are the IDs and values are the Accounts.
     * @return The Hashmap of account list.
     */
    public HashMap<Integer, Account> getAccountList(){ return new HashMap<>(accountList);}

    /**
     * Get the Account info given the ID, including the name and the ID.
     * @param id int representation of ID.
     * @return a string consisting of Account name and ID.
     */
    public String getinfoacc(int id){
        Account target = getAccountWithId(id);
        String targetname = target.getUsername();
        return targetname + "id:" + id + "\n";
    }

    /**
     * Add the message ID to the given message getter's UnreadInbox.
     * @param getterid message getter's ID as int.
     * @param msgid message ID as int.
     */
    public void addMsgToUnreadInbox(int getterid, int msgid){
        getAccountWithId(getterid).addUnreadInbox(msgid);
        try{
            this.gateWay.updateUnreadInbox(getterid, getCurrAccount().getUnreadInbox());
        }catch (IOException ignored){}
    }

    public void addMsgToSentBox(int senderid, int msgid){
        getAccountWithId(senderid).addSentBox(msgid);
        try{
            this.gateWay.updateSentBox(senderid, getCurrAccount().getSentBox());
        }catch (IOException ignored){}
    }

    public void addMsgToInBox(int senderid, int msgid){
        getAccountWithId(senderid).addInbox(msgid);
        try{
            this.gateWay.updateInbox(senderid, getCurrAccount().getInbox());
        }catch (IOException ignored){}
    }

    public void addMsgToArchiveBox(int accId, int msgId){
        getAccountWithId(accId).addArchiveBox(msgId);
        try{
            this.gateWay.updateArchiveBox(accId, getCurrAccount().getArchiveBox());
        }catch (IOException ignored){}
    }


    public void deleteMsg(int msgId, int accId){
        Account acc = getAccountWithId(accId);
        acc.removeMsgFromInbox(msgId);
        acc.removeMsgFromUnreadInbox(msgId);
        acc.removeMsgFromArchiveBox(msgId);
        try {
            this.gateWay.updateInbox(accId, acc.getInbox());
            this.gateWay.updateInbox(accId, acc.getUnreadInbox());
            this.gateWay.updateArchiveBox(accId, acc.getArchiveBox());
        }catch (IOException ignored){}
    }
    /**
     * Get the user name of the Account according to the given account ID.
     * @param accID int representation of account ID.
     */
    public String getUserName(int accID){
        Account account = getAccountWithId(accID);
        return account.getUsername();
    }

    /**
     * Check if the given account id is a Speaker.
     * @param userID The id of an account.
     * @return True iff the given account is a Speaker.
     */
    public boolean isSpeakerAcc(int userID){ return getAccountWithId(userID).getUserType() == 2;}

    /**
     * Check if the given account id is an Organizer.
     * @param userID The id of an account.
     * @return True iff the given account is an Organizer.
     */
    public boolean isOrganizerAcc(int userID){ return getAccountWithId(userID).getUserType() == 0;}

    /**
     * Check if the given account id is an Attendee.
     * @param userID The id of an account.
     * @return True iff the given account is an Attendee.
     */
    public boolean isAttendeeAcc(int userID){ return getAccountWithId(userID).getUserType() == 1;}

    /**
     * Check if the given account id is a VIP.
     * @param userID The id of an account.
     * @return True iff the given account is a VIP.
     */
    public boolean isVIPAcc(int userID){ return getAccountWithId(userID).getUserType() == 3;}

    /**
     * Archive a message for a ccount.
     * @param msgId message id
     * @param accId account id
     */
    public void archiveMsg(int msgId, int accId){
        Account acc =  getAccountWithId(accId);
        acc.removeMsgFromInbox(msgId);
        acc.addArchiveBox(msgId);

        try {
            this.gateWay.updateInbox(accId, acc.getInbox());
            this.gateWay.updateArchiveBox(accId, acc.getArchiveBox());
        }catch (IOException ignored){}
    }

    public void markAsRead(int accId, int msgId){
        Account acc = getAccountWithId(accId);
        acc.removeMsgFromUnreadInbox(msgId);
        acc.addInbox(msgId);

        try {
            this.gateWay.updateInbox(accId, acc.getInbox());
            this.gateWay.updateUnreadInbox(accId, acc.getUnreadInbox());
        }catch(IOException ignored){}
    }

    public void markAsUnread(int accId, int msgId){
        Account acc = getAccountWithId(accId);
        acc.removeMsgFromInbox(msgId);
        acc.addUnreadInbox(msgId);

        try {
            this.gateWay.updateInbox(accId, acc.getInbox());
            this.gateWay.updateUnreadInbox(accId, acc.getUnreadInbox());
        }catch(IOException ignored){}
    }

    public int getmyapp(){return getCurrAccount().getapplication();}

    public void changemyapp(int a){getCurrAccount().setApplication(a);}

    public String getPassword(int accID){
        Account account = getAccountWithId(accID);
        return account.getPassword();
    }

    public ArrayList<Integer> getUnreadInboxWithId(int accId){
        Account acc = getAccountWithId(accId);
        return acc.getUnreadInbox();
    }

}

