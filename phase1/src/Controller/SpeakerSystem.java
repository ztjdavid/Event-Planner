package Controller;
import Entity.Account;
import Entity.Message;
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


    public SpeakerSystem(LoginManager loginM, TalkManager TalkM, MessageManager MsgM, SpeakerUI SpeakerUI, StrategyManager StrategyManager) {
        this.loginM = loginM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.currSpeaker = (Speaker) loginM.getCurrAccount();
        this.speakerUI = SpeakerUI;
        this.strategyM = StrategyManager;


    }
    public void run(){
        int userinput = -1;
        while (userinput != 3){
            speakerUI.startup();

            userinput = chooseMode1();

            switch (userinput){
                case 1:
                    readalltalks();
                    break;
                case 2:

                    int userinput2 = -1;
                    while (userinput2 != 4){
                        speakerUI.messaging();
                        userinput2 = chooseMode2();
                        switch (userinput){
                            case 1:
                                readallatt();
                                int tgetter = targetgetter();
                                String txt = speakerUI.enteringtext();
                                messagetoatt(txt, tgetter);
                                break;
                            case 2:
                                readalltalkssimp();
                                String txt1 = speakerUI.enteringtext();
                                int targettalk = -1;
                                while (targettalk != 999) {
                                    targettalk= targettalks();
                                    messagetotalk(txt1, targettalk);

                                }
                                break;
                            case 3:
                                String txt2 = speakerUI.enteringtext();
                                messageall(txt2);
                                break;
                            default:
                                break;

                        }
                    }
                    System.out.println("Quit Messaging System");

                default:
                    break;
            }
        }
        System.out.println("Quit");

    }
    private int chooseMode1(){
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
    private int chooseMode2(){
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
        return mode;}

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
        Message msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
        this.currSpeaker.addSentMessage(msg.getmessageid());
        getter.addInbox(msg.getmessageid());
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
            Message msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
            this.currSpeaker.addSentMessage(msg.getmessageid());
            getter.addInbox(msg.getmessageid());

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
            Message msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
            this.currSpeaker.addSentMessage(msg.getmessageid());
            getter.addInbox(msg.getmessageid());

        }
        System.out.println("Message Send");

    }
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