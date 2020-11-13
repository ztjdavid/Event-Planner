package UI;

import java.util.Scanner;


public class OrganizerUI {
    private final Scanner input;

    public OrganizerUI(){
        this.input = new Scanner(System.in);
    }

    public void startup(){
        System.out.println("----------------OrganizerSystem-----------------\nHi, " +
                "Organizer! Would you like to\n1 -> Message\n2 -> Schedule a Talk\n3 -> Create Speaker Account\n4 -> Quit");
    }

    public String getRequest(){
        System.out.println("Please Enter Your Response");
        return input.nextLine();
    }

    public void informinvalidchoice(){
        System.out.println("Invalid Choice. Please try again.");
    }

    public void messaging(){
        System.out.println("----------------Messaging-----------------\nHi, Speaker! Would you like to\n1 -> Me" +
                "ssage to one attendee\n2 -> Message to one speaker\n3 -> Message to all of the attendees\n4 -> Message to all speakers");
    }

    public String enteringText() {
        System.out.println("Please Enter Your Message in One Line");
        StringBuilder a = new StringBuilder();
        while (input.hasNext()) {
            a.append(input.nextLine());
        }
        return a.toString();
    }


}
