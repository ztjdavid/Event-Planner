package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TalkManager class contains talks and methods that modify talks
 * Level in Clean Architecture: Use case class.
 */

public class TalkManager {
    protected static int totalTalkCount = 0;
    protected HashMap<Integer, Talk> talkList;
    protected static int currentTalkID = 0; // Track which Talk this program is working on now.

    public TalkManager() {
        this.talkList = new HashMap<>();
    }

    /**
     *Set the currentTalkID.
     */
    public void setCurrentTalkID(int talkID){
        currentTalkID = talkID;
    }

    /**
     * Getter for the ID of the talk currently working with.
     * @return int currentTalkID.
     */

    public Talk getCurrentTalkID(){
        return this.talkList.get(currentTalkID);
    }

    /**
     * Add the attendee to the Talk.
     * @param attendee The new attendee.
     */

    public void addAttendee(Attendee attendee){
        this.talkList.get(currentTalkID).getAttendeeId().add(attendee.getUserId());
    }

    /**
     * Remove the attendee from the Talk.
     * @param attendee The unwanted attendee.
     */

    public void removeAttendee(Attendee attendee){
        this.talkList.get(currentTalkID).getAttendeeId().remove(attendee.getUserId());
    }

    /**
     * Change the time of the Talk.
     * @param time The new time (in 24-hour format) to be updated.
     * @return true iff the new time is valid (ie. between 9 and 17)
     */

    public boolean changeTalkTime(int time) {
        if (time > 9 && time <= 17) {
            this.talkList.get(currentTalkID).setStartTime(time);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Change the speaker of the Talk.
     * @param speakerID The new ID of the new speaker to be updated.
     */

    public void changeTalkSpeaker(int speakerID){
        this.talkList.get(currentTalkID).setSpeaker(speakerID);
    }

    /**
     * Gets the number of remaining seats.
     * @return the number of remaining seats.
     */

    public int getRemainingSeats(){
        return this.talkList.get(currentTalkID).getRemainingSeat();
    }

    /**
     * Check whether a talk has a time conflict with the current talk.
     * @param talk The talk to be checked.
     * @return true iff the talk's time is in conflict with the current talk.
     */

    public boolean checkTimeConflict(Talk talk){
        return this.talkList.get(currentTalkID).getStartTime() == talk.getStartTime();
    }

    /**
     * Return the Talk given the talkID
     * @param talkID The ID of the talk.
     * @return the Talk with the given talkID.
     */

    public Talk getTalk(int talkID){
        return this.talkList.get(talkID);
    }

    /**
     * Return an ArrayList of all the Talks.
     * @return An ArrayList containing all the Talks.
     */

    public ArrayList<Talk> getAllTalks(){
        return new ArrayList<>(talkList.values());
    }

    /**
     * Creates a talk and updates the talkList.
     * @param talkId the ID of the talk.
     * @param talkTitle the title of the talk.
     * @param startTime the time (between 9 and 17 in 24-hour format) of the talk.
     * @param roomId the roomId of the talk.
     * @param speakerID the ID of the speaker of the talk.
     * @return true iff the talk is successfully created.
     */

    public boolean createTalk(int talkId, String talkTitle, int startTime, int roomId, int speakerID){
        if(this.talkList.containsKey(talkId)){
            return false;
        }else if(startTime > 9 && startTime <= 17){
            return false;
        } else{
            Talk newTalk = new Talk(talkId, talkTitle,startTime, roomId, speakerID);
            this.talkList.put(talkId, newTalk);
            totalTalkCount += 1;
            return true;
        }
    }

    /**
     * Remove a specific Talk from the Talk list.
     * @param talk The Talk that want to be removed.
     * @return true iff the Talk has been removed.
     */
    public boolean removeTalk(Talk talk){
        if (this.talkList.containsKey(talk.getTalkId())){
            this.talkList.remove(talk.getTalkId(), talk);
            totalTalkCount -= 1;
            System.out.println("The Talk has been successfully removed.");
            return true;
        }else{
            System.out.println("No such Talk has been found.");
            return false;
        }
    }

    /**
     * Reschedule a specific Talk with checking conflicts.
     * @param talk The Talk that want to be rescheduled.
     * @param startTime Start time of the Talk.
     * @param room  Room of the Talk.
     * @param speaker Speaker of the Talk.
     * @return true iff the Talk has been rescheduled.
     */
    public boolean rescheduleTalk(Talk talk, int startTime, Room room, Speaker speaker){
        if(!room.isOccupiedAt(startTime) /**并且speaker在这个时间是空闲的*/){
            talk.setRoomId(room.getRoomId());
            talk.setStartTime(startTime);
            talk.setSpeaker(speaker.getUserId());
            talk.setTalkTitle(talk.getTalkTitle());
            System.out.println("The Talk has been successfully rescheduled.");
            return true;
        }else{
            System.out.println("The Talk cannot be rescheduled due to certain conflicts.");
            return false;
        }
    }


}



