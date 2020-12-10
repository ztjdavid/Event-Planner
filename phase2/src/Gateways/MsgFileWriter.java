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

    /**
     * Write a new message into database.
     * @param id
     * @param senderName
     * @param senderId
     * @param getterId
     * @param txt
     * @throws IOException
     */
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

    /**
     * Update key "ReplyId" in given section(id).
     * @param msgId id
     * @param replyId replier's id
     * @throws IOException
     */
    public void updateReplyId(int msgId, int replyId)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "ReplyId", replyId);
        iniFile.store();
    }

    /**
     * Update key "Replier" in given section(id).
     * @param msgId id
     * @param replier name of the replier
     * @throws IOException
     */
    public void updateReplier(int msgId, String replier)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "Replier", replier);
        iniFile.store();
    }

    /**
     * Update key "SenderId" in given section(id).
     * @param msgId id
     * @param senderId id of the sender
     * @throws IOException
     */
    public void updateSenderId(int msgId, int senderId)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "SenderId", senderId);
        iniFile.store();
    }

    /**
     * Update key "SenderId" in given section(id).
     * @param msgId id
     * @param senderName name of the sender
     * @throws IOException
     */
    public void updateSenderName(int msgId, String senderName)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "SenderId", senderName);
        iniFile.store();
    }

    /**
     * Update key "GetterId" in given section(id).
     * @param msgId id
     * @param getterId id of the getter
     * @throws IOException
     */
    public void updateGetterId(int msgId, int getterId)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "GetterId", getterId);
        iniFile.store();
    }

    /**
     * Update key "HaveRead" in given section(id).
     * @param msgId id
     * @param haveRead if this message has been read
     * @throws IOException
     */
    public void updateReadStatus(int msgId, boolean haveRead)throws IOException{
        String msgID = String.valueOf(msgId);
        iniFile.put(msgID, "HaveRead", haveRead);
        iniFile.store();
    }




}
