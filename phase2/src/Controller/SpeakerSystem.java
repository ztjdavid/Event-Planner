package Controller;
import Presenters.SpeakerUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;


public class SpeakerSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected StrategyManager strategyM;
    protected SpeakerManager SpeakerM;
    protected RoomManager roomM;
    protected SpeakerSystemHandler sh;


    public SpeakerSystem(AccountManager accM, EventManager eventM, MessageManager MsgM, SpeakerUI SpeakerUI,
                         StrategyManager StrategyManager, SpeakerManager SpeakerM, RoomManager roomM) {
        this.accM = accM;
        this.eventManager = eventM;
        this.MsgM = MsgM;
        this.speakerUI = SpeakerUI;
        this.strategyM = StrategyManager;
        this.SpeakerM = SpeakerM;
        this.roomM = roomM;

    }

    /**
     * Run Speaker System, user can choose according to startup options.
     */
    public void run(){
        int userChoice;
        do{
            speakerUI.startup();
            userChoice = chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 3);
    }


    //Methods for controlling program process:
    private void enterBranch(int userChoice){
        switch (userChoice){
            case 1:
                talkDashboard();
                break;
            case 2:
                MsgDashboard();
                break;
            case 3:
                break;
        }
    }

    private void talkDashboard(){
        readalltalks();
        speakerUI.askForBack();
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            speakerUI.messaging();
            userChoice = chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 7);


    }

    private void msgOp(int userChoice){
        switch (userChoice){
            case 1:
                msgToAttendee();
                break;
            case 2:
                msgToTalk();
                break;
            case 3:
                msgToAllTalks();
                break;
            case 4:
                replytomsg();
                break;
            case 5:
                msgtoreply();
                break;
            case 6:
                allunreadmsg();
            case 7:
                break;
        }
    }


    //Methods for doing a specific user operation.
    private void msgToAttendee(){
        int tAttendeeId;
        do{
            readallatt();
            tAttendeeId = targetgetter();
            if (tAttendeeId != -1){
                String txt = enterTxt();
                messagetoatt(txt, tAttendeeId);
                speakerUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }

    private void msgToTalk(){
        int targettalk;
        do{
            readalltalkssimp();
            targettalk= targettalks();
            if (targettalk != -1){
                String txt = enterTxt();
                messagetotalk(txt, targettalk);
                speakerUI.askForBack();
            }
        } while (targettalk != -1);
    }

    private void msgToAllTalks(){
        int userChoice = chooseMode3();
        if (userChoice == 1){
            String txt = enterTxt();
            messageall(txt);
            speakerUI.askForBack();
        }
    }

    private void msgtoreply(){
        int tmsgid;
        do{
            readmsgandrep();
            tmsgid = targetmsg();
            if (tmsgid != -1){
                String txt = enterTxt();
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                speakerUI.askForBack();
            }
        }while(tmsgid != -1);
    }

    private void replytomsg(){
        int tAttendeeId;
        do{
            readrepandmsg();
            tAttendeeId = targetgetter();
            if (tAttendeeId != -1){
                String txt = enterTxt();
                messagetoatt(txt, tAttendeeId);
                speakerUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }

    ///// Louisa added
    private void allunreadmsg(){
        int tmsgid;
        do{
            readAllUnreadMsg();
            tmsgid = targetunread();
            if(tmsgid != -1){
                MsgM.readMessage(tmsgid);
                speakerUI.unreadSuccess(tmsgid);
                speakerUI.askForBack();
            }

        }while(tmsgid != -1);
    }

    private void readAllUnreadMsg(){
        readAllUnread();
        speakerUI.annouceUnread();

    }

    private void readAllUnread(){
        SpeakerSystemHandler UnH = new SpeakerSystemHandler(MsgM, speakerUI, SpeakerM);
        String all = MsgM.formatAllUnread(UnH.getAllUnread(SpeakerM.getCurrAccountId()));
        speakerUI.show(all);
    }
    /////


    private void readrepandmsg(){
        readallreply();
        speakerUI.announcemsg();

    }

    private void readmsgandrep(){
        readallmsg();
        speakerUI.announcereply();
    }


    //Helper Methods:
    private int chooseMode1(){    //For Speaker Dashboard.
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

    private int chooseMode2(){    // For messaging dashboard.
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        do{
            userInput = speakerUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private int chooseMode3(){    // For MessageAll confirmation.
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

    private String enterTxt(){
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

    private int targetgetter(){
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

    private int targettalks(){
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

    private int targetmsg(){
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

    ///// Louisa added
    private int targetunread(){
        ArrayList<Integer> validChoices = SpeakerM.getUnread();
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
    /////

    private void readalltalks(){
        StringBuilder a = new StringBuilder("Event Information:");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.gettalkinfoWithName(t, roomName));}
        speakerUI.show(a.toString());
    }
    private void readalltalkssimp(){
        StringBuilder a = new StringBuilder("Event Information with id:");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            a.append(eventManager.gettalkinfosimp(t));}
        speakerUI.show(a.toString());
    }


    private void readallatt(){
        ArrayList<Integer> att = getallattendeev1();
        StringBuilder a = new StringBuilder("These are the attendees who attend your talk. Choose an id to message:");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));

        }
        speakerUI.show(a.toString());
    }

    private void readallmsg(){
        String a = MsgM.formatmsgget(SpeakerM.getinbox());
        speakerUI.show(a);
    }

    private void readallreply(){
        String a = MsgM.formatreply(SpeakerM.getmsgsend());
        speakerUI.show(a);
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

    private void messagetoatt(String a, int getterid) {

        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), getterid, a);
        accM.addinbox(getterid, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        speakerUI.messagesend();
    }


    private void messageall(String a) {
        ArrayList<Integer> att = getallattendeev1();
        msgToList(a, att);
    }
    private void messagetotalk(String a, int b) {
        if (b == 999) {speakerUI.stopmessaging();}
        ArrayList<Integer> att = eventManager.getTalkWithId(b).getAttendeeId();
        msgToList(a, att);
    }


    private ArrayList<Integer> getallattendeev1() {
        ArrayList<Integer> talklist = SpeakerM.getalltalk();
        return eventManager.getallattendee(talklist);
    }

    ///// Louisa added


    /////

}