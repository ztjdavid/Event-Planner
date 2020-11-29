package UseCase;
import Entity.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * The AttendeeManager class implements all functionalities of an attendee.
 */
public class AttendeeManager extends AccountManager{

    public AttendeeManager() {super(); }

    /**
     * Get the current Attendee.
     * @return the current Attendee.
     */
    public Attendee getCurrAttendee(){
        return (Attendee)getCurrAccount();
    }

    /**
     * Change the current Attendee's user name into the given user name.
     * @param name String representation of the new user name.
     */
    public void changeUsername(String name){
        getCurrAttendee().setUsername(name);
    }

    /**
     * Change the current Attendee's password into the given password.
     * @param password String representation of the new password.
     */
    public void changePassword(String password){
        getCurrAttendee().setPassword(password);
    }

    /**
     * Sign up the Attendee a new talk given the talk ID.
     * @param talkID int value of the Event ID.
     */
    public void enrol(int talkID){
        getCurrAttendee().addTalk(talkID);
    }

    /**
     * Cancel the Attendee an existing talk given the talk ID.
     * @param talkID int value of the Event ID.
     */
    public void drop(int talkID) {
        getCurrAttendee().cancelTalk(talkID);
    }

    /**
     * Get the current Attendee's talk list.
     * @return An arraylist of current Attendee's talk list.
     */
    public ArrayList<Integer> getAllMyTalksId() {
        return getCurrAttendee().getTalkList();
    }


    /**
     * Get the current Attendee's inbox.
     * @return An arraylist of current Attendee's inbox.
     */
    public ArrayList<Integer> getInbox() {
        return getCurrAttendee().getInbox();
    }


    /**
     * Check if the current login account can message a given account.
     * @param other another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0 && other.getUserId() != getCurrAttendee().getUserId();
    }
    //////////////ERICMODIFY

    /**
     * Get the inbox of the current account
     * @return a Arraylist of integer, which contain the ids of the messages that got by current account.
     */
    public ArrayList<Integer> getinbox(){return getCurrAccount().getInbox();}
    /**
     * Get the ids of messages send from the current account
     * @return a Arraylist of integer, which contain the ids of the messages that current account send.
     */
    public ArrayList<Integer> getmsgsend(){return getCurrAccount().getSentMessage();}

    public void setAccInfo(int id, ArrayList<Integer> inbox, ArrayList<Integer> sentBox, ArrayList<Integer> eventList){
        Attendee acc = (Attendee) getAccountWithId(id);
        acc.setInbox(inbox);
        acc.setSentBox(sentBox);
        acc.setEventList(eventList);
    }
}

