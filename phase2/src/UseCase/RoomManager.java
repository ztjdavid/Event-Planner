package UseCase;
import Entity.*;
import UseCase.IGateWay.IRoomGateWay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomManager {
    protected HashMap<Integer, Room> allRooms;
    protected static int totalRoomCount;
    private IRoomGateWay gateWay;

    public RoomManager(IRoomGateWay g){
        this.gateWay = g;
        this.allRooms = new HashMap<>();
    }

    /**
     * Create a new Room with given room name, with room ID be the total number of Room.
     * @param roomName String representation of room name.
     * @return The ID of the room
     */
    public int createRoom(String roomName, int roomCapacity){
        try {
            this.gateWay.writeNewRoom(totalRoomCount, roomName, roomCapacity);
        }catch (IOException ignored){}
        return createHelper(roomName, roomCapacity);
    }

    /**
     * Used in gateway to scan a new room
     * @param roomName room name
     * @param roomCapacity room capacity
     * @return The ID of the room
     */
    public int scanInRoom(String roomName, int roomCapacity){
        return createHelper(roomName, roomCapacity);
    }

    /**
     * Helper to create a room
     * @param roomName room name
     * @param roomCapacity room capacity
     * @return The ID of the room
     */
    private int createHelper(String roomName, int roomCapacity) {
        int ID = totalRoomCount;
        Room room = new Room(roomName, ID, roomCapacity);
        allRooms.put(ID, room);
        totalRoomCount += 1;
        return ID;
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
     * Get an arraylist of all the Rooms' room ID as int.
     * @return The arraylist of all the Room ID.
     */
    public ArrayList<Integer> getAllRooms(){
        return new ArrayList<>(this.allRooms.keySet());
    }

    /**
     * Get the string representation of Room name given the room ID.
     * @param roomID The ID of the Room.
     * @return The name of the Room.
     */
    public String getRoomName(int roomID){
        Room room = this.allRooms.get(roomID);
        return room.getRoomName();
    }

    /**
     * Get the hashmap of Room's timetable given the room ID, with key as start time and value as talk ID.
     * @param roomID The ID of the Room.
     * @return The timetable of the Room.
     */
    public HashMap<Integer, Integer> getTimeTable(int roomID){
        Room room = this.allRooms.get(roomID);
        return room.getTimetable();
    }

    /**
     * Schedule the Event with the given talk ID to the Room given the room ID at the given start time.
     * @param talkID the ID of the Event as int.
     * @param startTime the start time of the Event as int.
     * @param roomID The ID of the Room as int.
     */
    public boolean addNewTalkToRoom(int talkID, int startTime, int roomID, int duration){
        Room room = this.allRooms.get(roomID);
        boolean succeed = room.scheduleTalk(talkID, startTime, duration);
        if (succeed){
            try{
                this.gateWay.updateTimetable(roomID, getTimeTable(roomID));
            }catch (IOException ignored){}
        }
        return succeed;
    }

    /**
     * Check if the given room id is valid.
     * @param roomId The room id to be checked.
     * @return True iff the given room exists.
     */
    public boolean isValidRoomId(int roomId){ return allRooms.containsKey(roomId);}

    /**
     * Check if the room's capacity is enough for the event
     * @param roomID The room id to be checked
     * @param eventCapacity The capacity of the event that will be created
     * @return A boolean indicating whether the room is capable of holding the event in terms of capacity
     */
    public boolean isWithinCapacity(int roomID, int eventCapacity){
        Room room = allRooms.get(roomID);
        return room.getRoomCapacity() >= eventCapacity;
    }

    /**
     * Set information for the room
     * @param id id of the room
     * @param timetable timetable of the room
     */
    public void setRoomInfo(int id, HashMap<Integer, Integer> timetable){
        Room r = getRoomWithID(id);
        r.setTimetable(timetable);
    }


}
