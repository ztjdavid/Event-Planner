package Controller;
import Entity.*;
import UI.OrganizerUI;
import UseCase.*;
import UseCase.LoginManager;


import java.util.ArrayList;
import java.util.Arrays;

public class OrganizerSystem {
    protected LoginManager loginM;
    protected MessageManager MsgM;
    protected Organizer currOrganizer;
    protected StrategyManager strategyM;
    protected OrganizerUI organizerUI;
    protected OrganizerManager ognM;

    public OrganizerSystem(LoginManager loginM, MessageManager MsgM, OrganizerUI organizerUI, StrategyManager strategyM, OrganizerManager ognM) {
        this.loginM = loginM;
        this.MsgM = MsgM;
        this.currOrganizer = (Organizer) loginM.getCurrAccount();
        this.strategyM = strategyM;
        this.organizerUI = organizerUI;
        this.ognM = ognM;
    }

    public void run(){
        int userInput = -1;
        while (userInput != 8) {
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
                case 3:
                case 4:
                    changeCurrPwd();
                    break;
                case 5:
                    organizerUI.displayCurrUsername(currOrganizer.getUsername());
                    changeCurrUsername();
                    break;
                default:
                    break;
            }

        }
        organizerUI.messageToDisplay(5);
    }

    private void changeCurrUsername(){
        String currPwd = organizerUI.currPwd();
        if (loginM.loginAccount(currOrganizer.getUsername(), currPwd)){
            String newName = organizerUI.getNewUsername();
            ognM.changeUsername(currOrganizer, newName);
            organizerUI.displayNewUsername(newName);
        }else{
            organizerUI.messageToDisplay(8);
            changeCurrUsername();
        }
    }

    private void changeCurrPwd(){
        String currPwd = organizerUI.currPwd();
        if (loginM.loginAccount(currOrganizer.getUsername(), currPwd)){
            String pwd1 = organizerUI.getNewPwd();
            String pwd2 = organizerUI.getNewPwd2();
            if(pwd1.equals(pwd2)){
                ognM.changePassword(currOrganizer, pwd1);
                organizerUI.messageToDisplay(6);
            }else{
                organizerUI.messageToDisplay(7);
            }
        }else{
            organizerUI.messageToDisplay(8);
            changeCurrPwd();
        }
    }

    private int chooseMode1() {
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
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


    public ArrayList<Account> speakerList() {
        ArrayList<Account> allSpeaker = new ArrayList<>();
        for(Account acc:loginM.getAccountList().values()){
            if(acc.getUserType() == 2) {
                allSpeaker.add(acc);
            }
        }
        return allSpeaker;
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
        ArrayList<Account> speakers = speakerList();
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