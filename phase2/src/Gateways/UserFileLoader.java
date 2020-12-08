package Gateways;

import UseCase.*;
import UseCase.IGateWay.IUserGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class UserFileLoader{
    private Ini iniFile;
    private AccountManager accM;
    private AttendeeManager attM;
    private SpeakerManager spkM;
    private OrganizerManager orgM;
    private VIPManager vipM;
    private GatewayHandler gH = new GatewayHandler();
    public UserFileLoader(String pathname, AccountManager accM, AttendeeManager attM, SpeakerManager spkM,
                          OrganizerManager orgM, VIPManager vipM)throws IOException{
        this.iniFile = new Ini(new File(pathname));
        this.accM = accM;
        this.attM = attM;
        this.spkM = spkM;
        this.orgM = orgM;
        this.vipM = vipM;
    }


    public void loadData()throws NumberFormatException{
        Set<String> idSet = iniFile.keySet(); // get id of all accounts
        for (String id: idSet){
            int userType = iniFile.get(id, "Type", int.class);
            String userName = iniFile.get(id, "Username");
            String passW = iniFile.get(id, "Password");
            ArrayList<Integer> inBox = gH.listDecoder(iniFile.get(id, "Inbox"));
            ArrayList<Integer> sentBox = gH.listDecoder(iniFile.get(id, "SentBox"));
            switch (userType){
                case 1:
                    loadAttendee(id, userName, passW, inBox, sentBox);
                    break;
                case 2:
                    loadSpeaker(id, userName, passW, inBox, sentBox);
                    break;
                case 3:
                    loadVIP(id, userName,passW,inBox,sentBox);
                    break;
                case 0:
                    loadOrganizer(id, userName, passW, inBox, sentBox);
                    break;
                default:
                    break;
            }
        }
    }

    private void loadAttendee(String id, String userName, String passW, ArrayList<Integer> inbox,
                              ArrayList<Integer> sentBox)throws NumberFormatException{
        int ID = Integer.parseInt(id);
        ArrayList<Integer> eventList = gH.listDecoder(iniFile.get(id, "EventList"));
        accM.createAccount(userName, passW, 1);
        attM.setAccInfo(ID, inbox, sentBox, eventList);
    }

    private void loadSpeaker(String id, String userName, String passW, ArrayList<Integer> inbox,
                              ArrayList<Integer> sentBox)throws NumberFormatException{
        int ID = Integer.parseInt(id);
        ArrayList<Integer> eventList = gH.listDecoder(iniFile.get(id, "EventList"));
        accM.createAccount(userName, passW, 2);
        spkM.setAccInfo(ID, inbox, sentBox, eventList);
    }

    private void loadOrganizer(String id, String userName, String passW, ArrayList<Integer> inbox,
                              ArrayList<Integer> sentBox)throws NumberFormatException{
        int ID = Integer.parseInt(id);
        accM.createAccount(userName, passW, 0);
        orgM.setAccInfo(ID, inbox, sentBox);
    }

    private void loadVIP(String id, String userName, String passW, ArrayList<Integer> inbox,
                         ArrayList<Integer> sentBox)throws NumberFormatException{
        int ID = Integer.parseInt(id);
        ArrayList<Integer> eventList = gH.listDecoder(iniFile.get(id, "EventList"));
        accM.createAccount(userName, passW, 3);
        attM.setAccInfo(ID, inbox, sentBox, eventList);
    }

}
