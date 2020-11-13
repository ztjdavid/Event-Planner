package Controller;
import Entity.*;
import UseCase.*;
import UseCase.LoginManager;

import java.util.ArrayList;

public class OrganizerSystem {
    protected LoginManager loginM;
    protected MessageManager MsgM;
    protected Organizer currOrganizer;
    protected ArrayList<Account> attendeeList;
    protected ArrayList<Account> speakerList;


    public OrganizerSystem(LoginManager loginM, MessageManager MsgM) {
        this.loginM = loginM;
        this.MsgM = MsgM;
        this.currOrganizer = (Organizer) loginM.getCurrAccount();
    }


    public void speakerList() {
        for(Account acc:loginM.getAccountList().values()){
            if(acc.getUserType() == 2) {
                this.speakerList.add(acc);
            }
        }
    }

    public void AttendeeList() {
        for(Account acc:loginM.getAccountList().values()){
            if(acc.getUserType() == 1) {
                this.attendeeList.add(acc);
            }
        }
    }

    public boolean messageToIndividual(String str, int receiverID) {
        Account receiver = loginM.getAccountWithId(receiverID);
        if(receiver.getUserType() == 2 || receiver.getUserType() == 1){
            Message msg = MsgM.createmessage(currOrganizer.getUserId(), receiverID, str);
            this.currOrganizer.addSentMessage(msg.getmessageid());
            receiver.addInbox(msg.getmessageid());
            return true;
        }else{
            return false;
        }
    }

    public boolean messageToAllSpeaker(String str) {
        for(Account speaker:this.speakerList){
                Message msg = MsgM.createmessage(currOrganizer.getUserId(), speaker.getUserId(), str);
                this.currOrganizer.addSentMessage(msg.getmessageid());
                speaker.addInbox(msg.getmessageid());
        }
        return true;
    }

    public boolean messageToAllAttendee(String str){
        for(Account attendee:this.attendeeList){
            Message msg = MsgM.createmessage(currOrganizer.getUserId(), attendee.getUserId(), str);
            this.currOrganizer.addSentMessage(msg.getmessageid());
            attendee.addInbox(msg.getmessageid());
        }
        return true;
    }




}