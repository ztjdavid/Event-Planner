package UseCase.IGateWay;

import java.io.IOException;

public interface IMsgGateWay {
    public void writeNewMsg(int id, String senderName, int senderId, int getterId, String txt)throws IOException;
    public void updateReplyId(int msgId, int replyId)throws IOException;
    public void updateReplier(int msgId, String replier)throws IOException;
    public void updateSenderId(int msgId, int senderId)throws IOException;
    public void updateSenderName(int msgId, String senderName)throws IOException;
    public void updateGetterId(int msgId, int getterId)throws IOException;
}
