package Controller;

import Presenters.OrganizerPresenter;
import UseCase.*;

import java.util.ArrayList;
import java.util.HashMap;

public class OrganizerSystemHandler {

    protected AccountManager accM;
    protected MessageManager MsgM;
    protected StrategyManager strategyM;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;
    protected EventManager eventM;
    protected RoomManager roomM;
    protected OrganizerPresenter organizerPresenter;
    protected RequestManager rqstM;

    public OrganizerSystemHandler(AccountManager accM, MessageManager MsgM, StrategyManager strategyM, OrganizerManager ognM,
                                  SpeakerManager spkM, EventManager eventM, RoomManager roomM, OrganizerPresenter organizerPresenter,
                                  RequestManager rqstM){
        this.rqstM = rqstM;
        this.accM = accM;
        this.MsgM = MsgM;
        this.strategyM = strategyM;
        this.ognM = ognM;
        this.spkM = spkM;
        this.eventM = eventM;
        this.roomM = roomM;
        this.organizerPresenter = organizerPresenter;
    }



    /**
     * Create a Event with Event title, start time, Room ID, Speaker ID according to user's input, iff the values are valid.
     */

    protected void doCreateTalk(){
        String talkTitle = organizerPresenter.getTalkTitle();
        int startTime = organizerPresenter.getTalkStartTime();
        if(startTime < 9 || startTime > 17){
            organizerPresenter.errorMessage();
        }else {
            int roomID = organizerPresenter.getRoomID();
            int speakerID = organizerPresenter.getSpeakerID();
            int duration = organizerPresenter.message3();
            boolean isVip = organizerPresenter.getVipEvent();
            if(duration + startTime < 19) {
                ArrayList<Integer> speakerList = new ArrayList<>();
                speakerList.add(speakerID);
                int result = checkTalkValidity(roomID, startTime, speakerID, duration);
                if (result == -1) {
                    int eventCapacity = organizerPresenter.eventCapacity();
                    if (roomM.isWithinCapacity(roomID, eventCapacity)) {
                        int talkID = eventM.createEvent(talkTitle, startTime, roomID, speakerList, eventCapacity, duration, isVip);
                        boolean added = roomM.addNewTalkToRoom(talkID, startTime, roomID, duration);
                        spkM.registerNewTalk(talkID, speakerID);
                        if (added) {
                            organizerPresenter.message12(talkID);
                        } else {
                            organizerPresenter.message9();
                            eventM.removeTalk(talkID);
                        }
                    } else {
                        organizerPresenter.message2();
                    }
                } else if (result == 0) organizerPresenter.message14();
                else if (result == 1) organizerPresenter.message18();
                else if (result == 2) organizerPresenter.message15();
                else if (result == 3) organizerPresenter.message19();
            }else{
                organizerPresenter.message12();
            }
        }
    }

    /**
     * Check if the given room and given speaker are all valid, and both are valid at the given start time.
     * @param roomID the ID of the Room.
     * @param startTime the int representation of the start time.
     * @param speakerID The ID of the Speaker
     * @return 0 if there is a conflict between room and start time, 1 if the room ID is invalid, 2 if there is a conflict between speaker and start time, 3 if the speaker is invalid, -1 otherwise.
     */
    private int checkTalkValidity(int roomID,int startTime, int speakerID, int duration){
        int flag = -1;
        if (!roomM.isValidRoomId(roomID)) return 1;
        else if (!accM.isSpeakerAcc(speakerID)) return 3;
        else if(roomM.getTimeTable(roomID).containsValue(startTime)) flag = 0;
        for(int item:spkM.getTalkList(speakerID)){
            int start = eventM.getStartTime(item);
            int end = eventM.getStartTime(item) + eventM.getDuration(item);
            if(start >= startTime + duration || end <= startTime){}
            else{
                flag = 2;
            }
        }
        return flag;
    }

