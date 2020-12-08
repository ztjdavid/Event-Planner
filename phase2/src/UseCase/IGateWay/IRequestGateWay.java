package UseCase.IGateWay;

import java.io.IOException;

public interface IRequestGateWay {
    void writeNewRequest(int id, String service, int senderid, int talkid)throws IOException;
    void updateStatus(int id, boolean status)throws IOException;
}
