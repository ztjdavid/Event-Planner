package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
     * Set the currentTalkID.
     * @param talkID The current talk Id.
     */

    public void setCurrentTalkID(int talkID){
        currentTalkID = talkID;
    }

    /**
     * Getter for the ID of the talk currently working with.
     * @return int currentTalkID.
     */

    public Talk getCurrentTalkWithID(){
        return this.talkList.get(currentTalkID);
    }

    /**
     * Get the speaker of this talk.
     * @param talkId The id of a talk.
     * @return An integer representing the id of the speaker in given talk.
     */
    public int getSpeakerIDIn(int talkId){
        return this.talkList.get(talkId).getSpeaker();
    }

    /**
     * Add the attendee to the current Talk.
     * @param attendee The new attendee.
     */

    public void addAttendee(Attendee attendee){
        this.talkList.get(currentTalkID).addAttendee(attendee.getUserId());
    }

    /**
     * Add the given Attendee's ID to the Talk's attendee list given the talk ID.
     * @param talkid ID of the talk.
     * @param attendee Attendee who want to attend the talk.
     */

    public void addAttendeev2(int talkid, Attendee attendee){
        this.talkList.get(talkid).addAttendee(attendee.getUserId());
    }

    /**
     * Remove the attendee from the Talk.
     * @param attendee The unwanted attendee.
     */

    public void removeAttendee(Attendee attendee){
        this.talkList.get(currentTalkID).removeAttendee(attendee.getUserId());
    }

    /**
     * Remove the given Attendee's ID from the Talk's attendee list given the talk ID.
     * @param talkid ID of the talk.
     * @param attendee Attendee who need to be removed from the talk.
     */

    public void removeAttendeev2(int talkid, Attendee attendee){
        this.talkList.get(talkid).removeAttendee(attendee.getUserId());
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

    public Talk getTalkWithId(int talkID){
        return this.talkList.get(talkID);
    }

    /**
     * Return an ArrayList of all the Talks.
     * @return An ArrayList containing all the Talks.
     */

    public ArrayList<Integer> getAllTalksID(){
        return new ArrayList<>(this.talkList.keySet());
    }

    /**
     * Creates a talk and updates the talkList.
     * @param talkTitle the title of the talk.
     * @param startTime the time (between 9 and 17 in 24-hour format) of the talk.
     * @param roomId the roomId of the talk.
     * @param speakerID the ID of the speaker of the talk.
     * @return the ID of the talk iff the talk is successfully created.
     */

    public int createTalk(String talkTitle, int startTime, int roomId, int speakerID){
        int talkId = totalTalkCount;
        Talk newTalk = new Talk(talkId, talkTitle,startTime, roomId, speakerID);
        this.talkList.put(talkId, newTalk);
        totalTalkCount += 1;
        return talkId;
    }

    /**
     * Return a string information of the Talk given the talk ID.
     * @param talkid The ID of the Talk.
     * @return the string information of the Talk with the given talk ID.(Including talk title, start time, room ID, and number of Attendee.)
     */

    public String gettalkinfo(int talkid){
        String a = "\n-------------------------";
        Talk talk = getTalkWithId(talkid);
        String talktitle = talk.getTalkTitle();
        int talktime = talk.getStartTime();
        int talkroom = talk.getRoomId();
        int numatt = talk.getAttendeeId().size();
        a = a + "\n Talk Title:" + talktitle + "\n Talk ID:" + talkid + "\n This talk starts at " + talktime +
                "\n This talk holds in roomID " + talkroom +"\n There are " + numatt + " attendees";
        return a;
    }

    /**
     * Return a string information of the Talk given the talk ID and room name.
     * @param talkid The ID of the Talk.
     * @param roomName The name of the room this talk is hold in.
     * @return the string information of the Talk with the given talk ID.(Including talk title, start time, room ID, and number of Attendee.)
     */

    public String gettalkinfoWithName(int talkid, String roomName){
        String a = "\n-------------------------";
        Talk talk = getTalkWithId(talkid);
        String talktitle = talk.getTalkTitle();
        int talktime = talk.getStartTime();
        int talkroom = talk.getRoomId();
        int numatt = talk.getAttendeeId().size();
        a = a + "\n Talk Title:" + talktitle + "\n This talk starts at " + talktime + "\n This talk holds in roomID " +
                talkroom + "(" + roomName + ")" +"\n There are " + numatt + " attendees";
        return a;
    }

    /**
     * Return a simplified string information of the Talk given the talk ID.
     * @param talkid The ID of the Talk.
     * @return the simplified string information of the Talk with the given talk ID.(Including talk title and talk ID.)
     */

    public String gettalkinfosimp(int talkid){
        String a = "";
        Talk talk = getTalkWithId(talkid);
        String talktitle = talk.getTalkTitle();
        a = a + "\n Talk Title:" + talktitle + "\n The id of this talk is  " + talkid;
        return a;
    }

    /**
     * Return an arraylist of all the Attendee of the Talks in the given talk list.
     * @param talklist Arraylist of all ID of the Talk.
     * @return Arraylist of all Attendee of all the Talks in the talk list.
     */

    public ArrayList<Integer> getallattendee(ArrayList<Integer> talklist){
        Set<Integer> att = new HashSet<>();
        for(Integer t:talklist){

            Talk talk = getTalkWithId(t);
            att.addAll(talk.getAttendeeId());
        }
        ArrayList<Integer> result = new ArrayList<>(att);
        return result;

    }


    /**
     * Return an arraylist of all the Attendee of the Talks in the given talk list.
     * @param talkList Arraylist of all ID of the Talk.
     * @return Arraylist of all Attendee of all the Talks in the talk list.
     */

    public ArrayList<Integer> getAllSpeakers(ArrayList<Integer> talkList){
        ArrayList<Integer> speakers = new ArrayList<>();
        for(Integer t:talkList){
            Talk talk = getTalkWithId(t);
            speakers.add(talk.getSpeaker());
        }
        return speakers;

    }

    /**
     * Return the total number of Talks
     * @return the int value of the total number of Talks.
     */

    public static int getTotalTalkCount(){
        return totalTalkCount;
    }

    /**
     * Return the start time of the Talk given the talk ID.
     * @param talkID The ID of the Talk.
     * @return the start time of the Talk with the given talk ID.
     */

    public int getStartTime(int talkID){
        Talk talk = getTalkWithId(talkID);
        return talk.getStartTime();
    }

    /**
     * Set the Talk with the given talk ID with the Speaker with the given speaker ID.
     * @param speakerID The ID of the Speaker
     * @param talkID The ID of the Talk.
     */

    public void setSpeakerTo(int speakerID, int talkID){
        Talk talk = getTalkWithId(talkID);
        talk.setSpeaker(speakerID);
    }

    /**
     * Remove the Talk given the talkID
     * @param talkID The ID of the talk.
     */

    public void removeTalk(int talkID){
        this.talkList.remove(talkID);
    }

    /**
     * Get the title of the Talk given the talk ID.
     * @param talkID The ID of the talk.
     * @return a String representation of the Talk title.
     */

    public String getTitle(int talkID){
        Talk talk = this.talkList.get(talkID);
        return talk.getTalkTitle();
    }

    /**
     * Get the room ID of the Talk given the talk ID.
     * @param talkID The ID of the talk.
     * @return an int representation of the room ID
     */

    public int getRoomIdWithId(int talkID){
        Talk talk = this.talkList.get(talkID);
        return talk.getRoomId();
    }


}



