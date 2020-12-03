package Controller;
import Presenters.*;
import UseCase.*;
import UI.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AppSystem {
    protected SignInSystem signInS;
    protected SignUpSystem signUpS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected VipSystem vipsystem;
    protected SpeakerSystem speakerS;
    protected SpeakerUI speakerUI;
    protected RoomManager roomM;
    protected OrganizerPresenter organizerPresenter;
    protected StrategyManager strategyM;
    protected AccountManager accM;
    protected EventManager eventM;
    protected MessageManager MsgM;
    protected AttendeeManager attM;
    protected VIPManager vipM;
    protected StartUI startUI;
    protected SignInUI signInUI;
    protected SignUpUI signUpUI;
    protected AttendeeUI attUI;
    protected OrganizerManager ognM;
    protected SpeakerManager spkM;
    protected TextUI textUI;
    protected VipUI vipUI;
    protected VIPsystemhandler vh;
    protected Attendeesystemhandler ah;



    public AppSystem(){
        this.textUI = new TextUI();
        this.attM = new AttendeeManager();
        this.vipM = new VIPManager();
        this.spkM = new SpeakerManager();
        this.ognM = new OrganizerManager();
        this.roomM = new RoomManager();
        this.startUI = new StartUI();
        this.signInUI = new SignInUI();
        this.signUpUI = new SignUpUI();
        this.speakerUI = new SpeakerUI();
        this.attUI = new AttendeeUI(textUI);
        this.vipUI = new VipUI(textUI);
        this.organizerPresenter = new OrganizerPresenter(textUI);
        this.accM = new AccountManager();
        this.MsgM = new MessageManager();
        this.eventM = new EventManager();
        this.strategyM = new StrategyManager();
        this.vh = new VIPsystemhandler(accM, eventM, MsgM, vipUI, strategyM, vipM, roomM);
        this.signInS = new SignInSystem(accM, signInUI);
        this.signUpS = new SignUpSystem(accM, signUpUI, strategyM);
        this.attendeeS = new AttendeeSystem(accM, eventM, MsgM, attUI, strategyM, attM, roomM, ah);
        this.organizerS = new OrganizerSystem(accM, MsgM, organizerPresenter, strategyM, ognM, spkM, eventM, roomM);
        this.speakerS = new SpeakerSystem(accM, eventM, MsgM, speakerUI, strategyM, spkM, roomM);
        this.vipsystem = new VipSystem(accM, eventM, MsgM, vipUI, strategyM, vipM, roomM, vh);
        this.ah = new Attendeesystemhandler(accM, eventM, MsgM, attUI, strategyM, attM, roomM);


    }

    /**
     * Start the whole program and guide users to sign up or sign in.
     */
    public void run(){
        int userChoice;
        do{
            startUI.startup();
            userChoice = chooseMode();
            if (userChoice != 4){
                int currAccountType = enterBranch(userChoice);
                enterSystems(currAccountType);
            }
        }while(userChoice != 4);
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
            case 3:
                vipsystem.run();
                break;
            default:
                break;
        }
    }

    private int chooseMode(){
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
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
