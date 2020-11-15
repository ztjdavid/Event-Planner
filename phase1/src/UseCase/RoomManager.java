package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomManager {
    protected HashMap<Integer, Room> allRooms;
    protected static int totalRoomCount;

    public RoomManager(){
        this.allRooms = new HashMap<>();
    }

    /**
     * Create a new Room with given room name and given room ID.
     * @param roomName String representation of room name.
     * @param roomID int representation of room ID.
     */
    public void createRoom(String roomName, int roomID){
        Room room = new Room(roomName, roomID);
        allRooms.put(roomID, room);
        totalRoomCount += 1;
    }

    /**
     * Get the total number of room.
     * @return int representation of the total number of room.
     */
    public static int getTotalRoomCount(){
        return totalRoomCount;
    }

    /**
     * Get the Room associated with the given room ID.
     * @param roomID int representation of room ID.
     * @return The Room associated with the room ID.
     */
    public Room getRoomWithID(int roomID){
        return allRooms.get(roomID);
    }

    /**
     * Check if the Room given the room ID is occupied at the given start time.
     * @param roomID int representation of room ID.
     * @param startTime int representation of the start time.
     * @return True iff the Room is occupied at the start time.
     */
    public boolean isOccupiedAt(int roomID, int startTime){
        Room room = getRoomWithID(roomID);
        return room.getTimetable().containsKey(startTime);
    }

    /**
     * Schedule the Talk given talk ID to the Room given room ID, at the given start time.
     * @param roomID int representation of room ID.
     * @param talkID int representation of the talk ID.
     * @param startTime int representation of the start time of the Talk.
     */
    public void scheduleTalk(int roomID, int talkID, int startTime){
        Room room = getRoomWithID(roomID);
        room.scheduleTalk(talkID, startTime);
    }


}
