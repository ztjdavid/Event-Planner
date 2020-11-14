package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager {
    protected static Speaker currSpeaker;

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

    public void registerNewTalk(int talkID, int speakerID){
        Speaker speaker = (Speaker) getAccountWithId(speakerID);
        setCurrSpeaker(speaker);
        speaker.registerTalk(talkID);
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

    public ArrayList<Integer> getalltalk(){return currSpeaker.getTalkList();}
    public ArrayList<Integer> getinbox(){return currSpeaker.getInbox();}

    public boolean checkTalk(int speakerID, int talkID){
        Speaker speaker = (Speaker) getAccountWithId(speakerID);
        return speaker.getTalkList().contains(talkID);
    }

    public ArrayList<Integer> getTalkList(int speakerID){
        Speaker speaker = (Speaker) getAccountWithId(speakerID);
        return new ArrayList<>(speaker.getTalkList());
    }



}

