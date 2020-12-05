package Controller;
import Presenters.AttendeeUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;


public class AttendeeSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected AttendeeUI attendeeUI;
    protected StrategyManager strategyM;
    protected AttendeeManager attendeeM;
    protected RoomManager roomM;
    protected Attendeesystemhandler ah;

    public AttendeeSystem(AccountManager accM, EventManager TalkM, MessageManager MsgM, AttendeeUI attendeeUI,
                          StrategyManager StrategyManager, AttendeeManager AttendeeM, RoomManager roomM, Attendeesystemhandler ah) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.attendeeUI = attendeeUI;
        this.strategyM = StrategyManager;
        this.attendeeM = AttendeeM;
        this.roomM = roomM;
        this.ah = ah;

    }

    /**
     * Run Attendee System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            attendeeUI.startup();
            userChoice = ah.chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    //Helper methods:
    private void enterBranch(int userChoice){
        switch (userChoice){
            case 1:
                EventDashboard();
                break;
            case 2:
                MsgDashboard();
                break;
            case 3:
                break;
        }
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            attendeeUI.msgSelect();
            userChoice = ah.chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 7);


    }
    private void EventDashboard(){
        int userChoice;
        do{
            attendeeUI.eventmain();
            userChoice = ah.chooseMode4();
            EventOp(userChoice);
        } while (userChoice != 7);
    }
    private void EventOp(int userChoice){
        switch (userChoice){
            case 1:
                SignupDashboard();
                break;
            case 2:
                MyTalksDashboard();
                break;
            case 3:
                cancelMyTalks();
                break;
            case 4:
                break;
        }
    }
    private void SignupDashboard(){
        int userChoice;
        do{
            attendeeUI.eventselect();
            userChoice = ah.chooseMode3();
            EventSignup(userChoice);
        } while (userChoice != 7);
    }
    private void EventSignup(int userChoice){
        switch (userChoice){
            case 1:
                signUpMyNewTalks(1);
                break;
            case 2:
                signUpMyNewTalks(2);
                break;
            case 3:
                signUpMyNewTalks(0);
                break;
            case 4:
                break;
        }
    }
    private void msgOp(int userChoice){
        switch (userChoice){
            case 1:
                msgToAttendee();
                break;
            case 2:
                msgToSpeaker();
                break;
            case 3:
                readAllMsg();
                break;
            case 4:
                replytomsg();
                break;
            case 5:
                msgtoreply();
                break;
            case 6:
                allUnreadMsg();
                break;
            case 7:
                break;
        }
    }

    private int chooseMode1(){    //For Attendee Dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int chooseMode2(){    //For MsgDashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int chooseMode3(){    //For EventDashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    public int chooseMode4(){    //For EventDashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest(1);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private void MyTalksDashboard(){
        ah.readAllMyTalks();
        attendeeUI.askForBack();
    }

    private void readrepandmsg(){
        readallreply();
        attendeeUI.announcemsg();

    }

    private void readmsgandrep(){
        readallmsg();
        attendeeUI.announcereply();
    }

    private void readallmsg(){
        String a = MsgM.formatmsgget(attendeeM.getinbox());
        attendeeUI.show(a);
    }

    private void readallreply(){
        String a = MsgM.formatreply(attendeeM.getmsgsend());
        attendeeUI.show(a);
    }


    private int targetmsg(){
        ArrayList<Integer> validChoices = attendeeM.getinbox();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attendeeUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void msgtoreply(){
        int tmsgid;
        do{
            readmsgandrep();
            tmsgid = ah.targetmsg();
            if (tmsgid != -1){
                String txt = ah.enterTxt();
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                attendeeUI.askForBack();
            }
        }while(tmsgid != -1);
    }

    private int targetgetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.addAll(getAllSpeakers());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attendeeUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void replytomsg(){
        int targetId;
        do{
            readrepandmsg();
            targetId = ah.targetgetter();
            if (targetId != -1){
                String txt = ah.enterTxt();
                messageToAtt(txt, targetId);
                attendeeUI.askForBack();
            }
        }while(targetId != -1);
    }

    private void readAllMyTalks(){
        StringBuilder a = new StringBuilder("My signed up talks:");
        ArrayList<Integer> allTalks = attendeeM.getAllMyTalksId();
        for(Integer t:allTalks){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.gettalkinfoWithName(t, roomName));}
        attendeeUI.show(a.toString());
    }

    /**
     * Get all available events that this account can attend.
     * @return A tlkList containing all events this account can attend.
     */
    private ArrayList<Integer> getAllAvailableTalks(int a){
        boolean is_VIP = false;
        if (accM.isVIPAcc(accM.getCurrAccountId())) is_VIP = true;
        ArrayList<Integer> myTalksId = attendeeM.getAllMyTalksId();
        ArrayList<Integer> allTalksId = eventManager.getListOfEventsByType(a);
        ArrayList<Integer> result = new ArrayList<>();
        for(Integer t:allTalksId){
            if (!myTalksId.contains(t) && (eventManager.getRemainingSeats() > 0 )) result.add(t);

        }
        if (!is_VIP){
            for (Integer i:result){
                if (eventManager.checkVIP(i)) result.remove(Integer.valueOf(i));

            }}



        return result;
    }

    private void readAllAvailableTalks(int type){
        StringBuilder a = new StringBuilder("Available Talks: ");
        ArrayList<Integer> availableTalksId = getAllAvailableTalks(type);
        for(Integer t:availableTalksId){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.gettalkinfoWithName(t, roomName));
        }
        attendeeUI.show(a.toString());}


    private int targetTalksSignUp(int type){
        ArrayList<Integer> validChoices = getAllAvailableTalks(type);
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest(3);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    ///// Louisa Modified
    private void signUpMyNewTalks(int a){
        int input;
        do{
            attendeeUI.signUpTalk();
            ah.readAllAvailableTalks(a);
            input = ah.targetTalksSignUp(a);
            if (input != -1){
                if(eventManager.checkVIP(input)){
                    attendeeUI.signUpVipTalk();
                    if(attendeeM.getCurrAttendee().getUserType() == 3){
                        attendeeM.enrol(input);
                        eventManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                        attendeeUI.signUpSuc();
                    }else{attendeeUI.informNotVip();}
                }else if(!eventManager.checkVIP(input)){
                    attendeeM.enrol(input);
                    eventManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                    attendeeUI.signUpSuc();
                }
            }
        }while(input != -1);
    }

    private int targetTalksCancel(){
        ArrayList<Integer> validChoices = attendeeM.getAllMyTalksId();
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest(4);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    private void cancelMyTalks(){
        int input;
        do{
            attendeeUI.cancelTalk();
            ah.readAllMyTalks();
            input = ah.targetTalksCancel();
            if (input != -1){
                attendeeM.drop(input);
                eventManager.removeAttendeev2(input, attendeeM.getCurrAttendee());
                attendeeUI.cancelSuc();
            }
        }while(input != -1);


    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            ah.readAllAttendees();
            tAttendeeId = ah.targetgetter();
            if (tAttendeeId != -1){
                String txt = ah.enterTxt();
                messageToAtt(txt, tAttendeeId);
                attendeeUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }

    private int targetGetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attendeeUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void messageToAtt(String a, int getterId) {

        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), getterId, a);
        accM.addinbox(getterId, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        attendeeUI.messagesend();
    }

    private void msgToSpeaker(){
        int tSpeakerId;
        do{
            ah.readAllSpeakers();
            tSpeakerId = ah.targetgetter();
            if (tSpeakerId != -1){
                String txt = ah.enterTxt();
                messageToSp(txt, tSpeakerId);
                attendeeUI.askForBack();
            }
        }while(tSpeakerId != -1);
    }
    private void messageToSp(String a, int speakerId) {
        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), speakerId, a);
        accM.addinbox(speakerId, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        attendeeUI.messagesend();
    }

    private int targetSpeaker(){
        ArrayList<Integer> validChoices = getAllSpeakers();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = attendeeUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void readAllMsg(){

        int messageID;
        ArrayList<Integer> inbox = attendeeM.getInbox();

        if (inbox.size() != 0) {
            do {
                String a = MsgM.formatmsgget(attendeeM.getInbox());
                attendeeUI.show(a);
                messageID = targetmsg();
                if(messageID != -1){
                    attendeeUI.show(MsgM.getString(messageID));
                    ah.askToAchieve(messageID);
                }
            }while(messageID != -1);
        } else {
            attendeeUI.announceEmptyInbox();
            attendeeUI.askForBack();
        }


    }

    //this is a helper function to get a list of all attendees except itself in current attendee signed up talks
    private ArrayList<Integer> getAllAttendees() {
        ArrayList<Integer> talkList = attendeeM.getAllMyTalksId();
        ArrayList<Integer> result = eventManager.getallattendee(talkList);
        int currAcc = accM.getCurrAccountId();
        if (result.contains(currAcc)) result.remove(Integer.valueOf(currAcc));
        return result;


    }

    private ArrayList<Integer> getAllSpeakers() {
        ArrayList<Integer> talkList = attendeeM.getAllMyTalksId();
        return eventManager.getAllSpeakers(talkList);
    }


    private void readAllAttendees(){
        ArrayList<Integer> att = getAllAttendees();
        StringBuilder a = new StringBuilder("These are the attendees who attend your signed up talks. Choose an id to message:\n");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        attendeeUI.show(a.toString());
    }

    private void readAllSpeakers(){
        ArrayList<Integer> allTalks = attendeeM.getAllMyTalksId();
        StringBuilder a = new StringBuilder("These are the speakers in talks you attend. Choose an id to message:\n");
        for (Integer t: allTalks){
            ArrayList<Integer> spkLst = new ArrayList<>(eventManager.getSpeakerIDIn(t));
            for(int speaker:spkLst){
                String each = "(" + eventManager.getTitle(t) + ")" + accM.getinfoacc(speaker);
                a.append(each);
            }
        }
        attendeeUI.show(a.toString());
    }
    private String enterTxt(){
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        attendeeUI.informEnteringText();
        do{
            String line = attendeeUI.getLineTxt();
            if (line.equals("end")) exit = true;
            else{
                a.append(line);
                a.append("\n");
            }
        } while(!exit);
        return a.toString();
    }
///Grey modify

    private void allUnreadMsg(){
        int tmsgid;
        do{
            ah.readAllUnreadMsg();
            tmsgid = ah.targetunread();
            if(tmsgid != -1){
                MsgM.readMessage(tmsgid);
                attendeeM.deleteUnreadInbox(tmsgid);
                attendeeUI.unreadSuccess(tmsgid);
                attendeeUI.askForBack();
            }

        }while(tmsgid != -1);
    }

}



