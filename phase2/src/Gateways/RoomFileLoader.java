package Gateways;

import UseCase.*;
import UseCase.IGateWay.IUserGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RoomFileLoader {
    private Ini iniFile;
    private RoomManager roomM;
    private GatewayHandler gH = new GatewayHandler();

    public RoomFileLoader(String pathname, RoomManager rm)throws IOException{
        this.iniFile = new Ini(new File(pathname));
        this.roomM = rm;
    }


    public void loadData()throws NumberFormatException{
        Set<String> idSet = iniFile.keySet();
        for (String ID: idSet){
            int id = Integer.parseInt(ID);
            //get all variables
            String roomName = iniFile.get(ID, "RoomName");
            ArrayList<Integer> timetableKey = gH.listDecoder(iniFile.get(ID, "TimetableKey"));
            ArrayList<Integer> timetableValue = gH.listDecoder(iniFile.get(ID, "TimetableValue"));
            HashMap<Integer, Integer> timetable = gH.mapAssemble(timetableKey, timetableValue);
            int roomCapacity = iniFile.get(ID, "RoomCapacity", int.class);
            //create room
            roomM.scanInRoom(roomName, roomCapacity);
            //set other variables
            roomM.setRoomInfo(id, timetable);
        }

    }


}
