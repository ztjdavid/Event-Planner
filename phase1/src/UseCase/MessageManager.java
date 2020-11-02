import java.util.ArrayList;

public class MessageManager {
    static ArrayList<Message> allmessage;

    public Message createmessage(int senderid, int getterid, String txt) {
        int a = allmessage.size();

        return new Message(a, senderid, getterid, txt);

    }

    public void setreply(Message a, String reply){a.response(reply);}


}
