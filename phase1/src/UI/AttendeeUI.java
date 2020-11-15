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
                "Attendee! Would you like to\n1 -> View your signed up talks\n2 -> Sign up for a new talk\n3 -> Cancel a talk\n4 ->Send message \n5 -> Quit");
    }
    public String getrequest(){
        System.out.println("Please Enter Your Response");
        return attendeescanner.nextLine();
    }

    public String getrequest1(){
        System.out.println("Please Enter Your Response(Enter -1 to go back.): ");
        return attendeescanner.nextLine();
    }

    public String getrequest2(){
        System.out.println("Please Enter The Valid ID of the Talk(Enter -1 to go back.): ");
        return attendeescanner.nextLine();
    }

    public String getrequest3(){
        System.out.println("Please Enter The Valid ID of one of your Talk(Enter -1 to go back.): ");
        return attendeescanner.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");
    }

    //should also print a list of available talks
    public void signUpTalk(){
        System.out.println("----------------Signing up a talk-----------------\n" +
                "Please enter the id of the talk that you want to sign up");
    }

    public void signUpSuc(){
        System.out.println("You have signed up a new talk.");
    }

    public void cancelTalk(){
        System.out.println("----------------Cancelling a talk-----------------\n" +
                "Please enter the id of the talk that you want to cancel");
    }

    public void cancelSuc(){
        System.out.println("You have canceled one of your talks.");
    }

    public void msgSelect(){
        System.out.println("----------------Message Interface-----------------\nHi, " +
                "Would you like to\n1 -> Send Message to a attendee\n2 -> Send a message to the speaker of a talk\n3 -> View your inbox\n4 -> Quit");
    }

    public void show(String a){System.out.println(a);}

    public void informEnteringText(){
        System.out.println("Please Enter Your Message.\n " +
                "(End editing by typing a single \"end\" in a new line.)");
    }
    public String getLineTxt(){
        return attendeescanner.nextLine();
    }

    public void messagesend(){ System.out.println("Message Send\n"); }

    public void askForBack(){
        System.out.println("\nPress enter to go back.");
        attendeescanner.nextLine();
    }

}

