package Gateways;

import UseCase.*;
import org.ini4j.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
public class RequestFileLoader {
    private Ini iniFile;
    private RequestManager requestM;

    public RequestFileLoader(String pathname, RequestManager rm)throws IOException{
        this.iniFile = new Ini(new File(pathname));
        this.requestM = rm;
    }

    /**
     * Load all request data.
     * @throws NumberFormatException
     */
    public void loadData()throws NumberFormatException{
        Set<String> idSet = iniFile.keySet();
        for (String ID: idSet){
            int id = Integer.parseInt(ID);
            //get all variables
            String service = iniFile.get(ID, "Service");
            boolean status = iniFile.get(ID, "Status", boolean.class);
            int senderId = iniFile.get(ID, "SenderId", int.class);
            int talkId = iniFile.get(ID, "TalkId", int.class);
            // create request
            this.requestM.scanInRequest(service, senderId, talkId);
            // set other variables
            this.requestM.setRequestInfo(id, status);
        }
    }


}
