package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;

/**
 * The OrganizerManager class implements all functionalities of an organizer.
 */
public class OrganizerManager extends AccountManager{

    protected Organizer currOrganizer;

    public OrganizerManager(){
        super();
    }

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0;
    }

    /**
     * Change the given organizer's username.
     * @param organizer The organizer who want to change username.
     * @param username The username that the organizer wants to change into.
     */
    public void changeUsername(Organizer organizer, String username){organizer.setUsername(username);}

    /**
     * Change the given organizer's password
     * @param organizer The organizer who want to change password.
     * @param password The password that the organizer wants to change into.
     */
    public void changePassword(Organizer organizer, String password){organizer.setPassword(password);}

    public HashMap<Integer, String> getAllAttendee(){
        HashMap<Integer, String> allAttendee = new HashMap<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(Account acc : accList.values()){
            if(acc.getUserType() == 1){
                allAttendee.put(acc.getUserId(), acc.getUsername());
            }
        }return allAttendee;
    }

    public HashMap<Integer, String> getAllSpeaker(){
        HashMap<Integer, String> allSpeaker = new HashMap<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(Account acc : accList.values()){
            if(acc.getUserType() == 2){
                allSpeaker.put(acc.getUserId(), acc.getUsername());
            }
        }return allSpeaker;
    }

    public ArrayList<Integer> getValidChoices(){
        ArrayList<Integer> aList = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(Account item: accList.values()){
            if (item.getUserType() == 1){
                aList.add(item.getUserId());
            }
        }
        return aList;
    }

    public int messageable1(int receiverID){
        Account receiver = getAccountWithId(receiverID);
        if(receiver.getUserType() == 2 || receiver.getUserType() == 1){
            return 1;
        }
        else{
            return 3;
        }
    }

    public void setCurrOrganizer(Organizer organizer){
        currOrganizer = organizer;
    }

    public int getCurrOrganizer(){
        return currOrganizer.getUserId();
    }

    public ArrayList<Integer> getSpeakerList(){
        ArrayList<Integer> lst = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(int item:accList.keySet()){
            if (accList.get(item).getUserType() == 2){
                lst.add(item);
            }
        }
        return lst;
    }

    public ArrayList<Integer> getAttendeeList(){
        ArrayList<Integer> lst = new ArrayList<>();
        HashMap<Integer, Account> accList = new HashMap<>(getAccountList());
        for(int item:accList.keySet()){
            if (accList.get(item).getUserType() == 1){
                lst.add(item);
            }
        }
        return lst;
    }


}