    protected void doCreateParty() {
        String talkTitle = organizerPresenter.getTalkTitle();
        int startTime = organizerPresenter.getTalkStartTime();
        if (startTime < 9 || startTime > 17) {
            organizerPresenter.errorMessage();
        } else {
            int roomID = organizerPresenter.getRoomID();
            ArrayList<Integer> speakerList = new ArrayList<>();
            int duration = organizerPresenter.message3();
            boolean isVip = organizerPresenter.getVipEvent();
            if (duration + startTime < 19) {
                int result = checkTalkValidity1(roomID, startTime, duration);
                if (result == -1) {
                    int eventCapacity = organizerPresenter.eventCapacity();
                    if (roomM.isWithinCapacity(roomID, eventCapacity)) {
                        int talkID = eventM.createEvent(talkTitle, startTime, roomID, speakerList, eventCapacity, duration, isVip);
                        boolean added = roomM.addNewTalkToRoom(talkID, startTime, roomID, duration);
                        if (added) {
                            organizerPresenter.message12(talkID);
                        } else {
                            organizerPresenter.message9();
                            eventM.removeTalk(talkID);
                        }
                    } else {
                        organizerPresenter.message2();
                    }
                } else if (result == 0) organizerPresenter.message14();
                else if (result == 1) organizerPresenter.message18();
            }
            else{
                organizerPresenter.message12();
            }
        }
    }

    private int checkTalkValidity1(int roomID, int startTime, int duration){
        int flag = -1;
        if (!roomM.isValidRoomId(roomID)) return 1;
        else if(roomM.getTimeTable(roomID).containsValue(startTime)) flag = 0;
        return flag;
    }

    protected void doCancelEvent(){
        int eventID = organizerPresenter.getEventID();
        if(eventM.checkEventExists(eventID)){
            eventM.removeTalk(eventID);
            organizerPresenter.message4();
        }else{
            organizerPresenter.message5();
        }
    }

    protected void doCreateDiscussion() {
        String talkTitle = organizerPresenter.getTalkTitle();
        int startTime = organizerPresenter.getTalkStartTime();
        if (startTime < 9 || startTime > 17) {
            organizerPresenter.errorMessage();
        } else {
            int roomID = organizerPresenter.getRoomID();
            int speakerNum = organizerPresenter.getSpeakerNum();
            int eventCapacity = organizerPresenter.eventCapacity();
            int duration = organizerPresenter.message3();
            boolean isVip = organizerPresenter.getVipEvent();
            if (duration + startTime < 19) {
                ArrayList<Integer> speakerList = new ArrayList<>();
                if (roomM.isWithinCapacity(roomID, eventCapacity)) {
                    int i = 0;
                    while (i != speakerNum) {
                        int speakerID = organizerPresenter.getSpeakerID();
                        int result = checkTalkValidity(roomID, startTime, speakerID, duration);
                        if (result == -1) {
                            speakerList.add(speakerID);
                        } else if (result == 0) organizerPresenter.message14();
                        else if (result == 1) organizerPresenter.message18();
                        else if (result == 2) organizerPresenter.message15();
                        else if (result == 3) organizerPresenter.message19();
                        i++;
                    }
                } else {
                    organizerPresenter.message2();
                }
                if (speakerList.size() == speakerNum) {
                    int talkID = eventM.createEvent(talkTitle, startTime, roomID, speakerList, eventCapacity, duration, isVip);
                    boolean added = roomM.addNewTalkToRoom(talkID, startTime, roomID, duration);
                    for (int item : speakerList) {
                        spkM.registerNewTalk(talkID, item);
                    }
                    if (added) {
                        organizerPresenter.message12(talkID);
                    } else {
                        organizerPresenter.message9();
                        eventM.removeTalk(talkID);
                    }
                }
            }else{
                organizerPresenter.message12();
            }
        }
    }

    /**
     * Show all Talks with each Event's title, ID, start time, room name, and room ID.
     */
    protected void readAllEvents(){
        organizerPresenter.message7();
        ArrayList<Integer> talkLst = new ArrayList<>(eventM.getAllEvents());
        for(int item:talkLst){
            String title = eventM.getTitle(item);
            int startTime = eventM.getStartTime(item);
            String roomName = roomM.getRoomName(eventM.getRoomIdWithId(item));
            int roomId = eventM.getRoomIdWithId(item);
            int type = eventM.getEventTypeWithID(item);
            int duration = eventM.getDuration(item);
            ArrayList<Integer> speaker = new ArrayList<>(eventM.getSpeakerOfEvent(item));
            ArrayList<Integer> attendee = new ArrayList<>(eventM.getAttendeeOfEvent(item));
            organizerPresenter.readTalks(title, item, startTime, roomName, roomId, type, speaker, attendee, duration);
        }
    }

