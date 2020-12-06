package Controller;
import Presenters.AttendeeUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;


public class AttendeeSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected AttendeeUI attendeeUI;
    protected StrategyManager strategyM;
    protected AttendeeManager attendeeM;
    protected RoomManager roomM;
    protected Attendeesystemhandler ah;

    public AttendeeSystem(AccountManager accM, EventManager TalkM, MessageManager MsgM, AttendeeUI attendeeUI,
                          StrategyManager StrategyManager, AttendeeManager AttendeeM, RoomManager roomM, Attendeesystemhandler ah) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.attendeeUI = attendeeUI;
        this.strategyM = StrategyManager;
        this.attendeeM = AttendeeM;
        this.roomM = roomM;
        this.ah = ah;

    }

    /**
     * Run Attendee System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            attendeeUI.startup();
            userChoice = ah.chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    //Helper methods:
    private void enterBranch(int userChoice){
        switch (userChoice){
            case 1:
                EventDashboard();
                break;
            case 2:
                MsgDashboard();
                break;
            case 3:
                break;
        }
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            attendeeUI.msgSelect();
            userChoice = ah.chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 7);


    }
    private void EventDashboard(){
        int userChoice;
        do{
            attendeeUI.eventmain();
            userChoice = ah.chooseMode4();
            EventOp(userChoice);
        } while (userChoice != 7);
    }
    private void EventOp(int userChoice){
        switch (userChoice){
            case 1:
                SignupDashboard();
                break;
            case 2:
                MyTalksDashboard();
                break;
            case 3:
                cancelMyTalks();
                break;
            case 4:
                break;
        }
    }
    private void SignupDashboard(){
        int userChoice;
        do{
            attendeeUI.eventselect();
            userChoice = ah.chooseMode3();
            EventSignup(userChoice);
        } while (userChoice != 4);
    }
    private void EventSignup(int userChoice){
        switch (userChoice){
            case 1:
                signUpMyNewTalks(1);
                break;
            case 2:
                signUpMyNewTalks(2);
                break;
            case 3:
                signUpMyNewTalks(0);
                break;
            case 4:
                break;
        }
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
                allUnreadMsg();
                break;
            case 7:
                break;
        }
    }

    private void MyTalksDashboard(){
        ah.readAllMyTalks();
        attendeeUI.askForBack();
    }

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
            tmsgid = ah.targetmsg();
            if (tmsgid != -1){
                String txt = ah.enterTxt();
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                attendeeUI.askForBack();
            }
        }while(tmsgid != -1);
    }


    private void replytomsg(){
        int targetId;
        do{
            readrepandmsg();
            targetId = ah.targetgetter();
            if (targetId != -1){
                String txt = ah.enterTxt();
                ah.messageToAtt(txt, targetId);
                attendeeUI.askForBack();
            }
        }while(targetId != -1);
    }

    ///// Louisa Modified
    private void signUpMyNewTalks(int a){
        int input;
        do{
            attendeeUI.signUpTalk(a);
            ah.readAllAvailableTalks(a);
            input = ah.targetTalksSignUp(a);
            if (input != -1){
                if(eventManager.checkVIP(input)){
                    attendeeUI.signUpVipTalk();
                    if(attendeeM.getCurrAttendee().getUserType() == 3){
                        attendeeM.enrol(input);
                        eventManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                        attendeeUI.signUpSuc();
                    }else{attendeeUI.informNotVip();}
                }else if(!eventManager.checkVIP(input)){
                    attendeeM.enrol(input);
                    eventManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                    attendeeUI.signUpSuc();
                }
            }
        }while(input != -1);
    }

    private void cancelMyTalks(){
        int input;
        do{
            attendeeUI.cancelTalk();
            ah.readAllMyTalks();
            input = ah.targetTalksCancel();
            if (input != -1){
                attendeeM.drop(input);
                eventManager.removeAttendeev2(input, attendeeM.getCurrAttendee());
                attendeeUI.cancelSuc();
            }
        }while(input != -1);


    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            ah.readAllAttendees();
            tAttendeeId = ah.targetgetter();
            if (tAttendeeId != -1){
                String txt = ah.enterTxt();
                ah.messageToAtt(txt, tAttendeeId);
                attendeeUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }


    private void msgToSpeaker(){
        int tSpeakerId;
        do{
            ah.readAllSpeakers();
            tSpeakerId = ah.targetgetter();
            if (tSpeakerId != -1){
                String txt = ah.enterTxt();
                ah.messageToSp(txt, tSpeakerId);
                attendeeUI.askForBack();
            }
        }while(tSpeakerId != -1);
    }


    private void readAllMsg(){

        int messageID;
        ArrayList<Integer> inbox = attendeeM.getInbox();

        if (inbox.size() != 0) {
            do {
                String a = MsgM.formatmsgget(attendeeM.getInbox());
                attendeeUI.show(a);
                messageID = targetmsg();
                if(messageID != -1){
                    attendeeUI.show(MsgM.getString(messageID));
                    ah.askToAchieve(messageID);
                }
            }while(messageID != -1);
        } else {
            attendeeUI.announceEmptyInbox();
            attendeeUI.askForBack();
        }


    }

///Grey modify

    private void allUnreadMsg(){
        int tmsgid;
        do{
            ah.readAllUnreadMsg();
            tmsgid = ah.targetunread();
            if(tmsgid != -1){
                MsgM.readMessage(tmsgid);
                attendeeM.deleteUnreadInbox(tmsgid);
                attendeeUI.unreadSuccess(tmsgid);
                attendeeUI.askForBack();
            }

        }while(tmsgid != -1);
    }

}



