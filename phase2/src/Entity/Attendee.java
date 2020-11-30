package Entity;
import java.util.ArrayList;

/**
 * The Attendee class stores all info of an attendee account.
 * <p>
 * Level in Clean Architecture: Entity
 */
public class Attendee extends Account{
    protected ArrayList<Integer> eventList;

    public Attendee(String username, String password, int userId){
        super(username, password, userId);
        this.eventList = new ArrayList<>();
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
    public ArrayList<Integer> getEventList(){
        return new ArrayList<>(this.eventList);
    }

    /**
     * Attend a new talk.
     * <p>
     * <b>NOTICE: This method is not responsible for checking correctness of the input.</b>
     * @param talkId The id of new talk that this account wants to attend.
     */
    public void addEvent(int talkId){
        this.eventList.add(talkId);
    }

    /**
     * Cancel appointment of a talk in talkList.
     * @param talkId The id of talk that is going to be removed.
     * @return Return true if the talk is successfully removed from talkList, otherwise return False.
     */
    public boolean cancelEvent(int talkId){
        int deleteID = -1;
        for (int i = 0; i < this.eventList.size(); i++){
            if (this.eventList.get(i) == talkId) deleteID = i;
        }
        if (deleteID == -1) return false;
        else {
            this.eventList.remove(deleteID);
            return true;
        }
    }

    public void setEventList(ArrayList<Integer> eventList) {this.eventList = eventList;}

}
