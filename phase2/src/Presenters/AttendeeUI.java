package Presenters;



public class AttendeeUI extends Presenter{


    public AttendeeUI(ITextUI textUI) {
        super(textUI);
    }


    public void startup() {
        printText("----------------AttendeeSystem-----------------\nHi, " +
                "Attendee! Would you like to\n1 -> View your signed up events\n" +
                "2 -> Sign up for a new events\n" +
                "3 -> Cancel a events\n4 -> Send message \n5 -> Logout");
    }

    public void informinvalidchoice(){
        printText("Invalid Choice. Please try again.");
    }

    //should also print a list of available talks
    public void signUpTalk(int s){
        switch (s){
            case 1:
                printText("----------------Signing up a talk-----------------\n" +
                        "Please enter the id of the talk that you want to sign up");
                break;
            case 2:
                printText("----------------Signing up a discussion-----------------\n" +
                        "Please enter the id of the discussion that you want to sign up");
                break;
            case 0:
                printText("----------------Signing up a party-----------------\n" +
                        "Please enter the id of the party that you want to sign up");
                break;
        }
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

    public void eventselect(){
        printText("----------------Event Interface-----------------\nHi, " +
                "Which type of event you would like to register\n" +
                "1 -> Talk\n" +
                "2 -> Discussion\n" +
                "3 -> Party\n" +
                "4 -> Go back"
        );
    }

    public void requestdb(){
        printText("--------------Your Request------------\nHi Attendee! Would you like to:" +
                "\n1 -> Read all my requests with status" +
                "\n2 -> Send new request\n3 -> Go back");
    }

    public void stoprequest(){printText("Stop Request");}

    public void show(String a){printText(a);}

    public void informEnteringText(){
        printText("Please Enter Your Message.\n " +
                "(End editing by typing a single \"end\" in a new line.)");
    }
    public String getLineTxt(){
        return requestInput();
    }

    public void messagesend(){ printText("Message sent\n"); }

    public void askForBack(){
        requestInput("\nPress enter to go back.");
    }
    /////////ERIC////////////////COMBINED ALL GET REQUEST METHODS TO 1///////////////
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

    public String getrequest2(){
        printText("Please Enter the ID of ONE Event. Enter -1 to cancel and go back. ");
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
    public void annouceMarkUnread(){printText("Message Successfully Marked as Unread.");}

    public void unreadSuccess(int messageId){printText("Your message with id: " + messageId +
            " has been successfully read!");}

    public void announceEmptyInbox(){ printText("There is No Message in Your Inbox."); }

    public void archiveMsg(){ printText("Message Successfully Archived."); }

    public void deleteMsg(){ printText("Message Successfully Deleted."); }

    //////////LouisaModify
    public void signUpVipTalk(){
        printText("This is an Vip Event.");
    }

    public void informNotVip(){
        printText("You do not have the permission to sign up for a VIP event.");
    }

    public void eventmain(){
        printText("----------------Event Interface-----------------\nHi, " +
                "Would you like to\n1 -> Sign Up a New Event\n2 -> View All My Event\n" +
                "3 -> Leave an Event\n" +
                "4 -> Go back");

    }






}

