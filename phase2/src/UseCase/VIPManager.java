package UseCase;

import Entity.*;
import UseCase.IGateWay.IUserGateWay;

import java.io.IOException;
import java.util.ArrayList;

public class VIPManager extends AccountManager{
    public VIPManager(IUserGateWay g){ super(g);}

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
    //////NEED
    public void enrolEvent(int userId, int eventId){
        getVIPWithId(userId).addEvent(eventId);
        try{
            this.gateWay.updateEventList(getCurrAccountId(), getCurrVIP().getEventList());
        }catch (IOException ignored){}
    }
    //////NEED
    public void dropEvent(int userId, int eventId){
        getVIPWithId(userId).cancelEvent(eventId);
        try{
            this.gateWay.updateEventList(getCurrAccountId(), getCurrVIP().getEventList());
        }catch (IOException ignored){}
    }

    public ArrayList<Integer> getEventList(int id){
        VIP Vip = (VIP) accountList.get(id);
        return new ArrayList<>(Vip.getEventList());
    }

    public ArrayList<Integer> getAllEvent(){ return getCurrVIP().getEventList();}

    public ArrayList<Integer> getInbox(int id){
        return getVIPWithId(id).getInbox();
    }

    public ArrayList<Integer> getSentBox(int id){
        return getVIPWithId(id).getSentMessage();
    }

    public ArrayList<Integer> getUnreadInbox(){return getCurrAccount().getUnreadInbox();}

    ///////NEED
    public void deleteUnreadInbox(int msgid){
        ArrayList<Integer> inbox = getCurrAccount().getUnreadInbox();
        inbox.remove(Integer.valueOf(msgid));
        try{
            this.gateWay.updateInbox(getCurrAccountId(), getCurrVIP().getInbox());
        }catch (IOException ignored){}

    }


    @Override
    public boolean messageable(Account other){
        return other.getUserType() != 0 && other.getUserId() != getCurrVIP().getUserId();
    }

    public void setAccInfo(int id, ArrayList<Integer> inbox, ArrayList<Integer> sentBox,
                           ArrayList<Integer> eventList, ArrayList<Integer> unreadInbox){
        VIP acc = (VIP) getAccountWithId(id);
        acc.setInbox(inbox);
        acc.setSentBox(sentBox);
        acc.setEventList(eventList);
        acc.setUnreadInbox(unreadInbox);
    }


}
