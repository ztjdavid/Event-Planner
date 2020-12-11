package UseCase;
import Entity.*;
import UseCase.IGateWay.IUserGateWay;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager {
    public SpeakerManager(IUserGateWay g) {
        super(g);
    }

    /**
     * Get the current Speaker.
     * @return the current Speaker.
     */
    public Speaker getCurrSpeaker(){
        return (Speaker)getCurrAccount();
    }


    /**
     * Register the Speaker given the speaker ID to the new Event given the talk ID.
     * @param talkID int value of the Event ID.
     * @param speakerID int value of the Speaker ID.
     */
    public void registerNewTalk(int talkID, int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        speaker.registerTalk(talkID);
        try{
            gateWay.updateEventList(getCurrAccountId(),speaker.getTalkList());
        }catch (IOException ignored){}
    }


    /**
     * Check if the current login account can message a given account.
     *
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override

    public boolean messageable(Account other) {
        return other.getUserType() == 1;
    }

    /**
     * Get the current Speaker's talk list.
     * @return An arraylist of current Speaker's talk list with talk ID represented by int.
     */
    public ArrayList<Integer> getalltalk(){return getCurrSpeaker().getTalkList();}

    /**
     * Get the Speaker's talk list with the given speaker ID.
     * @param speakerID The ID of the speaker as int.
     * @return An arraylist of the given Speaker's talk list with talk ID represented by int.
     */
    public ArrayList<Integer> getTalks(int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        return speaker.getTalkList();
    }

    /**
     * Get the current Speaker's inbox.
     * @return An arraylist of current Speaker's inbox.
     */
    public ArrayList<Integer> getinbox(){return getCurrSpeaker().getInbox();}

    /**
     * Get the messages that the current speaker got.
     * @return A list of message ids.
     */
    public ArrayList<Integer> getmsgsend(){return getCurrSpeaker().getSentBox();}



    /**
     * Check if the Speaker given the speaker ID is responsible for the Event given the talk ID.
     * @param speakerID The ID of the Speaker.
     * @param talkID The ID of the Event.
     * @return True iff the Speaker's talk list contains the Event.
     */
    public boolean checkTalk(int speakerID, int talkID){
        Speaker speaker = (Speaker) getAccountWithId(speakerID);
        return speaker.getTalkList().contains(talkID);
    }

    /**
     * Get the Speaker's talk list given the speaker ID.
     * @param speakerID The ID of the Speaker.
     * @return The copy of the arraylist of the Speaker's talk list represented by talk ID.
     */
    public ArrayList<Integer> getTalkList(int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        return new ArrayList<>(speaker.getTalkList());
    }

    /**
     * Set information of the account
     * @param id Id of the speaker
     * @param inbox inbox
     * @param sentBox sent box
     * @param eventList event list
     * @param unreadInbox unread inbox
     */
    public void setAccInfo(int id, ArrayList<Integer> inbox, ArrayList<Integer> sentBox,
                           ArrayList<Integer> eventList, ArrayList<Integer> unreadInbox,
                           ArrayList<Integer> archiveInbox){
        Speaker acc = (Speaker) getAccountWithId(id);
        acc.setInbox(inbox);
        acc.setSentBox(sentBox);
        acc.setEventList(eventList);
        acc.setUnreadInbox(unreadInbox);
        acc.setArchiveBox(archiveInbox);
    }

    /**
     * Get the IDs of the unread messages for the current speaker
     * @return ArrayList of IDs of unread messages
     */
    public ArrayList<Integer> getUnread(){return getCurrSpeaker().getUnreadInbox();}
}

