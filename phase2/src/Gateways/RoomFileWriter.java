package Gateways;

import UseCase.*;
import UseCase.IGateWay.IRoomGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RoomFileWriter implements IRoomGateWay {
    private Ini iniFile;
    private GatewayHandler gH = new GatewayHandler();

    public RoomFileWriter(String pathname)throws IOException{
        this.iniFile = new Ini(new File(pathname));
    }


    public void writeNewRoom(int id, String roomName, int roomC)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "RoomName", roomName);
        iniFile.put(ID, "TimetableKey", "");
        iniFile.put(ID, "TimetableValue", "");
        iniFile.put(ID, "RoomCapacity", roomC);
        iniFile.store();
    }

    public void updateTimetable(int id, HashMap<Integer, Integer> timetable)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder key = new StringBuilder("");
        StringBuilder value = new StringBuilder("");
        // generate key string
        for (Integer k: timetable.keySet()){
            key.append(k);
            key.append(",");
        }

        // generate value string
        for (Integer v: timetable.values()){
            value.append(v);
            value.append(",");
        }
        // write
        iniFile.put(ID, "TimetableKey", key.toString());
        iniFile.put(ID, "TimetableValue", value.toString());
        iniFile.store();
    }



}
