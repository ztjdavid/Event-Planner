package UseCase;
import Entity.*;

import java.util.ArrayList;
import Entity.Account;

public class MessageManager {
    static ArrayList<Message> allmessage;

    public MessageManager() {
        allmessage = new ArrayList<>();
    }

    public int createmessage(int senderid, int getterid, String txt) {
        int a = allmessage.size() + 1;
        Message b = new Message(a, senderid, getterid, txt);
        allmessage.add(b);

        return a;

    }

    public Message getmessage(int messageid){
        return allmessage.get(messageid);
    }

    public void setreply(int messageid, String reply){getmessage(messageid).response(reply);}

    public String formatreply(ArrayList<Integer> msgget){
        String a = "These are the replies:";
        for(Integer i: msgget){
            if(getmessage(i).getReply().isEmpty()){a += "The message you send to " + getmessage(i).getGetterid() + " has not been replied";}
            else{a += "This reply is from " + getmessage(i).getGetterid() + "\n" + getmessage(i).getReply();}
        }
        return a;
    }

    public String formatmsgget(ArrayList<Integer> inbox){
        String a = "These are the messages";
        for(Integer i: inbox){
            a += "The id of this message is "+getmessage(i).getmessageid()+"This message is from " + getmessage(i).getSenderid() + "\n" + getmessage(i).getTxt();
        }
        return a;
    }




}



