package Entity;
import java.util.ArrayList;

/**
 * !!!!!!!!!!!!NOT COMPLETED!!!!!!!!!!
 * The Attendee class stores all info of an attendee account.
 * Level in Clean Architecture：Entity
 */
public class Attendee {
    private String username;
    private String password;
    private final int userId;
    private final int userType;
    private ArrayList<Integer> sentMessage;
    private ArrayList<Integer> inbox;
    private ArrayList<Integer> talksList;


    public Attendee(String username, String password, int userId){
        this.userType = 1;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.sentMessage = new ArrayList<Integer>();
        this.inbox = new ArrayList<Integer>();
        this.talksList = new ArrayList<Integer>();
    }

    /**
     * Set the username for this account.
     * @param username The new username.
     */
    private void setUsername(String username){
        this.username = username;
    }

    /**
     * Get the username of this account.
     * @return The username of this account.
     */
    private String getUsername(){
        return this.username;
    }

    /**
     * Set the password for this account.
     * @param password Set password for this account.
     */
    private void setPassword(String password){
        this.password = password;
    }

    /**
     * Get the password od this account.
     * @return The password of this account.
     */
    private String getPassword(){
        return this.password;
    }

    /**
     * Get the UID of this account.
     * @return the userid of this account.
     */
    private int getUserId(){
        return this.userId;
    }

    /**
     * Get the user type of this account
     * @return 1 indicating this is an Attendee account.
     */
    private int getUserType(){
        return this.userType;
    }

    /**
     * Get all message IDs sent by this account.
     * @return A list of message IDs.
     */
    private ArrayList<Integer> getSentMessage(){
        return this.sentMessage;
    }

    /**
     * Add new message sent by this account.
     * NOTICE: This method is not responsible for checking correctness of the input.
     * @param mesID ID of new message sent by this account.
     */
    private void addSentMessage(int mesID){
        this.sentMessage.add(mesID);
    }


    /**
     * Get all message IDs received by this account.
     * @return A list of message IDs.
     */
    private ArrayList<Integer> getInbox(){
        return this.inbox;
    }

    /**
     * Add new message received by this account.
     * NOTICE: This method is not responsible for checking correctness of the input.
     * @param mesID ID of new message received by this account.
     */
    private void addInbox(int mesID){
        this.inbox.add(mesID);
    }

    /**
     * Get all talks that this account attends.
     * @return A list of all talks this account attends.
     */
    private ArrayList<Integer> getTalkList(){
        return this.talksList;
    }

    /**
     * Attend a new talk.
     * NOTICE: This method is not responsible for checking correctness of the input.
     * @param talkId The id of new talk that this account wants to attend.
     */
    private void addTalk(int talkId){
        this.talksList.add(talkId);
    }

    /**
     *
     * @param talkId The id of talk that is going to be removed.
     * @return Return true if the talk is successfully removed from talkList, otherwise return False.
     */
    private boolean cancelTalk(int talkId){
        int deleteID = -1;
        for (int i = 0; i < this.talksList.size(); i++){
            if (this.talksList.get(i) == talkId) deleteID = i;
        }
        if (deleteID == -1) return false;
        else {
            this.talksList.remove(deleteID);
            return true;
        }
    }




}
