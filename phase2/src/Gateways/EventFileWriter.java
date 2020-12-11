package Gateways;

import UseCase.*;
import UseCase.IGateWay.IEventGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class EventFileWriter implements IEventGateWay {
    private Ini iniFile;
    private GatewayHandler gH = new GatewayHandler();

    public EventFileWriter(String pathname)throws IOException{
        this.iniFile = new Ini(new File(pathname));
    }

    /**
     * Write a new event into database.
     * @param id id
     * @param talkTitle talk title
     * @param startTime start time
     * @param roomId room id
     * @param speakerList a list of speakers
     * @param eventCapacity capacity of this event
     * @param duration duration
     * @param isVip if it is a vip-only event
     * @throws IOException
     */
    public void writeNewEvent(int id, String talkTitle, int startTime, int roomId,
                              ArrayList<Integer> speakerList, int eventCapacity,
                              int duration, boolean isVip)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "TalkTitle", talkTitle);
        iniFile.put(ID, "StartTime", startTime);
        iniFile.put(ID, "RoomId", roomId);
        iniFile.put(ID, "SpeakerList", "");
        iniFile.put(ID, "AttendeeList", "");
        iniFile.put(ID, "RemainingSeat", 2);
        iniFile.put(ID, "EventCapacity", eventCapacity);
        iniFile.put(ID, "SeatsOccupied", 0);
        iniFile.put(ID, "Duration", duration);
        iniFile.put(ID, "IsVip", isVip);
        iniFile.store();
    }

    /**
     * Remove an event from database.
     * @param id id
     * @throws IOException
     */
    public void removeEvent(int id)throws IOException{
        String ID = String.valueOf(id);
        if (iniFile.containsKey(ID)) {
            iniFile.remove(iniFile.get(ID));
            iniFile.store();
        }
    }

    /**
     * Update key "TalkTitle" in given section(id).
     * @param id id
     * @param title title
     * @throws IOException
     */
    public void updateTalkTitle(int id, String title)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "TalkTitle", title);
        iniFile.store();
    }

    /**
     * Update key "AttendeeList" in given section(id).
     * @param id id
     * @param attList a list of attendee id
     * @throws IOException
     */
    public void updateAttendeeList(int id, ArrayList<Integer> attList)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder("");
        for(Integer attId: attList){
            s.append(attId);
            s.append(",");
        }
        iniFile.put(ID, "AttendeeList", s.toString());
        iniFile.store();
    }

    /**
     * Update key "RemainingSeat" in given section(id).
     * @param id id
     * @param rs remaining seat
     * @throws IOException
     */
    public void updateRemainingSeat(int id, int rs)throws IOException{
        String msgID = String.valueOf(id);
        iniFile.put(msgID, "RemainingSeat", rs);
        iniFile.store();
    }

    /**
     * Update key "SeatsOccupied" in section(id).
     * @param id id
     * @param so seats occupied
     * @throws IOException
     */
    public void updateSeatsOccupied(int id, int so)throws IOException{
        String msgID = String.valueOf(id);
        iniFile.put(msgID, "SeatsOccupied", so);
        iniFile.store();
    }


}
