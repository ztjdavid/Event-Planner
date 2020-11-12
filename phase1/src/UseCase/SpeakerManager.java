package UseCase;
import Entity.*;

import java.util.ArrayList;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager {
    public SpeakerManager() {
        super();
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


    //TODO
    public boolean responsibleForTalk(int talkId) {
        //t = getTalkWithId(talkId);
        //return t.getSpeaker().contain(currAcc);
        return false;
    }
}

