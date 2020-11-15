package Entity;
import java.util.ArrayList;

/**
 * Talk class contains info about a talk and...
 * Level in Clean Architecture: Entity.
 */

public class Talk {
    protected final int talkId;
    protected String talkTitle;
    protected int startTime;
    protected int roomId;
    protected int speakerId;
    protected ArrayList<Integer> attendeeId;
    protected int remainingSeat;

    public Talk(int talkId, String talkTitle, int startTime, int roomId, int speakerID){
        this.talkId = talkId;
        this.talkTitle = talkTitle;
        this.startTime = startTime;
        this.roomId = roomId;
        this.speakerId = speakerID;
        this.attendeeId = new ArrayList<>();
        this.remainingSeat = 2;
    }

    /**
     * Get the talkId of this talk.
     * @return An integer representing its talk id.
     */
    public int getTalkId(){return this.talkId;}

    /**
     * Get the title of this talk.
     * @return A string representing talk title.
     */
    public String getTalkTitle(){return this.talkTitle;}

    /**
     * Set talk title.
     * @param talkTitle The new talk title.
     */
    public void setTalkTitle(String talkTitle){this.talkTitle = talkTitle;}

    /**
     * Get the start time of this talk.
     * @return An integer representing talk start time.
     */
    public int getStartTime(){return this.startTime;}

    /**
     * Set talk start time.
     * @param startTime The new talk startTime, where it should be from 9am to 4pm(if one hour duration).
     */
    public void setStartTime(int startTime){this.startTime = startTime;}

    /**
     * Get the room id of this talk.
     * @return An integer representing talk room id.
     */
    public int getRoomId(){return this.roomId;}

    /**
     * Set talk room id.
     * @param roomId The new talk room id.
     */
    public void setRoomId(int roomId){this.roomId = roomId;}

    /**
     * Get the speaker of this talk.
     * @return The ID of a talk speaker.
     */
    public int getSpeaker(){return this.speakerId;}

    /**
     * Set talk speaker.
     * @param speakerID The ID of the new talk speaker.
     */
    public void setSpeaker(int speakerID){this.speakerId = speakerID;}

    /**
     * Get a copy of the attendees' id of this talk.
     * @return An ArrayList representing the attendees' id of this talk.
     */
    public ArrayList<Integer> getAttendeeId(){return new ArrayList<>(this.attendeeId);}

    /**
     * Get the remaining seats of this talk.
     * @return An integer representing talk remaining seats, where it should be in between 0 to 2.
     */
    public int getRemainingSeat(){return this.remainingSeat;}

}
