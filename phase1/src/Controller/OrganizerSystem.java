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
    protected EventManager eventM;
    protected RoomManager roomM;

    public OrganizerSystem(AccountManager accM, MessageManager MsgM, OrganizerUI organizerUI, StrategyManager strategyM,
                           OrganizerManager ognM, SpeakerManager spkM, EventManager eventM, RoomManager roomM) {
        this.accM = accM;
        this.MsgM = MsgM;
        this.strategyM = strategyM;
        this.organizerUI = organizerUI;
        this.ognM = ognM;
        this.spkM = spkM;
        this.eventM = eventM;
        this.roomM = roomM;
    }

    /**
     * Run Organizer System, user can choose according to startup options.
     */
    public void run() {
        int userChoice;
        do {
            organizerUI.startup();
            userChoice = chooseMode2();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    //Helper methods:
    /**
     * Enter the Branch according to user's choice in the startup menu.
     * @param userChoice an int chosen by user.
     */
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

    /**
     * Run Room dashboard, user can choose according to messaging4 options.
     */
    private void roomDashBoard(){
        int userInput;
        do{
            organizerUI.messaging4();
            userInput = chooseMode3();
            roomOp(userInput);
        }while(userInput != 3);
    }

    /**
     * Enter the Room Operation according to user input from the Room dashboard.
     * @param userInput an int chosen by user.
     */
    private void roomOp(int userInput){
        switch (userInput){
            case 1:
                doCreateRoom();
                break;
            case 2:
                readAllRooms();
                break;
            default:
                break;
        }
    }

    /**
     * Create a Room with Room name according to user's input. After creation, print out the room ID with message13.
     */
    private void doCreateRoom(){
        String roomName = organizerUI.getRoomName();
        int roomID = roomM.createRoom(roomName);
        organizerUI.message13(roomID);
        organizerUI.askForBack();
    }

    /**
     * Show all Rooms with Hashmap consisting of room ID, room name, and room's timetable.
     */
    public void readAllRooms(){
        organizerUI.message6();
        ArrayList<Integer> allRooms = roomM.getAllRooms(); // Arraylist of all room IDs.
        for(int item : allRooms){
            String name = roomM.getRoomName(item);
            HashMap<Integer, Integer> timeTable = new HashMap<>(roomM.getTimeTable(item));
            organizerUI.readAllRooms(item, name, timeTable);
        }
        organizerUI.askForBack();
    }

    /**
     * Run Event dashboard, user can choose according to messaging3 options.
     */
    private void talkDashBoard(){
        int userInput;
        do{
            organizerUI.messaging3();
            userInput = chooseMode2();
            tlkOp(userInput);
        }while(userInput != 5);
    }

    /**
     * Enter the Event Operation according to user input from the Event dashboard.
     * @param userInput an int chosen by user.
     */
    private void tlkOp(int userInput){
        switch (userInput){
            case 1:
                doCreateTalk();
                organizerUI.askForBack();
                break;
            case 2:
                doCreateDiscussion();
                organizerUI.askForBack();
                break;
            case 3:
                doCreateParty();
                organizerUI.askForBack();
                break;
            case 4:
                readAllEvents();
                organizerUI.askForBack();
                break;
            case 5:
                break;
        }
    }

    private void doCreateParty(){
        String talkTitle = organizerUI.getTalkTitle();
        int startTime = organizerUI.getTalkStartTime();
        if(startTime < 9 || startTime > 17){
            organizerUI.errorMessage();
        }else {
            int roomID = organizerUI.getRoomID();
            ArrayList<Integer> speakerList = new ArrayList<>();
            int result = checkTalkValidity1(roomID, startTime);
            if(result == -1) {
                int talkID = eventM.createEvent(talkTitle, startTime, roomID, speakerList);
                roomM.addNewTalkToRoom(talkID, startTime, roomID);
                organizerUI.message12(talkID);
            }else if (result == 0) organizerUI.message14();
            else if (result == 1) organizerUI.message18();
        }
    }

    private int checkTalkValidity1(int roomID,int startTime){
        int flag = -1;
        if (!roomM.isValidRoomId(roomID)) return 1;
        else if(roomM.getTimeTable(roomID).containsValue(startTime)) flag = 0;
        return flag;
    }

    private void doCreateDiscussion(){
        String talkTitle = organizerUI.getTalkTitle();
        int startTime = organizerUI.getTalkStartTime();
        if(startTime < 9 || startTime > 17){
            organizerUI.errorMessage();
        }else {
            int roomID = organizerUI.getRoomID();
            int speakerNum = organizerUI.getSpeakerNum();
            int i = 0;
            ArrayList<Integer> speakerList = new ArrayList<>();
            while(i != speakerNum) {
                int speakerID = organizerUI.getSpeakerID();
                int result = checkTalkValidity(roomID, startTime, speakerID);
                if (result == -1) {
                    speakerList.add(speakerID);
                } else if (result == 0) organizerUI.message14();
                else if (result == 1) organizerUI.message18();
                else if (result == 2) organizerUI.message15();
                else if (result == 3) organizerUI.message19();
                i++;
            }
            if (speakerList.size()==speakerNum) {
                int talkID = eventM.createEvent(talkTitle, startTime, roomID, speakerList);
                roomM.addNewTalkToRoom(talkID, startTime, roomID);
                for(int item:speakerList) {
                    spkM.registerNewTalk(talkID, item);
                }
                organizerUI.message12(talkID);
            }
        }
    }


    /**
     * Show all Talks with each Event's title, ID, start time, room name, and room ID.
     */
    private void readAllEvents(){
        organizerUI.message7();
        ArrayList<Integer> talkLst = new ArrayList<>(eventM.getAllEvents());
        for(int item:talkLst){
            String title = eventM.getTitle(item);
            int startTime = eventM.getStartTime(item);
            String roomName = roomM.getRoomName(eventM.getRoomIdWithId(item));
            int roomId = eventM.getRoomIdWithId(item);
            int type = eventM.getEventTypeWithID(item);
            ArrayList<Integer> speaker = new ArrayList<>(eventM.getSpeakerOfEvent(item));
            ArrayList<Integer> attendee = new ArrayList<>(eventM.getAttendeeOfEvent(item));
            organizerUI.readTalks(title, item, startTime, roomName, roomId, type, speaker, attendee);
        }
    }

    /**
     * Create a Event with Event title, start time, Room ID, Speaker ID according to user's input, iff the values are valid.
     */
    private void doCreateTalk(){
        String talkTitle = organizerUI.getTalkTitle();
        int startTime = organizerUI.getTalkStartTime();
        if(startTime < 9 || startTime > 17){
            organizerUI.errorMessage();
        }else {
            int roomID = organizerUI.getRoomID();
            int speakerID = organizerUI.getSpeakerID();
            ArrayList<Integer> speakerList = new ArrayList<>();
            speakerList.add(speakerID);
            int result = checkTalkValidity(roomID, startTime, speakerID);
            if (result == -1) {
                int talkID = eventM.createEvent(talkTitle, startTime, roomID, speakerList);
                roomM.addNewTalkToRoom(talkID, startTime, roomID);
                spkM.registerNewTalk(talkID, speakerID);
                organizerUI.message12(talkID);
            } else if (result == 0) organizerUI.message14();
            else if (result == 1) organizerUI.message18();
            else if (result == 2) organizerUI.message15();
            else if (result == 3) organizerUI.message19();
        }
    }

    /**
     * Check if the given room and given speaker are all valid, and both are valid at the given start time.
     * @param roomID the ID of the Room.
     * @param startTime the int representation of the start time.
     * @param speakerID The ID of the Speaker
     * @return 0 if there is a conflict between room and start time, 1 if the room ID is invalid, 2 if there is a conflict between speaker and start time, 3 if the speaker is invalid, -1 otherwise.
     */
    private int checkTalkValidity(int roomID,int startTime, int speakerID){
        int flag = -1;

        if (!roomM.isValidRoomId(roomID)) return 1;
        else if (!accM.isSpeakerAcc(speakerID)) return 3;
        else if(roomM.getTimeTable(roomID).containsValue(startTime)) flag = 0;
        for(int item:spkM.getTalkList(speakerID)){
            if(eventM.getStartTime(item) == startTime){
                flag = 2;
            }
        }
    return flag;
    }

    /**
     * Run Speaker dashboard, user can choose according to messaging2 options.
     */
    private void speakerDashboard() {
        int userInput;
        do {
            organizerUI.messaging2();
            userInput = chooseMode3();
            sprOp(userInput);
        } while (userInput != 3);
    }

    /**
     * Enter the Speaker Operation according to user input from the Speaker dashboard.
     * @param userInput an int chosen by user.
     */
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
                MsgM.setreply(getID, txt, accM.getCurrAccountName());
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
                a.append("\n");
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
            int msg = MsgM.createmessage(ognM.getCurrAccountName(), ognM.getCurrOrganizer().getUserId(), receiverID, str);
            accM.addinbox(receiverID, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
            organizerUI.message8();
        }else {
            organizerUI.message0();
        }
    }

    private void sendMessageToAllSpeaker(String str) {
        for (int speaker : ognM.getSpeakerList()) {
            int msg = MsgM.createmessage(ognM.getCurrAccountName(), ognM.getCurrOrganizer().getUserId(), speaker, str);
            accM.addinbox(speaker, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
        }
        organizerUI.message10();
    }

    private void sendMessageToAllAttendee(String str) {
        for (int attendee : ognM.getAttendeeList()) {
            int msg = MsgM.createmessage(ognM.getCurrAccountName(), ognM.getCurrOrganizer().getUserId(), attendee, str);
            accM.addinbox(attendee, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
        }
        organizerUI.message10();
    }


}