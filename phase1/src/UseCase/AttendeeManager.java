package UseCase;
import Entity.*;

import java.util.HashMap;

/**
 * The AttendeeManager class implements all functionalities of an attendee.
 */
public class AttendeeManager extends AccountManager{

    public AttendeeManager() {super(); }

    /**
     * Check if the current login account can message a given account.
     * @param other another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0;
    }

    public HashMap<Integer, String> getAllAttendee(){
        HashMap<Integer, String> allAttendee = new HashMap<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(Account acc : accList.values()){
            if(acc.getUserType() == 1){
                allAttendee.put(acc.getUserId(), acc.getUsername());
            }
        }return allAttendee;
    }



}
