package Entity;
import java.util.ArrayList;

/**
 * Event class contains info about a talk and...
 * Level in Clean Architecture: Entity.
 */

public class Event {
    protected final int talkId;
    protected String talkTitle;
    protected int startTime;
    protected int roomId;
    protected ArrayList<Integer> speakerList;
    protected ArrayList<Integer> attendeeId;
    protected int remainingSeat;
    protected int eventCapacity;
    protected int seatsOccupied;
    protected int duration;

    public Event(int talkId, String talkTitle, int startTime, int roomId, ArrayList<Integer> speakerID, int eventCapacity, int duration){
        this.talkId = talkId;
        this.talkTitle = talkTitle;
        this.startTime = startTime;
        this.roomId = roomId;
        this.speakerList = new ArrayList<>();
        this.speakerList.addAll(speakerID);
        this.attendeeId = new ArrayList<>();
        this.remainingSeat = 2;
        this.eventCapacity = eventCapacity;
        this.seatsOccupied = 0;
        this.duration = duration;
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
    public ArrayList<Integer> getSpeakerList(){
        return new ArrayList<>(this.speakerList);
    }

    /**
     * Set talk speaker.
     * @param speakerID The ID of the new talk speaker.
     */
    public void setSpeaker(int speakerID){this.speakerList.add(speakerID);}

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

    /**
     * Check if this talk is full.
     * @return True iff attendee can still enrol this talk.
     */
    public boolean isFull(){ return this.remainingSeat == 0;}

    /**
     * Add a new Attendee to this talk.
     * @param attId The id of attendee being added.
     * @return True iff the attendee is successfully added.
     */
    public boolean addAttendee(int attId){
        if (isFull() || this.attendeeId.contains(attId)) return false;
        this.attendeeId.add(attId);
        this.remainingSeat -= 1;
        this.seatsOccupied += 1;
        return true;
    }

    /**
     * Remove a Attendee from this talk.
     * @param attId The id of attendee being removed.
     * @return True iff the attendee is successfully removed.
     */
    public boolean removeAttendee(int attId){
        if (!this.attendeeId.contains(attId)) return false;
        this.attendeeId.remove(attId);
        this.remainingSeat += 1;
        return true;
    }

    public int getEventType(){
        if(this.speakerList.size()==1){
            return 1; //1 is talk
        }else if(this.speakerList.size()==0){
            return 0; //0 is party
        }else {
            return 2; //2 is discussion
        }
    }

    public int getDuration(){
        return this.duration;
    }


}
