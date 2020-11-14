package Controller;
import UI.SpeakerUI;
import UseCase.LoginManager;
import UseCase.StrategyManager;
import UseCase.TalkManager;
import UseCase.MessageManager;
import UseCase.SpeakerManager;

import java.util.ArrayList;
import java.util.Arrays;


public class SpeakerSystem {
    protected LoginManager loginM;
    protected TalkManager talkManager;
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected StrategyManager strategyM;
    protected SpeakerManager SpeakerM;

//TODO: 不应该出现在最终程序里会有的print指令，所有必要的print都必须在UI里实现。
    public SpeakerSystem(LoginManager loginM, TalkManager TalkM, MessageManager MsgM, SpeakerUI SpeakerUI,
                         StrategyManager StrategyManager, SpeakerManager SpeakerM) {
        this.loginM = loginM;
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
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = speakerUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int chooseMode2(){    // For messaging dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = speakerUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int targetgetter(){
        ArrayList<Integer> validChoices = getallattendeev1();
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = speakerUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    private int targettalks(){
        ArrayList<Integer> validChoices = SpeakerM.getalltalk();
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = speakerUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}
    //TODO： 与Entity直接联系了，生成每个talk的介绍应该在talkManager里实现，然后用这个method整合。
    private void readalltalks(){
        String a = "Talk Information";
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(int i = 0; i < alltalks.size(); i++){a += talkManager.gettalkinfo(alltalks.get(i));};
        speakerUI.show(a);
    }
    private void readalltalkssimp(){
        String a = "Talk Information with id";
        ArrayList<Integer> alltalks = SpeakerM.getalltalk();
        for(int i = 0; i < alltalks.size(); i++){a += talkManager.gettalkinfosimp(alltalks.get(i));};
        speakerUI.show(a);
    }


    private void readallatt(){
        ArrayList<Integer> att = getallattendeev1();
        String a = "There are the attendees who attend your talk. Choose an id to message";
        for(Integer i : att) {
            a += loginM.getinfoacc(i);

        }
        speakerUI.show(a);
    }

    private void readallreply(){
        String a = MsgM.formatreply(SpeakerM.getinbox());
        speakerUI.show(a);
    }

    public void messagetoatt(String a, int getterid) {

        int msg = MsgM.createmessage(SpeakerM.getCurrSpeaker(), getterid, a);
        loginM.addinbox(getterid, msg);
        loginM.addsend(SpeakerM.getCurrSpeaker(), msg);
        speakerUI.messagesend();
    }


    public void messageall(String a) {
        ArrayList<Integer> att = getallattendeev1();
        if (att.size() == 0) {speakerUI.noattendees();}
        for (int i = 0; i < att.size(); i++) {
            int getterid = att.get(i);
            messagetoatt(a, getterid);
        }
        speakerUI.messagesend();
    }
    public void messagetotalk(String a, int b) {
        if (b == 999) {speakerUI.stopmessaging();}
        ArrayList<Integer> att = talkManager.getTalk(b).getAttendeeId();
        if (att.size() == 0) {
            speakerUI.noattendees();
        }
        for (int i = 0; i < att.size(); i++) {
            int getterid = att.get(i);
            messagetoatt(a, getterid);

        }
        speakerUI.messagesend();

    }
    //TODO 不能直接对entity操作， 要在speakerManager里实现这个功能。

    public ArrayList<Integer> getallattendeev1() {
        ArrayList<Integer> talklist = SpeakerM.getalltalk();
        ArrayList<Integer> allattendeeid = talkManager.getallattendee(talklist);

        return allattendeeid;
    }


}