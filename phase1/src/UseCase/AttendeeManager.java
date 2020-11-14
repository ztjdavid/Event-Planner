package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    //TODO:为了跑程序暂时乱写的，需要改.
    public ArrayList<Integer> getAllMyTalks(){ return new ArrayList<>(Arrays.asList(0, 1));}

    //TODO:为了跑程序暂时乱写的，需要改.
    public void enrol(int a){}

    //TODO:为了跑程序暂时乱写的，需要改.
    public void drop(int a){}

    //TODO:为了跑程序暂时乱写的，需要改.
    public ArrayList<Integer> getAllAvailableTalks(){return new ArrayList<>(Arrays.asList(0, 1));}



}
