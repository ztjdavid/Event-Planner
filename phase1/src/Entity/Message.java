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

    /**
     * Set the reply of a message.
     * @param a is a string which represent the text of reply
     */
    public void response(String a){
        this.reply = a;

    }

    /**
     * Get the id of this message
     * @return an integer message id
     */
     public int getmessageid(){
        return this.messageid;
     }
    /**
     * Get the id of the sender of this message
     * @return an integer sender id
     */
     public int getSenderid(){return this.senderid;}
    /**
     * Get the id of the getter of this message
     * @return an integer getter id
     */
     public int getGetterid(){return this.getterid;}
    /**
     * Get the text of this message
     * @return a string which is the text of this message
     */
     public String getTxt(){return this.txt;}
    /**
     * Get the reply of this message
     * @return a string which is the reply of this message
     */
     public String getReply(){return this.reply;}
    /**
     * Get the name of the sender of this message
     * @return a string sender name
     */
     public String getSendername(){return this.sendername;}
    /**
     * Set the name of the replier of this message
     */
     public void setReplyer(String replyer){this.replyer = replyer;}
    /**
     * Get the name of the replier of this message
     * @return a string replier name
     */
     public String getReplyer(){return this.replyer;}
}
