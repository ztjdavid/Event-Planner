package Entity;

public class Message {
    protected int messageid;
    private String txt;
    protected String reply;
    private String replyer;
    private int senderid;
    private int getterid;
    private String sendername;

    public Message(String sendername, int messageid, int senderid, int getterid, String txt){
        this.messageid = messageid;
        this.senderid = senderid;
        this.getterid = getterid;
        this.txt = txt;
        this.reply = "";
        this.sendername = sendername;
        this.replyer = "";
    }
    public void response(String a){
        this.reply = a;

    }
     public int getmessageid(){
        return this.messageid;
     }

     public int getSenderid(){return this.senderid;}

     public int getGetterid(){return this.getterid;}

     public String getTxt(){return this.txt;}
     
     public String getReply(){return this.reply;}

     public String getSendername(){return this.sendername;}

     public void setReplyer(String replyer){this.replyer = replyer;}

     public String getReplyer(){return this.replyer;}
}
