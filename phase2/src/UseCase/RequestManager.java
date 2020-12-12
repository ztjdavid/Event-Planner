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

    /**
     * Create request
     * @param service service the user want to get in the event
     * @param senderid sender id
     * @param talkid talk id
     */
    public void createRequest(String service, int senderid, int talkid){
        try{
            this.gateWay.writeNewRequest(totalRequestCount, service, senderid, talkid);
        }catch (IOException ignored){}
        createHelper(service, senderid, talkid);
    }

    /**
     * Used in gateway to scan a new request
     * @param service service the user want to get in the event
     * @param senderid sender id
     * @param talkid talk id
     */
    public void scanInRequest(String service, int senderid, int talkid){
        createHelper(service, senderid, talkid);
    }

    /**
     * Helper to create a request
     * @param service service the user want to get in the event
     * @param senderid sender id
     * @param talkid talk id
     */
    private void createHelper(String service, int senderid, int talkid) {
        int requestid = totalRequestCount;
        Request newRequest = new Request(requestid, service, senderid, talkid);
        this.requestl.add(newRequest);
        this.requestList.put(requestid, newRequest);

        totalRequestCount +=1;
    }

    /**
     * Get the request by entering its Id
     * @param requestid Id of the targeted request
     * @return The Request with the given request Id
     */
    public Request getRequestWithID(int requestid) { return this.requestList.get(requestid);}

    /**
     * Show the status of the request by the given Id
     * @param requestid Id of the targeted request
     * @return A String indicating the status of the request is whether "pending" or "addressed"
     */
    public String showStatus(int requestid){
        if (!getRequestWithID(requestid).getStatus()){
            return "pending";
        }
        else {
            return "addressed";
        }
    }

    /**
     * Change the status of the request from "pending" to "addressed"
     * @param requestid Id of the targeted request
     */
    public void changeToAddressed(int requestid) {
        getRequestWithID(requestid).setStatus(true);
        try{
            this.gateWay.updateStatus(requestid, true);
        }catch (IOException ignored){}
    }

    //for all organizer to see
    /**
     * Provide the information of the request including the senderid, talkid, service and status of the request
     * @param requestid Id of the targeted request
     * @return A string providing the information of the request
     */
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

    /**
     * Check if the request exits in the request list.
     * @param requestid the id of the request
     * @return True iff the request id is in the request list.
     */
    public boolean checkRequestExist(int requestid){return requestList.containsKey(requestid);}

    /**
     * Return a string showing the message for the given id
     * @param i id of message
     * @return a string showing the message context
     */
    public HashMap<Integer, Request> getRequestList(){return new HashMap<>(requestList);}

    /**
     * Get the Ids of all requests in this request list
     * @return An arraylist of all request Ids
     */
    public ArrayList<Integer> getRequestID(){
        return new ArrayList<>(this.requestList.keySet());
    }

    /**
     * Get the all requests from a targeted sender
     * @param senderid the Id of the sender
     * @return An arraylist of all the requests from a sender
     */
    private ArrayList<Request> getrequestofsender(int senderid){
        ArrayList<Request> res = new ArrayList<>();
        for(Request re : requestl){
            if(re.getSenderid() == senderid){res.add(re);}
        }
        return res;
    }

    /**
     * Provide the information of requests for the given request list
     * @param requestlist the arraylist of the targeted requests
     * @return A String providing the information of the targeted requests
     */
    private String showall(ArrayList<Request> requestlist){
        String a = "";
        a += "These are your requests";
        for(Request re : requestlist){a += "\n" + getRequestInfo(re.getRequestid());}
        return a;

    }

    /**
     * Provide the information of requests from a targeted sender
     * @param senderid Id of the sender of requests
     * @return A String providing the information of requests from the sender
     */
    public String showallre(int senderid){
        return showall(getrequestofsender(senderid));
    }

    /**
     * Set the status of the given request by its Id to "pending" or "addressed"
     * @param id the request id
     * @param  status the status of the request: false -> pending; true -> addressed
     */
    public void setRequestInfo(int id, boolean status){
        Request r = getRequestWithID(id);
        r.setStatus(status);
    }


}

