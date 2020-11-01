package Entity;

import java.util.ArrayList;

/**
 * The Speaker class stores all info of a speaker.<br>
 * Level in Clean Architecture: Entity.
 */
public class Speaker extends Account{
    ArrayList<Integer> talkList;


    public Speaker(String username, String password, int userId){
        super(username, password, userId);
        this.talkList = new ArrayList<>();
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
        return new ArrayList<>(this.talkList);
    }
}
