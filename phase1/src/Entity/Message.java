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

    /**
     * Get the Message ID.
     * @return the Message's ID.
     */
     public int getmessageid(){
        return this.messageid;
     }

    /**
     * Get the sender's ID.
     * @return the Message's sender's ID.
     */
     public int getSenderid(){return this.senderid;}

    /**
     * Get the getter's ID.
     * @return the Message's getter's ID.
     */
     public int getGetterid(){return this.getterid;}

    /**
     * Get the Message's text.
     * @return the string representation of the Message's text.
     */
     public String getTxt(){return this.txt;}

    /**
     * Add the given reply message ID to the Message's arraylist of reply IDs.
     * @param messageid the ID of the Message.
     */
     public void addReply(int messageid){this.replies.add(messageid);}

    /**
     * Get all the reply ID of the Message as an arraylist.
     * @return all the replies of the Message.
     */
     public ArrayList<Integer> getAllReplies(){return new ArrayList<>(this.replies);}


}
