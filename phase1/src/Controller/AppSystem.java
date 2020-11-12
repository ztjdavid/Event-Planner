package Controller;

import UI.*;
import UseCase.*;

import java.util.Scanner;

public class AppSystem {
    protected SignInSystem signInS;
    protected SignUpSystem signUpS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected SpeakerSystem speakerS;
    protected LoginManager loginM;
    protected SignInUI signInUI;
    protected SignUpUI signUpUI;
    protected StartUI startUI;

    public AppSystem(){
        this.startUI = new StartUI();
        this.loginM = new LoginManager();
        this.signUpS = new SignUpSystem(loginM, signUpUI);
        this.signInS = new SignInSystem(loginM, signInUI);
        this.signInS = new SignInSystem(loginM, signInUI);
        this.signUpS = new SignUpSystem(loginM, signUpUI);
        this.attendeeS = new AttendeeSystem(loginM);
        this.organizerS = new OrganizerSystem(loginM);
        this.speakerS = new SpeakerSystem(loginM,);
    }

    public void run(){
        Scanner scannerApp = new Scanner(System.in);
        int userInput;
        startUI.startup();
        userInput = chooseMode(scannerApp);

        int currAccountType = -1;
        switch (userInput){
            case 1:
                currAccountType = loginS.run();
                break;
            case 2:
                signS.run();
                currAccountType = loginS.run();
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


    private int chooseMode(Scanner scannerApp){
        String userInput;
        int mode = -1;
        boolean valid = false;
        while(!valid){
            userInput = scannerApp.nextLine();
            if (!isValidChoice(userInput))
                System.out.println("Please select one operation from below:\n1 -> login\n2 -> signup");
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
