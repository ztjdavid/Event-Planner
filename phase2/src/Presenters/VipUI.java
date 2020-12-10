package Presenters;

import java.util.ArrayList;
import java.util.Arrays;

public class VipUI extends Presenter{

    public VipUI(ITextUI textUI){
        super(textUI);
    }

    public ArrayList<Integer> getchoicelist(int a){
        if(a == 1){
            return new ArrayList<>(Arrays.asList(1, 2, 3));}
        else if(a == 2){
            return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));}
        else if(a == 4){
            return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));}
        else{
            return new ArrayList<>(Arrays.asList(1, 2, 3, 4));}
    }

    public void annouceMarkUnread(){printText("Message Successfully Marked as Unread.");}
    public void archiveMsg(){ printText("Message Successfully Archived."); }
    public void deleteMsg(){ printText("Message Successfully Deleted."); }
    public void announceEmptyInbox(){ printText("There is No Message in Your Inbox."); }


    public void startup() {
        printText("----------------VIPSystem-----------------\nHi, " +
                "VIP! Would you like to\n1 -> View, Attend, Leave event\n2 -> Send message \n3 -> Application \n4 -> Log Out");
    }

    public void appmain() {
        printText("----------------Application-----------------\n" +
                "\n1 -> My Application\n2 -> Apply for Speaker \n3 -> Go back");
    }


    public void informinvalidchoice(){
        printText("Invalid Choice. Please try again.");
    }

    public void signUpTalk(){
        printText("----------------Signing up a talk-----------------\n" +
                "Please enter the id of the talk that you want to sign up");
    }

    public void signUpSuc(){
        printText("You have signed up a new talk.");
    }

    public void cancelTalk(){
        printText("----------------Cancelling a talk-----------------\n" +
                "Please enter the id of the talk that you want to cancel");
    }

    public void cancelSuc(){
        printText("You have canceled one of your talks.");
    }

    public void msgSelect(){
        printText("----------------Message Interface-----------------\nHi, " +
                "Would you like to\n1 -> Send Message to a attendee\n2 -> Send a message to the speaker of a talk\n" +
                "3 -> View your inbox\n" +
                "4 -> Read your replies and send message to repliers\n" +
                "5 -> Read your messages and reply to senders\n" +
                "6 -> Read your unread messages and mark those as read\n" +
                "7 -> Go Back");
    }

    public void eventmain(){
        printText("----------------Event Interface-----------------\nHi, " +
                "Would you like to\n1 -> Sign Up a New Event\n2 -> View All My Event\n" +
                "3 -> Leave an Event\n" + "4 -> Read Your Requests and Send New Request\n" +
                "5 -> Go back");

    }
    public void eventselect(){
        printText("----------------Sign Up Interface-----------------\nHi, " +
                "Which type of event you would like to register\n1 -> Talk\n2 -> Discussion\n" +
                "3 -> Party\n" +
                "4 -> VIP Talk\n" +
                "5 -> VIP Discussion\n" +
                "6 -> VIP Party\n" + "7 -> Go back"
                );}

    public void requestmain(){
        printText("----------------Request Interface-----------------\nHi dear VIP! Would you like to" +
                "1 -> Read all my requests with status\n" +
                "2 -> Send a new request\n" +
                "3 -> Go back\n");
    }

    public String  getrequest2(){
        printText("Please Enter the ID of ONE Event. Enter -1 to cancel and go back. ");
        return requestInput();
    }

    public void stoprequest(){printText("Stop Request");}

    public void show(String a){printText(a);}

    public void informEnteringText(){
        requestInput("Please Enter Your Message.\n " +
                "(End editing by typing a single \"end\" in a new line.)");
    }
    public String getLineTxt(){
        return requestInput();
    }

    public void messagesend(){ printText("Message sent\n"); }

    public void askForBack(){
        requestInput("\nPress enter to go back.");
    }
    /////////ERICMODIFY
    public String getrequest(int s){
        switch (s){
            case 1:
                printText("Please Enter Your Response.");
                break;
            case 2:
                printText("Please Enter Your Response(Enter -1 to go back.)");
                break;
            case 3:
                printText("Please Enter The Valid ID of the Event(Enter -1 to go back.): ");
                break;
            case 4:
                printText("Please Enter The Valid ID of one of your Event(Enter -1 to go back.): ");
                break;
        }
        return requestInput();
    }


    public void announcereply(){printText("Please enter the id of the message " +
            "if you want to reply to.");
    }

    public void announcemsg(){printText("Please enter the id of the replier " +
            "if you want to message to.");
    }


    public void annouceUnread(){printText("Please enter the id of the message" +
            "if you want to read.");}

    public void unreadSuccess(int messageId){printText("Your message with id: " + messageId +
            " has been successfully read!");}

    //////////LouisaModify
    public void signUpVipTalk(){
        printText("This is an Vip Event.");
    }

    public void informNotVip(){
        printText("You do not have the permission to sign up for a VIP event.");
    }

    public int checkapply(){
        return Integer.parseInt(requestInput("Are you sure to apply to be a speaker? If not, enter -1"));
    }

    public String whyapply(){
        return requestInput("Please Enter Why You Apply.\n " +
                "(End editing by typing a single \"end\" in a new line.)");

    }

    public void appsend(){
        printText("Your application has send!");
    }


}

