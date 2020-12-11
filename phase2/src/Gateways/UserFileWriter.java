package Gateways;

import UseCase.*;
import UseCase.IGateWay.IUserGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class UserFileWriter implements IUserGateWay{
    private Ini iniFile;
    private GatewayHandler gH = new GatewayHandler();
    public UserFileWriter(String pathname)throws IOException{
        this.iniFile = new Ini(new File(pathname));
    }

    /**
     * Write a new account into database.
     * @param id id
     * @param username username
     * @param password password
     * @param userType user type
     * @throws IOException
     */
    public void writeNewAcc(int id, String username, String password, int userType)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "Type", userType);
        iniFile.put(ID, "Username", username);
        iniFile.put(ID, "Password", password);
        iniFile.put(ID, "Inbox", "");
        iniFile.put(ID, "SentBox", "");
        iniFile.put(ID, "UnreadInbox", "");
        iniFile.put(ID, "ArchiveBox", "");
        if (userType != 0)  iniFile.put(ID, "EventList", "");
        iniFile.store();
    }

    public void updateArchiveBox(int id, ArrayList<Integer> archive)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder("");
        for(Integer msgid: archive){
            s.append(msgid);
            s.append(",");
        }
        iniFile.put(ID, "ArchiveBox", s.toString());
        iniFile.store();
    }

    /**
     * Update key "UnreadInbox" in given section(id).
     * @param id id
     * @param unreadInbox unreadBox
     * @throws IOException
     */
    public void updateUnreadInbox(int id, ArrayList<Integer> unreadInbox)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder("");
        for(Integer msgid: unreadInbox){
            s.append(msgid);
            s.append(",");
        }
        iniFile.put(ID, "UnreadInbox", s.toString());
        iniFile.store();
    }

    /**
     * Update key "Inbox" in given section(id).
     * @param id id
     * @param inbox inbox
     * @throws IOException
     */
    public void updateInbox(int id, ArrayList<Integer> inbox)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder("");
        for(Integer msgid: inbox){
            s.append(msgid);
            s.append(",");
        }
        iniFile.put(ID, "Inbox", s.toString());
        iniFile.store();
    }

    /**
     * Update key "SentBox" in given section(id).
     * @param id id
     * @param sentBox sentBox
     * @throws IOException
     */
    public void updateSentBox(int id, ArrayList<Integer> sentBox)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder("");
        for(Integer msgid: sentBox){
            s.append(msgid);
            s.append(",");
        }
        iniFile.put(ID, "SentBox", s.toString());
        iniFile.store();
    }

    /**
     * Update key "EventList" in given section(id).
     * @param id id
     * @param eList a list of event id
     * @throws IOException
     */
    public void updateEventList(int id, ArrayList<Integer> eList)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder("");
        for(Integer eId: eList){
            s.append(eId);
            s.append(",");
        }
        iniFile.put(ID, "EventList", s.toString());
        iniFile.store();
    }

}
