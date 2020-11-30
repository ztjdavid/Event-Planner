package UseCase;
import Entity.*;

import java.util.Hashmap;

public class RequestManager {
    protected HashMap<Integer, Request> requestList;
    protected static int totalRequestCount = 0;

    public RequestManager() {this.requestList = new HashMap<>();}

    //add the attribute of talk to relate a request with a talk?
    public void createRequest(String service, boolean status, int senderid){
        int requestid = totalRequestCount;
        Request newRequest = new Request(requestid, service, status, senderid);
        this.requestList.put(requestid, newRequest);
        totalRequestCount +=1;
    }

    public String showStatus(int requestid){
        if (requestList.get(requestid).getStatus() == false){
            return "pending";
        }
        else {
            return "addressed";
        }
    }

    public void changeToAddressed(int requestid){
        requestList.get(requestid).setStatus(true);
    }


}

