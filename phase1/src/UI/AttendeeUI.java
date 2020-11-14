package UI;

import java.util.Scanner;


public class AttendeeUI {
    private final Scanner attendeescanner;

    public AttendeeUI() {
        this.attendeescanner = new Scanner(System.in);
    }

    //not yet implement the message part
    public void startup() {
        System.out.println("----------------AttendeeSystem-----------------\nHi, " +
                "Attendee! Would you like to\n1 -> See your talk schedule\n2 -> Sign up a new talk\n3 -> Cancel a talk \n4 -> Quit");
    }
    public String getrequest(){
        System.out.println("Please Enter Your Response");
        return attendeescanner.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");
    }

    //should also print a list of available talks
    public void signUpTalk(){
        System.out.println("----------------Signing up a talk-----------------\nPlease enter the id of the talk that you want to sign up");
    }

    public void cancelTalk(){
        System.out.println("----------------Cancelling a talk-----------------\nPlease enter the id of the talk that you want to cancel");
    }

    public void msgSelect(){
        System.out.println("----------------Message Interface-----------------\nHi, " +
                "Would you like to\n1 -> View your inbox\n2 -> Send a message");
    }
}


