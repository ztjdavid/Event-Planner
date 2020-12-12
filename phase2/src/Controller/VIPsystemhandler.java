package Controller;

import Presenters.VipP;
import UseCase.*;

import java.util.ArrayList;

public class VIPsystemhandler {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected VipP vipP;
    protected StrategyManager strategyM;
    protected VIPManager vipM;
    protected RoomManager roomM;
    protected RequestManager ReqM;

    public VIPsystemhandler(AccountManager accM, EventManager TalkM, MessageManager MsgM, VipP vipP,
                     StrategyManager StrategyManager, VIPManager vipM, RoomManager roomM, RequestManager ReqM) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.vipP = vipP;
        this.strategyM = StrategyManager;
        this.vipM = vipM;
        this.roomM = roomM;
        this.ReqM = ReqM;

    }


    ///////////////HELPER FOR SIGN UP EVENT////////////////
    public void readAllAvailableTalks(int type){
        StringBuilder a = new StringBuilder("Available Event: ");
        ArrayList<Integer> availableTalksId = getAllAvailableTalks(type);
        for(Integer t:availableTalksId){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.getEventinfoWithName(t, roomName));
        }
        vipP.show(a.toString());}

    public void readAllMyTalksSimp(){
        StringBuilder a = new StringBuilder("Event you have signed up: ");
        ArrayList<Integer> allEvent = vipM.getAllEvent();
        for(Integer t:allEvent){
            a.append(eventManager.getEventinfosimp(t));
        }
        vipP.show(a.toString());

    }

    public int targetevent(){
        ArrayList<Integer> validChoices = vipM.getAllEvent();
        validChoices.add(-1);
        boolean valid = false;
        String userInput;
        do{
            userInput = vipP.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else { valid = true; }
        } while(!valid);
        return Integer.parseInt(userInput);
    }

    public void requestForTalk(String service, int talkId){
        if(talkId != -1){
            ReqM.createRequest(service, accM.getCurrAccountId(), talkId);
        }
        vipP.stoprequest();
    }

    /**
     * Get all available events that this account can attend.
     * @return A tlkList containing all events this account can attend.
     */
    private ArrayList<Integer> getAllAvailableTalks(int a){
        ArrayList<Integer> myTalksId = vipM.getEventList(accM.getCurrAccountId());
        ArrayList<Integer> allTalksId = eventManager.getListOfEventsByType(a);
        ArrayList<Integer> result = new ArrayList<>();
        for(Integer t:allTalksId){
            if (!myTalksId.contains(t) && (eventManager.getRemainingSeats() > 0 )) result.add(t);
            // Boolean is_VIP = false;
            // if (accM.isVIPAcc(accM.getCurrAccountId())) is_VIP = true;
            // TODO: add a checker for VIP event, waiting for event flag var update.
            // if event t is vip, remove t from result if cur_acc is not a VIP.
        }
        return result;
    }

    public int targetTalksSignUp(int type){
        ArrayList<Integer> validChoices = getAllAvailableTalks(type);
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipP.getrequest(3);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

        //////////////CANCEL TALK////////////////

    public void readAllMyTalks(){
        StringBuilder a = new StringBuilder("My signed up talks:");
        ArrayList<Integer> allTalks = vipM.getEventList(accM.getCurrAccountId());
        for(Integer t:allTalks){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.getEventinfoWithName(t, roomName));}
        vipP.show(a.toString());

    }

    public int targetTalksCancel(){
        ArrayList<Integer> validChoices = vipM.getEventList(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipP.getrequest(4);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    ///////////////MESSAGING SYSTEM////////////////

    public void readAllAttendees(){
        ArrayList<Integer> att = getAllAttendees();
        StringBuilder a = new StringBuilder("These are the attendees who attend your signed up talks. Choose an id to message:\n");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        vipP.show(a.toString());
    }

    public void readAllSpeakers(){
        ArrayList<Integer> allTalks = vipM.getEventList(accM.getCurrAccountId());
        StringBuilder a = new StringBuilder("These are the speakers in talks you attend. Choose an id to message:\n");
        for (Integer t: allTalks){
            ArrayList<Integer> spkLst = new ArrayList<>(eventManager.getSpeakerIDIn(t));
            for(int speaker:spkLst){
                String each = "(" + eventManager.getTitle(t) + ")" + accM.getinfoacc(speaker);
                a.append(each);
            }
        }
        vipP.show(a.toString());
    }


    public int targetgetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.addAll(getAllSpeakers());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }


    private ArrayList<Integer> getAllAttendees() {
        ArrayList<Integer> talkList = vipM.getEventList(accM.getCurrAccountId());
        ArrayList<Integer> result = eventManager.getallattendee(talkList);
        int currAcc = accM.getCurrAccountId();
        if (result.contains(currAcc)) result.remove(Integer.valueOf(currAcc));
        return result;


    }

    private ArrayList<Integer> getAllSpeakers() {
        ArrayList<Integer> talkList = vipM.getEventList(accM.getCurrAccountId());
        return eventManager.getAllSpeakers(talkList);
    }

    public String enterTxt(){
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        vipP.informEnteringText();
        do{
            String line = vipP.getLineTxt();
            if (line.equals("end")) exit = true;
            else{
                a.append(line);
                a.append("\n");
            }
        } while(!exit);
        return a.toString();
    }


    public void readAllUnreadMsg(){
        readAllUnread();
        vipP.annouceUnread();
    }

    private void readAllUnread(){

        String all = MsgM.formatmsgget(accM.getUnreadInboxWithId(accM.getCurrAccountId()));
        vipP.show(all);
    }

    public int targetunread(){
        ArrayList<Integer> validChoices = accM.getUnreadInboxWithId(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public void readAllarchivedMsg(){
        readallarchived();
        vipP.annouceUnread();

    }

    private void readallarchived(){

        String all = MsgM.formatmsgget(accM.getarchivedboxWithId(accM.getCurrAccountId()));
        vipP.show(all);
    }

    public int targetarchived(){
        ArrayList<Integer> validChoices = accM.getarchivedboxWithId(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }


    protected void askToAchieve(int msgId){
        int userInput = vipP.chooseOption(vipP.getchoicelist(1), "Would you like to:" +
                "\n1 -> Mark as Unread" +
                "\n2 -> Move to Archive" +
                "\n3 -> Delete Message", "Invalid Chooice, Please Try Again:");
        if(userInput == 1){
            vipP.annouceMarkUnread();
            vipM.addMsgToUnreadInbox(accM.getCurrAccountId(), msgId);
        } else if(userInput == 2){
            vipM.archiveMsg(msgId, accM.getCurrAccountId());
            vipP.archiveMsg();
        }else if(userInput == 3){
            vipM.deleteMsg(msgId, accM.getCurrAccountId());
            vipP.deleteMsg();
        }
        vipP.askForBack();}

    ////////////////////MODIFY LATER///////////
    public int targetmsg(){
        ArrayList<Integer> validChoices = vipM.getInbox(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

}
