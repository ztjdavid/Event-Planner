package UseCase;

import Entity.*;

import java.util.ArrayList;

public class VIPManager extends AccountManager{
    public VIPManager(){ super();}

    public VIP getCurrVIP() throws ClassCastException{
        return (VIP)getCurrAccount();
    }

    public VIP getVIPWithId(int id) throws ClassCastException{
        return (VIP)getAccountWithId(id);
    }

    public void changeUsername(int id, String username){
        getVIPWithId(id).setUsername(username);
    }

    public void changePassword(int id, String password){
        getVIPWithId(id).setPassword(password);
    }

    public void enrolEvent(int userId, int eventId){
        getVIPWithId(userId).addEvent(eventId);
    }

    public void dropEvent(int userId, int eventId){
        getVIPWithId(userId).cancelEvent(eventId);
    }

    public ArrayList<Integer> getEventList(int id){
        return getVIPWithId(id).getEventList();
    }

    public ArrayList<Integer> getInbox(int id){
        return getVIPWithId(id).getInbox();
    }

    public ArrayList<Integer> getSentBox(int id){
        return getVIPWithId(id).getSentMessage();
    }

    public ArrayList<Integer> getUnreadInbox(){return getCurrAccount().getUnreadInbox();}


    public void deleteUnreadInbox(int msgid){
        ArrayList<Integer> inbox = getCurrAccount().getUnreadInbox();
        inbox.remove(Integer.valueOf(msgid));

    }


    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0 && other.getUserId() != getCurrVIP().getUserId();
    }


}
