package UI;

import java.util.Scanner;

public class SpeakerUI {
    private final Scanner speakerscanner;

    public SpeakerUI(){
        this.speakerscanner = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("----------------SpeakerSystem-----------------\nHi, Speaker! Would you like to\n1 -> Read Your Talks\n2 -> Message\n3 -> Quit");
    }

    public String getrequest(){
        System.out.println("Please Enter Your Response");
        return speakerscanner.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");

    }
    public void messaging(){
        System.out.println("----------------Messaging-----------------\nHi, Speaker! Would you like to\n1 -> Me" +
                "ssage to one attendee\n2 -> Message to attendees in talks\n3 -> Message all att" +
                "endees who register your talks");
    }
    //TODO
    public String enteringtext(){
        System.out.println("Please Enter Your Message in One Line");
        String a = new String();
        while(speakerscanner.hasNext()) {a += speakerscanner.nextLine();}
        return a;

    }
    public String getrequest2(){
        System.out.println("Please Enter The ID of the Talk. Enter ");
        return speakerscanner.nextLine();
    }

}
