package Controller;
import Entity.Organizer;
import UseCase.LoginManager;
import UI.*;
import UseCase.MessageManager;
import UseCase.StrategyManager;
import UseCase.TalkManager;

import java.util.ArrayList;
import java.util.Arrays;

public class AppSystem {
    protected SignInSystem signInS;
    protected SignUpSystem signUpS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected SpeakerSystem speakerS;
    protected SpeakerUI speakerUI;
    protected OrganizerUI organizerUI;
    protected StrategyManager strategyM;
    protected LoginManager loginM;
    protected TalkManager TalkM;
    protected MessageManager MsgM;
    protected StartUI startUI;
    protected SignInUI signInUI;
    protected SignUpUI signUpUI;

    public AppSystem(){
        this.startUI = new StartUI();
        this.signInUI = new SignInUI();
        this.signUpUI = new SignUpUI();
        this.speakerUI = new SpeakerUI();
        this.loginM = new LoginManager();
        this.MsgM = new MessageManager();
        this.TalkM = new TalkManager();
        this.strategyM = new StrategyManager();
        this.signInS = new SignInSystem(loginM, signInUI);
        this.signUpS = new SignUpSystem(loginM, signUpUI, strategyM);
        this.attendeeS = new AttendeeSystem(loginM);
        this.organizerS = new OrganizerSystem(loginM, MsgM, organizerUI, strategyM);
        this.speakerS = new SpeakerSystem(loginM, TalkM, MsgM, speakerUI, strategyM);

    }


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
