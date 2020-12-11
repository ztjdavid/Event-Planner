package UseCase;

import Entity.*;
import UseCase.IGateWay.IUserGateWay;

import java.io.IOException;
import java.util.ArrayList;

public class VIPManager extends AccountManager{
    public VIPManager(IUserGateWay g){ super(g);}

    /**
     * Get the current VIP.
     * @return the current VIP.
     */
    public VIP getCurrVIP() throws ClassCastException{
        return (VIP)getCurrAccount();
    }

    /**
     * Get the VIP with the Id
     * @param id Id of
     * @return VIP
     */
    public VIP getVIPWithId(int id) throws ClassCastException{
        return (VIP)getAccountWithId(id);
    }

    public void changeUsername(int id, String username){
        getVIPWithId(id).setUsername(username);
    }

    public void changePassword(int id, String password){
        getVIPWithId(id).setPassword(password);
    }
    //////NEED

    /**
     * Enrol the VIP into the event
     * @param userId VIP's id
     * @param eventId event id
     */
    public void enrolEvent(int userId, int eventId){
        getVIPWithId(userId).addEvent(eventId);
        try{
            this.gateWay.updateEventList(getCurrAccountId(), getCurrVIP().getEventList());
        }catch (IOException ignored){}
    }
    //////NEED

    /**
     * Cancel the event that the VIP has enrolled
     * @param userId VIP's id
     * @param eventId event id
     */
    public void dropEvent(int userId, int eventId){
        getVIPWithId(userId).cancelEvent(eventId);
        try{
            this.gateWay.updateEventList(getCurrAccountId(), getCurrVIP().getEventList());
        }catch (IOException ignored){}
    }

    /**
     * Get the event list for the VIP
     * @param id VIP' id
     * @return Arraylist of events ids
     */
    public ArrayList<Integer> getEventList(int id){
        VIP Vip = (VIP) accountList.get(id);
        return new ArrayList<>(Vip.getEventList());
    }

    /**
     * Get the event list for the current VIP
     * @return Arraylist of events ids for the current VIP
     */
    public ArrayList<Integer> getAllEvent(){ return getCurrVIP().getEventList();}

    /**
     * Get the messages in Inbox of the VIP with id
     * @param id VIP's id
     * @return Arraylist of message ids in the Inbox
     */
    public ArrayList<Integer> getInbox(int id){
        return getVIPWithId(id).getInbox();
    }

    /**
     * Get the messages of SentBox of the VIP with id
     * @param id VIP's id
     * @return Arraylist of message ids in the SentBox
     */
    public ArrayList<Integer> getSentBox(int id){
        return getVIPWithId(id).getSentBox();
    }

    /**
     * Get the messages of SentBox of the VIP
     * @return Arraylist of message ids in the UnreadInBox
     */
    public ArrayList<Integer> getUnreadInbox(){return getCurrAccount().getUnreadInbox();}

    ///////NEED

    /**
     * Delete the message in UnreadInbox with its id
     * @param msgid message id
     */
    public void deleteUnreadInbox(int msgid){
        ArrayList<Integer> inbox = getCurrAccount().getUnreadInbox();
        inbox.remove(Integer.valueOf(msgid));
        try{
            this.gateWay.updateInbox(getCurrAccountId(), getCurrVIP().getInbox());
        }catch (IOException ignored){}

    }

    /**
     * Check if the target account is messageable
     * @param other Another account that the current login account is going to message.
     * @return true iff the account is messageable
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0 && other.getUserId() != getCurrVIP().getUserId();
    }

    /**
     * Set information of the account
     * @param id Id of the VIP
     * @param inbox inbox
     * @param sentBox sent box
     * @param eventList event list
     * @param unreadInbox unread inbox
     */
    public void setAccInfo(int id, ArrayList<Integer> inbox, ArrayList<Integer> sentBox,
                           ArrayList<Integer> eventList, ArrayList<Integer> unreadInbox,ArrayList<Integer> archiveInbox){
        VIP acc = (VIP) getAccountWithId(id);
        acc.setInbox(inbox);
        acc.setSentBox(sentBox);
        acc.setEventList(eventList);
        acc.setUnreadInbox(unreadInbox);
        acc.setArchiveBox(archiveInbox);
    }


}
