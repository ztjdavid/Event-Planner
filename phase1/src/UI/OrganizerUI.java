package UI;

import Entity.Account;

import java.util.ArrayList;
import java.util.Scanner;


public class OrganizerUI {
    private final Scanner input;

    public OrganizerUI(){
        this.input = new Scanner(System.in);
    }

    public void startup(){
        System.out.println("----------------OrganizerSystem-----------------\nHi, " +
                "Organizer! Would you like to\n1 -> Message\n2 -> Schedule a Talk\n3 -> Create a Speaker Account\n4 -> Change Your" +
                " Password\n5 -> Change your UserName\n6 -> Quit");
    }

    public String addTalkPrompt(){
        System.out.println("Would you like to\n1 -> Add a Talk\n2 -> Quit");
        return input.nextLine();
    }

    public String scheduleMode(){
        System.out.println("Would you like to\n1 -> Schedule a Talk\n2 -> Reschedule a Talk\n3 -> Quit");
        return input.nextLine();
    }

    public int getRoomID(){
        System.out.println("Please Enter the RoomID of the Room of the Talk:");
        return input.nextInt();
    }

    public int getSpeakerID(){
        System.out.println("Please Enter the SpeakerId of the Speaker of the Talk:");
        return input.nextInt();
    }

    public int getTalkID(){
        System.out.println("Please Enter the TalkID of the Talk:");
        return input.nextInt();
    }

    public int getOldTalkID(){
        System.out.println("Please Enter the ID of the Talk you would like to change:");
        return input.nextInt();
    }

    public int getNewTalkID(){
        System.out.println("Please Enter the New ID of the new Talk you would like to schedule");
        return input.nextInt();
    }

    public String getRequest(){
        System.out.println("Please Enter Your Response:");
        return input.nextLine();
    }

    public String getSpeakerUsername(){
        System.out.println("Please Enter The Username of The Speaker:");
        return input.nextLine();
    }

    public String getSpeakerpwd1(){
        System.out.println("Please Enter The Password of The Speaker:");
        return input.nextLine();
    }

    public String getSpeakerpwd2(){
        System.out.println("Great! Please Enter The Password of The Speaker Again:");
        return input.nextLine();
    }

    public String currPwd(){
        System.out.println("Please Enter Your Current Password:");
        return input.nextLine();
    }

    public String getNewPwd(){
        System.out.println("Password Correct! Please Enter Your New Password:");
        return input.nextLine();
    }

    public String getNewPwd2(){
        System.out.println("Great! Please Enter Your New Password again:");
        return input.nextLine();
    }

    public String getNewUsername(){
        System.out.println("Password Correct! Please Enter Your New Username!");
        return input.nextLine();
    }

    public String getTalkTitle(){
        System.out.println("Please Enter the Talk Title:");
        return input.nextLine();
    }

    public int getTalkStartTime(){
        System.out.println("Please Enter the Start Time of the Talk:");
        return input.nextInt();
    }

    public int getTalkRoomID(){
        System.out.println("Please Enter the Room ID of the Talk:");
        return input.nextInt();
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

    public void displayCurrUsername(String str){
        System.out.println("Your Current User Name is:" + str);
    }

    public void displayNewUsername(String str){
        System.out.println("Username Changed Successfully! Your New Username is:" + str);
    }

    public void messageToDisplay(int i){
        switch (i){
            case 1:
                System.out.println("Message sent!");
            case 2:
                System.out.println("Messages sent!");
            case 3:
                System.out.println("Failed to send!");
            case 4:
                System.out.println("Quit Messaging System.");
            case 5:
                System.out.println("Quit.");
            case 6:
                System.out.println("Password Changed Successfully!");
            case 7:
                System.out.println("Passwords Do Not Match! Please try again!");
            case 8:
                System.out.println("Incorrect Password! Please try again!");
            case 9:
                System.out.println("Speaker Account Successfully Created!");
            case 10:
                System.out.println("Fail to Add! The Talk Already Exists!");
            case 11:
                System.out.println("Successfully Added Talk!");
            case 12:
                System.out.println("There is a Time Conflict with the Existing Talks");
            case 13:
                System.out.println("The Speaker Has Been Successfully Scheduled.");
            case 14:
                System.out.println("The Speaker cannot be scheduled due to conflicts.");

        }
    }

    public void displayAllSpeakers(ArrayList<Account> lst){
        StringBuilder a = new StringBuilder("These are the all the speakers in the system. Choose an id to message");
        for(Account acc : lst) {
            int speakerID = acc.getUserId();
            String speakerName = acc.getUsername();
            a.append("\n").append("\n").append(speakerName).append("id:").append(speakerID);
        }
        System.out.println(a);
    }

    public void displayAllAttendees(ArrayList<Account> lst){
        StringBuilder a = new StringBuilder("These are the all the attendees in the system. Choose an id to message");
        for(Account acc : lst) {
            int attendeeID = acc.getUserId();
            String attendeeName = acc.getUsername();
            a.append("\n").append("\n").append(attendeeName).append("id:").append(attendeeID);
        }
        System.out.println(a);
    }


}
