package Controller;
import Entity.Account;
import Entity.Speaker;
import Entity.Talk;
import UseCase.LoginManager;
import UseCase.TalkManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SpeakerSystem {
    protected LoginManager loginM;
    protected TalkManager talkManager;
    protected Speaker currSpeaker;

    public SpeakerSystem(LoginManager loginM, TalkManager TalkM) {
        this.loginM = loginM;
        this.talkManager = TalkM;
        this.currSpeaker = (Speaker)loginM.getCurrAccount(); //Casting not finished

        }

    public void messageallv1(String a){
        ArrayList<Integer> att = getallattendeev1(this.currSpeaker);



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