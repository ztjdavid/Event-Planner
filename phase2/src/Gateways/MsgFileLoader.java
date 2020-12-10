package Gateways;

import UseCase.*;
import UseCase.IGateWay.IMsgGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class MsgFileLoader{
    private Ini iniFile;
    private MessageManager msgM;


    public MsgFileLoader(String pathname, MessageManager msgM)throws IOException{
        this.iniFile = new Ini(new File(pathname));
        this.msgM = msgM;
    }

    /**
     * Load all message data.
     * @throws NumberFormatException
     */
    public void loadData()throws NumberFormatException{
        Set<String> idSet = iniFile.keySet();
        for (String id: idSet){
            // get all variables
            int ID = Integer.parseInt(id);
            int replyId = iniFile.get(id, "ReplyId", int.class);
            String replier = iniFile.get(id, "Replier");
            int senderId = iniFile.get(id, "SenderId", int.class);
            String senderName = iniFile.get(id, "SenderName");
            int getterId = iniFile.get(id, "GetterId", int.class);
            boolean haveRead = iniFile.get(id, "HaveRead", boolean.class);
            String txt = iniFile.get(id, "TXT");
            // create message
            msgM.scanInMessage(senderName, senderId, getterId, txt);
            // set other variables
            msgM.setMsgInfo(ID, replyId, replier, haveRead);
        }
    }

}
