package Controller;

import Presenters.SpeakerP;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;

public class SpeakerSystemHandler {
    protected MessageManager MsgM;
    protected SpeakerP speakerP;
    protected SpeakerManager SpeakerM;
    protected AccountManager accM;
    protected EventManager eventManager;
    protected StrategyManager strategyM;
    protected RoomManager roomM;
    protected RequestManager reM;

    public SpeakerSystemHandler(AccountManager accM, EventManager eventM, MessageManager MsgM,
                                SpeakerP speakerP, StrategyManager strategyM, SpeakerManager SpeakerM,
                                RoomManager roomM, RequestManager reM) {
        this.MsgM = MsgM;
        this.speakerP = speakerP;
        this.SpeakerM = SpeakerM;
        this.accM = accM;
        this.eventManager = eventM;
        this.reM = reM;
        this.roomM = roomM;
        this.strategyM = strategyM;

    }

    //////////////////CHOOSING METHOD//////////////
    public int chooseMode3(){    // For MessageAll confirmation.
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(0, 1));
        do{
            userInput = speakerP.confirmMsgAll();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    ////////////////////EVENT METHODS/////////////////////////

    public void readalltalks(){
        StringBuilder a = new StringBuilder("Event Information:");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.getEventinfoWithName(t, roomName));}
        speakerP.show(a.toString());
    }

    public void readalltalkssimp(){
        StringBuilder a = new StringBuilder("Event Information with id:");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            a.append(eventManager.getEventinfosimp(t));}
        speakerP.show(a.toString());
    }

    public int targettalks(){
        ArrayList<Integer> validChoices = SpeakerM.getalltalk();
        validChoices.add(-1);
        boolean valid = false;
        String userInput;
        do{
            userInput = speakerP.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerP.informinvalidchoice();
            else { valid = true; }
        } while(!valid);
        return Integer.parseInt(userInput);
    }

    public void requestfortalk(String a, int b) {
        if (b != -1) {reM.createRequest(a, accM.getCurrAccountId(), b);}
        speakerP.stoprequest();
    }

    public String enterTxt(){
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        speakerP.informEnteringText();
        do{
            String line = speakerP.getLineTxt();
            if (line.equals("end")) exit = true;
            else{
                a.append(line);
                a.append("\n");
            }
        } while(!exit);
        return a.toString();
    }

    //////////////////////MESSAGING SYSTEM/////////////////////////


    public void readallatt(){
        ArrayList<Integer> att = getallattendeev1();
        StringBuilder a = new StringBuilder("These are the attendees who attend your talk. Choose an id to message:");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));

        }
        speakerP.show(a.toString());
    }

    public int targetgetter(){
        ArrayList<Integer> validChoices = getallattendeev1();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private ArrayList<Integer> getallattendeev1() {
        ArrayList<Integer> talklist = SpeakerM.getalltalk();
        return eventManager.getallattendee(talklist);
    }

    public void messagetoatt(String a, int getterid) {

        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), getterid, a);
        accM.addMsgToInBox(getterid, msg);
        accM.addMsgToSentBox(accM.getCurrAccountId(), msg);
        speakerP.messagesend();
    }

    public void messagetotalk(String a, int b) {
        if (b != -1) {ArrayList<Integer> att = eventManager.getEventWithId(b).getAttendeeId();
            msgToList(a, att);}
        speakerP.stopmessaging();
    }

    private void msgToList(String a, ArrayList<Integer> att){
        if (att.isEmpty()) speakerP.noattendees();
        else{
            for (int getterid : att) {
                messagetoatt(a, getterid);
            }
            speakerP.messagesend();
        }
    }

    public void messageall(String a) {
        ArrayList<Integer> att = getallattendeev1();
        msgToList(a, att);
    }

    public void readmsgandrep(){
        readallmsg();
        speakerP.announcereply();
    }

    public void readrepandmsg(){
        readallreply();
        speakerP.announcemsg();

    }

    private void readallreply(){
        String a = MsgM.formatreply(SpeakerM.getmsgsend());
        speakerP.show(a);
    }

    private void readallmsg(){
        String a = MsgM.formatmsgget(SpeakerM.getinbox());
        speakerP.show(a);
    }

    public int targetmsg(){
        ArrayList<Integer> validChoices = SpeakerM.getinbox();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }



    /////////////////UNREAD HELPER////////////////
    protected void askToAchieve(int msgId){
        int userInput = speakerP.chooseOption(speakerP.getchoicelist(1), "Would you like to:" +
                "\n1 -> Mark as Unread" +
                "\n2 -> Move to Archive" +
                "\n3 -> Delete Message", "Invalid Chooice, Please Try Again:");
        if(userInput == 1){
            speakerP.annouceMarkUnread();
            SpeakerM.addMsgToUnreadInbox(accM.getCurrAccountId(), msgId);
        } else if(userInput == 2){
            SpeakerM.archiveMsg(msgId, accM.getCurrAccountId());
            speakerP.archiveMsg();
        }else if(userInput == 3){
            SpeakerM.deleteMsg(msgId, accM.getCurrAccountId());
            speakerP.deleteMsg();
        }
        speakerP.askForBack();}

    public void readAllUnreadMsg(){
        readAllUnread();
        speakerP.annouceUnread();
    }

    private void readAllUnread(){

        String all = MsgM.formatmsgget(accM.getUnreadInboxWithId(accM.getCurrAccountId()));
        speakerP.show(all);
    }

    public int targetunread(){
        ArrayList<Integer> validChoices = accM.getUnreadInboxWithId(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public void readAllarchivedMsg(){
        readallarchived();
        speakerP.annouceUnread();

    }

    private void readallarchived(){

        String all = MsgM.formatmsgget(accM.getarchivedboxWithId(accM.getCurrAccountId()));
        speakerP.show(all);
    }

    public int targetarchived(){
        ArrayList<Integer> validChoices = accM.getarchivedboxWithId(accM.getCurrAccountId());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerP.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerP.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }


}