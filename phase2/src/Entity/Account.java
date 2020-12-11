package Entity;
import java.util.ArrayList;

/**
 * The Account class is a class as the superclass of all account classes.<br>
 * It implements methods and stores basic info that all account classes have in common.
 */
public abstract class Account {
    protected String username;
    protected String password;
    protected final int userId;
    protected ArrayList<Integer> sentBox;
    protected ArrayList<Integer> inbox;
    protected ArrayList<Integer> unreadInbox;
    protected ArrayList<Integer> archiveBox;
    protected Integer application;


    public Account(String username, String password, int userId){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.sentBox = new ArrayList<>();
        this.inbox = new ArrayList<>();
        this.unreadInbox = new ArrayList<>();
        this.archiveBox = new ArrayList<>();
        this.application = -1;
    }

    public void setApplication(int application){this.application = application;}

    public int getapplication(){return this.application;}


    /**
     * Set the username for this account.
     * @param username The new username.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Get the username of this account.
     * @return The username of this account.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Set the password for this account.
     * @param password Set password for this account.
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Get the password of this account.
     * @return The password of this account.
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Get the UID of this account.
     * @return the userid of this account.
     */
    public int getUserId(){
        return this.userId;
    }

    /**
     * Get the user type of this account
     * @return an integer indicating the user type.<br>
     *   0 - Organizer <br>
     *   1 - Attendee <br>
     *   2 - Speaker <br>
     *   3 - VIP <br>
     */
    public abstract int getUserType();

    /**
     * Get all message IDs sent by this account.
     * @return A copy of SentMessage containing message IDs.
     */
    public ArrayList<Integer> getSentBox(){
        return new ArrayList<>(this.sentBox);
    }

    public ArrayList<Integer> getArchiveBox(){return new ArrayList<>(this.archiveBox);}

    public void setArchiveBox(ArrayList<Integer> archiveBox){this.archiveBox = archiveBox;}

    public void addArchiveBox(int msgId) {this.archiveBox.add(msgId);}

    public void removeMsgFromArchiveBox(int msgId) {if(this.archiveBox.contains(msgId)) this.archiveBox.add(msgId);}

    /**
     * Add new message sent by this account.
     * <p>
     * <b>NOTICE: This method is not responsible for checking correctness of the input.</b>
     * @param mesID ID of new message sent by this account.
     */
    public void addSentBox(int mesID){
        if (this.sentBox.contains(mesID)) this.sentBox.add(mesID);
    }

    /**
     * Get all message IDs received by this account.
     * @return A copy of inbox containing message IDs.
     */
    public ArrayList<Integer> getInbox(){
        return new ArrayList<>(this.inbox);
    }

    /**
     * Add new message received by this account.
     * <p>
     * <b>NOTICE: This method is not responsible for checking correctness of the input.</b>
     * @param mesID ID of new message received by this account.
     */
    public void addInbox(int mesID){
        if(this.inbox.contains(mesID)) this.inbox.add(mesID);
    }

    /**
     * Set the message received by this account.
     * @param inbox An Arraylist of IDs of the messages received.
     */
    public void setInbox(ArrayList<Integer> inbox){ this.inbox = inbox;}

    /**
     * Set the message sent by this account.
     * @param sentBox An Arraylist of IDs of the messages sent.
     */
    public void setSentBox(ArrayList<Integer> sentBox) { this.sentBox = sentBox;}

    ///// Louisa Added

    /**
     * Get all unread message IDs received by this account.
     * @return A copy of inbox containing the unread message IDs.
     */
    public ArrayList<Integer> getUnreadInbox(){return new ArrayList<>(this.unreadInbox);}

    /**
     * Set the unread message inbox received by this account.
     * @param unreadInbox The IDs of the unread messages.
     */
    public void setUnreadInbox(ArrayList<Integer> unreadInbox){this.unreadInbox = unreadInbox;}

    /**
     * Add new unread message received by this account to the unread inbox.
     * @param unreadId The ID of the unread message.
     */
    public void addUnreadInbox(int unreadId){
        if(this.unreadInbox.contains(unreadId)) this.unreadInbox.add(unreadId);
    }
    /////

    /**
     * Remove a message from the unread inbox
     * @param messageID The ID of the message to be removed
     */
    public void removeMsgFromUnreadInbox(int messageID){
        if(this.unreadInbox.contains(messageID)) this.unreadInbox.remove(messageID);
    }

    /**
     * Remove a message from inbox
     * @param msgId id of a message
     */
    public void removeMsgFromInbox(int msgId){
        if(this.inbox.contains(msgId)) this.inbox.remove(msgId);
    }


}
