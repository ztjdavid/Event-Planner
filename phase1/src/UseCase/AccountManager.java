package UseCase;
import Entity.*;

import java.util.HashMap;

/**
 * The AccountManager class is a class with basic functionalities implemented.
 */
public abstract class AccountManager {
    protected static int currAccountId = -1; // Track which account this program is working on now.

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
    }

    /**
     * Show messages between the current login account and a given account.
     * @param otherId ID of another account.
     */
    public void showMsgWith(int otherId){
        //TODO: print all Messages between this account and <other>.
        // (through presenter use case?)
    }


}
