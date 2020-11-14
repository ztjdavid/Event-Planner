package Controller;
import Entity.*;
import UI.AttendeeUI;
import UseCase.LoginManager;
import UseCase.StrategyManager;
import UseCase.TalkManager;
import UseCase.MessageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//Completeness:25%

public class AttendeeSystem {
    protected LoginManager loginM;
    protected TalkManager talkManager;
    protected Attendee currAttendee;
    protected MessageManager MsgM;
    protected AttendeeUI attendeeUI;
    protected StrategyManager strategyM;

    public AttendeeSystem(LoginManager loginM){
        this.loginM = loginM;
    }

    public void run() {
        int userInput = -1;
        while (userInput != 5) {
            attendeeUI.startup();

            userInput = chooseMode1();

            switch (userInput) {
                case 1:
                    //readMyTalks();
                    break;
                case 2:
                    attendeeUI.signUpTalk();
                    //readAllTalks(); *only show available talk
                    //scan the input *if valid
                    //add talk into the schedule
                    //prompt: success

                    break;
                case 3:
                    attendeeUI.cancelTalk();
                    //readMyTalks();
                    //scan the input
                    //remove the talk from the schedule
                    //prompt: success

                    break;
                case 4:
                    //message part
                    attendeeUI.msgSelect();
                    //will have another 'switch cases'
                default:
                    break;

            }
            System.out.println("Quit Messaging System");
        }
    }

    private void readMyTalks() {
    }

    private void readAllTalks() {
    }


    private int chooseMode1(){
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
}
