package UI;

import java.util.Scanner;

public class SpeakerUI {
    private final Scanner speakerscanner;

    public SpeakerUI(){
        this.speakerscanner = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("----------------SpeakerSystem-----------------\nHi, Speaker! Would you like to\n1 -> Read Your Talks\nn2 -> Message");
    }

    public String getrequest(){
        System.out.println("Please Enter Your Response");
        return speakerscanner.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");

    }

}