    /**
     * Create a Room with Room name according to user's input. After creation, print out the room ID with message13.
     */
    protected void doCreateRoom(){
        String roomName = organizerPresenter.getRoomName();
        int roomCapacity = organizerPresenter.roomCapacity();
        int roomID = roomM.createRoom(roomName, roomCapacity);
        organizerPresenter.message13(roomID);
        organizerPresenter.askForBack();
    }

    protected void doCreateSpeaker() {
        int ID = createSpeaker();
        if(ID != -1){
            organizerPresenter.message3(ID);
        }
        organizerPresenter.askForBack();
    }

    private int createSpeaker() {
        String username = organizerPresenter.getSpeakerUsername();
        if (!accM.existsUsername(username)){
            String password1 = organizerPresenter.getSpeakerPwd1();
            String password2 = organizerPresenter.getSpeakerPwd2();
            if (password1.equals(password2)) {
                accM.createAccount(username, password1, 2);
                return accM.getTotalNumOfAccount()-1;
            } else {
                organizerPresenter.message1();
                organizerPresenter.askForBack();
            }
        }else{
            organizerPresenter.message17();
        }
        return -1;
    }

    /**
     * Show all Rooms with Hashmap consisting of room ID, room name, and room's timetable.
     */
    protected void readAllRooms(){
        organizerPresenter.message6();
        ArrayList<Integer> allRooms = roomM.getAllRooms(); // Arraylist of all room IDs.
        for(int item : allRooms){
            String name = roomM.getRoomName(item);
            HashMap<Integer, Integer> timeTable = new HashMap<>(roomM.getTimeTable(item));
            organizerPresenter.readAllRooms(item, name, timeTable);
        }
        organizerPresenter.askForBack();
    }

    protected void doCreateAttendee(){
        int ID = createAttendee();
        if(ID != -1){
            organizerPresenter.message9(ID);
        }
        organizerPresenter.askForBack();
    }

    private int createAttendee(){
        String username = organizerPresenter.getAttendeeUsername();
        if (!accM.existsUsername(username)){
            String password1 = organizerPresenter.getSpeakerPwd1();
            String password2 = organizerPresenter.getSpeakerPwd2();
            if (password1.equals(password2)) {
                accM.createAccount(username, password1, 1);
                return accM.getTotalNumOfAccount()-1;
            } else {
                organizerPresenter.message1();
                organizerPresenter.askForBack();
            }
        }else{
            organizerPresenter.message17();
        }
        return -1;
    }

    protected void doCreateVIP(){
        int ID = CreateVIP();
        if(ID != -1){
            organizerPresenter.message10(ID);
        }
        organizerPresenter.askForBack();
    }

    private int CreateVIP(){
        String username = organizerPresenter.getVIPUsername();
        if (!accM.existsUsername(username)){
            String password1 = organizerPresenter.getSpeakerPwd1();
            String password2 = organizerPresenter.getSpeakerPwd2();
            if (password1.equals(password2)) {
                accM.createAccount(username, password1, 3);
                return accM.getTotalNumOfAccount()-1;
            } else {
                organizerPresenter.message1();
                organizerPresenter.askForBack();
            }
        }else{
            organizerPresenter.message17();
        }
        return -1;
    }

    protected void readAllAccounts(){
        organizerPresenter.message16();
        ArrayList<Integer> speakerList = ognM.getSpeakerList();
        ArrayList<Integer> attendeeList = ognM.getAttendeeList();
        ArrayList<Integer> VIPList = ognM.getVIPList();
        for(int item: speakerList){
            String username = accM.getUserName(item);
            ArrayList<Integer> talks = new ArrayList<>(spkM.getTalkList(item));
            organizerPresenter.readSpeakers(username, item, talks);
        }
        for(int item: attendeeList){
            String username = accM.getUserName(item);
            ArrayList<Integer> talks = new ArrayList<>(spkM.getTalkList(item));
            organizerPresenter.readAttendee(username, item, talks);
        }
        for(int item: VIPList){
            String username = accM.getUserName(item);
            ArrayList<Integer> talks = new ArrayList<>(spkM.getTalkList(item));
            organizerPresenter.readVIP(username, item, talks);
        }
        organizerPresenter.askForBack();
    }

