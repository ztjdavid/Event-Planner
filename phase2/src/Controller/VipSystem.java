package Controller;
import Presenters.AttendeeUI;
import Presenters.VipUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;


public class VipSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected VipUI vipUI;
    protected StrategyManager strategyM;
    protected VIPManager vipM;
    protected RoomManager roomM;
    protected VIPsystemhandler vh;

    public VipSystem(AccountManager accM, EventManager TalkM, MessageManager MsgM, VipUI vipUI,
                          StrategyManager StrategyManager, VIPManager vipM, RoomManager roomM, VIPsystemhandler vh) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.vipUI = vipUI;
        this.strategyM = StrategyManager;
        this.vipM = vipM;
        this.roomM = roomM;
        this.vh = vh;

    }

    /**
     * Run VIP System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            vipUI.startup();
            userChoice = vh.chooseMode1();
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


    private void EventDashboard(){
        int userChoice;
        do{
            vipUI.eventmain();
            userChoice = vh.chooseMode4();
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
            vipUI.eventselect();
            userChoice = vh.chooseMode3();
            EventSignup(userChoice);
        } while (userChoice != 7);
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
                signUpMyNewTalks(3);
                break;
            case 5:
                signUpMyNewTalks(4);
                break;
            case 6:
                signUpMyNewTalks(5);
                break;
            case 7:
                break;
        }
    }
    private void MsgDashboard(){
        int userChoice;
        do{
            vipUI.msgSelect();
            userChoice = vh.chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 6);


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
                break;
        }
    }

    private void MyTalksDashboard(){
        vh.readAllMyTalks();
        vipUI.askForBack();
    }

    private void readrepandmsg(){
        readallreply();
        vipUI.announcemsg();

    }

    private void readmsgandrep(){
        readallmsg();
        vipUI.announcereply();
    }

    private void readallmsg(){
        String a = MsgM.formatmsgget(vipM.getInbox(accM.getCurrAccountId()));
        vipUI.show(a);
    }

    private void readallreply(){
        String a = MsgM.formatreply(vipM.getSentBox(accM.getCurrAccountId()));
        vipUI.show(a);
    }


    private void msgtoreply(){
        int tmsgid;
        do{
            readmsgandrep();
            tmsgid = vh.targetmsg();
            if (tmsgid != -1){
                String txt = vh.enterTxt();
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                vipUI.askForBack();
            }
        }while(tmsgid != -1);
    }


    private void replytomsg(){
        int targetId;
        do{
            readrepandmsg();
            targetId = vh.targetgetter();
            if (targetId != -1){
                String txt = vh.enterTxt();
                messageToAtt(txt, targetId);
                vipUI.askForBack();
            }
        }while(targetId != -1);
    }


    private void signUpMyNewTalks(int a){
        int input;
        do{
            vipUI.signUpTalk();
            vh.readAllAvailableTalks(a);
            input = vh.targetTalksSignUp(a);
            if (input != -1){
                if(eventManager.checkVIP(input)){
                    vipUI.signUpVipTalk();
                    if(vipM.getCurrVIP().getUserType() == 3){
                        vipM.enrolEvent(accM.getCurrAccountId(), input);
                        eventManager.addAttendeev2(input, vipM.getCurrVIP());
                        vipUI.signUpSuc();
                    }else{vipUI.informNotVip();}
                }else if(!eventManager.checkVIP(input)){
                    vipM.enrolEvent(accM.getCurrAccountId(), input);
                    eventManager.addAttendeev2(input, vipM.getCurrVIP());
                    vipUI.signUpSuc();
                }
            }
        }while(input != -1);
    }


    private void cancelMyTalks(){
        int input;
        do{
            vipUI.cancelTalk();
            vh.readAllMyTalks();
            input = vh.targetTalksCancel();
            if (input != -1){
                vipM.dropEvent(accM.getCurrAccountId(), input);
                eventManager.removeAttendeev2(input, vipM.getCurrVIP());
                vipUI.cancelSuc();
            }
        }while(input != -1);


    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            vh.readAllAttendees();
            tAttendeeId = vh.targetgetter();
            if (tAttendeeId != -1){
                String txt = vh.enterTxt();
                messageToAtt(txt, tAttendeeId);
                vipUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }


    private void messageToAtt(String a, int getterId) {

        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), getterId, a);
        accM.addinbox(getterId, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        vipUI.messagesend();
    }

    private void msgToSpeaker(){
        int tSpeakerId;
        do{
            vh.readAllSpeakers();
            tSpeakerId = vh.targetgetter();
            if (tSpeakerId != -1){
                String txt = vh.enterTxt();
                messageToSphelper(txt, tSpeakerId);
                vipUI.askForBack();
            }
        }while(tSpeakerId != -1);
    }

    private void messageToSphelper(String a, int speakerId) {
        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), speakerId, a);
        accM.addinbox(speakerId, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        vipUI.messagesend();
    }


    private void readAllMsg(){

        String a = MsgM.formatmsgget(vipM.getInbox(accM.getCurrAccountId()));
        vipUI.show(a);
        vipUI.askForBack();

    }


}