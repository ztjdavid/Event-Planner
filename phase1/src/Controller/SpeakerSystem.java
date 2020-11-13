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
        int userinput;
        speakerUI.startup();
        userinput = chooseMode();
        switch (userinput){
            case 1:
                readalltalks();
                break;
            case 2:

        }

    }
    private int chooseMode(){
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2));
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
    private String readalltalks(){
        String a = "Talk Information";
        for(int i = 0; i < getalltalks().size(); i++){Talk talk = getalltalks().get(i);
        String talktitle = talk.getTalkTitle();
        int talktime = talk.getStartTime();
        int talkroom = talk.getRoomId();
        int numatt = talk.getAttendeeId().size();
        a = a + "\n Talk Title:" + talktitle + "\n This talk start at " + talktime + "\n This talk hold in room " + talkroom + "\n There are " + numatt + "attendees";}
        return a;
    }


    public String messageall(String a) {
        ArrayList<Integer> att = getallattendeev1(this.currSpeaker);
        if (att.size() == 0) {
            String response = "No Attendees";
            return response;
        }
        for (int i = 0; i < att.size(); i++) {
            int getterid = att.get(i);
            Account getter = loginM.getAccountWithId(getterid);
            Message msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
            this.currSpeaker.addSentMessage(msg.getmessageid());
            getter.addInbox(msg.getmessageid());

        }
        String response1 = "Message Send";
        return response1;
    }
    public String messagetotalks(String a, ArrayList<Integer> talkids) {
        ArrayList<Integer> att = new ArrayList<>();
        for(int i = 0; i < talkids.size(); i++) {att.addAll(getallattendeev2(currSpeaker).get(talkids.get(i)));}
        if (att.size() == 0) {
            String response = "No Attendees";
            return response;
        }
        for (int i = 0; i < att.size(); i++) {
            int getterid = att.get(i);
            Account getter = loginM.getAccountWithId(getterid);
            Message msg = MsgM.createmessage(currSpeaker.getUserId(), getterid, a);
            this.currSpeaker.addSentMessage(msg.getmessageid());
            getter.addInbox(msg.getmessageid());

        }
        String response1 = "Message Send";
        return response1;

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