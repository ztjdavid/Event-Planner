package Controller;

import Presenters.VipUI;
import UseCase.*;

import java.util.ArrayList;

public class VIPsystemhandler {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected VipUI vipUI;
    protected StrategyManager strategyM;
    protected VIPManager vipM;
    protected RoomManager roomM;
    protected RequestManager ReqM;

    public VIPsystemhandler(AccountManager accM, EventManager TalkM, MessageManager MsgM, VipUI vipUI,
                     StrategyManager StrategyManager, VIPManager vipM, RoomManager roomM, RequestManager ReqM) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.vipUI = vipUI;
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
        vipUI.show(a.toString());}

    public void readAllMyTalksSimp(){
        StringBuilder a = new StringBuilder("Event you have signed up: ");
        ArrayList<Integer> allEvent = vipM.getAllEvent();
        for(Integer t:allEvent){
            a.append(eventManager.getEventinfosimp(t));
        }
        vipUI.show(a.toString());
    }

    public int targetevent(){
        ArrayList<Integer> validChoices = vipM.getAllEvent();
        validChoices.add(-1);
        boolean valid = false;
        String userInput;
        do{
            userInput = vipUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        } while(!valid);
        return Integer.parseInt(userInput);
    }

    public void requestForTalk(String service, int talkId){
        if(talkId != -1){
            ReqM.createRequest(service, accM.getCurrAccountId(), talkId);
        }
        vipUI.stoprequest();
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
            userInput = vipUI.getrequest(3);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
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
        vipUI.show(a.toString());
    }

    public int targetTalksCancel(){
        ArrayList<Integer> validChoices = vipM.getEventList(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipUI.getrequest(4);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
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
        vipUI.show(a.toString());
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
        vipUI.show(a.toString());
    }


    public int targetgetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.addAll(getAllSpeakers());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
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
        vipUI.informEnteringText();
        do{
            String line = vipUI.getLineTxt();
            if (line.equals("end")) exit = true;
            else{
                a.append(line);
                a.append("\n");
            }
        } while(!exit);
        return a.toString();
    }

    private ArrayList<Integer> getAllUnread() {
        ArrayList<Integer> inbox = vipM.getInbox(accM.getCurrAccountId());
        ArrayList<Integer> unread = new ArrayList<>();
        for (Integer i : inbox) {
            boolean is_Read = MsgM.checkMessageStatus(i);
            if (!is_Read) {
                unread.add(i);
                accM.addUnread(vipM.getCurrAccountId(), i);
            }
        }

        return unread;
    }

    public void readAllUnreadMsg(){
        readAllUnread();
        vipUI.annouceUnread();

    }

    private void readAllUnread(){

        String all = MsgM.formatAllUnread(getAllUnread());
        vipUI.show(all);
    }

    public int targetunread(){
        ArrayList<Integer> validChoices = vipM.getUnreadInbox();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }


    protected void askToAchieve(int msgId){
        int userInput = vipUI.chooseOption(vipUI.getchoicelist(1), "Would you like to:" +
                "\n1 -> Mark as Unread" +
                "\n2 -> Move to Archive" +
                "\n3 -> Delete Message", "Invalid Chooice, Please Try Again:");
        if(userInput == 1){
            vipUI.annouceMarkUnread();
        } else if(userInput == 2){
            vipM.removeUnreadMsg(msgId);
            vipM.archiveMessage(msgId);
            vipUI.archiveMsg();
        }else if(userInput == 3){
            vipM.removeUnreadMsg(msgId);
            vipUI.deleteMsg();
        }
        vipUI.askForBack();}

    ////////////////////MODIFY LATER///////////
    public int targetmsg(){
        ArrayList<Integer> validChoices = vipM.getInbox(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

}
