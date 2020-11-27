package Presenters;

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
                "\n3 -> Schedule a Event" +
                "\n4 -> Create a Room" +
                "\n5 -> Logout");
    }

    public int getRoomID(){
        System.out.println("Please Enter the RoomID of the Room of the Event:");
        return input.nextInt();
    }

    public int getSpeakerID(){
        System.out.println("Please Enter the user id of the Speaker of the Event:");
        return input.nextInt();
    }

    public int getSpeakerNum(){
        System.out.println("Please Enter Number of Speakers of the discussion (must be greater than 1):");
        return input.nextInt();
    }

    public int getNewTime(){
        System.out.println("Please Enter the New Start Time of the Event you would like to change:");
        return input.nextInt();
    }

    public int getReTalkID(){
        System.out.println("Please Enter the ID of the Event you would like to change:");
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
        System.out.println("Please Enter the Event Title:");
        return input.nextLine();
    }

    public int getTalkStartTime(){
        System.out.println("Please Enter the Start Time of the Event (the Start Time Must be Between 9 - 17 in 24-hour format):");
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
        System.out.println("----------------Creating Event-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a Talk" +
                "\n2 -> Create a Discussion" +
                "\n3 -> Create a Party" +
                "\n4 -> Read all Events" +
                "\n5 -> Go back");
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

    public void readTalks(String title, int ID, int startTime, String roomName, int roomId, int type,
                          ArrayList<Integer> speakers, ArrayList<Integer> attendees){
        System.out.println("-------------------------");
        if(type == 1){
            StringBuilder str1 = new StringBuilder();
            for(int item:attendees){
                str1.append(item);
                str1.append("\n");
            }
            System.out.println("Event Title:" + title + "\nEvent ID:" + ID + "\nStart time:" + startTime + "\nRoom:" +
                    roomName + "(id: " + roomId + ")" + "\nType:talk" + "\nSpeaker:" + speakers.get(0) + "\nAttendees:\n" + str1);
        }else if(type == 0){
            StringBuilder str2 = new StringBuilder();
            for(int item:attendees){
                str2.append(item);
                str2.append("\n");
            }
            System.out.println("Event Title:" + title + "\nEvent ID:" + ID + "\nStart time:" + startTime + "\nRoom:" +
                    roomName + "(id: " + roomId + ")" + "\nType:party" + "\nAttendees:\n" + str2);
        }else{
            StringBuilder str3 = new StringBuilder();
            for(int item:attendees){
                str3.append(item);
                str3.append("\n");
            }
            StringBuilder str4 = new StringBuilder();
            for(int item:speakers){
                str4.append(item);
                str4.append("\n");
            }
            System.out.println("Event Title:" + title + "\nEvent ID:" + ID + "\nStart time:" + startTime + "\nRoom:" +
                    roomName + "(id: " + roomId + ")" + "\nType:discussion" + "\nAttendees:" + str3 + "\nSpeakers:\n" + str4);
        }
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
        System.out.println("Speaker Creation Failed! Passwords Do Not Match!");
    }

    public void message3(int ID){
        System.out.println("Speaker Created Successfully with ID:" + ID);
    }

    public void message6(){
        System.out.println("These are All of the Rooms:");
    }

    public void message7(){
        System.out.println("These are ALl of the Events");
    }

    public void message11(){
        System.out.println("There is No Message in Your Inbox.");
    }

    public void message12(int id){
        System.out.println("Event Created Successfully with id:" + id);
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
        System.out.println("Room ID:" + id + "\nRoom Name:" + roomName);
        printHashMap(timeTable);
    }

    private void printHashMap(HashMap<Integer, Integer> timeTable){
        System.out.println("---------Time Table---------");
        Set<Map.Entry<Integer, Integer>> tool = timeTable.entrySet();
        for(Map.Entry<Integer, Integer> entry: tool){
            System.out.print(entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("----------------------------");
    }

    public void message14(){
        System.out.println("Event Creation Failed! Reason: StartTime Conflict in the Given Room.");
    }

    public void message15(){
        System.out.println("Event Creation Failed! Reason: StartTime Conflict with the Given Speaker.");
    }

    public void message16(){
        System.out.println("These are All of the Speakers:");
    }

    public void message17(){
        System.out.println("Creation Failed! The username Already Exists!");
    }

    public void message18() {
        System.out.println("Event Creation Failed! Given room id is invalid.");
    }

    public void message19() { System.out.println("Event Creation Failed! Invalid speaker."); }

    public void readSpeakers(String userName, int accountID, ArrayList<Integer> talks){
        System.out.println("Speaker Account ID:" + accountID + "\nUsername:" + userName);
        readTalks1(talks);
    }
    public void errorMessage(){
        System.out.println("Scheduling Failed! The StartTime of Event Must be Between 9 - 17! Please Try again!");
    }

    private void readTalks1(ArrayList<Integer> talks){
        System.out.println("---------IDs of the Speaker's Talks---------");
        for(int item : talks){
            System.out.println(item);
        }
        System.out.println("--------------------------------------------");
    }

    public int roomCapacity(){
        System.out.println("Please Enter the Room Capacity:");
        return input.nextInt();
    }


}
