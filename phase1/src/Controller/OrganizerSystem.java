package Controller;
import UI.OrganizerUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;

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
            userChoice = chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 4);
    }

    private void enterBranch(int userChoice) {
        switch (userChoice) {
            case 1:
                msgDashboard();
                break;
            case 2:
                schdlDashboard();
                break;
            case 3:
                spkrDashboard();
                break;
            case 4:
                break;
        }
    }

    private void spkrDashboard() {
        int userInput;
        do {
            organizerUI.messaging2();
            userInput = chooseMode3();
            spkrOp(userInput);
        } while (userInput != 1);
    }

    private void spkrOp(int userInput) {
        switch (userInput) {
            case 0:
                doCreateSpkr();
                break;
            case 1:
                break;
        }
    }

    private void doCreateSpkr() {
        String username = organizerUI.getSpeakerUsername();
        int speakerID = createSpeaker(username);
        int userInput;
        do {
            organizerUI.message3();
            userInput = chooseMode3();
            addTalk(userInput, speakerID);
        } while (userInput != 1);
    }


    private void addTalk(int userInput, int speakerID) {
        switch (userInput) {
            case 0:
                String talkTitle = organizerUI.getTalkTitle();
                int talkTime = organizerUI.getTalkStartTime();
                int talkRoomID = organizerUI.getTalkRoomID();
                int talkID = tlkM.createTalk(TalkManager.getTotalTalkCount() + 1, talkTitle, talkTime, talkRoomID, speakerID);
                if (talkID == -1) {
                    organizerUI.message7();
                } else {
                    organizerUI.message6();
                }
                break;
            case 1:
                break;
        }
    }


    private void msgDashboard() {
        int userInput;
        do {
            organizerUI.messaging();
            userInput = chooseMode1();
            messageOp(userInput);
        } while (userInput != 8);
    }

    private void schdlDashboard() {
        int userInput;
        do {
            organizerUI.messaging1();
            userInput = chooseMode3();
            schdlOp(userInput);
        } while (userInput != 3);
    }

    private void schdlOp(int userInput) {
        switch (userInput) {
            case 1:
                doSchedule();
            case 2:
                doReschedule();
            case 3:
                break;
        }
    }

    private void doReschedule() {
        int roomID2 = organizerUI.getRoomID();
        int speaker2ID = organizerUI.getSpeakerID();
        int oldTalkID = organizerUI.getOldTalkID();
        int newTalkID = organizerUI.getNewTalkID();
        rescheduleSpeaker(speaker2ID, oldTalkID, newTalkID, roomID2);
        organizerUI.askForBack();
    }

    private void doSchedule() {
        int roomID = organizerUI.getRoomID();
        int speakerID = organizerUI.getSpeakerID();
        int talkID = organizerUI.getTalkID();
        scheduleRoom(roomID, speakerID, talkID);
        organizerUI.askForBack();
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
                msgtoreply();
                break;
            case 8:
                break;

        }
    }

    private void msgtoreply() {
        int tmsgid;
        do {
            readmsgandrep();
            tmsgid = targetGetter(1);
            if (tmsgid != -1) {
                String txt = enterTxt();
                MsgM.setreply(tmsgid, txt);
                organizerUI.askForBack();
            }
        } while (tmsgid != -1);
    }

    private void readmsgandrep() {
        readallmsg();
        organizerUI.announcereply();
    }

    private void readallmsg() {
        String a = MsgM.formatmsgget(ognM.getinbox());
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


    private void rescheduleSpeaker(int speakerID, int currentTalkID, int rescheduledTalkID, int rescheduleRoomID) {
        for (int num : spkM.getTalkList(speakerID)) {
            if (num == currentTalkID) {
                tlkM.removeTalk(num);
            }
        }
        scheduleRoom(rescheduleRoomID, speakerID, rescheduledTalkID);
    }

    private void scheduleRoom(int roomID, int speakerID, int talkID) {
        if (!roomM.isOccupiedAt(roomID, tlkM.getStartTime(talkID))) {
            for (int item : spkM.getTalkList(speakerID)) {
                if (item == tlkM.getStartTime(talkID)) {
                    organizerUI.message2();
                    break;
                }/*查看Speaker现有talk的时间和当前talk是否会重合*/
            }
            tlkM.setSpeakerTo(speakerID, talkID);
            roomM.scheduleTalk(roomID, talkID, tlkM.getStartTime(talkID));
            organizerUI.message4();
        } else {
            organizerUI.message5();
        }
    }

    private int createSpeaker(String username) {
        String password1 = organizerUI.getSpeakerpwd1();
        String password2 = organizerUI.getSpeakerpwd2();
        if (password1.equals(password2)) {
            accM.createAccount(username, password1, 2);
            organizerUI.message9();
            return accM.getTotalNumOfAccount();
        } else {
            organizerUI.message1();
            createSpeaker(username);
        }
        return -1;
    }

    private int chooseMode1() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        int mode = -1;
        boolean valid = false;
        while (!valid) {
            String userInput = organizerUI.getRequest1();
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
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        int mode = -1;
        boolean valid = false;
        while (!valid) {
            String userInput = organizerUI.intChooseMode3();
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
        boolean valid = false;
        String userInput;
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(0, 1));
        do {
            userInput = organizerUI.confirmMsgAll();
            if (strategyM.isValidChoice(userInput, validChoices))
                organizerUI.informinvalidchoice();
            else {
                valid = true;
            }
        } while (!valid);
        return Integer.parseInt(userInput);

    }

    private void readAllAtt() {
        ArrayList<Integer> att = ognM.getAttendeeList();
        StringBuilder a = new StringBuilder("These are the attendees. Choose an id to message:");
        for (Integer i : att) {
            a.append(accM.getinfoacc(i));
        }
        organizerUI.show(a.toString());
    }

    private void readAllSpk() {
        ArrayList<Integer> att = ognM.getSpeakerList();
        StringBuilder a = new StringBuilder("These are the Speakers. Choose an id to message:");
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
                organizerUI.informinvalidchoice();
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
        }
        organizerUI.message0();
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