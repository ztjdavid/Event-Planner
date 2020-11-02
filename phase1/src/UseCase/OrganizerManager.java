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
     * @return True iff the new account is successfully created.
     */
    public boolean createAccount(String username, String password){
        for (Account x: this.accountList.values()){
            if(x.getUsername().equals(username)) return false;
        }
        Organizer newAcc = new Organizer(username, password, 0);
        this.accountList.put(TotalNumOfAccount, newAcc);
        TotalNumOfAccount += 1;
        return true;
    }

    /**
     * Check if the current login account can message a given account.
     * @param other Another account that the current login account is going to message.
     * @return True iff the current login account can message the given account.
     */
    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0;
    }

}
