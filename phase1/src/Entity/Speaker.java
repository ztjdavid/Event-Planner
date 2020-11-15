package Entity;

import java.util.ArrayList;

/**
 * The Speaker class stores all info of a speaker.<br>
 * Level in Clean Architecture: Entity.
 */
public class Speaker extends Account{
    private ArrayList<Integer> talksList;


    public Speaker(String username, String password, int userId){
        super(username, password, userId);
        this.talksList = new ArrayList<>();
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
        return new ArrayList<>(this.talksList);
    }

    /**
     * Register an new talk.
     * @param talkID The id of the talk that this account is going to register.
     */
    public void registerTalk(int talkID){
        this.talksList.add(talkID);
    }

    public void removeTalk(int talkID){
        this.talksList.remove(talkID);
    }
}
