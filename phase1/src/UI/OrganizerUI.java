package UI;

import java.util.*;


public class OrganizerUI {
    private final Scanner input;

    public OrganizerUI(){
        this.input = new Scanner(System.in);
    }

    public void startup(){
        System.out.println("----------------OrganizerSystem-----------------" +
                "\nHi, Organizer! Would you like to" +
                "\n1 -> Message" +
                "\n2 -> Create a Speaker Account" +
                "\n3 -> Schedule a Talk" +
                "\n4 -> Create a Room" +
                "\n5 -> Logout");
    }

    public int getRoomID(){
        System.out.println("Please Enter the RoomID of the Room of the Talk:");
        return input.nextInt();
    }

    public int getSpeakerID(){
        System.out.println("Please Enter the SpeakerId of the Speaker of the Talk:");
        return input.nextInt();
    }

    public int getNewTime(){
        System.out.println("Please Enter the New Start Time of the Talk you would like to change:");
        return input.nextInt();
    }

    public int getReTalkID(){
        System.out.println("Please Enter the ID of the Talk you would like to change:");
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

    public String getSpeakerPwd1(){
        System.out.println("Please Enter The Password of The Speaker:");
        return input.nextLine();
    }

    public String getSpeakerPwd2(){
        System.out.println("Great! Please Enter The Password of The Speaker Again:");
        return input.nextLine();
    }

    public String getTalkTitle(){
        System.out.println("Please Enter the Talk Title:");
        return input.nextLine();
    }

    public int getTalkStartTime(){
        System.out.println("Please Enter the Start Time of the Talk (the Start Time Must be Between 9 - 17 in 24-hour format):");
        return input.nextInt();
    }

    public void informInvalidChoice(){
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

    public void messaging2(){
        System.out.println("----------------Creating Speaker Account-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a speaker" +
                "\n2 -> Read All Speaker" +
                "\n3 -> Go back");
    }

    public void messaging3(){
        System.out.println("----------------Creating Talk-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a Talk" +
                "\n2 -> Read all Talks" +
                "\n3 -> Go back");
    }

    public void messaging4(){
        System.out.println("----------------Creating Room-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a Room" +
                "\n2 -> Read all Rooms" +
                "\n3 -> Go back");
    }


    public void infoEnteringText(){
        System.out.println("Please Enter Your Message." +
                "\n(End editing by typing a single \"end\" in a new line.)");
    }

    public void readTalks(String title, int ID, int startTime, int roomID){
        System.out.println("Talk Title:" + title + " Talk ID:" + ID + " Start time:" + startTime + " Room ID:" +
                roomID + "\n");
    }

    public String getLineTxt(){
        return input.nextLine();
    }

    public void askForBack(){
        System.out.println("\nPress enter to go back.");
        input.nextLine();
    }

    public void announceReply(){System.out.println("Please remember the id of the message " +
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

    public void message4(){
        System.out.println("The Speaker Has Been Successfully Scheduled.");
    }

    public void message5(){
        System.out.println("The Speaker cannot be scheduled due to conflicts.");
    }

    public void message2(){
        System.out.println("There is a Time Conflict with the Existing Talks"); }

    public void message3(int ID){
        System.out.println("Speaker Created Successfully with ID:" + ID);
    }

    public void message6(){
        System.out.println("These are All of the Rooms:");
    }

    public void message7(){
        System.out.println("These are ALl of the Talks");
    }

    public void message11(){
        System.out.println("There is No Message in Your Inbox.");
    }

    public void message12(int id){
        System.out.println("Talk Created Successfully with id:" + id);
    }

    public void announceMsg(){System.out.println("Please remember the id of the replier " +
            "if you want to message to.");
    }

    public String getRoomName(){
        System.out.println("Please Enter the Name of the New Room:");
        return input.nextLine();
    }

    public void message13(int id){
        System.out.println("Room Created Successfully with id:" + id);
    }

    public void readAllRooms(int id, String roomName, HashMap<Integer, Integer> timeTable){
        System.out.println("Room ID:" + id + " Room Name:" + roomName);
        printHashMap(timeTable);
    }

    private void printHashMap(HashMap<Integer, Integer> timeTable){
        System.out.println("\n---------Time Table---------");
        Set<Map.Entry<Integer, Integer>> tool = timeTable.entrySet();
        for(Map.Entry<Integer, Integer> entry: tool){
            System.out.print(entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("\n---------Time Table---------");
    }

    public void message14(){
        System.out.println("Talk Creation Failed! Reason: StartTime Conflict in the Given Room.");
    }

    public void message15(){
        System.out.println("Talk Creation Failed! Reason: StartTime Conflict with the Given Speaker.");
    }

    public void message16(){
        System.out.println("These are All of the Speakers");
    }

    public void message17(){
        System.out.println("Creation Failed! The username Already Exists!");
    }

    public void readSpeakers(String userName, int accountID, ArrayList<Integer> talks){
        System.out.println("Speaker Account ID:" + accountID + " Username:" + userName);
        readTalks(talks);
    }
    public void errorMessage(){
        System.out.println("Scheduling Failed! The StartTime of Talk Must be Between 9 - 17! Please Try again!");
    }

    private void readTalks(ArrayList<Integer> talks){
        System.out.println("\n---------IDs of the Speaker's Talks---------");
        for(int item : talks){
            System.out.println(item + "\n");
        }
        System.out.println("\n---------IDs of the Speaker's Talks---------");
    }


}
