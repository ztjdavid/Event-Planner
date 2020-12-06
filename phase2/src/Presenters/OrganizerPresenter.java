package Presenters;

import java.lang.reflect.Array;
import java.util.*;


public class OrganizerPresenter extends Presenter{

    public OrganizerPresenter(ITextUI textUI){
        super(textUI);
    }

    public void startup(){
        printText("----------------OrganizerSystem-----------------" +
                "\nHi, Organizer! Would you like to" +
                "\n1 -> Message Dashboard" +
                "\n2 -> Account Dashboard" +
                "\n3 -> Event Dashboard" +
                "\n4 -> Room Dashboard" +
                "\n5 -> Request Dashboard" +
                "\n6 -> Logout");
    }

    public int getRoomID(){
        return requestNumInput("Please Enter the RoomID of the Room of the Event:");
    }

    public int getSpeakerID(){
        return requestNumInput("Please Enter the user id of the Speaker of the Event:");
    }

    public int getSpeakerNum(){
        return requestNumInput("Please Enter Number of Speakers of the discussion (must be greater than 1):");
    }

    public int getReTalkID(){
        return requestNumInput("Please Enter the ID of the Event you would like to change:");
    }

    public String getRequest(){
        return requestInput("Please Enter Your Response (Enter -1 to go back.):");
    }

    public String getSpeakerUsername(){
        return requestInput("Please Enter The Username of The Speaker:");
    }

    public String getAttendeeUsername(){
        return requestInput("Please Enter The Username of The Attendee");
    }

    public String getVIPUsername(){
        return requestInput("Please Enter The Username of The VIP Attendee");
    }

    public String getSpeakerPwd1(){
        return requestInput("Please Enter The Password:");
    }

    public String getSpeakerPwd2(){
        return requestInput("Great! Please Enter The Password Again:");
    }

    public boolean getVipEvent(){
        ArrayList<Integer> Options = new ArrayList<Integer>();
        Options.add(0);
        Options.add(1);
        int result;
        result = chooseOption(Options, "Is this Event only for VIP? (Enter 0 if true, 1 if false)","Invalid choice, please try again.");
        return result == 1;
    }

    public String getTalkTitle(){
        return requestInput("Please Enter the Event Title:");
    }

    public int getTalkStartTime(){
        return requestNumInput("Please Enter the Start Time of the Event (the Start Time Must be Between 9 - 17 in 24-hour format):");
    }

    public void informInvalidChoice(){
        printText("Invalid Choice. Please try again.");
    }

    public void messaging(){
        printText("----------------Message Dashboard-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Message to one attendee" +
                "\n2 -> Message to one speaker" +
                "\n3 -> Message to all of the attendees" +
                "\n4 -> Message to all speakers" +
                "\n5 -> Read your replies and send message to repliers" +
                "\n6 -> Read all of Unread Messages" +
                "\n7 -> Read all of Archived Messages"+
                "\n8 -> Go Back");
    }

    public void messaging2(){
        printText("----------------Account DashBoard-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a speaker" +
                "\n2 -> Create an Attendee" +
                "\n3 -> Create a VIP Attendee" +
                "\n4 -> Read All Accounts" +
                "\n5 -> Go back");
    }

    public void messaging3(){
        printText("----------------Event Dashboard-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a Talk" +
                "\n2 -> Create a Discussion" +
                "\n3 -> Create a Party" +
                "\n4 -> Cancel an event" +
                "\n5 -> Read all Events" +
                "\n6 -> Go back");
    }

    public void messaging4(){
        printText("----------------Room Dashboard-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Create a Room" +
                "\n2 -> Read all Rooms" +
                "\n3 -> Go back");
    }

    public void messaging1(){
        printText("----------------Request Dashboard-----------------" +
                "\nHi, Organizer! Would you like to:" +
                "\n1 -> Read all Request" +
                "\n2 -> Change the Status of a Request" +
                "\n3 -> Go back");
    }

    public void message20(){
        printText("Message Successfully Marked as Unread.");
    }

    public void message21(){
        printText("Message Successfully Archived.");
    }

    public void message22(){
        printText("Message Successfully Deleted.");
    }


