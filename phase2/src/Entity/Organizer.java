package Entity;

import java.util.ArrayList;

/**
 * The Organizer class stores all info of an organizer account.
 * <p>
 * Level in Clean Architecture: Entity
 */
public class Organizer extends Account{
    public Organizer(String username, String password, int userId){
        super(username, password, userId);
    }

    /**
     * Get the user type of this account.
     * @return 0 indicating this account is an organizer.
     */
    @Override
    public int getUserType() {
        return 0;
    }

    public void setInbox(ArrayList<Integer> inbox){ this.inbox = inbox;}

    public void setSentBox(ArrayList<Integer> sentBox) { this.sentMessage = sentBox;}
}
