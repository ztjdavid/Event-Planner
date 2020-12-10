package Controller;
import Presenters.SpeakerUI;
import UseCase.*;

import java.util.ArrayList;


public class SpeakerSystem {
    protected AccountManager accM;
    protected EventManager eventManager;
    protected MessageManager MsgM;
    protected SpeakerUI speakerUI;
    protected StrategyManager strategyM;
    protected SpeakerManager SpeakerM;
    protected RoomManager roomM;
    protected RequestManager reM;
    protected SpeakerSystemHandler sh;


    public SpeakerSystem(AccountManager accountManager, EventManager eventM, MessageManager messageManager, SpeakerUI SpeakerUI,
                         StrategyManager StrategyManager, SpeakerManager speakerManager, RoomManager roomManager, RequestManager requestManager) {
        this.accM = accountManager;
        this.eventManager = eventM;
        this.MsgM = messageManager;
        this.speakerUI = SpeakerUI;
        this.strategyM = StrategyManager;
        this.SpeakerM = speakerManager;
        this.roomM = roomManager;
        this.reM = requestManager;
        this.sh = new SpeakerSystemHandler(accM, eventManager, MsgM, speakerUI, strategyM, SpeakerM, roomM, reM);

    }

    /**
     * Run Speaker System, user can choose according to startup options.
     */
    public void run(){
        int userChoice;
        do{
            speakerUI.startup();
            userChoice = speakerUI.chooseOption(speakerUI.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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
        speakerUI.askForBack();
    }
    /////////////////////EVENT DB//////////////
    private void EventDashboard(){
        int userChoice;
        do {
            speakerUI.eventdb();
            userChoice = speakerUI.chooseOption(speakerUI.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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
            speakerUI.requestdb();
            userChoice = speakerUI.chooseOption(speakerUI.getchoicelist(1), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
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

        speakerUI.show(reM.showallre(accM.getCurrAccountId()));
        speakerUI.askForBack();
    }

    private void Sendnewrequest(){
        int targettalk;
        do{
            sh.readalltalkssimp();
            targettalk = sh.targettalks();
            if (targettalk != -1){
                String txt = sh.enterTxt();
                sh.requestfortalk(txt, targettalk);
                speakerUI.askForBack();
            }
        } while (targettalk != -1);
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            speakerUI.messaging();
            userChoice = speakerUI.chooseOption(speakerUI.getchoicelist(2), "Please Choose an Option:", "Invalid Choice! Please Try Again:");
            msgOp(userChoice);
        } while (userChoice != 8);


    }

    private void msgOp(int userChoice){
        switch (userChoice){
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
                allunreadmsg();
                break;
            case 7:
                sh.readArchived();
                break;
            case 8:
                break;
        }
    }


    //Methods for doing a specific user operation.
    private void msgToAttendee(){
        int tAttendeeId;
        do{
            sh.readallatt();///////////
            tAttendeeId = sh.targetgetter();
            if (tAttendeeId != -1){
                String txt = sh.enterTxt();
                sh.messagetoatt(txt, tAttendeeId);
                speakerUI.askForBack();
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
                speakerUI.askForBack();
            }
        } while (targettalk != -1);
    }

    private void msgToAllTalks(){
        int userChoice = sh.chooseMode3();
        if (userChoice == 1){
            String txt = sh.enterTxt();
            sh.messageall(txt);
            speakerUI.askForBack();
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
                speakerUI.askForBack();
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
                speakerUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }

    ///// Louisa added
    private void allunreadmsg(){
        int tmsgid;
        do{
            readAllUnreadMsg();
            tmsgid = targetunread();
            if(tmsgid != -1){
                MsgM.readMessage(tmsgid);
                speakerUI.unreadSuccess(tmsgid);
                speakerUI.askForBack();
            }

        }while(tmsgid != -1);
    }

    private void readAllUnreadMsg(){
        readAllUnread();
        speakerUI.annouceUnread();

    }

    private void readAllUnread(){
        String all = MsgM.formatAllUnread(sh.getAllUnread(SpeakerM.getCurrAccountId()));
        speakerUI.show(all);
    }

    private int targetunread(){
        ArrayList<Integer> validChoices = SpeakerM.getUnread();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = speakerUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                speakerUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

}