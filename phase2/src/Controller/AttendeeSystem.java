package Controller;
import Presenters.AttendeeP;
import UseCase.*;

import java.util.ArrayList;


public class AttendeeSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected AttendeeP attendeeP;
    protected StrategyManager strategyM;
    protected AttendeeManager attendeeM;
    protected RoomManager roomM;
    protected RequestManager reM;
    protected Attendeesystemhandler ah;
    protected ApplicationManager appM;

    public AttendeeSystem(AccountManager accM, EventManager TalkM, MessageManager MsgM, AttendeeP attendeeP,
                          StrategyManager StrategyManager, AttendeeManager AttendeeM, RoomManager roomM,
                          RequestManager reM, ApplicationManager appM) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.attendeeP = attendeeP;
        this.strategyM = StrategyManager;
        this.attendeeM = AttendeeM;
        this.roomM = roomM;
        this.reM = reM;
        this.ah = new Attendeesystemhandler(MsgM, accM, TalkM, MsgM, attendeeP, StrategyManager, attendeeM, roomM,reM);
        this.appM = appM;
    }

    /**
     * Run Attendee System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            attendeeP.startup();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(6), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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
            attendeeP.appmain();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:" );
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


    private void EventDashboard(){
        int userChoice;
        do{
            attendeeP.eventmain();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(4), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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
                break;
            case 5:
                break;
        }
    }
    private void SignupDashboard(){
        int userChoice;
        do{
            attendeeP.eventselect();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(3), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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

    private void MsgDashboard(){
        int userChoice;
        do{
            attendeeP.msgSelect();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            msgOP1(userChoice);
        } while (userChoice != 3);


    }
    ////////////////////////////////
    private void msgOP1(int userChoice) {
        switch (userChoice) {
            case 1:
                read();
                break;
            case 2:
                message();
                break;
            case 3:
                break;
        }
    }

    private void message() {
        int userChoice;
        do {
            attendeeP.messaging();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(4), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            messaging(userChoice);
        } while (userChoice != 5);
    }

    private void messaging(int userChoice) {
        switch (userChoice) {
            case 1:
                msgToAttendee();
                break;
            case 2:
                msgToSpeaker();
                break;
            case 3:
                replytomsg();
                break;
            case 4:
                msgtoreply();
                break;
            case 5:
                break;
        }
    }

    private void read() {
        int userChoice;
        do {
            attendeeP.reading();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(7), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            reading(userChoice);
        } while (userChoice != 4);
    }

    private void reading(int userChoice) {
        switch (userChoice) {
            case 1:
                readAllMsg();
                break;
            case 2:
                allUnreadMsg();
                break;
            case 3:
                readarchived();
                break;
            case 4:
                break;
        }
    }
    //////////////////////////////////

    private void MyTalksDashboard(){
        ah.readAllMyTalks();
        attendeeP.askForBack();
    }

    private void readrepandmsg(){
        readallreply();
        attendeeP.announcemsg();

    }

    private void readmsgandrep(){
        readallmsg();
        attendeeP.announcereply();
    }

    private void readallmsg(){
        String a = MsgM.formatmsgget(attendeeM.getinbox());
        attendeeP.show(a);
    }

    private void readallreply(){
        String a = MsgM.formatreply(attendeeM.getmsgsend());
        attendeeP.show(a);
    }


    private void msgtoreply(){
        int tmsgid;
        do{
            readmsgandrep();
            tmsgid = ah.targetmsg();
            if (tmsgid != -1){
                String info = "Please Enter Your Message.\n " +
                        "(End editing by typing a single \"end\" in a new line.)";
                String txt = attendeeP.enterMessage(info);
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                attendeeP.askForBack();
            }
        }while(tmsgid != -1);
    }


    private void replytomsg(){
        int targetId;
        do{
            readrepandmsg();
            targetId = ah.targetgetter();
            if (targetId != -1){
                String info = "Please Enter Your Message.\n " +
                        "(End editing by typing a single \"end\" in a new line.)";
                String txt = attendeeP.enterMessage(info);
                ah.messageToAtt(txt, targetId);
                attendeeP.askForBack();
            }
        }while(targetId != -1);
    }



    ///// Louisa Modified
    private void signUpMyNewTalks(int a){
        int input;
        do{
            attendeeP.signUpTalk(a);
            ah.readAllAvailableTalks(a);
            input = ah.targetTalksSignUp(a);
            if (input != -1){
                if(eventManager.checkVIP(input)){
                    attendeeP.signUpVipTalk();
                    if(attendeeM.getCurrAttendee().getUserType() == 3){
                        attendeeM.enrol(input);
                        eventManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                        attendeeP.signUpSuc();
                    }else{
                        attendeeP.informNotVip();}
                }else if(!eventManager.checkVIP(input)){
                    attendeeM.enrol(input);
                    eventManager.addAttendeev2(input, attendeeM.getCurrAttendee());
                    attendeeP.signUpSuc();
                }
            }
        }while(input != -1);
    }

    private void cancelMyTalks(){
        int input;
        do{
            attendeeP.cancelTalk();
            ah.readAllMyTalks();
            input = ah.targetTalksCancel();
            if (input != -1){
                attendeeM.drop(input);
                eventManager.removeAttendeev2(input, attendeeM.getCurrAttendee());
                attendeeP.cancelSuc();
            }
        }while(input != -1);


    }

    // Request

    private void Request(){
        int userChoice;
        do {
            attendeeP.requestdb();
            userChoice = attendeeP.chooseOption(attendeeP.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            requestop(userChoice);
        } while (userChoice != 3);
    }

    private void requestop(int userChoice){
        switch (userChoice){
            case 1:
                Readallrequest();
                break;
            case 2:
                Sendnewrequest();
                break;
            case 3:
                break;
        }}

    private void Readallrequest(){

        attendeeP.show(reM.showallre(accM.getCurrAccountId()));
        attendeeP.askForBack();
    }

    private void Sendnewrequest(){
        int targettalk;
        do{
            ah.readalltalkssimp();
            targettalk = ah.targettalks();
            if (targettalk != -1){
                String info = "Please Enter Your Message.\n " +
                        "(End editing by typing a single \"end\" in a new line.)";
                String txt = attendeeP.enterMessage(info);
                ah.requestfortalk(txt, targettalk);
                attendeeP.askForBack();
            }
        } while (targettalk != -1);
    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            ah.readAllAttendees();
            tAttendeeId = ah.targetgetter();
            if (tAttendeeId != -1){
                String info = "Please Enter Your Message.\n " +
                        "(End editing by typing a single \"end\" in a new line.)";
                String txt = attendeeP.enterMessage(info);
                ah.messageToAtt(txt, tAttendeeId);
                attendeeP.askForBack();
            }
        }while(tAttendeeId != -1);
    }


    private void msgToSpeaker(){
        int tSpeakerId;
        do{
            ah.readAllSpeakers();
            tSpeakerId = ah.targetgetter();
            if (tSpeakerId != -1){
                String info = "Please Enter Your Message.\n " +
                        "(End editing by typing a single \"end\" in a new line.)";
                String txt = attendeeP.enterMessage(info);
                ah.messageToSp(txt, tSpeakerId);
                attendeeP.askForBack();
            }
        }while(tSpeakerId != -1);
    }


    private void readAllMsg() {

        int messageID;
        ArrayList<Integer> inbox = attendeeM.getInbox();

        if (inbox.size() != 0) {
            do {
                String a = MsgM.formatmsgget(attendeeM.getInbox());
                attendeeP.show(a);
                messageID = ah.targetmsg();
                if (messageID != -1) {
                    attendeeP.show(MsgM.getString(messageID));
                    attendeeM.markAsRead(accM.getCurrAccountId(), messageID);
                    ah.askToAchieve(messageID);
                }
            } while (messageID != -1);
        } else {
            attendeeP.announceEmptyInbox();
            attendeeP.askForBack();
        }
    }
    private void allUnreadMsg() {
        int tmsgid;
        do {
            ah.readAllUnreadMsg();
            tmsgid = ah.targetunread();
            if (tmsgid != -1) {
                attendeeP.show(MsgM.formatmsg(tmsgid));
                attendeeM.markAsRead(accM.getCurrAccountId(), tmsgid);
                attendeeP.unreadSuccess(tmsgid);
                attendeeP.askForBack();
            }

        } while (tmsgid != -1);
    }

    private void readarchived() {
        int tmsgid;
        do {
            ah.readAllarchivedMsg();
            tmsgid = ah.targetarchived();
            if (tmsgid != -1) {
                attendeeP.show(MsgM.formatmsg(tmsgid));
                attendeeP.askForBack();
            }
        } while (tmsgid != -1);
    }
    //////////////////////APPLICATION
    private void myapp(){
        int a = accM.getmyapp();
        String b;
        if(a == -1){
            b = "You don't have any processing application";
        }
        else {
            b = appM.formatInfoToAttendee(a);
        }
        attendeeP.show(b);
        attendeeP.askForBack();
    }

    private void newapp(){
        int input = attendeeP.checkapply();
        if (input == 1){
            String info = "Please Enter Why You Apply.\n " +
                    "(End editing by typing a single \"end\" in a new line.)";
            String text = attendeeP.enterMessage(info);
            int a = appM.createApplication(accM.getCurrAccountId(), accM.getUserName(accM.getCurrAccountId()), text);
            accM.changemyapp(a);
            attendeeP.appsend();
            attendeeP.askForBack();

        }}

    ///////////////////////

}




