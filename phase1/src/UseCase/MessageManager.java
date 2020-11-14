package UseCase;
import Entity.*;

import java.util.ArrayList;

public class MessageManager {
    static ArrayList<Message> allmessage;

    public int createmessage(int senderid, int getterid, String txt) {
        int a = allmessage.size() + 1;
        Message b = new Message(a, senderid, getterid, txt);
        allmessage.add(b);

        return a;

    }

    public void setreply(Message a, String reply){a.response(reply);}


    }



