package UseCase;
import Entity.*;

/**
 * The OrganizerManager class implements all functionalities of an organizer.
 */
public class OrganizerManager extends AccountManager{

    public OrganizerManager(){
        super();
    }


    /**
     * Create an Organizer account.
     * @param username The username of the new account.
     * @param password The password of the new account.
     */
    public void createAccount(String username, String password){
        Organizer newAcc = new Organizer(username, password, 0);
        accountList.put(TotalNumOfAccount, newAcc);
        TotalNumOfAccount += 1;
    }

    /**
     * Check if the current login account can message a given account.
     * @param otherId ID of another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(int otherId){
        Account other = getAccountWithId(otherId);
        return other.getUserType() != 0;
    }


}
