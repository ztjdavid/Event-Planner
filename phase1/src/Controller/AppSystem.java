package Controller;
import UseCase.LoginManager;
import Presenter.*;

public class AppSystem {
    protected SignInSystem signInS;
    protected SignUpSystem signUpS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected SpeakerSystem speakerS;
    protected LoginManager loginM;
    protected StartUI startUI;
    protected SignInUI signInUI;
    protected SignUpUI signUpUI;

    public AppSystem(){
        this.loginM = new LoginManager();
        this.startUI = new StartUI();
        this.signInUI = new SignInUI();
        this.signUpUI = new SignUpUI();
        this.signInS = new SignInSystem(loginM, signInUI);
        this.signUpS = new SignUpSystem(loginM, signUpUI);
        this.attendeeS = new AttendeeSystem(loginM);
        this.organizerS = new OrganizerSystem(loginM);
        this.speakerS = new SpeakerSystem(loginM);

    }

    public void run(){
        int userInput;
        int currAccountType = -1;

        startUI.startup();
        userInput = chooseMode();
        switch (userInput){
            case 1:
                currAccountType = signInS.run();
                break;
            case 2:
                signUpS.run();
                currAccountType = signInS.run();
                break;
        }

        switch (currAccountType){
            case 0:
                System.out.println("run organizer system");
                //organizerS.run();
                break;
            case 1:
                System.out.println("run attendee system");
                //attendeeS.run();
                break;
            case 2:
                System.out.println("run speaker system");
                //speakerS.run();
                break;
            default:
                System.out.println("To be implemented. Some necessary classes are not finished.");
        }
    }



    private int chooseMode(){
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = startUI.requestModeSelection();
            if (!isValidChoice(userInput))
                startUI.informInvalidInput();
            else {
                valid = true;
                mode = Integer.parseInt(userInput);}
        }
        return mode;
    }


    private boolean isValidChoice(String userInput){
        int num;
        try{
            num = Integer.parseInt(userInput);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return num == 1 || num == 2;
    }






}
