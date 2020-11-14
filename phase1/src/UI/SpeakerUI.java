package UI;

import java.util.Scanner;

public class SpeakerUI {
    private final Scanner speakerscanner;

    public SpeakerUI(){
        this.speakerscanner = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("----------------SpeakerSystem-----------------\n" +
                "Hi, Speaker! Would you like to\n1 -> Read Your Talks\n2 -> Message\n3 -> Quit");
    }

    public String getrequest(){
        System.out.println("Please Enter Your Response");
        return speakerscanner.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");

    }
    public void messaging(){
        System.out.println("----------------Messaging-----------------");
        System.out.println("Hi, Speaker! Would you like to:");
        System.out.println("1 -> Message to an attendee.\n" +
                            "2 -> Message to attendees in talks\n" +
                            "3 -> Message all attendees who register your talks\n" +
                            "4 -> Go back.");
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

    public void messagesend(){
        System.out.println("Message Send");

    }
    public void noattendees(){
        System.out.println("No Attendees");

    }
    public void stopmessaging(){
        System.out.println("Stop Messaging");

    }
    public void show(String a){System.out.println(a);}

}
