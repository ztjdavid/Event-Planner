package UseCase;
import Entity.*;
import UseCase.IGateWay.IUserGateWay;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The AttendeeManager class implements all functionalities of an attendee.
 */
public class AttendeeManager extends AccountManager{

    public AttendeeManager(IUserGateWay g) {super(g); }

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
        getCurrAttendee().addEvent(talkID);
        //TODO: 只要涉及到更改Object的属性，都需要更新Database。
        // 更新方式为提供Object的id和【整个】属性对应的东西，比如整个更新完的list等。
        try{
            this.gateWay.updateEventList(getCurrAccountId(), getCurrAttendee().getEventList());
        }catch (IOException ignored){}
    }

    /**
     * Cancel the Attendee an existing talk given the talk ID.
     * @param talkID int value of the Event ID.
     */
    public void drop(int talkID) {
        getCurrAttendee().cancelEvent(talkID);
        //TODO:
        try{
            this.gateWay.updateEventList(getCurrAccountId(), getCurrAttendee().getEventList());
        }catch (IOException ignored){}
    }

    /**
     * Get the current Attendee's talk list.
     * @return An arraylist of current Attendee's talk list.
     */
    public ArrayList<Integer> getAllMyTalksId() {
        return getCurrAttendee().getEventList();
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

    public void setAccInfo(int id, ArrayList<Integer> inbox, ArrayList<Integer> sentBox,
                           ArrayList<Integer> eventList, ArrayList<Integer> unreadInbox){
        Attendee acc = (Attendee) getAccountWithId(id);
        acc.setInbox(inbox);
        acc.setSentBox(sentBox);
        acc.setEventList(eventList);
        acc.setUnreadInbox(unreadInbox);
    }
    ///Grey modify
    public ArrayList<Integer> getUnreadInbox(){return getCurrAccount().getUnreadInbox();}

    public void deleteUnreadInbox(int msgid){
         ArrayList<Integer> inbox = getCurrAccount().getUnreadInbox();
         inbox.remove(Integer.valueOf(msgid));

         //TODO:
         try{
             this.gateWay.updateInbox(getCurrAccountId(), getCurrAttendee().getInbox());
         }catch (IOException ignored){}

    }


}

