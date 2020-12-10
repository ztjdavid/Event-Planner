package Controller;
import Presenters.VipUI;
import UseCase.*;

import java.util.ArrayList;


public class VipSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected VipUI vipUI;
    protected StrategyManager strategyM;
    protected VIPManager vipM;
    protected RoomManager roomM;
    protected RequestManager ReqM;
    protected VIPsystemhandler vh;
    protected ApplicationManager appM;

    public VipSystem(AccountManager accM, EventManager TalkM, MessageManager MsgM, VipUI vipUI,
                          StrategyManager StrategyManager, VIPManager vipM, RoomManager roomM, RequestManager ReqM, ApplicationManager appM) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.vipUI = vipUI;
        this.strategyM = StrategyManager;
        this.vipM = vipM;
        this.roomM = roomM;
        this.ReqM = ReqM;
        this.appM = appM;
        this.vh = new VIPsystemhandler(accM, TalkM, MsgM, vipUI, StrategyManager, vipM, roomM, ReqM);

    }

    /**
     * Run VIP System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            vipUI.startup();
            userChoice = vipUI.chooseOption(vipUI.getchoicelist(6), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
            enterBranch(userChoice);
        } while (userChoice != 4);
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
                appDashboard();
                break;
            case 4:
                break;
        }
    }

    private void appDashboard() {
        int userChoice;
        do{
            vipUI.appmain();
            userChoice = vipUI.chooseOption(vipUI.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
            AppOp(userChoice);
        } while (userChoice != 3);
    }

    private void AppOp(int userChoice){
        switch (userChoice){
            case 1:
                myapp();
                break;
            case 2:
                newapp();
                break;
            case 3:
                break;
        }
    }


///////////EVENT DASH////////////
    private void EventDashboard(){
        int userChoice;
        do{
            vipUI.eventmain();
            userChoice = vipUI.chooseOption(vipUI.getchoicelist(4), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
            EventOp(userChoice);
        } while (userChoice != 5);
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
                Request();
            case 5:
                break;
        }
    }

    ///// Request Dashboard /////
    private void Request(){
        int userChoice;
        do{
            vipUI.requestmain();
            userChoice = vipUI.chooseOption(vipUI.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
            RequestOp(userChoice);
        } while (userChoice != 3);
    }

    private void RequestOp(int userChoice){
        switch (userChoice){
            case 1:
                ReadAllRequests();
            case 2:
                SendNewRequest();
            case 3:
                break;
        }
    }

    private void ReadAllRequests(){
        vipUI.show(ReqM.showallre(accM.getCurrAccountId()));
        vipUI.askForBack();
    }

    private void SendNewRequest(){
        int targetEvent;
        do{
            vh.readAllMyTalksSimp();
            targetEvent = vh.targetevent();
            if (targetEvent != -1){
                String text = vh.enterTxt();
                vh.requestForTalk(text, targetEvent);
                vipUI.askForBack();
            }
        }while(targetEvent != -1);
    }

    ////////////////////////////

    private void SignupDashboard(){
        int userChoice;
        do{
            vipUI.eventselect();
            userChoice = vipUI.chooseOption(vipUI.getchoicelist(2), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
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
            userChoice = vipUI.chooseOption(vipUI.getchoicelist(2), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
            msgOp(userChoice);
        } while (userChoice != 7);


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
    //////////////////////APPLICATION
    private void myapp(){
        Integer a = accM.getmyapp();
        String b;
        if(a == -1){
            b = "You don't have any processing application";
        }
        else {
            b = appM.formatInfoToAttendee(a);
        }
        vipUI.show(b);
        vipUI.askForBack();
    }

    private void newapp(){
        int input = vipUI.checkapply();
        if (input == 1){
            String info = "Please Enter Why You Apply.\n " +
                    "(End editing by typing a single \"end\" in a new line.)";
            String text = vipUI.enterMessage(info);
            int a = appM.createApplication(accM.getCurrAccountId(), accM.getUserName(accM.getCurrAccountId()), text);
            accM.changemyapp(a);
            vipUI.appsend();
            vipUI.askForBack();

    }}

    ///////////////////////

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

        int messageID;
        ArrayList<Integer> inbox = vipM.getInbox(accM.getCurrAccountId());

        if (inbox.size() != 0) {
            do {
                String a = MsgM.formatmsgget(vipM.getInbox(accM.getCurrAccountId()));
                vipUI.show(a);
                messageID = vh.targetmsg();
                if(messageID != -1){
                    vipUI.show(MsgM.getString(messageID));
                    vh.askToAchieve(messageID);
                }
            }while(messageID != -1);
        } else {
            vipUI.announceEmptyInbox();
            vipUI.askForBack();
        }
    }

    private void allUnreadMsg(){
        int tmsgid;
        do{
            vh.readAllUnreadMsg();
            tmsgid = vh.targetunread();
            if(tmsgid != -1){
                MsgM.readMessage(tmsgid);
                vipM.deleteUnreadInbox(tmsgid);
                vipUI.unreadSuccess(tmsgid);
                vipUI.askForBack();
            }

        }while(tmsgid != -1);
    }


}