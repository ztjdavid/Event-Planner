package Entity;

import java.util.ArrayList;

public class VIP extends Attendee{
    public VIP(String username, String password, int userId){
        super(username, password, userId);
    }

    @Override
    public int getUserType(){ return 3; }

}