package Controller;
import Entity.Organizer;
import UseCase.*;
import UI.*;

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
    protected TalkManager TalkM;
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
        this.TalkM = new TalkManager();
        this.strategyM = new StrategyManager();
        this.signInS = new SignInSystem(accM, signInUI);
        this.signUpS = new SignUpSystem(accM, signUpUI, strategyM);
        this.attendeeS = new AttendeeSystem(accM, TalkM, MsgM, attUI, strategyM, attM);
        this.organizerS = new OrganizerSystem(accM, MsgM, organizerUI, strategyM, ognM, spkM, TalkM, roomM);
        this.speakerS = new SpeakerSystem(accM, TalkM, MsgM, speakerUI, strategyM, spkM);

    }

    /**
     * Start the whole program.
     */
    public void run(){
        startUI.startup();
        int userInput = chooseMode();
        int currAccountType = enterBranch(userInput);
        enterSystems(currAccountType);
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
                System.out.println("run organizer system");
                organizerS.run();
                break;
            case 1:
                System.out.println("run attendee system");
                //attendeeS.run();
                break;
            case 2:
                System.out.println("run speaker system");
                speakerS.run();
                break;
            default:
                System.out.println("To be implemented. Some necessary classes are not finished.");
        }
    }

    private int chooseMode(){
        ArrayList<Integer> validChoices = new ArrayList<>(Arrays.asList(1, 2));
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
