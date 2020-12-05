package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    protected HashMap<Integer, Request> requestList;
    protected static int totalRequestCount = 0;
    protected ArrayList<Request> requestl;

    public RequestManager() {this.requestList = new HashMap<>(); this.requestl = new ArrayList<>();}

    public void createRequest(String service, int senderid, int talkid){
        int requestid = totalRequestCount;
        Request newRequest = new Request(requestid, service, senderid, talkid);
        this.requestl.add(newRequest);
        this.requestList.put(requestid, newRequest);
        totalRequestCount +=1;
    }


    public Request getRequestWithID(int requestid) { return this.requestList.get(requestid);}

    public String showStatus(int requestid){
        if (!getRequestWithID(requestid).getStatus()){
            return "pending";
        }
        else {
            return "addressed";
        }
    }

    public void changeToAddressed(int requestid) {getRequestWithID(requestid).setStatus(true);}

    //for all organizer to see
    public String getRequestInfo(int requestid) {
        String a = "";
        Request request = getRequestWithID(requestid);
        int senderid = request.getSenderid();
        int talkid = request.getTalkid();
        String service = request.getService();
        String status = showStatus(requestid);
        a = a + "User(" + senderid + ") in the event(" + talkid + ") want " + service + "in that event" +
                "\nStatus: " + status;
        return a;
    }

    /////Louisa Modified

    public void cancelRequest(int requestid){requestList.remove(requestid);}

    public boolean checkRequestExist(int requestid){return requestList.containsKey(requestid);}

    public HashMap<Integer, Request> getRequestList(){return new HashMap<>(requestList);}

    public ArrayList<Integer> getRequestID(){
        return new ArrayList<>(this.requestList.keySet());
    }

    private ArrayList<Request> getrequestofsender(int senderid){
        ArrayList<Request> res = new ArrayList<>();
        for(Request re : requestl){
            if(re.getSenderid() == senderid){res.add(re);}
        }
        return res;
    }

    private String showall(ArrayList<Request> requestlist){
        String a = new String();
        a += "These are your requests";
        for(Request re : requestlist){a += "\n" + getRequestInfo(re.getRequestid());}
        return a;

    }

    public String showallre(int senderid){
        return showall(getrequestofsender(senderid));
    }


}

