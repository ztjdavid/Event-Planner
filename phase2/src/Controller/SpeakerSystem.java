package Controller;
import Presenters.SpeakerP;
import UseCase.*;

import java.util.ArrayList;


public class SpeakerSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected SpeakerP speakerP;
    protected StrategyManager strategyM;
    protected SpeakerManager SpeakerM;
    protected RoomManager roomM;
    protected RequestManager reM;
    protected SpeakerSystemHandler sh;


    public SpeakerSystem(AccountManager accountManager, EventManager eventM, MessageManager messageManager, SpeakerP SpeakerP,
                         StrategyManager StrategyManager, SpeakerManager speakerManager, RoomManager roomManager, RequestManager requestManager) {
        this.accM = accountManager;
        this.eventManager = eventM;
        this.MsgM = messageManager;
        this.speakerP = SpeakerP;
        this.strategyM = StrategyManager;
        this.SpeakerM = speakerManager;
        this.roomM = roomManager;
        this.reM = requestManager;
        this.sh = new SpeakerSystemHandler(accM, eventManager, MsgM, speakerP, strategyM, SpeakerM, roomM, reM);

    }

    /**
     * Run Speaker System, user can choose according to startup options.
     */
    public void run(){
        int userChoice;
        do{
            speakerP.startup();
            userChoice = speakerP.chooseOption(speakerP.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            enterBranch(userChoice);
        } while (userChoice != 3);
    }


    //Methods for controlling program process:
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

    private void Readallevent(){
        sh.readalltalks();
        speakerP.askForBack();
    }
    /////////////////////EVENT DB//////////////
    private void EventDashboard(){
        int userChoice;
        do {
            speakerP.eventdb();
            userChoice = speakerP.chooseOption(speakerP.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            eventop(userChoice);
        } while (userChoice != 3);
    }

    private void eventop(int userChoice){
        switch (userChoice){
            case 1:
                Readallevent();
                break;
            case 2:
                Request();
                break;
            case 3:
                break;
    }}
////////////////REQUEST///////////////
    private void Request(){
        int userChoice;
        do {
            speakerP.requestdb();
            userChoice = speakerP.chooseOption(speakerP.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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

        speakerP.show(reM.showallre(accM.getCurrAccountId()));
        speakerP.askForBack();
    }

    private void Sendnewrequest(){
        int targettalk;
        do{
            sh.readalltalkssimp();
            targettalk = sh.targettalks();
            if (targettalk != -1){
                String txt = sh.enterTxt();
                sh.requestfortalk(txt, targettalk);
                speakerP.askForBack();
            }
        } while (targettalk != -1);
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            speakerP.messagemain();
            userChoice = speakerP.chooseOption(speakerP.getchoicelist(2), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            msgOP1(userChoice);
        } while (userChoice != 3);


    }
    ////////////////////
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
            speakerP.messaging();
            userChoice = speakerP.chooseOption(speakerP.getchoicelist(3), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            messaging(userChoice);
        } while (userChoice != 6);
    }

    private void messaging(int userChoice) {
        switch (userChoice) {
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
                replytomsg();
                break;
            case 5:
                msgtoreply();
                break;
            case 6:
                break;
        }
    }

    private void read() {
        int userChoice;
        do {
            speakerP.reading();
            userChoice = speakerP.chooseOption(speakerP.getchoicelist(7), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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
    ////////////////////



    //Methods for doing a specific user operation.
    private void msgToAttendee(){
        int tAttendeeId;
        do{
            sh.readallatt();///////////
            tAttendeeId = sh.targetgetter();
            if (tAttendeeId != -1){
                String txt = sh.enterTxt();
                sh.messagetoatt(txt, tAttendeeId);
                speakerP.askForBack();
            }
        }while(tAttendeeId != -1);
    }

    private void msgToTalk(){
        int targettalk;
        do{
            sh.readalltalkssimp();////////////
            targettalk= sh.targettalks();
            if (targettalk != -1){
                String txt = sh.enterTxt();
                sh.messagetotalk(txt, targettalk);
                speakerP.askForBack();
            }
        } while (targettalk != -1);
    }

    private void msgToAllTalks(){
        int userChoice = sh.chooseMode3();
        if (userChoice == 1){
            String txt = sh.enterTxt();
            sh.messageall(txt);
            speakerP.askForBack();
        }
    }

    private void msgtoreply(){
        int tmsgid;
        do{
            sh.readmsgandrep();
            tmsgid = sh.targetmsg();
            if (tmsgid != -1){
                String txt = sh.enterTxt();
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                speakerP.askForBack();
            }
        }while(tmsgid != -1);
    }

    private void replytomsg(){
        int tAttendeeId;
        do{
            sh.readrepandmsg();
            tAttendeeId = sh.targetgetter();
            if (tAttendeeId != -1){
                String txt = sh.enterTxt();
                sh.messagetoatt(txt, tAttendeeId);
                speakerP.askForBack();
            }
        }while(tAttendeeId != -1);
    }
    ///////////////////////////////////
    private void readAllMsg() {

        int messageID;
        ArrayList<Integer> inbox = SpeakerM.getinbox();

        if (inbox.size() != 0) {
            do {
                String a = MsgM.formatmsgget(SpeakerM.getinbox());
                speakerP.show(a);
                messageID = sh.targetmsg();
                if (messageID != -1) {
                    speakerP.show(MsgM.getString(messageID));
                    SpeakerM.markAsRead(accM.getCurrAccountId(), messageID);
                    sh.askToAchieve(messageID);
                }
            } while (messageID != -1);
        } else {
            speakerP.announceEmptyInbox();
            speakerP.askForBack();
        }
    }

    private void allUnreadMsg() {
        int tmsgid;
        do {
            sh.readAllUnreadMsg();
            tmsgid = sh.targetunread();
            if (tmsgid != -1) {
                speakerP.show(MsgM.formatmsg(tmsgid));
                SpeakerM.markAsRead(accM.getCurrAccountId(), tmsgid);
                speakerP.unreadSuccess(tmsgid);
                speakerP.askForBack();
            }

        } while (tmsgid != -1);
    }

    private void readarchived() {
        int tmsgid;
        do {
            sh.readAllarchivedMsg();
            tmsgid = sh.targetarchived();
            if (tmsgid != -1) {
                speakerP.show(MsgM.formatmsg(tmsgid));
                speakerP.askForBack();
            }
        } while (tmsgid != -1);
    }



}