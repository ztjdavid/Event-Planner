package Controller;
import Presenters.*;
import UseCase.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AppSystem {
    protected SignInSystem signInS;
    protected SignUpSystem signUpS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected SpeakerSystem speakerS;
    protected SpeakerUI speakerUI;
    protected RoomManager roomM;
    protected OrganizerUI organizerUI;
    protected StrategyManager strategyM;
    protected AccountManager accM;
    protected EventManager eventM;
    protected MessageManager MsgM;
    protected AttendeeManager attM;
    protected StartUI startUI;
    protected SignInUI signInUI;
    protected SignUpUI signUpUI;
    protected AttendeeUI attUI;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;

    public AppSystem(){
        this.attM = new AttendeeManager();
        this.spkM = new SpeakerManager();
        this.ognM = new OrganizerManager();
        this.roomM = new RoomManager();
        this.startUI = new StartUI();
        this.signInUI = new SignInUI();
        this.signUpUI = new SignUpUI();
        this.speakerUI = new SpeakerUI();
        this.attUI = new AttendeeUI();
        this.organizerUI = new OrganizerUI();
        this.accM = new AccountManager();
        this.MsgM = new MessageManager();
        this.eventM = new EventManager();
        this.strategyM = new StrategyManager();
        this.signInS = new SignInSystem(accM, signInUI);
        this.signUpS = new SignUpSystem(accM, signUpUI, strategyM);
        this.attendeeS = new AttendeeSystem(accM, eventM, MsgM, attUI, strategyM, attM, roomM);
        this.organizerS = new OrganizerSystem(accM, MsgM, organizerUI, strategyM, ognM, spkM, eventM, roomM);
        this.speakerS = new SpeakerSystem(accM, eventM, MsgM, speakerUI, strategyM, spkM, roomM);

    }

    /**
     * Start the whole program and guide users to sign up or sign in.
     */
    public void run(){
        int userChoice;
        do{
            startUI.startup();
            userChoice = chooseMode();
            if (userChoice != 3){
                int currAccountType = enterBranch(userChoice);
                enterSystems(currAccountType);
            }
        }while(userChoice != 3);
        startUI.informQuiting();
    }



    //Helper methods:
    private int enterBranch(int userInput){
        switch (userInput){
            case 1:
                return signInS.run();
            case 2:
                signUpS.run();
                return signInS.run();
            default:
                return -1;
        }
    }

    private void enterSystems(int currAccountType){
        switch (currAccountType){
            case 0:
                organizerS.run();
                break;
            case 1:
                attendeeS.run();
                break;
            case 2:
                speakerS.run();
                break;
            default:
                break;
        }
    }

    private int chooseMode(){
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3));
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = startUI.requestModeSelection();
            if (!strategyM.isValidChoice(userInput, validChoices))
                startUI.informInvalidInput();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }



}