    public void readTalks(String title, int ID, int startTime, String roomName, int roomId, int type,
                          ArrayList<Integer> speakers, ArrayList<Integer> attendees, int duration){
        printText("-------------------------");
        if(type == 1){
            StringBuilder str1 = new StringBuilder();
            for(int item:attendees){
                str1.append(item);
                str1.append("\n");
            }
            printText("Event Title:" + title + "\nEvent ID:" + ID + "\nStart time:" + startTime + "\nDuration:" +duration + "\nRoom:" +
                    roomName + "(id: " + roomId + ")" + "\nType:talk" + "\nSpeaker:" + speakers.get(0) + "\nAttendees:\n" + str1);
        }else if(type == 0){
            StringBuilder str2 = new StringBuilder();
            for(int item:attendees){
                str2.append(item);
                str2.append("\n");
            }
            printText("Event Title:" + title + "\nEvent ID:" + ID + "\nStart time:" + startTime + "\nDuration:" +duration +"\nRoom:" +
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
            printText("Event Title:" + title + "\nEvent ID:" + ID + "\nStart time:" + startTime + "\nDuration:" +duration + "\nRoom:" +
                    roomName + "(id: " + roomId + ")" + "\nType:discussion" + "\nAttendees:" + str3 + "\nSpeakers:\n" + str4);
        }
    }

    public void askForBack(){
        pause();
    }

    public void announceReply(){printText("Please remember the id of the message " +
            "if you want to reply to.");
    }

    public void display(String str){
        printText(str);
    }

    public void show(String a){printText(a + "\n");}

    public void message10(){
        printText("Messages sent!");
    }

    public void message0(){
        printText("Failed to send!");
    }

    public void message8(){
        printText("Message sent!");
    }
    public void message8(int replyID){
        printText("Reply sent with ID:" + replyID + "!");
    }

    public void message1(){
        printText("Account Creation Failed! Passwords Do Not Match!");
    }

    public void message3(int ID){
        printText("Speaker Created Successfully with ID:" + ID);
    }

    public void message6(){
        printText("These are All of the Rooms:");
    }

    public void message7(){
        printText("These are ALl of the Events");
    }

    public void message11(){
        printText("There is No Message in Your Inbox.");
    }

    public void message12(int id){
        printText("Event Created Successfully with id:" + id);
    }

    public void announceMsg(){printText("Please remember the id of the replier " +
            "if you want to message to.");
    }

    public String getRoomName(){
        return requestInput("Please Enter the Name of the New Room:");
    }

    public void message13(int id){
        printText("Room Created Successfully with id:" + id);
    }

    public void readAllRooms(int id, String roomName, HashMap<Integer, Integer> timeTable){
        printText("Room ID:" + id + "\nRoom Name:" + roomName);
        printHashMap(timeTable);
    }

    private void printHashMap(HashMap<Integer, Integer> timeTable){
        printText("---------Time Table---------");
        Set<Map.Entry<Integer, Integer>> tool = timeTable.entrySet();
        for(Map.Entry<Integer, Integer> entry: tool){
            System.out.print(entry.getKey() + "=" + entry.getValue());
        }
        printText("----------------------------");
    }

    public void message14(){
        printText("Event Creation Failed! Reason: StartTime Conflict in the Given Room.");
    }

    public void message15(){
        printText("Event Creation Failed! Reason: StartTime Conflict with the Given Speaker.");
    }

    public void message16(){
        printText("These are All of the Accounts:");
    }

    public void message17(){
        printText("Account Creation Failed! The username Already Exists!");
    }

    public void message18() {
        printText("Event Creation Failed! Given room id is invalid.");
    }

    public void message19() { printText("Event Creation Failed! Invalid speaker."); }

    public void readSpeakers(String userName, int accountID, ArrayList<Integer> talks){
        printText("Speaker Account ID:" + accountID + "\nUsername:" + userName);
        readTalks1(talks);
    }

    public void readAttendee(String userName, int accountID){
        printText("Attendee Account ID:" + accountID + "\nUsername:" + userName);
    }

    public void readVIP(String userName, int accountID){
        printText("VIP Attendee Account ID:" + accountID + "\nUsername:" + userName);
    }

    public void errorMessage(){
        printText("Scheduling Failed! The StartTime of Event Must be Between 9 - 17! Please Try again!");
    }

    private void readTalks1(ArrayList<Integer> talks){
        printText("---------IDs of the Speaker's Talks---------");
        for(int item : talks){
            printText((Integer.toString(item)));
        }
        printText(("--------------------------------------------"));
    }

    public int roomCapacity(){
        return requestNumInput("Please Enter the Room Capacity:");
    }

    public int eventCapacity(){
        return requestNumInput("Please Enter the Event Capacity:");
    }

    public void message2(){
        printText("Event Creation Failed! The Event Capacity Exceeds the Room Capacity:");
    }

    public int getEventID(){
        return requestNumInput("Please Enter the ID of the Event You Would Like to Cancel:");
    }

    public void message4(){
        printText("The Event Has Been Canceled!");
    }

    public void message5(){
        printText("The Event ID You Entered is Invalid!");
    }

    public void message9(int attendeeID){
        printText("Attendee Created Successfully with ID:" + attendeeID);
    }

    public void message10(int attendeeID){
        printText("VIP Attendee Created Successfully with ID:" + attendeeID);
    }

    public int message3(){
        return requestNumInput("Please Enter the Duration of the Event that satisfy these conditions:" +
                "\n1. duration must an integer greater than 0 that represents the number of hours" +
                "\n2. duration + start time must be less than 19:");
    }

    public void message9(){
        printText("Event Creation Failed Due to Time Conflict!");
    }

    public void message12(){
        printText("Event Creation Failed. The During Exceeds the Limit. Rooms are Closed After 5 P.M.!");
    }

    public void message13(){
        printText("You Do Not Have a Message That Has Been Replied!");
    }

    public void message24(){
        printText("There is Currently no Request Recorded.");
    }

    public void message25(){
        printText("Request Status Changed to Addressed Successfully!");
    }

}
