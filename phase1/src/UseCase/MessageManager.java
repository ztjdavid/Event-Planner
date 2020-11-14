package UseCase;
import Entity.*;

import java.util.ArrayList;
import Entity.Account;

public class MessageManager {
    static ArrayList<Message> allmessage;

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

    public String formatreply(ArrayList<Integer> inbox){
        String a = "These are the replies";
        for(Integer i: inbox){
            a += "This reply is from " + getmessage(i).getGetterid() + "\n" + getmessage(i).getReply();
        }
        return a;
    }


    }



