package UI;

import java.util.Scanner;

public class SpeakerUI {
    private final Scanner speakerscanner;

    public SpeakerUI(){
        this.speakerscanner = new Scanner(System.in);
    }


    public void startup(){
        System.out.println("----------------SpeakerSystem-----------------\n" +
                "Hi, Speaker! Would you like to\n1 -> Read Your Talks\n2 -> Message\n3 -> Logout");
    }

    public String getrequest(int s){
        switch (s){
            case 1:
                System.out.println("Please Enter Your Response.");
                break;
            case 2:
                System.out.println("Please Enter Your Response(Enter -1 to go back.)");
                break;
        }
        return speakerscanner.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");

    }
    public void messaging(){
        System.out.println("----------------Messaging-----------------");
        System.out.println("Hi, Speaker! Would you like to:");
        System.out.println("1 -> Message to an attendee.\n" +
                            "2 -> Message to attendees in one talk\n" +
                            "3 -> Message all attendees who register your talks\n" +
                            "4 -> Read your replies and send message to repliers\n" +
                            "5 -> Read your messages and reply to senders\n"+
                            "6 -> Go back.");
    }

    public void informEnteringText(){
        System.out.println("Please Enter Your Message.\n " +
                "(End editing by typing a single \"end\" in a new line.)");
    }

    public String getLineTxt(){
        return speakerscanner.nextLine();
    }

    public String getrequest2(){
        System.out.println("Please Enter the ID of ONE Event. Enter -1 to cancel and go back. ");
        return speakerscanner.nextLine();
    }

    public void messagesend(){
        System.out.println("Message Send\n");

    }
    public void noattendees(){
        System.out.println("No Attendees");

    }
    public void stopmessaging(){
        System.out.println("Stop Messaging");

    }
    public void show(String a){System.out.println(a + "\n");}

    public void announcereply(){System.out.println("Please enter the id of the message " +
            "if you want to reply to.");
    }

    public void announcemsg(){System.out.println("Please enter the id of the replier " +
            "if you want to message to.");
    }

    public void askForBack(){
        System.out.println("\nPress enter to go back.");
        speakerscanner.nextLine();
    }

    public String confirmMsgAll(){
        System.out.println("Are you sure to message all attendees in this system?" +
                "\nEnter 1 to confirm, 0 to cancel and go back.(Irreversible once confirmed.)");
        return speakerscanner.nextLine();
    }

}
