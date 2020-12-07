package Gateways;

import UseCase.*;
import UseCase.IGateWay.IMsgGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class MsgFileWriter implements IMsgGateWay{
    private Ini iniFile;

    public MsgFileWriter(String pathname)throws IOException{
        this.iniFile = new Ini(new File(pathname));
    }

    public void writeNewMsg(int id, String senderName, int senderId, int getterId,
                            String txt)throws IOException{
        String ID = String.valueOf(id);
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
