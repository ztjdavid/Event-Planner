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

    public AttendeeSystem(AccountManager accM, TalkManager TalkM, MessageManager MsgM, AttendeeUI attendeeUI,
                         StrategyManager StrategyManager, AttendeeManager AttendeeM) {
        this.accM = accM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.attendeeUI = attendeeUI;
        this.strategyM = StrategyManager;
        this.attendeeM = AttendeeM;

    }

    public void run() {
        int userChoice;
        do {
            attendeeUI.startup();
            userChoice = chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

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
        } while (userChoice != 4);


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
                readallreply();
                break;
            case 4:
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
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
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

    private void readAllMyTalks(){
        StringBuilder a = new StringBuilder("My signed up talks");
        ArrayList<Integer> alltalks = attendeeM.getAllMyTalks(); //manager need to implement
        for(Integer t:alltalks){
            a.append(talkManager.gettalkinfo(t));}
        attendeeUI.show(a.toString());}

    private void readAllAvailableTalks(){
        StringBuilder a = new StringBuilder("Available Talks");
        ArrayList<Integer> alltalks = attendeeM.getAllAvailableTalks(); //manager need to implement
        for(Integer t:alltalks){
            a.append(talkManager.gettalkinfo(t));}
        attendeeUI.show(a.toString());}


    private int targetTalksSignUp(){
        ArrayList<Integer> validChoices = attendeeM.getAllAvailableTalks();//manager need to implement
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
        attendeeUI.signUpTalk();
        readAllAvailableTalks();
        int input = targetTalksSignUp();
        attendeeM.enrol(input);
        attendeeUI.signUpSuc();
    }

    private int targetTalksCancel(){
        ArrayList<Integer> validChoices = attendeeM.getAllMyTalks();//manager need to implement
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
        attendeeUI.cancelTalk();
        readAllMyTalks();
        int input = targetTalksCancel();
        attendeeM.drop(input);//manager need to implement
        attendeeUI.cancelSuc();
    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            readAllAttendees();
            tAttendeeId = targetGetter();
            if (tAttendeeId != -1){
                String txt = enterTxt();
                messagetoatt(txt, tAttendeeId);
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

    public void messagetoatt(String a, int getterid) {

        int msg = MsgM.createmessage(accM.getCurrAccountId(), getterid, a);
        accM.addinbox(getterid, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        attendeeUI.messagesend();
    }

    private void msgToSpeaker(){
        int tSpeakerId;
        do{
            readAllAttendees();
            tSpeakerId = targetSpeaker();
            if (tSpeakerId != -1){
                String txt = enterTxt();
                messageToSp(txt, tSpeakerId);
                attendeeUI.askForBack();
            }
        }while(tSpeakerId != -1);
    }
    public void messageToSp(String a, int speakerId) {

        int msg = MsgM.createmessage(accM.getCurrAccountId(), speakerId, a);
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

    private void readallreply(){
        String a = MsgM.formatreply(attendeeM.getinbox());
        attendeeUI.show(a);
    }

    //this is a helper function to get a list of all attendees in current attendee signed up talks
    public ArrayList<Integer> getAllAttendees() {
        ArrayList<Integer> talklist = attendeeM.getAllMyTalks();//manager need to implement
        return talkManager.getallattendee(talklist);
    }

    public ArrayList<Integer> getAllSpeakers() {
        ArrayList<Integer> talklist = attendeeM.getAllMyTalks();//manager need to implement
        return talkManager.getAllSpeakers(talklist);
    }

    private void readAllAttendees(){
        ArrayList<Integer> att = getAllAttendees();
        StringBuilder a = new StringBuilder("These are the attendees who attend your signed up talks. Choose an id to message:");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));
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
            }
        } while(!exit);
        return a.toString();
    }

}



