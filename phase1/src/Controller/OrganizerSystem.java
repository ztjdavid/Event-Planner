package Controller;
import Entity.Organizer;
import UI.OrganizerUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OrganizerSystem {
    protected AccountManager accM;
    protected MessageManager MsgM;
    protected Organizer currOrganizer;
    protected StrategyManager strategyM;
    protected OrganizerUI organizerUI;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;
    protected TalkManager tlkM;
    protected RoomManager roomM;

    public OrganizerSystem(AccountManager accM, MessageManager MsgM, OrganizerUI organizerUI, StrategyManager strategyM,
                           OrganizerManager ognM, SpeakerManager spkM, TalkManager tlkM, RoomManager roomM) {
        this.accM = accM;
        this.MsgM = MsgM;
        this.currOrganizer = (Organizer) accM.getCurrAccount();
        this.strategyM = strategyM;
        this.organizerUI = organizerUI;
        this.ognM = ognM;
        this.spkM = spkM;
        this.tlkM = tlkM;
        this.roomM = roomM;
    }

    public void run(){
        int userInput = -1;
        while (userInput != 5) {
            organizerUI.startup();
            userInput = chooseMode1();
            switch (userInput){
                case 1:
                    int userInput2 = -1;
                    while (userInput2 != 5){
                        organizerUI.messaging();
                        userInput2 = chooseMode1();
                        switch (userInput2){
                            case 1:
                                readAllAtt();
                                int response1 = getResponse();
                                String txt1 = organizerUI.enteringText();
                                messageToIndividual(txt1, response1);
                                break;
                            case 2:
                                readAllSpk();
                                int response2 = getResponse();
                                String txt2 = organizerUI.enteringText();
                                messageToIndividual(txt2, response2);
                                break;
                            case 3:
                                String txt3 = organizerUI.enteringText();
                                messageToAllAttendee(txt3);
                                break;
                            case 4:
                                String txt4 = organizerUI.enteringText();
                                messageToAllSpeaker(txt4);
                                break;
                            default:
                                break;
                        }
                    }
                    organizerUI.messageToDisplay(4);
                case 2:
                    int userInput4 = -1;
                    while(userInput4 != 4){
                        userInput4 = chooseMode4();
                        switch (userInput4){
                            case 1:
                                int roomID = organizerUI.getRoomID();
                                int speakerID = organizerUI.getSpeakerID();
                                int talkID = organizerUI.getTalkID();
                                scheduleRoom(roomID, speakerID, talkID);
                                break;
                            case 2:
                                int roomID2 = organizerUI.getRoomID();
                                int speaker2ID = organizerUI.getSpeakerID();
                                int oldTalkID = organizerUI.getOldTalkID();
                                int newTalkID = organizerUI.getNewTalkID();
                                rescheduleSpeaker(speaker2ID , oldTalkID, newTalkID, roomID2);
                                break;
                        }
                    }
                case 3:
                    String username = organizerUI.getSpeakerUsername();
                    int speakerID = createSpeaker(username);
                    int userInput3 = -1;
                    while (userInput3 != 3) {
                        userInput3 = chooseMode3();
                        switch (userInput3) {
                            case 1:
                                HashMap<Integer, Object> accList = new HashMap<>(accM.getAccountList());
                                String talkTitle = organizerUI.getTalkTitle();
                                int talkTime = organizerUI.getTalkStartTime();
                                int talkRoomID = organizerUI.getTalkRoomID();
                                int talkID = tlkM.createTalk(TalkManager.getTotalTalkCount() + 1, talkTitle, talkTime, talkRoomID, speakerID);
                                if(talkID == -1){
                                    organizerUI.messageToDisplay(10);
                                }else {
                                    spkM.registerNewTalk(talkID, speakerID, accList);
                                    organizerUI.messageToDisplay(11);
                                }
                                break;
                            case 2:
                                break;
                        }
                    }
                default:
                    break;
            }
        }
        organizerUI.messageToDisplay(5);
    }

    public void rescheduleSpeaker(int speakerID, int currentTalkID, int rescheduledTalkID, int rescheduleRoomID){
        HashMap<Integer, Object> accList = new HashMap<>(accM.getAccountList());
        for(int item:spkM.getTalkList(speakerID, accList)){
            if(item == currentTalkID){
                tlkM.removeTalk(item);
            }
        }
        scheduleRoom(rescheduleRoomID, speakerID, rescheduledTalkID);
    }

    private void scheduleRoom(int roomID, int speakerID, int talkID){
        HashMap<Integer, Object> accList = new HashMap<>(accM.getAccountList());
        if(!roomM.isOccupiedAt(roomID, tlkM.getStartTime(talkID)) && !spkM.checkTalk(speakerID, talkID, accList)) {
            for (int item : spkM.getTalkList(speakerID, accList)) {
                if (item == tlkM.getStartTime(talkID)) {
                    organizerUI.messageToDisplay(12);
                    break;
                }/*查看Speaker现有talk的时间和当前talk是否会重合*/
            }
            tlkM.setSpeakerTo(speakerID, talkID);
            roomM.scheduleTalk(roomID, talkID, tlkM.getStartTime(talkID));
            organizerUI.messageToDisplay(13);
        }else{
            organizerUI.messageToDisplay(14);
        }
    }

    private int createSpeaker(String username){
        String password1 = organizerUI.getSpeakerpwd1();
        String password2 = organizerUI.getSpeakerpwd2();
        if(password1.equals(password2)){
            accM.createAccount(username, password1, 2);
            organizerUI.messageToDisplay(9);
            return accM.getTotalNumOfAccount();
        }else{
            organizerUI.messageToDisplay(7);
            createSpeaker(username);
        }
        return -1;
    }

    private int chooseMode1() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        int mode = -1;
        boolean valid = false;
        while (!valid) {
            String userInput = organizerUI.getRequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);
            }
        }
        return mode;
    }

    private int chooseMode3() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2));
        int mode = -1;
        boolean valid = false;
        while (!valid) {
            String userInput = organizerUI.addTalkPrompt();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);
            }
        }
        return mode;
    }

    private int chooseMode4() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2));
        int mode = -1;
        boolean valid = false;
        while (!valid) {
            String userInput = organizerUI.scheduleMode();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);
            }
        }
        return mode;
    }

    public ArrayList<Account> attendeeList() {
        ArrayList<Account> attendeeList = new ArrayList<>();
        for(Account acc:loginM.getAccountList().values()){
            if(acc.getUserType() == 1) {
                attendeeList.add(acc);
            }
        }
        return attendeeList;
    }

    private void readAllAtt() {
        ArrayList<Account> attendees = attendeeList();
        organizerUI.displayAllAttendees(attendees);
    }

    private void readAllSpk() {
        HashMap<Integer, String> speakers = spkM.getAllSpeaker();
        organizerUI.displayAllSpeakers(speakers);
    }

    private int getResponse(){
        ArrayList<Integer> validChoices = new ArrayList<>();
        for(Account acc:attendeeList()){
            validChoices.add(acc.getUserId());
        }
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = organizerUI.getRequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    public void messageToIndividual(String str, int receiverID) {
        Account receiver = loginM.getAccountWithId(receiverID);
        if(receiver.getUserType() == 2 || receiver.getUserType() == 1){
            Message msg = MsgM.createmessage(currOrganizer.getUserId(), receiverID, str);
            this.currOrganizer.addSentMessage(msg.getmessageid());
            receiver.addInbox(msg.getmessageid());
            organizerUI.messageToDisplay(1);
        }else{
            organizerUI.messageToDisplay(3);
        }
    }

    public void messageToAllSpeaker(String str) {
        for(Account speaker:speakerList()){
                Message msg = MsgM.createmessage(currOrganizer.getUserId(), speaker.getUserId(), str);
                this.currOrganizer.addSentMessage(msg.getmessageid());
                speaker.addInbox(msg.getmessageid());
        }
        organizerUI.messageToDisplay(2);
    }

    public void messageToAllAttendee(String str){
        for(Account attendee:attendeeList()){
            Message msg = MsgM.createmessage(currOrganizer.getUserId(), attendee.getUserId(), str);
            this.currOrganizer.addSentMessage(msg.getmessageid());
            attendee.addInbox(msg.getmessageid());
        }
        organizerUI.messageToDisplay(2);
    }




}