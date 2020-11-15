package Controller;
import UI.OrganizerUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OrganizerSystem {
    protected AccountManager accM;
    protected MessageManager MsgM;
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
        this.strategyM = strategyM;
        this.organizerUI = organizerUI;
        this.ognM = ognM;
        this.spkM = spkM;
        this.tlkM = tlkM;
        this.roomM = roomM;
    }

    public void run() {
        int userChoice;
        do {
            organizerUI.startup();
            userChoice = chooseMode2();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    private void enterBranch(int userChoice) {
        switch (userChoice) {
            case 1:
                msgDashboard();
                break;
            case 2:
                speakerDashboard();
                break;
            case 3:
                talkDashBoard();
                break;
            case 4:
                roomDashBoard();
                break;
            case 5:
                break;
        }
    }

    private void roomDashBoard(){
        int userInput;
        do{
            organizerUI.messaging4();
            userInput = chooseMode3();
            roomOp(userInput);
        }while(userInput != 3);
    }

    private void roomOp(int userInput){
        switch (userInput){
            case 1:
                doCreateRoom();
                break;
            case 2:
                readAllRooms();
                break;
            case 3:
                break;
        }
    }

    private void doCreateRoom(){
        String roomName = organizerUI.getRoomName();
        int roomID = roomM.createRoom(roomName);
        organizerUI.message13(roomID);
        organizerUI.askForBack();
    }

    public void readAllRooms(){
        organizerUI.message6();
        ArrayList<Integer> allRooms = roomM.getAllRooms();
        for(int item : allRooms){
            String name = roomM.getRoomName(item);
            HashMap<Integer, Integer> timeTable = new HashMap<>(roomM.getTimeTable(item));
            organizerUI.readAllRooms(item, name, timeTable);
        }
        organizerUI.askForBack();
    }

    private void talkDashBoard(){
        int userInput;
        do{
            organizerUI.messaging3();
            userInput = chooseMode3();
            tlkOp(userInput);
        }while(userInput != 3);
    }

    private void tlkOp(int userInput){
        switch (userInput){
            case 1:
                doCreateTalk();
                break;
            case 2:
                readAllTalks();
                break;
            case 3:
                break;
        }
    }

    private void readAllTalks(){
        organizerUI.message7();
        ArrayList<Integer> talkLst = new ArrayList<>(tlkM.getAllTalksID());
        for(int item:talkLst){
            String title = tlkM.getTitle(item);
            int startTime = tlkM.getStartTime(item);
            int roomID = tlkM.getRoom(item);
            organizerUI.readTalks(title, item, startTime, roomID);
        }
        organizerUI.askForBack();
    }

    private void doCreateTalk(){
        String talkTitle = organizerUI.getTalkTitle();
        int startTime = organizerUI.getTalkStartTime();
        if(startTime < 9 || startTime > 17){
            organizerUI.errorMessage();
        }else {
            int roomID = organizerUI.getRoomID();
            int speakerID = organizerUI.getSpeakerID();
            int result = checkTalkValidity(roomID, startTime, speakerID);
            if (result == -1) {
                int talkID = tlkM.createTalk(talkTitle, startTime, roomID, speakerID);
                roomM.addNewTalkToRoom(talkID, startTime, roomID);
                spkM.registerNewTalk(talkID, speakerID);
                organizerUI.message12(talkID);
            } else if (result == 0) {
                organizerUI.message14();
            } else if (result == 1){
                organizerUI.message18();
            } else if (result == 2) {
                organizerUI.message15();
            }
        }
        organizerUI.askForBack();
    }

    private int checkTalkValidity(int roomID,int startTime, int speakerID){
        int flag = -1;
        if (!roomM.isValidRoomId(roomID)) return 1;
        if(roomM.getTimeTable(roomID).containsValue(startTime)){
            flag = 0;
        }
        for(int item:spkM.getTalkList(speakerID)){
            if(tlkM.getStartTime(item) == startTime){
                flag = 2;
            }
        }
    return flag;
    }

    private void speakerDashboard() {
        int userInput;
        do {
            organizerUI.messaging2();
            userInput = chooseMode3();
            sprOp(userInput);
        } while (userInput != 3);
    }

    private void sprOp(int userInput) {
        switch (userInput) {
            case 1:
                doCreateSpeaker();
                break;
            case 2:
                readAllSpeakers();
                break;
            case 3:
                break;
        }
    }

    private void readAllSpeakers(){
        organizerUI.message16();
        ArrayList<Integer> speakerList = ognM.getSpeakerList();
        for(int item: speakerList){
            String username = accM.getUserName(item);
            ArrayList<Integer> talks = new ArrayList<>(spkM.getTalkList(item));
            organizerUI.readSpeakers(username, item, talks);
        }
        organizerUI.askForBack();
    }

    private void doCreateSpeaker() {
        int ID = createSpeaker();
        if(ID != -1){
        organizerUI.message3(ID);
        }
        organizerUI.askForBack();
    }

    private void msgDashboard() {
        int userInput;
        do {
            organizerUI.messaging();
            userInput = chooseMode1();
            messageOp(userInput);
        } while (userInput != 8);
    }

    private void messageOp(int userInput) {
        switch (userInput) {
            case 1:
                messageToAttendee();
                break;
            case 2:
                messageToSpeaker();
                break;
            case 3:
                messageToAllSpeaker();
                break;
            case 4:
                messageToAllAttendee();
                break;
            case 5:
                replyToMsg();
                break;
            case 6:
                readAllMsg();
                break;
            case 7:
                msgToReply();
                break;
            case 8:
                break;
        }
    }

    private void msgToReply() {
        int getID;
        do {
            readMsgAndPrep();
            getID = targetGetter(1);
            if (getID != -1) {
                String txt = enterTxt();
                MsgM.setreply(getID, txt);
                organizerUI.askForBack();
            }
        } while (getID != -1);
    }

    private void readMsgAndPrep() {
        readAllOfMsg();
        organizerUI.announceReply();
    }

    private void readAllOfMsg() {
        String a = MsgM.formatmsgget(ognM.getInbox());
        organizerUI.show(a);
    }

    private void readAllMsg() {
        ArrayList<Integer> inboxID = new ArrayList<>(ognM.getInbox());
        if (inboxID.size() != 0) {
            organizerUI.display(MsgM.formatmsgget(inboxID));
        } else {
            organizerUI.message11();
        }
        organizerUI.askForBack();
    }

    private void replyToMsg() {
        int tAttendeeId;
        do {
            readMsg();
            tAttendeeId = targetGetter(1);
            if (tAttendeeId != -1) {
                String txt = enterTxt();
                messageToIndividual(txt, tAttendeeId);
                organizerUI.askForBack();
            }
        } while (tAttendeeId != -1);
    }

    private void readMsg() {
        readAllReply();
        organizerUI.announceMsg();
    }

    private void readAllReply() {
        String a = MsgM.formatreply(ognM.getMsgSend());
        organizerUI.show(a);
    }

    private void messageToAllSpeaker() {
        int userInput = chooseMode4();
        if (userInput == 1) {
            String txt = enterTxt();
            sendMessageToAllSpeaker(txt);
            organizerUI.askForBack();
        }
    }

    private void messageToAllAttendee() {
        int userChoice = chooseMode4();
        if (userChoice == 1) {
            String txt = enterTxt();
            sendMessageToAllAttendee(txt);
            organizerUI.askForBack();
        }
    }

    private void messageToSpeaker() {
        int spkId;
        do {
            readAllSpk();
            spkId = targetGetter(3);
            if (spkId != -1) {
                String txt = enterTxt();
                messageToIndividual(txt, spkId);
                organizerUI.askForBack();
            }
        } while (spkId != -1);
    }


    private void messageToAttendee() {
        int attId;
        do {
            readAllAtt();
            attId = targetGetter(2);
            if (attId != -1) {
                String txt = enterTxt();
                messageToIndividual(txt, attId);
                organizerUI.askForBack();
            }
        } while (attId != -1);
    }

    private String enterTxt() {
        StringBuilder a = new StringBuilder();
        boolean exit = false;
        organizerUI.infoEnteringText();
        do {
            String line = organizerUI.getLineTxt();
            if (line.equals("end")) exit = true;
            else {
                a.append(line);
            }
        } while (!exit);
        return a.toString();
    }

    private int createSpeaker() {
        String username = organizerUI.getSpeakerUsername();
        if (!accM.existsUsername(username)){
            String password1 = organizerUI.getSpeakerPwd1();
            String password2 = organizerUI.getSpeakerPwd2();
            if (password1.equals(password2)) {
                accM.createAccount(username, password1, 2);
                return accM.getTotalNumOfAccount()-1;
            } else {
                organizerUI.message1();
                organizerUI.askForBack();
            }
        }else{
            organizerUI.message17();
        }
        return -1;
    }

    private int chooseMode1() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        return chooseMode(validChoices);
    }

    private int chooseMode2() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        return chooseMode(validChoices);
    }

    private int chooseMode3() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        return chooseMode(validChoices);
    }

    private int chooseMode(ArrayList<Integer> validChoices) {
        int mode = -1;
        boolean valid = false;
        while (!valid) {
            String userInput = organizerUI.getRequest1();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informInvalidChoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);
            }
        }
        return mode;
    }

    private int chooseMode4() {
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(0, 1));
        do {
            userInput = organizerUI.confirmMsgAll();
            if (strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informInvalidChoice();
            else {
                valid = true;
            }
        } while (!valid);
        return Integer.parseInt(userInput);

    }

    private void readAllAtt() {
        ArrayList<Integer> att = ognM.getAttendeeList();
        StringBuilder a = new StringBuilder("These are the attendees. Choose an id to message:\n");
        for (Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        organizerUI.show(a.toString());
    }

    private void readAllSpk() {
        ArrayList<Integer> att = ognM.getSpeakerList();
        StringBuilder a = new StringBuilder("These are the Speakers. Choose an id to message:\n");
        for (Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        organizerUI.show(a.toString());
    }

    private int targetGetter(int i) {
        ArrayList<Integer> validChoices = new ArrayList<>();
        if (i == 1) {
            validChoices.addAll(ognM.getAttendeeList());
            validChoices.addAll(ognM.getSpeakerList());
        } else if (i == 2) {
            validChoices.addAll(ognM.getAttendeeList());
        } else {
            validChoices.addAll(ognM.getSpeakerList());
        }
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do {
            userInput = organizerUI.getRequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informInvalidChoice();
            else {
                valid = true;
            }
        } while (!valid);
        return Integer.parseInt(userInput);
    }

    private void messageToIndividual(String str, int receiverID) {
        int check = ognM.messageable1(receiverID);
        if (check == 1) {
            int msg = MsgM.createmessage(ognM.getCurrOrganizer().getUserId(), receiverID, str);
            accM.addinbox(receiverID, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
            organizerUI.message8();
        }else {
            organizerUI.message0();
        }
    }

    private void sendMessageToAllSpeaker(String str) {
        for (int speaker : ognM.getSpeakerList()) {
            int msg = MsgM.createmessage(ognM.getCurrOrganizer().getUserId(), speaker, str);
            accM.addinbox(speaker, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
        }
        organizerUI.message10();
    }

    private void sendMessageToAllAttendee(String str) {
        for (int attendee : ognM.getAttendeeList()) {
            int msg = MsgM.createmessage(ognM.getCurrOrganizer().getUserId(), attendee, str);
            accM.addinbox(attendee, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
        }
        organizerUI.message10();
    }


}