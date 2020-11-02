package Entity;
import java.util.HashMap;

/**
 * The Room class contains info about a room and can schedule talks in a room.<br>
 * Level in Clean Architecture: Entity.
 */
public class Room {
    private final int roomId;
    private String roomName;
    private final HashMap<Integer, Integer> timetable;

    public Room(String roomName, int roomId){
        this.roomId = roomId;
        this.roomName = roomName;
        this.timetable = new HashMap<>();
    }

    /**
     * Get the roomId of this room.
     * @return An integer representing its room id.
     */
    public int getRoomId(){
        return this.roomId;
    }

    /**
     * Get the name of this room.
     * @return A string representing room name.
     */
    public String getRoomName(){
        return this.roomName;
    }

    /**
     * Set room name.
     * @param roomName The new room name.
     */
    public void setRoomName(String roomName){
        this.roomName = roomName;
    }

    /**
     * Schedule a talk in this room at a given time.<br>
     * <b>NOTICE: This method only checks if there is a time conflict.</b>
     * @param talkId The id of the talk that is going to be scheduled.
     * @param startTime Starting time of this talk.
     * @return True iff a talk is successfully scheduled in this room.
     */
    public boolean scheduleTalk(int talkId, int startTime){
        if (timetable.containsKey(startTime)) return false;
        timetable.put(startTime, talkId);
        return true;
    }

    /**
     * Remove a specified talk with given start time.
     * @param talkId The talk being removed.
     * @param startTime The star time of that talk.
     * @return True iff the talk at given time is removed from timetable.
     */
    public boolean removeTalk(int talkId, int startTime){
        if (!(timetable.containsKey(startTime)
                && timetable.get(startTime) == talkId)) return false;
        timetable.remove(startTime, talkId);
        return true;
    }

    /**
     * Get a copy of the timetable of this room.
     * @return A HashMap representing the timetable of this room.
     */
    public HashMap<Integer, Integer> getTimetable(){
        return new HashMap<>(this.timetable);
    }

}
