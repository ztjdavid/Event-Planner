package UseCase;
import Entity.*;

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
     * @return the number of messages as int.
     */
    public int createmessage(int senderid, int getterid, String txt) {
        int a = allmessage.size();
        Message b = new Message(a, senderid, getterid, txt);
        allmessage.add(b);

        return a;

    }

    /**
     * Get the Message given the message ID.
     * @param messageid The ID of the message.
     * @return the Message.
     */
    public Message getmessage(int messageid){
        return allmessage.get(messageid);
    }

    /**
     * Return the reply ID to the Message given the message ID.
     * @param messageid the ID of the message.
     * @param reply a string representation of the reply.
     * @return return reply ID
     */
    public int setreply(int messageid, String reply){
        int receiverID = allmessage.get(messageid).getSenderid();
        int senderID = allmessage.get(messageid).getGetterid();
        int replyID = createmessage(senderID, receiverID, reply);
        allmessage.get(messageid).addReply(replyID);
        return replyID;
    }

    /**
     * Return a string showing if the message of the Arraylist has been replied.
     * @param msgget An arraylist of message ID.
     * @return a string showing the replies to the given message IDs.
     */
    public String formatreply(ArrayList<Integer> msgget){
        String a = "These are the replies:" + "\n";
        for(Integer i: msgget){
            if(getmessage(i).getAllReplies().isEmpty()){a += "The message you send to " + getmessage(i).getGetterid() +
                    " has not been replied" + "\n";}
            else{
                for(int item: getmessage(i).getAllReplies()){
                    a += "This reply is from " + getmessage(i).getGetterid() + "\n" + getmessage(item) + "\n";
                }
            }
        }
        return a;
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
                    "\nThis message is from " + getmessage(i).getSenderid() + ":\n" + getmessage(i).getTxt();
        }
        return a;
    }




}



