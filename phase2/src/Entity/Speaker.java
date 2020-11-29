package Entity;

import java.util.ArrayList;

/**
 * The Speaker class stores all info of a speaker.<br>
 * Level in Clean Architecture: Entity.
 */
public class Speaker extends Account{
    private ArrayList<Integer> eventList;


    public Speaker(String username, String password, int userId){
        super(username, password, userId);
        this.eventList = new ArrayList<>();
    }

    /**
     * Get the user type of this account.
     * @return 2 indicating this account is a speaker.
     */
    @Override
    public int getUserType() {
        return 2;
    }

    /**
     * Get all talks that this speaker holds.
     * @return A copy of talkList containing all talks this speaker holds.
     */
    public ArrayList<Integer> getTalkList() {
        return new ArrayList<>(this.eventList);
    }

    /**
     * Register a new talk.
     * @param talkID The id of the talk that this account is going to register.
     */
    public void registerTalk(int talkID){
        this.eventList.add(talkID);
    }

    /**
     * Remove an attended talk.
     * @param talkID The id of the talk that is going to be removed.
     */
    public void removeTalk(int talkID){
        if (!this.eventList.isEmpty()) this.eventList.remove(talkID);
    }

    public void setInbox(ArrayList<Integer> inbox){ this.inbox = inbox;}

    public void setSentBox(ArrayList<Integer> sentBox) { this.sentMessage = sentBox;}

    public void setEventList(ArrayList<Integer> eventList) {this.eventList = eventList;}
}
