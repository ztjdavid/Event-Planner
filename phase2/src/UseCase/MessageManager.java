package UseCase;
import Entity.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageManager {
    static ArrayList<Message> allmessage;

    public MessageManager() {
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


        int a = allmessage.size();
        Message b = new Message(sendername, a, senderid, getterid, txt);
        allmessage.add(b);

        return a;

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

    public String getString(int messageid){
        return getAllmessage().get(messageid).getTxt();
    }


    ///// Louisa modified
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
        msg.setReplyer(replyer);
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
                a.append("The message(id:").append(msg.getmessageid()).append(") you send to ").append(getmessage(i).getGetterid()).append(" has not been replied.\n");}
            else{
                a.append("This reply is from id ").append(getmessage(i).getGetterid()).append("(").append(msg.getReplyer()).append(")").append(":\n").append(allmessage.get(getmessage(i).getReply()).getTxt());}
        }
        return a.toString();
    }
    /////

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

    ///// Louisa added
    public boolean checkMessageStatus(int messageId){return getmessage(messageId).getReadStatus();}

    public void readMessage(int messageId){getmessage(messageId).setReadStatusRead();}

    public void unreadMessage(int messageId){getmessage(messageId).setReadStatusUnread();}

    public String formatAllUnread(ArrayList<Integer> unread){
        StringBuilder a = new StringBuilder("These are the unread messages:\n");
        for(Integer i: unread){
            a.append("\n-------------------------\n");
            Message msg = getmessage(i);
            a.append("The id of this message is ").append(msg.getmessageid()).append("\nThis message is from").append(msg.getSendername()).append(" whose id is ").append(msg.getSenderid());
        }
        return a.toString();
    }

    /////

    public boolean hasReply(int messageID){
        Message message = allmessage.get(messageID);
        return (message.getReply() != -1);
    }

    public int replyID(int messageID){
        Message message = allmessage.get(messageID);
        return message.getReply();
    }

    public int getReceiverID(int messageID){
        Message message = allmessage.get(messageID);
        return message.getGetterid();
    }

    public String getReplier(int messageID){
        Message message = allmessage.get(messageID);
        return message.getReplyer();
    }

}



