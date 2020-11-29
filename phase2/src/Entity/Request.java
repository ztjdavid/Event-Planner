package Entity;

public class Request {
    protected final int requestid;
    private String service;
    private boolean status;
    private int senderid;

    public Request(int requestid, String service, boolean status, int senderid){
        this.requestid = requestid;
        this.service = "";
        this.status = false;//false means pending; true means addressed
        this.senderid = senderid;
    }

    public void setService(String service){this.service = service;}

    public String getService(){return this.service;}

    public void setStatus(boolean status){this.status = status;}//pending into addressed

    public boolean getStatus(){return this.status;}

    public void setSenderid(int senderid){this.senderid = senderid;}

    public int getSenderid(){return this.senderid;}

}