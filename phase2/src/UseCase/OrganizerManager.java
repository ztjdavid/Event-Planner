package UseCase;
import Entity.*;
import UseCase.IGateWay.IUserGateWay;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The OrganizerManager class implements all functionalities of an organizer.
 */
public class OrganizerManager extends AccountManager{

    public OrganizerManager(IUserGateWay g){
        super(g);
    }

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0;
    }

    /**
     * Change the given organizer's username.
     * @param organizer The organizer who want to change username.
     * @param username The username that the organizer wants to change into.
     */
    public void changeUsername(Organizer organizer, String username){organizer.setUsername(username);}

    /**
     * Change the given organizer's password
     * @param organizer The organizer who want to change password.
     * @param password The password that the organizer wants to change into.
     */
    public void changePassword(Organizer organizer, String password){organizer.setPassword(password);}

    /**
     * Create a hashmap of all Attendee in the account list.
     * @return Hashmap of all Attendee with key as user ID and value as username.
     */
    public HashMap<Integer, String> getAllAttendee(){
        HashMap<Integer, String> allAttendee = new HashMap<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(Account acc : accList.values()){
            if(acc.getUserType() == 1){
                allAttendee.put(acc.getUserId(), acc.getUsername());
            }
        }return allAttendee;
    }


    /**
     * Create a arraylist of all Attendee's user ID in the account list.
     * @return Arraylist of all Attendee's user ID.
     */
    public ArrayList<Integer> getValidChoices(){
        ArrayList<Integer> aList = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(Account item: accList.values()){
            if (item.getUserType() == 1){
                aList.add(item.getUserId());
            }
        }
        return aList;
    }


    /**
     * Return int 1 iff the receiver is message-able. A receiver is message-able iff its user type is Attendee or Speaker. Otherwise return int 3.
     * @param receiverID The int ID of the receiver.
     * @return int 1 if the receiver is Attendee or Speaker, int 3 otherwise.
     */
    public int messageable1(int receiverID){
        Account receiver = getAccountWithId(receiverID);
        if(receiver.getUserType() == 2 || receiver.getUserType() == 1){
            return 1;
        }
        else{
            return 3;
        }
    }

    /**
     * Get the current Organizer.
     * @return the current account as an Organizer.
     */
    public Organizer getCurrOrganizer(){
        return (Organizer) getCurrAccount();
    }


    /**
     * Create a arraylist of all Speaker's user ID in the account list.
     * @return Arraylist of all Speaker's user ID.
     */
    public ArrayList<Integer> getSpeakerList(){
        ArrayList<Integer> lst = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(int item:accList.keySet()){
            if (accList.get(item).getUserType() == 2){
                lst.add(item);
            }
        }
        return lst;
    }

    /**
     * Create a arraylist of all Attendee's user ID in the account list.
     * @return Arraylist of all Attendee's user ID.
     */
    public ArrayList<Integer> getAttendeeList(){
        ArrayList<Integer> lst = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(int item:accList.keySet()){
            if (accList.get(item).getUserType() == 1){
                lst.add(item);
            }
        }
        return lst;
    }

    /**
     * Get the current Organizer's sent messages.
     * @return an arraylist representation of the current Organizer's sent messages.
     */
    public ArrayList<Integer> getMsgSend(){return getCurrOrganizer().getSentMessage();}

    /**
     * Get all message IDs received by the current Organizer.
     * @return A copy of inbox of the current Organizer containing message IDs.
     */
    public ArrayList<Integer> getInbox(){
        return new ArrayList<>(getCurrOrganizer().getInbox());
    }

    public void setAccInfo(int id, ArrayList<Integer> inbox, ArrayList<Integer> sentBox, ArrayList<Integer> unreadInbox){
        Organizer acc = (Organizer) getAccountWithId(id);
        acc.setInbox(inbox);
        acc.setSentBox(sentBox);
        acc.setUnreadInbox(unreadInbox);
    }

    public ArrayList<Integer> getVIPList(){
        ArrayList<Integer> lst = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(int item:accList.keySet()){
            if (accList.get(item).getUserType() == 3){
                lst.add(item);
            }
        }
        return lst;
    }


}
