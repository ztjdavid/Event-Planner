package Entity;
import java.util.ArrayList;

/**
 * Talk class contains info about a talk and...
 * Level in Clean Architecture: Entity.
 */

public class Talk {
    protected final int talkId;
    protected String talkTitle;
    protected int startTime; /** 有一点不太确定怎么规定这个时间是在9am-5pm之间的 */
    protected int roomId;
    protected String speaker;
    protected ArrayList<Integer> attendeeId;
    protected int remainingSeat;

    public Talk(int talkId, String talkTitle, int startTime, int roomId, String speaker){
        this.talkId = talkId;
        this.talkTitle = talkTitle;
        this.startTime = startTime;
        this.roomId = roomId;
        this.speaker = speaker;
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
    public void setTalkTitle(int startTime){this.startTime = startTime;}

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
     * @return A string representing talk speaker.
     */
    public String getSpeaker(){return this.speaker;}

    /**
     * Set talk speaker.
     * @param speaker The new talk speaker.
     */
    public void setSpeaker(String speaker){this.speaker = speaker;}

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
