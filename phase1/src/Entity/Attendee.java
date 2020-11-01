package Entity;
import java.util.ArrayList;

/**
 * The Attendee class stores all info of an attendee account.
 * <p>
 * Level in Clean Architecture: Entity
 */
public class Attendee extends Account{
    protected final ArrayList<Integer> talksList;

    public Attendee(String username, String password, int userId){
        super(username, password, userId);
        this.talksList = new ArrayList<>();
    }


    /**
     * Get the user type of this account
     * @return 1 indicating this is an Attendee account.
     */
    @Override
    public int getUserType(){ return 1;}


    /**
     * Get all talks that this account attends.
     * @return A copy of tlkList containing all talks this account attends.
     */
    public ArrayList<Integer> getTalkList(){
        return new ArrayList<>(this.talksList);
    }

    /**
     * Attend a new talk.
     * <p>
     * <b>NOTICE: This method is not responsible for checking correctness of the input.</b>
     * @param talkId The id of new talk that this account wants to attend.
     */
    public void addTalk(int talkId){
        this.talksList.add(talkId);
    }

    /**
     * Cancel appointment of a talk in talkList.
     * @param talkId The id of talk that is going to be removed.
     * @return Return true if the talk is successfully removed from talkList, otherwise return False.
     */
    public boolean cancelTalk(int talkId){
        int deleteID = -1;
        for (int i = 0; i < this.talksList.size(); i++){
            if (this.talksList.get(i) == talkId) deleteID = i;
        }
        if (deleteID == -1) return false;
        else {
            this.talksList.remove(deleteID);
            return true;
        }
    }

}
