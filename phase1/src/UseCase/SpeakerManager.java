package UseCase;
import Entity.*;

import java.util.ArrayList;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager {
    protected static Speaker currSpeaker;
    protected ArrayList<Integer> contactList;


    public SpeakerManager() {
        super();
    }

    public void setCurrSpeaker(Speaker speaker){
        currSpeaker = speaker;
    }

    public int getCurrSpeaker(){
        return currSpeaker.getUserId();
    }

    public void changeUsername(String name){
        currSpeaker.setUsername(name);
    }

    public void changePassword(String password){
        currSpeaker.setPassword(password);
    }

    public void registerNewTalk(Talk talk){
        currSpeaker.registerTalk(talk.getTalkId());
    }

    public void removeTalk(Talk talk){
        currSpeaker.removeTalk(talk.getTalkId());
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

    public boolean responsibleForTalk(int talkId) {
        ArrayList<Integer> talkList = currSpeaker.getTalkList();
        return talkList.contains(talkId);
    }

    /**
     * Create a speaker.
     * @param username The username of the Speaker.
     * @param password The password of the Speaker.
     * @param userID the ID of the user
     */

    public int createSpeaker(String username, String password, int userID){
            Speaker newSpeaker = new Speaker(username, password, userID);
            return newSpeaker.getUserId();
    }
    public Array

}

