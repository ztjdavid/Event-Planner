package Controller;
import Entity.Speaker;

import java.util.Scanner;

public class AppSystem {
    protected LoginSystem loginS;
    protected SignUpSystem signS;
    protected OrganizerSystem organizerS;
    protected AttendeeSystem attendeeS;
    protected SpeakerSystem speakerS;

    public AppSystem(){
        this.loginS = new LoginSystem();
        this.signS = new SignUpSystem();
        this.attendeeS = new AttendeeSystem();
        this.organizerS = new OrganizerSystem();
        this.speakerS = new SpeakerSystem();
    }

    public void run(){
        Scanner scannerApp = new Scanner(System.in);
        int userInput;
        System.out.println("Hi, user! Would you like to\n1 -> login\n2 -> signup");
        userInput = chooseMode(scannerApp);

        int currAccountId = -1;
        switch (userInput){ // 软件初始界面
            case 1:
                currAccountId = loginS.run(); //登录系统
                break;
            case 2:
                signS.run(); //注册系统
                currAccountId = loginS.run(); //注册后登录
                break;
        }

        switch (currAccountId){ //依照用户类型进入各自操作系统
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
