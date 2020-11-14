package Controller;
import UI.SpeakerUI;
import UseCase.AccountManager;
import UseCase.StrategyManager;
import UseCase.TalkManager;
import UseCase.MessageManager;
import UseCase.SpeakerManager;

import java.util.ArrayList;
import java.util.Arrays;


public class SpeakerSystem {
    protected AccountManager accM;
    protected TalkManager talkManager;
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected StrategyManager strategyM;
    protected SpeakerManager SpeakerM;


    public SpeakerSystem(AccountManager accM, TalkManager TalkM, MessageManager MsgM, SpeakerUI SpeakerUI,
                         StrategyManager StrategyManager, SpeakerManager SpeakerM) {
        this.accM = accM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.speakerUI = SpeakerUI;
        this.strategyM = StrategyManager;
        this.SpeakerM = SpeakerM;


    }
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
                readalltalks();
                break;
            case 2:
                MsgDashboard();
            case 3:
                break;
        }
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            speakerUI.messaging();
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
                msgToTalk();
                break;
            case 3:
                msgToAllTalks();
                break;
            case 4:
                readrepandmsg();
            case 5:
                break;
        }
    }


    //Methods for doing a specific user operation.
    private void msgToAttendee(){
        readallatt();
        int tgetter = targetgetter();
        String txt = speakerUI.enteringtext();
        messagetoatt(txt, tgetter);
    }

    private void msgToTalk(){
        readalltalkssimp();
        String txt1 = speakerUI.enteringtext();
        int targettalk = -1;
        while (targettalk != 999) {
            targettalk= targettalks();
            messagetotalk(txt1, targettalk);
        }
    }

    private void msgToAllTalks(){
        String txt2 = speakerUI.enteringtext();
        messageall(txt2);
    }

    private void readrepandmsg(){
        readallreply();
        speakerUI.announcereply();
    }


    //Helper Methods:

    private int chooseMode1(){    //For Speaker Dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private int chooseMode2(){    // For messaging dashboard.
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        do{
            userInput = speakerUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private int targetgetter(){
        ArrayList<Integer> validChoices = getallattendeev1();
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private int targettalks(){
        ArrayList<Integer> validChoices = SpeakerM.getalltalk();
        int mode = -1;
        boolean valid = false;
        while(!valid){
            String userInput = speakerUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private void readalltalks(){
        StringBuilder a = new StringBuilder("Talk Information");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            a.append(talkManager.gettalkinfo(t));}
        speakerUI.show(a.toString());
    }
    private void readalltalkssimp(){
        StringBuilder a = new StringBuilder("Talk Information with id");
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(Integer t:alltalks){
            a.append(talkManager.gettalkinfosimp(t));}
        speakerUI.show(a.toString());
    }


    private void readallatt(){
        ArrayList<Integer> att = getallattendeev1();
        StringBuilder a = new StringBuilder("There are the attendees who attend your talk. Choose an id to message");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));

        }
        speakerUI.show(a.toString());
    }

    private void readallreply(){
        String a = MsgM.formatreply(SpeakerM.getinbox());
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

    public void messagetoatt(String a, int getterid) {

        int msg = MsgM.createmessage(accM.getCurrAccountId(), getterid, a);
        accM.addinbox(getterid, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        speakerUI.messagesend();
    }


    public void messageall(String a) {
        ArrayList<Integer> att = getallattendeev1();
        msgToList(a, att);
    }
    public void messagetotalk(String a, int b) {
        if (b == 999) {speakerUI.stopmessaging();}
        ArrayList<Integer> att = talkManager.getTalk(b).getAttendeeId();
        msgToList(a, att);
    }


    public ArrayList<Integer> getallattendeev1() {
        ArrayList<Integer> talklist = SpeakerM.getalltalk();
        return talkManager.getallattendee(talklist);
    }


}