    protected void readAllMsg() {
        int messageID;
        ArrayList<Integer> inbox = ognM.getInbox();
        StringBuilder a = new StringBuilder("These are the messages. Choose an id to read:\n");
        if (inbox.size() != 0) {
            do {
                for (int item : inbox) {
                    a.append(item);
                }
                organizerPresenter.show(a.toString());
                messageID = targetGetter(3);
                if(messageID != -1){
                    organizerPresenter.display(MsgM.getString(messageID));
                    askToAchieve(messageID);
                }
            }while(messageID != -1);
        } else {
            organizerPresenter.message11();
            organizerPresenter.askForBack();
        }
    }

    private void askToAchieve(int messageID){
        int userInput = organizerPresenter.chooseOption(getChoiceList(3), "Would you like to:" +
                "\n1 -> Mark as Unread" +
                "\n2 -> Move to Archive" +
                "\n3 -> Delete Message", "Invalid Chooice, Please Try Again:");
        if(userInput == 1){
            organizerPresenter.message20();
        } else if(userInput == 2){
            accM.removeMessage(messageID);
            accM.archiveMessage(messageID);
            organizerPresenter.message21();
        }else if(userInput == 3){
            accM.removeMessage(messageID);
            MsgM.removeMessage(messageID);
            organizerPresenter.message22();
        }
        organizerPresenter.askForBack();
    }

    protected void replyToMsg() {
        int messageID;
        boolean hasReplies = readAllReply();
        if(hasReplies) {
            do {
                messageID = targetGetter(1);
                if (messageID != -1) {
                    String txt = organizerPresenter.enterMessage("Please Enter Your Message." +
                            "\n(End editing by typing a single \"end\" in a new line.)");
                    replyToIndividual(txt, messageID);
                    organizerPresenter.askForBack();
                }
            } while (messageID != -1);
        }else{
            organizerPresenter.askForBack();
        }
    }

    private ArrayList<Integer> replyList(){
        ArrayList<Integer> sent = new ArrayList<>(ognM.getMsgSend());
        ArrayList<Integer> replies = new ArrayList<>();
        for(int item : sent){
            if(MsgM.hasReply(item)){
                replies.add(item);
            }
        }
        return replies;
    }

    private boolean readAllReply() {
        ArrayList<Integer> replies = new ArrayList<>(replyList());
        if(replies.size() == 0){
            organizerPresenter.message13();
            return false;
        }else{
                StringBuilder a = new StringBuilder("These are the Replies. Choose an ID to reply:\n");
                for (int item : replies) {
                    a.append(item);
                }
                organizerPresenter.show(a.toString());
                return true;
        }
    }

    protected void messageToAllSpeaker() {
        int userInput = organizerPresenter.chooseOption(getChoiceList(2),
                "Are you sure to message all Speakers in this system?\nEnter 2 to confirm, 1 to cancel and go back.(Irreversible once confirmed.)",
                "Invalid Chooice, Please Try Again:");
        if (userInput == 1) {
            String txt = organizerPresenter.enterMessage("Please Enter Your Message." +
                    "\n(End editing by typing a single \"end\" in a new line.)");
            sendMessageToAllSpeaker(txt);
            organizerPresenter.askForBack();
        }
    }

    protected void messageToAllAttendee() {
        int userChoice = organizerPresenter.chooseOption(getChoiceList(2),
                "Are you sure to message all attendees in this system?\nEnter 2 to confirm, 1 to cancel and go back.(Irreversible once confirmed.)",
                "Invalid Chooice, Please Try Again:");
        if (userChoice == 1) {
            String txt = organizerPresenter.enterMessage("Please Enter Your Message." +
                    "\n(End editing by typing a single \"end\" in a new line.)");
            sendMessageToAllAttendee(txt);
            organizerPresenter.askForBack();
        }
    }

    protected void messageToSpeaker() {
        int spkId;
        do {
            readAllSpk();
            spkId = targetGetter(5);
            if (spkId != -1) {
                String txt = organizerPresenter.enterMessage("Please Enter Your Message." +
                        "\n(End editing by typing a single \"end\" in a new line.)");
                messageToIndividual(txt, spkId);
                organizerPresenter.askForBack();
            }
        } while (spkId != -1);
    }


    protected void messageToAttendee() {
        int attId;
        do {
            readAllAtt();
            attId = targetGetter(2);
            if (attId != -1) {
                String txt = organizerPresenter.enterMessage("Please Enter Your Message." +
                        "\n(End editing by typing a single \"end\" in a new line.)");
                messageToIndividual(txt, attId);
                organizerPresenter.askForBack();
            }
        } while (attId != -1);
    }


