package Gateways;

import UseCase.*;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class MsgFileGateway {
    private Ini iniFile;
    private MessageManager msgM;


    public MsgFileGateway(String pathname, MessageManager msgM)throws IOException{
        this.iniFile = new Ini(new File(pathname));
        this.msgM = msgM;
    }

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
            msgM.createmessage(senderName, senderId, getterId, txt);
            // set other variables
            msgM.setMsgInfo(ID, replyId, replier, haveRead);
        }
    }

    public void writeNewMsg(String senderName, int senderId, int getterId,
                            String txt)throws IOException{
        String ID = String.valueOf(msgM.getTotalNumOfMsg() - 1);
        iniFile.put(ID, "ReplyId", -1);
        iniFile.put(ID, "Replier", "");
        iniFile.put(ID, "SenderId", senderId);
        iniFile.put(ID, "SenderName", senderName);
        iniFile.put(ID, "GetterId", getterId);
        iniFile.put(ID, "HaveRead", false);
        iniFile.put(ID, "TXT", txt);
        iniFile.store();
    }

    public void updateReplyId(int msgId, int replyId)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "ReplyId", replyId);
        iniFile.store();
    }

    public void updateReplier(int msgId, String replier)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "Replier", replier);
        iniFile.store();
    }

    public void updateSenderId(int msgId, int senderId)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "SenderId", senderId);
        iniFile.store();
    }

    public void updateSenderName(int msgId, String senderName)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "SenderId", senderName);
        iniFile.store();
    }

    public void updateGetterId(int msgId, int getterId)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "GetterId", getterId);
        iniFile.store();
    }

    public void updateReadStatus(int msgId, boolean haveRead)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "HaveRead", haveRead);
        iniFile.store();
    }




}
