package UseCase;
import Entity.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The AccountManager class is a class with basic functionalities implemented.
 */
public abstract class AccountManager {
    protected static int currAccountId = -1; // Track which account this program is working on now.
//    private ArrayList<Account> accounts;
//    private Account currAccount;

    protected LoginManager LoginM = new LoginManager();
    protected Account currAccount = LoginM.getCurrAccount();

    public AccountManager(){}

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    public abstract boolean messageable(Account other);


    /**
     * Show all messages that the current login account has received.
     */
    public void showAllReceivedMsg(){
        //TODO: print all Messages of this Account.(through presenter use case?)

        System.out.println(currAccount.getInbox());
    }

    /**
     * Show messages between the current login account and a given account.
     * @param otherId ID of another account.
     */
    public void showMsgWith(int otherId){
        //TODO: print all Messages between this account and <other>.
        // (through presenter use case?)
        ArrayList<Message> result = new ArrayList();
        ArrayList sentMessages = currAccount.getSentMessage();
        ArrayList getMessages = currAccount.getInbox();
        for (int i = 0; i < sentMessages.size(); i++){
            Message message = (Message) sentMessages.get(i);
            if (message.getGetterid() == otherId){result.add(message);}
        }
        for (int j = 0; j < getMessages.size(); j++){
            Message message = (Message) sentMessages.get(j);
            if (message.getSenderid() == otherId){result.add(message);}
    }
        if(result.size() == 0){return;}
        else{System.out.println(result); }
    }





}
