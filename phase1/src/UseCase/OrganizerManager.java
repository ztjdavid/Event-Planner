package UseCase;
import Entity.*;
import com.sun.org.apache.xpath.internal.operations.Or;

public class OrganizerManager extends AccountManager{
    public OrganizerManager(){
        super();
    }

    public boolean createAccount(String username, String password){
        for (Account x: this.accountList.values()){
            if(x.getUsername().equals(username)) return false;
        }
        Organizer newAcc = new Organizer(username, password, 0);
        this.accountList.put(TotalNumOfAccount, newAcc);
        TotalNumOfAccount += 1;
        return true;
    }

    @Override
    public boolean messageable(Account o){
        return o.getUserType() != 0;
    }

}
