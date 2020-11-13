package Controller;
import Entity.Account;
import Entity.Message;
import Entity.Speaker;
import Entity.Talk;
import UseCase.LoginManager;
import UseCase.TalkManager;
import UseCase.MessageManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SpeakerSystem {
    protected LoginManager loginM;
    protected TalkManager talkManager;
    protected Speaker currSpeaker;
    protected MessageManager MsgM;

    public SpeakerSystem(LoginManager loginM, TalkManager TalkM, MessageManager MsgM) {
        this.loginM = loginM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.currSpeaker = (Speaker) loginM.getCurrAccount(); //Casting not finished

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
    public ArrayList<Talk> getalltalks() {
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