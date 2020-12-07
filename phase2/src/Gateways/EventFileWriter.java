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

    public void writeNewEvent(int id, String talkTitle, int startTime, int roomId,
                              ArrayList<Integer> speakerList, int eventCapacity,
                              int duration, boolean isVip)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "TalkTitle", talkTitle);
        iniFile.put(ID, "StartTime", startTime);
        iniFile.put(ID, "RoomId", roomId);
        iniFile.put(ID, "SpeakerList", speakerList);
        iniFile.put(ID, "AttendeeList", "");
        iniFile.put(ID, "RemainingSeat", 2);
        iniFile.put(ID, "EventCapacity", eventCapacity);
        iniFile.put(ID, "SeatsOccupied", 0);
        iniFile.put(ID, "Duration", duration);
        iniFile.put(ID, "IsVip", isVip);
    }

    public void updateAttendeeList(int id, ArrayList<Integer> attList)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder();
        for(Integer attId: attList){
            s.append(attId);
            s.append(",");
        }
        iniFile.put(ID, "AttendeeList", s.toString());
        iniFile.store();
    }

    public void updateRemainingSeat(int id, int rs)throws IOException{
        String msgID = String.valueOf(id);
        iniFile.put(msgID, "RemainingSeat", rs);
        iniFile.store();
    }

    public void updateSeatsOccupied(int id, int so)throws IOException{
        String msgID = String.valueOf(id);
        iniFile.put(msgID, "SeatsOccupied", so);
        iniFile.store();
    }











}
