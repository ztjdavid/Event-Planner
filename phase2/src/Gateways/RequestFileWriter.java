package Gateways;

import UseCase.*;
import UseCase.IGateWay.IRequestGateWay;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class RequestFileWriter implements IRequestGateWay {
    private Ini iniFile;

    public RequestFileWriter(String pathname) throws IOException {
        this.iniFile = new Ini(new File(pathname));
    }

    /**
     * Write new request into database.
     * @param id id
     * @param service request context
     * @param senderid id of the sender
     * @param talkid id of the event
     * @throws IOException
     */
    public void writeNewRequest(int id, String service, int senderid, int talkid)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "Service", service);
        iniFile.put(ID, "Status", false);
        iniFile.put(ID, "SenderId", senderid);
        iniFile.put(ID, "TalkId", talkid);
        iniFile.store();
    }

    /**
     * Update key "Status" in given section(id).
     * @param id id
     * @param status status
     * @throws IOException
     */
    public void updateStatus(int id, boolean status)throws IOException{
        String ID = String.valueOf(id);
        iniFile.put(ID, "Status", status);
        iniFile.store();
    }


}