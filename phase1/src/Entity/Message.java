package Entity;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    protected int messageid;
    private String txt;
    protected ArrayList<Integer> replies;
    private int senderid;
    private int getterid;

    public Message(int messageid, int senderid, int getterid, String txt){
        this.messageid = messageid;
        this.senderid = senderid;
        this.getterid = getterid;
        this.txt = txt;
        this.replies = new ArrayList<>();
    }

     public int getmessageid(){
        return this.messageid;
     }

     public int getSenderid(){return this.senderid;}
     public int getGetterid(){return this.getterid;}
     public String getTxt(){return this.txt;}
     public void addReply(int messageid){this.replies.add(messageid);}
     public ArrayList<Integer> getAllReplies(){return new ArrayList<>(this.replies);}


}
