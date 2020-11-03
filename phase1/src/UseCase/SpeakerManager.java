package UseCase;
import Entity.*;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager{
    public SpeakerManager(){
        super();
    }

    /**
     * Create a Speaker account.
     * @param username The username of the new account.
     * @param password The password of the new account.
     */
    public void createAccount(String username, String password) {
        Speaker newAcc = new Speaker(username, password, 2);
        accountList.put(TotalNumOfAccount, newAcc);
        TotalNumOfAccount += 1;
    }

    /**
     * Check if the current login account can message a given account.
     * @param otherId ID of another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(int otherId){
        Account other = getAccountWithId(otherId);
        return other.getUserType() == 1;
    }


    //TODO
    public boolean responsibleForTalk(int talkId){
        Account currAcc = getCurrAccount();
        //t = getTalkWithId(talkId);
        //return t.getSpeaker().contain(currAcc);
        return false;}
}