    private void readAllAtt() {
        ArrayList<Integer> att = ognM.getAttendeeList();
        att.addAll(ognM.getVIPList());
        StringBuilder a = new StringBuilder("These are the Attendees and VIP Attendees. Choose an ID to Message:\n");
        for (Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        organizerPresenter.show(a.toString());
    }

    private void readAllSpk() {
        ArrayList<Integer> att = ognM.getSpeakerList();
        StringBuilder a = new StringBuilder("These are the Speakers. Choose an ID to Message:\n");
        for (Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        organizerPresenter.show(a.toString());
    }

    private int targetGetter(int i) {
        ArrayList<Integer> validChoices = new ArrayList<>();
        if (i == 1) {
            validChoices.addAll(replyList());
        } else if (i == 2) {
            validChoices.addAll(ognM.getAttendeeList());
            validChoices.addAll(ognM.getVIPList());
        } else if (i == 3){
            validChoices.addAll(ognM.getInbox());
        } else if(i == 4){
            validChoices.addAll(rqstM.getRequestID());
        } else{
            validChoices.addAll(ognM.getSpeakerList());
        }
        validChoices.add(-1);
        String userInput;
        boolean valid = false;
        do {
            userInput = organizerPresenter.getRequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                organizerPresenter.informInvalidChoice();
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
            organizerPresenter.message8();
        }else {
            organizerPresenter.message0();
        }
    }

    private void replyToIndividual(String str, int messageID) {
        int replyid = MsgM.replyID(messageID);
        int receiverID = MsgM.getReceiverID(messageID);
        String replier = MsgM.getReplier(messageID);
        int check = ognM.messageable1(receiverID);
        if (check == 1) {
            int msg = MsgM.setreply(replyid, str, replier);
            accM.addinbox(receiverID, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
            organizerPresenter.message8(msg);
        }else {
            organizerPresenter.message0();
        }
    }

    private void sendMessageToAllSpeaker(String str) {
        for (int speaker : ognM.getSpeakerList()) {
            int msg = MsgM.createmessage(ognM.getCurrAccountName(), ognM.getCurrOrganizer().getUserId(), speaker, str);
            accM.addinbox(speaker, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
        }
        organizerPresenter.message10();
    }

    private void sendMessageToAllAttendee(String str) {
        for (int attendee : ognM.getAttendeeList()) {
            int msg = MsgM.createmessage(ognM.getCurrAccountName(), ognM.getCurrOrganizer().getUserId(), attendee, str);
            accM.addinbox(attendee, msg);
            accM.addsend(ognM.getCurrOrganizer().getUserId(), msg);
        }
        organizerPresenter.message10();
    }

    protected ArrayList<Integer> getChoiceList(int size){
        int i = 0;
        int j = 1;
        ArrayList<Integer> choiceList = new ArrayList<>();
        while (i!=size){
            choiceList.add(j);
            j++;
            i++;
        }
        return choiceList;
    }

    protected void readArchivedMsg(){
        ArrayList<Integer> arrayList = new ArrayList<>(accM.getArchive());
        String a = MsgM.formatmsgget(arrayList);
        organizerPresenter.show(a);
        organizerPresenter.askForBack();
    }

    protected void readAllRequest(){
        ArrayList<Integer> requestIDs = new ArrayList<>(rqstM.getRequestID());
        StringBuilder a = new StringBuilder();
        for (int item : requestIDs){
            a.append(rqstM.getRequestInfo(item));
        }
        organizerPresenter.show(a.toString());
        organizerPresenter.askForBack();
    }

    protected void changeStatus(){
        ArrayList<Integer> requestIDs = new ArrayList<>(rqstM.getRequestID());
        if(requestIDs.size() == 0){
            organizerPresenter.message24();
            organizerPresenter.askForBack();
        }else {
            int requestID;
            readAllRequest(requestIDs);
            requestID = targetGetter(4);
            rqstM.changeToAddressed(requestID);
            organizerPresenter.message25();
            organizerPresenter.askForBack();
        }
    }

    private void readAllRequest(ArrayList<Integer> lst){
        StringBuilder a = new StringBuilder("These are the Requests. Choose an ID to Change Status:\n");
        for (int item : lst) {
            a.append(accM.getinfoacc(item));
        }
        organizerPresenter.show(a.toString());
    }


}

