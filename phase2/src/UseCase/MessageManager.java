package UseCase;
import Entity.*;
import UseCase.IGateWay.IMsgGateWay;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageManager {
    static ArrayList<Message> allmessage;
    private IMsgGateWay gateWay;
    private int nextId = 0;

    public MessageManager(IMsgGateWay g) {
        this.gateWay =g;
        allmessage = new ArrayList<>();
    }

    /**
     * Create a message with given sender ID, getter ID, and txt. Update the arraylist of all message.
     * @param senderid message sender's ID as int.
     * @param getterid message getter's ID as int.
     * @param txt String representation of the message
     * @return the id of message as int.
     */

    public int createmessage(String sendername, int senderid, int getterid, String txt) {
        int id = nextId;
        try{
            this.gateWay.writeNewMsg(id, sendername, senderid, getterid, txt);
        }catch (IOException ignored){}

        createHelper(id, sendername, senderid, getterid, txt);
        nextId += 1;
        return id;
    }

    /**
     * Used in gateway to scan a new message
     * @param id id of message
     * @param sendername sender name
     * @param senderid sender id
     * @param getterid getter id
     * @param txt message context
     */
    public void scanInMessage(int id, String sendername, int senderid, int getterid, String txt) {
        createHelper(id, sendername, senderid, getterid, txt);
        nextId = id + 1;

    }

    /**
     * Helper to create message
     * @param id id of message
     * @param sendername sender name
     * @param senderid sender id
     * @param getterid getter id
     * @param txt message context
     */
    private void createHelper(int id, String sendername, int senderid, int getterid, String txt) {
        Message b = new Message(sendername, id, senderid, getterid, txt);
        allmessage.add(b);
    }

    /**
     * Get all the messages
     * @return a arraylist of message
     */

    public ArrayList<Message> getAllmessage(){return allmessage;}

    /**
     * Get the Message given the message ID.
     * @param messageid The ID of the message.
     * @return the Message.
     */
    public Message getmessage(int messageid){
        return getAllmessage().get(messageid);
    }

    /**
     * Get the text of the message with the given the message ID.
     * @param messageid The ID of the message.
     * @return the Message text.
     */
    public String getString(int messageid){
        return getAllmessage().get(messageid).getTxt();
    }


    /**
     * Set the reply to the Message given the message ID. Return the new reply's ID.
     * @param messageid the ID of the message.
     * @param reply a string representation of the reply.
     * @param replyer the replyer(getter) of the new reply.
     */
    public int setreply(int messageid, String reply, String replyer){
        int receiverId = allmessage.get(messageid).getSenderid();
        int senderId = allmessage.get(messageid).getGetterid();
        int replyId = createmessage(allmessage.get(messageid).getReplyer(), senderId, receiverId, reply);
        Message msg = getmessage(messageid);
        msg.setReply(replyId);
        try{
            this.gateWay.updateReplyId(messageid, replyId);
        }catch (IOException ignored){}
        msg.setReplyer(replyer);
        try{
            this.gateWay.updateReplier(messageid, replyer);
        }catch (IOException ignored){}
        return replyId;
        }

    /**
     * Return a string showing if the message of the Arraylist has been replied.
     * @param msgget An arraylist of message ID.
     * @return a string showing the replies to the given message IDs.
     */
    public String formatreply(ArrayList<Integer> msgget){
        StringBuilder a = new StringBuilder("These are the replies:\n");
        for(Integer i: msgget){
            Message msg = getmessage(i);
            if(msg.getReply() == -1){
                a.append("The message(id:").append(msg.getmessageid()).append(") you send to ").append(getmessage(i).getGetterid()).append(":\n" + msg.getTxt() + "\n").append(" Has Not been Replied.\n");}
            else{
                a.append("This reply is from id ").append(getmessage(i).getGetterid()).append("(").append(msg.getReplyer()).append(")").append(":\n").append(allmessage.get(getmessage(i).getReply()).getTxt());}
        }
        return a.toString();
    }

    /**
     * Return a string showing the messages in the given arraylist of message IDs and their senders.
     * @param inbox An arraylist of message ID.
     * @return a string showing the messages and according senders in the inbox.
     */
    public String formatmsgget(ArrayList<Integer> inbox){
        String a = "These are the messages:\n";
        for(Integer i: inbox){
            a += "\n-------------------------\n";
            a += "The id of this message is " + +getmessage(i).getmessageid()+
                    "\nThis message is from " + getmessage(i).getSendername() + " whose id is " +
                    getmessage(i).getSenderid() + ":\n" + getmessage(i).getTxt();
        }
        return a;
    }

    /**
     * Return a string showing the message information for the given id
     * @param i id of message
     * @return a string showing the message context
     */
    public String formatmsg(int i){
        String a = "Here is the messages:\n";
        a += "\n-------------------------\n";
        a += "The id of this message is " + +getmessage(i).getmessageid()+
                "\nThis message is from " + getmessage(i).getSendername() + " whose id is " +
                getmessage(i).getSenderid() + ":\n" + getmessage(i).getTxt();
        return a;
    }

    /**
     * Return a string showing the unread message information for the given id
     * @param unread id of unread message
     * @return a string showing the unread message context
     */
    public String formatAllUnread(ArrayList<Integer> unread){
        StringBuilder a = new StringBuilder("These are the messages:\n");
        for(Integer i: unread){
            a.append("\n-------------------------\n");
            Message msg = getmessage(i);
            a.append("The id of this message is ").append(msg.getmessageid()).append("\nThis message is from").append(msg.getSendername()).append(" whose id is ").append(msg.getSenderid());
        }
        return a.toString();
    }


    /**
     * Check whether the message has bean replied
     * @param messageID message id
     * @return boolean true iff replied
     */
    public boolean hasReply(int messageID){
        Message message = allmessage.get(messageID);
        return (message.getReply() != -1);
    }

    /**
     * Get the reply id of the message
     * @param messageID message id
     * @return int the reply id of the message
     */
    public int replyID(int messageID){
        Message message = allmessage.get(messageID);
        return message.getReply();
    }

    /**
     * Get the receiver of the message
     * @param messageID message id
     * @return int the receiver's id
     */
    public int getReceiverID(int messageID){
        Message message = allmessage.get(messageID);
        return message.getGetterid();
    }

    /**
     * Get the name of the replier with message id
      * @param messageID message id
     * @return String of the name of the replier
     */
    public String getReplier(int messageID){
        Message message = allmessage.get(messageID);
        return message.getReplyer();
    }

    /**
     * Get the total of number of messages in the message list
     * @return An integer of the number of the messages
     */
    public int getTotalNumOfMsg(){ return allmessage.size();}

    /**
     * Set the information for the message
     * @param msgId message id
     * @param replyId reply id
     * @param replier replier
     */
    public void setMsgInfo(int msgId, int replyId, String replier){
        Message msg = getmessage(msgId);
        msg.setReply(replyId);
        msg.setReplyer(replier);
    }

    /**
     * Return the sender id of the message with the given id
     * @param messageID id of message
     * @return the id of the sender
     */
    public int getSender(int messageID){
        Message msg = getmessage(messageID);
        return msg.getSenderid();
    }
}



