package Gateways;

import UseCase.*;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class EventFileGateway {
    private Ini iniFile;
    private EventManager eventM;
    private GatewayHandler gH = new GatewayHandler();

    public EventFileGateway(String pathname, EventManager eventM)throws IOException{
            this.iniFile = new Ini(new File(pathname));
            this.eventM = eventM;
    }

    public void loadData()throws NumberFormatException{
        Set<String> idSet = iniFile.keySet();
        for (String ID: idSet){
            //get all variables
            int id = Integer.parseInt(ID);
            String talkTitle = iniFile.get(ID, "TalkTitle");
            int startTime = iniFile.get(ID, "StartTime", int.class);
            int roomId = iniFile.get(ID, "RoomId", int.class);
            ArrayList<Integer> speakerList = gH.listDecoder(iniFile.get(ID, "SpeakerList"));
            ArrayList<Integer> attendeeList = gH.listDecoder(iniFile.get(ID, "AttendeeList"));
            int remainingSeat = iniFile.get(ID, "RemainingSeat", int.class);
            int eventCapacity = iniFile.get(ID, "EventCapacity", int.class);
            int seatsOccupied = iniFile.get(ID, "SeatsOccupied", int.class);
            int duration = iniFile.get (ID, "Duration", int.class);
            boolean isVip = iniFile.get(ID, "IsVip", boolean.class);
            // create event
            eventM.createEvent(talkTitle, startTime, roomId, speakerList, eventCapacity, duration, isVip);
            // set other variables
            eventM.setEventInfo(id, attendeeList, remainingSeat, seatsOccupied);
        }

    }









}
