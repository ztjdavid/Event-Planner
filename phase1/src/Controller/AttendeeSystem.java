package Controller;
import UI.AttendeeUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;


public class AttendeeSystem {
    protected AccountManager accM;
    protected TalkManager talkManager;
    protected MessageManager MsgM;
    protected AttendeeUI attendeeUI;
    protected StrategyManager strategyM;
    protected AttendeeManager attendeeM;
    protected RoomManager roomM;

    public AttendeeSystem(AccountManager accM, TalkManager TalkM, MessageManager MsgM, AttendeeUI attendeeUI,
                         StrategyManager StrategyManager, AttendeeManager AttendeeM, RoomManager roomM) {
        this.accM = accM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.attendeeUI = attendeeUI;
        this.strategyM = StrategyManager;
        this.attendeeM = AttendeeM;
        this.roomM = roomM;

    }

    /**
     * Run Attendee System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            attendeeUI.startup();
            userChoice = chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    //Helper methods:
    private void enterBranch(int userChoice){
        switch (userChoice){
            case 1:
                MyTalksDashboard();
                break;
            case 2:
                signUpMyNewTalks();
                break;
            case 3:
                cancelMyTalks();
                break;
            case 4:
                MsgDashboard();
                break;
            case 5:
                break;
        }
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            attendeeUI.msgSelect();
            userChoice = chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 6);


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
                break;
        }
    }

    private int chooseMode1(){    //For Attendee Dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest();
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
            userInput = attendeeUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }
    private void MyTalksDashboard(){
        readAllMyTalks();
        attendeeUI.askForBack();
    }
////////////ERICMODIFY

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
            tmsgid = targetmsg();
            if (tmsgid != -1){
                String txt = enterTxt();
                MsgM.setreply(tmsgid, txt);
                attendeeUI.askForBack();
            }
        }while(tmsgid != -1);
    }

    private int targetgetter(){
        ArrayList<Integer> a = getAllAttendees();
        a.addAll(getAllSpeakers());
        ArrayList<Integer> validChoices = a;
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
            targetId = targetgetter();
            if (targetId != -1){
                String txt = enterTxt();
                messageToAtt(txt, targetId);
                attendeeUI.askForBack();
            }
        }while(targetId != -1);
    }
///////////ERICMODIFY
    private void readAllMyTalks(){
        StringBuilder a = new StringBuilder("My signed up talks:");
        ArrayList<Integer> allTalks = attendeeM.getAllMyTalksId();
        for(Integer t:allTalks){
            String roomName = roomM.getRoomName(talkManager.getRoomIdWithId(t));
            a.append(talkManager.gettalkinfoWithName(t, roomName));}
        attendeeUI.show(a.toString());
    }

    private ArrayList<Integer> getNotAttendedTalks(){
        ArrayList<Integer> myTalksId = attendeeM.getAllMyTalksId();
        ArrayList<Integer> allTalksId = talkManager.getAllTalksID();
        ArrayList<Integer> result = new ArrayList<>();
        for(Integer t:allTalksId){
            if (!myTalksId.contains(t)) result.add(t);
        }
        return result;
    }

    private void readAllAvailableTalks(){
        StringBuilder a = new StringBuilder("Available Talks: ");
        ArrayList<Integer> availableTalksId = getNotAttendedTalks();
        for(Integer t:availableTalksId){
            String roomName = roomM.getRoomName(talkManager.getRoomIdWithId(t));
            a.append(talkManager.gettalkinfoWithName(t, roomName));
        }
        attendeeUI.show(a.toString());}


    private int targetTalksSignUp(){
        ArrayList<Integer> validChoices = getNotAttendedTalks();
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    private void signUpMyNewTalks(){
        int input;
        do{
            attendeeUI.signUpTalk();
            readAllAvailableTalks();
            input = targetTalksSignUp();
            if (input != -1){
                attendeeM.enrol(input);
                talkManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                attendeeUI.signUpSuc();
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
            userInput = attendeeUI.getrequest3();
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
            readAllMyTalks();
            input = targetTalksCancel();
            if (input != -1){
                attendeeM.drop(input);
                talkManager.removeAttendeev2(input, attendeeM.getCurrAttendee());
                attendeeUI.cancelSuc();
            }
        }while(input != -1);


    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            readAllAttendees();
            tAttendeeId = targetGetter();
            if (tAttendeeId != -1){
                String txt = enterTxt();
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
            userInput = attendeeUI.getrequest1();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    public void messageToAtt(String a, int getterId) {

        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), getterId, a);
        accM.addinbox(getterId, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        attendeeUI.messagesend();
    }

    private void msgToSpeaker(){
        int tSpeakerId;
        do{
            readAllSpeakers();
            tSpeakerId = targetSpeaker();
            if (tSpeakerId != -1){
                String txt = enterTxt();
                messageToSp(txt, tSpeakerId);
                attendeeUI.askForBack();
            }
        }while(tSpeakerId != -1);
    }
    public void messageToSp(String a, int speakerId) {
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
            userInput = attendeeUI.getrequest1();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void readAllMsg(){

        String a = MsgM.formatmsgget(attendeeM.getInbox());
        attendeeUI.show(a);
        attendeeUI.askForBack();

    }

    //this is a helper function to get a list of all attendees except itself in current attendee signed up talks
    private ArrayList<Integer> getAllAttendees() {
        ArrayList<Integer> talkList = attendeeM.getAllMyTalksId();
        ArrayList<Integer> result = talkManager.getallattendee(talkList);
        int currAcc = accM.getCurrAccountId();
        if (result.contains(currAcc)) result.remove(Integer.valueOf(currAcc));
        return result;


    }

    private ArrayList<Integer> getAllSpeakers() {
        ArrayList<Integer> talkList = attendeeM.getAllMyTalksId();
        return talkManager.getAllSpeakers(talkList);
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
            int spkId = talkManager.getSpeakerIDIn(t);
            String each = "(" + talkManager.getTitle(t) + ")" + accM.getinfoacc(spkId);
            a.append(each);
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

}



