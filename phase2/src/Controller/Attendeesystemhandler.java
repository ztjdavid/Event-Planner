package Controller;

import Presenters.AttendeeP;
import UseCase.*;

import java.util.ArrayList;

public class Attendeesystemhandler {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected AttendeeP attUI;
    protected StrategyManager strategyM;
    protected AttendeeManager attM;
    protected RoomManager roomM;
    protected RequestManager reM;
    protected MessageManager msgM;

    /**
     * Helper of the Attendee System
     */
    public Attendeesystemhandler(MessageManager msgM, AccountManager accM, EventManager TalkM, MessageManager MsgM, AttendeeP attUI,
                            StrategyManager StrategyManager, AttendeeManager attM, RoomManager roomM, RequestManager reM) {
        this.msgM = msgM;
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.attUI = attUI;
        this.strategyM = StrategyManager;
        this.attM = attM;
        this.roomM = roomM;
        this.reM = reM;

    }


    ///////////////HELPER FOR SIGN UP EVENT////////////////
    public void readAllAvailableTalks(int type){
        StringBuilder a = new StringBuilder("Available Event: ");
        ArrayList<Integer> availableTalksId = getAllAvailableTalks(type);
        for(Integer t:availableTalksId){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.getEventinfoWithName(t, roomName));
        }
        attUI.show(a.toString());}


    public void readalltalkssimp(){
        ArrayList<Integer> alltalks = attM.getAllMyTalksId();
        StringBuilder a = new StringBuilder("Event Information with id:");

        for(Integer t:alltalks){
            a.append(eventManager.getEventinfosimp(t));}
        attUI.show(a.toString());

    }

    public int targettalks(){
        ArrayList<Integer> validChoices = attM.getAllMyTalksId();
        validChoices.add(-1);
        boolean valid = false;
        String userInput;
        do{
            userInput = attUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else { valid = true; }
        } while(!valid);
        return Integer.parseInt(userInput);
    }

    /**
     * Get all available events that this account can attend.
     * @return A tlkList containing all events this account can attend.
     */
    private ArrayList<Integer> getAllAvailableTalks(int a) {
        boolean is_VIP = false;
        if (accM.isVIPAcc(accM.getCurrAccountId())) is_VIP = true;
        ArrayList<Integer> myTalksId = attM.getAllMyTalksId();
        ArrayList<Integer> allTalksId = eventManager.getListOfEventsByType(a);
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer t : allTalksId) {
            if (!myTalksId.contains(t) && (eventManager.getRemainingSeats() > 0)) result.add(t);

        }

        if (!is_VIP) {
            result.removeIf(i -> eventManager.checkVIP(i));
        }return result;
    }

    public int targetTalksSignUp(int type){
        ArrayList<Integer> validChoices = getAllAvailableTalks(type);
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attUI.getrequest(3);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    public void requestfortalk(String a, int b) {
        if (b != -1) {reM.createRequest(a, accM.getCurrAccountId(), b);}
        attUI.stoprequest();
    }

    //////////////CANCEL TALK////////////////

    public void readAllMyTalks(){
        StringBuilder a = new StringBuilder("My signed up events:");
        ArrayList<Integer> allTalks = attM.getAllMyTalksId();
        for(Integer t:allTalks){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.getEventinfoWithName(t, roomName));}
        attUI.show(a.toString());

    }

    public int targetTalksCancel(){
        ArrayList<Integer> validChoices = attM.getAllMyTalksId();
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attUI.getrequest(4);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    ///////////////MESSAGING SYSTEM////////////////

    public void readAllAttendees(){
        ArrayList<Integer> att = getAllAttendees();
        StringBuilder a = new StringBuilder("These are the attendees who attend your signed up events. Choose an id to message:\n");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        attUI.show(a.toString());
    }

    public void readAllSpeakers(){
        ArrayList<Integer> allTalks = attM.getAllMyTalksId();
        StringBuilder a = new StringBuilder("These are the speakers in events you attend. Choose an id to message:\n");
        for (Integer t: allTalks){
            ArrayList<Integer> spkLst = new ArrayList<>(eventManager.getSpeakerIDIn(t));
            for(int speaker:spkLst){
                String each = "(" + eventManager.getTitle(t) + ")" + accM.getinfoacc(speaker);
                a.append(each);
            }
        }
        attUI.show(a.toString());
    }


    public int targetgetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.addAll(getAllSpeakers());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }


    private ArrayList<Integer> getAllAttendees() {
        ArrayList<Integer> talkList = attM.getAllMyTalksId();
        ArrayList<Integer> result = eventManager.getallattendee(talkList);
        int currAcc = accM.getCurrAccountId();
        if (result.contains(currAcc)) result.remove(Integer.valueOf(currAcc));
        return result;


    }

    private ArrayList<Integer> getAllSpeakers() {
        ArrayList<Integer> talkList = attM.getAllMyTalksId();
        return eventManager.getAllSpeakers(talkList);
    }


    public void messageToSp(String a, int speakerId) {
        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), speakerId, a);
        accM.addMsgToUnreadInbox(speakerId, msg);
        accM.addMsgToSentBox(accM.getCurrAccountId(), msg);
        attUI.messagesend();
    }

    public void messageToAtt(String a, int getterId) {

        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), getterId, a);
        accM.addMsgToUnreadInbox(getterId, msg);
        accM.addMsgToSentBox(accM.getCurrAccountId(), msg);
        attUI.messagesend();
    }

    ////////////////////MODIFY LATER///////////
    public int targetmsg(){
        ArrayList<Integer> validChoices = attM.getInbox();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public void readAllUnreadMsg(){
        readAllUnread();
        attUI.annouceUnread();
    }

    private void readAllUnread(){

        String all = MsgM.formatmsgget(accM.getUnreadInboxWithId(accM.getCurrAccountId()));
        attUI.show(all);
    }

    public int targetunread(){
        ArrayList<Integer> validChoices = accM.getUnreadInboxWithId(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public void readAllarchivedMsg(){
        readallarchived();
        attUI.annouceUnread();

    }

    private void readallarchived(){

        String all = MsgM.formatmsgget(accM.getarchivedboxWithId(accM.getCurrAccountId()));
        attUI.show(all);
    }

    public int targetarchived(){
        ArrayList<Integer> validChoices = accM.getarchivedboxWithId(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }


    protected ArrayList<Integer> getChoiceList(int size){
        int i = 0;
        int j = 1;
        ArrayList<Integer> choiceList = new ArrayList<>();
        while (i!=size){
            choiceList.add(j);
            j++;
            i++;
        }
        return choiceList;
    }

    protected void askToAchieve(int msgId){
        int userInput = attUI.chooseOption(attUI.getchoicelist(1), "Would you like to:" +
                "\n1 -> Mark as Unread" +
                "\n2 -> Move to Archive" +
                "\n3 -> Delete Message", "Invalid Chooice, Please Try Again:");
        if(userInput == 1){
            attUI.annouceMarkUnread();
            attM.addMsgToUnreadInbox(accM.getCurrAccountId(), msgId);
        } else if(userInput == 2){
            accM.archiveMsg(msgId, accM.getCurrAccountId());
            attUI.archiveMsg();
        }else if(userInput == 3){
            attM.deleteMsg(msgId, accM.getCurrAccountId());
            attUI.deleteMsg();
        }
        attUI.askForBack();}

    protected void readAllMsg() {

        int messageID;
        ArrayList<Integer> inbox = attM.getInbox();

        if (inbox.size() != 0) {
            do {
                String a = MsgM.formatmsgget(attM.getInbox());
                attUI.show(a);
                messageID = targetmsg();
                if (messageID != -1) {
                    attUI.show(MsgM.getString(messageID));
                    askToAchieve(messageID);
                }
            } while (messageID != -1);
        } else {
            attUI.announceEmptyInbox();
            attUI.askForBack();
        }
    }

}