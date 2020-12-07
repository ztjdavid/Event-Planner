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

    public void writeNewAcc(int id, String username, String password, int userType)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "Type", userType);
        iniFile.put(ID, "Username", username);
        iniFile.put(ID, "Password", password);
        iniFile.put(ID, "Inbox", "");
        iniFile.put(ID, "SentBox", "");
        if (userType != 0)  iniFile.put(ID, "EventList", "");
        iniFile.store();
    }

    public void updateInbox(int id, ArrayList<Integer> inbox)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder();
        for(Integer msgid: inbox){
            s.append(msgid);
            s.append(",");
        }
        iniFile.put(ID, "Inbox", s.toString());
        iniFile.store();
    }

    public void updateSentBox(int id, ArrayList<Integer> sentBox)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder();
        for(Integer msgid: sentBox){
            s.append(msgid);
            s.append(",");
        }
        iniFile.put(ID, "SentBox", s.toString());
        iniFile.store();
    }

    public void updateEventList(int id, ArrayList<Integer> eList)throws IOException{
        String ID = String.valueOf(id);
        StringBuilder s = new StringBuilder();
        for(Integer eId: eList){
            s.append(eId);
            s.append(",");
        }
        iniFile.put(ID, "EventList", s.toString());
        iniFile.store();
    }

}
