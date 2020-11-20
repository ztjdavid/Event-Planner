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

    public int createmessage(String sendername, int senderid, int getterid, String txt) {


        int a = allmessage.size();
        Message b = new Message(sendername, a, senderid, getterid, txt);
        allmessage.add(b);

        return a;

    }

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
     * Set the reply to the Message given the message ID.
     * @param messageid the ID of the message.
     * @param reply a string representation of the reply.
     */
    public void setreply(int messageid, String reply){getmessage(messageid).response(reply);}

    /**
     * Return a string showing if the message of the Arraylist has been replied.
     * @param msgget An arraylist of message ID.
     * @return a string showing the replies to the given message IDs.
     */
    public String formatreply(ArrayList<Integer> msgget){
        String a = "These are the replies:";
        for(Integer i: msgget){
            if(getmessage(i).getReply().isEmpty()){a += "The message you send to " + getmessage(i).getGetterid() +
                    " has not been replied";}
            else{a += "This reply is from id " + getmessage(i).getGetterid() + "\n" + getmessage(i).getReply();}
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
                    "\nThis message is from " + getmessage(i).getSendername() + " whose id is " +
                    getmessage(i).getSenderid() + ":\n" + getmessage(i).getTxt();
        }
        return a;
    }




}



