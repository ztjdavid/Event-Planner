package UseCase;
import Entity.*;

import java.util.Hashmap;

public class RequestManager {
    protected HashMap<Integer, Event> requestList;
    protected static int totalRequestCount = 0;

    public RequestManager() {this.requestList = new HashMap<>();}

    public void createRequest(String service, boolean status, int senderid){
        int requestid = totalRequestCount;
        Request newRequest = new Request(requestid, service, status, senderid);
        this.requestList.put(requestid, newRequest);
        totalRequestCount +=1;
    }
}

