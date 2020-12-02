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

    public VipSystem(AccountManager accM, EventManager TalkM, MessageManager MsgM, VipUI vipUI,
                          StrategyManager StrategyManager, VIPManager vipM, RoomManager roomM) {
        this.accM = accM;
        this.eventManager = TalkM;
        this.MsgM = MsgM;
        this.vipUI = vipUI;
        this.strategyM = StrategyManager;
        this.vipM = vipM;
        this.roomM = roomM;

    }

    /**
     * Run Attendee System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            vipUI.startup();
            userChoice = chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    //Helper methods:
    private void enterBranch(int userChoice){
        switch (userChoice){
            case 1:
                MyTalksDashboard();
                break;
            case 2:
                EventDashboard();
                break;
            case 3:
                cancelMyTalks();
                break;
            case 4:
                MsgDashboard();
                break;
            case 5:
                break;
        }
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            vipUI.msgSelect();
            userChoice = chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 6);


    }
    /////event ERIC
    private void EventDashboard(){
        int userChoice;
        do{
            vipUI.eventselect();
            userChoice = chooseMode3();
            EventOp(userChoice);
        } while (userChoice != 7);
    }

    private void EventOp(int userChoice){
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


    ///////////////

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

    private int chooseMode1(){    //For Attendee Dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int chooseMode2(){    //For MsgDashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int chooseMode3(){    //For EventDashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private void MyTalksDashboard(){
        readAllMyTalks();
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
        String a = MsgM.formatmsgget(vipM.getinbox());
        vipUI.show(a);
    }

    private void readallreply(){
        String a = MsgM.formatreply(vipM.getmsgsend());
        vipUI.show(a);
    }


    private int targetmsg(){
        ArrayList<Integer> validChoices = vipM.getinbox();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void msgtoreply(){
        int tmsgid;
        do{
            readmsgandrep();
            tmsgid = targetmsg();
            if (tmsgid != -1){
                String txt = enterTxt();
                MsgM.setreply(tmsgid, txt, accM.getCurrAccountName());
                vipUI.askForBack();
            }
        }while(tmsgid != -1);
    }

    private int targetgetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.addAll(getAllSpeakers());
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest(2);
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void replytomsg(){
        int targetId;
        do{
            readrepandmsg();
            targetId = targetgetter();
            if (targetId != -1){
                String txt = enterTxt();
                messageToAtt(txt, targetId);
                vipUI.askForBack();
            }
        }while(targetId != -1);
    }
    ///////////ERICMODIFY
    private void readAllMyTalks(){
        StringBuilder a = new StringBuilder("My signed up talks:");
        ArrayList<Integer> allTalks = vipM.getAllMyTalksId();
        for(Integer t:allTalks){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.gettalkinfoWithName(t, roomName));}
        vipUI.show(a.toString());
    }

    /**
     * Get all available events that this account can attend.
     * @return A tlkList containing all events this account can attend.
     */
    private ArrayList<Integer> getAllAvailableTalks(int a){
        ArrayList<Integer> myTalksId = vipM.getAllMyTalksId();
        ArrayList<Integer> allTalksId = eventManager.getListOfEventsByType(a);
        ArrayList<Integer> result = new ArrayList<>();
        for(Integer t:allTalksId){
            if (!myTalksId.contains(t) && (eventManager.getRemainingSeats() > 0 )) result.add(t);
            // Boolean is_VIP = false;
            // if (accM.isVIPAcc(accM.getCurrAccountId())) is_VIP = true;
            // TODO: add a checker for VIP event, waiting for event flag var update.
            // if event t is vip, remove t from result if cur_acc is not a VIP.
        }
        return result;
    }

    private void readAllAvailableTalks(int type){
        StringBuilder a = new StringBuilder("Available Event: ");
        ArrayList<Integer> availableTalksId = getAllAvailableTalks(type);
        for(Integer t:availableTalksId){
            String roomName = roomM.getRoomName(eventManager.getRoomIdWithId(t));
            a.append(eventManager.gettalkinfoWithName(t, roomName));
        }
        vipUI.show(a.toString());}


    private int targetTalksSignUp(int type){
        ArrayList<Integer> validChoices = getAllAvailableTalks(type);
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    ///// Louisa Modified
    private void signUpMyNewTalks(int a){
        int input;
        do{
            vipUI.signUpTalk();
            readAllAvailableTalks(a);
            input = targetTalksSignUp(a);
            if (input != -1){
                if(eventManager.checkVIP(input)){
                    vipUI.signUpVipTalk();
                    if(vipM.getCurrAttendee().getUserType() == 3){
                        vipM.enrol(input);
                        eventManager.addAttendeev2(input, vipM.getCurrAttendee());
                        vipUI.signUpSuc();
                    }else{vipUI.informNotVip();}
                }else if(!eventManager.checkVIP(input)){
                    vipM.enrol(input);
                    eventManager.addAttendeev2(input, vipM.getCurrAttendee());
                    vipUI.signUpSuc();
                }
            }
        }while(input != -1);
    }

    private int targetTalksCancel(){
        ArrayList<Integer> validChoices = vipM.getAllMyTalksId();
        validChoices.add(-1);
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = vipUI.getrequest3();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    private void cancelMyTalks(){
        int input;
        do{
            vipUI.cancelTalk();
            readAllMyTalks();
            input = targetTalksCancel();
            if (input != -1){
                vipM.drop(input);
                eventManager.removeAttendeev2(input, vipM.getCurrAttendee());
                vipUI.cancelSuc();
            }
        }while(input != -1);


    }

    private void msgToAttendee(){
        int tAttendeeId;
        do{
            readAllAttendees();
            tAttendeeId = targetGetter();
            if (tAttendeeId != -1){
                String txt = enterTxt();
                messageToAtt(txt, tAttendeeId);
                vipUI.askForBack();
            }
        }while(tAttendeeId != -1);
    }

    private int targetGetter(){
        ArrayList<Integer> validChoices = getAllAttendees();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest1();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
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
            readAllSpeakers();
            tSpeakerId = targetSpeaker();
            if (tSpeakerId != -1){
                String txt = enterTxt();
                messageToSp(txt, tSpeakerId);
                vipUI.askForBack();
            }
        }while(tSpeakerId != -1);
    }
    private void messageToSp(String a, int speakerId) {
        int msg = MsgM.createmessage(accM.getCurrAccountName(), accM.getCurrAccountId(), speakerId, a);
        accM.addinbox(speakerId, msg);
        accM.addsend(accM.getCurrAccountId(), msg);
        vipUI.messagesend();
    }

    private int targetSpeaker(){
        ArrayList<Integer> validChoices = getAllSpeakers();
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do{
            userInput = vipUI.getrequest1();
            if (!strategyM.isValidChoice(userInput, validChoices))
                vipUI.informinvalidchoice();
            else { valid = true; }
        }while(!valid);
        return Integer.parseInt(userInput);
    }

    private void readAllMsg(){

        String a = MsgM.formatmsgget(vipM.getInbox());
        vipUI.show(a);
        vipUI.askForBack();

    }

    //this is a helper function to get a list of all attendees except itself in current attendee signed up talks
    private ArrayList<Integer> getAllAttendees() {
        ArrayList<Integer> talkList = vipM.getAllMyTalksId();
        ArrayList<Integer> result = eventManager.getallattendee(talkList);
        int currAcc = accM.getCurrAccountId();
        if (result.contains(currAcc)) result.remove(Integer.valueOf(currAcc));
        return result;


    }

    private ArrayList<Integer> getAllSpeakers() {
        ArrayList<Integer> talkList = vipM.getAllMyTalksId();
        return eventManager.getAllSpeakers(talkList);
    }

    private void readAllAttendees(){
        ArrayList<Integer> att = getAllAttendees();
        StringBuilder a = new StringBuilder("These are the attendees who attend your signed up talks. Choose an id to message:\n");
        for(Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        vipUI.show(a.toString());
    }

    private void readAllSpeakers(){
        ArrayList<Integer> allTalks = vipM.getAllMyTalksId();
        StringBuilder a = new StringBuilder("These are the speakers in talks you attend. Choose an id to message:\n");
        for (Integer t: allTalks){
            ArrayList<Integer> spkLst = new ArrayList<>(eventManager.getSpeakerIDIn(t));
            for(int speaker:spkLst){
                String each = "(" + eventManager.getTitle(t) + ")" + accM.getinfoacc(speaker);
                a.append(each);
            }
        }
        vipUI.show(a.toString());
    }
    private String enterTxt(){
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        vipUI.informEnteringText();
        do{
            String line = vipUI.getLineTxt();
            if (line.equals("end")) exit = true;
            else{
                a.append(line);
                a.append("\n");
            }
        } while(!exit);
        return a.toString();
    }

}