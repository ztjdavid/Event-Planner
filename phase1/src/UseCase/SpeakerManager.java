package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The SpeakerManager class implements all functionalities of a speaker.
 */
public class SpeakerManager extends AccountManager {
    protected static Speaker currSpeaker;
    protected ArrayList<Integer> contactList;


    public SpeakerManager() {
        super();
    }

    public void setCurrSpeaker(Speaker speaker){
        currSpeaker = speaker;
    }

    public int getCurrSpeaker(){
        return currSpeaker.getUserId();
    }

    public void changeUsername(String name){
        currSpeaker.setUsername(name);
    }

    public void changePassword(String password){
        currSpeaker.setPassword(password);
    }

    public void registerNewTalk(Talk talk){
        currSpeaker.getTalkList().add(talk.getTalkId());
    }


    /**
     * Check if the current login account can message a given account.
     *
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override

    public boolean messageable(Account other) {
        return other.getUserType() == 1;
    }

    public boolean responsibleForTalk(int talkId) {
        ArrayList<Integer> talkList = currSpeaker.getTalkList();
        return talkList.contains(talkId);
    }

    /**
     * Create a speaker.
     * @param username The username of the Speaker.
     * @param password The password of the Speaker.
     * @param userType If it's a speaker, the number should be 2.
     * @param accountList List of all Accounts.
     * @return true iff the Speaker has been created successfully.
     */
    public boolean createSpeaker(String username, String password, int userType, HashMap<Integer, Account> accountList){
        if(userType != 2){
            return false;
        }else if(accountList.containsKey(accountList.size())){
            System.out.println("The Speaker already exists. ");
            return false;
        }else{
            Speaker newSpeaker = new Speaker(username, password, accountList.size());
            accountList.put(accountList.size(), newSpeaker);
            /**需要给Speaker创建talk list么？*/
            System.out.println("A new Speaker has been created.");
            return true;
            /**怎么把speaker放到speaker的hashmap里？*/
        }
    }

    /**
     * Schedule a speaker.
     * @param speaker The Speaker who is scheduling.
     * @param talk The talk the Speaker is going to be scheduled.
     * @param room The room the Speaker is going to have the talk.
     * @return true iff the Speaker has been scheduled successfully.
     */
    public boolean scheduleSpeaker(Speaker speaker, Talk talk, Room room){
        if(!room.isOccupiedAt(talk.getStartTime()) && !speaker.getTalkList().contains(talk.getTalkId())){
            for(int i = 0; i < speaker.getTalkList().size(); i++){
                if(TalkManager.getTalk(speaker.getTalkList().get(i)).getStartTime() == talk.getStartTime()){
                    return false;
                }/**查看Speaker现有talk的时间和当前talk是否会重合*/
            }
            talk.setSpeaker(speaker.getUserId());
            room.scheduleTalk(talk.getTalkId(), talk.getStartTime());
            System.out.println("The speaker has been successfully scheduled.");
            return true;
        }else{
            System.out.println("The Speaker cannot be scheduled due to conflicts.");
            return false;
        }
    }

    /**
     * Reschedule a speaker.
     * @param speaker The Speaker who is scheduling.
     * @param currentTalk The talk the Speaker is currently scheduled.
     * @param rescheduleTalk The talk the Speaker is going to be rescheduled.
     * @param rescheduleRoom The room the Speaker is going to be rescheduled to have the talk.
     * @return true iff the Speaker has been rescheduled successfully.
     */
    public boolean rescheduleSpeaker(Speaker speaker, Talk currentTalk, Talk rescheduleTalk, Room rescheduleRoom){
        for(int i = 0; i < speaker.getTalkList().size(); i++){
            if(speaker.getTalkList().get(i) == currentTalk.getRoomId()){
                speaker.getTalkList().remove(i);
            }
        }
        return scheduleSpeaker(speaker, rescheduleTalk, rescheduleRoom);
    }




}

