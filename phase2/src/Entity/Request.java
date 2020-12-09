package Entity;

public class Request {
    protected final int requestid;
    private String service;
    private boolean status;
    private int senderid;
    private int talkid;

    public Request(int requestid, String service, int senderid, int talkid){
        this.requestid = requestid;
        this.service = service;//"dietary restrictions" or something like that to a talk
        this.status = false;//false means pending; true means addressed
        this.senderid = senderid;
        this.talkid = talkid;
    }
    /**
     * Get the id of this request
     * @return An integer representing its request id
     */
    public int getRequestid(){return this.requestid;}

    public void setService(String service){this.service = service;}

    /**
     * Get the service content indicated in this request
     * @return A string representing the service
     */
    public String getService(){return this.service;}

    /**
     * Set the status of the request
     * @param status the status of request that will change to
     */
    public void setStatus(boolean status){this.status = status;}//pending into addressed

    /**
     * Get the status of the request
     * @return A boolean representing the status of the request: false means pending; true means addressed
     */
    public boolean getStatus(){return this.status;}

    /**
     * Set the id of the user that initialized the request
     * @param senderid the sender's id
     */
    public void setSenderid(int senderid){this.senderid = senderid;}

    /**
     * Get the id of the user that initialized the request
     * @return An integer representing the id of the user that made the request
     */
    public int getSenderid(){return this.senderid;}

    /**
     * Set the talk id relating to the request
     * @param talkid the talk id related to the request
     */
    public void setTalkid(int talkid){this.talkid = talkid;}

    /**
     * Get the talk id relating to the request
     * @return An integer representing the talk id relating to the request
     */
    public int getTalkid(){return this.talkid;}

}