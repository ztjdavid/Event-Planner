package Gateways;

import UseCase.*;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class UserFileGateway {
    private Ini iniFile;
    private AccountManager accM;
    private AttendeeManager attM = new AttendeeManager();
    private SpeakerManager spkM = new SpeakerManager();
    private OrganizerManager orgM = new OrganizerManager();
    private GatewayHandler gH = new GatewayHandler();
    public UserFileGateway(String pathname, AccountManager accM)throws IOException{
        this.iniFile = new Ini(new File(pathname));
        this.accM = accM;
    }

    public void writeNewAcc(String username, String password, int userType)throws IOException{
        String id = String.valueOf(accM.getTotalNumOfAccount() - 1);
        iniFile.put(id, "Type", userType);
        iniFile.put(id, "Username", username);
        iniFile.put(id, "Password", password);
        iniFile.put(id, "Inbox", "");
        iniFile.put(id, "SentBox", "");
        if (userType != 0)  iniFile.put(id, "EventList", "");
        iniFile.store();
    }

    public void updateInbox(int id, int msgId)throws IOException{
        String ID = String.valueOf(id);
        String currInbox = iniFile.get(ID, "Inbox") + msgId + ",";
        iniFile.put(ID, "Inbox", currInbox);
        iniFile.store();
    }

    public void updateSentBox(int id, int msgId)throws IOException{
        String ID = String.valueOf(id);
        String currSentBox = iniFile.get(ID, "SentBox") + msgId + ",";
        iniFile.put(ID, "SentBox", currSentBox);
        iniFile.store();
    }

    public void updateEventList(int id, int msgId)throws IOException{
        if (!accM.isOrganizerAcc(id)){
            String ID = String.valueOf(id);
            String currEventList = iniFile.get(ID, "EventList") + msgId + ",";
            iniFile.put(ID, "EventList", currEventList);
            iniFile.store();
        }
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
