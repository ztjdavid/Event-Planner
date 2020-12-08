package UseCase;
import Entity.*;
import UseCase.IGateWay.IRequestGateWay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    protected HashMap<Integer, Request> requestList;
    protected static int totalRequestCount = 0;
    protected ArrayList<Request> requestl;
    private IRequestGateWay gateWay;

    public RequestManager(IRequestGateWay g) {
        this.requestList = new HashMap<>();
        this.requestl = new ArrayList<>();
        this.gateWay = g;
    }

    public void createRequest(String service, int senderid, int talkid){
        int requestid = totalRequestCount;
        Request newRequest = new Request(requestid, service, senderid, talkid);
        this.requestl.add(newRequest);
        this.requestList.put(requestid, newRequest);

        try{
            this.gateWay.writeNewRequest(requestid, service, senderid, talkid);
        }catch (IOException ignored){}

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

    public void changeToAddressed(int requestid) {
        getRequestWithID(requestid).setStatus(true);
        try{
            this.gateWay.updateStatus(requestid, true);
        }catch (IOException ignored){}
    }

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

    public void setRequestInfo(int id, boolean status){
        Request r = getRequestWithID(id);
        r.setStatus(status);
    }


}

