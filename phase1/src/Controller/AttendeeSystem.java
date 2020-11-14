package Controller;
import UI.AttendeeUI;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;


public class AttendeeSystem {
    protected AccountManager accM;
    protected TalkManager talkManager;
    protected MessageManager MsgM;
    protected AttendeeUI attendeeUI;
    protected StrategyManager strategyM;
    protected AttendeeManager attendeeM;

    public AttendeeSystem(AccountManager accM, TalkManager TalkM, MessageManager MsgM, AttendeeUI attendeeUI,
                         StrategyManager StrategyManager, AttendeeManager AttendeeM) {
        this.accM = accM;
        this.talkManager = TalkM;
        this.MsgM = MsgM;
        this.attendeeUI = attendeeUI;
        this.strategyM = StrategyManager;
        this.attendeeM = AttendeeM;

    }

    public void run() {
        int userChoice;
        do {
            attendeeUI.startup();
            userChoice = chooseMode1();
            enterBranch(userChoice);
        } while (userChoice != 5);
    }

    private void enterBranch(int userChoice){
        switch (userChoice){
            case 1:
                readAllMyTalks();
                break;
            case 2:
                signUpMyNewTalks();
            case 3:
                cancelMyTalks();
            case 4:
                MsgDashboard();
            case 5:
                break;
        }
    }

    private void MsgDashboard(){
        int userChoice;
        do{
            attendeeUI.msgSelect();
            userChoice = chooseMode2();
            msgOp(userChoice);
        } while (userChoice != 4);


    }

    private void msgOp(int userChoice){
        switch (userChoice){
            case 1:
                //msgToAttendee();
                break;
            case 2:
                //msgToSpeaker();
                break;
            case 3:
                //inbox();//check received message
                break;
            case 4:
                break;
        }
    }

    private int chooseMode1(){    //For Attendee Dashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private int chooseMode2(){    //For MsgDashboard.
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }

    private void readAllMyTalks(){
        String a = "My signed up talks";
        ArrayList<Integer> alltalks = attendeeM.getAllMyTalks(); //manager need to implement
        for(int i = 0; i < alltalks.size(); i++){a += talkManager.gettalkinfo(alltalks.get(i));};
        attendeeUI.show(a);
    }

    private void readAllAvailableTalks(){
        String a = "Available Talks";
        ArrayList<Integer> alltalks = attendeeM.getAllAvailableTalks(); //manager need to implement
        for(int i = 0; i < alltalks.size(); i++){a += talkManager.gettalkinfo(alltalks.get(i));};
        attendeeUI.show(a);
    }

    private int targetTalksSignUp(){
        ArrayList<Integer> validChoices = attendeeM.getAllAvailableTalks();//manager need to implement
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest2();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    private void signUpMyNewTalks(){
        attendeeUI.signUpTalk();
        readAllAvailableTalks();
        int input = targetTalksSignUp();
        attendeeM.enrol(input);
        attendeeUI.signUpSuc();
    }

    private int targetTalksCancel(){
        ArrayList<Integer> validChoices = attendeeM.getAllMyTalks();//manager need to implement
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = attendeeUI.getrequest3();
            if (!strategyM.isValidChoice(userInput, validChoices))
                attendeeUI.informinvalidchoice();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;}

    private void cancelMyTalks(){
        attendeeUI.cancelTalk();
        readAllMyTalks();
        int input = targetTalksCancel();
        attendeeM.drop(input);//manager need to implement
        attendeeUI.cancelSuc();
    }

}



