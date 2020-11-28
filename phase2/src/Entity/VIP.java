package Entity;

import java.util.ArrayList;

public class VIP extends Account{
    private ArrayList<Integer> eventList = new ArrayList<>();
    public VIP(String username, String password, int userId){
        super(username, password, userId);
    }

    @Override
    public int getUserType(){ return 3; }

    /**
     * Get all events that this account attends.
     * @return A copy of tlkList containing all talks this account attends.
     */

    public ArrayList<Integer> getTalkList(){
        return new ArrayList<>(this.eventList);
    }

    /**
     * Attend a new talk.
     * <p>
     * <b>NOTICE: This method is not responsible for checking correctness of the input.</b>
     * @param talkId The id of new talk that this account wants to attend.
     */

    public void addTalk(int talkId){
        this.eventList.add(talkId);
    }

    /**
     * Cancel appointment of a talk in talkList.
     * @param talkId The id of talk that is going to be removed.
     * @return Return true if the talk is successfully removed from talkList, otherwise return False.
     */
    public boolean cancelTalk(int talkId){
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

}
