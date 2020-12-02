package Entity;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;


public class Message {
    protected int messageid;
    private String txt;
    protected int replyId;
    private String replyer;
    private int senderid;
    private int getterid;
    private String sendername;

    public Message(String sendername, int messageid, int senderid, int getterid, String txt){
        this.messageid = messageid;
        this.senderid = senderid;
        this.getterid = getterid;
        this.txt = txt;
        this.replyId = -1;
        this.sendername = sendername;
        this.replyer = "";
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

    ///// Louisa modified
    /**
     * Get the reply of this message
     * @return a string which is the reply of this message
     */
     public int getReply(){return this.replyId;}

     public void setReply(int replyId){this.replyId = replyId;}
    /**
     * Get the name of the sender of this message
     * @return a string sender name
     */
    /////
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
