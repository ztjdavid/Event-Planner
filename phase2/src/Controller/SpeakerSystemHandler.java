package Controller;

import Entity.Message;
import Entity.Speaker;
import Presenters.SpeakerUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;

public class SpeakerSystemHandler {
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected SpeakerManager SpeakerM;
    protected AccountManager accM;
    protected EventManager eventManager;
    protected StrategyManager strategyM;
    protected RoomManager roomM;
    protected RequestManager reM;

    public SpeakerSystemHandler(AccountManager accM, EventManager eventM, MessageManager MsgM,
                                SpeakerUI speakerUI, StrategyManager strategyM, SpeakerManager SpeakerM,
                                RoomManager roomM, RequestManager reM) {
        this.MsgM = MsgM;
        this.speakerUI = speakerUI;
        this.SpeakerM = SpeakerM;
        this.accM = accM;
        this.eventManager = eventM;
        this.reM = reM;
        this.roomM = roomM;
        this.strategyM = strategyM;

    }

    //////////////////CHOOSING METHOD//////////////
    public int chooseMode1(){    //For Speaker Dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public int chooseMode2(){    // For messaging dashboard.
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        do{
            userInput = speakerUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public int chooseMode3(){    // For MessageAll confirmation.
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(0, 1));
        do{
            userInput = speakerUI.confirmMsgAll();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
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
        speakerUI.show(a.toString());
    }

    public void readalltalkssimp(){
        StringBuilder a = new StringBuilder("Event Information with id:");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            a.append(eventManager.getEventinfosimp(t));}
        speakerUI.show(a.toString());
    }

    public int targettalks(){
        ArrayList<Integer> validChoices = SpeakerM.getalltalk();
        validChoices.add(-1);
        boolean valid = false;
        String userInput;
        do{
            userInput = speakerUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        } while(!valid);
        return Integer.parseInt(userInput);
    }

    public void requestfortalk(String a, int b) {
        if (b != -1) {reM.createRequest(a, accM.getCurrAccountId(), b);}
        speakerUI.stoprequest();
    }

    public String enterTxt(){
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        speakerUI.informEnteringText();
        do{
            String line = speakerUI.getLineTxt();
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
        speakerUI.show(a.toString());
    }

    public int targetgetter(){
        ArrayList<Integer> validChoices = getallattendeev1();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
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
        accM.addinbox(getterid, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        speakerUI.messagesend();
    }

    public void messagetotalk(String a, int b) {
        if (b != -1) {ArrayList<Integer> att = eventManager.getEventWithId(b).getAttendeeId();
            msgToList(a, att);}
        speakerUI.stopmessaging();
    }

    private void msgToList(String a, ArrayList<Integer> att){
        if (att.isEmpty()) speakerUI.noattendees();
        else{
            for (int getterid : att) {
                messagetoatt(a, getterid);
            }
            speakerUI.messagesend();
        }
    }

    public void messageall(String a) {
        ArrayList<Integer> att = getallattendeev1();
        msgToList(a, att);
    }

    public void readmsgandrep(){
        readallmsg();
        speakerUI.announcereply();
    }

    public void readrepandmsg(){
        readallreply();
        speakerUI.announcemsg();

    }

    private void readallreply(){
        String a = MsgM.formatreply(SpeakerM.getmsgsend());
        speakerUI.show(a);
    }

    private void readallmsg(){
        String a = MsgM.formatmsgget(SpeakerM.getinbox());
        speakerUI.show(a);
    }

    public int targetmsg(){
        ArrayList<Integer> validChoices = SpeakerM.getinbox();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }



    /////////////////UNREAD HELPER////////////////

    public ArrayList<Integer> getAllUnread(int speakerId) {
        Speaker acc = (Speaker) SpeakerM.getAccountWithId(speakerId);
        ArrayList<Integer> inbox = acc.getInbox();
        ArrayList<Integer> unread = new ArrayList<>();
        for (Integer i : inbox) {
            Message msg = MsgM.getmessage(i);
            if (!msg.getReadStatus()) {
                unread.add(msg.getmessageid());
            }
        }
        return unread;
    }

    public ArrayList<Integer> getAllRead(int speakerId) {
        Speaker acc = (Speaker) SpeakerM.getAccountWithId(speakerId);
        ArrayList<Integer> inbox = acc.getInbox();
        ArrayList<Integer> read = new ArrayList<>();
        for (Integer i : inbox) {
            Message msg = MsgM.getmessage(i);
            if (!msg.getReadStatus()) {
                read.add(msg.getmessageid());
            }
        }
        return read;
    }

}