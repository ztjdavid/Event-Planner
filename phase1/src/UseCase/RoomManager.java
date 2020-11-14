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

    public void createRoom(String roomName, int roomID){
        Room room = new Room(roomName, roomID);
        allRooms.put(roomID, room);
        totalRoomCount += 1;
    }

    public static int getTotalRoomCount(){
        return totalRoomCount;
    }

    public Room getRoomWithID(int roomID){
        return allRooms.get(roomID);
    }

    public boolean isOccupiedAt(int roomID, int startTime){
        Room room = getRoomWithID(roomID);
        return room.getTimetable().containsKey(startTime);
    }


}
