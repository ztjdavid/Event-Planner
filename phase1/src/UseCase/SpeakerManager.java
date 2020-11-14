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

    public Speaker getCurrSpeaker(){
        return (Speaker)getCurrAccount();
    }

    public void changeUsername(String name){
        getCurrSpeaker().setUsername(name);
    }

    public void changePassword(String password){
        getCurrSpeaker().setPassword(password);
    }

    public void registerNewTalk(int talkID, int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        speaker.registerTalk(talkID);
    }

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

    public boolean responsibleForTalk(int talkId) {
        ArrayList<Integer> talkList = getCurrSpeaker().getTalkList();
        return talkList.contains(talkId);
    }

    public ArrayList<Integer> getalltalk(){return getCurrSpeaker().getTalkList();}
    public ArrayList<Integer> getinbox(){return getCurrSpeaker().getInbox();}

    public boolean checkTalk(int speakerID, int talkID){
        Speaker speaker = (Speaker) getAccountWithId(speakerID);
        return speaker.getTalkList().contains(talkID);
    }

    public ArrayList<Integer> getTalkList(int speakerID){
        Speaker speaker = (Speaker) accountList.get(speakerID);
        return new ArrayList<>(speaker.getTalkList());
    }



}

