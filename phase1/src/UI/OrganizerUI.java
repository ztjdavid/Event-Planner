package UI;

import java.util.HashMap;
import java.util.Scanner;


public class OrganizerUI {
    private final Scanner input;

    public OrganizerUI(){
        this.input = new Scanner(System.in);
    }

    public void startup(){
        System.out.println("----------------OrganizerSystem-----------------" +
                "\nHi, Organizer! Would you like to" +
                "\n1 -> Message" +
                "\n2 -> Schedule a Talk" +
                "\n3 -> Create a Speaker Account" +
                "\n4 -> Logout");
    }

    public String addTalkPrompt(){
        System.out.println("Would you like to" +
                "\n1 -> Add a Talk" +
                "\n2 -> Go Back");
        return input.nextLine();
    }

    public String intChooseMode3(){
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
        System.out.println("Please Enter Your Response (Enter -1 to go back.):");
        return input.nextLine();
    }

    public String getRequest1(){
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
        System.out.println("----------------Messaging-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Message to one attendee" +
                "\n2 -> Message to one speaker" +
                "\n3 -> Message to all of the attendees" +
                "\n4 -> Message to all speakers" +
                "\n5 -> Read your replies and send message to repliers" +
                "\n6 -> Read all Messages in Inbox" +
                "\n7 -> Read your messages and reply to senders" +
                "\n8 -> Go Back");
    }

    public void messaging1(){
        System.out.println("----------------Scheduling-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Schedule a talk for a speaker" +
                "\n2 -> Reschedule an existing talk with a new talk" +
                "\n3 -> Go back");
    }

    public void messaging2(){
        System.out.println("----------------Creating Speaker Account-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n0 -> Create a speaker" +
                "\n1 -> Go back");
    }

    public void infoEnteringText(){
        System.out.println("Please Enter Your Message." +
                "\n(End editing by typing a single \"end\" in a new line.)");
    }

    public String getLineTxt(){
        return input.nextLine();
    }

    public void askForBack(){
        System.out.println("\nPress enter to go back.");
        input.nextLine();
    }

    public void announcereply(){System.out.println("Please remember the id of the message " +
            "if you want to reply to.");
    }

    public void display(String str){
        System.out.println(str);
    }

    public String confirmMsgAll(){
        System.out.println("Are you sure to message all attendees in this system?" +
                "\nEnter 1 to confirm, 0 to cancel and go back.(Irreversible once confirmed.)");
        return input.nextLine();
    }

    public void show(String a){System.out.println(a + "\n");}

    public void message10(){
        System.out.println("Messages sent!");
    }

    public void message0(){
        System.out.println("Failed to send!");
    }

    public void message8(){
        System.out.println("Message sent!");
    }

    public void message1(){
        System.out.println("Passwords Do Not Match! Please try again!");
    }

    public void message9(){
        System.out.println("Speaker Account Successfully Created!");
    }

    public void message6(){
        System.out.println("Successfully Added Talk!");
    }

    public void message7(){
        System.out.println("Fail to Add!");
    }

    public void message4(){
        System.out.println("The Speaker Has Been Successfully Scheduled.");
    }

    public void message5(){
        System.out.println("The Speaker cannot be scheduled due to conflicts.");
    }

    public void message2(){
        System.out.println("There is a Time Conflict with the Existing Talks"); }

    public void message3(){
        System.out.println("Would You Like To Add a Talk to the New Speaker?:" +
                "\n0 -> Yes, I would Like to Add a Talk" +
                "\n1 -> No, Go Back");
    }

    public void message11(){
        System.out.println("There is No Message in Your Inbox.");
    }

    public void announceMsg(){System.out.println("Please remember the id of the replier " +
            "if you want to message to.");
    }


}
