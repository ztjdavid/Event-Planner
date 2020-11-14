package Controller;
import Entity.Account;
import Entity.Speaker;
import Entity.Talk;
import UI.SpeakerUI;
import UseCase.LoginManager;
import UseCase.StrategyManager;
import UseCase.TalkManager;
import UseCase.MessageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SpeakerSystem {
    protected LoginManager loginM;
    protected TalkManager talkManager;
    protected Speaker currSpeaker;
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected StrategyManager strategyM;

//TODO: 不应该出现在最终程序里会有的print指令，所有必要的print都必须在UI里实现。
    public SpeakerSystem(LoginManager loginM, TalkManager TalkM, MessageManager MsgM, SpeakerUI SpeakerUI,
                         StrategyManager StrategyManager) {
        this.loginM = loginM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.currSpeaker = (Speaker) loginM.getCurrAccount();
        this.speakerUI = SpeakerUI;
        this.strategyM = StrategyManager;


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
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
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
        ArrayList<Integer> validChoices = getallattendeev1(currSpeaker);
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
        ArrayList<Integer> validChoices = currSpeaker.getTalkList();
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
        for(int i = 0; i < getalltalks().size(); i++){Talk talk = getalltalks().get(i);
        String talktitle = talk.getTalkTitle();
        int talktime = talk.getStartTime();
        int talkroom = talk.getRoomId();
        int numatt = talk.getAttendeeId().size();
        a = a + "\n Talk Title:" + talktitle + "\n This talk start at " + talktime + "\n This talk hold in room " + talkroom + "\n There are " + numatt + "attendees";}
        System.out.println(a);
    }
    private void readalltalkssimp(){
        String a = "Talk Information";
        for(int i = 0; i < getalltalks().size(); i++){Talk talk = getalltalks().get(i);
            String talktitle = talk.getTalkTitle();
            int talkid = talk.getTalkId();

            a = a + "\n Talk Title:" + talktitle + "\n The id of this talk is  " + talkid;}
        System.out.println(a);
    }


    private void readallatt(){
        ArrayList<Integer> att = getallattendeev1(currSpeaker);
        String a = "There are the attendees who attend your talk. Choose an id to message";
        for(int i = 0; i < att.size(); i++) {
            Account attendee = loginM.getAccountWithId(att.get(i));
            int attendeeid = attendee.getUserId();
            String attendeename = attendee.getUsername();
            a = a + "\n" + "\n" + attendeename + "id:" + attendeeid;
        }
        System.out.println(a);
    }

    public void messagetoatt(String a, int getterid) {
        Account getter = loginM.getAccountWithId(getterid);
        int msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
        this.currSpeaker.addSentMessage(msg);
        getter.addInbox(msg);
        System.out.println("Message Send");


    }


    public void messageall(String a) {
        ArrayList<Integer> att = getallattendeev1(this.currSpeaker);
        if (att.size() == 0) {
            String response = "No Attendees";
            System.out.println(response);      }
        for (int i = 0; i < att.size(); i++) {
            int getterid = att.get(i);
            Account getter = loginM.getAccountWithId(getterid);
            int msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
            this.currSpeaker.addSentMessage(msg);
            getter.addInbox(msg);

        }
        System.out.println("Message Send");

    }
    public void messagetotalk(String a, int b) {
        if (b == 999) {System.out.println("Stop Messaging");}
        ArrayList<Integer> att = talkManager.getTalk(b).getAttendeeId();
        if (att.size() == 0) {
            String response = "No Attendees";
            System.out.println(response);
        }
        for (int i = 0; i < att.size(); i++) {
            int getterid = att.get(i);
            Account getter = loginM.getAccountWithId(getterid);
            int msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
            this.currSpeaker.addSentMessage(msg);
            getter.addInbox(msg);

        }
        System.out.println("Message Send");

    }
    //TODO 不能直接对entity操作， 要在speakerManager里实现这个功能。
    private ArrayList<Talk> getalltalks() {
        ArrayList<Talk> alltalks = new ArrayList<>();
        for(int i = 0; i < currSpeaker.getTalkList().size(); i++){
            alltalks.add(talkManager.getTalk(currSpeaker.getTalkList().get(i)));}
        return alltalks;
    }

    public ArrayList<Integer> getallattendeev1(Speaker speaker) {
        ArrayList<Integer> allattendeeid = new ArrayList<>();
        ArrayList<Integer> talklist = speaker.getTalkList();
        for (int i = 0; i < talklist.size(); i++) {
            Talk talk = talkManager.getTalk(talklist.get(i));
            for (int a = 0; a < talk.getAttendeeId().size(); a++) {
                int b = talk.getAttendeeId().get(a);
                allattendeeid.add(b);
            }
        }
        return allattendeeid;
    }

    public HashMap<Integer, ArrayList<Integer>> getallattendeev2(Speaker speaker) {
        HashMap<Integer, ArrayList<Integer>> allattendeeid = new HashMap<>();
        ArrayList<Integer> talklist = speaker.getTalkList();
        for (int i = 0; i < talklist.size(); i++) {
            Talk talk = talkManager.getTalk(talklist.get(i));
            allattendeeid.put(talklist.get(i), talk.getAttendeeId());
        }
        return allattendeeid;
    }
}