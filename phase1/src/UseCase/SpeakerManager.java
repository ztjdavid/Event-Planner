package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager {
    public SpeakerManager() {
        super();
    }

    /**
     * Get the current Speaker.
     * @return the current Speaker.
     */
    public Speaker getCurrSpeaker(){
        return (Speaker)getCurrAccount();
    }

    /**
     * Change the current Speaker's user name into the given user name.
     * @param name String representation of the new user name.
     */
    public void changeUsername(String name){
        getCurrSpeaker().setUsername(name);
    }

    /**
     * Change the current Speaker's password into the given password.
     * @param password String representation of the new password.
     */
    public void changePassword(String password){
        getCurrSpeaker().setPassword(password);
    }

    /**
     * Register the Speaker given the speaker ID to the new Talk given the talk ID.
     * @param talkID int value of the Talk ID.
     * @param speakerID int value of the Speaker ID.
     */
    public void registerNewTalk(int talkID, int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        speaker.registerTalk(talkID);
    }

    /**
     * Remove the given talk.
     * @param talk The Talk need to be removed.
     */
    public void removeTalk(Talk talk){
        getCurrSpeaker().removeTalk(talk.getTalkId());
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
     * Check if the current Speaker is responsible for the Talk given the talk ID.
     * @param talkId The Talk need to be removed.
     * @return True iff the current Speaker is responsible for the Talk.
     */
    public boolean responsibleForTalk(int talkId) {
        ArrayList<Integer> talkList = getCurrSpeaker().getTalkList();
        return talkList.contains(talkId);
    }

    /**
     * Get the current Speaker's talk list.
     * @return An arraylist of current Speaker's talk list.
     */
    public ArrayList<Integer> getalltalk(){return getCurrSpeaker().getTalkList();}

    /**
     * Get the current Speaker's inbox.
     * @return An arraylist of current Speaker's inbox.
     */
    public ArrayList<Integer> getinbox(){return getCurrSpeaker().getInbox();}

    /**
     * Check if the Speaker given the speaker ID is responsible for the Talk given the talk ID.
     * @param speakerID The ID of the Speaker.
     * @param talkID The ID of the Talk.
     * @return True iff the Speaker responsible for the Talk.
     */
    public boolean checkTalk(int speakerID, int talkID){
        Speaker speaker = (Speaker) getAccountWithId(speakerID);
        return speaker.getTalkList().contains(talkID);
    }

    /**
     * Get the Speaker's talk list given the speaker ID.
     * @param speakerID The ID of the Speaker
     * @return The arraylist of the Speaker's talk list.
     */
    public ArrayList<Integer> getTalkList(int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        return new ArrayList<>(speaker.getTalkList());
    }



}

